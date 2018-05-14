package com.example.hyo_jin.themostcleversubway.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hyo_jin.themostcleversubway.Item.FavoriteGridItem;
import com.example.hyo_jin.themostcleversubway.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.hyo_jin.themostcleversubway.R.id.parent;

/**
 * Created by Hyo-Jin on 2018-05-14.
 * 즐겨찾기 그리드 어댑터
 */

public class FavoriteGridAdapter extends BaseAdapter {

    private Context context;
    private List<FavoriteGridItem> favoriteGridItemList = new ArrayList<FavoriteGridItem>();

    public FavoriteGridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return favoriteGridItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return favoriteGridItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // 여기 코드 넣어야됨

        return null;
    }

    // FavoriteGridItem 정보 추가
    public void addItem(String station1, String station2) {
        FavoriteGridItem favoriteGridItem = new FavoriteGridItem();

        favoriteGridItem.setStation1(station1);
        favoriteGridItem.setStation2(station2);





        favoriteGridItemList.add(favoriteGridItem);
    }
}
