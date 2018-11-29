package com.wanquan.mlmmx.mlmm.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.MessageCenterActivity;
import com.wanquan.mlmmx.mlmm.activity.RemoteUpgradeActivity;
import com.wanquan.mlmmx.mlmm.activity.SettingActivity;
import com.wanquan.mlmmx.mlmm.adapter.MessageCenterAdapter;
import com.wanquan.mlmmx.mlmm.beans.MessageCenterActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageCenterBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.PersonalinformationActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.RemoteUpgradeFinishBeans;
import com.wanquan.mlmmx.mlmm.beans.RemoteUpgradeOneBeans;
import com.wanquan.mlmmx.mlmm.beans.RemoteUpgradeTwoBeans;
import com.wanquan.mlmmx.mlmm.beans.SettingBabyMessageBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MyListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：提醒
 */

public class MessageRemindFragment extends BaseFragment implements MessageCenterAdapter.MyCollectInterface {
    private PullToRefreshScrollView mMessageRemindPullToRefreshScrollView;
    private MyListView mMessageRemindListView;
    private LinearLayout mMessageRemindLl;

    private MessageCenterAdapter mMessageCenterAdapter;
    public static List<MessageCenterBeans.DataBean> mList = new ArrayList<>();
    private List<String> mList2 = new ArrayList<>();
    private boolean delete = false;
    private boolean isDelete = false;
    public static String str = "";
    private View view;
    private int flag;
    private String extend;

    //    private ImageView mRemoteUpgradeBank;
//    private TextView mRemoteUpgradeV;
//    private TextView mRemoteUpgradeTextView1;
//    private TextView mRemoteUpgradeTextView2;
//    private TextView mRemoteUpgradeTextView3;
//    private TextView mRemoteUpgradeTextView4;
    private int upgradeStatus;
    private String promptMsg;
    private AlertDialog alert;

    private boolean run = true;
    private final Handler handler = new Handler();
    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (run) {
                handler.postDelayed(this, 3000);
                //Finish
                initFinish();
            }
        }
    };
    private boolean runFlag = true;

    private void initFinish() {
        Log.e("ddddddd,", "到了1!");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "128");
        hashMap.put("token", SPUtils.get(getContext(), "token", ""));
        hashMap.put("deviceCode", extend);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<RemoteUpgradeFinishBeans>(getContext()) {
                    @Override
                    public void onSuccess(RemoteUpgradeFinishBeans mRemoteUpgradeOneBeans, Call call, Response response) {
                        if (mRemoteUpgradeOneBeans.getResultCode() == 1) {
                            if (mRemoteUpgradeOneBeans.getData().getUpgradeStatus() == 1) {
                                Log.e("ddddddd,", "恭喜你!");
                                if (runFlag) {
                                    Log.e("ddddddd,", "恭喜你,升级成功!");
                                    runFlag = false;
                                    Toast.makeText(getContext(), "恭喜你,升级成功!", Toast.LENGTH_SHORT).show();
                                    alert.dismiss();
                                }
                            }
                        } else {
                            Toast.makeText(getContext(), "升级失败,请稍后再试!", Toast.LENGTH_SHORT).show();
                            App.ErrorToken(mRemoteUpgradeOneBeans.getResultCode(), mRemoteUpgradeOneBeans.getMsg());
                        }
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.message_remind_fragment, null);

        // 注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        initViews();
        initData();
        initSetRefresh();
        initListeners();

        mMessageRemindListView.setEmptyView(mMessageRemindLl);
        mMessageCenterAdapter = new MessageCenterAdapter(mList, getContext(), delete);
        mMessageCenterAdapter.setMyCollectInterface(this);
        mMessageRemindListView.setAdapter(mMessageCenterAdapter);
        mMessageCenterAdapter.setMessageCenterAdapterList(mList);

        return view;
    }

    private void initViews() {
        mMessageRemindPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.MessageRemind_PullToRefreshScrollView);
        mMessageRemindListView = (MyListView) view.findViewById(R.id.MessageRemind_ListView);
        mMessageRemindLl = (LinearLayout) view.findViewById(R.id.MessageRemind_ll);
    }

    private void initSetRefresh() {
        mMessageRemindPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mMessageRemindPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //下拉刷新时隐藏红点
                SPUtils.put(getContext(), "hongdianShow1", "false");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.setIsReceiverCode(1);
                        EventBus.getDefault().post(messageEvent);
                    }
                }, 500);

                initData();
                mMessageRemindPullToRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//                page++;
//                initNetWorks(page);
                mMessageRemindPullToRefreshScrollView.onRefreshComplete();
            }
        });
    }

    private void isDelete() {
        if (mList.size() != 0) {
            str = "";
            mList2.clear();
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).isChoosed()) {
                    mList2.add(String.valueOf(mList.get(i).getId()));
                }
            }
            for (int i = 0; i < mList2.size(); i++) {
                if (i != mList2.size() - 1) {
                    if (!mList2.get(i).equals("")) {
                        str = str + mList2.get(i) + " ";
                    }
                } else {
                    str = str + mList2.get(i);
                }
//                Log.e("fsfsfsmsgIds1", str + "sss");
//                Log.e("fsfsfstoken", String.valueOf(SPUtils.get(getContext(), "token", "")));
            }

            if (!"".equals(str)) {
                final AlertDialog alert;
                alert = new AlertDialog.Builder(getContext()).create();
                alert.show();
                alert.setCanceledOnTouchOutside(false);

                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.delete);
                alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                //取消
                alert.getWindow().findViewById(R.id.cart_delete_cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                        return;
                    }
                });
                alert.getWindow().findViewById(R.id.cart_delete_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("itfaceId", "029");
                        hashMap.put("token", SPUtils.get(getContext(), "token", ""));
                        hashMap.put("msgIds", str);
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<MessageCenterActivityBeans>(getContext()) {
                                    @Override
                                    public void onSuccess(MessageCenterActivityBeans mMessageCenterActivityBeans, Call call, Response response) {
                                        if (mMessageCenterActivityBeans.getResultCode() == 1) {
                                            Toast.makeText(getContext(), mMessageCenterActivityBeans.getMsg(), Toast.LENGTH_SHORT).show();
                                            MessageEvent messageEvent = new MessageEvent();
                                            messageEvent.setDeleteShow(true);
                                            EventBus.getDefault().post(messageEvent);
                                            initData();
                                        } else {
                                            App.ErrorToken(mMessageCenterActivityBeans.getResultCode(), mMessageCenterActivityBeans.getMsg());
                                        }
                                    }

                                    @Override
                                    public void onError(Call call, Response response, Exception e) {
                                        super.onError(call, response, e);
                                        Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                                    }
                                });
                        alert.dismiss();
                    }
                });
            } else {
                Toast.makeText(getContext(), "请勾选删除项", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "028");
        hashMap.put("token", SPUtils.get(getContext(), "token", ""));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(
                UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<MessageCenterBeans>(getContext()) {
                    @Override
                    public void onSuccess(MessageCenterBeans mMessageCenterBeans, Call call, Response response) {
                        if (mMessageCenterBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mMessageCenterBeans.getData());
                            mMessageCenterAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mMessageCenterBeans.getResultCode(), mMessageCenterBeans.getMsg());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initListeners() {
        mMessageRemindListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog alert;
                alert = new AlertDialog.Builder(getContext()).create();
                alert.show();
                alert.setCanceledOnTouchOutside(false);
                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.delete);
                alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                //取消
                alert.getWindow().findViewById(R.id.cart_delete_cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                        return;
                    }
                });
                alert.getWindow().findViewById(R.id.cart_delete_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Log.e("fsfsfsmsgIds", String.valueOf(mList.get(position).getId()));
//                        Log.e("fsfsfstoken", String.valueOf(SPUtils.get(getContext(), "token", "")));
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("itfaceId", "029");
                        hashMap.put("token", SPUtils.get(getContext(), "token", ""));
                        hashMap.put("msgIds", mList.get(position).getId());
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<MessageCenterActivityBeans>(getContext()) {
                                    @Override
                                    public void onSuccess(MessageCenterActivityBeans mMessageCenterActivityBeans, Call call, Response response) {
                                        if (mMessageCenterActivityBeans.getResultCode() == 1) {
                                            Toast.makeText(getContext(), mMessageCenterActivityBeans.getMsg(), Toast.LENGTH_SHORT).show();
                                            initData();
                                        } else {
                                            App.ErrorToken(mMessageCenterActivityBeans.getResultCode(), mMessageCenterActivityBeans.getMsg());
                                        }
                                    }

                                    @Override
                                    public void onError(Call call, Response response, Exception e) {
                                        super.onError(call, response, e);
                                        Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                                    }
                                });
                        alert.dismiss();
                    }
                });
                return false;
            }
        });

        mMessageRemindListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("itfaceId", "028");
                hashMap.put("token", SPUtils.get(getContext(), "token", ""));
                JSONObject jsonObject = new JSONObject(hashMap);

                OkGo.post(UrlContent.URL).tag(this)
                        .upJson(jsonObject.toString())
                        .connTimeOut(10_000)
                        .execute(new CustomCallBackNoLoading<MessageCenterBeans>(getContext()) {
                            @Override
                            public void onSuccess(MessageCenterBeans mMessageCenterBeans, Call call, Response response) {
                                if (mMessageCenterBeans.getResultCode() == 1) {
                                    if (mMessageCenterBeans.getData().get(i).getReadStatus() == 1) {
                                        extend = mList.get(i).getExtend1();
                                        Log.e("fsdfsdfs", extend + "");
                                        if (!"".equals(extend)) {
                                            HashMap<String, Object> hashMap = new HashMap<>();
                                            hashMap.put("itfaceId", "127");
                                            hashMap.put("token", SPUtils.get(getContext(), "token", ""));
                                            hashMap.put("deviceCode", extend);
                                            JSONObject jsonObject = new JSONObject(hashMap);

                                            OkGo.post(UrlContent.URL).tag(this)
                                                    .upJson(jsonObject.toString())
                                                    .connTimeOut(10_000)
                                                    .execute(new CustomCallBackNoLoading<RemoteUpgradeFinishBeans>(getContext()) {
                                                        @Override
                                                        public void onSuccess(RemoteUpgradeFinishBeans mRemoteUpgradeOneBeans, Call call, Response response) {
                                                            if (mRemoteUpgradeOneBeans.getResultCode() == 1) {
                                                                int upReturnCode = mRemoteUpgradeOneBeans.getData().getReturnCode();
                                                                if (upReturnCode == -3) {
                                                                    Toast.makeText(getContext(), mRemoteUpgradeOneBeans.getData().getPromptMsg(), Toast.LENGTH_SHORT).show();
                                                                } else if (upReturnCode == -1) {
                                                                    Toast.makeText(getContext(), mRemoteUpgradeOneBeans.getData().getPromptMsg(), Toast.LENGTH_SHORT).show();
                                                                } else if (upReturnCode == -2) {
                                                                    flag = -2;
                                                                    initPopupWindows();
//                                                                    startActivity(new Intent(getContext(), RemoteUpgradeActivity.class).putExtra("extend", mList.get(i).getExtend1()).putExtra("flag", "-2"));
                                                                } else if (upReturnCode == 1) {
                                                                    flag = 1;
                                                                    initPopupWindows();
//                                                                    startActivity(new Intent(getContext(), RemoteUpgradeActivity.class).putExtra("extend", mList.get(i).getExtend1()).putExtra("flag", "1"));
                                                                }
                                                            } else {
                                                                Toast.makeText(getContext(), mRemoteUpgradeOneBeans.getData().getPromptMsg(), Toast.LENGTH_SHORT).show();
                                                                App.ErrorToken(mRemoteUpgradeOneBeans.getResultCode(), mRemoteUpgradeOneBeans.getMsg());
                                                            }
                                                        }

                                                        @Override
                                                        public void onError(Call call, Response response, Exception e) {
                                                            super.onError(call, response, e);
                                                            Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    } else {
                                        Toast.makeText(getContext(), mMessageCenterBeans.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    App.ErrorToken(mMessageCenterBeans.getResultCode(), mMessageCenterBeans.getMsg());
                                }
                            }
                        });
            }
        });
    }

    private void initPopupWindows() {
        alert = new AlertDialog.Builder(getContext(), R.style.AlertDialogs).create();
        alert.show();
        //加载自定义dialog
        alert.getWindow().setContentView(R.layout.update_dialogsss);

        ImageView mRemoteUpgradeBank = (ImageView) alert.getWindow().findViewById(R.id.RemoteUpgrade_Bank);
        final TextView mRemoteUpgradeV = (TextView) alert.getWindow().findViewById(R.id.RemoteUpgrade_V);
        final TextView mRemoteUpgradeTextView1 = (TextView) alert.getWindow().findViewById(R.id.RemoteUpgrade_TextView1);
        final TextView mRemoteUpgradeTextView2 = (TextView) alert.getWindow().findViewById(R.id.RemoteUpgrade_TextView2);
        final TextView mRemoteUpgradeTextView3 = (TextView) alert.getWindow().findViewById(R.id.RemoteUpgrade_TextView3);
        final TextView mRemoteUpgradeTextView4 = (TextView) alert.getWindow().findViewById(R.id.RemoteUpgrade_TextView4);


        mRemoteUpgradeTextView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upgradeStatus == 1) {
                    mRemoteUpgradeTextView4.setText("升级中");
                    mRemoteUpgradeTextView4.setClickable(false);

                    initUpdate();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            task.run();
                        }
                    }, 3000);
                } else if (upgradeStatus == 0) {
                    Toast.makeText(getContext(), promptMsg, Toast.LENGTH_SHORT).show();
                }
                return;
            }
        });
        mRemoteUpgradeBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                return;
            }
        });

        if (flag == -2) {
            mRemoteUpgradeTextView4.setText("升级中");
            mRemoteUpgradeTextView4.setClickable(false);
        } else if (flag == 1) {
            mRemoteUpgradeTextView4.setText("立即升级");
            mRemoteUpgradeTextView4.setClickable(true);
        }

        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "127");
        hashMap.put("token", SPUtils.get(getContext(), "token", ""));
        hashMap.put("deviceCode", extend);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<RemoteUpgradeOneBeans>(getContext()) {
                    @Override
                    public void onSuccess(RemoteUpgradeOneBeans mRemoteUpgradeOneBeans, Call call, Response response) {
                        if (mRemoteUpgradeOneBeans.getResultCode() == 1) {

                            mRemoteUpgradeTextView1.setText("设备编号：" + mRemoteUpgradeOneBeans.getData().getDeviceCode());

                            promptMsg = mRemoteUpgradeOneBeans.getData().getPromptMsg();
                            mRemoteUpgradeTextView2.setText(promptMsg);

                            mRemoteUpgradeTextView3.setText(mRemoteUpgradeOneBeans.getData().getUpgradeContent());
                            upgradeStatus = mRemoteUpgradeOneBeans.getData().getUpgradeStatus();
                        } else {
                            App.ErrorToken(mRemoteUpgradeOneBeans.getResultCode(), mRemoteUpgradeOneBeans.getMsg());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void initUpdate() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "126");
        hashMap.put("token", SPUtils.get(getContext(), "token", ""));
        hashMap.put("deviceCode", extend);
        hashMap.put("isUpgrade", "1");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<RemoteUpgradeTwoBeans>(getContext()) {
                    @Override
                    public void onSuccess(RemoteUpgradeTwoBeans mRemoteUpgradeTwoBeans, Call call, Response response) {
                        if (mRemoteUpgradeTwoBeans.getResultCode() == 1) {
//                            Toast.makeText(RemoteUpgradeActivity.this, "恭喜你,升级成功!", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(RemoteUpgradeActivity.this, "升级失败,请稍后再试!", Toast.LENGTH_SHORT).show();
                            App.ErrorToken(mRemoteUpgradeTwoBeans.getResultCode(), mRemoteUpgradeTwoBeans.getMsg());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 单选
     *
     * @param position  组元素位置
     * @param isChecked 组元素选中与否
     */
    @Override
    public void checkGroup(int position, boolean isChecked) {
        mList.get(position).setChoosed(isChecked);
        mMessageCenterAdapter.notifyDataSetChanged();
    }

    //EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        boolean show = event.isShow();
//        if (!"".equals(str)) {
//            mMessageCenterAdapter = new MessageCenterAdapter(mList, getContext(), false);
//        } else {
        mMessageCenterAdapter = new MessageCenterAdapter(mList, getContext(), show);
//        }
        mMessageCenterAdapter.setMyCollectInterface(this);
        mMessageRemindListView.setAdapter(mMessageCenterAdapter);
        mMessageCenterAdapter.setMessageCenterAdapterList(mList);
        boolean isDelete = event.isDelete();
        if (isDelete) {
            isDelete();
        }
    }
}
