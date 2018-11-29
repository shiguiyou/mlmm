package com.wanquan.mlmmx.mlmm.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;

/**
 * 描述：关于我们
 * 作者：薛昌峰
 * 时间：2017.09.27
 */
public class AboutActivity extends AppCompatActivity {
    private WebView mAboutWebVie;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(AboutActivity.this, R.color.tops);

        initViews();
        initData();
    }

    private void initViews() {
        mAboutWebVie = (WebView) findViewById(R.id.About_WebVie);
    }

    private void initData() {
        url = "http://api.env365.cn/html/aboutus.html";
        mAboutWebVie.getSettings().setJavaScriptEnabled(true);//设置页面支持Javascript
        mAboutWebVie.getSettings().setBlockNetworkImage(false);
        mAboutWebVie.getSettings().setSupportMultipleWindows(false);
        mAboutWebVie.getSettings().setSupportZoom(true);//支持缩放
        mAboutWebVie.getSettings().setBuiltInZoomControls(false);//显示放大缩小
        mAboutWebVie.setInitialScale(130);//初始化时缩放
        mAboutWebVie.getSettings().setDefaultTextEncodingName("utf-8");
        mAboutWebVie.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mAboutWebVie.setWebChromeClient(new WebChromeClient());
        mAboutWebVie.loadUrl(url);
        mAboutWebVie.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mAboutWebVie.getSettings().setJavaScriptEnabled(true);
        mAboutWebVie.getSettings().setSupportZoom(true);
        mAboutWebVie.getSettings().setLoadWithOverviewMode(true);
        mAboutWebVie.getSettings().setUseWideViewPort(true);

        mAboutWebVie.setVerticalScrollBarEnabled(false);
        mAboutWebVie.setVerticalScrollbarOverlay(false);
        mAboutWebVie.setHorizontalScrollBarEnabled(false);
        mAboutWebVie.setHorizontalScrollbarOverlay(false);
        mAboutWebVie.setWebViewClient(new WebViewClient() {
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
            }
        });
        mAboutWebVie.loadUrl(url);
    }

    public void About_Bank(View view) {
        finish();
    }
}
