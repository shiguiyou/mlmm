package com.wanquan.mlmmx.mlmm.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;
import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.AirSmallActivity;
import com.wanquan.mlmmx.mlmm.activity.BabyRespiratoryReportCardActivity;
import com.wanquan.mlmmx.mlmm.activity.ChoiceDeviceActivity;
import com.wanquan.mlmmx.mlmm.activity.EquipmentActivity;
import com.wanquan.mlmmx.mlmm.activity.GrowUpAppraisalActivity;
import com.wanquan.mlmmx.mlmm.activity.GuideActivity;
import com.wanquan.mlmmx.mlmm.activity.IntegralStrategyActivity;
import com.wanquan.mlmmx.mlmm.activity.LoginActivity;
import com.wanquan.mlmmx.mlmm.activity.SearchActivity;
import com.wanquan.mlmmx.mlmm.adapter.BleNameAdapter;
import com.wanquan.mlmmx.mlmm.adapter.EquipmentFragmentAdapter;
import com.wanquan.mlmmx.mlmm.beans.BannerBeans;
import com.wanquan.mlmmx.mlmm.beans.EquimentBluetoothBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeEquipmentBeans;
import com.wanquan.mlmmx.mlmm.beans.MainAirBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.GlideImageLoader;
import com.wanquan.mlmmx.mlmm.view.MyListView;
import com.yyydjk.library.BannerLayout;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/15.
 */

public class EquipmentFragment extends BaseFragment implements LocationSource, AMapLocationListener, EquipmentFragmentAdapter.InterfaceMDName {
    //定位需要的声明
    private static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 110;
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器
    private String lng;
    private String lat;
    private PullToRefreshScrollView mHomeFragmentScrollView;
    private LinearLayout mFragmentAdd;
    private LinearLayout mFragmentBank;
    //    private Banner mHomeFragmentBanner;
    private TextView mEquipmentGxsb;
    private TextView mFragmentAddress;
    private LinearLayout mFragmentCity;
    private ImageView mFragmentImageView;
    private TextView mFragmentSize1;
    private ImageView mFragmentSize1Img;
    private TextView mFragmentSize2;
    private TextView mFragmentSize3;
    private MyListView mFragmentListView;
    private MyListView mFragmentListView2;
    private BleNameAdapter mBleNameAdapter;
    private LinearLayout mHomeFragmentLl;
    private ImageView mHomeFragmentIv;
    private TextView mHomeFragmentTv;
    private TextView mHomeFragmentAdd;
    private EquipmentFragmentAdapter mEquipmentFragmentAdapter;
    private List<HomeEquipmentBeans.DataBean> mList = new ArrayList<>();
    private List<EquimentBluetoothBeans.DataBean> mList2 = new ArrayList<>();
    private View view;
    private String city;
    private List<String> images = new ArrayList<>();
    //    private Banner banner;
    private int pm = 0;
    private RelativeLayout mFragmentRelativeLayout;
    private TextView mFragmentBleName;
    private int size1;
    private int size2;
    private BannerLayout bannerLayout;
    private List<BannerBeans.DataBean> mListBanner = new ArrayList<>();
    private int addressflag = 0;

    @Override
    public void onResume() {
        super.onResume();

        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_equipment, container, false);
        // 注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION, getString(R.string.mis_permission_rationale_write_storage), REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        } else {
            initLoc();//定位
        }

        initViews();
//        initData();
        initListeners();
        initRefresh();


//        mFragmentListView.setEmptyView(mHomeFragmentLl);
        mEquipmentFragmentAdapter = new EquipmentFragmentAdapter(mList, getContext());
        mEquipmentFragmentAdapter.mInterfaceMDName(this);
        mFragmentListView.setAdapter(mEquipmentFragmentAdapter);

        //空气小秘列表
        mBleNameAdapter = new BleNameAdapter(mList2, getContext());
        mFragmentListView2.setAdapter(mBleNameAdapter);

        return view;
    }

    private void initRefresh() {
        mHomeFragmentScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION, getString(R.string.mis_permission_rationale_write_storage), REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
                } else {
                    initLoc();//定位
                }
                initData();
                mHomeFragmentScrollView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }
        });
    }

    private void initViews() {
        mEquipmentGxsb = (TextView) view.findViewById(R.id.Equipment_Gxsb);
        mHomeFragmentScrollView = (PullToRefreshScrollView) view.findViewById(R.id.HomeFragment_ScrollView);
        mFragmentBank = (LinearLayout) view.findViewById(R.id.Equipment_Bank);
        mFragmentAdd = (LinearLayout) view.findViewById(R.id.Fragment_Add);
        bannerLayout = (BannerLayout) view.findViewById(R.id.HomeFragment_Banner);
        mFragmentAddress = (TextView) view.findViewById(R.id.Fragment_Address);
        mFragmentCity = (LinearLayout) view.findViewById(R.id.Fragment_City);
        mFragmentImageView = (ImageView) view.findViewById(R.id.Fragment_ImageView);
        mFragmentSize1 = (TextView) view.findViewById(R.id.Fragment_Size1);
        mFragmentSize1Img = (ImageView) view.findViewById(R.id.Fragment_Size1_img);
        mFragmentSize2 = (TextView) view.findViewById(R.id.Fragment_Size2);
        mFragmentSize3 = (TextView) view.findViewById(R.id.Fragment_Size3);
        mFragmentListView = (MyListView) view.findViewById(R.id.Fragment_ListView);
        mFragmentListView2 = (MyListView) view.findViewById(R.id.Fragment_ListView2);
        mHomeFragmentLl = (LinearLayout) view.findViewById(R.id.HomeFragment_Ll);
        mHomeFragmentIv = (ImageView) view.findViewById(R.id.HomeFragment_iv);
        mHomeFragmentTv = (TextView) view.findViewById(R.id.HomeFragment_tv);
        mHomeFragmentAdd = (TextView) view.findViewById(R.id.HomeFragment_Add);
//      mFragmentRelativeLayout = (RelativeLayout) view.findViewById(R.id.Fragment_RelativeLayout);
//      mFragmentBleName = (TextView) view.findViewById(R.id.Fragment__ble_name);
    }

    private void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("sdadad", String.valueOf(mList.size()) + "ccccc" + mList2.size());
                if (mList.size() == 0 && mList2.size() == 0) {
                    mHomeFragmentLl.setVisibility(View.VISIBLE);
                } else {
                    mHomeFragmentLl.setVisibility(View.GONE);
                }
            }
        }, 1000);

//        mBleName.clear();
//        ListDataSave dataSave = new ListDataSave(getContext(), "baiyu");
////        Log.e("ttttttt1", dataSave.getDataList("string").toString());
//        if (!dataSave.getDataList("string").toString().equals("[]")) {
////            Log.e("ttttttt2", dataSave.getDataList("string").toString());
//            //删除集合中重复的元素
//            mBleName.add(dataSave.getDataList("string").toString());
//            HashSet h = new HashSet(mBleName);
//            mBleName.clear();
//            mBleName.addAll(h);
////            Log.e("tttttttna", String.valueOf(mBleName.size()));
//            if (mBleName.size() != 0) {
//                mHomeFragmentTv.setVisibility(View.GONE);
//                mHomeFragmentIv.setVisibility(View.GONE);
//                mHomeFragmentAdd.setVisibility(View.GONE);
//            } else if (mBleName.size() == 0) {
//                mHomeFragmentTv.setVisibility(View.VISIBLE);
//                mHomeFragmentIv.setVisibility(View.VISIBLE);
//                mHomeFragmentAdd.setVisibility(View.VISIBLE);
//            }
//            mBleNameAdapter = new BleNameAdapter(mBleName, getContext());
//            mFragmentListView2.setAdapter(mBleNameAdapter);
//            mFragmentListView2.setEmptyView(mHomeFragmentLl);
//        }
        //显示蓝牙名称
        //空气小秘列表
        if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
            HashMap<String, String> hashMap2 = new HashMap<>();
            hashMap2.put("itfaceId", "113");
            hashMap2.put("token", String.valueOf(SPUtils.get(getContext(), "token", "")));
            hashMap2.put("pageNo", "1");
            hashMap2.put("pageSize", "10");
            JSONObject jsonObject2 = new JSONObject(hashMap2);

            OkGo.post(UrlContent.URL).tag(getContext())
                    .upJson(jsonObject2.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<EquimentBluetoothBeans>(getContext()) {
                        @Override
                        public void onSuccess(EquimentBluetoothBeans mEquimentBluetoothBeans, Call call, Response response) {
                            if (mEquimentBluetoothBeans.getResultCode() == 1) {
                                mList2.clear();
                                mList2.addAll(mEquimentBluetoothBeans.getData());
                                size2 = mList2.size();
                                mBleNameAdapter.notifyDataSetChanged();
                                Log.e("xcf空气小秘设备", mEquimentBluetoothBeans.getMsg());

                            } else {
                                Log.e("xcf空气小秘设备", mEquimentBluetoothBeans.getMsg());
                                App.ErrorToken(mEquimentBluetoothBeans.getResultCode(), mEquimentBluetoothBeans.getMsg());
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
        }
        if (SPUtils.get(getContext(), "BabyState", "").equals("0") || SPUtils.get(getContext(), "BabyState", "").equals("1")) {
            mHomeFragmentTv.setText("妈妈,请帮我健康呼吸吧");
        } else if (SPUtils.get(getContext(), "BabyState", "").equals("2")) {
            mHomeFragmentTv.setText("妈妈，请给我健康呼吸吧");
        }

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "015");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(getActivity())
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<BannerBeans>(getContext()) {
                    @Override
                    public void onSuccess(BannerBeans bannerBeans, Call call, Response response) {
                        if (bannerBeans.getResultCode() == 1) {
                            images.clear();
                            mListBanner.clear();
                            mListBanner.addAll(bannerBeans.getData());

                            for (int i = 0; i < mListBanner.size(); i++) {
                                images.add(mListBanner.get(i).getImgUrl());
                                Log.e("xcfxcxf", mListBanner.get(i).getImgUrl());
                            }
//                            setBanner(images);
                            bannerLayout.setImageLoader(new GlideImageLoader());
                            bannerLayout.setViewUrls(images);

                            Log.e("xcf轮播图", bannerBeans.getMsg());
                        } else {
                            Log.e("xcf轮播图", bannerBeans.getMsg());
                            Toast.makeText(getContext(), bannerBeans.getMsg(), Toast.LENGTH_SHORT).show();
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

        if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
            //我的设备
            Log.e("ffffff", String.valueOf(SPUtils.get(getContext(), "token", "")));
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
                                size1 = mList.size();
                                mEquipmentFragmentAdapter.notifyDataSetChanged();
//                                mHomeFragmentScrollView.onRefreshComplete();
                                Log.e("xcf我的设备", mHomeEquipmentBeans.getMsg());
                            } else {
                                Log.e("xcf我的设备", mHomeEquipmentBeans.getMsg());
                                App.ErrorToken(mHomeEquipmentBeans.getResultCode(), mHomeEquipmentBeans.getMsg());
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                                    Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        } else {
//            Toast toast = Toast.makeText(getContext(), "请登录后查看设备", Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
            mList.clear();
            mEquipmentFragmentAdapter = new EquipmentFragmentAdapter(mList, getContext());
            mFragmentListView.setAdapter(mEquipmentFragmentAdapter);
        }


//        initNetWork(city);

    }

    private void initListeners() {
//        //长按修改空气小秘名称和删除设备
//        mFragmentRelativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                final AlertDialog alert;
//                alert = new AlertDialog.Builder(getContext()).create();
//                alert.show();
//                //加载自定义dialog
//                alert.getWindow().setContentView(R.layout.homefragment_dialog);
//                alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                TextView title1 = (TextView) alert.getWindow().findViewById(R.id.HomeDialog_text1);
//                TextView title2 = (TextView) alert.getWindow().findViewById(R.id.HomeDialog_text2);
//                title1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        mFragmentRelativeLayout.setVisibility(View.GONE);
//                        SPUtils.put(getContext(), "bluename", "");
//                        if (mList.size() == 0) {
//                            mHomeFragmentTv.setVisibility(View.VISIBLE);
//                            mHomeFragmentIv.setVisibility(View.VISIBLE);
//                            mHomeFragmentAdd.setVisibility(View.VISIBLE);
//                            alert.dismiss();
//                        }
//                    }
//                });
//
//                title2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        final AlertDialog alert2;
//                        alert2 = new AlertDialog.Builder(getContext()).create();
//                        alert2.show();
//                        //加载自定义dialog
//                        alert2.getWindow().setContentView(R.layout.delete_dialog);
//                        alert2.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//                        alert2.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//                        alert2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                        TextView title = (TextView) alert2.getWindow().findViewById(R.id.cart_delete_title);
//                        title.setText("修改设备名称");
//                        //取消
//                        alert2.getWindow().findViewById(R.id.cart_delete_cancle).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                alert2.dismiss();
//                            }
//                        });
//                        final EditText editText = alert2.getWindow().findViewById(R.id.cart_delete_content);
//                        alert2.getWindow().findViewById(R.id.cart_delete_confirm).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                SPUtils.put(getContext(), "bluename", editText.getText().toString().trim());
//                                mFragmentBleName.setText(SPUtils.get(getContext(), "bluename", "") + "");
//
//                                alert.dismiss();
//                                alert2.dismiss();
//                            }
//                        });
//                    }
//                });
//                return true;
//            }
//        });
        //添加监听事件
        bannerLayout.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String htmlUrl = mListBanner.get(position).getHtmlUrl();
                startActivity(new Intent(getContext(), GuideActivity.class).putExtra("htmlUrl", htmlUrl).putExtra("flag", "2"));
            }
        });
        mFragmentBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), IntegralStrategyActivity.class);
                if (SPUtils.get(getContext(), "ParentChildGameflas", "").equals("true")) {
                    intent.putExtra("flags", "4");
                } else {
                    intent.putExtra("flags", "2");
                }
                startActivity(intent);
            }
        });

//        mFragmentListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getContext(), AirSmallActivity.class);
//                startActivity(intent);
//            }
//        });
        mEquipmentGxsb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    startActivity(new Intent(getContext(), EquipmentActivity.class));
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        mFragmentAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    startActivity(new Intent(getContext(), ChoiceDeviceActivity.class));
//                    startActivity(new Intent(getContext(), BabyRespiratoryReportCardActivity.class));
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        mHomeFragmentAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SpUtil.getBooleanValue(getContext(), MyContant.ISLOGIN, true)) {
                    startActivity(new Intent(getContext(), ChoiceDeviceActivity.class));
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        mFragmentCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

//    private void setBanner(List<String> imagesUrls) {
//        //设置banner样式
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
//        //设置图片加载器
//        banner.setImageLoader(new GlideImageLoader());
//        //设置图片集合
//        banner.setImages(imagesUrls);
//        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.DepthPage);
//        //设置标题集合（当banner样式有显示title时）
////        banner.setBannerTitles(titles);
//        //设置自动轮播，默认为true
//        banner.isAutoPlay(true);
//        //设置轮播时间
//        banner.setDelayTime(1500);
//        //设置指示器位置（当banner模式中有指示器时）
//        banner.setIndicatorGravity(BannerConfig.CENTER);
//        //banner设置方法全部调用完毕时最后调用
//        banner.start();
//    }

    private void initNetWork(String city) {
        if (city != null) {
            Log.e("address", city + "xcfxcf");
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("itfaceId", "potable_001");
            hashMap.put("city", city);
            JSONObject jsonObject = new JSONObject(hashMap);

            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<MainAirBeans>(getContext()) {
                        @Override
                        public void onSuccess(MainAirBeans mainAirBeans, Call call, Response response) {
                            if (mainAirBeans.getResultCode() == 1) {
                                String url = mainAirBeans.getData().getResult().getUrl();
                                if (!url.equals("")) {
                                    Glide.with(getContext()).load(url).into(mFragmentImageView);
                                }
                                pm = Integer.parseInt(mainAirBeans.getData().getResult().getPM25());
                                mFragmentSize1.setText(pm + "");
                                Log.e("wwwwww", "pm2.5" + mainAirBeans.getData().getResult().getPM25() + "xcf");
                                mFragmentSize2.setText(mainAirBeans.getData().getResult().getTemp() + "℃");
                                mFragmentSize3.setText(mainAirBeans.getData().getResult().getHumi() + "%");
                                if (35 >= pm && pm >= 0) {
                                    mFragmentSize1Img.setImageDrawable(getResources().getDrawable(R.mipmap.execellent));
                                } else if (75 >= pm && pm >= 36) {
                                    mFragmentSize1Img.setImageDrawable(getResources().getDrawable(R.mipmap.better));
                                } else if (pm >= 76) {
                                    mFragmentSize1Img.setImageDrawable(getResources().getDrawable(R.mipmap.bad));
                                }
                                Log.e("xcf获取空气数据", mainAirBeans.getMsg());
                            } else {
                                Log.e("xcf获取空气数据", mainAirBeans.getMsg());
                                App.ErrorToken(mainAirBeans.getResultCode(), mainAirBeans.getMsg());
                            }
                        }
                    });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_WRITE_ACCESS_PERMISSION) {
            initLoc();//定位
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission(final String permission, String rationale, final int requestCode) {
        requestPermissions(new String[]{permission}, requestCode);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
        }
        EventBus.getDefault().unregister(this);
    }

    //定位回调函数
    //定位
    public void initLoc() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(1000000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            Log.e("22222222222", String.valueOf(amapLocation.getErrorCode()));
            if (amapLocation.getErrorCode() == 0) {
                if (addressflag == 0) {
                    addressflag = 1;
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                    lat = String.valueOf(amapLocation.getLatitude());//获取纬度
                    lng = String.valueOf(amapLocation.getLongitude());//获取经度
                    Log.e("22222222222lng2", lng + "");
                    Log.e("22222222222lat2", lat + "");
                    amapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);//定位时间
                    Log.e("333333333address", amapLocation.getCountry() + "" + amapLocation.getProvince()
                            + "" + amapLocation.getCity() + "" + amapLocation.getProvince() + ""
                            + amapLocation.getDistrict() + "" + amapLocation.getStreet() + ""
                            + amapLocation.getStreetNum());
                    mFragmentAddress.setText(amapLocation.getCity()+amapLocation.getDistrict());
                    city = amapLocation.getDistrict() + "," + amapLocation.getCity();
                    city = city.substring(0, city.length() - 1);
                    initNetWork(city);
                }
            }
        }
    }

    //EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        //是否显示返回键
        if (event.isShowBank()) {
            mFragmentBank.setVisibility(View.VISIBLE);
        } else {
            mFragmentBank.setVisibility(View.GONE);
        }
        String city = event.getCheckId();
        if (city != null) {
            mFragmentAddress.setText(city);
            initNetWork(city);
        }
        if (event.isShebBei()) {
            initData();
        }
    }

    @Override
    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
//        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
//        banner.stopAutoPlay();
    }

    @Override
    public void XiuGaiName() {
//        initData();
    }
}

