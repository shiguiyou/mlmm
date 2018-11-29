package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.BabyActivity;
import com.wanquan.mlmmx.mlmm.activity.EquipmentActivity;
import com.wanquan.mlmmx.mlmm.activity.FeedBackActivity;
import com.wanquan.mlmmx.mlmm.activity.IntegralStrategyActivity;
import com.wanquan.mlmmx.mlmm.activity.MainActivity;
import com.wanquan.mlmmx.mlmm.activity.ParentTaskActivity;
import com.wanquan.mlmmx.mlmm.activity.ShareActivity;
import com.wanquan.mlmmx.mlmm.activity.SignInActivity;
import com.wanquan.mlmmx.mlmm.beans.AntenatalTimeTableBeans;
import com.wanquan.mlmmx.mlmm.beans.IntegralStrategyBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

/**
 * Created by Administrator on 2017/11/1.
 */

public class IntegralStrategyAdapter extends BaseAdapter {
    private List<IntegralStrategyBeans.DataBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private int size;

    public IntegralStrategyAdapter(Context mContext, List<IntegralStrategyBeans.DataBean> mList, int size) {
        this.mList = mList;
        Log.e("dsdsds", String.valueOf(size));
        this.mContext = mContext;
        this.size = size;
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
        TextView mIntegralStrategyTV;
        TextView mIntegralStrategyTVSI;
        ImageView mIntegralStrategyMoney;
        ImageView mIntegralStrategyIMG;
        LinearLayout mIntegralStrategyLL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_integral_strategy, null);
            viewHolder = new ViewHolder();
            //确定没一个店铺布局中显示的内容
            viewHolder.mIntegralStrategyLL = (LinearLayout) convertView.findViewById(R.id.IntegralStrategy_LL);
            viewHolder.mIntegralStrategyTV = (TextView) convertView.findViewById(R.id.IntegralStrategy_TV);
            viewHolder.mIntegralStrategyTVSI = (TextView) convertView.findViewById(R.id.IntegralStrategy_TVSI);
            viewHolder.mIntegralStrategyMoney = (ImageView) convertView.findViewById(R.id.IntegralStrategy_money);
            viewHolder.mIntegralStrategyIMG = (ImageView) convertView.findViewById(R.id.IntegralStrategy_IMG);
            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示内容
        final IntegralStrategyBeans.DataBean mIntegralStrategyBeans = mList.get(position);
        viewHolder.mIntegralStrategyTV.setText(mIntegralStrategyBeans.getStrategyName());
        if (String.valueOf(mIntegralStrategyBeans.getStrategyVal()).equals("0")) {
            viewHolder.mIntegralStrategyMoney.setVisibility(View.GONE);
            viewHolder.mIntegralStrategyTVSI.setText("已完成");
        } else {
            viewHolder.mIntegralStrategyMoney.setVisibility(View.VISIBLE);
            viewHolder.mIntegralStrategyTVSI.setText("+" + mIntegralStrategyBeans.getStrategyVal());
        }
        if ("推荐用户注册".equals(mIntegralStrategyBeans.getStrategyName())) {
            viewHolder.mIntegralStrategyIMG.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.mIntegralStrategyIMG.setVisibility(View.VISIBLE);
        }
        viewHolder.mIntegralStrategyLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("添加宝宝".equals(mList.get(position).getStrategyName())) {
                    if (viewHolder.mIntegralStrategyTVSI.getText().equals("已完成")) {
                        Toast.makeText(mContext, "您已完成了宝宝添加，还有其它任务等着您哦~", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(mContext, BabyActivity.class);
                        mContext.startActivity(intent);
                    }
                } else if ("发帖".equals(mList.get(position).getStrategyName())) {
                    if (viewHolder.mIntegralStrategyTVSI.getText().equals("已完成")) {
                        Toast.makeText(mContext, "今日发帖任务已完成，明天可以继续赚积分哦~", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.setFinish(2);
                        messageEvent.setShowBank(true);
                        EventBus.getDefault().post(messageEvent);
                        mContext.startActivity(intent);
                    }
                } else if ("回帖".equals(mList.get(position).getStrategyName())) {
                    if (viewHolder.mIntegralStrategyTVSI.getText().equals("已完成")) {
                        Toast.makeText(mContext, "今日回帖任务已完成，明天可以继续赚积分哦~", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.setFinish(2);
                        messageEvent.setShowBank(true);
                        EventBus.getDefault().post(messageEvent);
                        mContext.startActivity(intent);
                    }
                } else if ("绑定设备".equals(mList.get(position).getStrategyName())) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setFinish(3);
                    messageEvent.setShowBank(true);
                    EventBus.getDefault().post(messageEvent);
                    mContext.startActivity(intent);
                } else if ("数据分享".equals(mList.get(position).getStrategyName())) {
                    if (viewHolder.mIntegralStrategyTVSI.getText().equals("已完成")) {
                        Toast.makeText(mContext, "今日数据分享任务已完成，明天可以继续赚积分哦~", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.setFinish(3);
                        messageEvent.setShowBank(true);
                        EventBus.getDefault().post(messageEvent);
                        mContext.startActivity(intent);
                    }
                } else if ("签到".equals(mList.get(position).getStrategyName())) {
                    if (viewHolder.mIntegralStrategyTVSI.getText().equals("已完成")) {
                        Toast.makeText(mContext, "今日签到任务已完成，明天可以继续赚积分哦~", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(mContext, SignInActivity.class);
                        mContext.startActivity(intent);
                    }
                } else if ("邀请亲友".equals(mList.get(position).getStrategyName())) {
                    Intent intent = new Intent(mContext, BabyActivity.class);
                    mContext.startActivity(intent);
                } else if ("亲子任务".equals(mList.get(position).getStrategyName())) {
                    if (viewHolder.mIntegralStrategyTVSI.getText().equals("已完成")) {
                        Toast.makeText(mContext, "今日亲子任务已完成，明天可以继续赚积分哦~", Toast.LENGTH_SHORT).show();
                    } else {
                        if (SPUtils.get(mContext, "BabyState", "").equals("2")) {
                            Intent intent = new Intent(mContext, ParentTaskActivity.class);
                            mContext.startActivity(intent);
                        } else {
                            Toast.makeText(mContext, "亲，只有已出生的宝宝才可以查看哦~", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if ("分享".equals(mList.get(position).getStrategyName())) {
                    if (viewHolder.mIntegralStrategyTVSI.getText().equals("已完成")) {
                        Toast.makeText(mContext, "今日分享任务已完成，明天可以继续赚积分哦~", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(mContext, ShareActivity.class);
                        mContext.startActivity(intent);
                    }
                } else if ("问题反馈".equals(mList.get(position).getStrategyName())) {
                    if (viewHolder.mIntegralStrategyTVSI.getText().equals("已完成")) {
                        Toast.makeText(mContext, "今日问题反馈任务已完成，明天可以继续赚积分哦~", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(mContext, FeedBackActivity.class);
                        mContext.startActivity(intent);
                    }
                }
            }
        });
        return convertView;
    }
}
