package com.custom.customview.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.custom.customview.R;

/**
 * Created by Jone on 17/5/3.
 */

public abstract class BaseBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_back);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.activity_base_back_content_layout);
        LayoutInflater.from(this).inflate(getContentLayoutId(),frameLayout);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.activity_base_back_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(getTitleStringId());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    protected abstract int getTitleStringId();

    abstract int getContentLayoutId() ;

}
