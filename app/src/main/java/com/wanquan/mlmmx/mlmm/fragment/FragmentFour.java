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

public class FragmentFour extends Fragment {
    private TextView mFragmentFourText;
    private ImageView mFragmentFourImg;
    private TextView mFragmentFourWD;
    private TextView mFragmentFourSD;
    private TextView mFragmentFourAddress;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_four, null);

        initViews();
        initData();

        return view;
    }

    private void initViews() {
        mFragmentFourText = (TextView) view.findViewById(R.id.FragmentFour_text);
        mFragmentFourImg = (ImageView) view.findViewById(R.id.FragmentFour_Img);
        mFragmentFourWD = (TextView) view.findViewById(R.id.FragmentFour_WD);
        mFragmentFourSD = (TextView) view.findViewById(R.id.FragmentFour_SD);
        mFragmentFourAddress = (TextView) view.findViewById(R.id.FragmentFour_Address);

    }

    private void initData() {
        mFragmentFourText.setText(SPUtils.get(getContext(), "pm25", "00") + "");
        int pm = Integer.parseInt((String) SPUtils.get(getContext(), "pm25", "00"));
        if (35 >= pm && pm >= 0) {
            mFragmentFourImg.setBackground(getResources().getDrawable(R.drawable.execellent));
        } else if (75 >= pm && pm >= 36) {
            mFragmentFourImg.setBackground(getResources().getDrawable(R.drawable.better));
        } else if (pm >= 76) {
            mFragmentFourImg.setBackground(getResources().getDrawable(R.drawable.bad));
        }
        mFragmentFourWD.setText(SPUtils.get(getActivity(), "wendu", "00") + "℃");
        mFragmentFourSD.setText(SPUtils.get(getActivity(), "shidu", "00") + "%");
        mFragmentFourAddress.setText(SPUtils.get(getActivity(), "address", "00") + "");
    }
}
