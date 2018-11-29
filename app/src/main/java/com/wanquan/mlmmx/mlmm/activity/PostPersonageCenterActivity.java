package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.BaseActivity;
import com.wanquan.mlmmx.mlmm.activity.CircleDetailsActivity;
import com.wanquan.mlmmx.mlmm.adapter.PostPersonageCenterAdapter;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.PostPersonageCenterBeans;
import com.wanquan.mlmmx.mlmm.beans.PostPersonageCenterIsFollowBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MyListView;
import com.wanquan.mlmmx.mlmm.view.MyScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

import static org.xutils.common.util.DensityUtil.getScreenHeight;

/**
 * 描述：发帖人个人中心
 * 作者：薛昌峰
 */
public class PostPersonageCenterActivity extends BaseActivity {
    private LinearLayout mPostPersonageCenterLinearLayout;
    private PullToRefreshScrollView mPostPersonageCenterMyScrollView;
    private LinearLayout mPostPersonageCenterLL;
    private RelativeLayout mPostPersonageCenterRelativeLayout;
    private LinearLayout mPostPersonageCenterBank;
    private ImageView mPostPersonageCenterBankImg;
    private TextView mPostPersonageCenterBankTv;
    private TextView mPostPersonageCenterTitle;
    private CircleImageView mPostPersonageCenterImg;
    private TextView mPostPersonageCenterTitle2;
    private TextView mPostPersonageCenterTV1;
    private TextView mPostPersonageCenterTV3;
    private MyListView mPostPersonageCenterMyListView;

    private PostPersonageCenterAdapter mPostPersonageCenterAdapter;
    private List<PostPersonageCenterBeans.DataBean.UserForumListBean> mList = new ArrayList<>();
    private String id;
    private int isFollow;
    private int pageNo = 1;
    private String times;
    private int month;
    private int days;
    private int dayAll;
    private int daytime;
    private int week;
    private int week_day;

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(PostPersonageCenterActivity.this, R.color.tops);

        //注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        id = getIntent().getStringExtra("id");

        Log.e("sfas", String.valueOf(SPUtils.get(this, "token", "")));
        Log.e("sfas", id);

        initData();
        initRefresh();
        initListeners();

        mPostPersonageCenterAdapter = new PostPersonageCenterAdapter(this, mList);
        mPostPersonageCenterMyListView.setAdapter(mPostPersonageCenterAdapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_post_personage_center;
    }

    @Override
    public void initView() throws Exception {
        mPostPersonageCenterMyScrollView = (PullToRefreshScrollView) findViewById(R.id.PostPersonageCenter_MyScrollView);
        mPostPersonageCenterLL = (LinearLayout) findViewById(R.id.PostPersonageCenter_LL);
        mPostPersonageCenterRelativeLayout = (RelativeLayout) findViewById(R.id.PostPersonageCenter_RelativeLayout);
        mPostPersonageCenterBank = (LinearLayout) findViewById(R.id.PostPersonageCenter_Bank);
        mPostPersonageCenterBankImg = (ImageView) findViewById(R.id.PostPersonageCenter_Bank_Img);
        mPostPersonageCenterBankTv = (TextView) findViewById(R.id.PostPersonageCenter_Bank_Tv);
        mPostPersonageCenterTitle = (TextView) findViewById(R.id.PostPersonageCenter_Title);
        mPostPersonageCenterImg = (CircleImageView) findViewById(R.id.PostPersonageCenter_Img);
        mPostPersonageCenterTitle2 = (TextView) findViewById(R.id.PostPersonageCenter_Title2);
        mPostPersonageCenterTV1 = (TextView) findViewById(R.id.PostPersonageCenter_TV1);
        mPostPersonageCenterTV3 = (TextView) findViewById(R.id.PostPersonageCenter_TV3);
        mPostPersonageCenterMyListView = (MyListView) findViewById(R.id.PostPersonageCenter_MyListView);

    }

    private void initRefresh() {
        mPostPersonageCenterMyScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPostPersonageCenterMyScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
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
        Log.e("dsadsadsa", String.valueOf(pageNo));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "120");
        hashMap.put("token", SPUtils.get(this, "token", ""));
        hashMap.put("uid", id);
        hashMap.put("pageNo", pageNo);
        hashMap.put("pageSize", "10");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<PostPersonageCenterBeans>(this) {
                    @Override
                    public void onSuccess(PostPersonageCenterBeans mPostPersonageCenterBeans, Call call, Response response) {
                        if (mPostPersonageCenterBeans.getResultCode() == 1) {
                            if (pageNo == 1) {
                                mList.clear();
                            }
                            mList.addAll(mPostPersonageCenterBeans.getData().getUserForumList());
                            mPostPersonageCenterAdapter.notifyDataSetChanged();

                            if (mPostPersonageCenterBeans.getData().getBabyInfo() != null)
                                if (mPostPersonageCenterBeans.getData().getBabyInfo().size() > 0) {
                                    int babyStatus = mPostPersonageCenterBeans.getData().getBabyInfo().get(0).getBabyStatus();
                                    if (babyStatus == 0) {
                                        mPostPersonageCenterTV1.setText("备孕中");
                                    } else if (babyStatus == 1) {
                                        if (null != mPostPersonageCenterBeans.getData().getBabyInfo().get(0).getPreChildDate()) {
                                            //计算宝宝怀孕天数
                                            String preChildDate = mPostPersonageCenterBeans.getData().getBabyInfo().get(0).getPreChildDate();
                                            //获取当前时间
                                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            Date curDate = new Date(System.currentTimeMillis());
                                            times = formatter.format(curDate);
                                            //当前时间
                                            DateTime now = DateTime.now();
                                            String startTimes = times;
                                            if (preChildDate.contains(" ")) {
                                                startTimes = preChildDate.substring(0, preChildDate.indexOf(" "));
                                            } else {
                                                startTimes = preChildDate;
                                            }
                                            DateTime dateTime = DateTime.parse(startTimes);//设置的时间
                                            DateTime nows = dateTime.minusDays(280);//向前推280

                                            month = Months.monthsBetween(nows, dateTime).getMonths();//月
                                            dayAll = Days.daysBetween(nows, dateTime).getDays();//总天
                                            daytime = Days.daysBetween(nows, now).getDays();//已怀孕天
                                            days = Days.daysBetween(now, dateTime).getDays();//距离出生日

                                            week = +daytime / 7;//周
                                            week_day = +daytime % 7 + 1;//周余天
                                            if (week_day == 0) {
                                                mPostPersonageCenterTV1.setText("孕" + week + "周");
                                            } else if (week_day == 7) {
                                                int weekjia = week + 1;
                                                mPostPersonageCenterTV1.setText("孕" + weekjia + "周");
                                            } else {
                                                if (week == 0) {
                                                    mPostPersonageCenterTV1.setText("孕" + week_day + "天");
                                                } else {
                                                    mPostPersonageCenterTV1.setText("孕" + week + "周" + week_day + "天");
                                                }
                                            }
                                        }

                                    } else {
                                        if (null != mPostPersonageCenterBeans.getData().getBabyInfo().get(0).getChildBirthDate()) {
                                            //当前时间
                                            DateTime now = DateTime.now();
                                            //宝宝生日
                                            DateTime dateTime = DateTime.parse(mPostPersonageCenterBeans.getData().getBabyInfo().get(0).getChildBirthDate());
                                            int QieHuanMonth = Months.monthsBetween(dateTime, now).getMonths();//宝宝出生月
                                            DateTime tmp = dateTime.plusMonths(QieHuanMonth); //月的天
                                            String QieHuanMonthday = String.valueOf(Days.daysBetween(tmp, now).getDays());//月的天
                                            int newDay = Integer.parseInt(QieHuanMonthday);
                                            QieHuanMonthday = String.valueOf(newDay);

                                            if (QieHuanMonth == 0 && QieHuanMonthday.equals("0")) {
                                                mPostPersonageCenterTV1.setText("宝宝今天出生");
                                            } else if (QieHuanMonth == 0 && !QieHuanMonthday.equals("0")) {
                                                mPostPersonageCenterTV1.setText("宝宝" + QieHuanMonthday + "天");
                                            } else if (QieHuanMonth < 12 && !QieHuanMonthday.equals("0")) {
                                                mPostPersonageCenterTV1.setText("宝宝" + QieHuanMonth + "个月" + QieHuanMonthday + "天");
                                            } else if (QieHuanMonth < 12 && QieHuanMonthday.equals("0")) {
                                                mPostPersonageCenterTV1.setText("宝宝" + QieHuanMonth + "个月");
                                            } else if (QieHuanMonth > 12 && QieHuanMonthday.equals("0")) {
                                                int month1 = month / 12;
                                                int month2 = month % 12;
                                                if (month2 == 0) {
                                                    mPostPersonageCenterTV1.setText("宝宝" + month1 + "岁");
                                                } else {
                                                    mPostPersonageCenterTV1.setText("宝宝" + month1 + "岁" + month2 + "个月");
                                                }
                                            } else if (QieHuanMonth > 12 && !QieHuanMonthday.equals("0")) {
                                                int month1 = month / 12;
                                                int month2 = month % 12;
                                                if (month2 == 0) {
                                                    mPostPersonageCenterTV1.setText("宝宝" + month1 + "岁" + QieHuanMonthday + "天");
                                                } else {
                                                    mPostPersonageCenterTV1.setText("宝宝" + month1 + "岁" + month2 + "个月" + QieHuanMonthday + "天");
                                                }
                                            }
                                        }
                                    }
                                }

                            Glide.with(PostPersonageCenterActivity.this).load(mPostPersonageCenterBeans.getData().getHeadIco()).into(mPostPersonageCenterImg);
                            mPostPersonageCenterTitle.setText(mPostPersonageCenterBeans.getData().getNickName());
                            mPostPersonageCenterTitle2.setText(mPostPersonageCenterBeans.getData().getNickName());

//                            Log.e("fdfsdfsd0", String.valueOf(mPostPersonageCenterBeans.getData().isIsFollow()));

                            if (mPostPersonageCenterBeans.getData().isIsFollow()) {
                                mPostPersonageCenterTV3.setText("取消关注");
                                isFollow = 0;
                            } else {
                                mPostPersonageCenterTV3.setText("关注");
                                isFollow = 1;
                            }
                            if (id.equals(SPUtils.get(PostPersonageCenterActivity.this, "userid", ""))) {
                                mPostPersonageCenterTV3.setVisibility(View.GONE);
                            } else {
                                mPostPersonageCenterTV3.setVisibility(View.VISIBLE);
                            }
                        } else {
                            App.ErrorToken(mPostPersonageCenterBeans.getResultCode(), mPostPersonageCenterBeans.getMsg());
                        }
                        mPostPersonageCenterMyScrollView.onRefreshComplete();
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        //取消关注的动态爱心显示
//        if (event.getItFaceCollect()) {
//            initData();
//        }
    }


    private void initListeners() {
        mPostPersonageCenterMyScrollView.getRefreshableView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    //可以监听到ScrollView的滚动事件
                    double alpha = (double) mPostPersonageCenterMyScrollView.getRefreshableView().getScrollY() / (getScreenHeight() / 3) * 255;
                    alpha = alpha > 255 ? 255 : alpha;
                    alpha = alpha < 2 ? 0 : alpha;
                    Log.e("TAG", "ALPHA:" + alpha);

                    if (alpha == 0 && alpha <= 40) {
                        mPostPersonageCenterRelativeLayout.setBackground(getResources().getDrawable(R.color.white1));
                        mPostPersonageCenterBankImg.setBackground(getResources().getDrawable(R.mipmap.arrowwhite));
                        mPostPersonageCenterBankTv.setTextColor(getResources().getColor(R.color.white));
                        mPostPersonageCenterTitle.setTextColor(getResources().getColor(R.color.black1));
                    } else if (alpha > 40 && alpha <= 60) {
                        mPostPersonageCenterRelativeLayout.setBackground(getResources().getDrawable(R.color.white2));
                        mPostPersonageCenterBankImg.setBackground(getResources().getDrawable(R.mipmap.backicon));
                        mPostPersonageCenterBankTv.setTextColor(getResources().getColor(R.color.topss));
                        mPostPersonageCenterTitle.setTextColor(getResources().getColor(R.color.black2));
                    } else if (alpha > 60 && alpha <= 100) {
                        mPostPersonageCenterRelativeLayout.setBackground(getResources().getDrawable(R.color.white3));
                        mPostPersonageCenterBankImg.setBackground(getResources().getDrawable(R.mipmap.backicon));
                        mPostPersonageCenterBankTv.setTextColor(getResources().getColor(R.color.topss));
                        mPostPersonageCenterTitle.setTextColor(getResources().getColor(R.color.black3));

                    } else if (alpha > 100 && alpha <= 140) {
                        mPostPersonageCenterRelativeLayout.setBackground(getResources().getDrawable(R.color.white4));
                        mPostPersonageCenterBankImg.setBackground(getResources().getDrawable(R.mipmap.backicon));
                        mPostPersonageCenterBankTv.setTextColor(getResources().getColor(R.color.topss));
                        mPostPersonageCenterTitle.setTextColor(getResources().getColor(R.color.black4));

                    } else if (alpha > 140) {
                        mPostPersonageCenterRelativeLayout.setBackground(getResources().getDrawable(R.color.white5));
                        mPostPersonageCenterBankImg.setBackground(getResources().getDrawable(R.mipmap.backicon));
                        mPostPersonageCenterBankTv.setTextColor(getResources().getColor(R.color.topss));
                        mPostPersonageCenterTitle.setTextColor(getResources().getColor(R.color.black5));
                    }

                    int scrollY = view.getScrollY();
                    int height = view.getHeight();
                    int scrollViewMeasuredHeight = mPostPersonageCenterMyScrollView.getChildAt(0).getMeasuredHeight();
                    if (scrollY == 0) {
                       Log.e("ddddddd","滑动到了顶端 view.getScrollY()=" + scrollY);
                    }
                    if ((scrollY + height) == scrollViewMeasuredHeight) {
                        Log.e("ddddddd","滑动到了底部 scrollY=" + scrollY);
                        Log.e("ddddddd","滑动到了底部 height=" + height);
                        Log.e("ddddddd","滑动到了底部 scrollViewMeasuredHeight=" + scrollViewMeasuredHeight);
                    }

                }
                return false;
            }
        });

        mPostPersonageCenterBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mPostPersonageCenterMyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(PostPersonageCenterActivity.this, CircleDetailsActivity.class);
                intent.putExtra("id", mList.get(position).getId());
                intent.putExtra("headico", mList.get(position).getHeadIco());
                intent.putExtra("name", mList.get(position).getUserName());
                intent.putExtra("name2", mList.get(position).getNickName());
                intent.putExtra("message", mList.get(position).getMessage());
                intent.putExtra("time", mList.get(position).getCreateTime());
                intent.putExtra("createTime", mList.get(position).getCreateTime());
                intent.putExtra("title", mList.get(position).getTitle());
                intent.putExtra("content", mList.get(position).getContent());
                intent.putExtra("img", mList.get(position).getImg());
                intent.putExtra("follow", mList.get(position).getFollow());
                startActivity(intent);
            }
        });
        mPostPersonageCenterTV3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("itfaceId", "117");
                hashMap.put("token", SPUtils.get(PostPersonageCenterActivity.this, "token", ""));
                hashMap.put("uid", id);
                hashMap.put("action", isFollow);
                JSONObject jsonObject = new JSONObject(hashMap);

                OkGo.post(UrlContent.URL).tag(this)
                        .upJson(jsonObject.toString())
                        .connTimeOut(10_000)
                        .execute(new CustomCallBackNoLoading<PostPersonageCenterIsFollowBeans>(PostPersonageCenterActivity.this) {
                            @Override
                            public void onSuccess(PostPersonageCenterIsFollowBeans mPostPersonageCenterIsFollowBeans, Call call, Response response) {
                                if (mPostPersonageCenterIsFollowBeans.getResultCode() == 1) {
                                    Toast.makeText(PostPersonageCenterActivity.this, mPostPersonageCenterIsFollowBeans.getMsg(), Toast.LENGTH_SHORT).show();
                                    initData();
                                } else {
                                    App.ErrorToken(mPostPersonageCenterIsFollowBeans.getResultCode(), mPostPersonageCenterIsFollowBeans.getMsg());
                                }
                            }
                        });
            }
        });

    }
}
