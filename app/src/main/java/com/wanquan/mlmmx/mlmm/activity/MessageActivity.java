package com.wanquan.mlmmx.mlmm.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.SelectPicPopup;
import com.wanquan.mlmmx.mlmm.view.SelectPicPopupShare;

import java.io.ByteArrayOutputStream;

import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneSession;
import static com.wanquan.mlmmx.mlmm.App.api;

/**
 * 描述：资讯
 * 作者：薛昌峰
 * 时间：2017.11.28
 */
public class MessageActivity extends BaseActivity {
    private ImageView mMessageActivitySave;
    private WebView mMessageActivityWebView;
    private String id;
    private String url;
    private SelectPicPopupShare menuWindow; // 自定义的头像编辑弹出框
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String title;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(MessageActivity.this, R.color.black);

        verifyStoragePermissions(this);

        id = String.valueOf(getIntent().getIntExtra("id", 0));
        url = "http://cms.env365.cn/news.jspx?newsId=" + id;

//        Log.e("urilfsfs", url);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mMessageActivityWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        NetWork();
        initListeners();
//        String temp = "<html><body bgcolor=\"" + "black"
//                + "\"> <br/><embed src=\"" + url + "\" width=\"" + "100%"
//                + "\" height=\"" + "90%" + "\" scale=\"" + "noscale"
//                + "\" type=\"" + "application/x-shockwave-flash"
//                + "\"> </embed></body></html>";
//        String mimeType = "text/html";
//        String encoding = "utf-8";
//        mMessageActivityWebView.loadDataWithBaseURL("null", temp, mimeType, encoding, "");
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
        mMessageActivitySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMessageActivityWebView.evaluateJavascript("document.getElementsByClassName('content-title')[0].innerHTML", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        //此处为 js 返回的结果
                        String str1 = value.substring(1);
                        String str2 = str1.substring(0, str1.length() - 1);
                        title = str2;
                        Log.e("ffsdfs1", title);
                    }
                });

                mMessageActivityWebView.evaluateJavascript("document.getElementsByTagName(\"p\")[0].innerText.substring(0,40)", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        //此处为 js 返回的结果
                        String str1 = value.substring(1);
                        String str2 = str1.substring(0, str1.length() - 1);
                        content = str2;
                    }
                });

                menuWindow = new SelectPicPopupShare(MessageActivity.this, itemsOnClick);
                View parent1 = LayoutInflater.from(MessageActivity.this).inflate(R.layout.activity_message, null);
                menuWindow.showAtLocation(parent1, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_message;
    }

    @Override
    public void initView() throws Exception {
        mMessageActivitySave = (ImageView) findViewById(R.id.MessageActivity_Save);
        mMessageActivityWebView = (WebView) findViewById(R.id.MessageActivity_WebView);
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.Message_Popup_One:
                    // 微信注册初始化
                    api = WXAPIFactory.createWXAPI(MessageActivity.this, "wxeca84b2f356693a2", true);
                    api.registerApp("wxeca84b2f356693a2");
                    share2weixin(0);
                    break;
                case R.id.Message_Popup_Two:
                    // 微信注册初始化
                    api = WXAPIFactory.createWXAPI(MessageActivity.this, "wxeca84b2f356693a2", true);
                    api.registerApp("wxeca84b2f356693a2");
                    share2weixin(1);
                    break;
                case R.id.Message_Popup_Three:
                    if (SpUtil.getBooleanValue(MessageActivity.this, MyContant.ISLOGIN, true)) {
                        startActivity(new Intent(MessageActivity.this, SelectCircleActivity.class)
                                .putExtra("flag", "1")
                                .putExtra("url", url + "&share=1" + "&from=mlmm")
                                .putExtra("title", title));
                    } else {
                        Intent intent = new Intent(MessageActivity.this, LoginActivity.class);
                        intent.putExtra("fanhui", "1");
                        intent.putExtra("url", url + "&share=1" + "&from=mlmm");
                        intent.putExtra("title", title);
                        startActivity(intent);
//                        finish();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void share2weixin(int flag) {
        mMessageActivitySave.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(mMessageActivitySave.getDrawingCache());
        mMessageActivitySave.setDrawingCacheEnabled(false);
        byte[] bytes = bitmap2Bytes(bitmap, 24);

        if (!api.isWXAppInstalled()) {
            Toast.makeText(MessageActivity.this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = title;
        msg.description = content;//内容
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

    private void NetWork() {
        mMessageActivityWebView.getSettings().setJavaScriptEnabled(true);//设置页面支持Javascript
        mMessageActivityWebView.getSettings().setBlockNetworkImage(false);
        mMessageActivityWebView.getSettings().setSupportMultipleWindows(false);
        mMessageActivityWebView.getSettings().setSupportZoom(true);//支持缩放
        mMessageActivityWebView.getSettings().setBuiltInZoomControls(false);//显示放大缩小
        mMessageActivityWebView.setInitialScale(130);//初始化时缩放
        mMessageActivityWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mMessageActivityWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mMessageActivityWebView.setWebChromeClient(new WebChromeClient());

        mMessageActivityWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mMessageActivityWebView.getSettings().setSupportZoom(true);
        mMessageActivityWebView.getSettings().setLoadWithOverviewMode(true);
        mMessageActivityWebView.getSettings().setUseWideViewPort(true);

        mMessageActivityWebView.setVerticalScrollBarEnabled(false);
        mMessageActivityWebView.setVerticalScrollbarOverlay(false);
        mMessageActivityWebView.setHorizontalScrollBarEnabled(false);
        mMessageActivityWebView.setHorizontalScrollbarOverlay(false);
        mMessageActivityWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                Dialog dialog = StyledDialog.buildLoading(BaseApplication.sContext,"加载中",false,false).show();
//                dialog.setCanceledOnTouchOutside(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                StyledDialog.dismissLoading();
//                String title = view.getTitle();
//                if (!TextUtils.isEmpty(title)) {
//                }
            }
        });

        mMessageActivityWebView.loadUrl(url);

    }

    public void MessageActivity_Bank(View view) {
        finish();
    }
}
