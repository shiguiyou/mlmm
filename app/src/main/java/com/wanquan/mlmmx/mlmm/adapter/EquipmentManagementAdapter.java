package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.EquipmentDeleteBeans;
import com.wanquan.mlmmx.mlmm.beans.EquipmentManagementBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.SwipeLayout;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/9/28.
 */

public class EquipmentManagementAdapter extends BaseAdapter {
    private List<EquipmentManagementBeans.DataBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int mid;
    private AddNotific mAddNotific;

    public EquipmentManagementAdapter(List<EquipmentManagementBeans.DataBean> list, Context context, int id) {
        mList = list;
        mContext = context;
        mid = id;
        mAddNotific = (AddNotific) context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public interface AddNotific {
        void putMessage(boolean flag);
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
        RelativeLayout mItemHomeRl;
        CircleImageView mItemEquipmentManageImg;
        TextView mItemEquipmentManageName;
        TextView mItemEquipmentManageNumber;
        LinearLayout mItemEquipmentManageDelete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_equipment_manage, null);
            viewHolder = new EquipmentManagementAdapter.ViewHolder();

            viewHolder.mItemHomeRl = (RelativeLayout) convertView.findViewById(R.id.Item_Home_Rl);
            viewHolder.mItemEquipmentManageImg = (CircleImageView) convertView.findViewById(R.id.Item_Equipment_Manage_Img);
            viewHolder.mItemEquipmentManageName = (TextView) convertView.findViewById(R.id.Item_Equipment_Manage_Name);
            viewHolder.mItemEquipmentManageNumber = (TextView) convertView.findViewById(R.id.Item_Equipment_Manage_Number);
            viewHolder.mItemEquipmentManageDelete = (LinearLayout) convertView.findViewById(R.id.Item_Equipment_Manage_Delete);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final EquipmentManagementBeans.DataBean mEquipmentManagementBeans = mList.get(position);
        Glide.with(mContext).load(mEquipmentManagementBeans.getHeadIco()).into(viewHolder.mItemEquipmentManageImg);
        viewHolder.mItemEquipmentManageName.setText(mEquipmentManagementBeans.getNickName());
        viewHolder.mItemEquipmentManageNumber.setText(mEquipmentManagementBeans.getMobile());

        viewHolder.mItemEquipmentManageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog alert;
                alert = new AlertDialog.Builder(mContext).create();
                alert.show();
                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.delete);
                alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView textView = (TextView) alert.findViewById(R.id.cart_delete_content);
                textView.setText("确定剔除吗？");

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
                        hashMap.put("itfaceId", "018");
                        hashMap.put("token", SPUtils.get(mContext, "token", ""));
                        hashMap.put("deviceId", String.valueOf(mid));
                        hashMap.put("shareUserId", String.valueOf(mEquipmentManagementBeans.getShareUserId()));
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<EquipmentDeleteBeans>(mContext) {
                                    @Override
                                    public void onSuccess(EquipmentDeleteBeans mEquipmentDeleteBeans, Call call, Response response) {
                                        if (mEquipmentDeleteBeans.getResultCode() == 1) {
                                            Toast.makeText(mContext, "剔除设备成功", Toast.LENGTH_SHORT).show();
                                            mAddNotific.putMessage(true);
                                            alert.dismiss();
                                        } else {
                                            Toast.makeText(mContext, mEquipmentDeleteBeans.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onError(Call call, Response response, Exception e) {
                                        Toast.makeText(mContext, "服务器连接异常，请稍后重试", Toast.LENGTH_SHORT).show();
                                        super.onError(call, response, e);
                                    }
                                });
                    }
                });
            }
        });
        return convertView;
    }
}

