package com.wanquan.mlmmx.mlmm.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;

import org.feezu.liuli.timeselector.TimeSelector;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述：产前提醒
 * 作者：薛昌峰
 * 时间：2017.09.10
 */
public class AntenatalAlertActivity extends BaseActivity {
    private TextView mAntenatalAlertSave;
    private RelativeLayout mAntenatalAlertRL;
    private TextView mAntenatalAlertTime;
    private String times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(AntenatalAlertActivity.this, R.color.black);

        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        times = formatter.format(curDate);

        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_antenatal_alert;
    }

    @Override
    public void initView() throws Exception {
        mAntenatalAlertSave = (TextView) findViewById(R.id.AntenatalAlert_save);
        mAntenatalAlertRL = (RelativeLayout) findViewById(R.id.AntenatalAlert_RL);
        mAntenatalAlertTime = (TextView) findViewById(R.id.AntenatalAlert_time);
    }

    private void initData() {
    }

    private void initListeners() {
        mAntenatalAlertRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeSelector timeSelector = new TimeSelector(AntenatalAlertActivity.this, new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        mAntenatalAlertTime.setText(time);
                    }
                }, times, "2099-12-31 23:59:59");
                timeSelector.setIsLoop(true);//设置不循环,true循环
                timeSelector.setMode(TimeSelector.MODE.YMDHM);//显示 年月日时分（默认）
                timeSelector.show();
            }
        });
    }

    public void AntenatalAlertActivity_Bank(View view) {
        finish();
    }
}
