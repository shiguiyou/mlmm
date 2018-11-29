package com.wanquan.mlmmx.mlmm.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.BabyRoomAdapter;
import com.wanquan.mlmmx.mlmm.adapter.BabyRoomAdapters;
import com.wanquan.mlmmx.mlmm.adapter.ChezaiAdapters;
import com.wanquan.mlmmx.mlmm.beans.BabyNumBeans;
import com.wanquan.mlmmx.mlmm.beans.BabyRoomDialogGridViewBeans;
import com.wanquan.mlmmx.mlmm.beans.BabyRoomGridViewBeans;
import com.wanquan.mlmmx.mlmm.beans.BabyRoomkzBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：车载净化器
 * 作者：薛昌峰
 * 时间：2018.08.14
 */
public class CheZaiActivity extends BaseActivity {
    private RelativeLayout mCheZaiActivityRLBG;
    private ImageView mCheZaiActivityImageView;
    private TextView mCheZaiActivitySize;
    private ImageView mCheZaiActivityImg;
    private TextView mCheZaiActivityText;
    private LinearLayout mCheZaiLL1;
    private ImageView mCheZaiImg1;
    private TextView mCheZaiTv1;
    private LinearLayout mCheZaiLL2;
    private ImageView mCheZaiImg2;
    private TextView mCheZaiTv2;
    private LinearLayout mCheZaiLL3;
    private ImageView mCheZaiImg3;
    private TextView mCheZaiTv3;

    private int id;
    private List<BabyRoomGridViewBeans.DataBean> mList = new ArrayList<>();
    private List<BabyRoomGridViewBeans.DataBean> mList2 = new ArrayList<>();
    private List<BabyRoomDialogGridViewBeans.DataBean.CfsBean> mList3 = new ArrayList<>();

    private BabyRoomAdapter mBabyRoomAdapter;
    private String type;
    private Animation animation;//补间动画
    private boolean status;
    private AlertDialog alert2;
    private String code;
    private String temp = "0";
    private String humi = "0";
    private String pm25 = "0";
    private String ozone = "0";
    private String pm03 = "0";
    private String img = "";
    private String name3;
    private String name2;
    private String name1;

    private boolean run = true;
    private final Handler handler = new Handler();
    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (run) {
                handler.postDelayed(this, 5000);
                initData();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(CheZaiActivity.this, R.color.black);

        id = getIntent().getIntExtra("id", 0);
        type = getIntent().getStringExtra("type");
        code = getIntent().getStringExtra("code");

        task.run();
        initData();
        initListeners();

//        mBabyRoomAdapter = new BabyRoomAdapter(this, mList2);
//        mCheZaiActivityGridView.setAdapter(mBabyRoomAdapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_che_zai;
    }

    @Override
    public void initView() throws Exception {

        mCheZaiActivityRLBG = (RelativeLayout) findViewById(R.id.CheZaiActivity_RL_BG);
        mCheZaiActivityImageView = (ImageView) findViewById(R.id.CheZaiActivity_ImageView);
        mCheZaiActivitySize = (TextView) findViewById(R.id.CheZaiActivity_Size);
        mCheZaiActivityImg = (ImageView) findViewById(R.id.CheZaiActivity_Img);
        mCheZaiActivityText = (TextView) findViewById(R.id.CheZaiActivity_Text);
        mCheZaiLL1 = (LinearLayout) findViewById(R.id.CheZai_LL1);
        mCheZaiImg1 = (ImageView) findViewById(R.id.CheZai_Img1);
        mCheZaiTv1 = (TextView) findViewById(R.id.CheZai_tv1);
        mCheZaiLL2 = (LinearLayout) findViewById(R.id.CheZai_LL2);
        mCheZaiImg2 = (ImageView) findViewById(R.id.CheZai_Img2);
        mCheZaiTv2 = (TextView) findViewById(R.id.CheZai_tv2);
        mCheZaiLL3 = (LinearLayout) findViewById(R.id.CheZai_LL3);
        mCheZaiImg3 = (ImageView) findViewById(R.id.CheZai_Img3);
        mCheZaiTv3 = (TextView) findViewById(R.id.CheZai_tv3);
    }

    private void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "004");
        hashMap.put("token", SPUtils.get(this, "token", ""));
        hashMap.put("deviceId", id);
        hashMap.put("deviceCode", code);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<BabyNumBeans>(this) {
                    @Override
                    public void onSuccess(BabyNumBeans mBabyNumBeans, Call call, Response response) {
                        if (mBabyNumBeans.getResultCode() == 1) {
                            temp = mBabyNumBeans.getData().getTemp();
                            humi = mBabyNumBeans.getData().getHumi();
                            pm25 = mBabyNumBeans.getData().getPm25();
                            ozone = mBabyNumBeans.getData().getOzone();
                            pm03 = mBabyNumBeans.getData().getPm03();
                            mCheZaiActivitySize.setText(mBabyNumBeans.getData().getPm25() + "");

                            if (Integer.parseInt(mBabyNumBeans.getData().getPm25()) <= 35 && Integer.parseInt(mBabyNumBeans.getData().getPm25()) > 10) {
                                mCheZaiActivityImg.setBackground(getResources().getDrawable(R.mipmap.execellent));
                            } else if (Integer.parseInt(mBabyNumBeans.getData().getPm25()) > 35 && Integer.parseInt(mBabyNumBeans.getData().getPm25()) <= 75) {
                                mCheZaiActivityImg.setBackground(getResources().getDrawable(R.mipmap.better));
                            } else if (Integer.parseInt(mBabyNumBeans.getData().getPm25()) > 75) {
                                mCheZaiActivityImg.setBackground(getResources().getDrawable(R.mipmap.bad));
                            } else if (Integer.parseInt(mBabyNumBeans.getData().getPm25()) <= 10) {
                                mCheZaiActivityImg.setBackground(getResources().getDrawable(R.mipmap.execellent));
                            }
                        } else {
                            App.ErrorToken(mBabyNumBeans.getResultCode(), mBabyNumBeans.getMsg());
                        }
                    }
                });

        Log.e("dsdsdsd", String.valueOf(id));
        Log.e("dsdsdsd", type);

        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("itfaceId", "020");
        hashMap2.put("token", SPUtils.get(this, "token", ""));
        hashMap2.put("deviceId", id);
        hashMap2.put("deviceType", type);
        JSONObject jsonObject2 = new JSONObject(hashMap2);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject2.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<BabyRoomGridViewBeans>(this) {
                    @Override
                    public void onSuccess(BabyRoomGridViewBeans mBabyRoomGridViewBeans, Call call, Response response) {
                        if (mBabyRoomGridViewBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mBabyRoomGridViewBeans.getData());

                            Glide.with(CheZaiActivity.this).load(mList.get(0).getIco()).into(mCheZaiImg1);
                            Glide.with(CheZaiActivity.this).load(mList.get(1).getIco()).into(mCheZaiImg2);
                            Glide.with(CheZaiActivity.this).load(mList.get(2).getCfs().get(1).getIco()).into(mCheZaiImg3);
                            mCheZaiTv1.setText(mList.get(0).getName());
                            mCheZaiTv2.setText(mList.get(1).getName());
                            mCheZaiTv3.setText("手动模式");

                        } else {
                            App.ErrorToken(mBabyRoomGridViewBeans.getResultCode(), mBabyRoomGridViewBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
        animation = AnimationUtils.loadAnimation(this, R.anim.rotate_upload);
        LinearInterpolator lir = new LinearInterpolator();
        animation.setInterpolator(lir);
        App.dialog_animation = animation;
        animation.start();
        mCheZaiActivityImageView.startAnimation(animation);
        mCheZaiLL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initCheck(1);
            }
        });
        mCheZaiLL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initCheck(2);
            }
        });
        mCheZaiLL3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initCheck(3);
            }
        });


//        mCheZaiActivityGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
////                status = mList.get(0).getCfs().get(0).isStatus();
//                if (!mList.get(i).isDisable()) {
//                    final String OutFunctionCode = mList.get(i).getFunctionCode();
//                    if (mList.get(i).getFunctionCode().equals("81")) {//开关
//                        if (mList.get(i).getCfs().get(i).isStatus()) {
//                            initNetwork1(OutFunctionCode, mList.get(i).getCfs().get(1).getFunctionCode());
//                        } else {
//                            initNetwork1(OutFunctionCode, mList.get(i).getCfs().get(0).getFunctionCode());
//                        }
//                    } else if (mList.get(i).getFunctionCode().equals("92")) {//解锁
//                        if (status) {
//                            if (mList.get(i).getCfs().get(0).isStatus()) {
//                                initNetwork1(OutFunctionCode, mList.get(i).getCfs().get(1).getFunctionCode());
//                            } else {
//                                initNetwork1(OutFunctionCode, mList.get(i).getCfs().get(0).getFunctionCode());
//                            }
//                        } else {
//                            Toast.makeText(CheZaiActivity.this, "设备未开启，该按钮不能操作哦", Toast.LENGTH_SHORT).show();
//                        }
//
//                    } else if (mList.get(i).getFunctionCode().equals("82")) {//负离子
//                        if (status) {
//                            if (mList.get(i).getCfs().get(0).isStatus()) {
//                                initNetwork1(OutFunctionCode, mList.get(i).getCfs().get(1).getFunctionCode());
//                            } else {
//                                initNetwork1(OutFunctionCode, mList.get(i).getCfs().get(0).getFunctionCode());
//                            }
//                        } else {
//                            Toast.makeText(CheZaiActivity.this, "设备未开启，该按钮不能操作哦", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        if (status) {
//                            final BabyRoomAdapters mBabyRoomAdapters;
//                            alert2 = new AlertDialog.Builder(CheZaiActivity.this).create();
//                            alert2.show();
//                            //加载自定义dialog
//                            alert2.getWindow().setContentView(R.layout.baby_dialog);
//                            alert2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                            GridView mGridView = (GridView) alert2.getWindow().findViewById(R.id.BabyRoom_GridView2);
//
//                            mBabyRoomAdapters = new BabyRoomAdapters(CheZaiActivity.this, mList3);
//                            mGridView.setAdapter(mBabyRoomAdapters);
//
//                            HashMap<String, Object> hashMap = new HashMap<>();
//                            hashMap.put("itfaceId", "020");
//                            hashMap.put("token", SPUtils.get(CheZaiActivity.this, "token", ""));
//                            hashMap.put("deviceId", id);
//                            hashMap.put("deviceType", type);
//                            JSONObject jsonObject = new JSONObject(hashMap);
//
//                            OkGo.post(UrlContent.URL).tag(this)
//                                    .upJson(jsonObject.toString())
//                                    .connTimeOut(10_000)
//                                    .execute(new CustomCallBackNoLoading<BabyRoomDialogGridViewBeans>(CheZaiActivity.this) {
//                                        @Override
//                                        public void onSuccess(BabyRoomDialogGridViewBeans mBabyRoomDialogGridViewBeans, Call call, Response response) {
//                                            if (mBabyRoomDialogGridViewBeans.getResultCode() == 1) {
//                                                mList3.clear();
//                                                mList3.addAll(mBabyRoomDialogGridViewBeans.getData().get(i).getCfs());
//                                                mBabyRoomAdapters.notifyDataSetChanged();
//                                            } else {
//                                                App.ErrorToken(mBabyRoomDialogGridViewBeans.getResultCode(), mBabyRoomDialogGridViewBeans.getMsg());
//                                            }
//                                        }
//                                    });
//
//                            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                    if (!mList3.get(i).isDisable()) {
//                                        initNetwork1(OutFunctionCode, mList3.get(i).getFunctionCode());
//                                    } else {
//                                        Toast.makeText(CheZaiActivity.this, "当前模式下按钮不可点击", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                        } else {
//                            Toast.makeText(CheZaiActivity.this, "设备未开启，该按钮不能操作哦", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                } else {
//                    Toast.makeText(CheZaiActivity.this, "当前模式下按钮不可点击", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

    private void initCheck(int size) {
        if (size == 1) {
            final String OutFunctionCode = mList.get(0).getFunctionCode();
//            if (!mList3.get(0).isDisable()) {
            if ("关".equals(mList.get(0).getName())) {
                initNetwork1(OutFunctionCode, mList.get(0).getCfs().get(0).getFunctionCode());
            } else {
                initNetwork1(OutFunctionCode, mList.get(0).getCfs().get(1).getFunctionCode());
            }
//            } else {
//                Toast.makeText(CheZaiActivity.this, "当前模式下按钮不可点击", Toast.LENGTH_SHORT).show();
//            }
        } else if (size == 2) {
//            if (!mList.get(1).isDisable()) {
                final String OutFunctionCode = mList.get(1).getFunctionCode();
                if ("关".equals(mList.get(0).getName())) {
                    Toast.makeText(CheZaiActivity.this, "设备未开启，该按钮不能操作哦", Toast.LENGTH_SHORT).show();
                } else {
                    final ChezaiAdapters mChezaiAdapters;
                    alert2 = new AlertDialog.Builder(CheZaiActivity.this).create();
                    alert2.show();
                    //加载自定义dialog
                    alert2.getWindow().setContentView(R.layout.baby_dialog);
                    alert2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    final GridView mGridView = (GridView) alert2.getWindow().findViewById(R.id.BabyRoom_GridView2);

                    mChezaiAdapters = new ChezaiAdapters(CheZaiActivity.this, mList3);
                    mGridView.setAdapter(mChezaiAdapters);

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("itfaceId", "020");
                    hashMap.put("token", SPUtils.get(CheZaiActivity.this, "token", ""));
                    hashMap.put("deviceId", id);
                    hashMap.put("deviceType", type);
                    JSONObject jsonObject = new JSONObject(hashMap);

                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<BabyRoomDialogGridViewBeans>(CheZaiActivity.this) {
                                @Override
                                public void onSuccess(BabyRoomDialogGridViewBeans mBabyRoomDialogGridViewBeans, Call call, Response response) {
                                    if (mBabyRoomDialogGridViewBeans.getResultCode() == 1) {
                                        mList3.clear();
                                        mList3.addAll(mBabyRoomDialogGridViewBeans.getData().get(1).getCfs());
                                        mChezaiAdapters.notifyDataSetChanged();

                                        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                initNetwork1(OutFunctionCode, mList3.get(i+1).getFunctionCode());
                                            }
                                        });
                                    } else {
                                        App.ErrorToken(mBabyRoomDialogGridViewBeans.getResultCode(), mBabyRoomDialogGridViewBeans.getMsg());
                                    }
                                }
                            });
                }
//            } else {
//                Toast.makeText(CheZaiActivity.this, "当前模式下按钮不可点击", Toast.LENGTH_SHORT).show();
//            }
        }
    }

    private void initNetwork1(String functionCode, String functionCode2) {
        Log.e("rrrrrrrrr", code + "---------" + functionCode + "---------" + functionCode2);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "005");
        hashMap.put("deviceCode", code);
        hashMap.put("ctlType", functionCode);
        hashMap.put("token", SPUtils.get(CheZaiActivity.this, "token", ""));
        hashMap.put("ctlStatus", functionCode2);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<BabyRoomkzBeans>(CheZaiActivity.this) {
                    @Override
                    public void onSuccess(BabyRoomkzBeans mBabyRoomkzBeans, Call call, Response response) {
                        if (mBabyRoomkzBeans.getResultCode() == 1) {
                            Toast.makeText(CheZaiActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                            initData();
                            if (alert2 != null) {
                                alert2.dismiss();
                            }
                        } else {
                            App.ErrorToken(mBabyRoomkzBeans.getResultCode(), mBabyRoomkzBeans.getMsg());
                        }
                    }
                });
    }

    public void CheZaiActivity_Bank(View view) {
        finish();
    }
}
