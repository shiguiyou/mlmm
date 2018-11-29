package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.MyAdapter;
import com.wanquan.mlmmx.mlmm.beans.SettingBabyMessageBeans;
import com.wanquan.mlmmx.mlmm.fragment.GrowthCurveButtonSGFragment;
import com.wanquan.mlmmx.mlmm.fragment.GrowthCurveButtonTZFragment;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.phone.Release_Work_Activity;
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
 * 描述：成长曲线
 * 作者：薛昌峰
 * 时间：2017.12.12
 */
public class GrowthCurveActivity extends BaseActivity {
    private RadioGroup mGrowthCurveRadioGroup;
    private RadioButton mGrowthCurveRadioButton1;
    private RadioButton mGrowthCurveRadioButton2;
    private ViewPager mGrowthCurveViewPager;
    private TextView mGrowthCurveTV;
    List<Fragment> mList;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    MyAdapter mAdapter;
    GrowthCurveButtonSGFragment mGrowthCurveButtonSGFragment;
    GrowthCurveButtonTZFragment mGrowthCurveButtonTZFragment;
    private int sex = 0;
    private String sg;
    private String tz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(GrowthCurveActivity.this, R.color.black);

        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_growth_curve;
    }

    @Override
    public void initView() throws Exception {
        mGrowthCurveRadioGroup = (RadioGroup) findViewById(R.id.GrowthCurve_RadioGroup);
        mGrowthCurveRadioButton1 = (RadioButton) findViewById(R.id.GrowthCurve_RadioButton1);
        mGrowthCurveRadioButton2 = (RadioButton) findViewById(R.id.GrowthCurve_RadioButton2);
        mGrowthCurveViewPager = (ViewPager) findViewById(R.id.GrowthCurve_ViewPager);
        mGrowthCurveTV = (TextView) findViewById(R.id.GrowthCurve_TV);
    }

    private void initData() {
        if (SPUtils.get(GrowthCurveActivity.this, "authority", "").equals("1") || SPUtils.get(GrowthCurveActivity.this, "authority", "").equals("2")) {
            mGrowthCurveTV.setVisibility(View.VISIBLE);
        } else if (SPUtils.get(GrowthCurveActivity.this, "authority", "").equals("0")) {
            mGrowthCurveTV.setVisibility(View.GONE);
        }
        //获取宝宝当前状态
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "033");
        hashMap.put("token", SPUtils.get(GrowthCurveActivity.this, "token", ""));
        hashMap.put("id", SPUtils.get(GrowthCurveActivity.this, "babyId", ""));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<SettingBabyMessageBeans>(GrowthCurveActivity.this) {
                    @Override
                    public void onSuccess(SettingBabyMessageBeans mSettingBabyMessageBeans, Call call, Response response) {
                        if (mSettingBabyMessageBeans.getResultCode() == 1) {
                            tz = String.valueOf(mSettingBabyMessageBeans.getData().getBabyWeight());
                            sg = String.valueOf(mSettingBabyMessageBeans.getData().getBabyHeight());
                            sex = mSettingBabyMessageBeans.getData().getBabySex();
                        } else {
                            Toast.makeText(GrowthCurveActivity.this, mSettingBabyMessageBeans.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        mList = new ArrayList<>();
        mGrowthCurveButtonSGFragment = new GrowthCurveButtonSGFragment();
        mGrowthCurveButtonTZFragment = new GrowthCurveButtonTZFragment();
        mList.add(mGrowthCurveButtonSGFragment);
        mList.add(mGrowthCurveButtonTZFragment);

        mFragmentManager = getSupportFragmentManager();
        mAdapter = new MyAdapter(mFragmentManager, mList);
        mGrowthCurveViewPager.setAdapter(mAdapter);

    }

    private void initListeners() {
        mGrowthCurveRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                resetViewPager(checkedId);
            }
        });
        //滑动ViewPage的时候及时修改底部导航栏对应的图标
        mGrowthCurveViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //根据当前位置设置默认选中单选按钮
                resetRadioButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mGrowthCurveTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SPUtils.get(GrowthCurveActivity.this, "authority", "").equals("0")) {
                    startActivity(new Intent(GrowthCurveActivity.this, SettingSGTZActivity.class));
                } else {
                    Toast.makeText(GrowthCurveActivity.this, "亲！您还没有该权限哦！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void resetViewPager(int checkedId) {
        switch (checkedId) {
            case R.id.GrowthCurve_RadioButton1:
                mGrowthCurveViewPager.setCurrentItem(0);
                mGrowthCurveRadioButton1.setTextColor(getResources().getColor(R.color.white));
                mGrowthCurveRadioButton2.setTextColor(getResources().getColor(R.color.tops));
                mGrowthCurveRadioButton1.setBackground(getResources().getDrawable(R.drawable.growth_curve_bg));
                mGrowthCurveRadioButton2.setBackground(getResources().getDrawable(R.drawable.growth_curve_bg4));
                break;
            case R.id.GrowthCurve_RadioButton2:
                mGrowthCurveViewPager.setCurrentItem(1);
                mGrowthCurveRadioButton1.setTextColor(getResources().getColor(R.color.tops));
                mGrowthCurveRadioButton2.setTextColor(getResources().getColor(R.color.white));
                mGrowthCurveRadioButton1.setBackground(getResources().getDrawable(R.drawable.growth_curve_bg3));
                mGrowthCurveRadioButton2.setBackground(getResources().getDrawable(R.drawable.growth_curve_bg2));
                break;
        }
    }

    private void resetRadioButton(int position) {
        //获取position位置处对于的单选按钮
        RadioButton radioButton = (RadioButton) mGrowthCurveRadioGroup.getChildAt(position);
        //设置当前单选按钮默认选中
        radioButton.setChecked(true);
    }

    public void GrowthCurve_Bank(View view) {
        finish();
    }
}
