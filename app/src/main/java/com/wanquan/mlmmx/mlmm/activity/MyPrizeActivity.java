package com.wanquan.mlmmx.mlmm.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.MyAdapter;
import com.wanquan.mlmmx.mlmm.fragment.MyPrizeCommodityFragment;
import com.wanquan.mlmmx.mlmm.fragment.MyPrizeDiscountFragment;
import com.wanquan.mlmmx.mlmm.fragment.MyPrizeIntegralFragment;
import com.wanquan.mlmmx.mlmm.fragment.ReceiveShareFragment;
import com.wanquan.mlmmx.mlmm.fragment.SponsorShareFragment;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：我的奖品
 * 作者：薛昌峰
 * 时间：2018.06.08
 */

public class MyPrizeActivity extends BaseActivity {
    private LinearLayout mAppTitleBank;
    private TextView mAppTitleName;
    private TextView mAppTitleSave;
    private RadioGroup mMyPrizeActivityRadioGroup;
    private RadioButton mMyPrizeActivityRadioButton1;
    private RadioButton mMyPrizeActivityRadioButton2;
    private RadioButton mMyPrizeActivityRadioButton3;
    private ViewPager mMyPrizeActivityViewPager;

    private MyPrizeIntegralFragment mMyPrizeIntegralFragment;
    private MyPrizeDiscountFragment mMyPrizeDiscountFragment;
    private MyPrizeCommodityFragment mMyPrizeCommodityFragment;
    private List<Fragment> mList;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(MyPrizeActivity.this, R.color.tops);

        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_my_prize;
    }

    @Override
    public void initView() throws Exception {
        mAppTitleBank = (LinearLayout) findViewById(R.id.App_Title_Bank);
        mAppTitleName = (TextView) findViewById(R.id.App_Title_Name);
        mAppTitleSave = (TextView) findViewById(R.id.App_Title_Save);
        mMyPrizeActivityRadioGroup = (RadioGroup) findViewById(R.id.MyPrizeActivity_RadioGroup);
        mMyPrizeActivityRadioButton1 = (RadioButton) findViewById(R.id.MyPrizeActivity_RadioButton1);
        mMyPrizeActivityRadioButton2 = (RadioButton) findViewById(R.id.MyPrizeActivity_RadioButton2);
        mMyPrizeActivityRadioButton3 = (RadioButton) findViewById(R.id.MyPrizeActivity_RadioButton3);
        mMyPrizeActivityViewPager = (ViewPager) findViewById(R.id.MyPrizeActivity_ViewPager);
    }

    private void initData() {
        mAppTitleName.setText("我的奖品");

        mList = new ArrayList<>();
        mMyPrizeIntegralFragment = new MyPrizeIntegralFragment();
        mMyPrizeDiscountFragment = new MyPrizeDiscountFragment();
        mMyPrizeCommodityFragment = new MyPrizeCommodityFragment();
        mList.add(mMyPrizeIntegralFragment);
        mList.add(mMyPrizeDiscountFragment);
        mList.add(mMyPrizeCommodityFragment);

        mFragmentManager = getSupportFragmentManager();
        mAdapter = new MyAdapter(mFragmentManager, mList);
        mMyPrizeActivityViewPager.setAdapter(mAdapter);
    }

    private void initListeners() {
        mAppTitleBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mMyPrizeActivityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                resetViewPager(checkedId);
            }
        });
        //滑动ViewPage的时候及时修改底部导航栏对应的图标
        mMyPrizeActivityViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
            case R.id.MyPrizeActivity_RadioButton1:
                mMyPrizeActivityViewPager.setCurrentItem(0);
                mMyPrizeActivityRadioButton1.setTextColor(getResources().getColor(R.color.dsa));
                mMyPrizeActivityRadioButton2.setTextColor(getResources().getColor(R.color.black));
                mMyPrizeActivityRadioButton3.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.MyPrizeActivity_RadioButton2:
                mMyPrizeActivityViewPager.setCurrentItem(1);
                mMyPrizeActivityRadioButton1.setTextColor(getResources().getColor(R.color.black));
                mMyPrizeActivityRadioButton2.setTextColor(getResources().getColor(R.color.dsa));
                mMyPrizeActivityRadioButton3.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.MyPrizeActivity_RadioButton3:
                mMyPrizeActivityViewPager.setCurrentItem(2);
                mMyPrizeActivityRadioButton1.setTextColor(getResources().getColor(R.color.black));
                mMyPrizeActivityRadioButton2.setTextColor(getResources().getColor(R.color.black));
                mMyPrizeActivityRadioButton3.setTextColor(getResources().getColor(R.color.dsa));
                break;
        }
    }

    private void resetRadioButton(int position) {
        //获取position位置处对于的单选按钮
        RadioButton radioButton = (RadioButton) mMyPrizeActivityRadioGroup.getChildAt(position);
        //设置当前单选按钮默认选中
        radioButton.setChecked(true);
    }


}
