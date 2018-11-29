package com.wanquan.mlmmx.mlmm.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.BabyActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.BabyMessageAddBeans;
import com.wanquan.mlmmx.mlmm.beans.ExpectedConfinementBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.fragment.HomeFragment;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：状态设置
 * 作者：薛昌峰
 * 时间：2017.09.10
 */
public class StateSettingActivity extends BaseActivity {
    private LinearLayout mStateSettingActivityBack;
    private LinearLayout mStateSettingActivityOne;
    private LinearLayout mStateSettingActivityTwo;
    private LinearLayout mStateSettingActivityThree;
    private LogoutReceiver logoutReceiver;
    private String flag;
    private String byz;
    private String hyz;
    private String isTianJia = "1";

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(StateSettingActivity.this, R.color.black);

        flag = getIntent().getStringExtra("flag");
        byz = getIntent().getStringExtra("byz");
        hyz = getIntent().getStringExtra("hyz");
//        Log.e("dfdfdfd1", byz + hyz);
        Log.e("dfdfdfd1", flag + "xcf");

        String isTianJias = getIntent().getStringExtra("isTianJia");//判断是添加新宝宝还修改宝宝信息
        if (isTianJias != null) {
            isTianJia = isTianJias;
            Log.e("dfdfdfd2", isTianJia);
        }

        initData();
        initListeners();

        logoutReceiver = new LogoutReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.xcf.logout");
        registerReceiver(logoutReceiver, filter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_state_setting;
    }

    @Override
    public void initView() throws Exception {
        mStateSettingActivityBack = (LinearLayout) findViewById(R.id.StateSettingActivity_Back);
        mStateSettingActivityOne = (LinearLayout) findViewById(R.id.StateSettingActivity_One);
        mStateSettingActivityTwo = (LinearLayout) findViewById(R.id.StateSettingActivity_Two);
        mStateSettingActivityThree = (LinearLayout) findViewById(R.id.StateSettingActivity_Three);
    }

    private void initData() {
        if (flag != null) {
            if ("1".equals(flag)) {
                mStateSettingActivityBack.setVisibility(View.VISIBLE);
            } else {
                mStateSettingActivityBack.setVisibility(View.GONE);
            }
        }
    }

    private void initListeners() {
        mStateSettingActivityOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hyz != null && hyz.equals("true")) {
                    Toast.makeText(StateSettingActivity.this, "您已经有怀孕中状态了哦，当前状态不可选择哦！", Toast.LENGTH_SHORT).show();
                } else if (byz != null && byz.equals("true")) {
                    Toast.makeText(StateSettingActivity.this, "您已经有备孕中状态了哦，当前状态不可选择哦！", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(StateSettingActivity.this, ExpectedConfinementActivity.class);
                    intent.putExtra("hyz", hyz);
                    intent.putExtra("isTianJia", isTianJia);
                    startActivity(intent);
                }
            }
        });

        mStateSettingActivityTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hyz != null && hyz.equals("true")) {
                    Toast.makeText(StateSettingActivity.this, "您已经有怀孕中状态了哦，当前状态不可选择哦！", Toast.LENGTH_SHORT).show();
                } else if (byz != null && byz.equals("true")) {
                    Toast.makeText(StateSettingActivity.this, "您已经有备孕中状态了哦，当前状态不可选择哦！", Toast.LENGTH_SHORT).show();
                } else if (byz == null || hyz == null || byz.equals("false") || hyz.equals("false")) {
                    if (isTianJia.equals("1")) {//1为添加宝宝，其它为修改宝宝
                        if (SpUtil.getBooleanValue(StateSettingActivity.this, MyContant.ISLOGIN, true)) {
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("itfaceId", "132");
                            hashMap.put("status", "0");
                            hashMap.put("token", SPUtils.get(StateSettingActivity.this, "token", ""));
                            hashMap.put("relationship", "妈妈");
                            JSONObject jsonObject = new JSONObject(hashMap);

                            OkGo.post(UrlContent.URL).tag(this)
                                    .upJson(jsonObject.toString())
                                    .connTimeOut(10_000)
                                    .execute(new CustomCallBackNoLoading<BabyMessageAddBeans>(StateSettingActivity.this) {
                                        @Override
                                        public void onSuccess(BabyMessageAddBeans mBabyMessageAddBeans, Call call, Response response) {
                                            if (mBabyMessageAddBeans.getResultCode() == 1) {
//                                                //通知HomeFragmentGridView刷新
//                                                MessageEvent messageEvent = new MessageEvent();
//                                                messageEvent.setFinish("1");
//                                                EventBus.getDefault().post(messageEvent);
                                                if (mBabyMessageAddBeans.getData().getGetIntegral() != 0) {
                                                    Toast toast = Toast.makeText(StateSettingActivity.this, "添加宝宝成功积分+" + mBabyMessageAddBeans.getData().getGetIntegral(), Toast.LENGTH_SHORT);
                                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                                    toast.show();
                                                }
                                                startActivity(new Intent(StateSettingActivity.this, MainActivity.class));
                                                finish();
                                            } else {
                                                App.ErrorToken(mBabyMessageAddBeans.getResultCode(), mBabyMessageAddBeans.getMsg());
                                            }
                                        }
                                    });
                        } else {
                            SPUtils.put(StateSettingActivity.this, "w_BabyState", "0");
                            SpUtil.setBooleanValue(StateSettingActivity.this, MyContant.W_SETTING, true);
                            SpUtil.setBooleanValue(StateSettingActivity.this, MyContant.W_TIAOZHUAN, true);
                            startActivity(new Intent(StateSettingActivity.this, MainActivity.class));
                            //切换过宝宝后通知工具栏修改为其它状态下的值
//                            SPUtils.put(StateSettingActivity.this, "GJSet", "");
                            finish();

                        }
                    } else if (isTianJia.equals("0")) {
                        if (SpUtil.getBooleanValue(StateSettingActivity.this, MyContant.ISLOGIN, true)) {
                            HashMap<String, Object> hashMap = new HashMap<String, Object>();
                            hashMap.put("itfaceId", "074");
                            hashMap.put("token", SPUtils.get(StateSettingActivity.this, "token", ""));
                            hashMap.put("id", SPUtils.get(StateSettingActivity.this, "babyId", ""));
                            hashMap.put("status", "0");
                            JSONObject jsonObject = new JSONObject(hashMap);

                            OkGo.post(UrlContent.URL).tag(this)
                                    .upJson(jsonObject.toString())
                                    .connTimeOut(10_000)
                                    .execute(new CustomCallBackNoLoading<ExpectedConfinementBeans>(StateSettingActivity.this) {
                                        @Override
                                        public void onSuccess(ExpectedConfinementBeans mExpectedConfinementBeans, Call call, Response response) {
                                            if (mExpectedConfinementBeans.getResultCode() == 1) {
                                                Toast toast = Toast.makeText(StateSettingActivity.this, "设置成功", Toast.LENGTH_SHORT);
                                                toast.setGravity(Gravity.CENTER, 0, 0);
                                                toast.show();
                                                SpUtil.setBooleanValue(StateSettingActivity.this, MyContant.SETTING, true);
                                                SpUtil.setBooleanValue(StateSettingActivity.this, MyContant.TIAOZHUAN, true);

                                                SPUtils.put(StateSettingActivity.this, "BabyState", "0");
                                                startActivity(new Intent(StateSettingActivity.this, MainActivity.class));
                                                if (HomeFragment.mediaPlayer != null) {
                                                    HomeFragment.mediaPlayer.stop();
                                                    HomeFragment.isPlay = true;
                                                    HomeFragment.isPlayStart = false;
                                                }
                                            } else {
                                                App.ErrorToken(mExpectedConfinementBeans.getResultCode(), mExpectedConfinementBeans.getMsg());
                                            }
                                        }
                                    });
                            finish();
                        } else {
                            SPUtils.put(StateSettingActivity.this, "w_BabyState", "0");
                            SpUtil.setBooleanValue(StateSettingActivity.this, MyContant.W_SETTING, true);
                            SpUtil.setBooleanValue(StateSettingActivity.this, MyContant.W_TIAOZHUAN, true);
                            startActivity(new Intent(StateSettingActivity.this, MainActivity.class));
                            //切换过宝宝后通知工具栏修改为其它状态下的值
//                            SPUtils.put(StateSettingActivity.this, "GJSet", "");
                            finish();
                        }
                    }
                }
            }
        });
        mStateSettingActivityThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StateSettingActivity.this, BabyMessageActivity.class);
                intent.putExtra("isTianJia", isTianJia);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(logoutReceiver);
    }

    public void StateSettingActivity_Bank(View view) {
        finish();
    }

    private class LogoutReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.xcf.logout")) {
                String logoutFlag = intent.getStringExtra("logout");
                Log.i("TAG", "获取的值-->" + logoutFlag);
                if (logoutFlag.equals("1")) {
                    finish();
                }
            }
        }
    }
}
