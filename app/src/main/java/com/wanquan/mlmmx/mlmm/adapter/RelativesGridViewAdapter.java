package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.BabyActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.RelativesGridViewBeans;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/11/16.
 */

public class RelativesGridViewAdapter extends BaseAdapter {
    List<String> mList = new ArrayList<>();
    Context mContext;
    LayoutInflater mLayoutInflater;

    public RelativesGridViewAdapter(List<String> list, Context context) {
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
        TextView mItemRelativesGridviewTextView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_relatives_gridview, null);
            viewHolder = new ViewHolder();
            viewHolder.mItemRelativesGridviewTextView = (TextView) convertView.findViewById(R.id.item_relatives_gridview_TextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mItemRelativesGridviewTextView.setText("+" + mList.get(position).toString());

        return convertView;
    }
}

