package com.wanquan.mlmmx.mlmm.xmly.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.BaseActivity;
import com.wanquan.mlmmx.mlmm.activity.SettingActivity;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.PlayDeletesCollectBeans;
import com.wanquan.mlmmx.mlmm.beans.SettingBabyMessageBeans;
import com.wanquan.mlmmx.mlmm.fragment.HomeFragment;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.DensityUtil;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.xmly.adapter.PlayListAdapter;
import com.wanquan.mlmmx.mlmm.xmly.beans.PlayAddCollectBeans;
import com.wanquan.mlmmx.mlmm.xmly.beans.PlayDeleteCollectBeans;
import com.wanquan.mlmmx.mlmm.xmly.beans.PlaySaveBeans;
import com.wanquan.mlmmx.mlmm.xmly.utils.FastBlurUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：播放界面
 * 作者：薛昌峰
 * 时间：2018.01.09
 */
public class PlayActivity extends BaseActivity {
    private Dialog dialog2;
    private ImageView mPlayActivityBG;
    private TextView mPlayActivityTitle;
    private ImageView mPlayActivityImageView;
    private ImageView mPlayActivityCollect;
    private TextView mPlayActivityPlayed;
    private SeekBar mPlayActivitySeek;
    private TextView mPlayActivityDuration;
    private ImageView mPlayActivityMode;
    private ImageView mPlayActivityPre;
    private ImageView mPlayActivityPlay;
    public static ImageView mPlayActivityNext;
    private ImageView mPlayActivityPlaylist;
    public static String img;
    public static String title;
    private String NewMp3;
    private String mp3 = "";
    private boolean isSeekBarChanging;//互斥变量，防止进度条与定时器冲突。
    private int currentPosition;//当前音乐播放的进度
    private boolean isCellPlay;/*在挂断电话的时候，用于判断是否为是来电时中断*/
    private boolean isPlay = false;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("mm:ss");
    private int current = 0;
    private int max = 0;
    private boolean isStart = true;
    public static int position;
    private ImageView mPlayActivitySelect;
    private ImageView mPlayActivityTime;
    private TextView mPlayActivityTimeName;
    private String isSelect = "2";
    private String isXunhuan = "1";
    private boolean isCollect = true;
    private int select_time;
    private int i;
    public static String playUrl64;
    private String flag;
    public static String dataId;
    public static String id;
    private String newMp3s;
    private int collectId;
    private int podition;
    private int positions = 0;
    private String firstTime = "0";
    private String sendTime = "0";

    Handler handler2 = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -8) {
                currentPosition = 0;
                App.mediaPlayer2.pause();
                mPlayActivityTimeName.setVisibility(View.GONE);
                isPlay = true;
                mPlayActivityPlay.setImageResource(R.drawable.play_rdi_btn_play);
            }
        }
    };
    private PlayListAdapter mPlayListAdapter;
    private ListView mPlayListView;
    private PopupWindow popupWindow = null;//更多弹出框

    @Override
    protected void onResume() {
        super.onResume();
        if ("1".equals(SPUtils.get(PlayActivity.this, "isSelect", ""))) {
            mPlayActivitySelect.setImageDrawable(getResources().getDrawable(R.mipmap.play_orderplay));
            isXunhuan = "1";
        } else if ("2".equals(SPUtils.get(PlayActivity.this, "isSelect", ""))) {
            mPlayActivitySelect.setImageDrawable(getResources().getDrawable(R.mipmap.play_random));
            isXunhuan = "2";
        } else if ("3".equals(SPUtils.get(PlayActivity.this, "isSelect", ""))) {
            mPlayActivitySelect.setImageDrawable(getResources().getDrawable(R.mipmap.play_singlecircle));
            isXunhuan = "3";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(PlayActivity.this, R.color.tops);

        img = getIntent().getStringExtra("img");
        title = getIntent().getStringExtra("title");
        flag = getIntent().getStringExtra("flag");
        dataId = getIntent().getStringExtra("dataId");
        id = getIntent().getStringExtra("id");
        newMp3s = getIntent().getStringExtra("mp3");
        if (newMp3s != null) {
            NewMp3 = newMp3s;
            playUrl64 = newMp3s;
        }
        position = getIntent().getIntExtra("position", 0);
//        Log.e("positionddd先进来的position", String.valueOf(position));
        initCollect(id);
        initMoHu(img);
        initData();
        initListeners();
        if (SPUtils.get(PlayActivity.this, "isDown", "").equals("2")) {
            Log.e("url", playUrl64);
            Log.e("url", String.valueOf(SPUtils.get(this, "mp3Uri", "")));
//            if (!playUrl64.equals(SPUtils.get(this, "mp3Uri", ""))) {
            App.mediaPlayer2.stop();
            App.mediaPlayer2.reset();
            play();//进来播放
//            } else {
//                mPlayActivitySeek.setMax(App.mediaPlayer2.getDuration());//第二次进入时定位进度条位置
//                task.run();
//                App.timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        if (!isSeekBarChanging) {
//                            mPlayActivitySeek.setProgress(App.mediaPlayer2.getCurrentPosition());
//                        }
//                    }
//                }, 0, 50);
//            }
        } else if (SPUtils.get(PlayActivity.this, "isDown", "").equals("1")) {
//            App.mediaPlayer2.seekTo(currentPosition);//重置播放器
            mPlayActivitySeek.setMax(App.mediaPlayer2.getDuration());//第二次进入时定位进度条位置
            task.run();
            App.timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!isSeekBarChanging) {
                        mPlayActivitySeek.setProgress(App.mediaPlayer2.getCurrentPosition());
                    }
                }
            }, 0, 50);
        }
//      监听来电事件
        TelephonyManager phoneyMana = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phoneyMana.listen(new myPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_play;
    }

    @Override
    public void initView() throws Exception {
        mPlayActivityBG = (ImageView) findViewById(R.id.PlayActivity_BG);
        mPlayActivityTitle = (TextView) findViewById(R.id.PlayActivity_Title);
        mPlayActivityCollect = (ImageView) findViewById(R.id.PlayActivity_Collect);
        mPlayActivitySelect = (ImageView) findViewById(R.id.PlayActivity_Select);
        mPlayActivityTime = (ImageView) findViewById(R.id.PlayActivity_Time);
        mPlayActivityTimeName = (TextView) findViewById(R.id.PlayActivity_TimeName);
        mPlayActivityImageView = (ImageView) findViewById(R.id.PlayActivity_ImageView);
        mPlayActivityPlayed = (TextView) findViewById(R.id.PlayActivity_played);
        mPlayActivitySeek = (SeekBar) findViewById(R.id.PlayActivity_seek);
        mPlayActivityDuration = (TextView) findViewById(R.id.PlayActivity_duration);
        mPlayActivityMode = (ImageView) findViewById(R.id.PlayActivity_mode);
        mPlayActivityPre = (ImageView) findViewById(R.id.PlayActivity_pre);
        mPlayActivityPlay = (ImageView) findViewById(R.id.PlayActivity_play);
        mPlayActivityNext = (ImageView) findViewById(R.id.PlayActivity_next);
        mPlayActivityPlaylist = (ImageView) findViewById(R.id.PlayActivity_playlist);
    }

    private void initData() {
        mPlayActivityTitle.setText(title);
        Glide.with(this).load(img).into(mPlayActivityImageView);
        mPlayActivitySeek.setIndeterminate(false);
        mPlayActivitySeek.setProgress(0);
        mPlayActivitySeek.setMax(1000);
    }

    private void initCollect(String id) {
//        Log.e("iiiiuserid", String.valueOf(SPUtils.get(PlayActivity.this, "userid", "")));
//        Log.e("iiiiid", id);
        //判断音乐是否收藏
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("itfaceId", "067");
        hashMap2.put("token", SPUtils.get(PlayActivity.this, "token", ""));
        hashMap2.put("voiceId", id);
        JSONObject jsonObject2 = new JSONObject(hashMap2);
        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject2.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<PlayDeleteCollectBeans>(PlayActivity.this) {
                    @Override
                    public void onSuccess(PlayDeleteCollectBeans mPlayDeleteCollectBeans, Call call, Response response) {
                        if (mPlayDeleteCollectBeans.getResultCode() == 1) {
                            if (mPlayDeleteCollectBeans.getData().size() != 0) {
                                mPlayActivityCollect.setImageDrawable(getResources().getDrawable(R.mipmap.play_collectioned));
                                collectId = mPlayDeleteCollectBeans.getData().get(0).getId();
                                isCollect = false;
                            } else {
                                mPlayActivityCollect.setImageDrawable(getResources().getDrawable(R.mipmap.play_collection));
                                isCollect = true;
                            }
                        } else {
                            Log.e("paly_msg1", mPlayDeleteCollectBeans.getMsg());
                            App.ErrorToken(mPlayDeleteCollectBeans.getResultCode(), mPlayDeleteCollectBeans.getMsg());
                        }
                    }
                });
    }

    private void initMoHu(final String img) {
        //url为网络图片的url，10 是缩放的倍数（越大模糊效果越高）
        final String pattern = "30";
        new Thread(new Runnable() {
            @Override
            public void run() {
                int scaleRatio = 0;
                if (TextUtils.isEmpty(pattern)) {
                    scaleRatio = 0;
                } else if (scaleRatio < 0) {
                    scaleRatio = 10;
                } else {
                    scaleRatio = Integer.parseInt(pattern);
                }
                // 下面的这个方法必须在子线程中执行
                final Bitmap blurBitmap2 = FastBlurUtil.GetUrlBitmap(img, scaleRatio);

                // 刷新ui必须在主线程中执行
                App.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        mPlayActivityBG.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        mPlayActivityBG.setImageBitmap(blurBitmap2);
                        mPlayActivityBG.setAlpha(200);
                    }
                });
            }
        }).start();
    }

    private void initListeners() {
        //播放列表
        mPlayActivityPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alert;
                alert = new AlertDialog.Builder(PlayActivity.this, R.style.AlertDialogs).create();
                alert.show();
                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.play_popupwindow);
                ListView mPlayListView = alert.getWindow().findViewById(R.id.Play_ListView);

                mPlayListAdapter = new PlayListAdapter(PlayActivity.this, App.mList);
                mPlayListView.setAdapter(mPlayListAdapter);

                mPlayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int positions, long id) {
                        App.mediaPlayer2.stop();
                        App.mediaPlayer2.reset();
                        max = 0;
                        position = positions;
                        try {
                            if (SPUtils.get(PlayActivity.this, "flag", "").equals("2")) {
                                playUrl64 = App.mList2.get(position).getPlayUrl64();
                                title = App.mList2.get(position).getTrackTitle();
                                mPlayActivityTitle.setText(title);
                                Glide.with(PlayActivity.this).load(App.mList2.get(position).getCoverUrlLarge()).into(mPlayActivityImageView);
                                initMoHu(App.mList2.get(position).getCoverUrlLarge());
                                initCollect(String.valueOf(App.mList2.get(position).getDataId()));
                            } else if (SPUtils.get(PlayActivity.this, "flag", "").equals("1")) {
                                playUrl64 = App.mList.get(position).getPlayUrl64();
                                title = App.mList.get(position).getTrackTitle();
                                mPlayActivityTitle.setText(title);
                                Glide.with(PlayActivity.this).load(App.mList.get(position).getCoverUrlLarge()).into(mPlayActivityImageView);
                                initMoHu(App.mList.get(position).getCoverUrlLarge());
                                initCollect(String.valueOf(App.mList.get(position).getDataId()));
                            }
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                        NewMp3 = playUrl64;
                        play();
                        alert.dismiss();
                        return;
                    }
                });
                // 取消
                alert.getWindow().findViewById(R.id.Play_ImageView).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                        return;
                    }
                });

            }
        });

        mPlayActivityCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                initCollect(id);
                Log.e("dddddddd", String.valueOf(isCollect));
                if (isCollect) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("itfaceId", "063");
                    hashMap.put("token", SPUtils.get(PlayActivity.this, "token", ""));
                    hashMap.put("voiceId", id);
                    hashMap.put("albumId", "1");
                    hashMap.put("voiceTitle", title);
                    hashMap.put("voiceUrl", NewMp3);
                    hashMap.put("voiceTimes", App.time);
                    Log.e("dddddddd1", id);
                    JSONObject jsonObject = new JSONObject(hashMap);
                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<PlayAddCollectBeans>(PlayActivity.this) {
                                @Override
                                public void onSuccess(PlayAddCollectBeans mPlayAddCollectBeans, Call call, Response response) {
                                    Log.e("ddddddddResultCode", String.valueOf(mPlayAddCollectBeans.getResultCode()));
                                    if (mPlayAddCollectBeans.getResultCode() == 1) {
                                        mPlayActivityCollect.setImageDrawable(getResources().getDrawable(R.mipmap.play_collectioned));
                                        collectId = (int) mPlayAddCollectBeans.getData();
                                        isCollect = false;
                                        Toast.makeText(PlayActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e("paly_msg2", mPlayAddCollectBeans.getMsg());
                                        App.ErrorToken(mPlayAddCollectBeans.getResultCode(), mPlayAddCollectBeans.getMsg());
                                    }
                                }
                            });
                } else {
                    HashMap<String, Object> hashMap2 = new HashMap<>();
                    hashMap2.put("itfaceId", "065");
                    hashMap2.put("token", SPUtils.get(PlayActivity.this, "token", ""));
                    hashMap2.put("id", collectId);
                    Log.e("dddddddd2", id);

                    JSONObject jsonObject2 = new JSONObject(hashMap2);
                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject2.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<PlayDeletesCollectBeans>(PlayActivity.this) {
                                @Override
                                public void onSuccess(PlayDeletesCollectBeans mPlayDeletesCollectBeans, Call call, Response response) {
                                    if (mPlayDeletesCollectBeans.getResultCode() == 1) {
                                        mPlayActivityCollect.setImageDrawable(getResources().getDrawable(R.mipmap.play_collection));
                                        isCollect = true;
                                        Toast.makeText(PlayActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e("paly_msg3", mPlayDeletesCollectBeans.getMsg());
                                        App.ErrorToken(mPlayDeletesCollectBeans.getResultCode(), mPlayDeletesCollectBeans.getMsg());
                                    }
                                }
                            });
                }
            }
        });

        mPlayActivitySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelect.equals("1")) {
                    Toast.makeText(PlayActivity.this, "列表循环", Toast.LENGTH_SHORT).show();
                    mPlayActivitySelect.setImageDrawable(getResources().getDrawable(R.mipmap.play_orderplay));
                    SPUtils.put(PlayActivity.this, "isSelect", isSelect);//判断再次进入时图标是否改变
                    isXunhuan = "1";
                    SPUtils.put(PlayActivity.this, "isXunhuan", isXunhuan);
                    isSelect = "2";
                } else if (isSelect.equals("2")) {
                    Toast.makeText(PlayActivity.this, "随机播放", Toast.LENGTH_SHORT).show();
                    mPlayActivitySelect.setImageDrawable(getResources().getDrawable(R.mipmap.play_random));
                    SPUtils.put(PlayActivity.this, "isSelect", isSelect);
                    isXunhuan = "2";
                    SPUtils.put(PlayActivity.this, "isXunhuan", isXunhuan);
                    isSelect = "3";
                } else if (isSelect.equals("3")) {
                    Toast.makeText(PlayActivity.this, "单曲循环", Toast.LENGTH_SHORT).show();
                    mPlayActivitySelect.setImageDrawable(getResources().getDrawable(R.mipmap.play_singlecircle));
                    SPUtils.put(PlayActivity.this, "isSelect", isSelect);
                    isXunhuan = "3";
                    SPUtils.put(PlayActivity.this, "isXunhuan", isXunhuan);
                    isSelect = "1";
                }
            }
        });

//        mPlayActivityTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog alert;
//                alert = new AlertDialog.Builder(PlayActivity.this).create();
//                alert.show();
//                //加载自定义dialog
//                alert.getWindow().setContentView(R.layout.play_time);
//                findViewById(R.id.cart_delete_content);
//                alert.getWindow().findViewById(R.id.Play_Time_One).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(PlayActivity.this, "1首", Toast.LENGTH_SHORT).show();
//                        mPlayActivityTimeName.setText("1首");
//                        mPlayActivityTimeName.setVisibility(View.VISIBLE);
//                        App.mediaPlayer2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                            @Override
//                            public void onCompletion(MediaPlayer mp) {
//                                currentPosition = 0;
//                                App.mediaPlayer2.pause();
//                                mPlayActivityPlay.setImageDrawable(getResources().getDrawable(R.drawable.play_rdi_btn_play));
//                                isPlay = true;
//                                mPlayActivityTimeName.setVisibility(View.GONE);
//                            }
//                        });
////                        }
//                        alert.dismiss();
//                    }
//                });
//                alert.getWindow().findViewById(R.id.Play_Time_Two).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(PlayActivity.this, "5首", Toast.LENGTH_SHORT).show();
//                        mPlayActivityTimeName.setVisibility(View.VISIBLE);
//                        mPlayActivityTimeName.setText("5首");
//                        SPUtils.put(PlayActivity.this, "StartTime", "true");//设置是否启动了该定时功能
//                        SPUtils.put(PlayActivity.this, "positions", String.valueOf(0));//把循环的歌曲数量归0
//                        SPUtils.put(PlayActivity.this, "StartSize", String.valueOf(2));//设置播放的歌曲数量
//                        alert.dismiss();
//
//                    }
//                });
//                alert.getWindow().findViewById(R.id.Play_Time_Three).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(PlayActivity.this, "10首", Toast.LENGTH_SHORT).show();
//                        mPlayActivityTimeName.setVisibility(View.VISIBLE);
//                        mPlayActivityTimeName.setText("10首");
//                        SPUtils.put(PlayActivity.this, "StartTime", "true");//设置是否启动了该定时功能
//                        SPUtils.put(PlayActivity.this, "positions", String.valueOf(0));//把循环的歌曲数量归0
//                        SPUtils.put(PlayActivity.this, "StartSize", String.valueOf(10));//设置播放的歌曲数量
//                        alert.dismiss();
//
//                    }
//                });
//                alert.getWindow().findViewById(R.id.Play_Time_Four).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(PlayActivity.this, "20首", Toast.LENGTH_SHORT).show();
//                        mPlayActivityTimeName.setVisibility(View.VISIBLE);
//                        mPlayActivityTimeName.setText("20首");
//                        SPUtils.put(PlayActivity.this, "StartTime", "true");//设置是否启动了该定时功能
//                        SPUtils.put(PlayActivity.this, "positions", String.valueOf(0));//把循环的歌曲数量归0
//                        SPUtils.put(PlayActivity.this, "StartSize", String.valueOf(20));//设置播放的歌曲数量
//                        alert.dismiss();
//                    }
//                });
//                alert.getWindow().findViewById(R.id.Play_Time_Five).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SPUtils.put(PlayActivity.this, "StartTime", "false");//设置是否启动了该定时功能
//                        mPlayActivityTimeName.setVisibility(View.VISIBLE);
//                        i = 600;
//                        isHandle();
//                        Toast.makeText(PlayActivity.this, "10分钟", Toast.LENGTH_SHORT).show();
//                        mPlayActivityTimeName.setText("10分钟");
//                        alert.dismiss();
//                    }
//                });
//                alert.getWindow().findViewById(R.id.Play_Time_Six).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SPUtils.put(PlayActivity.this, "StartTime", "false");//设置是否启动了该定时功能
//                        mPlayActivityTimeName.setVisibility(View.VISIBLE);
//                        i = 1200;
//                        isHandle();
//                        Toast.makeText(PlayActivity.this, "20分钟", Toast.LENGTH_SHORT).show();
//                        mPlayActivityTimeName.setText("20分钟");
//                        alert.dismiss();
//                    }
//                });
//                alert.getWindow().findViewById(R.id.Play_Time_Seven).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SPUtils.put(PlayActivity.this, "StartTime", "false");//设置是否启动了该定时功能
//                        mPlayActivityTimeName.setVisibility(View.VISIBLE);
//                        i = 1800;
//                        isHandle();
//                        Toast.makeText(PlayActivity.this, "30分钟", Toast.LENGTH_SHORT).show();
//                        mPlayActivityTimeName.setText("30分钟");
//                        alert.dismiss();
//                    }
//                });
//                alert.getWindow().findViewById(R.id.Play_Time_Eight).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SPUtils.put(PlayActivity.this, "StartTime", "false");//设置是否启动了该定时功能
//                        mPlayActivityTimeName.setVisibility(View.VISIBLE);
//                        i = 3600;
//                        isHandle();
//                        Toast.makeText(PlayActivity.this, "60分钟", Toast.LENGTH_SHORT).show();
//                        mPlayActivityTimeName.setText("60分钟");
//                        alert.dismiss();
//                    }
//                });
//                alert.getWindow().findViewById(R.id.Play_Time_Nine).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SPUtils.put(PlayActivity.this, "StartTime", "false");//设置是否启动了该定时功能
//                        mPlayActivityTimeName.setVisibility(View.VISIBLE);
//                        i = 5400;
//                        isHandle();
//                        Toast.makeText(PlayActivity.this, "90分钟", Toast.LENGTH_SHORT).show();
//                        mPlayActivityTimeName.setText("90分钟");
//                        alert.dismiss();
//                    }
//                });
//                alert.getWindow().findViewById(R.id.Play_Time_Ten).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SPUtils.put(PlayActivity.this, "StartTime", "false");//设置是否启动了该定时功能
//                        SPUtils.put(PlayActivity.this, "positions", String.valueOf(0));
//                        alert.dismiss();
//                        Toast.makeText(PlayActivity.this, "取消定时成功", Toast.LENGTH_SHORT).show();
//                        mPlayActivityTimeName.setVisibility(View.GONE);
//                        if (handler2 != null) {
//                            handler2.removeMessages(0);
//                        }
//                    }
//                });
//            }
//        });
        //上一首
        mPlayActivityPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayActivityPre.setClickable(false);
                mPlayActivityNext.setClickable(false);
                mPlayActivityPlay.setClickable(false);
//                dialog2 = new MyDialog_Views(PlayActivity.this, R.style.MyDialog);
//                dialog2.setCancelable(true);
//                dialog2.show();
//                MyDialog_Views myDialog_views = new MyDialog_Views(PlayActivity.this, "加载中...", "");
                if (position == 0) {
                    Toast.makeText(PlayActivity.this, "亲，已经是第一首歌啦~", Toast.LENGTH_SHORT).show();
                } else {
                    App.mediaPlayer2.stop();
                    App.mediaPlayer2.reset();
                    max = 0;
                    position = position - 1;
                    try {
                        if (SPUtils.get(PlayActivity.this, "flag", "").equals("2")) {
                            Log.e("vvvvvvvvv1",App.mList.get(position).getCoverUrlLarge());
                            Glide.with(PlayActivity.this).load(App.mList2.get(position).getCoverUrlLarge()).into(mPlayActivityImageView);

                            playUrl64 = App.mList2.get(position).getPlayUrl64();
                            title = App.mList2.get(position).getTrackTitle();
                            mPlayActivityTitle.setText(title);
                            initMoHu(App.mList2.get(position).getCoverUrlLarge());
                            initCollect(String.valueOf(App.mList2.get(position).getDataId()));
                        } else if (SPUtils.get(PlayActivity.this, "flag", "").equals("1")) {
                            Log.e("vvvvvvvvv2",App.mList.get(position).getCoverUrlLarge());
                            Glide.with(PlayActivity.this).load(App.mList.get(position).getCoverUrlLarge()).into(mPlayActivityImageView);

                            playUrl64 = App.mList.get(position).getPlayUrl64();
                            title = App.mList.get(position).getTrackTitle();
                            mPlayActivityTitle.setText(title);
                            initMoHu(App.mList.get(position).getCoverUrlLarge());
                            initCollect(String.valueOf(App.mList.get(position).getDataId()));
                        }
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    NewMp3 = playUrl64;
                    play();
                }
//                //模拟网络交互时间为5秒
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPlayActivityPre.setClickable(true);
                        mPlayActivityNext.setClickable(true);
                        mPlayActivityPlay.setClickable(true);
                    }
                }, 2000);
            }
        });

        //下一首
        mPlayActivityNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayActivityPre.setClickable(false);
                mPlayActivityNext.setClickable(false);
                mPlayActivityPlay.setClickable(false);
                try {
                    if (SPUtils.get(PlayActivity.this, "flag", "").equals("1")) {
                        if (position == App.mList.size() - 1) {
                            Toast.makeText(PlayActivity.this, "亲，已经是最后一首歌啦~", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("vvvvvvvvv3",App.mList.get(position).getCoverUrlLarge());
                            Glide.with(PlayActivity.this).load(App.mList.get(position).getCoverUrlLarge()).into(mPlayActivityImageView);

                            initMoHu(App.mList.get(position).getCoverUrlLarge());
                            initCollect(String.valueOf(App.mList.get(position).getDataId()));
                            isNext();
                        }
                    } else if (SPUtils.get(PlayActivity.this, "flag", "").equals("2")) {
                        if (position == App.mList2.size() - 1) {
                            Toast.makeText(PlayActivity.this, "亲，已经是最后一首歌啦~", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("vvvvvvvvv4",App.mList.get(position).getCoverUrlLarge());
                            Glide.with(PlayActivity.this).load(App.mList2.get(position).getCoverUrlLarge()).into(mPlayActivityImageView);

                            initMoHu(App.mList2.get(position).getCoverUrlLarge());
                            initCollect(String.valueOf(App.mList2.get(position).getDataId()));
                            isNext();
                        }
                    }

                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                //模拟网络交互时间为5秒
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPlayActivityPre.setClickable(true);
                        mPlayActivityNext.setClickable(true);
                        mPlayActivityPlay.setClickable(true);
                    }
                }, 2000);
            }
        });

        mPlayActivitySeek.setOnSeekBarChangeListener(new MySeekBar());

        // 监听[播放或暂停]事件
        mPlayActivityPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlay) {//播放状态
                    SPUtils.put(PlayActivity.this, "positions", String.valueOf(0));//把设置定时暂停播放的歌曲设为0，好size1和size2比较时好继续播放
                    currentPosition = App.mediaPlayer2.getCurrentPosition();//记录播放的位置
                    try {
                        App.mediaPlayer2.prepare();
                        App.mediaPlayer2.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mPlayActivityPlay.setImageResource(R.drawable.play_rdi_btn_pause);
                    if (App.time != 0) {
                        App.timer.purge();//移除所有任务;
                    }
                    isPlay = false;
                } else {//暂停状态
                    App.mediaPlayer2.stop();//暂停状态
                    isPlay = true;
                    mPlayActivityPlay.setImageResource(R.drawable.play_rdi_btn_play);
                }
            }
        });
    }

    private void isHandle() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; i > 0; i--) {
                    handler2.sendEmptyMessage(-9);
                    if (i <= 0) {
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler2.sendEmptyMessage(-8);
            }
        }).start();
    }

    /*来电事件处理*/
    private class myPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING://来电，应当停止音乐
                    if (App.mediaPlayer2.isPlaying()/* && playButton.getText().toString().equals("播放")*/) {
                        currentPosition = App.mediaPlayer2.getCurrentPosition();//记录播放的位置
                        App.mediaPlayer2.stop();
                        isCellPlay = true;//标记这是属于来电时暂停的标记
                        mPlayActivityPlay.setImageResource(R.drawable.play_rdi_btn_play);
                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE://无电话状态
                    if (isCellPlay) {
                        isCellPlay = false;
                        App.mediaPlayer2.pause();
                        App.mediaPlayer2.start();
                        if (App.time != 0) {
                            App.timer.purge();//移除所有任务;
                        }
                        mPlayActivityPlay.setImageResource(R.drawable.play_rdi_btn_pause);
                        play();
                    }
                    break;
            }
        }
    }

    /*进度条处理*/
    public class MySeekBar implements SeekBar.OnSeekBarChangeListener {
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        }

        /*滚动时,应当暂停后台定时器*/
        public void onStartTrackingTouch(SeekBar seekBar) {
            isSeekBarChanging = true;
        }

        /*滑动结束后，重新设置值*/
        public void onStopTrackingTouch(SeekBar seekBar) {
            isSeekBarChanging = false;
            App.mediaPlayer2.seekTo(seekBar.getProgress());
        }
    }

    private boolean run = true;
    private final Handler handler = new Handler();
    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (run) {
                handler.postDelayed(this, 1000);
                //音乐的总长度
                max = App.mediaPlayer2.getDuration();
                //获取当前音乐播放位置
                current = App.mediaPlayer2.getCurrentPosition();
                firstTime = mFormatter.format(current);
                mPlayActivityPlayed.setText(firstTime);
                String timeMax = mFormatter.format(max);
                int str1 = Integer.parseInt(timeMax.substring(0, 2));
                int str2 = Integer.parseInt(timeMax.substring(timeMax.length() - 2));
                App.time = str1 * 60 + str2;
                sendTime = mFormatter.format(max);
                mPlayActivityDuration.setText(sendTime);
            }
        }
    };

    /*播放处理*/
    private void play() {
        SPUtils.put(PlayActivity.this, "isStart", "1");//判断播放器是否已经启动了（1启动2没启动）
        isPlayMusic(NewMp3);
    }

    private void isPlayMusic(final String str) {
        currentPosition = 0;
        SPUtils.put(this, "isPlay", "true");//监听是否播放了
        SPUtils.put(this, "PlayName", title);//把标题存起来，底部播放栏目条要显示使用
        SPUtils.put(this, "mp3Uri", NewMp3);//把播放链接存储，用于下次点击相同的链接是否让播放
        try {
//          App.mediaPlayer2 = new MediaPlayer();
            App.mediaPlayer2.setDataSource(str);
//            App.mediaPlayer2.prepare();//数据缓冲(同步缓存)
            App.mediaPlayer2.prepareAsync();//数据缓冲(异步缓存)
            App.mediaPlayer2.setOnCompletionListener(
                    new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
//                    Log.e("gggggggggfirstTime", String.valueOf(current));
//                    Log.e("gggggggggsendTime", String.valueOf(max));
//                    Log.e("gggggggggfirstTime", String.valueOf(current / 10000));
//                    Log.e("gggggggggsendTime", String.valueOf(max / 10000));
                            if (current != 0 && max != 0 && current / 100000 == max / 100000) {
                                currentPosition = 0;
                                int size1 = 0;
                                int size2 = Integer.parseInt(String.valueOf(SPUtils.get(PlayActivity.this, "StartSize", "0")));//给个初始值使其在定时播放暂停后，比较size1 < size2这个大小时可以继续播放
                                if (SPUtils.get(PlayActivity.this, "StartTime", "").equals("true")) {
                                    positions = Integer.parseInt(String.valueOf(SPUtils.get(PlayActivity.this, "positions", "")));
                                    positions = positions + 1;
                                    SPUtils.put(PlayActivity.this, "positions", String.valueOf(positions));
//                            Log.e("ggggggggg", String.valueOf(SPUtils.get(PlayActivity.this, "positions", "")));
                                    size1 = Integer.parseInt(String.valueOf(SPUtils.get(PlayActivity.this, "positions", "")));
                                    size2 = Integer.parseInt(String.valueOf(SPUtils.get(PlayActivity.this, "StartSize", "")));
//                            Log.e("ggggggggg", String.valueOf(size1));
//                            Log.e("ggggggggg", String.valueOf(size2));
                                    if (size1 == size2) {
                                        Log.e("ggggggggg", "暂停了 ");
                                        mp.pause();
                                        SPUtils.put(PlayActivity.this, "StartTime", "false");
//                              SPUtils.put(PlayActivity.this, "positions", String.valueOf(0));
                                        isPlay = true;
                                        mPlayActivityTimeName.setVisibility(View.GONE);
                                        mPlayActivityPlay.setImageDrawable(getResources().getDrawable(R.drawable.play_rdi_btn_play));
                                    }
                                }
//                        Log.e("gggggggggsize1", String.valueOf(size1));
//                        Log.e("gggggggggsize2", String.valueOf(size2));
//                        Log.e("gggggggggisXunhuan", String.valueOf(isXunhuan));
                                if (isXunhuan.equals("1")) {//列表循环
                                    if (size1 <= size2) {
                                        isNext();
                                    }
                                } else if (isXunhuan.equals("2")) {//随机循环
                                    if (size1 <= size2) {
                                        isNext();
                                    }
                                } else if (isXunhuan.equals("3")) {//单曲循环
                                    mp.start();
                                }
                            }
                        }
                    });
            //监听播放时回调函数
            initTime();
            //上传播放历史记录
            initSaveNetWork();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTime() {
        /*监听缓存 事件，在缓冲完毕后，开始播放*/
        App.mediaPlayer2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.seekTo(currentPosition);
                mPlayActivitySeek.setMax(App.mediaPlayer2.getDuration());
                task.run();
            }
        });
        if (App.time != 0) {
            App.timer.purge();//移除所有任务;
//            Log.e("ooo移除所有任务2", "移除所有任务成功");
        }
        App.timer = new Timer();
        App.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isSeekBarChanging) {
                    try {
                        mPlayActivitySeek.setProgress(App.mediaPlayer2.getCurrentPosition());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 50);
    }

    private void initSaveNetWork() {

        Log.e("paly_voicetoken", String.valueOf(SPUtils.get(PlayActivity.this, "token", "")));
        Log.e("paly_voiceId", id + "xcf");
        Log.e("paly_voiceTitle", title + "xcf");
        Log.e("paly_voiceTimes", String.valueOf(App.time) + "xcf");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "068");
        hashMap.put("token", SPUtils.get(PlayActivity.this, "token", ""));
        hashMap.put("voiceId", id);
        hashMap.put("voiceTitle", title);
        hashMap.put("voiceTimes", App.time);
        JSONObject jsonObject = new JSONObject(hashMap);
        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<PlaySaveBeans>(PlayActivity.this) {
                    @Override
                    public void onSuccess(PlaySaveBeans mPlayAddCollectBeans, Call call, Response response) {
                        if (mPlayAddCollectBeans.getResultCode() == 1) {

                        } else {
                            Log.e("paly_msg4", mPlayAddCollectBeans.getMsg());
                            App.ErrorToken(mPlayAddCollectBeans.getResultCode(), mPlayAddCollectBeans.getMsg());
                        }
//                        Toast.makeText(PlayActivity.this, "上传数据成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //随机播放、列表播放、播放下一首
    public void isNext() {
        mPlayActivityPlay.setImageDrawable(getResources().getDrawable(R.drawable.play_rdi_btn_pause));
        App.mediaPlayer2.stop();
        App.mediaPlayer2.reset();
        max = 0;
        position = position + 1;
        try {
            if (SPUtils.get(PlayActivity.this, "flag", "").equals("2")) {
                playUrl64 = App.mList2.get(position).getPlayUrl64();
                title = App.mList2.get(position).getTrackTitle();
                mPlayActivityTitle.setText(title);
            } else if (SPUtils.get(PlayActivity.this, "flag", "").equals("1")) {
                playUrl64 = App.mList.get(position).getPlayUrl64();
                title = App.mList.get(position).getTrackTitle();
                mPlayActivityTitle.setText(title);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        NewMp3 = playUrl64;
        play();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void PlayActivity_Bank(View view) {
        finish();
    }

    /*销毁时释资源*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
