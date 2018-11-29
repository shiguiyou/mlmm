package com.wanquan.mlmmx.mlmm.xmly.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.BaseActivity;
import com.wanquan.mlmmx.mlmm.phone.MyGridView;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.view.MyScrollView;
import com.wanquan.mlmmx.mlmm.xmly.adapter.SongFragmentAdapter;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wanquan.mlmmx.mlmm.xmly.activity.PlayActivity.position;

/**
 * 描述：猜你喜欢(更多)
 * 作者：薛昌峰
 * 时间：2017.12.23
 */
public class YouLikeActivity extends BaseActivity {
    private RelativeLayout mPlayListActivityDownRL;
    private ImageView mYouLikeActivityDownImg;
    private TextView mYouLikeActivityDownName;
    private ImageView mYouLikeActivityDownPlay;
    private ImageView mYouLikeActivityDownPlayNext;
    private GridView mYouLikeActivityGridView;
    private SongFragmentAdapter mSongFragmentAdapter;
    private List<Album> mList = new ArrayList<>();
    private String playUrl64;

    @Override
    protected void onResume() {
        super.onResume();
        if (SPUtils.get(this, "isStart", "").equals("1")) {
            if (App.mediaPlayer2 != null) {
                if (App.mediaPlayer2.isPlaying()) {
                    mYouLikeActivityDownPlay.setImageDrawable(getResources().getDrawable(R.mipmap.minisuspend));
                } else {
                    mYouLikeActivityDownPlay.setImageDrawable(getResources().getDrawable(R.mipmap.minibreak));
                }
            }
            if (SPUtils.get(YouLikeActivity.this, "flag", "").equals("1")) {
                mPlayListActivityDownRL.setVisibility(View.VISIBLE);
                mYouLikeActivityDownName.setText(String.valueOf(SPUtils.get(YouLikeActivity.this, "PlayName", "")));
            } else if (SPUtils.get(YouLikeActivity.this, "flag", "").equals("2")) {
                mPlayListActivityDownRL.setVisibility(View.VISIBLE);
                mYouLikeActivityDownName.setText(String.valueOf(SPUtils.get(YouLikeActivity.this, "PlayName", "")));
            }
        } else {
            mPlayListActivityDownRL.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(YouLikeActivity.this, R.color.tops);

        initData();
        initListeners();

        mSongFragmentAdapter = new SongFragmentAdapter(this, mList);
        mYouLikeActivityGridView.setAdapter(mSongFragmentAdapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_you_like;
    }

    private void initData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.CATEGORY_ID, "6");
        map.put(DTransferConstants.CALC_DIMENSION, "1");

        CommonRequest.getUpToDateAlbums(map, new IDataCallBack<AlbumList>() {
            @Override
            public void onSuccess(AlbumList object) {
                Log.e("eeeee--推荐", object.getAlbums().toString());
//                mList.clear();
                mList.addAll(object.getAlbums());
                mSongFragmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code, String message) {
//                Toast.makeText(getContext(), message + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initView() throws Exception {
        mPlayListActivityDownRL = (RelativeLayout) findViewById(R.id.PlayListActivity_Down_RL);
        mYouLikeActivityDownImg = (ImageView) findViewById(R.id.YouLikeActivity_Down_img);
        mYouLikeActivityDownName = (TextView) findViewById(R.id.YouLikeActivity_Down_Name);
        mYouLikeActivityDownPlay = (ImageView) findViewById(R.id.YouLikeActivity_Down_Play);
        mYouLikeActivityDownPlayNext = (ImageView) findViewById(R.id.YouLikeActivity_Down_PlayNext);
        mYouLikeActivityGridView = (GridView) findViewById(R.id.YouLikeActivity_GridView);
    }

    private void initListeners() {
        mYouLikeActivityGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(YouLikeActivity.this, PlayListActivity.class);
                intent.putExtra("id", String.valueOf(mList.get(position).getId()));
                intent.putExtra("img", mList.get(position).getCoverUrlLarge());
                intent.putExtra("title", mList.get(position).getAlbumTitle());
                intent.putExtra("content", mList.get(position).getAlbumIntro());
                startActivity(intent);
            }
        });
        mPlayListActivityDownRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SPUtils.put(BabyListenActivity.this, "flag", "1");
                SPUtils.put(YouLikeActivity.this, "isDown", "1");
                Intent intent = new Intent(YouLikeActivity.this, PlayActivity.class);
                intent.putExtra("mp3", PlayActivity.playUrl64);
                intent.putExtra("title", PlayActivity.title);
                intent.putExtra("position", PlayActivity.position);
                intent.putExtra("img", PlayActivity.img);
//                intent.putExtra("dataId", id);
                intent.putExtra("id", PlayActivity.id);
                startActivity(intent);
            }
        });
        mYouLikeActivityDownPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.mediaPlayer2.isPlaying()) {
                    mYouLikeActivityDownPlay.setImageDrawable(getResources().getDrawable(R.mipmap.minibreak));
                    App.mediaPlayer2.pause();
                } else {
                    App.mediaPlayer2.start();
                    mYouLikeActivityDownPlay.setImageDrawable(getResources().getDrawable(R.mipmap.minisuspend));
                }
            }
        });
        mYouLikeActivityDownPlayNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SPUtils.get(YouLikeActivity.this, "flag", "").equals("1")) {
                    if (position == App.mList.size() - 1) {
                        Toast.makeText(YouLikeActivity.this, "亲，已经是最后一首歌啦~", Toast.LENGTH_SHORT).show();
                    } else {
                        PlayActivity.img = App.mList.get(position + 1).getCoverUrlLarge();
                        isNext();
                    }
                } else if (SPUtils.get(YouLikeActivity.this, "flag", "").equals("2")) {
                    if (position == App.mList2.size() - 1) {
                        Toast.makeText(YouLikeActivity.this, "亲，已经是最后一首歌啦~", Toast.LENGTH_SHORT).show();
                    } else {
                        PlayActivity.img = App.mList2.get(position + 1).getCoverUrlLarge();
                        isNext();
                    }
                }
            }
        });
    }

    private void isNext() {
        App.mediaPlayer2.stop();
        App.mediaPlayer2.reset();
        position = position + 1;
        try {
            if (SPUtils.get(YouLikeActivity.this, "flag", "").equals("2")) {
                playUrl64 = App.mList2.get(position).getPlayUrl64();
                PlayActivity.title = App.mList2.get(position).getTrackTitle();
                mYouLikeActivityDownName.setText(PlayActivity.title);
            } else if (SPUtils.get(YouLikeActivity.this, "flag", "").equals("1")) {
                playUrl64 = App.mList.get(position).getPlayUrl64();
                PlayActivity.title = App.mList.get(position).getTrackTitle();
                mYouLikeActivityDownName.setText(PlayActivity.title);
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

    public void YouLikeActivity_Bank(View view) {
        finish();
    }
}
