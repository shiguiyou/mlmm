package com.wanquan.mlmmx.mlmm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.MyPrizeIntegralAdapter;
import com.wanquan.mlmmx.mlmm.beans.HomeEquipmentBeans;
import com.wanquan.mlmmx.mlmm.beans.MyPrizeIntegralFragmentBeans;
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
 * 描述：我的奖品_积分
 * 作者：薛昌峰
 * Created by Administrator on 2018/6/8.
 */

public class MyPrizeIntegralFragment extends Fragment {
    private MyPrizeIntegralAdapter mMyPrizeIntegralAdapter;
    private ListView mMyPrizeIntegralListView;
    private LinearLayout mMyPrizeIntegralLl;
    private List<MyPrizeIntegralFragmentBeans.DataBean> mList = new ArrayList<>();
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.myprizeintegralfragment, null);

        initViews();
        initData();
        initListeners();

        mMyPrizeIntegralListView.setEmptyView(mMyPrizeIntegralLl);
        mMyPrizeIntegralAdapter = new MyPrizeIntegralAdapter(mList, getActivity());
        mMyPrizeIntegralListView.setAdapter(mMyPrizeIntegralAdapter);

        return view;
    }

    private void initViews() {

        mMyPrizeIntegralListView = (ListView) view.findViewById(R.id.MyPrizeIntegral_ListView);
        mMyPrizeIntegralLl = (LinearLayout) view.findViewById(R.id.MyPrizeIntegral_ll);

    }

    private void initData() {
        if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
            HashMap<String, Object> hashMap2 = new HashMap<>();
            hashMap2.put("itfaceId", "103");
            hashMap2.put("token", SPUtils.get(getContext(), "token", ""));
            hashMap2.put("lotteryType", "0");
            JSONObject jsonObject2 = new JSONObject(hashMap2);

            OkGo.post(UrlContent.URL).tag(getActivity())
                    .upJson(jsonObject2.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<MyPrizeIntegralFragmentBeans>(getContext()) {
                        @Override
                        public void onSuccess(MyPrizeIntegralFragmentBeans mMyPrizeIntegralFragmentBeans, Call call, Response response) {
                            if (mMyPrizeIntegralFragmentBeans.getResultCode() == 1) {
                                mList.clear();
                                mList.addAll(mMyPrizeIntegralFragmentBeans.getData());
                                mMyPrizeIntegralAdapter.notifyDataSetChanged();
                            } else {
                                App.ErrorToken(mMyPrizeIntegralFragmentBeans.getResultCode(), mMyPrizeIntegralFragmentBeans.getMsg());
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            Toast.makeText(getContext(), "服务器连接异常，请稍后重试", Toast.LENGTH_SHORT).show();
                            super.onError(call, response, e);
                        }
                    });
        }
    }

    private void initListeners() {
    }
}