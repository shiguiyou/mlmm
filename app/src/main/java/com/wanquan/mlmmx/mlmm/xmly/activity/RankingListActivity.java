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

import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.BaseActivity;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.xmly.adapter.RankingListAdapter;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.ranks.Rank;
import com.ximalaya.ting.android.opensdk.model.ranks.RankAlbumList;
import com.ximalaya.ting.android.opensdk.model.ranks.RankList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wanquan.mlmmx.mlmm.xmly.activity.PlayActivity.position;

/**
 * 描述：排行榜
 * 作者：薛昌峰
 * 时间：2018.01.05
 */
public class RankingListActivity extends BaseActivity {
    private List<Rank> mList = new ArrayList<>();
    private List<Album> mList2 = new ArrayList<>();
    private RankingListAdapter mRankingListAdapter;
    private ListView mRankingListListView;
    private RelativeLayout mPlayListActivityDownRL;
    private ImageView mPlayListActivityDownImg;
    private TextView mPlayListActivityDownName;
    private ImageView mPlayListActivityDownPlay;
    private ImageView mPlayListActivityDownPlayNext;
    private String rankKey;
    private String playUrl64;

    @Override
    protected void onResume() {
        super.onResume();
        if (SPUtils.get(RankingListActivity.this, "isStart", "").equals("1")) {
            if (App.mediaPlayer2 != null) {
                if (App.mediaPlayer2.isPlaying()) {
                    mPlayListActivityDownPlay.setImageDrawable(getResources().getDrawable(R.mipmap.minisuspend));
                } else {
                    mPlayListActivityDownPlay.setImageDrawable(getResources().getDrawable(R.mipmap.minibreak));
                }
            }
            if (SPUtils.get(RankingListActivity.this, "flag", "").equals("1")) {
                mPlayListActivityDownRL.setVisibility(View.VISIBLE);
                mPlayListActivityDownName.setText(String.valueOf(SPUtils.get(RankingListActivity.this, "PlayName", "")));
            } else if (SPUtils.get(RankingListActivity.this, "flag", "").equals("2")) {
                mPlayListActivityDownRL.setVisibility(View.VISIBLE);
                mPlayListActivityDownName.setText(String.valueOf(SPUtils.get(RankingListActivity.this, "PlayName", "")));
            }
        } else {
            mPlayListActivityDownRL.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(RankingListActivity.this, R.color.tops);

        initData();
        initListeners();

        mRankingListAdapter = new RankingListAdapter(this, mList2);
        mRankingListListView.setAdapter(mRankingListAdapter);

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_ranking_list;
    }

    @Override
    public void initView() throws Exception {
        mRankingListListView = (ListView) findViewById(R.id.RankingList_ListView);
        mPlayListActivityDownRL = (RelativeLayout) findViewById(R.id.PlayListActivity_Down_RL);
        mPlayListActivityDownImg = (ImageView) findViewById(R.id.PlayListActivity_Down_img);
        mPlayListActivityDownName = (TextView) findViewById(R.id.PlayListActivity_Down_Name);
        mPlayListActivityDownPlay = (ImageView) findViewById(R.id.PlayListActivity_Down_Play);
        mPlayListActivityDownPlayNext = (ImageView) findViewById(R.id.PlayListActivity_Down_PlayNext);
    }

    private void initData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.RANK_TYPE, "1");

        CommonRequest.getRankList(map, new IDataCallBack<RankList>() {
            @Override
            public void onSuccess(RankList object) {
                Log.e("eeeee--排行榜", object.getRankList().toString());
                mList.clear();
                mList.addAll(object.getRankList());
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).getCategoryId() == 6) {
                        rankKey = mList.get(i).getRankKey();
                        initNetWork();
                        break;
                    }
                }
            }

            private void initNetWork() {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.RANK_KEY, rankKey);

                CommonRequest.getRankAlbumList(map, new IDataCallBack<RankAlbumList>() {
                    @Override
                    public void onSuccess(RankAlbumList object2) {
//                        Log.e("eeeee--排行榜", object2.getRankAlbumList().toString());
                        mList2.clear();
                        mList2.addAll(object2.getRankAlbumList());
                        mRankingListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(int code, String message) {
                        Toast.makeText(RankingListActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(int code, String message) {
                Toast.makeText(RankingListActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initListeners() {
        mRankingListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RankingListActivity.this, PlayListActivity.class);
                intent.putExtra("id", String.valueOf(mList2.get(position).getId()));
                intent.putExtra("img", mList2.get(position).getCoverUrlLarge());
                intent.putExtra("title", mList2.get(position).getAlbumTitle());
                intent.putExtra("content", mList2.get(position).getSpeakerTitle());
                startActivity(intent);
            }
        });
        mPlayListActivityDownRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.put(RankingListActivity.this, "isDown", "1");
                Intent intent = new Intent(RankingListActivity.this, PlayActivity.class);
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
                if (SPUtils.get(RankingListActivity.this, "flag", "").equals("1")) {
                    if (position == App.mList.size() - 1) {
                        Toast.makeText(RankingListActivity.this, "亲，已经是最后一首歌啦~", Toast.LENGTH_SHORT).show();
                    } else {
                        PlayActivity.img = App.mList.get(position + 1).getCoverUrlLarge();
                        isNext();
                    }
                } else if (SPUtils.get(RankingListActivity.this, "flag", "").equals("2")) {
                    if (position == App.mList2.size() - 1) {
                        Toast.makeText(RankingListActivity.this, "亲，已经是最后一首歌啦~", Toast.LENGTH_SHORT).show();
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
            if (SPUtils.get(RankingListActivity.this, "flag", "").equals("2")) {
                playUrl64 = App.mList2.get(position).getPlayUrl64();
                PlayActivity.title = App.mList2.get(position).getTrackTitle();
                mPlayListActivityDownName.setText(PlayActivity.title);
            } else if (SPUtils.get(RankingListActivity.this, "flag", "").equals("1")) {
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
    public void RankingListActivity_Bank(View view) {
        finish();
    }
}
