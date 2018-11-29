package com.wanquan.mlmmx.mlmm.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.MyAdapter;
import com.wanquan.mlmmx.mlmm.fragment.ReceiveShareFragment;
import com.wanquan.mlmmx.mlmm.fragment.SponsorShareFragment;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：共享设备
 * 作者：薛昌峰
 * 时间：2017.10.28
 */
public class EquipmentActivity extends BaseActivity {
    private RadioGroup mEquipmentRadioGroup;
    private RadioButton mEquipmentRadioButton1;
    private RadioButton mEquipmentRadioButton2;
    private ViewPager mEquipmentViewPager;

    SponsorShareFragment mSponsorShareFragment;
    ReceiveShareFragment mReceiveShareFragment;

    List<Fragment> mList;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(EquipmentActivity.this, R.color.black);

        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_equipment;
    }

    @Override
    public void initView() throws Exception {
        mEquipmentRadioGroup = (RadioGroup) findViewById(R.id.Equipment_RadioGroup);
        mEquipmentRadioButton1 = (RadioButton) findViewById(R.id.Equipment_RadioButton1);
        mEquipmentRadioButton2 = (RadioButton) findViewById(R.id.Equipment_RadioButton2);
        mEquipmentViewPager = (ViewPager) findViewById(R.id.Equipment_ViewPager);
    }

    private void initData() {
        mList = new ArrayList<>();
        mSponsorShareFragment = new SponsorShareFragment();
        mReceiveShareFragment = new ReceiveShareFragment();
        mList.add(mSponsorShareFragment);
        mList.add(mReceiveShareFragment);

        mFragmentManager = getSupportFragmentManager();
        mAdapter = new MyAdapter(mFragmentManager, mList);
        mEquipmentViewPager.setAdapter(mAdapter);
    }

    private void initListeners() {
        mEquipmentRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                resetViewPager(checkedId);
            }
        });
        //滑动ViewPage的时候及时修改底部导航栏对应的图标
        mEquipmentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
            case R.id.Equipment_RadioButton1:
                mEquipmentViewPager.setCurrentItem(0);
                mEquipmentRadioButton1.setTextColor(getResources().getColor(R.color.black));
                mEquipmentRadioButton2.setTextColor(getResources().getColor(R.color.red));
                break;
            case R.id.Equipment_RadioButton2:
                mEquipmentViewPager.setCurrentItem(1);
                mEquipmentRadioButton1.setTextColor(getResources().getColor(R.color.red));
                mEquipmentRadioButton2.setTextColor(getResources().getColor(R.color.black));
                break;
        }
    }

    private void resetRadioButton(int position) {
        //获取position位置处对于的单选按钮
        RadioButton radioButton = (RadioButton) mEquipmentRadioGroup.getChildAt(position);
        //设置当前单选按钮默认选中
        radioButton.setChecked(true);
    }


    public void EquipmentActivity_Bank(View view) {
        finish();
    }
}
