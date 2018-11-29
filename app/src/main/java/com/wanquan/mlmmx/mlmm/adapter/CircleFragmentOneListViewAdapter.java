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
import com.wanquan.mlmmx.mlmm.activity.CircleDetailNewActivity;
import com.wanquan.mlmmx.mlmm.activity.GuideActivity;
import com.wanquan.mlmmx.mlmm.activity.MessageActivity;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentBeans;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentDeleteBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.activity.PostPersonageCenterActivity;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/13.
 */

public class CircleFragmentOneListViewAdapter extends BaseAdapter {
    private List<CircleFragmentBeans.DataBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private InterfaceIsCollection mInterfaceIsCollection;
    private int flag;
    private List<String> mList2 = new ArrayList<>();
    private String img;
    private String[] imgs;
    private String title;


    public CircleFragmentOneListViewAdapter(List<CircleFragmentBeans.DataBean> mList, Context mContext, int flag) {
        this.mContext = mContext;
        this.mList = mList;
        this.flag = flag;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }


    public void InterfaceIsCollection(InterfaceIsCollection mInterfaceIsCollection) {
        this.mInterfaceIsCollection = mInterfaceIsCollection;
    }

    public interface InterfaceIsCollection {
        void isCollection(String str);
    }

    @Override
    public int getCount() {
        Log.e("finishIdmlist", String.valueOf(mList.size()));

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
        TextView mFragmentItemTitle;
        TextView mFragmentItemContent;
        LinearLayout mFragmentItemLinkLinearLayout;
        TextView mFragmentItemLink;
        ImageView mFragmentItemImg1;
        LinearLayout mFragmentItemLinearLayout2;
        ImageView mFragmentItemLinearLayout2Img1;
        ImageView mFragmentItemLinearLayout2Img2;
        LinearLayout mFragmentItemLinearLayout3;
        ImageView mFragmentItemLinearLayout3Img1;
        ImageView mFragmentItemLinearLayout3Img2;
        ImageView mFragmentItemLinearLayout3Img3;
        LinearLayout mFragmentItemLL;
        CircleImageView mFragmentItemImage;
        TextView mFragmentItemTV1;
        TextView mFragmentItemTV2;
        LinearLayout mFragmentItemLL1;
        ImageView mFragmentItemCollection;
        TextView mFragmentItemDelete;
        LinearLayout mFragmentItemLL3;
        TextView mFragmentItemLll;
        LinearLayout mFragmentItemLL2;
        TextView mFragmentItemComment;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_circle_fragment_one, null);
            viewHolder = new ViewHolder();

            viewHolder.mFragmentItemTitle = (TextView) convertView.findViewById(R.id.Fragment_Item_Title);
            viewHolder.mFragmentItemContent = (TextView) convertView.findViewById(R.id.Fragment_Item_Content);
            viewHolder.mFragmentItemLinkLinearLayout = (LinearLayout) convertView.findViewById(R.id.Fragment_Item_LinkLinearLayout);
            viewHolder.mFragmentItemLink = (TextView) convertView.findViewById(R.id.Fragment_Item_Link);
            viewHolder.mFragmentItemImg1 = (ImageView) convertView.findViewById(R.id.Fragment_Item_img1);
            viewHolder.mFragmentItemLinearLayout2 = (LinearLayout) convertView.findViewById(R.id.Fragment_Item_LinearLayout2);
            viewHolder.mFragmentItemLinearLayout2Img1 = (ImageView) convertView.findViewById(R.id.Fragment_Item_LinearLayout2_img1);
            viewHolder.mFragmentItemLinearLayout2Img2 = (ImageView) convertView.findViewById(R.id.Fragment_Item_LinearLayout2_img2);
            viewHolder.mFragmentItemLinearLayout3 = (LinearLayout) convertView.findViewById(R.id.Fragment_Item_LinearLayout3);
            viewHolder.mFragmentItemLinearLayout3Img1 = (ImageView) convertView.findViewById(R.id.Fragment_Item_LinearLayout3_img1);
            viewHolder.mFragmentItemLinearLayout3Img2 = (ImageView) convertView.findViewById(R.id.Fragment_Item_LinearLayout3_img2);
            viewHolder.mFragmentItemLinearLayout3Img3 = (ImageView) convertView.findViewById(R.id.Fragment_Item_LinearLayout3_img3);
            viewHolder.mFragmentItemLL = (LinearLayout) convertView.findViewById(R.id.Fragment_Item_LL);
            viewHolder.mFragmentItemImage = (CircleImageView) convertView.findViewById(R.id.Fragment_Item_Image);
            viewHolder.mFragmentItemTV1 = (TextView) convertView.findViewById(R.id.Fragment_Item_TV1);
            viewHolder.mFragmentItemTV2 = (TextView) convertView.findViewById(R.id.Fragment_Item_TV2);
            viewHolder.mFragmentItemLL1 = (LinearLayout) convertView.findViewById(R.id.Fragment_Item_LL1);
            viewHolder.mFragmentItemCollection = (ImageView) convertView.findViewById(R.id.Fragment_Item_Collection);
            viewHolder.mFragmentItemDelete = (TextView) convertView.findViewById(R.id.Fragment_Item_Delete);
            viewHolder.mFragmentItemLL3 = (LinearLayout) convertView.findViewById(R.id.Fragment_Item_LL3);
            viewHolder.mFragmentItemLll = (TextView) convertView.findViewById(R.id.Fragment_Item_lll);
            viewHolder.mFragmentItemLL2 = (LinearLayout) convertView.findViewById(R.id.Fragment_Item_LL2);
            viewHolder.mFragmentItemComment = (TextView) convertView.findViewById(R.id.Fragment_Item_Comment);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final CircleFragmentBeans.DataBean mCircleFragmentBeans = mList.get(position);
        if (mCircleFragmentBeans.getCircleName() != null) {
            viewHolder.mFragmentItemTitle.setText("来自圈子：" + mCircleFragmentBeans.getCircleName());
        }
        viewHolder.mFragmentItemContent.setText(mCircleFragmentBeans.getContent());
        Glide.with(mContext).load(mCircleFragmentBeans.getHeadIco()).into(viewHolder.mFragmentItemImage);
        //评论个数
        if (!String.valueOf(mCircleFragmentBeans.getCommentCount()).equals("0")) {
            viewHolder.mFragmentItemComment.setText(String.valueOf(mCircleFragmentBeans.getCommentCount()));
        } else {
            viewHolder.mFragmentItemComment.setText("0");
        }
        //浏览量
        viewHolder.mFragmentItemLll.setText(mCircleFragmentBeans.getReadTimes() + "");
        //链接
        if (mCircleFragmentBeans.getLink() != null) {
            viewHolder.mFragmentItemLinkLinearLayout.setVisibility(View.VISIBLE);
            if (mCircleFragmentBeans.getLink().contains("share=1")) {
                viewHolder.mFragmentItemLink.setText("资讯");
            } else {
                viewHolder.mFragmentItemLink.setText("育儿知识");
            }
        } else {
            viewHolder.mFragmentItemLinkLinearLayout.setVisibility(View.GONE);
        }

        viewHolder.mFragmentItemLinkLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCircleFragmentBeans.getLink().contains("share=1")) {
                    title = "资讯";
                } else {
                    title = "育儿知识";
                }
                Intent intent = new Intent(mContext, GuideActivity.class);
                intent.putExtra("flag", "3");
                intent.putExtra("htmlUrl", mCircleFragmentBeans.getLink());
                intent.putExtra("title", title);
                mContext.startActivity(intent);
            }
        });

        if (mCircleFragmentBeans.getNickName() != null) {
            viewHolder.mFragmentItemTV1.setText(mCircleFragmentBeans.getNickName());
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
                viewHolder.mFragmentItemTV1.setText(sb.toString());
            }
        }

        if (flag == 1) {
            if (mList.get(position).getFollowOne() != null) {
                if (mList.get(position).getFollowOne()) {
                    viewHolder.mFragmentItemTV2.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.mFragmentItemTV2.setVisibility(View.GONE);
                }
            }

            viewHolder.mFragmentItemDelete.setVisibility(View.GONE);
            if (mCircleFragmentBeans.getFollow() != 0) {
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

        if (mList.get(position).getThumbnail() != null) {
            img = "";
            imgs = null;
            mList2.clear();
            img = mList.get(position).getImg();
            imgs = img.split("\\|");
            for (int i = 0; i < imgs.length; i++) {
                mList2.add(imgs[i]);
            }
            if (mList2.size() == 1) {
                viewHolder.mFragmentItemImg1.setVisibility(View.VISIBLE);
                viewHolder.mFragmentItemLinearLayout2.setVisibility(View.GONE);
                viewHolder.mFragmentItemLinearLayout3.setVisibility(View.GONE);

                Glide.with(mContext).load(mList2.get(0)).into(viewHolder.mFragmentItemImg1);
            } else if (mList2.size() == 2) {
                viewHolder.mFragmentItemImg1.setVisibility(View.GONE);
                viewHolder.mFragmentItemLinearLayout2.setVisibility(View.VISIBLE);
                viewHolder.mFragmentItemLinearLayout3.setVisibility(View.GONE);

                Glide.with(mContext).load(mList2.get(0)).into(viewHolder.mFragmentItemLinearLayout2Img1);
                Glide.with(mContext).load(mList2.get(1)).into(viewHolder.mFragmentItemLinearLayout2Img2);
            } else if (mList2.size() >= 3) {
                viewHolder.mFragmentItemImg1.setVisibility(View.GONE);
                viewHolder.mFragmentItemLinearLayout2.setVisibility(View.GONE);
                viewHolder.mFragmentItemLinearLayout3.setVisibility(View.VISIBLE);

                Glide.with(mContext).load(mList2.get(0)).into(viewHolder.mFragmentItemLinearLayout3Img1);
                Glide.with(mContext).load(mList2.get(1)).into(viewHolder.mFragmentItemLinearLayout3Img2);
                Glide.with(mContext).load(mList2.get(2)).into(viewHolder.mFragmentItemLinearLayout3Img3);
            }
        } else {
            viewHolder.mFragmentItemImg1.setVisibility(View.GONE);
            viewHolder.mFragmentItemLinearLayout2.setVisibility(View.GONE);
            viewHolder.mFragmentItemLinearLayout3.setVisibility(View.GONE);
        }

        viewHolder.mFragmentItemLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PostPersonageCenterActivity.class);
                intent.putExtra("id", String.valueOf(mList.get(position).getUserId()));
                mContext.startActivity(intent);
            }
        });

        viewHolder.mFragmentItemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CircleDetailNewActivity.class);
                intent.putExtra("id", String.valueOf(mList.get(position).getCircleId()));
                mContext.startActivity(intent);
            }
        });
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
                                            mList.get(position).setCollect(true);
                                            viewHolder.mFragmentItemCollection.setBackground(mContext.getResources().getDrawable(R.mipmap.collectioned));
//                                            mInterfaceIsCollection.isCollection("str");
                                            MessageEvent messageEvent = new MessageEvent();
                                            messageEvent.setItFaceCollect(true);
                                            EventBus.getDefault().post(messageEvent);

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
                                            mList.get(position).setCollect(false);
                                            viewHolder.mFragmentItemCollection.setBackground(mContext.getResources().getDrawable(R.mipmap.collection));
//                                            mInterfaceIsCollection.isCollection("str");
                                            MessageEvent messageEvent = new MessageEvent();
                                            messageEvent.setItFaceCollect(true);
                                            EventBus.getDefault().post(messageEvent);

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
                    TextView textView = alert.getWindow().findViewById(R.id.cart_delete_content);
                    textView.setText("确认删除该帖子？");
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

        return convertView;
    }
}


