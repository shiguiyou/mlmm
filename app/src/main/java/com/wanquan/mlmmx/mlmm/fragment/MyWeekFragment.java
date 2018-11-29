package com.wanquan.mlmmx.mlmm.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

/**
 * Created by XCF on 2018/11/5.
 */

public class MyWeekFragment extends Fragment {
    private WebView mMyWeekFragmentWebView;

    private View view;
    private String code;
    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.myweek_fragment, null);

        Bundle bundle = getArguments();
        if (bundle != null) {
            code = bundle.getString("code");
        }

        mMyWeekFragmentWebView = (WebView) view.findViewById(R.id.MyWeekFragment_WebView);
        url =  UrlContent.hx1 + code;

        initData();

        return view;
    }

    private void initData() {
        mMyWeekFragmentWebView.getSettings().setJavaScriptEnabled(true);//设置页面支持Javascript
        mMyWeekFragmentWebView.getSettings().setBlockNetworkImage(false);
        mMyWeekFragmentWebView.getSettings().setSupportMultipleWindows(false);
        mMyWeekFragmentWebView.getSettings().setSupportZoom(true);//支持缩放
        mMyWeekFragmentWebView.getSettings().setBuiltInZoomControls(false);//显示放大缩小
        mMyWeekFragmentWebView.setInitialScale(130);//初始化时缩放
        mMyWeekFragmentWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mMyWeekFragmentWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mMyWeekFragmentWebView.setWebChromeClient(new WebChromeClient());

        mMyWeekFragmentWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mMyWeekFragmentWebView.getSettings().setSupportZoom(true);
        mMyWeekFragmentWebView.getSettings().setLoadWithOverviewMode(true);
        mMyWeekFragmentWebView.getSettings().setUseWideViewPort(true);

        mMyWeekFragmentWebView.setVerticalScrollBarEnabled(false);
        mMyWeekFragmentWebView.setVerticalScrollbarOverlay(false);
        mMyWeekFragmentWebView.setHorizontalScrollBarEnabled(false);
        mMyWeekFragmentWebView.setHorizontalScrollbarOverlay(false);
        mMyWeekFragmentWebView.setWebViewClient(new WebViewClient() {
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

        mMyWeekFragmentWebView.loadUrl(url);

    }
}
