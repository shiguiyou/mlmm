package com.wanquan.mlmmx.mlmm.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.greenrobot.eventbus.EventBus;

/**
 * 描述：亲子游戏
 * 作者：薛昌峰
 * 时间：2018.06.07
 */

public class ParentChildGameActivity extends BaseActivity {
    private LinearLayout mAppTitleBank;
    private TextView mAppTitleName;
    private TextView mAppTitleSave;
    private WebView mParentChildGameWebView;
    private String url;
    private String flags;

//    @Override
//    protected void onResume() {
//        super.onResume();
//        initData();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(ParentChildGameActivity.this, R.color.tops);

        flags = getIntent().getStringExtra("flags");

        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_parent_child_game;
    }

    @Override
    public void initView() throws Exception {
        mAppTitleBank = (LinearLayout) findViewById(R.id.App_Title_Bank);
        mAppTitleName = (TextView) findViewById(R.id.App_Title_Name);
        mAppTitleSave = (TextView) findViewById(R.id.App_Title_Save);
        mParentChildGameWebView = (WebView) findViewById(R.id.ParentChildGame_WebView);
    }

    private void initListeners() {
        mAppTitleBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mParentChildGameWebView.canGoBack()) {
                    mParentChildGameWebView.goBack();
                } else if ("4".equals(flags)) {
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setFinish(1);
                    messageEvent.setShowBank(false);
                    EventBus.getDefault().post(messageEvent);
                    startActivity(new Intent(ParentChildGameActivity.this, MainActivity.class));
                } else {
                    finish();
                }
            }
        });
    }

    private void initData() {
        mAppTitleName.setText("亲子游戏");

        String token = String.valueOf(SPUtils.get(ParentChildGameActivity.this, "token", ""));

        url = UrlContent.GAME + "token=" + token;
//        Log.e("sdfsfs", url);
        mParentChildGameWebView.getSettings().setJavaScriptEnabled(true);//设置页面支持Javascript
        mParentChildGameWebView.getSettings().setBlockNetworkImage(false);
        mParentChildGameWebView.getSettings().setSupportMultipleWindows(false);
        mParentChildGameWebView.getSettings().setSupportZoom(true);//支持缩放
        mParentChildGameWebView.getSettings().setBuiltInZoomControls(false);//显示放大缩小
        mParentChildGameWebView.setInitialScale(130);//初始化时缩放
        mParentChildGameWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mParentChildGameWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mParentChildGameWebView.setWebChromeClient(new WebChromeClient());
        mParentChildGameWebView.loadUrl(url);
        mParentChildGameWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mParentChildGameWebView.getSettings().setJavaScriptEnabled(true);
        mParentChildGameWebView.getSettings().setSupportZoom(true);
        mParentChildGameWebView.getSettings().setLoadWithOverviewMode(true);
        mParentChildGameWebView.getSettings().setUseWideViewPort(true);

        mParentChildGameWebView.setVerticalScrollBarEnabled(false);
        mParentChildGameWebView.setVerticalScrollbarOverlay(false);
        mParentChildGameWebView.setHorizontalScrollBarEnabled(false);
        mParentChildGameWebView.setHorizontalScrollbarOverlay(false);


        mParentChildGameWebView.getSettings().setDomStorageEnabled(true);// 打开本地缓存提供JS调用,至关重要


        mParentChildGameWebView.addJavascriptInterface(new JavaScriptinterface(this), "android");

        mParentChildGameWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                view.loadUrl(url);//加了Android8.0点击无响应
                return false;
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
                String title = view.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    mAppTitleName.setText(title);
                }
                //H5声音不自动播放
                view.loadUrl("javascript:(function() { " +
                        "var videos = document.getElementsByTagName('audio');" +
                        " for(var i=0;i<videos.length;i++){videos[i].play();}})()");

                view.loadUrl("javascript:(function() { " +
                        "var videos = document.getElementsByTagName('video');" +
                        " for(var i=0;i<videos.length;i++){videos[i].play();}})()");
            }
        });
        mParentChildGameWebView.loadUrl(url);
//        网上说低版本不能使用
//        mParentChildGameWebView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                super.onReceivedTitle(view, title);
//                if (title != null) {
//                    mAppTitleName.setText(title);
//                }
//            }
//        });
    }

    public class JavaScriptinterface {
        Context context;

        public JavaScriptinterface(Context context) {
            super();
            this.context = context;
        }

        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        public void jsCallWebView(String url) {
            startActivity(new Intent(ParentChildGameActivity.this, MyPrizeActivity.class));
        }

        @JavascriptInterface
        public void jsCallWebViewJF(String url) {


            SPUtils.put(ParentChildGameActivity.this, "ParentChildGameflas", "true");//用于积分攻略界面返回到亲子游戏界面
            Intent intent = new Intent(ParentChildGameActivity.this, IntegralStrategyActivity.class);
            intent.putExtra("flags", "4");
            startActivity(intent);
        }
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        mParentChildGameWebView.destroy();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mParentChildGameWebView.destroy();
    }
}
