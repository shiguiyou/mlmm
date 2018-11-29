package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.SendInvitationClassifyAdapter;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentBeans;
import com.wanquan.mlmmx.mlmm.beans.SendInvitationClassifyBeans;
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
 * 描述：发帖分类
 * 作者：薛昌峰
 * 时间：2017.12.11
 */
public class SendInvitationClassifyActivity extends BaseActivity {
    private GridView mSendInvitationClassifyGV;
    private List<SendInvitationClassifyBeans.DataBean> mList = new ArrayList<>();
    SendInvitationClassifyAdapter mSendInvitationClassifyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(SendInvitationClassifyActivity.this, R.color.tops);

        initData();
        initListeners();

        mSendInvitationClassifyAdapter = new SendInvitationClassifyAdapter(this, mList);
        mSendInvitationClassifyGV.setAdapter(mSendInvitationClassifyAdapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_send_invitation_classify;
    }

    @Override
    public void initView() throws Exception {
        mSendInvitationClassifyGV = (GridView) findViewById(R.id.SendInvitationClassify_GV);
    }

    private void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "045");
        hashMap.put("token", SPUtils.get(this, "token", ""));
        hashMap.put("key", "ask.type");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<SendInvitationClassifyBeans>(SendInvitationClassifyActivity.this) {
                    @Override
                    public void onSuccess(SendInvitationClassifyBeans mSendInvitationClassifyBeans, Call call, Response response) {
                        if (mSendInvitationClassifyBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mSendInvitationClassifyBeans.getData());
                            mSendInvitationClassifyAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mSendInvitationClassifyBeans.getResultCode(), mSendInvitationClassifyBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
        mSendInvitationClassifyGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SendInvitationClassifyActivity.this, SendInvitationActivity.class);
                intent.putExtra("value", mList.get(position).getValue());
                setResult(2, intent);
                finish();
            }
        });
    }

    public void SendInvitationClassifyActivity_Bank(View view) {
        finish();
    }
}
