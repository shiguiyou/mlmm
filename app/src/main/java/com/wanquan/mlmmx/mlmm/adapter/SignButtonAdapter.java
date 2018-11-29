package com.wanquan.mlmmx.mlmm.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.IntegralStrategyActivity;
import com.wanquan.mlmmx.mlmm.activity.SignInActivity;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.MyIntegralBeans;
import com.wanquan.mlmmx.mlmm.beans.SignInBottonBeans;
import com.wanquan.mlmmx.mlmm.beans.SignInDuiHuanBeans;
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
 * Created by Administrator on 2017/10/19.
 */

public class SignButtonAdapter extends BaseAdapter {
    List<SignInBottonBeans.DataBean> mList;
    Context mContext;
    LayoutInflater mInflater;

    public SignButtonAdapter(Context mContext, List<SignInBottonBeans.DataBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
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
        TextView mItemMyIntegralMoney;
        TextView mItemMyIntegralTitle;
        TextView mItemMyIntegralSize;
        TextView mItemMyIntegralTv;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_my_integral, null);
            viewHolder = new ViewHolder();
            //确定没一个店铺布局中显示的内容
            viewHolder.mItemMyIntegralMoney = (TextView) convertView.findViewById(R.id.item_my_integral_money);
            viewHolder.mItemMyIntegralTitle = (TextView) convertView.findViewById(R.id.item_my_integral_title);
            viewHolder.mItemMyIntegralSize = (TextView) convertView.findViewById(R.id.item_my_integral_size);
            viewHolder.mItemMyIntegralTv = (TextView) convertView.findViewById(R.id.item_my_integral_tv);
            //把当前的控件缓存到布局视图中
            convertView.setTag(viewHolder);
        } else {
            //说明开始上下滑动，后面是所有布局采用第一次绘制时的缓存布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mItemMyIntegralMoney.setText("￥" + mList.get(position).getAmount());
        viewHolder.mItemMyIntegralTitle.setText(mList.get(position).getCouponName());
        viewHolder.mItemMyIntegralSize.setText("所需积分：" + mList.get(position).getPrice());

        viewHolder.mItemMyIntegralTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> hashMap2 = new HashMap<>();
                hashMap2.put("itfaceId", "095");
                hashMap2.put("token", SPUtils.get(mContext, "token", ""));
                hashMap2.put("couponId", mList.get(position).getId());
                JSONObject jsonObject2 = new JSONObject(hashMap2);

                OkGo.post(UrlContent.URL).tag(this)
                        .upJson(jsonObject2.toString())
                        .connTimeOut(10_000)
                        .execute(new CustomCallBackNoLoading<SignInDuiHuanBeans>(mContext) {
                            @Override
                            public void onSuccess(SignInDuiHuanBeans mSignInDuiHuanBeans, Call call, Response response) {
                                if (mSignInDuiHuanBeans.getResultCode() == 1) {

                                    //通知本Activity刷新
                                    MessageEvent messageEvent = new MessageEvent();
                                    messageEvent.setDuiHuan(true);
                                    EventBus.getDefault().post(messageEvent);

                                    final AlertDialog alert;
                                    alert = new AlertDialog.Builder(mContext, R.style.AlertDialog).create();
                                    alert.show();
                                    alert.getWindow().setContentView(R.layout.integral_success);
                                    TextView mIntegralSuccessTitle = (TextView) alert.getWindow().findViewById(R.id.Integral_Success_Title);
                                    TextView mIntegralSuccessContent = (TextView) alert.getWindow().findViewById(R.id.Integral_Success_Content);
                                    TextView mIntegralSuccessMoney1 = (TextView) alert.getWindow().findViewById(R.id.Integral_Success_Money1);
                                    TextView mIntegralSuccessMoney2 = (TextView) alert.getWindow().findViewById(R.id.Integral_Success_Money2);
                                    ImageView mIntegralSuccessClose = (ImageView) alert.getWindow().findViewById(R.id.Integral_Success_Close);

                                    mIntegralSuccessTitle.setText(mList.get(position).getCouponName());
                                    mIntegralSuccessContent.setText(mList.get(position).getContent());
                                    mIntegralSuccessMoney1.setText(String.valueOf(mList.get(position).getAmount()));
                                    String s1 = mList.get(position).getRule();
                                    String[] temp = s1.split("\\|");
                                    mIntegralSuccessMoney2.setText(temp[0]);

                                    //取消
                                    alert.getWindow().findViewById(R.id.Integral_Success_Close).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            alert.dismiss();
                                            return;
                                        }
                                    });
                                } else {
                                    final AlertDialog alert;
                                    alert = new AlertDialog.Builder(mContext, R.style.AlertDialogs).create();
                                    alert.show();
                                    alert.getWindow().setContentView(R.layout.delete_dialog_integral);
                                    TextView cart_delete_title = alert.getWindow().findViewById(R.id.cart_delete_title);
                                    TextView cart_delete_content = alert.getWindow().findViewById(R.id.cart_delete_content);
                                    TextView cart_delete_cancle = alert.getWindow().findViewById(R.id.cart_delete_cancle);
                                    TextView cart_delete_confirm = alert.getWindow().findViewById(R.id.cart_delete_confirm);
                                    cart_delete_title.setText("提示消息");
                                    cart_delete_content.setText("您的积分不够兑换该优惠券赶快去赚积分吧");
                                    cart_delete_cancle.setText("否");
                                    cart_delete_confirm.setText("赚积分");
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
                                            mContext.startActivity(new Intent(mContext, IntegralStrategyActivity.class));
                                            alert.dismiss();
                                            return;
                                        }
                                    });
                                }
                            }
                        });
            }
        });
        return convertView;
    }
}
