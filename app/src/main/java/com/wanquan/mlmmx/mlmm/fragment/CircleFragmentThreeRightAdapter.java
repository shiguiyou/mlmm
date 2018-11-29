package com.wanquan.mlmmx.mlmm.fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentThreeBeans;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentThreessBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by xcfchangfeng on 2017/5/18.
 */

public class CircleFragmentThreeRightAdapter extends BaseAdapter {
    private List<CircleFragmentThreessBeans.DataBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ViewHolder viewHolder;
    private int id;


    public CircleFragmentThreeRightAdapter(List<CircleFragmentThreessBeans.DataBean> list, Context context, int id) {
        this.mList = list;
        this.mContext = context;
        this.id = id;
        this.mLayoutInflater = LayoutInflater.from(mContext);
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
        ImageView mItemCircleThreeRightImg;
        TextView mItemCircleThreeRightTitle;
        TextView mItemCircleThreeRightContent;
        ImageView mItemCircleThreeRightAdd;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_circle_three_right, null);
            viewHolder = new ViewHolder();
            viewHolder.mItemCircleThreeRightImg = (ImageView) convertView.findViewById(R.id.item_circle_three_right_img);
            viewHolder.mItemCircleThreeRightTitle = (TextView) convertView.findViewById(R.id.item_circle_three_right_title);
            viewHolder.mItemCircleThreeRightContent = (TextView) convertView.findViewById(R.id.item_circle_three_right_content);
            viewHolder.mItemCircleThreeRightAdd = (ImageView) convertView.findViewById(R.id.item_circle_three_right_add);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(mList.get(position).getImg()).into(viewHolder.mItemCircleThreeRightImg);
        viewHolder.mItemCircleThreeRightTitle.setText(mList.get(position).getName());
        viewHolder.mItemCircleThreeRightContent.setText(mList.get(position).getDesc());

        String isFollow = String.valueOf(mList.get(position).getIsFollow());
        if (isFollow == null || "0".equals(isFollow)) {
            viewHolder.mItemCircleThreeRightAdd.setBackground(mContext.getResources().getDrawable(R.mipmap.circleweiguanzhu));
            mList.get(position).setFlag(false);
        } else if ("1".equals(isFollow)) {
            viewHolder.mItemCircleThreeRightAdd.setBackground(mContext.getResources().getDrawable(R.mipmap.circleyiguanzhu));
            mList.get(position).setFlag(true);
        }

        if (id == 0) {//选择圈子
            viewHolder.mItemCircleThreeRightAdd.setVisibility(View.GONE);
        } else {//圈子列表
            viewHolder.mItemCircleThreeRightAdd.setVisibility(View.VISIBLE);
        }

        viewHolder.mItemCircleThreeRightAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.get(position).isFlag()) {
                    initNetWork(0);
                } else {
                    initNetWork(1);
                }
            }

            private void initNetWork(final int isFollow) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("itfaceId", "115");
                hashMap.put("token", SPUtils.get(mContext, "token", ""));
                hashMap.put("id", mList.get(position).getId());
                hashMap.put("isFollow", isFollow);
                JSONObject jsonObject = new JSONObject(hashMap);

                OkGo.post(UrlContent.URL).tag(this)
                        .upJson(jsonObject.toString())
                        .connTimeOut(10_000)
                        .execute(new CustomCallBackNoLoading<CircleFragmentThreeBeans>(mContext) {
                            @Override
                            public void onSuccess(CircleFragmentThreeBeans mCircleFragmentThreeBeans, Call call, Response response) {
                                if (mCircleFragmentThreeBeans.getResultCode() == 1) {
                                    if (isFollow == 0) {
                                        Toast.makeText(mContext, "取消关注成功！", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(mContext, "关注成功！", Toast.LENGTH_SHORT).show();
                                    }
                                    MessageEvent messageEvent = new MessageEvent();
                                    messageEvent.setQZid(String.valueOf(id));
                                    EventBus.getDefault().post(messageEvent);
                                } else {
                                    App.ErrorToken(mCircleFragmentThreeBeans.getResultCode(), mCircleFragmentThreeBeans.getMsg());
                                }
                            }
                        });
            }
        });
        return convertView;
    }
}

