package com.wanquan.mlmmx.mlmm.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.BabyActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.PersonalinformationModifitionImgBeans;
import com.wanquan.mlmmx.mlmm.beans.SettingBabyMessageBeans;
import com.wanquan.mlmmx.mlmm.beans.SettingBabyXiuGaiBeans;
import com.wanquan.mlmmx.mlmm.fragment.HomeFragment;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.SelectPicPopup;

import org.feezu.liuli.timeselector.TimeSelector;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：设置宝宝信息（已出生）
 * 作者：薛昌峰
 * 时间：2017.09.11
 */
public class SettingBabyMessageActivity extends BaseActivity {
    private SelectPicPopup menuWindow; // 自定义的头像编辑弹出框
    /**
     * 使用照相机拍照获取图片
     */
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    /**
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
    /**
     * 获取到的图片路径
     */
    private String picPath = "";
    private Uri mPhotoUri;
    private String string = null;

    private LinearLayout mSettingBabyMessageMama;
    private ImageView mSettingBabyMessageMamaImg;
    private LinearLayout mSettingBabyMessageBaba;
    private ImageView mSettingBabyMessageBabaImg;
    private LinearLayout mSettingBabyMessageOther;
    private ImageView mSettingBabyMessageOtherImg;
    private TextView mSettingBabyMessageOtherName;
    private TextView mSettingBabyMessageSave;
    private CircleImageView mSettingBabyMessageImg;
    private TextView mSettingBabyMessageImgTv;
    private EditText mSettingBabyMessageName;
    private TextView mSettingBabyMessageYear;
    private String Year;
    private LinearLayout mSettingBabyMessageSexy;
    private ImageView mSettingBabyMessageSexyImg;
    private LinearLayout mSettingBabyMessageSexs;
    private ImageView mSettingBabyMessageSexsImg;
    private EditText mSettingBabyMessageTZ;
    private EditText mSettingBabyMessageSG;
    private RelativeLayout mSettingBabyMessageRL;
    private TextView mSettingBabyMessageProperty;
    private int sex = 0;
    private String result;
    private String babyId;
    private String authority;
    private String sort = "";
    private List<BabyActivityBeans.DataBean.BabyMessageBean> mList2 = new ArrayList<>();
    private String byz = "false";
    private String hyz = "false";
    private TimePickerView pvCustomTime;
    private int format1;
    private int format2;
    private int format3;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(SettingBabyMessageActivity.this, R.color.black);
        initCustomTimePicker();
        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_setting_baby_message;
    }

    @Override
    public void initView() throws Exception {
        mSettingBabyMessageSave = (TextView) findViewById(R.id.SettingBabyMessage_save);
        mSettingBabyMessageImg = (CircleImageView) findViewById(R.id.SettingBabyMessage_Img);
        mSettingBabyMessageImgTv = (TextView) findViewById(R.id.SettingBabyMessage_ImgTv);
        mSettingBabyMessageName = (EditText) findViewById(R.id.SettingBabyMessage_Name);
        mSettingBabyMessageYear = (TextView) findViewById(R.id.SettingBabyMessage_Year);
        mSettingBabyMessageSexy = (LinearLayout) findViewById(R.id.SettingBabyMessage_sexy);
        mSettingBabyMessageSexyImg = (ImageView) findViewById(R.id.SettingBabyMessage_sexy_img);
        mSettingBabyMessageSexs = (LinearLayout) findViewById(R.id.SettingBabyMessage_sexs);
        mSettingBabyMessageSexsImg = (ImageView) findViewById(R.id.SettingBabyMessage_sexs_img);
        mSettingBabyMessageTZ = (EditText) findViewById(R.id.SettingBabyMessage_TZ);
        mSettingBabyMessageSG = (EditText) findViewById(R.id.SettingBabyMessage_SG);
        mSettingBabyMessageMama = (LinearLayout) findViewById(R.id.SettingBabyMessage_mama);
        mSettingBabyMessageMamaImg = (ImageView) findViewById(R.id.SettingBabyMessage_mama_img);
        mSettingBabyMessageBaba = (LinearLayout) findViewById(R.id.SettingBabyMessage_baba);
        mSettingBabyMessageBabaImg = (ImageView) findViewById(R.id.SettingBabyMessage_baba_img);
        mSettingBabyMessageOther = (LinearLayout) findViewById(R.id.SettingBabyMessage_other);
        mSettingBabyMessageOtherImg = (ImageView) findViewById(R.id.SettingBabyMessage_other_img);
        mSettingBabyMessageOtherName = (TextView) findViewById(R.id.SettingBabyMessage_other_name);
        mSettingBabyMessageRL = (RelativeLayout) findViewById(R.id.SettingBabyMessage_RL);
        mSettingBabyMessageProperty = (TextView) findViewById(R.id.SettingBabyMessage_Property);
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
                mSettingBabyMessageYear.setText(getTime(date));
                result = getTime(date);
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

    private void initData() {
        if (SPUtils.get(SettingBabyMessageActivity.this, "orderId", "").equals("1")) {
            mSettingBabyMessageSave.setVisibility(View.VISIBLE);
        } else if (SPUtils.get(SettingBabyMessageActivity.this, "orderId", "").equals("2")) {
            mSettingBabyMessageSave.setVisibility(View.GONE);
        }
        if (SpUtil.getBooleanValue(SettingBabyMessageActivity.this, MyContant.ISLOGIN, true)) {
            HashMap<String, Object> hashMap2 = new HashMap<>();
            hashMap2.put("itfaceId", "075");
            hashMap2.put("token", SPUtils.get(SettingBabyMessageActivity.this, "token", ""));
            JSONObject jsonObject2 = new JSONObject(hashMap2);

            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject2.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<BabyActivityBeans>(SettingBabyMessageActivity.this) {
                        @Override
                        public void onSuccess(BabyActivityBeans mBabyActivityBeans, Call call, Response response) {
                            if (mBabyActivityBeans.getResultCode() == 1) {
                                mList2.clear();
                                mList2.addAll(mBabyActivityBeans.getData().getBabyMessage());
                                List<Integer> integers = new ArrayList<Integer>();
                                for (int i = 0; i < mList2.size(); i++) {
                                    if (mList2.get(i).getOrderId() == 1) {
                                        integers.add(mList2.get(i).getSTATUS());
                                    }
                                }
                                for (int i = 0; i < integers.size(); i++) {
                                    int status = Integer.parseInt(integers.get(i).toString());
//                                    Log.e("kkkkkk", String.valueOf(status));
                                    if (status == 0) {
                                        byz = "true";
                                    } else if (status == 1) {
                                        hyz = "true";
                                    }
                                }
                            } else {
                                App.ErrorToken(mBabyActivityBeans.getResultCode(), mBabyActivityBeans.getMsg());

                            }
                        }
                    });
//            Log.e("fffftoken", (String) SPUtils.get(SettingBabyMessageActivity.this, "token", ""));
//            Log.e("ffffid", (String) SPUtils.get(SettingBabyMessageActivity.this, "babyId", ""));

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("itfaceId", "033");
            hashMap.put("token", SPUtils.get(SettingBabyMessageActivity.this, "token", ""));
            hashMap.put("id", SPUtils.get(SettingBabyMessageActivity.this, "babyId", ""));
            JSONObject jsonObject = new JSONObject(hashMap);

            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<SettingBabyMessageBeans>(SettingBabyMessageActivity.this) {
                        @Override
                        public void onSuccess(SettingBabyMessageBeans mSettingBabyMessageBeans, Call call, Response response) {
                            if (mSettingBabyMessageBeans.getResultCode() == 1) {
                                Glide.with(SettingBabyMessageActivity.this).load(mSettingBabyMessageBeans.getData().getBabyHeadIco()).into(mSettingBabyMessageImg);
                                //宝宝id
                                babyId = String.valueOf(mSettingBabyMessageBeans.getData().getId());
                                SPUtils.put(SettingBabyMessageActivity.this, "babyId", babyId);
                                authority = mSettingBabyMessageBeans.getData().getAuthority();
                                if (mSettingBabyMessageBeans.getData().getRelationship().equals("妈妈")) {
                                    mSettingBabyMessageMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                                    mSettingBabyMessageBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                                    sort = "妈妈";
                                } else if (mSettingBabyMessageBeans.getData().getRelationship().equals("爸爸")) {
                                    mSettingBabyMessageMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                                    mSettingBabyMessageBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                                    sort = "爸爸";
                                } else {
                                    mSettingBabyMessageMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                                    mSettingBabyMessageBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                                    mSettingBabyMessageOtherImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                                    sort = mSettingBabyMessageBeans.getData().getRelationship();
                                    mSettingBabyMessageOtherName.setText(sort);
                                }
                                if (mSettingBabyMessageBeans.getData().getBabyNickname().equals("null")) {
                                    mSettingBabyMessageName.setText("");
                                } else {
                                    mSettingBabyMessageName.setText(mSettingBabyMessageBeans.getData().getBabyNickname());
                                }
                                String childBirthDate = mSettingBabyMessageBeans.getData().getChildBirthDate();
                                if (!childBirthDate.equals("")) {
                                    String startTimes = childBirthDate.substring(0, childBirthDate.indexOf(" "));
                                    mSettingBabyMessageYear.setText(startTimes);
                                }
                                if (String.valueOf(mSettingBabyMessageBeans.getData().getBabySex()) == "1") {
                                    mSettingBabyMessageSexyImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                                    mSettingBabyMessageSexsImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                                } else if (String.valueOf(mSettingBabyMessageBeans.getData().getBabySex()) == "2") {
                                    mSettingBabyMessageSexyImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                                    mSettingBabyMessageSexsImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                                } else if (String.valueOf(mSettingBabyMessageBeans.getData().getBabySex()) == "") {
                                    mSettingBabyMessageSexyImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                                    mSettingBabyMessageSexsImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                                }
                                sex = mSettingBabyMessageBeans.getData().getBabySex();
                                mSettingBabyMessageTZ.setText(mSettingBabyMessageBeans.getData().getBabyWeight());
                                mSettingBabyMessageSG.setText(mSettingBabyMessageBeans.getData().getBabyHeight());
                                String status = String.valueOf(mSettingBabyMessageBeans.getData().getBabyStatus());
                                if (status.equals("0")) {
                                    mSettingBabyMessageProperty.setText("备孕中");
                                } else if (status.equals("1")) {
                                    mSettingBabyMessageProperty.setText("怀孕中");
                                } else if (status.equals("2")) {
                                    mSettingBabyMessageProperty.setText("已出生");
                                } else if (status.equals("3")) {
                                    mSettingBabyMessageProperty.setText("未设置");
                                }
                            } else {
                                App.ErrorToken(mSettingBabyMessageBeans.getResultCode(), mSettingBabyMessageBeans.getMsg());

                            }
                        }
                    });
        } else {
            Year = String.valueOf(SPUtils.get(SettingBabyMessageActivity.this, "w_timey", ""));
//            String result = Year.substring(0, Year.indexOf(" "));
            mSettingBabyMessageYear.setText(Year);

            if (String.valueOf(SPUtils.get(SettingBabyMessageActivity.this, "w_sex", "")).equals("1")) {
                mSettingBabyMessageSexyImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                mSettingBabyMessageSexsImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                sex = 1;
            } else if (String.valueOf(SPUtils.get(SettingBabyMessageActivity.this, "w_sex", "")).equals("2")) {
                mSettingBabyMessageSexyImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                mSettingBabyMessageSexsImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                sex = 2;
            }
            if (String.valueOf(SPUtils.get(SettingBabyMessageActivity.this, "w_setbabyname", "")).equals("null")) {
                mSettingBabyMessageName.setText("");
            } else {
                mSettingBabyMessageName.setText(String.valueOf(SPUtils.get(SettingBabyMessageActivity.this, "w_setbabyname", "")));
            }
            mSettingBabyMessageTZ.setText(String.valueOf(SPUtils.get(SettingBabyMessageActivity.this, "w_babytz", "")));
            mSettingBabyMessageSG.setText(String.valueOf(SPUtils.get(SettingBabyMessageActivity.this, "w_babysg", "")));
            if (SPUtils.get(SettingBabyMessageActivity.this, "sort", "").equals("妈妈")) {
                mSettingBabyMessageMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                mSettingBabyMessageBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                mSettingBabyMessageOtherImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                sort = "妈妈";
            } else if (SPUtils.get(SettingBabyMessageActivity.this, "sort", "").equals("爸爸")) {
                mSettingBabyMessageMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                mSettingBabyMessageBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                mSettingBabyMessageOtherImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                sort = "爸爸";
            } else /*if (SPUtils.get(SettingBabyMessageActivity.this, "sort", "").equals("其它"))*/ {
                mSettingBabyMessageMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                mSettingBabyMessageBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                mSettingBabyMessageOtherImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                Log.e("fsffdfd", String.valueOf(SPUtils.get(SettingBabyMessageActivity.this, "sort", "")));
                sort = String.valueOf(SPUtils.get(SettingBabyMessageActivity.this, "sort", ""));
                mSettingBabyMessageOtherName.setText(String.valueOf(SPUtils.get(SettingBabyMessageActivity.this, "sort", "")));
            }
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
        mSettingBabyMessageMama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSettingBabyMessageMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                mSettingBabyMessageBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                mSettingBabyMessageOtherImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                sort = "妈妈";
            }
        });
        mSettingBabyMessageBaba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSettingBabyMessageBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                mSettingBabyMessageMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                mSettingBabyMessageOtherImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                sort = "爸爸";
            }
        });
        mSettingBabyMessageOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingBabyMessageActivity.this, BabyRelationActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        //获取当前时间
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date curDate = new Date(System.currentTimeMillis());
//        final String times = formatter.format(curDate);

        mSettingBabyMessageYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideInputMethod(SettingBabyMessageActivity.this, view);
                pvCustomTime.show(); //弹出自定义时间选择器

//                TimeSelector timeSelector = new TimeSelector(SettingBabyMessageActivity.this, new TimeSelector.ResultHandler() {
//                    @Override
//                    public void handle(String time) {
//                        result = time.substring(0, time.indexOf(" "));
//                        mSettingBabyMessageYear.setText(result);
//                    }
//                }, "2000-01-01 00:00:00", times);
//                timeSelector.setIsLoop(true);//设置不循环,true循环
//                timeSelector.setMode(TimeSelector.MODE.YMD);//显示 年月日时分（默认）
//                timeSelector.show();
            }
        });
        //选择性别
        mSettingBabyMessageSexy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSettingBabyMessageSexyImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                mSettingBabyMessageSexsImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                sex = 1;
            }
        });
        mSettingBabyMessageSexs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSettingBabyMessageSexyImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
                mSettingBabyMessageSexsImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
                sex = 2;
            }
        });

        mSettingBabyMessageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSettingBabyMessageName.getText().toString().trim().equals("")) {
                    Toast toast = Toast.makeText(SettingBabyMessageActivity.this, "请输入宝宝昵称", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (mSettingBabyMessageYear.getText().toString().trim().equals("")) {
                    Toast toast = Toast.makeText(SettingBabyMessageActivity.this, "请输入宝宝生日", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (sex == 0) {
                    Toast toast = Toast.makeText(SettingBabyMessageActivity.this, "请输入选择宝宝性别", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } /*else if (mSettingBabyMessageTZ.getText().toString().trim().equals("")) {
                    Toast toast = Toast.makeText(SettingBabyMessageActivity.this, "请输入宝宝体重", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (mSettingBabyMessageSG.getText().toString().trim().equals("")) {
                    Toast toast = Toast.makeText(SettingBabyMessageActivity.this, "请输入宝宝身高", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } */ else if (sort.equals("")) {
                    Toast toast = Toast.makeText(SettingBabyMessageActivity.this, "请选择您与宝宝的关系", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    if (SpUtil.getBooleanValue(SettingBabyMessageActivity.this, MyContant.ISLOGIN, true)) {
                        initWork(SPUtils.get(SettingBabyMessageActivity.this, "BabyState", ""));
                    } else {
                        SPUtils.put(SettingBabyMessageActivity.this, "w_setbabyname", mSettingBabyMessageName.getText().toString().trim());
                        SPUtils.put(SettingBabyMessageActivity.this, "w_babytz", mSettingBabyMessageTZ.getText().toString().trim());
                        SPUtils.put(SettingBabyMessageActivity.this, "w_babysg", mSettingBabyMessageSG.getText().toString().trim());
                        SPUtils.put(SettingBabyMessageActivity.this, "w_timey", mSettingBabyMessageYear.getText().toString().trim());
                        //SPUtils.put(SettingBabyMessageActivity.this, "w_timey", mSettingBabyMessageYear.getText().toString().trim());
                        SPUtils.put(SettingBabyMessageActivity.this, "sort", sort);
                        Toast.makeText(SettingBabyMessageActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
        mSettingBabyMessageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SpUtil.getBooleanValue(SettingBabyMessageActivity.this, MyContant.ISLOGIN, true)) {
                    menuWindow = new SelectPicPopup(SettingBabyMessageActivity.this, itemsOnClick);
                    View parent1 = LayoutInflater.from(SettingBabyMessageActivity.this).inflate(R.layout.activity_information, null);
                    menuWindow.showAtLocation(parent1, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                } else {
                    startActivity(new Intent(SettingBabyMessageActivity.this, LoginActivity.class));
                }
            }
        });
        mSettingBabyMessageRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e("rrrrrrorderId", String.valueOf(SPUtils.get(SettingBabyMessageActivity.this, "orderId", "")));
//                Log.e("rrrrrrhyz", hyz + "xcf");
//                Log.e("rrrrrrbyz", byz + "xcf");
                if (SpUtil.getBooleanValue(SettingBabyMessageActivity.this, MyContant.ISLOGIN, true)) {
                    if (SPUtils.get(SettingBabyMessageActivity.this, "orderId", "").equals("1") && hyz.equals("true")) {
                        Toast.makeText(SettingBabyMessageActivity.this, "亲，您自己添加的宝宝中已经存在了怀孕状态哦！", Toast.LENGTH_SHORT).show();
                    } else if (SPUtils.get(SettingBabyMessageActivity.this, "orderId", "").equals("1") && byz.equals("true")) {
                        Toast.makeText(SettingBabyMessageActivity.this, "亲，您自己添加的宝宝中已经存在了备孕状态哦！", Toast.LENGTH_SHORT).show();
                    } else if (SPUtils.get(SettingBabyMessageActivity.this, "orderId", "").equals("1") && byz.equals("false") || hyz.equals("false")) {
                        initSend();
                    } else if (SPUtils.get(SettingBabyMessageActivity.this, "orderId", "").equals("2")) {
                        if (authority.equals("0")) {
                            Toast.makeText(SettingBabyMessageActivity.this, "亲，您还没有修改的权限哦！", Toast.LENGTH_SHORT).show();
                        } else if (hyz.equals("true")) {
                            Toast.makeText(SettingBabyMessageActivity.this, "亲，您已经存在怀孕状态哦！", Toast.LENGTH_SHORT).show();
                        } else if (byz.equals("true")) {
                            Toast.makeText(SettingBabyMessageActivity.this, "亲，您已经存在备孕状态哦！", Toast.LENGTH_SHORT).show();
                        } else if (authority.equals("1") || authority.equals("2")) {
                            initSend();
                        }
                    }
                } else {
                    initSend();
                }
            }
        });
    }

    private void initSend() {
//        if (App.mediaPlayer2 != null) {
//            if (App.mediaPlayer2.isPlaying()) {
//                App.mediaPlayer2.release();
//            }
//        }
        if (HomeFragment.mediaPlayer != null) {
            if (HomeFragment.mediaPlayer.isPlaying()) {
                HomeFragment.mediaPlayer.release();
            }
        }
        if (SpUtil.getBooleanValue(SettingBabyMessageActivity.this, MyContant.ISLOGIN, true)) {
            if (SPUtils.get(SettingBabyMessageActivity.this, "BabyState", "").equals("3")) {
                Intent intent = new Intent(SettingBabyMessageActivity.this, StateSettingActivity.class);
                intent.putExtra("flag", "1");
                startActivity(intent);
//                        SpUtil.setBooleanValue(SettingBabyMessageActivity.this, MyContant.SETTING, false);
//                        SpUtil.setBooleanValue(SettingBabyMessageActivity.this, MyContant.TIAOZHUAN, false);
//                        finish();
            } else {
                final AlertDialog alert;
                alert = new AlertDialog.Builder(SettingBabyMessageActivity.this).create();
                alert.show();
                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.delete_dialogs);
                alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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
                        Intent intent = new Intent(SettingBabyMessageActivity.this, StateSettingActivity.class);
                        intent.putExtra("flag", "1");
                        intent.putExtra("isTianJia", "0");
                        startActivity(intent);
//                                SpUtil.setBooleanValue(SettingBabyMessageActivity.this, MyContant.TIAOZHUAN, false);
//                                SpUtil.setBooleanValue(SettingBabyMessageActivity.this, MyContant.SETTING, false);
//                                finish();
                        alert.dismiss();
                    }
                });
            }
        } else {
            if (SPUtils.get(SettingBabyMessageActivity.this, "w_BabyState", "4").equals("3")) {
//            if (SPUtils.get(SettingBabyMessageActivity.this, "w_BabyState", "").equals("2")) {
                Intent intent = new Intent(SettingBabyMessageActivity.this, StateSettingActivity.class);
                intent.putExtra("flag", "1");
                intent.putExtra("hyz", "false");
                startActivity(intent);
//                        SpUtil.setBooleanValue(SettingBabyMessageActivity.this, MyContant.W_SETTING, false);
//                        SpUtil.setBooleanValue(SettingBabyMessageActivity.this, MyContant.W_TIAOZHUAN, false);
//                        finish();
            } else {
                final AlertDialog alert;
                alert = new AlertDialog.Builder(SettingBabyMessageActivity.this).create();
                alert.show();
                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.delete_dialogs);
                alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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
                        Intent intent = new Intent(SettingBabyMessageActivity.this, StateSettingActivity.class);
                        intent.putExtra("flag", "1");
                        intent.putExtra("isTianJia", "0");
                        startActivity(intent);
                        alert.dismiss();
                    }
                });
            }
        }
    }

    private void initWork(Object Status) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "074");
        hashMap.put("id", babyId);//宝宝id
//        hashMap.put("status", Status);
        hashMap.put("token", SPUtils.get(this, "token", ""));
        hashMap.put("nick_name", mSettingBabyMessageName.getText().toString().trim());
        hashMap.put("sex", sex);
//        hashMap.put("head_ico", string);
        hashMap.put("relationship", sort);
//        hashMap.put("weight", mSettingBabyMessageTZ.getText().toString().trim());
        hashMap.put("child_birth_date", mSettingBabyMessageYear.getText().toString().trim() + " 00:00:00");
//        hashMap.put("height", mSettingBabyMessageSG.getText().toString().trim());
        hashMap.put("id", SPUtils.get(SettingBabyMessageActivity.this, "babyId", ""));

        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<SettingBabyXiuGaiBeans>(SettingBabyMessageActivity.this) {
                    @Override
                    public void onSuccess(SettingBabyXiuGaiBeans mSettingBabyXiuGaiBeans, Call call, Response response) {
                        if (mSettingBabyXiuGaiBeans.getResultCode() == 1) {
                            SPUtils.put(SettingBabyMessageActivity.this, "nickname", mSettingBabyMessageName.getText().toString().trim());

                            Toast.makeText(SettingBabyMessageActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            if (result == null) {
                                SPUtils.put(SettingBabyMessageActivity.this, "timey", mSettingBabyMessageYear.getText().toString().trim());
                            } else {
                                SPUtils.put(SettingBabyMessageActivity.this, "timey", result);
                            }
                            finish();
                        } else {
                            App.ErrorToken(mSettingBabyXiuGaiBeans.getResultCode(), mSettingBabyXiuGaiBeans.getMsg());

                        }
                    }
                });
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                // 拍照
                case R.id.set_head_photograph:
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (ContextCompat.checkSelfPermission(SettingBabyMessageActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                                || ContextCompat.checkSelfPermission(SettingBabyMessageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(SettingBabyMessageActivity.this, Manifest.permission.CAMERA)) {
                            }
                            ActivityCompat.requestPermissions(SettingBabyMessageActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
                        } else {
                            takePhoto();
                        }
                    }
                    break;
                // 相册选择图片
                case R.id.set_head_album:
                    if (ContextCompat.checkSelfPermission(SettingBabyMessageActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SettingBabyMessageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(SettingBabyMessageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SettingBabyMessageActivity.this,
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                    } else {
                        pickPhoto();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 拍照获取图片
     */
//    @NeedsPermission({Manifest.permission_group.CONTACTS})
    void takePhoto() {
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            /***
             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
             * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
             * 如果不使用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
             */
            ContentValues values = new ContentValues();
            mPhotoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        } else {
            Toast.makeText(this, "内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }

    /***
     * 从相册中取图片
     */
    void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        // 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }


    /**
     * 选择图片后，获取图片的路径
     *
     * @param requestCode
     * @param data
     */
    private void doPhoto(int requestCode, Intent data) {
        // 从相册取图片，有些手机有异常情况，请注意
        if (requestCode == SELECT_PIC_BY_PICK_PHOTO) {
            if (data == null) {
//                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
            mPhotoUri = data.getData();
            if (mPhotoUri == null) {
//                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
        }
        String[] pojo = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(mPhotoUri, pojo, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);
            // 4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
            if (Integer.parseInt(Build.VERSION.SDK) < 14) {
                cursor.close();
            }
        }
        // 如果图片符合要求将其上传到服务器
        if (picPath != null && (picPath.endsWith(".png") || picPath.endsWith(".PNG") ||
                picPath.endsWith(".jpg") || picPath.endsWith(".JPG"))) {
            BitmapFactory.Options option = new BitmapFactory.Options();
            // 压缩图片:表示缩略图大小为原始图片大小的几分之一，1为原图
            option.inSampleSize = 1;
            // 根据图片的SDCard路径读出Bitmap
            Bitmap bm = BitmapFactory.decodeFile(picPath, option);
            // 显示在图片控件上
//            mSettingBabyMessageImg.setImageBitmap(bm);
            Log.i("mars", "doPhoto: " + picPath);
            if (bm != null) {
                bitmaptoString(bm, 24);
            }
            Glide.with(SettingBabyMessageActivity.this).load(picPath).into(mSettingBabyMessageImg);
            upLoadUImageHeader(string);
        } else {
            Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
        }
    }

    private void upLoadUImageHeader(String string) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "074");
        hashMap.put("token", SPUtils.get(this, "token", ""));
        hashMap.put("head_ico", string);
        hashMap.put("id", SPUtils.get(SettingBabyMessageActivity.this, "babyId", ""));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<PersonalinformationModifitionImgBeans>(SettingBabyMessageActivity.this) {
                    @Override
                    public void onSuccess(PersonalinformationModifitionImgBeans mPersonalinformationModifitionImgBeans, Call call, Response response) {
                        if (mPersonalinformationModifitionImgBeans.getResultCode() == 1) {
                            Toast.makeText(SettingBabyMessageActivity.this, "头像上传成功", Toast.LENGTH_SHORT).show();
                            if (SpUtil.getBooleanValue(SettingBabyMessageActivity.this, MyContant.ISLOGIN, true)) {
                                if (SPUtils.get(SettingBabyMessageActivity.this, "BabyState", "").equals("0")) {
                                    SPUtils.put(SettingBabyMessageActivity.this, "icon_b", "true");
                                } else if (SPUtils.get(SettingBabyMessageActivity.this, "BabyState", "").equals("2")) {
                                    SPUtils.put(SettingBabyMessageActivity.this, "icon_y", "true");
                                }
                            }/* else {
                                if (SPUtils.get(SettingBabyMessageActivity.this, "w_BabyState", "").equals("0")) {
                                    SPUtils.put(SettingBabyMessageActivity.this, "w_icon_b", "true");
                                    SPUtils.put(SettingBabyMessageActivity.this, "w_img_b", picPath);
                                } else if (SPUtils.get(SettingBabyMessageActivity.this, "w_BabyState", "").equals("2")) {
                                    SPUtils.put(SettingBabyMessageActivity.this, "w_icon_y", "true");
                                    SPUtils.put(SettingBabyMessageActivity.this, "w_img_y", picPath);
                                }
                            }*/
                        } else {
                            App.ErrorToken(mPersonalinformationModifitionImgBeans.getResultCode(), mPersonalinformationModifitionImgBeans.getMsg());
                        }
                    }
                });
    }

    /**
     * 将bitmap转换成base64字符串
     *
     * @param bitmap
     * @return base64 字符串
     */

    public String bitmaptoString(Bitmap bitmap, int bitmapQuality) {
        // 将Bitmap转换成字符串
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, bitmapQuality, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }


    /**
     * 接收从其他页面返回的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SELECT_PIC_BY_PICK_PHOTO:// 直接从相册获取
                doPhoto(requestCode, data);
                break;
            case SELECT_PIC_BY_TACK_PHOTO:// 调用相机拍照
                doPhoto(requestCode, data);
                break;
        }
        if (requestCode == 0 && resultCode == 1) {
            sort = data.getStringExtra("name");
            mSettingBabyMessageOtherName.setText(sort);
            mSettingBabyMessageBabaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
            mSettingBabyMessageMamaImg.setImageDrawable(getResources().getDrawable(R.mipmap.unchecked));
            mSettingBabyMessageOtherImg.setImageDrawable(getResources().getDrawable(R.mipmap.radiochecked));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void SettingBabyMessageActivity_Bank(View view) {
        finish();
    }
}
