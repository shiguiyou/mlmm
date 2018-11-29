package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.MoreServiceGridViewAdapter1;
import com.wanquan.mlmmx.mlmm.adapter.MoreServiceGridViewAdapter2;
import com.wanquan.mlmmx.mlmm.adapter.MoreServiceGridViewAdapter3;
import com.wanquan.mlmmx.mlmm.beans.HomeSaveBeans;
import com.wanquan.mlmmx.mlmm.beans.MoreServiceBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.phone.MyGridView;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.xmly.activity.BabyListenActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：更多服务
 * 作者：薛昌峰
 * 时间：2018.06.05
 */

public class MoreServiceActivity extends BaseActivity {
    private LinearLayout mAppTitleBank;
    private TextView mAppTitleName;
    private TextView mAppTitleSave;
    private TextView mMoreServiceTextView1;
    private MyGridView mMoreServiceMyGridView1;
    private TextView mMoreServiceTextView2;
    private MyGridView mMoreServiceMyGridView2;
    private TextView mMoreServiceTextView3;
    private MyGridView mMoreServiceMyGridView3;
    private MoreServiceGridViewAdapter1 mMoreServiceGridViewAdapter1;
    private MoreServiceGridViewAdapter2 mMoreServiceGridViewAdapter2;
    private MoreServiceGridViewAdapter3 mMoreServiceGridViewAdapter3;
    private List<MoreServiceBeans.DataBean.ToolbarList1Bean> mList1 = new ArrayList<>();
    private List<MoreServiceBeans.DataBean.ToolbarList2Bean> mList2 = new ArrayList<>();
    private List<MoreServiceBeans.DataBean.ToolbarList3Bean> mList3 = new ArrayList<>();
    private List<String> mList4 = new ArrayList<>();
    private boolean flag = false;
    private String str1 = "";
    private int dayAll;
    private int days;
    private int daystwo;
    private int week;
    private int week_day;

    private String name;
    private String year;
    private String month;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(MoreServiceActivity.this, R.color.tops);

        dayAll = getIntent().getIntExtra("dayAll", 0);
        days = getIntent().getIntExtra("days", 0);
        daystwo = getIntent().getIntExtra("daystwo", 0);
        week = getIntent().getIntExtra("week", 0);
        week_day = getIntent().getIntExtra("week_day", 0);


        name = getIntent().getStringExtra("name");
        year = getIntent().getStringExtra("year");
        month = getIntent().getStringExtra("month");

//        Log.e("wewewewdayAll", String.valueOf(dayAll));
//        Log.e("wewewewdays", String.valueOf(days));
//        Log.e("wewewewweek", String.valueOf(week));
//        Log.e("wewewewweek_day", String.valueOf(week_day));

        initData("1");
        initListeners();

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_more_service;
    }

    @Override
    public void initView() throws Exception {
        mAppTitleBank = (LinearLayout) findViewById(R.id.App_Title_Bank);
        mAppTitleName = (TextView) findViewById(R.id.App_Title_Name);
        mAppTitleSave = (TextView) findViewById(R.id.App_Title_Save);
        mMoreServiceTextView1 = (TextView) findViewById(R.id.MoreService_TextView1);
        mMoreServiceMyGridView1 = (MyGridView) findViewById(R.id.MoreService_MyGridView1);
        mMoreServiceTextView2 = (TextView) findViewById(R.id.MoreService_TextView2);
        mMoreServiceMyGridView2 = (MyGridView) findViewById(R.id.MoreService_MyGridView2);
        mMoreServiceTextView3 = (TextView) findViewById(R.id.MoreService_TextView3);
        mMoreServiceMyGridView3 = (MyGridView) findViewById(R.id.MoreService_MyGridView3);

        mAppTitleName.setText("服务");
        mAppTitleSave.setText("管理");
        mAppTitleSave.setVisibility(View.VISIBLE);
        mAppTitleSave.setTextColor(getResources().getColor(R.color.topss));
    }

    private void initData(final String str) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "107");
        hashMap.put("token", SPUtils.get(MoreServiceActivity.this, "token", ""));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<MoreServiceBeans>(MoreServiceActivity.this) {
                    @Override
                    public void onSuccess(MoreServiceBeans mMoreServiceBeans, Call call, Response response) {
                        if (mMoreServiceBeans.getResultCode() == 1) {

                            mList1.clear();
                            mList2.clear();
                            mList3.clear();

                            mList1.addAll(mMoreServiceBeans.getData().getToolbarList1());
                            mList2.addAll(mMoreServiceBeans.getData().getToolbarList2());
                            mList3.addAll(mMoreServiceBeans.getData().getToolbarList3());

                            mMoreServiceGridViewAdapter1 = new MoreServiceGridViewAdapter1(mList1, MoreServiceActivity.this, str);
                            mMoreServiceMyGridView1.setAdapter(mMoreServiceGridViewAdapter1);

                            mMoreServiceGridViewAdapter2 = new MoreServiceGridViewAdapter2(mList2, MoreServiceActivity.this, str);
                            mMoreServiceMyGridView2.setAdapter(mMoreServiceGridViewAdapter2);

                            mMoreServiceGridViewAdapter3 = new MoreServiceGridViewAdapter3(mList3, MoreServiceActivity.this, str);
                            mMoreServiceMyGridView3.setAdapter(mMoreServiceGridViewAdapter3);

                        } else {
                            App.ErrorToken(mMoreServiceBeans.getResultCode(), mMoreServiceBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
        mAppTitleBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAppTitleSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    flag = true;
                    mAppTitleSave.setText("完成");
                    SPUtils.put(MoreServiceActivity.this, "isMoreServiceShow", "2");
                    initData("2");
                } else {
                    flag = false;
                    mAppTitleSave.setText("管理");
                    SPUtils.put(MoreServiceActivity.this, "isMoreServiceShow", "1");
                    initData("1");

                    //上传选择后的数据
                    initSave();
                }
            }
        });

        mMoreServiceMyGridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (flag) {//在完成的情况下才可以点击
                    if (!mList1.get(position).isFlag()) {
                        mList1.get(position).setUserId("1");
                        mList1.get(position).setFlag(true);

                        mMoreServiceGridViewAdapter1 = new MoreServiceGridViewAdapter1(mList1, MoreServiceActivity.this, "2");
                        mMoreServiceMyGridView1.setAdapter(mMoreServiceGridViewAdapter1);
                    } else {
                        mList1.get(position).setUserId(null);
                        mList1.get(position).setFlag(false);

                        mMoreServiceGridViewAdapter1 = new MoreServiceGridViewAdapter1(mList1, MoreServiceActivity.this, "2");
                        mMoreServiceMyGridView1.setAdapter(mMoreServiceGridViewAdapter1);
                    }
                } else {//点击跳转页面
                    initNetWork(mList1.get(position).getId());
                }
            }
        });

        mMoreServiceMyGridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (flag) {//在完成的情况下才可以点击
                    if (!mList2.get(position).isFlag()) {
                        mList2.get(position).setUserId("1");
                        mList2.get(position).setFlag(true);

                        mMoreServiceGridViewAdapter2 = new MoreServiceGridViewAdapter2(mList2, MoreServiceActivity.this, "2");
                        mMoreServiceMyGridView2.setAdapter(mMoreServiceGridViewAdapter2);
                    } else {
                        mList2.get(position).setUserId(null);
                        mList2.get(position).setFlag(false);

                        mMoreServiceGridViewAdapter2 = new MoreServiceGridViewAdapter2(mList2, MoreServiceActivity.this, "2");
                        mMoreServiceMyGridView2.setAdapter(mMoreServiceGridViewAdapter2);
                    }
                } else {//点击跳转页面
                    initNetWork(mList2.get(position).getId());
                }
            }
        });

        mMoreServiceMyGridView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (flag) {//在完成的情况下才可以点击
                    if (!mList3.get(position).isFlag()) {
                        mList3.get(position).setUserId("1");
                        mList3.get(position).setFlag(true);

                        mMoreServiceGridViewAdapter3 = new MoreServiceGridViewAdapter3(mList3, MoreServiceActivity.this, "2");
                        mMoreServiceMyGridView3.setAdapter(mMoreServiceGridViewAdapter3);
                    } else {
                        mList3.get(position).setUserId(null);
                        mList3.get(position).setFlag(false);

                        mMoreServiceGridViewAdapter3 = new MoreServiceGridViewAdapter3(mList3, MoreServiceActivity.this, "2");
                        mMoreServiceMyGridView3.setAdapter(mMoreServiceGridViewAdapter3);
                    }
                } else {//点击跳转页面
                    initNetWork(mList3.get(position).getId());
                }
            }
        });
    }

    private void initNetWork(String id) {
//        Log.e("wewe", id);
        if ("1".equals(id)) {//宝宝相册
            startActivity(new Intent(MoreServiceActivity.this, BabyPhonesActivity.class));
        } else if ("2".equals(id)) {//情感日志
            Intent intent = new Intent(MoreServiceActivity.this, EmotionalLogActivity.class);
            startActivityForResult(intent, 22);
        } else if ("3".equals(id)) {//发育变化（1，2才有）
            if (SPUtils.get(MoreServiceActivity.this, "BabyState", "").equals("1")) {
                Intent intent = new Intent(MoreServiceActivity.this, BabyDayGrowthActivity.class);
                intent.putExtra("dayAll", dayAll);
                intent.putExtra("days", daystwo);
                intent.putExtra("week", week - 1);
                startActivity(intent);
            } else if (SPUtils.get(MoreServiceActivity.this, "BabyState", "").equals("2")) {
                Intent intent = new Intent(MoreServiceActivity.this, BabyDevelopmentActivity.class);
                intent.putExtra("week", week);
                startActivity(intent);
            } else {
                Toast.makeText(MoreServiceActivity.this, "亲，备孕状态下不能使用哦~", Toast.LENGTH_SHORT).show();
            }
        } else if ("4".equals(id)) {//宝宝听听（必有）
            startActivity(new Intent(MoreServiceActivity.this, BabyListenActivity.class));
        } else if ("5".equals(id)) {//亲子游戏（必有）
            startActivity(new Intent(MoreServiceActivity.this, ParentChildGameActivity.class));
        } else if ("6".equals(id)) {//数胎动（1才有）
            if (SPUtils.get(MoreServiceActivity.this, "BabyState", "").equals("1")) {
                Intent intent = new Intent(MoreServiceActivity.this, FetalMovementActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MoreServiceActivity.this, "亲爱的,该功能只有已怀孕状态才能使用哦!", Toast.LENGTH_SHORT).show();
            }
        } else if ("7".equals(id)) {//产检提醒（1才有）
            if (SPUtils.get(MoreServiceActivity.this, "BabyState", "").equals("1")) {
                Intent intent = new Intent(MoreServiceActivity.this, AntenatalTimeTableActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MoreServiceActivity.this, "亲爱的,该功能只有已怀孕状态才能使用哦!", Toast.LENGTH_SHORT).show();
            }
        } else if ("8".equals(id)) {//孕期食谱（1才有）
            if (SPUtils.get(MoreServiceActivity.this, "BabyState", "").equals("1")) {
//                if (week_day > 0) {
//                    week = week + 1;
//                }
                Intent intent = new Intent(MoreServiceActivity.this, ServiceActivity.class);
                intent.putExtra("url", "http://cms.env365.cn/shiPu.jspx?" + "week=" + week);
                intent.putExtra("title", "孕期食谱");
                intent.putExtra("flag", "2");
                startActivity(intent);
            } else {
                Toast.makeText(MoreServiceActivity.this, "亲爱的,该功能只有已怀孕状态才能使用哦!~", Toast.LENGTH_SHORT).show();
            }
        } else if ("9".equals(id)) {//看懂B超单（1才有））
            if (SPUtils.get(MoreServiceActivity.this, "BabyState", "").equals("1")) {
//                if (week_day > 0) {
//                    week = week + 1;
//                }
                Intent intent = new Intent(MoreServiceActivity.this, ServiceActivity.class);
                intent.putExtra("url", "http://cms.env365.cn/bChaoDanIndex.jspx?" + "week=" + week);
                intent.putExtra("title", "看懂B超单");
                intent.putExtra("flag", "2");
                startActivity(intent);
            } else {
                Toast.makeText(MoreServiceActivity.this, "亲爱的,该功能只有已怀孕状态才能使用哦!", Toast.LENGTH_SHORT).show();
            }
        } else if ("10".equals(id)) {//亲子任务（2才有）
            if (SPUtils.get(MoreServiceActivity.this, "BabyState", "").equals("2")) {
                startActivity(new Intent(MoreServiceActivity.this, ParentTaskActivity.class));
            } else {
                Toast.makeText(MoreServiceActivity.this, "亲爱的,该功能只有已出生状态才能使用哦!", Toast.LENGTH_SHORT).show();
            }
        } else if ("11".equals(id)) {//成长曲线（2才有）
            if (SPUtils.get(MoreServiceActivity.this, "BabyState", "").equals("2")) {
                Intent intent = new Intent(MoreServiceActivity.this, GrowthCurveActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MoreServiceActivity.this, "亲爱的,该功能只有已出生状态才能使用哦!", Toast.LENGTH_SHORT).show();
            }
        } else if ("12".equals(id)) {//疫苗提醒（2才有）
            if (SPUtils.get(MoreServiceActivity.this, "BabyState", "").equals("2")) {
                startActivity(new Intent(MoreServiceActivity.this, GrowSeedlingsTimeActivity.class));
            } else {
                Toast.makeText(MoreServiceActivity.this, "亲爱的,该功能只有已出生状态才能使用哦!", Toast.LENGTH_SHORT).show();
            }
        } else if ("13".equals(id)) {//月子餐（2才有）
            if (SPUtils.get(MoreServiceActivity.this, "BabyState", "").equals("2")) {
//                if (days >= 40) {
//                    days = 40;
//                } else {
//                    days = days + 1;
//                }
                Intent intent = new Intent(MoreServiceActivity.this, ServiceActivity.class);
                intent.putExtra("url", "http://cms.env365.cn/yueZiCan.jspx?" + "day=" + days);
                intent.putExtra("title", "月子餐");
                intent.putExtra("flag", "2");
                startActivity(intent);
            } else {
                Toast.makeText(MoreServiceActivity.this, "亲爱的,该功能只有已出生状态才能使用哦!", Toast.LENGTH_SHORT).show();
            }
        } else if ("14".equals(id)) {//能不能吃（0，1,2都有）
            Intent intent = new Intent(MoreServiceActivity.this, ServiceActivity.class);
            intent.putExtra("url", "http://webview.babytree.com/api/mobile_toolcms/can_eat");
            intent.putExtra("title", "能不能吃");
            intent.putExtra("flag", "2");
            startActivity(intent);
        } else if ("15".equals(id)) {//成长评测（2才有）
            if (SPUtils.get(MoreServiceActivity.this, "BabyState", "").equals("2")) {
                Intent intent = new Intent(MoreServiceActivity.this, GrowUpAppraisalActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("year", year);
                intent.putExtra("month", String.valueOf(month));
                startActivity(intent);
            } else {
                Toast.makeText(MoreServiceActivity.this, "亲爱的,该功能只有已出生状态才能使用哦!", Toast.LENGTH_SHORT).show();
            }
        } else if ("16".equals(id)) {//知识百科
            Intent intent = new Intent(MoreServiceActivity.this, ParentingEncyclopediaActivity.class);
            startActivity(intent);
        }/*else if ("100".equals(id)) {//更多（0，1,2都有）
            startActivity(new Intent(MoreServiceActivity.this, MoreServiceActivity.class));
        }*/
    }


    private void initSave() {
        str1 = "";
        mList4.clear();

        for (int i = 0; i < mList1.size(); i++) {
            if (mList1.get(i).getUserId() != null) {
                mList4.add(mList1.get(i).getId());
            }
        }

        for (int i = 0; i < mList2.size(); i++) {
            if (mList2.get(i).getUserId() != null) {
                mList4.add(mList2.get(i).getId());
            }
        }

        for (int i = 0; i < mList3.size(); i++) {
            if (mList3.get(i).getUserId() != null) {
                mList4.add(mList3.get(i).getId());
            }
        }

        for (int i = 0; i < mList4.size(); i++) {
            if (i != mList4.size() - 1) {
                if (!mList4.get(i).equals("")) {
                    str1 = str1 + mList4.get(i) + ",";
                }
            } else {
                str1 = str1 + mList4.get(i);
            }
        }
//        Log.e("xcfxcfxcf1", str1 + "");

        if (!"".equals(str1)) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("itfaceId", "105");
            hashMap.put("token", SPUtils.get(MoreServiceActivity.this, "token", ""));
            hashMap.put("ids", str1);
            JSONObject jsonObject = new JSONObject(hashMap);

            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<HomeSaveBeans>(MoreServiceActivity.this) {
                        @Override
                        public void onSuccess(HomeSaveBeans mHomeSaveBeans, Call call, Response response) {
                            if (mHomeSaveBeans.getResultCode() == 1) {
//                                Log.e("xcfxcfxcf2", "上传工具集成功");
                                Toast.makeText(MoreServiceActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            } else {
                                App.ErrorToken(mHomeSaveBeans.getResultCode(), mHomeSaveBeans.getMsg());
                            }
                        }
                    });
        }
    }
}
