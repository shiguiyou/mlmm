package com.wanquan.mlmmx.mlmm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;

/**
 * 描述：胎动说明
 * 作者：薛昌峰
 * 时间：2018.05.10
 */
public class QuickeningInstructionsActivity extends BaseActivity {
    private TextView mQuickeningHistoryActivityTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(QuickeningInstructionsActivity.this, R.color.black);

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_quickening_instructions;
    }

    @Override
    public void initView() throws Exception {

        mQuickeningHistoryActivityTv = (TextView) findViewById(R.id.QuickeningHistoryActivity_tv);

        mQuickeningHistoryActivityTv.setText("若胎动≤3次/小时，12小时胎动≤20次为异常。");
    }

    public void QuickeningInstructionsActivity_Bank(View view) {
        finish();
    }
}
