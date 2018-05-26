package com.example.hyo_jin.themostcleversubway.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Hyo-Jin on 2018-05-18.
 * SQLite를 통해 테이블을 만드는 등 여러 일
 *
 * insert() : DB 테이블에 데이터 insert
 * select() : DB 테이블에서 조건검색해서 Cursor 리턴
 * selectAll() : DB에서 모두 검색
 */

public class StationDBHelper extends SQLiteOpenHelper {

    final private String TAG = "StationDBHelper";
    private Context context;

    public StationDBHelper(Context context) {
        //super(context, "themostcleversubway.db", null, 1);
        super(context, "subway.db", null, 1);
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

        // 사용자 테이블 생성
        buffer = new StringBuffer();
        buffer.append("CREATE TABLE USER (" +
                " USER_ID INTEGER NOT NULL " +
                " STATION_DEP INTEGER " +
                " STATION_ARR INTEGER " +
                " TYPE INTEGER " +
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

    public void insert(SQLiteDatabase db, int station_cd, String line_num, String station_nm, String fr_code) {

        // 이미 DB가 존재하면 함수 종료
        if (!selectAll(db).moveToFirst()) return;


        StringBuffer buffer = new StringBuffer();

        buffer.append(" INSERT INTO STATION_INFO ( ");
        buffer.append(" STATION_CD, LINE_NUM, STATION_NM, FR_CODE ) ");
        buffer.append(" VALUES ( " + station_cd + ", \"" + line_num + "\", \"" + station_nm + "\", \"" + fr_code + "\")");

        Log.v(TAG, buffer.toString());

        //db.execSQL(buffer.toString());
        Log.v(TAG, "Insert 쿼리 생성완료!");

    }

    // 검색 프래그먼트에서 왼쪽 리스트뷰를 위한 SQL 검색
    public Cursor select(SQLiteDatabase db, boolean flag) {
        //* STATION_CD INTEGER, LINE_NUM TEXT, STATION_NM TEXT, FR_CODE TEXT
        StringBuffer sql = new StringBuffer();

        sql.append(" SELECT DISTINCT LINE_NUM ");
        sql.append(" FROM STATION_INFO ");
        sql.append(" ORDER BY LINE_NUM ASC ");

        Cursor result = db.rawQuery(sql.toString(), null);
        return result;
    }

    // 검색 프래그먼트에서 사용하게 될 select()
    public Cursor select(SQLiteDatabase db, String condition, boolean flag) {
        StringBuffer sql = new StringBuffer();

        sql.append(" SELECT DISTINCT STATION_CD, STATION_NM ");
        sql.append(" FROM STATION_INFO ");
        // flag가 true면 가나다순 false면 호선순
        if (flag) {
            switch (condition) {
                case "ㄱ":
                    sql.append(" WHERE STATION_NM >='가' AND STATION_NM < '나'");
                case "ㄴ":
                    sql.append(" WHERE STATION_NM >='나' AND STATION_NM < '다'");
                case "ㄷ":
                    sql.append(" WHERE STATION_NM >='다' AND STATION_NM < '라'");
                case "ㄹ":
                    sql.append(" WHERE STATION_NM >='라' AND STATION_NM < '마'");
                case "ㅁ":
                    sql.append(" WHERE STATION_NM >='마' AND STATION_NM < '바'");
                case "ㅂ":
                    sql.append(" WHERE STATION_NM >='바' AND STATION_NM < '사'");
                case "ㅅ":
                    sql.append(" WHERE STATION_NM >='사' AND STATION_NM < '아'");
                case "ㅇ":
                    sql.append(" WHERE STATION_NM >='아' AND STATION_NM < '자'");
                case "ㅈ":
                    sql.append(" WHERE STATION_NM >='자' AND STATION_NM < '차'");
                case "ㅊ":
                    sql.append(" WHERE STATION_NM >='차' AND STATION_NM < '카'");
                case "ㅋ":
                    sql.append(" WHERE STATION_NM >='카' AND STATION_NM < '타'");
                case "ㅌ":
                    sql.append(" WHERE STATION_NM >='타' AND STATION_NM < '파'");
                case "ㅍ":
                    sql.append(" WHERE STATION_NM >='파' AND STATION_NM < '하'");
                case "ㅎ":
                    sql.append(" WHERE STATION_NM >='하'");
            }
            sql.append(" ORDER BY STATION_NM ASC ");
        }
        else
            sql.append(" WHERE LINE_NUM=\"" + condition + "\" ");

        Cursor result = db.rawQuery(sql.toString(), null);
        return result;
    }

    public Cursor selectAll(SQLiteDatabase db) {
        String sql = " SELECT * FROM STATION_INFO ";
        Cursor result = db.rawQuery(sql, null);

        return result;
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
