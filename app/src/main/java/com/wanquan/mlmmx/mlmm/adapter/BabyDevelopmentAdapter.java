package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.BabyDevelopmentBeans;

import java.util.List;

/**
 * Created by Administrator on 2017/11/23.
 */

public class BabyDevelopmentAdapter extends BaseAdapter {
    private List<BabyDevelopmentBeans> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private WeekInterface mWeekInterface;

    public BabyDevelopmentAdapter(Context mContext, List<BabyDevelopmentBeans> mList, int week) {
        this.mList = mList;
        this.mContext = mContext;
        this.clickTemp = week - 1;
        this.mInflater = LayoutInflater.from(mContext);
        Log.e("ffffff", String.valueOf(week));
    }

    private int clickTemp = -1;

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

    //缓存布局中的文件
    class ViewHolder {
        LinearLayout mItemBabydevelopLL;
        TextView mItemBabydevelopTv;
        ImageView mItemBabydevelopImg;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_babydevelop, null);
            viewHolder = new ViewHolder();
            //确定没一个店铺布局中显示的内容
            viewHolder.mItemBabydevelopLL = (LinearLayout) convertView.findViewById(R.id.item_babydevelop_ll);
            viewHolder.mItemBabydevelopTv = (TextView) convertView.findViewById(R.id.item_babydevelop_tv);
            viewHolder.mItemBabydevelopImg = (ImageView) convertView.findViewById(R.id.item_babydevelop_img);

            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
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
        viewHolder.mItemBabydevelopLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSeclection(position);
                mWeekInterface.checkGroup(mList.get(position).getWeek());//向外暴露接口
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
