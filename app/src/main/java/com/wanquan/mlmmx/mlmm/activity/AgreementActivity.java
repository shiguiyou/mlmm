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
 * 描述：注册协议
 * 作者：薛昌峰
 * 时间：2017.09.27
 */
public class AgreementActivity extends AppCompatActivity {
    private WebView mAgreementWebVie;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(AgreementActivity.this, R.color.top);

        initViews();
        initData();
    }

    private void initViews() {
        mAgreementWebVie = (WebView) findViewById(R.id.Agreement_WebVie);
    }

    private void initData() {
        url = "http://api.env365.cn/html/register.html";
        mAgreementWebVie.getSettings().setJavaScriptEnabled(true);//设置页面支持Javascript
        mAgreementWebVie.getSettings().setBlockNetworkImage(false);
        mAgreementWebVie.getSettings().setSupportMultipleWindows(false);
        mAgreementWebVie.getSettings().setSupportZoom(true);//支持缩放
        mAgreementWebVie.getSettings().setBuiltInZoomControls(false);//显示放大缩小
        mAgreementWebVie.setInitialScale(130);//初始化时缩放
        mAgreementWebVie.getSettings().setDefaultTextEncodingName("utf-8");
        mAgreementWebVie.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mAgreementWebVie.setWebChromeClient(new WebChromeClient());
        mAgreementWebVie.loadUrl(url);
        mAgreementWebVie.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mAgreementWebVie.getSettings().setJavaScriptEnabled(true);
        mAgreementWebVie.getSettings().setSupportZoom(true);
        mAgreementWebVie.getSettings().setLoadWithOverviewMode(true);
        mAgreementWebVie.getSettings().setUseWideViewPort(true);

        mAgreementWebVie.setVerticalScrollBarEnabled(false);
        mAgreementWebVie.setVerticalScrollbarOverlay(false);
        mAgreementWebVie.setHorizontalScrollBarEnabled(false);
        mAgreementWebVie.setHorizontalScrollbarOverlay(false);
        mAgreementWebVie.setWebViewClient(new WebViewClient() {
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
        mAgreementWebVie.loadUrl(url);
    }

    public void Agreement_Bank(View view) {
        finish();
    }
}
