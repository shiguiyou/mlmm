package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.MyIntegralAdapter;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.MyIntegralBeans;
import com.wanquan.mlmmx.mlmm.beans.PersonalinformationActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.SignInBottonBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：我的积分
 * 作者：薛昌峰
 * 时间：2018.04.10
 */
public class MyIntegralActivity extends BaseActivity implements MyIntegralAdapter.InterfaceMyIntegral {
    private LinearLayout mAppTitleBank;
    private LinearLayout mMyIntegralRule;
    private TextView mAppTitleName;
    private TextView mAppTitleSave;
    private ImageView mMyIntegralImg;
    private TextView mMyIntegralName;
    private TextView mMyIntegralSize1;
    private TextView mMyIntegralSize2;
    private LinearLayout mMyIntegralLL;
    private ListView mMyIntegralListView;
    private MyIntegralAdapter mMyIntegralAdapter;
    private List<MyIntegralBeans.DataBean> mList = new ArrayList<>();
    private String userLevel;
    private String integralBalance;

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(MyIntegralActivity.this, R.color.tops);

        initData();
        initListeners();

        mMyIntegralAdapter = new MyIntegralAdapter(this, mList);
        mMyIntegralAdapter.setInterfaceMyIntegral(this);
        mMyIntegralListView.setAdapter(mMyIntegralAdapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_my_integral;
    }

    @Override
    public void initView() throws Exception {
        mAppTitleBank = (LinearLayout) findViewById(R.id.App_Title_Bank);
        mAppTitleName = (TextView) findViewById(R.id.App_Title_Name);
        mAppTitleSave = (TextView) findViewById(R.id.App_Title_Save);
        mMyIntegralImg = (ImageView) findViewById(R.id.MyIntegral_Img);
        mMyIntegralName = (TextView) findViewById(R.id.MyIntegral_Name);
        mMyIntegralRule = (LinearLayout) findViewById(R.id.MyIntegral_Rule);
        mMyIntegralSize1 = (TextView) findViewById(R.id.MyIntegral_Size1);
        mMyIntegralSize2 = (TextView) findViewById(R.id.MyIntegral_Size2);
        mMyIntegralLL = (LinearLayout) findViewById(R.id.MyIntegral_LL);
        mMyIntegralListView = (ListView) findViewById(R.id.MyIntegral_ListView);
    }

    private void initData() {
        mAppTitleName.setText("我的积分");
        mAppTitleSave.setText("积分记录");
        mAppTitleSave.setTextColor(getResources().getColor(R.color.topss));

        Log.e("token", String.valueOf(SPUtils.get(MyIntegralActivity.this, "token", "")));

        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("itfaceId", "021");
        hashMap.put("token", SPUtils.get(MyIntegralActivity.this, "token", ""));
        hashMap.put("user", "my");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<PersonalinformationActivityBeans>(MyIntegralActivity.this) {
                    @Override
                    public void onSuccess(PersonalinformationActivityBeans mPersonalinformationActivityBeans, Call call, Response response) {
                        if (mPersonalinformationActivityBeans.getResultCode() == 1) {
                            //用户积分余额积分总数
                            integralBalance = String.valueOf(mPersonalinformationActivityBeans.getData().getIntegralBalance());
                            //用户当前等级
                            userLevel = String.valueOf(mPersonalinformationActivityBeans.getData().getUserLevel());
                            mMyIntegralSize1.setText(integralBalance);
                            mMyIntegralSize2.setText(String.valueOf(mPersonalinformationActivityBeans.getData().getIntegralAmount()));
                            if ("1".equals(userLevel)) {
                                mMyIntegralImg.setImageDrawable(getResources().getDrawable(R.mipmap.rank_ordinary));
                                mMyIntegralName.setText("普通会员");
                            } else if ("2".equals(userLevel)) {
                                mMyIntegralImg.setImageDrawable(getResources().getDrawable(R.mipmap.rank_silver));
                                mMyIntegralName.setText("白银会员");
                            } else if ("3".equals(userLevel)) {
                                mMyIntegralImg.setImageDrawable(getResources().getDrawable(R.mipmap.rank_golden));
                                mMyIntegralName.setText("黄金会员");
                            } else if ("4".equals(userLevel)) {
                                mMyIntegralImg.setImageDrawable(getResources().getDrawable(R.mipmap.rank_bojing));
                                mMyIntegralName.setText("铂金会员");
                            } else if ("5".equals(userLevel)) {
                                mMyIntegralImg.setImageDrawable(getResources().getDrawable(R.mipmap.rank_diamond));
                                mMyIntegralName.setText("钻石会员");
                            } else if ("6".equals(userLevel)) {
                                mMyIntegralImg.setImageDrawable(getResources().getDrawable(R.mipmap.rank_extreme));
                                mMyIntegralName.setText("至尊会员");
                            }
                        } else {
                            App.ErrorToken(mPersonalinformationActivityBeans.getResultCode(), mPersonalinformationActivityBeans.getMsg());
                        }
                    }
                });
        //优惠券
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("itfaceId", "092");
        hashMap2.put("token", SPUtils.get(MyIntegralActivity.this, "token", ""));
//        hashMap2.put("pageNum", "");
//        hashMap2.put("numberPage", "100");
        JSONObject jsonObject2 = new JSONObject(hashMap2);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject2.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<MyIntegralBeans>(this) {
                    @Override
                    public void onSuccess(MyIntegralBeans mMyIntegralBeans, Call call, Response response) {
                        if (mMyIntegralBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mMyIntegralBeans.getData());
                            mMyIntegralAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mMyIntegralBeans.getResultCode(), mMyIntegralBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
        mAppTitleBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyIntegralActivity.this, MainActivity.class);
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setShowBank(false);
                messageEvent.setFinish(4);
                EventBus.getDefault().post(messageEvent);
                startActivity(intent);
            }
        });
        mAppTitleSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyIntegralActivity.this, IntegralRecordActivity.class));
            }
        });
        mMyIntegralRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyIntegralActivity.this, IntegralRuleActivity.class));
            }
        });
        mMyIntegralLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyIntegralActivity.this, IntegralStrategyActivity.class).putExtra("flags","2"));
            }
        });
        mMyIntegralListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyIntegralActivity.this, DiscountCouponDetailsActivity.class);
                intent.putExtra("id", String.valueOf(mList.get(position).getId()));
                intent.putExtra("flag", "1");
                startActivity(intent);
            }
        });
    }

    @Override
    public void initFinish() {
        initData();
    }
}
