package com.wanquan.mlmmx.mlmm.xmly.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.wanquan.mlmmx.mlmm.beans.UpDataInfoBeans;
import com.wanquan.mlmmx.mlmm.view.LogUtil;
import com.wanquan.mlmmx.mlmm.view.MyListView;
import com.wanquan.mlmmx.mlmm.xmly.adapter.PlayCollectionAdapter;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.xmly.adapter.PlayCollectionAdapter2;
import com.wanquan.mlmmx.mlmm.xmly.beans.PlayCollectionBeans;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.BatchAlbumList;
import com.ximalaya.ting.android.opensdk.model.album.UpdateBatch;
import com.ximalaya.ting.android.opensdk.model.album.UpdateBatchList;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

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
 * 描述：宝宝听听收藏
 * 作者：薛昌峰
 * 时间：2018.01.16
 */
public class PlayCollectionActivity extends BaseActivity {
    private LinearLayout mPlayCollectionLL;
    private ImageView mItemPlayCollectImageView;
    private TextView mItemPlayCollectName;
    private TextView mItemPlayCollectSize;
    private MyListView mPlayCollectionListView;
    private MyListView mPlayCollectionListView2;
    private RelativeLayout mItemPlayCollectRelativeLayout;
    private ImageView mPlayListActivityDownImg;
    private RelativeLayout mPlayListActivityDownRL;
    private TextView mPlayListActivityDownName;
    private ImageView mPlayListActivityDownPlay;
    private ImageView mPlayListActivityDownPlayNext;
    private PlayCollectionAdapter mPlayCollectionAdapter;
    private PlayCollectionAdapter2 mPlayCollectionAdapter2;
    private List<PlayCollectionBeans.DataBean> mList = new ArrayList<>();
    private List<PlayCollectionBeans.DataBean> mList2 = new ArrayList<>();
    private List<Album> mList3 = new ArrayList<>();
    private String playUrl64;
    private String album_img;
    private String album_id;
    private String album_title;
    private String str = "";

    @Override
    protected void onResume() {
        super.onResume();
        if (SPUtils.get(PlayCollectionActivity.this, "isStart", "").equals("1")) {
            if (App.mediaPlayer2 != null) {
                if (App.mediaPlayer2.isPlaying()) {
                    mPlayListActivityDownPlay.setImageDrawable(getResources().getDrawable(R.mipmap.minisuspend));
                } else {
                    mPlayListActivityDownPlay.setImageDrawable(getResources().getDrawable(R.mipmap.minibreak));
                }
            }
            if (SPUtils.get(PlayCollectionActivity.this, "flag", "").equals("1")) {
                mPlayListActivityDownRL.setVisibility(View.VISIBLE);
                mPlayListActivityDownName.setText(String.valueOf(SPUtils.get(PlayCollectionActivity.this, "PlayName", "")));
            } else if (SPUtils.get(PlayCollectionActivity.this, "flag", "").equals("2")) {
                mPlayListActivityDownRL.setVisibility(View.VISIBLE);
                mPlayListActivityDownName.setText(String.valueOf(SPUtils.get(PlayCollectionActivity.this, "PlayName", "")));
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
        manageUtils.setSystemBarTintManage(PlayCollectionActivity.this, R.color.tops);

//        initData();
        initListeners();

        mPlayCollectionAdapter = new PlayCollectionAdapter(this, mList2);
        mPlayCollectionListView.setAdapter(mPlayCollectionAdapter);

        mPlayCollectionAdapter2 = new PlayCollectionAdapter2(this, mList3);
        mPlayCollectionListView2.setAdapter(mPlayCollectionAdapter2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mList2.size() == 0 && mList3.size() == 0) {
                    mPlayCollectionLL.setVisibility(View.VISIBLE);
                } else {
                    mPlayCollectionLL.setVisibility(View.GONE);
                }
            }
        }, 1000);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_play_collection;
    }

    @Override
    public void initView() throws Exception {
        mPlayCollectionLL = (LinearLayout) findViewById(R.id.PlayCollection_ll);
        mPlayCollectionListView = (MyListView) findViewById(R.id.PlayCollection_ListView);
        mPlayCollectionListView2 = (MyListView) findViewById(R.id.PlayCollection_ListView2);
        mPlayListActivityDownRL = (RelativeLayout) findViewById(R.id.PlayListActivity_Down_RL);
        mPlayListActivityDownImg = (ImageView) findViewById(R.id.PlayListActivity_Down_img);
        mPlayListActivityDownName = (TextView) findViewById(R.id.PlayListActivity_Down_Name);
        mPlayListActivityDownPlay = (ImageView) findViewById(R.id.PlayListActivity_Down_Play);
        mPlayListActivityDownPlayNext = (ImageView) findViewById(R.id.PlayListActivity_Down_PlayNext);
    }

    private void initData() {
        HashMap<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("itfaceId", "066");
        hashMap2.put("token", SPUtils.get(PlayCollectionActivity.this, "token", ""));
        JSONObject jsonObject2 = new JSONObject(hashMap2);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject2.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<PlayCollectionBeans>(PlayCollectionActivity.this) {
                    @Override
                    public void onSuccess(PlayCollectionBeans mPlayCollectionBeans, Call call, Response response) {
                        if (mPlayCollectionBeans.getResultCode() == 1) {
                            mList.clear();
                            mList2.clear();

                            mList.addAll(mPlayCollectionBeans.getData());
                            mPlayCollectionAdapter.notifyDataSetChanged();

                            str = "";
                            for (int i = 0; i < mList.size(); i++) {
                                if ("1".equals(mList.get(i).getAlbum_id())) {
                                    mList2.add(mList.get(i));
                                } else {
                                    if (i != mList.size() - 2) {
                                        if (!mList.get(i).getAlbum_id().equals("")) {
                                            str = str + mList.get(i).getAlbum_id() + ",";
                                        }
                                    } else {
                                        str = str + mList.get(i).getAlbum_id();
                                    }
                                }
                            }
                            mList3.clear();
                            initXMLY();
                        } else {
                            App.ErrorToken(mPlayCollectionBeans.getResultCode(), mPlayCollectionBeans.getMsg());
                        }
                    }
                });
    }

    private void initXMLY() {
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.ALBUM_IDS, str);
        CommonRequest.getBatch(map, new IDataCallBack<BatchAlbumList>() {
            @Override
            public void onSuccess(@Nullable BatchAlbumList batchAlbumList) {
                mList3.clear();
                mList3.addAll(batchAlbumList.getAlbums());
                mPlayCollectionAdapter2.notifyDataSetChanged();
            }

            @Override
            public void onError(int i, String s) {
//                Toast.makeText(PlayCollectionActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initListeners() {
        mPlayListActivityDownRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.put(PlayCollectionActivity.this, "isDown", "1");
                Intent intent = new Intent(PlayCollectionActivity.this, PlayActivity.class);
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
                if (SPUtils.get(PlayCollectionActivity.this, "flag", "").equals("1")) {
                    if (position == App.mList.size() - 1) {
                        Toast.makeText(PlayCollectionActivity.this, "亲，已经是最后一首歌啦~", Toast.LENGTH_SHORT).show();
                    } else {
                        PlayActivity.img = App.mList.get(position + 1).getCoverUrlLarge();
                        isNext();
                    }
                } else if (SPUtils.get(PlayCollectionActivity.this, "flag", "").equals("2")) {
                    if (position == App.mList2.size() - 1) {
                        Toast.makeText(PlayCollectionActivity.this, "亲，已经是最后一首歌啦~", Toast.LENGTH_SHORT).show();
                    } else {
                        PlayActivity.img = App.mList2.get(position + 1).getCoverUrlLarge();
                        isNext();
                    }
                }
            }
        });
        mPlayCollectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (mList.get(position).getAlbum_id().equals("1")) {
                Intent intent = new Intent(PlayCollectionActivity.this, PlayHistoryActivity.class);
                intent.putExtra("like", "like");
                intent.putExtra("img", mList.get(position).getAlbum_img());
                startActivity(intent);
//                } else {
//                    Intent intent = new Intent(PlayCollectionActivity.this, PlayListActivity.class);
//                    intent.putExtra("id", mList.get(position).getAlbum_id());
//                    intent.putExtra("img", mList.get(position).getAlbum_img());
//                    intent.putExtra("title", mList.get(position).getAlbum_title());
//                    //intent.putExtra("content", mList.get(position).getAlbumIntro());
//                    startActivity(intent);
//                }
            }
        });
        mPlayCollectionListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (mList3.get(position).getId() == 1) {
//                    Intent intent = new Intent(PlayCollectionActivity.this, PlayHistoryActivity.class);
//                    intent.putExtra("like", "like");
//                    intent.putExtra("img", mList3.get(position).getCoverUrlLarge());
//                    startActivity(intent);
//                } else {
                Intent intent = new Intent(PlayCollectionActivity.this, PlayListActivity.class);
                intent.putExtra("id", String.valueOf(mList3.get(position).getId()));
                intent.putExtra("img", mList3.get(position).getCoverUrlLarge());
                intent.putExtra("title", mList3.get(position).getAlbumTitle());
                //intent.putExtra("content", mList.get(position).getAlbumIntro());
                startActivity(intent);
//                }
            }
        });
    }

    private void isNext() {
        App.mediaPlayer2.stop();
        App.mediaPlayer2.reset();
        position = position + 1;
        try {
            if (SPUtils.get(PlayCollectionActivity.this, "flag", "").equals("2")) {
                playUrl64 = App.mList2.get(position).getPlayUrl64();
                PlayActivity.title = App.mList2.get(position).getTrackTitle();
                mPlayListActivityDownName.setText(PlayActivity.title);
            } else if (SPUtils.get(PlayCollectionActivity.this, "flag", "").equals("1")) {
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

    public void PlayCollectionActivity_Bank(View view) {
        finish();
    }
}
