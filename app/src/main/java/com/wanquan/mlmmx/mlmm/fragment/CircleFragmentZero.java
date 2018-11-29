package com.wanquan.mlmmx.mlmm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.CircleFragmentOneListViewAdapter;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by XCF on 2018/8/24.
 */

public class CircleFragmentZero extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.circlefragmentzero, container, false);
        Log.e("xcfxcfxcfvv","CircleFragmentZero");

//        // 注册
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
        return view;
    }
}
