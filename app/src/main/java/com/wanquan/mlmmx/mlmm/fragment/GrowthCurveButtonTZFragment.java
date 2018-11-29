package com.wanquan.mlmmx.mlmm.fragment;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/12.
 */

public class GrowthCurveButtonTZFragment extends BaseFragment {
    private String sg;
    private String tz;
    private String sex;
    private WebView mGrowthCurveButtonTZWebView;
    private String xingbie;
    private View view;

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.growth_tz_fragment, container, false);

        initViews();
        initData();
        initListeners();

        return view;
    }

    private void initViews() {
        mGrowthCurveButtonTZWebView = (WebView) view.findViewById(R.id.GrowthCurveButtonTZ_WebView);
    }

    private void initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mGrowthCurveButtonTZWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        String babySex = String.valueOf(SPUtils.get(getContext(), "BabySex", ""));
//        Log.e("ffffffffbabySex", babySex+"xcr");

        if (babySex.equals("1")) {
            xingbie = "F";
        } else if (babySex.equals("2")) {
            xingbie = "M";
        }
        String id = String.valueOf(SPUtils.get(getContext(), "userid", ""));
//        Log.e("ffffffff", String.valueOf(SPUtils.get(getContext(), "userid", "")));
//        Log.e("ffffffff", xingbie+"xcr");
        final String url = UrlContent.QX + "userId=" + id + "&type=weight" + "&sex=" + xingbie + "&babyId=" + SPUtils.get(getContext(), "babyId", "");
        mGrowthCurveButtonTZWebView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        mGrowthCurveButtonTZWebView.getSettings().setJavaScriptEnabled(true);//设置页面支持Javascript
        mGrowthCurveButtonTZWebView.getSettings().setBlockNetworkImage(false);
        mGrowthCurveButtonTZWebView.getSettings().setSupportMultipleWindows(false);
        mGrowthCurveButtonTZWebView.getSettings().setSupportZoom(true);//支持缩放
        mGrowthCurveButtonTZWebView.getSettings().setBuiltInZoomControls(false);//显示放大缩小
        mGrowthCurveButtonTZWebView.setInitialScale(130);//初始化时缩放
        mGrowthCurveButtonTZWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mGrowthCurveButtonTZWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mGrowthCurveButtonTZWebView.setWebChromeClient(new WebChromeClient());
        mGrowthCurveButtonTZWebView.loadUrl(url);
        mGrowthCurveButtonTZWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mGrowthCurveButtonTZWebView.getSettings().setSupportZoom(true);
        mGrowthCurveButtonTZWebView.getSettings().setLoadWithOverviewMode(true);
        mGrowthCurveButtonTZWebView.getSettings().setUseWideViewPort(true);

        mGrowthCurveButtonTZWebView.setVerticalScrollBarEnabled(false);
        mGrowthCurveButtonTZWebView.setVerticalScrollbarOverlay(false);
        mGrowthCurveButtonTZWebView.setHorizontalScrollBarEnabled(false);
        mGrowthCurveButtonTZWebView.setHorizontalScrollbarOverlay(false);
        mGrowthCurveButtonTZWebView.setWebViewClient(new WebViewClient() {
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
        mGrowthCurveButtonTZWebView.loadUrl(url);
    }


    private void initListeners() {
    }

}
