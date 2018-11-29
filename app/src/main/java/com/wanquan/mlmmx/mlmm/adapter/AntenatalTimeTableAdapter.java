package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.AntenatalTimeTableBeans;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */

public class AntenatalTimeTableAdapter extends BaseAdapter {
    private List<AntenatalTimeTableBeans.DataBean.MapListBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private String times;
    private String chanjianDate;

    public AntenatalTimeTableAdapter(Context mContext, List<AntenatalTimeTableBeans.DataBean.MapListBean> mList, String chanjianDate) {
        this.mList = mList;
        this.chanjianDate = chanjianDate;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
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
        LinearLayout mItemAntenatalll;
        TextView mItemAntenatalTime;
        TextView mItemAntenatalText;
        TextView mItemAntenatalyq;
        TextView mItemAntenatalZd;
        TextView mItemAntenatalContent;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_antenatal, null);
            viewHolder = new ViewHolder();
            //确定没一个店铺布局中显示的内容
            viewHolder.mItemAntenatalll = (LinearLayout) convertView.findViewById(R.id.Item_Antenatal_ll);
            viewHolder.mItemAntenatalTime = (TextView) convertView.findViewById(R.id.Item_Antenatal_Time);
            viewHolder.mItemAntenatalText = (TextView) convertView.findViewById(R.id.Item_Antenatal_Text);
            viewHolder.mItemAntenatalyq = (TextView) convertView.findViewById(R.id.Item_Antenatal_yq);
            viewHolder.mItemAntenatalZd = (TextView) convertView.findViewById(R.id.Item_Antenatal_zd);
            viewHolder.mItemAntenatalContent = (TextView) convertView.findViewById(R.id.Item_Antenatal_Content);

            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示内容
        final AntenatalTimeTableBeans.DataBean.MapListBean mBabyRoomGridViewBeans = mList.get(position);
        viewHolder.mItemAntenatalTime.setText(mBabyRoomGridViewBeans.getChanjianDate());
        //比较两个时间的月份是否相等
//        for (int i = 0; i < mList.size(); i++) {
//            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
//            DateTime old = DateTime.parse("2018-05-12", fmt);
//        Log.e("size", mBabyRoomGridViewBeans.getChanjianDate());
//        Log.e("size", String.valueOf(Months.monthsBetween(now, old).getMonths()));

        DateTime now = DateTime.now();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTimeFormatter fmt1 = DateTimeFormat.forPattern("yyyy-MM");

        DateTime old = DateTime.parse(mBabyRoomGridViewBeans.getChanjianDate(), fmt);
        old = DateTime.parse(old.toString("yyyy-MM"), fmt1);
        now = DateTime.parse(now.toString("yyyy-MM"), fmt1);

//        System.out.println(Months.monthsBetween(now,old).getMonths());

//        if (Months.monthsBetween(now, old).getMonths() == 0 && mBabyRoomGridViewBeans.getStatus() != 2) {
//            viewHolder.mItemAntenatalTime.setTextColor(mContext.getResources().getColor(R.color.tops));
//        } else if (Months.monthsBetween(now, old).getMonths() > 0 && mBabyRoomGridViewBeans.getStatus() != 2) {
//            viewHolder.mItemAntenatalTime.setTextColor(mContext.getResources().getColor(R.color.tops));
//        }
        if (chanjianDate != null) {
            if (chanjianDate.equals(mList.get(position).getChanjianDate())) {
                viewHolder.mItemAntenatalTime.setTextColor(mContext.getResources().getColor(R.color.tops));
            } else {
                viewHolder.mItemAntenatalTime.setTextColor(mContext.getResources().getColor(R.color.hui_s));
            }
        }

        viewHolder.mItemAntenatalText.setText("第" + mBabyRoomGridViewBeans.getSeq() + "次产检    " + "   怀孕" + mBabyRoomGridViewBeans.getWeek() + "周");
        if (mBabyRoomGridViewBeans.getStatus() == 0) {
            viewHolder.mItemAntenatalyq.setVisibility(View.GONE);
        } else if (mBabyRoomGridViewBeans.getStatus() == 1) {
            viewHolder.mItemAntenatalyq.setVisibility(View.GONE);
        } else if (mBabyRoomGridViewBeans.getStatus() == 2) {
            viewHolder.mItemAntenatalyq.setVisibility(View.VISIBLE);
            viewHolder.mItemAntenatalyq.setText("已逾期");
        }
        viewHolder.mItemAntenatalContent.setText(mBabyRoomGridViewBeans.getDesc());
        return convertView;
    }
}
