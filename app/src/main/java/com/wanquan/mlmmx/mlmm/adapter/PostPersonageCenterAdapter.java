package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.GuideActivity;
import com.wanquan.mlmmx.mlmm.beans.BabyRoomDialogGridViewBeans;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.PostPersonageCenterBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/10/19.
 */

public class PostPersonageCenterAdapter extends BaseAdapter {
    private List<PostPersonageCenterBeans.DataBean.UserForumListBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mList2 = new ArrayList<>();
    private String title;


    public PostPersonageCenterAdapter(Context mContext, List<PostPersonageCenterBeans.DataBean.UserForumListBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
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
        LinearLayout mItemPostpersongerCenterLl;
        TextView mItemPostpersongerCenterSize;
        TextView mItemPostpersongerCenterTime;
        TextView mItemPostpersongerCenterContent;
        LinearLayout mItemPostpersongerCenterLinkLinearLayout;
        TextView mItemPostpersongerCenterLink;
        ImageView mItemPostpersongerCenterImg1;
        ImageView mItemPostpersongerCenterImg2;
        ImageView mItemPostpersongerCenterImg3;
        LinearLayout mItemPostpersongerCenterLinearLayout2;
        ImageView mItemPostpersongerCenterImg4;
        ImageView mItemPostpersongerCenterImg5;
        ImageView mItemPostpersongerCenterImg6;
        LinearLayout mItemPostpersongerCenterLL1;
        ImageView mItemPostpersongerCenterCollection;
        TextView mItemPostpersongerCenterDelete;
        LinearLayout mItemPostpersongerCenterLL3;
        TextView mItemPostpersongerCenterLll;
        LinearLayout mItemPostpersongerCenterLL2;
        TextView mItemPostpersongerCenterComment;
        TextView mItemPostpersongerCenterView;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_postpersonger_center, null);
            viewHolder = new ViewHolder();
            //确定没一个店铺布局中显示的内容

            viewHolder.mItemPostpersongerCenterLl = (LinearLayout) convertView.findViewById(R.id.item_postpersonger_center_ll);
            viewHolder.mItemPostpersongerCenterSize = (TextView) convertView.findViewById(R.id.item_postpersonger_center_size);
            viewHolder.mItemPostpersongerCenterTime = (TextView) convertView.findViewById(R.id.item_postpersonger_center_time);
            viewHolder.mItemPostpersongerCenterContent = (TextView) convertView.findViewById(R.id.item_postpersonger_center_content);
            viewHolder.mItemPostpersongerCenterLinkLinearLayout = (LinearLayout) convertView.findViewById(R.id.item_postpersonger_center_LinkLinearLayout);
            viewHolder.mItemPostpersongerCenterLink = (TextView) convertView.findViewById(R.id.item_postpersonger_center_Link);
            viewHolder.mItemPostpersongerCenterImg1 = (ImageView) convertView.findViewById(R.id.item_postpersonger_center_img1);
            viewHolder.mItemPostpersongerCenterImg2 = (ImageView) convertView.findViewById(R.id.item_postpersonger_center_img2);
            viewHolder.mItemPostpersongerCenterImg3 = (ImageView) convertView.findViewById(R.id.item_postpersonger_center_img3);
            viewHolder.mItemPostpersongerCenterLinearLayout2 = (LinearLayout) convertView.findViewById(R.id.item_postpersonger_center_LinearLayout2);
            viewHolder.mItemPostpersongerCenterImg4 = (ImageView) convertView.findViewById(R.id.item_postpersonger_center_img4);
            viewHolder.mItemPostpersongerCenterImg5 = (ImageView) convertView.findViewById(R.id.item_postpersonger_center_img5);
            viewHolder.mItemPostpersongerCenterImg6 = (ImageView) convertView.findViewById(R.id.item_postpersonger_center_img6);
            viewHolder.mItemPostpersongerCenterLL1 = (LinearLayout) convertView.findViewById(R.id.item_postpersonger_center_LL1);
            viewHolder.mItemPostpersongerCenterCollection = (ImageView) convertView.findViewById(R.id.item_postpersonger_center_Collection);
            viewHolder.mItemPostpersongerCenterDelete = (TextView) convertView.findViewById(R.id.item_postpersonger_center_Delete);
            viewHolder.mItemPostpersongerCenterLL3 = (LinearLayout) convertView.findViewById(R.id.item_postpersonger_center_LL3);
            viewHolder.mItemPostpersongerCenterLll = (TextView) convertView.findViewById(R.id.item_postpersonger_center_lll);
            viewHolder.mItemPostpersongerCenterLL2 = (LinearLayout) convertView.findViewById(R.id.item_postpersonger_center_LL2);
            viewHolder.mItemPostpersongerCenterComment = (TextView) convertView.findViewById(R.id.item_postpersonger_center_Comment);
            viewHolder.mItemPostpersongerCenterView = (TextView) convertView.findViewById(R.id.item_postpersonger_center_view);


            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示内容
        final PostPersonageCenterBeans.DataBean.UserForumListBean mPostPersonageCenterBeans = mList.get(position);

        String[] split = mList.get(position).getCreateTime().split("-");
        viewHolder.mItemPostpersongerCenterSize.setText(split[2]);
        viewHolder.mItemPostpersongerCenterTime.setText(split[0] + "." + split[1]);

        viewHolder.mItemPostpersongerCenterContent.setText(mPostPersonageCenterBeans.getContent());

        viewHolder.mItemPostpersongerCenterComment.setText(mPostPersonageCenterBeans.getCommentCount() + "");

        if (!String.valueOf(mPostPersonageCenterBeans.getFollow()).equals("0")) {
            viewHolder.mItemPostpersongerCenterCollection.setBackground(mContext.getResources().getDrawable(R.mipmap.collectioned));
            mList.get(position).setCollect(true);
        } else {
            viewHolder.mItemPostpersongerCenterCollection.setBackground(mContext.getResources().getDrawable(R.mipmap.collection));
            mList.get(position).setCollect(false);
        }
        //浏览量
        viewHolder.mItemPostpersongerCenterLll.setText(mPostPersonageCenterBeans.getReadTimes() + "");
        //链接
        if (mPostPersonageCenterBeans.getLink() != null) {
            viewHolder.mItemPostpersongerCenterLinkLinearLayout.setVisibility(View.VISIBLE);
            if (mPostPersonageCenterBeans.getLink().contains("share=1")) {
                viewHolder.mItemPostpersongerCenterLink.setText("资讯");
            } else {
                viewHolder.mItemPostpersongerCenterLink.setText("育儿知识");
            }
        } else {
            viewHolder.mItemPostpersongerCenterLinkLinearLayout.setVisibility(View.GONE);
        }

        viewHolder.mItemPostpersongerCenterLinkLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPostPersonageCenterBeans.getLink().contains("share=1")) {
                    title = "资讯";
                } else {
                    title = "育儿知识";
                }
                Intent intent = new Intent(mContext, GuideActivity.class);
                intent.putExtra("flag", "3");
                intent.putExtra("htmlUrl", mPostPersonageCenterBeans.getLink());
                intent.putExtra("title", title);
                mContext.startActivity(intent);
            }
        });


        if (mList.get(position).getImg() != null) {
            String img = mList.get(position).getImg();
            String[] imgs = img.split("\\|");
            mList2.clear();
            for (int i = 0; i < imgs.length; i++) {
                mList2.add(imgs[i]);
            }
//            if (mList2.size() > 3) {
//                viewHolder.mItemPostpersongerCenterLinearLayout2.setVisibility(View.VISIBLE);
//            } else {
//                viewHolder.mItemPostpersongerCenterLinearLayout2.setVisibility(View.GONE);
//            }
            if (mList2.size() == 1) {
                Glide.with(mContext).load(mList2.get(0)).into(viewHolder.mItemPostpersongerCenterImg1);
                viewHolder.mItemPostpersongerCenterImg1.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg2.setVisibility(View.INVISIBLE);
                viewHolder.mItemPostpersongerCenterImg3.setVisibility(View.GONE);
                viewHolder.mItemPostpersongerCenterImg4.setVisibility(View.GONE);
                viewHolder.mItemPostpersongerCenterImg5.setVisibility(View.GONE);
                viewHolder.mItemPostpersongerCenterImg6.setVisibility(View.GONE);
            } else if (mList2.size() == 2) {
                Glide.with(mContext).load(mList2.get(0)).into(viewHolder.mItemPostpersongerCenterImg1);
                Glide.with(mContext).load(mList2.get(1)).into(viewHolder.mItemPostpersongerCenterImg2);
                viewHolder.mItemPostpersongerCenterImg1.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg2.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg3.setVisibility(View.INVISIBLE);
                viewHolder.mItemPostpersongerCenterImg4.setVisibility(View.GONE);
                viewHolder.mItemPostpersongerCenterImg5.setVisibility(View.GONE);
                viewHolder.mItemPostpersongerCenterImg6.setVisibility(View.GONE);
            } else if (mList2.size() == 3) {
                Glide.with(mContext).load(mList2.get(0)).into(viewHolder.mItemPostpersongerCenterImg1);
                Glide.with(mContext).load(mList2.get(1)).into(viewHolder.mItemPostpersongerCenterImg2);
                Glide.with(mContext).load(mList2.get(2)).into(viewHolder.mItemPostpersongerCenterImg3);
                viewHolder.mItemPostpersongerCenterImg1.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg2.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg3.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg4.setVisibility(View.GONE);
                viewHolder.mItemPostpersongerCenterImg5.setVisibility(View.GONE);
                viewHolder.mItemPostpersongerCenterImg6.setVisibility(View.GONE);
            } else if (mList2.size() == 4) {
                Glide.with(mContext).load(mList2.get(0)).into(viewHolder.mItemPostpersongerCenterImg1);
                Glide.with(mContext).load(mList2.get(1)).into(viewHolder.mItemPostpersongerCenterImg2);
                Glide.with(mContext).load(mList2.get(2)).into(viewHolder.mItemPostpersongerCenterImg3);
                Glide.with(mContext).load(mList2.get(3)).into(viewHolder.mItemPostpersongerCenterImg4);
                viewHolder.mItemPostpersongerCenterImg1.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg2.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg3.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg4.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg5.setVisibility(View.INVISIBLE);
                viewHolder.mItemPostpersongerCenterImg6.setVisibility(View.INVISIBLE);
            } else if (mList2.size() == 5) {
                Glide.with(mContext).load(mList2.get(0)).into(viewHolder.mItemPostpersongerCenterImg1);
                Glide.with(mContext).load(mList2.get(1)).into(viewHolder.mItemPostpersongerCenterImg2);
                Glide.with(mContext).load(mList2.get(2)).into(viewHolder.mItemPostpersongerCenterImg3);
                Glide.with(mContext).load(mList2.get(3)).into(viewHolder.mItemPostpersongerCenterImg4);
                Glide.with(mContext).load(mList2.get(4)).into(viewHolder.mItemPostpersongerCenterImg5);
                viewHolder.mItemPostpersongerCenterImg1.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg2.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg3.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg4.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg5.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg6.setVisibility(View.INVISIBLE);
            } else if (mList2.size() == 6) {
                Glide.with(mContext).load(mList2.get(0)).into(viewHolder.mItemPostpersongerCenterImg1);
                Glide.with(mContext).load(mList2.get(1)).into(viewHolder.mItemPostpersongerCenterImg2);
                Glide.with(mContext).load(mList2.get(2)).into(viewHolder.mItemPostpersongerCenterImg3);
                Glide.with(mContext).load(mList2.get(3)).into(viewHolder.mItemPostpersongerCenterImg4);
                Glide.with(mContext).load(mList2.get(4)).into(viewHolder.mItemPostpersongerCenterImg5);
                Glide.with(mContext).load(mList2.get(5)).into(viewHolder.mItemPostpersongerCenterImg6);
                viewHolder.mItemPostpersongerCenterImg1.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg2.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg3.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg4.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg5.setVisibility(View.VISIBLE);
                viewHolder.mItemPostpersongerCenterImg6.setVisibility(View.VISIBLE);
            }
        } else {
            viewHolder.mItemPostpersongerCenterImg1.setVisibility(View.GONE);
            viewHolder.mItemPostpersongerCenterImg2.setVisibility(View.GONE);
            viewHolder.mItemPostpersongerCenterImg3.setVisibility(View.GONE);
            viewHolder.mItemPostpersongerCenterImg4.setVisibility(View.GONE);
            viewHolder.mItemPostpersongerCenterImg5.setVisibility(View.GONE);
            viewHolder.mItemPostpersongerCenterImg6.setVisibility(View.GONE);
        }
        int size = mList.size();
        if (size != 0) {
            if (size - 1 == position) {
                viewHolder.mItemPostpersongerCenterView.setVisibility(View.GONE);
            } else {
                viewHolder.mItemPostpersongerCenterView.setVisibility(View.VISIBLE);
            }
        }

        viewHolder.mItemPostpersongerCenterCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mList.get(position).isCollect()) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("itfaceId", "054");
                    hashMap.put("token", SPUtils.get(mContext, "token", ""));
                    hashMap.put("forumId", mPostPersonageCenterBeans.getId());
                    JSONObject jsonObject = new JSONObject(hashMap);

                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<CircleFragmentBeans>(mContext) {
                                @Override
                                public void onSuccess(CircleFragmentBeans mCircleFragmentBeans, Call call, Response response) {
                                    if (mCircleFragmentBeans.getResultCode() == 1) {
                                        mList.get(position).setCollect(true);
                                        viewHolder.mItemPostpersongerCenterCollection.setBackground(mContext.getResources().getDrawable(R.mipmap.collectioned));
//                                            mInterfaceIsCollection.isCollection("str");
//                                        MessageEvent messageEvent = new MessageEvent();
//                                        messageEvent.setItFaceCollect(true);
//                                        EventBus.getDefault().post(messageEvent);

                                        Toast toast = Toast.makeText(mContext, "亲，收藏成功啦~", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    } else {
                                        App.ErrorToken(mCircleFragmentBeans.getResultCode(), mCircleFragmentBeans.getMsg());

                                    }
                                }
                            });
                } else {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("itfaceId", "055");
                    hashMap.put("token", SPUtils.get(mContext, "token", ""));
                    hashMap.put("forumId", mPostPersonageCenterBeans.getId());
                    JSONObject jsonObject = new JSONObject(hashMap);

                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<CircleFragmentBeans>(mContext) {
                                @Override
                                public void onSuccess(CircleFragmentBeans mCircleFragmentBeans, Call call, Response response) {
                                    if (mCircleFragmentBeans.getResultCode() == 1) {
                                        mList.get(position).setCollect(false);
                                        viewHolder.mItemPostpersongerCenterCollection.setBackground(mContext.getResources().getDrawable(R.mipmap.collection));
//                                            mInterfaceIsCollection.isCollection("str");
//                                        MessageEvent messageEvent = new MessageEvent();
//                                        messageEvent.setItFaceCollect(true);
//                                        EventBus.getDefault().post(messageEvent);

                                        Toast toast = Toast.makeText(mContext, "亲，取消成功啦~", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    } else {
                                        App.ErrorToken(mCircleFragmentBeans.getResultCode(), mCircleFragmentBeans.getMsg());

                                    }
                                }
                            });
                }
            }
        });
        return convertView;
    }
}
