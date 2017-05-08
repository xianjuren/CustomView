package com.custom.customview.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.custom.customview.R;
import com.custom.customview.bean.Group;
import com.custom.customview.bean.People;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jone on 17/5/6.
 */

public class HeadExpandableListAdapter extends BaseExpandableListAdapter {

    private ArrayList<Group> groupList;
    private ArrayList<List<People>> childList;

    private LayoutInflater inflater;

    public HeadExpandableListAdapter(Context context, ArrayList<Group> groupList, ArrayList<List<People>> childList) {
        inflater = LayoutInflater.from(context);
        this.groupList=groupList;
        this.childList=childList;
    }
    @Override
    public int getGroupCount() {
        if(null!=groupList&&groupList.size()>0){
            return groupList.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * whether or not the same ID always refers to the same object
     * @return
     * default value:false
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            groupHolder = new GroupHolder();
            convertView = inflater.inflate(R.layout.group, null);
            groupHolder.textView = (TextView) convertView.findViewById(R.id.group);
            groupHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }

        groupHolder.textView.setText(((Group) getGroup(groupPosition)).getTitle());
        if (isExpanded)// ture is Expanded or false is not isExpanded
            groupHolder.imageView.setImageResource(R.mipmap.expanded);
        else
            groupHolder.imageView.setImageResource(R.mipmap.collapse);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
      ChildHolder childHolder = null;
        if (convertView == null) {
            childHolder = new ChildHolder();
            convertView = inflater.inflate(R.layout.child, null);

            childHolder.textName = (TextView) convertView
                    .findViewById(R.id.name);
            childHolder.textAge = (TextView) convertView
                    .findViewById(R.id.age);
            childHolder.textAddress = (TextView) convertView
                    .findViewById(R.id.address);
            childHolder.imageView = (ImageView) convertView
                    .findViewById(R.id.image);
            Button button = (Button) convertView
                    .findViewById(R.id.button1);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        childHolder.textName.setText(((People) getChild(groupPosition,
                childPosition)).getName());
        childHolder.textAge.setText(String.valueOf(((People) getChild(
                groupPosition, childPosition)).getAge()));
        childHolder.textAddress.setText(((People) getChild(groupPosition,
                childPosition)).getAddress());

        return convertView;
    }


    class GroupHolder {
        TextView textView;
        ImageView imageView;
    }

    class ChildHolder {
        TextView textName;
        TextView textAge;
        TextView textAddress;
        ImageView imageView;
    }

    /**
     * whether or not the same ID always refers to the same object
     * @return
     * default value:false
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
