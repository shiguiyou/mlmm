package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.ParentingEncyclopediaSearchAdapter;
import com.wanquan.mlmmx.mlmm.beans.ParentingEncyclopediaSearchBeans;
import com.wanquan.mlmmx.mlmm.beans.ParentingEncyclopediaTwoBeans;
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
 * 描述：育儿百科_搜索
 * 作者：薛昌峰
 * 时间：2018.09.14
 */
public class ParentingEncyclopediaSearchActivity extends BaseActivity {
    private EditText mParentingEncyclopediaSearch;
    private TextView mParentingEncyclopediaSearchTextView;
    private ListView mParentingEncyclopediaSearchListView;
    private LinearLayout mParentingEncyclopediaSearchLinearLayout;

    private ParentingEncyclopediaSearchAdapter mParentingEncyclopediaSearchAdapter;
    private List<ParentingEncyclopediaSearchBeans.DataBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(ParentingEncyclopediaSearchActivity.this, R.color.black);

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mParentingEncyclopediaSearchAdapter = new ParentingEncyclopediaSearchAdapter(this, mList);
        mParentingEncyclopediaSearchListView.setAdapter(mParentingEncyclopediaSearchAdapter);

        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_parenting_encyclopedia_search;
    }

    @Override
    public void initView() throws Exception {
        mParentingEncyclopediaSearch = (EditText) findViewById(R.id.ParentingEncyclopedia_Search);
        mParentingEncyclopediaSearchTextView = (TextView) findViewById(R.id.ParentingEncyclopediaSearch_TextView);
        mParentingEncyclopediaSearchListView = (ListView) findViewById(R.id.ParentingEncyclopediaSearch_ListView);
        mParentingEncyclopediaSearchLinearLayout = (LinearLayout) findViewById(R.id.ParentingEncyclopediaSearch_LinearLayout);
    }

    private void initListeners() {
        mParentingEncyclopediaSearchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mParentingEncyclopediaSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String keyWord = String.valueOf(charSequence);
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("itfaceId", "131");
                hashMap.put("token", SPUtils.get(ParentingEncyclopediaSearchActivity.this, "token", ""));
                hashMap.put("keyword", keyWord);
                JSONObject jsonObject = new JSONObject(hashMap);

                OkGo.post(UrlContent.URL).tag(this)
                        .upJson(jsonObject.toString())
                        .connTimeOut(10_000)
                        .execute(new CustomCallBackNoLoading<ParentingEncyclopediaSearchBeans>(ParentingEncyclopediaSearchActivity.this) {
                            @Override
                            public void onSuccess(ParentingEncyclopediaSearchBeans mParentingEncyclopediaSearchBeans, Call call, Response response) {
                                if (mParentingEncyclopediaSearchBeans.getResultCode() == 1) {
                                    mList.clear();
                                    mList.addAll(mParentingEncyclopediaSearchBeans.getData());
                                    mParentingEncyclopediaSearchAdapter.notifyDataSetChanged();
                                    if (mList.size() == 0) {
                                        mParentingEncyclopediaSearchListView.setVisibility(View.GONE);
                                    } else {
                                        mParentingEncyclopediaSearchListView.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    mList.clear();
                                    mParentingEncyclopediaSearchAdapter.notifyDataSetChanged();
                                    App.ErrorToken(mParentingEncyclopediaSearchBeans.getResultCode(), mParentingEncyclopediaSearchBeans.getMsg());
                                }
                            }
                        });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mParentingEncyclopediaSearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(ParentingEncyclopediaSearchActivity.this, ParentingEncyclopediaHTMLActivity.class).putExtra("id", String.valueOf(mList.get(i).getId())).putExtra("name", mList.get(i).getTitle()));
            }
        });
    }
}
