package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.AntenatalTimeTableBeans;
import com.wanquan.mlmmx.mlmm.beans.VoiceListActivityBeans;

import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */

public class VoiceListAdapter extends BaseAdapter {
    List<VoiceListActivityBeans> mList;
    Context mContext;
    LayoutInflater mInflater;
    private String times;

    public VoiceListAdapter(Context mContext, List<VoiceListActivityBeans> mList) {
        this.mList = mList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
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

    //缓存布局中的文件
    class ViewHolder {
        ImageView mItemVoiceListPlay;
        TextView mItemVoiceListDay;
        TextView mItemVoiceListMonth;
        TextView mItemVoiceListTitle;
        TextView mItemVoiceListTime;
        TextView mItemVoiceListSize;
        ImageView mItemVoiceListImg;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_voice_list, null);
            viewHolder = new ViewHolder();

            viewHolder.mItemVoiceListPlay = (ImageView) convertView.findViewById(R.id.Item_VoiceList_Play);
            viewHolder.mItemVoiceListDay = (TextView) convertView.findViewById(R.id.Item_VoiceList_Day);
            viewHolder.mItemVoiceListMonth = (TextView) convertView.findViewById(R.id.Item_VoiceList_Month);
            viewHolder.mItemVoiceListTitle = (TextView) convertView.findViewById(R.id.Item_VoiceList_Title);
            viewHolder.mItemVoiceListTime = (TextView) convertView.findViewById(R.id.Item_VoiceList_Time);
            viewHolder.mItemVoiceListSize = (TextView) convertView.findViewById(R.id.Item_VoiceList_Size);
            viewHolder.mItemVoiceListImg = (ImageView) convertView.findViewById(R.id.Item_VoiceList_Img);


            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示内容
        final VoiceListActivityBeans mVoiceListActivityBeans = mList.get(position);

        return convertView;
    }
}
