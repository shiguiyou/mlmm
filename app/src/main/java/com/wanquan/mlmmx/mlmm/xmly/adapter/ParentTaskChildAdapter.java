package com.wanquan.mlmmx.mlmm.xmly.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.ParentTaskDetailsActivity;
import com.wanquan.mlmmx.mlmm.xmly.beans.ParentTaskBeans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */

public class ParentTaskChildAdapter extends BaseAdapter {
    List<ParentTaskBeans.DataBean.VoBean.ListBean> mList = new ArrayList<>();
    Context mContext;
    LayoutInflater mInflater;

    public ParentTaskChildAdapter(Context mContext, List<ParentTaskBeans.DataBean.VoBean.ListBean> mList) {
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
        RelativeLayout mParentTaskImg1RL;
        ImageView mParentTaskImg1;
        TextView mParentTaskTV;
        ImageView mParentTaskImg2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_child_parent, null);
            viewHolder = new ViewHolder();
            viewHolder.mParentTaskImg1RL = (RelativeLayout) convertView.findViewById(R.id.ParentTask_Img1_RL);
            viewHolder.mParentTaskImg1 = (ImageView) convertView.findViewById(R.id.ParentTask_Img1);
            viewHolder.mParentTaskTV = (TextView) convertView.findViewById(R.id.ParentTask_TV);
            viewHolder.mParentTaskImg2 = (ImageView) convertView.findViewById(R.id.ParentTask_Img2);
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示内容
        final ParentTaskBeans.DataBean.VoBean.ListBean mParentTaskBeans = mList.get(position);
        if (mParentTaskBeans.getDone().equals("0")) {
            viewHolder.mParentTaskImg1.setBackground(mContext.getResources().getDrawable(R.mipmap.parentachiveno));
        } else if (mParentTaskBeans.getDone().equals("1")) {
            viewHolder.mParentTaskImg1.setBackground(mContext.getResources().getDrawable(R.mipmap.parentachived));
        }
        if (mParentTaskBeans.getUrl() != null) {
            viewHolder.mParentTaskImg2.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mParentTaskImg2.setVisibility(View.GONE);
        }
        viewHolder.mParentTaskTV.setText(mParentTaskBeans.getTitle());

        viewHolder.mParentTaskImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ParentTaskDetailsActivity.class);
                intent.putExtra("url", mParentTaskBeans.getUrl());
                intent.putExtra("title", mParentTaskBeans.getTitle());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }
}




