package com.wanquan.mlmmx.mlmm.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.activity.BaseActivity;
import com.wanquan.mlmmx.mlmm.activity.LoginActivity;
import com.wanquan.mlmmx.mlmm.beans.LoginBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/13.
 */
public abstract class BaseFragment extends Fragment {
    protected BaseActivity mActivity;

//    protected abstract int setView();
//
//    protected abstract void init(View view);
//
//    protected abstract void initData(Bundle savedInstanceState);

    private void initToken() {
        if (SpUtil.getBooleanValue(getActivity(), MyContant.ISLOGIN, true)) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("itfaceId", "001");
            hashMap.put("username", SPUtils.get(getContext(), "loginName", ""));
            hashMap.put("password", SPUtils.get(getContext(), "loginPassWord", ""));
            JSONObject jsonObject = new JSONObject(hashMap);

            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<LoginBeans>() {
                        @Override
                        public void onSuccess(LoginBeans loginBeans, Call call, Response response) {
                            if (loginBeans.getResultCode() == 1) {
                                Log.e("重新获取token_f", "成功");
                                initTokes(loginBeans.getData().getToken());
                            } else {
                                Log.e("重新获取token_f", "不成功");
                                startActivity(new Intent(getContext(), LoginActivity.class));
                            }
                        }
                    });
        }
    }

    private void initTokes(String str) {
        SPUtils.put(getContext(), "token", str);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initToken();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(setView(), container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        init(view);
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        initData(savedInstanceState);
//    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
