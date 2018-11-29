package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.AirSmallActivity;
import com.wanquan.mlmmx.mlmm.beans.EquimentBluetoothBeans;
import com.wanquan.mlmmx.mlmm.beans.EquipmentDeleteBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeEquipmentBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.SwipeLayout;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/9/26.
 */

public class BleNameAdapter extends BaseAdapter {
    List<EquimentBluetoothBeans.DataBean> mList;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public BleNameAdapter(List<EquimentBluetoothBeans.DataBean> list, Context context) {
        mList = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
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
        SwipeLayout mItemBleSwipeLayout;
        TextView mItemBleName;
        TextView mItemBleNameT1;
        TextView mItemBleNameT2;
        RelativeLayout mItemBleLL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_blename, null);
            viewHolder = new ViewHolder();
            viewHolder.mItemBleLL = (RelativeLayout) convertView.findViewById(R.id.item_ble_ll);
            viewHolder.mItemBleSwipeLayout = (SwipeLayout) convertView.findViewById(R.id.item_ble_SwipeLayout);
            viewHolder.mItemBleName = (TextView) convertView.findViewById(R.id.item_ble_name);
            viewHolder.mItemBleNameT1 = (TextView) convertView.findViewById(R.id.item_ble_name_t1);
            viewHolder.mItemBleNameT2 = (TextView) convertView.findViewById(R.id.item_ble_name_t2);
            convertView.setTag(viewHolder);
            SwipeLayout.addSwipeView(viewHolder.mItemBleSwipeLayout);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mItemBleName.setText(mList.get(position).getName());
        viewHolder.mItemBleNameT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alert2;
                alert2 = new AlertDialog.Builder(mContext).create();
                alert2.show();
                //加载自定义dialog
                alert2.getWindow().setContentView(R.layout.delete_dialog);
                alert2.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                alert2.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                alert2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView title = (TextView) alert2.getWindow().findViewById(R.id.cart_delete_title);
                title.setText("修改设备名称");
                //取消
                alert2.getWindow().findViewById(R.id.cart_delete_cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert2.dismiss();
                        return;
                    }
                });
                final EditText editText = alert2.getWindow().findViewById(R.id.cart_delete_content);
                alert2.getWindow().findViewById(R.id.cart_delete_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("itfaceId", "112");
                        hashMap.put("token", SPUtils.get(mContext, "token", ""));
                        hashMap.put("id", mList.get(position).getId());
                        hashMap.put("name", editText.getText().toString().trim());
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<EquipmentDeleteBeans>(mContext) {
                                    @Override
                                    public void onSuccess(EquipmentDeleteBeans mEquipmentDeleteBeans, Call call, Response response) {
                                        if (mEquipmentDeleteBeans.getResultCode() == 1) {
                                            Toast.makeText(mContext, "修改名称成功", Toast.LENGTH_SHORT).show();
                                            SwipeLayout.removeSwipeView(viewHolder.mItemBleSwipeLayout);
                                            MessageEvent messageEvent = new MessageEvent();
                                            messageEvent.setShebBei(true);
                                            EventBus.getDefault().post(messageEvent);
                                            alert2.dismiss();
                                        } else {
                                            Toast.makeText(mContext, mEquipmentDeleteBeans.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
//                                    @Override
//                                    public void onError(Call call, Response response, Exception e) {
//                                        Toast.makeText(mContext, "服务器连接异常，请稍后重试", Toast.LENGTH_SHORT).show();
//                                        super.onError(call, response, e);
//                                    }
                                });
                    }
                });
            }
        });
        viewHolder.mItemBleNameT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alert;
                alert = new AlertDialog.Builder(mContext).create();
                alert.show();
                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.delete);
                alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView textView = (TextView) alert.findViewById(R.id.cart_delete_content);
                textView.setText("确定删除此设备吗?");

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
                        hashMap.put("itfaceId", "111");
                        hashMap.put("token", SPUtils.get(mContext, "token", ""));
                        hashMap.put("id", mList.get(position).getId());
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<EquipmentDeleteBeans>(mContext) {
                                    @Override
                                    public void onSuccess(EquipmentDeleteBeans mEquipmentDeleteBeans, Call call, Response response) {
                                        if (mEquipmentDeleteBeans.getResultCode() == 1) {
                                            Toast.makeText(mContext, "删除设备成功", Toast.LENGTH_SHORT).show();
                                            SwipeLayout.removeSwipeView(viewHolder.mItemBleSwipeLayout);
                                            MessageEvent messageEvent = new MessageEvent();
                                            messageEvent.setShebBei(true);
                                            EventBus.getDefault().post(messageEvent);
                                            alert.dismiss();
                                        } else {
                                            Toast.makeText(mContext, mEquipmentDeleteBeans.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
//                            @Override
//                            public void onError(Call call, Response response, Exception e) {
//                                Toast.makeText(mContext, "服务器连接异常，请稍后重试", Toast.LENGTH_SHORT).show();
//                                super.onError(call, response, e);
//                            }
                                });
                    }
                });
            }
        });
        viewHolder.mItemBleLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AirSmallActivity.class);
                intent.putExtra("flag", "100");
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
}
