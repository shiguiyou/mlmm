package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.ParentingEncyclopediaTwoAdapter;
import com.wanquan.mlmmx.mlmm.adapter.ParentingRecyclerViewAdapter;
import com.wanquan.mlmmx.mlmm.beans.ParentingEncyclopediaTwoBeans;
import com.wanquan.mlmmx.mlmm.beans.ParentingEncyclopediaNeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MyListView;
import com.wanquan.mlmmx.mlmm.view.MyScrollView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：知识百科
 * 作者：薛昌峰
 * 时间：2018.09.14
 */
public class ParentingEncyclopediaActivity extends BaseActivity implements ParentingRecyclerViewAdapter.ParentingEncyclopediaInterface {
    private LinearLayout mParentingEncyclopediaBank;
    private TextView mParentingEncyclopediaSearch;
    private RecyclerView mParentingEncyclopediaRecyclerView;
    private MyScrollView mParentingEncyclopediaMyScrollView;
    private MyListView mParentingEncyclopediaRecyclerViewTwo;

    private ParentingRecyclerViewAdapter mParentingRecyclerViewAdapter;
    private List<ParentingEncyclopediaNeans.DataBean> mList = new ArrayList<>();

    private ParentingEncyclopediaTwoAdapter mParentingEncyclopediaTwoAdapter;
    private List<ParentingEncyclopediaTwoBeans.DataBean> mList2 = new ArrayList<>();

    private int week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(ParentingEncyclopediaActivity.this, R.color.black);

        //=======================================================================================

//      LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
//      mParentingEncyclopediaRecyclerViewTwo.setLayoutManager(linearLayoutManager2);


        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_parenting_encyclopedia;
    }

    @Override
    public void initView() throws Exception {
        mParentingEncyclopediaBank = (LinearLayout) findViewById(R.id.ParentingEncyclopedia_Bank);
        mParentingEncyclopediaSearch = (TextView) findViewById(R.id.ParentingEncyclopedia_Search);
        mParentingEncyclopediaRecyclerView = (RecyclerView) findViewById(R.id.ParentingEncyclopedia_RecyclerView);
        mParentingEncyclopediaMyScrollView = (MyScrollView) findViewById(R.id.ParentingEncyclopedia_MyScrollView);
        mParentingEncyclopediaRecyclerViewTwo = (MyListView) findViewById(R.id.ParentingEncyclopedia_RecyclerView_Two);
    }

    private void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "129");
//        hashMap.put("token", SPUtils.get(ParentingEncyclopediaActivity.this, "token", ""));
        hashMap.put("pid", 0);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<ParentingEncyclopediaNeans>(ParentingEncyclopediaActivity.this) {
                    @Override
                    public void onSuccess(ParentingEncyclopediaNeans mParentingEncyclopediaNeans, Call call, Response response) {
                        if (mParentingEncyclopediaNeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mParentingEncyclopediaNeans.getData());
                            initFinish(0);
                            mParentingRecyclerViewAdapter.notifyDataSetChanged();
                            initData2(mList.get(0).getId());
                        } else {
                            App.ErrorToken(mParentingEncyclopediaNeans.getResultCode(), mParentingEncyclopediaNeans.getMsg());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(ParentingEncyclopediaActivity.this, "服务连接异常，请稍后重试！", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initFinish(int position) {
        Log.e("dadada", String.valueOf(position));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mParentingEncyclopediaRecyclerView.setLayoutManager(linearLayoutManager);

        mParentingRecyclerViewAdapter = new ParentingRecyclerViewAdapter(mList, this, position);
        mParentingRecyclerViewAdapter.setParentingEncyclopediaInterface(this);
        mParentingEncyclopediaRecyclerView.setAdapter(mParentingRecyclerViewAdapter);
//        mParentingEncyclopediaRecyclerView.smoothScrollToPosition(position);

        mParentingEncyclopediaRecyclerView.scrollToPosition(position);

    }

    private void initData2(int id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "129");
//        hashMap.put("token", SPUtils.get(ParentingEncyclopediaActivity.this, "token", ""));
        hashMap.put("pid", id);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<ParentingEncyclopediaTwoBeans>(ParentingEncyclopediaActivity.this) {
                    @Override
                    public void onSuccess(ParentingEncyclopediaTwoBeans mParentingEncyclopediaTwoBeans, Call call, Response response) {
                        if (mParentingEncyclopediaTwoBeans.getResultCode() == 1) {
                            mList2.clear();
                            mList2.addAll(mParentingEncyclopediaTwoBeans.getData());
                            mParentingEncyclopediaTwoAdapter = new ParentingEncyclopediaTwoAdapter(ParentingEncyclopediaActivity.this, mList2);
                            mParentingEncyclopediaRecyclerViewTwo.setAdapter(mParentingEncyclopediaTwoAdapter);
                            mParentingEncyclopediaTwoAdapter.notifyDataSetChanged();
                            mParentingEncyclopediaMyScrollView.scrollTo(0, 0);
                        } else {
                            App.ErrorToken(mParentingEncyclopediaTwoBeans.getResultCode(), mParentingEncyclopediaTwoBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
        mParentingEncyclopediaBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mParentingEncyclopediaSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ParentingEncyclopediaActivity.this, ParentingEncyclopediaSearchActivity.class));
            }
        });
    }

    @Override
    public void checkGroup(int id, int position) {
        initFinish(position);
//        mParentingEncyclopediaRecyclerView.smoothScrollToPosition(position);
        initData2(id);
    }
}
