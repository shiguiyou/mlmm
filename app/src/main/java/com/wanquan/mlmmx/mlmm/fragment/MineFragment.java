package com.wanquan.mlmmx.mlmm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.AboutActivity;
import com.wanquan.mlmmx.mlmm.activity.ApplyExperienceActivity;
import com.wanquan.mlmmx.mlmm.activity.BabyPhonesActivity;
import com.wanquan.mlmmx.mlmm.activity.FeedBackActivity;
import com.wanquan.mlmmx.mlmm.activity.GestateMessageActivity;
import com.wanquan.mlmmx.mlmm.activity.GuideActivity;
import com.wanquan.mlmmx.mlmm.activity.InformationActivity;
import com.wanquan.mlmmx.mlmm.activity.LoginActivity;
import com.wanquan.mlmmx.mlmm.activity.MyCircleActivity;
import com.wanquan.mlmmx.mlmm.activity.MyCollectionActivity;
import com.wanquan.mlmmx.mlmm.activity.MyDiscountCouponActivity;
import com.wanquan.mlmmx.mlmm.activity.MyFenSiActivity;
import com.wanquan.mlmmx.mlmm.activity.MyIntegralActivity;
import com.wanquan.mlmmx.mlmm.activity.SettingActivity;
import com.wanquan.mlmmx.mlmm.activity.SettingBabyMessageActivity;
import com.wanquan.mlmmx.mlmm.activity.ShareActivity;
import com.wanquan.mlmmx.mlmm.activity.StateSettingActivity;
import com.wanquan.mlmmx.mlmm.beans.BabyActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.LoginBeans;
import com.wanquan.mlmmx.mlmm.beans.PersonalinformationActivityBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MyScrollView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/15.
 */

public class MineFragment extends BaseFragment {
    private PullToRefreshScrollView mMineMyScrollView;
    private CircleImageView mMineImageView;
    private TextView mMineName;
    private TextView mMinePhone;
    private LinearLayout mMineCenterLL1;
    private TextView mMineCenterSize1;
    private LinearLayout mMineCenterLL2;
    private TextView mMineCenterSize2;
    private LinearLayout mMineCenterLL3;
    private TextView mMineCenterSize3;
    private LinearLayout mMineCenterLL4;
    private TextView mMineCenterSize4;
    private RelativeLayout mMineOne;
    private ImageView mMineOneImg;
    private TextView mMineOneTv;
    private TextView mMineOneTv2;
    private RelativeLayout mMineTiYan;
    private ImageView mMineTiYanImg;
    private TextView mMineTiYanTv;
    private RelativeLayout mMineTwo;
    private RelativeLayout mMineThree;
    private RelativeLayout mMineSeven;
    private RelativeLayout mMineSix;
    private RelativeLayout mMineFour;
    private RelativeLayout mMineFive;

    private List<BabyActivityBeans.DataBean.BabyMessageBean> mList1 = new ArrayList<>();
    private String byz = "false";
    private String hyz = "false";
    public static String integralBalance;
    private String userLevel;

    private View view;

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_fragment, container, false);

        initViews();
        initData();
        initSetRefresh();
        initListeners();

        return view;
    }

    private void initSetRefresh() {
//        mMineMyScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mMineMyScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                initData();
                mMineMyScrollView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//                page++;
//                initNetWorks(page);
            }
        });
    }

    private void initViews() {
        mMineMyScrollView = (PullToRefreshScrollView) view.findViewById(R.id.Mine_MyScrollView);
        mMineImageView = (CircleImageView) view.findViewById(R.id.Mine_ImageView);
        mMineName = (TextView) view.findViewById(R.id.Mine_Name);
        mMinePhone = (TextView) view.findViewById(R.id.Mine_Phone);
        mMineCenterLL1 = (LinearLayout) view.findViewById(R.id.Mine_Center_LL1);
        mMineCenterSize1 = (TextView) view.findViewById(R.id.Mine_Center_Size1);
        mMineCenterLL2 = (LinearLayout) view.findViewById(R.id.Mine_Center_LL2);
        mMineCenterSize2 = (TextView) view.findViewById(R.id.Mine_Center_Size2);
        mMineCenterLL3 = (LinearLayout) view.findViewById(R.id.Mine_Center_LL3);
        mMineCenterSize3 = (TextView) view.findViewById(R.id.Mine_Center_Size3);
        mMineCenterLL4 = (LinearLayout) view.findViewById(R.id.Mine_Center_LL4);
        mMineCenterSize4 = (TextView) view.findViewById(R.id.Mine_Center_Size4);
        mMineOne = (RelativeLayout) view.findViewById(R.id.Mine_One);
        mMineOneImg = (ImageView) view.findViewById(R.id.Mine_One_img);
        mMineOneTv = (TextView) view.findViewById(R.id.Mine_One_tv);
        mMineOneTv2 = (TextView) view.findViewById(R.id.Mine_One_tv2);
        mMineTiYan = (RelativeLayout) view.findViewById(R.id.Mine_TiYan);
        mMineTiYanImg = (ImageView) view.findViewById(R.id.Mine_TiYan_img);
        mMineTiYanTv = (TextView) view.findViewById(R.id.Mine_TiYan_tv);
        mMineTwo = (RelativeLayout) view.findViewById(R.id.Mine_Two);
        mMineThree = (RelativeLayout) view.findViewById(R.id.Mine_Three);
        mMineSeven = (RelativeLayout) view.findViewById(R.id.Mine_Seven);
        mMineSix = (RelativeLayout) view.findViewById(R.id.Mine_Six);
        mMineFour = (RelativeLayout) view.findViewById(R.id.Mine_Four);
        mMineFive = (RelativeLayout) view.findViewById(R.id.Mine_Five);
    }

    private void initData() {
//        Log.e("rrrrr3333", String.valueOf(SPUtils.get(getContext(), "BabyState", "")) + "xcf");
//        Log.e("rrrrr3333", String.valueOf(SPUtils.get(getContext(), "userid", "")) + "xcf");
        if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
            if (SPUtils.get(getContext(), "BabyState", "").equals("0")) {
                mMineOneTv.setText("孕育状态");
                mMineOneTv2.setVisibility(View.VISIBLE);
                mMineOneTv2.setText("备孕中");
            } else if (SPUtils.get(getContext(), "BabyState", "").equals("1")) {
                mMineOneTv.setText("孕育信息");
                mMineOneTv2.setVisibility(View.GONE);
                mMineOneTv2.setText("");
            } else if (SPUtils.get(getContext(), "BabyState", "").equals("2")) {
                mMineOneTv.setText("宝宝信息");
                mMineOneTv2.setVisibility(View.GONE);
                mMineOneTv2.setText("");
            }
            mMineName.setTextColor(getResources().getColor(R.color.black));
//            HashMap<String, Object> hashMap2 = new HashMap<>();
//            hashMap2.put("itfaceId", "075");
//            hashMap2.put("userId", SPUtils.get(getContext(), "userid", ""));
//            JSONObject jsonObject2 = new JSONObject(hashMap2);
//
//            OkGo.post(UrlContent.URL).tag(this)
//                    .upJson(jsonObject2.toString())
//                    .connTimeOut(10_000)
//                    .execute(new CustomCallBackNoLoading<BabyActivityBeans>(getContext()) {
//                        @Override
//                        public void onSuccess(BabyActivityBeans mBabyActivityBeans, Call call, Response response) {
//                            if (mBabyActivityBeans.getResultCode() == 1) {
//                                mList1.clear();
//                                mList1.addAll(mBabyActivityBeans.getData().getBabyMessage());
//                                List<Integer> integers = new ArrayList<Integer>();
//                                for (int i = 0; i < mList1.size(); i++) {
//                                    if (mList1.get(i).getOrderId() == 1) {
//                                        integers.add(mList1.get(i).getSTATUS());
//                                    }
//                                }
//                                for (int i = 0; i < integers.size(); i++) {
//                                    int status = Integer.parseInt(integers.get(i).toString());
//                                    Log.e("kkkk", integers.toString());
//                                    if (status == 0) {
//                                        byz = "true";
//                                    } else if (status == 1) {
//                                        hyz = "true";
//                                    }
//                                }
//                            } else {
//                                Toast.makeText(getContext(), mBabyActivityBeans.getMsg(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
        } else {
            if (SPUtils.get(getContext(), "w_BabyState", "").equals("0")) {
                mMineOneTv.setText("孕育状态");
                mMineOneTv2.setVisibility(View.VISIBLE);
                mMineOneTv2.setText("备孕中");
            } else if (SPUtils.get(getContext(), "w_BabyState", "").equals("1")) {
                mMineOneTv.setText("孕育信息");
                mMineOneTv2.setVisibility(View.GONE);
            } else if (SPUtils.get(getContext(), "w_BabyState", "").equals("2")) {
                mMineOneTv.setText("宝宝信息");
                mMineOneTv2.setVisibility(View.GONE);
            }
        }

        if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
            //获取个人信息
            Log.e("token", String.valueOf(SPUtils.get(getContext(), "token", "")));
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("itfaceId", "021");
            hashMap.put("token", SPUtils.get(getContext(), "token", ""));
            hashMap.put("user", "my");
            JSONObject jsonObject = new JSONObject(hashMap);

            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<PersonalinformationActivityBeans>(getContext()) {
                        @Override
                        public void onSuccess(PersonalinformationActivityBeans mPersonalinformationActivityBeans, Call call, Response response) {
                            if (mPersonalinformationActivityBeans.getResultCode() == 1) {
                                Glide.with(getContext()).load(mPersonalinformationActivityBeans.getData().getHeadIco()).into(mMineImageView);
                                if (!"".equals(mPersonalinformationActivityBeans.getData().getNickName())) {
                                    mMineName.setText(mPersonalinformationActivityBeans.getData().getNickName());
                                    mMineName.setBackgroundColor(getResources().getColor(R.color.white));
                                    mMineName.setVisibility(View.VISIBLE);
                                } else {
                                    mMineName.setVisibility(View.GONE);
                                }
                                mMinePhone.setText(mPersonalinformationActivityBeans.getData().getMobile());
                                mMineCenterSize1.setText(String.valueOf((mPersonalinformationActivityBeans.getData().getForumCount())));
                                mMineCenterSize2.setText(String.valueOf((mPersonalinformationActivityBeans.getData().getIntegralBalance())));
                                mMineCenterSize3.setText(String.valueOf((mPersonalinformationActivityBeans.getData().getUserCouponTotal())));
                                mMineCenterSize4.setText(String.valueOf((mPersonalinformationActivityBeans.getData().getFansCount())));
                                //用户积分余额积分总数
                                integralBalance = String.valueOf(mPersonalinformationActivityBeans.getData().getIntegralBalance());
                                //用户当前等级
                                userLevel = String.valueOf(mPersonalinformationActivityBeans.getData().getUserLevel());
                                Log.e("xcf获取个人信息", mPersonalinformationActivityBeans.getMsg());

                            } else {
                                Log.e("xcf获取个人信息", mPersonalinformationActivityBeans.getMsg());

                                App.ErrorToken(mPersonalinformationActivityBeans.getResultCode(), mPersonalinformationActivityBeans.getMsg());
                            }
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                                Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            mMineImageView.setImageDrawable(getResources().getDrawable(R.mipmap.imgdefault));
            mMineName.setText("请登录");
            mMineName.setBackground(getResources().getDrawable(R.drawable.mine_tx_img));
            mMinePhone.setText("");
            mMineCenterSize1.setText("0");
            mMineCenterSize2.setText("0");
            mMineCenterSize3.setText("0");
        }
    }

    private void initListeners() {
        //申请体验
        mMineTiYan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    startActivity(new Intent(getContext(), ApplyExperienceActivity.class));
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        //我的帖子
        mMineCenterLL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    startActivity(new Intent(getContext(), MyCircleActivity.class));
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        //积分
        mMineCenterLL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    Intent intent = new Intent(getContext(), MyIntegralActivity.class);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        //优惠券
        mMineCenterLL3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    startActivity(new Intent(getContext(), MyDiscountCouponActivity.class));
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        //粉丝
        mMineCenterLL4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    startActivity(new Intent(getContext(), MyFenSiActivity.class));
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        //头像
        mMineImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    startActivity(new Intent(getContext(), InformationActivity.class));
                } else {
//                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        mMineName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        //孕育状态
        mMineOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e("rrrrr登录状态4","宝宝状态：" +  String.valueOf(SPUtils.get(getContext(), "BabyState", "")));
//                Log.e("rrrrr未登录状态4",  "宝宝状态：" +String.valueOf(SPUtils.get(getContext(), "w_BabyState", "")));
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    if (SPUtils.get(getContext(), "BabyState", "").equals("0")) {
                        final AlertDialog alert;
                        alert = new AlertDialog.Builder(getContext()).create();
                        alert.show();
                        //加载自定义dialog
                        alert.getWindow().setContentView(R.layout.delete_dialogs);
                        alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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
                                Intent intent = new Intent(getContext(), StateSettingActivity.class);
//                                intent.putExtra("flag", "0");
                                intent.putExtra("flag", "1");
                                intent.putExtra("hyz", "false");
                                intent.putExtra("byz", "false");
                                intent.putExtra("isTianJia", "0");
                                startActivity(intent);
                                alert.dismiss();
                            }
                        });
                    } else if (SPUtils.get(getContext(), "BabyState", "").equals("1")) {
                        startActivity(new Intent(getContext(), GestateMessageActivity.class));
                    } else if (SPUtils.get(getContext(), "BabyState", "").equals("2")) {
                        startActivity(new Intent(getContext(), SettingBabyMessageActivity.class));
                    } else {
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                } else {
                    if (SPUtils.get(getContext(), "w_BabyState", "").equals("0")) {
                        final AlertDialog alert;
                        alert = new AlertDialog.Builder(getContext()).create();
                        alert.show();
                        //加载自定义dialog
                        alert.getWindow().setContentView(R.layout.delete_dialogs);
                        alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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
                                Intent intent = new Intent(getContext(), StateSettingActivity.class);
//                                intent.putExtra("flag", "0");
                                intent.putExtra("flag", "1");
                                intent.putExtra("isTianJia", "0");
                                startActivity(intent);
                                SpUtil.setBooleanValue(getContext(), MyContant.W_TIAOZHUAN, false);
//                                SpUtil.setBooleanValue(getContext(), MyContant.W_SETTING, false);
                                alert.dismiss();
                            }
                        });
                    } else if (SPUtils.get(getContext(), "w_BabyState", "").equals("1")) {
                        startActivity(new Intent(getContext(), GestateMessageActivity.class));
                    } else if (SPUtils.get(getContext(), "w_BabyState", "").equals("2")) {
                        startActivity(new Intent(getContext(), SettingBabyMessageActivity.class));
                    }
                }
            }
        });

        mMineTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ShareActivity.class));
            }
        });
        mMineThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), GuideActivity.class).putExtra("flag","1"));
            }
        });
        mMineFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    startActivity(new Intent(getContext(), SettingActivity.class));
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        mMineFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AboutActivity.class));
            }
        });
        mMineSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    startActivity(new Intent(getContext(), FeedBackActivity.class));
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        mMineSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    startActivity(new Intent(getContext(), MyCollectionActivity.class));
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });

    }
}
