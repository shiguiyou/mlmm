package com.wanquan.mlmmx.mlmm.xmly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.xmly.beans.PlayCollectionBeans;
import com.wanquan.mlmmx.mlmm.xmly.beans.PlayHistoryBeans;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */

public class PlayHistoryAdapter extends BaseAdapter {
    private List<Track> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private String times;

    public PlayHistoryAdapter(Context mContext, List<Track> mList) {
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

        ImageView mItemPlayHistoryImg;
        TextView mItemPlayHistoryName;
        TextView mItemPlayHistoryTime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_play_history, null);
            viewHolder = new ViewHolder();

            viewHolder.mItemPlayHistoryImg = (ImageView) convertView.findViewById(R.id.item_play_history_img);
            viewHolder.mItemPlayHistoryName = (TextView) convertView.findViewById(R.id.item_play_history_name);
            viewHolder.mItemPlayHistoryTime = (TextView) convertView.findViewById(R.id.item_play_history_time);

            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示内容
        final Track mTrack = mList.get(position);
        Glide.with(mContext).load(mList.get(position).getCoverUrlLarge()).into(viewHolder.mItemPlayHistoryImg);
        viewHolder.mItemPlayHistoryName.setText(mTrack.getTrackTitle());
        viewHolder.mItemPlayHistoryTime.setText(mTrack.getEndTime());
        return convertView;
    }
}
