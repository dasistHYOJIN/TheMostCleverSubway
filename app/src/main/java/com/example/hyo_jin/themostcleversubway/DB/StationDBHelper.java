package com.example.hyo_jin.themostcleversubway.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hyo-Jin on 2018-05-18.
 * SQLite를 통해 테이블을 만드는 등 여러 일
 *
 * insert() : DB 테이블에 데이터 insert
 * select() : DB 테이블에서 조건검색해서 Cursor 리턴
 * selectAll() : DB에서 모두 검색
 * selectLeftList() : 검색 프래그먼트에서 왼쪽 리스트뷰를 위한 SQL 검색
 * selectRightList() : 검색 프래그먼트에서 오른쪽 리스트뷰를 위한 SQL 검색
 */

public class StationDBHelper extends SQLiteOpenHelper {

    final private String TAG = "StationDBHelper";
    private Context context;
    private JSONArray jsonArray;

    public StationDBHelper(Context context) {
        super(context, "themostcleversubway.db", null, 1);
    }

    public StationDBHelper(Context context, JSONArray jsonArray) {
        super(context, "themostcleversubway.db", null, 1);
        this.jsonArray = jsonArray;
    }

    /* DB가 존재하지 않을때 DB를 만드는 역할
    * STATION_CD INTEGER, LINE_NUM TEXT, STATION_NM TEXT, FR_CODE TEXT
     * USER_ID INTEGER, STATION_DEP INTEGER, STATION_ARR INTEGER, TYPE INTEGER */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /* SQL문 작성 */

        // 지하철역 정보 테이블 생성
        StringBuffer buffer = new StringBuffer();
        buffer.append("CREATE TABLE STATION_INFO (");
        buffer.append(" STATION_CD INTEGER NOT NULL, ");
        buffer.append(" LINE_NUM TEXT, ");
        buffer.append(" STATION_NM TEXT, ");
        buffer.append(" FR_CODE TEXT, ");
        buffer.append(" PRIMARY KEY (STATION_CD)) ");
        db.execSQL(buffer.toString());

        // 지하철역 정보 테이블에 데이터 삽입
        insertData(db);

        // 사용자 테이블 생성
        buffer = new StringBuffer();
        buffer.append("CREATE TABLE USER (" +
                " USER_ID INTEGER NOT NULL, " +
                " STATION_DEP INTEGER, " +
                " STATION_ARR INTEGER, " +
                " TYPE INTEGER, " +
                " PRIMARY KEY (USER_ID)) ");

        // DB에 쿼리 실행하기
        db.execSQL(buffer.toString());

        Log.v(TAG, "STATION_INFO Create 쿼리 생성완료!");
    }

    /* DB를 업그레이드할 때 호출.
     * 기존 테이블을 삭제하고 새로 만들거나 ALTER할 때
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

   /* public void insert(SQLiteDatabase db, int station_cd, String line_num, String station_nm, String fr_code) {

        // 이미 DB가 존재하면 함수 종료
        if (!selectAll(db).moveToFirst()) return;


        StringBuffer buffer = new StringBuffer();

        buffer.append(" INSERT INTO STATION_INFO ( ");
        buffer.append(" STATION_CD, LINE_NUM, STATION_NM, FR_CODE ) ");
        buffer.append(" VALUES ( " + station_cd + ", \"" + line_num + "\", \"" + station_nm + "\", \"" + fr_code + "\")");

        Log.v(TAG, buffer.toString());

        //db.execSQL(buffer.toString());
        Log.v(TAG, "Insert 쿼리 생성완료!");

    }*/

    // 검색 프래그먼트에서 왼쪽 리스트뷰를 위한 SQL 검색
    public Cursor selectLeftList(SQLiteDatabase db, boolean flag) {
        //* STATION_CD INTEGER, LINE_NUM TEXT, STATION_NM TEXT, FR_CODE TEXT
        StringBuffer sql = new StringBuffer();

        sql.append(" SELECT DISTINCT LINE_NUM ");
        sql.append(" FROM STATION_INFO ");
        sql.append(" ORDER BY FR_CODE ASC ");

        Cursor result = db.rawQuery(sql.toString(), null);
        return result;
    }

    // 검색 프래그먼트에서 사용하게 될 select()
    public Cursor selectRightList(SQLiteDatabase db, String condition, boolean flag) {
        StringBuffer sql = new StringBuffer();

        //sql.append(" SELECT DISTINCT STATION_CD, STATION_NM ");
        sql.append(" SELECT DISTINCT STATION_NM ");
        sql.append(" FROM STATION_INFO ");
        // flag가 true면 가나다순 false면 호선순
        if (flag) {
            switch (condition) {
                case "ㄱ":
                    sql.append(" WHERE STATION_NM >='가' AND STATION_NM < '나'");
                    break;
                case "ㄴ":
                    sql.append(" WHERE STATION_NM >='나' AND STATION_NM < '다'");
                    break;
                case "ㄷ":
                    sql.append(" WHERE STATION_NM >='다' AND STATION_NM < '라'");
                    break;
                case "ㄹ":
                    sql.append(" WHERE STATION_NM >='라' AND STATION_NM < '마'");
                    break;
                case "ㅁ":
                    sql.append(" WHERE STATION_NM >='마' AND STATION_NM < '바'");
                    break;
                case "ㅂ":
                    sql.append(" WHERE STATION_NM >='바' AND STATION_NM < '사'");
                    break;
                case "ㅅ":
                    sql.append(" WHERE STATION_NM >='사' AND STATION_NM < '아'");
                    break;
                case "ㅇ":
                    sql.append(" WHERE STATION_NM >='아' AND STATION_NM < '자'");
                    break;
                case "ㅈ":
                    sql.append(" WHERE STATION_NM >='자' AND STATION_NM < '차'");
                    break;
                case "ㅊ":
                    sql.append(" WHERE STATION_NM >='차' AND STATION_NM < '카'");
                    break;
                case "ㅋ":
                    sql.append(" WHERE STATION_NM >='카' AND STATION_NM < '타'");
                    break;
                case "ㅌ":
                    sql.append(" WHERE STATION_NM >='타' AND STATION_NM < '파'");
                    break;
                case "ㅍ":
                    sql.append(" WHERE STATION_NM >='파' AND STATION_NM < '하'");
                    break;
                case "ㅎ":
                    sql.append(" WHERE STATION_NM >='하'");
                    break;
            }
            sql.append(" ORDER BY STATION_NM ASC ");
        }
        else {
            sql.append(" WHERE LINE_NUM=\"" + condition + "\" ");
            sql.append(" ORDER BY FR_CODE ASC ");
        }

        Cursor result = db.rawQuery(sql.toString(), null);
        return result;
    }

    public Cursor selectAll(SQLiteDatabase db) {
        String sql = " SELECT * FROM STATION_INFO ";
        Cursor result = db.rawQuery(sql, null);

        return result;
    }

    protected void insertData(SQLiteDatabase db) {
        Log.v(TAG, "insertData() 실행");

        try {
            if (jsonArray == null) {
                Log.v(TAG, "jsonArray가 null");
                return;
            }

            // JSONArray 클래스는 JSON 파일에서 배열을 읽어들인다.
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject station = jsonArray.getJSONObject(i);

                StringBuffer buffer = new StringBuffer();
                buffer.append(" INSERT INTO STATION_INFO ( ");
                buffer.append(" STATION_CD, LINE_NUM, STATION_NM, FR_CODE ) ");
                buffer.append(" VALUES ( " + station.getInt("station_cd") + ", \"" + station.getString("line_num") + "\", \""
                        + station.getString("station_nm") + "\", \"" + station.getString("fr_code") + "\")");

                db.execSQL(buffer.toString());
                Log.v(TAG, i + "번째 데이터 insert 실행함");
            }
            Log.v(TAG, "JSON 배열 다 읽어옴");
        } catch (JSONException e) {
            Log.v(TAG, "insertData() 실패");
            e.printStackTrace();
        }
    }

    /*public String selectAll(SQLiteDatabase db) {
        String sql = " SELECT * FROM STATION_INFO ";
        Cursor result = db.rawQuery(sql, null);

        // 결과 커서를 맨 처음으로 가게 함 -> false일 때는 result(Cursor 객체)가 비어 있는 경우임
        if(result.moveToFirst()){
            StringBuffer buffer = new StringBuffer();

            while (!result.isAfterLast()) {
                int id = result.getInt(0);
                buffer.append(id + " : " +result.getString(1)).append("\n");
                result.moveToNext();
            }
            result.close();

            return buffer.toString();
        }

        else return "null";
    }*/
}
