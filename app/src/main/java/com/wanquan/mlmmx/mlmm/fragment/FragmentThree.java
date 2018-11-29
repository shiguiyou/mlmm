package com.wanquan.mlmmx.mlmm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;

/**
 * Created by sgy on 2017/8/8.
 */

public class FragmentThree extends Fragment {
    private TextView mFragmentThreeText;
    private ImageView mFragmentThreeImg;
    private TextView mFragmentThreeWD;
    private TextView mFragmentThreeSD;
    private TextView mFragmentThreeAddress;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_three, null);

        initViews();
        initData();
        return view;
    }

    private void initViews() {
        mFragmentThreeText = (TextView) view.findViewById(R.id.FragmentThree_text);
        mFragmentThreeImg = (ImageView) view.findViewById(R.id.FragmentThree_Img);
        mFragmentThreeWD = (TextView) view.findViewById(R.id.FragmentThree_WD);
        mFragmentThreeSD = (TextView) view.findViewById(R.id.FragmentThree_SD);
        mFragmentThreeAddress = (TextView) view.findViewById(R.id.FragmentThree_Address);
    }

    private void initData() {
        mFragmentThreeText.setText(SPUtils.get(getContext(), "pm25", "00") + "");
        int pm = Integer.parseInt((String) SPUtils.get(getContext(), "pm25", "00"));
        if (35 >= pm && pm >= 0) {
            mFragmentThreeImg.setBackground(getResources().getDrawable(R.drawable.execellent));
        } else if (75 >= pm && pm >= 36) {
            mFragmentThreeImg.setBackground(getResources().getDrawable(R.drawable.better));
        } else if (pm >= 76) {
            mFragmentThreeImg.setBackground(getResources().getDrawable(R.drawable.bad));
        }
        mFragmentThreeWD.setText(SPUtils.get(getActivity(), "wendu", "00") + "℃");
        mFragmentThreeSD.setText(SPUtils.get(getActivity(), "shidu", "00") + "%");
        mFragmentThreeAddress.setText(SPUtils.get(getActivity(), "address", "00") + "");
    }
}
