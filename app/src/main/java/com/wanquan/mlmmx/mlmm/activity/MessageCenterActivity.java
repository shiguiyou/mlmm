package com.wanquan.mlmmx.mlmm.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.MessageCenterAdapter;
import com.wanquan.mlmmx.mlmm.adapter.MyAdapter;
import com.wanquan.mlmmx.mlmm.beans.LoginBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageCenterBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.PersonalinformationActivityBeans;
import com.wanquan.mlmmx.mlmm.fragment.MessageRemindFragment;
import com.wanquan.mlmmx.mlmm.fragment.MessageReplayFragment;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.receiver.MyReceiver;
import com.wanquan.mlmmx.mlmm.utils.MD5Util;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MyDialog_Views;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：通知中心
 * 作者：薛昌峰
 * 时间：2017.09.11
 */
public class MessageCenterActivity extends BaseActivity {
    private LinearLayout mMessageCenterBank;
    private ImageView mMessageCenterHongimg;
    private ImageView mMessageCenterHongimg2;
    private TextView mMessageCenterClose;
    private TextView mMessageCenterDelete;
    private RadioGroup mMessageCenterRadioGroup;
    private RadioButton mMessageCenterRadioButton1;
    private RadioButton mMessageCenterRadioButton2;
    private ViewPager mMessageCenterViewPager;
    private MessageCenterAdapter mMessageCenterAdapter;
    private List<MessageCenterBeans.DataBean> mList = new ArrayList<>();
    private MessageRemindFragment mMessageRemindFragment;
    private MessageReplayFragment mMessageReplayFragment;
    private List<Fragment> mList2;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private MyAdapter mAdapter;

    private boolean delete = false;
    private boolean isDelete = false;
    private String str;
    private int code;
    private LogoutReceiver logoutReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(MessageCenterActivity.this, R.color.black);

        if (SpUtil.getBooleanValue(MessageCenterActivity.this, MyContant.ISLOGIN, true)) {
        } else {
            startActivity(new Intent(MessageCenterActivity.this, LoginActivity.class));
        }

        Log.e(":dsdsasdada", "到了2");

        final Dialog dialog2 = new MyDialog_Views(MessageCenterActivity.this, R.style.MyDialog);
        dialog2.setCancelable(true);
        dialog2.show();
        MyDialog_Views myDialog_views = new MyDialog_Views(MessageCenterActivity.this, "正在拼命加载...", "");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initDeleteShow();

                        code = getIntent().getIntExtra("code", 0);
                        if (code == 3) {
                            resetViewPager(R.id.MessageCenter_RadioButton2);
                        }
                        dialog2.dismiss();
                    }
                }, 2000);
            }
        }, 1000);


        logoutReceiver = new LogoutReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.xcf.receiver");
        registerReceiver(logoutReceiver, filter);


        initData();
        initHongDian(1);
        initListeners();

        mMessageCenterViewPager.setOffscreenPageLimit(1);

    }

    private class LogoutReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.xcf.receiver")) {
                int logoutFlag = intent.getIntExtra("receiver", -1);
                Log.e("TAG", "-->" + logoutFlag);
                if (logoutFlag == 1) {//1：普通消息
                    initHongDian(1);
                } else if (logoutFlag == 2) {//2 .触发事件
                    initHongDian(1);
                } else if (logoutFlag == 3) {//3.消息回复
                    initHongDian(2);
//                    resetViewPager(R.id.MessageCenter_RadioButton2);
                }
            }
        }
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_message_center;
    }

    @Override
    public void initView() throws Exception {
        mMessageCenterBank = (LinearLayout) findViewById(R.id.MessageCenter_Bank);
        mMessageCenterHongimg = (ImageView) findViewById(R.id.MessageCenter_Hong_img);
        mMessageCenterHongimg2 = (ImageView) findViewById(R.id.MessageCenter_Hong_img2);
        mMessageCenterClose = (TextView) findViewById(R.id.MessageCenter_Close);
        mMessageCenterDelete = (TextView) findViewById(R.id.MessageCenter_Delete);
        mMessageCenterRadioGroup = (RadioGroup) findViewById(R.id.MessageCenter_RadioGroup);
        mMessageCenterRadioButton1 = (RadioButton) findViewById(R.id.MessageCenter_RadioButton1);
        mMessageCenterRadioButton2 = (RadioButton) findViewById(R.id.MessageCenter_RadioButton2);
        mMessageCenterViewPager = (ViewPager) findViewById(R.id.MessageCenter_ViewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initData() {
        mList2 = new ArrayList<>();
        mMessageRemindFragment = new MessageRemindFragment();
        mMessageReplayFragment = new MessageReplayFragment();
        mList2.add(mMessageRemindFragment);
        mList2.add(mMessageReplayFragment);

        mFragmentManager = getSupportFragmentManager();
        mAdapter = new MyAdapter(mFragmentManager, mList2);
        mMessageCenterViewPager.setAdapter(mAdapter);
    }

    private void initListeners() {
        mMessageCenterRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                resetViewPager(checkedId);
            }
        });
        //滑动ViewPage的时候及时修改底部导航栏对应的图标
        mMessageCenterViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //根据当前位置设置默认选中单选按钮
                resetRadioButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mMessageCenterDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDelete) {
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setDelete(true);
                    messageEvent.setShow(true);
                    EventBus.getDefault().post(messageEvent);
                    if (!"".equals(MessageRemindFragment.str)) {
                        isDelete = false;
                        mMessageCenterDelete.setText("确认");
                        mMessageCenterBank.setVisibility(View.VISIBLE);
                        mMessageCenterClose.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(MessageCenterActivity.this, "请勾选删除项", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    isDelete = true;
                    delete = true;

                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setShow(delete);
                    EventBus.getDefault().post(messageEvent);

                    mMessageCenterDelete.setText("确定删除");
                    mMessageCenterBank.setVisibility(View.GONE);
                    mMessageCenterClose.setVisibility(View.VISIBLE);

                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("fsdfsdfsd1", String.valueOf(MessageRemindFragment.mList.size()));

                        initDeleteShow();
                    }
                }, 1000);
            }
        });
        mMessageCenterClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDelete = false;
                delete = false;

                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setShow(delete);
                EventBus.getDefault().post(messageEvent);

                mMessageCenterDelete.setText("删除");
                mMessageCenterBank.setVisibility(View.VISIBLE);
                mMessageCenterClose.setVisibility(View.GONE);
            }
        });
    }

    private void initHongDian(final int size) {
        Log.e("dsadadsada1", String.valueOf(SPUtils.get(this, "hongdianShow1", "")));
        Log.e("dsadadsada2", String.valueOf(SPUtils.get(this, "hongdianShow2", "")));

        if ("true".equals(String.valueOf(SPUtils.get(this, "hongdianShow1", "")))) {
            mMessageCenterHongimg.setVisibility(View.VISIBLE);
        } else {
            mMessageCenterHongimg.setVisibility(View.GONE);
        }

        if ("true".equals(String.valueOf(SPUtils.get(this, "hongdianShow2", "")))) {
            mMessageCenterHongimg2.setVisibility(View.VISIBLE);
        } else {
            mMessageCenterHongimg2.setVisibility(View.GONE);
        }
//
//        //获取个人信息接口——判断是否显示红点
////      Log.e("token", (String) SPUtils.get(getContext(), "token", ""));
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("itfaceId", "021");
//        hashMap.put("token", SPUtils.get(this, "token", ""));
//        JSONObject jsonObject = new JSONObject(hashMap);
//
//        OkGo.post(UrlContent.URL).tag(this)
//                .upJson(jsonObject.toString())
//                .connTimeOut(10_000)
//                .execute(new CustomCallBackNoLoading<PersonalinformationActivityBeans>(this) {
//                    @Override
//                    public void onSuccess(PersonalinformationActivityBeans mPersonalinformationActivityBeans, Call call, Response response) {
//                        if (mPersonalinformationActivityBeans.getResultCode() == 1) {
//                            Log.e("dasdasa", String.valueOf(mPersonalinformationActivityBeans.getData().isHasUnReadMsg()));
//                            Log.e("dasdasa", String.valueOf(mPersonalinformationActivityBeans.getData().isHasUnReadComment()));
//                            if (size == 1) {
//                                if (mPersonalinformationActivityBeans.getData().isHasUnReadMsg()) {
//                                    mMessageCenterHongimg.setVisibility(View.VISIBLE);
//                                } else {
//                                    mMessageCenterHongimg.setVisibility(View.GONE);
//                                }
//                            } else {
//                                if (mPersonalinformationActivityBeans.getData().isHasUnReadComment()) {
//                                    mMessageCenterHongimg2.setVisibility(View.VISIBLE);
//                                } else {
//                                    mMessageCenterHongimg2.setVisibility(View.GONE);
//                                }
//                            }
//                        } else {
//                            App.ErrorToken(mPersonalinformationActivityBeans.getResultCode(), mPersonalinformationActivityBeans.getMsg());
//                        }
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                        Toast.makeText(MessageCenterActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    private void resetViewPager(int checkedId) {
        switch (checkedId) {
            case R.id.MessageCenter_RadioButton1:
                Log.e("FFFF1", String.valueOf(checkedId));

                mMessageCenterViewPager.setCurrentItem(0);
                mMessageCenterRadioButton1.setTextColor(getResources().getColor(R.color.text_hui));
                mMessageCenterRadioButton2.setTextColor(getResources().getColor(R.color.black));

                mMessageCenterDelete.setVisibility(View.VISIBLE);
                break;
            case R.id.MessageCenter_RadioButton2:
                Log.e("FFFF2", String.valueOf(checkedId));

                mMessageCenterViewPager.setCurrentItem(1);
                SPUtils.put(this, "hongdianShow2", "false");
                mMessageCenterHongimg2.setVisibility(View.GONE);
                mMessageCenterRadioButton1.setTextColor(getResources().getColor(R.color.black));
                mMessageCenterRadioButton2.setTextColor(getResources().getColor(R.color.text_hui));
                mMessageCenterDelete.setVisibility(View.GONE);


                Intent logoutIntent = new Intent();
                logoutIntent.setAction("com.xcf.receiver");
                logoutIntent.putExtra("receiver", -2);
                sendBroadcast(logoutIntent);

                break;
        }
    }

    private void resetRadioButton(int position) {
        //获取position位置处对于的单选按钮
        RadioButton radioButton = (RadioButton) mMessageCenterRadioGroup.getChildAt(position);
        //设置当前单选按钮默认选中
        radioButton.setChecked(true);//2131558986//2131558985
    }

    public void MessageCenterActivity_Bank(View view) {
        finish();
    }

    //EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        initDeleteShow();


        if (event.getIsReceiverCode() == 1) {
            initHongDian(1);
        } else {
            initHongDian(2);
        }

        boolean show = event.isDeleteShow();
        if (show) {
            isDelete = false;
            delete = false;
            mMessageCenterDelete.setText("删除");
            mMessageCenterBank.setVisibility(View.VISIBLE);
            mMessageCenterClose.setVisibility(View.GONE);
        }
    }

    private void initDeleteShow() {
        Log.e("fsdfsdfsd2", String.valueOf(MessageRemindFragment.mList.size()));
        if (MessageRemindFragment.mList.size() == 0) {
            mMessageCenterDelete.setVisibility(View.GONE);
        } else {
            mMessageCenterDelete.setVisibility(View.VISIBLE);
        }
    }
}
