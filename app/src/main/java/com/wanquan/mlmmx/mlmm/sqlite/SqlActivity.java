package com.wanquan.mlmmx.mlmm.sqlite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.wanquan.mlmmx.mlmm.R;

public class SqlActivity extends AppCompatActivity {

    public static final String SAVE = "save";
    EditText mUserNameEditText, mPassWordEditText;
    CheckBox mCheckBox;
    String mUserNameString, mPassWordString;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adjustIsLogin();
        initViews();
        initListeners();
    }

    private void adjustIsLogin() {
        mSharedPreferences = getSharedPreferences(SAVE, MODE_PRIVATE);
        //取出偏好设置文件中的值,第一个参数，数据的键值，第二个参数表示此键值不存在时候的默认值
        boolean isLoin = mSharedPreferences.getBoolean("islogin", false);
        if (isLoin) {
            //已经记住了用户名和密码，直接跳转到成功界面
            Intent intent = new Intent(SqlActivity.this, SuccessActivity.class);
            mUserNameString = mSharedPreferences.getString("username", "");
            intent.putExtra("name", mUserNameString);
            startActivity(intent);
            finish();
        }
    }

    private void initViews() {
        mUserNameEditText = (EditText) findViewById(R.id.username);
        mPassWordEditText = (EditText) findViewById(R.id.password);
        mCheckBox = (CheckBox) findViewById(R.id.checkbox);
    }

    private void initListeners() {
        /*mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mUserNameString = mUserNameEditText.getText().toString();
                mPassWordString = mPassWordEditText.getText().toString();
                if (isChecked) {
                    //用户选择记住用户名和密码
                    if (mUserNameString.equals("123") && mPassWordString.equals("123")) {
                        //第一个参数：偏好设置文件的名称；第二个参数：文件访问模式
                        mSharedPreferences = getSharedPreferences(SAVE, MODE_PRIVATE);
                        //向偏好设置文件中保存数据
                        mEditor = mSharedPreferences.edit();
                        mEditor.putString("username", mUserNameString);
                        mEditor.putBoolean("islogin", true);
                        //提交保存结果
                        mEditor.commit();
                    }
                } else {
                    //用户取消记住用户名和密码
                }
            }
        });*/
    }

    //记住用户名和密码不再由复选框单击事件触发，而是由登录按钮触发：单击登录按钮时，判断复选框是否被选中
    public void login(View view) {
        mUserNameString = mUserNameEditText.getText().toString();
        mPassWordString = mPassWordEditText.getText().toString();

        //判断用户是否选中了复选框
        if (mCheckBox.isChecked()) {
            //用户选择记住用户名和密码
            if (mUserNameString.equals("123") && mPassWordString.equals("123")) {
                //第一个参数：偏好设置文件的名称；第二个参数：文件访问模式
                mSharedPreferences = getSharedPreferences(SAVE, MODE_PRIVATE);
                //向偏好设置文件中保存数据
                mEditor = mSharedPreferences.edit();
                mEditor.putString("username", mUserNameString);
                mEditor.putBoolean("islogin", true);
                //提交保存结果
                mEditor.commit();
            } else {
                //用户没有选中复选框，需要清空
                //第一个参数：偏好设置文件的名称；第二个参数：文件访问模式
                mSharedPreferences = getSharedPreferences(SAVE, MODE_PRIVATE);
                //向偏好设置文件中保存数据
                mEditor = mSharedPreferences.edit();
                mEditor.putString("username", mUserNameString);
                mEditor.putBoolean("islogin", false);
                //提交保存结果
                mEditor.commit();
            }

        } else {
            //用户没有选中复选框，需要清空
            //第一个参数：偏好设置文件的名称；第二个参数：文件访问模式
            mSharedPreferences = getSharedPreferences(SAVE, MODE_PRIVATE);
            //向偏好设置文件中保存数据
            mEditor = mSharedPreferences.edit();
            mEditor.putString("username", mUserNameString);
            mEditor.putBoolean("islogin", false);
            //提交保存结果
            mEditor.commit();
        }

        if (mUserNameString.equals("123") && mPassWordString.equals("123")) {
            //登录成功
            Intent intent = new Intent(SqlActivity.this, SuccessActivity.class);
            intent.putExtra("name", mUserNameString);
            startActivity(intent);
            finish();
        } else {
            //登录失败：重新输入
            //清空用户名和密码
            mUserNameEditText.setText("");
            mPassWordEditText.setText("");
            //如何把光标移到用户名位置处（用户名输入框获取焦点）
            mUserNameEditText.requestFocus();
        }
    }
}
