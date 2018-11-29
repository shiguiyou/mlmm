package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.StrainerInformationBeans;

import java.util.List;

/**
 * Created by Administrator on 2017/10/20.
 */

public class StrainerInformationAdapter extends BaseAdapter {
    List<StrainerInformationBeans.DataBean> mList;
    Context mContext;
    LayoutInflater mInflater;

    public StrainerInformationAdapter(Context mContext, List<StrainerInformationBeans.DataBean> mList) {
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
        TextView mItemStrainerTv1;
        TextView mItemStrainerTv2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_strainer, null);
            viewHolder = new ViewHolder();
            //确定没一个店铺布局中显示的内容
            viewHolder.mItemStrainerTv1 = (TextView) convertView.findViewById(R.id.Item_Strainer_Tv1);
            viewHolder.mItemStrainerTv2 = (TextView) convertView.findViewById(R.id.Item_Strainer_Tv2);

            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示内容
        viewHolder.mItemStrainerTv1.setText(mList.get(position).getFilterName());
        viewHolder.mItemStrainerTv2.setText("剩余"+ mList.get(position).getUnusedTime() + "小时");

        return convertView;
    }
}
