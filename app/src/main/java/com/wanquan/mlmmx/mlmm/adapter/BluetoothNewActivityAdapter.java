package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.BluetoothNewActivityBeans;
import java.util.List;

/**
 * Created by Administrator on 2018/5/11.
 */

public class BluetoothNewActivityAdapter extends BaseAdapter {
    List<BluetoothNewActivityBeans> mList;
    Context mContext;
    LayoutInflater mInflater;

    public BluetoothNewActivityAdapter(Context mContext, List<BluetoothNewActivityBeans> mList) {
        this.mList = mList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
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

    //缓存布局中的文件
    class ViewHolder {
        TextView mItemBleAdapterName;
        TextView mItemBleAdapterSrc;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_bluetooth, null);
            viewHolder = new ViewHolder();
            //确定没一个店铺布局中显示的内容
            viewHolder.mItemBleAdapterName = (TextView) convertView.findViewById(R.id.item_ble_adapter_name);
            viewHolder.mItemBleAdapterSrc = (TextView) convertView.findViewById(R.id.item_ble_adapter_src);

            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mItemBleAdapterName.setText(mList.get(position).getName());

        return convertView;
    }
}
