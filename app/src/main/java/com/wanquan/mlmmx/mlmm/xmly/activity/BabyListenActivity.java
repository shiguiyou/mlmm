package com.wanquan.mlmmx.mlmm.xmly.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.Text;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.BaseActivity;
import com.wanquan.mlmmx.mlmm.adapter.MyAdapter;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.view.NoScrollViewPager;
import com.wanquan.mlmmx.mlmm.xmly.fragment.LullThemAllFragment;
import com.wanquan.mlmmx.mlmm.xmly.fragment.RecommendFragment;
import com.wanquan.mlmmx.mlmm.xmly.fragment.SongFragment;
import com.wanquan.mlmmx.mlmm.xmly.fragment.StoryFragment;
import com.wanquan.mlmmx.mlmm.xmly.fragment.TrainingFragment;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;
import com.ximalaya.ting.android.opensdk.model.tag.TagList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wanquan.mlmmx.mlmm.xmly.activity.PlayActivity.position;

/**
 * 描述：宝宝听听
 * 作者：薛昌峰
 * 时间：2018.01.05
 */
public class BabyListenActivity extends BaseActivity {
    private RadioGroup mBabyListenRadioGroup;
    private RadioButton mBabyListenRadioButton1;
    private RadioButton mBabyListenRadioButton2;
    private RadioButton mBabyListenRadioButton3;
    private RadioButton mBabyListenRadioButton4;
    private RadioButton mBabyListenRadioButton5;
    private NoScrollViewPager mBabyListenViewPager;
    //下面
    private RelativeLayout mPlayListActivityDownRL;
    private ImageView mPlayListActivityDownImg;
    private TextView mPlayListActivityDownName;
    private ImageView mPlayListActivityDownPlay;
    private ImageView mPlayListActivityDownPlayNext;

    RecommendFragment mRecommendFragment;
    SongFragment mSongFragment;
    StoryFragment mStoryFragment;
    LullThemAllFragment mLullThemAllFragment;
    TrainingFragment mTrainingFragment;

    List<Fragment> mList;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    MyAdapter mAdapter;
    private String playUrl64;

    @Override
    protected void onResume() {
        super.onResume();
        if (SPUtils.get(BabyListenActivity.this, "isStart", "").equals("1")) {
            if (App.mediaPlayer2 != null) {
                if (App.mediaPlayer2.isPlaying()) {
                    mPlayListActivityDownPlay.setImageDrawable(getResources().getDrawable(R.mipmap.minisuspend));
                } else {
                    mPlayListActivityDownPlay.setImageDrawable(getResources().getDrawable(R.mipmap.minibreak));
                }
            }
            if (SPUtils.get(BabyListenActivity.this, "flag", "").equals("1")) {
                mPlayListActivityDownRL.setVisibility(View.VISIBLE);
                mPlayListActivityDownName.setText(String.valueOf(SPUtils.get(BabyListenActivity.this, "PlayName", "")));
            } else if (SPUtils.get(BabyListenActivity.this, "flag", "").equals("2")) {
                mPlayListActivityDownRL.setVisibility(View.VISIBLE);
                mPlayListActivityDownName.setText(String.valueOf(SPUtils.get(BabyListenActivity.this, "PlayName", "")));
            }
        } else {
            mPlayListActivityDownRL.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(BabyListenActivity.this, R.color.tops);

//        initNetWorks();
        initData();
        initListeners();
        mBabyListenViewPager.setOffscreenPageLimit(1);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_baby_listen;
    }

    @Override
    public void initView() throws Exception {
        mBabyListenRadioGroup = (RadioGroup) findViewById(R.id.BabyListen_RadioGroup);
        mBabyListenRadioButton1 = (RadioButton) findViewById(R.id.BabyListen_RadioButton1);
        mBabyListenRadioButton2 = (RadioButton) findViewById(R.id.BabyListen_RadioButton2);
        mBabyListenRadioButton3 = (RadioButton) findViewById(R.id.BabyListen_RadioButton3);
        mBabyListenRadioButton4 = (RadioButton) findViewById(R.id.BabyListen_RadioButton4);
        mBabyListenRadioButton5 = (RadioButton) findViewById(R.id.BabyListen_RadioButton5);
        mBabyListenViewPager = (NoScrollViewPager) findViewById(R.id.BabyListen_ViewPager);
        mPlayListActivityDownRL = (RelativeLayout) findViewById(R.id.PlayListActivity_Down_RL);
        mPlayListActivityDownImg = (ImageView) findViewById(R.id.PlayListActivity_Down_img);
        mPlayListActivityDownName = (TextView) findViewById(R.id.PlayListActivity_Down_Name);
        mPlayListActivityDownPlay = (ImageView) findViewById(R.id.PlayListActivity_Down_Play);
        mPlayListActivityDownPlayNext = (ImageView) findViewById(R.id.PlayListActivity_Down_PlayNext);
    }

    private void initNetWorks() {
        Map<String, String> map = new HashMap<String, String>();
        CommonRequest.getCategories(map, new IDataCallBack<CategoryList>() {
            @Override
            public void onSuccess(CategoryList object) {
                Log.e("eeeee111", object.getCategories().toString());
//                text.setText(object.getCategories().toString());
//                Map<String, String> map = new HashMap<String, String>();
//                map.put(DTransferConstants.CATEGORY_ID, "6");
//                map.put(DTransferConstants.TYPE, "1");
//                CommonRequest.getTags(map, new IDataCallBack<TagList>() {
//                    @Override
//                    public void onSuccess(TagList object) {
//                        Log.e("eeeee222",object.getTagList().toString());
//
//                        Map<String, String> map = new HashMap<String, String>();
//                        map.put(DTransferConstants.CATEGORY_ID, "6");
//                        map.put(DTransferConstants.TAG_NAME, "儿歌");
//                        map.put(DTransferConstants.CALC_DIMENSION ,"1");
//                        CommonRequest.getAlbumList(map, new IDataCallBack<AlbumList>() {
//                            @Override
//                            public void onSuccess(AlbumList object) {
//                                Log.e("eeeee333",object.getAlbums().toString());
//                            }
//
//                            @Override
//                            public void onError(int code, String message) {
//                                Toast.makeText(BabyListenActivity.this, message, Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onError(int code, String message) {
//                        Toast.makeText(BabyListenActivity.this, message, Toast.LENGTH_SHORT).show();
//                    }
//                });
            }

            @Override
            public void onError(int code, String message) {
                Toast.makeText(BabyListenActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        mList = new ArrayList<>();
        mRecommendFragment = new RecommendFragment();
        mSongFragment = new SongFragment();
        mStoryFragment = new StoryFragment();
        mLullThemAllFragment = new LullThemAllFragment();
        mTrainingFragment = new TrainingFragment();

        mList.add(mRecommendFragment);
        mList.add(mSongFragment);
        mList.add(mStoryFragment);
        mList.add(mLullThemAllFragment);
        mList.add(mTrainingFragment);

        mFragmentManager = getSupportFragmentManager();
        mAdapter = new MyAdapter(mFragmentManager, mList);
        mBabyListenViewPager.setAdapter(mAdapter);
    }

    private void initListeners() {
        mPlayListActivityDownRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SPUtils.put(BabyListenActivity.this, "flag", "1");
                SPUtils.put(BabyListenActivity.this, "isDown", "1");
                Intent intent = new Intent(BabyListenActivity.this, PlayActivity.class);
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
                if (SPUtils.get(BabyListenActivity.this, "flag", "").equals("1")) {
                    if (position == App.mList.size() - 1) {
                        Toast.makeText(BabyListenActivity.this, "亲，已经是最后一首歌啦~", Toast.LENGTH_SHORT).show();
                    } else {
                        PlayActivity.img = App.mList.get(position + 1).getCoverUrlLarge();
                        isNext();
                    }
                } else if (SPUtils.get(BabyListenActivity.this, "flag", "").equals("2")) {
                    if (position == App.mList2.size() - 1) {
                        Toast.makeText(BabyListenActivity.this, "亲，已经是最后一首歌啦~", Toast.LENGTH_SHORT).show();
                    } else {
                        PlayActivity.img = App.mList2.get(position + 1).getCoverUrlLarge();
                        isNext();
                    }
                }
            }
        });
        mBabyListenRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                resetViewPager(checkedId);
            }
        });
        //滑动ViewPage的时候及时修改底部导航栏对应的图标
        mBabyListenViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //根据当前位置设置默认选中单选按钮
                resetRadioButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void isNext() {
        App.mediaPlayer2.stop();
        App.mediaPlayer2.reset();
        position = position + 1;
        try {
            if (SPUtils.get(BabyListenActivity.this, "flag", "").equals("2")) {
                playUrl64 = App.mList2.get(position).getPlayUrl64();
                PlayActivity.title = App.mList2.get(position).getTrackTitle();
                mPlayListActivityDownName.setText(PlayActivity.title);
            } else if (SPUtils.get(BabyListenActivity.this, "flag", "").equals("1")) {
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

    private void resetViewPager(int checkedId) {
        switch (checkedId) {
            case R.id.BabyListen_RadioButton1:
                mBabyListenViewPager.setCurrentItem(0);
                mBabyListenRadioButton1.setTextColor(getResources().getColor(R.color.red));
                mBabyListenRadioButton2.setTextColor(getResources().getColor(R.color.black));
                mBabyListenRadioButton3.setTextColor(getResources().getColor(R.color.black));
                mBabyListenRadioButton4.setTextColor(getResources().getColor(R.color.black));
                mBabyListenRadioButton5.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.BabyListen_RadioButton2:
                mBabyListenViewPager.setCurrentItem(1);
                mBabyListenRadioButton1.setTextColor(getResources().getColor(R.color.black));
                mBabyListenRadioButton2.setTextColor(getResources().getColor(R.color.red));
                mBabyListenRadioButton3.setTextColor(getResources().getColor(R.color.black));
                mBabyListenRadioButton4.setTextColor(getResources().getColor(R.color.black));
                mBabyListenRadioButton5.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.BabyListen_RadioButton3:
                mBabyListenViewPager.setCurrentItem(2);
                mBabyListenRadioButton1.setTextColor(getResources().getColor(R.color.black));
                mBabyListenRadioButton2.setTextColor(getResources().getColor(R.color.black));
                mBabyListenRadioButton3.setTextColor(getResources().getColor(R.color.red));
                mBabyListenRadioButton4.setTextColor(getResources().getColor(R.color.black));
                mBabyListenRadioButton5.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.BabyListen_RadioButton4:
                mBabyListenViewPager.setCurrentItem(3);
                mBabyListenRadioButton1.setTextColor(getResources().getColor(R.color.black));
                mBabyListenRadioButton2.setTextColor(getResources().getColor(R.color.black));
                mBabyListenRadioButton3.setTextColor(getResources().getColor(R.color.black));
                mBabyListenRadioButton4.setTextColor(getResources().getColor(R.color.red));
                mBabyListenRadioButton5.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.BabyListen_RadioButton5:
                mBabyListenViewPager.setCurrentItem(4);
                mBabyListenRadioButton1.setTextColor(getResources().getColor(R.color.black));
                mBabyListenRadioButton2.setTextColor(getResources().getColor(R.color.black));
                mBabyListenRadioButton3.setTextColor(getResources().getColor(R.color.black));
                mBabyListenRadioButton4.setTextColor(getResources().getColor(R.color.black));
                mBabyListenRadioButton5.setTextColor(getResources().getColor(R.color.red));
                break;
        }
    }

    private void resetRadioButton(int position) {
        //获取position位置处对于的单选按钮
        RadioButton radioButton = (RadioButton) mBabyListenRadioGroup.getChildAt(position);
        //设置当前单选按钮默认选中
        radioButton.setChecked(true);
    }

    public void BabyListenActivity_Bank(View view) {
        finish();
    }
}
