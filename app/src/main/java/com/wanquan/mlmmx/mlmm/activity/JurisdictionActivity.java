package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.JurisdictionBeans;
import com.wanquan.mlmmx.mlmm.beans.ParentMessageBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：权限设置
 * 作者：薛昌峰
 * 时间：2018.03.01
 */
public class JurisdictionActivity extends BaseActivity {
    private TextView mJurisdictionSave;
    private LinearLayout mJurisdictionLL1;
    private ImageView mJurisdictionImg1;
    private LinearLayout mJurisdictionLL2;
    private ImageView mJurisdictionImg2;
    private int jurisdiction;
    private String id;
    private String authority;
    private int babyid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(JurisdictionActivity.this, R.color.black);

        id = getIntent().getStringExtra("id");
        babyid = getIntent().getIntExtra("babyid", 0);
        authority = getIntent().getStringExtra("authority");
//        Log.e("dddddddd", authority+"dddd");

        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_jurisdiction;
    }

    @Override
    public void initView() throws Exception {
        mJurisdictionSave = (TextView) findViewById(R.id.Jurisdiction_Save);
        mJurisdictionLL1 = (LinearLayout) findViewById(R.id.Jurisdiction_LL1);
        mJurisdictionImg1 = (ImageView) findViewById(R.id.Jurisdiction_Img1);
        mJurisdictionLL2 = (LinearLayout) findViewById(R.id.Jurisdiction_LL2);
        mJurisdictionImg2 = (ImageView) findViewById(R.id.Jurisdiction_Img2);
    }

    private void initData() {
        if (authority.equals("1")) {
            mJurisdictionImg1.setVisibility(View.VISIBLE);
            mJurisdictionImg2.setVisibility(View.INVISIBLE);
            jurisdiction = 1;
        } else if (authority.equals("0")) {
            mJurisdictionImg1.setVisibility(View.INVISIBLE);
            mJurisdictionImg2.setVisibility(View.VISIBLE);
            jurisdiction = 0;
        }
    }

    private void initListeners() {
        mJurisdictionLL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJurisdictionImg1.setVisibility(View.VISIBLE);
                mJurisdictionImg2.setVisibility(View.INVISIBLE);
                jurisdiction = 1;
                initNetWork();
            }
        });
        mJurisdictionLL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJurisdictionImg1.setVisibility(View.INVISIBLE);
                mJurisdictionImg2.setVisibility(View.VISIBLE);
                jurisdiction = 0;
                initNetWork();
            }
        });
    }

    private void initNetWork() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "077");
        hashMap.put("userId", id);
        hashMap.put("babyId", babyid);
        hashMap.put("token", SPUtils.get(JurisdictionActivity.this, "token", ""));
        hashMap.put("authority", jurisdiction);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<JurisdictionBeans>(JurisdictionActivity.this) {
                    @Override
                    public void onSuccess(JurisdictionBeans mJurisdictionBeans, Call call, Response response) {
                        if (mJurisdictionBeans.getResultCode() == 1) {
                            Toast toast = Toast.makeText(JurisdictionActivity.this, "保存成功！", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            Intent intent = new Intent(JurisdictionActivity.this, ParentMessageActivity.class);
                            intent.putExtra("jurisdiction", String.valueOf(jurisdiction));
                            setResult(1, intent);
                            finish();
                        } else {
                            App.ErrorToken(mJurisdictionBeans.getResultCode(), mJurisdictionBeans.getMsg());

                        }
                    }
                });
    }

    public void ParentMessageActivity_Bank(View view) {
        finish();
    }
}
