package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.GrowSeedlingsTimeBeans;
import com.wanquan.mlmmx.mlmm.beans.QuickeningHistoryBeans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/5/10.
 */

public class QuickeningHistoryRecyclerViewAdapter extends RecyclerView.Adapter<QuickeningHistoryRecyclerViewAdapter.ViewHolder> {
    private List<QuickeningHistoryBeans.DataBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
//    private QuickeningHistoryRecycler mQuickeningHistoryRecycler;

    public QuickeningHistoryRecyclerViewAdapter(List<QuickeningHistoryBeans.DataBean> mList, Context mContext) {
        this.mContext = mContext;
        this.mList = mList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public QuickeningHistoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_cloud_cat_notice_elv_adapter, parent ,false);
        View view = mLayoutInflater.inflate(R.layout.quickeninghistory_f, parent, false);
        return new QuickeningHistoryRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuickeningHistoryRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        viewHolder. mQuickeningHistoryTime.setText(mList.get(position).getCreateDate() );
        viewHolder. mQuickeningHistoryTv1.setText(mList.get(position).getResultMsg());
        viewHolder. mQuickeningHistoryTv3.setText(mList.get(position).getExpect12());

        List<QuickeningHistoryBeans.DataBean.FetalMoveHisBean> mList2 = mList.get(position).getFetalMoveHis();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        viewHolder.mQuickeningHistoryChildRecyclerView.setLayoutManager(linearLayoutManager);

        QuickeningHistoryRecyclerViewChildAdapter mQuickeningHistoryRecyclerViewChildAdapter = new QuickeningHistoryRecyclerViewChildAdapter(mList2, mContext);
//        mQuickeningHistoryRecyclerViewChildAdapter.setInterFaceGrowSeedlingsChild((QuickeningHistoryRecyclerViewChildAdapter.InterFaceGrowSeedlingsChild) this);
        viewHolder.mQuickeningHistoryChildRecyclerView.setAdapter(mQuickeningHistoryRecyclerViewChildAdapter);

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
        private TextView mQuickeningHistoryTime;
        private TextView mQuickeningHistoryTv1;
        private TextView mQuickeningHistoryTv2;
        private TextView mQuickeningHistoryTv3;
        private RecyclerView mQuickeningHistoryChildRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            mQuickeningHistoryTime = (TextView) itemView.findViewById(R.id.QuickeningHistory_Time);
            mQuickeningHistoryTv1 = (TextView) itemView.findViewById(R.id.QuickeningHistory_Tv1);
            mQuickeningHistoryTv2 = (TextView) itemView.findViewById(R.id.QuickeningHistory_Tv2);
            mQuickeningHistoryTv3 = (TextView) itemView.findViewById(R.id.QuickeningHistory_Tv3);
            mQuickeningHistoryChildRecyclerView = (RecyclerView) itemView.findViewById(R.id.QuickeningHistory_Child_RecyclerView);
        }
    }
}

