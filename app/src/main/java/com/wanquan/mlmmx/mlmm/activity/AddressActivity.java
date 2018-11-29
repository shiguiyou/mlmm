package com.wanquan.mlmmx.mlmm.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.DefaultItemAnimator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.geocoder.RegeocodeRoad;
import com.amap.api.services.geocoder.StreetNumber;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.PoiKeywordSearchAdapter;
import com.wanquan.mlmmx.mlmm.adapter.PoiKeywordSearchAdaptetwo;
import com.wanquan.mlmmx.mlmm.beans.PoiAddressBean;
import com.wanquan.mlmmx.mlmm.phone.Release_Work_Activity;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.ToastUtil;
import com.wanquan.mlmmx.mlmm.view.MyListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述：附近地址
 * 作者：薛昌峰
 * 时间：2017.11.08
 */
public class AddressActivity extends AppCompatActivity implements PoiSearch.OnPoiSearchListener, LocationSource, AMapLocationListener {
    //定位需要的声明
    private static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 110;
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器
    private String lng;
    private String lat;
    private ListView mListView;
    private EditText mEt_keyword;
    private String keyWord = "写字楼";// 要输入的poi搜索关键字
    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private String address;
    private List<PoiItem> poiItems;
    private String address2;
    private List<PoiItem> pois;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(AddressActivity.this, R.color.black);

        initLoc();//定位
        initView();
        initData();
        initListener();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                regeocodeSearch(Double.valueOf(lat), Double.valueOf(lng), 2000);
            }
        }, 1000);

//      mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.ListView);
        mEt_keyword = (EditText) findViewById(R.id.et_keyword);
    }

    private void initListener() {
        mEt_keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                keyWord = String.valueOf(charSequence);
                if ("".equals(keyWord)) {
                    ToastUtil.show(AddressActivity.this, "请输入搜索关键字");
                    return;
                } else {
                    doSearchQuery(keyWord, address);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String keyWord, String address) {
        currentPage = 0;
        //不输入城市名称有些地方搜索不到
        query = new PoiSearch.Query(keyWord, "", address);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        //这里没有做分页加载了,默认给50条数据
        query.setPageSize(1000);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        //poiSearch.setBound(new PoiSearch.SearchBound(lat, 1000));//设置周边搜索的中心点以及半径
        poiSearch.searchPOIAsyn();
    }

    private void initData() {
    }


    /**
     * POI信息查询回调方法
     */
    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {  // 搜索poi的结果
                if (result.getQuery().equals(query)) {  // 是否是同一条
                    poiResult = result;
                    ArrayList<PoiAddressBean> data = new ArrayList<PoiAddressBean>();//自己创建的数据集合
                    // 取得搜索到的poiitems有多少页
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    for (PoiItem item : poiItems) {
                        //获取经纬度对象
                        LatLonPoint llp = item.getLatLonPoint();
                        double lon = llp.getLongitude();
                        double lat = llp.getLatitude();

                        String title = item.getTitle();
                        String text = item.getSnippet();
                        String provinceName = item.getProvinceName();
                        String cityName = item.getCityName();
                        String adName = item.getAdName();
                        data.add(new PoiAddressBean(String.valueOf(lon), String.valueOf(lat), title, text, provinceName, cityName, adName));
                    }
                    PoiKeywordSearchAdapter adapter = new PoiKeywordSearchAdapter(data, AddressActivity.this);
                    mListView.setAdapter(adapter);
                }
            } else {
                ToastUtil.show(AddressActivity.this, getString(R.string.no_result));
            }
        } else {
            ToastUtil.showerror(this, rCode);
        }
    }

    /**
     * POI信息查询回调方法
     */
    @Override
    public void onPoiItemSearched(PoiItem item, int rCode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_WRITE_ACCESS_PERMISSION) {
            initLoc();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission(final String permission, String rationale, final int requestCode) {
        requestPermissions(new String[]{permission}, requestCode);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();
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
//                address2 = amapLocation.getCountry() + "" + amapLocation.getProvince()
//                        + "" + amapLocation.getCity() + "" + amapLocation.getProvince() + ""
//                        + amapLocation.getDistrict() + "" + amapLocation.getStreet() + ""
//                        + amapLocation.getStreetNum();
                address = amapLocation.getDistrict();
//                doSearchQuery("写字楼", address);
                regeocodeSearch(Double.valueOf(lat), Double.valueOf(lng), 2000);
//               Log.e("Dddddddd", address);
            }
        }
    }

    /***
     * 逆地理编码获取定位后的附近地址
     * @param weidu
     * @param jingdu
     * @param distances 设置查找范围
     */
    private void regeocodeSearch(Double weidu, Double jingdu, int distances) {
        final LatLonPoint point = new LatLonPoint(weidu, jingdu);
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(point, distances, geocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(regeocodeQuery);

        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
                String preAdd = "";//地址前缀
                if (1000 == rCode) {
                    final RegeocodeAddress address = regeocodeResult.getRegeocodeAddress();
                    StringBuffer stringBuffer = new StringBuffer();
                    String area = address.getProvince();//省或直辖市
                    String loc = address.getCity();//地级市或直辖市
                    String subLoc = address.getDistrict();//区或县或县级市
                    String ts = address.getTownship();//乡镇
                    String thf = null;//道路
                    List<RegeocodeRoad> regeocodeRoads = address.getRoads();//道路列表
                    if (regeocodeRoads != null && regeocodeRoads.size() > 0) {
                        RegeocodeRoad regeocodeRoad = regeocodeRoads.get(0);
                        if (regeocodeRoad != null) {
                            thf = regeocodeRoad.getName();
                        }
                    }
                    String subthf = null;//门牌号
                    StreetNumber streetNumber = address.getStreetNumber();
                    if (streetNumber != null) {
                        subthf = streetNumber.getNumber();
                    }
                    String fn = address.getBuilding();//标志性建筑,当道路为null时显示
                    if (area != null) {
                        stringBuffer.append(area);
                        preAdd += area;
                    }
                    if (loc != null && !area.equals(loc)) {
                        stringBuffer.append(loc);
                        preAdd += loc;
                    }
                    if (subLoc != null) {
                        stringBuffer.append(subLoc);
                        preAdd += subLoc;
                    }
                    if (ts != null)
                        stringBuffer.append(ts);
                    if (thf != null)
                        stringBuffer.append(thf);
                    if (subthf != null)
                        stringBuffer.append(subthf);
                    if ((thf == null && subthf == null) && fn != null && !subLoc.equals(fn))
                        stringBuffer.append(fn + "附近");
                    //  String ps = "poi";

                    pois = address.getPois();//获取周围兴趣点
                    PoiKeywordSearchAdaptetwo adapter = new PoiKeywordSearchAdaptetwo(pois, AddressActivity.this);
                    mListView.setAdapter(adapter);
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
            }
        });
    }

    /**
     * 设置详情地址
     *
     * @param detailAddress
     */
    public void setDetailAddress(String detailAddress) {
        mEt_keyword.setText(detailAddress);
        Intent intent = new Intent(AddressActivity.this, Release_Work_Activity.class);
        intent.putExtra("name", detailAddress);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);
        setResult(2, intent);
        finish();
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    public void Address_Bank(View view) {
        finish();
    }
}
