package com.wanquan.mlmmx.mlmm.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.BabyRoomActivity;
import com.wanquan.mlmmx.mlmm.adapter.BabyRoomAdapter;
import com.wanquan.mlmmx.mlmm.adapter.BabyRoomAdapters;
import com.wanquan.mlmmx.mlmm.beans.BabyRoomDialogGridViewBeans;
import com.wanquan.mlmmx.mlmm.beans.BabyRoomGridViewBeans;
import com.wanquan.mlmmx.mlmm.beans.BabyRoomkzBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：普通模式
 * 作者：薛昌峰
 * 时间：2018.05.23
 */

public class BabyRoomFragmentPT extends BaseFragment {
    private GridView mBabyRoomGridView;
    private BabyRoomAdapter mBabyRoomAdapter;
    private List<BabyRoomGridViewBeans.DataBean> mList = new ArrayList<>();
    private List<BabyRoomDialogGridViewBeans.DataBean.CfsBean> mList2 = new ArrayList<>();

    private String id;
    private String code;

    private AlertDialog alert2;

    //    private boolean run = true;
//    private final Handler handler = new Handler();
//    private final Runnable task = new Runnable() {
//        @Override
//        public void run() {
//            // TODO Auto-generated method stub
//            if (run) {
//                handler.postDelayed(this, 5000);
//                initData();
//            }
//        }
//    };
    private View view;
    private boolean status;
    private String token;
    private AirConditionerReceiver mAirConditionerReceiver;

    private class AirConditionerReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.xcf.moshi")) {
                String logoutFlag = intent.getStringExtra("moshi");
                if (logoutFlag.equals("1")) {
                    initData();
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_babyroompt, null);

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
            code = bundle.getString("code");
        }
//        Log.e("dddd_id", id + "xcf");
//        Log.e("dddd_code", code + "xcf");

//        task.run();
        initViews();
        initData();
        initListeners();

        mAirConditionerReceiver = new AirConditionerReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.xcf.moshi");
        getActivity().registerReceiver(mAirConditionerReceiver, filter);

        mBabyRoomAdapter = new BabyRoomAdapter(getContext(), mList);
        mBabyRoomGridView.setAdapter(mBabyRoomAdapter);

        return view;
    }

    //17f2bfe078c243378e88e258d96987a7
    private void initViews() {
        mBabyRoomGridView = (GridView) view.findViewById(R.id.BabyRoom_GridView);
    }

    private void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "020");
        hashMap.put("token", BabyRoomActivity.token);
        hashMap.put("deviceId", id);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<BabyRoomGridViewBeans>(getContext()) {
                    @Override
                    public void onSuccess(BabyRoomGridViewBeans mBabyRoomGridViewBeans, Call call, Response response) {
                        if (mBabyRoomGridViewBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mBabyRoomGridViewBeans.getData());
                            mBabyRoomAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mBabyRoomGridViewBeans.getResultCode(), mBabyRoomGridViewBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
        mBabyRoomGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                status = mList.get(0).getCfs().get(0).isStatus();
                if (!mList.get(i).isDisable()) {
                    final String OutFunctionCode = mList.get(i).getFunctionCode();
                    if (mList.get(i).getFunctionCode().equals("81")) {//开关
                        if (mList.get(i).getCfs().get(i).isStatus()) {
                            initNetwork1(OutFunctionCode, mList.get(i).getCfs().get(1).getFunctionCode());
                        } else {
                            initNetwork1(OutFunctionCode, mList.get(i).getCfs().get(0).getFunctionCode());
                        }
                    } else if (mList.get(i).getFunctionCode().equals("92")) {//解锁
                        if (status) {
                            if (mList.get(i).getCfs().get(0).isStatus()) {
                                initNetwork1(OutFunctionCode, mList.get(i).getCfs().get(1).getFunctionCode());
                            } else {
                                initNetwork1(OutFunctionCode, mList.get(i).getCfs().get(0).getFunctionCode());
                            }
                        } else {
                            Toast.makeText(getContext(), "设备未开启，该按钮不能操作哦", Toast.LENGTH_SHORT).show();
                        }

                    } else if (mList.get(i).getFunctionCode().equals("82")) {//负离子
                        if (status) {
                            if (mList.get(i).getCfs().get(0).isStatus()) {
                                initNetwork1(OutFunctionCode, mList.get(i).getCfs().get(1).getFunctionCode());
                            } else {
                                initNetwork1(OutFunctionCode, mList.get(i).getCfs().get(0).getFunctionCode());
                            }
                        } else {
                            Toast.makeText(getContext(), "设备未开启，该按钮不能操作哦", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (status) {
                            final BabyRoomAdapters mBabyRoomAdapters;
                            alert2 = new AlertDialog.Builder(getContext()).create();
                            alert2.show();
                            //加载自定义dialog
                            alert2.getWindow().setContentView(R.layout.baby_dialog);
                            alert2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            GridView mGridView = (GridView) alert2.getWindow().findViewById(R.id.BabyRoom_GridView2);

                            mBabyRoomAdapters = new BabyRoomAdapters(getContext(), mList2);
                            mGridView.setAdapter(mBabyRoomAdapters);

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("itfaceId", "020");
                            hashMap.put("token", SPUtils.get(getContext(), "token", ""));
                            hashMap.put("deviceId", id);
                            JSONObject jsonObject = new JSONObject(hashMap);

                            OkGo.post(UrlContent.URL).tag(this)
                                    .upJson(jsonObject.toString())
                                    .connTimeOut(10_000)
                                    .execute(new CustomCallBackNoLoading<BabyRoomDialogGridViewBeans>(getContext()) {
                                        @Override
                                        public void onSuccess(BabyRoomDialogGridViewBeans mBabyRoomDialogGridViewBeans, Call call, Response response) {
                                            if (mBabyRoomDialogGridViewBeans.getResultCode() == 1) {
                                                mList2.clear();
                                                mList2.addAll(mBabyRoomDialogGridViewBeans.getData().get(i).getCfs());
                                                mBabyRoomAdapters.notifyDataSetChanged();
                                            } else {
                                                App.ErrorToken(mBabyRoomDialogGridViewBeans.getResultCode(), mBabyRoomDialogGridViewBeans.getMsg());
                                            }
                                        }
                                    });

                            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (!mList2.get(i).isDisable()) {
                                        initNetwork1(OutFunctionCode, mList2.get(i).getFunctionCode());
                                    } else {
                                        Toast.makeText(getContext(), "当前模式下按钮不可点击", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "设备未开启，该按钮不能操作哦", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "当前模式下按钮不可点击", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initNetwork1(String functionCode, String functionCode2) {
//        Log.e("rrrrrrrrr", functionCode + "---------" + functionCode2);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "005");
        hashMap.put("deviceCode", code);
        hashMap.put("ctlType", functionCode);
        hashMap.put("token", SPUtils.get(getContext(), "token", ""));
        hashMap.put("ctlStatus", functionCode2);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<BabyRoomkzBeans>(getContext()) {
                    @Override
                    public void onSuccess(BabyRoomkzBeans mBabyRoomkzBeans, Call call, Response response) {
                        if (mBabyRoomkzBeans.getResultCode() == 1) {
                            Toast.makeText(getContext(), "操作成功", Toast.LENGTH_SHORT).show();
                            initData();
                            if (alert2 != null) {
                                alert2.dismiss();
                            }
                        } else {
                            App.ErrorToken(mBabyRoomkzBeans.getResultCode(), mBabyRoomkzBeans.getMsg());
                        }
                    }
                });
    }
}
