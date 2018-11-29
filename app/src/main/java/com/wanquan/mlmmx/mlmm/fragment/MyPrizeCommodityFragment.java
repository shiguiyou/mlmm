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
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.PrizeDetailsActivity;
import com.wanquan.mlmmx.mlmm.adapter.MyPrizeDiscountAdapter;
import com.wanquan.mlmmx.mlmm.beans.MyPrizeDiscountFragmentBeans;
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
 * 描述：我的奖品_商品
 * 作者：薛昌峰
 * Created by Administrator on 2018/6/8.
 */

public class MyPrizeCommodityFragment extends Fragment {
    private MyPrizeDiscountAdapter mMyPrizeDiscountAdapter;
    private ListView mMyPrizeDiscountListView;
    private LinearLayout mMyPrizeDiscountLl;
    private TextView mMyPrizeDiscountTextView;
    private List<MyPrizeDiscountFragmentBeans.DataBean> mList = new ArrayList<>();
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.myprizedisxountfragment, null);

        initViews();
        initData();
        initListeners();

        mMyPrizeDiscountListView.setEmptyView(mMyPrizeDiscountLl);
        mMyPrizeDiscountAdapter = new MyPrizeDiscountAdapter(mList, getContext(), 2);
        mMyPrizeDiscountListView.setAdapter(mMyPrizeDiscountAdapter);

        return view;
    }

    private void initViews() {
        mMyPrizeDiscountListView = (ListView) view.findViewById(R.id.MyPrizeDiscount_ListView);
        mMyPrizeDiscountLl = (LinearLayout) view.findViewById(R.id.MyPrizeDiscount_ll);
        mMyPrizeDiscountTextView = (TextView) view.findViewById(R.id.MyPrizeDiscount_TextView);
    }

    private void initData() {
        mMyPrizeDiscountTextView.setText("亲，您还没有抽到过商品哦~");
        if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
            HashMap<String, Object> hashMap2 = new HashMap<>();
            hashMap2.put("itfaceId", "103");
            hashMap2.put("token", SPUtils.get(getContext(), "token", ""));
            hashMap2.put("lotteryType", "2");
            JSONObject jsonObject2 = new JSONObject(hashMap2);

            OkGo.post(UrlContent.URL).tag(getActivity())
                    .upJson(jsonObject2.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<MyPrizeDiscountFragmentBeans>(getContext()) {
                        @Override
                        public void onSuccess(MyPrizeDiscountFragmentBeans mMyPrizeDiscountFragmentBeans, Call call, Response response) {
                            if (mMyPrizeDiscountFragmentBeans.getResultCode() == 1) {
                                mList.clear();
                                mList.addAll(mMyPrizeDiscountFragmentBeans.getData());
                                mMyPrizeDiscountAdapter.notifyDataSetChanged();
                            } else {
                                App.ErrorToken(mMyPrizeDiscountFragmentBeans.getResultCode(), mMyPrizeDiscountFragmentBeans.getMsg());
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
        mMyPrizeDiscountListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), PrizeDetailsActivity.class);
                intent.putExtra("contact", String.valueOf(mList.get(position).getContact()));
                intent.putExtra("createTime", String.valueOf(mList.get(position).getCreateTime()));
                intent.putExtra("lotteryName", String.valueOf(mList.get(position).getLotteryName()));
                intent.putExtra("price", String.valueOf(mList.get(position).getPrice()));
                startActivity(intent);
            }
        });
    }
}