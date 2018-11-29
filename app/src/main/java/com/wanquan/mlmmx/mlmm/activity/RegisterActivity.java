package com.wanquan.mlmmx.mlmm.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.RegisterBeans;
import com.wanquan.mlmmx.mlmm.beans.RegisterRequestBeans;
import com.wanquan.mlmmx.mlmm.beans.RegisterSaveBeans;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：注册页面
 * 作者：薛昌峰
 * 时间：2017.09.25
 */
public class RegisterActivity extends BaseActivity {
    private EditText mRegisterPhone;
    private TextView mRegisterGetPswUp;
    private TextView mRegisterGetPswDown;
    private EditText mRegisterCode;
    private EditText mRegisterPsw;
    private EditText mRegisterTjm;
    private ImageView mRegisterCheck;
    private LinearLayout mRegisterll;
    private TextView mRegisterButton;
    private String msg;
    boolean ischeck = true;
    private Set<String> tagSet = new HashSet<>();
    private Boolean recharge_flag = true;
    private String token;


    int i = 120;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                if (i >= 10)
                    mRegisterGetPswDown.setText(i + "s");
                if (i > 0 && i < 10) {
                    mRegisterGetPswDown.setText("0" + i + "s");
                }
            } else if (msg.what == -8) {
                mRegisterGetPswUp.setText("重新发送");
                mRegisterGetPswUp.setClickable(true);//恢复获取验证码按钮可点击
                mRegisterGetPswUp.setVisibility(View.VISIBLE);
                mRegisterGetPswDown.setVisibility(View.INVISIBLE);
//                mRegisterGetPswUp.setBackgroundResource(R.drawable.register_button_bg);//设置可点击时属性
                i = 120;
            } else {
                int event = msg.arg1;
//                Log.e("event", "event=" + event);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(RegisterActivity.this, R.color.top);

//      getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) ;

        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() throws Exception {
        mRegisterPhone = (EditText) findViewById(R.id.Register_Phone);
        mRegisterGetPswUp = (TextView) findViewById(R.id.Register_GetPswUp);
        mRegisterGetPswDown = (TextView) findViewById(R.id.Register_GetPswDown);
        mRegisterCode = (EditText) findViewById(R.id.Register_Code);
        mRegisterPsw = (EditText) findViewById(R.id.Register_Psw);
        mRegisterTjm = (EditText) findViewById(R.id.Register_TJM);
        mRegisterll = (LinearLayout) findViewById(R.id.Register_ll);
        mRegisterCheck = (ImageView) findViewById(R.id.Register_Check);
        mRegisterButton = (TextView) findViewById(R.id.Register_Button);
    }

    private void initData() {

    }

    private void initListeners() {
        mRegisterll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, AgreementActivity.class));
            }
        });
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("register_username", mRegisterPhone.getText().toString().trim());
//                Log.e("register_password", MD5Util.GetMD5Code(mRegisterPsw.getText().toString()).trim());
//                Log.e("register_verifyCode", mRegisterCode.getText().toString().trim());
//                Log.e("register_referee", mRegisterTjm.getText().toString().trim());
                if (mRegisterPhone.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "亲，电话号码不能为空哦~", Toast.LENGTH_SHORT).show();
                } else if (!Verification_Utils.checkMobileNumber(mRegisterPhone.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "亲，请输入正确的电话号码哦~", Toast.LENGTH_SHORT).show();
                } else if (mRegisterCode.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "亲，验证码不能为空哦~", Toast.LENGTH_SHORT).show();
                } else if (mRegisterPsw.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "亲，密码不能为空哦~", Toast.LENGTH_SHORT).show();
                } else if (mRegisterPsw.getText().length() < 6) {
                    Toast.makeText(RegisterActivity.this, "亲，密码为6-16位数字、字母或数字和字母的组合哦~", Toast.LENGTH_SHORT).show();
                } else if (!Verification_Utils.CheckPassword(mRegisterPsw.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "亲，密码为6-16位数字、字母或数字和字母的组合哦~", Toast.LENGTH_SHORT).show();
                } else if (mRegisterPsw.getText().length() > 16) {
                    Toast.makeText(RegisterActivity.this, "亲，密码为6-16位数字、字母或数字和字母的组合哦~", Toast.LENGTH_SHORT).show();
                } else {
                    final Dialog dialog2 = new MyDialog_Views(RegisterActivity.this, R.style.MyDialog);
                    dialog2.setCancelable(false);
                    dialog2.show();
                    MyDialog_Views myDialog_views = new MyDialog_Views(RegisterActivity.this, "正在注册...", "");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mRegisterButton.setClickable(false);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("itfaceId", "008");
                            hashMap.put("username", mRegisterPhone.getText().toString().trim());
                            hashMap.put("password", MD5Util.GetMD5Code(mRegisterPsw.getText().toString()).trim());
                            hashMap.put("verifyCode", mRegisterCode.getText().toString().trim());
                            hashMap.put("referee", mRegisterTjm.getText().toString().trim());
                            JSONObject jsonObject = new JSONObject(hashMap);

                            OkGo.post(UrlContent.URL).tag(this)
                                    .upJson(jsonObject.toString())
                                    .connTimeOut(10_000)
                                    .execute(new CustomCallBackNoLoading<RegisterBeans>(RegisterActivity.this) {
                                        @Override
                                        public void onSuccess(final RegisterBeans registerBeans, Call call, Response response) {
                                            if (registerBeans.getResultCode() == 1) {
                                                dialog2.dismiss();

                                                tagSet.add(registerBeans.getData().getMobile());
//                                        Log.e("mars1", registerBeans.getData().getMobile());

                                                if (!TextUtils.isEmpty(registerBeans.getData().getMobile()) && tagSet.size() != 0) {
                                                    JPushInterface.setAliasAndTags(RegisterActivity.this, registerBeans.getData().getMobile(), tagSet, new TagAliasCallback() {
                                                        @Override
                                                        public void gotResult(int i, String s, Set<String> set) {
//                                                    Log.e("mars2", "Jpush==" + i + "Alias=" + s + "tag=" + tagSet.toArray()[0]);
                                                            switch (i) {
                                                                case 0:
                                                                    initLogin();
                                                                    break;
                                                                case 6012:
                                                                    Toast.makeText(RegisterActivity.this, "请求超时，请重试...", Toast.LENGTH_SHORT).show();
                                                                    break;
                                                                case 6002:
                                                                    Toast.makeText(RegisterActivity.this, "请求超时，请重试...", Toast.LENGTH_SHORT).show();
                                                                    break;
                                                                case 6009:
                                                                    Toast.makeText(RegisterActivity.this, "请求超时，请重试...", Toast.LENGTH_SHORT).show();
                                                                    break;
                                                                case 6011:
                                                                    Toast.makeText(RegisterActivity.this, "请求超时，请重试...", Toast.LENGTH_SHORT).show();
                                                                    break;
                                                            }
                                                        }

                                                        private void initLogin() {
                                                            SpUtil.setBooleanValue(RegisterActivity.this, MyContant.ISLOGIN, true);
                                                            if (registerBeans.getData().getNickName() == null) {
                                                                SPUtils.put(RegisterActivity.this, "username", registerBeans.getData().getUsername());
                                                            } else {
                                                                SPUtils.put(RegisterActivity.this, "username", registerBeans.getData().getNickName());
                                                            }
                                                            token = String.valueOf(registerBeans.getData().getToken());
                                                            Log.e("register_Token", String.valueOf(registerBeans.getData().getToken()));
                                                            Log.e("register_userid", String.valueOf(registerBeans.getData().getId()));

                                                            SPUtils.put(RegisterActivity.this, "token", token);
                                                            SPUtils.put(RegisterActivity.this, "userid", String.valueOf(registerBeans.getData().getId()));
                                                            SPUtils.put(RegisterActivity.this, "isYoumeng", "1");

                                                            initUpLoading(String.valueOf(registerBeans.getData().getToken()), registerBeans.getData().getUsername());
                                                        }
                                                    });
                                                }
                                            } else if (registerBeans.getResultCode() != 1) {
                                                dialog2.dismiss();
//                                                App.ErrorToken(registerBeans.getResultCode(), registerBeans.getMsg());
                                                mRegisterButton.setClickable(true);
                                                Toast.makeText(RegisterActivity.this, registerBeans.getMsg(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }, 1000);
                }
            }
        });

        mRegisterGetPswUp.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (mRegisterPhone.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "亲，电话号码不能为空哦~", Toast.LENGTH_SHORT).show();
                } else if (!Verification_Utils.checkMobileNumber(mRegisterPhone.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "亲，请输入正确的手机号码哦~", Toast.LENGTH_SHORT).show();
                } else {
                    mRegisterGetPswUp.setClickable(false);
                    //获取验证码
                    getCode();
                }
            }
        });
    }

    private void initWork(final String token) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "033");
        hashMap.put("token", token);
//        hashMap.put("id", );
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<SettingBabyMessageBeans>(RegisterActivity.this) {
                    @Override
                    public void onSuccess(SettingBabyMessageBeans mSettingBabyMessageBeans, Call call, Response response) {
                        if (mSettingBabyMessageBeans.getResultCode() == 1) {
//                            Log.e("fffffffffff11", String.valueOf(mSettingBabyMessageBeans.getData().getBabyStatus()));
//                            Log.e("fffffffffff22", String.valueOf(mSettingBabyMessageBeans.getData().getBabySex()));
//                            Log.e("fffffffffff33", String.valueOf(mSettingBabyMessageBeans.getData().getPreChildDate()));
//                            Log.e("fffffffffff44", String.valueOf(mSettingBabyMessageBeans.getData().getChildBirthDate()));
                            mRegisterButton.setClickable(true);
                            Toast.makeText(RegisterActivity.this, "亲，注册成功啦~", Toast.LENGTH_SHORT).show();
                            SPUtils.put(RegisterActivity.this, "BabyState", String.valueOf(mSettingBabyMessageBeans.getData().getBabyStatus()));
                            SPUtils.put(RegisterActivity.this, "BabySex", String.valueOf(mSettingBabyMessageBeans.getData().getBabySex()));
                            SPUtils.put(RegisterActivity.this, "babyId", String.valueOf(mSettingBabyMessageBeans.getData().getId()));
                            SPUtils.put(RegisterActivity.this, "authority", String.valueOf(mSettingBabyMessageBeans.getData().getAuthority()));
                            SPUtils.put(RegisterActivity.this, "loginName", mRegisterPhone.getText().toString().trim());
                            SPUtils.put(RegisterActivity.this, "loginPassWord", mRegisterPsw.getText().toString().trim());

                            if (mSettingBabyMessageBeans.getData().getBabyStatus() == 1) {
                                SPUtils.put(RegisterActivity.this, "timeh", mSettingBabyMessageBeans.getData().getPreChildDate());
                            } else if (mSettingBabyMessageBeans.getData().getBabyStatus() == 2) {
                                SPUtils.put(RegisterActivity.this, "timey", mSettingBabyMessageBeans.getData().getChildBirthDate());
                            }
                            if (HomeFragment.mediaPlayer != null) {
                                HomeFragment.mediaPlayer.stop();
                                HomeFragment.isPlay = true;
                                HomeFragment.isPlayStart = false;
                            }

                            if (mSettingBabyMessageBeans.getData().getBabyStatus() == 0
                                    || mSettingBabyMessageBeans.getData().getBabyStatus() == 1
                                    || mSettingBabyMessageBeans.getData().getBabyStatus() == 2) {
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(RegisterActivity.this, StateSettingActivity.class);
                                intent.putExtra("isTianJia", "1");
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            mRegisterButton.setClickable(true);
//                            App.ErrorToken(mSettingBabyMessageBeans.getResultCode(), mSettingBabyMessageBeans.getMsg());
                            Toast.makeText(RegisterActivity.this, mSettingBabyMessageBeans.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initUpLoading(final String id, String name) {
        Log.e("fffffffToken", id);
        Log.e("fffffffname", name);
        Log.e("fffffffrelationship", SPUtils.get(RegisterActivity.this, "sort", "") + "");
        Log.e("fffffffstatus", SPUtils.get(RegisterActivity.this, "w_BabyState", "") + "");
        Log.e("fffffffsex", SPUtils.get(RegisterActivity.this, "w_sex", "") + "");
        Log.e("fffffffweight", SPUtils.get(RegisterActivity.this, "w_babytz", "") + "");
//        Log.e("fffffffchild_birth_date", str);
        Log.e("fffffffpre_child_date", SPUtils.get(RegisterActivity.this, "w_timeh", "") + "");
        Log.e("fffffffheight", SPUtils.get(RegisterActivity.this, "w_babysg", "") + "");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "073");
        hashMap.put("token", id);
        hashMap.put("nickName", SPUtils.get(RegisterActivity.this, "w_setbabyname", ""));
        hashMap.put("relationship", SPUtils.get(RegisterActivity.this, "sort", ""));
        hashMap.put("status", SPUtils.get(RegisterActivity.this, "w_BabyState", ""));
        if (!SPUtils.get(RegisterActivity.this, "w_sex", "").equals("")) {
            hashMap.put("sex", SPUtils.get(RegisterActivity.this, "w_sex", ""));
        }
        if (!SPUtils.get(RegisterActivity.this, "w_babytz", "").equals("")) {
            hashMap.put("weight", SPUtils.get(RegisterActivity.this, "w_babytz", ""));
        }
        if (!SPUtils.get(RegisterActivity.this, "w_timey", "").equals("")) {
            String str = SPUtils.get(RegisterActivity.this, "w_timey", "").toString();
            if (!str.contains(" ")) {
                str = str + " 00:00:00";
            }
            hashMap.put("child_birth_date", str);
        }
        if (!SPUtils.get(RegisterActivity.this, "w_timeh", "").equals("")) {
            hashMap.put("pre_child_date", SPUtils.get(RegisterActivity.this, "w_timeh", ""));
        }
        if (!SPUtils.get(RegisterActivity.this, "w_babysg", "").equals("")) {
            hashMap.put("height", SPUtils.get(RegisterActivity.this, "w_babysg", ""));
        }
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<RegisterSaveBeans>(RegisterActivity.this) {
                    @Override
                    public void onSuccess(RegisterSaveBeans mRegisterSaveBeans, Call call, Response response) {
                        if (mRegisterSaveBeans.getResultCode() == 1) {
                            initWork(token);
//                            Toast.makeText(RegisterActivity.this, "上传数据成功", Toast.LENGTH_SHORT).show();
//                            finish();
                        } else {
                            mRegisterButton.setClickable(true);

//                            App.ErrorToken(mRegisterSaveBeans.getResultCode(), mRegisterSaveBeans.getMsg());
                            Toast.makeText(RegisterActivity.this, mRegisterSaveBeans.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getCode() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "010");
        hashMap.put("username", mRegisterPhone.getText().toString().trim());
        hashMap.put("type", "1");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<RegisterRequestBeans>(this) {
                    @Override
                    public void onSuccess(RegisterRequestBeans registerRequestBeans, Call call, Response response) {
                        if (registerRequestBeans.getResultCode() == 1) {
                            mRegisterGetPswUp.setClickable(true);
                            Toast.makeText(RegisterActivity.this, "亲，快去短信查收验证码啦~ ", Toast.LENGTH_SHORT).show();
                            mRegisterGetPswUp.setVisibility(View.INVISIBLE);
                            mRegisterGetPswDown.setVisibility(View.VISIBLE);
                            mRegisterGetPswDown.setClickable(false);//设置获取验证码按钮不可点击
                            mRegisterGetPswDown.setText(i + "s后获取");
//                          mRegisterGetPswDown.setBackgroundResource(R.drawable.resend_verification_code);//设置不可点击时属性
                            mRegisterGetPswDown.setTextColor(getResources().getColor(R.color.dialog_background));
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    for (; i > 0; i--) {
                                        handler.sendEmptyMessage(-9);
                                        if (i <= 0) {
                                            break;
                                        }
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    handler.sendEmptyMessage(-8);
                                }
                            }).start();
                        } else if (registerRequestBeans.getResultCode() != 1) {
                            mRegisterGetPswUp.setClickable(true);

//                            App.ErrorToken(registerRequestBeans.getResultCode(), registerRequestBeans.getMsg());
                            Toast.makeText(RegisterActivity.this, registerRequestBeans.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mRegisterGetPswUp.setClickable(true);

                        Log.e("11", e.getMessage());
                    }
                });
    }

    public void RegisterActivity_Bank(View view) {
        finish();
    }
}
