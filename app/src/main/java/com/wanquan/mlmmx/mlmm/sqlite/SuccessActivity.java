package com.wanquan.mlmmx.mlmm.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;


public class SuccessActivity extends AppCompatActivity {
    TextView mTextView;
    String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        initData();
        initViews();
    }

    private void initData() {
        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
    }

    private void initViews() {
        mTextView = (TextView) findViewById(R.id.success);
        mTextView.setText(mName);
    }
}
