package com.wanquan.mlmmx.mlmm.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.RegisterBeans;
import com.wanquan.mlmmx.mlmm.beans.RegisterRequestBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MD5Util;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.utils.Verification_Utils;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：找回密码
 * 作者：薛昌峰
 * 时间：2017.09.27
 */
public class ForgetActivity extends BaseActivity {
    private EditText mForgetPhone;
    private TextView mForgetGetPswUp;
    private TextView mForgetGetPswDown;
    private EditText mForgetCode;
    private EditText mForgetPsw;
    private TextView mForgetButton;

    int i = 120;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                if (i >= 10) {
                    mForgetGetPswDown.setText(i + "s");
                }
                if (i > 0 && i < 10) {
                    mForgetGetPswDown.setText("0" + i + "s");
                }
            } else if (msg.what == -8) {
                mForgetGetPswUp.setText("重新发送");
                mForgetGetPswUp.setClickable(true);//恢复获取验证码按钮可点击
                mForgetGetPswUp.setVisibility(View.VISIBLE);
                mForgetGetPswDown.setVisibility(View.INVISIBLE);
//        mRegisterGetPswUp.setBackgroundResource(R.drawable.register_button_bg);//设置可点击时属性
                i = 120;
            } else {
                int event = msg.arg1;
          /*      int result = msg.arg2;
                Object data = msg.obj;*/
                Log.e("event", "event=" + event);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(ForgetActivity.this, R.color.black);

        initData();
        initListeners();

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_forget;
    }

    @Override
    public void initView() throws Exception {
        mForgetPhone = (EditText) findViewById(R.id.Forget_Phone);
        mForgetGetPswUp = (TextView) findViewById(R.id.Forget_GetPswUp);
        mForgetGetPswDown = (TextView) findViewById(R.id.Forget_GetPswDown);
        mForgetCode = (EditText) findViewById(R.id.Forget_Code);
        mForgetPsw = (EditText) findViewById(R.id.Forget_Psw);
        mForgetButton = (TextView) findViewById(R.id.Forget_Button);
    }

    private void initData() {
    }


    private void initListeners() {
        mForgetGetPswUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mForgetPhone.getText().toString().equals("")) {
                    Toast.makeText(ForgetActivity.this, "亲，电话号码不可以为空哦~", Toast.LENGTH_SHORT).show();
                } else if (!Verification_Utils.checkMobileNumber(mForgetPhone.getText().toString())) {
                    Toast.makeText(ForgetActivity.this, "亲，请输入正确的手机号码哦~", Toast.LENGTH_SHORT).show();
                } else {
                    //获取验证码
                    getCode();
                }
            }
        });

        //注册
        mForgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mForgetPhone.getText().toString().equals("")) {
                    Toast.makeText(ForgetActivity.this, "亲，电话号码不可以为空哦~", Toast.LENGTH_SHORT).show();
                } else if (!Verification_Utils.checkMobileNumber(mForgetPhone.getText().toString())) {
                    Toast.makeText(ForgetActivity.this, "亲，电话号码记错啦~", Toast.LENGTH_SHORT).show();
                } else if (mForgetCode.getText().toString().equals("")) {
                    Toast.makeText(ForgetActivity.this, "亲，验证码不能为空哦~", Toast.LENGTH_SHORT).show();
                } else if (mForgetPsw.getText().toString().equals("")) {
                    Toast.makeText(ForgetActivity.this, "亲，密码不能为空哦~", Toast.LENGTH_SHORT).show();
                } else if (mForgetPsw.getText().length() < 6) {
                    Toast.makeText(ForgetActivity.this, "亲，密码长度不能小于6位哦~", Toast.LENGTH_SHORT).show();
                } else if (!Verification_Utils.CheckPassword(mForgetPsw.getText().toString().trim())) {
                    Toast.makeText(ForgetActivity.this, "亲，密码由字母、数字、下划线组成哦~", Toast.LENGTH_SHORT).show();
                } else if (mForgetPsw.getText().length() > 16) {
                    Toast.makeText(ForgetActivity.this, "亲，密码长度不能大于16位哦~", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("itfaceId", "009");
                    hashMap.put("username", mForgetPhone.getText().toString());
                    hashMap.put("password", MD5Util.GetMD5Code(mForgetPsw.getText().toString()));
                    hashMap.put("verifyCode", mForgetCode.getText().toString());
                    JSONObject jsonObject = new JSONObject(hashMap);

                    OkGo.post(
                            UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<RegisterBeans>(ForgetActivity.this) {
                                @Override
                                public void onSuccess(RegisterBeans registerBeans, Call call, Response response) {
                                    if (registerBeans.getResultCode() == 1) {
                                        Toast.makeText(ForgetActivity.this, "亲，修改成功啦~", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else if (registerBeans.getResultCode() != 1) {
                                        App.ErrorToken(registerBeans.getResultCode(), registerBeans.getMsg());

                                    }
                                }
                            });
                }
            }
        });
    }

    private void getCode() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "010");
        hashMap.put("username", mForgetPhone.getText().toString());
        hashMap.put("type", "2");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(
                UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<RegisterRequestBeans>(this) {
                    @Override
                    public void onSuccess(RegisterRequestBeans registerRequestBeans, Call call, Response response) {
                        if (registerRequestBeans.getResultCode() == 1) {
                            Toast.makeText(ForgetActivity.this, "亲，快去短信查收验证码啦~ ", Toast.LENGTH_SHORT).show();
                            mForgetGetPswUp.setVisibility(View.INVISIBLE);
                            mForgetGetPswDown.setVisibility(View.VISIBLE);
                            mForgetGetPswDown.setClickable(false);//设置获取验证码按钮不可点击

                            mForgetGetPswDown.setText(i + "s后获取");
//            mRegisterGetPswDown.setBackgroundResource(R.drawable.resend_verification_code);//设置不可点击时属性
                            mForgetGetPswDown.setTextColor(getResources().getColor(R.color.dialog_background));
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
                            App.ErrorToken(registerRequestBeans.getResultCode(), registerRequestBeans.getMsg());

                        }
                    }
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
////                        Log.i("11", e.getMessage());
//                    }
                });
    }

    public void ForgetActivity_Bank(View view) {
        finish();
    }
}
