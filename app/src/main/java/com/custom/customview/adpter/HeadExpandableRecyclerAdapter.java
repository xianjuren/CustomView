package com.custom.customview.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.custom.customview.R;
import com.custom.customview.bean.Group;
import com.custom.customview.bean.People;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jone on 17/5/6.
 */

public class HeadExpandableRecyclerAdapter extends ExpandableRecyclerAdapter<Group, People, GroupViewHolder, ChildHolder> {

    LayoutInflater mInflater;
    private ArrayList<Group> groupList;

    public HeadExpandableRecyclerAdapter(Context context, @NonNull List parentList) {
        super(parentList);
        mInflater = LayoutInflater.from(context);
        groupList = (ArrayList<Group>) parentList;
    }


    @NonNull
    @Override
    public GroupViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.group, parentViewGroup, false);
        return new GroupViewHolder(view);
    }

    @NonNull
    @Override
    public ChildHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.child, childViewGroup, false);
        return new ChildHolder(view);
    }

    @Override
    public void onBindParentViewHolder(@NonNull GroupViewHolder parentViewHolder, int parentPosition, @NonNull Group parent) {
        Group croup = parent;
        if (null != croup) {
            parentViewHolder.textView.setText(croup.getTitle());
            parentViewHolder.imageView.setImageResource(R.mipmap.collapse);
        }

    }

    @Override
    public void onBindChildViewHolder(@NonNull ChildHolder childViewHolder, int parentPosition, int childPosition, @NonNull People child) {
        People people = child;
        if (null != people) {
            childViewHolder.textName.setText(people.getName());
            childViewHolder.textAge.setText(String.valueOf(people.getAge()));
            childViewHolder.textAddress.setText(people.getAddress());
        }
    }


    @Override
    public int getParentViewType(int parentPosition) {
        return super.getParentViewType(parentPosition);
    }

    public Group getGroup(int position) {
        try {
            if (null != groupList && groupList.size() > 0) {
                return groupList.get(position);
            }
        } catch (Exception e) {
            return groupList.get(0);
        }
        return null;
    }
}
