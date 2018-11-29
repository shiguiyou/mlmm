package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.BabyPhonesActivity;
import com.wanquan.mlmmx.mlmm.beans.BabyPhoneBeans;
import com.wanquan.mlmmx.mlmm.beans.BabyPhonesDeleteBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MessagePicturesLayout;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class MessageAdapter extends BaseAdapter {
    List<BabyPhoneBeans.DataBean> mList;
    Context mContext;
    LayoutInflater mInflater;
    private MessagePicturesLayout.Callback mCallback;

    public MessageAdapter(Context mContext, List<BabyPhoneBeans.DataBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public MessageAdapter setPictureClickCallback(MessagePicturesLayout.Callback callback) {
        mCallback = callback;
        return this;
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
        ImageView mitemaddressimg;
        TextView mitemaddress;
        TextView mTNickname;
        LinearLayout mitemaddressll;
        LinearLayout mItemll;
        TextView mTTime;
        TextView mItemBabyDelete;
        TextView mTContent;
        MessagePicturesLayout mLPictures;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.recycler_main_message, null);
            viewHolder = new ViewHolder();
            //确定没一个店铺布局中显示的内容
            viewHolder.mitemaddressimg = (ImageView) convertView.findViewById(R.id.item_address_img);
            viewHolder.mitemaddress = (TextView) convertView.findViewById(R.id.item_address);
            viewHolder.mitemaddressll = (LinearLayout) convertView.findViewById(R.id.item_address_ll);
            viewHolder.mItemll = (LinearLayout) convertView.findViewById(R.id.Item_ll);
            viewHolder.mTNickname = (TextView) convertView.findViewById(R.id.t_nickname);
            viewHolder.mTTime = (TextView) convertView.findViewById(R.id.t_time);
            viewHolder.mItemBabyDelete = (TextView) convertView.findViewById(R.id.item_Baby_delete);
            viewHolder.mTContent = (TextView) convertView.findViewById(R.id.t_content);
            viewHolder.mLPictures = (MessagePicturesLayout) convertView.findViewById(R.id.l_pictures);
            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
            viewHolder.mLPictures.setCallback(mCallback);

        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示内容
        if (mList.get(position).getAlbumImglist().size() == 0) {
            viewHolder.mItemll.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.mItemll.setVisibility(View.VISIBLE);
        }
        viewHolder.mitemaddress.setText(mList.get(position).getPlace());
        if (mList.get(position).getPlace().equals("")) {
            viewHolder.mitemaddressll.setVisibility(View.GONE);
        } else {
            viewHolder.mitemaddressll.setVisibility(View.VISIBLE);
        }
//        viewHolder.mTNickname.setText(mList.get(position).getDistanceDateReminder());
        if (SPUtils.get(mContext, "authority", "").equals("1") || SPUtils.get(mContext, "authority", "").equals("2")) {
            viewHolder.mItemBabyDelete.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mItemBabyDelete.setVisibility(View.GONE);
        }

        viewHolder.mTTime.setText(mList.get(position).getCreateTime());
        viewHolder.mTContent.setText(mList.get(position).getTitle());
        viewHolder.mLPictures.set((mList.get(position).getAlbumImglist()), (mList.get(position).getAlbumImglist()));
        viewHolder.mItemBabyDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alert;
                alert = new AlertDialog.Builder(mContext).create();
                alert.show();
                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.delete_dialogs);
                alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView cart_delete_title = alert.getWindow().findViewById(R.id.cart_delete_title);
                TextView cart_delete_content = alert.getWindow().findViewById(R.id.cart_delete_content);
                TextView cart_delete_cancle = alert.getWindow().findViewById(R.id.cart_delete_cancle);
                TextView cart_delete_confirm = alert.getWindow().findViewById(R.id.cart_delete_confirm);

                cart_delete_title.setText("提示消息");
                cart_delete_content.setText("慎重，再点就再也见不到我啦！这可是无法恢复的哦~");
                cart_delete_cancle.setText("取消");
                cart_delete_confirm.setText("确认");
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
                        hashMap.put("itfaceId", "040");
                        hashMap.put("token", SPUtils.get(mContext, "token", ""));
                        hashMap.put("id", mList.get(position).getId());
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<BabyPhonesDeleteBeans>(mContext) {
                                    @Override
                                    public void onSuccess(BabyPhonesDeleteBeans mBabyPhonesDeleteBeans, Call call, Response response) {
                                        if (mBabyPhonesDeleteBeans.getResultCode() == 1) {
                                            MessageEvent messageEvent = new MessageEvent();
                                            messageEvent.setDeleteId(String.valueOf(mList.get(position).getId()));
                                            EventBus.getDefault().post(messageEvent);

                                            Toast toast = Toast.makeText(mContext, "亲，删除成功啦~", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();
                                            alert.dismiss();
                                            notifyDataSetChanged();
                                        } else {
                                            Toast.makeText(mContext, mBabyPhonesDeleteBeans.getMsg(), Toast.LENGTH_SHORT).show();
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
