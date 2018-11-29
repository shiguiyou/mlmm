package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
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

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.BabyRoomActivity;
import com.wanquan.mlmmx.mlmm.activity.CheZaiActivity;
import com.wanquan.mlmmx.mlmm.beans.EquipmentDeleteBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeEquipmentBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.QuickeningDeleteBeans;
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

public class EquipmentFragmentAdapter extends BaseAdapter {
    private List<HomeEquipmentBeans.DataBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public InterfaceMDName mInterfaceMDName;

    public EquipmentFragmentAdapter(List<HomeEquipmentBeans.DataBean> list, Context context) {
        mList = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void mInterfaceMDName(InterfaceMDName mInterfaceMDName) {
        this.mInterfaceMDName = mInterfaceMDName;
    }

    public interface InterfaceMDName {
        void XiuGaiName();
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
        RelativeLayout mItemHomeRl;
        ImageView mItemHomeImg;
        TextView mItemHomeName;
        TextView mItemHomeNumber;
        TextView mItemHomeState;
        TextView mItemHomeStates;
        SwipeLayout mItemHomeSwipeLayout;
        LinearLayout mItemHomeLinearLayout;
        TextView mItemHomeT1;
        TextView mItemHomeT2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_equipmentfragment, null);
            viewHolder = new ViewHolder();
            viewHolder.mItemHomeRl = (RelativeLayout) convertView.findViewById(R.id.Item_Home_Rl);
            viewHolder.mItemHomeImg = (ImageView) convertView.findViewById(R.id.Item_Home_Img);
            viewHolder.mItemHomeName = (TextView) convertView.findViewById(R.id.Item_Home_Name);
            viewHolder.mItemHomeNumber = (TextView) convertView.findViewById(R.id.Item_Home_Number);
            viewHolder.mItemHomeState = (TextView) convertView.findViewById(R.id.Item_Home_State);
            viewHolder.mItemHomeStates = (TextView) convertView.findViewById(R.id.Item_Home_States);
            viewHolder.mItemHomeSwipeLayout = (SwipeLayout) convertView.findViewById(R.id.Item_Home_SwipeLayout);
            viewHolder.mItemHomeLinearLayout = (LinearLayout) convertView.findViewById(R.id.Item_Home_LinearLayout);
            viewHolder.mItemHomeT1 = (TextView) convertView.findViewById(R.id.Item_Home_t1);
            viewHolder.mItemHomeT2 = (TextView) convertView.findViewById(R.id.Item_Home_t2);
            convertView.setTag(viewHolder);
            SwipeLayout.addSwipeView(viewHolder.mItemHomeSwipeLayout);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final HomeEquipmentBeans.DataBean mHomeEquipmentBeans = mList.get(position);
        viewHolder.mItemHomeName.setText(mHomeEquipmentBeans.getDeviceName());
        Glide.with(mContext).load(mHomeEquipmentBeans.getThumbNail()).into(viewHolder.mItemHomeImg);

        viewHolder.mItemHomeNumber.setText("设备编号 " + mHomeEquipmentBeans.getDeviceCode());
        if (mHomeEquipmentBeans.getIsOnLine() == 0) {
            viewHolder.mItemHomeState.setText("不在线");
            viewHolder.mItemHomeStates.setText("");
        } else if (mHomeEquipmentBeans.getIsOnLine() == 1) {
            int pm25 = mHomeEquipmentBeans.getPm25Value();
            if (pm25 <= 35 && pm25 > 10) {
                viewHolder.mItemHomeState.setText("空气 ：");
                viewHolder.mItemHomeStates.setText("优");
                viewHolder.mItemHomeStates.setTextColor(mContext.getResources().getColor(R.color.green));
            } else if (pm25 > 35 && pm25 <= 75) {
                viewHolder.mItemHomeState.setText("空气 ：");
                viewHolder.mItemHomeStates.setText("良");
                viewHolder.mItemHomeStates.setTextColor(mContext.getResources().getColor(R.color.horvoice_text_color));
            } else if (pm25 > 75) {
                viewHolder.mItemHomeState.setText("空气 ：");
                viewHolder.mItemHomeStates.setText("差");
                viewHolder.mItemHomeStates.setTextColor(mContext.getResources().getColor(R.color.style_red));
            } else if (pm25 <= 10) {
                viewHolder.mItemHomeState.setText("空气 ：");
                viewHolder.mItemHomeStates.setText("优");
                viewHolder.mItemHomeStates.setTextColor(mContext.getResources().getColor(R.color.green));
            }
        }

        viewHolder.mItemHomeT1.setOnClickListener(new View.OnClickListener() {
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
                        hashMap.put("itfaceId", "014");
                        hashMap.put("deviceCode", mList.get(position).getDeviceCode());
                        hashMap.put("token", SPUtils.get(mContext, "token", ""));
                        hashMap.put("deviceName", editText.getText().toString().trim());
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<EquipmentDeleteBeans>(mContext) {
                                    @Override
                                    public void onSuccess(EquipmentDeleteBeans mEquipmentDeleteBeans, Call call, Response response) {
                                        if (mEquipmentDeleteBeans.getResultCode() == 1) {
                                            Toast.makeText(mContext, "修改名称成功", Toast.LENGTH_SHORT).show();
                                            SwipeLayout.removeSwipeView(viewHolder.mItemHomeSwipeLayout);
//                                            mInterfaceMDName.XiuGaiName();
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
        viewHolder.mItemHomeT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alert;
                alert = new AlertDialog.Builder(mContext).create();
                alert.show();
                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.delete);
                alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView textView = (TextView) alert.findViewById(R.id.cart_delete_content);
                textView.setText("确定删除此设备吗？");

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
                        hashMap.put("itfaceId", "013");
                        hashMap.put("token", SPUtils.get(mContext, "token", ""));
                        hashMap.put("deviceCode", mList.get(position).getDeviceCode());
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<EquipmentDeleteBeans>(mContext) {
                                    @Override
                                    public void onSuccess(EquipmentDeleteBeans mEquipmentDeleteBeans, Call call, Response response) {
                                        if (mEquipmentDeleteBeans.getResultCode() == 1) {
                                            Toast.makeText(mContext, "删除设备成功", Toast.LENGTH_SHORT).show();
//                                          mInterfaceMDName.XiuGaiName();
                                            MessageEvent messageEvent = new MessageEvent();
                                            messageEvent.setShebBei(true);
                                            EventBus.getDefault().post(messageEvent);
                                            SwipeLayout.removeSwipeView(viewHolder.mItemHomeSwipeLayout);
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
        viewHolder.mItemHomeRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.get(position).getIsOnLine() == 1) {
                    String deviceType = mList.get(position).getDeviceType();
                    if ("0F".equals(deviceType) && "10".equals(deviceType)) {
                        Intent intent = new Intent(mContext, BabyRoomActivity.class);
                        intent.putExtra("name", mList.get(position).getDeviceName());
                        intent.putExtra("id", mList.get(position).getDeviceId());
                        intent.putExtra("code", mList.get(position).getDeviceCode());
                        intent.putExtra("img", mList.get(position).getImage());
                        mContext.startActivity(intent);
                    } else if ("04".equals(deviceType)) {
                        Intent intent = new Intent(mContext, CheZaiActivity.class);
                        intent.putExtra("id", mList.get(position).getDeviceId());
                        intent.putExtra("type", mList.get(position).getDeviceType());
                        intent.putExtra("code", mList.get(position).getDeviceCode());
                        mContext.startActivity(intent);
                    } else if ("0E".equals(deviceType)) {
                        Intent intent = new Intent(mContext, BabyRoomActivity.class);
                        intent.putExtra("name", mList.get(position).getDeviceName());
                        intent.putExtra("id", mList.get(position).getDeviceId());
                        intent.putExtra("code", mList.get(position).getDeviceCode());
                        intent.putExtra("img", mList.get(position).getImage());
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, BabyRoomActivity.class);
                        intent.putExtra("name", mList.get(position).getDeviceName());
                        intent.putExtra("id", mList.get(position).getDeviceId());
                        intent.putExtra("code", mList.get(position).getDeviceCode());
                        intent.putExtra("img", mList.get(position).getImage());
                        mContext.startActivity(intent);
                    }
                } else if (mList.get(position).getIsOnLine() == 0) {
                    Toast toast = Toast.makeText(mContext, "当前设备不在线，请连接设备!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        return convertView;
    }
}
