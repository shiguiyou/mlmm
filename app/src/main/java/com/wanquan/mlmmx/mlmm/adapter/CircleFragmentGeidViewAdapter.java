package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentBeans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */

public class CircleFragmentGeidViewAdapter extends BaseAdapter {
    List<CircleFragmentBeans.DataBean> mList;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public CircleFragmentGeidViewAdapter(List<CircleFragmentBeans.DataBean> list, Context context) {
        mList = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
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
        ImageView mItemCircleGridViewImageView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_circle_gridview_fragment, null);
            viewHolder = new ViewHolder();
            viewHolder.mItemCircleGridViewImageView = (ImageView) convertView.findViewById(R.id.item_circle_gridView_ImageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        Log.e("RRRRRR", mList.get(position).getImg() + "DDDDD");
        if (mList.get(position).getImg() != null) {
            String img = mList.get(position).getImg();
            String[] imgs = img.split("\\|");
//            List<String> mList2 = new ArrayList<>();
//            for (int i = 0; i < imgs.length; i++) {
//                mList2.add(imgs[i]);
                Glide.with(mContext).load(imgs).into(viewHolder.mItemCircleGridViewImageView);
//                Log.e("yyyyyyyy", String.valueOf(imgs));
//            }
        }


        return convertView;
    }
}



