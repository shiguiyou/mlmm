package com.wanquan.mlmmx.mlmm.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.StrainerInformationAdapter;
import com.wanquan.mlmmx.mlmm.beans.BabyPhonesDeleteBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.StrainerInformationBeans;
import com.wanquan.mlmmx.mlmm.beans.StrainerInformationCloseBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：滤网信息
 * 作者：薛昌峰
 * 时间：2017.10.20
 */
public class StrainerInformationActivity extends BaseActivity {
    private ListView mStrainerInformationListView;
    StrainerInformationAdapter mStrainerInformationAdapter;
    TextView mStrainerInformationTV;
    List<StrainerInformationBeans.DataBean> mList = new ArrayList<>();
    private String id;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(StrainerInformationActivity.this, R.color.black);

        id = getIntent().getStringExtra("id");
        code = getIntent().getStringExtra("code");

        initData();
        initListeners();


    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_strainer_information;
    }

    @Override
    public void initView() throws Exception {
        mStrainerInformationListView = (ListView) findViewById(R.id.StrainerInformation_ListView);
        mStrainerInformationTV = (TextView) findViewById(R.id.StrainerInformation_TV);
    }

    private void initData() {
        Log.e("ffff", id + "");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "025");
        hashMap.put("token", SPUtils.get(StrainerInformationActivity.this, "token", ""));
        hashMap.put("deviceId", id);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<StrainerInformationBeans>(this) {
                    @Override
                    public void onSuccess(StrainerInformationBeans mStrainerInformationBeans, Call call, Response response) {
                        if (mStrainerInformationBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mStrainerInformationBeans.getData());
                            mStrainerInformationAdapter = new StrainerInformationAdapter(StrainerInformationActivity.this, mList);
                            mStrainerInformationListView.setAdapter(mStrainerInformationAdapter);
                            mStrainerInformationAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mStrainerInformationBeans.getResultCode(), mStrainerInformationBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
        mStrainerInformationTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alert;
                alert = new AlertDialog.Builder(StrainerInformationActivity.this).create();
                alert.show();
                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.delete_dialogs);
                alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView cart_delete_title = alert.getWindow().findViewById(R.id.cart_delete_title);
                TextView cart_delete_content = alert.getWindow().findViewById(R.id.cart_delete_content);
                TextView cart_delete_cancle = alert.getWindow().findViewById(R.id.cart_delete_cancle);
                TextView cart_delete_confirm = alert.getWindow().findViewById(R.id.cart_delete_confirm);

                cart_delete_title.setText("温馨提示");
                cart_delete_content.setText("您确定要恢复吗？这样滤网信息就会恢复成初始状态哦~");
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
                        hashMap.put("itfaceId", "005");
                        hashMap.put("deviceCode", code);
                        hashMap.put("ctlType", "98");
                        hashMap.put("token", SPUtils.get(StrainerInformationActivity.this, "token", ""));
                        hashMap.put("ctlStatus", "00,45");
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<StrainerInformationCloseBeans>(StrainerInformationActivity.this) {
                                    @Override
                                    public void onSuccess(StrainerInformationCloseBeans mStrainerInformationCloseBeans, Call call, Response response) {
                                        if (mStrainerInformationCloseBeans.getResultCode() == 1) {
                                            initData();
                                            Toast toast = Toast.makeText(StrainerInformationActivity.this, "复位成功", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();
                                            alert.dismiss();
                                        } else {
                                            App.ErrorToken(mStrainerInformationCloseBeans.getResultCode(), mStrainerInformationCloseBeans.getMsg());

                                        }
                                    }
                                });
                    }
                });

            }
        });
    }

    public void StrainerInformationActivity_Bank(View view) {
        finish();
    }
}
