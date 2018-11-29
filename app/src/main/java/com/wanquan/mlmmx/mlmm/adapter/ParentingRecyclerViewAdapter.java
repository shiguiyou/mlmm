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
import com.wanquan.mlmmx.mlmm.beans.ParentingEncyclopediaNeans;

import java.util.List;

/**
 * Created by Administrator on 2018/4/13.
 */

public class ParentingRecyclerViewAdapter extends RecyclerView.Adapter<ParentingRecyclerViewAdapter.ViewHolder> {
    private List<ParentingEncyclopediaNeans.DataBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ParentingEncyclopediaInterface mWeekInterface;
    private int positions;

    public ParentingRecyclerViewAdapter(List<ParentingEncyclopediaNeans.DataBean> mList, Context mContext, int position) {
        this.mContext = mContext;
        this.mList = mList;
        this.positions = position;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }


    public void setParentingEncyclopediaInterface(ParentingEncyclopediaInterface mWeekInterface) {
        this.mWeekInterface = mWeekInterface;
    }

    public interface ParentingEncyclopediaInterface {
        void checkGroup(int id, int position);
    }

    @Override
    public ParentingRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_parenting, null);
        return new ParentingRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ParentingRecyclerViewAdapter.ViewHolder viewHolder, final int position) {
        //显示内容
        final ParentingEncyclopediaNeans.DataBean mParentingEncyclopediaNeans = mList.get(position);

        viewHolder.mItemParentingTv.setText(mParentingEncyclopediaNeans.getTitle());
        if (position == positions) {
            viewHolder.mItemParentingTv.setTextColor(mContext.getResources().getColor(R.color.dsadasdw));
            viewHolder.mItemParentingIM.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mItemParentingTv.setTextColor(mContext.getResources().getColor(R.color.gary));
            viewHolder.mItemParentingIM.setVisibility(View.GONE);
        }

        viewHolder.mItemParentingLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWeekInterface.checkGroup(mList.get(position).getId(), position);
                viewHolder.mItemParentingTv.setTextColor(mContext.getResources().getColor(R.color.dsadasdw));
                viewHolder.mItemParentingIM.setVisibility(View.VISIBLE);
//                notifyDataSetChanged();
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mItemParentingLinearLayout;
        private TextView mItemParentingTv;
        private ImageView mItemParentingIM;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemParentingLinearLayout = (LinearLayout) itemView.findViewById(R.id.item_parenting_LinearLayout);
            mItemParentingTv = (TextView) itemView.findViewById(R.id.item_parenting_tv);
            mItemParentingIM = (ImageView) itemView.findViewById(R.id.item_parenting_IM);

        }
    }
}

