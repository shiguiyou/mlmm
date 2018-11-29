package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.FeedBackGV1Beans;

import java.util.List;

/**
 * Created by Administrator on 2017/12/12.
 */

public class FeedBackGridView1Adapter extends BaseAdapter {
    List<FeedBackGV1Beans.DataBean> mList;
    Context mContext;
    LayoutInflater mLayoutInflater;
    SetImageView mSetImageView;

    public FeedBackGridView1Adapter(List<FeedBackGV1Beans.DataBean> list, Context context) {
        mList = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setImageView(SetImageView mSetImageView) {
        this.mSetImageView = mSetImageView;
    }

    public interface SetImageView {
        void ImageView(String value,int position);
    }

    @Override
    public int getCount() {
        return mList.size();
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
        private TextView mItemFeedGv1Text;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_feed_gv1, null);
            viewHolder = new ViewHolder();

            viewHolder.mItemFeedGv1Text = (TextView) convertView.findViewById(R.id.Item_Feed_gv1_text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final FeedBackGV1Beans.DataBean mFeedBackGV1Beans = mList.get(position);
        viewHolder.mItemFeedGv1Text.setText(mFeedBackGV1Beans.getValue());

        if (mList.get(position).isCheck()) {
            viewHolder.mItemFeedGv1Text.setBackground(mContext.getResources().getDrawable(R.drawable.feedback_bg));
            viewHolder.mItemFeedGv1Text.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            viewHolder.mItemFeedGv1Text.setBackground(mContext.getResources().getDrawable(R.drawable.fatie_bg));
            viewHolder.mItemFeedGv1Text.setTextColor(mContext.getResources().getColor(R.color.black));
        }

        viewHolder.mItemFeedGv1Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.get(position).setCheck(true);
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(position).isCheck()) {
                        viewHolder.mItemFeedGv1Text.setBackground(mContext.getResources().getDrawable(R.drawable.feedback_bg));
                        viewHolder.mItemFeedGv1Text.setTextColor(mContext.getResources().getColor(R.color.white));
                    } else {
                        viewHolder.mItemFeedGv1Text.setBackground(mContext.getResources().getDrawable(R.drawable.fatie_bg));
                        viewHolder.mItemFeedGv1Text.setTextColor(mContext.getResources().getColor(R.color.black));
                    }
                }
                mSetImageView.ImageView(mFeedBackGV1Beans.getValue(),position);
            }
        });

        return convertView;
    }
}
