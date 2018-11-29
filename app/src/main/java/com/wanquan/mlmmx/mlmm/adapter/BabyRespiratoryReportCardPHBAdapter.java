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

import com.bumptech.glide.Glide;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.BabyDevelopmentBeans;
import com.wanquan.mlmmx.mlmm.beans.BabyRespiratoryReportCardPHBBeans;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/11/23.
 */

public class BabyRespiratoryReportCardPHBAdapter extends BaseAdapter {
    private List<BabyRespiratoryReportCardPHBBeans.DataBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private String ownerUserId;

    public BabyRespiratoryReportCardPHBAdapter(Context mContext, List<BabyRespiratoryReportCardPHBBeans.DataBean> mList, String ownerUserId) {
        this.ownerUserId = ownerUserId;
        this.mList = mList;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
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
        ImageView mItemBabyRespiratoryImageView;
        TextView mItemBabyRespiratoryTv;
        CircleImageView mItemBabyRespiratoryCircleImageView;
        TextView mItemBabyRespiratoryName;
        TextView mItemBabyRespiratoryTime;
        TextView mItemBabyRespiratorySize;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_babyrespiratoryreportcard, null);
            viewHolder = new ViewHolder();
            //确定没一个店铺布局中显示的内容

            viewHolder.mItemBabyRespiratoryImageView = (ImageView) convertView.findViewById(R.id.item_BabyRespiratory_ImageView);
            viewHolder.mItemBabyRespiratoryTv = (TextView) convertView.findViewById(R.id.item_BabyRespiratory_tv);
            viewHolder.mItemBabyRespiratoryCircleImageView = (CircleImageView) convertView.findViewById(R.id.item_BabyRespiratory_CircleImageView);
            viewHolder.mItemBabyRespiratoryName = (TextView) convertView.findViewById(R.id.item_BabyRespiratory_Name);
            viewHolder.mItemBabyRespiratoryTime = (TextView) convertView.findViewById(R.id.item_BabyRespiratoryTime);
            viewHolder.mItemBabyRespiratorySize = (TextView) convertView.findViewById(R.id.item_BabyRespiratory_Size);

            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示内容
        final BabyRespiratoryReportCardPHBBeans.DataBean mBabyRespiratoryReportCardPHBBeans = mList.get(position);

        int rownum = mBabyRespiratoryReportCardPHBBeans.getRownum();
        if (position == 0) {
            viewHolder.mItemBabyRespiratoryImageView.setVisibility(View.VISIBLE);
            viewHolder.mItemBabyRespiratoryTv.setVisibility(View.GONE);
            viewHolder.mItemBabyRespiratoryImageView.setBackground(mContext.getResources().getDrawable(R.mipmap.jin));
        } else if (position == 1) {
            viewHolder.mItemBabyRespiratoryImageView.setVisibility(View.VISIBLE);
            viewHolder.mItemBabyRespiratoryTv.setVisibility(View.GONE);
            viewHolder.mItemBabyRespiratoryImageView.setBackground(mContext.getResources().getDrawable(R.mipmap.yin));
        } else if (position == 2) {
            viewHolder.mItemBabyRespiratoryImageView.setVisibility(View.VISIBLE);
            viewHolder.mItemBabyRespiratoryTv.setVisibility(View.GONE);
            viewHolder.mItemBabyRespiratoryImageView.setBackground(mContext.getResources().getDrawable(R.mipmap.tong));
        } else {
            viewHolder.mItemBabyRespiratoryImageView.setVisibility(View.GONE);
            viewHolder.mItemBabyRespiratoryTv.setVisibility(View.VISIBLE);
            if (mList.size() <= 10) {
                if (position == 3) {
                    viewHolder.mItemBabyRespiratoryTv.setText("4");
                } else if (position == 4) {
                    viewHolder.mItemBabyRespiratoryTv.setText("5");
                } else if (position == 5) {
                    viewHolder.mItemBabyRespiratoryTv.setText("6");
                } else if (position == 6) {
                    viewHolder.mItemBabyRespiratoryTv.setText("7");
                } else if (position == 7) {
                    viewHolder.mItemBabyRespiratoryTv.setText("8");
                } else if (position == 8) {
                    viewHolder.mItemBabyRespiratoryTv.setText("9");
                } else if (position == 9) {
                    viewHolder.mItemBabyRespiratoryTv.setText("10");
                }
            } else {
                viewHolder.mItemBabyRespiratoryTv.setText(mBabyRespiratoryReportCardPHBBeans.getRownum() + "");
            }
        }

        Glide.with(mContext).load(mBabyRespiratoryReportCardPHBBeans.getHeadIco()).into(viewHolder.mItemBabyRespiratoryCircleImageView);
        viewHolder.mItemBabyRespiratoryName.setText(mBabyRespiratoryReportCardPHBBeans.getUsername());
        viewHolder.mItemBabyRespiratoryTime.setText(mBabyRespiratoryReportCardPHBBeans.getMessage());
        viewHolder.mItemBabyRespiratorySize.setText(mBabyRespiratoryReportCardPHBBeans.getValue() + "");

        String userid = String.valueOf(SPUtils.get(mContext, "userid", ""));
        if (ownerUserId != null) {
            if (!userid.equals(ownerUserId)) {
                userid = ownerUserId;
            }
        }
        if (userid.equals(mList.get(position).getUserId())) {
            viewHolder.mItemBabyRespiratoryName.setTextColor(mContext.getResources().getColor(R.color.tops));
        }
        return convertView;
    }
}
