package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.IntegralStrategyAdapter;
import com.wanquan.mlmmx.mlmm.beans.IntegralStrategyBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：积分攻略
 * 作者：薛昌峰
 * 时间：2018.04.10
 */
public class IntegralStrategyActivity extends BaseActivity {
    private LinearLayout mAppTitleBank;
    private TextView mAppTitleName;
    private TextView mAppTitleSave;
    private ListView mIntegralStrategyListView;
    private List<IntegralStrategyBeans.DataBean> mList = new ArrayList<>();
    private List<IntegralStrategyBeans.DataBean> mList2 = new ArrayList<>();
    private IntegralStrategyAdapter mIntegralStrategyAdapter;
    private int size;
    public static String flag;
    public static String flags;

    @Override
    protected void onResume() {
        super.onResume();
//        Log.e("dfdfdfsd", "到了");
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(IntegralStrategyActivity.this, R.color.tops);

        flag = getIntent().getStringExtra("flag");
        flags = getIntent().getStringExtra("flags");
//        Log.e("222222flag_1", flag + "xcf");
//        Log.e("222222flag_s1", flags + "xcf");

//        if (!"4".equals(flags)) {
//            SPUtils.put(IntegralStrategyActivity.this, "ParentChildGameflas", "false");
//        }
//      initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_integral_strategy;
    }

    @Override
    public void initView() throws Exception {
        mAppTitleBank = (LinearLayout) findViewById(R.id.App_Title_Bank);
        mAppTitleName = (TextView) findViewById(R.id.App_Title_Name);
        mAppTitleSave = (TextView) findViewById(R.id.App_Title_Save);
        mIntegralStrategyListView = (ListView) findViewById(R.id.IntegralStrategy_ListView);
    }

    private void initData() {
        mAppTitleName.setText("积分攻略");
//      Log.e("fffffffff", String.valueOf(SPUtils.get(this, "token", "")));
//      Log.e("fffffffff", String.valueOf(SPUtils.get(this, "userid", "")));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "089");
        hashMap.put("token", SPUtils.get(this, "token", ""));
        hashMap.put("userId", SPUtils.get(this, "userid", ""));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<IntegralStrategyBeans>(this) {
                    @Override
                    public void onSuccess(IntegralStrategyBeans mIntegralStrategyBeans, Call call, Response response) {
                        if (mIntegralStrategyBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mIntegralStrategyBeans.getData());
//                            if (mList != null && mList.size() > 0) {
//                                IntegralStrategyBeans.DataBean objDataBean = new IntegralStrategyBeans.DataBean();
//                                for (int i = 0; i < mList.size(); i++) {
//                                    objDataBean = mList.get(i);
//                                    if (0 != objDataBean.getStrategyVal()) {
//                                        mList2.add(objDataBean);
////                                        Log.e("dsdsds", String.valueOf(i));
////                                        size = size + 1;
//                                    }
//                                }
//                            }
                            mIntegralStrategyAdapter = new IntegralStrategyAdapter(IntegralStrategyActivity.this, mList, size);
                            mIntegralStrategyListView.setAdapter(mIntegralStrategyAdapter);
                            mIntegralStrategyAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mIntegralStrategyBeans.getResultCode(), mIntegralStrategyBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
        mAppTitleBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("222222flag_", flag + "xcf");
//                Log.e("222222flag_s", flags + "xcf");
                if (flags != null) {
                    if (flags.equals("3")) {
                        startActivity(new Intent(IntegralStrategyActivity.this, DiscountCouponDetailsActivity.class).putExtra("flag", flag).putExtra("flags", flags));
                    } else if ("4".equals(flags)) {
                        startActivity(new Intent(IntegralStrategyActivity.this, ParentChildGameActivity.class).putExtra("flags", "4"));
                    } else if ("5".equals(flags)) {
                        startActivity(new Intent(IntegralStrategyActivity.this, SignInActivity.class).putExtra("flags", "5"));
                    } else if (flags.equals("2")) {
                        startActivity(new Intent(IntegralStrategyActivity.this, MyIntegralActivity.class));
                    }
                } else {
                    finish();
                }
            }
        });
    }
}
