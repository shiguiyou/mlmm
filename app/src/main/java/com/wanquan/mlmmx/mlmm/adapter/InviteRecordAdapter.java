package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.InviteRecordActivity;
import com.wanquan.mlmmx.mlmm.beans.AntenatalTimeTableBeans;
import com.wanquan.mlmmx.mlmm.beans.InviteRecordBeans;
import com.wanquan.mlmmx.mlmm.beans.InviteRecordItemBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/1.
 */

public class InviteRecordAdapter extends BaseAdapter {
    private List<InviteRecordBeans.DataBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private InterfaceFinsh mInterfaceFinsh;

    public InviteRecordAdapter(Context mContext, List<InviteRecordBeans.DataBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void finish(InterfaceFinsh mInterfaceFinsh) {
        this.mInterfaceFinsh = mInterfaceFinsh;
    }

    public interface InterfaceFinsh {
        void initfinish();
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
        CircleImageView mItemInviteImg;
        TextView mItemInviteName;
        TextView mItemInvitePhone;
        TextView mItemInviteTime;
        TextView mItemInviteTv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_invite, null);
            viewHolder = new ViewHolder();

            viewHolder.mItemInviteImg = (CircleImageView) convertView.findViewById(R.id.item_invite_img);
            viewHolder.mItemInviteName = (TextView) convertView.findViewById(R.id.item_invite_name);
            viewHolder.mItemInvitePhone = (TextView) convertView.findViewById(R.id.item_invite_phone);
            viewHolder.mItemInviteTime = (TextView) convertView.findViewById(R.id.item_invite_time);
            viewHolder.mItemInviteTv = (TextView) convertView.findViewById(R.id.item_invite_tv);

            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示内容
        final InviteRecordBeans.DataBean mInviteRecordBeans = mList.get(position);

        viewHolder.mItemInviteName.setText(mInviteRecordBeans.getRelationship());
        viewHolder.mItemInvitePhone.setText(mInviteRecordBeans.getMobile());
        viewHolder.mItemInviteTime.setText(mInviteRecordBeans.getRequest_time());
        viewHolder.mItemInviteTv.setText(mInviteRecordBeans.getRequest_time());
        if (mInviteRecordBeans.getStatus() == 0) {
            viewHolder.mItemInviteTv.setText("撤销");
        } else if (mInviteRecordBeans.getStatus() == 1) {
            viewHolder.mItemInviteTv.setText("拒绝");
        } else if (mInviteRecordBeans.getStatus() == 2) {
            viewHolder.mItemInviteTv.setText("同意");
        } else if (mInviteRecordBeans.getStatus() == -1) {
            viewHolder.mItemInviteTv.setText("失效");
        }else if (mInviteRecordBeans.getStatus() == 3) {
            viewHolder.mItemInviteTv.setText("已完成");
        }
        viewHolder.mItemInviteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInviteRecordBeans.getStatus() == 0) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("itfaceId", "085");
                    hashMap.put("token", SPUtils.get(mContext, "token", ""));
                    hashMap.put("id", mInviteRecordBeans.getId());
                    hashMap.put("status", "-1");
                    JSONObject jsonObject = new JSONObject(hashMap);

                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<InviteRecordItemBeans>(mContext) {
                                @Override
                                public void onSuccess(InviteRecordItemBeans mInviteRecordItemBeans, Call call, Response response) {
                                    if (mInviteRecordItemBeans.getResultCode() == 1) {
                                        Toast.makeText(mContext, "操作成功!", Toast.LENGTH_SHORT).show();
                                        mInterfaceFinsh.initfinish();
                                    } else {
                                        Toast.makeText(mContext, mInviteRecordItemBeans.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        return convertView;
    }
}
