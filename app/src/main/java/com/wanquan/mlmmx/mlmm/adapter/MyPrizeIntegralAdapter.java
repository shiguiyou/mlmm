package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.MyPrizeIntegralFragmentBeans;

import java.util.List;

//import com.wanquan.mlmmx.mlmm.phone.MyGridView;

/**
 * Created by Administrator on 2017/11/2.
 */

public class MyPrizeIntegralAdapter extends BaseAdapter {
    List<MyPrizeIntegralFragmentBeans.DataBean> mList;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public MyPrizeIntegralAdapter(List<MyPrizeIntegralFragmentBeans.DataBean> list, Context context) {
        mList = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
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
        TextView mItemMyprizeIntegralTextView;
        TextView mItemMyprizeIntegralTime;
        TextView mItemMyprizeIntegralNum;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_myprize_integral, null);
            viewHolder = new ViewHolder();

            viewHolder.mItemMyprizeIntegralTextView = (TextView) convertView.findViewById(R.id.item_myprize_integral_TextView);
            viewHolder.mItemMyprizeIntegralTime = (TextView) convertView.findViewById(R.id.item_myprize_integral_time);
            viewHolder.mItemMyprizeIntegralNum = (TextView) convertView.findViewById(R.id.item_myprize_integral_num);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mItemMyprizeIntegralTextView.setText("转盘抽奖");
        viewHolder.mItemMyprizeIntegralTime.setText(mList.get(position).getCreateTime());
        viewHolder.mItemMyprizeIntegralNum.setText("+" + mList.get(position).getCost());

        return convertView;
    }
}


