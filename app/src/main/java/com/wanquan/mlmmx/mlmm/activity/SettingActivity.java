package com.wanquan.mlmmx.mlmm.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.HomeGridViewNewAdapter;
import com.wanquan.mlmmx.mlmm.beans.FetalMovementStopBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeGridViewNewBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.SettingBabyMessageBeans;
import com.wanquan.mlmmx.mlmm.fragment.HomeFragment;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：设置
 * 作者：薛昌峰
 * 时间：2017.09.27
 */
public class SettingActivity extends BaseActivity {
    private RelativeLayout mSettingOne;
    private RelativeLayout mSettingTwo;
    private RelativeLayout mSettingThree;
    private TextView mSettingNumber;
    //    private LogoutReceiver logoutReceiver;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(SettingActivity.this, R.color.black);

        initData();
        initListeners();

//        logoutReceiver = new LogoutReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("com.xcf.logout");
//        registerReceiver(logoutReceiver, filter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() throws Exception {
        mSettingOne = (RelativeLayout) findViewById(R.id.Setting_One);
        mSettingTwo = (RelativeLayout) findViewById(R.id.Setting_Two);
        mSettingThree = (RelativeLayout) findViewById(R.id.Setting_Three);
        mSettingNumber = (TextView) findViewById(R.id.Setting_Number);
    }

    private void initData() {
        context = this;
    }

    private void initListeners() {
        mSettingOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, ModificationActivity.class));
            }
        });
        Log.e("dddddddd", String.valueOf(SPUtils.get(SettingActivity.this, "userid", "")));

        mSettingTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e("login__宝宝id", String.valueOf(SPUtils.get(SettingActivity.this, "babyId", ""))+"xcf");
                final AlertDialog alert;
                alert = new AlertDialog.Builder(SettingActivity.this, R.style.AlertDialogs).create();
                alert.show();
                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.delete_dialogsss);
                TextView cart_delete_title = alert.getWindow().findViewById(R.id.cart_delete_title);
                TextView cart_delete_content = alert.getWindow().findViewById(R.id.cart_delete_content);
                TextView cart_delete_cancle = alert.getWindow().findViewById(R.id.cart_delete_cancle);
                TextView cart_delete_confirm = alert.getWindow().findViewById(R.id.cart_delete_confirm);

                cart_delete_title.setText("提示消息");
                cart_delete_content.setText("点击确定，您的账号就退出咯，您也可以点击取消再想想~");
                cart_delete_cancle.setText("取消");
                cart_delete_confirm.setText("确认");
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
                        if (App.mediaPlayer2 != null) {
                            if (App.mediaPlayer2.isPlaying()) {
                                App.mediaPlayer2.stop();
                            }
                        }
                        if (HomeFragment.mediaPlayer != null) {
                            if (HomeFragment.mediaPlayer.isPlaying()) {
                                HomeFragment.mediaPlayer.release();
                            }
                        }
                        //退出登录后需要把登录后的信息全都赋给登录前的
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("itfaceId", "033");
                        hashMap.put("token", SPUtils.get(SettingActivity.this, "token", ""));
                        if (!"".equals(SPUtils.get(SettingActivity.this, "babyId", ""))) {
                            hashMap.put("id", SPUtils.get(SettingActivity.this, "babyId", ""));
                        }
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<SettingBabyMessageBeans>(SettingActivity.this) {
                                    @Override
                                    public void onSuccess(SettingBabyMessageBeans mSettingBabyMessageBeans, Call call, Response response) {
                                        if (mSettingBabyMessageBeans.getResultCode() == 1) {
                                            //退出登录后把宝宝工具栏的信息赋值给退出后
                                            int num = Integer.valueOf(String.valueOf(SPUtils.get(SettingActivity.this, "BabyState", "")));
                                            //此值用于判断退出后工具栏状态是否显示，当退出状态下切换宝宝了，此值将设为空
                                            SPUtils.put(SettingActivity.this, "GJSet", "true");
                                            initLieBiao(num);

                                            String babyHeadIco = mSettingBabyMessageBeans.getData().getBabyHeadIco();
                                            String babyStatus = String.valueOf(mSettingBabyMessageBeans.getData().getBabyStatus());
                                            String babyHeight = String.valueOf(mSettingBabyMessageBeans.getData().getBabyHeight());
                                            String babyNickname = mSettingBabyMessageBeans.getData().getBabyNickname();
                                            String babySex = String.valueOf(mSettingBabyMessageBeans.getData().getBabySex());
                                            String babyWeight = String.valueOf(mSettingBabyMessageBeans.getData().getBabyWeight());
                                            String childBirthDate = mSettingBabyMessageBeans.getData().getChildBirthDate();
                                            String preChildDate = mSettingBabyMessageBeans.getData().getPreChildDate();
                                            String sort = mSettingBabyMessageBeans.getData().getRelationship();

                                            if (babyStatus.equals("1")) {
                                                SPUtils.put(SettingActivity.this, "w_timeh", preChildDate);
                                                SPUtils.put(SettingActivity.this, "w_BabyState", babyStatus);
                                                SPUtils.put(SettingActivity.this, "w_sort", sort);

                                            } else if (babyStatus.equals("2")) {
//                                        Log.e("rrrrr到了状态复制界面","1111");
//                                        Log.e("rrrrr到了状态复制界面",babyStatus);
                                                SPUtils.put(SettingActivity.this, "w_timey", childBirthDate);
                                                SPUtils.put(SettingActivity.this, "w_BabyState", babyStatus);
                                                SPUtils.put(SettingActivity.this, "w_setbabyname", babyNickname);
                                                SPUtils.put(SettingActivity.this, "w_babytz", babyWeight);
                                                SPUtils.put(SettingActivity.this, "w_babysg", babyHeight);
                                                SPUtils.put(SettingActivity.this, "w_sex", babySex);
                                                SPUtils.put(SettingActivity.this, "w_sort", sort);

                                                if (SPUtils.get(SettingActivity.this, "icon_y", "").equals("true")) {
                                                    SPUtils.put(SettingActivity.this, "w_img_y", babyHeadIco);
                                                }
                                            } else if (babyStatus.equals("0")) {
                                                SPUtils.put(SettingActivity.this, "w_BabyState", babyStatus);
                                                if (SPUtils.get(SettingActivity.this, "icon_b", "").equals("true")) {
                                                    SPUtils.put(SettingActivity.this, "w_img_b", babyHeadIco);
                                                    SPUtils.put(SettingActivity.this, "w_icon_b", "trues");//把未登录状态设置trues（不让在显示未登录之前上传的图片）
                                                }
                                            }
                                            SPUtils.put(SettingActivity.this, "babyId", String.valueOf(mSettingBabyMessageBeans.getData().getId()));
                                            SpUtil.setBooleanValue(SettingActivity.this, MyContant.ISLOGIN, false);
                                            SPUtils.put(SettingActivity.this, "userid_s", String.valueOf(SPUtils.get(SettingActivity.this, "userid", "")));//判断再次登录时是否显示之前宝宝
                                            SPUtils.put(SettingActivity.this, "userid", "");
                                            SPUtils.put(SettingActivity.this, "token", "");

                                            Set<String> mSet = new HashSet<String>();
                                            mSet.add("0");
                                            JPushInterface.setAliasAndTags(SettingActivity.this, "0", mSet, new TagAliasCallback() {
                                                @Override
                                                public void gotResult(int i, String s, Set<String> set) {
                                                    Log.e("mars", "gotResult: code=" + i + "alias=" + s + "tags=" + set.toArray()[0]);
                                                }
                                            });
                                            //通知圈子页面刷新
                                            MessageEvent messageEvent = new MessageEvent();
//                                            messageEvent.setShuaXin(true);
//                                            messageEvent.setFinish(1);
//                                            EventBus.getDefault().post(messageEvent);
                                            messageEvent.setFinish(4);
                                            EventBus.getDefault().post(messageEvent);
                                            alert.dismiss();
                                            finish();
                                        } else {
                                            App.ErrorToken(mSettingBabyMessageBeans.getResultCode(), mSettingBabyMessageBeans.getMsg());
                                        }
                                    }

                                    @Override
                                    public void onError(Call call, Response response, Exception e) {
                                        super.onError(call, response, e);
                                        Toast.makeText(SettingActivity.this, "服务器连接异常，请稍后重试!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
            }
        });
        mSettingThree.setOnClickListener(new View.OnClickListener() {
            private String packageName;

            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.MAIN");
//                intent.addCategory("android.intent.category.APP_MARKET");
//                PackageManager pm = getPackageManager();
//                List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
//                int size = infos.size();
//                for (int i = 0; i < size; i++) {
//                    ActivityInfo activityInfo = infos.get(i).activityInfo;
//                    packageName = activityInfo.packageName;
//                    Log.e("dsadad", packageName);
//                    //获取应用市场的包名
//                }
//                if ("3.3.9".equals(mSettingNumber)) {
                    if (isAppInstalled(context)) {
                        Intent intent2 = new Intent(Intent.ACTION_VIEW);
                        Uri uri = Uri.parse("market://details?id=" + "com.wanquan.mlmmx.mlmm");//app包名
                        intent2.setData(uri);
                        intent2.setPackage("com.tencent.android.qqdownloader");//应用市场包名
                        startActivity(intent2);
                    } else {
                        Toast.makeText(SettingActivity.this, "请先下载应用宝软件", Toast.LENGTH_SHORT).show();
                    }
//                } else {
//                    Toast.makeText(context, "已是最新版本", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    public static boolean isAppInstalled(Context context) {
        PackageManager pm;
        if ((pm = context.getApplicationContext().getPackageManager()) == null) {
            return false;
        }
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo info : packages) {
            String name = info.packageName.toLowerCase(Locale.ENGLISH);
            if ("com.tencent.android.qqdownloader".equals(name)) { //应用包名 eg:com.sina.weibo
                return true;
            }
        }
        return false;
    }

    private void initLieBiao(int status) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "106");
        hashMap.put("token", SPUtils.get(SettingActivity.this, "token", ""));
        hashMap.put("babyStatus", status);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<HomeGridViewNewBeans>(SettingActivity.this) {
                    @Override
                    public void onSuccess(HomeGridViewNewBeans mHomeGridViewNewBeans, Call call, Response response) {
                        if (mHomeGridViewNewBeans.getResultCode() == 1) {
                            App.mList5.clear();
                            App.mList5.addAll(mHomeGridViewNewBeans.getData());

                            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss5 = new HomeGridViewNewBeans.DataBean();
                            mHomeGridViewNewBeanss5.setId("100");
                            mHomeGridViewNewBeanss5.setName("更多");
                            App.mList5.add(mHomeGridViewNewBeanss5);

                            String GJId = null;
                            for (int i = 0; i < App.mList5.size(); i++) {
                                if (i == 0) {
                                    GJId = App.mList5.get(i).getId();
                                } else {
                                    GJId = GJId + "," + App.mList5.get(i).getId();
                                }
                            }
                            //存储工具类id
                            Log.e("xcf_gJid", GJId);
                            SPUtils.put(SettingActivity.this, "GJId", GJId);

//                            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss = new HomeGridViewNewBeans.DataBean();
//                            mHomeGridViewNewBeanss.setId("100");
//                            mHomeGridViewNewBeanss.setName("更多");
//                            HomeFragment.mList5.add(mHomeGridViewNewBeanss);
//
//                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//                            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//                            mHome_GridView.setLayoutManager(linearLayoutManager);
//
//                            mHomeGridViewNewAdapter = new HomeGridViewNewAdapter(mList5, getContext());
//                            mHome_GridView.setAdapter(mHomeGridViewNewAdapter);
                        } else {
                            Log.e("xcf_log", "106");
                            App.ErrorToken(mHomeGridViewNewBeans.getResultCode(), mHomeGridViewNewBeans.getMsg());
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(logoutReceiver);
    }

    //    private class LogoutReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals("com.xcf.logout")) {
//                String logoutFlag = intent.getStringExtra("logout");
//
//                Log.i("TAG", "获取的值-->" + logoutFlag);
//
//                if (logoutFlag.equals("1")) {
//                    finish();
//                }
//            }
//
//        }
//    }
    public void Setting_Bank(View view) {
        finish();
    }
}
