package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.PostPersonageCenterActivity;
import com.wanquan.mlmmx.mlmm.beans.HomeListViewBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageCenterActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageReplayBenas;
import com.wanquan.mlmmx.mlmm.beans.MessageReplayDeleteBenas;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MyListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/19.
 */

public class MessageReplayAdapter extends BaseAdapter {
    List<MessageReplayBenas.DataBean> mList = new ArrayList<>();
    Context mContext;
    LayoutInflater mLayoutInflater;
    InterfaceMessageReplay mInterfaceMessageReplay;

    public MessageReplayAdapter(List<MessageReplayBenas.DataBean> list, Context context) {
        mList = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setReplayAdapter(List<MessageReplayBenas.DataBean> list) {
        this.mList = list;
        this.notifyDataSetChanged();
    }

    public void setMessageReplay(InterfaceMessageReplay mInterfaceMessageReplay) {
        this.mInterfaceMessageReplay = mInterfaceMessageReplay;
    }

    public interface InterfaceMessageReplay {
        void delete(String str);
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
        CircleImageView mItemMessageReplayImg;
        TextView mItemMessageReplayName;
        TextView mItemMessageReplayTime;
        TextView mItemMessageReplayTitle;
        TextView mItemMessageReplayContent;
        LinearLayout mItemMessageReplayDelete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_message_replay, null);
            viewHolder = new ViewHolder();

            viewHolder.mItemMessageReplayImg = (CircleImageView) convertView.findViewById(R.id.item_message_replay_img);
            viewHolder.mItemMessageReplayName = (TextView) convertView.findViewById(R.id.item_message_replay_name);
            viewHolder.mItemMessageReplayTime = (TextView) convertView.findViewById(R.id.item_message_replay_time);
            viewHolder.mItemMessageReplayTitle = (TextView) convertView.findViewById(R.id.item_message_replay_title);
            viewHolder.mItemMessageReplayContent = (TextView) convertView.findViewById(R.id.item_message_replay_content);
            viewHolder.mItemMessageReplayDelete = (LinearLayout) convertView.findViewById(R.id.item_message_replay_delete);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(mList.get(position).getHeadIco()).into(viewHolder.mItemMessageReplayImg);
        viewHolder.mItemMessageReplayName.setText(mList.get(position).getReviewerName());
        viewHolder.mItemMessageReplayTime.setText(mList.get(position).getCreateTime());
        if (mList.get(position).getUserId() == mList.get(position).getReceiver()) {
            viewHolder.mItemMessageReplayTitle.setText("回复我:" + mList.get(position).getContent());
        } else {
            viewHolder.mItemMessageReplayTitle.setText("回复" + mList.get(position).getReceiveName() + ":" + mList.get(position).getContent());
        }

        viewHolder.mItemMessageReplayImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PostPersonageCenterActivity.class);
                intent.putExtra("id", String.valueOf(mList.get(position).getReviewUserId()));
                mContext.startActivity(intent);
            }
        });
        viewHolder.mItemMessageReplayName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PostPersonageCenterActivity.class);
                intent.putExtra("id", String.valueOf(mList.get(position).getReviewUserId()));
                mContext.startActivity(intent);
            }
        });

        viewHolder.mItemMessageReplayContent.setText("当前话题 ：" + mList.get(position).getTopic());
        viewHolder.mItemMessageReplayDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alert;
                alert = new AlertDialog.Builder(mContext).create();
                alert.show();
                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.delete);
                alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView textView = (TextView) alert.findViewById(R.id.cart_delete_content);
                textView.setText("确定删除此消息吗?");

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
//                        Log.e("rrrrrrrr", String.valueOf(mList.get(position).getNotificationId()));
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("itfaceId", "058");
                        hashMap.put("token", SPUtils.get(mContext, "token", ""));
                        hashMap.put("id", mList.get(position).getId());
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<MessageReplayDeleteBenas>(mContext) {
                                    @Override
                                    public void onSuccess(MessageReplayDeleteBenas mMessageReplayDeleteBenas, Call call, Response response) {
                                        if (mMessageReplayDeleteBenas.getResultCode() == 1) {
                                            mInterfaceMessageReplay.delete("1");
                                            Toast toast = Toast.makeText(mContext, "删除成功了哦~", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();
                                            alert.dismiss();
                                        }
                                    }
                                });
                    }
                });
            }
        });
        return convertView;
    }
}

