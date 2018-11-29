package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.CircleDetailsActivity;
import com.wanquan.mlmmx.mlmm.activity.PostPersonageCenterActivity;
import com.wanquan.mlmmx.mlmm.activity.ReplyActivity;
import com.wanquan.mlmmx.mlmm.beans.CircleDetailsBeans;
import com.wanquan.mlmmx.mlmm.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/12/4.
 */

public class CircleDetailsAdapter extends BaseAdapter {
    private List<CircleDetailsBeans.DataBean> mList;
    private List<String> mList2 = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private String id;
    private InterfaceCircleDetails mInterfaceCircleDetails;
    private InterfaceCircleDetailsisShow mInterfaceCircleDetailsisShow;
    private boolean isShowRefresh;
    private String isShow = "0";


    public CircleDetailsAdapter(Context mContext, List<CircleDetailsBeans.DataBean> mList, String id, boolean isShowRefresh) {
        this.isShowRefresh = isShowRefresh;
        this.mList = mList;
        this.mContext = mContext;
        this.id = id;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setDetails(InterfaceCircleDetails mInterfaceCircleDetails) {
        this.mInterfaceCircleDetails = mInterfaceCircleDetails;
    }

    public void setDetailsisShow(InterfaceCircleDetailsisShow mInterfaceCircleDetailsisShow) {
        this.mInterfaceCircleDetailsisShow = mInterfaceCircleDetailsisShow;
    }

    public interface InterfaceCircleDetails {
        void setCircle(String UserName, int pid, int fid);
    }

    public interface InterfaceCircleDetailsisShow {
        void setCircleisShow(boolean isShowRefresh);
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

        private CircleImageView mDetailsItemImage;
        private TextView mDetailsItemName;
        private TextView mDetailsItemDays;
        private TextView mDetailsItemTime;
        private TextView mDetailsItemContent;
        private TextView mDetailsItemGD;
        private LinearLayout mDetailsItemLL;
        private MyListView mDetailsItemMyListView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_circle_details_fragment, null);
            viewHolder = new ViewHolder();
            //确定没一个店铺布局中显示的内容
            viewHolder.mDetailsItemImage = (CircleImageView) convertView.findViewById(R.id.Details_Item_Image);
            viewHolder.mDetailsItemName = (TextView) convertView.findViewById(R.id.Details_Item_Name);
            viewHolder.mDetailsItemDays = (TextView) convertView.findViewById(R.id.Details_Item_Days);
            viewHolder.mDetailsItemTime = (TextView) convertView.findViewById(R.id.Details_Item_Time);
            viewHolder.mDetailsItemMyListView = (MyListView) convertView.findViewById(R.id.Details_Item_MyListView);
            viewHolder.mDetailsItemContent = (TextView) convertView.findViewById(R.id.Details_Item_Content);
            viewHolder.mDetailsItemGD = (TextView) convertView.findViewById(R.id.Details_Item_GD);
            viewHolder.mDetailsItemLL = (LinearLayout) convertView.findViewById(R.id.Details_Item_LL);
            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示内容
        final CircleDetailsBeans.DataBean mCircleDetailsBeans = mList.get(position);
        final List<CircleDetailsBeans.DataBean.ChildCommentBean> mList2 = mList.get(position).getChildComment();
        viewHolder.mDetailsItemDays.setText(mCircleDetailsBeans.getMessage());
        Glide.with(mContext).load(mCircleDetailsBeans.getHeadIco()).into(viewHolder.mDetailsItemImage);
        if (mCircleDetailsBeans.getNickName().equals("")) {
            if (!TextUtils.isEmpty(mCircleDetailsBeans.getUserName()) && mCircleDetailsBeans.getUserName().length() >= 11) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mCircleDetailsBeans.getUserName().length(); i++) {
                    char c = mCircleDetailsBeans.getUserName().charAt(i);
                    if (i >= 3 && i <= 6) {
                        sb.append("*");
                    } else {
                        sb.append(c);
                    }
                }
                viewHolder.mDetailsItemName.setText(sb.toString());
            }
        } else {
            viewHolder.mDetailsItemName.setText(mCircleDetailsBeans.getNickName());
        }

        viewHolder.mDetailsItemTime.setText(mCircleDetailsBeans.getCreateTime());
        viewHolder.mDetailsItemContent.setText(mCircleDetailsBeans.getContent());

        if (mList.get(position).getChildComment().size() <= 3) {
            viewHolder.mDetailsItemGD.setVisibility(View.GONE);
        } else {
            if (isShowRefresh) {
                viewHolder.mDetailsItemGD.setVisibility(View.GONE);
            } else {
                viewHolder.mDetailsItemGD.setVisibility(View.VISIBLE);
            }
        }

        viewHolder.mDetailsItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PostPersonageCenterActivity.class);
                intent.putExtra("id", String.valueOf(mList.get(position).getUserId()));
                mContext.startActivity(intent);
            }
        });
        viewHolder.mDetailsItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PostPersonageCenterActivity.class);
                intent.putExtra("id", String.valueOf(mList.get(position).getUserId()));
                mContext.startActivity(intent);
            }
        });

        viewHolder.mDetailsItemGD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterfaceCircleDetailsisShow.setCircleisShow(true);
//                viewHolder.mDetailsItemGD.setVisibility(View.GONE);
                mCircleDetailsBeans.getChildComment().get(position).setShow(true);
                mCircleDetailsBeans.setShow(true);
            }
        });
        viewHolder.mDetailsItemLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReplyActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("ids", mCircleDetailsBeans.getId());
                intent.putExtra("name", mCircleDetailsBeans.getNickName());
                mContext.startActivity(intent);
            }
        });
        viewHolder.mDetailsItemMyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mInterfaceCircleDetails.setCircle(mCircleDetailsBeans.getChildComment().get(position).getNickName(),
                        mCircleDetailsBeans.getChildComment().get(position).getId(),
                        mCircleDetailsBeans.getId());
            }
        });
        if (isShowRefresh) {
            isShow = "1";
        } else {
            isShow = "0";
        }
        CircleDetailsChildAdapter circleDetailsChildAdapter = new CircleDetailsChildAdapter(mContext, mList2, isShow, isShowRefresh, mList);
        viewHolder.mDetailsItemMyListView.setAdapter(circleDetailsChildAdapter);

        return convertView;
    }
}
