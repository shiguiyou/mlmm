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
import com.wanquan.mlmmx.mlmm.beans.AntenatalTimeTableBeans;
import com.wanquan.mlmmx.mlmm.xmly.beans.PlayCollectionBeans;

import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */

public class PlayCollectionAdapter extends BaseAdapter {
    List<PlayCollectionBeans.DataBean> mList;
    Context mContext;
    LayoutInflater mInflater;
    private String times;

    public PlayCollectionAdapter(Context mContext, List<PlayCollectionBeans.DataBean> mList) {
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
        ImageView mItemPlayCollectImageView;
        TextView mItemPlayCollectName;
        TextView mItemPlayCollectSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_play_collect, null);
            viewHolder = new ViewHolder();
            viewHolder.mItemPlayCollectImageView = (ImageView) convertView.findViewById(R.id.item_play_collect_ImageView);
            viewHolder.mItemPlayCollectName = (TextView) convertView.findViewById(R.id.item_play_collect_Name);
            viewHolder.mItemPlayCollectSize = (TextView) convertView.findViewById(R.id.item_play_collect_Size);
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示内容
        final PlayCollectionBeans.DataBean mPlayCollectionBeans = mList.get(position);
//        if (!"1".equals(mPlayCollectionBeans.getAlbum_id())) {
            Glide.with(mContext).load(mPlayCollectionBeans.getAlbum_img()).into(viewHolder.mItemPlayCollectImageView);
            viewHolder.mItemPlayCollectName.setText(mPlayCollectionBeans.getAlbum_title());
            viewHolder.mItemPlayCollectSize.setText(mPlayCollectionBeans.getVoice_count() + "首");
//        }

        return convertView;
    }
}
