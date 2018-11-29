package com.wanquan.mlmmx.mlmm.xmly.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.BaseActivity;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.LogUtil;
import com.wanquan.mlmmx.mlmm.xmly.adapter.PlayListAdapter;
import com.wanquan.mlmmx.mlmm.xmly.beans.PlayCollectionBeans;
import com.wanquan.mlmmx.mlmm.xmly.beans.PlayDeleteCollectBeans;
import com.wanquan.mlmmx.mlmm.xmly.beans.PlayListAddCollectBeans;
import com.wanquan.mlmmx.mlmm.xmly.utils.FastBlurUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.httputil.util.BASE64Encoder;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.yuyh.library.imgsel.utils.LogUtils;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.wanquan.mlmmx.mlmm.xmly.activity.PlayActivity.position;
import static rx.internal.operators.NotificationLite.isNext;

/**
 * 描述：播放列表
 * 作者：薛昌峰
 * 时间：2018.01.08
 */
public class PlayListActivity extends BaseActivity {
    private LinearLayout mPlayListActivityBg1;
    private RelativeLayout mPlayListActivityBg2;
    private TextView mPlayListActivityTitle;
    private ImageView mPlayListActivityBG;
    private ImageView mPlayListActivityImg;
    private TextView mPlayListActivityContent;
    private LinearLayout mPlayListActivityLL;
    private ImageView mPlayListActivityLLPlay;
    private ImageView mPlayListActivityCollect;
    private ListView mPlayListActivityListView;
    private RelativeLayout mPlayListActivityDownRL;
    private ImageView mPlayListActivityDownImg;
    private TextView mPlayListActivityDownName;
    private ImageView mPlayListActivityDownPlay;
    private ImageView mPlayListActivityDownPlayNext;
    private String id;
    private PlayListAdapter mPlayListAdapter;
    private String img;
    private String title;
    private String content;
    private Bitmap bitmap;
    private ImageView image;
    private XmPlayerManager mPlayerManager;
    private TrackList mTrackHotList = null;
    private CommonRequest mXimalaya;
    private boolean isPlay = true;
    private String dataId;
    private boolean isCollect = true;
    private String string;
    private int collectId;
    private int size;
    private String collect = "";
    private String playUrl64;

    @Override
    protected void onResume() {
        super.onResume();
        if (SPUtils.get(PlayListActivity.this, "isStart", "").equals("1")) {
            if (App.mediaPlayer2 != null) {
                if (App.mediaPlayer2.isPlaying()) {
                    mPlayListActivityDownPlay.setImageDrawable(getResources().getDrawable(R.mipmap.minisuspend));
                } else {
                    mPlayListActivityDownPlay.setImageDrawable(getResources().getDrawable(R.mipmap.minibreak));
                }
            }
            if (SPUtils.get(PlayListActivity.this, "flag", "").equals("1")) {
                mPlayListActivityDownRL.setVisibility(View.VISIBLE);
                mPlayListActivityDownName.setText(String.valueOf(SPUtils.get(PlayListActivity.this, "PlayName", "")));
            } else if (SPUtils.get(PlayListActivity.this, "flag", "").equals("2")) {
                mPlayListActivityDownRL.setVisibility(View.VISIBLE);
                mPlayListActivityDownName.setText(String.valueOf(SPUtils.get(PlayListActivity.this, "PlayName", "")));
            }
        } else {
            mPlayListActivityDownRL.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(PlayListActivity.this, R.color.tops);

        id = getIntent().getStringExtra("id");
        img = getIntent().getStringExtra("img");
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");

        Log.e("fddfdid", id + "id");
        Log.e("fddfdimg", img + "xcf");
        Log.e("fddfdtitle", title + "xcf");

        App.mList = new ArrayList();

        initMoHu();
        initData();
        initListeners();

        mPlayListAdapter = new PlayListAdapter(this, App.mList);
        mPlayListActivityListView.setAdapter(mPlayListAdapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_play_list;
    }

    @Override
    public void initView() throws Exception {
        mPlayListActivityBg1 = (LinearLayout) findViewById(R.id.PlayListActivity_bg1);
        mPlayListActivityBg2 = (RelativeLayout) findViewById(R.id.PlayListActivity_bg2);
        mPlayListActivityTitle = (TextView) findViewById(R.id.PlayListActivity_Title);
        mPlayListActivityBG = (ImageView) findViewById(R.id.PlayListActivity_BG);
        mPlayListActivityImg = (ImageView) findViewById(R.id.PlayListActivityImg);
        mPlayListActivityContent = (TextView) findViewById(R.id.PlayListActivity_Content);
        mPlayListActivityLL = (LinearLayout) findViewById(R.id.PlayListActivity_LL);
        mPlayListActivityLLPlay = (ImageView) findViewById(R.id.PlayListActivity_LL_Play);
        mPlayListActivityCollect = (ImageView) findViewById(R.id.PlayListActivity_Collect);
        mPlayListActivityListView = (ListView) findViewById(R.id.PlayListActivity_ListView);
        mPlayListActivityDownRL = (RelativeLayout) findViewById(R.id.PlayListActivity_Down_RL);
        mPlayListActivityDownImg = (ImageView) findViewById(R.id.PlayListActivity_Down_img);
        mPlayListActivityDownName = (TextView) findViewById(R.id.PlayListActivity_Down_Name);
        mPlayListActivityDownPlay = (ImageView) findViewById(R.id.PlayListActivity_Down_Play);
        mPlayListActivityDownPlayNext = (ImageView) findViewById(R.id.PlayListActivity_Down_PlayNext);
    }

    private void initMoHu() {
        //url为网络图片的url，10 是缩放的倍数（越大模糊效果越高）
        final String pattern = "3";
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
                        mPlayListActivityBG.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        mPlayListActivityBG.setImageBitmap(blurBitmap2);
                        mPlayListActivityBG.setAlpha(200);
                    }
                });
            }
        }).start();
    }

    private void initData() {
        Glide.with(this).load(img).into(mPlayListActivityImg);
        mPlayListActivityTitle.setText(title);
        mPlayListActivityContent.setText(content);

        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.ALBUM_ID, id);
        map.put(DTransferConstants.SORT, "asc");
        CommonRequest.getTracks(map, new IDataCallBack<TrackList>() {
            @Override
            public void onSuccess(TrackList object) {
                LogUtil.e("fdfdfdfdsf", object.toString());
                App.mList.clear();
                App.mList.addAll(object.getTracks());
                size = App.mList.size();
                mPlayListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code, String message) {
                Toast.makeText(PlayListActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        //判断专辑是否收藏
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("itfaceId", "066");
        hashMap2.put("token", SPUtils.get(PlayListActivity.this, "token", ""));
        hashMap2.put("albumId", id);
        JSONObject jsonObject2 = new JSONObject(hashMap2);
        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject2.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<PlayDeleteCollectBeans>(PlayListActivity.this) {
                    @Override
                    public void onSuccess(PlayDeleteCollectBeans mPlayDeleteCollectBeans, Call call, Response response) {
                        if (mPlayDeleteCollectBeans.getResultCode() == 1) {
                            if (mPlayDeleteCollectBeans.getData().size() != 0) {
                                mPlayListActivityCollect.setImageDrawable(getResources().getDrawable(R.mipmap.tingshoucang));
                                collectId = mPlayDeleteCollectBeans.getData().get(0).getId();
                                isCollect = false;
                            }
                        } else {
                            App.ErrorToken(mPlayDeleteCollectBeans.getResultCode(), mPlayDeleteCollectBeans.getMsg());

                        }
                    }
                });
    }

    /**
     * 获取网络图片并转为Base64编码
     *
     * @param url 网络图片路径
     * @return base64编码
     * @throws Exception
     */
    public String GetUrlImageToBase64(String url) throws Exception {
        if (url == null || "".equals(url.trim()))
            return null;
        URL u = new URL(url);
        // 打开图片路径
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        // 设置请求方式为GET
        conn.setRequestMethod("GET");
        // 设置超时响应时间为5秒
        conn.setConnectTimeout(5000);
        // 通过输入流获取图片数据
        InputStream inStream = conn.getInputStream();
        // 读取图片字节数组
        byte[] data = new byte[inStream.available()];
        inStream.read(data);
        inStream.close();
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        string = encoder.encode(data);
        Log.e("ggggggalbumImg", string);
        return encoder.encode(data);
    }

    private void initListeners() {
        mPlayListActivityCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCollect) {
//                    Log.e("ggggggvoiceTitle", title);
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("itfaceId", "062");
                    hashMap.put("albumId", id);
                    hashMap.put("token", SPUtils.get(PlayListActivity.this, "token", ""));
                    hashMap.put("albumTitle", title);
                    hashMap.put("albumImg", img);
                    hashMap.put("voiceCount", size);
                    JSONObject jsonObject = new JSONObject(hashMap);
                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<PlayListAddCollectBeans>(PlayListActivity.this) {
                                @Override
                                public void onSuccess(PlayListAddCollectBeans mPlayListAddCollectBeans, Call call, Response response) {
                                    if (mPlayListAddCollectBeans.getResultCode() == 1) {
                                        mPlayListActivityCollect.setImageDrawable(getResources().getDrawable(R.mipmap.tingshoucang));
                                        collectId = (int) mPlayListAddCollectBeans.getData();
                                        isCollect = false;
                                        Toast.makeText(PlayListActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                    } else if (mPlayListAddCollectBeans.getResultCode() == -2) {
                                        App.ErrorToken(mPlayListAddCollectBeans.getResultCode(), mPlayListAddCollectBeans.getMsg());

                                    }
                                }
                            });
                } else {
                    HashMap<String, Object> hashMap2 = new HashMap<>();
                    hashMap2.put("itfaceId", "064");
                    hashMap2.put("token", SPUtils.get(PlayListActivity.this, "token", ""));
                    hashMap2.put("id", collectId);
                    JSONObject jsonObject2 = new JSONObject(hashMap2);
                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject2.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<PlayDeleteCollectBeans>(PlayListActivity.this) {
                                @Override
                                public void onSuccess(PlayDeleteCollectBeans mPlayDeleteCollectBeans, Call call, Response response) {
                                    if (mPlayDeleteCollectBeans.getResultCode() == 1) {
                                        mPlayListActivityCollect.setImageDrawable(getResources().getDrawable(R.mipmap.tingshoucangno));
                                        isCollect = true;
                                        Toast.makeText(PlayListActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        App.ErrorToken(mPlayDeleteCollectBeans.getResultCode(), mPlayDeleteCollectBeans.getMsg());

                                    }
                                }
                            });
                }
            }
        });

        //随机播放全部
        mPlayListActivityLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.mediaPlayer2 != null && App.mediaPlayer2.isPlaying()) {
                    App.mediaPlayer2.stop();
                }
                SPUtils.put(PlayListActivity.this, "flag", "1");
                SPUtils.put(PlayListActivity.this, "isDown", "2");
                Intent intent = new Intent(PlayListActivity.this, PlayActivity.class);
                intent.putExtra("title", App.mList.get(0).getTrackTitle());
                intent.putExtra("mp3", App.mList.get(0).getPlayUrl64());
                intent.putExtra("position", 0);
                intent.putExtra("img", img);
                intent.putExtra("dataId", id);
                intent.putExtra("id", String.valueOf(App.mList.get(0).getDataId()));
                startActivity(intent);
            }
        });
        mPlayListActivityDownRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.put(PlayListActivity.this, "flag", "1");
                SPUtils.put(PlayListActivity.this, "isDown", "1");
                Intent intent = new Intent(PlayListActivity.this, PlayActivity.class);
                intent.putExtra("mp3", PlayActivity.playUrl64);
                intent.putExtra("title", PlayActivity.title);
                intent.putExtra("position", PlayActivity.position);
                intent.putExtra("img", PlayActivity.img);
                intent.putExtra("dataId", id);
                intent.putExtra("id", PlayActivity.id);
                startActivity(intent);
            }
        });
        mPlayListActivityDownPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.mediaPlayer2.isPlaying()) {
                    mPlayListActivityDownPlay.setImageDrawable(getResources().getDrawable(R.mipmap.minibreak));
                    App.mediaPlayer2.pause();
                } else {
                    App.mediaPlayer2.start();
                    mPlayListActivityDownPlay.setImageDrawable(getResources().getDrawable(R.mipmap.minisuspend));
                }
            }
        });
        mPlayListActivityDownPlayNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SPUtils.get(PlayListActivity.this, "flag", "").equals("1")) {
                    if (position == App.mList.size() - 1) {
                        Toast.makeText(PlayListActivity.this, "亲，已经是最后一首歌啦~", Toast.LENGTH_SHORT).show();
                    } else {
                        PlayActivity.img = App.mList.get(position + 1).getCoverUrlLarge();
                        isNext();
                    }
                } else if (SPUtils.get(PlayListActivity.this, "flag", "").equals("2")) {
                    if (position == App.mList2.size() - 1) {
                        Toast.makeText(PlayListActivity.this, "亲，已经是最后一首歌啦~", Toast.LENGTH_SHORT).show();
                    } else {
                        PlayActivity.img = App.mList2.get(position + 1).getCoverUrlLarge();
                        isNext();
                    }
                }
            }
        });
        mPlayListActivityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long longs) {
                if (App.mediaPlayer2 != null && App.mediaPlayer2.isPlaying()) {
                    App.mediaPlayer2.stop();
                }
//                if (!App.mList.get(position).getPlayUrl64().equals(SPUtils.get(PlayListActivity.this, "mp3Uri", ""))) {
                SPUtils.put(PlayListActivity.this, "flag", "1");
                SPUtils.put(PlayListActivity.this, "isDown", "2");
                Intent intent = new Intent(PlayListActivity.this, PlayActivity.class);
                intent.putExtra("title", App.mList.get(position).getTrackTitle());
                intent.putExtra("mp3", App.mList.get(position).getPlayUrl64());
                intent.putExtra("position", position);
                intent.putExtra("img", App.mList.get(position).getCoverUrlLarge());
                intent.putExtra("dataId", id);
                intent.putExtra("id", String.valueOf(App.mList.get(position).getDataId()));
                startActivity(intent);
//                } else {
//                    SPUtils.put(PlayListActivity.this, "flag", "1");
//                    SPUtils.put(PlayListActivity.this, "isDown", "1");
//                    Intent intent = new Intent(PlayListActivity.this, PlayActivity.class);
//                    intent.putExtra("mp3", PlayActivity.playUrl64);
//                    intent.putExtra("title", PlayActivity.title);
//                    intent.putExtra("position", PlayActivity.position);
//                    intent.putExtra("img", PlayActivity.img);
//                    intent.putExtra("dataId", id);
//                    intent.putExtra("id", PlayActivity.id);
//                    startActivity(intent);
//                }
            }
        });
    }

    private void isNext() {
        App.mediaPlayer2.stop();
        App.mediaPlayer2.reset();
        position = position + 1;
        try {
            if (SPUtils.get(PlayListActivity.this, "flag", "").equals("2")) {
                playUrl64 = App.mList2.get(position).getPlayUrl64();
                PlayActivity.title = App.mList2.get(position).getTrackTitle();
                mPlayListActivityDownName.setText(PlayActivity.title);
            } else if (SPUtils.get(PlayListActivity.this, "flag", "").equals("1")) {
                playUrl64 = App.mList.get(position).getPlayUrl64();
                PlayActivity.title = App.mList.get(position).getTrackTitle();
                mPlayListActivityDownName.setText(PlayActivity.title);
            }
            try {
                App.mediaPlayer2.setDataSource(playUrl64);
            } catch (IOException e) {
                e.printStackTrace();
            }
            App.mediaPlayer2.prepareAsync();//数据缓冲(异步缓存)
            App.mediaPlayer2.setOnCompletionListener(
                    new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                        }
                    });
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }

    public String bitmaptoString(Bitmap bitmap, int bitmapQuality) {
        // 将Bitmap转换成字符串
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, bitmapQuality, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        Log.e("ggggggalbumImg", string);
        return string;
    }

    public String encodeBase64(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        string = Base64.encodeToString(buffer, Base64.DEFAULT);
        Log.e("ggggggalbumImg", string);

        return string;
    }

    /**
     * 将图片转换成Base64编码的字符串
     *
     * @param path
     * @return base64编码的字符串
     */
    public String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            string = Base64.encodeToString(data, Base64.DEFAULT);
            Log.e("ggggggalbumImg", string);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return string;
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
        Log.e("ggggggalbumImg", string);

        return string;
    }

    //图片转化成base64字符串
    public static String GetImageStr(String path) {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = path;//待处理的图片
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        Log.e("ggggggalbumImg", encoder.encode(data));

        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

    public void PlayListActivity_Bank(View view) {
        finish();
    }
}
