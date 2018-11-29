package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.MessageCenterBeans;

import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */

public class MessageCenterAdapter extends BaseAdapter {
    private List<MessageCenterBeans.DataBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private MyCollectInterface mMyCollectInterface;
    boolean delete;
    private boolean ischeck=false;

    public MessageCenterAdapter(List<MessageCenterBeans.DataBean> list, Context context, boolean deletes) {
        delete = deletes;
        mList = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setMessageCenterAdapterList(List<MessageCenterBeans.DataBean> setMessageCenterAdapterList) {
        this.mList = setMessageCenterAdapterList;
        notifyDataSetChanged();
    }

    /**
     * 单选接口
     *
     * @param mMyCollectInterface
     */
    public void setMyCollectInterface(MyCollectInterface mMyCollectInterface) {
        this.mMyCollectInterface = mMyCollectInterface;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
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
        ImageView mItemMessageCenterImg;
        CheckBox mItemMessageCenterCheckBox;
        LinearLayout mItemMessageCenterLL;
        TextView mItemMessageCenterTextView;
        TextView mItemMessageCenterTime;
        LinearLayout mitemMessageCenterll;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_messagecenter, null);
            viewHolder = new ViewHolder();
            viewHolder.mitemMessageCenterll = (LinearLayout) convertView.findViewById(R.id.item_MessageCenter_ll);
            viewHolder.mItemMessageCenterImg = (ImageView) convertView.findViewById(R.id.item_MessageCenter_img);
            viewHolder.mItemMessageCenterCheckBox = (CheckBox) convertView.findViewById(R.id.item_MessageCenter_CheckBox);
            viewHolder.mItemMessageCenterLL = (LinearLayout) convertView.findViewById(R.id.item_MessageCenter_LL);
            viewHolder.mItemMessageCenterTextView = (TextView) convertView.findViewById(R.id.item_MessageCenter_TextView);
            viewHolder.mItemMessageCenterTime = (TextView) convertView.findViewById(R.id.item_MessageCenter_Time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final MessageCenterBeans.DataBean mMessageCenterBeans = mList.get(position);
        viewHolder.mItemMessageCenterCheckBox.setChecked(mMessageCenterBeans.isChoosed());
        //单选
        viewHolder.mItemMessageCenterCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageCenterBeans.setChoosed(((CheckBox) v).isChecked());
                mMyCollectInterface.checkGroup(position, ((CheckBox) v).isChecked());//向外暴露接口
            }
        });
        viewHolder.mItemMessageCenterTextView.setText(mMessageCenterBeans.getTitle());
        viewHolder.mItemMessageCenterTime.setText(String.valueOf(mMessageCenterBeans.getCreateTime()));
        if (delete) {
//            viewHolder.mItemMessageCenterImg.setVisibility(View.GONE);
            viewHolder.mItemMessageCenterCheckBox.setVisibility(View.VISIBLE);
        } else {
//            viewHolder.mItemMessageCenterImg.setVisibility(View.VISIBLE);
            viewHolder.mItemMessageCenterCheckBox.setVisibility(View.GONE);
        }
        return convertView;
    }

//    //EventBus
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(MessageEvent event) {
//        if (event.isGone()) {
//            viewHolder.mItemMessageCenterImg.setVisibility(View.GONE);
//            viewHolder.mItemMessageCenterCheckBox.setVisibility(View.VISIBLE);
//        } else {
//            viewHolder.mItemMessageCenterImg.setVisibility(View.VISIBLE);
//            viewHolder.mItemMessageCenterCheckBox.setVisibility(View.GONE);
//        }
//    }

    /**
     * 复选框接口
     */
    public interface MyCollectInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param position  元素位置
         * @param isChecked 元素选中与否
         */
        void checkGroup(int position, boolean isChecked);
    }
}
