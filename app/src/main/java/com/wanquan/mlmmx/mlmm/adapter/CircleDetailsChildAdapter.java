package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.PostPersonageCenterActivity;
import com.wanquan.mlmmx.mlmm.activity.ReplyActivity;
import com.wanquan.mlmmx.mlmm.beans.CircleDetailsBeans;
import com.wanquan.mlmmx.mlmm.view.MyListView;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/12/4.
 */

public class CircleDetailsChildAdapter extends BaseAdapter {
    List<CircleDetailsBeans.DataBean.ChildCommentBean> mList2;
    List<CircleDetailsBeans.DataBean> mList;
    Context mContext;
    LayoutInflater mInflater;
    String isShow;
    boolean isShowRefresh;
    private String nickName;//回复人

    public CircleDetailsChildAdapter(Context mContext, List<CircleDetailsBeans.DataBean.ChildCommentBean> mList2, String isShow, Boolean isShowRefresh, List<CircleDetailsBeans.DataBean> mList) {
        this.isShowRefresh = isShowRefresh;
        this.mList = mList;
        this.isShow = isShow;
        this.mList2 = mList2;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
//        Log.e("AAA__isShow", isShow);
//        Log.e("AAA__isShowRefresh", String.valueOf(isShowRefresh));

    }

    @Override
    public int getCount() {
        int size;
        if (isShow.equals("0")) {
            if (mList2.size() == 0) {
                size = 0;
            } else if (mList2.size() == 1) {
                size = 1;
            } else if (mList2.size() == 2) {
                size = 2;
            } else {
                size = 3;
            }
        } else {
            return mList2.size();
        }
        return size;
    }

    @Override
    public Object getItem(int position) {
        return mList2.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //缓存布局中的文件
    class ViewHolder {
        TextView mDetailsItemChildName;
        TextView mDetailsItemChildName2;
        TextView mDetailsItemChildName3;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_circle_child_details_fragment, null);
            viewHolder = new ViewHolder();
            //确定没一个店铺布局中显示的内容

            viewHolder.mDetailsItemChildName = (TextView) convertView.findViewById(R.id.Details_Item_Child_Name);
            viewHolder.mDetailsItemChildName2 = (TextView) convertView.findViewById(R.id.Details_Item_Child_Name2);
            viewHolder.mDetailsItemChildName3 = (TextView) convertView.findViewById(R.id.Details_Item_Child_Name3);

            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示内容
        final CircleDetailsBeans.DataBean.ChildCommentBean mCircleDetailsBeans = mList2.get(position);
        for (int i = 0; i < mList2.size(); i++) {
            if (isShow.equals("0")) {
                if (mCircleDetailsBeans.getNickName() != null && mCircleDetailsBeans.getPassiveNickName() != null) {
                    viewHolder.mDetailsItemChildName.setText(mCircleDetailsBeans.getNickName() + " 回复 ");
                    viewHolder.mDetailsItemChildName2.setText(mCircleDetailsBeans.getPassiveNickName() + " : ");
                    viewHolder.mDetailsItemChildName3.setText(mCircleDetailsBeans.getContent() + "");
                } else if (mCircleDetailsBeans.getNickName() == null && mCircleDetailsBeans.getPassiveNickName() != null) {
                    if (!TextUtils.isEmpty(mCircleDetailsBeans.getUserName())) {
                        StringBuilder sb = new StringBuilder();
                        for (int j = 0; j < mCircleDetailsBeans.getUserName().length(); j++) {
                            char c = mCircleDetailsBeans.getUserName().charAt(j);
                            if (j >= 3 && j <= 6) {
                                sb.append("*");
                            } else {
                                sb.append(c);
                            }
                        }
//                        StringUtils.isNotBlank()
                        viewHolder.mDetailsItemChildName.setText(sb.toString() + " 回复 ");
                        viewHolder.mDetailsItemChildName2.setText(mCircleDetailsBeans.getPassiveNickName() + " : ");
                        viewHolder.mDetailsItemChildName3.setText(mCircleDetailsBeans.getContent() + "");

                    }
                } else if (mCircleDetailsBeans.getNickName() != null && mCircleDetailsBeans.getPassiveNickName() == null) {
                    if (!TextUtils.isEmpty(mCircleDetailsBeans.getPassiveUserName())) {
                        StringBuilder sb = new StringBuilder();
                        for (int j = 0; j < mCircleDetailsBeans.getPassiveUserName().length(); j++) {
                            char c = mCircleDetailsBeans.getPassiveUserName().charAt(j);
                            if (j >= 3 && j <= 6) {
                                sb.append("*");
                            } else {
                                sb.append(c);
                            }
                        }
                        viewHolder.mDetailsItemChildName.setText(mCircleDetailsBeans.getNickName() + " 回复 ");
                        viewHolder.mDetailsItemChildName2.setText(sb.toString() + " : ");
                        viewHolder.mDetailsItemChildName3.setText(mCircleDetailsBeans.getContent() + "");
                    }
                } else if (mCircleDetailsBeans.getNickName() == null && mCircleDetailsBeans.getPassiveNickName() == null) {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < mCircleDetailsBeans.getUserName().length(); j++) {
                        char c = mCircleDetailsBeans.getUserName().charAt(j);
                        if (j >= 3 && j <= 6) {
                            sb.append("*");
                        } else {
                            sb.append(c);
                        }
                    }
                    StringBuilder sb2 = new StringBuilder();
                    for (int k = 0; k < mCircleDetailsBeans.getUserName().length(); k++) {
                        char c = mCircleDetailsBeans.getUserName().charAt(k);
                        if (k >= 3 && k <= 6) {
                            sb2.append("*");
                        } else {
                            sb2.append(c);
                        }
                    }
                    viewHolder.mDetailsItemChildName.setText(sb.toString() + " 回复 ");
                    viewHolder.mDetailsItemChildName2.setText(sb2.toString() + " : ");
                    viewHolder.mDetailsItemChildName3.setText(mCircleDetailsBeans.getContent() + "");

                }
                if (i == 3) {
                    break;
                }
            } else if (isShow.equals("1")) {
                if (isShowRefresh) {
                    if (mCircleDetailsBeans.getNickName() != null && mCircleDetailsBeans.getPassiveNickName() != null) {
                        viewHolder.mDetailsItemChildName.setText(mCircleDetailsBeans.getNickName() + " 回复 ");
                        viewHolder.mDetailsItemChildName2.setText(mCircleDetailsBeans.getPassiveNickName() + " : ");
                        viewHolder.mDetailsItemChildName3.setText(mCircleDetailsBeans.getContent() + "");
                    } else if (mCircleDetailsBeans.getNickName() != null && mCircleDetailsBeans.getPassiveNickName() != null) {
                        if (!TextUtils.isEmpty(mCircleDetailsBeans.getUserName())) {
                            StringBuilder sb = new StringBuilder();
                            for (int j = 0; j < mCircleDetailsBeans.getUserName().length(); j++) {
                                char c = mCircleDetailsBeans.getUserName().charAt(j);
                                if (j >= 3 && j <= 6) {
                                    sb.append("*");
                                } else {
                                    sb.append(c);
                                }
                            }
                            viewHolder.mDetailsItemChildName.setText(sb.toString() + " 回复 ");
                            viewHolder.mDetailsItemChildName2.setText(mCircleDetailsBeans.getPassiveNickName() + " : ");
                            viewHolder.mDetailsItemChildName3.setText(mCircleDetailsBeans.getContent() + "");
                        }
                    } else if (mCircleDetailsBeans.getNickName() != null && mCircleDetailsBeans.getPassiveNickName() == null) {
                        if (!TextUtils.isEmpty(mCircleDetailsBeans.getPassiveUserName())) {
                            StringBuilder sb = new StringBuilder();
                            for (int j = 0; j < mCircleDetailsBeans.getPassiveUserName().length(); j++) {
                                char c = mCircleDetailsBeans.getPassiveUserName().charAt(j);
                                if (j >= 3 && j <= 6) {
                                    sb.append("*");
                                } else {
                                    sb.append(c);
                                }
                            }
                            viewHolder.mDetailsItemChildName.setText(mCircleDetailsBeans.getNickName() + " 回复 ");
                            viewHolder.mDetailsItemChildName2.setText(sb.toString() + " : ");
                            viewHolder.mDetailsItemChildName3.setText(mCircleDetailsBeans.getContent() + "");
                        }
                    } else if (mCircleDetailsBeans.getNickName() == null && mCircleDetailsBeans.getPassiveNickName() == null) {
                        StringBuilder sb = new StringBuilder();
                        for (int j = 0; j < mCircleDetailsBeans.getUserName().length(); j++) {
                            char c = mCircleDetailsBeans.getUserName().charAt(j);
                            if (j >= 3 && j <= 6) {
                                sb.append("*");
                            } else {
                                sb.append(c);
                            }
                        }
                        StringBuilder sb2 = new StringBuilder();
                        for (int k = 0; k < mCircleDetailsBeans.getUserName().length(); k++) {
                            char c = mCircleDetailsBeans.getUserName().charAt(k);
                            if (k >= 3 && k <= 6) {
                                sb2.append("*");
                            } else {
                                sb2.append(c);
                            }
                        }
                        viewHolder.mDetailsItemChildName.setText(sb.toString() + " 回复 ");
                        viewHolder.mDetailsItemChildName2.setText(sb2.toString() + " : ");
                        viewHolder.mDetailsItemChildName3.setText(mCircleDetailsBeans.getContent() + "");
                    }
                }
            }
            viewHolder.mDetailsItemChildName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mList.get(position).getChildComment() != null) {
                        if (mList.get(position).getChildComment().size() > 0) {
                            Intent intent = new Intent(mContext, PostPersonageCenterActivity.class);
                            intent.putExtra("id", String.valueOf(mList.get(position).getChildComment().get(0).getReviewUserId()));
                            mContext.startActivity(intent);
                        }
                    }
                }
            });
            viewHolder.mDetailsItemChildName2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, PostPersonageCenterActivity.class);
                    intent.putExtra("id", String.valueOf(mList.get(position).getUserId()));
                    mContext.startActivity(intent);
                }
            });
        }
        return convertView;
    }
}
