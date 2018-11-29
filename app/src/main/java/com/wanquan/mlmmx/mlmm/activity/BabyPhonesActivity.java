package com.wanquan.mlmmx.mlmm.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.wanquan.mlmmx.mlmm.adapter.MessageAdapter;
import com.wanquan.mlmmx.mlmm.beans.BabyPhoneBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.phone.Release_Work_Activity;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MessagePicturesLayout;

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
 * 描述：宝宝相册
 * 作者：薛昌峰
 * 时间：2017.12.05
 */
public class BabyPhonesActivity extends BaseActivity implements MessagePicturesLayout.Callback, ImageWatcher.OnPictureLongPressListener {
    private TextView mBabyPhoneActivitysSave;
    private PullToRefreshListView mBabyPhonesListView;
    private LinearLayout mBabyPhonesLl;
    private TextView mBabyPhonesActivityTv;
    private ImageWatcher vImageWatcher;
    private MessageAdapter adapter;
    List<BabyPhoneBeans.DataBean> mList = new ArrayList<>();
    private int page = 1;

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

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
        manageUtils.setSystemBarTintManage(BabyPhonesActivity.this, R.color.black);

        // 注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

//        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
//        View view = layoutInflater.inflate(R.layout.recycler_main_message,null);
//        TextView text = view.findViewById(R.id.item_Baby_delete);
//        text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(BabyPhonesActivity.this, "到了", Toast.LENGTH_SHORT).show();
//            }
//        });

        initListeners();
        initRefresh();

        mBabyPhonesListView.setEmptyView(mBabyPhonesLl);
        adapter = new MessageAdapter(this, mList);
        mBabyPhonesListView.setAdapter(adapter);
        mBabyPhonesListView.setAdapter(adapter = new MessageAdapter(BabyPhonesActivity.this, mList).setPictureClickCallback(this));

//        vRecycler.setLayoutManager(new LinearLayoutManager(this));
//        vRecycler.addItemDecoration(new SpaceItemDecoration(this).setSpace(14).setSpaceColor(0xFFECECEC));
//        adapter = new MessageAdapter(this,mList);
//        vRecycler.setAdapter(adapter = new MessageAdapter(BabyPhonesActivity.this,mList).setPictureClickCallback(this));
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

//        Utils.fitsSystemWindows(isTranslucentStatus, findViewById(R.id.v_fit));
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_baby_phones;
    }

    @Override
    public void initView() throws Exception {
        mBabyPhoneActivitysSave = (TextView) findViewById(R.id.BabyPhoneActivitys_Save);
        mBabyPhonesListView = (PullToRefreshListView) findViewById(R.id.BabyPhones_listView);
        mBabyPhonesLl = (LinearLayout) findViewById(R.id.BabyPhones_ll);
        mBabyPhonesActivityTv = (TextView) findViewById(R.id.BabyPhonesActivity_Tv);
    }

    private void initRefresh() {
        mBabyPhonesListView.setMode(PullToRefreshBase.Mode.BOTH);
        mBabyPhonesListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                initNetWork(page);
            }
        });
    }

    private void initNetWork(int page) {
//        Log.e("ffffffff", String.valueOf(SPUtils.get(BabyPhonesActivity.this, "isQieHuanBBId", "")));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "038");
        hashMap.put("token", SPUtils.get(BabyPhonesActivity.this, "token", ""));
        hashMap.put("pageNum", page);
        hashMap.put("numberPahe", 10);
        hashMap.put("babyId", SPUtils.get(BabyPhonesActivity.this, "babyId", ""));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<BabyPhoneBeans>(BabyPhonesActivity.this) {
                    @Override
                    public void onSuccess(BabyPhoneBeans mBabyPhoneBeans, Call call, Response response) {
                        if (mBabyPhoneBeans.getResultCode() == 1) {
//                            mList.clear();
                            mList.addAll(mBabyPhoneBeans.getData());
                            adapter.notifyDataSetChanged();
                            mBabyPhonesListView.onRefreshComplete();
                        } else {
                            App.ErrorToken(mBabyPhoneBeans.getResultCode(),mBabyPhoneBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
        mBabyPhoneActivitysSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SPUtils.get(BabyPhonesActivity.this, "authority", "").equals("1") || SPUtils.get(BabyPhonesActivity.this, "authority", "").equals("2")) {
                    startActivity(new Intent(BabyPhonesActivity.this, Release_Work_Activity.class));
                } else if (SPUtils.get(BabyPhonesActivity.this, "authority", "").equals("0")) {
                    Toast.makeText(BabyPhonesActivity.this, "亲，您还没有该权限哦", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBabyPhonesActivityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SPUtils.get(BabyPhonesActivity.this, "authority", "").equals("1") || SPUtils.get(BabyPhonesActivity.this, "authority", "").equals("2")) {
                    startActivity(new Intent(BabyPhonesActivity.this, Release_Work_Activity.class));
                } else if (SPUtils.get(BabyPhonesActivity.this, "authority", "").equals("0")) {
                    Toast.makeText(BabyPhonesActivity.this, "亲，您还没有该权限哦", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initData() {
//        Log.e("gggggggguserid", String.valueOf(SPUtils.get(BabyPhonesActivity.this, "userid", "")));
//        Log.e("ggggggggisQieHuanBBId", String.valueOf(SPUtils.get(BabyPhonesActivity.this, "isQieHuanBBId", "")));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "038");
        hashMap.put("token", SPUtils.get(BabyPhonesActivity.this, "token", ""));
        hashMap.put("userId", SPUtils.get(BabyPhonesActivity.this, "userid", ""));
        hashMap.put("babyId", SPUtils.get(BabyPhonesActivity.this, "babyId", ""));

        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<BabyPhoneBeans>(BabyPhonesActivity.this) {
                    @Override
                    public void onSuccess(BabyPhoneBeans mBabyPhoneBeans, Call call, Response response) {
                        if (mBabyPhoneBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mBabyPhoneBeans.getData());
                            adapter.notifyDataSetChanged();
                            mBabyPhonesListView.onRefreshComplete();
                        } else {
                            App.ErrorToken(mBabyPhoneBeans.getResultCode(),mBabyPhoneBeans.getMsg());
                        }
                    }
                });
    }

    @Override
    public void onThumbPictureClick(ImageView i, List<ImageView> imageGroupList, List<String> urlList) {
        vImageWatcher.show(i, imageGroupList, urlList);
    }

    @Override
    public void onPictureLongPress(ImageView v, String url, int pos) {
//        Toast.makeText(v.getContext().getApplicationContext(), "长按了第" + (pos + 1) + "张图片", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (!vImageWatcher.handleBackPressed()) {
            super.onBackPressed();
        }
    }

    public void BabyPhoneActivitys_Bank(View view) {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        initData();
    }
}
