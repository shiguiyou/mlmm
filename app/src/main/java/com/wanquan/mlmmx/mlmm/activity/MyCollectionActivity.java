package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.CircleDetailsAdapter;
import com.wanquan.mlmmx.mlmm.adapter.CircleFragmentAdapter;
import com.wanquan.mlmmx.mlmm.adapter.CircleFragmentOneListViewAdapter;
import com.wanquan.mlmmx.mlmm.adapter.MyCollectionAdapter;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.MyCollectionBeans;
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
 * 描述：我的收藏
 * 作者：薛昌峰
 * 时间：2017.12.16
 */
public class MyCollectionActivity extends BaseActivity/* implements CircleFragmentAdapter.InterfaceIsCollection */ {
    private PullToRefreshScrollView mMyCollectionPullToRefreshScrollView;
    private MyListView mMyCollectionListView;
    private LinearLayout mMyCollectionLl;

    //    List<MyCollectionBeans.DataBean> mList = new ArrayList<>();
//    MyCollectionAdapter mMyCollectionAdapter;
    private CircleFragmentAdapter mCircleFragmentAdapter;
    private List<CircleFragmentBeans.DataBean> mList = new ArrayList();
    private CircleFragmentOneListViewAdapter mCircleFragmentOneRecyclerViewAdapter;
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
        manageUtils.setSystemBarTintManage(MyCollectionActivity.this, R.color.tops);


        //注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        initData();
        initRefresh();
        initListeners();

//        mMyCollectionAdapter = new MyCollectionAdapter(this,mList);
//        mMyCollectionListView.setAdapter(mMyCollectionAdapter);

//        mMyCollectionListView.setEmptyView(mMyCollectionLL);
//        mCircleFragmentAdapter = new CircleFragmentAdapter(this, mList, 1);
//        mCircleFragmentAdapter.InterfaceIsCollection(this);
//        mMyCollectionListView.setAdapter(mCircleFragmentAdapter);

        mCircleFragmentOneRecyclerViewAdapter = new CircleFragmentOneListViewAdapter(mList, this, 1);
        mMyCollectionListView.setAdapter(mCircleFragmentOneRecyclerViewAdapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_my_collection;
    }

    @Override
    public void initView() throws Exception {

        mMyCollectionPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.MyCollection_PullToRefreshScrollView);
        mMyCollectionListView = (MyListView) findViewById(R.id.MyCollection_ListView);
        mMyCollectionLl = (LinearLayout) findViewById(R.id.MyCollection_ll);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        //取消关注的动态爱心显示
        if (event.getItFaceCollect()) {
            page = 1;
            initData();
        }
    }

    private void initRefresh() {
        mMyCollectionPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mMyCollectionPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                initData();
                mMyCollectionPullToRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page++;
                initData();
                mMyCollectionPullToRefreshScrollView.onRefreshComplete();
            }
        });
    }

    private void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "056");
        hashMap.put("token", SPUtils.get(MyCollectionActivity.this, "token", ""));
        hashMap.put("pageNo", page);
        hashMap.put("pageSize", "10");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<CircleFragmentBeans>(this) {
                    @Override
                    public void onSuccess(CircleFragmentBeans mCircleFragmentBeans, Call call, Response response) {
                        if (mCircleFragmentBeans.getResultCode() == 1) {
                            if (page == 1) {
                                mList.clear();
                            }
                            mList.addAll(mCircleFragmentBeans.getData());
                            mCircleFragmentOneRecyclerViewAdapter.notifyDataSetChanged();
                            mMyCollectionListView.setEmptyView(mMyCollectionLl);

                        } else {
                            App.ErrorToken(mCircleFragmentBeans.getResultCode(), mCircleFragmentBeans.getMsg());
                        }
                        mMyCollectionPullToRefreshScrollView.onRefreshComplete();
                    }
                });
    }

    private void initListeners() {
        mMyCollectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyCollectionActivity.this, CircleDetailsActivity.class);
                intent.putExtra("id", mList.get(position).getId());
                intent.putExtra("userId", mList.get(position).getUserId());
                intent.putExtra("headico", mList.get(position).getHeadIco());
                intent.putExtra("name", mList.get(position).getUserName());
                intent.putExtra("name2", mList.get(position).getNickName());
                intent.putExtra("message", mList.get(position).getMessage());
                intent.putExtra("createTime", mList.get(position).getCreateTime());
                intent.putExtra("time", mList.get(position).getCreateTime());
                intent.putExtra("title", mList.get(position).getTitle());
                intent.putExtra("content", mList.get(position).getContent());
                intent.putExtra("img", mList.get(position).getImg());
                if (mList.get(position).isCollect()) {
                    intent.putExtra("follow", 1);
                } else {
                    intent.putExtra("follow", 0);
                }
                startActivity(intent);
            }
        });
    }

    public void MyCollection_Bank(View view) {
        finish();
    }

//    @Override
//    public void isCollection(String str) {
//        initData();
//    }
}
