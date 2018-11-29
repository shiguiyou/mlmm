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
import com.wanquan.mlmmx.mlmm.xmly.beans.RankingListBeans;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.ranks.Rank;
import com.ximalaya.ting.android.opensdk.model.ranks.RankAlbumList;

import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */

public class RankingListAdapter extends BaseAdapter {
    List<Album> mList;
    Context mContext;
    LayoutInflater mInflater;
    private String times;

    public RankingListAdapter(Context mContext, List<Album> mList) {
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
        ImageView mItemRankingIv;
        TextView mItemRankingTitle;
        TextView mItemRankingTv1;
        TextView mItemRankingTv2;
        TextView mItemRankingTv3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_ranking_list, null);
            viewHolder = new ViewHolder();

            viewHolder.mItemRankingIv = (ImageView) convertView.findViewById(R.id.item_ranking_iv);
            viewHolder.mItemRankingTitle = (TextView) convertView.findViewById(R.id.item_ranking_title);
            viewHolder.mItemRankingTv1 = (TextView) convertView.findViewById(R.id.item_ranking_tv1);
            viewHolder.mItemRankingTv2 = (TextView) convertView.findViewById(R.id.item_ranking_tv2);
            viewHolder.mItemRankingTv3 = (TextView) convertView.findViewById(R.id.item_ranking_tv3);

            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(mList.get(position).getCoverUrlLarge()).into(viewHolder.mItemRankingIv);
        viewHolder.mItemRankingTitle.setText(mList.get(position).getAlbumTitle());
//        viewHolder.mItemRankingTv1.setText(mList.get(position).getSpeakerTitle());
        viewHolder.mItemRankingTv2.setText(mList.get(position).getAlbumIntro());
//        viewHolder.mItemRankingTv3.setText(mList.get(position).getAlbumTags());
        return convertView;
    }
}
