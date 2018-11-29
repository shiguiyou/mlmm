package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.GrowUpAppraisalSavetBeans;
import com.wanquan.mlmmx.mlmm.beans.GrowUpAppraisalStartBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.SettingBabyMessageBeans;
import com.wanquan.mlmmx.mlmm.fragment.HomeFragment;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.ximalaya.ting.android.opensdk.httputil.util.freeflow.IFreeFlowBase;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：开始成长测评
 * 作者：薛昌峰
 * 时间：2018.09.11
 */
public class GrowUpAppraisalStartActivity extends BaseActivity implements GestureDetector.OnGestureListener {
    private ViewFlipper mGrowUpAppraisalStartViewFlipper;
    private ImageView mGrowUpAppraisalStartBank;
    private TextView mGrowUpAppraisalStartTV1;
    private TextView mGrowUpAppraisalStartTV2;
    private TextView mGrowUpAppraisalStartTV3;
    private TextView mGrowUpAppraisalStartTV4;
    private ProgressBar mGrowUpAppraisalStartProgressBarOne;
    // 定义手势检测实例
    private GestureDetector detector;
    // 定义一个动画数组，用于为ViewFlipper指定切换动画效果
    private Animation[] animations = new Animation[4];
    // 定义手势动作亮点之间的最小距离
    private int FLIP_DISTANCE_LIFT;
    private int FLIP_DISTANCE_Right;
    private int size = 10;
    private String month;
    private String name;
    private int flag = 0;//记住到第几题了
    private int oneResult;
    private int twoResult;
    private int threeResult;
    private boolean flags = true;//没网时禁止滑动
    private int flagdingwei = 0;//往回滑动时是否允许在往右滑动

//    13956306163


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(GrowUpAppraisalStartActivity.this, R.color.black);

        month = getIntent().getStringExtra("month");
        name = getIntent().getStringExtra("name");

        Log.e("month1", month);
        Log.e("babyid", String.valueOf(SPUtils.get(GrowUpAppraisalStartActivity.this, "babyId", "")));

        // 创建手势检测器
        detector = new GestureDetector(this, this);

        // 为ViewFlipper添加8个ImageView组件
//        mGrowUpAppraisalStartViewFlipper.addView(addImageView(R.mipmap.img3));
//        mGrowUpAppraisalStartViewFlipper.addView(addImageView(R.mipmap.img3));
//        mGrowUpAppraisalStartViewFlipper.addView(addImageView(R.mipmap.img3));
//        mGrowUpAppraisalStartViewFlipper.addView(addImageView(R.mipmap.img3));
        // 初始化Animation数组
        animations[0] = AnimationUtils.loadAnimation(this, R.anim.left_in);
        animations[1] = AnimationUtils.loadAnimation(this, R.anim.left_out);
        animations[2] = AnimationUtils.loadAnimation(this, R.anim.right_in);
        animations[3] = AnimationUtils.loadAnimation(this, R.anim.right_out);
        initData(-1, 0, false);
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_grow_up_appraisal_start;
    }

    @Override
    public void initView() throws Exception {
        mGrowUpAppraisalStartViewFlipper = (ViewFlipper) findViewById(R.id.GrowUpAppraisalStart_ViewFlipper);
        mGrowUpAppraisalStartBank = (ImageView) findViewById(R.id.GrowUpAppraisalStart_Bank);
        mGrowUpAppraisalStartTV1 = (TextView) findViewById(R.id.GrowUpAppraisalStart_TV1);
        mGrowUpAppraisalStartTV2 = (TextView) findViewById(R.id.GrowUpAppraisalStart_TV2);
        mGrowUpAppraisalStartTV3 = (TextView) findViewById(R.id.GrowUpAppraisalStart_TV3);
        mGrowUpAppraisalStartTV4 = (TextView) findViewById(R.id.GrowUpAppraisalStart_TV4);
        mGrowUpAppraisalStartProgressBarOne = (ProgressBar) findViewById(R.id.GrowUpAppraisalStart_ProgressBar_One);
    }

    // 定义添加ImageView的工具方法
    private View addImageView(int resId) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(resId);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        return imageView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 将该Activity上的触碰事件交给GestureDetector处理
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // 如果第一个触点事件的X坐标大于第二个触点事件的X坐标超过FLIP_DISTANCE
        // 也就是手势从右向左滑
        Log.e("ooooooosize", String.valueOf(size));

        if (flagdingwei == -1) {
            flagdingwei = flagdingwei + 1;
        }

        Log.e("ooooooo11", String.valueOf(flagdingwei));
        Log.e("ooooooo22", String.valueOf(flag));


        if (flagdingwei == flag) {
            FLIP_DISTANCE_LIFT = 5000;
        } else {
            FLIP_DISTANCE_LIFT = 50;
        }

        if (10 <= size && size <= 120) {
            if (e1.getX() - e2.getX() > FLIP_DISTANCE_LIFT) {
                // 为flipper设置切换的动画效果
                mGrowUpAppraisalStartViewFlipper.setInAnimation(animations[0]);
                mGrowUpAppraisalStartViewFlipper.setOutAnimation(animations[1]);
                mGrowUpAppraisalStartViewFlipper.showPrevious();

                if (size < 120) {
//                    FLIP_DISTANCE_LIFT = 50;
//                    FLIP_DISTANCE_Right = 50;
                    size = size + 10;
                    Log.e("dsdada", String.valueOf(size) + "右向左滑");
                    mGrowUpAppraisalStartProgressBarOne.setProgress(size);
                    Log.e("dsdada", String.valueOf(flag));
                    if (flag < 11) {
                        flag++;
                        initData(flag, 0, false);
                    }
                }
                if (size == 120) {
                    FLIP_DISTANCE_LIFT = 5000;
                }
                return true;
            }
            // 如果第二个触点事件的X坐标大于第一个触点事件的X坐标超过FLIP_DISTANCE
            // 也就是手势从右向左滑
            else if (e2.getX() - e1.getX() > FLIP_DISTANCE_Right) {
                // 为flipper设置切换的动画效果
                mGrowUpAppraisalStartViewFlipper.setInAnimation(animations[2]);
                mGrowUpAppraisalStartViewFlipper.setOutAnimation(animations[3]);
                mGrowUpAppraisalStartViewFlipper.showNext();
                if (size > 10) {
//                    FLIP_DISTANCE_LIFT = 50;
                    FLIP_DISTANCE_Right = 50;
                    size = size - 10;
                    Log.e("dsdada", String.valueOf(size) + "左向右滑");
                    mGrowUpAppraisalStartProgressBarOne.setProgress(size);

                    if (flag >= 0) {
                        flag--;
                        initData(flag, 0, true);
                    }
                }
                if (size == 10) {
                    FLIP_DISTANCE_Right = 5000;
                }
                return true;
            }
        }
        return false;
    }

    private void initData(final int flag, int size, boolean lift) {
        if (!lift) {
            if (flag > flagdingwei) {
                flagdingwei = flag;
            }
        }
        Log.e("返回参数", flag + "----" + size);
        if (flag == 0 || flag == 1 || flag == 2 || flag == 3 || flag == 4) {
            if (size == 1) {
                oneResult++;
            }
        } else if (flag == 5 || flag == 6 || flag == 7 || flag == 8) {
            if (size == 1) {
                twoResult++;
            }
        } else if (flag == 9 || flag == 10 || flag == 11 || flag == 12) {
            if (size == 1) {
                threeResult++;
            }
        }
        Log.e("返回参数", "A:" + oneResult + "B:" + twoResult + "C:" + threeResult);


        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "138");
        hashMap.put("token", SPUtils.get(GrowUpAppraisalStartActivity.this, "token", ""));
        hashMap.put("age", month);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<GrowUpAppraisalStartBeans>(GrowUpAppraisalStartActivity.this) {
                    @Override
                    public void onSuccess(GrowUpAppraisalStartBeans mGrowUpAppraisalStartBeans, Call call, Response response) {
                        if (mGrowUpAppraisalStartBeans.getResultCode() == 1) {
                            if (flag == -1) {
                                mGrowUpAppraisalStartTV1.setText(mGrowUpAppraisalStartBeans.getData().get(0).getQuestion());
                            } else {
                                if (flag != 12) {
                                    mGrowUpAppraisalStartTV1.setText(mGrowUpAppraisalStartBeans.getData().get(flag).getQuestion());
                                }
                            }
                        } else {
                            App.ErrorToken(mGrowUpAppraisalStartBeans.getResultCode(), mGrowUpAppraisalStartBeans.getMsg());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        flags = false;
                    }
                });
    }

    private void initListeners() {
        mGrowUpAppraisalStartBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog alert;
                alert = new AlertDialog.Builder(GrowUpAppraisalStartActivity.this, R.style.AlertDialogs).create();
                alert.show();
                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.delete_dialogsss);
                TextView cart_delete_title = alert.getWindow().findViewById(R.id.cart_delete_title);
                TextView cart_delete_content = alert.getWindow().findViewById(R.id.cart_delete_content);
                TextView cart_delete_cancle = alert.getWindow().findViewById(R.id.cart_delete_cancle);
                TextView cart_delete_confirm = alert.getWindow().findViewById(R.id.cart_delete_confirm);

                cart_delete_title.setText("提示");
                cart_delete_content.setText("测评还没做完，真的要退出吗？");
                cart_delete_cancle.setText("退出");
                cart_delete_confirm.setText("继续测评");
                cart_delete_cancle.setTextColor(getResources().getColor(R.color.dsdsdds));
                cart_delete_confirm.setTextColor(getResources().getColor(R.color.dsdsdds));
                //取消
                alert.getWindow().findViewById(R.id.cart_delete_cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        alert.dismiss();
                        return;
                    }
                });
                alert.getWindow().findViewById(R.id.cart_delete_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                        return;
                    }
                });
            }
        });
        mGrowUpAppraisalStartTV3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flags) {
                    mGrowUpAppraisalStartViewFlipper.setInAnimation(animations[0]);
                    mGrowUpAppraisalStartViewFlipper.setOutAnimation(animations[1]);
                    mGrowUpAppraisalStartViewFlipper.showPrevious();
//                FLIP_DISTANCE_LIFT = 50;
                    FLIP_DISTANCE_Right = 50;
                    if (flag < 13) {
                        flag++;
                        if (flag != 13) {
                            initData(flag, 1, false);
                        }
                        size = size + 10;
                        mGrowUpAppraisalStartProgressBarOne.setProgress(size);
                    }
                    if (flag == 12) {
                        //提交审核结果
                        initSave();
                        startActivity(new Intent(GrowUpAppraisalStartActivity.this, GrowUpAppraisalResultActivity.class).putExtra("month", month).putExtra("name", name));
//                        finish();
                    }
                }
            }
        });
        mGrowUpAppraisalStartTV4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flags) {
                    mGrowUpAppraisalStartViewFlipper.setInAnimation(animations[0]);
                    mGrowUpAppraisalStartViewFlipper.setOutAnimation(animations[1]);
                    mGrowUpAppraisalStartViewFlipper.showPrevious();
//                FLIP_DISTANCE_LIFT = 50;
                    FLIP_DISTANCE_Right = 50;
                    if (flag < 12) {
                        flag++;
                        if (flag != 12) {
                            initData(flag, 2, false);
                        }
                        size = size + 10;
                        mGrowUpAppraisalStartProgressBarOne.setProgress(size);
                    }
                    if (flag == 12) {
                        //提交审核结果
                        initSave();
                        startActivity(new Intent(GrowUpAppraisalStartActivity.this, GrowUpAppraisalResultActivity.class).putExtra("month", month).putExtra("name", name));
//                        finish();
                    }
                }
            }
        });
    }

    private void initSave() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "137");
        hashMap.put("token", SPUtils.get(GrowUpAppraisalStartActivity.this, "token", ""));
        hashMap.put("babyId", SPUtils.get(GrowUpAppraisalStartActivity.this, "babyId", ""));
        hashMap.put("sense", oneResult);
        hashMap.put("language", twoResult);
        hashMap.put("emotion", threeResult);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<GrowUpAppraisalSavetBeans>(GrowUpAppraisalStartActivity.this) {
                    @Override
                    public void onSuccess(GrowUpAppraisalSavetBeans mGrowUpAppraisalSavetBeans, Call call, Response response) {
                        if (mGrowUpAppraisalSavetBeans.getResultCode() == 1) {
                            Toast.makeText(GrowUpAppraisalStartActivity.this, mGrowUpAppraisalSavetBeans.getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            App.ErrorToken(mGrowUpAppraisalSavetBeans.getResultCode(), mGrowUpAppraisalSavetBeans.getMsg());
                        }
                    }
                });
    }
}
