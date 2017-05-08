package com.custom.customview.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.custom.customview.R;
import com.custom.customview.adpter.HeadExpandableRecyclerAdapter;
import com.custom.customview.adpter.HeadExpandableListAdapter;
import com.custom.customview.bean.Group;
import com.custom.customview.bean.People;
import com.custom.customview.customInterface.OnHeaderUpdateListener;
import com.custom.customview.customInterface.OnRecyclerFirstVisibleGroupListener;
import com.custom.customview.customView.ExpandableRecyclerView;
import com.custom.customview.customView.PinnedHeaderExpandableListView;
import com.custom.customview.customView.StickyLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jone on 17/5/6.
 */
public class SynthesizeConflictActivity extends BaseBackActivity implements
        ExpandableListView.OnChildClickListener,
        ExpandableListView.OnGroupClickListener,
        OnHeaderUpdateListener, StickyLayout.OnGiveUpTouchEventListener, OnRecyclerFirstVisibleGroupListener {
    private static final String TAG = "SynthesizeConflictActivity";
    ExpandableRecyclerView mRecyclerView;
    int mMOveTopValue = 0;
    int RECYCLERVIEW_TOP = 0;
    int LISTVIEW_TOP = 1;
    LinearLayoutManager mLayoutManager;
    HeadExpandableRecyclerAdapter mRecyclerAdapter;
    private PinnedHeaderExpandableListView expandableListView;
    private StickyLayout stickyLayout;
    private ArrayList<Group> groupList;
    private ArrayList<List<People>> childList;
    private HeadExpandableListAdapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stickyLayout = (StickyLayout) findViewById(R.id.sticky_layout);
        mRecyclerView = (ExpandableRecyclerView) findViewById(R.id.expandable_recycler_view);
        expandableListView = (PinnedHeaderExpandableListView) findViewById(R.id.expandablelist);
        initData();
        // initListView();
        initRecyclerView();
        stickyLayout.setOnGiveUpTouchEventListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initRecyclerView() {
        //有疑问参照：https://www.bignerdranch.com/blog/expand-a-recyclerview-in-four-steps/?utm_source=Android+Weekly&
        // utm_campaign=8f0cc3ff1f-Android_Weekly_165&utm_medium=email&utm_term=0_4eb677ad19-8f0cc3ff1f-337834121
        mMOveTopValue = RECYCLERVIEW_TOP;
        mRecyclerView.setVisibility(View.VISIBLE);
        expandableListView.setVisibility(View.GONE);
        mRecyclerAdapter = new HeadExpandableRecyclerAdapter(this, groupList);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.notifyDataSetChanged();
        mRecyclerView.setGroupListener(this);
        mRecyclerView.setHeaderUpdateListener(this);
    }

    private void initListView() {
        mMOveTopValue = LISTVIEW_TOP;
        mRecyclerView.setVisibility(View.GONE);
        expandableListView.setVisibility(View.VISIBLE);
        adapter = new HeadExpandableListAdapter(this, groupList, childList);
        expandableListView.setAdapter(adapter);
        // 展开所有group
        for (int i = 0, count = expandableListView.getCount(); i < count; i++) {
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnHeaderUpdateListener(this);
        expandableListView.setOnChildClickListener(this);
        expandableListView.setOnGroupClickListener(this);

    }

    void initData() {
        groupList = new ArrayList<Group>();
        Group group = null;
        for (int i = 0; i < 3; i++) {
            group = new Group();
            group.setTitle("group-" + i);
            groupList.add(group);
        }

        childList = new ArrayList<List<People>>();
        for (int i = 0; i < groupList.size(); i++) {
            ArrayList<People> childTemp;
            if (i == 0) {
                childTemp = new ArrayList<People>();
                for (int j = 0; j < 13; j++) {
                    People people = new People();
                    people.setName("yy-" + j);
                    people.setAge(30);
                    people.setAddress("sh-" + j);

                    childTemp.add(people);
                }

            } else if (i == 1) {
                childTemp = new ArrayList<People>();
                for (int j = 0; j < 8; j++) {
                    People people = new People();
                    people.setName("ff-" + j);
                    people.setAge(40);
                    people.setAddress("sh-" + j);

                    childTemp.add(people);
                }
            } else {
                childTemp = new ArrayList<People>();
                for (int j = 0; j < 23; j++) {
                    People people = new People();
                    people.setName("hh-" + j);
                    people.setAge(20);
                    people.setAddress("sh-" + j);

                    childTemp.add(people);
                }
            }
            childList.add(childTemp);
            groupList.get(i).setChildList(childTemp);
        }

    }


    @Override
    public boolean onGroupClick(final ExpandableListView parent, final View v, int groupPosition, final long id) {
        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return false;
    }


    @Override
    public View getPinnedHeader() {
        View headerView = getLayoutInflater().inflate(R.layout.group, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return headerView;
    }

    @Override
    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {

        Group firstVisibleGroup = null;
        if (mMOveTopValue == RECYCLERVIEW_TOP) {
            firstVisibleGroup = mRecyclerAdapter.getGroup(firstVisibleGroupPos);
        } else if (mMOveTopValue == LISTVIEW_TOP) {
            firstVisibleGroup = (Group) adapter.getGroup(firstVisibleGroupPos);
        }
        TextView textView = (TextView) headerView.findViewById(R.id.group);
        textView.setText(firstVisibleGroup.getTitle());
    }


    @Override
    public boolean giveUpTouchEvent(MotionEvent event) {
        if (mMOveTopValue == RECYCLERVIEW_TOP) {
            //recyclerView判断滑动到顶部
            if (null != mLayoutManager && mLayoutManager.findFirstVisibleItemPosition() == 0) {
                View view = mLayoutManager.getChildAt(0);
                if (null != view && view.getTop() >= 0) {
                    return true;
                }
            }
        } else if (mMOveTopValue == LISTVIEW_TOP)
            //listView判断滑动到顶部
            if (expandableListView.getFirstVisiblePosition() == 0) {
                View view = expandableListView.getChildAt(0);
                if (view != null && view.getTop() >= 0) {
                    return true;
                }
            }
        return false;
    }

    @Override
    protected int getTitleStringId() {
        return R.string.synthesize_direction_conflict;
    }

    @Override
    int getContentLayoutId() {
        return R.layout.activity_synthesize_conflict;
    }

    @Override
    public int getFirstVisibleGroupPosition() {
        if (null != mLayoutManager) {
            //返回的值不是需要的Group的位置数据。
            return mLayoutManager.findFirstVisibleItemPosition();
        }
        Log.i(TAG, "VisibleItemPosition===" + mLayoutManager.findFirstVisibleItemPosition());
        return 0;
    }
}
