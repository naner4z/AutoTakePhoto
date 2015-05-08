package com.example.zyh.autotakephoto.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.zyh.autotakephoto.R;


public class StatisticsActivity extends MTAActivity implements View.OnClickListener{

    public static void start(Context context) {
        context.startActivity(new Intent(context, StatisticsActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_statistics);

        init();
    }

    private void init() {
        TextView titleTv, backTv;
        titleTv = (TextView)findViewById(R.id.title);
        backTv = (TextView)findViewById(R.id.backTv);

        findViewById(R.id.tv_quit).setVisibility(View.GONE);

        titleTv.setText(getString(R.string.actionbar_bottom_statistics));

        backTv.setVisibility(View.VISIBLE);
        backTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backTv:
                finish();
                break;
        }
    }
}
