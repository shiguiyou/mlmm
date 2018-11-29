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
import android.widget.ListView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.HistoryMemoryBeans;
import com.wanquan.mlmmx.mlmm.beans.StrainerInformationBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：历史记录
 * 作者：薛昌峰
 * 时间：2017.10.20
 */
public class HistoryMemoryActivity extends BaseActivity {
    private WebView mHistoryMemoryWebView;
    private List<HistoryMemoryBeans> mList = new ArrayList<>();
    private String id;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(HistoryMemoryActivity.this, R.color.black);
        id = getIntent().getStringExtra("id");
        initData();
        initListeners();

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_history;
    }

    @Override
    public void initView() throws Exception {
        mHistoryMemoryWebView = (WebView) findViewById(R.id.HistoryMemory_WebView);
    }

    private void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "027");
        hashMap.put("token", SPUtils.get(this, "token", ""));
        hashMap.put("deviceId", id);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<HistoryMemoryBeans>(this) {
                    @Override
                    public void onSuccess(HistoryMemoryBeans mHistoryMemoryBeans, Call call, Response response) {
                        if (mHistoryMemoryBeans.getResultCode() == 1) {
                            url = mHistoryMemoryBeans.getData();
                            Web(url);
                        } else {
                            App.ErrorToken(mHistoryMemoryBeans.getResultCode(), mHistoryMemoryBeans.getMsg());

                        }
                    }
                });
    }

    private void Web(final String url) {
        mHistoryMemoryWebView.getSettings().setJavaScriptEnabled(true);//设置页面支持Javascript
        mHistoryMemoryWebView.getSettings().setBlockNetworkImage(false);
        mHistoryMemoryWebView.getSettings().setSupportMultipleWindows(false);
        mHistoryMemoryWebView.getSettings().setSupportZoom(true);//支持缩放
        mHistoryMemoryWebView.getSettings().setBuiltInZoomControls(false);//显示放大缩小
        mHistoryMemoryWebView.setInitialScale(130);//初始化时缩放
        mHistoryMemoryWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mHistoryMemoryWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mHistoryMemoryWebView.setWebChromeClient(new WebChromeClient());
        mHistoryMemoryWebView.loadUrl(url);
        mHistoryMemoryWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mHistoryMemoryWebView.getSettings().setJavaScriptEnabled(true);
        mHistoryMemoryWebView.getSettings().setSupportZoom(true);
        mHistoryMemoryWebView.getSettings().setLoadWithOverviewMode(true);
        mHistoryMemoryWebView.getSettings().setUseWideViewPort(true);

        mHistoryMemoryWebView.setVerticalScrollBarEnabled(false);
        mHistoryMemoryWebView.setVerticalScrollbarOverlay(false);
        mHistoryMemoryWebView.setHorizontalScrollBarEnabled(false);
        mHistoryMemoryWebView.setHorizontalScrollbarOverlay(false);
        mHistoryMemoryWebView.setWebViewClient(new WebViewClient() {
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
        mHistoryMemoryWebView.loadUrl(url);
    }

    private void initListeners() {
    }

    public void HistoryMemoryActivity_Bank(View view) {
        finish();
    }
}
