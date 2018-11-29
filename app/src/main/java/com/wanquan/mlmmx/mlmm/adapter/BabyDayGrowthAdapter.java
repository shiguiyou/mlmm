package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.BabyDayGrowthBeans;
import com.wanquan.mlmmx.mlmm.beans.BabyDevelopmentBeans;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */

public class BabyDayGrowthAdapter extends BaseAdapter {
    private List<BabyDayGrowthBeans> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private int syDay;

    public BabyDayGrowthAdapter(Context mContext, List<BabyDayGrowthBeans> mList, int syDay) {
        this.syDay = syDay;
        this.mList = mList;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
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
        ImageView mItemBabydayImg;
        ImageView mItemBabydayImgs;
        TextView mItemBabydayTitle;
        TextView mItemBabydayNow;
        TextView mItemBabydayContent;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_babyday, null);
            viewHolder = new ViewHolder();
            //确定没一个店铺布局中显示的内容
            viewHolder.mItemBabydayImg = (ImageView) convertView.findViewById(R.id.item_babyday_img);
            viewHolder.mItemBabydayImgs = (ImageView) convertView.findViewById(R.id.item_babyday_imgs);
            viewHolder.mItemBabydayTitle = (TextView) convertView.findViewById(R.id.item_babyday_title);
            viewHolder.mItemBabydayNow = (TextView) convertView.findViewById(R.id.item_babyday_now);
            viewHolder.mItemBabydayContent = (TextView) convertView.findViewById(R.id.item_babyday_content);

            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Log.e(":syDay", String.valueOf(syDay));
        if (position == syDay) {
            viewHolder.mItemBabydayNow.setVisibility(View.VISIBLE);
            viewHolder.mItemBabydayImg.setBackground(mContext.getResources().getDrawable(R.mipmap.circlebabys));
            viewHolder.mItemBabydayImgs.setBackground(mContext.getResources().getDrawable(R.color.babysss));
            viewHolder.mItemBabydayTitle.setTextColor(mContext.getResources().getColor(R.color.topss));
            viewHolder.mItemBabydayNow.setTextColor(mContext.getResources().getColor(R.color.topss));
        } else {
            viewHolder.mItemBabydayNow.setVisibility(View.GONE);
            viewHolder.mItemBabydayImg.setBackground(mContext.getResources().getDrawable(R.mipmap.circlebaby));
            viewHolder.mItemBabydayImgs.setBackground(mContext.getResources().getDrawable(R.color.babyss));
            viewHolder.mItemBabydayTitle.setTextColor(mContext.getResources().getColor(R.color.jufdse));
            viewHolder.mItemBabydayNow.setTextColor(mContext.getResources().getColor(R.color.jufdse));
        }
        //显示内容
        final BabyDayGrowthBeans mBabyDayGrowthBeans = mList.get(position);
        if (mBabyDayGrowthBeans.getDay() <= 7) {
            if (mBabyDayGrowthBeans.getDay() == 1) {
                viewHolder.mItemBabydayTitle.setText("孕1天");
            } else if (mBabyDayGrowthBeans.getDay() == 2) {
                viewHolder.mItemBabydayTitle.setText("孕2天");
            } else if (mBabyDayGrowthBeans.getDay() == 3) {
                viewHolder.mItemBabydayTitle.setText("孕3天");
            } else if (mBabyDayGrowthBeans.getDay() == 4) {
                viewHolder.mItemBabydayTitle.setText("孕4天");
            } else if (mBabyDayGrowthBeans.getDay() == 5) {
                viewHolder.mItemBabydayTitle.setText("孕5天");
            } else if (mBabyDayGrowthBeans.getDay() == 6) {
                viewHolder.mItemBabydayTitle.setText("孕6天");
            } else if (mBabyDayGrowthBeans.getDay() == 7) {
                viewHolder.mItemBabydayTitle.setText("孕7天");
            }
        } else if (mBabyDayGrowthBeans.getDay() > 7) {
            int day = mBabyDayGrowthBeans.getDay();
            if (day / 7 == 0) {
                viewHolder.mItemBabydayTitle.setText("孕" + day / 7 + "周");
            } else if (day / 7 > 0) {
                int days = day % 7;
                if (days == 0) {
                    viewHolder.mItemBabydayTitle.setText("孕" + day / 7 + "周");
                } else {
                    viewHolder.mItemBabydayTitle.setText("孕" + day / 7 + "周" + " + " + days + "天");
                }

            }
        }

        viewHolder.mItemBabydayContent.setText(mBabyDayGrowthBeans.getDescription());
        return convertView;
    }
}

