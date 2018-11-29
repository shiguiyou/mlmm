package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.MoreServiceBeans;

import java.util.List;

/**
 * Created by Administrator on 2018/6/5.
 */

public class MoreServiceGridViewAdapter3 extends BaseAdapter {
    private List<MoreServiceBeans.DataBean.ToolbarList3Bean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private String str;
    private ViewHolder viewHolder;
    private MoreServiceBeans.DataBean.ToolbarList3Bean toolbarList3Bean;

    public MoreServiceGridViewAdapter3(List<MoreServiceBeans.DataBean.ToolbarList3Bean> list, Context context, String str) {
        this.mList = list;
        this.mContext = context;
        this.str = str;
        this.mLayoutInflater = LayoutInflater.from(mContext);
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

    class ViewHolder {
        RelativeLayout mItemMoreServiceRelativeLayout;
        ImageView mItemMoreServiceImageView;
        TextView mItemMoreServiceTextView;
        ImageView mItemMoreServiceAd;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_more_service, null);
            viewHolder = new ViewHolder();

            viewHolder.mItemMoreServiceRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.item_more_service_RelativeLayout);
            viewHolder.mItemMoreServiceImageView = (ImageView) convertView.findViewById(R.id.item_more_service_ImageView);
            viewHolder.mItemMoreServiceTextView = (TextView) convertView.findViewById(R.id.item_more_service_TextView);
            viewHolder.mItemMoreServiceAd = (ImageView) convertView.findViewById(R.id.item_more_service_ad);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        toolbarList3Bean = mList.get(position);

        viewHolder.mItemMoreServiceTextView.setText(toolbarList3Bean.getName());

        if ("1".equals(toolbarList3Bean.getOrder())) {//宝宝相册
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.babyalbum));
            initShow();

        } else if ("2".equals(toolbarList3Bean.getOrder())) {//情感日志
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.diary));
            initShow();

        } else if ("3".equals(toolbarList3Bean.getOrder())) {//发育变化
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.groth));
            initShow();

        } else if ("4".equals(toolbarList3Bean.getOrder())) {//宝宝听听
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.bbtt));
            initShow();

        } else if ("5".equals(toolbarList3Bean.getOrder())) {//亲子游戏
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.childgame));
            initShow();

        } else if ("7".equals(toolbarList3Bean.getOrder())) {//数胎动
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.taidong));
            initShow();

        } else if ("8".equals(toolbarList3Bean.getOrder())) {//产检提醒
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.checkremind));
            initShow();

        } else if ("10".equals(toolbarList3Bean.getOrder())) {//孕期食谱
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.yqsp));
            initShow();

        } else if ("11".equals(toolbarList3Bean.getOrder())) {//看懂B超单
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.bc));
            initShow();

        } else if ("12".equals(toolbarList3Bean.getOrder())) {//亲子任务
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.qzrw));
            initShow();

        } else if ("13".equals(toolbarList3Bean.getOrder())) {//成长曲线
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.growthtest));
            initShow();

        } else if ("14".equals(toolbarList3Bean.getOrder())) {//疫苗提醒
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.vaccine));
            initShow();

        } else if ("15".equals(toolbarList3Bean.getOrder())) {//月子餐
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.yzc));
            initShow();

        } else if ("16".equals(toolbarList3Bean.getOrder())) {//能不能吃
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.food));
            initShow();

        } else if ("6".equals(toolbarList3Bean.getOrder())) {//成长测评
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.chegnzhangceping));
            initShow();

        } else if ("9".equals(toolbarList3Bean.getOrder())) {//知识百科
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.zsbk));
            initShow();

        } else if ("100".equals(toolbarList3Bean.getOrder())) {//更多
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.moreservice));
            initShow();

        }

        return convertView;
    }

    private void initShow() {
        if ("2".equals(str)) {//完成显示
            viewHolder.mItemMoreServiceRelativeLayout.setBackground(mContext.getResources().getDrawable(R.drawable.more_service2));

            Log.e("dfdfdf", toolbarList3Bean.getOptional());
            if ("0".equals(toolbarList3Bean.getOptional())) {
                viewHolder.mItemMoreServiceAd.setVisibility(View.GONE);

            } else if ("1".equals(toolbarList3Bean.getOptional())) {
                viewHolder.mItemMoreServiceAd.setVisibility(View.VISIBLE);
                Log.e("dfdfdfsss", String.valueOf(toolbarList3Bean.getUserId()));
                if (toolbarList3Bean.getUserId() == null) {
                    viewHolder.mItemMoreServiceAd.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.toolsadd));
                } else if (toolbarList3Bean.getUserId() != null) {
                    viewHolder.mItemMoreServiceAd.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.toolsdelete));
                }
            }

        } else if ("1".equals(str)) {//管理显示
            viewHolder.mItemMoreServiceRelativeLayout.setBackground(mContext.getResources().getDrawable(R.drawable.more_service));
            viewHolder.mItemMoreServiceAd.setVisibility(View.GONE);
        }

    }
}


