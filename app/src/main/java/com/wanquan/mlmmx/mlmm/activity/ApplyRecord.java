package com.wanquan.mlmmx.mlmm.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.ApplyExperienceAdapter;
import com.wanquan.mlmmx.mlmm.adapter.ApplyRecordAdapter;
import com.wanquan.mlmmx.mlmm.beans.ApplyBeans;
import com.wanquan.mlmmx.mlmm.beans.ApplyRecordBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：申请记录
 * 这种：薛昌峰
 * 时间：2018.08.28
 */

public class ApplyRecord extends BaseActivity {
    private LinearLayout mAppTitleBank;
    private TextView mAppTitleName;
    private TextView mAppTitleSave;
    private PullToRefreshListView mApplyRecordListView;
    private LinearLayout mApplyRecordLl;

    private List<ApplyRecordBeans.DataBean> mList = new ArrayList<>();
    private ApplyRecordAdapter mApplyRecordAdapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(ApplyRecord.this, R.color.top);

        initData();
        initRefresh();
        initListeners();

        mApplyRecordListView.setEmptyView(mApplyRecordLl);
        mApplyRecordAdapter = new ApplyRecordAdapter(this, mList);
        mApplyRecordListView.setAdapter(mApplyRecordAdapter);

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_apply_record;
    }

    @Override
    public void initView() throws Exception {
        mAppTitleBank = (LinearLayout) findViewById(R.id.App_Title_Bank);
        mAppTitleName = (TextView) findViewById(R.id.App_Title_Name);
        mAppTitleSave = (TextView) findViewById(R.id.App_Title_Save);
        mApplyRecordListView = (PullToRefreshListView) findViewById(R.id.ApplyRecord_ListView);
        mApplyRecordLl = (LinearLayout) findViewById(R.id.ApplyRecord_ll);
    }


    private void initRefresh() {
        mApplyRecordListView.setMode(PullToRefreshBase.Mode.BOTH);
        mApplyRecordListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                initData();
            }
        });
    }

    private void initData() {
        mAppTitleName.setText("申请记录");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "124");
        hashMap.put("token", SPUtils.get(ApplyRecord.this, "token", ""));
        hashMap.put("pageNo", page);
        hashMap.put("pageSize", "10");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<ApplyRecordBeans>(ApplyRecord.this) {
                    @Override
                    public void onSuccess(ApplyRecordBeans mApplyRecordBeans, Call call, Response response) {
                        if (mApplyRecordBeans.getResultCode() == 1) {
                            if (page == 1) {
                                mList.clear();
                            }
                            mList.addAll(mApplyRecordBeans.getData());
                            mApplyRecordAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mApplyRecordBeans.getResultCode(), mApplyRecordBeans.getMsg());
                        }
                        mApplyRecordListView.onRefreshComplete();
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
    }

}
