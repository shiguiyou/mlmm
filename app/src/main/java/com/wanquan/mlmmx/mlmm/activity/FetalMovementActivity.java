package com.wanquan.mlmmx.mlmm.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.FetalMovementOperationBeans;
import com.wanquan.mlmmx.mlmm.beans.FetalMovementQueryBeans;
import com.wanquan.mlmmx.mlmm.beans.FetalMovementStartBeans;
import com.wanquan.mlmmx.mlmm.beans.FetalMovementStopBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MyDialog_Views;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：数胎动
 * 作者：薛昌峰
 * 时间：20187.05.10
 */
public class FetalMovementActivity extends BaseActivity {
    private LinearLayout mFetalMovementBank;
    private TextView mFetalMovementTextView;
    private ImageView mFetalMovementImageView1;
    private ImageView mFetalMovementImageView2;
    private TextView mFetalMovementTime;
    private TextView mFetalMovementStart;
    private TextView mFetalMovementOver;
    private LinearLayout mFetalMovementExplain;
    private LinearLayout mFetalMovementClick;
    private LinearLayout mFetalMovementClick1;
    private TextView mFetalMovementClickSize;
    private LinearLayout mFetalMovementClick2;
    private TextView mFetalMovementClickSize2;
    private TextView mFetalMovementStartTime;
    private TextView mFetalMovementButton1;
    private TextView mFetalMovementButton2;

    private int minute = 0;//这是分钟
    private int second = 0;//这是分钟后面的秒数。这里是以30分钟为例的，所以，minute是30，second是0
    private Timer timer;
    private TimerTask timerTask;
    private int size1 = 0;
    private int size2 = 0;
    private boolean isCheck = false;
    private String currentTime;
    private String endTime;
    private int mid;
    private boolean isImages;//让计时开始，点击动一下，点一下开始计为实际点击
    private Dialog dialog;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0) {
                //这里可以进行UI操作，如Toast，Dialog等
//                Toast.makeText(FetalMovementActivity.this, "刷新了", Toast.LENGTH_SHORT).show();
                Log.e("xcf","刷新了");
                //停止计时
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                if (timerTask != null) {
                    timerTask = null;
                }
                minute = 00;
                second = 00;
                initInquire();
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("xcfxcf", "onResume");

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }
        minute = -1;
        second = -1;

        initInquire();
    }

    //查询
    private void initInquire() {
        Log.e("token", (String) SPUtils.get(FetalMovementActivity.this, "token", ""));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "098");
        hashMap.put("token", SPUtils.get(FetalMovementActivity.this, "token", ""));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<FetalMovementQueryBeans>(FetalMovementActivity.this) {
                    @Override
                    public void onSuccess(FetalMovementQueryBeans mFetalMovementQueryBeans, Call call, Response response) {
                        if (mFetalMovementQueryBeans.getResultCode() == 1) {
                            mid = mFetalMovementQueryBeans.getData().get(0).getMid();
                            SPUtils.put(FetalMovementActivity.this, "TDId", String.valueOf(mid));
//                            Log.e("ffffff", String.valueOf(mid));

                            currentTime = mFetalMovementQueryBeans.getData().get(0).getCurrentTime();
                            endTime = mFetalMovementQueryBeans.getData().get(0).getEndTime();

                            mFetalMovementStartTime.setText("开始时间：" + mFetalMovementQueryBeans.getData().get(0).getStartTime());
                            mFetalMovementClickSize.setText(mFetalMovementQueryBeans.getData().get(0).getClickTimes());
                            mFetalMovementClickSize2.setText(mFetalMovementQueryBeans.getData().get(0).getMoveTimes());
//                            Log.e("ddddd", currentTime);
//                            Log.e("ddddd", endTime);

                            dateDiff(currentTime, endTime);
                        } else {
//                            App.ErrorToken(mFetalMovementQueryBeans.getResultCode(), mFetalMovementQueryBeans.getMsg());
                        }
                    }
                });
    }

    /**
     * 根据指定类型计算两个日期相差的时间
     * eg. dateDiff(birth, today, Calendar.MONTH) 孩子的月龄
     *
     * @param sDate    开始时间
     * @param eDate    结束时间
     * @param diffType 日期类型
     * @return 根据 diffType计算的 eDate - sDate 时差
     */
    private void dateDiff(String fromDate, String toDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //前的时间
            Date fd = df.parse(fromDate);
            //后的时间
            Date td = df.parse(toDate);
            //两时间差,精确到毫秒
            long diff = td.getTime() - fd.getTime();
            long day = diff / 86400000;                         //以天数为单位取整
            long hour = diff % 86400000 / 3600000;               //以小时为单位取整
            long min = diff % 86400000 % 3600000 / 60000;       //以分钟为单位取整
            long seconds = diff % 86400000 % 3600000 % 60000 / 1000;   //以秒为单位取整
            //天时分秒
            Log.e("ddddd", "两时间差---> " + day + "天" + hour + "小时" + min + "分" + seconds + "秒");

            if (hour == 1) {
                initStart(60, 00);
            } else {
                initStart(Integer.parseInt(String.valueOf(min)), Integer.parseInt(String.valueOf(seconds)));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(FetalMovementActivity.this, R.color.tops);

        initData();
        initListeners();
    }

    private void initData() {
    }


    @Override
    protected int setLayoutID() {
        return R.layout.activity_fetal_movement;
    }

    //这是接收回来处理的消息
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (minute == 0) {
                if (second == 0) {
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    if (timerTask != null) {
                        timerTask = null;
                    }
                    initStop(1);
                } else {
                    second--;
                    if (second >= 10) {
                        mFetalMovementTime.setText("0" + minute + ":" + second);
                    } else {
                        mFetalMovementTime.setText("0" + minute + ":0" + second);
                    }
                }
            } else {
                if (second == 0) {
                    second = 59;
                    minute--;
                    if (minute >= 10) {
                        mFetalMovementTime.setText(minute + ":" + second);
                    } else {
                        mFetalMovementTime.setText("0" + minute + ":" + second);
                    }
                } else {
                    second--;
                    if (second >= 10) {
                        if (minute >= 10) {
                            mFetalMovementTime.setText(minute + ":" + second);
                        } else {
                            mFetalMovementTime.setText("0" + minute + ":" + second);
                        }
                    } else {
                        if (minute >= 10) {
                            mFetalMovementTime.setText(minute + ":0" + second);
                        } else {
                            mFetalMovementTime.setText("0" + minute + ":0" + second);
                        }
                    }
                }
            }
        }

    };

    @Override
    public void initView() throws Exception {
        mFetalMovementBank = (LinearLayout) findViewById(R.id.FetalMovement_Bank);
        mFetalMovementTextView = (TextView) findViewById(R.id.FetalMovement_TextView);
        mFetalMovementImageView1 = (ImageView) findViewById(R.id.FetalMovement_ImageView1);
        mFetalMovementImageView2 = (ImageView) findViewById(R.id.FetalMovement_ImageView2);
        mFetalMovementTime = (TextView) findViewById(R.id.FetalMovement_Time);
        mFetalMovementStart = (TextView) findViewById(R.id.FetalMovement_Start);
        mFetalMovementOver = (TextView) findViewById(R.id.FetalMovement_Over);
        mFetalMovementExplain = (LinearLayout) findViewById(R.id.FetalMovement_Explain);
        mFetalMovementClick = (LinearLayout) findViewById(R.id.FetalMovement_Click);
        mFetalMovementClick1 = (LinearLayout) findViewById(R.id.FetalMovement_Click1);
        mFetalMovementClickSize = (TextView) findViewById(R.id.FetalMovement_Click_Size);
        mFetalMovementClick2 = (LinearLayout) findViewById(R.id.FetalMovement_Click2);
        mFetalMovementClickSize2 = (TextView) findViewById(R.id.FetalMovement_Click_Size2);
        mFetalMovementStartTime = (TextView) findViewById(R.id.FetalMovement_Start_Time);
        mFetalMovementButton1 = (TextView) findViewById(R.id.FetalMovement_Button1);
        mFetalMovementButton2 = (TextView) findViewById(R.id.FetalMovement_Button2);
    }

    private void initListeners() {
        mFetalMovementBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(FetalMovementActivity.this, MainActivity.class));
                finish();
            }
        });
        //开始
        mFetalMovementImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("dddsdsds", String.valueOf(isImages));
//                Log.e("dddsdsds", String.valueOf(isCheck));
                if (isImages) {
                    //实际点击
                    if (isCheck) {
                        Toast toast = Toast.makeText(FetalMovementActivity.this, "不要太着急哦", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("itfaceId", "099");
                        hashMap.put("token", SPUtils.get(FetalMovementActivity.this, "token", ""));
                        hashMap.put("fetalId", mid);
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<FetalMovementOperationBeans>(FetalMovementActivity.this) {
                                    @Override
                                    public void onSuccess(FetalMovementOperationBeans mFetalMovementOperationBeans, Call call, Response response) {
                                        if (mFetalMovementOperationBeans.getResultCode() == 1) {
                                            mFetalMovementClickSize.setText(mFetalMovementOperationBeans.getData().getClickTimes());
                                            mFetalMovementClickSize2.setText(mFetalMovementOperationBeans.getData().getBabyBoveTimes());
                                            isCheck = true;
                                            //30秒内按钮不让点击
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    isCheck = false;
                                                }
                                            }, 30000);
                                        } else {
                                            App.ErrorToken(mFetalMovementOperationBeans.getResultCode(), mFetalMovementOperationBeans.getMsg());
                                        }
                                    }
                                });
                    }
                } else {
                    initDialog();
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("itfaceId", "097");
                    hashMap.put("token", SPUtils.get(FetalMovementActivity.this, "token", ""));
                    JSONObject jsonObject = new JSONObject(hashMap);

                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<FetalMovementStartBeans>(FetalMovementActivity.this) {
                                @Override
                                public void onSuccess(FetalMovementStartBeans mFetalMovementStartBeans, Call call, Response response) {
                                    if (mFetalMovementStartBeans.getResultCode() == 1) {
                                        initInquire();
//                                    //获取当前时间
//                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                    Date curDate = new Date(System.currentTimeMillis());
//                                    String times = formatter.format(curDate);
//                                    mFetalMovementStartTime.setText("开始时间：" + times);

//                                    initStart(60, 0);
//                                        Toast.makeText(FetalMovementActivity.this, mFetalMovementStartBeans.getMsg(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        App.ErrorToken(mFetalMovementStartBeans.getResultCode(), mFetalMovementStartBeans.getMsg());
                                    }
                                }
                            });
                }
            }
        });
        //有效胎动
        mFetalMovementClick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                size2 += 1;
//                mFetalMovementClickSize2.setText(String.valueOf(size2));
            }
        });
        //取消
        mFetalMovementOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alert;
                alert = new AlertDialog.Builder(FetalMovementActivity.this, R.style.AlertDialogs).create();
                alert.show();
                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.delete_dialogsss);
                TextView cart_delete_title = alert.getWindow().findViewById(R.id.cart_delete_title);
                TextView cart_delete_content = alert.getWindow().findViewById(R.id.cart_delete_content);
                TextView cart_delete_cancle = alert.getWindow().findViewById(R.id.cart_delete_cancle);
                TextView cart_delete_confirm = alert.getWindow().findViewById(R.id.cart_delete_confirm);
                cart_delete_title.setVisibility(View.GONE);
                cart_delete_content.setText("计时未满1小时，结果会不准确。确定选择结束将无法保存此次记录哦~");
                cart_delete_cancle.setText("取消");
                cart_delete_confirm.setText("确定结束");
                cart_delete_cancle.setTextColor(getResources().getColor(R.color.colorPrimarys));
                cart_delete_confirm.setTextColor(getResources().getColor(R.color.colorPrimarys));
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
                        initStop(2);
                        alert.dismiss();
                    }
                });
            }
        });

        mFetalMovementButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FetalMovementActivity.this, QuickeningInstructionsActivity.class));
            }
        });
        mFetalMovementButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FetalMovementActivity.this, QuickeningHistoryActivity.class));
            }
        });
    }

    private void initDialog() {
        dialog = new MyDialog_Views(FetalMovementActivity.this, R.style.MyDialog);
        dialog.setCancelable(true);
        dialog.show();
        MyDialog_Views myDialog_views = new MyDialog_Views(FetalMovementActivity.this, "加载中...", "");

    }

    //结束（手动2/自动1）
    private void initStop(final int num) {
        initDialog();

        Log.e("xcfmid", String.valueOf(mid));
        Log.e("xcfnum", String.valueOf(num));

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "100");
        hashMap.put("token", SPUtils.get(FetalMovementActivity.this, "token", ""));
        hashMap.put("fetalId", mid);
        hashMap.put("status", num);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<FetalMovementStopBeans>(FetalMovementActivity.this) {
                    @Override
                    public void onSuccess(FetalMovementStopBeans mFetalMovementStopBeans, Call call, Response response) {
                        if (mFetalMovementStopBeans.getResultCode() == 1) {
                            Log.e("xcf数胎动结束", "到了");

                            if (dialog != null) {
                                dialog.dismiss();
                            }
                            mFetalMovementImageView1.setBackground(getResources().getDrawable(R.mipmap.shutaidongbtn));
                            isImages = false;
                            if (num == 1) {
                                mFetalMovementImageView2.setVisibility(View.GONE);//脚印
                                mFetalMovementExplain.setVisibility(View.GONE);//操作说明
                                mFetalMovementTime.setText("00:00");
                                mFetalMovementStart.setText("重新开始");
                                mFetalMovementTextView.setText("1小时计时已结束，记录已保存");
                                mFetalMovementOver.setVisibility(View.GONE);//结束
                            } else {
                                //mFetalMovementImageView1.setClickable(true);
                                mFetalMovementImageView2.setVisibility(View.VISIBLE);//脚印
                                mFetalMovementExplain.setVisibility(View.VISIBLE);//操作说明
                                mFetalMovementTextView.setVisibility(View.INVISIBLE);//头部文字
                                mFetalMovementOver.setVisibility(View.GONE);//结束
                                mFetalMovementTime.setVisibility(View.GONE);//倒计时时间
                                mFetalMovementClick.setVisibility(View.GONE);//点击次数LinearLayout
                                mFetalMovementStart.setText("点击开始");
                                mFetalMovementClickSize.setText("0");
                                mFetalMovementClickSize2.setText("0");
                            }

                            size1 = 0;
                            //停止计时
                            if (timer != null) {
                                timer.cancel();
                                timer = null;
                            }
                            if (timerTask != null) {
                                timerTask = null;
                            }
                            minute = -1;
                            second = -1;
                            Log.e("xcf数胎动结束", mFetalMovementStopBeans.getMsg());
                        } else {
                            App.ErrorToken(mFetalMovementStopBeans.getResultCode(), mFetalMovementStopBeans.getMsg());
                        }
                    }
                });
    }

    //开始计时
    private void initStart(int minutes, int seconds) {
        if (dialog != null) {
            dialog.dismiss();
        }


        mFetalMovementImageView1.setBackground(getResources().getDrawable(R.mipmap.shutaidongbtn1));
        isImages = true;
        minute = minutes;
        second = seconds;
//        mFetalMovementImageView1.setClickable(false);
        mFetalMovementImageView2.setVisibility(View.GONE);//脚印
        mFetalMovementExplain.setVisibility(View.GONE);//操作说明
        mFetalMovementTextView.setVisibility(View.VISIBLE);//头部文字
        mFetalMovementTextView.setText("5分钟内多次点击只算1次有效胎动~");
        mFetalMovementTime.setVisibility(View.VISIBLE);//倒计时时间
        mFetalMovementClick.setVisibility(View.VISIBLE);//点击次数LinearLayout
        mFetalMovementOver.setVisibility(View.VISIBLE);//结束
        mFetalMovementStart.setText("动一下，点一下");

        if (timer == null) {
            Timer timer2 = new Timer();
            //定时刷新计时器
            timer2.schedule(new TimerTask() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Message message = new Message();
                    message.what = 0;
                    mHandler.sendMessage(message);
                }
//        }, 500, 2000);//0.5秒之后，每隔2秒做一次run()操作
            }, 60000);//

            timerTask = new TimerTask() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = 0;
                    handler.sendMessage(msg);
                }
            };

            timer = new Timer();
            timer.schedule(timerTask, 0, 1000);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("xcfxcf", "onPause");
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }
        minute = -1;
        second = -1;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("xcfxcf", "onStop");

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }
        minute = -1;
        second = -1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("xcfxcf", "onDestroy");

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }
        minute = -1;
        second = -1;
    }

}
