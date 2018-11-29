package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.FeedBackGV1Beans;
import com.wanquan.mlmmx.mlmm.beans.FeedBackGV2Beans;
import com.wanquan.mlmmx.mlmm.phone.Photo_Bimp;
import com.yuyh.library.imgsel.ImgSelActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/12.
 */

public class FeedBackGridView2Adapter extends BaseAdapter {
    List<String> mList;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public FeedBackGridView2Adapter(List<String> list, Context context) {
        mList = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (mList.size() == 6) {
            return 6;
        }
        return (mList.size() + 1);
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
        ImageView mItemFeedgv2Image;
        ImageView mItemFeedgv2Delete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_feed_gv2, null);
            viewHolder = new ViewHolder();

            viewHolder.mItemFeedgv2Image = (ImageView) convertView.findViewById(R.id.item_feedgv2_image);
            viewHolder.mItemFeedgv2Delete = (ImageView) convertView.findViewById(R.id.item_feedgv2_delete);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mList.size()!=0){
            Glide.with(mContext).load(mList.get(position)).into(viewHolder.mItemFeedgv2Image);
        }

        if (position == mList.size()) {
            viewHolder.mItemFeedgv2Image.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.upimg_logo));
            viewHolder.mItemFeedgv2Delete.setVisibility(View.INVISIBLE);
            if (position==6){
                viewHolder.mItemFeedgv2Image.setVisibility(View.GONE);
            }
        } else {
//            holder.image.setImageBitmap(Photo_Bimp.albumSelectBitmap.get(position).getBitmap());
            viewHolder.mItemFeedgv2Delete.setVisibility(View.VISIBLE);
        }

        viewHolder.mItemFeedgv2Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        viewHolder.mItemFeedgv2Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(position);
                v.setVisibility(View.GONE);
                //刷新适配器
            }
        });

        return convertView;
    }
}
