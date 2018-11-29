//
//package com.wanquan.mlmmx.mlmm.activity;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Base64;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.lzy.okgo.OkGo;
//import com.wanquan.mlmmx.mlmm.App;
//import com.wanquan.mlmmx.mlmm.R;
//import com.wanquan.mlmmx.mlmm.beans.FeedBackGV1SuccessTuPianBeans;
//import com.wanquan.mlmmx.mlmm.beans.FeedBackGV1SuccessTwoBeans;
//import com.wanquan.mlmmx.mlmm.beans.FeedBackSuccessImgTwoBeans;
//import com.wanquan.mlmmx.mlmm.beans.SendInvitationActivityBeans;
//import com.wanquan.mlmmx.mlmm.beans.SendInvitationImgActivityBeans;
//import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
//import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
//import com.wanquan.mlmmx.mlmm.utils.SPUtils;
//import com.wanquan.mlmmx.mlmm.utils.UrlContent;
//import com.wanquan.mlmmx.mlmm.view.MyDialog_Views;
//import com.wanquan.mlmmx.mlmm.voice.WriteEmotionalActivity;
//import com.yuyh.library.imgsel.ImageLoader;
//import com.yuyh.library.imgsel.ImgSelActivity;
//import com.yuyh.library.imgsel.ImgSelConfig;
//
//import org.json.JSONObject;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import okhttp3.Call;
//import okhttp3.Response;
//
///**
// * 描述：圈子发帖
// * 作者：薛昌峰
// * 时间：2017. 12.11
// */
//public class SendInvitationActivity extends BaseActivity {
//    private TextView mSendInvitationSave;
//    private TextView mSendInvitationTV;
//    private RelativeLayout mSendInvitationRL;
//    private EditText mSendInvitationTitle;
//    private EditText mSendInvitationEd;
//    private RelativeLayout mSendInvitationRel1;
//    private ImageView mSendInvitationImg1;
//    private ImageView mSendInvitationDelete1;
//    private RelativeLayout mSendInvitationRel2;
//    private ImageView mSendInvitationImg2;
//    private ImageView mSendInvitationDelete2;
//    private RelativeLayout mSendInvitationRel3;
//    private ImageView mSendInvitationImg3;
//    private ImageView mSendInvitationDelete3;
//    private RelativeLayout mSendInvitationRel4;
//    private ImageView mSendInvitationImg4;
//    private RelativeLayout mSendInvitationRel5;
//    private ImageView mSendInvitationImg5;
//    private ImageView mSendInvitationDelete5;
//    private RelativeLayout mSendInvitationRel6;
//    private ImageView mSendInvitationImg6;
//    private ImageView mSendInvitationDelete6;
//    private RelativeLayout mSendInvitationRel7;
//    private ImageView mSendInvitationImg7;
//    private ImageView mSendInvitationDelete7;
//    private RelativeLayout mSendInvitationRel8;
//    private ImageView mSendInvitationImg8;
//    private ImgSelConfig config;
//    private static final int REQUEST_CODE = 0;
//    private Bitmap bm;
//    private List<String> mPathList = new ArrayList<>();
//    private String value = "";
//    private String string = "";
//    private Boolean recharge_flag = true;
//    private int id;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
//        manageUtils.setSystemBarTintManage(SendInvitationActivity.this, R.color.tops);
//
//        initData();
//        initListeners();
//    }
//
//    @Override
//    protected int setLayoutID() {
//        return R.layout.activity_sendi_invitation;
//    }
//
//    @Override
//    public void initView() throws Exception {
//        mSendInvitationTV = (TextView) findViewById(R.id.SendInvitation_tv);
//        mSendInvitationSave = (TextView) findViewById(R.id.SendInvitation_Save);
//        mSendInvitationRL = (RelativeLayout) findViewById(R.id.SendInvitation_RL);
//        mSendInvitationTitle = (EditText) findViewById(R.id.SendInvitation_Title);
//        mSendInvitationEd = (EditText) findViewById(R.id.SendInvitation_Ed);
//        mSendInvitationRel1 = (RelativeLayout) findViewById(R.id.SendInvitation_Rel1);
//        mSendInvitationImg1 = (ImageView) findViewById(R.id.SendInvitation_Img1);
//        mSendInvitationDelete1 = (ImageView) findViewById(R.id.SendInvitation_Delete1);
//        mSendInvitationRel2 = (RelativeLayout) findViewById(R.id.SendInvitation_Rel2);
//        mSendInvitationImg2 = (ImageView) findViewById(R.id.SendInvitation_Img2);
//        mSendInvitationDelete2 = (ImageView) findViewById(R.id.SendInvitation_Delete2);
//        mSendInvitationRel3 = (RelativeLayout) findViewById(R.id.SendInvitation_Rel3);
//        mSendInvitationImg3 = (ImageView) findViewById(R.id.SendInvitation_Img3);
//        mSendInvitationDelete3 = (ImageView) findViewById(R.id.SendInvitation_Delete3);
//        mSendInvitationRel4 = (RelativeLayout) findViewById(R.id.SendInvitation_Rel4);
//        mSendInvitationImg4 = (ImageView) findViewById(R.id.SendInvitation_Img4);
//        mSendInvitationRel5 = (RelativeLayout) findViewById(R.id.SendInvitation_Rel5);
//        mSendInvitationImg5 = (ImageView) findViewById(R.id.SendInvitation_Img5);
//        mSendInvitationDelete5 = (ImageView) findViewById(R.id.SendInvitation_Delete5);
//        mSendInvitationRel6 = (RelativeLayout) findViewById(R.id.SendInvitation_Rel6);
//        mSendInvitationImg6 = (ImageView) findViewById(R.id.SendInvitation_Img6);
//        mSendInvitationDelete6 = (ImageView) findViewById(R.id.SendInvitation_Delete6);
//        mSendInvitationRel7 = (RelativeLayout) findViewById(R.id.SendInvitation_Rel7);
//        mSendInvitationImg7 = (ImageView) findViewById(R.id.SendInvitation_Img7);
//        mSendInvitationDelete7 = (ImageView) findViewById(R.id.SendInvitation_Delete7);
//        mSendInvitationRel8 = (RelativeLayout) findViewById(R.id.SendInvitation_Rel8);
//        mSendInvitationImg8 = (ImageView) findViewById(R.id.SendInvitation_Img8);
//    }
//
//    private void initData() {
//        config = new ImgSelConfig.Builder(SendInvitationActivity.this, loader)
//                .multiSelect(true)
//                // 是否记住上次选中记录
//                .rememberSelected(false)
//                // 使用沉浸式状态栏
//                .statusBarColor(Color.parseColor("#fa749a")).build();
//        config.maxNum = 6;
//        config.titleBgColor = Color.parseColor("#fa749a");
//    }
//
//    private ImageLoader loader = new ImageLoader() {
//        @Override
//        public void displayImage(Context context, String path, ImageView imageView) {
//            Glide.with(context).load(path).into(imageView);
//        }
//    };
//
//    private void initListeners() {
//        mSendInvitationRL.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SendInvitationActivity.this, SendInvitationClassifyActivity.class);
//                startActivityForResult(intent, 1);
//            }
//        });
//
//        mSendInvitationImg4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int size = mPathList.size();
//                int size2 = 6 - size;
//                config.maxNum = size2;
//                ImgSelActivity.startActivity(SendInvitationActivity.this, config, REQUEST_CODE);
//            }
//        });
//
//        mSendInvitationImg8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int size = mPathList.size();
//                int size2 = 6 - size;
//                config.maxNum = size2;
//                ImgSelActivity.startActivity(SendInvitationActivity.this, config, REQUEST_CODE);
//            }
//        });
//
//        mSendInvitationDelete1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPathList.remove(0);
//                mSendInvitationRel4.setVisibility(View.VISIBLE);
//                mSendInvitationImg4.setVisibility(View.VISIBLE);
//                mSendInvitationRel8.setVisibility(View.GONE);
//                mSendInvitationImg8.setVisibility(View.GONE);
//                mSendInvitationRel1.setVisibility(View.GONE);
//                releaseImageViewResouce(mSendInvitationImg1);
//                initSize();
//                initSetTuPian();
//            }
//        });
//
//        mSendInvitationDelete2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPathList.remove(1);
//                mSendInvitationRel4.setVisibility(View.VISIBLE);
//                mSendInvitationImg4.setVisibility(View.VISIBLE);
//                mSendInvitationRel8.setVisibility(View.GONE);
//                mSendInvitationImg8.setVisibility(View.GONE);
//                mSendInvitationRel2.setVisibility(View.GONE);
//                releaseImageViewResouce(mSendInvitationImg2);
//                initSize();
//                initSetTuPian();
//            }
//        });
//
//        mSendInvitationDelete3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPathList.remove(2);
//                mSendInvitationRel4.setVisibility(View.VISIBLE);
//                mSendInvitationImg4.setVisibility(View.VISIBLE);
//                mSendInvitationRel8.setVisibility(View.GONE);
//                mSendInvitationImg8.setVisibility(View.GONE);
//                mSendInvitationRel3.setVisibility(View.GONE);
//                releaseImageViewResouce(mSendInvitationImg3);
//                initSize();
//                initSetTuPian();
//            }
//        });
//
//        mSendInvitationDelete5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPathList.remove(3);
//                mSendInvitationRel4.setVisibility(View.GONE);
//                mSendInvitationImg4.setVisibility(View.GONE);
//                mSendInvitationRel8.setVisibility(View.VISIBLE);
//                mSendInvitationImg8.setVisibility(View.VISIBLE);
//                mSendInvitationRel5.setVisibility(View.GONE);
//                releaseImageViewResouce(mSendInvitationImg5);
//                initSize();
//                initSetTuPian();
//            }
//        });
//
//        mSendInvitationDelete6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPathList.remove(4);
//                mSendInvitationRel4.setVisibility(View.GONE);
//                mSendInvitationImg4.setVisibility(View.GONE);
//                mSendInvitationRel8.setVisibility(View.VISIBLE);
//                mSendInvitationImg8.setVisibility(View.VISIBLE);
//                mSendInvitationRel6.setVisibility(View.GONE);
//                releaseImageViewResouce(mSendInvitationImg6);
//                initSize();
//                initSetTuPian();
//            }
//        });
//
//        mSendInvitationDelete7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPathList.remove(5);
//                mSendInvitationRel4.setVisibility(View.GONE);
//                mSendInvitationImg4.setVisibility(View.GONE);
//                mSendInvitationRel8.setVisibility(View.VISIBLE);
//                mSendInvitationImg8.setVisibility(View.VISIBLE);
//                mSendInvitationRel7.setVisibility(View.GONE);
//                releaseImageViewResouce(mSendInvitationImg7);
//                initSize();
//                initSetTuPian();
//            }
//        });
//
//        mSendInvitationSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (value.equals("")) {
//                    Toast toast = Toast.makeText(SendInvitationActivity.this, "请选择分类", Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                } else if (mSendInvitationTitle.getText().toString().trim().equals("")) {
//                    Toast toast = Toast.makeText(SendInvitationActivity.this, "请输入标题", Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                } else if (mSendInvitationEd.getText().toString().trim().equals("") && mPathList.size() == 0) {
//                    Toast toast = Toast.makeText(SendInvitationActivity.this, "请输入内容或选择图片", Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                } else {
//                    mSendInvitationSave.setClickable(false);
//                    if (mPathList.size() <= 1) {
//                        try {
//                            encodeBase64File(mPathList.get(0).toString());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        HashMap<String, Object> hashMap = new HashMap<>();
//                        hashMap.put("itfaceId", "049");
//                        hashMap.put("token", SPUtils.get(SendInvitationActivity.this, "token", ""));
//                        hashMap.put("title", mSendInvitationTitle.getText().toString().trim());
//                        hashMap.put("type", value);
//                        hashMap.put("content", mSendInvitationEd.getText().toString().trim());
//                        hashMap.put("img", string);
//                        JSONObject jsonObject = new JSONObject(hashMap);
//
//                        OkGo.post(UrlContent.URL).tag(this)
//                                .upJson(jsonObject.toString())
//                                .connTimeOut(10_000)
//                                .execute(new CustomCallBackNoLoading<SendInvitationActivityBeans>(SendInvitationActivity.this) {
//                                    @Override
//                                    public void onSuccess(SendInvitationActivityBeans mSendInvitationActivityBeans, Call call, Response response) {
//                                        if (mSendInvitationActivityBeans.getResultCode() == 1) {
//                                            id = mSendInvitationActivityBeans.getData();
//                                            final Dialog dialog = new MyDialog_Views(SendInvitationActivity.this, R.style.MyDialog);
//                                            dialog.setCancelable(false);
//                                            dialog.show();
//                                            MyDialog_Views myDialog_views = new MyDialog_Views(SendInvitationActivity.this, "正在上传...", "");
//                                            new Handler().postDelayed(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    if (recharge_flag) {
//                                                        MyDialog_Views myDialog_views = new MyDialog_Views(SendInvitationActivity.this, "发布成功", "success");
//                                                    } else {
//                                                        MyDialog_Views myDialog_views = new MyDialog_Views(SendInvitationActivity.this, "发布失败", "fail");
//                                                    }
//                                                    new Handler().postDelayed(new Runnable() {
//                                                        @Override
//                                                        public void run() {
//                                                            dialog.dismiss();
//                                                            finish();
//                                                            mSendInvitationSave.setClickable(true);
//                                                        }
//                                                    }, 2000);
//                                                }
//                                            }, 2000);
//                                        } else {
//                                            App.ErrorToken(mSendInvitationActivityBeans.getResultCode(), mSendInvitationActivityBeans.getMsg());
//
//                                        }
//                                    }
//                                });
//                    } else {
//                        try {
//                            encodeBase64File(mPathList.get(0).toString());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        HashMap<String, Object> hashMap = new HashMap<>();
//                        hashMap.put("itfaceId", "049");
//                        hashMap.put("token", SPUtils.get(SendInvitationActivity.this, "token", ""));
//                        hashMap.put("title", mSendInvitationTitle.getText().toString().trim());
//                        hashMap.put("type", value);
//                        hashMap.put("content", mSendInvitationEd.getText().toString().trim());
//                        hashMap.put("img", string);
//                        JSONObject jsonObject = new JSONObject(hashMap);
//
//                        OkGo.post(UrlContent.URL).tag(this)
//                                .upJson(jsonObject.toString())
//                                .connTimeOut(10_000)
//                                .execute(new CustomCallBackNoLoading<SendInvitationActivityBeans>(SendInvitationActivity.this) {
//                                    @Override
//                                    public void onSuccess(final SendInvitationActivityBeans mSendInvitationActivityBeans, Call call, Response response) {
//                                        id = mSendInvitationActivityBeans.getData();
//                                        for (int i = 1; i < mPathList.size(); i++) {
//                                            try {
//                                                encodeBase64File(mPathList.get(i).toString());
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//                                            HashMap<String, Object> hashMap = new HashMap<>();
//                                            hashMap.put("itfaceId", "050");
//                                            hashMap.put("id", id);
//                                            hashMap.put("token", SPUtils.get(SendInvitationActivity.this, "token", ""));
//                                            hashMap.put("img", string);
//                                            JSONObject jsonObject = new JSONObject(hashMap);
//
//                                            final int finalI = i;
//                                            OkGo.post(UrlContent.URL).tag(this)
//                                                    .upJson(jsonObject.toString())
//                                                    .connTimeOut(10_000)
//                                                    .execute(new CustomCallBackNoLoading<SendInvitationImgActivityBeans>(SendInvitationActivity.this) {
//                                                        @Override
//                                                        public void onSuccess(SendInvitationImgActivityBeans mSendInvitationImgActivityBeans, Call call, Response response) {
//                                                            if (mSendInvitationActivityBeans.getResultCode() == 1) {
//                                                                if (finalI == mPathList.size() - 1) {
//                                                                    final Dialog dialog = new MyDialog_Views(SendInvitationActivity.this, R.style.MyDialog);
//                                                                    dialog.setCancelable(false);
//                                                                    dialog.show();
//                                                                    MyDialog_Views myDialog_views = new MyDialog_Views(SendInvitationActivity.this, "正在上传...", "");
//                                                                    new Handler().postDelayed(new Runnable() {
//                                                                        @Override
//                                                                        public void run() {
//                                                                            if (recharge_flag) {
//                                                                                MyDialog_Views myDialog_views = new MyDialog_Views(SendInvitationActivity.this, "发布成功", "success");
//                                                                            } else {
//                                                                                MyDialog_Views myDialog_views = new MyDialog_Views(SendInvitationActivity.this, "发布失败", "fail");
//                                                                            }
//                                                                            new Handler().postDelayed(new Runnable() {
//                                                                                @Override
//                                                                                public void run() {
////                                                                            dialog.dismiss();
//                                                                                    finish();
//                                                                                }
//                                                                            }, 2000);
//                                                                        }
//                                                                    }, 1000);
//                                                                }
//                                                            } else {
//                                                                App.ErrorToken(mSendInvitationActivityBeans.getResultCode(), mSendInvitationActivityBeans.getMsg());
//                                                            }
//                                                        }
//                                                    });
//                                        }
//                                    }
//                                });
//                    }
//                }
//            }
//        });
//    }
//
//    /**
//     * encodeBase64File:(将文件转成base64 字符串). <br/>
//     *
//     * @param path 文件路径
//     * @return
//     * @throws Exception
//     * @author guhaizhou@126.com
//     * @since JDK 1.6
//     */
//    public String encodeBase64File(String path) throws Exception {
//        File file = new File(path);
//        FileInputStream inputFile = new FileInputStream(file);
//        byte[] buffer = new byte[(int) file.length()];
//        inputFile.read(buffer);
//        inputFile.close();
//        string = Base64.encodeToString(buffer, Base64.DEFAULT);
//        return string;
//    }
//
//    private void initSize() {
//        if (mPathList.size() == 5) {
//            mSendInvitationRel7.setVisibility(View.GONE);
//        } else if (mPathList.size() == 4) {
//            mSendInvitationRel6.setVisibility(View.GONE);
//        } else if (mPathList.size() == 3) {
//            mSendInvitationRel5.setVisibility(View.GONE);
//        } else if (mPathList.size() == 2) {
//            mSendInvitationRel3.setVisibility(View.GONE);
//        } else if (mPathList.size() == 1) {
//            mSendInvitationRel2.setVisibility(View.GONE);
//        } else if (mPathList.size() == 0) {
//            mSendInvitationRel1.setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
//            if (bm != null) {
//                bm.recycle();
//            }
//            mPathList.addAll(data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT));
//            initSetTuPian();
//        }
//        if (requestCode == 1 && resultCode == 2) {
//            value = data.getStringExtra("value");
//            mSendInvitationTV.setText(value);
//        }
//    }
//
//    private void initSetTuPian() {
//        for (int i = 0; i < mPathList.size(); i++) {
//            File file = new File(mPathList.get(i));
//            if (file.exists()) {
//                bm = getimage(mPathList.get(i));
//                if (i == 0) {
//                    mSendInvitationImg1.setImageBitmap(bm);
//                    mSendInvitationRel1.setVisibility(View.VISIBLE);
//                    mSendInvitationDelete1.setVisibility(View.VISIBLE);
//                    mSendInvitationImg4.setVisibility(View.VISIBLE);
//                } else if (i == 1) {
//                    mSendInvitationImg2.setImageBitmap(bm);
//                    mSendInvitationRel2.setVisibility(View.VISIBLE);
//                    mSendInvitationDelete2.setVisibility(View.VISIBLE);
//                    mSendInvitationImg4.setVisibility(View.VISIBLE);
//                } else if (i == 2) {
//                    mSendInvitationImg3.setImageBitmap(bm);
//                    mSendInvitationRel3.setVisibility(View.VISIBLE);
//                    mSendInvitationDelete3.setVisibility(View.VISIBLE);
//                    mSendInvitationRel4.setVisibility(View.GONE);
//                    mSendInvitationImg4.setVisibility(View.GONE);
//                    mSendInvitationRel8.setVisibility(View.VISIBLE);
//                    mSendInvitationImg8.setVisibility(View.VISIBLE);
//                } else if (i == 3) {
//                    mSendInvitationImg5.setImageBitmap(bm);
//                    mSendInvitationRel5.setVisibility(View.VISIBLE);
//                    mSendInvitationDelete5.setVisibility(View.VISIBLE);
//                    mSendInvitationRel4.setVisibility(View.GONE);
//                    mSendInvitationImg4.setVisibility(View.GONE);
//                    mSendInvitationRel8.setVisibility(View.VISIBLE);
//                    mSendInvitationImg8.setVisibility(View.VISIBLE);
//                } else if (i == 4) {
//                    mSendInvitationImg6.setImageBitmap(bm);
//                    mSendInvitationRel6.setVisibility(View.VISIBLE);
//                    mSendInvitationDelete6.setVisibility(View.VISIBLE);
//                    mSendInvitationRel4.setVisibility(View.GONE);
//                    mSendInvitationImg4.setVisibility(View.GONE);
//                    mSendInvitationRel8.setVisibility(View.VISIBLE);
//                    mSendInvitationImg8.setVisibility(View.VISIBLE);
//                } else if (i == 5) {
//                    mSendInvitationImg7.setImageBitmap(bm);
//                    mSendInvitationRel7.setVisibility(View.VISIBLE);
//                    mSendInvitationDelete7.setVisibility(View.VISIBLE);
//                    mSendInvitationRel4.setVisibility(View.GONE);
//                    mSendInvitationImg4.setVisibility(View.GONE);
//                    mSendInvitationRel8.setVisibility(View.GONE);
//                    mSendInvitationImg8.setVisibility(View.GONE);
//                }
//            }
//        }
//    }
//
//    public static void releaseImageViewResouce(ImageView imageView) {
//        if (imageView == null) return;
//        Drawable drawable = imageView.getDrawable();
//        if (drawable != null && drawable instanceof BitmapDrawable) {
//            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//            Bitmap bitmap = bitmapDrawable.getBitmap();
//            if (bitmap != null && !bitmap.isRecycled()) {
//                bitmap.recycle();
//            }
//        }
//    }
//
//    private Bitmap getimage(String srcPath) {
//        BitmapFactory.Options newOpts = new BitmapFactory.Options();
//        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
//        newOpts.inJustDecodeBounds = true;
//        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空
//
//        newOpts.inJustDecodeBounds = false;
//        int w = newOpts.outWidth;
//        int h = newOpts.outHeight;
//        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
//        float hh = 800f;//这里设置高度为800f
//        float ww = 480f;//这里设置宽度为480f
//        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
//        int be = 1;//be=1表示不缩放
//        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
//            be = (int) (newOpts.outWidth / ww);
//        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
//            be = (int) (newOpts.outHeight / hh);
//        }
//        if (be <= 0)
//            be = 1;
//        newOpts.inSampleSize = be;//设置缩放比例
//        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
//        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
//    }
//
//    private Bitmap compressImage(Bitmap image) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        int options = 100;
//        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
//            baos.reset();//重置baos即清空baos
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
//            options -= 10;//每次都减少10
//        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
//        return bitmap;
//    }
//
//    public void SendInvitationActivity_Bank(View view) {
//        finish();
//    }
//}

package com.wanquan.mlmmx.mlmm.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.wanquan.mlmmx.mlmm.beans.FeedBackGV1SuccessTuPianBeans;
import com.wanquan.mlmmx.mlmm.beans.FeedBackGV1SuccessTwoBeans;
import com.wanquan.mlmmx.mlmm.beans.FeedBackSuccessImgTwoBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.SendInvitationActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.SendInvitationImgActivityBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MyDialog_Views;
import com.wanquan.mlmmx.mlmm.voice.WriteEmotionalActivity;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：圈子发帖
 * 作者：薛昌峰
 * 时间：2017. 12.11
 */
public class SendInvitationActivity extends BaseActivity {
    private TextView mSendInvitationTitleTV;
    private TextView mSendInvitationSave;
    private TextView mSendInvitationTV;
    private RelativeLayout mSendInvitationRL;
    private EditText mSendInvitationTitle;
    private EditText mSendInvitationEd;
    private RelativeLayout mSendInvitationRel1;
    private ImageView mSendInvitationImg1;
    private ImageView mSendInvitationDelete1;
    private RelativeLayout mSendInvitationRel2;
    private ImageView mSendInvitationImg2;
    private ImageView mSendInvitationDelete2;
    private RelativeLayout mSendInvitationRel3;
    private ImageView mSendInvitationImg3;
    private ImageView mSendInvitationDelete3;
    private RelativeLayout mSendInvitationRel4;
    private ImageView mSendInvitationImg4;
    private RelativeLayout mSendInvitationRel5;
    private ImageView mSendInvitationImg5;
    private ImageView mSendInvitationDelete5;
    private RelativeLayout mSendInvitationRel6;
    private ImageView mSendInvitationImg6;
    private ImageView mSendInvitationDelete6;
    private RelativeLayout mSendInvitationRel7;
    private ImageView mSendInvitationImg7;
    private ImageView mSendInvitationDelete7;
    private RelativeLayout mSendInvitationRel8;
    private ImageView mSendInvitationImg8;
    private ImgSelConfig config;
    private static final int REQUEST_CODE = 0;
    private Bitmap bm;
    private List<String> mPathList = new ArrayList<>();
    private String value = "";
    private String string = "";
    private Boolean recharge_flag = true;
    private int id;
    private String name = "";
    private String idqz = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(SendInvitationActivity.this, R.color.tops);

        name = getIntent().getStringExtra("name");
        idqz = getIntent().getStringExtra("id");
        Log.e("fsdfsd", name + " ");
        if (name != null) {
            mSendInvitationTitleTV.setText(name);
        }

        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_sendi_invitation;
    }

    @Override
    public void initView() throws Exception {
        mSendInvitationTitleTV = (TextView) findViewById(R.id.SendInvitation_Title_tv);
        mSendInvitationTV = (TextView) findViewById(R.id.SendInvitation_tv);
        mSendInvitationSave = (TextView) findViewById(R.id.SendInvitation_Save);
        mSendInvitationRL = (RelativeLayout) findViewById(R.id.SendInvitation_RL);
        mSendInvitationTitle = (EditText) findViewById(R.id.SendInvitation_Title);
        mSendInvitationEd = (EditText) findViewById(R.id.SendInvitation_Ed);
        mSendInvitationRel1 = (RelativeLayout) findViewById(R.id.SendInvitation_Rel1);
        mSendInvitationImg1 = (ImageView) findViewById(R.id.SendInvitation_Img1);
        mSendInvitationDelete1 = (ImageView) findViewById(R.id.SendInvitation_Delete1);
        mSendInvitationRel2 = (RelativeLayout) findViewById(R.id.SendInvitation_Rel2);
        mSendInvitationImg2 = (ImageView) findViewById(R.id.SendInvitation_Img2);
        mSendInvitationDelete2 = (ImageView) findViewById(R.id.SendInvitation_Delete2);
        mSendInvitationRel3 = (RelativeLayout) findViewById(R.id.SendInvitation_Rel3);
        mSendInvitationImg3 = (ImageView) findViewById(R.id.SendInvitation_Img3);
        mSendInvitationDelete3 = (ImageView) findViewById(R.id.SendInvitation_Delete3);
        mSendInvitationRel4 = (RelativeLayout) findViewById(R.id.SendInvitation_Rel4);
        mSendInvitationImg4 = (ImageView) findViewById(R.id.SendInvitation_Img4);
        mSendInvitationRel5 = (RelativeLayout) findViewById(R.id.SendInvitation_Rel5);
        mSendInvitationImg5 = (ImageView) findViewById(R.id.SendInvitation_Img5);
        mSendInvitationDelete5 = (ImageView) findViewById(R.id.SendInvitation_Delete5);
        mSendInvitationRel6 = (RelativeLayout) findViewById(R.id.SendInvitation_Rel6);
        mSendInvitationImg6 = (ImageView) findViewById(R.id.SendInvitation_Img6);
        mSendInvitationDelete6 = (ImageView) findViewById(R.id.SendInvitation_Delete6);
        mSendInvitationRel7 = (RelativeLayout) findViewById(R.id.SendInvitation_Rel7);
        mSendInvitationImg7 = (ImageView) findViewById(R.id.SendInvitation_Img7);
        mSendInvitationDelete7 = (ImageView) findViewById(R.id.SendInvitation_Delete7);
        mSendInvitationRel8 = (RelativeLayout) findViewById(R.id.SendInvitation_Rel8);
        mSendInvitationImg8 = (ImageView) findViewById(R.id.SendInvitation_Img8);
    }

    private void initData() {
        config = new ImgSelConfig.Builder(SendInvitationActivity.this, loader)
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
        //选择圈子
        mSendInvitationTitleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendInvitationActivity.this, SelectCircleActivity.class);
                startActivityForResult(intent, 2);
            }
        });
//        mSendInvitationRL.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SendInvitationActivity.this, SendInvitationClassifyActivity.class);
//                startActivityForResult(intent, 1);
//            }
//        });

        mSendInvitationImg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = mPathList.size();
                int size2 = 6 - size;
                config.maxNum = size2;
                ImgSelActivity.startActivity(SendInvitationActivity.this, config, REQUEST_CODE);
            }
        });

        mSendInvitationImg8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = mPathList.size();
                int size2 = 6 - size;
                config.maxNum = size2;
                ImgSelActivity.startActivity(SendInvitationActivity.this, config, REQUEST_CODE);
            }
        });

        mSendInvitationDelete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSendInvitationRel4.setVisibility(View.VISIBLE);
                mSendInvitationImg4.setVisibility(View.VISIBLE);
                mSendInvitationRel8.setVisibility(View.GONE);
                mSendInvitationImg8.setVisibility(View.GONE);
                mSendInvitationRel1.setVisibility(View.GONE);
                mPathList.remove(0);
                releaseImageViewResouce(mSendInvitationImg1);
                initSize();
                initSetTuPian();
            }
        });

        mSendInvitationDelete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathList.remove(1);
                mSendInvitationRel4.setVisibility(View.VISIBLE);
                mSendInvitationImg4.setVisibility(View.VISIBLE);
                mSendInvitationRel8.setVisibility(View.GONE);
                mSendInvitationImg8.setVisibility(View.GONE);
                mSendInvitationRel2.setVisibility(View.GONE);
                releaseImageViewResouce(mSendInvitationImg2);
                initSize();
                initSetTuPian();
            }
        });

        mSendInvitationDelete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathList.remove(2);
                mSendInvitationRel4.setVisibility(View.VISIBLE);
                mSendInvitationImg4.setVisibility(View.VISIBLE);
                mSendInvitationRel8.setVisibility(View.GONE);
                mSendInvitationImg8.setVisibility(View.GONE);
                mSendInvitationRel3.setVisibility(View.GONE);
                releaseImageViewResouce(mSendInvitationImg3);
                initSize();
                initSetTuPian();
            }
        });

        mSendInvitationDelete5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathList.remove(3);
                mSendInvitationRel4.setVisibility(View.GONE);
                mSendInvitationImg4.setVisibility(View.GONE);
                mSendInvitationRel8.setVisibility(View.VISIBLE);
                mSendInvitationImg8.setVisibility(View.VISIBLE);
                mSendInvitationRel5.setVisibility(View.GONE);
                releaseImageViewResouce(mSendInvitationImg5);
                initSize();
                initSetTuPian();
            }
        });

        mSendInvitationDelete6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathList.remove(4);
                mSendInvitationRel4.setVisibility(View.GONE);
                mSendInvitationImg4.setVisibility(View.GONE);
                mSendInvitationRel8.setVisibility(View.VISIBLE);
                mSendInvitationImg8.setVisibility(View.VISIBLE);
                mSendInvitationRel6.setVisibility(View.GONE);
                releaseImageViewResouce(mSendInvitationImg6);
                initSize();
                initSetTuPian();
            }
        });

        mSendInvitationDelete7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathList.remove(5);
                mSendInvitationRel4.setVisibility(View.GONE);
                mSendInvitationImg4.setVisibility(View.GONE);
                mSendInvitationRel8.setVisibility(View.VISIBLE);
                mSendInvitationImg8.setVisibility(View.VISIBLE);
                mSendInvitationRel7.setVisibility(View.GONE);
                releaseImageViewResouce(mSendInvitationImg7);
                initSize();
                initSetTuPian();
            }
        });

        mSendInvitationSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(name)) {
                    Toast toast = Toast.makeText(SendInvitationActivity.this, "请选择圈子分类", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } /*else if (mSendInvitationTitle.getText().toString().trim().equals("")) {
                    Toast toast = Toast.makeText(SendInvitationActivity.this, "请输入标题", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } */ else if (mSendInvitationEd.getText().toString().trim().equals("") && mPathList.size() == 0) {
                    Toast toast = Toast.makeText(SendInvitationActivity.this, "请输入内容或选择图片", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    mSendInvitationSave.setClickable(false);
                    if (mPathList.size() <= 1) {
                        try {
                            encodeBase64File(mPathList.get(0).toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("itfaceId", "133");
                        hashMap.put("token", SPUtils.get(SendInvitationActivity.this, "token", ""));
                        hashMap.put("title", name);
                        hashMap.put("type", idqz);
                        hashMap.put("content", mSendInvitationEd.getText().toString().trim());
                        hashMap.put("img", string);
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<SendInvitationActivityBeans>(SendInvitationActivity.this) {
                                    @Override
                                    public void onSuccess(final SendInvitationActivityBeans mSendInvitationActivityBeans, Call call, Response response) {
                                        if (mSendInvitationActivityBeans.getResultCode() == 1) {
                                            id = mSendInvitationActivityBeans.getData().getCommentId();
                                            final Dialog dialog = new MyDialog_Views(SendInvitationActivity.this, R.style.MyDialog);
                                            dialog.setCancelable(false);
                                            dialog.show();
                                            MyDialog_Views myDialog_views = new MyDialog_Views(SendInvitationActivity.this, "正在上传...", "");
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (recharge_flag) {
                                                        MyDialog_Views myDialog_views = new MyDialog_Views(SendInvitationActivity.this, "发布成功", "success");
                                                    } else {
                                                        MyDialog_Views myDialog_views = new MyDialog_Views(SendInvitationActivity.this, "发布失败", "fail");
                                                    }
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            dialog.dismiss();
                                                            Intent intent = new Intent(SendInvitationActivity.this, MainActivity.class);
                                                            MessageEvent messageEvent = new MessageEvent();
                                                            messageEvent.setFinish(2);
                                                            EventBus.getDefault().post(messageEvent);
                                                            startActivity(intent);
                                                            finish();
                                                            if (mSendInvitationActivityBeans.getData().getGetIntegral() != 0) {
                                                                Toast.makeText(SendInvitationActivity.this, "发帖成功积分+" + mSendInvitationActivityBeans.getData().getGetIntegral(), Toast.LENGTH_SHORT).show();
                                                            }
                                                            mSendInvitationSave.setClickable(true);
                                                        }
                                                    }, 2000);
                                                }
                                            }, 1000);
                                        } else {
                                            App.ErrorToken(mSendInvitationActivityBeans.getResultCode(), mSendInvitationActivityBeans.getMsg());
                                        }
                                    }
                                });
                    } else {
                        try {
                            encodeBase64File(mPathList.get(0).toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("itfaceId", "133");
                        hashMap.put("token", SPUtils.get(SendInvitationActivity.this, "token", ""));
                        hashMap.put("title", name);
                        hashMap.put("type", idqz);
                        hashMap.put("content", mSendInvitationEd.getText().toString().trim());
                        hashMap.put("img", string);
                        JSONObject jsonObject = new JSONObject(hashMap);

                        OkGo.post(UrlContent.URL).tag(this)
                                .upJson(jsonObject.toString())
                                .connTimeOut(10_000)
                                .execute(new CustomCallBackNoLoading<SendInvitationActivityBeans>(SendInvitationActivity.this) {
                                    @Override
                                    public void onSuccess(final SendInvitationActivityBeans mSendInvitationActivityBeans, Call call, Response response) {
                                        id = mSendInvitationActivityBeans.getData().getForumId();
                                        for (int i = 1; i < mPathList.size(); i++) {
                                            Log.e("dsadasda", "到了");
                                            try {
                                                encodeBase64File(mPathList.get(i).toString());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            HashMap<String, Object> hashMap = new HashMap<>();
                                            hashMap.put("itfaceId", "050");
                                            hashMap.put("id", id);
                                            hashMap.put("token", SPUtils.get(SendInvitationActivity.this, "token", ""));
                                            hashMap.put("img", string);
                                            JSONObject jsonObject = new JSONObject(hashMap);

                                            final int finalI = i;
                                            OkGo.post(UrlContent.URL).tag(this)
                                                    .upJson(jsonObject.toString())
                                                    .connTimeOut(10_000)
                                                    .execute(new CustomCallBackNoLoading<SendInvitationImgActivityBeans>(SendInvitationActivity.this) {
                                                        @Override
                                                        public void onSuccess(SendInvitationImgActivityBeans mSendInvitationImgActivityBeans, Call call, Response response) {
                                                            if (mSendInvitationActivityBeans.getResultCode() == 1) {

                                                                if (finalI == mPathList.size() - 1) {
                                                                    final Dialog dialog = new MyDialog_Views(SendInvitationActivity.this, R.style.MyDialog);
                                                                    dialog.setCancelable(false);
                                                                    dialog.show();
                                                                    MyDialog_Views myDialog_views = new MyDialog_Views(SendInvitationActivity.this, "正在上传...", "");
                                                                    new Handler().postDelayed(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            if (recharge_flag) {
                                                                                MyDialog_Views myDialog_views = new MyDialog_Views(SendInvitationActivity.this, "发布成功", "success");
                                                                            } else {
                                                                                MyDialog_Views myDialog_views = new MyDialog_Views(SendInvitationActivity.this, "发布失败", "fail");
                                                                            }
                                                                            new Handler().postDelayed(new Runnable() {
                                                                                @Override
                                                                                public void run() {
//                                                                            dialog.dismiss();
                                                                                    Intent intent = new Intent(SendInvitationActivity.this, MainActivity.class);
                                                                                    MessageEvent messageEvent = new MessageEvent();
                                                                                    messageEvent.setFinish(2);
                                                                                    EventBus.getDefault().post(messageEvent);
                                                                                    startActivity(intent);
                                                                                    finish();
                                                                                    if (mSendInvitationActivityBeans.getData().getGetIntegral() != 0) {
                                                                                        Toast.makeText(SendInvitationActivity.this, "发帖成功积分+" + mSendInvitationActivityBeans.getData().getGetIntegral(), Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            }, 2000);
                                                                        }
                                                                    }, 1000);
                                                                }
                                                            } else {
                                                                App.ErrorToken(mSendInvitationActivityBeans.getResultCode(), mSendInvitationActivityBeans.getMsg());
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                    }
                }
            }
        });
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
        string = Base64.encodeToString(buffer, Base64.DEFAULT);
        return string;
    }

    private void initSize() {
        if (mPathList.size() == 5) {
            mSendInvitationRel7.setVisibility(View.GONE);
        } else if (mPathList.size() == 4) {
            mSendInvitationRel6.setVisibility(View.GONE);
        } else if (mPathList.size() == 3) {
            mSendInvitationRel5.setVisibility(View.GONE);
        } else if (mPathList.size() == 2) {
            mSendInvitationRel3.setVisibility(View.GONE);
        } else if (mPathList.size() == 1) {
            mSendInvitationRel2.setVisibility(View.GONE);
        } else if (mPathList.size() == 0) {
            mSendInvitationRel1.setVisibility(View.GONE);
        }
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
        }
        if (requestCode == 1 && resultCode == 2) {
            value = data.getStringExtra("value");
            mSendInvitationTV.setText(value);
        }
        //选择圈子返回
        if (requestCode == 2 && resultCode == 3) {
            name = data.getStringExtra("name");
            idqz = data.getStringExtra("id");
            Log.e("dfsasdfsdfsd", name);
            mSendInvitationTitleTV.setText(name);
        }
    }

    private void initSetTuPian() {
        for (int i = 0; i < mPathList.size(); i++) {
            File file = new File(mPathList.get(i));
            if (file.exists()) {
                bm = getimage(mPathList.get(i));
                if (i == 0) {
                    mSendInvitationImg1.setImageBitmap(bm);
                    mSendInvitationRel1.setVisibility(View.VISIBLE);
                    mSendInvitationDelete1.setVisibility(View.VISIBLE);
                    mSendInvitationImg4.setVisibility(View.VISIBLE);
                } else if (i == 1) {
                    mSendInvitationImg2.setImageBitmap(bm);
                    mSendInvitationRel2.setVisibility(View.VISIBLE);
                    mSendInvitationDelete2.setVisibility(View.VISIBLE);
                    mSendInvitationImg4.setVisibility(View.VISIBLE);
                } else if (i == 2) {
                    mSendInvitationImg3.setImageBitmap(bm);
                    mSendInvitationRel3.setVisibility(View.VISIBLE);
                    mSendInvitationDelete3.setVisibility(View.VISIBLE);
                    mSendInvitationRel4.setVisibility(View.GONE);
                    mSendInvitationImg4.setVisibility(View.GONE);
                    mSendInvitationRel8.setVisibility(View.VISIBLE);
                    mSendInvitationImg8.setVisibility(View.VISIBLE);
                } else if (i == 3) {
                    mSendInvitationImg5.setImageBitmap(bm);
                    mSendInvitationRel5.setVisibility(View.VISIBLE);
                    mSendInvitationDelete5.setVisibility(View.VISIBLE);
                    mSendInvitationRel4.setVisibility(View.GONE);
                    mSendInvitationImg4.setVisibility(View.GONE);
                    mSendInvitationRel8.setVisibility(View.VISIBLE);
                    mSendInvitationImg8.setVisibility(View.VISIBLE);
                } else if (i == 4) {
                    mSendInvitationImg6.setImageBitmap(bm);
                    mSendInvitationRel6.setVisibility(View.VISIBLE);
                    mSendInvitationDelete6.setVisibility(View.VISIBLE);
                    mSendInvitationRel4.setVisibility(View.GONE);
                    mSendInvitationImg4.setVisibility(View.GONE);
                    mSendInvitationRel8.setVisibility(View.VISIBLE);
                    mSendInvitationImg8.setVisibility(View.VISIBLE);
                } else if (i == 5) {
                    mSendInvitationImg7.setImageBitmap(bm);
                    mSendInvitationRel7.setVisibility(View.VISIBLE);
                    mSendInvitationDelete7.setVisibility(View.VISIBLE);
                    mSendInvitationRel4.setVisibility(View.GONE);
                    mSendInvitationImg4.setVisibility(View.GONE);
                    mSendInvitationRel8.setVisibility(View.GONE);
                    mSendInvitationImg8.setVisibility(View.GONE);
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

    public void SendInvitationActivity_Bank(View view) {
        finish();
    }
}
