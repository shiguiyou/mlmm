package com.wanquan.mlmmx.mlmm.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.wanquan.mlmmx.mlmm.view.SelectPicPopupShare;

import java.io.ByteArrayOutputStream;

import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneSession;
import static com.wanquan.mlmmx.mlmm.App.api;

/**
 * 描述：服务类
 * 作者：薛昌峰
 * 时间：2017.10.27
 */
public class ServiceActivity extends BaseActivity {
    private LinearLayout mAppTitleBank;
    private TextView mAppTitleName;
    private ImageView mAppTitleSave;
    private WebView mAboutWebVie;
    private String url;
    private String title;
    private String flag = "2";
    private SelectPicPopupShare menuWindow; // 自定义的头像编辑弹出框
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String url2;
    private String title2;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(ServiceActivity.this, R.color.black);

        verifyStoragePermissions(this);

        flag = getIntent().getStringExtra("flag");
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");

//        Log.e("wwwwwwwwurl", url + "");
//        Log.e("wwwwwwww", title);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mAboutWebVie.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        NetWork();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_service;
    }

    @Override
    public void initView() throws Exception {
        mAppTitleBank = (LinearLayout) findViewById(R.id.App_Title_Bank);
        mAppTitleName = (TextView) findViewById(R.id.App_Title_Name);
        mAppTitleSave = (ImageView) findViewById(R.id.App_Title_Save);
        mAboutWebVie = (WebView) findViewById(R.id.Service_WebView);
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
        mAppTitleSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAboutWebVie.evaluateJavascript("document.getElementsByClassName('content-title')[0].innerHTML", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        //此处为 js 返回的结果
                        String str1 = value.substring(1);
                        String str2 = str1.substring(0, str1.length() - 1);
                        title2 = str2;
                        Log.e("ffsdfs1", title2);
                    }
                });
                mAboutWebVie.evaluateJavascript("document.getElementsByTagName(\"p\")[0].innerText.substring(0,40)", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        //此处为 js 返回的结果
                        String str1 = value.substring(1);
                        String str2 = str1.substring(0, str1.length() - 1);
                        content = str2;
                    }
                });

                menuWindow = new SelectPicPopupShare(ServiceActivity.this, itemsOnClick);
                View parent1 = LayoutInflater.from(ServiceActivity.this).inflate(R.layout.activity_message, null);
                menuWindow.showAtLocation(parent1, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.Message_Popup_One:
                    // 微信注册初始化
                    api = WXAPIFactory.createWXAPI(ServiceActivity.this, "wxeca84b2f356693a2", true);
                    api.registerApp("wxeca84b2f356693a2");
                    share2weixin(0);
                    break;
                case R.id.Message_Popup_Two:
                    // 微信注册初始化
                    api = WXAPIFactory.createWXAPI(ServiceActivity.this, "wxeca84b2f356693a2", true);
                    api.registerApp("wxeca84b2f356693a2");
                    share2weixin(1);
                    break;
                case R.id.Message_Popup_Three:
                    if (SpUtil.getBooleanValue(ServiceActivity.this, MyContant.ISLOGIN, true)) {
                        startActivity(new Intent(ServiceActivity.this, SelectCircleActivity.class).putExtra("flag", "1").putExtra("url", url2 + "&share=1").putExtra("title", title2));
                    } else {
                        Intent intent = new Intent(ServiceActivity.this, LoginActivity.class);
                        intent.putExtra("fanhui", "1");
                        intent.putExtra("url", url2 + "&share=1");
                        intent.putExtra("title", title2);
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
        mAppTitleSave.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(mAppTitleSave.getDrawingCache());
        mAppTitleSave.setDrawingCacheEnabled(false);
        byte[] bytes = bitmap2Bytes(bitmap, 24);

        if (!api.isWXAppInstalled()) {
            Toast.makeText(ServiceActivity.this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url2;
        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = title2;
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
        mAppTitleName.setText(title);

//        url = "http://api.env365.cn/html/aboutus.html";
        mAboutWebVie.getSettings().setJavaScriptEnabled(true);//设置页面支持Javascript
        mAboutWebVie.getSettings().setBlockNetworkImage(false);
        mAboutWebVie.getSettings().setAllowContentAccess(true);
        mAboutWebVie.getSettings().setSupportMultipleWindows(false);
        mAboutWebVie.getSettings().setAppCacheEnabled(false);
        mAboutWebVie.getSettings().setSupportZoom(true);//支持缩放
        mAboutWebVie.getSettings().setBuiltInZoomControls(true);//显示放大缩小
        mAboutWebVie.setInitialScale(130);//初始化时缩放
        mAboutWebVie.getSettings().setDefaultTextEncodingName("utf-8");
        mAboutWebVie.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mAboutWebVie.setWebChromeClient(new WebChromeClient());
        mAboutWebVie.loadUrl(url);
        mAboutWebVie.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mAboutWebVie.getSettings().setSupportZoom(true);
        mAboutWebVie.getSettings().setLoadWithOverviewMode(true);
        mAboutWebVie.getSettings().setUseWideViewPort(true);

//        mAboutWebVie.getSettings().setBuiltInZoomControls(true);//设置显示缩放按钮
//        mAboutWebVie.getSettings().setDisplayZoomControls(true);//设置是否支持缩放
//        mAboutWebVie.getSettings().setCacheMode(20);//设置缓冲的模式
//        mAboutWebVie.getSettings().setDefaultFontSize (16);//设置默认的字体大小

        mAboutWebVie.setVerticalScrollBarEnabled(false);
        mAboutWebVie.setVerticalScrollbarOverlay(false);
        mAboutWebVie.setHorizontalScrollBarEnabled(false);
        mAboutWebVie.setHorizontalScrollbarOverlay(false);
        mAboutWebVie.setWebViewClient(new WebViewClient() {
            //            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String request) {
//                view.loadUrl(url);
//                返回true  表明点击网页里面的链接还是在当前的webview里跳转，
//                返回false 跳到其他浏览器
                Log.e("webview_url1", String.valueOf(request));
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                Dialog dialog = StyledDialog.buildLoading(BaseApplication.sContext,"加载中",false,false).show();
//                dialog.setCanceledOnTouchOutside(true);
                Log.e("webview_url2", String.valueOf(url));
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//              StyledDialog.dismissLoading();
                url2 = url;
                Log.e("webview_url3", String.valueOf(url));
                if (url.contains("share=1")) {
                    mAppTitleSave.setVisibility(View.VISIBLE);
                } else {
                    mAppTitleSave.setVisibility(View.GONE);
                }
            }
        });
        mAboutWebVie.loadUrl(url);

        mAppTitleBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.equals("1")) {
                    finish();
                } else if (mAboutWebVie.canGoBack()) {
                    mAboutWebVie.goBack();
                } else {
                    finish();
                }
            }
        });
    }
}
