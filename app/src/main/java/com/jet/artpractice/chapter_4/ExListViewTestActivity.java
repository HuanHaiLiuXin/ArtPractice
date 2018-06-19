package com.jet.artpractice.chapter_4;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.jet.artpractice.BaseActivity;
import com.jet.artpractice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:幻海流心
 * GitHub:https://github.com/HuanHaiLiuXin
 * 邮箱:wall0920@163.com
 * 2018/6/19 14:20
 */
public class ExListViewTestActivity extends BaseActivity {
    private ExpandableListView ex_listview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exlistview_test);
        ex_listview = findViewById(R.id.ex_listview);
        initView();
    }

    private void initView() {
        List<String> groups = new ArrayList<>();
        groups.add("西游记");
        groups.add("水浒站");
        groups.add("白蛇传");
        groups.add("三国志");
        groups.add("金瓶梅");
        List<List<String>> childLists = new ArrayList<>();
        for(int i = 0; i< groups.size(); i++){
            List<String> items = new ArrayList<String>();
            items.add(groups.get(i) + ":" + "选项" + 1);
            items.add(groups.get(i) + ":" + "选项" + 2);
            items.add(groups.get(i) + ":" + "选项" + 3);
            childLists.add(items);
        }
        MyAdapter adapter = new MyAdapter(groups,childLists,R.layout.item_exlistview_group,R.layout.item_exlistview_child);
        ex_listview.setAdapter(adapter);
    }

    class MyAdapter implements ExpandableListAdapter{
        private List<String> groups;
        private List<List<String>> childLists;
        private int layoutGroup;
        private int layoutChild;

        public MyAdapter(List groups,List childLists,int layoutGroup, int layoutChild) {
            this.groups = groups;
            this.childLists = childLists;
            this.layoutGroup = layoutGroup;
            this.layoutChild = layoutChild;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
        }
        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
        }

        @Override
        public int getGroupCount() {
            return groups == null || groups.size() <= 0 ? 0 : groups.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childLists == null || childLists.size() <= 0 ? 0 : childLists.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groups.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childLists.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupViewHolder viewHolder = null;
            if(convertView == null){
                convertView = LayoutInflater.from(ExListViewTestActivity.this).inflate(layoutGroup,parent,false);
                viewHolder = new GroupViewHolder();
                viewHolder.tv = convertView.findViewById(R.id.group);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (GroupViewHolder) convertView.getTag();
            }
            viewHolder.tv.setText(groups.get(groupPosition));
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildViewHolder viewHolder;
            if(convertView == null){
                convertView = LayoutInflater.from(ExListViewTestActivity.this).inflate(layoutChild,parent,false);
                viewHolder = new ChildViewHolder();
                viewHolder.tv = convertView.findViewById(R.id.child);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ChildViewHolder) convertView.getTag();
            }
            viewHolder.tv.setText(childLists.get(groupPosition).get(childPosition));
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public void onGroupExpanded(int groupPosition) {
        }
        @Override
        public void onGroupCollapsed(int groupPosition) {
        }
        @Override
        public long getCombinedChildId(long groupId, long childId) {
            return 0;
        }
        @Override
        public long getCombinedGroupId(long groupId) {
            return 0;
        }
    }
    class GroupViewHolder{
        public TextView tv;
    }
    class ChildViewHolder{
        public TextView tv;
    }
}