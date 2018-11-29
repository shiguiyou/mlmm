package com.wanquan.mlmmx.mlmm.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.LoginBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.StatusBarCompat;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by wangzeya on 16/10/21.
 * <p>
 * 基础的activity
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    public ProgressDialog pd;
    public AlertDialog.Builder dialog;
    private Dialog loading;

//    @Override
//    protected void onResume() {
//        super.onResume();
//        initToken();
//    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(FLAG_, FLAG_FULLSCREEN);
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(BaseActivity.this, R.color.black);

//        initToken();

        if (setLayoutID() != 0) {
            setContentView(setLayoutID());
        } else {
            Log.e(TAG, "没有布局文件");
        }
        try {
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //设置状态栏沉浸模式
        if (this.getClass().getSimpleName().equals("LoginActivity")
                || this.getClass().getSimpleName().equals("SplashActivity")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
        } else {
//            setBar();
        }
    }


    public void initToken() {
        if (SpUtil.getBooleanValue(this, MyContant.ISLOGIN, true)) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("itfaceId", "001");
            hashMap.put("username", SPUtils.get(this, "loginName", ""));
            hashMap.put("password", SPUtils.get(this, "loginPassWord", ""));
            JSONObject jsonObject = new JSONObject(hashMap);

            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<LoginBeans>() {
                        @Override
                        public void onSuccess(LoginBeans loginBeans, Call call, Response response) {
                            if (loginBeans.getResultCode() == 1) {
                                Log.e("重新获取token_a", "成功");
                                initTokes(loginBeans.getData().getToken());
                            } else {
//                            Log.e("重新获取token_f","不成功");
                            initTokes2();
                            }
                        }
                    });
        }
    }

    private void initTokes2() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void initTokes(String str) {
        SPUtils.put(this, "token", str);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setTranslucentStatus(boolean is) {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (is) {
            params.flags |= bits;
        } else {
            params.flags &= ~bits;
        }
        window.setAttributes(params);
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void setStatusBarColor() {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.juse));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void setStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void translucentStatusBar() {
        StatusBarCompat.translucentStatusBar(this);
    }

    protected void setToolBar(Toolbar toolBar, CharSequence title) {
        toolBar.setTitle(title);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
    }

    private void onBackPressedSupport() {
        onBackPressed();
    }


    public void actyLoad() {
    }

    protected abstract int setLayoutID();

    //推荐插件 findviewbyme
    public abstract void initView() throws Exception;


    /**
     * 开启浮动加载进度条
     */
    public void showLoading() {
        if (pd == null) pd = new ProgressDialog(this);
        pd.setMessage("loading");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
    }

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void showLoading(String msg) {

        if (msg == null || msg == "") return;
        if (pd == null) pd = new ProgressDialog(this);
        pd.setMessage(msg);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
    }

    /**
     * 停止浮动加载进度条
     */
    public void hideLoading() {
        if (pd != null) pd.cancel();
    }

    public void showNetError() {
        if (pd != null) pd.cancel();

        if (dialog == null) {
            dialog = new AlertDialog.Builder(this);
            dialog.setMessage(R.string.net_error)
                    .setTitle(R.string.hint)
                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
        }

        dialog.show();
    }


    public void startActy(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public <T extends Serializable> void startActy(String key, Class<?> cls, T t) {
        Intent intent = new Intent(this, cls);
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, t);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
