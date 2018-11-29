package com.wanquan.mlmmx.mlmm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;

/**
 * 描述：举报
 * 作者：薛昌峰
 * 时间：2018.09.03
 */
public class JuBaoActivity extends BaseActivity {
    private TextView mCircleDetailsSave;
    private TextView mJuBaoTextView;
    private LinearLayout mJuBaoLl1;
    private LinearLayout mJuBaoLl2;
    private LinearLayout mJuBaoLl3;
    private LinearLayout mJuBaoLl4;
    private LinearLayout mJuBaoLl5;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(JuBaoActivity.this, R.color.black);

        name = getIntent().getStringExtra("name");


        initData();
        initListeners();

    }

    private void initData() {
        mJuBaoTextView.setText(name);
    }

    private void initListeners() {
        mJuBaoLl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(JuBaoActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
            }
        });
        mJuBaoLl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(JuBaoActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
            }
        });
        mJuBaoLl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(JuBaoActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
            }
        });
        mJuBaoLl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(JuBaoActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
            }
        });
        mJuBaoLl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(JuBaoActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_ju_bao;
    }

    @Override
    public void initView() throws Exception {


        mCircleDetailsSave = (TextView) findViewById(R.id.CircleDetails_Save);
        mJuBaoTextView = (TextView) findViewById(R.id.JuBao_TextView);
        mJuBaoLl1 = (LinearLayout) findViewById(R.id.JuBao_ll1);
        mJuBaoLl2 = (LinearLayout) findViewById(R.id.JuBao_ll2);
        mJuBaoLl3 = (LinearLayout) findViewById(R.id.JuBao_ll3);
        mJuBaoLl4 = (LinearLayout) findViewById(R.id.JuBao_ll4);
        mJuBaoLl5 = (LinearLayout) findViewById(R.id.JuBao_ll5);

    }

    public void JuBaoActivity_Bank(View view) {
        finish();
    }
}
