package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.CircleDetailNewAdapter;
import com.wanquan.mlmmx.mlmm.beans.CircleDetailNewBeans;
import com.wanquan.mlmmx.mlmm.beans.CircleDetailNewFollowBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.DKDragView;
import com.wanquan.mlmmx.mlmm.view.MyListView;
import com.wanquan.mlmmx.mlmm.view.MyScrollView;
import com.wanquan.mlmmx.mlmm.view.SonnyJackDragView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static org.xutils.common.util.DensityUtil.getScreenHeight;

/**
 * 描述：单类所有圈子集合
 * 作者：薛昌峰
 * 时间：2018.06.26
 */
public class CircleDetailNewActivity extends BaseActivity {
    private PullToRefreshScrollView mCircleDetailNewPullToRefreshScrollView;
    private ImageView mCircleDetailNewImageView;
    private LinearLayout mCircleDetailNewLinearLayout;
    private LinearLayout mCircleDetailNewLL;
    private LinearLayout mCircleDetailNewBank;
    private TextView mCircleDetailNewTitle;
    private TextView mCircleDetailNewTitle2;
    private TextView mCircleDetailNewTV1;
    private TextView mCircleDetailNewTV2;
    private TextView mCircleDetailNewTV3;
    private MyListView mCircleDetailNewMyListView;
    private DKDragView mCircleDetailNewActivityTextView;

    private List<CircleDetailNewBeans.DataBean.FornumListBean> mList = new ArrayList<>();
    private CircleDetailNewAdapter mCircleDetailNewAdapter;
    private String id;
    private String isFollow;
    private int pageNo = 1;
    private String name;
    private ImageView imageView;
    private SonnyJackDragView mSonnyJackDragView;



    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(CircleDetailNewActivity.this, R.color.tops);


        //注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        id = getIntent().getStringExtra("id");

        Log.e("fffsdf", id + "");

        initData();
        initRefresh();
        initListeners();

        mCircleDetailNewAdapter = new CircleDetailNewAdapter(this, mList);
        mCircleDetailNewMyListView.setAdapter(mCircleDetailNewAdapter);
    }


    @Override
    protected int setLayoutID() {
        return R.layout.activity_circle_detail_new;
    }

    @Override
    public void initView() throws Exception {
        mCircleDetailNewPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.CircleDetailNew_PullToRefreshScrollView);
        mCircleDetailNewImageView = (ImageView) findViewById(R.id.CircleDetailNew_ImageView);
        mCircleDetailNewLinearLayout = (LinearLayout) findViewById(R.id.CircleDetailNew_LinearLayout);
        mCircleDetailNewLL = (LinearLayout) findViewById(R.id.CircleDetailNew_LL);
        mCircleDetailNewBank = (LinearLayout) findViewById(R.id.CircleDetailNew_Bank);
        mCircleDetailNewTitle = (TextView) findViewById(R.id.CircleDetailNew_Title);
        mCircleDetailNewTitle2 = (TextView) findViewById(R.id.CircleDetailNew_Title2);
        mCircleDetailNewTV1 = (TextView) findViewById(R.id.CircleDetailNew_TV1);
        mCircleDetailNewTV2 = (TextView) findViewById(R.id.CircleDetailNew_TV2);
        mCircleDetailNewTV3 = (TextView) findViewById(R.id.CircleDetailNew_TV3);
        mCircleDetailNewMyListView = (MyListView) findViewById(R.id.CircleDetailNew_MyListView);
        mCircleDetailNewActivityTextView = (DKDragView) findViewById(R.id.CircleDetailNewActivity_TextView);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getItFaceCollect()) {
            initData();
        }
    }

    private void initRefresh() {
        mCircleDetailNewPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mCircleDetailNewPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                pageNo++;
                initData();
            }
        });
    }

    private void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "114");
        hashMap.put("token", SPUtils.get(this, "token", ""));
        hashMap.put("circleId", id);
        hashMap.put("pageNo", pageNo);
        hashMap.put("pageSize", "10");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<CircleDetailNewBeans>(this) {
                    @Override
                    public void onSuccess(CircleDetailNewBeans mCircleDetailNewBeans, Call call, Response response) {
                        if (mCircleDetailNewBeans.getResultCode() == 1) {
                            if (pageNo == 1) {
                                mList.clear();
                            }
                            mList.addAll(mCircleDetailNewBeans.getData().getFornumList());

                            if (mList.size() > 0) {
                                mCircleDetailNewLinearLayout.setVisibility(View.GONE);
                            } else {
                                mCircleDetailNewLinearLayout.setVisibility(View.VISIBLE);
                            }
                            name = mCircleDetailNewBeans.getData().getName();
                            mCircleDetailNewTitle.setText(mCircleDetailNewBeans.getData().getName());
                            mCircleDetailNewTitle2.setText(mCircleDetailNewBeans.getData().getName());
                            mCircleDetailNewTV1.setText("成员: " + mCircleDetailNewBeans.getData().getMemberNum());
                            mCircleDetailNewTV2.setText("今日话题: " + mCircleDetailNewBeans.getData().getForumCount());
                            isFollow = mCircleDetailNewBeans.getData().getIsFollow();
                            if ("1".equals(isFollow)) {
                                mCircleDetailNewTV3.setText("取消关注");
                                isFollow = "0";
                            } else {
                                mCircleDetailNewTV3.setText("关注圈子");
                                isFollow = "1";
                            }
//                            if(Util.isOnMainThread()) {
                            Glide.with(getApplicationContext()).load(mCircleDetailNewBeans.getData().getImg()).into(mCircleDetailNewImageView);
//                            }

                            mCircleDetailNewAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mCircleDetailNewBeans.getResultCode(), mCircleDetailNewBeans.getMsg());
                        }
                        mCircleDetailNewPullToRefreshScrollView.onRefreshComplete();
                    }
                });
    }

    private void initListeners() {
        imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.fatie);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CircleDetailNewActivity.this, SelectCircleActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        mSonnyJackDragView = new SonnyJackDragView.Builder()
                .setActivity(this)
                .setDefaultLeft(850)
                .setDefaultTop(1300)
                .setNeedNearEdge(false)
                .setSize(150)
                .setView(imageView)
                .build();
//        mPostPersonageCenterMyScrollView.getRefreshableView().setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                    //可以监听到ScrollView的滚动事件
//                    double alpha = (double) mPostPersonageCenterMyScrollView.getRefreshableView().getScrollY() / (getScreenHeight() / 3) * 255;
//                    alpha = alpha > 255 ? 255 : alpha;
//                    alpha = alpha < 2 ? 0 : alpha;
//                    Log.e("TAG", "ALPHA:" + alpha);
//
//                    if (alpha == 0 && alpha <= 40) {
//                        mPostPersonageCenterRelativeLayout.setBackground(getResources().getDrawable(R.color.white1));
//                        mPostPersonageCenterBankImg.setBackground(getResources().getDrawable(R.mipmap.arrowwhite));
//                        mPostPersonageCenterBankTv.setTextColor(getResources().getColor(R.color.white));
//                        mPostPersonageCenterTitle.setTextColor(getResources().getColor(R.color.black1));
//                    } else if (alpha > 40 && alpha <= 60) {
//                        mPostPersonageCenterRelativeLayout.setBackground(getResources().getDrawable(R.color.white2));
//                        mPostPersonageCenterBankImg.setBackground(getResources().getDrawable(R.mipmap.backicon));
//                        mPostPersonageCenterBankTv.setTextColor(getResources().getColor(R.color.topss));
//                        mPostPersonageCenterTitle.setTextColor(getResources().getColor(R.color.black2));
//                    } else if (alpha > 60 && alpha <= 100) {
//                        mPostPersonageCenterRelativeLayout.setBackground(getResources().getDrawable(R.color.white3));
//                        mPostPersonageCenterBankImg.setBackground(getResources().getDrawable(R.mipmap.backicon));
//                        mPostPersonageCenterBankTv.setTextColor(getResources().getColor(R.color.topss));
//                        mPostPersonageCenterTitle.setTextColor(getResources().getColor(R.color.black3));
//
//                    } else if (alpha > 100 && alpha <= 140) {
//                        mPostPersonageCenterRelativeLayout.setBackground(getResources().getDrawable(R.color.white4));
//                        mPostPersonageCenterBankImg.setBackground(getResources().getDrawable(R.mipmap.backicon));
//                        mPostPersonageCenterBankTv.setTextColor(getResources().getColor(R.color.topss));
//                        mPostPersonageCenterTitle.setTextColor(getResources().getColor(R.color.black4));
//
//                    } else if (alpha > 140) {
//                        mPostPersonageCenterRelativeLayout.setBackground(getResources().getDrawable(R.color.white5));
//                        mPostPersonageCenterBankImg.setBackground(getResources().getDrawable(R.mipmap.backicon));
//                        mPostPersonageCenterBankTv.setTextColor(getResources().getColor(R.color.topss));
//                        mPostPersonageCenterTitle.setTextColor(getResources().getColor(R.color.black5));
//                    }
//
//                    int scrollY = view.getScrollY();
//                    int height = view.getHeight();
//                    int scrollViewMeasuredHeight = mPostPersonageCenterMyScrollView.getChildAt(0).getMeasuredHeight();
//                    if (scrollY == 0) {
//                        Log.e("ddddddd","滑动到了顶端 view.getScrollY()=" + scrollY);
//                    }
//                    if ((scrollY + height) == scrollViewMeasuredHeight) {
//                        Log.e("ddddddd","滑动到了底部 scrollY=" + scrollY);
//                        Log.e("ddddddd","滑动到了底部 height=" + height);
//                        Log.e("ddddddd","滑动到了底部 scrollViewMeasuredHeight=" + scrollViewMeasuredHeight);
//                    }
//
//                }
//                return false;
//            }
//        });
        mCircleDetailNewActivityTextView.setOnDragViewClickListener(new DKDragView.onDragViewClickListener() {
            @Override
            public void onClick() {
                startActivity(new Intent(CircleDetailNewActivity.this, SendInvitationActivity.class)
                        .putExtra("name", name).putExtra("id", id));
            }
        });
        mCircleDetailNewBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCircleDetailNewMyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CircleDetailNewActivity.this, CircleDetailsActivity.class);
                intent.putExtra("id", mList.get(position).getId());
                intent.putExtra("headico", mList.get(position).getHeadIco());
                intent.putExtra("name", mList.get(position).getUserName());
                intent.putExtra("name2", mList.get(position).getNickName());
                intent.putExtra("message", mList.get(position).getMessage());
                intent.putExtra("createTime", mList.get(position).getCreateTime());
                intent.putExtra("time", mList.get(position).getCreateTime());
                intent.putExtra("title", mList.get(position).getTitle());
                intent.putExtra("content", mList.get(position).getContent());
                intent.putExtra("img", mList.get(position).getImg());
                intent.putExtra("follow", mList.get(position).getFollow());
                startActivity(intent);
            }
        });

        mCircleDetailNewTV3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("0".equals(isFollow)) {
                    initIsFollow(0);
                } else {
                    initIsFollow(1);
                }
            }
        });

    }

    private void initIsFollow(int initIsFollow) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "115");
        hashMap.put("token", SPUtils.get(CircleDetailNewActivity.this, "token", ""));
        hashMap.put("id", id);
        hashMap.put("isFollow", initIsFollow);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<CircleDetailNewFollowBeans>(CircleDetailNewActivity.this) {
                    @Override
                    public void onSuccess(CircleDetailNewFollowBeans circleDetailNewFollowBeans, Call call, Response response) {
                        if (circleDetailNewFollowBeans.getResultCode() == 1) {
                            Toast.makeText(CircleDetailNewActivity.this, circleDetailNewFollowBeans.getData().getMsg(), Toast.LENGTH_SHORT).show();
                            initData();
                        } else {
                            App.ErrorToken(circleDetailNewFollowBeans.getResultCode(), circleDetailNewFollowBeans.getMsg());
                        }
                    }

                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(Util.isOnMainThread()) {
//            Glide.with(getApplicationContext()).pauseRequests();
//        }
    }
}
