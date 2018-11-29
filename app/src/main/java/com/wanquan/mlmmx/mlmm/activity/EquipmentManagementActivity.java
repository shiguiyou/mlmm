package com.wanquan.mlmmx.mlmm.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.EquipmentManagementAdapter;
import com.wanquan.mlmmx.mlmm.beans.EquipmentAddBeans;
import com.wanquan.mlmmx.mlmm.beans.EquipmentManagementBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.utils.Verification_Utils;
import com.wanquan.mlmmx.mlmm.view.MyListView;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：共享设备管理
 * 作者：薛昌峰
 * 时间：2017.09.28
 */
public class EquipmentManagementActivity extends BaseActivity implements EquipmentManagementAdapter.AddNotific {
    private TextView mEquipmentManagementName;
    private TextView mEquipmentManagementNumber;
    private MyListView mEquipmentManagementListView;
    private TextView mEquipmentManagementTextView;

    EquipmentManagementAdapter mEquipmentManagementAdapter;
    List<EquipmentManagementBeans.DataBean> mList = new ArrayList<>();

    String name;
    String code;
    int id;
    private EditText content = null;
    private AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name = getIntent().getStringExtra("name");
        code = getIntent().getStringExtra("code");
        id = getIntent().getIntExtra("id", 0);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(EquipmentManagementActivity.this, R.color.black);

        initData();
        initListeners();

        mEquipmentManagementAdapter = new EquipmentManagementAdapter(mList, this, id);
        mEquipmentManagementListView.setAdapter(mEquipmentManagementAdapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_equipmentmanagement;
    }

    @Override
    public void initView() throws Exception {
        mEquipmentManagementName = (TextView) findViewById(R.id.EquipmentManagement_Name);
        mEquipmentManagementNumber = (TextView) findViewById(R.id.EquipmentManagement_Number);
        mEquipmentManagementListView = (MyListView) findViewById(R.id.EquipmentManagement_ListView);
        mEquipmentManagementTextView = (TextView) findViewById(R.id.EquipmentManagement_TextView);
    }

    private void initData() {
        mEquipmentManagementName.setText(name);
        mEquipmentManagementNumber.setText("设备编号：" + code);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "016");
        hashMap.put("token", SPUtils.get(this, "token", ""));
        hashMap.put("ownerUserId", String.valueOf(SPUtils.get(this, "userid", "")));
        hashMap.put("deviceId", String.valueOf(id));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<EquipmentManagementBeans>(this) {
                    @Override
                    public void onSuccess(EquipmentManagementBeans mEquipmentManagementBeans, Call call, Response response) {
                        if (mEquipmentManagementBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mEquipmentManagementBeans.getData());
                            mEquipmentManagementAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mEquipmentManagementBeans.getResultCode(), mEquipmentManagementBeans.getMsg());

                        }
                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        Toast.makeText(EquipmentManagementActivity.this, "服务器连接异常，请稍后重试", Toast.LENGTH_SHORT).show();
//                        super.onError(call, response, e);
//                    }
                });
    }

    private void initListeners() {
        mEquipmentManagementTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert = new AlertDialog.Builder(EquipmentManagementActivity.this).create();
                alert.show();
                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.delete_dialog);

                alert.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                TextView title = alert.getWindow().findViewById(R.id.cart_delete_title);
                content = (EditText) alert.getWindow().findViewById(R.id.cart_delete_content);

                title.setText("添加设备号");
                //取消删除
                alert.getWindow().findViewById(R.id.cart_delete_cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                        return;
                    }
                });
                //确认修改
                alert.getWindow().findViewById(R.id.cart_delete_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Verification_Utils.checkMobileNumber(content.getText().toString())){
                            Toast.makeText(EquipmentManagementActivity.this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
                        }else if (!content.getText().toString().equals("")) {
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("itfaceId", "017");
                            hashMap.put("deviceId", String.valueOf(id));
                            hashMap.put("token", SPUtils.get(EquipmentManagementActivity.this, "token", ""));
                            hashMap.put("mobile", content.getText().toString().trim());
                            JSONObject jsonObject = new JSONObject(hashMap);

                            OkGo.post(UrlContent.URL).tag(this)
                                    .upJson(jsonObject.toString())
                                    .connTimeOut(10_000)
                                    .execute(new CustomCallBackNoLoading<EquipmentAddBeans>(EquipmentManagementActivity.this) {
                                        @Override
                                        public void onSuccess(EquipmentAddBeans mEquipmentAddBeans, Call call, Response response) {
                                            if (mEquipmentAddBeans.getResultCode() == 1) {
                                                Toast.makeText(EquipmentManagementActivity.this, "添加设备成功", Toast.LENGTH_SHORT).show();
                                                initData();
                                                alert.dismiss();
                                            } else {
                                                App.ErrorToken(mEquipmentAddBeans.getResultCode(), mEquipmentAddBeans.getMsg());

                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(EquipmentManagementActivity.this, "请输入修改的昵称", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void EquipmentManagement_Bank(View view) {
        finish();
    }

    @Override
    public void putMessage(boolean flag) {
        initData();
    }
}
