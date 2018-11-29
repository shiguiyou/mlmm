package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.GrowSeedlingsDetailsActivity;
import com.wanquan.mlmmx.mlmm.activity.GrowSeedlingsTimeActivity;
import com.wanquan.mlmmx.mlmm.beans.GrowSeedlingsTimeBeans;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/4/13.
 */

public class GrowSeedlingsTimeChildAdapter extends RecyclerView.Adapter<GrowSeedlingsTimeChildAdapter.ViewHolder> {
    private List<GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean> mList1;
    private List<GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean.ListMapBean> mList2;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int fposition;
    private InterFaceGrowSeedlingsChild mInterFaceGrowSeedlingsChild;
    private boolean flag2;

    public GrowSeedlingsTimeChildAdapter(List<GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean> mList1, List<GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean.ListMapBean> mList2, Context mContext, int position, boolean flag2) {
        this.mContext = mContext;
        this.mList1 = mList1;
        this.mList2 = mList2;
        this.flag2 = flag2;
        this.fposition = position;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setInterFaceGrowSeedlingsChild(InterFaceGrowSeedlingsChild mInterFaceGrowSeedlingsChild) {
        this.mInterFaceGrowSeedlingsChild = mInterFaceGrowSeedlingsChild;
    }

    public interface InterFaceGrowSeedlingsChild {
        void setSize(int position);
    }

    //获取当前时间
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
    Date curDate = new Date(System.currentTimeMillis());
    String times = formatter.format(curDate);

    @Override
    public GrowSeedlingsTimeChildAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_child_clout_cat_motice_elv_adapter, parent, false);
        return new GrowSeedlingsTimeChildAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GrowSeedlingsTimeChildAdapter.ViewHolder viewHolder, int position) {
        final GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean.ListMapBean childBean = mList2.get(position);

        if (childBean.getVaccineIsNecessary() == 0) {
            viewHolder.mGrowSeedingImg.setVisibility(View.GONE);
        } else if (childBean.getVaccineIsNecessary() == 1) {
            viewHolder.mGrowSeedingImg.setVisibility(View.VISIBLE);
        }
        viewHolder.mGrowSeedlingsText.setText(childBean.getVaccineName());

        if (childBean.getStatus() == 0) {
//            holders.mGrowSeedlingsName.setText("未打");
            viewHolder.mGrowSeedlingsName.setVisibility(View.GONE);

        } else if (childBean.getStatus() == 1) {
            viewHolder.mGrowSeedlingsName.setVisibility(View.GONE);
        } else if (childBean.getStatus() == 2) {
            viewHolder.mGrowSeedlingsName.setVisibility(View.VISIBLE);
            viewHolder.mGrowSeedlingsName.setText("已逾期");
        }

        if (childBean.getVaccineTotalBatch() == 1) {
            viewHolder.mGrowSeedlingsSize.setVisibility(View.GONE);
        } else {
            viewHolder.mGrowSeedlingsSize.setVisibility(View.VISIBLE);
            viewHolder.mGrowSeedlingsSize.setText("第 " + childBean.getVaccineBatch() + " 次");
        }

        viewHolder.mGrowSeedlingsForm.setText(childBean.getVaccineDesc());

        viewHolder.mCharRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GrowSeedlingsDetailsActivity.class);
                intent.putExtra("id", String.valueOf(childBean.getId()));
                intent.putExtra("method", String.valueOf(childBean.getMethod()));
                intent.putExtra("principle", String.valueOf(childBean.getPrinciple()));
                intent.putExtra("reason", String.valueOf(childBean.getReason()));
                intent.putExtra("time1", String.valueOf(childBean.getVaccineDate()));
                intent.putExtra("time2", String.valueOf(childBean.getReminderDate()));
                intent.putExtra("Batch", String.valueOf(childBean.getVaccineBatch()));
                intent.putExtra("vaccineTotalBatch", String.valueOf(childBean.getVaccineTotalBatch()));
                intent.putExtra("status", String.valueOf(childBean.getStatus()));
                intent.putExtra("title", String.valueOf(childBean.getVaccineName()));
                mContext.startActivity(intent);
            }
        });
        if (flag2) {
            for (int i = 0; i < mList2.size(); i++) {
                if (mList2.get(i).getStatus() == 0 || mList2.get(i).getStatus() == 1) {
                    mInterFaceGrowSeedlingsChild.setSize(1);
                    flag2 = false;
                    break;
                } else if (mList2.get(i).getStatus() == 2) {
                    mInterFaceGrowSeedlingsChild.setSize(2);
                }
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList2.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mCharRelativeLayout;
        ImageView mGrowSeedingImg;
        TextView mGrowSeedlingsText;
        TextView mGrowSeedlingsSize;
        TextView mGrowSeedlingsName;
        TextView mGrowSeedlingsForm;

        public ViewHolder(View itemView) {
            super(itemView);
            mCharRelativeLayout = (LinearLayout) itemView.findViewById(R.id.Char_RelativeLayout);
            mGrowSeedingImg = (ImageView) itemView.findViewById(R.id.GrowSeeding_Img);
            mGrowSeedlingsText = (TextView) itemView.findViewById(R.id.GrowSeedlings_Text);
            mGrowSeedlingsSize = (TextView) itemView.findViewById(R.id.GrowSeedlings_Size);
            mGrowSeedlingsName = (TextView) itemView.findViewById(R.id.GrowSeedlings_Name);
            mGrowSeedlingsForm = (TextView) itemView.findViewById(R.id.GrowSeedlings_Form);

        }
    }
}
