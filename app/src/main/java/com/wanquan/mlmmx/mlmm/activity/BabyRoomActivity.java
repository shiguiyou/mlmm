package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lzy.okgo.OkGo;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.BabyRoomAdapters;
import com.wanquan.mlmmx.mlmm.adapter.MyAdapter;
import com.wanquan.mlmmx.mlmm.beans.BabyNumBeans;
import com.wanquan.mlmmx.mlmm.beans.BabyRoomDialogGridViewBeans;
import com.wanquan.mlmmx.mlmm.beans.BabyRoomShowBeans;
import com.wanquan.mlmmx.mlmm.beans.LineChartData;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.People;
import com.wanquan.mlmmx.mlmm.beans.SettingBabyMessageBeans;
import com.wanquan.mlmmx.mlmm.fragment.BabyRoomFragmentCJ;
import com.wanquan.mlmmx.mlmm.fragment.BabyRoomFragmentPT;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.HorizontalLineView;
import com.wanquan.mlmmx.mlmm.view.HorizontalListView;
import com.wanquan.mlmmx.mlmm.view.LineChart;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneSession;
import static com.wanquan.mlmmx.mlmm.App.api;
import static com.wanquan.mlmmx.mlmm.App.getContext;

/**
 * 描述：宝宝房间
 * 作者：薛昌峰
 * 时间：2017.10.18
 */
public class BabyRoomActivity extends BaseActivity implements IUiListener {
    private Tencent mTencent;
    private String APP_ID = "1106283467";

    private List<BabyRoomDialogGridViewBeans.DataBean.CfsBean> mList2 = new ArrayList<>();
    private List<BabyRoomDialogGridViewBeans.DataBean> mList3 = new ArrayList<>();
    private List<BabyRoomShowBeans.DataBean> misBabyShowTypeList = new ArrayList<>();
    private List<People> mListPeople = new ArrayList<>();
    private ArrayList<PointF> mPointArrayList = new ArrayList<>();

    private PullToRefreshScrollView mPullToRefreshScrollView;
    private TextView mBabyRoomSize;
    private ImageView mBabyRoomImgbg;
    private ImageView mBabyRoomImgbgs;
    private ImageView mBabyRoomImg;
    private TextView mBabyRoomContent;
    private LinearLayout mBabyRoomActivityLinearLayout;
    private TextView mBabyRoomTitle;
    private TextView mBabyRoomText;
    private TextView mBabyRoomWenDu;
    private TextView mBabyRoomShiDu;
    private ImageView mBabyRoomImageView;
    private TextView mBabyRoomOne;
    private TextView mBabyRoomTwo;
    private TextView mBabyRoomThree;
    private LinearLayout mBabyRoomRL1;
    private LinearLayout mBabyRoomRL2;
    private LinearLayout mBabyRoomRL3;
    private LinearLayout mBabyRoomRL4;
    private String id;
    private String code;
    private Animation animation;//补间动画

    private LinearLayout mShareLinearLayout;
    private String title = "0";
    private String temp = "0";
    private String humi = "0";
    private String pm25 = "0";
    private String ozone = "0";
    private String pm03 = "0";
    private String img = "";

    private ViewPager mBabyRoomViewPager;
    private RadioGroup mBabyRoomRadioGroup;
    private RadioButton mBabyRoomRadioButton1;
    private RadioButton mBabyRoomRadioButton2;
    private BabyRoomFragmentPT mBabyRoomFragmentPT;
    private BabyRoomFragmentCJ mBabyRoomFragmentCJ;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private List<Fragment> mListFragment;
    private MyAdapter mAdapter;

    private LinearLayout mBabyRoomLLBG;
    private RelativeLayout mBabyRoomRLBG;
    private LinearLayout mBabyRoomLLBG2;
    private LinearLayout mBabyRoomZXTLL;
    private TextView mBabyRoomZXTName;
    private HorizontalLineView mBabyRoomZXTCurveChart;
    private String isBabyShowType;
    private boolean run = true;
    private final Handler handler = new Handler();
    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (run) {
                handler.postDelayed(this, 5000);
                //通知Finish
                Intent logoutIntent = new Intent();
                logoutIntent.setAction("com.xcf.moshi");
                logoutIntent.putExtra("moshi", "1");
                sendBroadcast(logoutIntent);

                initData();
            }
        }
    };
    public static String token;

    //x轴坐标对应的数据
    //y轴坐标对应的数据
    final ArrayList<Float> value = new ArrayList<>();
    final ArrayList<String> mon = new ArrayList<>();
    private String ownerUserId;
    private String babyId;
    private String name;
    private int babyStatus;

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTencent = Tencent.createInstance(APP_ID, getApplicationContext());

        //注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        Intent intent = getIntent();
        id = String.valueOf(intent.getIntExtra("id", 0));
        code = String.valueOf(intent.getStringExtra("code"));
        title = getIntent().getStringExtra("name");
        img = getIntent().getStringExtra("img");
        ownerUserId = getIntent().getStringExtra("ownerUserId");
        babyId = getIntent().getStringExtra("babyId");
        Log.e("gggggggg", id + code + ownerUserId + "xcf");
        Log.e("gggggggg", babyId + "xcf");

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(BabyRoomActivity.this, R.color.black);

        if (ownerUserId != null) {
            initNetwork(babyId);
        }

        task.run();
        initFragment();
        initSetRefresh();
        initListeners();

    }

    private void initNetwork(String babyid) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "033");
        hashMap.put("token", String.valueOf(SPUtils.get(getContext(), "token", "")));
        hashMap.put("id", babyid);

        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<SettingBabyMessageBeans>(getContext()) {
                    @Override
                    public void onSuccess(SettingBabyMessageBeans mSettingBabyMessageBeans, Call call, Response response) {
                        if (mSettingBabyMessageBeans.getResultCode() == 1) {
                            name = mSettingBabyMessageBeans.getData().getBabyNickname();
                            babyStatus = mSettingBabyMessageBeans.getData().getBabyStatus();
                        } else {
                            App.ErrorToken(mSettingBabyMessageBeans.getResultCode(), mSettingBabyMessageBeans.getMsg());
                        }
                    }
                });
    }

    private void initSetRefresh() {
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                initData();
                mPullToRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//                page++;
//                initNetWorks(page);
                mPullToRefreshScrollView.onRefreshComplete();
            }
        });
    }

    private void initFragment() {
        mListFragment = new ArrayList<>();
        mBabyRoomFragmentPT = new BabyRoomFragmentPT();
        mBabyRoomFragmentCJ = new BabyRoomFragmentCJ();

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("code", code);
        mBabyRoomFragmentPT.setArguments(bundle);

        Bundle bundle2 = new Bundle();
        bundle2.putString("id", id);
        bundle2.putString("code", code);
        mBabyRoomFragmentCJ.setArguments(bundle2);

        mListFragment.add(mBabyRoomFragmentPT);
        mListFragment.add(mBabyRoomFragmentCJ);

        //初始化适配器
        mFragmentManager = getSupportFragmentManager();
        mAdapter = new MyAdapter(mFragmentManager, mListFragment);
        mBabyRoomViewPager.setAdapter(mAdapter);

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_baby_room;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final MessageEvent event) {
        if (event.isBabyShow()) {
            mBabyRoomZXTCurveChart.setVisibility(View.VISIBLE);
        } else {
            mBabyRoomZXTCurveChart.setVisibility(View.GONE);
        }
        if (event.getIsBabyShowType() != null) {
            isBabyShowType = event.getIsBabyShowType();
            if ("pm03".equals(isBabyShowType)) {
                mBabyRoomZXTName.setText("室内PM0.3浓度(个/0.01L)");
            } else if ("ozone".equals(isBabyShowType)) {
                mBabyRoomZXTName.setText("室内臭氧浓度(mg/m3)");
            }
        }

        //获取折线图数据
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("itfaceId", "109");
        hashMap2.put("token", SPUtils.get(getContext(), "token", ""));
        hashMap2.put("deviceId", id);
        hashMap2.put("type", isBabyShowType);
        JSONObject jsonObject2 = new JSONObject(hashMap2);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject2.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<BabyRoomShowBeans>(getContext()) {
                    @Override
                    public void onSuccess(BabyRoomShowBeans mBabyRoomShowBeans, Call call, Response response) {
                        if (mBabyRoomShowBeans.getResultCode() == 1) {
                            misBabyShowTypeList.clear();
                            misBabyShowTypeList.addAll(mBabyRoomShowBeans.getData());
                            mon.clear();
                            value.clear();
                            if ("pm03".equals(isBabyShowType)) {
                                getHisBillDate();
                            } else if ("ozone".equals(isBabyShowType)) {
                                getHisBillDate();
                            }
                        } else {
//                            App.ErrorToken(mBabyRoomShowBeans.getResultCode(), mBabyRoomShowBeans.getMsg());
                        }
                    }
                });
    }


    private void getHisBillDate() {
        for (int i = 0; i < misBabyShowTypeList.size(); i++) {
            mon.add(misBabyShowTypeList.get(i).getTime() + "时");
            value.add(Float.valueOf(misBabyShowTypeList.get(i).getValue()));
        }
        mBabyRoomZXTCurveChart
                .value(value)
                .xvalue(mon)
                .listener(new HorizontalLineView.OnSelectedChangedListener() {
                    @Override
                    public void onChanged(int position) {
                        Log.v("3699", position + "");
//                        mSelectedTv.setText("" + value.get(position));
                    }
                }).setIOnScrollStateListener(new HorizontalLineView.IOnScrollStateListener() {


            @Override
            public void onScrolling(int firstposition) {

            }

            @Override
            public void onScrollStop(int firstposition) {

            }

            @Override
            public void onScrollbottom() {
//                Toast.makeText(ReceiverMainActivity.this, "加载更多", Toast.LENGTH_SHORT).show();
//                for (int i = flag[0]; i < flag[0] + 2; i++) {
//                    value.add(0, (float) ((int) (Math.random() * 1000)));
//                    mon.add(0, i + "");
//                }
//                flag[0] += 3;
//                indicatorView.value(value).xvalue(mon);
//                Log.d("3699onScrollbottom", value.size() + "          " + flag[0]);
            }
        });
    }


    @Override
    public void initView() throws Exception {
        mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.BabyRoomActivity_PullToRefreshScrollView);
        mBabyRoomSize = (TextView) findViewById(R.id.BabyRoom_Size);
        mBabyRoomImgbg = (ImageView) findViewById(R.id.BabyRoomActivity_IMGBG);
        mBabyRoomImgbgs = (ImageView) findViewById(R.id.BabyRoomActivity_IMGBGS);
        mBabyRoomImg = (ImageView) findViewById(R.id.BabyRoom_Img);
        mBabyRoomContent = (TextView) findViewById(R.id.BabyRoom_Content);
        mBabyRoomActivityLinearLayout = (LinearLayout) findViewById(R.id.BabyRoomActivity_LinearLayout);
        mBabyRoomTitle = (TextView) findViewById(R.id.BabyRoom_Title);
        mBabyRoomText = (TextView) findViewById(R.id.BabyRoom_text);
        mBabyRoomWenDu = (TextView) findViewById(R.id.BabyRoom_WenDu);
        mBabyRoomShiDu = (TextView) findViewById(R.id.BabyRoom_ShiDu);
        mBabyRoomImageView = (ImageView) findViewById(R.id.BabyRoom_ImageView);
        mBabyRoomOne = (TextView) findViewById(R.id.BabyRoom_One);
        mBabyRoomTwo = (TextView) findViewById(R.id.BabyRoom_Two);
        mBabyRoomThree = (TextView) findViewById(R.id.BabyRoom_Three);
        mBabyRoomRL1 = (LinearLayout) findViewById(R.id.BabyRoom_RL1);
        mBabyRoomRL2 = (LinearLayout) findViewById(R.id.BabyRoom_RL2);
        mBabyRoomRL3 = (LinearLayout) findViewById(R.id.BabyRoom_RL3);
        mBabyRoomRL4 = (LinearLayout) findViewById(R.id.BabyRoom_RL4);
        mBabyRoomZXTCurveChart = (HorizontalLineView) findViewById(R.id.BabyRoom_ZXT_CurveChart);

        mBabyRoomZXTLL = (LinearLayout) findViewById(R.id.BabyRoom_ZXT_LL);
        mBabyRoomZXTName = (TextView) findViewById(R.id.BabyRoom_ZXT_Name);
        mBabyRoomLLBG = (LinearLayout) findViewById(R.id.BabyRoom_LL_BG);
        mBabyRoomRLBG = (RelativeLayout) findViewById(R.id.BabyRoom_RL_BG);
        mBabyRoomLLBG2 = (LinearLayout) findViewById(R.id.BabyRoom_LL_BG2);

        mBabyRoomViewPager = (ViewPager) findViewById(R.id.BabyRoom_ViewPager);
        mBabyRoomRadioGroup = (RadioGroup) findViewById(R.id.BabyRoom_RadioGroup);
        mBabyRoomRadioButton1 = (RadioButton) findViewById(R.id.BabyRoom_RadioButton1);
        mBabyRoomRadioButton2 = (RadioButton) findViewById(R.id.BabyRoom_RadioButton2);

        if (SPUtils.get(BabyRoomActivity.this, "BabyState", "").equals("0") || SPUtils.get(BabyRoomActivity.this, "BabyState", "").equals("1")) {
            mBabyRoomTitle.setText("妈妈,谢谢您帮我健康呼吸");
            mBabyRoomContent.setText("孕妈妈呼吸指数");
        } else if (SPUtils.get(BabyRoomActivity.this, "BabyState", "").equals("2")) {
            mBabyRoomTitle.setText("妈妈,谢谢您给我健康呼吸");
            String nickname = String.valueOf(SPUtils.get(BabyRoomActivity.this, "nickname", ""));
            if (nickname.length() > 3) {
                String substring = nickname.substring(0, 3);
                mBabyRoomContent.setText(substring + "呼吸指数");
            } else {
                mBabyRoomContent.setText(nickname + "呼吸指数");
            }
        }
    }

    private void initData() {
        token = String.valueOf(SPUtils.get(this, "token", ""));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "004");
        hashMap.put("token", SPUtils.get(this, "token", ""));
        hashMap.put("deviceId", id);
//        hashMap.put("deviceCode", code);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<BabyNumBeans>(this) {
                    @Override
                    public void onSuccess(BabyNumBeans mBabyNumBeans, Call call, Response response) {
                        if (mBabyNumBeans.getResultCode() == 1) {
                            temp = mBabyNumBeans.getData().getTemp();
                            humi = mBabyNumBeans.getData().getHumi();
                            pm25 = mBabyNumBeans.getData().getPm25();
                            ozone = mBabyNumBeans.getData().getOzone();
                            pm03 = mBabyNumBeans.getData().getPm03();
                            mBabyRoomWenDu.setText(mBabyNumBeans.getData().getTemp() + " ℃");
                            mBabyRoomShiDu.setText(mBabyNumBeans.getData().getHumi() + " %");
                            mBabyRoomOne.setText(mBabyNumBeans.getData().getPm25() + "");
                            mBabyRoomTwo.setText(mBabyNumBeans.getData().getOzone() + "");
                            mBabyRoomThree.setText(mBabyNumBeans.getData().getPm03() + "");
                            mBabyRoomSize.setText(mBabyNumBeans.getData().getPm25() + "");

                            Log.e("ccccc", img + "xcf");
                            if (Integer.parseInt(mBabyNumBeans.getData().getPm25()) <= 35 && Integer.parseInt(mBabyNumBeans.getData().getPm25()) > 10) {
                                mBabyRoomText.setText("优");
                                mBabyRoomImg.setVisibility(View.GONE);
                                if (!"".equals(img)) {
                                    if (Util.isOnMainThread()) {
                                        Glide.with(getContext()).load(img).into(mBabyRoomImgbg);
                                    }
                                    mBabyRoomImgbgs.setBackground(getResources().getDrawable(R.color.tpum1));
                                    mBabyRoomImgbgs.setVisibility(View.VISIBLE);
                                } else {
                                    Log.e("ccccc", "优" + "xcf");

//                                    mBabyRoomActivityLinearLayout.setBackground(getResources().getDrawable(R.drawable.controlbg1));
                                    mBabyRoomImgbg.setBackground(getResources().getDrawable(R.drawable.controlbg1));
                                    mBabyRoomImgbgs.setVisibility(View.GONE);
                                }
                            } else if (Integer.parseInt(mBabyNumBeans.getData().getPm25()) > 35 && Integer.parseInt(mBabyNumBeans.getData().getPm25()) <= 75) {
                                mBabyRoomText.setText("良");
                                mBabyRoomImg.setVisibility(View.GONE);
                                if (!"".equals(img)) {
                                    if (Util.isOnMainThread()) {
                                        Glide.with(getContext()).load(img).into(mBabyRoomImgbg);
                                    }
                                    mBabyRoomImgbgs.setBackground(getResources().getDrawable(R.color.tpum2));
                                    mBabyRoomImgbgs.setVisibility(View.VISIBLE);

                                } else {
                                    Log.e("ccccc", "良" + "xcf");

                                    mBabyRoomImgbg.setBackground(getResources().getDrawable(R.drawable.controlbg2));
                                    mBabyRoomImgbgs.setVisibility(View.GONE);
                                }
                            } else if (Integer.parseInt(mBabyNumBeans.getData().getPm25()) > 75) {
                                mBabyRoomText.setText("差");
                                mBabyRoomImg.setVisibility(View.GONE);
                                if (!"".equals(img)) {
                                    if (Util.isOnMainThread()) {
                                        Glide.with(getContext()).load(img).into(mBabyRoomImgbg);
                                    }
                                    mBabyRoomImgbgs.setBackground(getResources().getDrawable(R.color.tpum3));
                                    mBabyRoomImgbgs.setVisibility(View.VISIBLE);

                                } else {
                                    Log.e("ccccc", "差" + "xcf");

                                    mBabyRoomImgbg.setBackground(getResources().getDrawable(R.drawable.controlbg3));
                                    mBabyRoomImgbgs.setVisibility(View.GONE);
                                }
                            } else if (Integer.parseInt(mBabyNumBeans.getData().getPm25()) <= 10) {
                                mBabyRoomText.setText("优");
                                mBabyRoomImg.setVisibility(View.VISIBLE);
                                if (!"".equals(img)) {
                                    if (Util.isOnMainThread()) {
                                        Glide.with(getContext()).load(img).into(mBabyRoomImgbg);
                                    }
                                    mBabyRoomImgbgs.setBackground(getResources().getDrawable(R.color.tpum1));
                                    mBabyRoomImgbgs.setVisibility(View.VISIBLE);

                                } else {
                                    Log.e("ccccc", "优" + "xcf");
                                    mBabyRoomImgbg.setBackground(getResources().getDrawable(R.drawable.controlbg1));
                                    mBabyRoomImgbgs.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            App.ErrorToken(mBabyNumBeans.getResultCode(), mBabyNumBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
        mBabyRoomRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                resetViewPager(checkedId);
            }
        });
        //滑动ViewPage的时候及时修改底部导航栏对应的图标
        mBabyRoomViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //根据当前位置设置默认选中单选按钮
                resetRadioButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        animation = AnimationUtils.loadAnimation(this, R.anim.rotate_upload);
        LinearInterpolator lir = new LinearInterpolator();
        animation.setInterpolator(lir);
        App.dialog_animation = animation;
        animation.start();
        mBabyRoomImageView.startAnimation(animation);

        mBabyRoomRL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BabyRoomActivity.this, StrainerInformationActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("code", code);
                startActivity(intent);
            }
        });
        mBabyRoomRL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BabyRoomActivity.this, HistoryMemoryActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        mBabyRoomRL3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BabyRoomAdapters mBabyRoomAdapters;
                final List<BabyRoomDialogGridViewBeans.DataBean.CfsBean> mList2 = new ArrayList<>();
                final AlertDialog alert2;
                alert2 = new AlertDialog.Builder(BabyRoomActivity.this).create();
                alert2.show();
                //加载自定义dialog
                alert2.getWindow().setContentView(R.layout.share_dialog);
                alert2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                mShareLinearLayout = (LinearLayout) alert2.getWindow().findViewById(R.id.Share_LinearLayout);
                LinearLayout mShareOne = (LinearLayout) alert2.getWindow().findViewById(R.id.Share_One);
                LinearLayout mShareTwo = (LinearLayout) alert2.getWindow().findViewById(R.id.Share_Two);
                LinearLayout mShareThree = (LinearLayout) alert2.getWindow().findViewById(R.id.Share_Three);
                LinearLayout mShareFour = (LinearLayout) alert2.getWindow().findViewById(R.id.Share_Four);

                mShareOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //955cd17bec064540362346ad410b3f7b
                        // 95:5C:D1:7B:EC:06:45:40:36:23:46:AD:41:0B:3F:7B
                        //9c75e78fBa0419e106f87ba2e4192e53
                        IWXAPI wxApi;
                        // 微信注册初始化
                        api = WXAPIFactory.createWXAPI(BabyRoomActivity.this, "wxeca84b2f356693a2", true);
                        api.registerApp("wxeca84b2f356693a2");
                        share2weixin(0);
                        alert2.dismiss();
                    }
                });
                mShareTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        IWXAPI wxApi;
                        // 微信注册初始化
                        api = WXAPIFactory.createWXAPI(BabyRoomActivity.this, "wxeca84b2f356693a2", true);
                        api.registerApp("wxeca84b2f356693a2");
                        share2weixin(1);
                        alert2.dismiss();
                    }
                });
                mShareThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert2.dismiss();
                        Bundle params = new Bundle();
                        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                        params.putString(QQShare.SHARE_TO_QQ_TITLE, "美丽妈妈好空气");
                        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,
                                "温度：" + temp + " ℃"
                                        + " 湿度" + humi + "%"
                                        + " 臭氧" + ozone + "mg/m3"
                                        + " pm2.5:" + pm25 + "ug/m3"
                                        + " pm0.3:" + pm03 + "个/0.01L");
                        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://api.env365.cn/html/shareDevice.html?humi=" + humi + "&ozone=" + ozone + "&pm03=" + pm03 + "&pm25=" + pm25 + "&temp=" + temp);
                        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://api.env365.cn/img/share_thumbnail.png");
                        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "美丽妈妈");
                        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
                        mTencent.shareToQQ(BabyRoomActivity.this, params, BabyRoomActivity.this);
                        App.integral("sharedata", "", "分享成功积分");
                    }
                });
                mShareFour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert2.dismiss();
                        Bundle params = new Bundle();
                        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                        params.putString(QQShare.SHARE_TO_QQ_TITLE, "美丽妈妈好空气");
                        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,
                                "温度：" + temp + " ℃"
                                        + "湿度：" + humi + "%"
                                        + " 臭氧：" + ozone + "mg/m3"
                                        + " pm2.5:" + pm25 + "ug/m3"
                                        + " pm0.3:" + pm03 + "个/0.01L");
                        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://api.env365.cn/html/shareDevice.html?humi=" + humi + "&ozone=" + ozone + "&pm03=" + pm03 + "&pm25=" + pm25 + "&temp=" + temp);
                        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://api.env365.cn/img/share_thumbnail.png");
                        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "美丽妈妈");
                        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
                        mTencent.shareToQQ(BabyRoomActivity.this, params, BabyRoomActivity.this);
                        App.integral("sharedata", "", "分享成功积分");
                    }
                });
            }
        });

        mBabyRoomRL4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("fdfsdfs", code + "*********" + ownerUserId + "*********" + name + "");
                startActivity(new Intent(BabyRoomActivity.this, BabyRespiratoryReportCardActivity.class)
                        .putExtra("id", id)
                        .putExtra("ownerUserId", ownerUserId)
                        .putExtra("name", name)
                        .putExtra("babyStatus", babyStatus));
            }
        });
    }

    public void resetViewPager(int checkedId) {
        switch (checkedId) {
            case R.id.BabyRoom_RadioButton1:
                mBabyRoomViewPager.setCurrentItem(0);
                mBabyRoomRadioButton1.setBackground(getResources().getDrawable(R.drawable.radiobtn_border_orderds1));
                mBabyRoomRadioButton2.setBackground(getResources().getDrawable(R.drawable.radiobtn_border_orderds4));
                mBabyRoomRadioButton1.setTextColor(getResources().getColor(R.color.white));
                mBabyRoomRadioButton2.setTextColor(getResources().getColor(R.color.tpumss));
                break;
            case R.id.BabyRoom_RadioButton2:
                mBabyRoomViewPager.setCurrentItem(1);
                mBabyRoomRadioButton1.setBackground(getResources().getDrawable(R.drawable.radiobtn_border_orderds2));
                mBabyRoomRadioButton2.setBackground(getResources().getDrawable(R.drawable.radiobtn_border_orderds3));
                mBabyRoomRadioButton1.setTextColor(getResources().getColor(R.color.tpumss));
                mBabyRoomRadioButton2.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }

    private void resetRadioButton(int position) {
        //获取position位置处对于的单选按钮
        RadioButton radioButton = (RadioButton) mBabyRoomRadioGroup.getChildAt(position);
        //设置当前单选按钮默认选中
        radioButton.setChecked(true);
    }

    private void share2weixin(int flag) {
        mShareLinearLayout.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(mShareLinearLayout.getDrawingCache());
        mShareLinearLayout.setDrawingCacheEnabled(false);
        byte[] bytes = bitmap2Bytes(bitmap, 24);

        if (!api.isWXAppInstalled()) {
            Toast.makeText(BabyRoomActivity.this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://api.env365.cn/html/shareDevice.html?humi=" + humi + "&ozone=" + ozone + "&pm03=" + pm03 + "&pm25=" + pm25 + "&temp=" + temp;
        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = "美丽妈妈";//name名称
        msg.description = "温度：" + temp + " ℃"
                + " 湿度" + humi + "%"
                + " 臭氧" + ozone + "mg/m3"
                + " pm2.5:" + pm25 + "ug/m3"
                + " pm0.3:" + pm03 + "个/0.01L";//内容
        msg.thumbData = bytes;
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);//图片-
        msg.setThumbImage(thumb);
        //图片加载是使用的ImageLoader.loadImageSync() 同步方法
        // 并且还要创建图片的缩略图，因为微信限制了图片的大小
//        Bitmap thumbBmp = Bitmap.createScaledBitmap(ImageLoaderUtil.getBitmap(image), 200, 200, true);
//        msg.setThumbImage(thumbBmp);
//        thumbBmp.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag;
        api.sendReq(req);
        req.scene = WXSceneSession;
        App.integral("sharedata", "", "分享成功积分");
    }

    /**
     * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
     *
     * @param bitmap //     * @param IMAGE_SIZE
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb && options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        return output.toByteArray();
    }

    public void BabyRoomActivity_Bank(View view) {
        finish();
    }

    @Override
    public void onComplete(Object o) {
//        Toast.makeText(this, o.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(this, uiError.errorMessage + "--" + uiError.errorCode + "---" + uiError.errorDetail, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mTencent != null) {
            Tencent.onActivityResultData(requestCode, resultCode, data, this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (Util.isOnMainThread()) {
//            Glide.with(getContext()).pauseRequests();
//        }
    }
}
