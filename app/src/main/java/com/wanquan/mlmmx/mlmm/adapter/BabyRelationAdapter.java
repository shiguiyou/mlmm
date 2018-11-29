package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.BabyPhoneBeans;

import java.util.List;

//import com.wanquan.mlmmx.mlmm.phone.MyGridView;

/**
 * Created by Administrator on 2017/11/2.
 */

public class BabyRelationAdapter extends BaseAdapter {
    List<String> mList;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public BabyRelationAdapter(List<String> list, Context context) {
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
        TextView mItemBabyRelationTv;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_baby_relation, null);
            viewHolder = new ViewHolder();
            viewHolder.mItemBabyRelationTv = (TextView) convertView.findViewById(R.id.item_baby_relation_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mItemBabyRelationTv.setText(mList.get(position).toString());

        return convertView;
    }
}


