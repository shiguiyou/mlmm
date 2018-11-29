package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.BabyDevelopmentBeans;
import com.wanquan.mlmmx.mlmm.beans.SignInCenterBeans;

import java.util.List;

/**
 * Created by Administrator on 2018/4/13.
 */

public class BabyDevelopmentRecyclerViewAdapter extends RecyclerView.Adapter<BabyDevelopmentRecyclerViewAdapter.ViewHolder> {
    private List<BabyDevelopmentBeans> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private WeekInterface mWeekInterface;
    private int clickTemp = -1;

    public BabyDevelopmentRecyclerViewAdapter(List<BabyDevelopmentBeans> mList, Context mContext, int week) {
        this.mContext = mContext;
        this.mList = mList;
        this.clickTemp = week - 1;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    //标识选择的Item
    public void setSeclection(int position) {
        clickTemp = position;
    }

    public void setWeek(WeekInterface mWeekInterface) {
        this.mWeekInterface = mWeekInterface;
    }

    public interface WeekInterface {
        void checkGroup(int mWeek);
    }

    @Override
    public BabyDevelopmentRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_babydevelop, null);
        return new BabyDevelopmentRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BabyDevelopmentRecyclerViewAdapter.ViewHolder viewHolder, final int position) {
        //显示内容
        final BabyDevelopmentBeans mBabyDevelopmentBeans = mList.get(position);
        viewHolder.mItemBabydevelopTv.setText("宝宝" + mBabyDevelopmentBeans.getWeek() + "周");

        if (clickTemp == position) {
            viewHolder.mItemBabydevelopTv.setTextColor(mContext.getResources().getColor(R.color.deeppink));
            viewHolder.mItemBabydevelopImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.grid_bg));
        } else {
            viewHolder.mItemBabydevelopTv.setTextColor(mContext.getResources().getColor(R.color.text_grey_2));
            viewHolder.mItemBabydevelopImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.grid_bg_write));
        }
        viewHolder.mItemBabydevelopLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSeclection(position);
                mWeekInterface.checkGroup(mList.get(position).getWeek());//向外暴露接口
                notifyDataSetChanged();
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
        LinearLayout mItemBabydevelopLl;
        TextView mItemBabydevelopTv;
        ImageView mItemBabydevelopImg;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemBabydevelopLl = (LinearLayout) itemView.findViewById(R.id.item_babydevelop_ll);
            mItemBabydevelopTv = (TextView) itemView.findViewById(R.id.item_babydevelop_tv);
            mItemBabydevelopImg = (ImageView) itemView.findViewById(R.id.item_babydevelop_img);

        }
    }
}

