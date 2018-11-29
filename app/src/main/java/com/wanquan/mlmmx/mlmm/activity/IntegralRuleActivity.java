package com.wanquan.mlmmx.mlmm.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;

/**
 * 描述：积分规则
 * 作者：薛昌峰
 * 时间：2018.01.11
 */
public class IntegralRuleActivity extends BaseActivity {
    private LinearLayout mAppTitleBank;
    private TextView mAppTitleName;
    private TextView mAppTitleSave;
    private WebView mIntegralRuleWB;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(IntegralRuleActivity.this, R.color.tops);
        NetWork();

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_integral_rule;
    }

    @Override
    public void initView() throws Exception {
        mAppTitleBank = (LinearLayout) findViewById(R.id.App_Title_Bank);
        mAppTitleName = (TextView) findViewById(R.id.App_Title_Name);
        mAppTitleSave = (TextView) findViewById(R.id.App_Title_Save);
        mIntegralRuleWB = (WebView) findViewById(R.id.IntegralRule_WB);

        mAppTitleBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAppTitleName.setText("积分规则");
    }

    private void NetWork() {
        url = "http://api.env365.cn/html/integralrules.html";
        mIntegralRuleWB.getSettings().setJavaScriptEnabled(true);//设置页面支持Javascript
        mIntegralRuleWB.getSettings().setBlockNetworkImage(false);
        mIntegralRuleWB.getSettings().setAllowContentAccess(true);
        mIntegralRuleWB.getSettings().setSupportMultipleWindows(false);
        mIntegralRuleWB.getSettings().setAppCacheEnabled(false);
        mIntegralRuleWB.getSettings().setSupportZoom(true);//支持缩放
        mIntegralRuleWB.getSettings().setBuiltInZoomControls(true);//显示放大缩小
        mIntegralRuleWB.setInitialScale(130);//初始化时缩放
        mIntegralRuleWB.getSettings().setDefaultTextEncodingName("utf-8");
        mIntegralRuleWB.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mIntegralRuleWB.setWebChromeClient(new WebChromeClient());
//        mIntegralRuleWB.loadUrl(url);
        mIntegralRuleWB.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mIntegralRuleWB.getSettings().setSupportZoom(true);
        mIntegralRuleWB.getSettings().setLoadWithOverviewMode(true);
        mIntegralRuleWB.getSettings().setUseWideViewPort(true);

//        mIntegralRuleWB.getSettings().setDisplayZoomControls(true);//设置是否支持缩放
//        mAboutWebVie.getSettings().setCacheMode(20);//设置缓冲的模式
//        mIntegralRuleWB.getSettings().setDefaultFontSize(16);//设置默认的字体大小

        mIntegralRuleWB.setVerticalScrollBarEnabled(false);
        mIntegralRuleWB.setVerticalScrollbarOverlay(false);
        mIntegralRuleWB.setHorizontalScrollBarEnabled(false);
        mIntegralRuleWB.setHorizontalScrollbarOverlay(false);
        mIntegralRuleWB.setWebViewClient(new WebViewClient() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        mIntegralRuleWB.loadUrl(url);
    }
}
