package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.HomeGridViewBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeListViewBeans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/16.
 */

public class HomeListViewAdapter extends BaseAdapter {
    List<HomeListViewBeans> mList = new ArrayList<>();
    Context mContext;
    LayoutInflater mLayoutInflater;

    public HomeListViewAdapter(List<HomeListViewBeans> list, Context context) {
        mList = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
//        Log.e("kkkkkkk3", String.valueOf(mList.size()));
    }

    public void setData(List<HomeListViewBeans> list){
        this.mList = list;
        this.notifyDataSetChanged();
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

    class ViewHolder {
        RelativeLayout mItemHomeFragment;
        LinearLayout mItemHomeLl;
        TextView mItemHomeTitle;
        TextView mItemHomeContent;
        TextView mItemHomeTime;
        ImageView mItemHomeImage;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_homefragment_gridview, null);
            viewHolder = new ViewHolder();
            viewHolder.mItemHomeFragment = (RelativeLayout) convertView.findViewById(R.id.item_HomeFragment);
            viewHolder.mItemHomeLl = (LinearLayout) convertView.findViewById(R.id.item_home_ll);
            viewHolder.mItemHomeTitle = (TextView) convertView.findViewById(R.id.item_home_title);
            viewHolder.mItemHomeContent = (TextView) convertView.findViewById(R.id.item_home_content);
            viewHolder.mItemHomeImage = (ImageView) convertView.findViewById(R.id.item_home_image);
            viewHolder.mItemHomeTime = (TextView) convertView.findViewById(R.id.item_home_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(mList.get(position).getType_img()).into(viewHolder.mItemHomeImage);
        viewHolder.mItemHomeTitle.setText(mList.get(position).getTitle());
        viewHolder.mItemHomeContent.setText(mList.get(position).getChannel_name());
        viewHolder.mItemHomeTime.setText(mList.get(position).getDate());

        return convertView;
    }
}

