package com.wanquan.mlmmx.mlmm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.wanquan.mlmmx.mlmm.activity.EquipmentManagementActivity;
import com.wanquan.mlmmx.mlmm.adapter.SponsorShareFragmentAdapter;
import com.wanquan.mlmmx.mlmm.beans.HomeEquipmentBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：共享设备--发起共享
 * 作者：薛昌峰
 * 时间：2017/9/26
 */

public class SponsorShareFragment extends BaseFragment {
    SponsorShareFragmentAdapter mSponsorShareFragmentAdapter;
    List<HomeEquipmentBeans.DataBean> mList = new ArrayList<>();
    private ListView mSponsorShareListView;
    private LinearLayout mSponsorShareLl;
    private View view;

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sponsorshare_fragment, container, false);

        initViews();
        initData();
        initListeners();

        mSponsorShareFragmentAdapter = new SponsorShareFragmentAdapter(mList, getContext());
        mSponsorShareListView.setAdapter(mSponsorShareFragmentAdapter);

        mSponsorShareListView.setEmptyView(mSponsorShareLl);

        return view;
    }

    private void initViews() {
        mSponsorShareLl = (LinearLayout) view.findViewById(R.id.SponsorShare_Ll);
        mSponsorShareListView = (ListView) view.findViewById(R.id.SponsorShare_ListView);
    }

    private void initData() {
        //我的设备
        if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
            HashMap<String, String> hashMap2 = new HashMap<>();
            hashMap2.put("itfaceId", "002");
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
                                mSponsorShareFragmentAdapter.notifyDataSetChanged();
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
            mSponsorShareFragmentAdapter = new SponsorShareFragmentAdapter(mList, getContext());
            mSponsorShareListView.setAdapter(mSponsorShareFragmentAdapter);
        }
    }

    private void initListeners() {
        mSponsorShareListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), EquipmentManagementActivity.class);
                intent.putExtra("name", mList.get(i).getDeviceName());
                intent.putExtra("code", mList.get(i).getDeviceCode());
                intent.putExtra("id", mList.get(i).getDeviceId());
                startActivity(intent);
            }
        });
    }
}
