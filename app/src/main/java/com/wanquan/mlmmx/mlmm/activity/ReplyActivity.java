package com.wanquan.mlmmx.mlmm.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.CircleDetailsFaSongBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：回复
 * 作者：薛昌峰
 * 时间：2017.12.11
 */
public class ReplyActivity extends BaseActivity {
    private TextView mReplyActivitySave;
    private EditText mReplyActivityEditText;
    private TextView mReplyActivityName;
    private String id;
    private String ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(ReplyActivity.this, R.color.tops);

        id = getIntent().getStringExtra("id");
        ids = String.valueOf(getIntent().getIntExtra("ids", 0));
        String name = getIntent().getStringExtra("name");
        mReplyActivityName.setText("回复" + name);
        initData();
        initListeners();

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_reply;
    }

    @Override
    public void initView() throws Exception {
        mReplyActivitySave = (TextView) findViewById(R.id.ReplyActivity_Save);
        mReplyActivityEditText = (EditText) findViewById(R.id.ReplyActivity_EditText);
        mReplyActivityName = (TextView) findViewById(R.id.ReplyActivity_name);
    }

    private void initData() {

    }

    private void initListeners() {
        mReplyActivitySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mReplyActivityEditText.getText().toString().trim().equals("")) {
                    Toast toast = Toast.makeText(ReplyActivity.this, "请输入要回复的内容哦", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    HashMap<String, Object> hashMap = new HashMap<>();
//                    hashMap.put("itfaceId", "052");
                    hashMap.put("itfaceId", "134");
                    hashMap.put("forumId", id);
                    hashMap.put("token", SPUtils.get(ReplyActivity.this, "token", ""));
                    hashMap.put("content", mReplyActivityEditText.getText().toString().trim());
                    hashMap.put("pid", ids);
                    hashMap.put("fid", ids);
                    JSONObject jsonObject = new JSONObject(hashMap);

                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<CircleDetailsFaSongBeans>(ReplyActivity.this) {
                                @Override
                                public void onSuccess(CircleDetailsFaSongBeans mCircleDetailsFaSongBeans, Call call, Response response) {
                                    if (mCircleDetailsFaSongBeans.getResultCode() == 1) {
                                        if (mCircleDetailsFaSongBeans.getData().getGetIntegral() != 0) {
                                            Toast toast = Toast.makeText(ReplyActivity.this, "回帖成功积分+" + mCircleDetailsFaSongBeans.getData().getGetIntegral(), Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();
                                        }
//                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                                        finish();
                                    } else {
                                        App.ErrorToken(mCircleDetailsFaSongBeans.getResultCode(), mCircleDetailsFaSongBeans.getMsg());

                                    }
                                }
                            });
                }
            }
        });
    }

    public void ReplyActivity_Bank(View view) {
        finish();
    }
}
