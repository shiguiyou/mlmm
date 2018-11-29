package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.QRCodeActivityBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.HashMap;

import io.github.xudaojie.qrcodelib.CaptureActivity;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：扫描二维码
 * 作者：薛昌峰
 * 时间：2017.10.18
 */
public class QRCodeActivity extends BaseActivity {
    private ImageView mQRCodeActivityImg;
    private EditText mQRCodeActivityEt;
    private TextView mQRCodeActivityTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(QRCodeActivity.this, R.color.black);

        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_qrcode;
    }

    @Override
    public void initView() throws Exception {
        mQRCodeActivityImg = (ImageView) findViewById(R.id.QRCodeActivity_img);
        mQRCodeActivityEt = (EditText) findViewById(R.id.QRCodeActivity_et);
        mQRCodeActivityTv = (TextView) findViewById(R.id.QRCodeActivity_tv);

    }

    private void initData() {
        //判断当前系统是否高于或等于6.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //当前系统大于等于6.0
            if (ContextCompat.checkSelfPermission(QRCodeActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                //具有拍照权限，直接调用相机
                //证书查询即  扫描二维码
//                startActivityForResult(new Intent(QRCodeActivity.this, CaptureActivity.class), 100);
                startActivityForResult(new Intent(QRCodeActivity.this, SimpleCaptureActivity.class), 100);
            } else {
                //不具有拍照权限，需要进行权限申请
                Toast.makeText(QRCodeActivity.this, "请在设置中打开“相机”访问权限！", Toast.LENGTH_LONG).show();
//                        ActivityCompat.requestPermissions(MineInforActivity.this,new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA_CODE);
            }
        } else {
            //当前系统小于6.0，直接调用拍照
            //证书查询即  扫描二维码
//            startActivityForResult(new Intent(QRCodeActivity.this, CaptureActivity.class), 100);
            startActivityForResult(new Intent(QRCodeActivity.this, SimpleCaptureActivity.class), 100);
        }
    }

    private void initListeners() {
        mQRCodeActivityImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(QRCodeActivity.this, SimpleCaptureActivity.class), 100);
            }
        });

        mQRCodeActivityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mQRCodeActivityEt.getText().toString().trim().equals("")) {
                    initNetwork(mQRCodeActivityEt.getText().toString().trim());
                } else {
                    Toast toast = Toast.makeText(QRCodeActivity.this, "请输入设备号", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 22) {
            if (null != data) {
                String result = data.getStringExtra("resultString");
                String id = result.substring(result.length() - 8, result.length());

                Log.e("tt", "onActivityResult:" + id);
                if (id != null) {
                    initNetwork(id);
                } else {
                    Toast toast = Toast.makeText(this, "设备号错误", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } else {
                return;
            }
        }
    }

    private void initNetwork(String id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "012");
        hashMap.put("token", SPUtils.get(QRCodeActivity.this, "token", ""));
        hashMap.put("deviceCode", id);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<QRCodeActivityBeans>(this) {
                    @Override
                    public void onSuccess(QRCodeActivityBeans mQRCodeActivityBeans, Call call, Response response) {
                        if (mQRCodeActivityBeans.getResultCode() == 1) {
                            if (mQRCodeActivityBeans.getData().getExtra() != 0) {
                                Toast.makeText(QRCodeActivity.this, "设备绑定成功积分+" + mQRCodeActivityBeans.getData().getExtra(), Toast.LENGTH_SHORT).show();
                            }
                            startActivity(new Intent(QRCodeActivity.this, MainActivity.class));
                            finish();
                        } else {
                            App.ErrorToken(mQRCodeActivityBeans.getResultCode(), mQRCodeActivityBeans.getMsg());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        Toast.makeText(QRCodeActivity.this, "服务器连接异常，请稍后重试", Toast.LENGTH_SHORT).show();
                        super.onError(call, response, e);
                    }
                });
    }

    public void QRCodeActivity_Bank(View view) {
        finish();
    }
}
