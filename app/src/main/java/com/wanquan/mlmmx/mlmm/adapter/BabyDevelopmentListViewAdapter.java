package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.BabyDevelopmentListViewBeans;
import java.util.List;

/**
 * Created by Administrator on 2017/11/26.
 */

public class BabyDevelopmentListViewAdapter  extends BaseAdapter {
    List<BabyDevelopmentListViewBeans> mList;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public BabyDevelopmentListViewAdapter(List<BabyDevelopmentListViewBeans> list, Context context) {
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
        ImageView mItemDevelopmentImg;
        TextView mItemDevelopmentTv;
        TextView mItemDevelopmentContent;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_development_listview, null);
            viewHolder = new ViewHolder();

            viewHolder.mItemDevelopmentImg = (ImageView) convertView.findViewById(R.id.item_development_img);
            viewHolder.mItemDevelopmentTv = (TextView) convertView.findViewById(R.id.item_development_tv);
            viewHolder.mItemDevelopmentContent = (TextView) convertView.findViewById(R.id.item_development_content);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mItemDevelopmentTv.setText(mList.get(position).getTitle());
        viewHolder.mItemDevelopmentContent.setText(mList.get(position).getContent());

        return convertView;
    }
}



