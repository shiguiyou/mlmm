package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.HomeEquipmentBeans;

import java.util.List;


/**
 * Created by Administrator on 2017/9/26.
 */

public class HomeFragmentAdapter extends BaseAdapter {
    List<HomeEquipmentBeans.DataBean> mList;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public HomeFragmentAdapter(List<HomeEquipmentBeans.DataBean> list, Context context) {
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
        RelativeLayout mItemHomeRl;
        ImageView mItemHomeImg;
        TextView mItemHomeName;
        TextView mItemHomeNumber;
        TextView mItemHomeState;
        TextView mItemHomeStates;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_homefragment, null);
            viewHolder = new ViewHolder();
            viewHolder.mItemHomeRl = (RelativeLayout) convertView.findViewById(R.id.Item_Home_Rl);
            viewHolder.mItemHomeImg = (ImageView) convertView.findViewById(R.id.Item_Home_Img);
            viewHolder.mItemHomeName = (TextView) convertView.findViewById(R.id.Item_Home_Name);
            viewHolder.mItemHomeNumber = (TextView) convertView.findViewById(R.id.Item_Home_Number);
            viewHolder.mItemHomeState = (TextView) convertView.findViewById(R.id.Item_Home_State);
            viewHolder.mItemHomeStates = (TextView) convertView.findViewById(R.id.Item_Home_States);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final HomeEquipmentBeans.DataBean mHomeEquipmentBeans = mList.get(position);
        viewHolder.mItemHomeName.setText(mHomeEquipmentBeans.getDeviceName());
        viewHolder.mItemHomeNumber.setText("设备编号" + mHomeEquipmentBeans.getDeviceCode());
        if (mHomeEquipmentBeans.getIsOnLine() == 0) {
            viewHolder.mItemHomeState.setText("不在线");
        } else if (mHomeEquipmentBeans.getIsOnLine() == 1) {
            viewHolder.mItemHomeState.setText("");
        }
        int pm25 = mHomeEquipmentBeans.getPm25Value();
        if (pm25 <= 35 && pm25 > 10) {
            viewHolder.mItemHomeState.setText("空气 ：");
            viewHolder.mItemHomeStates.setText("优");
            viewHolder.mItemHomeStates.setTextColor(mContext.getResources().getColor(R.color.green));
        } else if (pm25 > 35 && pm25 <= 75) {
            viewHolder.mItemHomeState.setText("空气 ：");
            viewHolder.mItemHomeStates.setText("良");
            viewHolder.mItemHomeStates.setTextColor(mContext.getResources().getColor(R.color.horvoice_text_color));
        } else if (pm25 > 75) {
            viewHolder.mItemHomeState.setText("空气 ：");
            viewHolder.mItemHomeStates.setText("差");
            viewHolder.mItemHomeStates.setTextColor(mContext.getResources().getColor(R.color.style_red));
        } else if (pm25 <= 10) {
            viewHolder.mItemHomeState.setText("空气 ：");
            viewHolder.mItemHomeStates.setText("优");
            viewHolder.mItemHomeStates.setTextColor(mContext.getResources().getColor(R.color.green));
        }
        return convertView;
    }
}
