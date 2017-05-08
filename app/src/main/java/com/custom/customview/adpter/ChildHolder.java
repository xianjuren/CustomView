package com.custom.customview.adpter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.custom.customview.R;

/**
 * Created by Jone on 17/5/6.
 */

public class ChildHolder extends ChildViewHolder {

    TextView textName;
    TextView textAge;
    TextView textAddress;
    ImageView imageView;

    public ChildHolder(@NonNull View itemView) {
        super(itemView);
       textName = (TextView) itemView
                .findViewById(R.id.name);
       textAge = (TextView) itemView
                .findViewById(R.id.age);
       textAddress = (TextView) itemView
                .findViewById(R.id.address);
       imageView = (ImageView) itemView
                .findViewById(R.id.image);
        Button button = (Button) itemView
                .findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
