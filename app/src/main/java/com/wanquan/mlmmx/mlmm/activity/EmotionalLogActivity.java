package com.wanquan.mlmmx.mlmm.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.EmotionalLogAdapter;
import com.wanquan.mlmmx.mlmm.beans.EmotionalLogBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MessagePicturesLayout;
import com.wanquan.mlmmx.mlmm.view.MyListView;
import com.wanquan.mlmmx.mlmm.voice.WriteEmotionalActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.ielse.view.imagewatcher.ImageWatcher;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：情感日志
 * 作者：薛昌峰
 * 时间：2017.11.17
 */

public class EmotionalLogActivity extends BaseActivity implements EmotionalLogAdapter.voideInterface, MessagePicturesLayout.Callback, ImageWatcher.OnPictureLongPressListener {
    private TextView mEmotionalLogTextView;
    private PullToRefreshListView mEmotionalLogListView;
    private LinearLayout mEmotionalLogLl;
    private TextView mEmotionalLogTv;
    private List<EmotionalLogBeans.DataBean> mList = new ArrayList<>();
    private EmotionalLogAdapter mEmotionalLogAdapter;
    private String pathName;
    private static String targetPath;
    private ImageWatcher vImageWatcher;

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.e("gggg2", "ffdg");
//        initData();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        boolean isTranslucentStatus = false;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//            isTranslucentStatus = true;
//        }
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(EmotionalLogActivity.this, R.color.black);
        // 注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        mEmotionalLogListView.setEmptyView(mEmotionalLogLl);
        initData();
        initListeners();
        initRefresh();


        vImageWatcher = ImageWatcher.Helper.with(this) // 一般来讲， ImageWatcher 需要占据全屏的位置
//                .setTranslucentStatus(!isTranslucentStatus ? Utils.calcStatusBarHeight(this) : 0) // 如果是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
                .setErrorImageRes(R.mipmap.error_picture) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
                .setOnPictureLongPressListener(this) // 长按图片的回调，你可以显示一个框继续提供一些复制，发送等功能
                .setLoader(new ImageWatcher.Loader() {
                    @Override
                    public void load(Context context, String url, final ImageWatcher.LoadCallback lc) {
                        Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                lc.onResourceReady(resource);
                            }

                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                lc.onLoadStarted(placeholder);
                            }

                            @Override
                            public void onLoadFailed(Drawable errorDrawable) {
                                lc.onLoadFailed(errorDrawable);
                            }
                        });
                    }
                }).create();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_emotional_log;
    }

    @Override
    public void initView() throws Exception {
        mEmotionalLogTextView = (TextView) findViewById(R.id.EmotionalLog_TextView);
        mEmotionalLogListView = (PullToRefreshListView) findViewById(R.id.EmotionalLog_listView);
        mEmotionalLogLl = (LinearLayout) findViewById(R.id.EmotionalLog_ll);
        mEmotionalLogTv = (TextView) findViewById(R.id.EmotionalLog_Tv);
    }

    private void initRefresh() {
        mEmotionalLogListView.setMode(PullToRefreshBase.Mode.BOTH);
        mEmotionalLogListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                initData();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        if (!vImageWatcher.handleBackPressed()) {
            super.onBackPressed();
        }
    }

    private void initData() {
        Log.e("gggg3", String.valueOf(SPUtils.get(EmotionalLogActivity.this, "token", "")));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "042");
        hashMap.put("token", String.valueOf(SPUtils.get(EmotionalLogActivity.this, "token", "")));
//        hashMap.put("createTime", );
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<EmotionalLogBeans>(EmotionalLogActivity.this) {
                    @Override
                    public void onSuccess(EmotionalLogBeans mEmotionalLogBeans, Call call, Response response) {
                        if (mEmotionalLogBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mEmotionalLogBeans.getData());
//                            mEmotionalLogAdapter = new EmotionalLogAdapter(EmotionalLogActivity.this, mList);
//                            mEmotionalLogListView.setAdapter(mEmotionalLogAdapter);
//                            mEmotionalLogListView.setAdapter(mEmotionalLogAdapter = new EmotionalLogAdapter(EmotionalLogActivity.this, mList).setPictureClickLogCallback(EmotionalLogActivity.this));
                            mEmotionalLogAdapter.notifyDataSetChanged();
                            mEmotionalLogListView.onRefreshComplete();
                        } else {
                            App.ErrorToken(mEmotionalLogBeans.getResultCode(), mEmotionalLogBeans.getMsg());

                        }
                    }
                });
        mEmotionalLogAdapter = new EmotionalLogAdapter(this, mList);
        mEmotionalLogListView.setAdapter(mEmotionalLogAdapter);
        mEmotionalLogListView.setAdapter(mEmotionalLogAdapter = new EmotionalLogAdapter(EmotionalLogActivity.this, mList).setPictureClickLogCallback(this));

    }

    private void initListeners() {
        mEmotionalLogTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(EmotionalLogActivity.this, WriteEmotionalActivity.class), 11);
            }
        });
        mEmotionalLogTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(EmotionalLogActivity.this, WriteEmotionalActivity.class), 11);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 12) {
//            Log.e("gggg1", "ffdg");
            initData();

        }
    }

    public void EmotionalLogActivity_Bank(View view) {
//        Intent intent = new Intent(EmotionalLogActivity.this, ReceiverMainActivity.class);
//        setResult(11, intent);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        initData();
    }

    @Override
    public void voideEmotion(String work) {
//        initData();
    }

    @Override
    public void onThumbPictureClick(ImageView i, List<ImageView> imageGroupList, List<String> urlList) {
        vImageWatcher.show(i, imageGroupList, urlList);
    }

    @Override
    public void onPictureLongPress(ImageView v, String url, int pos) {

    }
}
