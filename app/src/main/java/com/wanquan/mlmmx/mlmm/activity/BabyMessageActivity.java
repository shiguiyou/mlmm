package com.wanquan.mlmmx.mlmm.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.BabyActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.BabyMessageAddBeans;
import com.wanquan.mlmmx.mlmm.beans.BabyMessageBeans;
import com.wanquan.mlmmx.mlmm.fragment.HomeFragment;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.feezu.liuli.timeselector.TimeSelector;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：宝宝信息(已出生)
 * 作者：薛昌峰
 * 时间：2017.09.27
 */
public class BabyMessageActivity extends BaseActivity {
    private TextView mBabyMessageSave;
    private TextView mBabyMessageTime;
    private EditText mBabyMessageName;
    private LinearLayout mBabyMessageSexy;
    private ImageView mBabyMessageSexyImg;
    private LinearLayout mBabyMessageSexs;
    private ImageView mBabyMessageSexsImg;
    private LinearLayout mBabyMessageMama;
    private ImageView mBabyMessageMamaImg;
    private LinearLayout mBabyMessageBaba;
    private ImageView mBabyMessageBabaImg;
    private LinearLayout mBabyMessageOther;
    private ImageView mBabyMessageOtherImg;
    private String times;
    private String times1;
    private int sex = 0;
    private String sort = "";
    private String result;
    private String isTianJia = "0";
    private TextView mBabyMessageOtherName;
    private TimePickerView pvCustomTime;
    private int format1;
    private int format2;
    private int format3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(BabyMessageActivity.this, R.color.black);

        isTianJia = getIntent().getStringExtra("isTianJia");
//        Log.e("BabyMessageActivity", isTianJia + "xcf");

        initCustomTimePicker();
        initData();
        initListeners();

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_baby_meassage;
    }

    @Override
    public void initView() throws Exception {
        mBabyMessageOtherName = (TextView) findViewById(R.id.BabyMessage_other_name);
        mBabyMessageSave = (TextView) findViewById(R.id.BabyMessage_Save);
        mBabyMessageName = (EditText) findViewById(R.id.BabyMessage_Name);
        mBabyMessageTime = (TextView) findViewById(R.id.BabyMessage_time);
        mBabyMessageSexy = (LinearLayout) findViewById(R.id.BabyMessage_sexy);
        mBabyMessageSexyImg = (ImageView) findViewById(R.id.BabyMessage_sexy_img);
        mBabyMessageSexs = (LinearLayout) findViewById(R.id.BabyMessage_sexs);
        mBabyMessageSexsImg = (ImageView) findViewById(R.id.BabyMessage_sexs_img);
        mBabyMessageMama = (LinearLayout) findViewById(R.id.BabyMessage_mama);
        mBabyMessageMamaImg = (ImageView) findViewById(R.id.BabyMessage_mama_img);
        mBabyMessageBaba = (LinearLayout) findViewById(R.id.BabyMessage_baba);
        mBabyMessageBabaImg = (ImageView) findViewById(R.id.BabyMessage_baba_img);
        mBabyMessageOther = (LinearLayout) findViewById(R.id.BabyMessage_other);
        mBabyMessageOtherImg = (ImageView) findViewById(R.id.BabyMessage_other_img);
    }

    private void initData() {
    }

    private void initCustomTimePicker() {
//        String startTime = String.valueOf(SPUtils.get(BabyMessageActivity.this, "timey", ""));
//        String startTimes;
//        if (startTime.contains(" ")) {
//            startTimes = startTime.substring(0, startTime.indexOf(" "));
//        } else {
//            startTimes = startTime;
//        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = sdf.parse("2000-01-01");
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
                mBabyMessageTime.setText(getTime(date));
                times = getTime(date);
                result = times;
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

    private void initListeners() {
        mBabyMessageMama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBabyMessageMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                mBabyMessageBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                mBabyMessageOtherImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                sort = "妈妈";
            }
        });
        mBabyMessageBaba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBabyMessageBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                mBabyMessageMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                mBabyMessageOtherImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                sort = "爸爸";
            }
        });
        mBabyMessageOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BabyMessageActivity.this, BabyRelationActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        mBabyMessageSexs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBabyMessageSexyImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                mBabyMessageSexsImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                sex = 2;
            }
        });
        mBabyMessageSexy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBabyMessageSexyImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                mBabyMessageSexsImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                sex = 1;
            }
        });
        mBabyMessageTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBabyMessageName.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(BabyMessageActivity.this, "请输入宝宝小名", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
//                    InputMethodManager inputMgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    inputMgr.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
                    hideInputMethod(BabyMessageActivity.this, view);

                    pvCustomTime.show();
                }
//                //获取当前时间
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date curDate = new Date(System.currentTimeMillis());
//                times = formatter.format(curDate);
//
//                TimeSelector timeSelector = new TimeSelector(BabyMessageActivity.this, new TimeSelector.ResultHandler() {
//                    @Override
//                    public void handle(String time) {
//                        times1 = time;
//                        result = time.substring(0, time.indexOf(" "));
//                        mBabyMessageTime.setText(result);
//                    }
//                }, times,"2000-01-01 00:00:00");
//                timeSelector.setIsLoop(true);//设置不循环,true循环
//                timeSelector.setMode(TimeSelector.MODE.YMD);//显示 年月日时分（默认）
//                timeSelector.show();
            }
        });
        mBabyMessageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBabyMessageName.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(BabyMessageActivity.this, "请输入宝宝小名", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (mBabyMessageTime.getText().toString().equals("点击设置时间")) {
                    Toast toast = Toast.makeText(BabyMessageActivity.this, "请选择时间", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (sex == 0) {
                    Toast toast = Toast.makeText(BabyMessageActivity.this, "请选择性别", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (sort.equals("")) {
                    Toast toast = Toast.makeText(BabyMessageActivity.this, "请选择您与宝宝的关系", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    if (SpUtil.getBooleanValue(BabyMessageActivity.this, MyContant.ISLOGIN, true)) {
//                        Log.e("rrrrrr", times1);
//                        Log.e("rrrrrr", String.valueOf(SPUtils.get(BabyMessageActivity.this, "userid", "")));
//                        Log.e("rrrrrr", String.valueOf(sex));
                        mBabyMessageSave.setClickable(false);
                        if (isTianJia.equals("1")) {//1为添加宝宝，其它为修改宝宝
                            Log.e("jjjjjjjj", "添加");
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("itfaceId", "132");
                            hashMap.put("status", "2");
                            hashMap.put("token", SPUtils.get(BabyMessageActivity.this, "token", ""));
                            hashMap.put("nickName", mBabyMessageName.getText().toString().trim());
                            hashMap.put("child_birth_date", result + " 00:00:00");
                            hashMap.put("sex", sex);
                            hashMap.put("relationship", sort);
                            JSONObject jsonObject = new JSONObject(hashMap);

                            OkGo.post(UrlContent.URL).tag(this)
                                    .upJson(jsonObject.toString())
                                    .connTimeOut(10_000)
                                    .execute(new CustomCallBackNoLoading<BabyMessageAddBeans>(BabyMessageActivity.this) {
                                        @Override
                                        public void onSuccess(BabyMessageAddBeans mBabyMessageAddBeans, Call call, Response response) {
                                            if (mBabyMessageAddBeans.getResultCode() == 1) {
//                                                Log.e("fdsdfs", String.valueOf(mBabyMessageAddBeans.getResultCode()));
//                                                Log.e("fdsdfs", mBabyMessageAddBeans.toString());
                                                SPUtils.put(BabyMessageActivity.this, "sort", sort);
                                                if (mBabyMessageAddBeans.getData().getGetIntegral() != 0) {
                                                    Toast toast = Toast.makeText(BabyMessageActivity.this, "添加宝宝成功积分+" + mBabyMessageAddBeans.getData().getGetIntegral(), Toast.LENGTH_SHORT);
                                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                                    toast.show();
                                                }
                                                mBabyMessageSave.setClickable(true);
                                                startActivity(new Intent(BabyMessageActivity.this, MainActivity.class));
                                                finish();
                                            } else {
                                                App.ErrorToken(mBabyMessageAddBeans.getResultCode(), mBabyMessageAddBeans.getMsg());
                                            }
                                        }
                                    });
                        } else if (isTianJia.equals("0")) {
//                            Log.e("jjjjjjjj", result);
//                            Log.e("jjjjjjjj", "修改");
//                            Log.e("jjjjjjjj_token", String.valueOf(SPUtils.get(BabyMessageActivity.this, "token", "")));
//                            Log.e("jjjjjjjj_isQieHuanBBId", String.valueOf(SPUtils.get(BabyMessageActivity.this, "isQieHuanBBId", "")));
//                            Log.e("jjjjjjjj", result + " 00:00:00");
//                            Log.e("jjjjjjjj", mBabyMessageName.getText().toString().trim());
//                            Log.e("jjjjjjjj", sort);
//                            Log.e("jjjjjjjj", String.valueOf(sex));
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("itfaceId", "074");
                            hashMap.put("token", SPUtils.get(BabyMessageActivity.this, "token", ""));
                            hashMap.put("id", SPUtils.get(BabyMessageActivity.this, "babyId", ""));
                            hashMap.put("child_birth_date", result + " 00:00:00");
                            hashMap.put("nick_name", mBabyMessageName.getText().toString().trim());
                            hashMap.put("relationship", sort);
                            hashMap.put("sex", sex);
                            hashMap.put("status", "2");
                            JSONObject jsonObject = new JSONObject(hashMap);

                            OkGo.post(UrlContent.URL).tag(this)
                                    .upJson(jsonObject.toString())
                                    .connTimeOut(10_000)
                                    .execute(new CustomCallBackNoLoading<BabyMessageBeans>(BabyMessageActivity.this) {
                                        @Override
                                        public void onSuccess(BabyMessageBeans mBabyMessageBeans, Call call, Response response) {
                                            if (mBabyMessageBeans.getResultCode() == 1) {
                                                SPUtils.put(BabyMessageActivity.this, "sort", sort);
                                                Toast toast = Toast.makeText(BabyMessageActivity.this, "保存成功", Toast.LENGTH_SHORT);
                                                toast.setGravity(Gravity.CENTER, 0, 0);
                                                toast.show();
                                                SpUtil.setBooleanValue(BabyMessageActivity.this, MyContant.SETTING, true);
                                                SpUtil.setBooleanValue(BabyMessageActivity.this, MyContant.TIAOZHUAN, true);
                                                SPUtils.put(BabyMessageActivity.this, "timey", result);
                                                SPUtils.put(BabyMessageActivity.this, "BabyState", "2");
                                                startActivity(new Intent(BabyMessageActivity.this, MainActivity.class));
                                                if (HomeFragment.mediaPlayer != null) {
                                                    HomeFragment.mediaPlayer.stop();
                                                    HomeFragment.isPlay = true;
                                                    HomeFragment.isPlayStart = false;
                                                }
                                                finish();
                                            } else {
                                                App.ErrorToken(mBabyMessageBeans.getResultCode(), mBabyMessageBeans.getMsg());
                                            }
                                        }
                                    });
                        }
                    } else {
                        Toast toast = Toast.makeText(BabyMessageActivity.this, "保存成功", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        SPUtils.put(BabyMessageActivity.this, "w_BabyState", "2");
                        SPUtils.put(BabyMessageActivity.this, "w_setbabyname", mBabyMessageName.getText().toString().trim());
                        SPUtils.put(BabyMessageActivity.this, "w_timey", result);
                        SPUtils.put(BabyMessageActivity.this, "w_sex", String.valueOf(sex));
                        SpUtil.setBooleanValue(BabyMessageActivity.this, MyContant.W_SETTING, true);
                        SpUtil.setBooleanValue(BabyMessageActivity.this, MyContant.W_TIAOZHUAN, true);
                        SPUtils.put(BabyMessageActivity.this, "sort", sort);
//                        Log.e("fffffff", sort);
                        if (HomeFragment.mediaPlayer != null) {
                            HomeFragment.mediaPlayer.stop();
                            HomeFragment.isPlay = true;
                            HomeFragment.isPlayStart = false;
                        }

                        //切换过宝宝后通知工具栏修改为其它状态下的值
//                        SPUtils.put(BabyMessageActivity.this, "GJSet", "");

                        startActivity(new Intent(BabyMessageActivity.this, MainActivity.class));
                        finish();
                    }
                }
            }
        });
    }

    private Boolean hideInputMethod(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 1) {
            sort = data.getStringExtra("name");
            mBabyMessageOtherName.setText(sort);
            mBabyMessageBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
            mBabyMessageMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
            mBabyMessageOtherImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
        }
    }

    public void BabyMessageActivity_Bank(View view) {
        finish();
    }
}
