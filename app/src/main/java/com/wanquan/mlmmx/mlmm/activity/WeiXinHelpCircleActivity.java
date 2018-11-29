package com.wanquan.mlmmx.mlmm.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;

/**
 * 描述：微信求助圈
 * 作者：薛昌峰
 * 时间：2017.12.11
 */
public class WeiXinHelpCircleActivity extends BaseActivity {
    private WebView mWeiXinHelpCircleWB;
    String url= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(WeiXinHelpCircleActivity.this, R.color.tops);

        initData();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_wei_xin_help_circle;
    }

    @Override
    public void initView() throws Exception {
        mWeiXinHelpCircleWB = (WebView) findViewById(R.id.WeiXinHelpCircle_WB);

    }

    private void initData() {
        url = "http://api.env365.cn/img/qrcode.jpg";
        mWeiXinHelpCircleWB.getSettings().setJavaScriptEnabled(true);//设置页面支持Javascript
        mWeiXinHelpCircleWB.getSettings().setBlockNetworkImage(false);
        mWeiXinHelpCircleWB.getSettings().setSupportMultipleWindows(false);
        mWeiXinHelpCircleWB.getSettings().setSupportZoom(true);//支持缩放
        mWeiXinHelpCircleWB.getSettings().setBuiltInZoomControls(false);//显示放大缩小
        mWeiXinHelpCircleWB.setInitialScale(130);//初始化时缩放
        mWeiXinHelpCircleWB.getSettings().setDefaultTextEncodingName("utf-8");
        mWeiXinHelpCircleWB.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWeiXinHelpCircleWB.setWebChromeClient(new WebChromeClient());
        mWeiXinHelpCircleWB.loadUrl(url);
        mWeiXinHelpCircleWB.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWeiXinHelpCircleWB.getSettings().setJavaScriptEnabled(true);
        mWeiXinHelpCircleWB.getSettings().setSupportZoom(true);
        mWeiXinHelpCircleWB.getSettings().setLoadWithOverviewMode(true);
        mWeiXinHelpCircleWB.getSettings().setUseWideViewPort(true);

        mWeiXinHelpCircleWB.setVerticalScrollBarEnabled(false);
        mWeiXinHelpCircleWB.setVerticalScrollbarOverlay(false);
        mWeiXinHelpCircleWB.setHorizontalScrollBarEnabled(false);
        mWeiXinHelpCircleWB.setHorizontalScrollbarOverlay(false);
        mWeiXinHelpCircleWB.setWebViewClient(new WebViewClient() {
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
        mWeiXinHelpCircleWB.loadUrl(url);
    }

    public void WeiXinHelpCircleActivity_Bank(View view) {
        finish();
    }
}
