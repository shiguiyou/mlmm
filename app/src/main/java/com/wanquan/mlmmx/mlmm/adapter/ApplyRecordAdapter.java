package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.ApplyBeans;
import com.wanquan.mlmmx.mlmm.beans.ApplyRecordBeans;

import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */

public class ApplyRecordAdapter extends BaseAdapter {
    private List<ApplyRecordBeans.DataBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public ApplyRecordAdapter(Context mContext, List<ApplyRecordBeans.DataBean> mList) {
        this.mList = mList;
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
        TextView mItemRecordApplyName;
        TextView mItemRecordApplyTime;
        ImageView mItemRecordApplyImageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_record_apply, null);
            viewHolder = new ViewHolder();
            //确定没一个店铺布局中显示的内容
            viewHolder.mItemRecordApplyName = (TextView) convertView.findViewById(R.id.item_record_apply_name);
            viewHolder.mItemRecordApplyTime = (TextView) convertView.findViewById(R.id.item_record_apply_time);
            viewHolder.mItemRecordApplyImageView = (ImageView) convertView.findViewById(R.id.item_record_apply_ImageView);
            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示内容
        final ApplyRecordBeans.DataBean mApplyRecordBeans = mList.get(position);
        viewHolder.mItemRecordApplyName.setText("美丽妈妈净化器体验申请");
        viewHolder.mItemRecordApplyTime.setText(mApplyRecordBeans.getApplyTime());
        int applyStatus = mApplyRecordBeans.getApplyStatus();
        if (applyStatus == 0) {
            viewHolder.mItemRecordApplyImageView.setBackground(mContext.getResources().getDrawable(R.mipmap.shenhezhong));
        } else if (applyStatus == 1) {
            viewHolder.mItemRecordApplyImageView.setBackground(mContext.getResources().getDrawable(R.mipmap.success));
        } else {
            viewHolder.mItemRecordApplyImageView.setBackground(mContext.getResources().getDrawable(R.mipmap.bohui));
        }
        return convertView;
    }
}
