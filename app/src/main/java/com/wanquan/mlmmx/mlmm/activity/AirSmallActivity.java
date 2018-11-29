package com.wanquan.mlmmx.mlmm.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.MainAirBeans;
import com.wanquan.mlmmx.mlmm.beans.MainRukuBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.PersonalinformationActivityBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.BatteryView;
import com.wanquan.mlmmx.mlmm.view.MyDialog_Views;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * 描述：空气小秘
 * 作者：薛昌峰
 * 时间：2018.01.11
 */

public class AirSmallActivity extends BaseActivity implements LocationSource, AMapLocationListener {
    private static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 110;
    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器
    private String lng;
    private String lat;
    private BluetoothAdapter bluetoothAdapter;
    private BatteryView mMainDianLiang;
    private ImageView mMainChongdian;
    private LinearLayout mMainBj;
    private LinearLayout mMainCenter;
    private LinearLayout mMainPhoto;
    private ImageView mMainShare;
    private TextView mMainNumber;
    private ImageView mMainProperty;
    private TextView mMainPmText;
    private TextView mMainPm;
    private TextView mMainWenDu;
    private TextView mMainShiDu;
    private RelativeLayout mMainBluetooth;
    private ImageView mMainBluetoothTitle;
    private LinearLayout mLinearLayout;
    private TextView mMainBluetoothName;
    private TextView mMainBluetoothSize;
    private TextView mMainAddress;
    private ImageView mMainAirImage;
    private TextView mMainAirNumber;
    private ImageView mMainAirProperty;
    private TextView mMainAirHouse;
    private TextView mMainTemp;
    private TextView mMainHumi;
    private RelativeLayout mMainCity;
    private TextView mMainBluetoothLianjie;
    private Dialog dialog2;
    private String dianliang = "0";
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    private String city = "";
    private boolean run = true;
    private final Handler handler = new Handler();
    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (run) {
                handler.postDelayed(this, 5000);
                //获取蓝牙数据
                if (SPUtils.get(AirSmallActivity.this, "off", "").equals("1")) {
                    if (!"".equals(SPUtils.get(AirSmallActivity.this, "pm25", "00"))) {
                        mMainNumber.setText(String.valueOf(SPUtils.get(AirSmallActivity.this, "pm25", "00")));
                        dialog2.dismiss();
                    }
                    if (!SPUtils.get(AirSmallActivity.this, "wendu", "00").equals("")) {
                        mMainWenDu.setText(String.valueOf(SPUtils.get(AirSmallActivity.this, "wendu", "00")) + "℃");
                        dialog2.dismiss();
                    }
                    if (!SPUtils.get(AirSmallActivity.this, "dianliang", "00").equals("")) {
                        mMainBluetoothSize.setText(String.valueOf(SPUtils.get(AirSmallActivity.this, "dianliang", "")) + "%");
                        String dianliang = String.valueOf(SPUtils.get(AirSmallActivity.this, "dianliang", "00"));
                        if (!dianliang.equals("null")) {
                            mMainDianLiang.setPower(Integer.parseInt(dianliang));
                        }
                        dialog2.dismiss();
                    }
                    if (!SPUtils.get(AirSmallActivity.this, "shidu", "00").equals("")) {
                        mMainShiDu.setText(String.valueOf(SPUtils.get(AirSmallActivity.this, "shidu", "00")) + "%");
                        dialog2.dismiss();
                    }
                    Log.e("fffff", String.valueOf(SPUtils.get(AirSmallActivity.this, "pm03", "00")) + "lll,ll");
                    if (!SPUtils.get(AirSmallActivity.this, "pm03", "00").equals("")) {
                        mMainPm.setText(String.valueOf(SPUtils.get(AirSmallActivity.this, "pm03", "00")) + "个/0.01L");
                        dialog2.dismiss();
                    }
                    initDatas();
                    //空气数据入库
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("itfaceId", "potable_002");
                    hashMap.put("userName", String.valueOf(SPUtils.get(AirSmallActivity.this, "username", "")));
                    hashMap.put("temp", String.valueOf(SPUtils.get(AirSmallActivity.this, "wendu", "")));
                    hashMap.put("hum", String.valueOf(SPUtils.get(AirSmallActivity.this, "shidu", "")));
                    hashMap.put("pm25", String.valueOf(SPUtils.get(AirSmallActivity.this, "pm25", "")));
                    hashMap.put("pm03", String.valueOf(SPUtils.get(AirSmallActivity.this, "pm03", "")));
                    hashMap.put("longitude", lng);
                    hashMap.put("latitude", lat);
                    JSONObject jsonObject = new JSONObject(hashMap);
                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<MainRukuBeans>(AirSmallActivity.this) {
                                @Override
                                public void onSuccess(MainRukuBeans mainRukuBeans, Call call, Response response) {
                                    if (mainRukuBeans.getResultCode() == 1) {
                                    } else {
//                                        App.ErrorToken(mainRukuBeans.getResultCode(),mainRukuBeans.getMsg());
                                    }
                                }
                            });
                    mMainBluetoothName.setText(String.valueOf(SPUtils.get(AirSmallActivity.this, "name", "")));
                    mMainBluetoothLianjie.setText("断开蓝牙连接");
                } else if (SPUtils.get(AirSmallActivity.this, "off", "").equals("0")) {
                    mMainNumber.setText("00");
                    mMainWenDu.setText("00" + "℃");
                    mMainBluetoothSize.setText("00" + "%");
                    mMainShiDu.setText("00" + "%");
                    mMainPm.setText("00" + "个/0.01L");
                    mMainBluetoothName.setText("空气小秘检测仪");
                    mMainBluetoothLianjie.setText("蓝牙连接");
                }
            }
        }
    };
    private String flag;

    private void initDatas() {
        int pm1 = 0;
        pm1 = Integer.parseInt(SPUtils.get(AirSmallActivity.this, "pm25", "0") + "");
        if (35 >= pm1 && pm1 >= 0) {
            mMainProperty.setBackground(getResources().getDrawable(R.drawable.execellent));
        } else if (75 >= pm1 && pm1 >= 36) {
            mMainProperty.setBackground(getResources().getDrawable(R.drawable.better));
        } else if (pm1 >= 76) {
            mMainProperty.setBackground(getResources().getDrawable(R.drawable.bad));
        }
        //设置背景颜色
        if (pm1 >= 0 && pm1 <= 35) {
            mMainBj.setBackground(getResources().getDrawable(R.color.one));
        } else if (pm1 > 35 && pm1 <= 75) {
            mMainBj.setBackground(getResources().getDrawable(R.color.two));
        } else if (pm1 > 75) {
            mMainBj.setBackground(getResources().getDrawable(R.color.three));
        }
        //是否充电
        if (SPUtils.get(AirSmallActivity.this, "chongdian", "").equals("0")) {
            //没充电
            mMainDianLiang.setVisibility(View.VISIBLE);
            mMainChongdian.setVisibility(View.GONE);
        } else if (SPUtils.get(AirSmallActivity.this, "chongdian", "").equals("1")) {
            //充电
            mMainDianLiang.setVisibility(View.GONE);
            mMainChongdian.setVisibility(View.VISIBLE);
            mMainChongdian.setBackground(getResources().getDrawable(R.mipmap.power));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Toast.makeText(this, flag + "", Toast.LENGTH_SHORT).show();
        //模拟网络交互时间为5秒
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //如果收不到蓝牙返回的值就把蓝牙断开
                if (BluetoothNewActivity.single != null) {
                    initData();
                    if (SPUtils.get(AirSmallActivity.this, "off", "").equals("1")) {
//                        final Boolean recharge_flag = true;
                        dialog2 = new MyDialog_Views(AirSmallActivity.this, R.style.MyDialog);
                        dialog2.setCancelable(true);
                        dialog2.show();
                        MyDialog_Views myDialog_views = new MyDialog_Views(AirSmallActivity.this, "正在获取数据...", "");
                        //模拟网络交互时间为5秒
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                task.run();
                            }
                        }, 5000);
                    }
                } else {
                    if (BluetoothNewActivity.bluetoothGatt != null) {
                        BluetoothNewActivity.bluetoothGatt.disconnect();
                    }
                }
            }
        }, 2500);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        flag = getIntent().getStringExtra("flag");

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(AirSmallActivity.this, R.color.black);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION,
                    getString(R.string.mis_permission_rationale_write_storage),
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        } else {
            initLoc();//定位
        }
        // 注册
        EventBus.getDefault().register(this);
        initData();
        initListeners();

        if (BluetoothNewActivity.single != null) {
            if (SPUtils.get(AirSmallActivity.this, "off", "").equals("0")) {
                //提示蓝牙未连接
                final AlertDialog alert;
                alert = new AlertDialog.Builder(this).create();
                alert.show();
                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.delete_dialogss);
                alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                //取消
                alert.getWindow().findViewById(R.id.cart_delete_cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                        return;
                    }
                });
                TextView content = (TextView) alert.getWindow().findViewById(R.id.cart_delete_content);
                content.setText("蓝牙尚未连接，是否连接蓝牙？");
                //跳转到连接蓝牙界面
                alert.getWindow().findViewById(R.id.cart_delete_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (bluetoothAdapter == null) {
                            Toast.makeText(AirSmallActivity.this, "本机不支持蓝牙", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        alert.dismiss();
                        if (flag.equals("100")) {
                            Intent intent = new Intent(AirSmallActivity.this, BluetoothNewActivity.class);
                            intent.putExtra("flag", "100");
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(AirSmallActivity.this, BluetoothNewActivity.class);
                            intent.putExtra("flag", "101");
                            startActivity(intent);

                        }
                    }
                });
            }
        } else {
            if (BluetoothNewActivity.bluetoothGatt != null) {
                BluetoothNewActivity.bluetoothGatt.disconnect();
            }
        }
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_air_small;
    }

    @Override
    public void initView() throws Exception {
        mMainCenter = (LinearLayout) findViewById(R.id.Main_center);
        mMainCity = (RelativeLayout) findViewById(R.id.Main_City);
        mMainBj = (LinearLayout) findViewById(R.id.Main_bj);//背景
        mMainChongdian = (ImageView) findViewById(R.id.Main_chongdian);//充电
        mMainDianLiang = (BatteryView) findViewById(R.id.Main_DianLiang);//电量
        mMainPhoto = (LinearLayout) findViewById(R.id.Main_Photo);
        mMainShare = (ImageView) findViewById(R.id.Main_Share);
        mMainNumber = (TextView) findViewById(R.id.Main_Number);
        mMainProperty = (ImageView) findViewById(R.id.Main_Property);
        mMainPmText = (TextView) findViewById(R.id.Main_pmText);
        mMainPm = (TextView) findViewById(R.id.Main_pm);
        mMainWenDu = (TextView) findViewById(R.id.Main_WenDu);
        mMainShiDu = (TextView) findViewById(R.id.Main_ShiDu);
        mMainBluetooth = (RelativeLayout) findViewById(R.id.Main_Bluetooth);
        mMainBluetoothTitle = (ImageView) findViewById(R.id.Main_Bluetooth_title);
        mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        mMainBluetoothName = (TextView) findViewById(R.id.Main_Bluetooth_name);
        mMainBluetoothSize = (TextView) findViewById(R.id.Main_Bluetooth_size);
        mMainAddress = (TextView) findViewById(R.id.Main_Address);
        mMainAirImage = (ImageView) findViewById(R.id.Main_AirImage);
        mMainAirNumber = (TextView) findViewById(R.id.Main_AirNumber);
        mMainAirProperty = (ImageView) findViewById(R.id.Main_AirProperty);
        mMainAirHouse = (TextView) findViewById(R.id.Main_AirHouse);
        mMainTemp = (TextView) findViewById(R.id.Main_Temp);
        mMainHumi = (TextView) findViewById(R.id.Main_Humi);
        mMainBluetoothLianjie = (TextView) findViewById(R.id.Main_Bluetooth_lianjie);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission(final String permission, String rationale, final int requestCode) {
        requestPermissions(new String[]{permission}, requestCode);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //定位回调函数
    //定位
    public void initLoc() {
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
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
//            Log.e("22222222222", String.valueOf(amapLocation.getErrorCode()));
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                lat = String.valueOf(amapLocation.getLatitude());//获取纬度
                lng = String.valueOf(amapLocation.getLongitude());//获取经度
//                Log.e("lng2", lng + "");
//                Log.e("lat2", lat + "");
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
//                Log.e("address", amapLocation.getCountry() + "" + amapLocation.getProvince()
//                        + "" + amapLocation.getCity() + "" + amapLocation.getProvince() + ""
//                        + amapLocation.getDistrict() + "" + amapLocation.getStreet() + ""
//                        + amapLocation.getStreetNum());
                mMainAddress.setText(amapLocation.getProvince()
                        + "" + amapLocation.getCity() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + ""
                        + amapLocation.getStreetNum());
                city = amapLocation.getCity();
                SPUtils.put(AirSmallActivity.this, "address", amapLocation.getProvince()
                        + "" + amapLocation.getCity() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + ""
                        + amapLocation.getStreetNum());
                initNetWork(city);
            }

        }
    }

    private void initNetWork(String city) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "potable_001");
        hashMap.put("city", city);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<MainAirBeans>(this) {
                    @Override
                    public void onSuccess(MainAirBeans mainAirBeans, Call call, Response response) {
                        if (mainAirBeans.getResultCode() == 1) {
                            Glide.with(AirSmallActivity.this).load(mainAirBeans.getData().getResult().getUrl()).into(mMainAirImage);
                            int pm = Integer.parseInt(mainAirBeans.getData().getResult().getPM25());

                            mMainAirNumber.setText(pm + "");

                            mMainTemp.setText(mainAirBeans.getData().getResult().getTemp() + "℃");
                            mMainHumi.setText(mainAirBeans.getData().getResult().getHumi() + "%");
                            if (35 >= pm && pm >= 0) {
                                mMainAirProperty.setBackground(getResources().getDrawable(R.drawable.execellent));
                            } else if (75 >= pm && pm >= 36) {
                                mMainAirProperty.setBackground(getResources().getDrawable(R.drawable.better));
                            } else if (pm >= 76) {
                                mMainAirProperty.setBackground(getResources().getDrawable(R.drawable.bad));
                            }
                        } else {
                            App.ErrorToken(mainAirBeans.getResultCode(), mainAirBeans.getMsg());

                        }
                    }
                });
    }

    @Override
    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    private void initData() {
        //判断电量是否小于20%和10%
        dianliang = String.valueOf(SPUtils.get(AirSmallActivity.this, "dianliang", "0"));
//        Log.e("fffffffff", dianliang);
        if (!"0".equals(dianliang)) {
            if (Integer.parseInt(dianliang) <= 20) {
                dialogs(20, "请及时充电!");
            } else if (Integer.parseInt(dianliang) <= 10) {
                dialogs(10, "请及时充电!");
            } else if (Integer.parseInt(dianliang) <= 2) {
                dialogs(2, "即将关机!");
            }
        }
    }

    private void dialogs(int i, String s) {
        Toast.makeText(this, "电量低于" + i + s, Toast.LENGTH_SHORT).show();
//        final AlertDialog alert;
//        alert = new AlertDialog.Builder(this).create();
//        alert.show();
//        //加载自定义dialog
//        alert.getWindow().setContentView(R.layout.delete);
//        alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        //取消
//        alert.getWindow().findViewById(R.id.cart_delete_cancle).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alert.dismiss();
//                return;
//            }
//        });
//        TextView content = (TextView) alert.getWindow().findViewById(R.id.cart_delete_content);
//        content.setText("电量低于" + i + s);
//        //确认登录
//        alert.getWindow().findViewById(R.id.cart_delete_confirm).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alert.dismiss();
//                return;
//            }
//        });
    }

    private void initListeners() {
        mMainPhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mMainCity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AirSmallActivity.this, SearchActivity.class));
            }
        });

        mMainBluetooth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SPUtils.get(AirSmallActivity.this, "off", "0").equals("1")) {
                    final AlertDialog alert;
                    alert = new AlertDialog.Builder(AirSmallActivity.this).create();
                    alert.show();
                    //加载自定义dialog
                    alert.getWindow().setContentView(R.layout.delete_dialogss);
                    alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    //取消
                    alert.getWindow().findViewById(R.id.cart_delete_cancle).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                            return;
                        }
                    });
                    final TextView content = (TextView) alert.getWindow().findViewById(R.id.cart_delete_content);
                    content.setText("确定断开蓝牙连接吗？");
                    //确认登录
                    alert.getWindow().findViewById(R.id.cart_delete_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            if (BluetoothNewActivity.bluetoothAdapter != null) {
//                                BluetoothNewActivity.bluetoothAdapter.disable();
//                            }
                            SPUtils.put(AirSmallActivity.this, "istwo","true");

//                            Intent logoutIntent = new Intent();
//                            logoutIntent.setAction("com.xcf.receiverout");
//                            logoutIntent.putExtra("receiverout", "1");
//                            AirSmallActivity.this.sendBroadcast(logoutIntent);
                            //断开蓝牙连接
                            if (BluetoothNewActivity.bluetoothGatt != null) {
                                BluetoothNewActivity.bluetoothGatt.disconnect();
                                //当你要手动断开时调用disconnect方法，然后在onConnectionStateChange的状态回调中调用close方法。
                                //如果在disconnect后直接调用close，会导致不走onConnectionStateChange这里的。
//                                BluetoothNewActivity.bluetoothGatt.close();
                            }
                            alert.dismiss();
                            final Boolean recharge_flag = true;
                            final Dialog dialog;
                            dialog = new MyDialog_Views(AirSmallActivity.this, R.style.MyDialog);
                            dialog.setCancelable(false);
                            dialog.show();
                            MyDialog_Views myDialog_views = new MyDialog_Views(AirSmallActivity.this, "正在断开...", "");
                            //模拟网络交互时间为5秒
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //访问服务器时间
                                    if (recharge_flag) {
                                        dialog.dismiss();
                                        Toast.makeText(AirSmallActivity.this, "蓝牙已断开", Toast.LENGTH_SHORT).show();
                                        SPUtils.put(AirSmallActivity.this, "off", "0");
                                    } else {
                                    }
                                }
                            }, 3000);
                            return;
                        }
                    });
                } else if (SPUtils.get(AirSmallActivity.this, "off", "0").equals("0")) {
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (bluetoothAdapter == null) {
                        Toast.makeText(AirSmallActivity.this, "本机不支持蓝牙", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        if (bluetoothAdapter.isEnabled()) {
                        } else {
                            bluetoothAdapter.enable();
                        }
                        if (flag.equals("100")) {
                            Intent intent2 = new Intent(AirSmallActivity.this, BluetoothNewActivity.class);
                            intent2.putExtra("flag", "100");
                            startActivity(intent2);
                        } else {
                            Intent intent2 = new Intent(AirSmallActivity.this, BluetoothNewActivity.class);
                            intent2.putExtra("flag", "101");
                            startActivity(intent2);
                        }
                    }
                }
            }
        });
        mMainShare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AirSmallActivity.this, AirSmallShareActivity.class);
                startActivityForResult(intent, 11);
            }
        });
    }

    //EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        String city = event.getCheckId();
        mMainAddress.setText(city);
        initNetWork(city);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_STORAGE_WRITE_ACCESS_PERMISSION) {
            initLoc();
        }
    }
}














