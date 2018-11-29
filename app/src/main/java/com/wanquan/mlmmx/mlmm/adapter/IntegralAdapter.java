package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.IntegralGainIntegralBeans;

import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */

public class IntegralAdapter extends BaseAdapter {
    private List<IntegralGainIntegralBeans.DataBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private int flag;

    public IntegralAdapter(Context mContext, List<IntegralGainIntegralBeans.DataBean> mList, int flag) {
        this.mList = mList;
        this.flag = flag;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
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
        TextView mItemConsumptionTitle;
        TextView mItemConsumptionTime;
        TextView mItemConsumptionSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_consumption, null);
            viewHolder = new ViewHolder();
            //确定没一个店铺布局中显示的内容
            viewHolder.mItemConsumptionTitle = (TextView) convertView.findViewById(R.id.item_consumption_title);
            viewHolder.mItemConsumptionTime = (TextView) convertView.findViewById(R.id.item_consumption_time);
            viewHolder.mItemConsumptionSize = (TextView) convertView.findViewById(R.id.item_consumption_size);

            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (flag == 1) {
            //显示内容
            if (mList.get(position).getReason().equals("SIGN")) {
                viewHolder.mItemConsumptionTitle.setText("签到");
            } else if (mList.get(position).getReason().equals("REGIST")) {
                viewHolder.mItemConsumptionTitle.setText("注册");
            } else if (mList.get(position).getReason().equals("COMMENTFORRUM")) {
                viewHolder.mItemConsumptionTitle.setText("回帖");
            } else if (mList.get(position).getReason().equals("POSTFORUM")) {
                viewHolder.mItemConsumptionTitle.setText("发帖");
            } else if (mList.get(position).getReason().equals("BINDDEVICE")) {
                viewHolder.mItemConsumptionTitle.setText("设备绑定");
            } else if (mList.get(position).getReason().equals("SHAREDATA")) {
                viewHolder.mItemConsumptionTitle.setText("数据分享");
            } else if (mList.get(position).getReason().equals("SHAREDOWNLOAD")) {
                viewHolder.mItemConsumptionTitle.setText("分享");
            } else if (mList.get(position).getReason().equals("FEEDBACKQuestion")) {
                viewHolder.mItemConsumptionTitle.setText("问题反馈");
            } else if (mList.get(position).getReason().equals("INVITATION")) {
                viewHolder.mItemConsumptionTitle.setText("亲友邀请");
            } else if (mList.get(position).getReason().equals("ADDBABY")) {
                viewHolder.mItemConsumptionTitle.setText("添加宝宝");
            } else if (mList.get(position).getReason().equals("BABYTASK")) {
                viewHolder.mItemConsumptionTitle.setText("亲子任务");
            } else if (mList.get(position).getReason().equals("RECOMMEND")) {
                viewHolder.mItemConsumptionTitle.setText("推荐用户注册");
            } else {
                viewHolder.mItemConsumptionTitle.setText("转盘摇奖");
            }
            viewHolder.mItemConsumptionSize.setText("+" + String.valueOf(mList.get(position).getAmount()));
        } else if (flag == 2) {
            if (mList.get(position).getReason().equals("EXCHANGECOUPON")) {
                viewHolder.mItemConsumptionTitle.setText("兑换优惠券");
            } else {
                viewHolder.mItemConsumptionTitle.setText("转盘摇奖");
            }
            viewHolder.mItemConsumptionSize.setText("-" + String.valueOf(mList.get(position).getAmount()));
        }

        viewHolder.mItemConsumptionTime.setText(mList.get(position).getCreateTime());
        return convertView;
    }
}
