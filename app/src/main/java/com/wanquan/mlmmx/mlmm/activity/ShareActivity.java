package com.wanquan.mlmmx.mlmm.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;

import java.io.ByteArrayOutputStream;

import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneSession;
import static com.wanquan.mlmmx.mlmm.App.api;

/**
 * 描述：分享应用
 * 作者：薛昌峰
 * 时间：2017.09.11
 */
public class ShareActivity extends BaseActivity implements IUiListener {
    private LinearLayout mShareOne;
    private LinearLayout mShareTwo;
    private LinearLayout mShareThree;
    private LinearLayout mShareFour;
    private Tencent mTencent;
    private String APP_ID = "1106283467";
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(ShareActivity.this, R.color.black);

        mTencent = Tencent.createInstance(APP_ID, getApplicationContext());
        verifyStoragePermissions(this);

        initListeners();
    }
    /**
     * 验证读取sd卡的权限
     *
     * @param activity
     */
    public boolean verifyStoragePermissions(Activity activity) {
        /*******below android 6.0*******/
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "授权失败,请去设置打开权限", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void initListeners() {
        mShareOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 微信注册初始化
                api = WXAPIFactory.createWXAPI(ShareActivity.this, "wxeca84b2f356693a2", true);
                api.registerApp("wxeca84b2f356693a2");
                share2weixin(0);
            }
        });
        mShareTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 微信注册初始化
                api = WXAPIFactory.createWXAPI(ShareActivity.this, "wxeca84b2f356693a2", true);
                api.registerApp("wxeca84b2f356693a2");
                share2weixin(1);
            }
        });
        mShareThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, "美丽妈妈好空气");
//                params.putString(QQShare.SHARE_TO_QQ_SUMMARY,
//                        "温度：" + temp + " ℃"
//                                + " 湿度" + humi + "%"
//                                + " 臭氧" + ozone + "mg/m3"
//                                + " pm2.5:" + pm25 + "ug/m3"
//                                + " pm0.3:" + pm03 + "个/0.01L");
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://api.env365.cn/img/qcode.png");
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://api.env365.cn/img/share_thumbnail.png");
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "美丽妈妈");
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
                mTencent.shareToQQ(ShareActivity.this, params, ShareActivity.this);
                App.integral("sharedownload", "", "分享成功积分");

            }
        });
        mShareFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, "美丽妈妈好空气");
//                params.putString(QQShare.SHARE_TO_QQ_SUMMARY,
//                        "温度：" + temp + " ℃"
//                                + "湿度：" + humi + "%"
//                                + " 臭氧：" + ozone + "mg/m3"
//                                + " pm2.5:" + pm25 + "ug/m3"
//                                + " pm0.3:" + pm03 + "个/0.01L");
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://api.env365.cn/img/qcode.png");
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://api.env365.cn/img/share_thumbnail.png");
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "美丽妈妈");
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
                mTencent.shareToQQ(ShareActivity.this, params, ShareActivity.this);
                App.integral("sharedownload", "", "分享成功积分");
            }
        });
    }

    private void share2weixin(int flag) {
        mShareOne.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(mShareOne.getDrawingCache());
        mShareOne.setDrawingCacheEnabled(false);
        byte[] bytes = bitmap2Bytes(bitmap, 24);

        if (!api.isWXAppInstalled()) {
            Toast.makeText(ShareActivity.this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://api.env365.cn/img/qcode.png";
        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = "让美丽妈妈APP来呵护您的宝宝吧";
        msg.description = "美丽妈妈是一款母婴类综合性服务平台，快去体验吧";//内容
        msg.thumbData = bytes;
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);//图片-
        msg.setThumbImage(thumb);
        //图片加载是使用的ImageLoader.loadImageSync() 同步方法
        // 并且还要创建图片的缩略图，因为微信限制了图片的大小
//        Bitmap thumbBmp = Bitmap.createScaledBitmap(ImageLoaderUtil.getBitmap(image), 200, 200, true);
//        msg.setThumbImage(thumbBmp);
//        thumbBmp.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag;
        api.sendReq(req);
        req.scene = WXSceneSession;
        App.integral("sharedownload", "", "分享成功积分");
    }

    /**
     * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
     *
     * @param bitmap //     * @param IMAGE_SIZE
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb && options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        return output.toByteArray();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_share;
    }

    @Override
    public void initView() throws Exception {
        mShareOne = (LinearLayout) findViewById(R.id.Share_One);
        mShareTwo = (LinearLayout) findViewById(R.id.Share_Two);
        mShareThree = (LinearLayout) findViewById(R.id.Share_Three);
        mShareFour = (LinearLayout) findViewById(R.id.Share_Four);
    }

    public void ShareActivity_Bank(View view) {
        finish();
    }

    @Override
    public void onComplete(Object o) {

    }

    @Override
    public void onError(UiError uiError) {

    }

    @Override
    public void onCancel() {
        Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
    }
}
