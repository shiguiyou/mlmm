package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
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
import com.wanquan.mlmmx.mlmm.activity.CircleDetailsActivity;
import com.wanquan.mlmmx.mlmm.activity.ReplyActivity;
import com.wanquan.mlmmx.mlmm.activity.SendInvitationActivity;
import com.wanquan.mlmmx.mlmm.beans.BabyRoomDialogGridViewBeans;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentBeans;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentDeleteBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageReplayDeleteBenas;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.phone.MyGridView;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/4.
 */

public class CircleFragmentAdapter extends BaseAdapter {
    List<CircleFragmentBeans.DataBean> mList;
    Context mContext;
    LayoutInflater mInflater;
    InterfaceIsCollection mInterfaceIsCollection;
    boolean isCheck = true;
    int flag;

    public CircleFragmentAdapter(Context mContext, List<CircleFragmentBeans.DataBean> mList, int flag) {
        this.mList = mList;
        this.mContext = mContext;
        this.flag = flag;
        mInflater = LayoutInflater.from(mContext);
    }

    public void InterfaceIsCollection(InterfaceIsCollection mInterfaceIsCollection) {
        this.mInterfaceIsCollection = mInterfaceIsCollection;
    }

    public interface InterfaceIsCollection {
        void isCollection(String str);
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
        CircleImageView mFragmentItemImage;
        TextView mFragmentItemDelete;
        TextView mFragmentItemName;
        TextView mFragmentItemTime;
        TextView mFragmentItemAttention;
        TextView mFragmentItemTitle;
        TextView mFragmentItemContent;
        LinearLayout mFragmentItemLL1;
        TextView mFragmentItemLike;
        LinearLayout mFragmentItemLL2;
        TextView mFragmentItemComment;
        ImageView mFragmentItemCollection;
        MyGridView mFragmentItemMyGridView;
        ImageView mFragmentItemImg;
        ImageView mFragmentItemImg1;
        ImageView mFragmentItemImg2;
        ImageView mFragmentItemImg3;
        ImageView mFragmentItemImg4;
        ImageView mFragmentItemImg5;
        ImageView mFragmentItemImg6;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_circle_fragment, null);
            viewHolder = new ViewHolder();
            //确定没一个店铺布局中显示的内容
            viewHolder.mFragmentItemImage = (CircleImageView) convertView.findViewById(R.id.Fragment_Item_Image);
            viewHolder.mFragmentItemDelete = (TextView) convertView.findViewById(R.id.Fragment_Item_Delete);
            viewHolder.mFragmentItemName = (TextView) convertView.findViewById(R.id.Fragment_Item_Name);
            viewHolder.mFragmentItemTime = (TextView) convertView.findViewById(R.id.Fragment_Item_Time);
            viewHolder.mFragmentItemAttention = (TextView) convertView.findViewById(R.id.Fragment_Item_Attention);
            viewHolder.mFragmentItemTitle = (TextView) convertView.findViewById(R.id.Fragment_Item_Title);
            viewHolder.mFragmentItemContent = (TextView) convertView.findViewById(R.id.Fragment_Item_Content);
            viewHolder.mFragmentItemLL1 = (LinearLayout) convertView.findViewById(R.id.Fragment_Item_LL1);
            viewHolder.mFragmentItemLL2 = (LinearLayout) convertView.findViewById(R.id.Fragment_Item_LL2);
            viewHolder.mFragmentItemComment = (TextView) convertView.findViewById(R.id.Fragment_Item_Comment);
            viewHolder.mFragmentItemCollection = (ImageView) convertView.findViewById(R.id.Fragment_Item_Collection);
            viewHolder.mFragmentItemMyGridView = (MyGridView) convertView.findViewById(R.id.Fragment_Item_MyGridView);
            viewHolder.mFragmentItemImg = (ImageView) convertView.findViewById(R.id.Fragment_Item_img);
            viewHolder.mFragmentItemImg1 = (ImageView) convertView.findViewById(R.id.Fragment_Item_Img1);
            viewHolder.mFragmentItemImg2 = (ImageView) convertView.findViewById(R.id.Fragment_Item_Img2);
            viewHolder.mFragmentItemImg3 = (ImageView) convertView.findViewById(R.id.Fragment_Item_Img3);
            viewHolder.mFragmentItemImg4 = (ImageView) convertView.findViewById(R.id.Fragment_Item_Img4);
            viewHolder.mFragmentItemImg5 = (ImageView) convertView.findViewById(R.id.Fragment_Item_Img5);
            viewHolder.mFragmentItemImg6 = (ImageView) convertView.findViewById(R.id.Fragment_Item_Img6);

            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示内容
        final CircleFragmentBeans.DataBean mCircleFragmentBeans = mList.get(position);
        Glide.with(mContext).load(mCircleFragmentBeans.getHeadIco()).into(viewHolder.mFragmentItemImage);
        if (mCircleFragmentBeans.getNickName() != null) {
            viewHolder.mFragmentItemName.setText(mCircleFragmentBeans.getNickName());
        } else {
            if (!TextUtils.isEmpty(mCircleFragmentBeans.getUserName()) && mCircleFragmentBeans.getUserName().length() >= 11) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mCircleFragmentBeans.getUserName().length(); i++) {
                    char c = mCircleFragmentBeans.getUserName().charAt(i);
                    if (i >= 3 && i <= 6) {
                        sb.append('*');
                    } else {
                        sb.append(c);
                    }
                }
                viewHolder.mFragmentItemName.setText(sb.toString());
            }
        }
        viewHolder.mFragmentItemTime.setText(mCircleFragmentBeans.getCreateTime());
        viewHolder.mFragmentItemTitle.setText(mCircleFragmentBeans.getTitle());
        viewHolder.mFragmentItemContent.setText(mCircleFragmentBeans.getContent());

        if (!String.valueOf(mCircleFragmentBeans.getCommentCount()).equals("0")) {
            viewHolder.mFragmentItemComment.setText(String.valueOf(mCircleFragmentBeans.getCommentCount()));
        } else {
            viewHolder.mFragmentItemComment.setText("0");
        }
        if (flag == 1) {
            viewHolder.mFragmentItemDelete.setVisibility(View.GONE);
            if (!String.valueOf(mCircleFragmentBeans.getFollow()).equals("0")) {
                viewHolder.mFragmentItemCollection.setBackground(mContext.getResources().getDrawable(R.mipmap.collectioned));
                mList.get(position).setCollect(true);
            } else {
                viewHolder.mFragmentItemCollection.setBackground(mContext.getResources().getDrawable(R.mipmap.collection));
                mList.get(position).setCollect(false);
            }
        } else if (flag == 2) {
            viewHolder.mFragmentItemDelete.setVisibility(View.VISIBLE);
            viewHolder.mFragmentItemCollection.setBackground(mContext.getResources().getDrawable(R.mipmap.messagedelete));
        }

        if (mList.get(position).getImg() != null) {
            viewHolder.mFragmentItemImg.setVisibility(View.VISIBLE);
            String img = mList.get(position).getImg();
            String[] imgs = img.split("\\|");
            List<String> mList2 = new ArrayList<>();
            mList2.clear();
            for (int i = 0; i < imgs.length; i++) {
                mList2.add(imgs[i]);
            }
            if (mList2.size() == 1) {
                Glide.with(mContext).load(mList2.get(0)).into(viewHolder.mFragmentItemImg1);
                viewHolder.mFragmentItemImg1.setVisibility(View.VISIBLE);
                viewHolder.mFragmentItemImg2.setVisibility(View.GONE);
                viewHolder.mFragmentItemImg3.setVisibility(View.GONE);
                viewHolder.mFragmentItemImg4.setVisibility(View.GONE);
                viewHolder.mFragmentItemImg5.setVisibility(View.GONE);
                viewHolder.mFragmentItemImg6.setVisibility(View.GONE);
            } else if (mList2.size() == 2) {
                Glide.with(mContext).load(mList2.get(0)).into(viewHolder.mFragmentItemImg1);
                viewHolder.mFragmentItemImg1.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mList2.get(1)).into(viewHolder.mFragmentItemImg2);
                viewHolder.mFragmentItemImg2.setVisibility(View.VISIBLE);
                viewHolder.mFragmentItemImg3.setVisibility(View.GONE);
                viewHolder.mFragmentItemImg4.setVisibility(View.GONE);
                viewHolder.mFragmentItemImg5.setVisibility(View.GONE);
                viewHolder.mFragmentItemImg6.setVisibility(View.GONE);
            } else if (mList2.size() == 3) {
                Glide.with(mContext).load(mList2.get(0)).into(viewHolder.mFragmentItemImg1);
                viewHolder.mFragmentItemImg1.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mList2.get(1)).into(viewHolder.mFragmentItemImg2);
                viewHolder.mFragmentItemImg2.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mList2.get(2)).into(viewHolder.mFragmentItemImg3);
                viewHolder.mFragmentItemImg3.setVisibility(View.VISIBLE);
                viewHolder.mFragmentItemImg4.setVisibility(View.GONE);
                viewHolder.mFragmentItemImg5.setVisibility(View.GONE);
                viewHolder.mFragmentItemImg6.setVisibility(View.GONE);
            } else if (mList2.size() == 4) {
                Glide.with(mContext).load(mList2.get(0)).into(viewHolder.mFragmentItemImg1);
                viewHolder.mFragmentItemImg1.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mList2.get(1)).into(viewHolder.mFragmentItemImg2);
                viewHolder.mFragmentItemImg2.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mList2.get(2)).into(viewHolder.mFragmentItemImg3);
                viewHolder.mFragmentItemImg3.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mList2.get(3)).into(viewHolder.mFragmentItemImg4);
                viewHolder.mFragmentItemImg4.setVisibility(View.VISIBLE);
                viewHolder.mFragmentItemImg5.setVisibility(View.GONE);
                viewHolder.mFragmentItemImg6.setVisibility(View.GONE);
            } else if (mList2.size() == 5) {
                Glide.with(mContext).load(mList2.get(0)).into(viewHolder.mFragmentItemImg1);
                viewHolder.mFragmentItemImg1.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mList2.get(1)).into(viewHolder.mFragmentItemImg2);
                viewHolder.mFragmentItemImg2.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mList2.get(2)).into(viewHolder.mFragmentItemImg3);
                viewHolder.mFragmentItemImg3.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mList2.get(3)).into(viewHolder.mFragmentItemImg4);
                viewHolder.mFragmentItemImg4.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mList2.get(4)).into(viewHolder.mFragmentItemImg5);
                viewHolder.mFragmentItemImg5.setVisibility(View.VISIBLE);
                viewHolder.mFragmentItemImg6.setVisibility(View.GONE);
            } else if (mList2.size() == 6) {
                Glide.with(mContext).load(mList2.get(0)).into(viewHolder.mFragmentItemImg1);
                viewHolder.mFragmentItemImg1.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mList2.get(1)).into(viewHolder.mFragmentItemImg2);
                viewHolder.mFragmentItemImg2.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mList2.get(2)).into(viewHolder.mFragmentItemImg3);
                viewHolder.mFragmentItemImg3.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mList2.get(3)).into(viewHolder.mFragmentItemImg4);
                viewHolder.mFragmentItemImg4.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mList2.get(4)).into(viewHolder.mFragmentItemImg5);
                viewHolder.mFragmentItemImg5.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mList2.get(5)).into(viewHolder.mFragmentItemImg6);
                viewHolder.mFragmentItemImg6.setVisibility(View.VISIBLE);
            }
        } else {
            viewHolder.mFragmentItemImg.setVisibility(View.GONE);
        }

        viewHolder.mFragmentItemLL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 1) {
                    if (!mList.get(position).isCollect()) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("itfaceId", "054");
                        hashMap.put("token", SPUtils.get(mContext, "token", ""));
                        hashMap.put("forumId", mCircleFragmentBeans.getId());
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<CircleFragmentBeans>(mContext) {
                                    @Override
                                    public void onSuccess(CircleFragmentBeans mCircleFragmentBeans, Call call, Response response) {
                                        if (mCircleFragmentBeans.getResultCode() == 1) {
                                            mList.get(position).setCollect(false);
                                            viewHolder.mFragmentItemCollection.setBackground(mContext.getResources().getDrawable(R.mipmap.collectioned));
                                            mInterfaceIsCollection.isCollection("str");
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
                        hashMap.put("forumId", mCircleFragmentBeans.getId());
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<CircleFragmentBeans>(mContext) {
                                    @Override
                                    public void onSuccess(CircleFragmentBeans mCircleFragmentBeans, Call call, Response response) {
                                        if (mCircleFragmentBeans.getResultCode() == 1) {
                                            mList.get(position).setCollect(true);
                                            viewHolder.mFragmentItemCollection.setBackground(mContext.getResources().getDrawable(R.mipmap.collection));
                                            mInterfaceIsCollection.isCollection("str");
                                            Toast toast = Toast.makeText(mContext, "亲，取消成功啦~", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();
                                        } else {
                                            App.ErrorToken(mCircleFragmentBeans.getResultCode(), mCircleFragmentBeans.getMsg());

                                        }
                                    }
                                });
                    }
                } else if (flag == 2) {//删除
                    final AlertDialog alert;
                    alert = new AlertDialog.Builder(mContext).create();
                    alert.show();
                    //加载自定义dialog
                    alert.getWindow().setContentView(R.layout.delete);
                    alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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
                            hashMap.put("itfaceId", "091");
                            hashMap.put("token", SPUtils.get(mContext, "token", ""));
                            hashMap.put("userid", SPUtils.get(mContext, "userid", ""));
                            hashMap.put("forumId", mCircleFragmentBeans.getId());
                            JSONObject jsonObject = new JSONObject(hashMap);

                            OkGo.post(UrlContent.URL).tag(this)
                                    .upJson(jsonObject.toString())
                                    .connTimeOut(10_000)
                                    .execute(new CustomCallBackNoLoading<CircleFragmentDeleteBeans>(mContext) {
                                        @Override
                                        public void onSuccess(CircleFragmentDeleteBeans mCircleFragmentDeleteBeans, Call call, Response response) {
                                            if (mCircleFragmentDeleteBeans.getResultCode() == 1) {
                                                mInterfaceIsCollection.isCollection("str");
                                                Toast.makeText(mContext, "删除成功！", Toast.LENGTH_SHORT).show();
                                                alert.dismiss();
                                            } else {
                                                App.ErrorToken(mCircleFragmentDeleteBeans.getResultCode(), mCircleFragmentDeleteBeans.getMsg());
                                            }
                                        }
                                    });
                        }
                    });
                }
            }
        });
        viewHolder.mFragmentItemLL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CircleDetailsActivity.class);
                intent.putExtra("id", mCircleFragmentBeans.getId());
                intent.putExtra("headico", mCircleFragmentBeans.getHeadIco());
                intent.putExtra("name", mCircleFragmentBeans.getUserName());
                intent.putExtra("name2", mCircleFragmentBeans.getNickName());
                intent.putExtra("message", mCircleFragmentBeans.getMessage());
                intent.putExtra("time", mCircleFragmentBeans.getCreateTime());
                intent.putExtra("title", mCircleFragmentBeans.getTitle());
                intent.putExtra("content", mCircleFragmentBeans.getContent());
                intent.putExtra("img", mCircleFragmentBeans.getImg());
                intent.putExtra("follow", (int)mCircleFragmentBeans.getFollow());
                mContext.startActivity(intent);
            }
        });

//        CircleFragmentGeidViewAdapter circleFragmentGeidViewAdapter = new CircleFragmentGeidViewAdapter(mList,mContext);
//        viewHolder.mFragmentItemMyGridView.setAdapter(circleFragmentGeidViewAdapter);
        return convertView;
    }
}
