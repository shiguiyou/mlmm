package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.SignInCenterBeans;
import com.ximalaya.ting.android.opensdk.auth.utils.Logger;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by xcfchangfeng on 2017/5/13.
 */

public class SignInCenterAdapter extends RecyclerView.Adapter<SignInCenterAdapter.ViewHolder> {
    private List<SignInCenterBeans.DataBean.TomorrowSignBean> mListQian;
    private List<SignInCenterBeans.DataBean.TomorrowSignBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public SignInCenterAdapter(List<SignInCenterBeans.DataBean.TomorrowSignBean> mListQian, List<SignInCenterBeans.DataBean.TomorrowSignBean> mList, Context mContext) {
        this.mContext = mContext;
        this.mListQian = mListQian;
        this.mList = mList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public SignInCenterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_signin_center, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SignInCenterAdapter.ViewHolder viewHolder, int position) {
        viewHolder.mItemSigninCenterSize.setText("+" + String.valueOf(mList.get(position).getGainIntegral()));
        String signDate = mList.get(position).getSignDate();
        signDate = signDate.substring(5);
        viewHolder.mItemSigninCenterTime.setText(signDate);
        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        final String times = formatter.format(curDate);

        if (times.equals(signDate)) {
//            viewHolder.mItemSigninCenterImg.setVisibility(View.VISIBLE);
//            viewHolder.mItemSigninCenterImgBg.setBackground(mContext.getResources().getDrawable(R.drawable.yuan_hong));
//            viewHolder.mItemSigninCenterSize.setTextColor(mContext.getResources().getColor(R.color.white));
            viewHolder.mItemSigninCenterTime.setText("今日");
        } else {
//            viewHolder.mItemSigninCenterImg.setVisibility(View.GONE);
//            viewHolder.mItemSigninCenterImgBg.setBackground(mContext.getResources().getDrawable(R.drawable.sign_bg2));
//            viewHolder.mItemSigninCenterSize.setTextColor(mContext.getResources().getColor(R.color.black));
        }
        if (mList.get(position).isFlag()){
            viewHolder.mItemSigninCenterImg.setVisibility(View.VISIBLE);
            viewHolder.mItemSigninCenterImgBg.setBackground(mContext.getResources().getDrawable(R.drawable.yuan_hong));
            viewHolder.mItemSigninCenterSize.setTextColor(mContext.getResources().getColor(R.color.white));
        }else {
            viewHolder.mItemSigninCenterImg.setVisibility(View.GONE);
            viewHolder.mItemSigninCenterImgBg.setBackground(mContext.getResources().getDrawable(R.drawable.sign_bg2));
            viewHolder.mItemSigninCenterSize.setTextColor(mContext.getResources().getColor(R.color.black));
        }
//        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
//        Date curDate2 = new Date(System.currentTimeMillis());
//        final String times2 = formatter2.format(curDate2);
//        Log.e("dsadasdassignDate", mList.get(position).getSignDate());
//        Log.e("dsadasdastimes", times2);
//        DateTime dateTime1 = DateTime.parse(mList.get(position).getSignDate());
//        DateTime dateTime2 = DateTime.parse(times);
//        int size = Days.daysBetween(dateTime1, dateTime2).getDays();
//        Log.e("dsadasdas", String.valueOf(size));
//        for (int i = 0; i < mList.size() - 15; i++) {
//            if (mList.get(i).getGainIntegral() != 0) {
//                viewHolder.mItemSigninCenterImg.setVisibility(View.VISIBLE);
//                viewHolder.mItemSigninCenterImgBg.setBackground(mContext.getResources().getDrawable(R.drawable.yuan_hong));
//                viewHolder.mItemSigninCenterSize.setTextColor(mContext.getResources().getColor(R.color.white));
//            }
//        }
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
        LinearLayout mItemSigninCenterLl;
        TextView mItemSigninCenterSize;
        ImageView mItemSigninCenterImg;
        ImageView mItemSigninCenterImgBg;
        TextView mItemSigninCenterTime;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemSigninCenterLl = (LinearLayout) itemView.findViewById(R.id.item_signin_center_ll);
            mItemSigninCenterSize = (TextView) itemView.findViewById(R.id.item_signin_center_size);
            mItemSigninCenterImg = (ImageView) itemView.findViewById(R.id.item_signin_center_img);
            mItemSigninCenterImgBg = (ImageView) itemView.findViewById(R.id.item_signin_center_imgbg);
            mItemSigninCenterTime = (TextView) itemView.findViewById(R.id.item_signin_center_time);

        }
    }
}
