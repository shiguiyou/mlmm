package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.BabyRoomDialogGridViewBeans;

import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */

public class BabyRoomAdapters extends BaseAdapter {
    List<BabyRoomDialogGridViewBeans.DataBean.CfsBean> mList2;
    Context mContext;
    LayoutInflater mInflater;

    public BabyRoomAdapters(Context mContext, List<BabyRoomDialogGridViewBeans.DataBean.CfsBean> mList2) {
        this.mList2 = mList2;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList2.size();
    }

    @Override
    public Object getItem(int position) {
        return mList2.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //缓存布局中的文件
    class ViewHolder {
        ImageView mItemBabyImg;
        TextView mItemBabyTv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_babyroom, null);
            viewHolder = new ViewHolder();
            //确定没一个店铺布局中显示的内容
            viewHolder.mItemBabyImg = (ImageView) convertView.findViewById(R.id.item_Baby_img);
            viewHolder.mItemBabyTv = (TextView) convertView.findViewById(R.id.item_Baby_tv);
            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示内容
        final BabyRoomDialogGridViewBeans.DataBean.CfsBean mBabyRoomGridViewBeans = mList2.get(position);
        Glide.with(mContext).load(mList2.get(position).getIco()).into(viewHolder.mItemBabyImg);
        viewHolder.mItemBabyTv.setText(mList2.get(position).getName());
        return convertView;
    }
}
