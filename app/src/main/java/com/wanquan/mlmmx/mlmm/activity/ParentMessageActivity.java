package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.ParentMessageBeans;
import com.wanquan.mlmmx.mlmm.beans.RelativesListViewBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：亲人信息
 * 作者：薛昌峰
 * 时间：2018.01.30
 */
public class ParentMessageActivity extends BaseActivity {
    private TextView mParentMessageKan;
    private TextView mParentMessageName;
    private TextView mParentMessageNickName;
    private RelativeLayout mParentMessageRL;
    private TextView mParentMessageTV;
    private int babyid;
    private String relationship;
    private String name;
    private String authority;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        babyid = getIntent().getIntExtra("babyid", 0);
        relationship = getIntent().getStringExtra("relationship");
        id = getIntent().getStringExtra("id");
        authority = getIntent().getStringExtra("authority");
        name = getIntent().getStringExtra("name");
//        Log.e("sadasdas", id);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(ParentMessageActivity.this, R.color.black);

        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_parent_message;
    }

    @Override
    public void initView() throws Exception {
        mParentMessageKan = (TextView) findViewById(R.id.ParentMessage_Kan);
        mParentMessageName = (TextView) findViewById(R.id.ParentMessage_Name);
        mParentMessageNickName = (TextView) findViewById(R.id.ParentMessage_NickName);
        mParentMessageRL = (RelativeLayout) findViewById(R.id.ParentMessage_RL);
        mParentMessageTV = (TextView) findViewById(R.id.ParentMessage_TV);
    }

    private void initData() {
        if (relationship != null) {
            mParentMessageName.setText(relationship);
        }
        if (name != null) {
            mParentMessageNickName.setText(name);
        }
        if (authority.equals("0")) {
            mParentMessageKan.setText("查看");
        } else if (authority.equals("1")) {
            mParentMessageKan.setText("管理员");
        }
    }

    private void initListeners() {
        mParentMessageRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentMessageActivity.this, JurisdictionActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("babyid", babyid);
                intent.putExtra("authority", authority);
                startActivityForResult(intent, 0);
            }
        });
        mParentMessageTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alert;
                alert = new AlertDialog.Builder(ParentMessageActivity.this, R.style.AlertDialogs).create();
                alert.show();
                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.delete_dialogs);
                TextView cart_delete_title = alert.getWindow().findViewById(R.id.cart_delete_title);
                TextView cart_delete_content = alert.getWindow().findViewById(R.id.cart_delete_content);
                TextView cart_delete_cancle = alert.getWindow().findViewById(R.id.cart_delete_cancle);
                TextView cart_delete_confirm = alert.getWindow().findViewById(R.id.cart_delete_confirm);

                cart_delete_title.setText("温馨提示");
                cart_delete_content.setText("您确定要删除吗？这样对方就不能访问宝宝的相册了");
                cart_delete_cancle.setText("取消");
                cart_delete_confirm.setText("确定");
                //取消
                alert.getWindow().findViewById(R.id.cart_delete_cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                        return;
                    }
                });

                alert.getWindow().findViewById(R.id.cart_delete_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("itfaceId", "080");
                        hashMap.put("userId", id);
                        hashMap.put("babyId", babyid);
                        hashMap.put("token", SPUtils.get(ParentMessageActivity.this, "token", ""));
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<ParentMessageBeans>(ParentMessageActivity.this) {
                                    @Override
                                    public void onSuccess(ParentMessageBeans mParentMessageBeans, Call call, Response response) {
                                        if (mParentMessageBeans.getResultCode() == 1) {
                                            Toast toast = Toast.makeText(ParentMessageActivity.this, "删除成功！", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();
                                            finish();
                                        } else {
                                            App.ErrorToken(mParentMessageBeans.getResultCode(), mParentMessageBeans.getMsg());

                                        }
                                    }
                                });
                        alert.dismiss();
                        return;
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 1) {
            if (data.getStringExtra("jurisdiction").equals("1")) {
                mParentMessageKan.setText("管理员");
                authority = "1";
            } else if (data.getStringExtra("jurisdiction").equals("0")) {
                mParentMessageKan.setText("查看");
                authority = "0";
            }
        }
    }

    public void ParentMessageActivity_Bank(View view) {
        finish();
    }
}
