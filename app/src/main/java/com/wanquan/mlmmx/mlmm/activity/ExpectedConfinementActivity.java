package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.ExpectedConfinementBeans;
import com.wanquan.mlmmx.mlmm.beans.ExpectedConfinementsBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.fragment.HomeFragment;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.feezu.liuli.timeselector.TimeSelector;
import org.greenrobot.eventbus.EventBus;
import org.joda.time.DateTime;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：预产期
 * 作者：薛昌峰
 * 时间：2017.09.10
 */
public class ExpectedConfinementActivity extends BaseActivity {
    private TextView mExpectedSave;
    private TextView mExpectedTime;
    private LinearLayout mExpectedMama;
    private ImageView mExpectedMamaImg;
    private LinearLayout mExpectedBaba;
    private ImageView mExpectedBabaImg;
    private String times;
    private String selectTime;
    private String format;
    private String result;
    private String s;
    private String sort = "";
    private String hyz;
    private String isTianJia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(ExpectedConfinementActivity.this, R.color.black);

        hyz = getIntent().getStringExtra("hyz");
        isTianJia = getIntent().getStringExtra("isTianJia");
        Log.e("ggggggg", isTianJia + "xcf");

        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_expected_confinement;
    }

    @Override
    public void initView() throws Exception {
        mExpectedSave = (TextView) findViewById(R.id.Expected_Save);
        mExpectedTime = (TextView) findViewById(R.id.Expected_time);
        mExpectedMama = (LinearLayout) findViewById(R.id.Expected_mama);
        mExpectedMamaImg = (ImageView) findViewById(R.id.Expected_mama_img);
        mExpectedBaba = (LinearLayout) findViewById(R.id.Expected_baba);
        mExpectedBabaImg = (ImageView) findViewById(R.id.Expected_baba_img);
    }

    private void initData() {
        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        times = formatter.format(curDate);
        String startTimes = times.substring(0, times.indexOf(" "));

        DateTime dateTime = DateTime.parse(startTimes);//设置的时间
        final DateTime nowstime = dateTime.plusDays(280);//向前推280
        final Date date = nowstime.toDate();
        s = formatter.format(date);
    }

    private void initListeners() {
        mExpectedMama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpectedMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                mExpectedBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                sort = "妈妈";
            }
        });
        mExpectedBaba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpectedMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                mExpectedBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                sort = "爸爸";
            }
        });
        mExpectedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeSelector timeSelector = new TimeSelector(ExpectedConfinementActivity.this, new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        selectTime = time;
                        result = time.substring(0, time.indexOf(" "));
                        mExpectedTime.setText(result);
                    }
                }, times, s);
                timeSelector.setIsLoop(true);//设置不循环,true循环
                timeSelector.setMode(TimeSelector.MODE.YMD);//显示 年月日时分（默认）
                timeSelector.show();
            }
        });
        mExpectedSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectTime != null && !sort.equals("")) {
                    if (SpUtil.getBooleanValue(ExpectedConfinementActivity.this, MyContant.ISLOGIN, true)) {
                        mExpectedSave.setClickable(false);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = null;
                        try {
                            date = sdf.parse(selectTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                        format = sdf1.format(date);
                        if (!mExpectedTime.getText().toString().equals("点击设置时间")) {
                            if (isTianJia.equals("1")) {//1为添加宝宝，其它为修改宝宝
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("itfaceId", "132");
                                hashMap.put("token", SPUtils.get(ExpectedConfinementActivity.this, "token", ""));
                                hashMap.put("pre_child_date", format);
                                hashMap.put("relationship", sort);
                                hashMap.put("status", "1");
                                JSONObject jsonObject = new JSONObject(hashMap);

                                OkGo.post(UrlContent.URL).tag(this)
                                        .upJson(jsonObject.toString())
                                        .connTimeOut(10_000)
                                        .execute(new CustomCallBackNoLoading<ExpectedConfinementsBeans>(ExpectedConfinementActivity.this) {
                                            @Override
                                            public void onSuccess(ExpectedConfinementsBeans mExpectedConfinementBeans, Call call, Response response) {
                                                mExpectedSave.setClickable(true);
                                                if (mExpectedConfinementBeans.getResultCode() == 1) {
                                                    if (mExpectedConfinementBeans.getData().getGetIntegral() != 0) {
                                                        Toast toast = Toast.makeText(ExpectedConfinementActivity.this, "添加宝宝成功积分+" + mExpectedConfinementBeans.getData().getGetIntegral(), Toast.LENGTH_SHORT);
                                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                                        toast.show();
                                                    }
                                                    SpUtil.setBooleanValue(ExpectedConfinementActivity.this, MyContant.SETTING, true);
                                                    SpUtil.setBooleanValue(ExpectedConfinementActivity.this, MyContant.TIAOZHUAN, true);
                                                    SPUtils.put(ExpectedConfinementActivity.this, "BabyState", "1");
//                                                    SPUtils.put(ExpectedConfinementActivity.this, "timeh", result);
                                                    SPUtils.put(ExpectedConfinementActivity.this, "timeh", format);
                                                    SPUtils.put(ExpectedConfinementActivity.this, "babyId", mExpectedConfinementBeans.getData());
                                                    startActivity(new Intent(ExpectedConfinementActivity.this, MainActivity.class));
                                                    Log.e("dddddddid_11", String.valueOf(SPUtils.get(ExpectedConfinementActivity.this, "babyId", "")));

                                                    //预产期修改后，设置播放器播放修改后的语音
                                                    SPUtils.put(ExpectedConfinementActivity.this, "isPlay", "1");
                                                    MessageEvent messageEvent = new MessageEvent();
                                                    messageEvent.setPlay(true);
                                                    EventBus.getDefault().post(messageEvent);
                                                    if (HomeFragment.mediaPlayer != null) {
                                                        HomeFragment.mediaPlayer.stop();
                                                        HomeFragment.isPlay = true;
                                                        HomeFragment.isPlayStart = false;
                                                    }
                                                    finish();
                                                } else {
                                                    App.ErrorToken(mExpectedConfinementBeans.getResultCode(), mExpectedConfinementBeans.getMsg());
                                                }
                                            }
                                        });
                            } else if (isTianJia.equals("0")) {//1为添加宝宝，其它为修改宝宝
                                Log.e("gggggggid", String.valueOf(SPUtils.get(ExpectedConfinementActivity.this, "babyId", "")));
                                Log.e("gggggggrelationship", sort);
                                Log.e("gggggggpre_child_date", format);
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("itfaceId", "074");
                                hashMap.put("token", SPUtils.get(ExpectedConfinementActivity.this, "token", ""));
                                hashMap.put("id", SPUtils.get(ExpectedConfinementActivity.this, "babyId", ""));
                                hashMap.put("relationship", sort);
                                hashMap.put("pre_child_date", format);
                                hashMap.put("status", "1");
                                JSONObject jsonObject = new JSONObject(hashMap);

                                OkGo.post(UrlContent.URL).tag(this)
                                        .upJson(jsonObject.toString())
                                        .connTimeOut(10_000)
                                        .execute(new CustomCallBackNoLoading<ExpectedConfinementBeans>(ExpectedConfinementActivity.this) {
                                            @Override
                                            public void onSuccess(ExpectedConfinementBeans mExpectedConfinementBeans, Call call, Response response) {
                                                mExpectedSave.setClickable(true);
                                                if (mExpectedConfinementBeans.getResultCode() == 1) {
                                                    Toast toast = Toast.makeText(ExpectedConfinementActivity.this, "保存成功", Toast.LENGTH_SHORT);
                                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                                    toast.show();
                                                    SpUtil.setBooleanValue(ExpectedConfinementActivity.this, MyContant.SETTING, true);
                                                    SpUtil.setBooleanValue(ExpectedConfinementActivity.this, MyContant.TIAOZHUAN, true);
                                                    SPUtils.put(ExpectedConfinementActivity.this, "BabyState", "1");
                                                    SPUtils.put(ExpectedConfinementActivity.this, "timeh", result);
                                                    startActivity(new Intent(ExpectedConfinementActivity.this, MainActivity.class));
                                                    //预产期修改后，设置播放器播放修改后的语音
                                                    SPUtils.put(ExpectedConfinementActivity.this, "isPlay", "1");
                                                    MessageEvent messageEvent = new MessageEvent();
                                                    messageEvent.setPlay(true);
                                                    EventBus.getDefault().post(messageEvent);
                                                    if (HomeFragment.mediaPlayer != null) {
                                                        HomeFragment.mediaPlayer.stop();
                                                        HomeFragment.isPlay = true;
                                                        HomeFragment.isPlayStart = false;
                                                    }
                                                    finish();
                                                } else {
                                                    App.ErrorToken(mExpectedConfinementBeans.getResultCode(), mExpectedConfinementBeans.getMsg());
                                                }
                                            }
                                        });
                            }
                        } else {
                            mExpectedSave.setClickable(true);
                            Toast toast = Toast.makeText(ExpectedConfinementActivity.this, "请选择时间", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    } else {
                        mExpectedSave.setClickable(true);
                        SPUtils.put(ExpectedConfinementActivity.this, "w_BabyState", "1");
                        SPUtils.put(ExpectedConfinementActivity.this, "w_timeh", result);
                        SPUtils.put(ExpectedConfinementActivity.this, "w_sort", sort);
                        SpUtil.setBooleanValue(ExpectedConfinementActivity.this, MyContant.W_SETTING, true);
                        SpUtil.setBooleanValue(ExpectedConfinementActivity.this, MyContant.W_TIAOZHUAN, true);
                        //切换过宝宝后通知工具栏修改为其它状态下的值
//                        SPUtils.put(ExpectedConfinementActivity.this, "GJSet", "");
                        //预产期修改后，设置播放器播放修改后的语音
                        SPUtils.put(ExpectedConfinementActivity.this, "isPlay", "1");
                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.setPlay(true);
                        EventBus.getDefault().post(messageEvent);
                        if (HomeFragment.mediaPlayer != null) {
                            HomeFragment.mediaPlayer.stop();
                            HomeFragment.isPlay = true;
                            HomeFragment.isPlayStart = false;
                        }
                        startActivity(new Intent(ExpectedConfinementActivity.this, MainActivity.class));
                        finish();
                    }
                } else if ("点击设置时间".equals(mExpectedTime.getText().toString().trim())) {
                    Toast toast = Toast.makeText(ExpectedConfinementActivity.this, "请选择时间", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mExpectedSave.setClickable(true);

                } else if (sort.equals("")) {
                    Toast toast = Toast.makeText(ExpectedConfinementActivity.this, "请选择您与宝宝的关系", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mExpectedSave.setClickable(true);

                }

            }
        });
    }


    public void ExpectedConfinementActivity_Bank(View view) {
        finish();
    }
}
