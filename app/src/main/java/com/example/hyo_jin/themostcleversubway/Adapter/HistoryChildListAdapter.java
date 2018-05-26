package com.example.hyo_jin.themostcleversubway.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.example.hyo_jin.themostcleversubway.Item.HistoryChildListItem;
import com.example.hyo_jin.themostcleversubway.Item.HistoryListItem;
import com.example.hyo_jin.themostcleversubway.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hyo-Jin on 2018-05-16.
 */

public class HistoryChildListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<HistoryListItem> groupList = new ArrayList<>();
    //private List<List> childList = new ArrayList<>();
    private Map<String, List<HistoryListItem>> childList = new HashMap<>();
    private LayoutInflater layout;
    private RecyclerView.ViewHolder viewHolder;

    public HistoryChildListAdapter(Context context, List<HistoryListItem> groupList, Map<String, List<HistoryListItem>> childList, LayoutInflater layout, RecyclerView.ViewHolder viewHolder) {

        this.layout = layout;
        this.groupList = groupList;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int position) {
        return childList.size();
    }

    @Override
    public Object getGroup(int position) {
        return groupList.get(position);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int position) {
        return position;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int position, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
/*
        if (view == null) {
            viewHolder = new RecyclerView.ViewHolder() {
                @Override
                public String toString() {
                    return super.toString();
                }
            };
            view = layout.inflate(R.layout.child_listview, null);
            viewHolder.
        }*/

        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
