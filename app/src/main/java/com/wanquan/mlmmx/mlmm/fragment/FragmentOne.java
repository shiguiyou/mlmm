package com.wanquan.mlmmx.mlmm.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

public class FragmentOne extends Fragment {
    private ImageView mOneImg;
    private TextView mFragmentOneText;
    private ImageView mFragmentOneImg;
    private TextView mFragmentOneWD;
    private TextView mFragmentOneSD;
    private TextView mFragmentOneAddress;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragemnt_one, null);
        initViews();

        String path = String.valueOf(SPUtils.get(getContext(), "path", ""));
        if (!"".equals(path)) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            mOneImg.setImageBitmap(bitmap);
        } else {
            mOneImg.setImageDrawable(getResources().getDrawable(R.mipmap.img1));
        }


        initData();
        initListeners();

        return view;
    }

    private void initViews() {
        mOneImg = (ImageView) view.findViewById(R.id.One_Img);
        mFragmentOneText = (TextView) view.findViewById(R.id.FragmentOne_text);
        mFragmentOneImg = (ImageView) view.findViewById(R.id.FragmentOne_Img);
        mFragmentOneWD = (TextView) view.findViewById(R.id.FragmentOne_WD);
        mFragmentOneSD = (TextView) view.findViewById(R.id.FragmentOne_SD);
        mFragmentOneAddress = (TextView) view.findViewById(R.id.FragmentOne_Address);

    }

    private void initData() {
        mFragmentOneText.setText(SPUtils.get(getContext(), "pm25", "00") + "");
        int pm = Integer.parseInt((String) SPUtils.get(getContext(), "pm25", "00"));
        if (35 >= pm && pm >= 0) {
            mFragmentOneImg.setBackground(getResources().getDrawable(R.drawable.execellent));
        } else if (75 >= pm && pm >= 36) {
            mFragmentOneImg.setBackground(getResources().getDrawable(R.drawable.better));
        } else if (pm >= 76) {
            mFragmentOneImg.setBackground(getResources().getDrawable(R.drawable.bad));
        }
        mFragmentOneWD.setText(SPUtils.get(getActivity(), "wendu", "") + "℃");
        mFragmentOneSD.setText(SPUtils.get(getActivity(), "shidu", "") + "%");
        mFragmentOneAddress.setText(SPUtils.get(getActivity(), "address", "00") + "");
    }


    private void initListeners() {
    }
}
