package com.custom.customview.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.custom.customview.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void nestClash(View view){
        startActivity(new Intent(this, NestClashActivity.class));
    }
    public void autoLinefeed(View view) {
        startActivity(new Intent(this, AutoLinefeedActivity.class));
    }

    public void moveScreen(View view){
        startActivity(new Intent(this, MoveScreenActivity.class));
    }

    public void moveConflict(View view){
        startActivity(new Intent(this,MoveConflictActivity.class));
    }

    public void rollPicture(View view){
        startActivity(new Intent(this,RollingPictureActivity.class));
    }
}
