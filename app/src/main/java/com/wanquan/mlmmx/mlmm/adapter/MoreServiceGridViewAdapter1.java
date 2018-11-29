package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import com.wanquan.mlmmx.mlmm.beans.BabyPhoneBeans;
import com.wanquan.mlmmx.mlmm.beans.MoreServiceBeans;

import java.util.List;

/**
 * Created by Administrator on 2018/6/5.
 */

public class MoreServiceGridViewAdapter1 extends BaseAdapter {
    private List<MoreServiceBeans.DataBean.ToolbarList1Bean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private String str;
    private MoreServiceBeans.DataBean.ToolbarList1Bean toolbarList1Bean;
    private ViewHolder viewHolder;

    public MoreServiceGridViewAdapter1(List<MoreServiceBeans.DataBean.ToolbarList1Bean> list, Context context, String str) {
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
        toolbarList1Bean = mList.get(position);

        viewHolder.mItemMoreServiceTextView.setText(toolbarList1Bean.getName());

        if ("1".equals(toolbarList1Bean.getOrder())) {//宝宝相册
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.babyalbum));
            initShow();
            //为加减图标赋值，让其可以进行点击判断
            if (toolbarList1Bean.getUserId() == null) {
                mList.get(position).setFlag(false);
            } else if (toolbarList1Bean.getUserId() != null) {
                mList.get(position).setFlag(true);
            }

        } else if ("2".equals(toolbarList1Bean.getOrder())) {//情感日志
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.diary));
            initShow();
            //为加减图标赋值，让其可以进行点击判断
            if (toolbarList1Bean.getUserId() == null) {
                mList.get(position).setFlag(false);
            } else if (toolbarList1Bean.getUserId() != null) {
                mList.get(position).setFlag(true);
            }

        } else if ("3".equals(toolbarList1Bean.getOrder())) {//发育变化
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.groth));
            initShow();
            //为加减图标赋值，让其可以进行点击判断
            if (toolbarList1Bean.getUserId() == null) {
                mList.get(position).setFlag(false);
            } else if (toolbarList1Bean.getUserId() != null) {
                mList.get(position).setFlag(true);
            }

        } else if ("4".equals(toolbarList1Bean.getOrder())) {//宝宝听听
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.bbtt));
            initShow();
            //为加减图标赋值，让其可以进行点击判断
            if (toolbarList1Bean.getUserId() == null) {
                mList.get(position).setFlag(false);
            } else if (toolbarList1Bean.getUserId() != null) {
                mList.get(position).setFlag(true);
            }

        } else if ("5".equals(toolbarList1Bean.getOrder())) {//亲子游戏
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.childgame));
            initShow();
            //为加减图标赋值，让其可以进行点击判断
            if (toolbarList1Bean.getUserId() == null) {
                mList.get(position).setFlag(false);
            } else if (toolbarList1Bean.getUserId() != null) {
                mList.get(position).setFlag(true);
            }

        } else if ("7".equals(toolbarList1Bean.getOrder())) {//数胎动
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.taidong));
            initShow();
            //为加减图标赋值，让其可以进行点击判断
            if (toolbarList1Bean.getUserId() == null) {
                mList.get(position).setFlag(false);
            } else if (toolbarList1Bean.getUserId() != null) {
                mList.get(position).setFlag(true);
            }

        } else if ("8".equals(toolbarList1Bean.getOrder())) {//产检提醒
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.checkremind));
            initShow();
            //为加减图标赋值，让其可以进行点击判断
            if (toolbarList1Bean.getUserId() == null) {
                mList.get(position).setFlag(false);
            } else if (toolbarList1Bean.getUserId() != null) {
                mList.get(position).setFlag(true);
            }
        } else if ("10".equals(toolbarList1Bean.getOrder())) {//孕期食谱
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.yqsp));
            initShow();
            //为加减图标赋值，让其可以进行点击判断
            if (toolbarList1Bean.getUserId() == null) {
                mList.get(position).setFlag(false);
            } else if (toolbarList1Bean.getUserId() != null) {
                mList.get(position).setFlag(true);
            }
        } else if ("11".equals(toolbarList1Bean.getOrder())) {//看懂B超单
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.bc));
            initShow();
            //为加减图标赋值，让其可以进行点击判断
            if (toolbarList1Bean.getUserId() == null) {
                mList.get(position).setFlag(false);
            } else if (toolbarList1Bean.getUserId() != null) {
                mList.get(position).setFlag(true);
            }
        } else if ("12".equals(toolbarList1Bean.getOrder())) {//亲子任务
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.qzrw));
            initShow();
            //为加减图标赋值，让其可以进行点击判断
            if (toolbarList1Bean.getUserId() == null) {
                mList.get(position).setFlag(false);
            } else if (toolbarList1Bean.getUserId() != null) {
                mList.get(position).setFlag(true);
            }
        } else if ("13".equals(toolbarList1Bean.getOrder())) {//成长曲线
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.growthtest));
            initShow();
            //为加减图标赋值，让其可以进行点击判断
            if (toolbarList1Bean.getUserId() == null) {
                mList.get(position).setFlag(false);
            } else if (toolbarList1Bean.getUserId() != null) {
                mList.get(position).setFlag(true);
            }
        } else if ("14".equals(toolbarList1Bean.getOrder())) {//疫苗提醒
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.vaccine));
            initShow();
            //为加减图标赋值，让其可以进行点击判断
            if (toolbarList1Bean.getUserId() == null) {
                mList.get(position).setFlag(false);
            } else if (toolbarList1Bean.getUserId() != null) {
                mList.get(position).setFlag(true);
            }
        } else if ("15".equals(toolbarList1Bean.getOrder())) {//月子餐
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.yzc));
            initShow();
            //为加减图标赋值，让其可以进行点击判断
            if (toolbarList1Bean.getUserId() == null) {
                mList.get(position).setFlag(false);
            } else if (toolbarList1Bean.getUserId() != null) {
                mList.get(position).setFlag(true);
            }
        } else if ("16".equals(toolbarList1Bean.getOrder())) {//能不能吃
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.food));
            initShow();
            //为加减图标赋值，让其可以进行点击判断
            if (toolbarList1Bean.getUserId() == null) {
                mList.get(position).setFlag(false);
            } else if (toolbarList1Bean.getUserId() != null) {
                mList.get(position).setFlag(true);
            }
        } else if ("6".equals(toolbarList1Bean.getOrder())) {//成长评测
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.chegnzhangceping));
            initShow();
            //为加减图标赋值，让其可以进行点击判断
            if (toolbarList1Bean.getUserId() == null) {
                mList.get(position).setFlag(false);
            } else if (toolbarList1Bean.getUserId() != null) {
                mList.get(position).setFlag(true);
            }
        } else if ("9".equals(toolbarList1Bean.getOrder())) {//知识百科
            viewHolder.mItemMoreServiceImageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.zsbk));
            initShow();
            //为加减图标赋值，让其可以进行点击判断
            if (toolbarList1Bean.getUserId() == null) {
                mList.get(position).setFlag(false);
            } else if (toolbarList1Bean.getUserId() != null) {
                mList.get(position).setFlag(true);
            }
        }

        return convertView;
    }

    private void initShow() {
        if ("2".equals(str)) {//完成显示
            viewHolder.mItemMoreServiceRelativeLayout.setBackground(mContext.getResources().getDrawable(R.drawable.more_service2));

            if ("0".equals(toolbarList1Bean.getOptional())) {
                viewHolder.mItemMoreServiceAd.setVisibility(View.GONE);

            } else if ("1".equals(toolbarList1Bean.getOptional())) {
                viewHolder.mItemMoreServiceAd.setVisibility(View.VISIBLE);

                if (toolbarList1Bean.getUserId() == null) {
                    viewHolder.mItemMoreServiceAd.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.toolsadd));
                } else if (toolbarList1Bean.getUserId() != null) {
                    viewHolder.mItemMoreServiceAd.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.toolsdelete));
                }
            }
        } else if ("1".equals(str)) {//管理显示
            viewHolder.mItemMoreServiceRelativeLayout.setBackground(mContext.getResources().getDrawable(R.drawable.more_service));
            viewHolder.mItemMoreServiceAd.setVisibility(View.GONE);
        }
    }
}


