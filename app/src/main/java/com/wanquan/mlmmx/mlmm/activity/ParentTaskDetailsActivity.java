package com.wanquan.mlmmx.mlmm.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;

/**
 * 描述：亲子任务详情
 * 作者：薛昌峰
 * 时间：2018.01.17
 */
public class ParentTaskDetailsActivity extends BaseActivity {
    private TextView mParentTaskDetailsTitle;
    private WebView mParentTaskDetailsWebView;
    private String url;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(ParentTaskDetailsActivity.this, R.color.tops);

        initData();
        initListeners();

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_parent_task_details;
    }

    @Override
    public void initView() throws Exception {
        mParentTaskDetailsTitle = (TextView) findViewById(R.id.ParentTaskDetails_Title);
        mParentTaskDetailsWebView = (WebView) findViewById(R.id.ParentTaskDetails_WebView);
    }

    private void initData() {
        mParentTaskDetailsTitle.setText(title);
        mParentTaskDetailsWebView.getSettings().setJavaScriptEnabled(true);//设置页面支持Javascript
        mParentTaskDetailsWebView.getSettings().setBlockNetworkImage(false);
        mParentTaskDetailsWebView.getSettings().setSupportMultipleWindows(false);
        mParentTaskDetailsWebView.getSettings().setSupportZoom(true);//支持缩放
        mParentTaskDetailsWebView.getSettings().setBuiltInZoomControls(false);//显示放大缩小
        mParentTaskDetailsWebView.setInitialScale(130);//初始化时缩放
        mParentTaskDetailsWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mParentTaskDetailsWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mParentTaskDetailsWebView.setWebChromeClient(new WebChromeClient());
        mParentTaskDetailsWebView.loadUrl(url);
        mParentTaskDetailsWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mParentTaskDetailsWebView.getSettings().setJavaScriptEnabled(true);
        mParentTaskDetailsWebView.getSettings().setSupportZoom(true);
        mParentTaskDetailsWebView.getSettings().setLoadWithOverviewMode(true);
        mParentTaskDetailsWebView.getSettings().setUseWideViewPort(true);

        mParentTaskDetailsWebView.setVerticalScrollBarEnabled(false);
        mParentTaskDetailsWebView.setVerticalScrollbarOverlay(false);
        mParentTaskDetailsWebView.setHorizontalScrollBarEnabled(false);
        mParentTaskDetailsWebView.setHorizontalScrollbarOverlay(false);
        mParentTaskDetailsWebView.setWebViewClient(new WebViewClient() {
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
        mParentTaskDetailsWebView.loadUrl(url);
    }

    private void initListeners() {
    }

    public void ParentTaskDetailsActivity_Bank(View view) {
        finish();
    }
}
