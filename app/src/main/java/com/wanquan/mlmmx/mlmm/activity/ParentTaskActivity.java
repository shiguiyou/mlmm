package com.wanquan.mlmmx.mlmm.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.xmly.adapter.ParentTaskAdapter;
import com.wanquan.mlmmx.mlmm.xmly.beans.ParentTaskBeans;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：亲子任务
 * 作者：薛昌峰
 * 时间：2018.01.17
 */
public class ParentTaskActivity extends BaseActivity implements ParentTaskAdapter.ParentTaskAdapterInterface{
    private ListView mParentTaskListView;
    private ParentTaskAdapter mParentTaskAdapter;
    private List<ParentTaskBeans.DataBean.VoBean> mList = new ArrayList<>();
    private Set<String> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(ParentTaskActivity.this, R.color.tops);

        initData();
        initListeners();

        mParentTaskAdapter = new ParentTaskAdapter(this, mList);
        mParentTaskAdapter.ParentTaskAdapterInterface(this);
        mParentTaskListView.setAdapter(mParentTaskAdapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_parent_task;
    }

    @Override
    public void initView() throws Exception {
        mParentTaskListView = (ListView) findViewById(R.id.ParentTask_ListView);
    }

    private void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "072");
        hashMap.put("token", SPUtils.get(ParentTaskActivity.this, "token", ""));
        hashMap.put("babyId", SPUtils.get(ParentTaskActivity.this, "babyId", ""));

        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<ParentTaskBeans>(this) {
                    @Override
                    public void onSuccess(ParentTaskBeans mParentTaskBeans, Call call, Response response) {
                        if (mParentTaskBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mParentTaskBeans.getData().getVo());
                            mParentTaskAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mParentTaskBeans.getResultCode(), mParentTaskBeans.getMsg());

                        }
                    }
                });
    }

    private void initListeners() {
    }

    public void ParentTaskActivity_Bank(View view) {
        finish();
    }

    @Override
    public void refresh() {
        initData();
    }
}
