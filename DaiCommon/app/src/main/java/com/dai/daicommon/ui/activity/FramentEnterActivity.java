package com.dai.daicommon.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dai.daicommon.R;

public class FramentEnterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        initView();
    }

    private void initView() {
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        findViewById(R.id.btn_flow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FramentEnterActivity.this, FramentOneActivity.class));
            }
        });

        findViewById(R.id.btn_wechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FramentEnterActivity.this, FramentTwoActivity.class));
            }
        });

        findViewById(R.id.btn_zhihu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(FramentEnterActivity.this, FramentThreeActivity.class));
            }
        });
    }
}
