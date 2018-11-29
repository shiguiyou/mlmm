package com.wanquan.mlmmx.mlmm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.BabyRoomActivity;
import com.wanquan.mlmmx.mlmm.activity.LoginActivity;
import com.wanquan.mlmmx.mlmm.activity.MainActivity;
import com.wanquan.mlmmx.mlmm.activity.SelectCircleActivity;
import com.wanquan.mlmmx.mlmm.activity.StateSettingActivity;
import com.wanquan.mlmmx.mlmm.adapter.HomeFragmentAdapter;
import com.wanquan.mlmmx.mlmm.beans.HomeEquipmentBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.SettingBabyMessageBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MD5Util;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：共享设备--收到共享
 * 作者：薛昌峰
 * 时间：2017/9/26
 */
public class ReceiveShareFragment extends BaseFragment {
    HomeFragmentAdapter mHomeFragmentAdapter;
    List<HomeEquipmentBeans.DataBean> mList = new ArrayList<>();
    private ListView mReceiveShareListView;
    private LinearLayout mReceiveShareLl;
    private View view;


    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.receiveshare_fragment, container, false);

        initViews();
        initData();
        initListeners();

        mHomeFragmentAdapter = new HomeFragmentAdapter(mList, getContext());
        mReceiveShareListView.setAdapter(mHomeFragmentAdapter);

        mReceiveShareListView.setEmptyView(mReceiveShareLl);

        return view;
    }

    private void initViews() {
        mReceiveShareListView = (ListView) view.findViewById(R.id.ReceiveShare_ListView);
        mReceiveShareLl = (LinearLayout) view.findViewById(R.id.ReceiveShare_Ll);
    }

    private void initData() {
        if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
            HashMap<String, String> hashMap2 = new HashMap<>();
            hashMap2.put("itfaceId", "019");
            hashMap2.put("token", String.valueOf(SPUtils.get(getContext(), "token", "")));
            JSONObject jsonObject2 = new JSONObject(hashMap2);

            OkGo.post(UrlContent.URL).tag(getActivity())
                    .upJson(jsonObject2.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<HomeEquipmentBeans>(getContext()) {
                        @Override
                        public void onSuccess(HomeEquipmentBeans mHomeEquipmentBeans, Call call, Response response) {
                            if (mHomeEquipmentBeans.getResultCode() == 1) {
                                mList.clear();
                                mList.addAll(mHomeEquipmentBeans.getData());
                                mHomeFragmentAdapter.notifyDataSetChanged();
                            } else {
                                App.ErrorToken(mHomeEquipmentBeans.getResultCode(), mHomeEquipmentBeans.getMsg());
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            Toast.makeText(getContext(), "服务器连接异常，请稍后重试", Toast.LENGTH_SHORT).show();
                            super.onError(call, response, e);
                        }
                    });
        }else {
            mList.clear();
            mHomeFragmentAdapter = new HomeFragmentAdapter(mList, getContext());
            mReceiveShareListView.setAdapter(mHomeFragmentAdapter);
        }
    }



    private void initListeners() {
        mReceiveShareListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList.get(position).getIsOnLine() == 1) {
                    Intent intent = new Intent(getContext(), BabyRoomActivity.class);
                    intent.putExtra("name", mList.get(position).getDeviceName());
                    intent.putExtra("id", mList.get(position).getDeviceId());
                    intent.putExtra("code", mList.get(position).getDeviceCode());
                    intent.putExtra("ownerUserId", mList.get(position).getOwnerUserId());
                    intent.putExtra("babyId", (mList.get(position).getBabyId()));
                    startActivity(intent);
                } else if (mList.get(position).getIsOnLine() == 0) {
                    Toast toast = Toast.makeText(getContext(), "当前设备不在线，请连接设备!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }
}
