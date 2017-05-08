package com.custom.customview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.custom.customview.R;

/**
 * Created by Jone on 17/5/5.
 */
public class MoveConflictActivity extends BaseBackActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView tvDifferent = (TextView) findViewById(R.id.different_direction_conflict);
        TextView tvSame = (TextView) findViewById(R.id.same_direction_conflict);
        TextView tvSynthesize = (TextView) findViewById(R.id.synthesize_direction_conflict);
        tvDifferent.setOnClickListener(this);
        tvSame.setOnClickListener(this);
        tvSynthesize.setOnClickListener(this);
    }

    @Override
    protected int getTitleStringId() {
        return R.string.move_conflict;
    }

    @Override
    int getContentLayoutId() {
        return R.layout.activity_move_conflict;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.different_direction_conflict:
                startActivity(new Intent(this, DifferentDirectionConflictActivity.class));
                break;

            case R.id.same_direction_conflict:
                startActivity(new Intent(this, SameDirectionConflictActivity.class));
                break;
            case R.id.synthesize_direction_conflict:
                startActivity(new Intent(this, SynthesizeConflictActivity.class));
                break;

        }
    }
}
