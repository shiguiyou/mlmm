package com.wanquan.mlmmx.mlmm.xmly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.xmly.beans.MyLikeListActivityBeans;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;

import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */

public class MyLikeListAdapter extends BaseAdapter {
    private  List<MyLikeListActivityBeans.DataBean> mList;
    private  Context mContext;
    private LayoutInflater mInflater;
    private String times;
    private XmPlayerManager mPlayerManager;


    public MyLikeListAdapter(Context mContext, List<MyLikeListActivityBeans.DataBean> mList) {
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
        TextView mMyLikeListTitle;
        TextView mMyLikeListTime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_mylike_list, null);
            viewHolder = new ViewHolder();

            viewHolder.mMyLikeListTitle = (TextView) convertView.findViewById(R.id.MyLike_List_Title);
            viewHolder.mMyLikeListTime = (TextView) convertView.findViewById(R.id.MyLike_List_Time);

            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mMyLikeListTitle.setText(mList.get(position).getVoice_title());
        viewHolder.mMyLikeListTime.setText(mList.get(position).getCreate_time());
        return convertView;
    }
}
