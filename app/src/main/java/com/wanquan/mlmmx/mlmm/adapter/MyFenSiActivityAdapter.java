package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.PostPersonageCenterActivity;
import com.wanquan.mlmmx.mlmm.beans.BabyPhoneBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.MyFenSiActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.PostPersonageCenterIsFollowBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
//import com.wanquan.mlmmx.mlmm.phone.MyGridView;

import org.greenrobot.eventbus.EventBus;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/2.
 */

public class MyFenSiActivityAdapter extends BaseAdapter {
    private List<MyFenSiActivityBeans.DataBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private String times;
    private int month;
    private int days;
    private int dayAll;
    private int daytime;
    private int week;
    private int week_day;


    public MyFenSiActivityAdapter(List<MyFenSiActivityBeans.DataBean> list, Context context) {
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
        CircleImageView mFenSiItemImg;
        TextView mFenSiItemName;
        TextView mFenSiItemContent;
        LinearLayout mFenSiItemLL;
        TextView mFenSiItemTv2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_fensi, null);
            viewHolder = new ViewHolder();

            viewHolder.mFenSiItemImg = (CircleImageView) convertView.findViewById(R.id.FenSi_Item_Img);
            viewHolder.mFenSiItemName = (TextView) convertView.findViewById(R.id.FenSi_Item_Name);
            viewHolder.mFenSiItemContent = (TextView) convertView.findViewById(R.id.FenSi_Item_Content);
            viewHolder.mFenSiItemLL = (LinearLayout) convertView.findViewById(R.id.FenSi_Item_LL);
            viewHolder.mFenSiItemTv2 = (TextView) convertView.findViewById(R.id.FenSi_Item_Tv2);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(mList.get(position).getHeadIco()).into(viewHolder.mFenSiItemImg);
        viewHolder.mFenSiItemName.setText(mList.get(position).getFansName());

        if (mList.get(position).isMutual()) {
            viewHolder.mFenSiItemTv2.setText("取消关注");
        } else {
            viewHolder.mFenSiItemTv2.setText("关注");
        }

        int babyStatus = mList.get(position).getBabyInfo().get(0).getBabyStatus();
        if (babyStatus == 0) {
            viewHolder.mFenSiItemContent.setText("备孕中");
        } else if (babyStatus == 1) {
            if (null != mList.get(position).getBabyInfo().get(0).getPreChildDate()) {
                //计算宝宝怀孕天数
                String preChildDate = mList.get(position).getBabyInfo().get(0).getPreChildDate();
                //获取当前时间
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                times = formatter.format(curDate);
                //当前时间
                DateTime now = DateTime.now();
                String startTimes = times;
                if (preChildDate.contains(" ")) {
                    startTimes = preChildDate.substring(0, preChildDate.indexOf(" "));
                } else {
                    startTimes = preChildDate;
                }
                DateTime dateTime = DateTime.parse(startTimes);//设置的时间
                DateTime nows = dateTime.minusDays(280);//向前推280

                month = Months.monthsBetween(nows, dateTime).getMonths();//月
                dayAll = Days.daysBetween(nows, dateTime).getDays();//总天
                daytime = Days.daysBetween(nows, now).getDays();//已怀孕天
                days = Days.daysBetween(now, dateTime).getDays();//距离出生日

                week = +daytime / 7;//周
                week_day = +daytime % 7 + 1;//周余天
                if (week_day == 0) {
                    viewHolder.mFenSiItemContent.setText("孕" + week + "周");
                } else if (week_day == 7) {
                    int weekjia = week + 1;
                    viewHolder.mFenSiItemContent.setText("孕" + weekjia + "周");
                } else {
                    if (week == 0) {
                        viewHolder.mFenSiItemContent.setText("孕" + week_day + "天");
                    } else {
                        viewHolder.mFenSiItemContent.setText("孕" + week + "周" + week_day + "天");
                    }
                }
            }
        } else {
            if (null != mList.get(position).getBabyInfo().get(0).getChildBirthDate()) {
                //当前时间
                DateTime now = DateTime.now();
                //宝宝生日
                DateTime dateTime = DateTime.parse( mList.get(position).getBabyInfo().get(0).getChildBirthDate());
                int QieHuanMonth = Months.monthsBetween(dateTime, now).getMonths();//宝宝出生月
                DateTime tmp = dateTime.plusMonths(QieHuanMonth); //月的天
                String QieHuanMonthday = String.valueOf(Days.daysBetween(tmp, now).getDays());//月的天
                int newDay = Integer.parseInt(QieHuanMonthday);
                QieHuanMonthday = String.valueOf(newDay);

                if (QieHuanMonth == 0 && QieHuanMonthday.equals("0")) {
                    viewHolder.mFenSiItemContent.setText("宝宝今天出生");
                } else if (QieHuanMonth == 0 && !QieHuanMonthday.equals("0")) {
                    viewHolder.mFenSiItemContent.setText("宝宝" + QieHuanMonthday + "天");
                } else if (QieHuanMonth < 12 && !QieHuanMonthday.equals("0")) {
                    viewHolder.mFenSiItemContent.setText("宝宝" + QieHuanMonth + "个月" + QieHuanMonthday + "天");
                } else if (QieHuanMonth < 12 && QieHuanMonthday.equals("0")) {
                    viewHolder.mFenSiItemContent.setText("宝宝" + QieHuanMonth + "个月");
                } else if (QieHuanMonth > 12 && QieHuanMonthday.equals("0")) {
                    int month1 = month / 12;
                    int month2 = month % 12;
                    if (month2 == 0) {
                        viewHolder.mFenSiItemContent.setText("宝宝" + month1 + "岁");
                    } else {
                        viewHolder.mFenSiItemContent.setText("宝宝" + month1 + "岁" + month2 + "个月");
                    }
                } else if (QieHuanMonth > 12 && !QieHuanMonthday.equals("0")) {
                    int month1 = month / 12;
                    int month2 = month % 12;
                    if (month2 == 0) {
                        viewHolder.mFenSiItemContent.setText("宝宝" + month1 + "岁" + QieHuanMonthday + "天");
                    } else {
                        viewHolder.mFenSiItemContent.setText("宝宝" + month1 + "岁" + month2 + "个月" + QieHuanMonthday + "天");
                    }
                }
            }
        }

//        if (mList.get(position).getBabyInfo().get(0).getBabyName()!=null){
//            viewHolder.mFenSiItemContent.setText(mList.get(position).getBabyInfo().get(0).getBabyName());
//        }
        viewHolder.mFenSiItemLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mList.get(position).isMutual()) {
                    initData(0);
                } else {
                    initData(1);
                }
            }

            private void initData(int size) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("itfaceId", "117");
                hashMap.put("token", SPUtils.get(mContext, "token", ""));
                hashMap.put("uid", mList.get(position).getFid());
                hashMap.put("action", size);
                JSONObject jsonObject = new JSONObject(hashMap);

                OkGo.post(UrlContent.URL).tag(this)
                        .upJson(jsonObject.toString())
                        .connTimeOut(10_000)
                        .execute(new CustomCallBackNoLoading<PostPersonageCenterIsFollowBeans>(mContext) {
                            @Override
                            public void onSuccess(PostPersonageCenterIsFollowBeans mPostPersonageCenterIsFollowBeans, Call call, Response response) {
                                if (mPostPersonageCenterIsFollowBeans.getResultCode() == 1) {
                                    Toast.makeText(mContext, mPostPersonageCenterIsFollowBeans.getMsg(), Toast.LENGTH_SHORT).show();
                                    MessageEvent messageEvent = new MessageEvent();
                                    messageEvent.setItFaceId("ss");
                                    EventBus.getDefault().post(messageEvent);
                                } else {
                                    App.ErrorToken(mPostPersonageCenterIsFollowBeans.getResultCode(), mPostPersonageCenterIsFollowBeans.getMsg());
                                }
                            }
                        });
            }
        });
        return convertView;
    }


}


