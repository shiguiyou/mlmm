package com.wanquan.mlmmx.mlmm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.IntegralAdapter;
import com.wanquan.mlmmx.mlmm.beans.IntegralGainIntegralBeans;
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
 * Created by Administrator on 2018/4/10.
 */

public class GainIntegralFragment extends BaseFragment {
    private IntegralAdapter mIntegralAdapter;
    private List<IntegralGainIntegralBeans.DataBean> mList = new ArrayList<>();
    private ListView mGainIntegralFragmentListView;
    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.gain_integra_fragment, container, false);

        initViews();
        initData();
        initListeners();

        mIntegralAdapter = new IntegralAdapter(getContext(), mList, 1);
        mGainIntegralFragmentListView.setAdapter(mIntegralAdapter);
        return view;
    }

    private void initViews() {
        mGainIntegralFragmentListView = (ListView) view.findViewById(R.id.GainIntegralFragment_ListView);
    }

    private void initData() {
//        Log.e("fffff", String.valueOf(SPUtils.get(getContext(), "token", "")));
//        Log.e("fffff", String.valueOf(SPUtils.get(getContext(), "userid", "")));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "088");
        hashMap.put("token", SPUtils.get(getContext(), "token", ""));
        hashMap.put("direction", "IN");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<IntegralGainIntegralBeans>(getContext()) {
                    @Override
                    public void onSuccess(IntegralGainIntegralBeans mIntegralGainIntegralBeans, Call call, Response response) {
                        if (mIntegralGainIntegralBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mIntegralGainIntegralBeans.getData());
                            mIntegralAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mIntegralGainIntegralBeans.getResultCode(), mIntegralGainIntegralBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
    }
}
