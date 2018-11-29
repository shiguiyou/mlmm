package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.MessageCenterAdapter;
import com.wanquan.mlmmx.mlmm.adapter.SignButtonAdapter;
import com.wanquan.mlmmx.mlmm.adapter.SignInCenterAdapter;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.MyDiscountCouponBeans;
import com.wanquan.mlmmx.mlmm.beans.PersonalinformationActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.SignBeans;
import com.wanquan.mlmmx.mlmm.beans.SignInBottonBeans;
import com.wanquan.mlmmx.mlmm.beans.SignInCenterBeans;
import com.wanquan.mlmmx.mlmm.fragment.MineFragment;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MyListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：签到
 * 作者：薛昌峰
 * 时间：2018.04.11
 */
public class SignInActivity extends BaseActivity {
    private LinearLayout mAppTitleBank;
    private LinearLayout mAppTitleZhuanJF;
    private TextView mAppTitleName;
    private TextView mAppTitleSave;
    private TextView mSignInMoney;
    private TextView mSignInTimeSize;
    private TextView mSignInMoney2;
    private TextView mSignInIntegral;
    private RecyclerView mSignInRecyclerView;
    private MyListView mSignInMyListView;
    private SignInCenterAdapter mSignInCenterAdapter;
    private List<SignInCenterBeans.DataBean.TomorrowSignBean> mListQian = new ArrayList<>();
    private List<SignInCenterBeans.DataBean.TomorrowSignBean> mList = new ArrayList<>();
    private List<SignInBottonBeans.DataBean> mList2 = new ArrayList<>();
    private SignButtonAdapter mSignButtonAdapter;
    private String times;
    private String balanceAfterSign;
    private String flags;

    @Override
    protected void onResume() {
        super.onResume();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "087");
        hashMap.put("token", SPUtils.get(SignInActivity.this, "token", ""));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<SignBeans>(SignInActivity.this) {
                    @Override
                    public void onSuccess(final SignBeans mSignBeans, Call call, Response response) {
                        if (mSignBeans.getResultCode() == 1) {
                            final AlertDialog alert;
                            alert = new AlertDialog.Builder(SignInActivity.this).create();
                            alert.show();
                            alert.getWindow().setContentView(R.layout.dialog_sign2);

                            TextView mSingDiaLogSize = (TextView) alert.getWindow().findViewById(R.id.Sing_DiaLog_Size);
                            TextView mSingDiaLogTV = (TextView) alert.getWindow().findViewById(R.id.Sing_DiaLog_TV);
                            TextView mSingDiaLogTV2 = (TextView) alert.getWindow().findViewById(R.id.Sing_DiaLog_TV2);
                            TextView mSignGot = (TextView) alert.getWindow().findViewById(R.id.Sign_Got);
                            TextView mSignSize = (TextView) alert.getWindow().findViewById(R.id.Sign_Size);

                            mSingDiaLogSize.setText("+" + mSignBeans.getData().getTodayIntegral());
                            mSingDiaLogTV.setText("签到成功+" + mSignBeans.getData().getTodayIntegral() + "分");
                            mSingDiaLogTV2.setText("已连续签到" + mSignBeans.getData().getContinuousDays() + "天");
                            mSignSize.setText(mSignBeans.getData().getBalanceAfterSign() + "");

                            mSignGot.setText("确定");
                            mSignGot.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alert.dismiss();
                                }
                            });
                        } else {
//                            App.ErrorToken(mSignBeans.getResultCode(), mSignBeans.getMsg());
                        }
                        initData();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(SignInActivity.this, R.color.tops);
        //注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        flags = getIntent().getStringExtra("flags");

        initListeners();
        mSignButtonAdapter = new SignButtonAdapter(this, mList2);
        mSignInMyListView.setAdapter(mSignButtonAdapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_sign_in;
    }

    @Override
    public void initView() throws Exception {
        mAppTitleBank = (LinearLayout) findViewById(R.id.App_Title_Bank);
        mAppTitleZhuanJF = (LinearLayout) findViewById(R.id.SignIn_ZhuanJF);
        mAppTitleName = (TextView) findViewById(R.id.App_Title_Name);
        mAppTitleSave = (TextView) findViewById(R.id.App_Title_Save);
        mSignInMoney = (TextView) findViewById(R.id.SignIn_Money);
        mSignInTimeSize = (TextView) findViewById(R.id.SignIn_TimeSize);
        mSignInMoney2 = (TextView) findViewById(R.id.SignIn_Money2);
        mSignInIntegral = (TextView) findViewById(R.id.SignIn_Integral);
        mSignInRecyclerView = (RecyclerView) findViewById(R.id.SignIn_RecyclerView);
        mSignInMyListView = (MyListView) findViewById(R.id.SignIn_MyListView);
    }

    private void initData() {
        //获取个人信息
        Log.e("token", String.valueOf(SPUtils.get(SignInActivity.this, "token", "")));
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("itfaceId", "021");
        hashMap.put("token", SPUtils.get(SignInActivity.this, "token", ""));
        hashMap.put("user", "my");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<PersonalinformationActivityBeans>(SignInActivity.this) {
                    @Override
                    public void onSuccess(PersonalinformationActivityBeans mPersonalinformationActivityBeans, Call call, Response response) {
                        if (mPersonalinformationActivityBeans.getResultCode() == 1) {
                            mSignInMoney.setText(String.valueOf(mPersonalinformationActivityBeans.getData().getIntegralBalance()));
                        } else {
                            App.ErrorToken(mPersonalinformationActivityBeans.getResultCode(), mPersonalinformationActivityBeans.getMsg());
                        }
                    }
                });
        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        times = formatter.format(curDate);
        Log.e("time", times);
        mAppTitleName.setText("签到");

//        Log.e("fffff", String.valueOf(SPUtils.get(SignInActivity.this, "token", "")));
//        Log.e("fffff", String.valueOf(SPUtils.get(SignInActivity.this, "userid", "")));

        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("itfaceId", "086");
        hashMap2.put("token", SPUtils.get(SignInActivity.this, "token", ""));
        JSONObject jsonObject2 = new JSONObject(hashMap2);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject2.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<SignInCenterBeans>(SignInActivity.this) {
                    @Override
                    public void onSuccess(SignInCenterBeans mSignInCenterBeans, Call call, Response response) {
                        if (mSignInCenterBeans.getResultCode() == 1) {
                            mList.clear();
                            mListQian.addAll(mSignInCenterBeans.getData().getSignHis());
                            mList.addAll(mSignInCenterBeans.getData().getSignHis());
                            mList.addAll(mSignInCenterBeans.getData().getTomorrowSign());

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SignInActivity.this);
                            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            mSignInRecyclerView.setLayoutManager(linearLayoutManager);

//                          mSignInMoney.setText(mSignInCenterBeans.getData().getSignHis().get(mSignInCenterBeans.getData().getSignHis().size() - 1).getGainIntegral() + "");
                            mSignInTimeSize.setText(mSignInCenterBeans.getData().getContinueSignDays() + "");
                            mSignInMoney2.setText("明日签到可领取 " + mSignInCenterBeans.getData().getTomorrowSign().get(0).getGainIntegral() + " 积分");
                            for (int i = 0; i < mListQian.size(); i++) {
                                if (mListQian.get(i).getGainIntegral() != 0) {
                                    mListQian.get(i).setFlag(true);
                                }
                            }
                            for (int i = 0; i < mList.size(); i++) {
                                String signDate = mList.get(i).getSignDate();
                                signDate = signDate.substring(5);
                                if (times.equals(signDate)) {
                                    linearLayoutManager.scrollToPositionWithOffset(i, 0);
                                    break;
                                }
                            }
                            mSignInCenterAdapter = new SignInCenterAdapter(mListQian, mList, SignInActivity.this);
                            mSignInRecyclerView.setAdapter(mSignInCenterAdapter);
                        } else {
                            App.ErrorToken(mSignInCenterBeans.getResultCode(), mSignInCenterBeans.getMsg());

                        }
                    }
                });

        //优惠券
        HashMap<String, Object> hashMap3 = new HashMap<>();
        hashMap3.put("itfaceId", "092");
        hashMap3.put("token", SPUtils.get(SignInActivity.this, "token", ""));
//        hashMap.put("pageNum", "1");
//        hashMap3.put("numberPage", "100");
        JSONObject jsonObject3 = new JSONObject(hashMap3);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject3.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<SignInBottonBeans>(this) {
                    @Override
                    public void onSuccess(SignInBottonBeans mSignInBottonBeans, Call call, Response response) {
                        if (mSignInBottonBeans.getResultCode() == 1) {
                            mList2.clear();
                            mList2.addAll(mSignInBottonBeans.getData());
                            mSignButtonAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mSignInBottonBeans.getResultCode(), mSignInBottonBeans.getMsg());
                        }
                    }
                });

    }

    private void initListeners() {
        mAppTitleZhuanJF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, IntegralStrategyActivity.class).putExtra("flags", "5"));
            }
        });
        mAppTitleBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flags != null) {
                    if (flags.equals("3")) {
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    } else if (flags.equals("5")) {
                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.setShowBank(false);
                        EventBus.getDefault().post(messageEvent);
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    } else {
                        finish();
                    }
                } else {
                    finish();
                }
            }
        });
        mSignInIntegral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alert;
                alert = new AlertDialog.Builder(SignInActivity.this).create();
                alert.show();
                alert.getWindow().setContentView(R.layout.dialog_sign1);
                alert.getWindow().findViewById(R.id.Sign_Got).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });
            }
        });
        mSignInMyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SignInActivity.this, DiscountCouponDetailsActivity.class);
                intent.putExtra("id", String.valueOf(mList2.get(position).getId()));
                intent.putExtra("flag", "1");
                intent.putExtra("flags", "1");
                startActivity(intent);
            }
        });
    }

    //EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        boolean show = event.isDuiHuan();
        if (show) {
            initData();
        }
    }
}
