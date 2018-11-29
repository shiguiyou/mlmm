package com.wanquan.mlmmx.mlmm.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.IntegralStrategyActivity;
import com.wanquan.mlmmx.mlmm.activity.ParentingEncyclopediaHTMLActivity;
import com.wanquan.mlmmx.mlmm.beans.MyIntegralBeans;
import com.wanquan.mlmmx.mlmm.beans.ParentingEncyclopediaTwoBeans;
import com.wanquan.mlmmx.mlmm.beans.SignInDuiHuanBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/13.
 */
//public class ParentingChildAdapter extends RecyclerView.Adapter<ParentingChildAdapter.ViewHolder> {
//    private List<ParentingEncyclopediaTwoBeans.DataBean.BaikeListBean> mList;
//    private Context mContext;
//    private LayoutInflater mLayoutInflater;
//
//    public ParentingChildAdapter(List<ParentingEncyclopediaTwoBeans.DataBean.BaikeListBean> mList, Context mContext) {
//        this.mContext = mContext;
//        this.mList = mList;
//        mLayoutInflater = LayoutInflater.from(mContext);
//    }
//
//    @Override
//    public ParentingChildAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
////        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_cloud_cat_notice_elv_adapter, parent ,false);
//        View view = mLayoutInflater.inflate(R.layout.item_child_clout_cat_parting, parent ,false);
//        return new ParentingChildAdapter.ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//        ParentingEncyclopediaTwoBeans.DataBean.BaikeListBean groupBean = mList.get(position);
//        viewHolder.mItemChildCloutCatPartingTV.setText(groupBean.getTitle());
//
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public int getItemCount() {
//        return/* mList == null ? 0 :*/ mList.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView mItemChildCloutCatPartingTV;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            mItemChildCloutCatPartingTV = (TextView) itemView.findViewById(R.id.item_child_clout_cat_parting_TV);
//        }
//    }
//}

public class ParentingChildAdapter extends BaseAdapter {
    private List<ParentingEncyclopediaTwoBeans.DataBean.BaikeListBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private InterfaceMyIntegral mInterfaceMyIntegral;

    public ParentingChildAdapter(Context mContext, List<ParentingEncyclopediaTwoBeans.DataBean.BaikeListBean> mList) {
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
        TextView mItemChildCloutCatPartingTV;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_child_clout_cat_parting, null);
            viewHolder = new ViewHolder();
            //确定没一个店铺布局中显示的内容

            viewHolder.mItemChildCloutCatPartingTV = (TextView) convertView.findViewById(R.id.item_child_clout_cat_parting_TV);

            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mItemChildCloutCatPartingTV.setText(mList.get(position).getTitle());
        viewHolder.mItemChildCloutCatPartingTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, ParentingEncyclopediaHTMLActivity.class).putExtra("id", String.valueOf(mList.get(position).getId())).putExtra("name",mList.get(position).getTitle()));
            }
        });
        return convertView;
    }
}











