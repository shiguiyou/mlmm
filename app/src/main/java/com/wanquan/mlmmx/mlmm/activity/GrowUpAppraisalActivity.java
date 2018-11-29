package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.GrowUpAppraisalActivityTwoBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：成长测评
 * 作者：薛昌峰
 * 时间：2018.09.11
 */
public class GrowUpAppraisalActivity extends BaseActivity {
    private LinearLayout mGrowUpAppraisalActivityLinearLayout;
    private TextView mGrowUpAppraisalName;
    private TextView mGrowUpAppraisalYear;
    private TextView mGrowUpAppraisalNumberOne;
    private TextView mGrowUpAppraisalContentOne;
    private ProgressBar mGrowUpAppraisalProgressBarOne;
    private TextView mGrowUpAppraisalNumberTwo;
    private TextView mGrowUpAppraisalContentTwo;
    private ProgressBar mGrowUpAppraisalProgressBarTwo;
    private TextView mGrowUpAppraisalNumberThree;
    private TextView mGrowUpAppraisalContentThree;
    private ProgressBar mGrowUpAppraisalProgressBarThree;
    private TextView mGrowUpAppraisalTextView;
    private TextView mGrowUpAppraisalTextView2;
    private LinearLayout mGrowUpAppraisalButtonLL;
    private TextView mGrowUpAppraisalButtonTv1;
    private TextView mGrowUpAppraisalButtonTv2;

    private String name;
    private String year;
    private String month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(GrowUpAppraisalActivity.this, R.color.black);
        name = getIntent().getStringExtra("name");
        year = getIntent().getStringExtra("year");
        month = getIntent().getStringExtra("month");

        initData();
        initListeners();
    }


    @Override
    protected int setLayoutID() {
        return R.layout.activity_grow_up_appraisal;
    }

    @Override
    public void initView() throws Exception {
        mGrowUpAppraisalActivityLinearLayout = (LinearLayout) findViewById(R.id.GrowUpAppraisalActivity_LinearLayout);
        mGrowUpAppraisalName = (TextView) findViewById(R.id.GrowUpAppraisal_Name);
        mGrowUpAppraisalYear = (TextView) findViewById(R.id.GrowUpAppraisal_Year);
        mGrowUpAppraisalNumberOne = (TextView) findViewById(R.id.GrowUpAppraisal_Number_One);
        mGrowUpAppraisalContentOne = (TextView) findViewById(R.id.GrowUpAppraisal_Content_One);
        mGrowUpAppraisalProgressBarOne = (ProgressBar) findViewById(R.id.GrowUpAppraisal_ProgressBar_One);
        mGrowUpAppraisalNumberTwo = (TextView) findViewById(R.id.GrowUpAppraisal_Number_Two);
        mGrowUpAppraisalContentTwo = (TextView) findViewById(R.id.GrowUpAppraisal_Content_Two);
        mGrowUpAppraisalProgressBarTwo = (ProgressBar) findViewById(R.id.GrowUpAppraisal_ProgressBar_Two);
        mGrowUpAppraisalNumberThree = (TextView) findViewById(R.id.GrowUpAppraisal_Number_Three);
        mGrowUpAppraisalContentThree = (TextView) findViewById(R.id.GrowUpAppraisal_Content_Three);
        mGrowUpAppraisalProgressBarThree = (ProgressBar) findViewById(R.id.GrowUpAppraisal_ProgressBar_Three);
        mGrowUpAppraisalTextView = (TextView) findViewById(R.id.GrowUpAppraisal_TextView);
        mGrowUpAppraisalTextView2 = (TextView) findViewById(R.id.GrowUpAppraisal_TextView2);
        mGrowUpAppraisalButtonLL = (LinearLayout) findViewById(R.id.GrowUpAppraisal_Button_LL);
        mGrowUpAppraisalButtonTv1 = (TextView) findViewById(R.id.GrowUpAppraisal_Button_tv1);
        mGrowUpAppraisalButtonTv2 = (TextView) findViewById(R.id.GrowUpAppraisal_Button_tv2);
    }

    public void GrowUpAppraisalActivity_Bank(View view) {
        finish();
    }

    private void initData() {
        mGrowUpAppraisalName.setText(name + "现在");
        mGrowUpAppraisalYear.setText(year);
        mGrowUpAppraisalTextView.setText(name + "有什么新变化呢？");

//        Log.e("sdss", month);

        if (Integer.parseInt(month) >= 36) {
            mGrowUpAppraisalButtonTv1.setText("宝宝已经长大了，不需要做发育测评了");
        } else {
            mGrowUpAppraisalButtonTv1.setText("暂时没有合适的评测,等宝宝长大一些再来看看吧");
        }

        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("itfaceId", "139");
        hashMap2.put("token", SPUtils.get(GrowUpAppraisalActivity.this, "token", ""));
        hashMap2.put("age", month);
        JSONObject jsonObject2 = new JSONObject(hashMap2);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject2.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<GrowUpAppraisalActivityTwoBeans>(GrowUpAppraisalActivity.this) {
                    @Override
                    public void onSuccess(GrowUpAppraisalActivityTwoBeans mGrowUpAppraisalActivityTwoBeans, Call call, Response response) {
                        if (mGrowUpAppraisalActivityTwoBeans.getResultCode() == 1) {
                            if (!mGrowUpAppraisalActivityTwoBeans.getData().isEmpty()) {
                                mGrowUpAppraisalActivityLinearLayout.setVisibility(View.VISIBLE);
                                mGrowUpAppraisalButtonLL.setVisibility(View.GONE);
                                mGrowUpAppraisalNumberOne.setText(mGrowUpAppraisalActivityTwoBeans.getData().get(0).getScale() + "%");
                                mGrowUpAppraisalNumberTwo.setText(mGrowUpAppraisalActivityTwoBeans.getData().get(1).getScale() + "%");
                                mGrowUpAppraisalNumberThree.setText(mGrowUpAppraisalActivityTwoBeans.getData().get(2).getScale() + "%");

                                mGrowUpAppraisalContentOne.setText(mGrowUpAppraisalActivityTwoBeans.getData().get(0).getAbility());
                                mGrowUpAppraisalContentTwo.setText(mGrowUpAppraisalActivityTwoBeans.getData().get(1).getAbility());
                                mGrowUpAppraisalContentThree.setText(mGrowUpAppraisalActivityTwoBeans.getData().get(2).getAbility());

                                mGrowUpAppraisalProgressBarOne.setProgress((int) mGrowUpAppraisalActivityTwoBeans.getData().get(0).getScale());
                                mGrowUpAppraisalProgressBarTwo.setProgress((int) mGrowUpAppraisalActivityTwoBeans.getData().get(1).getScale());
                                mGrowUpAppraisalProgressBarThree.setProgress((int) mGrowUpAppraisalActivityTwoBeans.getData().get(2).getScale());
                            } else {
                                mGrowUpAppraisalActivityLinearLayout.setVisibility(View.GONE);
                                mGrowUpAppraisalButtonLL.setVisibility(View.VISIBLE);
                            }
                        } else {
                            App.ErrorToken(mGrowUpAppraisalActivityTwoBeans.getResultCode(), mGrowUpAppraisalActivityTwoBeans.getMsg());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(GrowUpAppraisalActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void initListeners() {
        mGrowUpAppraisalTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GrowUpAppraisalActivity.this, GrowUpAppraisalStartActivity.class).putExtra("month", month).putExtra("name", name));
            }
        });
    }
}
