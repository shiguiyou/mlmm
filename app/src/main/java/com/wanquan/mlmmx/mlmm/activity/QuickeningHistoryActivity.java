package com.wanquan.mlmmx.mlmm.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.GrowSeedlingsTimeAdapter;
import com.wanquan.mlmmx.mlmm.adapter.QuickeningHistoryRecyclerViewAdapter;
import com.wanquan.mlmmx.mlmm.beans.FetalMovementOperationBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.QuickeningHistoryBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

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
 * 描述：胎动历史记录
 * 作者：薛昌峰
 * 时间：2018.05.10
 */
public class QuickeningHistoryActivity extends BaseActivity {
    private RecyclerView mQuickeningHistoryRecyclerView;
    private QuickeningHistoryRecyclerViewAdapter mQuickeningHistoryRecyclerViewAdapter;
    private List<QuickeningHistoryBeans.DataBean> mList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout mQuickeningHistoryLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(QuickeningHistoryActivity.this, R.color.black);

        //注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        initData();

        linearLayoutManager = new LinearLayoutManager(QuickeningHistoryActivity.this);
        mQuickeningHistoryRecyclerView.setLayoutManager(linearLayoutManager);
        mQuickeningHistoryRecyclerViewAdapter = new QuickeningHistoryRecyclerViewAdapter(mList, QuickeningHistoryActivity.this);
        mQuickeningHistoryRecyclerView.setAdapter(mQuickeningHistoryRecyclerViewAdapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_quickening_history;
    }

    @Override
    public void initView() throws Exception {
        mQuickeningHistoryLL= (LinearLayout) findViewById(R.id.QuickeningHistory_LL);
        mQuickeningHistoryRecyclerView = (RecyclerView) findViewById(R.id.QuickeningHistory_RecyclerView);
    }

    private void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "101");
        hashMap.put("token", SPUtils.get(QuickeningHistoryActivity.this, "token", ""));
//        hashMap.put("all", "all");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<QuickeningHistoryBeans>(QuickeningHistoryActivity.this) {
                    @Override
                    public void onSuccess(QuickeningHistoryBeans mQuickeningHistoryBeans, Call call, Response response) {
                        if (mQuickeningHistoryBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mQuickeningHistoryBeans.getData());
                            if (mList.size()==0){
                                mQuickeningHistoryLL.setVisibility(View.VISIBLE);
                            }else {
                                mQuickeningHistoryLL.setVisibility(View.GONE);
                            }
                            mQuickeningHistoryRecyclerViewAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mQuickeningHistoryBeans.getResultCode(), mQuickeningHistoryBeans.getMsg());
                        }
                    }
                });
    }

    //EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        boolean show = event.isTaiDong();
        if (show) {
            initData();
        }
    }

    public void QuickeningHistoryActivity_Bank(View view) {
        finish();
    }
}
