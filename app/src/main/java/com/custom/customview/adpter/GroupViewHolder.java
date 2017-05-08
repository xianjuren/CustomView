package com.custom.customview.adpter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.custom.customview.R;

/**
 * Created by Jone on 17/5/6.
 */

public class GroupViewHolder extends ParentViewHolder {

    TextView textView;
    ImageView imageView;
    public GroupViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.group);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }
}
