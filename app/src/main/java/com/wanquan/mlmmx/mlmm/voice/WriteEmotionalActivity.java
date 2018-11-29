package com.wanquan.mlmmx.mlmm.voice;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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
import com.wanquan.mlmmx.mlmm.activity.BaseActivity;
import com.wanquan.mlmmx.mlmm.activity.FeedBackActivity;
import com.wanquan.mlmmx.mlmm.activity.MainActivity;
import com.wanquan.mlmmx.mlmm.activity.RegisterActivity;
import com.wanquan.mlmmx.mlmm.activity.SendInvitationActivity;
import com.wanquan.mlmmx.mlmm.beans.FeedBackGV1SuccessTuPianBeans;
import com.wanquan.mlmmx.mlmm.beans.FeedBackGV1SuccessTwoBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.PersonalinformationActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.WriteEmotionSuccessBeans;
import com.wanquan.mlmmx.mlmm.beans.WriteEmotionSuccessImgBeans;
import com.wanquan.mlmmx.mlmm.beans.WriteEmotionalBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.phone.Photo_Bimp;
import com.wanquan.mlmmx.mlmm.phone.Release_Work_Activity;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.TextUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MyDialog_Views;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：写日志
 * 时间：2017.11.25
 * 作者：薛昌峰
 */

public class WriteEmotionalActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout mWriteEmotionalRel1;
    private ImageView mWriteEmotionalImg1;
    private ImageView mWriteEmotionalDelete1;
    private RelativeLayout mWriteEmotionalRel2;
    private ImageView mWriteEmotionalImg2;
    private ImageView mWriteEmotionalDelete2;
    private RelativeLayout mWriteEmotionalRel3;
    private ImageView mWriteEmotionalImg3;
    private ImageView mWriteEmotionalDelete3;
    private RelativeLayout mWriteEmotionalRel4;
    private ImageView mWriteEmotionalImg4;
    private RelativeLayout mWriteEmotionalRel5;
    private ImageView mWriteEmotionalImg5;
    private ImageView mWriteEmotionalDelete5;
    private RelativeLayout mWriteEmotionalRel6;
    private ImageView mWriteEmotionalImg6;
    private ImageView mWriteEmotionalDelete6;
    private RelativeLayout mWriteEmotionalRel7;
    private ImageView mWriteEmotionalImg7;
    private ImageView mWriteEmotionalDelete7;
    private RelativeLayout mWriteEmotionalRel8;
    private ImageView mWriteEmotionalImg8;
    private static final String TAG = "WriteEmotionalActivity";
    private ImageView ivAbandonSound;
    private CommonSoundItemView soundItemView;
    private LinearLayout recordBtn;
    private TextView mWriteEmotionalText;
    private EditText mWriteEmotionalContent;
    private TextView mWriteEmotionalTv;
    private String path = null;
    private File tempFiles;
    private String base64;
    private int time;
    private ImgSelConfig config;
    private static final int REQUEST_CODE = 0;
    private Bitmap bm;
    private List<String> mPathList;
    private String string;
    private int id;
    private Boolean recharge_flag = true;
    private int time1;
    private int time2;
    String size4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(WriteEmotionalActivity.this, R.color.top);

        initData();
        initListeners();
        EventBus.getDefault().register(this);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_write_emotional;
    }

    @Override
    public void initView() throws Exception {
        mPathList = new ArrayList<>();
        mPathList.clear();
        mWriteEmotionalRel1 = (RelativeLayout) findViewById(R.id.Write_Emotional_Rel1);
        mWriteEmotionalImg1 = (ImageView) findViewById(R.id.Write_Emotional_Img1);
        mWriteEmotionalDelete1 = (ImageView) findViewById(R.id.Write_Emotional_Delete1);
        mWriteEmotionalRel2 = (RelativeLayout) findViewById(R.id.Write_Emotional_Rel2);
        mWriteEmotionalImg2 = (ImageView) findViewById(R.id.Write_Emotional_Img2);
        mWriteEmotionalDelete2 = (ImageView) findViewById(R.id.Write_Emotional_Delete2);
        mWriteEmotionalRel3 = (RelativeLayout) findViewById(R.id.Write_Emotional_Rel3);
        mWriteEmotionalImg3 = (ImageView) findViewById(R.id.Write_Emotional_Img3);
        mWriteEmotionalDelete3 = (ImageView) findViewById(R.id.Write_Emotional_Delete3);
        mWriteEmotionalRel4 = (RelativeLayout) findViewById(R.id.Write_Emotional_Rel4);
        mWriteEmotionalImg4 = (ImageView) findViewById(R.id.Write_Emotional_Img4);
        mWriteEmotionalRel5 = (RelativeLayout) findViewById(R.id.Write_Emotional_Rel5);
        mWriteEmotionalImg5 = (ImageView) findViewById(R.id.Write_Emotional_Img5);
        mWriteEmotionalDelete5 = (ImageView) findViewById(R.id.Write_Emotional_Delete5);
        mWriteEmotionalRel6 = (RelativeLayout) findViewById(R.id.Write_Emotional_Rel6);
        mWriteEmotionalImg6 = (ImageView) findViewById(R.id.Write_Emotional_Img6);
        mWriteEmotionalDelete6 = (ImageView) findViewById(R.id.Write_Emotional_Delete6);
        mWriteEmotionalRel7 = (RelativeLayout) findViewById(R.id.Write_Emotional_Rel7);
        mWriteEmotionalImg7 = (ImageView) findViewById(R.id.Write_Emotional_Img7);
        mWriteEmotionalDelete7 = (ImageView) findViewById(R.id.Write_Emotional_Delete7);
        mWriteEmotionalRel8 = (RelativeLayout) findViewById(R.id.Write_Emotional_Rel8);
        mWriteEmotionalImg8 = (ImageView) findViewById(R.id.Write_Emotional_Img8);
        mWriteEmotionalText = (TextView) findViewById(R.id.WriteEmotional_Text);
        mWriteEmotionalContent = (EditText) findViewById(R.id.WriteEmotional_Content);
        mWriteEmotionalTv = (TextView) findViewById(R.id.WriteEmotional_tv);
        ivAbandonSound = (ImageView) findViewById(R.id.pp_iv_abandon_sound);
        ivAbandonSound.setOnClickListener(this);
        soundItemView = (CommonSoundItemView) findViewById(R.id.pp_sound_item_view);
        recordBtn = (LinearLayout) findViewById(R.id.WriteEmotional_Rl);
        recordBtn.setOnClickListener(this);
    }

    private void initData() {
        config = new ImgSelConfig.Builder(WriteEmotionalActivity.this, loader)
                .multiSelect(true)
                // 是否记住上次选中记录
                .rememberSelected(false)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#fa749a")).build();
        config.maxNum = 6;
        config.titleBgColor = Color.parseColor("#fa749a");
    }

    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    };

    private void initListeners() {
        mWriteEmotionalContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mWriteEmotionalContent.getText().toString().trim().equals("")) {
                    mWriteEmotionalTv.setBackground(getResources().getDrawable(R.drawable.write_bg));
                } else {
                    mWriteEmotionalTv.setBackground(getResources().getDrawable(R.drawable.write_bgs));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mWriteEmotionalTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("eeeeeeeecontent", mWriteEmotionalContent.getText().toString().trim());
//                Log.e("eeeeeeeevoice", base64 + "");
                Log.e("eeeeeeeetime", time + "");
                if (TextUtils.isFastClick()) {
                    if (!mWriteEmotionalContent.getText().toString().trim().equals("") || path != null || mPathList.size() != 0) {
                        mWriteEmotionalTv.setClickable(false);
                        if (mPathList.size() <= 1) {
                            try {
                                encodeBase64(mPathList.get(0).toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("itfaceId", "041");
                            hashMap.put("token", SPUtils.get(WriteEmotionalActivity.this, "token", ""));
                            if (!"".equals(mWriteEmotionalContent.getText().toString().trim())) {
                                hashMap.put("content", mWriteEmotionalContent.getText().toString().trim());
                            }
                            if (!"".equals(base64)) {
                                hashMap.put("voice", base64);
                            }
                            hashMap.put("tail", "amr");
                            if (!"".equals(time)) {
                                hashMap.put("time", time);
                            }
                            if (!"".equals(string)) {
                                hashMap.put("img", string);
                            }
                            JSONObject jsonObject = new JSONObject(hashMap);

                            OkGo.post(UrlContent.URL).tag(this)
                                    .upJson(jsonObject.toString())
                                    .connTimeOut(10_000)
                                    .execute(new CustomCallBackNoLoading<WriteEmotionSuccessBeans>(WriteEmotionalActivity.this) {
                                        @Override
                                        public void onSuccess(final WriteEmotionSuccessBeans mWriteEmotionSuccessBeans, Call call, Response response) {
                                            if (mWriteEmotionSuccessBeans.getResultCode() == 1) {
                                                final Dialog dialog = new MyDialog_Views(WriteEmotionalActivity.this, R.style.MyDialog);
                                                dialog.setCancelable(false);
                                                dialog.show();
                                                MyDialog_Views myDialog_views = new MyDialog_Views(WriteEmotionalActivity.this, "正在上传...", "");
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (recharge_flag) {
                                                            MyDialog_Views myDialog_views = new MyDialog_Views(WriteEmotionalActivity.this, "上传成功", "success");
                                                        } else {
                                                            MyDialog_Views myDialog_views = new MyDialog_Views(WriteEmotionalActivity.this, "上传失败", "fail");
                                                        }
                                                        new Handler().postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                mWriteEmotionalTv.setClickable(true);
                                                                id = mWriteEmotionSuccessBeans.getData();
                                                                Toast toast = Toast.makeText(WriteEmotionalActivity.this, "发布成功", Toast.LENGTH_SHORT);
                                                                toast.setGravity(Gravity.CENTER, 0, 0);
                                                                toast.show();
                                                                mPathList.clear();
                                                                string = null;
                                                                Intent intent = new Intent(WriteEmotionalActivity.this, MainActivity.class);
                                                                setResult(12, intent);
                                                                finish();
                                                            }
                                                        }, 1000);
                                                    }
                                                }, 2000);
                                            } else {
                                                mWriteEmotionalTv.setClickable(true);
                                                App.ErrorToken(mWriteEmotionSuccessBeans.getResultCode(), mWriteEmotionSuccessBeans.getMsg());
                                            }
                                        }
                                    });
                        } else {
                            try {
                                encodeBase64(mPathList.get(0).toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("itfaceId", "041");
                            hashMap.put("token", SPUtils.get(WriteEmotionalActivity.this, "token", ""));
                            if (!"".equals(mWriteEmotionalContent.getText().toString().trim())) {
                                hashMap.put("content", mWriteEmotionalContent.getText().toString().trim());
                            }
                            if (!"".equals(base64)) {
                                hashMap.put("voice", base64);
                            }
                            hashMap.put("tail", "amr");
                            if (!"".equals(time)) {
                                hashMap.put("time", time);
                            }
                            if (!"".equals(string)) {
                                hashMap.put("img", string);
                            }
                            JSONObject jsonObject = new JSONObject(hashMap);

                            OkGo.post(UrlContent.URL).tag(this)
                                    .upJson(jsonObject.toString())
                                    .connTimeOut(10_000)
                                    .execute(new CustomCallBackNoLoading<WriteEmotionSuccessBeans>(WriteEmotionalActivity.this) {
                                        @Override
                                        public void onSuccess(final WriteEmotionSuccessBeans mWriteEmotionSuccessBeans, Call call, Response response) {
                                            int id = mWriteEmotionSuccessBeans.getData();
                                            for (int i = 1; i < mPathList.size(); i++) {
                                                try {
                                                    encodeBase64(mPathList.get(i).toString());
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                hashMap.put("itfaceId", "053");
                                                hashMap.put("id", id);
                                                hashMap.put("token", SPUtils.get(WriteEmotionalActivity.this, "token", ""));
                                                hashMap.put("img", string);
                                                JSONObject jsonObject = new JSONObject(hashMap);

                                                OkGo.post(UrlContent.URL).tag(this)
                                                        .upJson(jsonObject.toString())
                                                        .connTimeOut(10_000)
                                                        .execute(new CustomCallBackNoLoading<WriteEmotionSuccessImgBeans>(WriteEmotionalActivity.this) {
                                                            @Override
                                                            public void onSuccess(WriteEmotionSuccessImgBeans mWriteEmotionSuccessImgBeans, Call call, Response response) {
                                                                if (mWriteEmotionSuccessBeans.getResultCode() == 1) {
                                                                    final Dialog dialog2 = new MyDialog_Views(WriteEmotionalActivity.this, R.style.MyDialog);
                                                                    dialog2.setCancelable(false);
                                                                    dialog2.show();
                                                                    MyDialog_Views myDialog_views = new MyDialog_Views(WriteEmotionalActivity.this, "正在上传...", "");
                                                                    new Handler().postDelayed(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            if (recharge_flag) {
                                                                                MyDialog_Views myDialog_views = new MyDialog_Views(WriteEmotionalActivity.this, "上传成功", "success");
                                                                            } else {
                                                                                MyDialog_Views myDialog_views = new MyDialog_Views(WriteEmotionalActivity.this, "上传失败", "fail");
                                                                            }
                                                                            new Handler().postDelayed(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    mWriteEmotionalTv.setClickable(true);
                                                                                    Toast toast = Toast.makeText(WriteEmotionalActivity.this, "发布成功", Toast.LENGTH_SHORT);
                                                                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                                                                    toast.show();
                                                                                    mPathList.clear();
                                                                                    string = null;
                                                                                    Intent intent = new Intent(WriteEmotionalActivity.this, MainActivity.class);
                                                                                    setResult(12, intent);
                                                                                    finish();
                                                                                }
                                                                            }, 1000);
                                                                        }
                                                                    }, 2000);
                                                                } else {
                                                                    mWriteEmotionalTv.setClickable(true);
                                                                    App.ErrorToken(mWriteEmotionSuccessBeans.getResultCode(), mWriteEmotionSuccessBeans.getMsg());
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                    } else {
                        mWriteEmotionalTv.setClickable(true);
                        Toast toast = Toast.makeText(WriteEmotionalActivity.this, "请输入内容、语音或图片", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            }
        });

        mWriteEmotionalImg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = mPathList.size();
                int size2 = 6 - size;
                config.maxNum = size2;
                ImgSelActivity.startActivity(WriteEmotionalActivity.this, config, REQUEST_CODE);
            }
        });

        mWriteEmotionalImg8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = mPathList.size();
                int size2 = 6 - size;
                config.maxNum = size2;
                ImgSelActivity.startActivity(WriteEmotionalActivity.this, config, REQUEST_CODE);
            }
        });

        mWriteEmotionalDelete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathList.remove(0);
                mWriteEmotionalRel4.setVisibility(View.VISIBLE);
                mWriteEmotionalImg4.setVisibility(View.VISIBLE);
                mWriteEmotionalRel8.setVisibility(View.GONE);
                mWriteEmotionalImg8.setVisibility(View.GONE);
                mWriteEmotionalRel1.setVisibility(View.GONE);
                releaseImageViewResouce(mWriteEmotionalImg1);
                initSize();
                initSetTuPian();
            }
        });

        mWriteEmotionalDelete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathList.remove(1);
                mWriteEmotionalRel4.setVisibility(View.VISIBLE);
                mWriteEmotionalImg4.setVisibility(View.VISIBLE);
                mWriteEmotionalRel8.setVisibility(View.GONE);
                mWriteEmotionalImg8.setVisibility(View.GONE);
                mWriteEmotionalRel2.setVisibility(View.GONE);
                releaseImageViewResouce(mWriteEmotionalImg2);
                initSize();
                initSetTuPian();
            }
        });

        mWriteEmotionalDelete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathList.remove(2);
                mWriteEmotionalRel4.setVisibility(View.VISIBLE);
                mWriteEmotionalImg4.setVisibility(View.VISIBLE);
                mWriteEmotionalRel8.setVisibility(View.GONE);
                mWriteEmotionalImg8.setVisibility(View.GONE);
                mWriteEmotionalRel3.setVisibility(View.GONE);
                releaseImageViewResouce(mWriteEmotionalImg3);
                initSize();
                initSetTuPian();
            }
        });

        mWriteEmotionalDelete5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathList.remove(3);
                mWriteEmotionalRel4.setVisibility(View.GONE);
                mWriteEmotionalImg4.setVisibility(View.GONE);
                mWriteEmotionalRel8.setVisibility(View.VISIBLE);
                mWriteEmotionalImg8.setVisibility(View.VISIBLE);
                mWriteEmotionalRel5.setVisibility(View.GONE);
                releaseImageViewResouce(mWriteEmotionalImg5);
                initSize();
                initSetTuPian();
            }
        });

        mWriteEmotionalDelete6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathList.remove(4);
                mWriteEmotionalRel4.setVisibility(View.GONE);
                mWriteEmotionalImg4.setVisibility(View.GONE);
                mWriteEmotionalRel8.setVisibility(View.VISIBLE);
                mWriteEmotionalImg8.setVisibility(View.VISIBLE);
                mWriteEmotionalRel6.setVisibility(View.GONE);
                releaseImageViewResouce(mWriteEmotionalImg6);
                initSize();
                initSetTuPian();
            }
        });

        mWriteEmotionalDelete7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathList.remove(5);
                mWriteEmotionalRel4.setVisibility(View.GONE);
                mWriteEmotionalImg4.setVisibility(View.GONE);
                mWriteEmotionalRel8.setVisibility(View.VISIBLE);
                mWriteEmotionalImg8.setVisibility(View.VISIBLE);
                mWriteEmotionalRel7.setVisibility(View.GONE);
                releaseImageViewResouce(mWriteEmotionalImg7);
                initSize();
                initSetTuPian();
            }
        });
    }

    private void initSize() {
        if (mPathList.size() == 5) {
            mWriteEmotionalRel7.setVisibility(View.GONE);
        } else if (mPathList.size() == 4) {
            mWriteEmotionalRel6.setVisibility(View.GONE);
        } else if (mPathList.size() == 3) {
            mWriteEmotionalRel5.setVisibility(View.GONE);
        } else if (mPathList.size() == 2) {
            mWriteEmotionalRel3.setVisibility(View.GONE);
        } else if (mPathList.size() == 1) {
            mWriteEmotionalRel2.setVisibility(View.GONE);
        } else if (mPathList.size() == 0) {
            mWriteEmotionalRel1.setVisibility(View.GONE);
        }
    }

    private void initSetTuPian() {
        for (int i = 0; i < mPathList.size(); i++) {
            File file = new File(mPathList.get(i));
            if (file.exists()) {
                bm = getimage(mPathList.get(i));
                if (i == 0) {
                    mWriteEmotionalImg1.setImageBitmap(bm);
                    mWriteEmotionalRel1.setVisibility(View.VISIBLE);
                    mWriteEmotionalDelete1.setVisibility(View.VISIBLE);
                    mWriteEmotionalImg4.setVisibility(View.VISIBLE);
                } else if (i == 1) {
                    mWriteEmotionalImg2.setImageBitmap(bm);
                    mWriteEmotionalRel2.setVisibility(View.VISIBLE);
                    mWriteEmotionalDelete2.setVisibility(View.VISIBLE);
                    mWriteEmotionalImg4.setVisibility(View.VISIBLE);
                } else if (i == 2) {
                    mWriteEmotionalImg3.setImageBitmap(bm);
                    mWriteEmotionalRel3.setVisibility(View.VISIBLE);
                    mWriteEmotionalDelete3.setVisibility(View.VISIBLE);
                    mWriteEmotionalRel4.setVisibility(View.GONE);
                    mWriteEmotionalImg4.setVisibility(View.GONE);
                    mWriteEmotionalRel8.setVisibility(View.VISIBLE);
                    mWriteEmotionalImg8.setVisibility(View.VISIBLE);
                } else if (i == 3) {
                    mWriteEmotionalImg5.setImageBitmap(bm);
                    mWriteEmotionalRel5.setVisibility(View.VISIBLE);
                    mWriteEmotionalDelete5.setVisibility(View.VISIBLE);
                    mWriteEmotionalRel4.setVisibility(View.GONE);
                    mWriteEmotionalImg4.setVisibility(View.GONE);
                    mWriteEmotionalRel8.setVisibility(View.VISIBLE);
                    mWriteEmotionalImg8.setVisibility(View.VISIBLE);
                } else if (i == 4) {
                    mWriteEmotionalImg6.setImageBitmap(bm);
                    mWriteEmotionalRel6.setVisibility(View.VISIBLE);
                    mWriteEmotionalDelete6.setVisibility(View.VISIBLE);
                    mWriteEmotionalRel4.setVisibility(View.GONE);
                    mWriteEmotionalImg4.setVisibility(View.GONE);
                    mWriteEmotionalRel8.setVisibility(View.VISIBLE);
                    mWriteEmotionalImg8.setVisibility(View.VISIBLE);
                } else if (i == 5) {
                    mWriteEmotionalImg7.setImageBitmap(bm);
                    mWriteEmotionalRel7.setVisibility(View.VISIBLE);
                    mWriteEmotionalDelete7.setVisibility(View.VISIBLE);
                    mWriteEmotionalRel4.setVisibility(View.GONE);
                    mWriteEmotionalImg4.setVisibility(View.GONE);
                    mWriteEmotionalRel8.setVisibility(View.GONE);
                    mWriteEmotionalImg8.setVisibility(View.GONE);
                }
            }
        }
    }

    public static void releaseImageViewResouce(ImageView imageView) {
        if (imageView == null) return;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    private Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    private Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /**
     * EventBus监听
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MainThreadEvent mainThreadEvent) throws Exception {
        if (mainThreadEvent.getWhat() == EventBusConfig.SOUND_FEED_RECORD_FINISH) {
            Object soundPath = mainThreadEvent.getObj();
            if (soundPath != null && soundPath instanceof String) {
                path = (String) soundPath;
//                Log.e("ffffffffff", path);
                if (path != null) {
                    mWriteEmotionalTv.setBackground(getResources().getDrawable(R.drawable.write_bg));
                } else {
                    mWriteEmotionalTv.setBackground(getResources().getDrawable(R.drawable.write_bgs));
                }

                AudioEntity entity = new AudioEntity();
                entity.setUrl(path);
                encodeBase64File(path);
//                time = StringUtil.formatTime((int) entity.getDuration());

//                Log.e("ddddddddddddd", base64);

                int duration = AudioPlaybackManager.getDuration(path);
                if (duration <= 0) {
                    Log.e("voide", "duration <= 0");
                    PaoPaoTips.showDefault(this, "无权限");
                    File tempFile = new File(path);
                    if (tempFile.exists()) {
                        tempFile.delete();
                        return;
                    }
                } else {
                    entity.setDuration(duration / 1000);
                    soundItemView.setSoundData(entity);
                    soundItemView.setVisibility(View.VISIBLE);
                    ivAbandonSound.setVisibility(View.VISIBLE);
                    mWriteEmotionalText.setVisibility(View.GONE);
                    Log.e("voide", "soundPath:" + path);
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getIsVoiceTime() != null) {

            String isVoiceTime = event.getIsVoiceTime();
            Log.e("fsdfdsfsd", isVoiceTime + "");
            String size1 = isVoiceTime.substring(0, isVoiceTime.indexOf("’"));
            Log.e("fsdfdsfsd1", size1 + "");

//            String size2 = isVoiceTime.substring(isVoiceTime.indexOf("’"), isVoiceTime.indexOf("”"));
            String size2 = isVoiceTime.substring(0, isVoiceTime.length() - 1);
            Log.e("fsdfdsfsd2", size2 + "");
            String size3 = size2.substring(size2.length() - 2);
            Log.e("fsdfdsfsd3", size3 + "");
            if (!"".equals(size1)) {
                if (" 1".equals(size1)) {
                    time1 = 60;
                }else  if (" 2".equals(size1)) {
                    time1 = 120;
                }else  if (" 3".equals(size1)) {
                    time1 = 180;
                }else  if (" 4".equals(size1)) {
                    time1 = 240;
                } else if (" 5".equals(size1)) {
                    time1 = 300;
                }
            }
            if (!"".equals(size3)) {
                if (size3.contains(" ")) {
                    size4 = size3.substring(size3.length() - 1);
                } else {
                    size4 = size3;
                }
                time2 = Integer.parseInt(size4);
            }
            Log.e("fsdfdsfsdtime1", String.valueOf(time1) + "xcf1");
            Log.e("fsdfdsfsdtime2", String.valueOf(time2) + "xcf2");
            time = time1 + time2;
            Log.e("fsdfdsfsdtime3", String.valueOf(time) + "xcf3");
        }
    }

    /**
     * encodeBase64File:(将文件转成base64 字符串). <br/>
     *
     * @param path 文件路径
     * @return
     * @throws Exception
     * @author guhaizhou@126.com
     * @since JDK 1.6
     */
    public String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        base64 = Base64.encodeToString(buffer, Base64.DEFAULT);
        return base64;
    }

    /**
     * encodeBase64File:(将文件转成base64 字符串). <br/>
     *
     * @param path 文件路径
     * @return
     * @throws Exception
     * @author guhaizhou@126.com
     * @since JDK 1.6
     */
    public String encodeBase64(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        string = Base64.encodeToString(buffer, Base64.DEFAULT);
        return string;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            if (bm != null) {
                bm.recycle();
            }
            mPathList.addAll(data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT));
            initSetTuPian();
            if (mPathList.size() != 0) {
                mWriteEmotionalTv.setBackground(getResources().getDrawable(R.drawable.write_bg));
            } else {
                mWriteEmotionalTv.setBackground(getResources().getDrawable(R.drawable.write_bgs));
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pp_iv_abandon_sound) {//删除
            abandonSound();
        } else if (v.getId() == R.id.WriteEmotional_Rl) {
            AudioRecordJumpUtil.startRecordAudio(WriteEmotionalActivity.this);
        }
    }

    private void abandonSound() {
        soundItemView.clearData();
        soundItemView.setVisibility(View.GONE);
        ivAbandonSound.setVisibility(View.GONE);
        mWriteEmotionalText.setVisibility(View.VISIBLE);
    }

    public void WriteEmotional_Bank(View view) {
        finish();
    }
}
