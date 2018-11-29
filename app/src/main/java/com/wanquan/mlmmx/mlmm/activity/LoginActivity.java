package com.wanquan.mlmmx.mlmm.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.LoginBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.SettingBabyMessageBeans;
import com.wanquan.mlmmx.mlmm.fragment.HomeFragment;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MD5Util;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.utils.Verification_Utils;
import com.wanquan.mlmmx.mlmm.view.MyDialog_Views;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import okhttp3.Response;

import static com.wanquan.mlmmx.mlmm.R.id.Login_Forget_Psw;
import static com.wanquan.mlmmx.mlmm.R.id.Login_Login;
import static com.wanquan.mlmmx.mlmm.R.id.Login_Phone;
import static com.wanquan.mlmmx.mlmm.R.id.Login_Psw;
import static com.wanquan.mlmmx.mlmm.R.id.Login_Register;

/**
 * 描述：登录界面
 * 作者：薛昌峰
 * 时间：2017.09.25
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImageView;
    private EditText mLoginPhone;
    private EditText mLoginPsw;
    private TextView mLoginForgetPsw;
    private TextView mLoginLogin;
    private TextView mLoginRegister;
    private Set<String> tagSet = new HashSet<>();
    private String userId;
    private String flag;
    private Boolean recharge_flag = true;
    private String fanhui;
    private String name;
    private String url;
    private String title;
    private MessageEvent messageEvent;
    private Dialog dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////            getWindow().setNavigationBarColor(Color.parseColor("#1bb5d7"));
//            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
//            //getWindow().setNavigationBarColor(Color.BLUE);
//        }
        boolean isTranslucentStatus = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
            window.setNavigationBarColor(Color.BLACK);
            isTranslucentStatus = true;
        }

//        Log.e("babyId", String.valueOf(SPUtils.get(this, "babyId", "")));
//        Log.e("babyIduserid_s", String.valueOf(SPUtils.get(this, "userid_s", "")));

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(LoginActivity.this, R.color.top);

        fanhui = getIntent().getStringExtra("fanhui");//分享返回用,未登录跳转用
        //分享返回用
        name = getIntent().getStringExtra("name");
        //分享返回用
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");


        messageEvent = new MessageEvent();

        if (SpUtil.getBooleanValue(LoginActivity.this, MyContant.ISLOGIN, true)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        initData();
        initListeners();

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() throws Exception {
        mImageView = (ImageView) findViewById(R.id.imageView);
        mLoginPhone = (EditText) findViewById(Login_Phone);
        mLoginPsw = (EditText) findViewById(Login_Psw);
        mLoginForgetPsw = (TextView) findViewById(Login_Forget_Psw);
        mLoginLogin = (TextView) findViewById(Login_Login);
        mLoginRegister = (TextView) findViewById(Login_Register);
    }

    private void initData() {
        mImageView.setOnClickListener(this);
        mLoginPhone.setOnClickListener(this);
        mLoginPsw.setOnClickListener(this);
        mLoginForgetPsw.setOnClickListener(this);
        mLoginLogin.setOnClickListener(this);
        mLoginRegister.setOnClickListener(this);
    }

    private void initListeners() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case Login_Phone:
                break;
            case Login_Psw:
                break;
            case Login_Forget_Psw:
                startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
                break;
            case Login_Login:
                if (mLoginPhone.getText().toString().equals("")) {
                    Toast.makeText(this, "亲，不输入手机号是见不到我的全貌哒~", Toast.LENGTH_SHORT).show();
                } else if (!Verification_Utils.checkMobileNumber(mLoginPhone.getText().toString())) {
                    Toast.makeText(this, "亲，请输入正确的手机号码哦~", Toast.LENGTH_SHORT).show();
                } else if (mLoginPsw.getText().toString().equals("")) {
                    Toast.makeText(this, "亲，没有密码是不能登录的哦~", Toast.LENGTH_SHORT).show();
                } else {
                    dialog2 = new MyDialog_Views(LoginActivity.this, R.style.MyDialog);
                    dialog2.setCancelable(true);
                    dialog2.show();
                    MyDialog_Views myDialog_views = new MyDialog_Views(LoginActivity.this, "正在登录...", "");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            Log.e("2222222222222", MD5Util.GetMD5Code(mLoginPsw.getText().toString()));
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("itfaceId", "001");
                            hashMap.put("username", mLoginPhone.getText().toString().trim());
                            hashMap.put("password", MD5Util.GetMD5Code(mLoginPsw.getText().toString().trim()));
                            JSONObject jsonObject = new JSONObject(hashMap);

                            Log.e("fffffff", mLoginPhone.getText().toString());
                            Log.e("fffffff", MD5Util.GetMD5Code(mLoginPsw.getText().toString()));

                            OkGo.post(UrlContent.URL).tag(this)
                                    .upJson(jsonObject.toString())
                                    .connTimeOut(10_000)
                                    .execute(new CustomCallBackNoLoading<LoginBeans>(LoginActivity.this) {
                                        @Override
                                        public void onSuccess(final LoginBeans loginBeans, Call call, Response response) {
                                            if (loginBeans.getResultCode() == 1) {
                                                recharge_flag = true;
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        tagSet.add(loginBeans.getData().getMobile());
//                                                        initLogin();
                                                        Log.e("mars1", loginBeans.getData().getMobile());
                                                        if (!TextUtils.isEmpty(loginBeans.getData().getMobile()) && tagSet.size() != 0) {
                                                            JPushInterface.setAliasAndTags(LoginActivity.this, loginBeans.getData().getMobile(), tagSet, new TagAliasCallback() {
                                                                @Override
                                                                public void gotResult(int i, String s, Set<String> set) {
                                                                    Log.e("mars2", "Jpush==" + i + "Alias=" + s + "tag=" + tagSet.toArray()[0]);
                                                                    switch (i) {
                                                                        case 0:
                                                                            initLogin();
                                                                            break;
                                                                        case 6012:
                                                                            Toast.makeText(LoginActivity.this, "请求超时，请重试...", Toast.LENGTH_SHORT).show();
                                                                            break;
                                                                        case 6002:
                                                                            Toast.makeText(LoginActivity.this, "请求超时，请重试...", Toast.LENGTH_SHORT).show();
                                                                            break;
                                                                        case 6009:
                                                                            Toast.makeText(LoginActivity.this, "请求超时，请重试...", Toast.LENGTH_SHORT).show();
                                                                            break;
                                                                        case 6011:
                                                                            Toast.makeText(LoginActivity.this, "请求超时，请重试...", Toast.LENGTH_SHORT).show();
                                                                            break;
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }

                                                    private void initLogin() {
                                                        SpUtil.setBooleanValue(LoginActivity.this, MyContant.ISLOGIN, true);
//                                                                    if (loginBeans.getData().getNickName() == null) {
                                                        SPUtils.put(LoginActivity.this, "username", loginBeans.getData().getUsername());
//                                                                    } else {
                                                        SPUtils.put(LoginActivity.this, "nickname", loginBeans.getData().getNickName());
//                                                                    }
                                                        userId = String.valueOf(loginBeans.getData().getOwnerUserId());

                                                        initWork(loginBeans.getData().getToken(), userId);

                                                        SPUtils.put(LoginActivity.this, "userid", String.valueOf(loginBeans.getData().getOwnerUserId()));
//                                                        Log.e("mars_userId", String.valueOf(SPUtils.get(LoginActivity.this, "userid", "")));


                                                        SPUtils.put(LoginActivity.this, "token", loginBeans.getData().getToken());
                                                        SPUtils.put(LoginActivity.this, "isYoumeng", "1");
                                                    }
                                                }, 1000);
                                            } else {
                                                recharge_flag = false;
                                                dialog2.dismiss();
                                                App.ErrorToken(loginBeans.getResultCode(), loginBeans.getMsg());
                                            }
                                        }

                                        @Override
                                        public void onError(Call call, Response response, Exception e) {
                                            super.onError(call, response, e);
                                            Toast.makeText(LoginActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                                        }
                                    });

//                            if (recharge_flag) {
//                                MyDialog_Views myDialog_views = new MyDialog_Views(LoginActivity.this, "登录成功", "success");
//                            } else {
//                                MyDialog_Views myDialog_views = new MyDialog_Views(LoginActivity.this, "登录失败", "fail");
//                            }
                        }
                    }, 1000);
                }
                break;
            case Login_Register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }

    private void initWork(final String token, final String userId) {
        Log.e("xcf", token);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "033");
        hashMap.put("token", token);

//        Log.e("babyId2", String.valueOf(SPUtils.get(this, "babyId", "")));

        //判断userId是否相等，为了退出账号后再次进入显示退出之前的宝宝
        if (String.valueOf(SPUtils.get(this, "userid_s", "")).equals(userId)) {
            hashMap.put("id", SPUtils.get(LoginActivity.this, "babyId", ""));
        }

        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<SettingBabyMessageBeans>(LoginActivity.this) {
                    @Override
                    public void onSuccess(SettingBabyMessageBeans mSettingBabyMessageBeans, Call call, Response response) {
                        if (mSettingBabyMessageBeans.getResultCode() == 1) {
//                            Log.e("fffffffffff11", String.valueOf(mSettingBabyMessageBeans.getData().getBabyStatus()));
//                            Log.e("fffffffffff22", String.valueOf(mSettingBabyMessageBeans.getData().getBabySex()));
//                            Log.e("fffffffffff33", String.valueOf(mSettingBabyMessageBeans.getData().getPreChildDate()));
//                            Log.e("fffffffffff44", String.valueOf(mSettingBabyMessageBeans.getData().getChildBirthDate()));
                            SPUtils.put(LoginActivity.this, "BabyState", String.valueOf(mSettingBabyMessageBeans.getData().getBabyStatus()));
                            SPUtils.put(LoginActivity.this, "BabySex", String.valueOf(mSettingBabyMessageBeans.getData().getBabySex()));

                            Log.e("login__宝宝id", String.valueOf(SPUtils.get(LoginActivity.this, "babyId", "")) + "xcf");
                            Log.e("login__宝宝id", String.valueOf(userId) + "xcf");

                            //判断是否相等，为了退出账号后再次进入显示退出之前的宝宝
                            if (!SPUtils.get(LoginActivity.this, "userid_s", "").equals(userId)) {
                                SPUtils.put(LoginActivity.this, "babyId", String.valueOf(mSettingBabyMessageBeans.getData().getId()));
                            } else {
                                SPUtils.put(LoginActivity.this, "babyId", String.valueOf(SPUtils.get(LoginActivity.this, "babyId", "")));
                            }
                            SPUtils.put(LoginActivity.this, "loginName", mLoginPhone.getText().toString());
                            SPUtils.put(LoginActivity.this, "loginPassWord", MD5Util.GetMD5Code(mLoginPsw.getText().toString()));
                            SPUtils.put(LoginActivity.this, "babyNickname", mSettingBabyMessageBeans.getData().getBabyNickname());

                            SPUtils.put(LoginActivity.this, "authority", String.valueOf(mSettingBabyMessageBeans.getData().getAuthority()));
                            if (mSettingBabyMessageBeans.getData().getBabyStatus() == 1) {
                                SPUtils.put(LoginActivity.this, "timeh", mSettingBabyMessageBeans.getData().getPreChildDate());
                            } else if (mSettingBabyMessageBeans.getData().getBabyStatus() == 2) {
                                SPUtils.put(LoginActivity.this, "timey", mSettingBabyMessageBeans.getData().getChildBirthDate());
                            }
                            if (HomeFragment.mediaPlayer != null) {
                                HomeFragment.mediaPlayer.stop();
                                HomeFragment.isPlay = true;
                                HomeFragment.isPlayStart = false;
                            }
                            //通知圈子刷新
//                            MessageEvent messageEvent = new MessageEvent();
//                            messageEvent.setShuaXin(true);
//                            EventBus.getDefault().post(messageEvent);

                            if (mSettingBabyMessageBeans.getData().getBabyStatus() == 0
                                    || mSettingBabyMessageBeans.getData().getBabyStatus() == 1
                                    || mSettingBabyMessageBeans.getData().getBabyStatus() == 2) {
                                if ("1".equals(fanhui)) {
                                    Intent intent = new Intent(LoginActivity.this, SelectCircleActivity.class);
                                    intent.putExtra("title", title);
                                    intent.putExtra("url", url);
                                    intent.putExtra("flag", "1");
                                    startActivity(intent);
                                    finish();
                                } else if ("2".equals(fanhui)) {
                                    Intent intent = new Intent(LoginActivity.this, SelectCircleActivity.class);
                                    intent.putExtra("title", name);
                                    intent.putExtra("url", url);
                                    intent.putExtra("flag", "1");
                                    startActivity(intent);
                                    finish();
                                } else if ("3".equals(fanhui)) {
                                    Log.e(":dsdsasdada", "到了1");

                                    SPUtils.put(LoginActivity.this, "hongdianShow1", "false");
                                    startActivity(new Intent(LoginActivity.this, MessageCenterActivity.class));
                                    finish();
                                } else {
                                    messageEvent.setFinish(4);
                                    EventBus.getDefault().post(messageEvent);

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Intent intent = new Intent(LoginActivity.this, StateSettingActivity.class);
                                intent.putExtra("isTianJia", "1");
                                startActivity(intent);
                                finish();
                            }
                            dialog2.dismiss();
                        } else {
                            App.ErrorToken(mSettingBabyMessageBeans.getResultCode(), mSettingBabyMessageBeans.getMsg());
                        }
                    }
                });
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            finish();
//            System.exit(0);
////            MessageEvent messageEvent = new MessageEvent();
////            messageEvent.setFlag("1");
////
////            Intent intent = new Intent(LoginActivity.this, PlayMainActivity.class);
////            intent.putExtra("flag","flag");
////            startActivity(intent);
////            finish();
//            return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void LoginActivity_Bank(View view) {
        finish();
    }
}
