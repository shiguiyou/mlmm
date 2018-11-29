package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.BabyActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.BabyRespiratoryReportCardBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：宝宝呼吸成绩单
 * 作者：薛昌峰
 * 时间：2018.11.02
 */
public class BabyRespiratoryReportCardActivity extends BaseActivity {
    private TextView mBabyActivitySave;
    private TextView mBabyRespiratoryName;
    private TextView mBabyRespiratoryNum1;
    private TextView mBabyRespiratoryNum2;
    private TextView mBabyRespiratoryNum3;
    private TextView mBabyRespiratoryNum4;
    private TextView mBabyRespiratoryTv;
    private String id;
    private String ownerUserId;
    private String content;
    private String content2;
    private String name;
    private int babyStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getIntent().getStringExtra("id");
        ownerUserId = getIntent().getStringExtra("ownerUserId");
        name = getIntent().getStringExtra("name");
        babyStatus = getIntent().getIntExtra("babyStatus", -1);
        Log.e("fdfsdfs", id + "*********" + ownerUserId + "");
//        code = "28";
        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_baby_respiratory_report_card;
    }

    @Override
    public void initView() throws Exception {
        mBabyActivitySave = (TextView) findViewById(R.id.BabyActivity_Save);
        mBabyRespiratoryName = (TextView) findViewById(R.id.BabyRespiratory_name);
        mBabyRespiratoryNum1 = (TextView) findViewById(R.id.BabyRespiratory_num1);
        mBabyRespiratoryNum2 = (TextView) findViewById(R.id.BabyRespiratory_num2);
        mBabyRespiratoryNum3 = (TextView) findViewById(R.id.BabyRespiratory_num3);
        mBabyRespiratoryNum4 = (TextView) findViewById(R.id.BabyRespiratory_num4);
        mBabyRespiratoryTv = (TextView) findViewById(R.id.BabyRespiratory_tv);
    }

    private void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "140");
        hashMap.put("token", SPUtils.get(BabyRespiratoryReportCardActivity.this, "token", ""));
        hashMap.put("deviceId", id);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<BabyRespiratoryReportCardBeans>(BabyRespiratoryReportCardActivity.this) {
                    @Override
                    public void onSuccess(BabyRespiratoryReportCardBeans mBabyRespiratoryReportCardBeans, Call call, Response response) {
                        if (mBabyRespiratoryReportCardBeans.getResultCode() == 1) {
                            String pm25 = mBabyRespiratoryReportCardBeans.getData().getPm25();
                            String pm03 = mBabyRespiratoryReportCardBeans.getData().getPm03();
                            String ozone = mBabyRespiratoryReportCardBeans.getData().getOzone();
                            String runningHours = mBabyRespiratoryReportCardBeans.getData().getRunningHours();

                            if (ownerUserId == null) {
                                if (SPUtils.get(BabyRespiratoryReportCardActivity.this, "BabyState", "").equals("2")) {
                                    //宝宝名称
                                    name = String.valueOf(SPUtils.get(BabyRespiratoryReportCardActivity.this, "babyNickname", ""));
                                    mBabyRespiratoryName.setText(name + "过去一周平均呼吸质量");
                                    mBabyRespiratoryNum1.setText(pm25);
                                    content = name + "过去一周平均呼吸质量" + pm25 + "\n";
                                } else if (SPUtils.get(BabyRespiratoryReportCardActivity.this, "BabyState", "").equals("1")) {
                                    mBabyRespiratoryName.setText("宝妈过去一周平均呼吸质量");
                                    mBabyRespiratoryNum1.setText(pm25);
                                    content = "宝妈过去一周平均呼吸质量" + pm25;
                                } else {
                                    mBabyRespiratoryName.setText("过去一周平均呼吸质量");
                                    mBabyRespiratoryNum1.setText(pm25);
                                }
                            } else {
                                if (babyStatus == 2) {
                                    //宝宝名称
                                    mBabyRespiratoryName.setText(name + "过去一周平均呼吸质量");
                                    mBabyRespiratoryNum1.setText(pm25);
                                    content = name + "过去一周平均呼吸质量" + pm25 + "\n";
                                } else if (babyStatus == 1) {
                                    mBabyRespiratoryName.setText("宝妈过去一周平均呼吸质量");
                                    mBabyRespiratoryNum1.setText(pm25);
                                    content = "宝妈过去一周平均呼吸质量" + pm25;
                                } else {
                                    mBabyRespiratoryName.setText("过去一周平均呼吸质量");
                                    mBabyRespiratoryNum1.setText(pm25);
                                }
                            }

                            content2 = "PM0.3平均值" + pm03 + "/0.01L，" + "\n" + "臭氧浓度平均值" + ozone + "/m³，" + "\n" + "过去一周设备开启时间" + runningHours + "个小时";

                            mBabyRespiratoryNum2.setText("PM0.3平均值" + pm03 + "/0.01L");
                            mBabyRespiratoryNum3.setText("臭氧浓度平均值" + ozone + "/m³");
                            mBabyRespiratoryNum4.setText("过去一周设备开启时间" + runningHours + "个小时");
                        } else {
                            App.ErrorToken(mBabyRespiratoryReportCardBeans.getResultCode(), mBabyRespiratoryReportCardBeans.getMsg());
                        }
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (SpUtil.getBooleanValue(BabyRespiratoryReportCardActivity.this, MyContant.ISLOGIN, true)) {
                            Toast.makeText(BabyRespiratoryReportCardActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initListeners() {
        mBabyActivitySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BabyRespiratoryReportCardActivity.this, BabyRespiratoryReportCardPHBActivity.class).putExtra("id", id).putExtra("ownerUserId", ownerUserId));
            }
        });
        mBabyRespiratoryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BabyRespiratoryReportCardActivity.this, BabyQualityRespirationActivity.class)
                        .putExtra("id", id)
                        .putExtra("content", content + content2)
                        .putExtra("ownerUserId", ownerUserId)
                        .putExtra("name", name)
                        .putExtra("babyStatus", babyStatus));
            }
        });

    }

    public void BabyRespiratoryReportCardActivity_Bank(View view) {
        finish();
    }

}
