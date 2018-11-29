package com.wanquan.mlmmx.mlmm;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wanquan.mlmmx.mlmm.activity.LoginActivity;
import com.wanquan.mlmmx.mlmm.beans.APPIntegralBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeGridViewNewBeans;
import com.wanquan.mlmmx.mlmm.beans.LoginBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import org.json.JSONObject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by xcfchangfeng on 2017/3/24.
 */

public class App extends MultiDexApplication {
    private static App instance;//全局实例
    //Voice
    private static Application mApplication = null;
    //自定义提示框
    public static View dialog_textview = null;
    public static View dialog_imageview = null;
    public static Animation dialog_animation = null;
    //qcl用来在主线程中刷新ui
    private static Handler mHandler;
    public static MediaPlayer mediaPlayer2;
    public static Timer timer;
    public static int time;

    public static IWXAPI api;
    private static final String APP_ID = "";
    public static List<Track> mList;//喜马拉雅播放列表数据
    public static List<Track> mList2;
    //首页工具栏
    public static List<HomeGridViewNewBeans.DataBean> mList5 = new ArrayList<>();
    private Context context;

    //高斯模糊
    public static void runOnUIThread(Runnable r) {
        App.getMainHandler().post(r);
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    public static int H, W;

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
                hashMap.put("username", SPUtils.get(App.getContext(), "loginName", ""));
                hashMap.put("password", SPUtils.get(App.getContext(), "loginPassWord", ""));
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
    public void onCreate() {
        super.onCreate();
        //友盟
        /**
         * 初始化common库
         * 参数1:上下文，必须的参数，不能为空
         * 参数2:友盟 app key，非必须参数，如果Manifest文件中已配置app key，该参数可以传空，则使用Manifest中配置的app key，否则该参数必须传入
         * 参数3:友盟 channel，非必须参数，如果Manifest文件中已配置channel，该参数可以传空，则使用Manifest中配置的channel，否则该参数必须传入，channel命名请详见channel渠道命名规范
         * 参数4:设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机
         * 参数5:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空
         */
        //如果AndroidManifest.xml清单配置中没有设置appkey和channel，则可以在这里设置
//                UMConfigure.init(this, "58edcfeb310c93091c000be2", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "1fe6a20054bcef865eeb0991ee84525b");
//        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE,"");
//        UMConfigure.setLogEnabled(true);
//        UMConfigure.init(this, "5971c0cb07fe6509ac001514", "Ffgxy", UMConfigure.DEVICE_TYPE_PHONE, null);


        context = getApplicationContext();

        x.Ext.init(this);

         /* Bugly SDK初始化
        * 参数1：上下文对象
        * 参数2：APPID，平台注册时得到,注意替换成你的appId
        * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
        */
//        CrashReport.initCrashReport(getApplicationContext(), "54b79c7202", false);
//        CrashReport.initCrashReport(getApplicationContext(), "3c3aba07-5b18-442f-be5a-cece5dd51477", false);
        getScreen(this);

        initImageLoader(getApplicationContext());
        //Voice
        if (mApplication == null) {
            mApplication = this;
            super.onCreate();
        }

        api = WXAPIFactory.createWXAPI(this, this.APP_ID, false);
        api.registerApp(this.APP_ID);

        SPUtils.put(this, "StartTime", "false");//设置是否启动了该定时功能
        SPUtils.put(this, "isStart", "2");//判断播放器是否已经启动了（1启动2没启动）
        MultiDex.install(this);
        //初始化handler
        mHandler = new Handler();
        //为全局变量赋值
        instance = this;
        //极光初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //喜马拉雅
        CommonRequest mXimalaya = CommonRequest.getInstanse();
        mXimalaya.setAppkey("4d88cbe8f4167101a8d2c237cf9948d7");
        mXimalaya.setPackid("com.wanquan.mlmmx.mlmm");
        mXimalaya.init(this, "54641dfd1bfff351c96026ebc5643950");



        mediaPlayer2 = new MediaPlayer();

        if (SpUtil.getBooleanValue(App.getContext(), MyContant.ISLOGIN, true)) {
            task.run();
        }

    }

    public void getScreen(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        H = dm.heightPixels;
        W = dm.widthPixels;
    }

    public static void ErrorToken(int code, String msg) {

        Log.e("xcf_error", code + msg);

        if (null == SPUtils.get(App.getContext(), "loginName", "")) {
            SpUtil.setBooleanValue(App.getContext(), MyContant.ISLOGIN, false);
            SPUtils.put(App.getContext(), "userid", "");
            Intent intent = new Intent(App.getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//不然会报错
            App.getContext().startActivity(intent);
        }
        if ("".equals(SPUtils.get(App.getContext(), "loginName", ""))) {
            SpUtil.setBooleanValue(App.getContext(), MyContant.ISLOGIN, false);
            SPUtils.put(App.getContext(), "userid", "");
            Intent intent = new Intent(App.getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//不然会报错
            App.getContext().startActivity(intent);
        }
        if (null == SPUtils.get(App.getContext(), "loginPassWord", "")) {
            SpUtil.setBooleanValue(App.getContext(), MyContant.ISLOGIN, false);
            SPUtils.put(App.getContext(), "userid", "");
            Intent intent = new Intent(App.getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//不然会报错
            App.getContext().startActivity(intent);
        }
        if ("".equals(SPUtils.get(App.getContext(), "loginPassWord", ""))) {
            SpUtil.setBooleanValue(App.getContext(), MyContant.ISLOGIN, false);
            SPUtils.put(App.getContext(), "userid", "");
            Intent intent = new Intent(App.getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//不然会报错
            App.getContext().startActivity(intent);
        }
        if (-21 == code) {
            initToken();
//            SpUtil.setBooleanValue(App.getContext(), MyContant.ISLOGIN, false);
//            SPUtils.put(getContext(), "userid", "");
//            Intent intent = new Intent(App.getContext(), LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );//不然会报错
//            App.getContext().startActivity(intent);
        } else if (-20 == code) {
            SpUtil.setBooleanValue(App.getContext(), MyContant.ISLOGIN, false);
            SPUtils.put(App.getContext(), "userid", "");
            Intent intent = new Intent(App.getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//不然会报错
            App.getContext().startActivity(intent);
        } else {
            Log.e("xcf_app", code + "--------" + msg);
            Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    private static void initToken() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "001");
        hashMap.put("username", SPUtils.get(App.getContext(), "loginName", ""));
        hashMap.put("password", SPUtils.get(App.getContext(), "loginPassWord", ""));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(getContext())
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<LoginBeans>() {
                    @Override
                    public void onSuccess(LoginBeans loginBeans, Call call, Response response) {
                        if (loginBeans.getResultCode() == 1) {
                            SPUtils.put(App.getContext(), "token", loginBeans.getData().getToken());
                        } else if (loginBeans.getResultCode() == -4) {//密码被修改后，重新登录

                            SpUtil.setBooleanValue(App.getContext(), MyContant.ISLOGIN, false);
                            SPUtils.put(App.getContext(), "userid_s", String.valueOf(SPUtils.get(App.getContext(), "userid", "")));//判断再次登录时是否显示之前宝宝
                            SPUtils.put(App.getContext(), "userid", "");

                            Set<String> mSet = new HashSet<String>();
                            mSet.add("0");
                            JPushInterface.setAliasAndTags(App.getContext(), "0", mSet, new TagAliasCallback() {
                                @Override
                                public void gotResult(int i, String s, Set<String> set) {
                                    Log.e("mars", "gotResult: code=" + i + "alias=" + s + "tags=" + set.toArray()[0]);
                                }
                            });

                            Intent intent = new Intent(App.getContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//不然会报错
                            App.getContext().startActivity(intent);
                        }
                    }
                });
    }

    //积分操作接口
    public static void integral(String action, String remark, final String str) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "090");
        hashMap.put("token", SPUtils.get(getContext(), "token", ""));
        hashMap.put("action", action);
        hashMap.put("remark", remark);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(getContext())
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<APPIntegralBeans>() {
                    @Override
                    public void onSuccess(APPIntegralBeans mAPPIntegralBeans, Call call, Response response) {
                        if (mAPPIntegralBeans.getResultCode() == 1) {
                            if (mAPPIntegralBeans.getExtra() != 0) {
                                Toast.makeText(getContext(), str + "+" + mAPPIntegralBeans.getExtra(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), mAPPIntegralBeans.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCacheSize(2 * 1024 * 1024) //缓存到内存的最大数据
                .memoryCacheSize(50 * 1024 * 1024) //设置内存缓存的大小
                .diskCacheFileCount(200)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    //Voice
    public static Context getContext() {
        return mApplication.getApplicationContext();
    }

    List<Activity> activityList = new ArrayList<>();

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    //遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }

    /**
     * 获取全局变量
     *
     * @return
     */
    public static App getInstance() {
        return instance;
    }

}
