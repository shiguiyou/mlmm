package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.BabyActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.ExpectedConfinementBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.SettingBabyMessageBeans;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：孕育信息
 * 作者：薛昌峰
 * 时间：2017.09.27
 */
public class GestateMessageActivity extends BaseActivity {
    private TextView mGestateMessageSave;
    private RelativeLayout mGestateMessageRL1;
    private TextView mGestateMessageTime;
    private RelativeLayout mGestateMessageRL2;
    private LinearLayout mGestateMessageMama;
    private ImageView mGestateMessageMamaImg;
    private LinearLayout mGestateMessageBaba;
    private ImageView mGestateMessageBabaImg;
    private String times;
    private String time1;
    private String babyId;
    private String authority;
    private String sort = "";
    private String format;
    private List<BabyActivityBeans.DataBean.BabyMessageBean> mList1 = new ArrayList<>();
    private String byz;
    private String hyz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(GestateMessageActivity.this, R.color.black);

        initNetWork();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_gestate_message;
    }

    @Override
    public void initView() throws Exception {
        mGestateMessageSave = (TextView) findViewById(R.id.GestateMessage_Save);
        mGestateMessageMama = (LinearLayout) findViewById(R.id.GestateMessage_mama);
        mGestateMessageMamaImg = (ImageView) findViewById(R.id.GestateMessage_mama_img);
        mGestateMessageBaba = (LinearLayout) findViewById(R.id.GestateMessage_baba);
        mGestateMessageBabaImg = (ImageView) findViewById(R.id.GestateMessage_baba_img);
        mGestateMessageRL1 = (RelativeLayout) findViewById(R.id.GestateMessage_RL1);
        mGestateMessageTime = (TextView) findViewById(R.id.GestateMessage_time);
        mGestateMessageRL2 = (RelativeLayout) findViewById(R.id.GestateMessage_RL2);
    }

    private void initNetWork() {
        if (SPUtils.get(GestateMessageActivity.this, "orderId", "").equals("1")) {
            mGestateMessageSave.setVisibility(View.VISIBLE);
        } else if (SPUtils.get(GestateMessageActivity.this, "orderId", "").equals("2")) {
            mGestateMessageSave.setVisibility(View.GONE);
        }
//        Log.e("XCFXCFuserid", String.valueOf(SPUtils.get(GestateMessageActivity.this, "userid", "")));
//        Log.e("XCFXCFbabyId", String.valueOf(SPUtils.get(GestateMessageActivity.this, "babyId", "")));
        if (SpUtil.getBooleanValue(GestateMessageActivity.this, MyContant.ISLOGIN, true)) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("itfaceId", "033");
            hashMap.put("token", SPUtils.get(GestateMessageActivity.this, "token", ""));
            hashMap.put("id", SPUtils.get(GestateMessageActivity.this, "babyId", ""));
            JSONObject jsonObject = new JSONObject(hashMap);

            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<SettingBabyMessageBeans>(GestateMessageActivity.this) {
                        @Override
                        public void onSuccess(SettingBabyMessageBeans mSettingBabyMessageBeans, Call call, Response response) {
                            if (mSettingBabyMessageBeans.getResultCode() == 1) {
                                mGestateMessageTime.setText(mSettingBabyMessageBeans.getData().getPreChildDate());
                                format = mSettingBabyMessageBeans.getData().getPreChildDate();
                                babyId = String.valueOf(mSettingBabyMessageBeans.getData().getId());
                                SPUtils.put(GestateMessageActivity.this, "babyId", babyId);
                                authority = mSettingBabyMessageBeans.getData().getAuthority();
                                if (mSettingBabyMessageBeans.getData().getRelationship().equals("妈妈")) {
                                    mGestateMessageMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                                    mGestateMessageBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                                    sort = "妈妈";
                                } else if (mSettingBabyMessageBeans.getData().getRelationship().equals("爸爸")) {
                                    mGestateMessageMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                                    mGestateMessageBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                                    sort = "爸爸";
                                }
//                                Log.e("XCFXCFauthority", authority);
                            } else {
                                App.ErrorToken(mSettingBabyMessageBeans.getResultCode(), mSettingBabyMessageBeans.getMsg());
                            }
                        }
                    });
        } else {
            mGestateMessageTime.setText(String.valueOf(SPUtils.get(GestateMessageActivity.this, "w_timeh", "")));
            if ("妈妈".equals(SPUtils.get(GestateMessageActivity.this, "w_sort", ""))) {
                mGestateMessageMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                mGestateMessageBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                sort = "妈妈";
            } else if ("爸爸".equals(SPUtils.get(GestateMessageActivity.this, "w_sort", ""))) {
                mGestateMessageMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                mGestateMessageBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                sort = "爸爸";
            }
            format = String.valueOf(SPUtils.get(GestateMessageActivity.this, "w_timeh", ""));
        }
    }

    private void initData() {
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("itfaceId", "075");
        hashMap2.put("token", SPUtils.get(GestateMessageActivity.this, "token", ""));
        JSONObject jsonObject2 = new JSONObject(hashMap2);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject2.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<BabyActivityBeans>(GestateMessageActivity.this) {
                    @Override
                    public void onSuccess(BabyActivityBeans mBabyActivityBeans, Call call, Response response) {
                        if (mBabyActivityBeans.getResultCode() == 1) {
                            mList1.clear();
                            mList1.addAll(mBabyActivityBeans.getData().getBabyMessage());
                            List<Integer> integers = new ArrayList<Integer>();
                            for (int i = 0; i < mList1.size(); i++) {
                                integers.add(mList1.get(i).getSTATUS());
                            }
                            for (int i = 0; i < integers.size(); i++) {
                                int status = Integer.parseInt(integers.get(i).toString());
                                if (status == 0) {
                                    byz = "true";
                                } else if (status == 1) {
                                    hyz = "true";
                                }
                            }
                        } else {
                            App.ErrorToken(mBabyActivityBeans.getResultCode(), mBabyActivityBeans.getMsg());

                        }
                    }
                });
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "074");
        hashMap.put("id", babyId);
        hashMap.put("relationship", sort);
        hashMap.put("token", SPUtils.get(this, "token", ""));
        hashMap.put("pre_child_date", format);
//        hashMap.put("status", "1");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<ExpectedConfinementBeans>(GestateMessageActivity.this) {
                    @Override
                    public void onSuccess(ExpectedConfinementBeans mExpectedConfinementBeans, Call call, Response response) {
                        if (mExpectedConfinementBeans.getResultCode() == 1) {
                            Toast toast = Toast.makeText(GestateMessageActivity.this, "保存成功", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            SPUtils.put(GestateMessageActivity.this, "timeh", format);
                            SpUtil.setBooleanValue(GestateMessageActivity.this, MyContant.SETTING, true);
                            finish();
                        } else {
                            App.ErrorToken(mExpectedConfinementBeans.getResultCode(), mExpectedConfinementBeans.getMsg());

                        }
                    }
                });
    }

    private void initListeners() {
        mGestateMessageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sort.equals("")) {
                    Toast toast = Toast.makeText(GestateMessageActivity.this, "请选择您与宝宝的关系", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    if (SpUtil.getBooleanValue(GestateMessageActivity.this, MyContant.ISLOGIN, true)) {
                        initData();
                    } else {
                        SPUtils.put(GestateMessageActivity.this, "w_timeh", format);
                        SPUtils.put(GestateMessageActivity.this, "w_sort", sort);
                        finish();
                    }
                }
            }
        });
        mGestateMessageMama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGestateMessageMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                mGestateMessageBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                sort = "妈妈";
            }
        });
        mGestateMessageBaba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGestateMessageMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                mGestateMessageBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                sort = "爸爸";
            }
        });
        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        times = formatter.format(curDate);

        String startTimes = times.substring(0, times.indexOf(" "));

        DateTime dateTime = DateTime.parse(startTimes);//设置的时间
        final DateTime nowstime = dateTime.plusDays(280);//向前推280
        final Date date = nowstime.toDate();
        final String s = formatter.format(date);

        mGestateMessageRL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeSelector timeSelector = new TimeSelector(GestateMessageActivity.this, new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        time1 = time;
                        String result = time.substring(0, time.indexOf(" "));
                        mGestateMessageTime.setText(result);
                        format = result;
                        //预产期修改后，设置播放器播放修改后的语音
//                        SPUtils.put(GestateMessageActivity.this, "isPlay", "1");
//                        MessageEvent messageEvent = new MessageEvent();
//                        messageEvent.setPlay(true);
//                        EventBus.getDefault().post(messageEvent);
                        if (HomeFragment.mediaPlayer != null) {
                            HomeFragment.mediaPlayer.stop();
                            HomeFragment.isPlay = true;
                            HomeFragment.isPlayStart = false;
                        }

//                        if (SpUtil.getBooleanValue(GestateMessageActivity.this, MyContant.ISLOGIN, true)) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = null;
                            try {
                                date = sdf.parse(time);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                            format = sdf1.format(date);
//                        } else {
//                            SPUtils.put(GestateMessageActivity.this, "w_timeh", result);
//                            Toast.makeText(GestateMessageActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
//                        }
                    }
                }, times, s);
                timeSelector.setIsLoop(true);//设置不循环,true循环
                timeSelector.setMode(TimeSelector.MODE.YMD);//显示 年月日时分（默认）
                timeSelector.show();
            }
        });

        mGestateMessageRL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SpUtil.getBooleanValue(GestateMessageActivity.this, MyContant.ISLOGIN, true)) {
                    if (authority.equals("0")) {
                        Toast.makeText(GestateMessageActivity.this, "亲，您还没有修改的权限哦！", Toast.LENGTH_SHORT).show();
                    } else if (authority.equals("1") || authority.equals("2")) {
                        initSend();
                    }
                } else {
                    initSend();
                }
            }
        });
    }

    private void initSend() {
        final AlertDialog alert;
        alert = new AlertDialog.Builder(GestateMessageActivity.this).create();
        alert.show();
        //加载自定义dialog
        alert.getWindow().setContentView(R.layout.delete_dialogs);
        alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //取消
        alert.getWindow().findViewById(R.id.cart_delete_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                return;
            }
        });
        alert.getWindow().findViewById(R.id.cart_delete_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (App.mediaPlayer2 != null) {
//                    if (App.mediaPlayer2.isPlaying()) {
//                        App.mediaPlayer2.release();
//                    }
//                }
                if (HomeFragment.mediaPlayer != null) {
                    if (HomeFragment.mediaPlayer.isPlaying()) {
                        HomeFragment.mediaPlayer.release();
                    }
                }
                Intent intent = new Intent(GestateMessageActivity.this, StateSettingActivity.class);
                intent.putExtra("flag", "1");
//                intent.putExtra("hyz", "false");
                intent.putExtra("hyz", hyz);
                intent.putExtra("byz", byz);
                intent.putExtra("isTianJia", "0");
                startActivity(intent);
                SpUtil.setBooleanValue(GestateMessageActivity.this, MyContant.SETTING, false);
                SpUtil.setBooleanValue(GestateMessageActivity.this, MyContant.W_SETTING, false);
//                        SpUtil.setBooleanValue(GestateMessageActivity.this, MyContant.TIAOZHUAN, false);
//                        finish();
                alert.dismiss();
            }
        });
    }

    public void GestateMessage_Bank(View view) {
        finish();
    }
}
