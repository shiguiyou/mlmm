package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.MyFenSiActivityAdapter;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentThreeBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.MyFenSiActivityBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MyListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：我的粉丝
 * 作者：薛昌峰
 * 时间：2018.06.13
 */
public class MyFenSiActivity extends BaseActivity {
    private LinearLayout mAppTitleBank;
    private TextView mAppTitleName;
    private TextView mAppTitleSave;
    private PullToRefreshScrollView mMyFenSiActivityPullToRefreshScrollView;
    private MyListView mMyFenSiActivityListView;
    private LinearLayout mMyFenSiActivityLl;
    private TextView mMyFenSiActivityContent;

    private MyFenSiActivityAdapter mMyFenSiActivityAdapter;
    private List<MyFenSiActivityBeans.DataBean> mList = new ArrayList<>();
    private int pageNo = 1;

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(MyFenSiActivity.this, R.color.black);

        //注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        initRefresh();
        initListeners();

        mMyFenSiActivityListView.setEmptyView(mMyFenSiActivityLl);
        mMyFenSiActivityAdapter = new MyFenSiActivityAdapter(mList, this);
        mMyFenSiActivityListView.setAdapter(mMyFenSiActivityAdapter);

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_my_fen_si;
    }

    @Override
    public void initView() throws Exception {
        mAppTitleBank = (LinearLayout) findViewById(R.id.App_Title_Bank);
        mAppTitleName = (TextView) findViewById(R.id.App_Title_Name);
        mAppTitleSave = (TextView) findViewById(R.id.App_Title_Save);
        mMyFenSiActivityPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.MyFenSiActivity_PullToRefreshScrollView);
        mMyFenSiActivityListView = (MyListView) findViewById(R.id.MyFenSiActivity_ListView);
        mMyFenSiActivityLl = (LinearLayout) findViewById(R.id.MyFenSiActivity_ll);
        mMyFenSiActivityContent = (TextView) findViewById(R.id.MyFenSiActivity_Content);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getItFaceId() != null) {
            initData();
        }
    }

    private void initRefresh() {
        mMyFenSiActivityPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mMyFenSiActivityPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                pageNo++;
                initDatas();
            }
        });
    }

    private void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "118");
        hashMap.put("token", SPUtils.get(MyFenSiActivity.this, "token", ""));
        hashMap.put("pageNo", "1");
        hashMap.put("pageSize", "10");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<MyFenSiActivityBeans>(MyFenSiActivity.this) {
                    @Override
                    public void onSuccess(MyFenSiActivityBeans mMyFenSiActivityBeans, Call call, Response response) {
                        if (mMyFenSiActivityBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mMyFenSiActivityBeans.getData());
                            mMyFenSiActivityAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mMyFenSiActivityBeans.getResultCode(), mMyFenSiActivityBeans.getMsg());
                        }
                        mMyFenSiActivityPullToRefreshScrollView.onRefreshComplete();
                    }
                });
    }

    private void initDatas() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "118");
        hashMap.put("token", SPUtils.get(MyFenSiActivity.this, "token", ""));
        hashMap.put("pageNo", pageNo);
        hashMap.put("pageSize", "10");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<MyFenSiActivityBeans>(MyFenSiActivity.this) {
                    @Override
                    public void onSuccess(MyFenSiActivityBeans mMyFenSiActivityBeans, Call call, Response response) {
                        if (mMyFenSiActivityBeans.getResultCode() == 1) {

                            mList.addAll(mMyFenSiActivityBeans.getData());
                            mMyFenSiActivityAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mMyFenSiActivityBeans.getResultCode(), mMyFenSiActivityBeans.getMsg());
                        }
                        mMyFenSiActivityPullToRefreshScrollView.onRefreshComplete();
                    }
                });
    }

    private void initListeners() {
        mAppTitleBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mAppTitleName.setText("我的粉丝");

        mMyFenSiActivityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MyFenSiActivity.this, PostPersonageCenterActivity.class);
                intent.putExtra("id", String.valueOf(mList.get(position).getFid()));
                startActivity(intent);
            }
        });
    }

}
