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

public class FragmentTwo extends Fragment {
    private TextView mFragmentTwoText;
    private ImageView mFragmentTwoImg;
    private TextView mFragmentTwoWD;
    private TextView mFragmentTwoSD;
    private TextView mFragmentTwoAddress;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragemnt_two, null);

        initViews();
        initData();
        initListeners();

        return view;
    }

    private void initViews() {
        mFragmentTwoText = (TextView) view.findViewById(R.id.FragmentTwo_text);
        mFragmentTwoImg = (ImageView) view.findViewById(R.id.FragmentTwo_Img);
        mFragmentTwoWD = (TextView) view.findViewById(R.id.FragmentTwo_WD);
        mFragmentTwoSD = (TextView) view.findViewById(R.id.FragmentTwo_SD);
        mFragmentTwoAddress = (TextView) view.findViewById(R.id.FragmentTwo_Address);
    }

    private void initData() {
        mFragmentTwoText.setText(SPUtils.get(getContext(), "pm25", "00") + "");
        int pm = Integer.parseInt((String) SPUtils.get(getContext(), "pm25", "00"));
        if (35 >= pm && pm >= 0) {
            mFragmentTwoImg.setBackground(getResources().getDrawable(R.drawable.execellent));
        } else if (75 >= pm && pm >= 36) {
            mFragmentTwoImg.setBackground(getResources().getDrawable(R.drawable.better));
        } else if (pm >= 76) {
            mFragmentTwoImg.setBackground(getResources().getDrawable(R.drawable.bad));
        }
        mFragmentTwoWD.setText(SPUtils.get(getActivity(), "wendu", "00") + "℃");
        mFragmentTwoSD.setText(SPUtils.get(getActivity(), "shidu", "00") + "%");
        mFragmentTwoAddress.setText(SPUtils.get(getActivity(), "address", "00") + "");
    }

    private void initListeners() {
    }
}
