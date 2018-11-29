package com.wanquan.mlmmx.mlmm.xmly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.xmly.beans.PlayCollectionBeans;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.UpdateBatch;

import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */

public class PlayCollectionAdapter2 extends BaseAdapter {
    List<Album> mList;
    Context mContext;
    LayoutInflater mInflater;
    private String times;

    public PlayCollectionAdapter2(Context mContext, List<Album> mList) {
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
        final Album mAlbum = mList.get(position);
        Glide.with(mContext).load(mAlbum.getCoverUrlLarge()).into(viewHolder.mItemPlayCollectImageView);
        viewHolder.mItemPlayCollectName.setText(mAlbum.getAlbumTitle());
        viewHolder.mItemPlayCollectSize.setText(mAlbum.getIncludeTrackCount() + "首");

        return convertView;
    }
}
