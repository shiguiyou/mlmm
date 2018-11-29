package com.wanquan.mlmmx.mlmm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.InviteRecordAdapter;
import com.wanquan.mlmmx.mlmm.beans.InviteRecordBeans;
import com.wanquan.mlmmx.mlmm.beans.ParentMessageBeans;
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
 * 描述：邀请记录
 * 作者：薛昌峰
 * 时间：2018.03.01
 */
public class InviteRecordActivity extends BaseActivity implements InviteRecordAdapter.InterfaceFinsh {
    private ListView mInviteRecordLV;
    private LinearLayout mInviteRecordLL;
    private List<InviteRecordBeans.DataBean> mList = new ArrayList();
    private InviteRecordAdapter mInviteRecordAdapter;
    private int babyid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(InviteRecordActivity.this, R.color.black);
        babyid = getIntent().getIntExtra("babyid", 0);
        initData();
        initListeners();

        mInviteRecordLV.setEmptyView(mInviteRecordLL);
        mInviteRecordAdapter = new InviteRecordAdapter(this, mList);
        mInviteRecordAdapter.finish(this);
        mInviteRecordLV.setAdapter(mInviteRecordAdapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_invite_record;
    }

    @Override
    public void initView() throws Exception {
        mInviteRecordLV = (ListView) findViewById(R.id.InviteRecord_LV);
        mInviteRecordLL = (LinearLayout) findViewById(R.id.InviteRecord_ll);
    }

    private void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "081");
        hashMap.put("babyId", babyid);
        hashMap.put("token", SPUtils.get(this, "token", ""));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<InviteRecordBeans>(InviteRecordActivity.this) {
                    @Override
                    public void onSuccess(InviteRecordBeans mInviteRecordBeans, Call call, Response response) {
                        if (mInviteRecordBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mInviteRecordBeans.getData());
                            mInviteRecordAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mInviteRecordBeans.getResultCode(), mInviteRecordBeans.getMsg());

                        }
                    }
                });
    }

    private void initListeners() {
    }

    public void InviteRecordActivity_Bank(View view) {
        finish();
    }

    @Override
    public void initfinish() {
        initData();
    }
}
