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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
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
import com.wanquan.mlmmx.mlmm.adapter.FeedBackGridView1Adapter;
import com.wanquan.mlmmx.mlmm.beans.FeedBackGV1Beans;
import com.wanquan.mlmmx.mlmm.beans.FeedBackGV1SuccessBeans;
import com.wanquan.mlmmx.mlmm.beans.FeedBackGV1SuccessTuPianBeans;
import com.wanquan.mlmmx.mlmm.beans.FeedBackGV1SuccessTwoBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.phone.MyGridView;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MyDialog_Views;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

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
 * 描述：问题反馈
 * 作者：薛昌峰
 * 时间：2017.12.11
 */
public class FeedBackActivity extends BaseActivity implements FeedBackGridView1Adapter.SetImageView {
    private RelativeLayout mFeedBackRL;
    private ImageView mFeedBackIM;
    private LinearLayout mFeedBackLL;
    private MyGridView mFeedBackG1;
    private EditText mFeedBackContent;
    private MyGridView mFeedBackGV2;
    private RelativeLayout mCommentNewRel1;
    private ImageView mCommentNewActivityImg1;
    private ImageView mCommentNewDelete1;
    private RelativeLayout mCommentNewRel2;
    private ImageView mCommentNewActivityImg2;
    private ImageView mCommentNewDelete2;
    private RelativeLayout mCommentNewRel3;
    private ImageView mCommentNewActivityImg3;
    private ImageView mCommentNewDelete3;
    private RelativeLayout mCommentNewRel4;
    private ImageView mCommentNewActivityImg4;
    private RelativeLayout mCommentNewRel5;
    private ImageView mCommentNewActivityImg5;
    private ImageView mCommentNewDelete5;
    private RelativeLayout mCommentNewRel6;
    private ImageView mCommentNewActivityImg6;
    private ImageView mCommentNewDelete6;
    private RelativeLayout mCommentNewRel7;
    private ImageView mCommentNewActivityImg7;
    private ImageView mCommentNewDelete7;
    private RelativeLayout mCommentNewRel8;
    private ImageView mCommentNewActivityImg8;
    private TextView mFeedBackTextView;
    private FeedBackGridView1Adapter mFeedBackGridView1Adapter;
    //    private FeedBackGridView2Adapter mFeedBackGridView2Adapter;
    private List<FeedBackGV1Beans.DataBean> mList1 = new ArrayList<>();
    //    private List<String> mList2 = new ArrayList<>();
    private boolean isCheck = false;

    private ImgSelConfig config;
    private static final int REQUEST_CODE = 0;
    private Bitmap bm;
    private List<String> mPathList = new ArrayList<>();
    private String type;
    String string;
    private int positions = 10000;
    private Boolean recharge_flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(FeedBackActivity.this, R.color.black);

        initData();
        initListeners();

        mFeedBackGridView1Adapter = new FeedBackGridView1Adapter(mList1, this);
        mFeedBackGridView1Adapter.setImageView(this);
        mFeedBackG1.setAdapter(mFeedBackGridView1Adapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void initView() throws Exception {
        mFeedBackRL = (RelativeLayout) findViewById(R.id.FeedBack_RL);
        mFeedBackIM = (ImageView) findViewById(R.id.FeedBack_IM);
        mFeedBackLL = (LinearLayout) findViewById(R.id.FeedBack_LL);
        mFeedBackG1 = (MyGridView) findViewById(R.id.FeedBack_G1);
        mFeedBackContent = (EditText) findViewById(R.id.FeedBack_Content);
        mFeedBackGV2 = (MyGridView) findViewById(R.id.FeedBack_GV2);
        mCommentNewRel1 = (RelativeLayout) findViewById(R.id.CommentNew_Rel1);
        mCommentNewActivityImg1 = (ImageView) findViewById(R.id.CommentNewActivity_Img1);
        mCommentNewDelete1 = (ImageView) findViewById(R.id.CommentNew_Delete1);
        mCommentNewRel2 = (RelativeLayout) findViewById(R.id.CommentNew_Rel2);
        mCommentNewActivityImg2 = (ImageView) findViewById(R.id.CommentNewActivity_Img2);
        mCommentNewDelete2 = (ImageView) findViewById(R.id.CommentNew_Delete2);
        mCommentNewRel3 = (RelativeLayout) findViewById(R.id.CommentNew_Rel3);
        mCommentNewActivityImg3 = (ImageView) findViewById(R.id.CommentNewActivity_Img3);
        mCommentNewDelete3 = (ImageView) findViewById(R.id.CommentNew_Delete3);
        mCommentNewRel4 = (RelativeLayout) findViewById(R.id.CommentNew_Rel4);
        mCommentNewActivityImg4 = (ImageView) findViewById(R.id.CommentNewActivity_Img4);
        mCommentNewRel5 = (RelativeLayout) findViewById(R.id.CommentNew_Rel5);
        mCommentNewActivityImg5 = (ImageView) findViewById(R.id.CommentNewActivity_Img5);
        mCommentNewDelete5 = (ImageView) findViewById(R.id.CommentNew_Delete5);
        mCommentNewRel6 = (RelativeLayout) findViewById(R.id.CommentNew_Rel6);
        mCommentNewActivityImg6 = (ImageView) findViewById(R.id.CommentNewActivity_Img6);
        mCommentNewDelete6 = (ImageView) findViewById(R.id.CommentNew_Delete6);
        mCommentNewRel7 = (RelativeLayout) findViewById(R.id.CommentNew_Rel7);
        mCommentNewActivityImg7 = (ImageView) findViewById(R.id.CommentNewActivity_Img7);
        mCommentNewDelete7 = (ImageView) findViewById(R.id.CommentNew_Delete7);
        mCommentNewRel8 = (RelativeLayout) findViewById(R.id.CommentNew_Rel8);
        mCommentNewActivityImg8 = (ImageView) findViewById(R.id.CommentNewActivity_Img8);
        mFeedBackTextView = (TextView) findViewById(R.id.FeedBack_TextView);
    }

    private void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "045");
        hashMap.put("token", SPUtils.get(this, "token", ""));
        hashMap.put("key", "feedback.type");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<FeedBackGV1Beans>(this) {
                    @Override
                    public void onSuccess(FeedBackGV1Beans mFeedBackGV1Beans, Call call, Response response) {
                        if (mFeedBackGV1Beans.getResultCode() == 1) {
                            mList1.clear();
                            mList1.addAll(mFeedBackGV1Beans.getData());
                            for (int i = 0; i < mList1.size(); i++) {
                                if (i == positions) {
                                    mList1.get(i).setCheck(true);
                                } else {
                                    mList1.get(i).setCheck(false);
                                }
                            }
                            mFeedBackGridView1Adapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mFeedBackGV1Beans.getResultCode(), mFeedBackGV1Beans.getMsg());
                        }
                    }
                });

        config = new ImgSelConfig.Builder(FeedBackActivity.this, loader)
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
        mFeedBackContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mFeedBackContent.getText().toString().trim().equals("")) {
                    mFeedBackTextView.setBackground(getResources().getDrawable(R.drawable.write_bg));
                } else {
                    mFeedBackTextView.setBackground(getResources().getDrawable(R.drawable.write_bgs));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mFeedBackRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck) {
                    mFeedBackLL.setVisibility(View.VISIBLE);
                    mFeedBackIM.setImageDrawable(getResources().getDrawable(R.mipmap.arrowrightdown));
                    isCheck = false;
                } else {
                    mFeedBackLL.setVisibility(View.GONE);
                    isCheck = true;
                    mFeedBackIM.setImageDrawable(getResources().getDrawable(R.mipmap.arrowright));
                }
            }
        });

        mCommentNewActivityImg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = mPathList.size();
                int size2 = 6 - size;
                config.maxNum = size2;
                ImgSelActivity.startActivity(FeedBackActivity.this, config, REQUEST_CODE);
            }
        });

        mCommentNewActivityImg8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = mPathList.size();
                int size2 = 6 - size;
                config.maxNum = size2;
                ImgSelActivity.startActivity(FeedBackActivity.this, config, REQUEST_CODE);
            }
        });

        mCommentNewDelete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathList.remove(0);
                mCommentNewRel4.setVisibility(View.VISIBLE);
                mCommentNewActivityImg4.setVisibility(View.VISIBLE);
                mCommentNewRel8.setVisibility(View.GONE);
                mCommentNewActivityImg8.setVisibility(View.GONE);
                mCommentNewRel1.setVisibility(View.GONE);
                releaseImageViewResouce(mCommentNewActivityImg1);
                initSize();
                initSetTuPian();
            }
        });

        mCommentNewDelete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathList.remove(1);
                mCommentNewRel4.setVisibility(View.VISIBLE);
                mCommentNewActivityImg4.setVisibility(View.VISIBLE);
                mCommentNewRel8.setVisibility(View.GONE);
                mCommentNewActivityImg8.setVisibility(View.GONE);
                mCommentNewRel2.setVisibility(View.GONE);
                releaseImageViewResouce(mCommentNewActivityImg2);
                initSize();
                initSetTuPian();
            }
        });

        mCommentNewDelete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathList.remove(2);
                mCommentNewRel4.setVisibility(View.VISIBLE);
                mCommentNewActivityImg4.setVisibility(View.VISIBLE);
                mCommentNewRel8.setVisibility(View.GONE);
                mCommentNewActivityImg8.setVisibility(View.GONE);
                mCommentNewRel3.setVisibility(View.GONE);
                releaseImageViewResouce(mCommentNewActivityImg3);
                initSize();
                initSetTuPian();
            }
        });

        mCommentNewDelete5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathList.remove(3);
                mCommentNewRel4.setVisibility(View.GONE);
                mCommentNewActivityImg4.setVisibility(View.GONE);
                mCommentNewRel8.setVisibility(View.VISIBLE);
                mCommentNewActivityImg8.setVisibility(View.VISIBLE);
                mCommentNewRel5.setVisibility(View.GONE);
                releaseImageViewResouce(mCommentNewActivityImg5);
                initSize();
                initSetTuPian();
            }
        });

        mCommentNewDelete6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathList.remove(4);
                mCommentNewRel4.setVisibility(View.GONE);
                mCommentNewActivityImg4.setVisibility(View.GONE);
                mCommentNewRel8.setVisibility(View.VISIBLE);
                mCommentNewActivityImg8.setVisibility(View.VISIBLE);
                mCommentNewRel6.setVisibility(View.GONE);
                releaseImageViewResouce(mCommentNewActivityImg6);
                initSize();
                initSetTuPian();
            }
        });

        mCommentNewDelete7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathList.remove(5);
                mCommentNewRel4.setVisibility(View.GONE);
                mCommentNewActivityImg4.setVisibility(View.GONE);
                mCommentNewRel8.setVisibility(View.VISIBLE);
                mCommentNewActivityImg8.setVisibility(View.VISIBLE);
                mCommentNewRel7.setVisibility(View.GONE);
                releaseImageViewResouce(mCommentNewActivityImg7);
                initSize();
                initSetTuPian();
            }
        });

        mFeedBackTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == null) {
                    Toast toast = Toast.makeText(FeedBackActivity.this, "请选择反馈类型", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (mFeedBackContent.getText().toString().trim().equals("") && mPathList.size() == 0) {
                    Toast toast = Toast.makeText(FeedBackActivity.this, "请输入建议或选择照片", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    HashMap<String, Object> hashMap = new HashMap<>();
//                    hashMap.put("itfaceId", "044");
                    hashMap.put("itfaceId", "135");
                    hashMap.put("token", SPUtils.get(FeedBackActivity.this, "token", ""));
                    hashMap.put("type", type);
                    hashMap.put("message", mFeedBackContent.getText().toString().trim());
                    JSONObject jsonObject = new JSONObject(hashMap);

                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<FeedBackGV1SuccessBeans>(FeedBackActivity.this) {
                                @Override
                                public void onSuccess(final FeedBackGV1SuccessBeans mFeedBackGV1SuccessBeans, Call call, Response response) {
                                    if (mFeedBackGV1SuccessBeans.getResultCode() == 1) {
                                        if (mPathList.size() <= 1) {
                                            final Dialog dialog = new MyDialog_Views(FeedBackActivity.this, R.style.MyDialog);
                                            dialog.setCancelable(false);
                                            dialog.show();
                                            MyDialog_Views myDialog_views = new MyDialog_Views(FeedBackActivity.this, "正在上传...", "");
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (recharge_flag) {
                                                        MyDialog_Views myDialog_views = new MyDialog_Views(FeedBackActivity.this, "发布成功", "success");
                                                    } else {
                                                        MyDialog_Views myDialog_views = new MyDialog_Views(FeedBackActivity.this, "发布失败", "fail");
                                                    }
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            dialog.dismiss();
                                                            finish();
                                                            if (mFeedBackGV1SuccessBeans.getData().getGetIntegral() != 0) {
                                                                Toast.makeText(FeedBackActivity.this, "反馈成功积分+" + mFeedBackGV1SuccessBeans.getData().getGetIntegral(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }, 1000);
                                                }
                                            }, 2000);
                                        } else {
                                            for (int i = 0; i < mPathList.size(); i++) {
                                                try {
                                                    encodeBase64File(mPathList.get(i).toString());
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                hashMap.put("itfaceId", "047");
                                                hashMap.put("id", mFeedBackGV1SuccessBeans.getData());
                                                hashMap.put("img", string);
                                                JSONObject jsonObject = new JSONObject(hashMap);

                                                OkGo.post(UrlContent.URL).tag(this)
                                                        .upJson(jsonObject.toString())
                                                        .connTimeOut(10_000)
                                                        .execute(new CustomCallBackNoLoading<FeedBackGV1SuccessTuPianBeans>(FeedBackActivity.this) {
                                                            @Override
                                                            public void onSuccess(FeedBackGV1SuccessTuPianBeans mFeedBackGV1SuccessTuPianBeans, Call call, Response response) {
                                                                if (mFeedBackGV1SuccessBeans.getResultCode() == 1) {
                                                                    final Dialog dialog2 = new MyDialog_Views(FeedBackActivity.this, R.style.MyDialog);
                                                                    dialog2.setCancelable(false);
                                                                    dialog2.show();
                                                                    MyDialog_Views myDialog_views = new MyDialog_Views(FeedBackActivity.this, "正在上传...", "");
                                                                    new Handler().postDelayed(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            if (recharge_flag) {
                                                                                MyDialog_Views myDialog_views = new MyDialog_Views(FeedBackActivity.this, "发布成功", "success");
                                                                            } else {
                                                                                MyDialog_Views myDialog_views = new MyDialog_Views(FeedBackActivity.this, "发布失败", "fail");
                                                                            }
                                                                            new Handler().postDelayed(new Runnable() {
                                                                                @Override
                                                                                public void run() {
//                                                                                    if (dialog2 != null) {
//                                                                                        dialog2.dismiss();
//                                                                                    }
                                                                                    finish();
                                                                                    if (mFeedBackGV1SuccessBeans.getData().getGetIntegral() != 0) {
                                                                                        Toast.makeText(FeedBackActivity.this, "反馈成功积分+" + mFeedBackGV1SuccessBeans.getData().getGetIntegral(), Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            }, 1000);
                                                                        }
                                                                    }, 2000);
                                                                } else {
                                                                    App.ErrorToken(mFeedBackGV1SuccessTuPianBeans.getResultCode(), mFeedBackGV1SuccessTuPianBeans.getMsg());
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    } else {
                                        App.ErrorToken(mFeedBackGV1SuccessBeans.getResultCode(), mFeedBackGV1SuccessBeans.getMsg());

                                    }
                                }
                            });
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
            mCommentNewRel7.setVisibility(View.GONE);
        } else if (mPathList.size() == 4) {
            mCommentNewRel6.setVisibility(View.GONE);
        } else if (mPathList.size() == 3) {
            mCommentNewRel5.setVisibility(View.GONE);
        } else if (mPathList.size() == 2) {
            mCommentNewRel3.setVisibility(View.GONE);
        } else if (mPathList.size() == 1) {
            mCommentNewRel2.setVisibility(View.GONE);
        } else if (mPathList.size() == 0) {
            mCommentNewRel1.setVisibility(View.GONE);
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
            if (mPathList != null) {
                mFeedBackTextView.setBackground(getResources().getDrawable(R.drawable.write_bg));
            } else {
                mFeedBackTextView.setBackground(getResources().getDrawable(R.drawable.write_bgs));
            }
            initSetTuPian();
        }
    }

    private void initSetTuPian() {
        for (int i = 0; i < mPathList.size(); i++) {
            File file = new File(mPathList.get(i));
            if (file.exists()) {
                bm = getimage(mPathList.get(i));
                if (i == 0) {
                    mCommentNewActivityImg1.setImageBitmap(bm);
                    mCommentNewRel1.setVisibility(View.VISIBLE);
                    mCommentNewDelete1.setVisibility(View.VISIBLE);
                    mCommentNewActivityImg4.setVisibility(View.VISIBLE);
                } else if (i == 1) {
                    mCommentNewActivityImg2.setImageBitmap(bm);
                    mCommentNewRel2.setVisibility(View.VISIBLE);
                    mCommentNewDelete2.setVisibility(View.VISIBLE);
                    mCommentNewActivityImg4.setVisibility(View.VISIBLE);
                } else if (i == 2) {
                    mCommentNewActivityImg3.setImageBitmap(bm);
                    mCommentNewRel3.setVisibility(View.VISIBLE);
                    mCommentNewDelete3.setVisibility(View.VISIBLE);
                    mCommentNewRel4.setVisibility(View.GONE);
                    mCommentNewActivityImg4.setVisibility(View.GONE);
                    mCommentNewRel8.setVisibility(View.VISIBLE);
                    mCommentNewActivityImg8.setVisibility(View.VISIBLE);
                } else if (i == 3) {
                    mCommentNewActivityImg5.setImageBitmap(bm);
                    mCommentNewRel5.setVisibility(View.VISIBLE);
                    mCommentNewDelete5.setVisibility(View.VISIBLE);
                    mCommentNewRel4.setVisibility(View.GONE);
                    mCommentNewActivityImg4.setVisibility(View.GONE);
                    mCommentNewRel8.setVisibility(View.VISIBLE);
                    mCommentNewActivityImg8.setVisibility(View.VISIBLE);
                } else if (i == 4) {
                    mCommentNewActivityImg6.setImageBitmap(bm);
                    mCommentNewRel6.setVisibility(View.VISIBLE);
                    mCommentNewDelete6.setVisibility(View.VISIBLE);
                    mCommentNewRel4.setVisibility(View.GONE);
                    mCommentNewActivityImg4.setVisibility(View.GONE);
                    mCommentNewRel8.setVisibility(View.VISIBLE);
                    mCommentNewActivityImg8.setVisibility(View.VISIBLE);
                } else if (i == 5) {
                    mCommentNewActivityImg7.setImageBitmap(bm);
                    mCommentNewRel7.setVisibility(View.VISIBLE);
                    mCommentNewDelete7.setVisibility(View.VISIBLE);
                    mCommentNewRel4.setVisibility(View.GONE);
                    mCommentNewActivityImg4.setVisibility(View.GONE);
                    mCommentNewRel8.setVisibility(View.GONE);
                    mCommentNewActivityImg8.setVisibility(View.GONE);
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

    public void FeedBackActivity_Bank(View view) {
        finish();
    }

    /**
     * 接口回调
     */
    @Override
    public void ImageView(String Value, int position) {
        type = Value;
        Log.e("dddddd", type);
        positions = position;
        initData();
    }
}
