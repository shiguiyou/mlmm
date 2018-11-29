package com.wanquan.mlmmx.mlmm.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.BuildConfig;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.MyAdapter;
import com.wanquan.mlmmx.mlmm.beans.HomeListViewBeans;
import com.wanquan.mlmmx.mlmm.beans.LoginBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.UpDataInfoBeans;
import com.wanquan.mlmmx.mlmm.fragment.CircleFragment;
import com.wanquan.mlmmx.mlmm.fragment.EquipmentFragment;
import com.wanquan.mlmmx.mlmm.fragment.HomeFragment;
import com.wanquan.mlmmx.mlmm.fragment.MineFragment;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
//import com.wanquan.mlmmx.mlmm.updata.AppUpdateService;
import com.wanquan.mlmmx.mlmm.updata.UpdateManager2;
import com.wanquan.mlmmx.mlmm.utils.JSONUtils;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.NoScrollViewPager;
import com.wanquan.mlmmx.mlmm.view.SonnyJackDragView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：主界面
 * 作者：薛昌峰
 * 时间：2017.09.25
 */
public class MainActivity extends BaseActivity {
    private static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 110;
    public static Bitmap bitmap_;//勿动
    HomeFragment mHomeFragment;
    CircleFragment mCircleFragment;
    EquipmentFragment mEquipmentFragment;
    MineFragment mMineFragment;

    List<Fragment> mList;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    MyAdapter mAdapter;

    private NoScrollViewPager mMainActivityViewPager;
    private LinearLayout mMainButton;
    private RadioGroup mMainActivityRadioGroup;
    private RadioButton mMainActivityRadioButton1;
    private RadioButton mMainActivityRadioButton2;
    private RadioButton mMainActivityRadioButton3;
    private RadioButton mMainActivityRadioButton4;


    LinearLayout mMainActivity_lll1;
    LinearLayout mMainActivity_lll2;
    LinearLayout mMainActivity_lll3;
    LinearLayout mMainActivity_lll4;

    ImageView mMainActivity_ImageView1;
    ImageView mMainActivity_ImageView2;
    ImageView mMainActivity_ImageView3;
    ImageView mMainActivity_ImageView4;

    TextView mMainActivity_TextView1;
    TextView mMainActivity_TextView2;
    TextView mMainActivity_TextView3;
    TextView mMainActivity_TextView4;

    //    private LogoutReceiver logoutReceiver;
//    public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;//定义屏蔽参数
//
//    public void onAttachedToWindow() {
//        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
//        super.onAttachedToWindow();
//    }
    private boolean isForce;
    private UpdateManager2 updateManager;
    private String downLoad_url = "http://api.env365.cn/app-arm-release.apk";
    private int versionCode;
    private int versionCodes = 0;
    private int checkedIds;
    private Context mContext;
    //    private int circleflag = 0;
    private SonnyJackDragView mSonnyJackDragView;
    private ImageView imageView;


    @Override
    protected void onResume() {
        super.onResume();
        if (SpUtil.getBooleanValue(MainActivity.this, MyContant.ISLOGIN, true)) {
            task.run();
        }
    }

    //更新token
    private boolean run = true;
    private final Handler handler = new Handler();
    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (run) {
                handler.postDelayed(this, 3600000);
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("itfaceId", "001");
                hashMap.put("username", SPUtils.get(MainActivity.this, "loginName", ""));
                hashMap.put("password", SPUtils.get(MainActivity.this, "loginPassWord", ""));
                JSONObject jsonObject = new JSONObject(hashMap);

                OkGo.post(UrlContent.URL).tag(this)
                        .upJson(jsonObject.toString())
                        .connTimeOut(10_000)
                        .execute(new CustomCallBackNoLoading<LoginBeans>() {
                            @Override
                            public void onSuccess(LoginBeans loginBeans, Call call, Response response) {
                                if (loginBeans.getResultCode() == 1) {
                                    SPUtils.put(App.getContext(), "token", loginBeans.getData().getToken());
                                }
                            }
                        });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //注册广播
//        registerReceiver(mHomeKeyEventReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        //注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(MainActivity.this, R.color.tops);

        initCode();//判断版本信息是否相同

        imageView = new ImageView(this);
        mSonnyJackDragView = new SonnyJackDragView.Builder()
                .setActivity(this)
                .setDefaultLeft(850)
                .setDefaultTop(1300)
                .setNeedNearEdge(false)
                .setSize(150)
                .setView(imageView)
                .build();

//        circleflag = getIntent().getIntExtra("circleflag", 0);
//        Log.e("ffffdddd1", String.valueOf(checkedIds));

        //检测更新
//        initUpdate();

        if (SpUtil.getBooleanValue(MainActivity.this, MyContant.ISLOGIN, true)) {
            if (!SpUtil.getBooleanValue(MainActivity.this, MyContant.TIAOZHUAN, false)) {
                startActivity(new Intent(MainActivity.this, StateSettingActivity.class));
                finish();
            }
        } else {
            if (!SpUtil.getBooleanValue(MainActivity.this, MyContant.W_TIAOZHUAN, false)) {
                startActivity(new Intent(MainActivity.this, StateSettingActivity.class));
                finish();
            }
        }
        initData();
        initListeners();

//        Intent intent = new Intent();
//        intent.setAction("ACTION_SHOW_NAVBAR");
//        intent.putExtra("cmd", "hide");
//        sendBroadcast(intent, null);

        mMainActivityViewPager.setOffscreenPageLimit(2);
//        logoutReceiver = new LogoutReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("com.xcf.logout");
//        registerReceiver(logoutReceiver, filter);
    }


    @Override
    protected int setLayoutID() {
        return R.layout.activity_main;
    }


    @Override
    public void initView() throws Exception {
        mMainActivityViewPager = (NoScrollViewPager) findViewById(R.id.MainActivity_ViewPager);
        mMainButton = (LinearLayout) findViewById(R.id.Main_Button);
        mMainActivityRadioGroup = (RadioGroup) findViewById(R.id.MainActivity_RadioGroup);
        mMainActivityRadioButton1 = (RadioButton) findViewById(R.id.MainActivity_RadioButton1);
        mMainActivityRadioButton2 = (RadioButton) findViewById(R.id.MainActivity_RadioButton2);
        mMainActivityRadioButton3 = (RadioButton) findViewById(R.id.MainActivity_RadioButton3);
        mMainActivityRadioButton4 = (RadioButton) findViewById(R.id.MainActivity_RadioButton4);


        mMainActivity_lll1 = (LinearLayout) findViewById(R.id.MainActivity_lll1);
        mMainActivity_lll2 = (LinearLayout) findViewById(R.id.MainActivity_lll2);
        mMainActivity_lll3 = (LinearLayout) findViewById(R.id.MainActivity_lll3);
        mMainActivity_lll4 = (LinearLayout) findViewById(R.id.MainActivity_lll4);

        mMainActivity_ImageView1 = (ImageView) findViewById(R.id.MainActivity_ImageView1);
        mMainActivity_ImageView2 = (ImageView) findViewById(R.id.MainActivity_ImageView2);
        mMainActivity_ImageView3 = (ImageView) findViewById(R.id.MainActivity_ImageView3);
        mMainActivity_ImageView4 = (ImageView) findViewById(R.id.MainActivity_ImageView4);

        mMainActivity_TextView1 = (TextView) findViewById(R.id.MainActivity_TextView1);
        mMainActivity_TextView2 = (TextView) findViewById(R.id.MainActivity_TextView2);
        mMainActivity_TextView3 = (TextView) findViewById(R.id.MainActivity_TextView3);
        mMainActivity_TextView4 = (TextView) findViewById(R.id.MainActivity_TextView4);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        resetViewPager(event.isFinish());
        if (event.isShowBank()) {
            mMainButton.setVisibility(View.GONE);
        } else {
            mMainButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取当前本地apk的版本
     *
     * @param mContext
     * @return
     */
    public int getVersionCode(Context mContext) {
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCodes = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCodes;
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

    private void initCode() {
        HashMap<String, Object> hashMap = new HashMap<>();
        JSONObject jsonObject = new JSONObject(hashMap);
        OkGo.post("http://api.env365.cn/update.json").tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10000)
                .execute(new CustomCallBackNoLoading<UpDataInfoBeans>(this) {
                    @Override
                    public void onSuccess(UpDataInfoBeans upDataInfoBeans, Call call, Response response) {
                        Log.e("ddddddd", "新版本" + upDataInfoBeans.getUpdate().getVersionCode() + "当前版本" + getVersionCode(MainActivity.this) + "版本名称" + upDataInfoBeans.getUpdate().getVersionName());
                        versionCode = Integer.parseInt(upDataInfoBeans.getUpdate().getVersionCode());
                        if (versionCode > versionCodes) {
//                            Log.e("ddddddd", "到了");
//                            //监测更新
//                            updateManager = new UpdateManager2(ReceiverMainActivity.this, upDataInfoBeans.getUpdate().getVersionName());
//                            isForce = true;
//                            updateManager.setForce(isForce);
//                            updateManager.showNoticeUpdateDialog(downLoad_url);
//
//                            SpUtil.setBooleanValue(ReceiverMainActivity.this, MyContant.ISLOGIN, false);
//                            SPUtils.put(ReceiverMainActivity.this, "userid", "");
                            if (isAppInstalled(MainActivity.this)) {
                                Intent intent2 = new Intent(Intent.ACTION_VIEW);
                                Uri uri = Uri.parse("market://details?id=" + "com.wanquan.mlmmx.mlmm");//app包名
                                intent2.setData(uri);
                                intent2.setPackage("com.tencent.android.qqdownloader");//应用市场包名
                                startActivity(intent2);
                            } else {
                                Toast.makeText(MainActivity.this, "请先下载应用宝软件", Toast.LENGTH_SHORT).show();
                            }
                        } else if (versionCode == versionCodes) {
                            Log.e("ddddddd", "到了2");
                            if (UpdateManager2.mDownloadDialog != null) {
                                UpdateManager2.mDownloadDialog.dismiss();
                            }
                        }
                    }
                });
    }

    /**
     * 监听是否点击了home键将客户端推到后台
     */
    private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {
        String SYSTEM_REASON = "reason";
        String SYSTEM_HOME_KEY = "homekey";
        String SYSTEM_HOME_KEY_LONG = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                    //表示按了home键,程序到了后台
                    //通知设置状态页Finish
                    Intent logoutIntent = new Intent();
                    logoutIntent.setAction("com.xcf.logout");
                    logoutIntent.putExtra("logout", "1");
                    MainActivity.this.sendBroadcast(logoutIntent);
                    finish();
                } else if (TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)) {
                    //表示长按home键,显示最近使用的程序列表
                }
            }
        }
    };

    private void initData() {
        mList = new ArrayList<>();
        mHomeFragment = new HomeFragment();
        mCircleFragment = new CircleFragment();
        mEquipmentFragment = new EquipmentFragment();
        mMineFragment = new MineFragment();

        mList.add(mHomeFragment);
        mList.add(mCircleFragment);
        mList.add(mEquipmentFragment);
        mList.add(mMineFragment);

        //初始化适配器
        mFragmentManager = getSupportFragmentManager();
        mAdapter = new MyAdapter(mFragmentManager, mList);
        mMainActivityViewPager.setAdapter(mAdapter);
    }

    private void initListeners() {
        mMainActivity_lll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMainActivity_ImageView1.setBackground(getResources().getDrawable(R.mipmap.homechecked));
                mMainActivity_ImageView2.setBackground(getResources().getDrawable(R.mipmap.sharedevice));
                mMainActivity_ImageView3.setBackground(getResources().getDrawable(R.mipmap.device));
                mMainActivity_ImageView4.setBackground(getResources().getDrawable(R.mipmap.me));

                mMainActivity_TextView1.setTextColor(getResources().getColor(R.color.tops));
                mMainActivity_TextView2.setTextColor(getResources().getColor(R.color.text_white_c));
                mMainActivity_TextView3.setTextColor(getResources().getColor(R.color.text_white_c));
                mMainActivity_TextView4.setTextColor(getResources().getColor(R.color.text_white_c));

                imageView.setVisibility(View.GONE);
                mMainActivityViewPager.setCurrentItem(0);
            }
        });
        mMainActivity_lll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SpUtil.getBooleanValue(MainActivity.this, MyContant.ISLOGIN, true)) {

                    mMainActivity_ImageView1.setBackground(getResources().getDrawable(R.mipmap.home));
                    mMainActivity_ImageView2.setBackground(getResources().getDrawable(R.mipmap.sharechecked));
                    mMainActivity_ImageView3.setBackground(getResources().getDrawable(R.mipmap.device));
                    mMainActivity_ImageView4.setBackground(getResources().getDrawable(R.mipmap.me));

                    mMainActivity_TextView1.setTextColor(getResources().getColor(R.color.text_white_c));
                    mMainActivity_TextView2.setTextColor(getResources().getColor(R.color.tops));
                    mMainActivity_TextView3.setTextColor(getResources().getColor(R.color.text_white_c));
                    mMainActivity_TextView4.setTextColor(getResources().getColor(R.color.text_white_c));

                    initpopup();
                    imageView.setVisibility(View.VISIBLE);
                    mMainActivityViewPager.setCurrentItem(1);
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        });
        mMainActivity_lll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMainActivity_ImageView1.setBackground(getResources().getDrawable(R.mipmap.home));
                mMainActivity_ImageView2.setBackground(getResources().getDrawable(R.mipmap.sharedevice));
                mMainActivity_ImageView3.setBackground(getResources().getDrawable(R.mipmap.deviceselected));
                mMainActivity_ImageView4.setBackground(getResources().getDrawable(R.mipmap.me));

                mMainActivity_TextView1.setTextColor(getResources().getColor(R.color.text_white_c));
                mMainActivity_TextView2.setTextColor(getResources().getColor(R.color.text_white_c));
                mMainActivity_TextView3.setTextColor(getResources().getColor(R.color.tops));
                mMainActivity_TextView4.setTextColor(getResources().getColor(R.color.text_white_c));

                imageView.setVisibility(View.GONE);
                mMainActivityViewPager.setCurrentItem(2);
            }
        });
        mMainActivity_lll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMainActivity_ImageView1.setBackground(getResources().getDrawable(R.mipmap.home));
                mMainActivity_ImageView2.setBackground(getResources().getDrawable(R.mipmap.sharedevice));
                mMainActivity_ImageView3.setBackground(getResources().getDrawable(R.mipmap.device));
                mMainActivity_ImageView4.setBackground(getResources().getDrawable(R.mipmap.mechecked));

                mMainActivity_TextView1.setTextColor(getResources().getColor(R.color.text_white_c));
                mMainActivity_TextView2.setTextColor(getResources().getColor(R.color.text_white_c));
                mMainActivity_TextView3.setTextColor(getResources().getColor(R.color.text_white_c));
                mMainActivity_TextView4.setTextColor(getResources().getColor(R.color.tops));

                imageView.setVisibility(View.GONE);
                mMainActivityViewPager.setCurrentItem(3);
            }
        });
//        mMainActivityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                resetViewPager(checkedId);
//            }
//        });
//        //滑动ViewPage的时候及时修改底部导航栏对应的图标
//        mMainActivityViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                //根据当前位置设置默认选中单选按钮
//                resetRadioButton(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    public void resetViewPager(int checkedId) {
        if (checkedId == 1) {
            imageView.setVisibility(View.GONE);
            mMainActivityViewPager.setCurrentItem(0);
        } else if (checkedId == 2) {
            initpopup();
            imageView.setVisibility(View.VISIBLE);
            mMainActivityViewPager.setCurrentItem(1);
            if (SpUtil.getBooleanValue(MainActivity.this, MyContant.ISLOGIN, true)) {
            } else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        } else if (checkedId == 3) {
            imageView.setVisibility(View.GONE);
            mMainActivityViewPager.setCurrentItem(2);
        } else if (checkedId == 4) {
            imageView.setVisibility(View.GONE);
            mMainActivityViewPager.setCurrentItem(3);
        }
//        Log.e("dddddsize", String.valueOf(checkedId));
//        switch (checkedId) {
//            case R.id.MainActivity_lll1:
//                imageView.setVisibility(View.GONE);
//                mMainActivityViewPager.setCurrentItem(0);
//                mMainActivity_ImageView1.setBackground(getResources().getDrawable(R.mipmap.homechecked));
//                break;
//            case R.id.MainActivity_RadioButton2:
//                initpopup();
//                imageView.setVisibility(View.VISIBLE);
//                mMainActivityViewPager.setCurrentItem(1);
//                if (SpUtil.getBooleanValue(MainActivity.this, MyContant.ISLOGIN, true)) {
//                } else {
//                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                }
//                mMainActivity_ImageView2.setBackground(getResources().getDrawable(R.mipmap.sharechecked));
//
//                break;
//            case R.id.MainActivity_RadioButton3:
//                imageView.setVisibility(View.GONE);
//                mMainActivityViewPager.setCurrentItem(2);
//                mMainActivity_ImageView3.setBackground(getResources().getDrawable(R.mipmap.deviceselected));
//
//                break;
//            case R.id.MainActivity_RadioButton4:
//                imageView.setVisibility(View.GONE);
//                mMainActivityViewPager.setCurrentItem(3);
//                checkedIds = checkedId;
//                mMainActivity_ImageView4.setBackground(getResources().getDrawable(R.mipmap.mechecked));
//
//                break;
//        }
    }

    private void initpopup() {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.fatie);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectCircleActivity.class);
                startActivityForResult(intent, 2);
            }
        });
    }

    private void resetRadioButton(int position) {
        //获取position位置处对于的单选按钮
        RadioButton radioButton = (RadioButton) mMainActivityRadioGroup.getChildAt(position);
        //设置当前单选按钮默认选中
        radioButton.setChecked(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
//        unregisterReceiver(logoutReceiver);
    }


    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(MainActivity.this, "再按一次退出美丽妈妈！", Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 1500);
        } else {
            //通知设置状态页Finish
            Intent logoutIntent = new Intent();
            logoutIntent.setAction("com.xcf.logout");
            logoutIntent.putExtra("logout", "1");
            MainActivity.this.sendBroadcast(logoutIntent);
            finish();
//            System.exit(0);
        }
    }

    //监测更新
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == UpdateManager2.REQ_PERMISSION_CODE) {
            boolean isAllow = true;
            for (int res : grantResults) {
                if (res != PackageManager.PERMISSION_GRANTED) {
                    isAllow = false;
                    break;
                }
            }
            if (isAllow) {
                updateManager.showDownloadDialog(downLoad_url);
            } else {
                if (isForce) {
                    updateManager.goToSetPermission(this, "在设置-应用-权限中打开SD卡存储权限，以保证功能的正常使用", UpdateManager2.REQ_PERMISSION_CODE);
                } else {
                    // do nothing 非强制更新
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UpdateManager2.REQ_PERMISSION_CODE || requestCode == UpdateManager2.REQ_INSTALL_CODE) {
            if (versionCode > versionCodes) {
                updateManager.showNoticeUpdateDialog(downLoad_url);
            }
        }

    }
}
