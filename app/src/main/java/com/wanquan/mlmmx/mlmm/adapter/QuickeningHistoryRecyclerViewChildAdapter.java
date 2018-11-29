package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.GrowSeedlingsDetailsActivity;
import com.wanquan.mlmmx.mlmm.activity.QuickeningHistoryActivity;
import com.wanquan.mlmmx.mlmm.beans.GrowSeedlingsTimeBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.MessageReplayDeleteBenas;
import com.wanquan.mlmmx.mlmm.beans.QuickeningDeleteBeans;
import com.wanquan.mlmmx.mlmm.beans.QuickeningHistoryBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.SwipeLayout;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/13.
 */

public class QuickeningHistoryRecyclerViewChildAdapter extends RecyclerView.Adapter<QuickeningHistoryRecyclerViewChildAdapter.ViewHolder> {
    private List<QuickeningHistoryBeans.DataBean.FetalMoveHisBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public QuickeningHistoryRecyclerViewChildAdapter(List<QuickeningHistoryBeans.DataBean.FetalMoveHisBean> mList, Context mContext) {
        this.mContext = mContext;
        this.mList = mList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    //获取当前时间
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
    Date curDate = new Date(System.currentTimeMillis());
    String times = formatter.format(curDate);

    @Override
    public QuickeningHistoryRecyclerViewChildAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.quickeninghistory_c, parent, false);
        return new QuickeningHistoryRecyclerViewChildAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final QuickeningHistoryRecyclerViewChildAdapter.ViewHolder viewHolder, final int position) {
//        final GrowSeedlingsTimeBeans.DataBean.MonthVaccineBean.ListMapBean childBean = mList2.get(position);
//        viewHolder.mQuickeningChildTime1.setText("计时 " + mList.get(position).getClickTimes() + " 分钟");
        String startTime = mList.get(position).getStartTime();
        String endTime = mList.get(position).getEndTime();
        String startTimes = startTime.substring(startTime.indexOf(" ") + 1);
        String endTimes = endTime.substring(endTime.indexOf(" ") + 1);

        viewHolder.mQuickeningChildTime2.setText(startTimes + " - " + endTimes);
        viewHolder.mQuickeningChildSize1.setText(mList.get(position).getMoveTimes());
        viewHolder.mQuickeningChildSize2.setText(mList.get(position).getClickTimes());

        viewHolder.mQuickeningChildDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final AlertDialog alert;
                alert = new AlertDialog.Builder(mContext).create();
                alert.show();
                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.delete);
                alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView textView = (TextView) alert.findViewById(R.id.cart_delete_content);
                textView.setText("确定删除此条记录吗?");

                //取消
                alert.getWindow().findViewById(R.id.cart_delete_cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                        return;
                    }
                });
                alert.getWindow().findViewById(R.id.cart_delete_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("itfaceId", "102");
                        hashMap.put("token", SPUtils.get(mContext, "token", ""));
                        hashMap.put("fetalId", mList.get(position).getMid());
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<QuickeningDeleteBeans>(mContext) {
                                    @Override
                                    public void onSuccess(QuickeningDeleteBeans mQuickeningDeleteBeans, Call call, Response response) {
                                        if (mQuickeningDeleteBeans.getResultCode() == 1) {
                                            MessageEvent messageEvent = new MessageEvent();
                                            messageEvent.setTaiDong(true);
                                            EventBus.getDefault().post(messageEvent);
                                            SwipeLayout.removeSwipeView(viewHolder.mQuickeningChildSwipeLayout);
                                            alert.dismiss();
                                            Toast.makeText(mContext, "删除成功！", Toast.LENGTH_SHORT).show();
                                        } else {
                                            App.ErrorToken(mQuickeningDeleteBeans.getResultCode(), mQuickeningDeleteBeans.getMsg());
                                        }
                                    }
                                });
                    }
                });
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private SwipeLayout mQuickeningChildSwipeLayout;
        private TextView mQuickeningChildTime1;
        private TextView mQuickeningChildTime2;
        private TextView mQuickeningChildSize1;
        private TextView mQuickeningChildSize2;
        private TextView mQuickeningChildDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mQuickeningChildSwipeLayout = (SwipeLayout) itemView.findViewById(R.id.Quickening_Child_SwipeLayout);
            mQuickeningChildTime1 = (TextView) itemView.findViewById(R.id.Quickening_Child_Time1);
            mQuickeningChildTime2 = (TextView) itemView.findViewById(R.id.Quickening_Child_Time2);
            mQuickeningChildSize1 = (TextView) itemView.findViewById(R.id.Quickening_Child_Size1);
            mQuickeningChildSize2 = (TextView) itemView.findViewById(R.id.Quickening_Child_Size2);
            mQuickeningChildDelete = (TextView) itemView.findViewById(R.id.Quickening_Child_Delete);
        }
    }
}
