package com.wanquan.mlmmx.mlmm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.BabyRespiratoryReportCardPHBAdapter;
import com.wanquan.mlmmx.mlmm.beans.BabyRespiratoryReportCardBeans;
import com.wanquan.mlmmx.mlmm.beans.BabyRespiratoryReportCardPHBBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：宝宝呼吸单周排行榜
 * 作者：薛昌峰
 * 时间：2018.11.02
 */
public class BabyRespiratoryReportCardPHBActivity extends BaseActivity {
    private BabyRespiratoryReportCardPHBAdapter mBabyRespiratoryReportCardPHBAdapter;
    private List<BabyRespiratoryReportCardPHBBeans.DataBean> mList = new ArrayList();
    private String id;

    private ListView mBabyRespiratoryReportCardPHBListView;
    private LinearLayout mBabyRespiratoryReportCardPHBLL;
    private ImageView mBabyRespiratoryReportCardPHBImageView;
    private TextView mBabyRespiratoryReportCardPHBTV;
    private CircleImageView mBabyRespiratoryReportCardPHBCircleImageView;
    private TextView mBabyRespiratoryReportCardPHBName;
    private TextView mBabyRespiratoryReportCardPHBTime;
    private TextView mBabyRespiratoryReportCardPHBSize;
    private String ownerUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getIntent().getStringExtra("id");
        ownerUserId = getIntent().getStringExtra("ownerUserId");

        mBabyRespiratoryReportCardPHBAdapter = new BabyRespiratoryReportCardPHBAdapter(this, mList, ownerUserId);
        mBabyRespiratoryReportCardPHBListView.setAdapter(mBabyRespiratoryReportCardPHBAdapter);

        initData();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_baby_respiratory_report_card_phb;
    }

    @Override
    public void initView() throws Exception {
        mBabyRespiratoryReportCardPHBListView = (ListView) findViewById(R.id.BabyRespiratoryReportCardPHBListView);
        mBabyRespiratoryReportCardPHBLL = (LinearLayout) findViewById(R.id.BabyRespiratoryReportCardPHB_LL);
        mBabyRespiratoryReportCardPHBImageView = (ImageView) findViewById(R.id.BabyRespiratoryReportCardPHB_ImageView);
        mBabyRespiratoryReportCardPHBTV = (TextView) findViewById(R.id.BabyRespiratoryReportCardPHB_TV);
        mBabyRespiratoryReportCardPHBCircleImageView = (CircleImageView) findViewById(R.id.BabyRespiratoryReportCardPHB_CircleImageView);
        mBabyRespiratoryReportCardPHBName = (TextView) findViewById(R.id.BabyRespiratoryReportCardPHB_Name);
        mBabyRespiratoryReportCardPHBTime = (TextView) findViewById(R.id.BabyRespiratoryReportCardPHB_Time);
        mBabyRespiratoryReportCardPHBSize = (TextView) findViewById(R.id.BabyRespiratoryReportCardPHB_Size);
    }

    private void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "141");
        hashMap.put("token", SPUtils.get(BabyRespiratoryReportCardPHBActivity.this, "token", ""));
        hashMap.put("deviceId", id);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<BabyRespiratoryReportCardPHBBeans>(BabyRespiratoryReportCardPHBActivity.this) {
                    @Override
                    public void onSuccess(BabyRespiratoryReportCardPHBBeans mBabyRespiratoryReportCardPHBBeans, Call call, Response response) {
                        if (mBabyRespiratoryReportCardPHBBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mBabyRespiratoryReportCardPHBBeans.getData());
                            mBabyRespiratoryReportCardPHBAdapter.notifyDataSetChanged();
                            if (mList.size() > 10) {
                                mBabyRespiratoryReportCardPHBLL.setVisibility(View.VISIBLE);

                                mBabyRespiratoryReportCardPHBTV.setText(mBabyRespiratoryReportCardPHBBeans.getData().get(10).getRownum() + "");
                                Glide.with(BabyRespiratoryReportCardPHBActivity.this).load(mBabyRespiratoryReportCardPHBBeans.getData().get(10).getHeadIco()).into(mBabyRespiratoryReportCardPHBCircleImageView);
                                mBabyRespiratoryReportCardPHBName.setText(mBabyRespiratoryReportCardPHBBeans.getData().get(10).getUsername());
                                mBabyRespiratoryReportCardPHBTime.setText(mBabyRespiratoryReportCardPHBBeans.getData().get(10).getMessage());
                                mBabyRespiratoryReportCardPHBSize.setText(mBabyRespiratoryReportCardPHBBeans.getData().get(10).getValue() + "");

                            } else {
                                mBabyRespiratoryReportCardPHBLL.setVisibility(View.GONE);
                            }
                        } else {
                            App.ErrorToken(mBabyRespiratoryReportCardPHBBeans.getResultCode(), mBabyRespiratoryReportCardPHBBeans.getMsg());
                        }
                    }
                });
    }

    public void BabyRespiratoryReportCardPHBActivity_Bank(View view) {
        finish();
    }
}
