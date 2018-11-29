package com.wanquan.mlmmx.mlmm.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.MyDiscountCouponBeans;
import com.wanquan.mlmmx.mlmm.beans.MyDiscountCouponDistailBeans;
import com.wanquan.mlmmx.mlmm.beans.SignInDuiHuanBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：优惠券详情
 * 作者：薛昌峰
 * 时间：2018.04.11
 */
public class DiscountCouponDetailsActivity extends BaseActivity {
    private LinearLayout mAppTitleBank;
    private TextView mAppTitleName;
    private TextView mAppTitleSave;
    private TextView mDiscountCouponDetailsMoney;
    private TextView mDiscountCouponDetailsName;
    private TextView mDiscountCouponDetailsContent;
    private TextView mDiscountCouponDetailsNumber;
    private TextView mDiscountCouponDetailsNumberBM;
    private TextView mDiscountCouponDetailsTV1;
    private TextView mDiscountCouponDetailsTV2;
    private TextView mDiscountCouponDetailsTV3;
    private TextView mDiscountCouponDetailsTV34;
    private TextView mDiscountCouponDetailsTV4;
    private TextView mDiscountCouponDetailsTV5;
    private TextView mDiscountCouponDetailsTV6;
    private TextView mDiscountCouponDetailsTV;
    private String id;
    private String flag;
    private String flags;
    private String couponCode;
    private String couponName;
    private String content;
    private String amount;
    private String s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(DiscountCouponDetailsActivity.this, R.color.tops);

        id = getIntent().getStringExtra("id");
        flag = getIntent().getStringExtra("flag");
        flags = getIntent().getStringExtra("flags");
        couponCode = getIntent().getStringExtra("couponCode");
        Log.e("couponCode", couponCode + "");
//        Log.e("flag", flag);

        initData();
        initListeners();

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_discount_coupon_details;
    }

    @Override
    public void initView() throws Exception {
        mAppTitleBank = (LinearLayout) findViewById(R.id.App_Title_Bank);
        mAppTitleName = (TextView) findViewById(R.id.App_Title_Name);
        mAppTitleSave = (TextView) findViewById(R.id.App_Title_Save);
        mDiscountCouponDetailsMoney = (TextView) findViewById(R.id.DiscountCouponDetails_Money);
        mDiscountCouponDetailsName = (TextView) findViewById(R.id.DiscountCouponDetails_Name);
        mDiscountCouponDetailsContent = (TextView) findViewById(R.id.DiscountCouponDetails_Content);
        mDiscountCouponDetailsNumber = (TextView) findViewById(R.id.DiscountCouponDetails_Number);
        mDiscountCouponDetailsNumberBM = (TextView) findViewById(R.id.DiscountCouponDetails_Number_BM);
        mDiscountCouponDetailsTV1 = (TextView) findViewById(R.id.DiscountCouponDetails_TV1);
        mDiscountCouponDetailsTV2 = (TextView) findViewById(R.id.DiscountCouponDetails_TV2);
        mDiscountCouponDetailsTV3 = (TextView) findViewById(R.id.DiscountCouponDetails_TV3);
        mDiscountCouponDetailsTV34 = (TextView) findViewById(R.id.DiscountCouponDetails_TV34);
        mDiscountCouponDetailsTV4 = (TextView) findViewById(R.id.DiscountCouponDetails_TV4);
        mDiscountCouponDetailsTV5 = (TextView) findViewById(R.id.DiscountCouponDetails_TV5);
        mDiscountCouponDetailsTV6 = (TextView) findViewById(R.id.DiscountCouponDetails_TV6);
        mDiscountCouponDetailsTV = (TextView) findViewById(R.id.DiscountCouponDetails_TV);
    }

    private void initData() {
        mAppTitleName.setText("优惠券详情");
        if (flag.equals("1")) {
            mDiscountCouponDetailsTV.setVisibility(View.VISIBLE);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("itfaceId", "094");
            hashMap.put("token", SPUtils.get(DiscountCouponDetailsActivity.this, "token", ""));
            hashMap.put("couponId", id);
            JSONObject jsonObject = new JSONObject(hashMap);

            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<MyDiscountCouponDistailBeans>(this) {
                        @Override
                        public void onSuccess(MyDiscountCouponDistailBeans mMyDiscountCouponDistailBeans, Call call, Response response) {
                            if (mMyDiscountCouponDistailBeans.getResultCode() == 1) {
                                amount = String.valueOf(mMyDiscountCouponDistailBeans.getData().getAmount());
                                couponName = mMyDiscountCouponDistailBeans.getData().getCouponName();
                                content = mMyDiscountCouponDistailBeans.getData().getContent();

                                mDiscountCouponDetailsMoney.setText(amount);
                                mDiscountCouponDetailsName.setText(couponName);
//                                mDiscountCouponDetailsContent.setText(content);
                                mDiscountCouponDetailsNumber.setText("所需积分：");
                                mDiscountCouponDetailsNumberBM.setText(amount);
                                mDiscountCouponDetailsNumberBM.setTextColor(getResources().getColor(R.color.se));

                                s1 = mMyDiscountCouponDistailBeans.getData().getRule();
                                String[] temp = s1.split("\\|");
                                if (temp.length == 1) {
                                    mDiscountCouponDetailsTV1.setText("● " + temp[0]);

                                    mDiscountCouponDetailsTV1.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV2.setVisibility(View.GONE);
                                    mDiscountCouponDetailsTV3.setVisibility(View.GONE);
                                } else if (temp.length == 2) {
                                    mDiscountCouponDetailsTV1.setText("● " + temp[0]);
                                    mDiscountCouponDetailsTV2.setText("● " + temp[1]);

                                    mDiscountCouponDetailsTV1.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV2.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV3.setVisibility(View.GONE);
                                } else if (temp.length == 3) {
                                    mDiscountCouponDetailsTV1.setText("● " + temp[0]);
                                    mDiscountCouponDetailsTV2.setText("● " + temp[1]);
                                    mDiscountCouponDetailsTV3.setText("● " + temp[2]);

                                    mDiscountCouponDetailsTV1.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV2.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV3.setVisibility(View.VISIBLE);
                                } else if (temp.length == 3) {
                                    mDiscountCouponDetailsTV1.setText("● " + temp[0]);
                                    mDiscountCouponDetailsTV2.setText("● " + temp[1]);
                                    mDiscountCouponDetailsTV3.setText("● " + temp[2]);
                                    mDiscountCouponDetailsTV34.setText("● " + temp[3]);

                                    mDiscountCouponDetailsTV1.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV2.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV3.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV34.setVisibility(View.VISIBLE);
                                }

                                String s2 = mMyDiscountCouponDistailBeans.getData().getTip();
                                String[] temp2 = s2.split("\\|");
                                if (temp2.length == 1) {
                                    mDiscountCouponDetailsTV4.setText("● " + temp2[0]);

                                    mDiscountCouponDetailsTV4.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV5.setVisibility(View.GONE);
                                    mDiscountCouponDetailsTV6.setVisibility(View.GONE);
                                } else if (temp2.length == 2) {
                                    mDiscountCouponDetailsTV4.setText("● " + temp2[0]);
                                    mDiscountCouponDetailsTV5.setText("● " + temp2[1]);

                                    mDiscountCouponDetailsTV4.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV5.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV6.setVisibility(View.GONE);
                                } else if (temp2.length == 3) {
                                    mDiscountCouponDetailsTV4.setText("● " + temp2[0]);
                                    mDiscountCouponDetailsTV5.setText("● " + temp2[1]);
                                    mDiscountCouponDetailsTV6.setText("● " + temp2[2]);

                                    mDiscountCouponDetailsTV4.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV5.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV6.setVisibility(View.VISIBLE);
                                }
                            } else {
                                App.ErrorToken(mMyDiscountCouponDistailBeans.getResultCode(), mMyDiscountCouponDistailBeans.getMsg());
                            }
                        }
                    });
        } else if (flag.equals("2")) {
            mDiscountCouponDetailsTV.setVisibility(View.GONE);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("itfaceId", "096");
            hashMap.put("token", SPUtils.get(DiscountCouponDetailsActivity.this, "token", ""));
            hashMap.put("couponCode", couponCode);
            JSONObject jsonObject = new JSONObject(hashMap);

            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<MyDiscountCouponDistailBeans>(this) {
                        @Override
                        public void onSuccess(MyDiscountCouponDistailBeans mMyDiscountCouponDistailBeans, Call call, Response response) {
                            if (mMyDiscountCouponDistailBeans.getResultCode() == 1) {
                                mDiscountCouponDetailsMoney.setText(String.valueOf(mMyDiscountCouponDistailBeans.getData().getAmount()));
                                mDiscountCouponDetailsName.setText(mMyDiscountCouponDistailBeans.getData().getCouponName());
//                                mDiscountCouponDetailsContent.setText(mMyDiscountCouponDistailBeans.getData().getContent());
                                mDiscountCouponDetailsNumber.setText("优惠券编码：");
                                mDiscountCouponDetailsNumberBM.setText(mMyDiscountCouponDistailBeans.getData().getCouponCode());

                                String s1 = mMyDiscountCouponDistailBeans.getData().getRule();
                                String[] temp = s1.split("\\|");
                                if (temp.length == 1) {
                                    mDiscountCouponDetailsTV1.setText("● " + temp[0]);

                                    mDiscountCouponDetailsTV1.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV2.setVisibility(View.GONE);
                                    mDiscountCouponDetailsTV3.setVisibility(View.GONE);
                                } else if (temp.length == 2) {
                                    mDiscountCouponDetailsTV1.setText("● " + temp[0]);
                                    mDiscountCouponDetailsTV2.setText("● " + temp[1]);

                                    mDiscountCouponDetailsTV1.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV2.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV3.setVisibility(View.GONE);
                                } else if (temp.length == 3) {
                                    mDiscountCouponDetailsTV1.setText("● " + temp[0]);
                                    mDiscountCouponDetailsTV2.setText("● " + temp[1]);
                                    mDiscountCouponDetailsTV3.setText("● " + temp[2]);

                                    mDiscountCouponDetailsTV1.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV2.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV3.setVisibility(View.VISIBLE);
                                } else if (temp.length == 4) {
                                    mDiscountCouponDetailsTV1.setText("● " + temp[0]);
                                    mDiscountCouponDetailsTV2.setText("● " + temp[1]);
                                    mDiscountCouponDetailsTV3.setText("● " + temp[2]);
                                    mDiscountCouponDetailsTV34.setText("● " + temp[3]);

                                    mDiscountCouponDetailsTV1.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV2.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV3.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV34.setVisibility(View.VISIBLE);
                                }

                                String s2 = mMyDiscountCouponDistailBeans.getData().getTip();
                                String[] temp2 = s2.split("\\|");
                                if (temp2.length == 1) {
                                    mDiscountCouponDetailsTV4.setText("● " + temp2[0]);

                                    mDiscountCouponDetailsTV4.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV5.setVisibility(View.GONE);
                                    mDiscountCouponDetailsTV6.setVisibility(View.GONE);
                                } else if (temp2.length == 2) {
                                    mDiscountCouponDetailsTV4.setText("● " + temp2[0]);
                                    mDiscountCouponDetailsTV5.setText("● " + temp2[1]);

                                    mDiscountCouponDetailsTV4.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV5.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV6.setVisibility(View.GONE);
                                } else if (temp2.length == 3) {
                                    mDiscountCouponDetailsTV4.setText("● " + temp2[0]);
                                    mDiscountCouponDetailsTV5.setText("● " + temp2[1]);
                                    mDiscountCouponDetailsTV6.setText("● " + temp2[2]);

                                    mDiscountCouponDetailsTV4.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV5.setVisibility(View.VISIBLE);
                                    mDiscountCouponDetailsTV6.setVisibility(View.VISIBLE);
                                }
                            } else {
                                App.ErrorToken(mMyDiscountCouponDistailBeans.getResultCode(), mMyDiscountCouponDistailBeans.getMsg());
                            }
                        }
                    });
        }
    }

    private void initListeners() {
        mAppTitleBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flags != null) {
                    if (flags.equals("3")) {
                        startActivity(new Intent(DiscountCouponDetailsActivity.this, SignInActivity.class).putExtra("flags", flags));
                    } else {
                        finish();
                    }
                } else {
                    finish();
                }
            }
        });
        mDiscountCouponDetailsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> hashMap2 = new HashMap<>();
                hashMap2.put("itfaceId", "095");
                hashMap2.put("token", SPUtils.get(DiscountCouponDetailsActivity.this, "token", ""));
                hashMap2.put("couponId", id);
                JSONObject jsonObject2 = new JSONObject(hashMap2);

                OkGo.post(UrlContent.URL).tag(this)
                        .upJson(jsonObject2.toString())
                        .connTimeOut(10_000)
                        .execute(new CustomCallBackNoLoading<SignInDuiHuanBeans>(DiscountCouponDetailsActivity.this) {
                            @Override
                            public void onSuccess(SignInDuiHuanBeans mSignInDuiHuanBeans, Call call, Response response) {
                                if (mSignInDuiHuanBeans.getResultCode() == 1) {
                                    final AlertDialog alert;
                                    alert = new AlertDialog.Builder(DiscountCouponDetailsActivity.this, R.style.AlertDialog).create();
                                    alert.show();
                                    alert.getWindow().setContentView(R.layout.integral_success);
                                    TextView mIntegralSuccessTitle = (TextView) alert.getWindow().findViewById(R.id.Integral_Success_Title);
                                    TextView mIntegralSuccessContent = (TextView) alert.getWindow().findViewById(R.id.Integral_Success_Content);
                                    TextView mIntegralSuccessMoney1 = (TextView) alert.getWindow().findViewById(R.id.Integral_Success_Money1);
                                    TextView mIntegralSuccessMoney2 = (TextView) alert.getWindow().findViewById(R.id.Integral_Success_Money2);
                                    ImageView mIntegralSuccessClose = (ImageView) alert.getWindow().findViewById(R.id.Integral_Success_Close);

                                    mIntegralSuccessTitle.setText(couponName);
                                    mIntegralSuccessContent.setText(content);
                                    mIntegralSuccessMoney1.setText(String.valueOf(amount));
                                    String[] temp = s1.split("\\|");
                                    mIntegralSuccessMoney2.setText(temp[0]);
                                    //取消
                                    alert.getWindow().findViewById(R.id.Integral_Success_Close).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            alert.dismiss();
                                            return;
                                        }
                                    });
                                } else {
                                    final AlertDialog alert;
                                    alert = new AlertDialog.Builder(DiscountCouponDetailsActivity.this, R.style.AlertDialogs).create();
                                    alert.show();
                                    alert.getWindow().setContentView(R.layout.delete_dialogs);
                                    TextView cart_delete_title = alert.getWindow().findViewById(R.id.cart_delete_title);
                                    TextView cart_delete_content = alert.getWindow().findViewById(R.id.cart_delete_content);
                                    TextView cart_delete_cancle = alert.getWindow().findViewById(R.id.cart_delete_cancle);
                                    TextView cart_delete_confirm = alert.getWindow().findViewById(R.id.cart_delete_confirm);
                                    cart_delete_title.setText("提示消息");
                                    cart_delete_content.setText("您的积分不够兑换该优惠券赶快去赚积分吧");
                                    cart_delete_cancle.setText("否");
                                    cart_delete_confirm.setText("赚积分");
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
                                            //flags用于判断是否在返回该页面
                                            startActivity(new Intent(DiscountCouponDetailsActivity.this, IntegralStrategyActivity.class).putExtra("flag", flag).putExtra("flags", "3"));
                                            alert.dismiss();
                                            return;
                                        }
                                    });
                                }
                            }
                        });
            }
        });
    }
}
