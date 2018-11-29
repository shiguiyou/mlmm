package com.wanquan.mlmmx.mlmm.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;

/**
 * 描述：奖品详情
 * 作者：薛昌峰
 * 时间：2018.06.29
 */
public class PrizeDetailsActivity extends BaseActivity {
    private LinearLayout mAppTitleBank;
    private TextView mAppTitleName;
    private TextView mAppTitleSave;
    private ImageView mPrizeDetailsActivityImg;
    private TextView mPrizeDetailsActivityTv1;
    private TextView mPrizeDetailsActivityTv2;
    private LinearLayout mPrizeDetailsActivityLinearLayout2;
    private TextView mPrizeDetailsActivityTv3;
    private TextView mPrizeDetailsActivityTv4;
    private TextView mPrizeDetailsActivityTv5;
    private String contact;
    private String createTime;
    private String lotteryName;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(PrizeDetailsActivity.this, R.color.tops);

        contact = getIntent().getStringExtra("contact");
        createTime = getIntent().getStringExtra("createTime");
        lotteryName = getIntent().getStringExtra("lotteryName");
        price = getIntent().getStringExtra("price");

        initData();
        initListeners();

    }

    private void initListeners() {
        mAppTitleBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        mAppTitleName.setText("商品详情");
        if ("呼吸口罩".equals(lotteryName)) {
            mPrizeDetailsActivityImg.setBackground(getResources().getDrawable(R.mipmap.kouzhao));
        } else if ("空气小秘".equals(lotteryName)) {
            mPrizeDetailsActivityImg.setBackground(getResources().getDrawable(R.mipmap.devicexiaomi));
        }
        mPrizeDetailsActivityTv1.setText(lotteryName + "");
        mPrizeDetailsActivityTv2.setText("抽中时间：" + createTime);
        mPrizeDetailsActivityTv3.setText(price + "");

        String[] ts = contact.split("\\|");

        mPrizeDetailsActivityTv4.setText(ts[0] + "");
        mPrizeDetailsActivityTv5.setText(ts[1] + "");
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_prize_details;
    }

    @Override
    public void initView() throws Exception {
        mAppTitleBank = (LinearLayout) findViewById(R.id.App_Title_Bank);
        mAppTitleName = (TextView) findViewById(R.id.App_Title_Name);
        mAppTitleSave = (TextView) findViewById(R.id.App_Title_Save);
        mPrizeDetailsActivityImg = (ImageView) findViewById(R.id.PrizeDetailsActivity_img);
        mPrizeDetailsActivityTv1 = (TextView) findViewById(R.id.PrizeDetailsActivity_tv1);
        mPrizeDetailsActivityTv2 = (TextView) findViewById(R.id.PrizeDetailsActivity_tv2);
        mPrizeDetailsActivityLinearLayout2 = (LinearLayout) findViewById(R.id.PrizeDetailsActivity_LinearLayout2);
        mPrizeDetailsActivityTv3 = (TextView) findViewById(R.id.PrizeDetailsActivity_tv3);
        mPrizeDetailsActivityTv4 = (TextView) findViewById(R.id.PrizeDetailsActivity_tv4);
        mPrizeDetailsActivityTv5 = (TextView) findViewById(R.id.PrizeDetailsActivity_tv5);
    }
}
