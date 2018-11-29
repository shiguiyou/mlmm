package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.MyDiscountCouponAdapter;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentBeans;
import com.wanquan.mlmmx.mlmm.beans.MyDiscountCouponBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：我的优惠券
 * 作者：薛昌峰
 * 时间：2018.04.10
 */
public class MyDiscountCouponActivity extends BaseActivity {
    private LinearLayout mAppTitleBank;
    private LinearLayout mMyDiscountCouponLL;
    private TextView mAppTitleName;
    private TextView mAppTitleSave;
    private ListView mMyDiscountCouponListView;
    private MyDiscountCouponAdapter mMyDiscountCouponAdapter;
    private List<MyDiscountCouponBeans.DataBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(MyDiscountCouponActivity.this, R.color.tops);

        initData();
        initListeners();

        mMyDiscountCouponListView.setEmptyView(mMyDiscountCouponLL);
        mMyDiscountCouponAdapter = new MyDiscountCouponAdapter(this, mList);
        mMyDiscountCouponListView.setAdapter(mMyDiscountCouponAdapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_my_discount_coupon;
    }

    @Override
    public void initView() throws Exception {
        mAppTitleBank = (LinearLayout) findViewById(R.id.App_Title_Bank);
        mAppTitleName = (TextView) findViewById(R.id.App_Title_Name);
        mAppTitleSave = (TextView) findViewById(R.id.App_Title_Save);
        mMyDiscountCouponLL = (LinearLayout) findViewById(R.id.MyDiscountCoupon_LL);
        mMyDiscountCouponListView = (ListView) findViewById(R.id.MyDiscountCoupon_ListView);
    }

    private void initData() {
        mAppTitleName.setText("我的优惠券");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "093");
        hashMap.put("token", SPUtils.get(MyDiscountCouponActivity.this, "token", ""));
        hashMap.put("pageNum", "1");
        hashMap.put("numberPage", "1000");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<MyDiscountCouponBeans>(MyDiscountCouponActivity.this) {
                    @Override
                    public void onSuccess(MyDiscountCouponBeans mMyDiscountCouponBeans, Call call, Response response) {
                        if (mMyDiscountCouponBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mMyDiscountCouponBeans.getData());
                            mMyDiscountCouponAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mMyDiscountCouponBeans.getResultCode(), mMyDiscountCouponBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
        mAppTitleBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mMyDiscountCouponListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyDiscountCouponActivity.this, DiscountCouponDetailsActivity.class);
                intent.putExtra("id", String.valueOf(mList.get(position).getCouponId()));
                intent.putExtra("couponCode", String.valueOf(mList.get(position).getCouponCode()));
                intent.putExtra("flag", "2");
                startActivity(intent);
            }
        });
    }
}
