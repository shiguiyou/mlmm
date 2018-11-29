package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.AddressActivity;

import java.util.List;

/**
 * author           Alpha58
 * time             2017/2/25 10:48
 * desc	            ${TODO}
 * <p>
 * upDateAuthor     $Author$
 * upDate           $Date$
 * upDateDesc       ${TODO}
 */
public class PoiKeywordSearchAdaptetwo extends BaseAdapter {
    List<PoiItem> mList;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public PoiKeywordSearchAdaptetwo(List<PoiItem> list, Context context) {
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
        LinearLayout mLlItemLayout;
        TextView mTvDetailAddress;
        TextView mTvContent;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_poi_keyword_search, null);
            viewHolder = new ViewHolder();

            viewHolder.mLlItemLayout = (LinearLayout) convertView.findViewById(R.id.ll_item_layout);
            viewHolder.mTvDetailAddress = (TextView) convertView.findViewById(R.id.tv_detailAddress);
            viewHolder.mTvContent = (TextView) convertView.findViewById(R.id.tv_content);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final PoiItem mPoiAddressBean = mList.get(position);
        viewHolder.mTvDetailAddress.setText(mPoiAddressBean.getTitle());
        viewHolder.mTvContent.setText(mPoiAddressBean.getSnippet());
//        Log.e("sadsadsa", mPoiAddressBean.getDistance() + "--" + mPoiAddressBean.getTypeDes() + "--" + mPoiAddressBean.getWebsite());
        viewHolder.mLlItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AddressActivity) mContext).setDetailAddress(mPoiAddressBean.getTitle());
            }
        });

        return convertView;
    }
}
