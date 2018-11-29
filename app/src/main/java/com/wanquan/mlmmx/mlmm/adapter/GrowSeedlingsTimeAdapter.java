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

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */

//public class QuickeningHistoryRecyclerViewAdapter extends BaseExpandableListAdapter {
//    private Context mContext;
//    private List<GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean> mDataBeanList;
//    private String monthFirstVaccineDate;//用于listview滚动定位判断
//    private InterFaceGrowSeedlings mInterFaceGrowSeedlings;
//
//    public void setInterFaceGrowSeedlings(InterFaceGrowSeedlings mInterFaceGrowSeedlingss){
//        mInterFaceGrowSeedlings = mInterFaceGrowSeedlingss;
//    }
//
//    public interface InterFaceGrowSeedlings {
//        void setSize(int position);
//    }
//
//    public QuickeningHistoryRecyclerViewAdapter(Context mContext) {
//        this.mContext = mContext;
//    }
//
//    //获取当前时间
//    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
//    Date curDate = new Date(System.currentTimeMillis());
//    String times = formatter.format(curDate);
//
//    //Log.e("33333333", times);
//    @Override
//    public int getGroupCount() {   //父条目有多少条
////        notifyDataSetChanged();
//        return mDataBeanList == null ? 0 : mDataBeanList.size();
//    }
//
//    @Override
//    public int getChildrenCount(int groupPosition) {  // 子条目有多少条
////        notifyDataSetChanged();
//        return mDataBeanList == null ? 0 : mDataBeanList.get(groupPosition).getListMap().size();
//    }
//
//    @Override
//    public Object getGroup(int groupPosition) {
//        return mDataBeanList == null ? null : mDataBeanList.get(groupPosition);
//    }
//
//    @Override
//    public Object getChild(int groupPosition, int childPosition) {
//        return mDataBeanList == null ? null : mDataBeanList.get(groupPosition).getListMap().get(childPosition);
//    }
//
//    @Override
//    public long getGroupId(int groupPosition) {
//        return groupPosition;
//    }
//
//    @Override
//    public long getChildId(int groupPosition, int childPosition) {
//        return childPosition;
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return false;
//    }
//
//    // 父布局
//    @Override
//    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//        GroupViewHolder holder;
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group_cloud_cat_notice_elv_adapter, parent, false);
//            holder = new GroupViewHolder(convertView);
//            convertView.setTag(holder);
//        } else {
//            holder = (GroupViewHolder) convertView.getTag();
//        }
//
//        GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean groupBean = mDataBeanList.get(groupPosition);
//
//        holder.mGrowSeedingTitle.setText(groupBean.getMonthFirstVaccineDate());
//
//        if (groupBean.getMonth() == 0) {
//            holder.mGrowSeedingYes.setText("当月出生");
//        } else {
//            holder.mGrowSeedingYes.setText(groupBean.getMonth() + "月龄");
//        }
//        monthFirstVaccineDate = groupBean.getMonthFirstVaccineDate();
//        if (groupBean.getMonthFirstVaccineDate().equals(times)) {
//            holder.mGrowSeedingTitle.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
//            holder.mGrowSeedingYes.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
//        } else {
//            holder.mGrowSeedingTitle.setTextColor(mContext.getResources().getColor(R.color.black));
//            holder.mGrowSeedingYes.setTextColor(mContext.getResources().getColor(R.color.black));
//        }
//
//
//        return convertView;
//    }
//
//    // 子布局
//    @Override
//    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        ChildViewHolder holders;
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_child_clout_cat_motice_elv_adapter, parent, false);
//            holders = new ChildViewHolder(convertView);
//            convertView.setTag(holders);
//        } else {
//            holders = (ChildViewHolder) convertView.getTag();
//        }
//        final GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean.ListMapBean childBean = mDataBeanList.get(groupPosition).getListMap().get(childPosition);
//
//        if (childBean.getVaccineIsNecessary() == 0) {
//            holders.mGrowSeedingImg.setVisibility(View.GONE);
//        } else if (childBean.getVaccineIsNecessary() == 1) {
//            holders.mGrowSeedingImg.setVisibility(View.VISIBLE);
//        }
//        holders.mGrowSeedlingsText.setText(childBean.getVaccineName());
//
//        if (childBean.getStatus() == 0) {
////            holders.mGrowSeedlingsName.setText("未打");
//            holders.mGrowSeedlingsName.setVisibility(View.GONE);
//
//        } else if (childBean.getStatus() == 1) {
//            holders.mGrowSeedlingsName.setVisibility(View.GONE);
//        } else if (childBean.getStatus() == 2) {
//            holders.mGrowSeedlingsName.setVisibility(View.VISIBLE);
//            holders.mGrowSeedlingsName.setText("已逾期");
//        }
//
//        if (childBean.getVaccineTotalBatch() == 1) {
//            holders.mGrowSeedlingsSize.setVisibility(View.GONE);
//        } else {
//            holders.mGrowSeedlingsSize.setVisibility(View.VISIBLE);
//            holders.mGrowSeedlingsSize.setText("第 " + childBean.getVaccineBatch() + " 次");
//        }
//
//        holders.mGrowSeedlingsForm.setText(childBean.getVaccineDesc());
//
//        holders.mCharRelativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, GrowSeedlingsDetailsActivity.class);
//                intent.putExtra("id", String.valueOf(childBean.getId()));
//                intent.putExtra("method", String.valueOf(childBean.getMethod()));
//                intent.putExtra("principle", String.valueOf(childBean.getPrinciple()));
//                intent.putExtra("reason", String.valueOf(childBean.getReason()));
//                intent.putExtra("time1", String.valueOf(childBean.getVaccineDate()));
//                intent.putExtra("time2", String.valueOf(childBean.getReminderDate()));
//                intent.putExtra("Batch", String.valueOf(childBean.getVaccineBatch()));
//                intent.putExtra("vaccineTotalBatch", String.valueOf(childBean.getVaccineTotalBatch()));
//                intent.putExtra("status", String.valueOf(childBean.getStatus()));
//                intent.putExtra("title", String.valueOf(childBean.getVaccineName()));
//                mContext.startActivity(intent);
//            }
//        });
//
//        return convertView;
//    }
//
//    @Override
//    public boolean isChildSelectable(int groupPosition, int childPosition) {
//        return true;
//    }
//
//    public void setmDataBeanList(List<GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean> dataBeanList) {
//        if (mDataBeanList == null) {
//            mDataBeanList = new ArrayList<>();
//        }
//        mDataBeanList.clear();
//        mDataBeanList.addAll(dataBeanList);
//        notifyDataSetChanged();
//    }
//
//    static class GroupViewHolder {
//        private TextView mGrowSeedingTitle;
//        private TextView mGrowSeedingYes;
//
//        public GroupViewHolder(View view) {
//            mGrowSeedingTitle = (TextView) view.findViewById(R.id.GrowSeeding_Title);
//            mGrowSeedingYes = (TextView) view.findViewById(R.id.GrowSeeding_yes);
//        }
//    }
//
//    static class ChildViewHolder {
//        private ImageView mGrowSeedingImg;
//        private LinearLayout mCharRelativeLayout;
//        private TextView mGrowSeedlingsText;
//        private TextView mGrowSeedlingsSize;
//        private TextView mGrowSeedlingsName;
//        private TextView mGrowSeedlingsForm;
//
//        public ChildViewHolder(View view) {
//            mGrowSeedingImg = (ImageView) view.findViewById(R.id.GrowSeeding_Img);
//            mCharRelativeLayout = (LinearLayout) view.findViewById(R.id.Char_RelativeLayout);
//            mGrowSeedlingsText = (TextView) view.findViewById(R.id.GrowSeedlings_Text);
//            mGrowSeedlingsSize = (TextView) view.findViewById(R.id.GrowSeedlings_Size);
//            mGrowSeedlingsName = (TextView) view.findViewById(R.id.GrowSeedlings_Name);
//            mGrowSeedlingsForm = (TextView) view.findViewById(R.id.GrowSeedlings_Form);
//        }
//    }
//}

//public class QuickeningHistoryRecyclerViewAdapter extends BaseAdapter {
//    private List<GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean> mList;
//    private Context mContext;
//    private LayoutInflater mLayoutInflater;
//    private String monthFirstVaccineDate;//用于listview滚动定位判断
//
//    public QuickeningHistoryRecyclerViewAdapter(List<GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean> list, Context context) {
//        mList = list;
//        mContext = context;
//        mLayoutInflater = LayoutInflater.from(mContext);
//    }
//
//    //获取当前时间
//    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
//    Date curDate = new Date(System.currentTimeMillis());
//    String times = formatter.format(curDate);
//
//    @Override
//    public int getCount() {
//        return mList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return mList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    class ViewHolder {
//        TextView mGrowSeedingTitle;
//        TextView mGrowSeedingYes;
//        MyListView mGrowSeedingMyListView;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup viewGroup) {
//        final QuickeningHistoryRecyclerViewAdapter.ViewHolder viewHolder;
//        if (convertView == null) {
//            convertView = mLayoutInflater.inflate(R.layout.item_group_cloud_cat_notice_elv_adapter, null);
//            viewHolder = new QuickeningHistoryRecyclerViewAdapter.ViewHolder();
//
//            viewHolder.mGrowSeedingTitle = (TextView) convertView.findViewById(R.id.GrowSeeding_Title);
//            viewHolder.mGrowSeedingYes = (TextView) convertView.findViewById(R.id.GrowSeeding_yes);
//            viewHolder.mGrowSeedingMyListView = (MyListView) convertView.findViewById(R.id.GrowSeeding_MyListView);
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (QuickeningHistoryRecyclerViewAdapter.ViewHolder) convertView.getTag();
//        }
//
//        GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean groupBean = mList.get(position);
//        viewHolder.mGrowSeedingTitle.setText(groupBean.getMonthFirstVaccineDate());
//
//        if (groupBean.getMonth() == 0) {
//            viewHolder.mGrowSeedingYes.setText("当月出生");
//        } else {
//            viewHolder.mGrowSeedingYes.setText(groupBean.getMonth() + "月龄");
//        }
//        monthFirstVaccineDate = groupBean.getMonthFirstVaccineDate();
//        if (groupBean.getMonthFirstVaccineDate().equals(times)) {
//            viewHolder.mGrowSeedingTitle.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
//            viewHolder.mGrowSeedingYes.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
//        } else {
//            viewHolder.mGrowSeedingTitle.setTextColor(mContext.getResources().getColor(R.color.black));
//            viewHolder.mGrowSeedingYes.setTextColor(mContext.getResources().getColor(R.color.black));
//        }
//
//        List<GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean.ListMapBean> mList2 = mList.get(position).getListMap();
//        GrowSeedlingsTimeChildAdapter growSeedlingsTimeChildAdapter = new GrowSeedlingsTimeChildAdapter(mList2, mContext, position);
//        viewHolder.mGrowSeedingMyListView.setAdapter(growSeedlingsTimeChildAdapter);
////        viewHolder.mGrowSeedingMyListView.smoothScrollToPosition(5);
//        return convertView;
//    }
//}
public class GrowSeedlingsTimeAdapter extends RecyclerView.Adapter<GrowSeedlingsTimeAdapter.ViewHolder> implements GrowSeedlingsTimeChildAdapter.InterFaceGrowSeedlingsChild {
    private List<GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private InterFaceGrowSeedlings mInterFaceGrowSeedlings;
    private boolean flag;
    private boolean flag2 = true;
    private List<Integer> mListStatus = new ArrayList<>();
    private String monthFirstVaccineDate;

    public GrowSeedlingsTimeAdapter(List<GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean> mList, Context mContext, boolean flag, String monthFirstVaccineDate) {
        this.mContext = mContext;
        this.mList = mList;
        this.flag = flag;
        this.monthFirstVaccineDate = monthFirstVaccineDate;
        Log.e("sdsdsdsflag", String.valueOf(flag));
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setInterFaceGrowSeedlings(InterFaceGrowSeedlings mInterFaceGrowSeedlingss) {
        mInterFaceGrowSeedlings = mInterFaceGrowSeedlingss;
    }

    @Override
    public void setSize(int flag2) {
        if (flag2 == 1) {
//            mGrowSeedingTitle.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
//            mGrowSeedingYes.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        } else if (flag2 == 2) {

        }
    }

    public interface InterFaceGrowSeedlings {
        void setSize(int position);
    }

    //获取当前时间
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
    Date curDate = new Date(System.currentTimeMillis());
    String times = formatter.format(curDate);

    @Override
    public GrowSeedlingsTimeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_cloud_cat_notice_elv_adapter, parent ,false);
        View view = mLayoutInflater.inflate(R.layout.item_group_cloud_cat_notice_elv_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GrowSeedlingsTimeAdapter.ViewHolder viewHolder, int position) {
        GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean groupBean = mList.get(position);
        viewHolder.mGrowSeedingTitle.setText(groupBean.getMonthFirstVaccineDate());

        if (groupBean.getMonth() == 0) {
            viewHolder.mGrowSeedingYes.setText("当月出生");
        } else {
            viewHolder.mGrowSeedingYes.setText(groupBean.getMonth() + "月龄");
        }
//      monthFirstVaccineDate = groupBean.getMonthFirstVaccineDate();

        List<GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean.ListMapBean> mList2 = mList.get(position).getListMap();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        viewHolder.mGrowSeedingRecyclerView.setLayoutManager(linearLayoutManager);
        GrowSeedlingsTimeChildAdapter growSeedlingsTimeChildAdapter = new GrowSeedlingsTimeChildAdapter(mList, mList2, mContext, position, flag2);
        growSeedlingsTimeChildAdapter.setInterFaceGrowSeedlingsChild(this);
        viewHolder.mGrowSeedingRecyclerView.setAdapter(growSeedlingsTimeChildAdapter);

        if (flag) {
            Log.e("fffffffff","到了");
            if (mList.get(position).getMonthFirstVaccineDate().equals(times)) {
                for (int j = 0; j < mList2.size(); j++) {
                    if (mList2.get(j).getStatus() == 2) {
                        mInterFaceGrowSeedlings.setSize(position + 1);
                        break;
                    } else if (mList2.get(j).getStatus() == 1 || mList2.get(j).getStatus() == 0) {
                        mInterFaceGrowSeedlings.setSize(position);
                    }
                }
            }
        }
        if (monthFirstVaccineDate.equals(mList.get(position).getMonthFirstVaccineDate())) {
            viewHolder.mGrowSeedingTitle.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            viewHolder.mGrowSeedingYes.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        } else {
            viewHolder.mGrowSeedingTitle.setTextColor(mContext.getResources().getColor(R.color.black));
            viewHolder.mGrowSeedingYes.setTextColor(mContext.getResources().getColor(R.color.black));
        }
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
        TextView mGrowSeedingTitle;
        TextView mGrowSeedingYes;
        RecyclerView mGrowSeedingRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            mGrowSeedingTitle = (TextView) itemView.findViewById(R.id.GrowSeeding_Title);
            mGrowSeedingYes = (TextView) itemView.findViewById(R.id.GrowSeeding_yes);
            mGrowSeedingRecyclerView = (RecyclerView) itemView.findViewById(R.id.GrowSeeding_RecyclerView);
        }
    }
}
