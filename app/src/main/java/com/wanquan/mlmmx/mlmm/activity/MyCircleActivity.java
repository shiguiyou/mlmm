package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.CircleFragmentAdapter;
import com.wanquan.mlmmx.mlmm.adapter.CircleFragmentOneListViewAdapter;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MyListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：我的帖子
 * 作者：薛昌峰
 * 时间：2018.04.20
 */
public class MyCircleActivity extends BaseActivity implements CircleFragmentOneListViewAdapter.InterfaceIsCollection {
    private LinearLayout mAppTitleBank;
    private TextView mAppTitleName;
    private TextView mAppTitleSave;
    private PullToRefreshScrollView mMyCircleActivityPullToRefreshScrollView;
    private MyListView mMyCircleActivityListView;
    private LinearLayout mMyCircleActivityLl;
    private TextView mMyCircleActivityContent;
    private TextView mMyCircleActivityTv;

    private CircleFragmentOneListViewAdapter mCircleFragmentOneRecyclerViewAdapter;

    private List<CircleFragmentBeans.DataBean> mList = new ArrayList();
    private int page = 1;

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(MyCircleActivity.this, R.color.tops);

        initData();
        initRefresh();
        initListeners();

//        mCircleFragmentOneRecyclerViewAdapter = new CircleFragmentOneListViewAdapter(mList, MyCircleActivity.this, 2, "null");
//        mCircleFragmentOneRecyclerViewAdapter.InterfaceIsCollection(MyCircleActivity.this);
//        mMyCircleActivityListView.setAdapter(mCircleFragmentOneRecyclerViewAdapter);

//        mCircleFragmentAdapter = new CircleFragmentAdapter(this, mList, 2);
//        mCircleFragmentAdapter.InterfaceIsCollection(this);
//        mMyCircleActivityListView.setAdapter(mCircleFragmentAdapter);

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_my_circle;
    }

    @Override
    public void initView() throws Exception {
        mAppTitleBank = (LinearLayout) findViewById(R.id.App_Title_Bank);
        mAppTitleName = (TextView) findViewById(R.id.App_Title_Name);
        mAppTitleSave = (TextView) findViewById(R.id.App_Title_Save);
        mMyCircleActivityPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.MyCircleActivity_PullToRefreshScrollView);
        mMyCircleActivityListView = (MyListView) findViewById(R.id.MyCircleActivity_ListView);
        mMyCircleActivityLl = (LinearLayout) findViewById(R.id.MyCircleActivity_ll);
        mMyCircleActivityContent = (TextView) findViewById(R.id.MyCircleActivity_Content);
        mMyCircleActivityTv = (TextView) findViewById(R.id.MyCircleActivity_Tv);
    }


    private void initRefresh() {
        mMyCircleActivityPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mMyCircleActivityPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                initData();
                mMyCircleActivityPullToRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page++;
                initData();
                mMyCircleActivityPullToRefreshScrollView.onRefreshComplete();
            }
        });
    }

    private void initData() {
        mAppTitleName.setText("我的帖子");
//        Log.e("dsdsada", String.valueOf(page));

//        if (SpUtil.getBooleanValue(MyCircleActivity.this, MyContant.ISLOGIN, true)) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "048");
        hashMap.put("token", SPUtils.get(MyCircleActivity.this, "token", ""));
        hashMap.put("user", "my");
        hashMap.put("pageNo", page);
        hashMap.put("pageSize", "10");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<CircleFragmentBeans>(MyCircleActivity.this) {
                    @Override
                    public void onSuccess(CircleFragmentBeans mCircleFragmentBeans, Call call, Response response) {
                        if (mCircleFragmentBeans.getResultCode() == 1) {
                            if (page == 1) {
                                mList.clear();
                            }
                            mList.addAll(mCircleFragmentBeans.getData());
//                            for (int i = 0; i <mList.size() ; i++) {
////                                Log.e("dsadsad", String.valueOf(mList.get(i).getId()));
//                            }
                            Log.e("sdsada", String.valueOf(mList.size()));
                            if (mList.size() == 0) {
//                                mMyCircleActivityTv.setText("发帖");
//                                mMyCircleActivityContent.setText("您还没有发过帖哦~");
//                                mMyCircleActivityListView.setVisibility(View.GONE);
//                                mMyCircleActivityLl.setVisibility(View.VISIBLE);
                            } else {
//                                mMyCircleActivityListView.setVisibility(View.VISIBLE);
//                                mMyCircleActivityLl.setVisibility(View.GONE);
                            }
                            mCircleFragmentOneRecyclerViewAdapter = new CircleFragmentOneListViewAdapter(mList, MyCircleActivity.this, 2);
                            mCircleFragmentOneRecyclerViewAdapter.InterfaceIsCollection(MyCircleActivity.this);
                            mMyCircleActivityListView.setAdapter(mCircleFragmentOneRecyclerViewAdapter);
                            mMyCircleActivityListView.setEmptyView(mMyCircleActivityLl);

                        } else {
                            App.ErrorToken(mCircleFragmentBeans.getResultCode(), mCircleFragmentBeans.getMsg());
                        }
                        mMyCircleActivityPullToRefreshScrollView.onRefreshComplete();

                    }
                });
//        } else {
//            mList.clear();
//            mMyCircleActivityListView.setVisibility(View.GONE);
//            mMyCircleActivityLl.setVisibility(View.VISIBLE);
//        }
    }

    private void initListeners() {
        mAppTitleBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mMyCircleActivityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpUtil.getBooleanValue(MyCircleActivity.this, MyContant.ISLOGIN, true)) {
                    Intent intent = new Intent(MyCircleActivity.this, SelectCircleActivity.class);
                    startActivityForResult(intent, 2);
                } else {
                    Intent intent = new Intent(MyCircleActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        mMyCircleActivityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("dsadsad", String.valueOf(position));
                Log.e("dsadsad", String.valueOf(mList.get(position).getId()));

                Intent intent = new Intent(MyCircleActivity.this, CircleDetailsActivity.class);
                intent.putExtra("id", mList.get(position).getId());
                intent.putExtra("userId", mList.get(position).getUserId());
                intent.putExtra("headico", mList.get(position).getHeadIco());
                intent.putExtra("name", mList.get(position).getUserName());
                intent.putExtra("name2", mList.get(position).getNickName());
                intent.putExtra("message", mList.get(position).getMessage());
                intent.putExtra("time", mList.get(position).getCreateTime());
                intent.putExtra("createTime", mList.get(position).getCreateTime());
                intent.putExtra("commentCount", mList.get(position).getCommentCount());
                intent.putExtra("title", mList.get(position).getTitle());
                intent.putExtra("content", mList.get(position).getContent());
                intent.putExtra("img", mList.get(position).getImg());
                intent.putExtra("html", mList.get(position).getLink());

                if (mList.get(position).getFollow() != 0) {
                    intent.putExtra("follow", 1);
                } else {
                    intent.putExtra("follow", 0);
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public void isCollection(String str) {
        initData();
    }
}
