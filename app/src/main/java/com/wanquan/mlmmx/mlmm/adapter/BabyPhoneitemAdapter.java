package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wanquan.mlmmx.mlmm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */

public class BabyPhoneitemAdapter extends BaseAdapter {
    List<String> mList = new ArrayList<>();
    Context mContext;
    LayoutInflater mLayoutInflater;

    public BabyPhoneitemAdapter(List<String> list, Context context) {
        mList = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
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
        ImageView mBabyPhoneItem;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_child, null);
            viewHolder = new ViewHolder();

            viewHolder.mBabyPhoneItem = (ImageView) convertView.findViewById(R.id.BabyPhone_item);
//            viewHolder.mBabyPhoneItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    PhotoStringBeans photoStringBeans = new PhotoStringBeans();
//                    for (int i = 0; i <mList.size() ; i++) {
//                        photoStringBeans.setStr(mList.get(position));
//                    }
//                }
//            });


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(mList.get(position)).into(viewHolder.mBabyPhoneItem);

        return convertView;
    }
}



