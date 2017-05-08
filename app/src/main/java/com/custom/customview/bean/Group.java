package com.custom.customview.bean;


import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

public class Group implements Parent<People> {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChildList(List<People> childList) {
        mChildList = childList;
    }

    List<People> mChildList;
    @Override
    public List<People> getChildList() {
        return mChildList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
