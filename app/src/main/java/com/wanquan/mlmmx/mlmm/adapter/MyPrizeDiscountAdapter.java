package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.MyPrizeDiscountFragmentBeans;
import com.wanquan.mlmmx.mlmm.beans.MyPrizeIntegralFragmentBeans;

import java.util.List;

//import com.wanquan.mlmmx.mlmm.phone.MyGridView;

/**
 * 描述：我的奖品优惠券适配器和我的奖品商品共用
 * Created by Administrator on 2017/11/2.
 */

public class MyPrizeDiscountAdapter extends BaseAdapter {
    List<MyPrizeDiscountFragmentBeans.DataBean> mList;
    Context mContext;
    LayoutInflater mLayoutInflater;
    int size;

    public MyPrizeDiscountAdapter(List<MyPrizeDiscountFragmentBeans.DataBean> list, Context context, int size) {
        this.mList = list;
        this.mContext = context;
        this.size = size;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        LinearLayout mItemMyprizeDiscointLinearLayout;
        TextView mItemMyprizeDiscointMoney;
        TextView mItemMyprizeDiscointTextView;
        ImageView mItemMyprizeDiscointImg;
        TextView mItemMyprizeDiscointTv1;
        TextView mItemMyprizeDiscointTv2;
        TextView mItemMyprizeDiscointTv3;
        LinearLayout mItemMyprizeDiscointLinearLayout2;
        TextView mItemMyprizeDiscointTv4;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_myprize_discoint, null);
            viewHolder = new ViewHolder();

            viewHolder.mItemMyprizeDiscointLinearLayout = (LinearLayout) convertView.findViewById(R.id.item_myprize_discoint_LinearLayout);
            viewHolder.mItemMyprizeDiscointMoney = (TextView) convertView.findViewById(R.id.item_myprize_discoint_money);
            viewHolder.mItemMyprizeDiscointTextView = (TextView) convertView.findViewById(R.id.item_myprize_discoint_TextView);
            viewHolder.mItemMyprizeDiscointImg = (ImageView) convertView.findViewById(R.id.item_myprize_discoint_img);
            viewHolder.mItemMyprizeDiscointTv1 = (TextView) convertView.findViewById(R.id.item_myprize_discoint_tv1);
            viewHolder.mItemMyprizeDiscointTv2 = (TextView) convertView.findViewById(R.id.item_myprize_discoint_tv2);
            viewHolder.mItemMyprizeDiscointTv3 = (TextView) convertView.findViewById(R.id.item_myprize_discoint_tv3);
            viewHolder.mItemMyprizeDiscointLinearLayout2 = (LinearLayout) convertView.findViewById(R.id.item_myprize_discoint_LinearLayout2);
            viewHolder.mItemMyprizeDiscointTv4 = (TextView) convertView.findViewById(R.id.item_myprize_discoint_tv4);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MyPrizeDiscountFragmentBeans.DataBean dataBean = mList.get(position);
        if (size == 1) {
            viewHolder.mItemMyprizeDiscointLinearLayout.setVisibility(View.VISIBLE);
            viewHolder.mItemMyprizeDiscointLinearLayout2.setVisibility(View.GONE);
            viewHolder.mItemMyprizeDiscointTv3.setVisibility(View.VISIBLE);

            viewHolder.mItemMyprizeDiscointMoney.setText("" + dataBean.getCost());
            viewHolder.mItemMyprizeDiscointTextView.setText("满" + dataBean.getUserCoupon().get(0).getMinCost() + "使用");
            viewHolder.mItemMyprizeDiscointTv1.setText(dataBean.getLotteryName());
            viewHolder.mItemMyprizeDiscointTv2.setText("有效使用日期：" + dataBean.getUserCoupon().get(0).getEndTime());
            viewHolder.mItemMyprizeDiscointTv3.setText("抽中时间：" + dataBean.getUserCoupon().get(0).getStartTime());
        } else {
            viewHolder.mItemMyprizeDiscointLinearLayout.setVisibility(View.GONE);
            viewHolder.mItemMyprizeDiscointLinearLayout2.setVisibility(View.VISIBLE);
            viewHolder.mItemMyprizeDiscointTv3.setVisibility(View.GONE);
            viewHolder.mItemMyprizeDiscointImg.setVisibility(View.VISIBLE);

            if ("呼吸口罩".equals(dataBean.getLotteryName())) {
                viewHolder.mItemMyprizeDiscointImg.setBackground(mContext.getResources().getDrawable(R.mipmap.kouzhao));
            } else if ("空气小秘".equals(dataBean.getLotteryName())) {
                viewHolder.mItemMyprizeDiscointImg.setBackground(mContext.getResources().getDrawable(R.mipmap.devicexiaomi));
            }

            viewHolder.mItemMyprizeDiscointTv1.setText(dataBean.getLotteryName());
            viewHolder.mItemMyprizeDiscointTv2.setText("抽中时间：" + dataBean.getCreateTime());
            viewHolder.mItemMyprizeDiscointTv4.setText("" + dataBean.getPrice());
        }

        return convertView;
    }
}


