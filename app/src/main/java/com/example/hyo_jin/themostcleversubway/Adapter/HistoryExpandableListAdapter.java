package com.example.hyo_jin.themostcleversubway.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.example.hyo_jin.themostcleversubway.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;

    private List<String> groupList = new ArrayList<>();
    private Map<String, List<String>> childrenMap = new HashMap<>();

    public HistoryExpandableListAdapter(Context context, List<String> groupList, Map<String, List<String>> childrenMap) {
        this.context = context;
        this.groupList = groupList;
        this.childrenMap = childrenMap;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String group = groupList.get(groupPosition);
        return childrenMap.get(group).size();
    }

    @Override
    public Object getGroup(int position) {
        return groupList.get(position);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String group = groupList.get(groupPosition);
        return childrenMap.get(group).get(childPosition);
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

        if (view == null) {
            LayoutInflater layout = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layout.inflate(R.layout.listlayout_history, null);
        }

        TextView course = (TextView) view.findViewById(R.id.text_course_history);
        course.setText((String) getGroup(position));

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater layout = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layout.inflate(R.layout.listlayout_history_child, null);
        }

        //TextView course = (TextView) view.findViewById(R.id.text_child_course);
        //course.setText((String) getChild(groupPosition, childPosition));

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
