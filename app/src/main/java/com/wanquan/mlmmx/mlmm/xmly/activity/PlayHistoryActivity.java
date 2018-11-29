package com.wanquan.mlmmx.mlmm.xmly.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.BaseActivity;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.xmly.adapter.MyLikeListAdapter;
import com.wanquan.mlmmx.mlmm.xmly.adapter.PlayHistoryAdapter;
import com.wanquan.mlmmx.mlmm.xmly.beans.MyLikeListActivityBeans;
import com.wanquan.mlmmx.mlmm.xmly.beans.PlayHistoryBeans;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.track.BatchTrackList;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.wanquan.mlmmx.mlmm.xmly.activity.PlayActivity.position;

/**
 * 描述：播放历史
 * 作者：薛昌峰
 * 时间：2018.01.16
 */
public class PlayHistoryActivity extends BaseActivity {
    private TextView mPlayHistoryTitle;
    private ListView mPlayHistoryListView;
    private RelativeLayout mPlayListActivityDownRL;
    private ImageView mPlayListActivityDownImg;
    private TextView mPlayListActivityDownName;
    private ImageView mPlayListActivityDownPlay;
    private ImageView mPlayListActivityDownPlayNext;
    private List<PlayHistoryBeans.DataBean> mList = new ArrayList<>();
    private PlayHistoryAdapter mPlayHistoryAdapter;
    private String str = "";
    ////////////
    public static String like;
    public static List<MyLikeListActivityBeans.DataBean> mList3 = new ArrayList<>();
    private MyLikeListAdapter mMyLikeListAdapter;
    public static String img;
    private String playUrl64;

    @Override
    protected void onResume() {
        super.onResume();
        if (SPUtils.get(PlayHistoryActivity.this, "isStart", "").equals("1")) {
            if (App.mediaPlayer2 != null) {
                if (App.mediaPlayer2.isPlaying()) {
                    mPlayListActivityDownPlay.setImageDrawable(getResources().getDrawable(R.mipmap.minisuspend));
                } else {
                    mPlayListActivityDownPlay.setImageDrawable(getResources().getDrawable(R.mipmap.minibreak));
                }
            }
            if (SPUtils.get(PlayHistoryActivity.this, "flag", "").equals("1")) {
                mPlayListActivityDownRL.setVisibility(View.VISIBLE);
                mPlayListActivityDownName.setText(String.valueOf(SPUtils.get(PlayHistoryActivity.this, "PlayName", "")));
            } else if (SPUtils.get(PlayHistoryActivity.this, "flag", "").equals("2")) {
                mPlayListActivityDownRL.setVisibility(View.VISIBLE);
                mPlayListActivityDownName.setText(String.valueOf(SPUtils.get(PlayHistoryActivity.this, "PlayName", "")));
            }
        } else {
            mPlayListActivityDownRL.setVisibility(View.GONE);
        }
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(PlayHistoryActivity.this, R.color.tops);

        like = getIntent().getStringExtra("like");
        img = getIntent().getStringExtra("img");

        App.mList2 = new ArrayList<>();
        initData();
        initListeners();

        if (like.equals("like")) {
            mPlayHistoryTitle.setText("我喜欢");
            mMyLikeListAdapter = new MyLikeListAdapter(this, mList3);
            mPlayHistoryListView.setAdapter(mMyLikeListAdapter);
        } else if (like.equals("likes")) {
            mPlayHistoryAdapter = new PlayHistoryAdapter(this, App.mList2);
            mPlayHistoryListView.setAdapter(mPlayHistoryAdapter);
        }
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_play_history;
    }

    @Override
    public void initView() throws Exception {
        mPlayHistoryTitle = (TextView) findViewById(R.id.PlayHistory_Title);
        mPlayHistoryListView = (ListView) findViewById(R.id.PlayHistory_ListView);
        mPlayListActivityDownRL = (RelativeLayout) findViewById(R.id.PlayListActivity_Down_RL);
        mPlayListActivityDownImg = (ImageView) findViewById(R.id.PlayListActivity_Down_img);
        mPlayListActivityDownName = (TextView) findViewById(R.id.PlayListActivity_Down_Name);
        mPlayListActivityDownPlay = (ImageView) findViewById(R.id.PlayListActivity_Down_Play);
        mPlayListActivityDownPlayNext = (ImageView) findViewById(R.id.PlayListActivity_Down_PlayNext);
    }

    private void initData() {
        if (like.equals("like")) {
            //我喜欢
            HashMap<String, Object> hashMap3 = new HashMap<>();
            hashMap3.put("itfaceId", "067");
            hashMap3.put("token", SPUtils.get(PlayHistoryActivity.this, "token", ""));
            JSONObject jsonObject3 = new JSONObject(hashMap3);
            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject3.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<MyLikeListActivityBeans>(PlayHistoryActivity.this) {
                        @Override
                        public void onSuccess(MyLikeListActivityBeans mMyLikeListActivityBeans, Call call, Response response) {
                            if (mMyLikeListActivityBeans.getResultCode() == 1) {
                                mList3.clear();
                                mList3.addAll(mMyLikeListActivityBeans.getData());
                                mMyLikeListAdapter.notifyDataSetChanged();
                            } else {
                                App.ErrorToken(mMyLikeListActivityBeans.getResultCode(), mMyLikeListActivityBeans.getMsg());

                            }
                        }
                    });
        } else if (like.equals("likes")) {
            HashMap<String, Object> hashMap2 = new HashMap<>();
            hashMap2.put("itfaceId", "069");
            hashMap2.put("token", SPUtils.get(PlayHistoryActivity.this, "token", ""));
            JSONObject jsonObject2 = new JSONObject(hashMap2);
            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject2.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<PlayHistoryBeans>(PlayHistoryActivity.this) {
                        @Override
                        public void onSuccess(PlayHistoryBeans mPlayHistoryBeans, Call call, Response response) {
                            if (mPlayHistoryBeans.getResultCode() == 1) {
                                mList.clear();
                                mList.addAll(mPlayHistoryBeans.getData());
                                //用逗号隔开
                                for (int i = 0; i < mList.size(); i++) {
                                    if (i != mList.size() - 1) {
                                        str += mList.get(i).getVoiceId() + ",";
                                    } else {
                                        str += mList.get(i).getVoiceId();
                                    }
                                }
                                initXMLY();
                            } else {
                                App.ErrorToken(mPlayHistoryBeans.getResultCode(), mPlayHistoryBeans.getMsg());

                            }
                        }

                        private void initXMLY() {
//                            Log.e("fffffffff", str);
                            Map<String, String> map = new HashMap<>();
                            map.put(DTransferConstants.TRACK_IDS, str);
                            CommonRequest.getBatchTracks(map, new IDataCallBack<BatchTrackList>() {
                                @Override
                                public void onSuccess(BatchTrackList object) {
//                                    Log.e("fffffffff", object.toString());
                                    App.mList2.clear();
                                    App.mList2.addAll(object.getTracks());
                                    mPlayHistoryAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onError(int code, String message) {
//                                    Toast.makeText(PlayHistoryActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
        }
    }

    private void initListeners() {
        mPlayListActivityDownRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.put(PlayHistoryActivity.this, "flag", "2");
                SPUtils.put(PlayHistoryActivity.this, "isDown", "1");
                Intent intent = new Intent(PlayHistoryActivity.this, PlayActivity.class);
                intent.putExtra("mp3", PlayActivity.playUrl64);
                intent.putExtra("title", PlayActivity.title);
                intent.putExtra("position", PlayActivity.position);
                intent.putExtra("img", PlayActivity.img);
//                intent.putExtra("dataId", id);
                intent.putExtra("id", PlayActivity.id);
                startActivity(intent);
            }
        });
        mPlayListActivityDownPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.mediaPlayer2 != null && App.mediaPlayer2.isPlaying()) {
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
                if (SPUtils.get(PlayHistoryActivity.this, "flag", "").equals("1")) {
                    if (position == App.mList.size() - 1) {
                        Toast.makeText(PlayHistoryActivity.this, "亲，已经是最后一首歌啦~", Toast.LENGTH_SHORT).show();
                    } else {
                        PlayActivity.img = App.mList.get(position + 1).getCoverUrlLarge();
                        isNext();
                    }
                } else if (SPUtils.get(PlayHistoryActivity.this, "flag", "").equals("2")) {
                    if (position == App.mList2.size() - 1) {
                        Toast.makeText(PlayHistoryActivity.this, "亲，已经是最后一首歌啦~", Toast.LENGTH_SHORT).show();
                    } else {
                        PlayActivity.img = App.mList2.get(position + 1).getCoverUrlLarge();
                        isNext();
                    }
                }
            }
        });
        mPlayHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (App.mediaPlayer2 != null && App.mediaPlayer2.isPlaying()) {
                    App.mediaPlayer2.stop();
                }
                SPUtils.put(PlayHistoryActivity.this, "flag", "2");
                SPUtils.put(PlayHistoryActivity.this, "isDown", "2");
                if (like.equals("like")) {
                    Intent intent = new Intent(PlayHistoryActivity.this, PlayActivity.class);
                    intent.putExtra("title", mList3.get(position).getVoice_title());
                    intent.putExtra("mp3", mList3.get(position).getVoice_url());
                    intent.putExtra("position", position);
//                  intent.putExtra("img", mList.get(position).getCoverUrlLarge());
                    intent.putExtra("dataId", mList3.get(position).getId());
                    intent.putExtra("id", String.valueOf(mList3.get(position).getVoice_id()));
                    startActivity(intent);
                } else if (like.equals("likes")) {
                    Intent intent = new Intent(PlayHistoryActivity.this, PlayActivity.class);
                    intent.putExtra("title", App.mList2.get(position).getTrackTitle());
                    intent.putExtra("mp3", App.mList2.get(position).getPlayUrl64());
                    intent.putExtra("position", position);
                    intent.putExtra("img", App.mList2.get(position).getCoverUrlLarge());
                    intent.putExtra("dataId", App.mList2.get(position).getDataId());
                    intent.putExtra("id", String.valueOf(App.mList2.get(position).getDataId()));
                    startActivity(intent);
                }
            }
        });
    }
    private void isNext() {
        App.mediaPlayer2.stop();
        App.mediaPlayer2.reset();
        position = position + 1;
        try {
            if (SPUtils.get(PlayHistoryActivity.this, "flag", "").equals("2")) {
                playUrl64 = App.mList2.get(position).getPlayUrl64();
                PlayActivity.title = App.mList2.get(position).getTrackTitle();
                mPlayListActivityDownName.setText(PlayActivity.title);
            } else if (SPUtils.get(PlayHistoryActivity.this, "flag", "").equals("1")) {
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
    public void PlayHistoryActivity_Bank(View view) {
        finish();
    }
}
