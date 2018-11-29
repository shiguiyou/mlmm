package com.wanquan.mlmmx.mlmm.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.BabyActivity;
import com.wanquan.mlmmx.mlmm.activity.BabyRoomActivity;
import com.wanquan.mlmmx.mlmm.beans.BabyRoomGridViewBeans;
import com.wanquan.mlmmx.mlmm.beans.BabyRoomkzBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：场景模式
 * 作者：薛昌峰
 * 时间：2018.05.23
 */

public class BabyRoomFragmentCJ extends BaseFragment {
    private LinearLayout mLinearLayoutLL1;
    private ImageView mLinearLayoutImg1;
    private LinearLayout mLinearLayoutLL2;
    private ImageView mLinearLayoutImg2;
    private TextView mTextView2;
    private LinearLayout mLinearLayoutLL3;
    private ImageView mLinearLayoutImg3;
    private LinearLayout mLinearLayoutLL4;
    private ImageView mLinearLayoutImg4;
    private LinearLayout mLinearLayoutLL5;
    private ImageView mLinearLayoutImg5;
    private LinearLayout mLinearLayoutLL6;
    private ImageView mLinearLayoutImg6;
    private List<BabyRoomGridViewBeans.DataBean> mList = new ArrayList<>();
    private String id;
    private String code;
    //    private boolean run = true;
//    private final Handler handler = new Handler();
//    private final Runnable task = new Runnable() {
//        @Override
//        public void run() {
//            // TODO Auto-generated method stub
//            if (run) {
//                handler.postDelayed(this, 5000);
//                initData();
//            }
//        }
//    };
    private AirConditionerReceiver mAirConditionerReceiver;

    private class AirConditionerReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.xcf.moshi")) {
                String logoutFlag = intent.getStringExtra("moshi");
                if (logoutFlag.equals("1")) {
                    initData();
                }
            }
        }
    }

    private View view;

    private boolean status_k;
    private boolean status_flz;
    private String namems;
    private String token;
    private String dwName;
    private boolean isShow2;
    private boolean isShow1;
    private boolean LL5Show = true;//臭氧显示或取消
    private boolean LL6Show = true;//pm0.3显示或取消


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_babyroomcj, null);

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
            code = bundle.getString("code");
        }
        Log.e("ddddid", id + "xcf");
//        Log.e("ddddcode", code + "xcf");

//        task.run();
        initViews();
        initData();
        initListeners();

        mAirConditionerReceiver = new AirConditionerReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.xcf.moshi");
        getActivity().registerReceiver(mAirConditionerReceiver, filter);

        return view;
    }

    private void initViews() {
        mLinearLayoutLL1 = (LinearLayout) view.findViewById(R.id.LinearLayout_LL1);
        mLinearLayoutImg1 = (ImageView) view.findViewById(R.id.LinearLayout_Img1);
        mLinearLayoutLL2 = (LinearLayout) view.findViewById(R.id.LinearLayout_LL2);
        mLinearLayoutImg2 = (ImageView) view.findViewById(R.id.LinearLayout_Img2);
        mTextView2 = (TextView) view.findViewById(R.id.textView2);
        mLinearLayoutLL3 = (LinearLayout) view.findViewById(R.id.LinearLayout_LL3);
        mLinearLayoutImg3 = (ImageView) view.findViewById(R.id.LinearLayout_Img3);
        mLinearLayoutLL4 = (LinearLayout) view.findViewById(R.id.LinearLayout_LL4);
        mLinearLayoutImg4 = (ImageView) view.findViewById(R.id.LinearLayout_Img4);
        mLinearLayoutLL5 = (LinearLayout) view.findViewById(R.id.LinearLayout_LL5);
        mLinearLayoutImg5 = (ImageView) view.findViewById(R.id.LinearLayout_Img5);
        mLinearLayoutLL6 = (LinearLayout) view.findViewById(R.id.LinearLayout_LL6);
        mLinearLayoutImg6 = (ImageView) view.findViewById(R.id.LinearLayout_Img6);
    }

    private void initData() {
        if ("0".equals(SPUtils.get(getContext(), "BabyState", ""))) {
            mTextView2.setText("睡眠模式");
        } else if ("1".equals(SPUtils.get(getContext(), "BabyState", ""))) {
            mTextView2.setText("宝妈睡眠模式");
        } else if ("2".equals(SPUtils.get(getContext(), "BabyState", ""))) {
            mTextView2.setText("宝宝睡眠模式");
        }


        Log.e("xcfxcftoken", BabyRoomActivity.token);
//        Log.e("xcfxcfdeviceId", id);
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("itfaceId", "020");
        hashMap2.put("token", BabyRoomActivity.token);
        hashMap2.put("deviceId", id);
        JSONObject jsonObject2 = new JSONObject(hashMap2);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject2.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<BabyRoomGridViewBeans>(getContext()) {
                    @Override
                    public void onSuccess(BabyRoomGridViewBeans mBabyRoomGridViewBeans, Call call, Response response) {
                        if (mBabyRoomGridViewBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mBabyRoomGridViewBeans.getData());

                            status_k = mBabyRoomGridViewBeans.getData().get(0).getCfs().get(0).isStatus();//是否开启
                            status_flz = mBabyRoomGridViewBeans.getData().get(5).getCfs().get(0).isStatus();//负离子
                            namems = mBabyRoomGridViewBeans.getData().get(2).getName();//模式
                            dwName = mBabyRoomGridViewBeans.getData().get(1).getName();//档位

                            if (status_k && status_flz && "3档".equals(dwName)) {//三重杀菌
                                if (isAdded()) {
                                    mLinearLayoutImg1.setBackground(getResources().getDrawable(R.mipmap.scene_sterilizationon));
                                    mLinearLayoutImg2.setBackground(getResources().getDrawable(R.mipmap.scene_sleepoff));
                                    mLinearLayoutImg3.setBackground(getResources().getDrawable(R.mipmap.scene_nagtiveionsoff));
                                    mLinearLayoutImg4.setBackground(getResources().getDrawable(R.mipmap.scene_freshoff));
                                    initShows();

                                }
                            } else if (status_k && !status_flz && "2档".equals(dwName)) {//睡眠模式
                                if (isAdded()) {
                                    mLinearLayoutImg1.setBackground(getResources().getDrawable(R.mipmap.scene_sterilizationoff));
                                    mLinearLayoutImg2.setBackground(getResources().getDrawable(R.mipmap.scene_sleepon));
                                    mLinearLayoutImg3.setBackground(getResources().getDrawable(R.mipmap.scene_nagtiveionsoff));
                                    mLinearLayoutImg4.setBackground(getResources().getDrawable(R.mipmap.scene_freshoff));
                                    initShows();
                                }
                            } else if (status_k && status_flz && "5档".equals(dwName)) {//强效负离子
                                if (isAdded()) {
                                    mLinearLayoutImg1.setBackground(getResources().getDrawable(R.mipmap.scene_sterilizationoff));
                                    mLinearLayoutImg2.setBackground(getResources().getDrawable(R.mipmap.scene_sleepoff));
                                    mLinearLayoutImg3.setBackground(getResources().getDrawable(R.mipmap.scene_nagtiveionson));
                                    mLinearLayoutImg4.setBackground(getResources().getDrawable(R.mipmap.scene_freshoff));
                                    initShows();
                                }
                            } else if (status_k && status_flz && "2档".equals(dwName)) {//清新模式
                                if (isAdded()) {//使用上下文地方调用，此处会出现Fragment not attached to Activity异常
                                    mLinearLayoutImg1.setBackground(getResources().getDrawable(R.mipmap.scene_sterilizationoff));
                                    mLinearLayoutImg2.setBackground(getResources().getDrawable(R.mipmap.scene_sleepoff));
                                    mLinearLayoutImg3.setBackground(getResources().getDrawable(R.mipmap.scene_nagtiveionsoff));
                                    mLinearLayoutImg4.setBackground(getResources().getDrawable(R.mipmap.scene_freshon));

                                    initShows();
                                }
                            } else if ("1档".equals(dwName) && !"自动模式".equals(namems)) {
                                if (isAdded()) {
                                    mLinearLayoutImg1.setBackground(getResources().getDrawable(R.mipmap.scene_sterilizationoff));
                                    mLinearLayoutImg2.setBackground(getResources().getDrawable(R.mipmap.scene_sleepoff));
                                    mLinearLayoutImg3.setBackground(getResources().getDrawable(R.mipmap.scene_nagtiveionsoff));
                                    mLinearLayoutImg4.setBackground(getResources().getDrawable(R.mipmap.scene_freshoff));
                                    initShows();
                                }
                            } else if ("4档".equals(dwName) && !"自动模式".equals(namems)) {
                                if (isAdded()) {
                                    mLinearLayoutImg1.setBackground(getResources().getDrawable(R.mipmap.scene_sterilizationoff));
                                    mLinearLayoutImg2.setBackground(getResources().getDrawable(R.mipmap.scene_sleepoff));
                                    mLinearLayoutImg3.setBackground(getResources().getDrawable(R.mipmap.scene_nagtiveionsoff));
                                    mLinearLayoutImg4.setBackground(getResources().getDrawable(R.mipmap.scene_freshoff));
                                    initShows();
                                }
                            } else if (!status_k) {
                                if (isAdded()) {
                                    mLinearLayoutImg1.setBackground(getResources().getDrawable(R.mipmap.scene_sterilizationoff));
                                    mLinearLayoutImg2.setBackground(getResources().getDrawable(R.mipmap.scene_sleepoff));
                                    mLinearLayoutImg3.setBackground(getResources().getDrawable(R.mipmap.scene_nagtiveionsoff));
                                    mLinearLayoutImg4.setBackground(getResources().getDrawable(R.mipmap.scene_freshoff));
                                }
                            } else {
                                if (isAdded()) {
                                    mLinearLayoutImg1.setBackground(getResources().getDrawable(R.mipmap.scene_sterilizationoff));
                                    mLinearLayoutImg2.setBackground(getResources().getDrawable(R.mipmap.scene_sleepoff));
                                    mLinearLayoutImg3.setBackground(getResources().getDrawable(R.mipmap.scene_nagtiveionsoff));
                                    mLinearLayoutImg4.setBackground(getResources().getDrawable(R.mipmap.scene_freshoff));
                                }
                            }
                        } else {
                            Log.e("rrrrrrrrr1", mBabyRoomGridViewBeans.getMsg());
                            App.ErrorToken(mBabyRoomGridViewBeans.getResultCode(), mBabyRoomGridViewBeans.getMsg());
                        }
                    }
                });
    }

    private void initShows() {
        if (isShow1) {
            mLinearLayoutImg5.setBackground(getResources().getDrawable(R.mipmap.scene_ozoneon));
            mLinearLayoutImg6.setBackground(getResources().getDrawable(R.mipmap.scene_pm03off));
        } else if (isShow2) {
            mLinearLayoutImg5.setBackground(getResources().getDrawable(R.mipmap.scene_ozoneoff));
            mLinearLayoutImg6.setBackground(getResources().getDrawable(R.mipmap.scene_pm03on));
        }
        if (isShow1 = false) {
            mLinearLayoutImg5.setBackground(getResources().getDrawable(R.mipmap.scene_ozoneoff));
            mLinearLayoutImg6.setBackground(getResources().getDrawable(R.mipmap.scene_pm03off));
        } else if (isShow2 = false) {
            mLinearLayoutImg5.setBackground(getResources().getDrawable(R.mipmap.scene_ozoneoff));
            mLinearLayoutImg6.setBackground(getResources().getDrawable(R.mipmap.scene_pm03off));
        }
    }

    private void initListeners() {
        mLinearLayoutLL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
//                new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        try {
//                            if (!status_k) {
//                                initNetwork1("81", "00,0A");//打开
//                                Thread.sleep(2000);
//                            }
//                            if (!"手动模式".equals(namems)) {
//                                initNetwork1("91", "00,3C");//手动模式
//                                Thread.sleep(2000);
//                            }
//                            if (!"2档".equals(dwName)) {
//                                initNetwork1("84", "00,11");//风量2
//                                Thread.sleep(2000);
//                            }
//                            if (!status_flz) {
//                                initNetwork1("82", "00,0C");//负离子
//                            }
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }.start();
                initNetwork1("81", "00,0A,91,00,3C,84,00,12,82,00,0C");//打开
            }
        });
        mLinearLayoutLL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
//                new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        try {
//                            if (!status_k) {
//                                initNetwork1("81", "00,0A");//打开
//                                Thread.sleep(2000);
//                            }
//                            if (!"手动模式".equals(namems)) {
//                                initNetwork1("91", "00,3C");//手动模式
//                                Thread.sleep(2000);
//                            }
//                            if (!"3档".equals(dwName)) {
//                                initNetwork1("84", "00,12");//风量3
//                                Thread.sleep(2000);
//                            }
//                            if (!status_flz) {
//                                initNetwork1("82", "00,0C");//负离子
//                            }
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }.start();
                initNetwork1("81", "00,0A,82,00,0D,84,00,11");//打开

            }
        });
        mLinearLayoutLL3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
//                new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        try {
//                            if (!status_k) {
//                                initNetwork1("81", "00,0A");//打开
//                                Thread.sleep(2000);
//                            }
//                            if (!"手动模式".equals(namems)) {
//                                initNetwork1("91", "00,3C");//手动模式
//                                Thread.sleep(2000);
//                            }
//                            if (!"5档".equals(dwName)) {
//                                initNetwork1("84", "00,14");//风量5
//                                Thread.sleep(2000);
//                            }
//                            if (!status_flz) {
//                                initNetwork1("82", "00,0C");//负离子
//                            }
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }.start();
                initNetwork1("81", "00,0A,91,00,3C,84,00,14,82,00,0C");//打开
            }
        });
        mLinearLayoutLL4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
//                new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        try {
//                            if (!status_k) {
//                                initNetwork1("81", "00,0A");//打开
//                                Thread.sleep(2000);
//                            }
//                            if (status_flz) {
//                                initNetwork1("82", "00,0D");//关闭负离子
//                                Thread.sleep(2000);
//                            }
//                            if (!namems.equals("自动模式")) {
//                                initNetwork1("91", "00,3A");//自动模式
//                            }
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }.start();
                initNetwork1("81", "00,0A,91,00,3C,84,00,11,82,00,0C");//打开

            }
        });
        mLinearLayoutLL5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LL5Show) {
                    mLinearLayoutImg5.setBackground(getResources().getDrawable(R.mipmap.scene_ozoneon));
                    mLinearLayoutImg6.setBackground(getResources().getDrawable(R.mipmap.scene_pm03off));
                    //显示折线图
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setBabyShow(true);
                    messageEvent.setIsBabyShowType("ozone");
                    EventBus.getDefault().post(messageEvent);
                    LL5Show = false;
                    isShow1 = true;
                    isShow2 = false;
                } else {
                    mLinearLayoutImg5.setBackground(getResources().getDrawable(R.mipmap.scene_ozoneoff));
                    mLinearLayoutImg6.setBackground(getResources().getDrawable(R.mipmap.scene_pm03off));
                    //显示折线图
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setBabyShow(false);
                    messageEvent.setIsBabyShowType("ozone");
                    EventBus.getDefault().post(messageEvent);
                    LL5Show = true;
                    isShow1 = false;
                    isShow2 = false;
                }
            }
        });
        mLinearLayoutLL6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LL6Show) {
                    mLinearLayoutImg5.setBackground(getResources().getDrawable(R.mipmap.scene_ozoneoff));
                    mLinearLayoutImg6.setBackground(getResources().getDrawable(R.mipmap.scene_pm03on));
                    //显示折线图
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setBabyShow(true);
                    messageEvent.setIsBabyShowType("pm03");
                    EventBus.getDefault().post(messageEvent);
                    LL6Show = false;
                    isShow1 = false;
                    isShow2 = true;
                } else {
                    mLinearLayoutImg5.setBackground(getResources().getDrawable(R.mipmap.scene_ozoneoff));
                    mLinearLayoutImg6.setBackground(getResources().getDrawable(R.mipmap.scene_pm03off));
                    //显示折线图
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setBabyShow(false);
                    messageEvent.setIsBabyShowType("pm03");
                    EventBus.getDefault().post(messageEvent);
                    LL6Show = true;
                    isShow1 = false;
                    isShow2 = false;
                }
            }
        });
    }

    private void initShow() {
        //显示折线图
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setBabyShow(false);
        EventBus.getDefault().post(messageEvent);
    }

    private void initNetwork1(final String functionCode, final String functionCode2) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "005");
        hashMap.put("token", SPUtils.get(getContext(), "token", ""));
        hashMap.put("deviceCode", code);
        hashMap.put("ctlType", functionCode);
        hashMap.put("ctlStatus", functionCode2);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
//                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<BabyRoomkzBeans>(getContext()) {
                    @Override
                    public void onSuccess(BabyRoomkzBeans mBabyRoomkzBeans, Call call, Response response) {
                        if (mBabyRoomkzBeans.getResultCode() == 1) {
                            Log.e("rrrrrrrrrfunctionCode", functionCode + "---------" + functionCode2);

                            //获取当前时间
//                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                            Date curDate = new Date(System.currentTimeMillis());
//                            String times = formatter.format(curDate);
//                            Log.e("rrrrrrrrr_tine", times);
                            Toast.makeText(getContext(), "操作成功", Toast.LENGTH_SHORT).show();
//                            initData();
                        } else {
                            Log.e("rrrrrrrrr2", mBabyRoomkzBeans.getMsg());
                            App.ErrorToken(mBabyRoomkzBeans.getResultCode(), mBabyRoomkzBeans.getMsg());
                        }
                    }
                });
    }
}
