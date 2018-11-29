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
import com.wanquan.mlmmx.mlmm.beans.AntenatalTimeTableDetailsBeans;
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
 * 描述：产检时刻表详情
 * 作者：薛昌峰
 * 时间：2017.09.27
 */
public class AntenatalTimeTableDetailsActivity extends BaseActivity {
    private TextView mAntenatalTimeTableDetailsTitle;
    private TextView mAntenatalTimeTableDetailsTime;
    private LinearLayout mAntenatalTimeTableDetailsLl;
    private ImageView mAntenatalTimeTableDetailsImg;
    private TextView mAntenatalTimeTableDetailsStatus;
    private TextView mAntenatalTimeTableDetailsYes;
    private TextView mAntenatalTimeTableDetailsTimes;
    private TextView mAntenatalTimeTableDetailsOne;
    private TextView mAntenatalTimeTableDetailsOneContent;
    private TextView mAntenatalTimeTableDetailsTwo;
    private TextView mAntenatalTimeTableDetailsTwoContent;
    private String id;
    private String status;
    private String seq;
    private String content;
    private String help;
    private String week;
    private String desc;
    private String times;
    private String ChanjianDate;
    private String ReminderDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(AntenatalTimeTableDetailsActivity.this, R.color.black);

        id = getIntent().getStringExtra("id");
        status = getIntent().getStringExtra("status");
        seq = getIntent().getStringExtra("seq");
        content = getIntent().getStringExtra("content");
        help = getIntent().getStringExtra("help");
        week = getIntent().getStringExtra("week");
        desc = getIntent().getStringExtra("desc");
        ChanjianDate = getIntent().getStringExtra("ChanjianDate");
        ReminderDate = getIntent().getStringExtra("ReminderDate");

        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_antenatal_time_table_details;
    }

    @Override
    public void initView() throws Exception {
        mAntenatalTimeTableDetailsTitle = (TextView) findViewById(R.id.AntenatalTimeTableDetails_Title);
        mAntenatalTimeTableDetailsTime = (TextView) findViewById(R.id.AntenatalTimeTableDetails_Time);
        mAntenatalTimeTableDetailsLl = (LinearLayout) findViewById(R.id.AntenatalTimeTableDetails_ll);
        mAntenatalTimeTableDetailsImg = (ImageView) findViewById(R.id.AntenatalTimeTableDetails_Img);
        mAntenatalTimeTableDetailsStatus = (TextView) findViewById(R.id.AntenatalTimeTableDetails_status);
        mAntenatalTimeTableDetailsYes = (TextView) findViewById(R.id.AntenatalTimeTableDetails_yes);
        mAntenatalTimeTableDetailsTimes = (TextView) findViewById(R.id.AntenatalTimeTableDetails_Times);
        mAntenatalTimeTableDetailsOne = (TextView) findViewById(R.id.AntenatalTimeTableDetails_One);
        mAntenatalTimeTableDetailsOneContent = (TextView) findViewById(R.id.AntenatalTimeTableDetails_OneContent);
        mAntenatalTimeTableDetailsTwo = (TextView) findViewById(R.id.AntenatalTimeTableDetails_Two);
        mAntenatalTimeTableDetailsTwoContent = (TextView) findViewById(R.id.AntenatalTimeTableDetails_TwoContent);
    }

    private void initData() {
        if (ChanjianDate.equals("null")) {
            mAntenatalTimeTableDetailsYes.setText("未设置");
        } else {
            mAntenatalTimeTableDetailsYes.setText(ChanjianDate);
        }
        if (ReminderDate.equals("null")) {
            mAntenatalTimeTableDetailsTimes.setText("未设置");
        } else {
            mAntenatalTimeTableDetailsTimes.setText(ReminderDate);
        }

        mAntenatalTimeTableDetailsOneContent.setText(content);
        mAntenatalTimeTableDetailsTwoContent.setText(help);
        mAntenatalTimeTableDetailsTitle.setText(desc + "      第 " + week + " 周");

        if (status.equals("0") || status.equals("2")) {
            mAntenatalTimeTableDetailsStatus.setText("未检");
            mAntenatalTimeTableDetailsImg.setVisibility(View.VISIBLE);
            mAntenatalTimeTableDetailsImg.setImageDrawable(getResources().getDrawable(R.mipmap.radioselect));
        } else if (status.equals("1")) {
            mAntenatalTimeTableDetailsImg.setVisibility(View.VISIBLE);
            mAntenatalTimeTableDetailsStatus.setText("已检");
            mAntenatalTimeTableDetailsImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
        }
    }

    private void initNetWork(final String key, final String value) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "034");
        hashMap.put("id", id);
        hashMap.put("token", SPUtils.get(this, "token", ""));
        hashMap.put(key, value);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<AntenatalTimeTableDetailsBeans>(AntenatalTimeTableDetailsActivity.this) {
                    @Override
                    public void onSuccess(AntenatalTimeTableDetailsBeans mAntenatalTimeTableAdapterBeans, Call call, Response response) {
                        if (mAntenatalTimeTableAdapterBeans.getResultCode() == 1) {
                            Toast.makeText(AntenatalTimeTableDetailsActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        } else {
                            App.ErrorToken(mAntenatalTimeTableAdapterBeans.getResultCode(),mAntenatalTimeTableAdapterBeans.getMsg());
                        }
                    }
                });

    }

    private void initListeners() {
        mAntenatalTimeTableDetailsLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SPUtils.get(AntenatalTimeTableDetailsActivity.this, "authority", "").equals("0")) {
                    Toast.makeText(AntenatalTimeTableDetailsActivity.this, "亲，您还没有权限哦！", Toast.LENGTH_SHORT).show();
                } else {
                    if (status.equals("0")) {
                        mAntenatalTimeTableDetailsImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                        mAntenatalTimeTableDetailsStatus.setText("已产检");
                        status = "1";
                        initNetWork("status", "1");
                    } else if (status.equals("1")) {
                        if (SPUtils.get(AntenatalTimeTableDetailsActivity.this, "yijian", "").equals("false")) {
                            mAntenatalTimeTableDetailsImg.setImageDrawable(getResources().getDrawable(R.mipmap.radioselect));
                            mAntenatalTimeTableDetailsStatus.setText("未产检");
                            status = "2";
                            initNetWork("status", "2");
                            SPUtils.put(AntenatalTimeTableDetailsActivity.this, "yijian", "true");
                        } else {
                            mAntenatalTimeTableDetailsImg.setImageDrawable(getResources().getDrawable(R.mipmap.radioselect));
                            mAntenatalTimeTableDetailsStatus.setText("未产检");
                            status = "0";
                            initNetWork("status", "0");
                        }
                    } else if (status.equals("2")) {
                        SPUtils.put(AntenatalTimeTableDetailsActivity.this, "yijian", "true");
                        if (SPUtils.get(AntenatalTimeTableDetailsActivity.this, "yijian", "").equals("true")) {
                            mAntenatalTimeTableDetailsImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                            mAntenatalTimeTableDetailsStatus.setText("已产检");
                            status = "1";
                            initNetWork("status", "1");
                            SPUtils.put(AntenatalTimeTableDetailsActivity.this, "yijian", "false");
                        }
                    }
                }
            }
        });

        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        times = formatter.format(curDate);

        mAntenatalTimeTableDetailsYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SPUtils.get(AntenatalTimeTableDetailsActivity.this, "authority", "").equals("0")) {
                    Toast.makeText(AntenatalTimeTableDetailsActivity.this, "亲，您还没有权限哦！", Toast.LENGTH_SHORT).show();
                } else {
                    TimeSelector timeSelector = new TimeSelector(AntenatalTimeTableDetailsActivity.this, new TimeSelector.ResultHandler() {
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
                            String format = sdf1.format(date);
                            mAntenatalTimeTableDetailsYes.setText(format);
                            initNetWork("chanjianDate", format);
                        }
                    }, times, "2099-12-31 23:59:59");
                    timeSelector.setIsLoop(true);//设置不循环,true循环
                    timeSelector.setMode(TimeSelector.MODE.YMD);//显示 年月日时分（默认）
                    timeSelector.show();
                }
            }
        });
        mAntenatalTimeTableDetailsTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SPUtils.get(AntenatalTimeTableDetailsActivity.this, "authority", "").equals("0")) {
                    Toast.makeText(AntenatalTimeTableDetailsActivity.this, "亲，您还没有权限哦！", Toast.LENGTH_SHORT).show();
                } else {
                    TimeSelector timeSelector = new TimeSelector(AntenatalTimeTableDetailsActivity.this, new TimeSelector.ResultHandler() {
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
                            String format = sdf1.format(date);

                            if (DateUtils.isDateOneBigger(mAntenatalTimeTableDetailsYes.getText().toString(), format)) {
                                mAntenatalTimeTableDetailsTimes.setText(format);
                                initNetWork("reminderDate", format);
                            } else {
                                Toast toast = Toast.makeText(AntenatalTimeTableDetailsActivity.this, "提醒时间不能超过产检日期", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }
                    }, times, "2099-12-31 23:59:59");
                    timeSelector.setIsLoop(true);//设置不循环,true循环
                    timeSelector.setMode(TimeSelector.MODE.YMD);//显示 年月日时分（默认）
                    timeSelector.show();
                }
            }
        });
    }

    public void AntenatalTimeTableDetails_Bank(View view) {
        finish();
    }
}
