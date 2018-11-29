package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.MyDiscountCouponBeans;
import com.wanquan.mlmmx.mlmm.beans.MyIntegralBeans;

import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */

public class MyDiscountCouponAdapter extends BaseAdapter {
    private List<MyDiscountCouponBeans.DataBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public MyDiscountCouponAdapter(Context mContext, List<MyDiscountCouponBeans.DataBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
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

    //缓存布局中的文件
    class ViewHolder {
        RelativeLayout mItemMyDiscountBg;
        RelativeLayout mItemMyDiscountRl;
        LinearLayout mItemMyDiscountLl;
        TextView mItemMyDiscountMoney1;
        TextView mItemMyDiscountMoney2;
        TextView mItemMyDiscountTitle;
        TextView mItemMyIntegralSize;
        TextView mItemMyIntegralTime;
        ImageView mItemMyIntegralImg;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_my_discount, null);
            viewHolder = new ViewHolder();
            //确定没一个店铺布局中显示的内容
            viewHolder.mItemMyDiscountBg = (RelativeLayout) convertView.findViewById(R.id.item_my_discount_bg);
            viewHolder.mItemMyDiscountRl = (RelativeLayout) convertView.findViewById(R.id.item_my_discount_rl);
            viewHolder.mItemMyDiscountLl = (LinearLayout) convertView.findViewById(R.id.item_my_discount_ll);
            viewHolder.mItemMyDiscountMoney1 = (TextView) convertView.findViewById(R.id.item_my_discount_money1);
            viewHolder.mItemMyDiscountMoney2 = (TextView) convertView.findViewById(R.id.item_my_discount_money2);
            viewHolder.mItemMyDiscountTitle = (TextView) convertView.findViewById(R.id.item_my_discount_title);
            viewHolder.mItemMyIntegralSize = (TextView) convertView.findViewById(R.id.item_my_integral_size);
            viewHolder.mItemMyIntegralTime = (TextView) convertView.findViewById(R.id.item_my_integral_Time);
            viewHolder.mItemMyIntegralImg = (ImageView) convertView.findViewById(R.id.item_my_integral_img);
            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mItemMyDiscountMoney1.setText("￥" + mList.get(position).getAmount());
        viewHolder.mItemMyDiscountMoney2.setText("满" + mList.get(position).getMinCost() + "元可用");
        viewHolder.mItemMyDiscountTitle.setText(mList.get(position).getCouponName());
        viewHolder.mItemMyIntegralSize.setText(mList.get(position).getContent());
        viewHolder.mItemMyIntegralTime.setText("有效期：" + mList.get(position).getStartTime() + "~" + mList.get(position).getEndTime());
        if (mList.get(position).getStatus1() == 0) {
            viewHolder.mItemMyIntegralImg.setVisibility(View.GONE);
            viewHolder.mItemMyDiscountBg.setBackground(mContext.getResources().getDrawable(R.mipmap.couponbg));
            viewHolder.mItemMyDiscountMoney1.setTextColor(mContext.getResources().getColor(R.color.dsfdsf));
            viewHolder.mItemMyDiscountMoney2.setTextColor(mContext.getResources().getColor(R.color.dsfdsf));
        } else if (mList.get(position).getStatus1() == 1) {
            viewHolder.mItemMyIntegralImg.setVisibility(View.VISIBLE);
            viewHolder.mItemMyIntegralImg.setBackground(mContext.getResources().getDrawable(R.mipmap.usedcoupon));
            viewHolder.mItemMyDiscountBg.setBackground(mContext.getResources().getDrawable(R.mipmap.huisecoupon));
            viewHolder.mItemMyDiscountMoney1.setTextColor(mContext.getResources().getColor(R.color.hui_s));
            viewHolder.mItemMyDiscountMoney2.setTextColor(mContext.getResources().getColor(R.color.hui_s));
        } else if (mList.get(position).getStatus1() == 2) {
            viewHolder.mItemMyIntegralImg.setVisibility(View.VISIBLE);
            viewHolder.mItemMyIntegralImg.setBackground(mContext.getResources().getDrawable(R.mipmap.invailcoupond));
            viewHolder.mItemMyDiscountBg.setBackground(mContext.getResources().getDrawable(R.mipmap.huisecoupon));
            viewHolder.mItemMyDiscountMoney1.setTextColor(mContext.getResources().getColor(R.color.hui_s));
            viewHolder.mItemMyDiscountMoney2.setTextColor(mContext.getResources().getColor(R.color.hui_s));
        }
        //显示内容
        return convertView;
    }
}
