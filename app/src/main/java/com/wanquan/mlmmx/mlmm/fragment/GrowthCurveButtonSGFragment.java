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
import com.wanquan.mlmmx.mlmm.activity.GrowthCurveActivity;
import com.wanquan.mlmmx.mlmm.beans.HomeListViewBeans;
import com.wanquan.mlmmx.mlmm.beans.SettingBabyMessageBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.JSONUtils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/12.
 */

public class GrowthCurveButtonSGFragment extends BaseFragment {
    private String sg;
    private String tz;
    private String sex;
    private WebView mGrowthCurveButtonSGWebView;
    private View view;
    private String xingbie = "M";

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.growth_sg_fragment, container, false);

        initViews();
        initData();
        initListeners();

        return view;
    }

    private void initViews() {
        mGrowthCurveButtonSGWebView = (WebView) view.findViewById(R.id.GrowthCurveButtonSG_WebView);
    }

    private void initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mGrowthCurveButtonSGWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        String babySex = String.valueOf(SPUtils.get(getContext(), "BabySex", ""));
        if (babySex.equals("1")) {
            xingbie = "F";
        } else if (babySex.equals("2")) {
            xingbie = "M";
        }
        String id = String.valueOf(SPUtils.get(getContext(), "userid", ""));
        Log.e("ffffffff", String.valueOf(SPUtils.get(getContext(), "userid", "")));
//        Log.e("ffffffff", xingbie+"xcr");
        final String url = UrlContent.QX + "userId=" + id + "&type=height" + "&sex=" + xingbie + "&babyId=" + SPUtils.get(getContext(), "babyId", "");
        Log.e("成长曲线_身高", "http://api.env365.cn/html/echarts.html?userId=" + id + "&type=height" + "&sex=" + xingbie + "&babyId=" + SPUtils.get(getContext(), "babyId", ""));
        mGrowthCurveButtonSGWebView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        mGrowthCurveButtonSGWebView.getSettings().setJavaScriptEnabled(true);//设置页面支持Javascript
        mGrowthCurveButtonSGWebView.getSettings().setBlockNetworkImage(false);
        mGrowthCurveButtonSGWebView.getSettings().setSupportMultipleWindows(false);
        mGrowthCurveButtonSGWebView.getSettings().setSupportZoom(true);//支持缩放
        mGrowthCurveButtonSGWebView.getSettings().setBuiltInZoomControls(false);//显示放大缩小
        mGrowthCurveButtonSGWebView.setInitialScale(130);//初始化时缩放
        mGrowthCurveButtonSGWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mGrowthCurveButtonSGWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mGrowthCurveButtonSGWebView.setWebChromeClient(new WebChromeClient());
        mGrowthCurveButtonSGWebView.loadUrl(url);
        mGrowthCurveButtonSGWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mGrowthCurveButtonSGWebView.getSettings().setSupportZoom(true);
        mGrowthCurveButtonSGWebView.getSettings().setLoadWithOverviewMode(true);
        mGrowthCurveButtonSGWebView.getSettings().setUseWideViewPort(true);

        mGrowthCurveButtonSGWebView.setVerticalScrollBarEnabled(false);
        mGrowthCurveButtonSGWebView.setVerticalScrollbarOverlay(false);
        mGrowthCurveButtonSGWebView.setHorizontalScrollBarEnabled(false);
        mGrowthCurveButtonSGWebView.setHorizontalScrollbarOverlay(false);
        mGrowthCurveButtonSGWebView.setWebViewClient(new WebViewClient() {
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
        mGrowthCurveButtonSGWebView.loadUrl(url);
    }

    private void initListeners() {
    }

}
