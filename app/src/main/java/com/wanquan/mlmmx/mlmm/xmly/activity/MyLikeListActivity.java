package com.wanquan.mlmmx.mlmm.xmly.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.BaseActivity;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.xmly.adapter.MyLikeListAdapter;
import com.wanquan.mlmmx.mlmm.xmly.beans.MyLikeListActivityBeans;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：我喜欢列表
 * 作者：薛昌峰
 * 时间：2018.01.12
 */
public class MyLikeListActivity extends BaseActivity {
    private List<MyLikeListActivityBeans.DataBean> mList = new ArrayList<>();
    private List<MyLikeListActivityBeans.DataBean> mList2 = new ArrayList<>();
    private MyLikeListAdapter mMyLikeListAdapter;
    private ListView mMyLikeListActivityListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(MyLikeListActivity.this, R.color.tops);

        initData();
        initListeners();

        mMyLikeListAdapter = new MyLikeListAdapter(this, mList2);
        mMyLikeListActivityListView.setAdapter(mMyLikeListAdapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_my_like_list;
    }

    @Override
    public void initView() throws Exception {
        mMyLikeListActivityListView = (ListView) findViewById(R.id.MyLikeListActivity_ListView);
    }

    private void initData() {
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("itfaceId", "067");
        hashMap2.put("token", SPUtils.get(MyLikeListActivity.this, "token", ""));
        JSONObject jsonObject2 = new JSONObject(hashMap2);
        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject2.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<MyLikeListActivityBeans>(MyLikeListActivity.this) {
                    @Override
                    public void onSuccess(MyLikeListActivityBeans mMyLikeListActivityBeans, Call call, Response response) {
                        if (mMyLikeListActivityBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mMyLikeListActivityBeans.getData());
                            mMyLikeListAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mMyLikeListActivityBeans.getResultCode(), mMyLikeListActivityBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
        mMyLikeListActivityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (App.mediaPlayer2 != null) {
                    App.mediaPlayer2.stop();
                }
                SPUtils.put(MyLikeListActivity.this, "flag", "3");
                SPUtils.put(MyLikeListActivity.this, "isDown", "2");
                Intent intent = new Intent(MyLikeListActivity.this, PlayActivity.class);
                intent.putExtra("title", mList.get(position).getVoice_title());
                intent.putExtra("mp3", mList.get(position).getVoice_url());
                intent.putExtra("position", position);
//                intent.putExtra("img", mList.get(position).getCoverUrlLarge());
                intent.putExtra("dataId", id);
                intent.putExtra("id", String.valueOf(mList.get(0).getId()));
                startActivity(intent);
            }
        });
    }

    public void MyLikeListActivity(View view) {
        finish();
    }
}
