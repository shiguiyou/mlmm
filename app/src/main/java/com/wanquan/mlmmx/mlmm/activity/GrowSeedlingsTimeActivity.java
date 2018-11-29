package com.wanquan.mlmmx.mlmm.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.GrowSeedlingsTimeAdapter;
import com.wanquan.mlmmx.mlmm.beans.GrowSeedlingsTimeBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：疫苗时间表
 * 作者：薛昌峰
 * 时间：2017.09.10
 */
public class GrowSeedlingsTimeActivity extends BaseActivity implements GrowSeedlingsTimeAdapter.InterFaceGrowSeedlings {
    private TextView mGrowSeedlingsTimeTextView;
    private RecyclerView mGrowSeedlingsTimeRecyclerView;
    private PullToRefreshScrollView mGrowSeedlingsTimeScrollView;
    private GrowSeedlingsTimeAdapter mGrowSeedlingsTimeAdapter;
    private List<GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean> mList = new ArrayList<>();
    private List<GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean.ListMapBean> mList2 = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private boolean flag = true;
    private String monthFirstVaccineDate;
    private List<String> mList3 = new ArrayList<>();
    private String string;
    private int position;

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        flag = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(GrowSeedlingsTimeActivity.this, R.color.black);

        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_grow_seedlings_time;
    }

    @Override
    public void initView() throws Exception {
//        mGrowSeedlingsTimeScrollView = (PullToRefreshScrollView) findViewById(R.id.GrowSeedlingsTime_ScrollView);
        mGrowSeedlingsTimeTextView = (TextView) findViewById(R.id.GrowSeedlingsTime_TextView);
        mGrowSeedlingsTimeRecyclerView = (RecyclerView) findViewById(R.id.GrowSeedlingsTime_RecyclerView);
    }

    private void initData() {
        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        Date curDate = new Date(System.currentTimeMillis());
        final String times = formatter.format(curDate);
//        Log.e("疫苗时刻表", String.valueOf(SPUtils.get(GrowSeedlingsTimeActivity.this, "token", "")));
//        Log.e("疫苗时刻表", String.valueOf(SPUtils.get(GrowSeedlingsTimeActivity.this, "isQieHuanBBId", "")));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "032");
        hashMap.put("token", SPUtils.get(GrowSeedlingsTimeActivity.this, "token", ""));
        hashMap.put("babyId", SPUtils.get(GrowSeedlingsTimeActivity.this, "babyId", ""));
        JSONObject jsonObject = new JSONObject(hashMap);
        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<GrowSeedlingsTimeBeans>(this) {
                    @Override
                    public void onSuccess(GrowSeedlingsTimeBeans mGrowSeedlingsTimeBeans, Call call, Response response) {
                        if (mGrowSeedlingsTimeBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mGrowSeedlingsTimeBeans.getData().getMonthVaccine());
                            mGrowSeedlingsTimeTextView.setText(mGrowSeedlingsTimeBeans.getData().getDistanceVaccineDate() + "");
                            for (int i = 0; i < mList.size(); i++) {
                                for (int j = 0; j < mList.get(i).getListMap().size(); j++) {
                                    DateTime now = DateTime.now();
                                    String vaccineDate = mList.get(i).getListMap().get(j).getVaccineDate();
                                    DateTime dateTime = DateTime.parse(vaccineDate);
                                    int days = Days.daysBetween(now, dateTime).getDays();

                                    if (days > 0 && mList.get(i).getListMap().get(j).getStatus() != 2) {
                                        mList3.add(mList.get(i).getMonthFirstVaccineDate());
                                        break;
                                    }
                                }
                            }
                            for (int i = 0; i < mList3.size(); i++) {
                                monthFirstVaccineDate = mList3.get(0).toString();
//                                Log.e("fsfafa", monthFirstVaccineDate);
                            }
                            for (int i = 0; i < mList.size(); i++) {
                                if (monthFirstVaccineDate.equals(mList.get(i).getMonthFirstVaccineDate())) {
                                    position = i;
//                                    Log.e("fsfafaposition", String.valueOf(position));
                                }
                            }
                            linearLayoutManager = new LinearLayoutManager(GrowSeedlingsTimeActivity.this);
                            mGrowSeedlingsTimeRecyclerView.setLayoutManager(linearLayoutManager);

                            mGrowSeedlingsTimeAdapter = new GrowSeedlingsTimeAdapter(mList, GrowSeedlingsTimeActivity.this, flag, monthFirstVaccineDate);
                            flag = false;
                            mGrowSeedlingsTimeAdapter.setInterFaceGrowSeedlings(GrowSeedlingsTimeActivity.this);
                            mGrowSeedlingsTimeRecyclerView.setAdapter(mGrowSeedlingsTimeAdapter);
                            mGrowSeedlingsTimeAdapter.notifyDataSetChanged();
                            linearLayoutManager.scrollToPositionWithOffset(position, 0);
//                                }
//                            }
//                            mGrowSeedlingsTimeScrollView.onRefreshComplete();
//                            mGrowSeedlingsTimeRecyclerView.smoothScrollToPosition(4);
                        } else {
                            App.ErrorToken(mGrowSeedlingsTimeBeans.getResultCode(), mGrowSeedlingsTimeBeans.getMsg());
                        }
//                        mGrowSeedlingsTimeAdapter.setmDataBeanList(mList);
//                        for (int i = 0; i < mGrowSeedlingsTimeAdapter.getGroupCount(); i++) {
//                            mGrowSeedlingsTimeListView.expandGroup(i);
//                        }
                    }
                });
    }

    private void initRefresh() {
//        mGrowSeedlingsTimeTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mGrowSeedlingsTimeListView.setSelectionFromTop(5, 0);
//            }
//        });
//        mGrowSeedlingsTimeScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//                initData();
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//
//            }
//        });
    }

    private void initListeners() {

    }

    public void GrowSeedlingsTimeActivity_Bank(View view) {
        finish();
    }

    @Override
    public void setSize(int positions) {
//        Log.e("sdsdsdsactivity", String.valueOf(position));
        position = positions;
        initData();
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//
//        mGrowSeedlingsTimeRecyclerView.setLayoutManager(linearLayoutManager);
////
////        mGrowSeedlingsTimeAdapter = new QuickeningHistoryRecyclerViewAdapter(mList, this);
////        mGrowSeedlingsTimeAdapter.setInterFaceGrowSeedlings(this);
////        mGrowSeedlingsTimeRecyclerView.setAdapter(mGrowSeedlingsTimeAdapter);
//        linearLayoutManager.scrollToPositionWithOffset(5, 0);
//        mGrowSeedlingsTimeAdapter = new QuickeningHistoryRecyclerViewAdapter(mList, this);
//        mGrowSeedlingsTimeAdapter.setInterFaceGrowSeedlings(this);
//        mGrowSeedlingsTimeRecyclerView.setAdapter(mGrowSeedlingsTimeAdapter);

    }

}
