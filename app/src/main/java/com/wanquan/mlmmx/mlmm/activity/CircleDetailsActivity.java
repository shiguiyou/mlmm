package com.wanquan.mlmmx.mlmm.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import com.wanquan.mlmmx.mlmm.adapter.CircleDetailsAdapter;
import com.wanquan.mlmmx.mlmm.adapter.CircleDetailsGridViewAdapter;
import com.wanquan.mlmmx.mlmm.beans.CircleDetailsBeans;
import com.wanquan.mlmmx.mlmm.beans.CircleDetailsFaSongBeans;
import com.wanquan.mlmmx.mlmm.beans.CircleDetailsSizeBeans;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.fragment.CircleFragment;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.phone.HideInputManager_Utils;
import com.wanquan.mlmmx.mlmm.phone.Login_Static;
import com.wanquan.mlmmx.mlmm.phone.MyGridView;
import com.wanquan.mlmmx.mlmm.phone.MyPicpupWindow;
import com.wanquan.mlmmx.mlmm.phone.MyPicpupWindows;
import com.wanquan.mlmmx.mlmm.phone.Photo_Album_Activity;
import com.wanquan.mlmmx.mlmm.phone.Photo_Bimp;
import com.wanquan.mlmmx.mlmm.phone.Release_Work_Activity;
import com.wanquan.mlmmx.mlmm.utils.ImageInfo;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.ImgActivity;
import com.wanquan.mlmmx.mlmm.view.MessagePicturesLayout;
import com.wanquan.mlmmx.mlmm.view.MyListView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：圈子详情
 * 作者：薛昌峰
 * 时间：2017.12.04
 */
public class CircleDetailsActivity extends ImgActivity implements CircleDetailsAdapter.InterfaceCircleDetails, CircleDetailsAdapter.InterfaceCircleDetailsisShow/*, View.OnLayoutChangeListener*/ {
    private MyPicpupWindows mPopWindow;//选择照片方式弹出框
    private CircleDetailsAdapter mCircleDetailsAdapter;
    private CircleDetailsGridViewAdapter mCircleDetailsGridViewAdapter;
    private List<CircleDetailsBeans.DataBean> mList = new ArrayList<>();
    public LinearLayout mCircleDetailsSave;
    public RelativeLayout mCircleDetailsActivitys;
    public MyGridView mCircleDetailsMyGridView;
    private CircleImageView mCircleDetailsImage;
    private TextView mCircleDetailsName;
    private TextView mCircleDetailsTime;
    private TextView mCircleDetailsTime2;
    private TextView mCircleDetailsTitle;
    private TextView mCircleDetailsContent;
    private MyListView mCircleDetailsListView;
    private RelativeLayout mCircleDetailsRL;
    private EditText mCircleDetailsEditText;
    private TextView mCircleDetailsSize;
    private ImageView mCircleDetailsCollect;
    private TextView mCircleDetailsFaSong;
    private LinearLayout mCircleDetailsLinkLinearLayout;
    private TextView mCircleDetailsLink;


    private String headico;
    private String name;
    private String name2;
    private String message;
    private String time;
    private String title;
    private String content;
    private String id;
    private List<String> mList2;
    private ImageView mCircleDetailsImg;
    boolean isCheck = true;
    private String img2;
    private int follow;
    private String fid = "0";
    private String pid = "0";
    private boolean isLzouzhu;
    private boolean isShowRefreshs;
    private MessagePicturesLayout mCallback;
    private ArrayList<ImageInfo> data = new ArrayList<ImageInfo>();
    private String createTime;
    private int commentCount;
    private static View view;
    //Activity最外层的Layout视图
    private View activityRootView;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    private String userId;
    private int collectFlag;
    private String html;

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initNetWork() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "061");
        hashMap.put("token", SPUtils.get(this, "token", ""));
        hashMap.put("id", id);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<CircleDetailsSizeBeans>(this) {
                    @Override
                    public void onSuccess(CircleDetailsSizeBeans mCircleDetailsSizeBeans, Call call, Response response) {
                        if (mCircleDetailsSizeBeans.getResultCode() == 1) {
                            commentCount = mCircleDetailsSizeBeans.getData().getCommentCount();
                        } else {
                            App.ErrorToken(mCircleDetailsSizeBeans.getResultCode(), mCircleDetailsSizeBeans.getMsg());
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_details);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(CircleDetailsActivity.this, R.color.black);

        //注册
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }

        id = String.valueOf(getIntent().getIntExtra("id", 0));
        userId = String.valueOf(getIntent().getIntExtra("userId", 0));
        headico = getIntent().getStringExtra("headico");
        name = getIntent().getStringExtra("name");
        name2 = getIntent().getStringExtra("name2");
        message = getIntent().getStringExtra("message");
        time = getIntent().getStringExtra("time");
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        commentCount = getIntent().getIntExtra("commentCount", -1);
        img2 = getIntent().getStringExtra("img");
        follow = getIntent().getIntExtra("follow", -1);
        createTime = getIntent().getStringExtra("createTime");
        html = getIntent().getStringExtra("html");

        Log.e("dsadsad", id);
//        Log.e("xcfheadico",String.valueOf(userId));
//        Log.e("xcfheadico",headico);
//        Log.e("xcfname",name);
//        Log.e("xcfname",name2);
//        Log.e("xcfname",message);
//        Log.e("xcftime",time);
//        Log.e("xcftitle",title);
//        Log.e("xcfcontent",content);
//        Log.e("xcfcontent", String.valueOf(commentCount));
////        Log.e("xcfimg2",img2);
        Log.e("xcffollow", String.valueOf(follow));
//        Log.e("xcffollow", String.valueOf(createTime));


        initViews();

        mList2 = new ArrayList<>();

        if (follow == 0) {
            isCheck = true;
        } else {
            isCheck = false;
        }
        if (img2 != null) {
            mCircleDetailsImg.setVisibility(View.GONE);
            String[] imgs = img2.split("\\|");
            mList2.clear();
            for (int i = 0; i < imgs.length; i++) {
                mList2.add(imgs[i]);
            }
        } else {
            mCircleDetailsImg.setVisibility(View.GONE);
        }
        for (int i = 0; i < mList2.size(); i++) {
            ImageInfo model = new ImageInfo();
            model.url = mList2.get(i).toString();
            model.width = 1280;
            model.height = 720;
            data.add(model);
        }

        initListeners();

        mCircleDetailsGridViewAdapter = new CircleDetailsGridViewAdapter(CircleDetailsActivity.this, data);
        mCircleDetailsMyGridView.setAdapter(mCircleDetailsGridViewAdapter);

        mCircleDetailsAdapter = new CircleDetailsAdapter(this, mList, id, isShowRefreshs);
        mCircleDetailsAdapter.setDetails(this);
        mCircleDetailsAdapter.setDetailsisShow(CircleDetailsActivity.this);
        mCircleDetailsListView.setAdapter(mCircleDetailsAdapter);
    }

    private void initViews() {
        mCircleDetailsSave = (LinearLayout) findViewById(R.id.CircleDetails_Save);
        mCircleDetailsActivitys = (RelativeLayout) findViewById(R.id.CircleDetailsActivitys);
        mCircleDetailsMyGridView = (MyGridView) findViewById(R.id.CircleDetails_MyGridView);
        mCircleDetailsImage = (CircleImageView) findViewById(R.id.CircleDetails_Image);
        mCircleDetailsName = (TextView) findViewById(R.id.CircleDetails_Name);
        mCircleDetailsTime = (TextView) findViewById(R.id.CircleDetails_Time);
        mCircleDetailsTime2 = (TextView) findViewById(R.id.CircleDetails_Time2);
        mCircleDetailsTitle = (TextView) findViewById(R.id.CircleDetails_Title);
        mCircleDetailsContent = (TextView) findViewById(R.id.CircleDetails_Content);
        mCircleDetailsListView = (MyListView) findViewById(R.id.CircleDetails_ListView);
        mCircleDetailsRL = (RelativeLayout) findViewById(R.id.CircleDetails_RL);
        mCircleDetailsEditText = (EditText) findViewById(R.id.CircleDetails_EditText);
        mCircleDetailsSize = (TextView) findViewById(R.id.CircleDetails_Size);
        mCircleDetailsCollect = (ImageView) findViewById(R.id.CircleDetails_Collect);
        mCircleDetailsFaSong = (TextView) findViewById(R.id.CircleDetails_FaSong);
        mCircleDetailsImg = (ImageView) findViewById(R.id.CircleDetails_Img);
        mCircleDetailsLinkLinearLayout = (LinearLayout) findViewById(R.id.CircleDetails_LinkLinearLayout);
        mCircleDetailsLink = (TextView) findViewById(R.id.CircleDetails_Link);

    }

    private void initData() {
        //链接
        if (html != null) {
            mCircleDetailsLinkLinearLayout.setVisibility(View.VISIBLE);
            if (html.contains("share=1")) {
                mCircleDetailsLink.setText("资讯");
            } else {
                mCircleDetailsLink.setText("育儿知识");
            }
        } else {
            mCircleDetailsLinkLinearLayout.setVisibility(View.GONE);
        }


        initNetWork();
        if (follow != 0) {
            mCircleDetailsCollect.setBackground(getResources().getDrawable(R.mipmap.commentcollectioned));
        } else {
            mCircleDetailsCollect.setBackground(getResources().getDrawable(R.mipmap.commentcolletion));
        }

        Glide.with(this).load(headico).into(mCircleDetailsImage);
        if (name2 != null) {
            mCircleDetailsName.setText(name2);
        } else {
            if (!TextUtils.isEmpty(name) && name.length() >= 11) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < name.length(); i++) {
                    char c = name.charAt(i);
                    if (i >= 3 && i <= 6) {
                        sb.append("*");
                    } else {
                        sb.append(c);
                    }
                }
                mCircleDetailsName.setText(sb.toString());
            }
        }
        mCircleDetailsTime.setText(message);
        mCircleDetailsTime2.setText(createTime);
        mCircleDetailsTitle.setText(title);
        mCircleDetailsContent.setText(content);

//        Log.e("圈子详情", String.valueOf(SPUtils.get(this, "token", "")));
        Log.e("圈子详情", id + "");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "051");
        hashMap.put("token", SPUtils.get(this, "token", ""));
        hashMap.put("forumId", id);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<CircleDetailsBeans>(this) {
                    @Override
                    public void onSuccess(CircleDetailsBeans mCircleDetailsBeans, Call call, Response response) {
                        if (mCircleDetailsBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mCircleDetailsBeans.getData());
                            mCircleDetailsSize.setText("已有" + commentCount + "条回复");
                            mCircleDetailsAdapter = new CircleDetailsAdapter(CircleDetailsActivity.this, mList, id, isShowRefreshs);
                            mCircleDetailsAdapter.setDetails(CircleDetailsActivity.this);
                            mCircleDetailsAdapter.setDetailsisShow(CircleDetailsActivity.this);
                            mCircleDetailsListView.setAdapter(mCircleDetailsAdapter);
                        } else {
                            App.ErrorToken(mCircleDetailsBeans.getResultCode(), mCircleDetailsBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {

        mCircleDetailsLinkLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (html.contains("share=1")) {
                    title = "资讯";
                } else {
                    title = "育儿知识";
                }
                Intent intent = new Intent(CircleDetailsActivity.this, GuideActivity.class);
                intent.putExtra("flag", "3");
                intent.putExtra("htmlUrl", html);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });
        mCircleDetailsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //底部弹出框
                //实例化MyPicPopupWindow
                HideInputManager_Utils.hideInputManager(CircleDetailsActivity.this);//隐藏 键盘
                mPopWindow = new MyPicpupWindows(CircleDetailsActivity.this, itemsOnClick, "举报");
                //设置弹出动画效果
                // mPopWindow.setAnimationStyle(R.style.PopupAnimation);
                //显示窗口  //设置layout在PopupWindow中显示的位置
                mPopWindow.showAtLocation(findViewById(R.id.CircleDetailsActivitys), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

            }
        });

        mCircleDetailsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CircleDetailsActivity.this, PostPersonageCenterActivity.class);
                intent.putExtra("id", userId);
                startActivity(intent);
            }
        });
        mCircleDetailsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CircleDetailsActivity.this, PostPersonageCenterActivity.class);
                intent.putExtra("id", userId);
                startActivity(intent);
            }
        });


        mCircleDetailsSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLzouzhu) {
                    mCircleDetailsEditText.setHint("回复楼主 ：");
                    mCircleDetailsSize.setText("已有" + commentCount + "条回复");
                    pid = "0";
                    fid = "0";
                }
            }
        });
        mCircleDetailsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mCircleDetailsSize.setVisibility(View.GONE);
                mCircleDetailsCollect.setVisibility(View.GONE);
                mCircleDetailsFaSong.setVisibility(View.VISIBLE);
                if (mCircleDetailsEditText.getText().toString().equals("")) {
//                    mCircleDetailsSize.setVisibility(View.VISIBLE);
                    mCircleDetailsCollect.setVisibility(View.VISIBLE);
                    mCircleDetailsFaSong.setVisibility(View.GONE);
                }
//                if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
//                    Toast.makeText(CircleDetailsActivity.this, "显示", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(CircleDetailsActivity.this, "没显示", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mCircleDetailsRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mCircleDetailsFaSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> hashMap = new HashMap<>();
//                hashMap.put("itfaceId", "052");
                hashMap.put("itfaceId", "134");
                hashMap.put("forumId", id);
                hashMap.put("token", SPUtils.get(CircleDetailsActivity.this, "token", ""));
                hashMap.put("content", mCircleDetailsEditText.getText().toString().trim());
                hashMap.put("pid", pid);
                hashMap.put("fid", fid);
                JSONObject jsonObject = new JSONObject(hashMap);

                OkGo.post(UrlContent.URL).tag(this)
                        .upJson(jsonObject.toString())
                        .connTimeOut(10_000)
                        .execute(new CustomCallBackNoLoading<CircleDetailsFaSongBeans>(CircleDetailsActivity.this) {
                            @Override
                            public void onSuccess(CircleDetailsFaSongBeans mCircleDetailsFaSongBeans, Call call, Response response) {
                                if (mCircleDetailsFaSongBeans.getResultCode() == 1) {
                                    mCircleDetailsEditText.setText("");
//                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                                    //判断隐藏软键盘是否弹出
//                                    if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
//                                        //隐藏软键盘
//                                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//                                    }
//                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//                                    hideSoftKeyboard(CircleDetailsActivity.this);

                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(CircleDetailsActivity.this.getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘

                                    mCircleDetailsEditText.setHint("回复楼主 ：");
                                    mCircleDetailsSize.setText("已有" + commentCount + "条回复");
                                    pid = "0";
                                    fid = "0";
                                    initData();
                                    if (mCircleDetailsFaSongBeans.getData().getGetIntegral() != 0) {
                                        Toast toast = Toast.makeText(CircleDetailsActivity.this, "回帖成功积分+" + mCircleDetailsFaSongBeans.getData().getGetIntegral(), Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                } else {
                                    App.ErrorToken(mCircleDetailsFaSongBeans.getResultCode(), mCircleDetailsFaSongBeans.getMsg());

                                }
                            }
                        });
            }
        });
        mCircleDetailsCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("itfaceId", "054");
                    hashMap.put("token", SPUtils.get(CircleDetailsActivity.this, "token", ""));
                    hashMap.put("forumId", id);
                    JSONObject jsonObject = new JSONObject(hashMap);

                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<CircleFragmentBeans>(CircleDetailsActivity.this) {
                                @Override
                                public void onSuccess(CircleFragmentBeans mCircleFragmentBeans, Call call, Response response) {
                                    if (mCircleFragmentBeans.getResultCode() == 1) {
                                        isCheck = false;
                                        mCircleDetailsCollect.setBackground(getResources().getDrawable(R.mipmap.commentcollectioned));
                                        Toast toast = Toast.makeText(CircleDetailsActivity.this, "亲，收藏成功啦~", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    } else {
                                        App.ErrorToken(mCircleFragmentBeans.getResultCode(), mCircleFragmentBeans.getMsg());

                                    }
                                }
                            });
                } else {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("itfaceId", "055");
                    hashMap.put("token", SPUtils.get(CircleDetailsActivity.this, "token", ""));
                    hashMap.put("forumId", id);
                    JSONObject jsonObject = new JSONObject(hashMap);

                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<CircleFragmentBeans>(CircleDetailsActivity.this) {
                                @Override
                                public void onSuccess(CircleFragmentBeans mCircleFragmentBeans, Call call, Response response) {
                                    if (mCircleFragmentBeans.getResultCode() == 1) {
                                        isCheck = true;
                                        mCircleDetailsCollect.setBackground(getResources().getDrawable(R.mipmap.commentcolletion));
                                        Toast toast = Toast.makeText(CircleDetailsActivity.this, "亲，取消成功啦~", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    } else {
                                        App.ErrorToken(mCircleFragmentBeans.getResultCode(), mCircleFragmentBeans.getMsg());

                                    }
                                }
                            });
                }
            }
        });
    }

    //为弹出窗口实现监听类
    public View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.chioce_camera:
                    Intent intent = new Intent(CircleDetailsActivity.this, JuBaoActivity.class);
                    intent.putExtra("name", name2);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
            mPopWindow.dismiss();
        }
    };

    public void CircleDetailsActivity_Bank(View view) {
//        if (isCheck) {
//            //取消了收藏传1
//            collectFlag = 2;
//        } else {
//            collectFlag = 1;
//        }
//
//        Intent intent = new Intent(CircleDetailsActivity.this, ReceiverMainActivity.class);
//        intent.putExtra("circleflag", collectFlag);
//        startActivity(intent);
        finish();

    }

    @Override
    public void setCircle(String username, int pids, int fids) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        mCircleDetailsEditText.setHint("回复" + username + " :");
        pid = String.valueOf(pids);
        fid = String.valueOf(fids);
        isLzouzhu = true;
        mCircleDetailsSize.setText("回复楼主");

    }


    @Override
    public void setCircleisShow(boolean isShowRefresh) {
        isShowRefreshs = true;
        if (isShowRefresh) {
            initData();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
//        if (isCheck) {
//            //取消了收藏传1
//            collectFlag = 2;
//        } else {
//            collectFlag = 1;
//        }
//
//        Intent intent = new Intent(CircleDetailsActivity.this, ReceiverMainActivity.class);
//        intent.putExtra("circleflag", collectFlag);
//        startActivity(intent);
        finish();
    }
}
