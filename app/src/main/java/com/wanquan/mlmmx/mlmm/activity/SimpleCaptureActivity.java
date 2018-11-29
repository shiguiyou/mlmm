package com.wanquan.mlmmx.mlmm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.beans.QRCodeActivityBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.HashMap;

import io.github.xudaojie.qrcodelib.CaptureActivity;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by xdj on 16/9/17.
 */

public class SimpleCaptureActivity extends CaptureActivity {
    protected Activity mActivity = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
    }

    @Override
    protected void handleResult(String resultString) {
        if (TextUtils.isEmpty(resultString)) {
            restartPreview();
        } else {
            Intent intent = new Intent(SimpleCaptureActivity.this, QRCodeActivity.class);
            intent.putExtra("resultString", resultString);
            setResult(22, intent);
            finish();
//            String id = resultString.substring(resultString.length() - 8, resultString.length());
//            initNetwork(id);
        }
    }

    private void initNetwork(String id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "012");
        hashMap.put("token", SPUtils.get(SimpleCaptureActivity.this, "token", ""));
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
                                Toast.makeText(SimpleCaptureActivity.this, "设备绑定成功积分+" + mQRCodeActivityBeans.getData().getExtra(), Toast.LENGTH_SHORT).show();
                            }
                            startActivity(new Intent(SimpleCaptureActivity.this, MainActivity.class));
                            finish();
                        } else {
                            App.ErrorToken(mQRCodeActivityBeans.getResultCode(), mQRCodeActivityBeans.getMsg());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        Toast.makeText(SimpleCaptureActivity.this, "服务器连接异常，请稍后重试", Toast.LENGTH_SHORT).show();
                        super.onError(call, response, e);
                    }
                });
    }

}
