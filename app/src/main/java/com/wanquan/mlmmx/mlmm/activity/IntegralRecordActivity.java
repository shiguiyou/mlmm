package com.wanquan.mlmmx.mlmm.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.MyAdapter;
import com.wanquan.mlmmx.mlmm.fragment.ConsumptionIntegralFragment;
import com.wanquan.mlmmx.mlmm.fragment.GainIntegralFragment;
import com.wanquan.mlmmx.mlmm.fragment.ReceiveShareFragment;
import com.wanquan.mlmmx.mlmm.fragment.SponsorShareFragment;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：积分记录
 * 作者：薛昌峰
 * 时间：2018.04.10
 */
public class IntegralRecordActivity extends BaseActivity {
    private LinearLayout mAppTitleBank;
    private TextView mAppTitleName;
    private TextView mAppTitleSave;
    private RadioGroup mIntegralRecordActivityRadioGroup;
    private RadioButton mIntegralRecordActivityRadioButton1;
    private RadioButton mIntegralRecordActivityRadioButton2;
    private ViewPager mIntegralRecordActivityViewPager;
    private GainIntegralFragment mGainIntegralFragment;
    private ConsumptionIntegralFragment mConsumptionIntegralFragment;
    private List<Fragment> mList;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(IntegralRecordActivity.this, R.color.tops);

        initData();
        initListeners();

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_integral_record;
    }

    private void initData() {
        mAppTitleName.setText("积分记录");
        mList = new ArrayList<>();
        mGainIntegralFragment = new GainIntegralFragment();
        mConsumptionIntegralFragment = new ConsumptionIntegralFragment();
        mList.add(mGainIntegralFragment);
        mList.add(mConsumptionIntegralFragment);

        mFragmentManager = getSupportFragmentManager();
        mAdapter = new MyAdapter(mFragmentManager, mList);
        mIntegralRecordActivityViewPager.setAdapter(mAdapter);
    }

    private void initListeners() {
        mAppTitleBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mIntegralRecordActivityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                resetViewPager(checkedId);
            }
        });
        //滑动ViewPage的时候及时修改底部导航栏对应的图标
        mIntegralRecordActivityViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
    }
    private void resetViewPager(int checkedId) {
        switch (checkedId) {
            case R.id.IntegralRecordActivity_RadioButton1:
                mIntegralRecordActivityViewPager.setCurrentItem(0);
                mIntegralRecordActivityRadioButton1.setTextColor(getResources().getColor(R.color.se));
                mIntegralRecordActivityRadioButton2.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.IntegralRecordActivity_RadioButton2:
                mIntegralRecordActivityViewPager.setCurrentItem(1);
                mIntegralRecordActivityRadioButton1.setTextColor(getResources().getColor(R.color.black));
                mIntegralRecordActivityRadioButton2.setTextColor(getResources().getColor(R.color.se));
                break;
        }
    }

    private void resetRadioButton(int position) {
        //获取position位置处对于的单选按钮
        RadioButton radioButton = (RadioButton) mIntegralRecordActivityRadioGroup.getChildAt(position);
        //设置当前单选按钮默认选中
        radioButton.setChecked(true);
    }
    @Override
    public void initView() throws Exception {
        mAppTitleBank = (LinearLayout) findViewById(R.id.App_Title_Bank);
        mAppTitleName = (TextView) findViewById(R.id.App_Title_Name);
        mAppTitleSave = (TextView) findViewById(R.id.App_Title_Save);
        mIntegralRecordActivityRadioGroup = (RadioGroup) findViewById(R.id.IntegralRecordActivity_RadioGroup);
        mIntegralRecordActivityRadioButton1 = (RadioButton) findViewById(R.id.IntegralRecordActivity_RadioButton1);
        mIntegralRecordActivityRadioButton2 = (RadioButton) findViewById(R.id.IntegralRecordActivity_RadioButton2);
        mIntegralRecordActivityViewPager = (ViewPager) findViewById(R.id.IntegralRecordActivity_ViewPager);

    }
}
