package com.wanquan.mlmmx.mlmm.fragment;

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

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentThreeBeans;

import java.util.List;

/**
 * Created by xcfchangfeng on 2017/5/18.
 */

public class CircleFragmentThreeLiftAdapter extends BaseAdapter {
    private List<CircleFragmentThreeBeans.DataBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ViewHolder viewHolder;
    private int positions;


    public CircleFragmentThreeLiftAdapter(List<CircleFragmentThreeBeans.DataBean> list, Context context, int positions) {
        this.mList = list;
        this.mContext = context;
        this.positions = positions;
        this.mLayoutInflater = LayoutInflater.from(mContext);
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
        RelativeLayout mCircleThreeLiftItemLinearLayout;
        ImageView mCircleThreeLiftItemImageView;
        TextView mCircleThreeLiftItemTextView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_circle_three_lift, null);
            viewHolder = new ViewHolder();

            viewHolder.mCircleThreeLiftItemLinearLayout = (RelativeLayout) convertView.findViewById(R.id.circle_three_lift_Item_LinearLayout);
            viewHolder.mCircleThreeLiftItemImageView = (ImageView) convertView.findViewById(R.id.circle_three_lift_Item_ImageView);
            viewHolder.mCircleThreeLiftItemTextView = (TextView) convertView.findViewById(R.id.circle_three_lift_Item_TextView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position == positions) {
            viewHolder.mCircleThreeLiftItemLinearLayout.setBackground(mContext.getResources().getDrawable(R.color.white));
            viewHolder.mCircleThreeLiftItemImageView.setVisibility(View.VISIBLE);
            viewHolder.mCircleThreeLiftItemTextView.setTextColor(mContext.getResources().getColor(R.color.topssfsdfdss));
        } else {
            viewHolder.mCircleThreeLiftItemLinearLayout.setBackground(mContext.getResources().getDrawable(R.color.light_gredddy));
            viewHolder.mCircleThreeLiftItemImageView.setVisibility(View.INVISIBLE);
            viewHolder.mCircleThreeLiftItemTextView.setTextColor(mContext.getResources().getColor(R.color.black));
        }
        viewHolder.mCircleThreeLiftItemTextView.setText(mList.get(position).getName());

        return convertView;
    }
}

