package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
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
import com.wanquan.mlmmx.mlmm.activity.RelativesTeamActivity;
import com.wanquan.mlmmx.mlmm.beans.BabyActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.RelativesListViewBeans;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/11/16.
 */

public class RelativesListViewAdapter extends BaseAdapter {
    List<RelativesListViewBeans.DataBean> mList = new ArrayList<>();
    Context mContext;
    LayoutInflater mLayoutInflater;

    public RelativesListViewAdapter(List<RelativesListViewBeans.DataBean> list, Context context) {
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
        CircleImageView mItemRelativesListviewImg;
        TextView mItemRelativesListviewName;
        TextView mItemRelativesListviewName2;
        TextView mItemRelativesListviewPhone;
        LinearLayout mitem_relatives_listview_ill;
        ImageView mItemRelativesXin;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_relatives_listview, null);
            viewHolder = new ViewHolder();

            viewHolder.mitem_relatives_listview_ill = (LinearLayout) convertView.findViewById(R.id.item_relatives_listview_ill);
            viewHolder.mItemRelativesListviewImg = (CircleImageView) convertView.findViewById(R.id.item_relatives_listview_img);
            viewHolder.mItemRelativesXin = (ImageView) convertView.findViewById(R.id.item_relatives_xin);
            viewHolder.mItemRelativesListviewName = (TextView) convertView.findViewById(R.id.item_relatives_listview_name);
            viewHolder.mItemRelativesListviewName2 = (TextView) convertView.findViewById(R.id.item_relatives_listview_name2);
            viewHolder.mItemRelativesListviewPhone = (TextView) convertView.findViewById(R.id.item_relatives_listview_phone);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mList.get(position).getHead_ico() != null) {
            Glide.with(mContext).load(mList.get(position).getHead_ico()).into(viewHolder.mItemRelativesListviewImg);
        } else {
            viewHolder.mItemRelativesListviewImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
        }
        viewHolder.mItemRelativesListviewName.setText(mList.get(position).getRelationship());

        if (mList.get(position).getNick_name() != null) {
            viewHolder.mItemRelativesListviewName2.setText(mList.get(position).getNick_name());
        } else {
            viewHolder.mItemRelativesListviewName2.setText("");
        }
        viewHolder.mItemRelativesListviewPhone.setText(mList.get(position).getUsername());
        if (SPUtils.get(mContext, "userid", "").equals(String.valueOf(mList.get(position).getId()))) {
            viewHolder.mItemRelativesXin.setVisibility(View.VISIBLE);
        } else if (!SPUtils.get(mContext, "userid", "").equals(String.valueOf(mList.get(position).getId()))) {
            viewHolder.mItemRelativesXin.setVisibility(View.GONE);
        }
        return convertView;
    }
}

