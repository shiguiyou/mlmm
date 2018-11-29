package com.wanquan.mlmmx.mlmm.xmly.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */

public class PlayListAdapter extends BaseAdapter {
    private List<Track> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private String times;
    private XmPlayerManager mPlayerManager;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("mm:ss");

    public PlayListAdapter(Context mContext, List<Track> mList) {
        this.mList = mList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        mPlayerManager = XmPlayerManager.getInstance(mContext);
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
        TextView mItemPlayName;
        TextView mItemPlayTime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_play, null);
            viewHolder = new ViewHolder();
            viewHolder.mItemPlayName = (TextView) convertView.findViewById(R.id.item_play_name);
            viewHolder.mItemPlayTime = (TextView) convertView.findViewById(R.id.item_play_time);
            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mItemPlayName.setText(mList.get(position).getTrackTitle());

//        int max = App.mediaPlayer2.getDuration();
//        Log.e("Dddddddd", String.valueOf(max));
//        Log.e("Dddddddd", String.valueOf(mFormatter.format(max)));
//        viewHolder.mItemPlayTime.setText(mFormatter.format(max));
        int duration = mList.get(position).getDuration();
        int size1 = duration / 60;
        int size2 = duration % 60;
        viewHolder.mItemPlayTime.setText(String.valueOf(size1) + ":" + String.valueOf(size2));

//        viewHolder.mItemPlayTime.setText(String.valueOf(mList.get(position).getDownloadTime())+String.valueOf(mList.get(position).geth())+String.valueOf(mList.get(position).getEndTime())+String.valueOf(mList.get(position).getTimeline()));
//        PlayableModel curr = mPlayerManager.getCurrSound();
//        if (mList.get(position).equals(curr)) {
//            viewHolder.content.setBackgroundResource(R.color.selected_bg);
//        } else {
//            viewHolder.content.setBackgroundColor(Color.WHITE);
//        }
        return convertView;
    }
}
