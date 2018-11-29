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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.BabyDevelopmentBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeGridViewBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeGridViewNewBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2017/11/22.
 */

public class HomeGridViewNewAdapter extends RecyclerView.Adapter<HomeGridViewNewAdapter.ViewHolder> {
    private List<HomeGridViewNewBeans.DataBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
//    private OnItemClickListenerInterface mOnItemClickListenerInterface;

    public HomeGridViewNewAdapter(List<HomeGridViewNewBeans.DataBean> mList, Context mContext) {
        this.mContext = mContext;
        this.mList = mList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

//    public void setOnItem(OnItemClickListenerInterface mOnItemClickListenerInterface) {
//        this.mOnItemClickListenerInterface = mOnItemClickListenerInterface;
//    }
//
//    public interface OnItemClickListenerInterface {
//        void SetOnItemClick(int id);
//    }

    @Override
    public HomeGridViewNewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_home_gridview, null);
        return new HomeGridViewNewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        //显示内容
        final HomeGridViewNewBeans.DataBean mHomeGridViewNewBeanss = mList.get(position);
        viewHolder.mItemHomeTv.setText(mHomeGridViewNewBeanss.getName());

        if ("1".equals(mHomeGridViewNewBeanss.getId())) {//宝宝相册
            viewHolder.mItemHomeImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.babyalbum));
        } else if ("2".equals(mHomeGridViewNewBeanss.getId())) {//情感日志
            viewHolder.mItemHomeImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.diary));
        } else if ("3".equals(mHomeGridViewNewBeanss.getId())) {//发育变化
            viewHolder.mItemHomeImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.groth));
        } else if ("4".equals(mHomeGridViewNewBeanss.getId())) {//宝宝听听
            viewHolder.mItemHomeImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.bbtt));
        } else if ("5".equals(mHomeGridViewNewBeanss.getId())) {//亲子游戏
            viewHolder.mItemHomeImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.childgame));
        } else if ("6".equals(mHomeGridViewNewBeanss.getId())) {//数胎动
            viewHolder.mItemHomeImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.taidong));
        } else if ("7".equals(mHomeGridViewNewBeanss.getId())) {//产检提醒
            viewHolder.mItemHomeImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.checkremind));
        } else if ("8".equals(mHomeGridViewNewBeanss.getId())) {//孕期食谱
            viewHolder.mItemHomeImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.yqsp));
        } else if ("9".equals(mHomeGridViewNewBeanss.getId())) {//看懂B超单
            viewHolder.mItemHomeImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.bc));
        } else if ("10".equals(mHomeGridViewNewBeanss.getId())) {//亲子任务
            viewHolder.mItemHomeImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.qzrw));
        } else if ("11".equals(mHomeGridViewNewBeanss.getId())) {//成长曲线
            viewHolder.mItemHomeImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.growthtest));
        } else if ("12".equals(mHomeGridViewNewBeanss.getId())) {//疫苗提醒
            viewHolder.mItemHomeImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.vaccine));
        } else if ("13".equals(mHomeGridViewNewBeanss.getId())) {//月子餐
            viewHolder.mItemHomeImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.yzc));
        } else if ("14".equals(mHomeGridViewNewBeanss.getId())) {//能不能吃
            viewHolder.mItemHomeImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.food));
        } else if ("15".equals(mHomeGridViewNewBeanss.getId())) {//成长测评
            viewHolder.mItemHomeImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.chegnzhangceping));
        } else if ("16".equals(mHomeGridViewNewBeanss.getId())) {//知识百科
            viewHolder.mItemHomeImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.zsbk));
        } else if ("100".equals(mHomeGridViewNewBeanss.getId())) {//更多
            viewHolder.mItemHomeImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.moreservice));
        }
        viewHolder.mItemHomeOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("ffffff", mHomeGridViewNewBeanss.getId());
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setReId(String.valueOf(position));
                EventBus.getDefault().post(messageEvent);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mItemHomeOne;
        private ImageView mItemHomeImg;
        private TextView mItemHomeTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemHomeOne = (LinearLayout) itemView.findViewById(R.id.item_Home_One);
            mItemHomeImg = (ImageView) itemView.findViewById(R.id.item_Home_Img);
            mItemHomeTv = (TextView) itemView.findViewById(R.id.item_Home_tv);

        }
    }
}

