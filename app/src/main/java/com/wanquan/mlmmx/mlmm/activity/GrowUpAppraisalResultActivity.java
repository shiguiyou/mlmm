package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.GrowUpAppraisalResultBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：成长评测结果
 * 作者：薛昌峰
 * 时间：2018.09.30
 */
public class GrowUpAppraisalResultActivity extends BaseActivity {
    private LinearLayout mGrowUpAppraisalResultBank;
    private TextView mGrowUpAppraisalResultTitle;
    private ImageView mGrowUpAppraisalResultSave;
    private TextView mGrowUpAppraisalResultText1;
    private TextView mGrowUpAppraisalOneText1;
    private TextView mGrowUpAppraisalOneText2;
    private TextView mGrowUpAppraisalTwoText1;
    private TextView mGrowUpAppraisalTwoText2;
    private TextView mGrowUpAppraisalThreeText1;
    private TextView mGrowUpAppraisalThreeText2;
    private TextView mGrowUpAppraisalButton;
    private String month;
    private String name;
    private String year;
    private GrowUpAppraisalResultBeans.DataBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(GrowUpAppraisalResultActivity.this, R.color.black);

        month = getIntent().getStringExtra("month");
        year = getIntent().getStringExtra("babyBothDay");
        name = getIntent().getStringExtra("name");
//        Log.e("month1", month);

        initData();
        initListeners();

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_grow_up_appraisal_result;
    }

    @Override
    public void initView() throws Exception {
        mGrowUpAppraisalResultBank = (LinearLayout) findViewById(R.id.GrowUpAppraisalResult_Bank);
        mGrowUpAppraisalResultTitle = (TextView) findViewById(R.id.GrowUpAppraisalResult_Title);
        mGrowUpAppraisalResultSave = (ImageView) findViewById(R.id.GrowUpAppraisalResult_Save);
        mGrowUpAppraisalResultText1 = (TextView) findViewById(R.id.GrowUpAppraisalResult_Text1);
        mGrowUpAppraisalOneText1 = (TextView) findViewById(R.id.GrowUpAppraisalOne_Text1);
        mGrowUpAppraisalOneText2 = (TextView) findViewById(R.id.GrowUpAppraisalOne_Text2);
        mGrowUpAppraisalTwoText1 = (TextView) findViewById(R.id.GrowUpAppraisalTwo_Text1);
        mGrowUpAppraisalTwoText2 = (TextView) findViewById(R.id.GrowUpAppraisalTwo_Text2);
        mGrowUpAppraisalThreeText1 = (TextView) findViewById(R.id.GrowUpAppraisalThree_Text1);
        mGrowUpAppraisalThreeText2 = (TextView) findViewById(R.id.GrowUpAppraisalThree_Text2);
        mGrowUpAppraisalButton = (TextView) findViewById(R.id.GrowUpAppraisal_Button);
    }

    private void initData() {
        mGrowUpAppraisalResultTitle.setText(name + "");
        Log.e("fdfdfd", (String) SPUtils.get(GrowUpAppraisalResultActivity.this, "babyId", ""));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "136");
        hashMap.put("token", SPUtils.get(GrowUpAppraisalResultActivity.this, "token", ""));
        hashMap.put("babyId", SPUtils.get(GrowUpAppraisalResultActivity.this, "babyId", ""));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<GrowUpAppraisalResultBeans>(GrowUpAppraisalResultActivity.this) {
                    @Override
                    public void onSuccess(GrowUpAppraisalResultBeans mGrowUpAppraisalResultBeans, Call call, Response response) {
                        if (mGrowUpAppraisalResultBeans.getResultCode() == 1) {
                            mGrowUpAppraisalResultText1.setText(mGrowUpAppraisalResultBeans.getData().getGeneralConclusion());

                            mGrowUpAppraisalOneText1.setText(mGrowUpAppraisalResultBeans.getData().getSenseConclusion());
                            mGrowUpAppraisalTwoText1.setText(mGrowUpAppraisalResultBeans.getData().getLanguageConclusion());
                            mGrowUpAppraisalThreeText1.setText(mGrowUpAppraisalResultBeans.getData().getEmotionConclusion());
                            data = mGrowUpAppraisalResultBeans.getData();
//                            mGrowUpAppraisalOneText2.setText(mGrowUpAppraisalResultBeans.getData().getSenseLink());
//                            mGrowUpAppraisalTwoText2.setText(mGrowUpAppraisalResultBeans.getData().getLanguageLink());
//                            mGrowUpAppraisalThreeText2.setText(mGrowUpAppraisalResultBeans.getData().getEmotionLink());

                        } else {
                            App.ErrorToken(mGrowUpAppraisalResultBeans.getResultCode(), mGrowUpAppraisalResultBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
        mGrowUpAppraisalResultBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GrowUpAppraisalResultActivity.this, MainActivity.class));
            }
        });

        mGrowUpAppraisalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GrowUpAppraisalResultActivity.this, GrowUpAppraisalStartActivity.class).putExtra("month", month).putExtra("name", name)/*.putExtra("year", year)*/);
                finish();
            }
        });
        mGrowUpAppraisalOneText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GrowUpAppraisalResultActivity.this, GuideActivity.class);
                intent.putExtra("flag", "4");
                intent.putExtra("htmlUrl", data.getSenseLink());
                startActivity(intent);
            }
        });
        mGrowUpAppraisalTwoText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GrowUpAppraisalResultActivity.this, GuideActivity.class);
                intent.putExtra("flag", "4");
                intent.putExtra("htmlUrl", data.getLanguageLink());
                startActivity(intent);
            }
        });
        mGrowUpAppraisalThreeText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GrowUpAppraisalResultActivity.this, GuideActivity.class);
                intent.putExtra("flag", "4");
                intent.putExtra("htmlUrl", data.getEmotionLink());
                startActivity(intent);
            }
        });
    }
}
