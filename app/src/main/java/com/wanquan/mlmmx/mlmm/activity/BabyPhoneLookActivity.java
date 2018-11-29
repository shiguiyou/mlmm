package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.phone.Release_Work_Activity;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;

/**
 * 描述：宝宝相册权限设置
 * 作者：薛昌峰
 * 时间：2018.03.15
 */
public class BabyPhoneLookActivity extends BaseActivity {
    private TextView mBabyPhoneLookActivitySave;
    private LinearLayout mBabyPhoneLookActivityLL1;
    private ImageView mBabyPhoneLookActivityImg1;
    private LinearLayout mBabyPhoneLookActivityLL2;
    private ImageView mBabyPhoneLookActivityImg2;
    private int authority = 1;
    private String auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(BabyPhoneLookActivity.this, R.color.black);

        auto = getIntent().getStringExtra("auto");

        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_baby_phone_look;
    }

    @Override
    public void initView() throws Exception {
        mBabyPhoneLookActivitySave = (TextView) findViewById(R.id.BabyPhoneLookActivity_Save);
        mBabyPhoneLookActivityLL1 = (LinearLayout) findViewById(R.id.BabyPhoneLookActivity_LL1);
        mBabyPhoneLookActivityImg1 = (ImageView) findViewById(R.id.BabyPhoneLookActivity_Img1);
        mBabyPhoneLookActivityLL2 = (LinearLayout) findViewById(R.id.BabyPhoneLookActivity_LL2);
        mBabyPhoneLookActivityImg2 = (ImageView) findViewById(R.id.BabyPhoneLookActivity_Img2);
    }

    private void initData() {
        if (auto.equals("1")) {
            mBabyPhoneLookActivityImg1.setVisibility(View.VISIBLE);
            mBabyPhoneLookActivityImg2.setVisibility(View.INVISIBLE);
        } else if (auto.equals("0")) {
            mBabyPhoneLookActivityImg1.setVisibility(View.INVISIBLE);
            mBabyPhoneLookActivityImg2.setVisibility(View.VISIBLE);
        }
    }

    private void initListeners() {
        mBabyPhoneLookActivityLL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBabyPhoneLookActivityImg1.setVisibility(View.VISIBLE);
                mBabyPhoneLookActivityImg2.setVisibility(View.INVISIBLE);
                authority = 1;
                initNet();
            }
        });
        mBabyPhoneLookActivityLL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBabyPhoneLookActivityImg1.setVisibility(View.INVISIBLE);
                mBabyPhoneLookActivityImg2.setVisibility(View.VISIBLE);
                authority = 0;
                initNet();
            }
        });
    }

    private void initNet() {
        Intent intent = new Intent(BabyPhoneLookActivity.this, Release_Work_Activity.class);
        intent.putExtra("authority", String.valueOf(authority));
        setResult(2, intent);
        finish();
    }

    public void BabyPhoneLookActivity_Bank(View view) {
        finish();
    }
}
