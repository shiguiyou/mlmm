package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.SendInvitationClassifyActivity;
import com.wanquan.mlmmx.mlmm.beans.BabyRoomDialogGridViewBeans;
import com.wanquan.mlmmx.mlmm.beans.SendInvitationClassifyBeans;

import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */

public class SendInvitationClassifyAdapter extends BaseAdapter {
    List<SendInvitationClassifyBeans.DataBean> mList;
    Context mContext;
    LayoutInflater mInflater;

    public SendInvitationClassifyAdapter(Context mContext, List<SendInvitationClassifyBeans.DataBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
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

    //缓存布局中的文件
    class ViewHolder {
        TextView mItemSendInvitation;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_send_invitation_classfy, null);
            viewHolder = new ViewHolder();
            viewHolder.mItemSendInvitation = (TextView) convertView.findViewById(R.id.item_send_invitation);
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mItemSendInvitation.setText(mList.get(position).getValue());
        //显示内容
        return convertView;
    }
}
