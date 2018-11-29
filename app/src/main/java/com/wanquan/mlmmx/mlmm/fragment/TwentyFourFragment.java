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

public class TwentyFourFragment extends Fragment {
    private WebView mTwentyFourFragmentWebView;
    private View view;
    private String url;
    private String code;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.twentyfour_fragment,null);
        Bundle bundle = getArguments();
        if (bundle != null) {
            code = bundle.getString("code");
        }


        mTwentyFourFragmentWebView = (WebView) view.findViewById(R.id.TwentyFourFragment_WebView);

        url = UrlContent.hx2 + code;

        initData();

        return view;
    }

    private void initData() {
        mTwentyFourFragmentWebView.getSettings().setJavaScriptEnabled(true);//设置页面支持Javascript
        mTwentyFourFragmentWebView.getSettings().setBlockNetworkImage(false);
        mTwentyFourFragmentWebView.getSettings().setSupportMultipleWindows(false);
        mTwentyFourFragmentWebView.getSettings().setSupportZoom(true);//支持缩放
        mTwentyFourFragmentWebView.getSettings().setBuiltInZoomControls(false);//显示放大缩小
        mTwentyFourFragmentWebView.setInitialScale(130);//初始化时缩放
        mTwentyFourFragmentWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mTwentyFourFragmentWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mTwentyFourFragmentWebView.setWebChromeClient(new WebChromeClient());

        mTwentyFourFragmentWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mTwentyFourFragmentWebView.getSettings().setSupportZoom(true);
        mTwentyFourFragmentWebView.getSettings().setLoadWithOverviewMode(true);
        mTwentyFourFragmentWebView.getSettings().setUseWideViewPort(true);

        mTwentyFourFragmentWebView.setVerticalScrollBarEnabled(false);
        mTwentyFourFragmentWebView.setVerticalScrollbarOverlay(false);
        mTwentyFourFragmentWebView.setHorizontalScrollBarEnabled(false);
        mTwentyFourFragmentWebView.setHorizontalScrollbarOverlay(false);
        mTwentyFourFragmentWebView.setWebViewClient(new WebViewClient() {
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

        mTwentyFourFragmentWebView.loadUrl(url);

    }
}
