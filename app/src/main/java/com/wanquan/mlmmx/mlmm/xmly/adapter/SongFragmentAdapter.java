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
import com.wanquan.mlmmx.mlmm.xmly.beans.RankingListBeans;
import com.wanquan.mlmmx.mlmm.xmly.fragment.SongFragment;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;
import com.ximalaya.ting.android.opensdk.model.tag.Tag;
import com.ximalaya.ting.android.opensdk.model.tag.TagList;

import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */

public class SongFragmentAdapter extends BaseAdapter {
    List<Album> mList;
    Context mContext;
    LayoutInflater mInflater;
    private String times;

    public SongFragmentAdapter(Context mContext, List<Album> mList) {
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
        ImageView mItemSongIV;
        TextView mItemSongTV;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_song, null);
            viewHolder = new ViewHolder();

            viewHolder.mItemSongIV = (ImageView) convertView.findViewById(R.id.Item_Song_IV);
            viewHolder.mItemSongTV = (TextView) convertView.findViewById(R.id.Item_Song_TV);

            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(mList.get(position).getCoverUrlLarge()).into(viewHolder.mItemSongIV);
        viewHolder.mItemSongTV.setText(mList.get(position).getAlbumTitle());
        return convertView;
    }
}
