package com.wanquan.mlmmx.mlmm.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.CircleDetailsActivity;
import com.wanquan.mlmmx.mlmm.activity.MessageCenterActivity;
import com.wanquan.mlmmx.mlmm.adapter.MessageReplayAdapter;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.MessageReplayBenas;
import com.wanquan.mlmmx.mlmm.beans.MessageReplayCircleDetailsBenas;
import com.wanquan.mlmmx.mlmm.beans.PersonalinformationActivityBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
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
 * Created by Administrator on 2017/12/15.
 * 回复
 */

public class MessageReplayFragment extends BaseFragment implements MessageReplayAdapter.InterfaceMessageReplay {
    private PullToRefreshScrollView mMessageReplayPullToRefreshScrollView;
    private MyListView mMessageReplayListView;
    private LinearLayout mMessageReplayLl;

    private List<MessageReplayBenas.DataBean> mList = new ArrayList<>();
    private MessageReplayAdapter mMessageReplayAdapter;
    private View view;
    private LogoutReceiver logoutReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.message_replay_fragment, null);

        initViews();
        initData();
        initSetRefresh();
        initListeners();

        logoutReceiver = new LogoutReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.xcf.receiver");
        getContext().registerReceiver(logoutReceiver, filter);

        mMessageReplayListView.setEmptyView(mMessageReplayLl);
        mMessageReplayAdapter = new MessageReplayAdapter(mList, getContext());
        mMessageReplayAdapter.setMessageReplay(this);
        mMessageReplayListView.setAdapter(mMessageReplayAdapter);

        return view;
    }

    private void initViews() {
        mMessageReplayPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.MessageReplay_PullToRefreshScrollView);
        mMessageReplayListView = (MyListView) view.findViewById(R.id.MessageReplay_ListView);
        mMessageReplayLl = (LinearLayout) view.findViewById(R.id.MessageReplay_ll);
    }

    private class LogoutReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.xcf.receiver")) {
                int logoutFlag = intent.getIntExtra("receiver", -1);
                Log.e("TAGlo", "-->" + logoutFlag);
                if (logoutFlag == -2) {
                    initData();
                }
            }
        }
    }

    private void initSetRefresh() {
        mMessageReplayPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mMessageReplayPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //下拉刷新时隐藏红点
                SPUtils.put(getContext(), "hongdianShow2", "false");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.setIsReceiverCode(2);
                        EventBus.getDefault().post(messageEvent);
                    }
                }, 500);
                initData();
                mMessageReplayPullToRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//                page++;
//                initNetWorks(page);
                mMessageReplayPullToRefreshScrollView.onRefreshComplete();
            }
        });
    }

    private void initData() {
//        Log.e("rrrrrrrruserid", String.valueOf(SPUtils.get(getContext(), "userid", "")));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "059");
        hashMap.put("token", SPUtils.get(getContext(), "token", ""));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<MessageReplayBenas>(getContext()) {
                    @Override
                    public void onSuccess(MessageReplayBenas mMessageReplayBenas, Call call, Response response) {
                        if (mMessageReplayBenas.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mMessageReplayBenas.getData());
                            mMessageReplayAdapter.setReplayAdapter(mList);
                            mMessageReplayAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mMessageReplayBenas.getResultCode(), mMessageReplayBenas.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
        mMessageReplayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ddfdfdfd", String.valueOf(mList.get(position).getForumId()));
                initNetWork(position);
            }
        });
    }

    private void initNetWork(final int position) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "061");
        hashMap.put("token", SPUtils.get(getContext(), "token", ""));
        hashMap.put("id", mList.get(position).getForumId());
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<MessageReplayCircleDetailsBenas>(getContext()) {
                    @Override
                    public void onSuccess(MessageReplayCircleDetailsBenas mMessageReplayCircleDetailsBenas, Call call, Response response) {
                        if (mMessageReplayCircleDetailsBenas.getResultCode() == 1) {
                            Intent intent = new Intent(getContext(), CircleDetailsActivity.class);
                            intent.putExtra("id", mMessageReplayCircleDetailsBenas.getData().getId());
                            intent.putExtra("headico", mMessageReplayCircleDetailsBenas.getData().getHeadIco());
                            intent.putExtra("name", mMessageReplayCircleDetailsBenas.getData().getUserName());
                            intent.putExtra("name2", mMessageReplayCircleDetailsBenas.getData().getNickName());
                            intent.putExtra("message", "");
                            intent.putExtra("time", mMessageReplayCircleDetailsBenas.getData().getCreateTime());
                            intent.putExtra("createTime", mMessageReplayCircleDetailsBenas.getData().getCreateTime());
                            intent.putExtra("title", mMessageReplayCircleDetailsBenas.getData().getTitle());
                            intent.putExtra("content", mMessageReplayCircleDetailsBenas.getData().getContent());
                            intent.putExtra("img", mMessageReplayCircleDetailsBenas.getData().getImg());
                            intent.putExtra("follow", mMessageReplayCircleDetailsBenas.getData().getFollow());
                            startActivity(intent);
                        } else {
                            App.ErrorToken(mMessageReplayCircleDetailsBenas.getResultCode(), mMessageReplayCircleDetailsBenas.getMsg());

                        }
                    }
                });
    }

    @Override
    public void delete(String str) {
        initData();
    }
}
