package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentBeans;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentTwoBeans;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/4/13.
 */

public class CircleFragmentTwoListViewAdapter extends BaseAdapter {
    private List<CircleFragmentTwoBeans.DataBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private InterfaceIsCollection mInterfaceIsCollection;


    public CircleFragmentTwoListViewAdapter(List<CircleFragmentTwoBeans.DataBean> mList, Context mContext) {
        this.mContext = mContext;
        this.mList = mList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    public void InterfaceIsCollection(InterfaceIsCollection mInterfaceIsCollection) {
        this.mInterfaceIsCollection = mInterfaceIsCollection;
    }

    public interface InterfaceIsCollection {
        void isCollection(String str);
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
        ImageView mItemCircleFragmentThreeImg;
        TextView mItemCircleFragmentThreeTv1;
        TextView mItemCircleFragmentThreeTv2;
        TextView mItemCircleFragmentThreeTv3;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_circle_fragment_two, null);
            viewHolder = new ViewHolder();

            viewHolder.mItemCircleFragmentThreeImg = (ImageView) convertView.findViewById(R.id.item_circle_fragment_three_img);
            viewHolder.mItemCircleFragmentThreeTv1 = (TextView) convertView.findViewById(R.id.item_circle_fragment_three_tv1);
            viewHolder.mItemCircleFragmentThreeTv2 = (TextView) convertView.findViewById(R.id.item_circle_fragment_three_tv2);
            viewHolder.mItemCircleFragmentThreeTv3 = (TextView) convertView.findViewById(R.id.item_circle_fragment_three_tv3);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(mList.get(position).getImg()).into(viewHolder.mItemCircleFragmentThreeImg);
        viewHolder.mItemCircleFragmentThreeTv1.setText(mList.get(position).getName());
        viewHolder.mItemCircleFragmentThreeTv2.setText(mList.get(position).getDesc());
        viewHolder.mItemCircleFragmentThreeTv3.setText(mList.get(position).getJoinTotal() + "人参与");
        return convertView;
    }
}


