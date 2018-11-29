package com.wanquan.mlmmx.mlmm.adapter;

import android.content.Context;
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
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentTwoBeans;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentTwoBeansTwo;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentTwoCancleBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

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
 * Created by Administrator on 2018/4/13.
 */

public class CircleFragmentTwoListViewTwoAdapter extends BaseAdapter {
    private List<CircleFragmentTwoBeansTwo.DataBean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private InterfaceIsCollection mInterfaceIsCollection;
    private String times;
    private int month;
    private int days;
    private int dayAll;
    private int daytime;
    private int week;
    private int week_day;


    public CircleFragmentTwoListViewTwoAdapter(List<CircleFragmentTwoBeansTwo.DataBean> mList, Context mContext) {
        this.mContext = mContext;
        this.mList = mList;
        mLayoutInflater = LayoutInflater.from(mContext);
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

    class ViewHolder {
        CircleImageView mItemCircleFragmentTwoRenImg;
        TextView mItemCircleFragmentTwoRenName;
        TextView mItemCircleFragmentTwoRenSize;
        LinearLayout mItemCircleFragmentTwoRenLl;
        ImageView mItemCircleFragmentTwoRenImg2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_circle_fragment_two_ren, null);
            viewHolder = new ViewHolder();

            viewHolder.mItemCircleFragmentTwoRenImg = (CircleImageView) convertView.findViewById(R.id.item_circle_fragment_two_ren_img);
            viewHolder.mItemCircleFragmentTwoRenName = (TextView) convertView.findViewById(R.id.item_circle_fragment_two_ren_name);
            viewHolder.mItemCircleFragmentTwoRenSize = (TextView) convertView.findViewById(R.id.item_circle_fragment_two_ren_size);
            viewHolder.mItemCircleFragmentTwoRenLl = (LinearLayout) convertView.findViewById(R.id.item_circle_fragment_two_ren_ll);
            viewHolder.mItemCircleFragmentTwoRenImg2 = (ImageView) convertView.findViewById(R.id.item_circle_fragment_two_ren_img2);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(mList.get(position).getHeadIco()).into(viewHolder.mItemCircleFragmentTwoRenImg);
        viewHolder.mItemCircleFragmentTwoRenName.setText(mList.get(position).getFollowerName());
        if (mList.get(position).getBabyInfo() != null) {
            if (mList.get(position).getBabyInfo().size() > 0) {
                int babyStatus = (int) mList.get(position).getBabyInfo().get(0).getBabyStatus();
                if (babyStatus == 0) {
                    viewHolder.mItemCircleFragmentTwoRenSize.setText("备孕中");
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
                            viewHolder.mItemCircleFragmentTwoRenSize.setText("孕" + week + "周");
                        } else if (week_day == 7) {
                            int weekjia = week + 1;
                            viewHolder.mItemCircleFragmentTwoRenSize.setText("孕" + weekjia + "周");
                        } else {
                            if (week == 0) {
                                viewHolder.mItemCircleFragmentTwoRenSize.setText("孕" + week_day + "天");
                            } else {
                                viewHolder.mItemCircleFragmentTwoRenSize.setText("孕" + week + "周" + week_day + "天");
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
                            viewHolder.mItemCircleFragmentTwoRenSize.setText("宝宝今天出生");
                        } else if (QieHuanMonth == 0 && !QieHuanMonthday.equals("0")) {
                            viewHolder.mItemCircleFragmentTwoRenSize.setText("宝宝" + QieHuanMonthday + "天");
                        } else if (QieHuanMonth < 12 && !QieHuanMonthday.equals("0")) {
                            viewHolder.mItemCircleFragmentTwoRenSize.setText("宝宝" + QieHuanMonth + "个月" + QieHuanMonthday + "天");
                        } else if (QieHuanMonth < 12 && QieHuanMonthday.equals("0")) {
                            viewHolder.mItemCircleFragmentTwoRenSize.setText("宝宝" + QieHuanMonth + "个月");
                        } else if (QieHuanMonth > 12 && QieHuanMonthday.equals("0")) {
                            int month1 = month / 12;
                            int month2 = month % 12;
                            if (month2 == 0) {
                                viewHolder.mItemCircleFragmentTwoRenSize.setText("宝宝" + month1 + "岁");
                            } else {
                                viewHolder.mItemCircleFragmentTwoRenSize.setText("宝宝" + month1 + "岁" + month2 + "个月");
                            }
                        } else if (QieHuanMonth > 12 && !QieHuanMonthday.equals("0")) {
                            int month1 = month / 12;
                            int month2 = month % 12;
                            if (month2 == 0) {
                                viewHolder.mItemCircleFragmentTwoRenSize.setText("宝宝" + month1 + "岁" + QieHuanMonthday + "天");
                            } else {
                                viewHolder.mItemCircleFragmentTwoRenSize.setText("宝宝" + month1 + "岁" + month2 + "个月" + QieHuanMonthday + "天");
                            }
                        }
                    }
                }
            }
        }


        viewHolder.mItemCircleFragmentTwoRenLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("itfaceId", "117");
                hashMap.put("token", SPUtils.get(mContext, "token", ""));
                hashMap.put("uid", mList.get(position).getUid());
                hashMap.put("action", 0);
                JSONObject jsonObject = new JSONObject(hashMap);

                OkGo.post(UrlContent.URL).tag(this)
                        .upJson(jsonObject.toString())
                        .connTimeOut(10_000)
                        .execute(new CustomCallBackNoLoading<CircleFragmentTwoCancleBeans>(mContext) {
                            @Override
                            public void onSuccess(CircleFragmentTwoCancleBeans mCircleFragmentTwoCancleBeans, Call call, Response response) {
                                if (mCircleFragmentTwoCancleBeans.getResultCode() == 1) {
                                    Toast.makeText(mContext, mCircleFragmentTwoCancleBeans.getMsg(), Toast.LENGTH_SHORT).show();

                                    MessageEvent messageEvent = new MessageEvent();
                                    messageEvent.setItFaceId("ss");
                                    EventBus.getDefault().post(messageEvent);

                                } else {
                                    App.ErrorToken(mCircleFragmentTwoCancleBeans.getResultCode(), mCircleFragmentTwoCancleBeans.getMsg());
                                }
                            }
                        });
            }
        });

        return convertView;
    }
}


