package com.wanquan.mlmmx.mlmm.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.SettingSGTZSuccessBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：身高体重设置界面
 * 作者：薛昌峰
 * 时间：2017.12.13
 */
public class SettingSGTZActivity extends BaseActivity {
    private TextView mSettingSGTZSave;
    private TextView mSettingSGTZTime;
    private TextView mSettingSGTZMouth;
    private EditText mSettingSGTZEditText1;
    private EditText mSettingSGTZEditText2;
    private TimePickerView pvCustomTime;
    private int days;
    private String times;
    private DateTime dateTime;
    private int format1;
    private int format2;
    private int format3;
    private String startTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(SettingSGTZActivity.this, R.color.tops);

        initCustomTimePicker();
        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_setting_sgtz;
    }

    @Override
    public void initView() throws Exception {
        mSettingSGTZSave = (TextView) findViewById(R.id.SettingSGTZ_Save);
        mSettingSGTZTime = (TextView) findViewById(R.id.SettingSGTZ_Time);
        mSettingSGTZMouth = (TextView) findViewById(R.id.SettingSGTZ_Mouth);
        mSettingSGTZEditText1 = (EditText) findViewById(R.id.SettingSGTZ_EditText1);
        mSettingSGTZEditText2 = (EditText) findViewById(R.id.SettingSGTZ_EditText2);
    }

    private void initData() {
        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        times = formatter.format(curDate);
        String startTime = String.valueOf(SPUtils.get(SettingSGTZActivity.this, "timey", ""));
        initTime(startTime);
    }

    private void initTime(String startTime) {
//        //计算宝宝出生多少月多少天
        if (startTime.contains(" ")) {
            startTimes = startTime.substring(0, startTime.indexOf(" "));
        } else {
            startTimes = startTime;
        }
        //当前时间
        DateTime now = DateTime.now();
        //设置的时间
        dateTime = DateTime.parse(startTimes);
        int month = Months.monthsBetween(dateTime, now).getMonths();//宝宝出生月
        DateTime tmp = dateTime.plusMonths(month); //月的天
        //宝宝出生天
        days = Days.daysBetween(dateTime, now).getDays();
//        Log.e("Ddddddd", String.valueOf(days));
        String monthday = String.valueOf(Days.daysBetween(tmp, now).getDays());//月的天
        int newDay = Integer.parseInt(monthday) + 1;//月的天
        if (month == 0) {
            mSettingSGTZMouth.setText(newDay + " 天 ");
        } else {
            mSettingSGTZMouth.setText(month + " 个月 " + newDay + " 天 ");
        }
    }

    private Boolean hideInputMethod(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return false;
    }

    private void initListeners() {
        mSettingSGTZTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if ("".equals(mSettingSGTZEditText1.getText().toString().trim())) {
//                    Toast.makeText(SettingSGTZActivity.this, "请先输入身高", Toast.LENGTH_SHORT).show();
//                } else if ("".equals(mSettingSGTZEditText2.getText().toString().trim())) {
//                    Toast.makeText(SettingSGTZActivity.this, "请先输入体重", Toast.LENGTH_SHORT).show();
//                } else {
//                    InputMethodManager inputMgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    inputMgr.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
                hideInputMethod(SettingSGTZActivity.this, v);
                pvCustomTime.show(); //弹出自定义时间选择器
//                }
            }
        });
        mSettingSGTZSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mSettingSGTZEditText1.getText().toString().trim().equals("") || !mSettingSGTZEditText2.getText().toString().trim().equals("")) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("itfaceId", "046");
                    hashMap.put("token", SPUtils.get(SettingSGTZActivity.this, "token", ""));
                    hashMap.put("days", times);
                    hashMap.put("height", mSettingSGTZEditText1.getText().toString().trim());
                    hashMap.put("weight", mSettingSGTZEditText2.getText().toString().trim());
                    hashMap.put("babyId", SPUtils.get(SettingSGTZActivity.this, "babyId", ""));
                    JSONObject jsonObject = new JSONObject(hashMap);

                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<SettingSGTZSuccessBeans>(SettingSGTZActivity.this) {
                                @Override
                                public void onSuccess(SettingSGTZSuccessBeans mSettingSGTZSuccessBeans, Call call, Response response) {
                                    if (mSettingSGTZSuccessBeans.getResultCode() == 1) {
                                        Toast.makeText(SettingSGTZActivity.this, mSettingSGTZSuccessBeans.getMsg(), Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        App.ErrorToken(mSettingSGTZSuccessBeans.getResultCode(), mSettingSGTZSuccessBeans.getMsg());

                                    }
                                }
                            });
                } else {
                    Toast toast = Toast.makeText(SettingSGTZActivity.this, "请输入身高或体重", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }

    private void initCustomTimePicker() {
        String startTime = String.valueOf(SPUtils.get(SettingSGTZActivity.this, "timey", ""));
        String startTimes;
        if (startTime.contains(" ")) {
            startTimes = startTime.substring(0, startTime.indexOf(" "));
        } else {
            startTimes = startTime;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = sdf.parse(startTimes);
            SimpleDateFormat year1 = new SimpleDateFormat("yyyy");
            SimpleDateFormat year2 = new SimpleDateFormat("MM");
            SimpleDateFormat year3 = new SimpleDateFormat("dd");

            format1 = Integer.parseInt(year1.format(parse));
            format2 = Integer.parseInt(year2.format(parse)) - 1;
            format3 = Integer.parseInt(year3.format(parse));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /**
         * @deprecated (注意事项)
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针
         * 具体可参考demo 里面的两个自定义布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * 控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(format1, format2, format3);

        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("MM");
        SimpleDateFormat formatter3 = new SimpleDateFormat("dd");
        Date curDate2 = new Date(System.currentTimeMillis());
        int times1 = Integer.parseInt(formatter1.format(curDate2));
        int times2 = Integer.parseInt(formatter2.format(curDate2)) - 1;
        int times3 = Integer.parseInt(formatter3.format(curDate2));

        Calendar endDate = Calendar.getInstance();
        endDate.set(times1, times2, times3);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                mSettingSGTZTime.setText(getTime(date));
                times = getTime(date);
                initTime(times);

//                Log.e("dasdassdasdasd", getTime(date));
//                DateTime dateTime = DateTime.parse(getTime(date));//设置的时间
//                initTime(dateTime);
            }
        })
//                .setType(TimePickerView.Type.ALL)//default is all
//                .setCancelText("Cancel")
//                .setSubmitText("Sure")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setOutSideCancelable(false)// default is true
                .setContentSize(18)
                .setTitleSize(20)
                .setTitleText("请选择时间")
                .setTitleColor(Color.BLACK)
                .setDividerColor(Color.BLACK)//设置分割线的颜色
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
//                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
//                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
//                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
//                .setSubmitColor(Color.WHITE)
//                .setCancelColor(Color.WHITE)
//                .gravity(Gravity.RIGHT)// default is center
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = (TextView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }

    private String getTime(Date date) {
        //可根据需要自行截取数据显示
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public void SettingSGTZ_Bank(View view) {
        finish();
    }
}
