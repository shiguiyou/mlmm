package com.wanquan.mlmmx.mlmm.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.GrowSeedlingsDetailsBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.DateUtils;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.feezu.liuli.timeselector.TimeSelector;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：疫苗详情
 * 作者：薛昌峰
 * 时间：2017.10.18
 */

public class GrowSeedlingsDetailsActivity extends BaseActivity {
    private LinearLayout mGrowSeedlingsDetailll;
    private TextView mGrowSeedlingsDetailsTitle;
    private TextView mGrowSeedlingsDetailsTime;
    private ImageView mGrowSeedlingsDetailsImg;
    private TextView mGrowSeedlingsDetailsStatus;
    private TextView mGrowSeedlingsDetailsYes;
    private TextView mGrowSeedlingsDetailsTimes;
    private TextView mGrowDetailsOne;
    private TextView mGrowDetailsOneContent;
    private TextView mGrowDetailsTwo;
    private TextView mGrowDetailsTwoContent;
    private TextView mGrowDetailsThree;
    private TextView mGrowDetailsThreeContent;
    private String id;
    private String method;
    private String principle;
    private String reason;
    private String title;
    private String status;
    private String time1;
    private String time2;
    private String times;
    private String Batch;
    private String vaccineTotalBatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getIntent().getStringExtra("id");
        method = getIntent().getStringExtra("method");
        principle = getIntent().getStringExtra("principle");
        reason = getIntent().getStringExtra("reason");
        status = getIntent().getStringExtra("status");
        time1 = getIntent().getStringExtra("time1");
        time2 = getIntent().getStringExtra("time2");
        title = getIntent().getStringExtra("title");
        Batch = getIntent().getStringExtra("Batch");
        vaccineTotalBatch = getIntent().getStringExtra("vaccineTotalBatch");

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(GrowSeedlingsDetailsActivity.this, R.color.black);

        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_grow_seedlings_details;
    }

    @Override
    public void initView() throws Exception {
        mGrowSeedlingsDetailll = (LinearLayout) findViewById(R.id.GrowSeedlingsDetails_ll);
        mGrowSeedlingsDetailsTitle = (TextView) findViewById(R.id.GrowSeedlingsDetails_Title);
        mGrowSeedlingsDetailsTime = (TextView) findViewById(R.id.GrowSeedlingsDetails_Time);
        mGrowSeedlingsDetailsImg = (ImageView) findViewById(R.id.GrowSeedlingsDetails_Img);
        mGrowSeedlingsDetailsStatus = (TextView) findViewById(R.id.GrowSeedlingsDetails_status);
        mGrowSeedlingsDetailsYes = (TextView) findViewById(R.id.GrowSeedlingsDetails_yes);
        mGrowSeedlingsDetailsTimes = (TextView) findViewById(R.id.GrowSeedlingsDetails_Times);
        mGrowDetailsOne = (TextView) findViewById(R.id.GrowDetails_One);
        mGrowDetailsOneContent = (TextView) findViewById(R.id.GrowDetails_OneContent);
        mGrowDetailsTwo = (TextView) findViewById(R.id.GrowDetails_Two);
        mGrowDetailsTwoContent = (TextView) findViewById(R.id.GrowDetails_TwoContent);
        mGrowDetailsThree = (TextView) findViewById(R.id.GrowDetails_Three);
        mGrowDetailsThreeContent = (TextView) findViewById(R.id.GrowDetails_ThreeContent);
    }

    private void initData() {
        mGrowDetailsOneContent.setText(method);
        mGrowDetailsTwoContent.setText(principle);
        mGrowDetailsThreeContent.setText(reason);
        mGrowSeedlingsDetailsTitle.setText(title + "    第" + Batch + "/" + vaccineTotalBatch + "针");
        if (time1.equals("null")) {
            mGrowSeedlingsDetailsYes.setText("未设置");
        } else {
            mGrowSeedlingsDetailsYes.setText(time1);
        }
        if (time2.equals("null")) {
            mGrowSeedlingsDetailsTimes.setText("未设置");
        } else {
            mGrowSeedlingsDetailsTimes.setText(time2);
        }

        if (status.equals("0") || status.equals("2")) {
            mGrowSeedlingsDetailsImg.setImageDrawable(getResources().getDrawable(R.mipmap.radioselect));
            mGrowSeedlingsDetailsStatus.setText("未打");
        } else if (status.equals("1")) {
            mGrowSeedlingsDetailsImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
            mGrowSeedlingsDetailsStatus.setText("已打");
        }
    }

    private void initListeners() {
        mGrowSeedlingsDetailll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SPUtils.get(GrowSeedlingsDetailsActivity.this, "authority", "").equals("1")) {
                    if (status.equals("0")) {
                        mGrowSeedlingsDetailsImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                        mGrowSeedlingsDetailsStatus.setText("已打");
                        status = "1";
                        initNetWork("status", "1");
                    } else if (status.equals("1")) {
                        if (SPUtils.get(GrowSeedlingsDetailsActivity.this, "yida", "").equals("false")) {
                            mGrowSeedlingsDetailsImg.setImageDrawable(getResources().getDrawable(R.mipmap.radioselect));
                            mGrowSeedlingsDetailsStatus.setText("未打");
                            status = "2";
                            initNetWork("status", "2");
                            SPUtils.put(GrowSeedlingsDetailsActivity.this, "yida", "true");
                        } else {
                            mGrowSeedlingsDetailsImg.setImageDrawable(getResources().getDrawable(R.mipmap.radioselect));
                            mGrowSeedlingsDetailsStatus.setText("未打");
                            status = "0";
                            initNetWork("status", "0");
                        }
                    } else if (status.equals("2")) {
                        SPUtils.put(GrowSeedlingsDetailsActivity.this, "yida", "true");
                        if (SPUtils.get(GrowSeedlingsDetailsActivity.this, "yida", "").equals("true")) {
                            mGrowSeedlingsDetailsImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                            mGrowSeedlingsDetailsStatus.setText("已打");
                            status = "1";
                            initNetWork("status", "1");
                            SPUtils.put(GrowSeedlingsDetailsActivity.this, "yida", "false");
                        }
                    }
                } else {
                    Toast.makeText(GrowSeedlingsDetailsActivity.this, "亲！您还没有该权限哦！", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        times = formatter.format(curDate);
        if (SPUtils.get(GrowSeedlingsDetailsActivity.this, "authority", "").equals("1")) {
            mGrowSeedlingsDetailsYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TimeSelector timeSelector = new TimeSelector(GrowSeedlingsDetailsActivity.this, new TimeSelector.ResultHandler() {
                        @Override
                        public void handle(String time) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = null;
                            try {
                                date = sdf.parse(time);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                            String format1 = sdf1.format(date);
                            mGrowSeedlingsDetailsYes.setText(format1);
                            initNetWork("vaccineDate", format1);
                        }
                    }, times, "2099-12-31 23:59:59");
                    timeSelector.setIsLoop(true);//设置不循环,true循环
                    timeSelector.setMode(TimeSelector.MODE.YMD);//显示 年月日时分（默认）
                    timeSelector.show();
                }
            });
        } else {
            Toast.makeText(GrowSeedlingsDetailsActivity.this, "亲！您还没有该权限哦！", Toast.LENGTH_SHORT).show();
        }

        mGrowSeedlingsDetailsTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SPUtils.get(GrowSeedlingsDetailsActivity.this, "authority", "").equals("1")) {
                    TimeSelector timeSelector = new TimeSelector(GrowSeedlingsDetailsActivity.this, new TimeSelector.ResultHandler() {
                        @Override
                        public void handle(String time) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = null;
                            try {
                                date = sdf.parse(time);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                            String format2 = sdf1.format(date);

                            if (DateUtils.isDateOneBigger(mGrowSeedlingsDetailsYes.getText().toString(), format2)) {
                                mGrowSeedlingsDetailsTimes.setText(format2);
                                initNetWork("reminderDate", format2);
                            } else {
                                Toast toast = Toast.makeText(GrowSeedlingsDetailsActivity.this, "提醒时间不能超过接种日期", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }
                    }, times, "2099-12-31 23:59:59");
                    timeSelector.setIsLoop(true);//设置不循环,true循环
                    timeSelector.setMode(TimeSelector.MODE.YMD);//显示 年月日时分（默认）
                    timeSelector.show();
                } else {
                    Toast.makeText(GrowSeedlingsDetailsActivity.this, "亲！您还没有该权限哦！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initNetWork(String key, String value) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "030");
        hashMap.put("token", SPUtils.get(this, "token", ""));
        hashMap.put("id", id);
        hashMap.put(key, value);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<GrowSeedlingsDetailsBeans>(GrowSeedlingsDetailsActivity.this) {
                    @Override
                    public void onSuccess(GrowSeedlingsDetailsBeans mGrowSeedlingsDetailsBeans, Call call, Response response) {
                        if (mGrowSeedlingsDetailsBeans.getResultCode() == 1) {
                            Toast toast = Toast.makeText(GrowSeedlingsDetailsActivity.this, "修改成功", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {
                            App.ErrorToken(mGrowSeedlingsDetailsBeans.getResultCode(), mGrowSeedlingsDetailsBeans.getMsg());

                        }
                    }
                });
    }

    public void GrowSeedlingsDetailsActivity_Bank(View view) {
        finish();
    }
}
