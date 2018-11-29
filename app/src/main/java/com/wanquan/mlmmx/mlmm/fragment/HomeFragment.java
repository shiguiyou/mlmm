package com.wanquan.mlmmx.mlmm.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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
import com.wanquan.mlmmx.mlmm.activity.AntenatalTimeTableActivity;
import com.wanquan.mlmmx.mlmm.activity.BabyActivity;
import com.wanquan.mlmmx.mlmm.activity.BabyDayGrowthActivity;
import com.wanquan.mlmmx.mlmm.activity.BabyDevelopmentActivity;
import com.wanquan.mlmmx.mlmm.activity.BabyMessageActivity;
import com.wanquan.mlmmx.mlmm.activity.BabyPhonesActivity;
import com.wanquan.mlmmx.mlmm.activity.EmotionalLogActivity;
import com.wanquan.mlmmx.mlmm.activity.ExpectedConfinementActivity;
import com.wanquan.mlmmx.mlmm.activity.FetalMovementActivity;
import com.wanquan.mlmmx.mlmm.activity.GrowSeedlingsTimeActivity;
import com.wanquan.mlmmx.mlmm.activity.GrowUpAppraisalActivity;
import com.wanquan.mlmmx.mlmm.activity.GrowUpAppraisalResultActivity;
import com.wanquan.mlmmx.mlmm.activity.GrowthCurveActivity;
import com.wanquan.mlmmx.mlmm.activity.InformationActivity;
import com.wanquan.mlmmx.mlmm.activity.LoginActivity;
import com.wanquan.mlmmx.mlmm.activity.MessageActivity;
import com.wanquan.mlmmx.mlmm.activity.MessageCenterActivity;
import com.wanquan.mlmmx.mlmm.activity.MoreServiceActivity;
import com.wanquan.mlmmx.mlmm.activity.MyPrizeActivity;
import com.wanquan.mlmmx.mlmm.activity.ParentChildGameActivity;
import com.wanquan.mlmmx.mlmm.activity.ParentingEncyclopediaActivity;
import com.wanquan.mlmmx.mlmm.activity.ServiceActivity;
import com.wanquan.mlmmx.mlmm.activity.SettingActivity;
import com.wanquan.mlmmx.mlmm.activity.SettingBabyMessageActivity;
import com.wanquan.mlmmx.mlmm.activity.SignInActivity;
import com.wanquan.mlmmx.mlmm.adapter.BabyDevelopmentRecyclerViewAdapter;
import com.wanquan.mlmmx.mlmm.adapter.HomeGridViewAdapter;
import com.wanquan.mlmmx.mlmm.adapter.HomeGridViewNewAdapter;
import com.wanquan.mlmmx.mlmm.adapter.HomeListViewAdapter;
import com.wanquan.mlmmx.mlmm.beans.EmotionalLogBeans;
import com.wanquan.mlmmx.mlmm.beans.GrowUpAppraisalResultBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeFragmentSQLiteBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeFragmentSQLiteSTBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeGridViewBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeGridViewNewBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeListViewBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeQieHuanBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeSaveBeans;
import com.wanquan.mlmmx.mlmm.beans.LoginBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.PersonalinformationActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.PersonalinformationModifitionImgBeans;
import com.wanquan.mlmmx.mlmm.beans.SettingBabyMessageBeans;
import com.wanquan.mlmmx.mlmm.beans.SignBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.sqlite.DataBaseHelper;
import com.wanquan.mlmmx.mlmm.utils.JSONUtils;
import com.wanquan.mlmmx.mlmm.utils.MD5Util;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MyListView;
import com.wanquan.mlmmx.mlmm.view.SelectPicPopup;
import com.wanquan.mlmmx.mlmm.voice.WriteEmotionalActivity;
import com.wanquan.mlmmx.mlmm.xmly.activity.BabyListenActivity;
import com.wanquan.mlmmx.mlmm.activity.ParentTaskActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.MonthDay;
import org.joda.time.Months;
import org.joda.time.Years;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：首页
 * 作者：薛昌峰
 * 时间：2017/11/15.
 */
public class HomeFragment extends BaseFragment /*implements HomeGridViewNewAdapter.OnItemClickListenerInterface */ {
    private SelectPicPopup menuWindow; // 自定义的头像编辑弹出框
    /**
     * 使用照相机拍照获取图片
     */
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    /**
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
    /**
     * 获取到的图片路径
     */
    private String picPath = "";
    private Uri mPhotoUri;
    private String string = null;
    private ImageView mHomeContentImg;
    private TextView mHomeContentName;
    private TextView mHomeContentTime;
    private PullToRefreshScrollView mHomePullToRefreshScrollView;
    private RelativeLayout mHomeMessage;
    private LinearLayout mHomeFriends;
    private LinearLayout mHomeWrite;
    private CircleImageView mHomeImageView;
    private LinearLayout mHomeContentLL;
    private RelativeLayout mHomeContentRL;
    private TextView mHomeTitle;
    private ImageView mHomeSign;
    private ImageView mHomeMessageImg;
    private TextView mHomeText;
    private TextView mHomeContentTitle;
    private TextView mHomeContent;
    private LinearLayout mHomeOne;
    private LinearLayout mHomeTwo;
    private LinearLayout mHomeThree;
    private ImageView mHomeThreeImg;
    private TextView mHomeThreeTv;
    private LinearLayout mHomeFour;
    private LinearLayout mHomeFive;
    private LinearLayout mHomeSix;
    private TextView mHomeTextView;
    private MyListView mHomeListView;
    private LinearLayout mHomeLinearLayout;
    private TextView mHomeShenGao;
    private TextView mHomeTiZhong;
    private List<HomeGridViewBeans> mList2 = new ArrayList<>();
    private List<HomeListViewBeans> mList3 = new ArrayList<>();
    private List<HomeListViewBeans> mList4 = new ArrayList<>();
    private HomeListViewAdapter mHomeListViewAdapter;
    private RecyclerView mHome_GridView;
    private HomeGridViewAdapter mHomeGridViewAdapter;
    private HomeGridViewNewAdapter mHomeGridViewNewAdapter;
    //声明数据库辅助类对象
    private DataBaseHelper dataBaseHelper;
    private DataBaseHelper dataBaseHelper2;
    private DataBaseHelper dataBaseHelper3;
    private DataBaseHelper dataBaseHelper4;
    private List<HomeFragmentSQLiteBeans> mList = new ArrayList<>();
    private List<HomeFragmentSQLiteSTBeans> mListst = new ArrayList<>();
    //声明一个数据库操作类
    private SQLiteDatabase mDatabase;
    private SQLiteDatabase mDatabase2;
    private SQLiteDatabase mDatabase3;
    private SQLiteDatabase mDatabase4;
    //声明一个游标接口对象，用来遍历查询结果
    private Cursor mCursor;
    private Cursor mCursor2;
    private Cursor mCursor3;
    private Cursor mCursor4;
    private String times;
    private static final long ONE_DAY_MS = 24 * 60 * 60 * 1000;
    private View view;
    private Date d1;
    private Date d2;
    private int week;
    private int month;
    private int daySize;
    private String monthday;
    private String result;
    private int hyz_id;
    private int hyz_week;
    private String hyz_description;
    private Double hyz_height = 0.0;
    private Double hyz_weight = 0.0;
    private int days;
    private int dayAll;
    private int week_day;
    private int daytime;
    private String hyz_head;
    private String hyz_voice = "0";
    private int page = 1;
    private int weekAdd;
    private boolean w_img_b;//判断未登录状态下（备孕中）宝宝头像是否上传过
    private boolean w_img_y;//判断未登录状态下（已出生）宝宝头像是否上传过
    private boolean img_b;//判断登录状态下（备孕中）宝宝头像是否上传过
    private boolean img_y;//判断登录状态下（已出生）宝宝头像是否上传过
    private String babyHeadIco;
    private String babyDaycontent = null;//当天是否写有日志
    private String dataIntro;
    private LinearLayout mHomePlayLL;
    private ImageView mHomePlay;
    public static boolean isPlay = true;
    public static boolean isPlayStart;
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    private int initStatus;
    private String babyNickname;
    private String babyBothDay;
    private int qieHuanMonth;
    private LogoutReceiver logoutReceiver;
    private int newDay;

    @Override
    public void onResume() {
        super.onResume();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mHomePlay.setBackground(getResources().getDrawable(R.mipmap.zanting2));
            } else {
                mHomePlay.setBackground(getResources().getDrawable(R.mipmap.bofang2));
            }
        }

        initStatus();
        initData();
        initHongDian();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);

        //注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        logoutReceiver = new LogoutReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.xcf.receiver");
        getContext().registerReceiver(logoutReceiver, filter);

        initViews();
        initNetWork();
        initSetRefresh();
        initHongDian();
        initListeners();

        mHomeSign.setImageResource(R.drawable.qiandao_finish);
        AnimationDrawable animationDrawable = (AnimationDrawable) mHomeSign.getDrawable();
        animationDrawable.start();

        mHomeListViewAdapter = new HomeListViewAdapter(mList3, getContext());
        mHomeListView.setAdapter(mHomeListViewAdapter);

        return view;
    }

    private class LogoutReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.xcf.receiver")) {
                int logoutFlag = intent.getIntExtra("receiver", -1);
                Log.e("TAG", "获取的值-->" + logoutFlag);
                if (!"0".equals(logoutFlag)) {
                    mHomeMessageImg.setVisibility(View.VISIBLE);
                } else {
                    mHomeMessageImg.setVisibility(View.GONE);
                }
            }
        }
    }

    private void initHongDian() {
        //获取个人信息接口——判断是否显示红点
        Log.e("token", (String) SPUtils.get(getContext(), "token", ""));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "021");
//        if (!"".equals(SPUtils.get(getContext(), "token", ""))) {
        hashMap.put("token", SPUtils.get(getContext(), "token", ""));
//        }
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<PersonalinformationActivityBeans>(getContext()) {
                    @Override
                    public void onSuccess(PersonalinformationActivityBeans mPersonalinformationActivityBeans, Call call, Response response) {
                        if (mPersonalinformationActivityBeans.getResultCode() == 1) {
                            if (mPersonalinformationActivityBeans.getData().isHasUnReadComment()
                                    || mPersonalinformationActivityBeans.getData().isHasUnReadMsg()) {
                                mHomeMessageImg.setVisibility(View.VISIBLE);
                            } else {
                                mHomeMessageImg.setVisibility(View.GONE);
                            }
                            if (mPersonalinformationActivityBeans.getData().isHasUnReadMsg()) {
                                SPUtils.put(getContext(), "hongdianShow1", "true");
                            } else {
                                SPUtils.put(getContext(), "hongdianShow1", "false");
                            }
                            if (mPersonalinformationActivityBeans.getData().isHasUnReadComment()) {
                                SPUtils.put(getContext(), "hongdianShow2", "true");
                            } else {
                                SPUtils.put(getContext(), "hongdianShow2", "false");
                            }
                        } else {
//                            Toast.makeText(mActivity, mPersonalinformationActivityBeans.getMsg(), Toast.LENGTH_SHORT).show();
//                            App.ErrorToken(mPersonalinformationActivityBeans.getResultCode(), mPersonalinformationActivityBeans.getMsg());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                            Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initSetRefresh() {
        mHomePullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mHomePullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                initHongDian();
                initNetWork();
                initStatus();
                initData();
//                initNetWorks(1);
                mHomePullToRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page++;
                initNetWorks(page);
            }
        });
    }

    private void initNetWorks(int page) {
        OkGo.get(UrlContent.URLZ).tag(this)
                .params("pageNum", page)
                .params("numberPage", 10)
                .params("keyValue", initStatus)
                .connTimeOut(10000)
                .execute(new CustomCallBackNoLoading<String>(getContext()) {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
                        List<HomeListViewBeans> data = (List<HomeListViewBeans>) JSONUtils.parseToJavaList(result, HomeListViewBeans.class);
                        if (data.size() != 0) {
                            mList3.addAll(data);
                            mHomeListViewAdapter.setData(mList3);
                            mHomePullToRefreshScrollView.onRefreshComplete();
                            Toast.makeText(getContext(), "加载成功!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "亲，你触碰到我的底线啦~", Toast.LENGTH_SHORT).show();
                            mHomePullToRefreshScrollView.onRefreshComplete();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                            Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initNetWork() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("keyValue", initStatus);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URLZ).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10000)
                .execute(new CustomCallBackNoLoading<String>(getContext()) {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
                        List<HomeListViewBeans> data = (List<HomeListViewBeans>) JSONUtils.parseToJavaList(result, HomeListViewBeans.class);
                        mList3.clear();
                        mList3.addAll(data);
                        mHomeListViewAdapter.setData(mList3);
                        mHomePullToRefreshScrollView.onRefreshComplete();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                            Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initStatus() {
        if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
            if (SPUtils.get(getContext(), "BabyState", "").equals("1")) {
                initStatus = 1;
                mHomeContentLL.setVisibility(View.VISIBLE);
//                Log.e("fsfasfasfasf", String.valueOf(SPUtils.get(getActivity(), "timeh", "")));
                String startTime = String.valueOf(SPUtils.get(getActivity(), "timeh", ""));
                initTime(startTime, 1);
            } else if (SPUtils.get(getContext(), "BabyState", "").equals("2")) {
                initStatus = 2;
                mHomeContentLL.setVisibility(View.VISIBLE);
                String startTime = String.valueOf(SPUtils.get(getActivity(), "timey", ""));
                initTime(startTime, 2);
            } else if (SPUtils.get(getContext(), "BabyState", "").equals("0")) {
                initStatus = 0;
            }
        } else {
            if (SPUtils.get(getContext(), "w_BabyState", "").equals("1")) {
                initStatus = 1;
                mHomeContentLL.setVisibility(View.VISIBLE);
                String startTime = String.valueOf(SPUtils.get(getActivity(), "w_timeh", ""));
                initTime(startTime, 1);
            } else if (SPUtils.get(getContext(), "w_BabyState", "").equals("2")) {
                initStatus = 2;
                mHomeContentLL.setVisibility(View.VISIBLE);
                String startTime = String.valueOf(SPUtils.get(getActivity(), "w_timey", ""));
                initTime(startTime, 2);
            } else if (SPUtils.get(getContext(), "w_BabyState", "").equals("0")) {
                initStatus = 0;
            }
        }
    }

    private void initSQLite() {
        Log.e("dsdad", "到了到了到了到了");

        weekAdd = week + 1;
        dataBaseHelper = new DataBaseHelper(getContext());
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        dataBaseHelper.openDataBase();

        //发育信息(已出生)
        mDatabase = dataBaseHelper.getReadableDatabase();
        mList.clear();
        String querySQL = "select * from growth where week = " + weekAdd;
        mCursor = mDatabase.rawQuery(querySQL, null);

        Log.e("dsdad2", querySQL + "fffffsfdfdo");
        Log.e("dsdad3", mCursor + "fffffsfdfdo");

        if (weekAdd > 119) {
            dataIntro = "";
        }


        while (mCursor.moveToNext()) {
            int id = mCursor.getInt(mCursor.getColumnIndex("id"));
            int week = mCursor.getInt(mCursor.getColumnIndex("week"));
            String key = mCursor.getString(mCursor.getColumnIndex("key"));
            String dataTitle = mCursor.getString(mCursor.getColumnIndex("dataTitle"));
            dataIntro = mCursor.getString(mCursor.getColumnIndex("dataIntro"));

            Log.e("dsdad", dataIntro + "fffffsfdfdo");
        }

        mCursor.close();
        mDatabase.close();
        dataBaseHelper.close();
        //======================================
        //身高体重
        dataBaseHelper2 = new DataBaseHelper(getContext());
        try {
            dataBaseHelper2.createDataBase();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        dataBaseHelper2.openDataBase();
        mDatabase2 = dataBaseHelper2.getReadableDatabase();
        mListst.clear();
        String querySQL2 = "select * from growth_criteria where week = " + weekAdd;
        mCursor2 = mDatabase2.rawQuery(querySQL2, null);
        while (mCursor2.moveToNext()) {
            int sqlid = mCursor2.getInt(mCursor2.getColumnIndex("id"));
            int sqlweek = mCursor2.getInt(mCursor2.getColumnIndex("week"));
            String sqlsex = mCursor2.getString(mCursor2.getColumnIndex("sex"));
            Double sqlmin_height = mCursor2.getDouble(mCursor2.getColumnIndex("min_height"));
            Double sqlmax_height = mCursor2.getDouble(mCursor2.getColumnIndex("max_height"));
            Double sqlmin_weight = mCursor2.getDouble(mCursor2.getColumnIndex("min_weight"));
            Double sqlmax_weight = mCursor2.getDouble(mCursor2.getColumnIndex("max_weight"));
            HomeFragmentSQLiteSTBeans homeFragmentSQLiteSTBeans = new HomeFragmentSQLiteSTBeans(sqlid, sqlweek, sqlsex, sqlmin_height, sqlmax_height, sqlmin_weight, sqlmax_weight);
            mListst.add(homeFragmentSQLiteSTBeans);
        }
        mCursor2.close();
        mDatabase2.close();
        dataBaseHelper2.close();
        //======================================
        //怀孕中
        int newDatTime = daytime + 1;
        if (newDatTime >= 280) {
            newDatTime = 280;
        }
//        Log.e("hyz_newDatTime", newDatTime + "");
        dataBaseHelper3 = new DataBaseHelper(getContext());
        try {
            dataBaseHelper3.createDataBase();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        dataBaseHelper3.openDataBase();
        mDatabase3 = dataBaseHelper3.getReadableDatabase();
        String querySQL3 = "select * from pregnancy where day = " + newDatTime;
        mCursor3 = mDatabase3.rawQuery(querySQL3, null);
        hyz_week = 0;
        while (mCursor3.moveToNext()) {
            hyz_id = mCursor3.getInt(mCursor3.getColumnIndex("id"));
            hyz_week = mCursor3.getInt(mCursor3.getColumnIndex("day"));
            hyz_description = mCursor3.getString(mCursor3.getColumnIndex("description"));
            hyz_height = mCursor3.getDouble(mCursor3.getColumnIndex("height"));
            hyz_weight = mCursor3.getDouble(mCursor3.getColumnIndex("weight"));
            hyz_head = mCursor3.getString(mCursor3.getColumnIndex("head"));
            hyz_voice = mCursor3.getString(mCursor3.getColumnIndex("voice"));
            Log.e("hyz_id", hyz_id + "");
            Log.e("hyz_week", hyz_week + "");
            Log.e("hyz_description", hyz_description + "");
            Log.e("hyz_height", hyz_height + "");
            Log.e("hyz_weight", hyz_weight + "");
            Log.e("hyz_head", hyz_head + "");
            Log.e("hyz_voice", hyz_voice + "");
        }
        mCursor3.close();
        mDatabase3.close();
        dataBaseHelper3.close();
    }

    private void initTime(String startTime, int babyState) {
        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        times = formatter.format(curDate);
        //当前时间
        DateTime now = DateTime.now();
        String startTimes = times;
        if (startTime.contains(" ")) {
            startTimes = startTime.substring(0, startTime.indexOf(" "));
        } else {
            startTimes = startTime;
        }
//        Log.e("fsfasfasfasf1", String.valueOf(startTime));
//        Log.e("fsfasfasfasf2", String.valueOf(startTimes));

        DateTime dateTime = DateTime.parse(startTimes);//设置的时间
        DateTime nows = dateTime.minusDays(280);//向前推280

        if (babyState == 1) {
            month = Months.monthsBetween(nows, dateTime).getMonths();//月
            dayAll = Days.daysBetween(nows, dateTime).getDays();//总天
            daytime = Days.daysBetween(nows, now).getDays();//已怀孕天
            days = Days.daysBetween(now, dateTime).getDays();//距离出生日
        } else if (babyState == 2) {
            month = Months.monthsBetween(dateTime, now).getMonths();//宝宝出生月
            days = Days.daysBetween(dateTime, now).getDays();//宝宝出生天
            DateTime tmp = dateTime.plusMonths(month); //月的天
            monthday = String.valueOf(Days.daysBetween(tmp, now).getDays());//月的天

            if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                newDay = Integer.parseInt(monthday) + 1;
            } else {
                newDay = Integer.parseInt(monthday);
            }
            monthday = String.valueOf(newDay);
        }
        if (babyState == 1) {
            week = +daytime / 7;//周
            week_day = +daytime % 7 + 1;//周余天
        } else if (babyState == 2) {
            week = +days / 7;//周
            week_day = +days % 7 + 1;//周余天
        }
        initSQLite();
//        Log.e("dddd总月", String.valueOf(month));
//        Log.e("dddd总天", String.valueOf(dayAll));
//        Log.e("dddd周余天", String.valueOf(week_day));
//        Log.e("dddd周", String.valueOf(week));
//        Log.e("dddd月余天", String.valueOf(monthday));
//        Log.e("dddd距离出生日", String.valueOf(days));
    }

    private void initViews() {
        mHomeSign = (ImageView) view.findViewById(R.id.Home_Sign);
        mHomeMessageImg = (ImageView) view.findViewById(R.id.Home_Message_img);
        mHomePullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.Home_PullToRefreshScrollView);
        mHomeMessage = (RelativeLayout) view.findViewById(R.id.Home_Message);
        mHomeFriends = (LinearLayout) view.findViewById(R.id.Home_Friends);
        mHomeWrite = (LinearLayout) view.findViewById(R.id.Home_Write);
        mHomeContentLL = (LinearLayout) view.findViewById(R.id.Home_Content_LL);
        mHomeContentRL = (RelativeLayout) view.findViewById(R.id.Home_Content_RL);
        mHomeImageView = (CircleImageView) view.findViewById(R.id.Home_ImageView);
        mHomeTitle = (TextView) view.findViewById(R.id.Home_Title);
        mHomeText = (TextView) view.findViewById(R.id.Home_Text);
        mHomeContentTitle = (TextView) view.findViewById(R.id.Home_Content_title);
        mHomeContent = (TextView) view.findViewById(R.id.Home_Content);
        mHomeTextView = (TextView) view.findViewById(R.id.Home_TextView);
        mHome_GridView = (RecyclerView) view.findViewById(R.id.Home_GridView);
        mHomeListView = (MyListView) view.findViewById(R.id.Home_ListView);
        mHomeLinearLayout = (LinearLayout) view.findViewById(R.id.Home_LinearLayout);
        mHomeShenGao = (TextView) view.findViewById(R.id.Home_ShenGao);
        mHomeTiZhong = (TextView) view.findViewById(R.id.Home_TiZhong);
        mHomeContentImg = (ImageView) view.findViewById(R.id.Home_Content_Img);
        mHomeContentName = (TextView) view.findViewById(R.id.Home_Content_Name);
        mHomeContentTime = (TextView) view.findViewById(R.id.Home_Content_Time);
        mHomePlayLL = (LinearLayout) view.findViewById(R.id.Home_Play_LL);
        mHomePlay = (ImageView) view.findViewById(R.id.Home_Play);
    }

    private void initData() {
//        Log.e("rrrrr登录状态1", "宝宝状态：" + String.valueOf(SPUtils.get(getContext(), "BabyState", "")));
//        Log.e("rrrrr未登录状态1", "宝宝状态：" + String.valueOf(SPUtils.get(getContext(), "w_BabyState", "")));
        mList2.clear();
        if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
            //获取当天是否写有新写的日志
            //获取当前时间
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            times = formatter.format(curDate);
            String time = times.substring(0, times.indexOf(" "));

            HashMap<String, Object> hashMap2 = new HashMap<>();
            hashMap2.put("itfaceId", "042");
            hashMap2.put("token", String.valueOf(SPUtils.get(getContext(), "token", "")));
            hashMap2.put("createTime", time);
            JSONObject jsonObject2 = new JSONObject(hashMap2);

            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject2.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<EmotionalLogBeans>(getContext()) {
                        @Override
                        public void onSuccess(EmotionalLogBeans mEmotionalLogBeans, Call call, Response response) {
                            if (mEmotionalLogBeans.getResultCode() == 1) {
                                babyDaycontent = null;
                                if (mEmotionalLogBeans.getData().size() > 0) {
                                    for (int i = 0; i < mEmotionalLogBeans.getData().size(); i++) {
                                        babyDaycontent = mEmotionalLogBeans.getData().get(i).getContent();
                                        if (babyDaycontent != null) {
                                            break;
                                        }
                                    }
                                }
                                if (SPUtils.get(getContext(), "BabyState", "").equals("0")) {
                                    if (babyDaycontent != null) {
                                        mHomeContentLL.setVisibility(View.VISIBLE);
                                        mHomeContentTitle.setText("日志 ：");
                                        mHomeContent.setText("            " + babyDaycontent);
                                    } else {
                                        mHomeContentLL.setVisibility(View.GONE);
                                    }
                                } else if (SPUtils.get(getContext(), "BabyState", "").equals("1")) {
                                    mHomeContentLL.setVisibility(View.VISIBLE);
                                    if (babyDaycontent != null) {
                                        mHomeContentTitle.setText("日志 ：");
                                        mHomeContent.setText("            " + babyDaycontent);
                                    } else {
                                        if (hyz_description != null) {
                                            mHomeContentTitle.setText("今日宝宝发育变化 ：");
                                            mHomeContent.setText("                                     " + hyz_description);
                                        }
                                    }
                                } else if (SPUtils.get(getContext(), "BabyState", "").equals("2")) {
                                    mHomeContentLL.setVisibility(View.VISIBLE);
                                    if (babyDaycontent != null) {
                                        mHomeContentTitle.setText("日志 ：");
                                        mHomeContent.setText("            " + babyDaycontent);
                                    } else {
                                        if (!"".equals(dataIntro)) {
                                            mHomeContentTitle.setText("今日宝宝发育变化 ：");
                                            mHomeContent.setText("                                     " + dataIntro);
                                        } else {
                                            mHomeContentTitle.setText("");
                                            mHomeContent.setText("");
                                        }
                                    }
                                }
                            } else {
                                Log.e("xcf_log", "042");
                                App.ErrorToken(mEmotionalLogBeans.getResultCode(), mEmotionalLogBeans.getMsg());
                            }
                        }
                    });
            if (SPUtils.get(getContext(), "BabyState", "").equals("1")) {
                if (hyz_voice != null) {
                    if (hyz_week <= 21 || hyz_week >= 279) {
                        mHomePlayLL.setVisibility(View.GONE);
                    } else {
                        mHomePlayLL.setVisibility(View.VISIBLE);
                    }
                }
                if (days <= 7) {
                    mHomeContentRL.setVisibility(View.VISIBLE);
                } else {
                    mHomeContentRL.setVisibility(View.GONE);
                }
//                Log.e("dddddddid", String.valueOf(SPUtils.get(getContext(), "babyId", "")));
                if (!"".equals(String.valueOf(SPUtils.get(getContext(), "babyId", "")))) {
//                    Log.e("ddddddd", String.valueOf(SPUtils.get(getContext(), "token", "")));
                    //获取切换后的宝宝状态信息
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("itfaceId", "033");
                    hashMap.put("token", SPUtils.get(getContext(), "token", ""));
                    hashMap.put("id", SPUtils.get(getContext(), "babyId", ""));
                    JSONObject jsonObject = new JSONObject(hashMap);

                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<HomeQieHuanBeans>(getContext()) {
                                @Override
                                public void onSuccess(HomeQieHuanBeans mHomeQieHuanBeans, Call call, Response response) {
                                    if (mHomeQieHuanBeans.getResultCode() == 1) {
                                        if (!"".equals(mHomeQieHuanBeans.getData().getBabyHeadIco())) {
                                            Glide.with(getContext()).load(mHomeQieHuanBeans.getData().getBabyHeadIco()).into(mHomeImageView);
                                        } /*else {
                                            mHomeImageView.setBackground(getResources().getDrawable(R.mipmap.ic_launcher));
                                        }*/
                                        //计算宝宝怀孕天数
                                        String QieHuanTime = "";
                                        if (mHomeQieHuanBeans.getData().getPreChildDate().contains(" ")) {
                                            QieHuanTime = mHomeQieHuanBeans.getData().getPreChildDate().substring(0, mHomeQieHuanBeans.getData().getPreChildDate().indexOf(" "));
                                        } else {
                                            QieHuanTime = mHomeQieHuanBeans.getData().getPreChildDate();
                                        }
                                        initTime(QieHuanTime, 1);
                                        Glide.with(getContext()).load("http://api.env365.cn/img/head/" + hyz_head).into(mHomeImageView);
//                                        if (!QieHuanTime.equals("")) {
//                                            //当前时间
//                                            DateTime now = DateTime.now();
//                                            DateTime dateTime = DateTime.parse(QieHuanTime);//设置的时间
//                                            int QieHuanDayTime = Days.daysBetween(now, dateTime).getDays();//已怀孕天
//                                            int QirHuanWeek = +QieHuanDayTime / 7;//周
//                                            int QirHuanWeek_day = +QieHuanDayTime % 7 + 1;//周余天
//                                            Log.e("预产期天数", String.valueOf(QieHuanDayTime));
//
//                                            if (QirHuanWeek_day == 0) {
//                                                mHomeTitle.setText("孕" + week + "周");
//                                            } else if (QirHuanWeek_day == 7) {
//                                                int weekjia = week + 1;
//                                                mHomeTitle.setText("孕" + weekjia + "周");
//                                            } else {
//                                                mHomeTitle.setText("孕" + week + "周" + week_day + "天");
//                                            }
//                                            mHomeText.setText("距离出生还有" + QieHuanDayTime + "天");
//                                        }
                                    } else {
                                        Log.e("xcf_log", "033_1");
                                        App.ErrorToken(mHomeQieHuanBeans.getResultCode(), mHomeQieHuanBeans.getMsg());
                                    }
                                }
                            });
                } else {
                    //Log.e("首页怀孕中宝宝头像",hyz_head);
                    Glide.with(getContext()).load("http://api.env365.cn/img/head/" + hyz_head).into(mHomeImageView);
                    Log.e("预产期天数", String.valueOf(week_day));
//                    if (week_day == 0) {
//                        mHomeTitle.setText("孕" + week + "周");
//                    } else if (week_day == 7) {
//                        int weekjia = week + 1;
//                        mHomeTitle.setText("孕" + weekjia + "周");
//                    } else {
//                        mHomeTitle.setText("孕" + week + "周" + week_day + "天");
//                    }
//                    mHomeText.setText("距离出生还有" + days + "天");
                }
                if (week_day == 0) {
                    mHomeTitle.setText("孕" + week + "周");
                } else if (week_day == 7) {
                    int weekjia = week + 1;
                    mHomeTitle.setText("孕" + weekjia + "周");
                } else {
                    if (week == 0) {
                        mHomeTitle.setText("孕" + week_day + "天");
                    } else {
                        mHomeTitle.setText("孕" + week + "周" + week_day + "天");
                    }
                }
                if (days <= 0) {
                    mHomeText.setText("距离出生还有" + 0 + "天");
                } else {
                    mHomeText.setText("距离出生还有" + days + "天");
                }

                if (hyz_height == 0 && hyz_weight == 0) {
                    mHomeLinearLayout.setVisibility(View.GONE);
                } else {
                    mHomeLinearLayout.setVisibility(View.VISIBLE);
                    mHomeShenGao.setText(String.valueOf(hyz_height) + " mm");
                    if (hyz_weight != null) {
                        mHomeTiZhong.setText(String.valueOf(hyz_weight) + " g");
                    }
                }

                initLieBiao(1);
            } else if (SPUtils.get(getContext(), "BabyState", "").equals("2")) {
                mHomePlayLL.setVisibility(View.GONE);
                mHomeContentRL.setVisibility(View.GONE);
                mHomeLinearLayout.setVisibility(View.GONE);

                if (!"".equals(SPUtils.get(getContext(), "babyId", ""))) {
//                    Log.e("dddddd", String.valueOf(SPUtils.get(getContext(), "userid", "")));
                    Log.e("gggggggggggg", String.valueOf(SPUtils.get(getContext(), "babyId", "")));
                    //获取切换后的宝宝状态信息
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("itfaceId", "033");
                    hashMap.put("token", SPUtils.get(getContext(), "token", ""));
                    hashMap.put("id", SPUtils.get(getContext(), "babyId", ""));
                    JSONObject jsonObject = new JSONObject(hashMap);

                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<HomeQieHuanBeans>(getContext()) {
                                @Override
                                public void onSuccess(HomeQieHuanBeans mHomeQieHuanBeans, Call call, Response response) {
                                    if (mHomeQieHuanBeans.getResultCode() == 1) {
                                        babyNickname = mHomeQieHuanBeans.getData().getBabyNickname();
//                                        if (!"".equals(mHomeQieHuanBeans.getData().getBabyHeadIco())){
//                                            Glide.with(getContext()).load(mHomeQieHuanBeans.getData().getBabyHeadIco()).into(mHomeImageView);
//                                        }else {
//                                            mHomeImageView.setImageDrawable(getResources().getDrawable(R.mipmap.babyavalatar));
//                                        }
//                                        if (SPUtils.get(getContext(), "icon_y", "").equals("true")) {
//                                            Glide.with(getContext()).load(mHomeQieHuanBeans.getData().getBabyHeadIco()).into(mHomeImageView);
//                                        } else {
//                                            mHomeImageView.setImageDrawable(getResources().getDrawable(R.mipmap.babyavalatar));
//                                        }
                                        //获取切换后的宝宝状态信息
                                        if (!"".equals(mHomeQieHuanBeans.getData().getBabyHeadIco())) {
                                            Log.e("gggggggggggg头像", "获取到头像");
                                            Glide.with(getContext()).load(mHomeQieHuanBeans.getData().getBabyHeadIco()).into(mHomeImageView);
                                        } else {
                                            Log.e("gggggggggggg头像", "默认头像");
                                            mHomeImageView.setImageDrawable(getResources().getDrawable(R.mipmap.babyavalatar));
                                        }
                                        //当前时间
                                        String QieHuanMonthday;
                                        DateTime now = DateTime.now();
                                        //计算宝宝已出生天数
                                        String QieHuanDay;
                                        if (mHomeQieHuanBeans.getData().getChildBirthDate().contains(" ")) {
                                            QieHuanDay = mHomeQieHuanBeans.getData().getChildBirthDate().substring(0, mHomeQieHuanBeans.getData().getChildBirthDate().indexOf(" "));
                                        } else {
                                            QieHuanDay = mHomeQieHuanBeans.getData().getChildBirthDate();
                                        }
                                        if (!QieHuanDay.equals("")) {
                                            DateTime dateTime = DateTime.parse(QieHuanDay);//设置的时间

//                                            int QieHuanYears = Years.yearsBetween(dateTime, now).getYears();//宝宝出生年
//                                            Log.e("cvcvcvc年", "" + QieHuanYears);
//                                            MonthDay monthDay = new MonthDay();
//                                            MonthDay monthDay1 = monthDay.plusMonths(QieHuanYears);
//                                            Log.e("cvcvcvc年2", "" + monthDay1);

                                            //宝宝出生月
                                            qieHuanMonth = Months.monthsBetween(dateTime, now).getMonths();

                                            DateTime tmp = dateTime.plusMonths(qieHuanMonth); //月的天
                                            QieHuanMonthday = String.valueOf(Days.daysBetween(tmp, now).getDays());//月的天
//                                            Log.e("fdfsdfsd", String.valueOf(qieHuanMonth));
//                                            Log.e("fdfsdfsd", String.valueOf(QieHuanMonthday));
                                            int newDay = Integer.parseInt(QieHuanMonthday);
                                            QieHuanMonthday = String.valueOf(newDay);

                                            if (qieHuanMonth == 0 && QieHuanMonthday.equals("0")) {
                                                mHomeTitle.setText("宝宝今天出生");
                                                babyBothDay = "出生";
                                            } else if (qieHuanMonth == 0 && !QieHuanMonthday.equals("0")) {
                                                mHomeTitle.setText("宝宝" + QieHuanMonthday + "天");
                                                babyBothDay = QieHuanMonthday + "天";
                                            } else if (qieHuanMonth < 12 && !QieHuanMonthday.equals("0")) {
                                                mHomeTitle.setText("宝宝" + qieHuanMonth + "个月" + QieHuanMonthday + "天");
                                                babyBothDay = qieHuanMonth + "个月" + QieHuanMonthday + "天";
                                            } else if (qieHuanMonth < 12 && QieHuanMonthday.equals("0")) {
                                                mHomeTitle.setText("宝宝" + qieHuanMonth + "个月");
                                                babyBothDay = qieHuanMonth + "个月";
                                            } else if (qieHuanMonth > 12 && QieHuanMonthday.equals("0")) {
                                                int month1 = month / 12;
                                                int month2 = month % 12;
                                                if (month2 == 0) {
                                                    mHomeTitle.setText("宝宝" + month1 + "岁");
                                                    babyBothDay = month1 + "岁";
                                                } else {
                                                    mHomeTitle.setText("宝宝" + month1 + "岁" + month2 + "个月");
                                                    babyBothDay = month1 + "岁" + month2 + "个月";
                                                }
                                            } else if (qieHuanMonth > 12 && !QieHuanMonthday.equals("0")) {
                                                int month1 = month / 12;
                                                int month2 = month % 12;
                                                if (month2 == 0) {
                                                    mHomeTitle.setText("宝宝" + month1 + "岁" + QieHuanMonthday + "天");
                                                    babyBothDay = month1 + "岁" + QieHuanMonthday + "天";
                                                } else {
                                                    mHomeTitle.setText("宝宝" + month1 + "岁" + month2 + "个月" + QieHuanMonthday + "天");
                                                    babyBothDay = month1 + "岁" + month2 + "个月" + QieHuanMonthday + "天";
                                                }
                                            }
                                        }
                                    } else {
                                        Log.e("xcf_log", "033_2");
                                        App.ErrorToken(mHomeQieHuanBeans.getResultCode(), mHomeQieHuanBeans.getMsg());
                                    }
                                }
                            });
                } else {
                    if (month == 0 && monthday.equals("0")) {
                        mHomeTitle.setText("宝宝" + 1 + "天啦");
                    } else if (month == 0 && !monthday.equals("0")) {
                        mHomeTitle.setText("宝宝" + monthday + "天啦");
                    } else if (month != 0 && !monthday.equals("0")) {
                        int year = month / 12;
                        int month1 = month % 12;
                        if (year != 0 && month != 0) {
                            mHomeTitle.setText("宝宝" + year + "岁" + month1 + "个月" + monthday + "天啦");
                        } else {
                            mHomeTitle.setText("宝宝" + year + "岁" + monthday + "天啦");
                        }
                    } else if (month != 0 && monthday.equals("0")) {
                        int month1 = month / 12;
                        mHomeTitle.setText("宝宝" + month1 + "个月");
                    }
                }
                if (mListst.size() != 0) {
                    for (int i = 0; i < mListst.size(); i++) {
                        if (mListst.get(i).getSex().equals("F")) {
                            mHomeText.setText("身长：" + mListst.get(1).getMin_height() + "~" + mListst.get(1).getMax_height() + " cm" + "      体重：" + mListst.get(1).getMin_weight() + "~" + mListst.get(1).getMax_weight() + " kg");
                        } else {
                            mHomeText.setText("身长：" + mListst.get(0).getMin_height() + "~" + mListst.get(0).getMax_height() + " cm" + "      体重：" + mListst.get(0).getMin_weight() + "~" + mListst.get(0).getMax_weight() + " kg");
                        }
                    }
                } else {
                    mHomeText.setText("");
                }

                initLieBiao(2);
            } else if (SPUtils.get(getContext(), "BabyState", "").equals("0")) {
                mHomePlayLL.setVisibility(View.GONE);
                mHomeContentRL.setVisibility(View.GONE);
//                if (SPUtils.get(getContext(), "icon_b", "").equals("true")) {
                Log.e("gggggggggggg", String.valueOf(SPUtils.get(getContext(), "babyId", "")));

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("itfaceId", "033");
                hashMap.put("token", SPUtils.get(getContext(), "token", ""));
                hashMap.put("id", SPUtils.get(getContext(), "babyId", ""));
                JSONObject jsonObject = new JSONObject(hashMap);

                OkGo.post(UrlContent.URL).tag(this)
                        .upJson(jsonObject.toString())
                        .connTimeOut(10_000)
                        .execute(new CustomCallBackNoLoading<SettingBabyMessageBeans>(getContext()) {
                            @Override
                            public void onSuccess(SettingBabyMessageBeans mSettingBabyMessageBeans, Call call, Response response) {
                                if (mSettingBabyMessageBeans.getResultCode() == 1) {
                                    if (!"".equals(mSettingBabyMessageBeans.getData().getBabyHeadIco())) {
                                        Glide.with(getContext()).load(mSettingBabyMessageBeans.getData().getBabyHeadIco()).into(mHomeImageView);
                                    } else {
                                        mHomeImageView.setImageDrawable(getResources().getDrawable(R.mipmap.sz));
                                    }
                                } else {
                                    Log.e("xcf_log", "033_22");
                                    App.ErrorToken(mSettingBabyMessageBeans.getResultCode(), mSettingBabyMessageBeans.getMsg());
                                }
                            }
                        });
//                } else {
//                    mHomeImageView.setImageDrawable(getResources().getDrawable(R.mipmap.sz));
//                }
                mHomeTitle.setText("备孕中");
                mHomeText.setText("我已经怀孕啦>>");
                initLieBiao(0);
            }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        } else {
            if (SPUtils.get(getContext(), "w_BabyState", "").equals("1")) {
                if (hyz_voice != null) {
                    if (hyz_week <= 21 || hyz_week >= 279) {
                        mHomePlayLL.setVisibility(View.GONE);
                    } else {
                        mHomePlayLL.setVisibility(View.VISIBLE);
                    }
                }
                mHomeContentLL.setVisibility(View.VISIBLE);
                if (days <= 7) {
                    mHomeContentRL.setVisibility(View.VISIBLE);
                } else {
                    mHomeContentRL.setVisibility(View.GONE);
                }
                Glide.with(getContext()).load("http://api.env365.cn/img/head/" + hyz_head).into(mHomeImageView);
                if (week_day == 0) {
                    mHomeTitle.setText("孕" + week + "周");
                } else if (week_day == 7) {
                    int weekjia = week + 1;
                    mHomeTitle.setText("孕" + weekjia + "周");
                } else {
                    if (week == 0) {
                        mHomeTitle.setText("孕" + week_day + "天");
                    } else {
                        mHomeTitle.setText("孕" + week + "周" + week_day + "天");
                    }
                }
                if (days <= 0) {
                    mHomeText.setText("距离出生还有" + 0 + "天");
                } else {
                    mHomeText.setText("距离出生还有" + days + "天");
                }
                if (babyDaycontent != null) {
                    mHomeContentTitle.setText("日志 ：");
                    mHomeContent.setText("            " + babyDaycontent);
                } else {
                    if (hyz_description != null) {
                        mHomeContentTitle.setText("今日宝宝发育变化 ：");
                        mHomeContent.setText("                                     " + hyz_description);
                    }
                }
                if (hyz_height == 0 && hyz_weight == 0) {
                    mHomeLinearLayout.setVisibility(View.GONE);
                } else {
                    mHomeLinearLayout.setVisibility(View.VISIBLE);
                    mHomeShenGao.setText(String.valueOf(hyz_height) + " mm");
                    if (hyz_weight != null) {
                        mHomeTiZhong.setText(String.valueOf(hyz_weight) + " g");
                    }
                }

                //退出登录后将之前的数据存储赋值
                if (!"".equals(SPUtils.get(getContext(), "GJSet", ""))) {
                    String str = String.valueOf(SPUtils.get(getContext(), "GJId", ""));
                    String[] temp = str.split(",");
                    App.mList5.clear();
                    for (int i = 0; i < temp.length; i++) {
                        initSetExitData(temp[i].toString());
                    }
                } else {
                    App.mList5.clear();
                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss1 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss1.setId("1");
                    mHomeGridViewNewBeanss1.setName("美丽相册");
                    App.mList5.add(mHomeGridViewNewBeanss1);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss2 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss2.setId("2");
                    mHomeGridViewNewBeanss2.setName("情感日志");
                    App.mList5.add(mHomeGridViewNewBeanss2);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss3 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss3.setId("3");
                    mHomeGridViewNewBeanss3.setName("发育变化");
                    App.mList5.add(mHomeGridViewNewBeanss3);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss4 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss4.setId("4");
                    mHomeGridViewNewBeanss4.setName("宝宝听听");
                    App.mList5.add(mHomeGridViewNewBeanss4);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss5 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss5.setId("5");
                    mHomeGridViewNewBeanss5.setName("亲子游戏");
                    App.mList5.add(mHomeGridViewNewBeanss5);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss6 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss6.setId("6");
                    mHomeGridViewNewBeanss6.setName("数胎动");
                    App.mList5.add(mHomeGridViewNewBeanss6);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss7 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss7.setId("7");
                    mHomeGridViewNewBeanss7.setName("产检提醒");
                    App.mList5.add(mHomeGridViewNewBeanss7);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss8 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss8.setId("8");
                    mHomeGridViewNewBeanss8.setName("孕期食谱");
                    App.mList5.add(mHomeGridViewNewBeanss8);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss9 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss9.setId("9");
                    mHomeGridViewNewBeanss9.setName("看懂B超单");
                    App.mList5.add(mHomeGridViewNewBeanss9);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss10 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss10.setId("14");
                    mHomeGridViewNewBeanss10.setName("能不能吃");
                    App.mList5.add(mHomeGridViewNewBeanss10);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss11 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss11.setId("16");
                    mHomeGridViewNewBeanss11.setName("知识百科");
                    App.mList5.add(mHomeGridViewNewBeanss11);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss12 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss12.setId("100");
                    mHomeGridViewNewBeanss12.setName("更多");
                    App.mList5.add(mHomeGridViewNewBeanss12);

                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mHome_GridView.setLayoutManager(linearLayoutManager);

                mHomeGridViewNewAdapter = new HomeGridViewNewAdapter(App.mList5, getContext());
                mHome_GridView.setAdapter(mHomeGridViewNewAdapter);

            } else if (SPUtils.get(getContext(), "w_BabyState", "").equals("2")) {
                mHomePlayLL.setVisibility(View.GONE);
                mHomeContentRL.setVisibility(View.GONE);
                mHomeContentLL.setVisibility(View.VISIBLE);
                mHomeLinearLayout.setVisibility(View.GONE);

                if (SPUtils.get(getContext(), "icon_y", "").equals("true")) {
                    Glide.with(getContext()).load(SPUtils.get(getContext(), "w_img_y", "")).into(mHomeImageView);
                } else {
                    mHomeImageView.setImageDrawable(getResources().getDrawable(R.mipmap.babyavalatar));
                }
//                if (month == 0 && monthday.equals("0")) {
//                    mHomeTitle.setText("宝宝" + 1 + "天啦");
//                } else if (month == 0 && !monthday.equals("0")) {
//                    mHomeTitle.setText("宝宝" + monthday + "天啦");
//                } else if (month != 0 && !monthday.equals("0")) {
//                    int year = month / 12;
//                    int month1 = month % 12;
//                    if (year != 0 && month != 0) {
//                        mHomeTitle.setText("宝宝" + year + "岁" + month1 + "个月" + monthday + "天啦");
//                    } else {
//                        mHomeTitle.setText("宝宝" + year + "岁" + monthday + "天啦");
//                    }
//                } else if (month != 0 && monthday.equals("0")) {
//                    int month1 = month / 12;
//                    mHomeTitle.setText("宝宝" + month1 + "个月");
//                }

                if (month == 0 && monthday.equals("0")) {
                    mHomeTitle.setText("宝宝今天出生");
                    babyBothDay = "出生";
                } else if (month == 0 && !monthday.equals("0")) {
                    mHomeTitle.setText("宝宝" + monthday + "天");
                    babyBothDay = monthday + "天";
                } else if (month < 12 && !monthday.equals("0")) {
                    mHomeTitle.setText("宝宝" + month + "个月" + monthday + "天");
                    babyBothDay = month + "个月" + monthday + "天";
                } else if (month < 12 && monthday.equals("0")) {
                    mHomeTitle.setText("宝宝" + month + "个月");
                    babyBothDay = month + "个月";
                } else if (month > 12 && monthday.equals("0")) {
                    int month1 = month / 12;
                    int month2 = month % 12;
                    if (month2 == 0) {
                        mHomeTitle.setText("宝宝" + month1 + "岁");
                        babyBothDay = month1 + "岁";
                    } else {
                        mHomeTitle.setText("宝宝" + month1 + "岁" + month2 + "个月");
                        babyBothDay = month1 + "岁" + month2 + "个月";
                    }
                } else if (month > 12 && !monthday.equals("0")) {
                    int month1 = month / 12;
                    int month2 = month % 12;
                    if (month2 == 0) {
                        mHomeTitle.setText("宝宝" + month1 + "岁" + monthday + "天");
                        babyBothDay = month1 + "岁" + monthday + "天";
                    } else {
                        mHomeTitle.setText("宝宝" + month1 + "岁" + month2 + "个月" + monthday + "天");
                        babyBothDay = month1 + "岁" + month2 + "个月" + monthday + "天";
                    }
                }


                if (mListst.size() != 0) {
                    for (int i = 0; i < mListst.size(); i++) {
                        if (String.valueOf(SPUtils.get(getContext(), "w_sex", "")).equals("2")) {
                            if (mListst.get(i).getSex().equals("F")) {
                                mHomeText.setText("身高：" + mListst.get(1).getMin_height() + "~" + mListst.get(1).getMax_height() + " cm" + "      体重：" + mListst.get(1).getMin_weight() + "~" + mListst.get(1).getMax_weight() + " kg");
                            }
                        } else if (String.valueOf(SPUtils.get(getContext(), "w_sex", "")).equals("1")) {
                            mHomeText.setText("身高：" + mListst.get(0).getMin_height() + "~" + mListst.get(0).getMax_height() + " cm" + "      体重：" + mListst.get(0).getMin_weight() + "~" + mListst.get(0).getMax_weight() + " kg");
                        }
                    }
                } else {
                    mHomeText.setText("");
                }

                if (babyDaycontent != null) {
                    mHomeContentTitle.setText("日志 ：");
                    mHomeContent.setText("            " + babyDaycontent);
                } else {
                    if (dataIntro != null) {
                        mHomeContentTitle.setText("今日宝宝发育变化 ：");
                        mHomeContent.setText("                                     " + dataIntro);
                    }
                }
                if (!"".equals(SPUtils.get(getContext(), "GJSet", ""))) {
                    String str = String.valueOf(SPUtils.get(getContext(), "GJId", ""));
                    String[] temp = str.split(",");
                    App.mList5.clear();
                    for (int i = 0; i < temp.length; i++) {
                        initSetExitData(temp[i].toString());
                    }
                } else {
                    App.mList5.clear();
                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss1 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss1.setId("1");
                    mHomeGridViewNewBeanss1.setName("美丽相册");
                    App.mList5.add(mHomeGridViewNewBeanss1);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss2 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss2.setId("2");
                    mHomeGridViewNewBeanss2.setName("情感日志");
                    App.mList5.add(mHomeGridViewNewBeanss2);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss3 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss3.setId("3");
                    mHomeGridViewNewBeanss3.setName("发育变化");
                    App.mList5.add(mHomeGridViewNewBeanss3);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss4 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss4.setId("4");
                    mHomeGridViewNewBeanss4.setName("宝宝听听");
                    App.mList5.add(mHomeGridViewNewBeanss4);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss5 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss5.setId("5");
                    mHomeGridViewNewBeanss5.setName("亲子游戏");
                    App.mList5.add(mHomeGridViewNewBeanss5);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss6 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss6.setId("10");
                    mHomeGridViewNewBeanss6.setName("亲子任务");
                    App.mList5.add(mHomeGridViewNewBeanss6);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss7 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss7.setId("11");
                    mHomeGridViewNewBeanss7.setName("成长曲线");
                    App.mList5.add(mHomeGridViewNewBeanss7);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss8 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss8.setId("12");
                    mHomeGridViewNewBeanss8.setName("疫苗提醒");
                    App.mList5.add(mHomeGridViewNewBeanss8);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss9 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss9.setId("13");
                    mHomeGridViewNewBeanss9.setName("月子餐");
                    App.mList5.add(mHomeGridViewNewBeanss9);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss10 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss10.setId("14");
                    mHomeGridViewNewBeanss10.setName("能不能吃");
                    App.mList5.add(mHomeGridViewNewBeanss10);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss11 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss11.setId("15");
                    mHomeGridViewNewBeanss11.setName("成长测评");
                    App.mList5.add(mHomeGridViewNewBeanss11);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss12 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss12.setId("16");
                    mHomeGridViewNewBeanss12.setName("知识百科");
                    App.mList5.add(mHomeGridViewNewBeanss12);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss13 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss13.setId("100");
                    mHomeGridViewNewBeanss13.setName("更多");
                    App.mList5.add(mHomeGridViewNewBeanss13);
                }

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mHome_GridView.setLayoutManager(linearLayoutManager);

                mHomeGridViewNewAdapter = new HomeGridViewNewAdapter(App.mList5, getContext());
                mHome_GridView.setAdapter(mHomeGridViewNewAdapter);

            } else if (SPUtils.get(getContext(), "w_BabyState", "").equals("0")) {
                mHomePlayLL.setVisibility(View.GONE);
                mHomeContentRL.setVisibility(View.GONE);
                mHomeImageView.setImageDrawable(getResources().getDrawable(R.mipmap.sz));
                mHomeTitle.setText("备孕中");
                mHomeText.setText("我已经怀孕啦>>");
                if (babyDaycontent != null) {
                    mHomeContentLL.setVisibility(View.VISIBLE);
                    mHomeContentTitle.setText("日志 ：");
                    mHomeContent.setText("            " + babyDaycontent);
                } else {
                    mHomeContentLL.setVisibility(View.GONE);
                }

                if (!"".equals(SPUtils.get(getContext(), "GJSet", ""))) {
                    String str = String.valueOf(SPUtils.get(getContext(), "GJId", ""));
                    String[] temp = str.split(",");
                    App.mList5.clear();
                    for (int i = 0; i < temp.length; i++) {
                        initSetExitData(temp[i].toString());
                    }
                } else {
                    App.mList5.clear();

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss1 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss1.setId("1");
                    mHomeGridViewNewBeanss1.setName("美丽相册");
                    App.mList5.add(mHomeGridViewNewBeanss1);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss2 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss2.setId("2");
                    mHomeGridViewNewBeanss2.setName("情感日志");
                    App.mList5.add(mHomeGridViewNewBeanss2);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss3 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss3.setId("4");
                    mHomeGridViewNewBeanss3.setName("宝宝听听");
                    App.mList5.add(mHomeGridViewNewBeanss3);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss4 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss4.setId("5");
                    mHomeGridViewNewBeanss4.setName("亲子游戏");
                    App.mList5.add(mHomeGridViewNewBeanss4);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss5 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss5.setId("14");
                    mHomeGridViewNewBeanss5.setName("能不能吃");
                    App.mList5.add(mHomeGridViewNewBeanss5);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss6 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss6.setId("16");
                    mHomeGridViewNewBeanss6.setName("知识百科");
                    App.mList5.add(mHomeGridViewNewBeanss6);

                    HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss7 = new HomeGridViewNewBeans.DataBean();
                    mHomeGridViewNewBeanss7.setId("100");
                    mHomeGridViewNewBeanss7.setName("更多");
                    App.mList5.add(mHomeGridViewNewBeanss7);
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mHome_GridView.setLayoutManager(linearLayoutManager);

                mHomeGridViewNewAdapter = new HomeGridViewNewAdapter(App.mList5, getContext());
                mHome_GridView.setAdapter(mHomeGridViewNewAdapter);
            }
        }
    }

    private void initSetExitData(String id) {
        if ("1".equals(id)) {//宝宝相册
            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss1 = new HomeGridViewNewBeans.DataBean();
            mHomeGridViewNewBeanss1.setId("1");
            mHomeGridViewNewBeanss1.setName("美丽相册");
            App.mList5.add(mHomeGridViewNewBeanss1);
        } else if ("2".equals(id)) {//情感日志
            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss2 = new HomeGridViewNewBeans.DataBean();
            mHomeGridViewNewBeanss2.setId("2");
            mHomeGridViewNewBeanss2.setName("情感日志");
            App.mList5.add(mHomeGridViewNewBeanss2);
        } else if ("3".equals(id)) {//发育变化
            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss3 = new HomeGridViewNewBeans.DataBean();
            mHomeGridViewNewBeanss3.setId("3");
            mHomeGridViewNewBeanss3.setName("发育变化");
            App.mList5.add(mHomeGridViewNewBeanss3);
        } else if ("4".equals(id)) {//宝宝听听
            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss4 = new HomeGridViewNewBeans.DataBean();
            mHomeGridViewNewBeanss4.setId("4");
            mHomeGridViewNewBeanss4.setName("宝宝听听");
            App.mList5.add(mHomeGridViewNewBeanss4);
        } else if ("5".equals(id)) {//亲子游戏
            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss5 = new HomeGridViewNewBeans.DataBean();
            mHomeGridViewNewBeanss5.setId("5");
            mHomeGridViewNewBeanss5.setName("亲子游戏");
            App.mList5.add(mHomeGridViewNewBeanss5);
        } else if ("6".equals(id)) {//数胎动
            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss6 = new HomeGridViewNewBeans.DataBean();
            mHomeGridViewNewBeanss6.setId("6");
            mHomeGridViewNewBeanss6.setName("数胎动");
            App.mList5.add(mHomeGridViewNewBeanss6);
        } else if ("7".equals(id)) {//产检提醒
            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss7 = new HomeGridViewNewBeans.DataBean();
            mHomeGridViewNewBeanss7.setId("7");
            mHomeGridViewNewBeanss7.setName("产检提醒");
            App.mList5.add(mHomeGridViewNewBeanss7);
        } else if ("8".equals(id)) {//孕期食谱
            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss8 = new HomeGridViewNewBeans.DataBean();
            mHomeGridViewNewBeanss8.setId("8");
            mHomeGridViewNewBeanss8.setName("孕期食谱");
            App.mList5.add(mHomeGridViewNewBeanss8);
        } else if ("9".equals(id)) {//看懂B超单
            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss9 = new HomeGridViewNewBeans.DataBean();
            mHomeGridViewNewBeanss9.setId("9");
            mHomeGridViewNewBeanss9.setName("看懂B超单");
            App.mList5.add(mHomeGridViewNewBeanss9);
        } else if ("10".equals(id)) {//亲子任务
            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss10 = new HomeGridViewNewBeans.DataBean();
            mHomeGridViewNewBeanss10.setId("10");
            mHomeGridViewNewBeanss10.setName("亲子任务");
            App.mList5.add(mHomeGridViewNewBeanss10);
        } else if ("11".equals(id)) {//成长曲线
            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss11 = new HomeGridViewNewBeans.DataBean();
            mHomeGridViewNewBeanss11.setId("11");
            mHomeGridViewNewBeanss11.setName("成长曲线");
            App.mList5.add(mHomeGridViewNewBeanss11);
        } else if ("12".equals(id)) {//疫苗提醒
            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss12 = new HomeGridViewNewBeans.DataBean();
            mHomeGridViewNewBeanss12.setId("12");
            mHomeGridViewNewBeanss12.setName("疫苗提醒");
            App.mList5.add(mHomeGridViewNewBeanss12);
        } else if ("13".equals(id)) {//月子餐
            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss13 = new HomeGridViewNewBeans.DataBean();
            mHomeGridViewNewBeanss13.setId("13");
            mHomeGridViewNewBeanss13.setName("月子餐");
            App.mList5.add(mHomeGridViewNewBeanss13);
        } else if ("14".equals(id)) {//能不能吃
            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss14 = new HomeGridViewNewBeans.DataBean();
            mHomeGridViewNewBeanss14.setId("14");
            mHomeGridViewNewBeanss14.setName("能不能吃");
            App.mList5.add(mHomeGridViewNewBeanss14);
        } else if ("15".equals(id)) {//成长测评
            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss15 = new HomeGridViewNewBeans.DataBean();
            mHomeGridViewNewBeanss15.setId("15");
            mHomeGridViewNewBeanss15.setName("成长测评");
            App.mList5.add(mHomeGridViewNewBeanss15);
        } else if ("16".equals(id)) {//知识百科
            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss16 = new HomeGridViewNewBeans.DataBean();
            mHomeGridViewNewBeanss16.setId("16");
            mHomeGridViewNewBeanss16.setName("知识百科");
            App.mList5.add(mHomeGridViewNewBeanss16);
        } else if ("100".equals(id)) {//更多
            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss100 = new HomeGridViewNewBeans.DataBean();
            mHomeGridViewNewBeanss100.setId("100");
            mHomeGridViewNewBeanss100.setName("更多");
            App.mList5.add(mHomeGridViewNewBeanss100);
        }
    }

    private void initLieBiao(int status) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "106");
        hashMap.put("token", SPUtils.get(getContext(), "token", ""));
        hashMap.put("babyStatus", status);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<HomeGridViewNewBeans>(getContext()) {
                    @Override
                    public void onSuccess(HomeGridViewNewBeans mHomeGridViewNewBeans, Call call, Response response) {
                        if (mHomeGridViewNewBeans.getResultCode() == 1) {
                            App.mList5.clear();
                            App.mList5.addAll(mHomeGridViewNewBeans.getData());

                            HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss = new HomeGridViewNewBeans.DataBean();
                            mHomeGridViewNewBeanss.setId("100");
                            mHomeGridViewNewBeanss.setName("更多");
                            App.mList5.add(mHomeGridViewNewBeanss);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            mHome_GridView.setLayoutManager(linearLayoutManager);

                            mHomeGridViewNewAdapter = new HomeGridViewNewAdapter(App.mList5, getContext());
                            mHome_GridView.setAdapter(mHomeGridViewNewAdapter);
                        } else {
                            Log.e("xcf_log", "106");
                            App.ErrorToken(mHomeGridViewNewBeans.getResultCode(), mHomeGridViewNewBeans.getMsg());
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.isPlay()) {
            isPlay = true;
            isPlayStart = false;
        }
        if (event.getReId() != null) {
            int position = Integer.parseInt(event.getReId());
            //登录状态
            if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                if ("1".equals(App.mList5.get(position).getId())) {//宝宝相册
                    startActivity(new Intent(getContext(), BabyPhonesActivity.class));
                } else if ("2".equals(App.mList5.get(position).getId())) {//情感日志
                    Intent intent = new Intent(getContext(), EmotionalLogActivity.class);
                    startActivityForResult(intent, 22);
                } else if ("3".equals(App.mList5.get(position).getId())) {//发育变化（1，2才有）
                    if (SPUtils.get(getContext(), "BabyState", "").equals("1")) {
                        Intent intent = new Intent(getContext(), BabyDayGrowthActivity.class);
                        intent.putExtra("dayAll", dayAll);
                        intent.putExtra("days", days);
                        intent.putExtra("week", week);
                        startActivity(intent);
                    } else if (SPUtils.get(getContext(), "BabyState", "").equals("2")) {
                        Intent intent = new Intent(getContext(), BabyDevelopmentActivity.class);
                        intent.putExtra("week", week + 1);
                        startActivity(intent);
                    } else {
                        Toast.makeText(mActivity, "亲，备孕状态下不能使用哦~", Toast.LENGTH_SHORT).show();
                    }
                } else if ("4".equals(App.mList5.get(position).getId())) {//宝宝听听（必有）
                    startActivity(new Intent(getContext(), BabyListenActivity.class));
                } else if ("5".equals(App.mList5.get(position).getId())) {//亲子游戏（必有）
                    startActivity(new Intent(getContext(), ParentChildGameActivity.class));
                } else if ("6".equals(App.mList5.get(position).getId())) {//数胎动（1才有）
                    if (SPUtils.get(getContext(), "BabyState", "").equals("1")) {
                        Intent intent = new Intent(getContext(), FetalMovementActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(mActivity, "亲爱的,该功能只有已怀孕状态才能使用哦!", Toast.LENGTH_SHORT).show();
                    }
                } else if ("7".equals(App.mList5.get(position).getId())) {//产检提醒（1才有）
                    if (SPUtils.get(getContext(), "BabyState", "").equals("1")) {
                        Intent intent = new Intent(getContext(), AntenatalTimeTableActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(mActivity, "亲爱的,该功能只有已怀孕状态才能使用哦!", Toast.LENGTH_SHORT).show();
                    }
                } else if ("8".equals(App.mList5.get(position).getId())) {//孕期食谱（1才有）
                    if (SPUtils.get(getContext(), "BabyState", "").equals("1")) {
                        if (week >= 40) {
                            week = 40;
                        } else if (week_day > 0) {
                            week = week + 1;
                        }
                        Intent intent = new Intent(getContext(), ServiceActivity.class);
                        intent.putExtra("url", "http://cms.env365.cn/shiPu.jspx?" + "week=" + week);
                        intent.putExtra("title", "孕期食谱");
                        intent.putExtra("flag", "2");
                        startActivity(intent);
                    } else {
                        Toast.makeText(mActivity, "亲爱的,该功能只有已怀孕状态才能使用哦!", Toast.LENGTH_SHORT).show();
                    }
                } else if ("9".equals(App.mList5.get(position).getId())) {//看懂B超单（1才有））
                    if (SPUtils.get(getContext(), "BabyState", "").equals("1")) {
                        if (week >= 40) {
                            week = 40;
                        } else if (week_day > 0) {
                            week = week + 1;
                        }
                        Intent intent = new Intent(getContext(), ServiceActivity.class);
                        intent.putExtra("url", "http://cms.env365.cn/bChaoDanIndex.jspx?" + "week=" + week);
                        intent.putExtra("title", "看懂B超单");
                        intent.putExtra("flag", "2");
                        startActivity(intent);
                    } else {
                        Toast.makeText(mActivity, "亲爱的,该功能只有已怀孕状态才能使用哦!", Toast.LENGTH_SHORT).show();
                    }
                } else if ("10".equals(App.mList5.get(position).getId())) {//亲子任务（2才有）
                    if (SPUtils.get(getContext(), "BabyState", "").equals("2")) {
                        startActivity(new Intent(getContext(), ParentTaskActivity.class));
                    } else {
                        Toast.makeText(mActivity, "亲爱的,该功能只有已出生状态才能使用哦!", Toast.LENGTH_SHORT).show();
                    }
                } else if ("11".equals(App.mList5.get(position).getId())) {//成长曲线（2才有）
                    if (SPUtils.get(getContext(), "BabyState", "").equals("2")) {
                        Intent intent = new Intent(getContext(), GrowthCurveActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(mActivity, "亲爱的,该功能只有已出生状态才能使用哦!", Toast.LENGTH_SHORT).show();
                    }
                } else if ("12".equals(App.mList5.get(position).getId())) {//疫苗提醒（2才有）
                    if (SPUtils.get(getContext(), "BabyState", "").equals("2")) {
                        startActivity(new Intent(getContext(), GrowSeedlingsTimeActivity.class));
                    } else {
                        Toast.makeText(mActivity, "亲爱的,该功能只有已出生状态才能使用哦!", Toast.LENGTH_SHORT).show();
                    }
                } else if ("13".equals(App.mList5.get(position).getId())) {//月子餐（2才有）
                    if (SPUtils.get(getContext(), "BabyState", "").equals("2")) {
                        if (days >= 40) {
                            days = 40;
                        } else {
                            days = days + 1;
                        }
                        Intent intent = new Intent(getContext(), ServiceActivity.class);
                        intent.putExtra("url", "http://cms.env365.cn/yueZiCan.jspx?" + "day=" + days);
                        intent.putExtra("title", "月子餐");
                        intent.putExtra("flag", "2");
                        startActivity(intent);
                    } else {
                        Toast.makeText(mActivity, "亲爱的,该功能只有已出生状态才能使用哦!", Toast.LENGTH_SHORT).show();
                    }
                } else if ("14".equals(App.mList5.get(position).getId())) {//能不能吃（0，1,2都有）
                    Intent intent = new Intent(getContext(), ServiceActivity.class);
                    intent.putExtra("url", "http://webview.babytree.com/api/mobile_toolcms/can_eat");
                    intent.putExtra("title", "能不能吃");
                    intent.putExtra("flag", "2");
                    startActivity(intent);
                } else if ("15".equals(App.mList5.get(position).getId())) {//成长测评（2才有）
                    if (SPUtils.get(getContext(), "BabyState", "").equals("2")) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("itfaceId", "136");
                        hashMap.put("token", SPUtils.get(getContext(), "token", ""));
                        hashMap.put("babyId", SPUtils.get(getContext(), "babyId", ""));
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<GrowUpAppraisalResultBeans>(getContext()) {
                                    @Override
                                    public void onSuccess(GrowUpAppraisalResultBeans mGrowUpAppraisalResultBeans, Call call, Response response) {
                                        if (mGrowUpAppraisalResultBeans.getResultCode() == 1) {
                                            if (mGrowUpAppraisalResultBeans.getData().isIsNeedNewTest()) {
                                                Intent intent = new Intent(getContext(), GrowUpAppraisalActivity.class);
                                                intent.putExtra("name", babyNickname);
                                                intent.putExtra("year", babyBothDay);
                                                intent.putExtra("month", String.valueOf(qieHuanMonth));
                                                startActivity(intent);
                                            } else {
                                                Intent intent = new Intent(getContext(), GrowUpAppraisalResultActivity.class);
                                                intent.putExtra("name", babyNickname);
                                                intent.putExtra("year", babyBothDay);
                                                intent.putExtra("month", String.valueOf(qieHuanMonth));
                                                startActivity(intent);
                                            }
                                        } else {
                                            App.ErrorToken(mGrowUpAppraisalResultBeans.getResultCode(), mGrowUpAppraisalResultBeans.getMsg());
                                        }
                                    }

                                    @Override
                                    public void onError(Call call, Response response, Exception e) {
                                        super.onError(call, response, e);
                                        if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                                            Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(mActivity, "亲爱的,该功能只有已出生状态才能使用哦!", Toast.LENGTH_SHORT).show();
                    }
                } else if ("16".equals(App.mList5.get(position).getId())) {//知识百科
                    Intent intent = new Intent(getContext(), ParentingEncyclopediaActivity.class);
                    startActivity(intent);
                } else if ("100".equals(App.mList5.get(position).getId())) {//更多（0，1,2都有）
                    Intent intent = new Intent(getContext(), MoreServiceActivity.class);
                    intent.putExtra("daystwo", days);//怀孕中发育变化用
                    if (week >= 40) {
                        week = 40;
                    } else if (week_day > 0) {
                        week = week + 1;
                    }

                    if (days >= 40) {
                        days = 40;
                    } else {
                        days = days + 1;
                    }

                    intent.putExtra("dayAll", dayAll);
                    intent.putExtra("days", days);
                    intent.putExtra("week", week);
                    intent.putExtra("week_day", week_day);

                    intent.putExtra("name", babyNickname);
                    intent.putExtra("year", babyBothDay);
                    intent.putExtra("month", String.valueOf(qieHuanMonth));

                    startActivity(intent);
                }
                //未登录状态//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            } else {
//                Log.e("dfdfsdfs", App.mList5.get(position).getId());
                if ("1".equals(App.mList5.get(position).getId())) {//美丽相册（必有）
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else if ("2".equals(App.mList5.get(position).getId())) {//情感日志（必有）
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else if ("3".equals(App.mList5.get(position).getId())) {//发育变化（1，2才有）
                    if (SPUtils.get(getContext(), "w_BabyState", "").equals("1")) {
                        Intent intent = new Intent(getContext(), BabyDayGrowthActivity.class);
                        intent.putExtra("dayAll", dayAll);
                        intent.putExtra("days", days);
                        intent.putExtra("week", week);
                        startActivity(intent);
                    } else if (SPUtils.get(getContext(), "w_BabyState", "").equals("2")) {
                        Intent intent = new Intent(getContext(), BabyDevelopmentActivity.class);
                        intent.putExtra("week", week + 1);
                        startActivity(intent);
                    } else {
                        Toast.makeText(mActivity, "亲，备孕状态下不能使用哦~", Toast.LENGTH_SHORT).show();
                    }
                } else if ("4".equals(App.mList5.get(position).getId())) {//宝宝听听（必有）
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else if ("5".equals(App.mList5.get(position).getId())) {//亲子游戏（必有）
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else if ("6".equals(App.mList5.get(position).getId())) {//数胎动（1才有）
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else if ("7".equals(App.mList5.get(position).getId())) {//产检提醒（1才有）
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else if ("8".equals(App.mList5.get(position).getId())) {//孕期食谱（1才有）
                    if (SPUtils.get(getContext(), "w_BabyState", "").equals("1")) {
                        if (week >= 40) {
                            week = 40;
                        } else if (week_day > 0) {
                            week = week + 1;
                        }
                        Intent intent = new Intent(getContext(), ServiceActivity.class);
                        intent.putExtra("url", "http://cms.env365.cn/shiPu.jspx?" + "week=" + week);
                        intent.putExtra("title", "孕期食谱");
                        intent.putExtra("flag", "2");
                        startActivity(intent);
                    } else {
                        Toast.makeText(mActivity, "亲，只有怀孕中才能使用哦~", Toast.LENGTH_SHORT).show();
                    }
                } else if ("9".equals(App.mList5.get(position).getId())) {//看懂B超单（1才有）
                    if (SPUtils.get(getContext(), "w_BabyState", "").equals("1")) {
                        if (week >= 40) {
                            week = 40;
                        } else if (week_day > 0) {
                            week = week + 1;
                        }
                        Intent intent = new Intent(getContext(), ServiceActivity.class);
                        intent.putExtra("url", "http://cms.env365.cn/bChaoDanIndex.jspx?" + "week=" + week);
                        intent.putExtra("title", "看懂B超单");
                        intent.putExtra("flag", "2");
                        startActivity(intent);
                    } else {
                        Toast.makeText(mActivity, "亲，只有怀孕中才能使用哦~", Toast.LENGTH_SHORT).show();
                    }
                } else if ("10".equals(App.mList5.get(position).getId())) {//亲子任务（2才有）
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else if ("11".equals(App.mList5.get(position).getId())) {//成长曲线（2才有）
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else if ("12".equals(App.mList5.get(position).getId())) {//疫苗提醒（2才有）
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else if ("13".equals(App.mList5.get(position).getId())) {//月子餐（2才有）
                    if (SPUtils.get(getContext(), "w_BabyState", "").equals("2")) {
                        if (days >= 40) {
                            days = 40;
                        } else {
                            days = days + 1;
                        }
                        Intent intent = new Intent(getContext(), ServiceActivity.class);
                        intent.putExtra("url", "http://cms.env365.cn/yueZiCan.jspx?" + "day=" + days);
                        intent.putExtra("title", "月子餐");
                        intent.putExtra("flag", "2");
                        startActivity(intent);
                    } else {
                        Toast.makeText(mActivity, "亲爱的，该功能只有已出生状态才能使用哦！", Toast.LENGTH_SHORT).show();
                    }
                } else if ("14".equals(App.mList5.get(position).getId())) {//能不能吃（0，1,2都有）
                    Intent intent = new Intent(getContext(), ServiceActivity.class);
                    intent.putExtra("url", "http://webview.babytree.com/api/mobile_toolcms/can_eat");
                    intent.putExtra("title", "能不能吃");
                    intent.putExtra("flag", "2");
                    startActivity(intent);
                } else if ("15".equals(App.mList5.get(position).getId())) {//成长测评
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else if ("16".equals(App.mList5.get(position).getId())) {//知识百科
                    Intent intent = new Intent(getContext(), ParentingEncyclopediaActivity.class);
                    startActivity(intent);
                } else if ("100".equals(App.mList5.get(position).getId())) {//更多（0，1,2都有）
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    private void initListeners() {
        //签到
        mHomeSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("itfaceId", "087");
                    hashMap.put("token", SPUtils.get(getContext(), "token", ""));
                    JSONObject jsonObject = new JSONObject(hashMap);

                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<SignBeans>(getContext()) {
                                @Override
                                public void onSuccess(final SignBeans mSignBeans, Call call, Response response) {
//                                    initData();
                                    if (mSignBeans.getResultCode() == 1) {
                                        final AlertDialog alert;
                                        alert = new AlertDialog.Builder(getContext(), R.style.AlertDialogs).create();
                                        alert.show();
                                        alert.getWindow().setContentView(R.layout.dialog_sign2);

                                        TextView mSingDiaLogSize = (TextView) alert.getWindow().findViewById(R.id.Sing_DiaLog_Size);
                                        TextView mSingDiaLogTV = (TextView) alert.getWindow().findViewById(R.id.Sing_DiaLog_TV);
                                        TextView mSingDiaLogTV2 = (TextView) alert.getWindow().findViewById(R.id.Sing_DiaLog_TV2);
                                        TextView mSingSize = (TextView) alert.getWindow().findViewById(R.id.Sign_Size);
                                        TextView mSignGot = (TextView) alert.getWindow().findViewById(R.id.Sign_Got);

                                        mSingDiaLogSize.setText("+" + mSignBeans.getData().getTodayIntegral());
                                        mSingDiaLogTV.setText("签到成功+" + mSignBeans.getData().getTodayIntegral() + "分");
                                        mSingDiaLogTV2.setText("已连续签到" + mSignBeans.getData().getContinuousDays() + "天");
                                        mSingSize.setText(mSignBeans.getData().getBalanceAfterSign() + "");

                                        alert.getWindow().findViewById(R.id.Sign_Got).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                alert.dismiss();
                                                Intent intent = new Intent(getContext(), SignInActivity.class);
                                                intent.putExtra("balanceAfterSign", String.valueOf(mSignBeans.getData().getBalanceAfterSign()));
                                                startActivity(intent);
                                            }
                                        });
                                    } else {
                                        Log.e("xcf_log", "087");
                                        startActivity(new Intent(getContext(), SignInActivity.class));
                                    }
                                }
                            });
                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        //亲友团
        mHomeFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    Intent intent = new Intent(getContext(), BabyActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }

            }
        });
        mHomePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("isPlay1", String.valueOf(isPlay));
                Log.e("isPlayStart1", String.valueOf(isPlayStart));
                if (isPlay) {
                    mHomePlay.setBackground(getResources().getDrawable(R.mipmap.zanting2));
                    isPlay = false;
//                    Log.e("isPlay2", String.valueOf(isPlay));
//                    if (isPlayStart) {
                    mediaPlayer.start();
//                    } else {
//                        mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource("http://api.env365.cn/voice/sys/" + hyz_voice);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mHomePlay.setClickable(true);
//                                    mHomePlay.setBackground(getResources().getDrawable(R.mipmap.bofang2));
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    }
                } else {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mHomePlay.setBackground(getResources().getDrawable(R.mipmap.bofang2));
                        mediaPlayer.stop();
                    }
                    isPlay = true;
                    isPlayStart = true;
                }
            }
        });
        //查看更多
        mHomeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (SpUtil.getBooleanValue(getActivity(), MyContant.ISLOGIN, true)) {
                Intent intent = new Intent(getContext(), ServiceActivity.class);
                intent.putExtra("url", "http://cms.env365.cn/");
                intent.putExtra("title", "美丽妈妈-资讯");
                intent.putExtra("flag", "2");
                startActivity(intent);
//                } else {
//                    Intent intent = new Intent(getContext(), LoginActivity.class);
//                    startActivity(intent);
//                }
            }
        });

        mHomeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpUtil.getBooleanValue(getActivity(), MyContant.ISLOGIN, true)) {
                    if (SPUtils.get(getContext(), "BabyState", "").equals("0")) {
                        menuWindow = new SelectPicPopup(getActivity(), itemsOnClick);
                        View parent1 = LayoutInflater.from(getActivity()).inflate(R.layout.activity_information, null);
                        menuWindow.showAtLocation(parent1, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    } else if (SPUtils.get(getContext(), "BabyState", "").equals("2")) {
                        Intent intent = new Intent(getContext(), SettingBabyMessageActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (SPUtils.get(getContext(), "w_BabyState", "").equals("0")) {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    } else if (SPUtils.get(getContext(), "w_BabyState", "").equals("2")) {
                        Intent intent = new Intent(getContext(), SettingBabyMessageActivity.class);
                        intent.putExtra("flag", "0");
                        intent.putExtra("isTianJia", "0");
                        startActivity(intent);
                    }
                }
            }
        });
        mHome_GridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        mHome_GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
        mHomeMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    SPUtils.put(getContext(), "hongdianShow1", "false");
                    startActivity(new Intent(getContext(), MessageCenterActivity.class));
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class).putExtra("fanhui","3"));
                }
            }
        });
        mHomeWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    startActivity(new Intent(getContext(), WriteEmotionalActivity.class));
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        mHomeContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    if (SPUtils.get(getContext(), "BabyState", "").equals("1")) {
                        if (babyDaycontent != null) {
                            startActivity(new Intent(getContext(), EmotionalLogActivity.class));
                        } else {
                            Intent intent = new Intent(getContext(), BabyDayGrowthActivity.class);
                            intent.putExtra("dayAll", dayAll);
                            intent.putExtra("days", days);
                            intent.putExtra("week", week + 1);
                            startActivity(intent);
                        }
                    } else if (SPUtils.get(getContext(), "BabyState", "").equals("2")) {
                        if (babyDaycontent != null) {
                            startActivity(new Intent(getContext(), EmotionalLogActivity.class));
                        } else {
                            Intent intent = new Intent(getContext(), BabyDevelopmentActivity.class);
                            intent.putExtra("week", week + 1);
                            startActivity(intent);
                        }
                    } else if (SPUtils.get(getContext(), "BabyState", "").equals("0")) {
                        startActivity(new Intent(getContext(), EmotionalLogActivity.class));
                    }
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        mHomeListView.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra("id", mList3.get(position).getContent_id());
                startActivity(intent);
            }
        });

        mHomeText.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    if (SPUtils.get(getContext(), "BabyState", "").equals("0")) {
                        Intent intent = new Intent(getContext(), ExpectedConfinementActivity.class);
                        intent.putExtra("flag", "0");
                        intent.putExtra("isTianJia", "0");
                        startActivity(intent);
                    }
                } else {
                    if (SPUtils.get(getContext(), "w_BabyState", "").equals("0")) {
                        Intent intent = new Intent(getContext(), ExpectedConfinementActivity.class);
                        intent.putExtra("flag", "0");
                        intent.putExtra("isTianJia", "0");
                        startActivity(intent);
//                        SpUtil.setBooleanValue(getContext(), MyContant.W_SETTING, false);
                    }
                }
            }
        });

        mHomeContentRL.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    if (SPUtils.get(getContext(), "BabyState", "").equals("1")) {
                        Intent intent = new Intent(getContext(), BabyMessageActivity.class);
                        intent.putExtra("isTianJia", "0");
                        startActivity(intent);
                    }
                } else {
                    if (SPUtils.get(getContext(), "w_BabyState", "").equals("1")) {
                        Intent intent = new Intent(getContext(), BabyMessageActivity.class);
                        intent.putExtra("isTianJia", "0");
                        startActivity(intent);
                    }
                }
            }
        });
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                // 拍照
                case R.id.set_head_photograph:
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        takePhoto();
                    }
                    break;
                // 相册选择图片
                case R.id.set_head_album:
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                    } else {
                        pickPhoto();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 拍照获取图片
     */
//    @NeedsPermission({Manifest.permission_group.CONTACTS})
    void takePhoto() {
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            /***
             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
             * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
             * 如果不使用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
             */
            ContentValues values = new ContentValues();
            mPhotoUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        } else {
            Toast.makeText(getContext(), "内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }


    /***
     * 从相册中取图片
     */
    void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        // 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }


    /**
     * 选择图片后，获取图片的路径
     *
     * @param requestCode
     * @param data
     */
    private void doPhoto(int requestCode, Intent data) {
        // 从相册取图片，有些手机有异常情况，请注意
        if (requestCode == SELECT_PIC_BY_PICK_PHOTO) {
            if (data == null) {
                Toast.makeText(getContext(), "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
            mPhotoUri = data.getData();
            if (mPhotoUri == null) {
                Toast.makeText(getContext(), "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
        }
        String[] pojo = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getActivity().getContentResolver().query(mPhotoUri, pojo, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);
            // 4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
            if (Integer.parseInt(Build.VERSION.SDK) < 14) {
                cursor.close();
            }
        }
        // 如果图片符合要求将其上传到服务器
        if (picPath != null && (picPath.endsWith(".png") || picPath.endsWith(".PNG") ||
                picPath.endsWith(".jpg") || picPath.endsWith(".JPG"))) {

            BitmapFactory.Options option = new BitmapFactory.Options();
            // 压缩图片:表示缩略图大小为原始图片大小的几分之一，1为原图
            option.inSampleSize = 1;
            // 根据图片的SDCard路径读出Bitmap
            Bitmap bm = BitmapFactory.decodeFile(picPath, option);
            // 显示在图片控件上
//            mHomeImageView.setImageBitmap(bm);

            Log.i("mars", "doPhoto: " + picPath);
            if (bm != null) {
                bitmaptoString(bm, 24);
            }
            if (SpUtil.getBooleanValue(getActivity(), MyContant.ISLOGIN, true)) {
                if (SPUtils.get(getContext(), "BabyState", "").equals("0")) {
                    SPUtils.put(getContext(), "icon_b", "true");
                    upLoadUImageHeader(string);
                }
            } /*else {
                if (SPUtils.get(getContext(), "w_BabyState", "").equals("0")) {
                    SPUtils.put(getContext(), "w_icon_b", "true");
                    SPUtils.put(getContext(), "w_img_b", picPath);
                } *//*else if (SPUtils.get(getContext(), "w_BabyState", "").equals("2")) {
                    SPUtils.put(getContext(), "w_icon_y", "true");
                    SPUtils.put(getContext(), "w_img_y", picPath);
                }*//*
            }*/
            Glide.with(getContext()).load(picPath).into(mHomeImageView);
            cursor.close();
        } else {
            Toast.makeText(getContext(), "选择图片文件不正确", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 将bitmap转换成base64字符串
     *
     * @param bitmap
     * @return base64 字符串
     */

    public String bitmaptoString(Bitmap bitmap, int bitmapQuality) {
        // 将Bitmap转换成字符串
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, bitmapQuality, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    private void upLoadUImageHeader(String string) {
        Log.e("fdfd", string);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "022");
        hashMap.put("babyHeadIco", string);
        hashMap.put("token", SPUtils.get(getContext(), "token", ""));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<PersonalinformationModifitionImgBeans>(getContext()) {
                    @Override
                    public void onSuccess(PersonalinformationModifitionImgBeans mPersonalinformationModifitionImgBeans, Call call, Response response) {
                        if (mPersonalinformationModifitionImgBeans.getResultCode() == 1) {
                            Toast.makeText(getContext(), "亲，头像上传成功了哦！", Toast.LENGTH_SHORT).show();
                            initData();
                        } else {
                            Log.e("xcf_log", "022");
                            App.ErrorToken(mPersonalinformationModifitionImgBeans.getResultCode(), mPersonalinformationModifitionImgBeans.getMsg());
                        }
                    }
                });
    }

    /**
     * 接收从其他页面返回的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SELECT_PIC_BY_PICK_PHOTO:// 直接从相册获取
                doPhoto(requestCode, data);
                break;
            case SELECT_PIC_BY_TACK_PHOTO:// 调用相机拍照
                doPhoto(requestCode, data);
                break;
//            case 22:
//                if (resultCode == 11) {
//                    initStatus();
//                    initSQLite();
//                    initData();
//                    initNetWork();
//                }
//                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}


