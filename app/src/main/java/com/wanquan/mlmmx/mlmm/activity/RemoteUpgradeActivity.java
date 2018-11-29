package com.wanquan.mlmmx.mlmm.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.RemoteUpgradeFinishBeans;
import com.wanquan.mlmmx.mlmm.beans.RemoteUpgradeOneBeans;
import com.wanquan.mlmmx.mlmm.beans.RemoteUpgradeTwoBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：远程升级
 * 作者：薛昌峰
 * 时间：2018.09.14
 */
public class RemoteUpgradeActivity extends BaseActivity {
    private ImageView mRemoteUpgradeBank;
    private TextView mRemoteUpgradeTextView1;
    private TextView mRemoteUpgradeTextView2;
    private TextView mRemoteUpgradeTextView3;
    private TextView mRemoteUpgradeTextView4;

    private String extend;
    private int upgradeStatus;
    private String promptMsg;

    private boolean run = true;
    private final Handler handler = new Handler();
    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (run) {
                handler.postDelayed(this, 3000);
                //Finish
                initFinish();
            }
        }
    };
    private String flag;
    private boolean runFlag = true;
//     停止线程：
//     * 1.stop():已过时，这种方法有固有的不安全性，强制停止线程，不论处于什么状态都会停止，就会导致线程的不安全。
//     * 2.run方法结束了，当线程中没有运行的代码时，线程就结束了，意味着任务结束，线程消失。
//    在循环当中设置标志位，通过标志位来完成

    private void initFinish() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "128");
        hashMap.put("token", SPUtils.get(RemoteUpgradeActivity.this, "token", ""));
        hashMap.put("deviceCode", extend);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<RemoteUpgradeFinishBeans>(this) {
                    @Override
                    public void onSuccess(RemoteUpgradeFinishBeans mRemoteUpgradeOneBeans, Call call, Response response) {
                        if (mRemoteUpgradeOneBeans.getResultCode() == 1) {
                            if (mRemoteUpgradeOneBeans.getData().getUpgradeStatus() == 1) {
                                if (runFlag) {
                                    runFlag = false;
                                    Toast.makeText(RemoteUpgradeActivity.this, "恭喜你,升级成功!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        } else {
                            Toast.makeText(RemoteUpgradeActivity.this, "升级失败,请稍后再试!", Toast.LENGTH_SHORT).show();
                            App.ErrorToken(mRemoteUpgradeOneBeans.getResultCode(), mRemoteUpgradeOneBeans.getMsg());
                        }
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(RemoteUpgradeActivity.this, R.color.black);

        extend = getIntent().getStringExtra("extend");
        flag = getIntent().getStringExtra("flag");

//        task.run();
        initData();
        initListeners();

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_remote_upgrade;
    }

    @Override
    public void initView() throws Exception {
        mRemoteUpgradeBank = (ImageView) findViewById(R.id.RemoteUpgrade_Bank);
        mRemoteUpgradeTextView1 = (TextView) findViewById(R.id.RemoteUpgrade_TextView1);
        mRemoteUpgradeTextView2 = (TextView) findViewById(R.id.RemoteUpgrade_TextView2);
        mRemoteUpgradeTextView3 = (TextView) findViewById(R.id.RemoteUpgrade_TextView3);
        mRemoteUpgradeTextView4 = (TextView) findViewById(R.id.RemoteUpgrade_TextView4);
    }

    private void initData() {
        if (flag.equals("-2")) {
            mRemoteUpgradeTextView4.setText("升级中");
            mRemoteUpgradeTextView4.setClickable(false);
        } else if (flag.equals("1")) {
            mRemoteUpgradeTextView4.setText("立即升级");
            mRemoteUpgradeTextView4.setClickable(true);
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "127");
        hashMap.put("token", SPUtils.get(RemoteUpgradeActivity.this, "token", ""));
        hashMap.put("deviceCode", extend);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<RemoteUpgradeOneBeans>(this) {
                    @Override
                    public void onSuccess(RemoteUpgradeOneBeans mRemoteUpgradeOneBeans, Call call, Response response) {
                        if (mRemoteUpgradeOneBeans.getResultCode() == 1) {

                            mRemoteUpgradeTextView1.setText("设备编号：" + mRemoteUpgradeOneBeans.getData().getDeviceCode());

                            promptMsg = mRemoteUpgradeOneBeans.getData().getPromptMsg();
                            mRemoteUpgradeTextView2.setText(promptMsg);

                            mRemoteUpgradeTextView3.setText(mRemoteUpgradeOneBeans.getData().getUpgradeContent());
                            upgradeStatus = mRemoteUpgradeOneBeans.getData().getUpgradeStatus();
                        } else {
                            App.ErrorToken(mRemoteUpgradeOneBeans.getResultCode(), mRemoteUpgradeOneBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
        mRemoteUpgradeBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRemoteUpgradeTextView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (upgradeStatus == 1) {
                    mRemoteUpgradeTextView4.setText("升级中");
                    mRemoteUpgradeTextView4.setClickable(false);

                    initUpdate();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            task.run();
                        }
                    }, 3000);
                } else if (upgradeStatus == 0) {
                    Toast.makeText(RemoteUpgradeActivity.this, promptMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initUpdate() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "126");
        hashMap.put("token", SPUtils.get(RemoteUpgradeActivity.this, "token", ""));
        hashMap.put("deviceCode", extend);
        hashMap.put("isUpgrade", "1");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<RemoteUpgradeTwoBeans>(this) {
                    @Override
                    public void onSuccess(RemoteUpgradeTwoBeans mRemoteUpgradeTwoBeans, Call call, Response response) {
                        if (mRemoteUpgradeTwoBeans.getResultCode() == 1) {
//                            Toast.makeText(RemoteUpgradeActivity.this, "恭喜你,升级成功!", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(RemoteUpgradeActivity.this, "升级失败,请稍后再试!", Toast.LENGTH_SHORT).show();
                            App.ErrorToken(mRemoteUpgradeTwoBeans.getResultCode(), mRemoteUpgradeTwoBeans.getMsg());
                        }
                    }
                });
    }
}
