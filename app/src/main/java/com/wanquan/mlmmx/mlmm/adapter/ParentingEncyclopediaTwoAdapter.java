package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.ParentingEncyclopediaTwoBeans;
import com.wanquan.mlmmx.mlmm.phone.MyGridView;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */

//public class ParentingEncyclopediaTwoAdapter extends RecyclerView.Adapter<ParentingEncyclopediaTwoAdapter.ViewHolder> {
//    private List<ParentingEncyclopediaTwoBeans.DataBean> mList;
//    private Context mContext;
//    private LayoutInflater mLayoutInflater;
//
//    public ParentingEncyclopediaTwoAdapter(List<ParentingEncyclopediaTwoBeans.DataBean> mList, Context mContext) {
//        this.mContext = mContext;
//        this.mList = mList;
//        mLayoutInflater = LayoutInflater.from(mContext);
//    }
//
//    @Override
//    public ParentingEncyclopediaTwoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
////        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_cloud_cat_parenting, null ,false);
//        View view = mLayoutInflater.inflate(R.layout.item_group_cloud_cat_parenting, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ParentingEncyclopediaTwoAdapter.ViewHolder viewHolder, int position) {
//        ParentingEncyclopediaTwoBeans.DataBean groupBean = mList.get(position);
//        viewHolder.mItemGroupCloudCatParentingTextView.setText(groupBean.getIcon());
//
//        List<ParentingEncyclopediaTwoBeans.DataBean.BaikeListBean> mList2 = groupBean.getBaikeList();
//
////        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mContext);
////        viewHolder.mItemGroupCloudCatParentingRecyclerView.setLayoutManager(linearLayoutManager2);
////        viewHolder.mItemGroupCloudCatParentingRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
//
//        ParentingChildAdapter mParentingChildAdapter = new ParentingChildAdapter(mContext, mList2);
//        viewHolder.mItemGroupCloudCatParentingRecyclerView.setAdapter(mParentingChildAdapter);
//
//
////        viewHolder.mItemGroupCloudCatParentingRecyclerView.setFocusable(false);
////        viewHolder.mItemGroupCloudCatParentingRecyclerView.setFocusableInTouchMode(false);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public int getItemCount() {
//        return/* mList == null ? 0 : */mList.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        TextView mItemGroupCloudCatParentingTextView;
//        MyGridView mItemGroupCloudCatParentingRecyclerView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            mItemGroupCloudCatParentingTextView = (TextView) itemView.findViewById(R.id.item_group_cloud_cat_parenting_TextView);
//            mItemGroupCloudCatParentingRecyclerView = (MyGridView) itemView.findViewById(R.id.item_group_cloud_cat_parenting_RecyclerView);
//        }
//    }
//}

public class ParentingEncyclopediaTwoAdapter extends BaseAdapter {
    private List<ParentingEncyclopediaTwoBeans.DataBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private InterfaceMyIntegral mInterfaceMyIntegral;

    public ParentingEncyclopediaTwoAdapter(Context mContext, List<ParentingEncyclopediaTwoBeans.DataBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setInterfaceMyIntegral(InterfaceMyIntegral mInterfaceMyIntegral) {
        this.mInterfaceMyIntegral = mInterfaceMyIntegral;
        notifyDataSetChanged();
    }

    public interface InterfaceMyIntegral {
        void initFinish();
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

        TextView mItemGroupCloudCatParentingTextView;
        MyGridView mItemGroupCloudCatParentingRecyclerView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_group_cloud_cat_parenting, null);
            viewHolder = new ViewHolder();
            //确定没一个店铺布局中显示的内容

            viewHolder.mItemGroupCloudCatParentingTextView = (TextView) convertView.findViewById(R.id.item_group_cloud_cat_parenting_TextView);
            viewHolder.mItemGroupCloudCatParentingRecyclerView = (MyGridView) convertView.findViewById(R.id.item_group_cloud_cat_parenting_RecyclerView);

            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mItemGroupCloudCatParentingTextView.setText(mList.get(position).getIcon());

        List<ParentingEncyclopediaTwoBeans.DataBean.BaikeListBean> mList2 = mList.get(position).getBaikeList();
        ParentingChildAdapter mParentingChildAdapter = new ParentingChildAdapter(mContext, mList2);
        viewHolder.mItemGroupCloudCatParentingRecyclerView.setAdapter(mParentingChildAdapter);

        return convertView;
    }
}











