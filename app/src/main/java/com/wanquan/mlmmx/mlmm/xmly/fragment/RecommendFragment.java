package com.wanquan.mlmmx.mlmm.xmly.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.fragment.BaseFragment;
import com.wanquan.mlmmx.mlmm.phone.MyGridView;
import com.wanquan.mlmmx.mlmm.xmly.activity.PlayCollectionActivity;
import com.wanquan.mlmmx.mlmm.xmly.activity.PlayHistoryActivity;
import com.wanquan.mlmmx.mlmm.xmly.activity.PlayListActivity;
import com.wanquan.mlmmx.mlmm.xmly.activity.RankingListActivity;
import com.wanquan.mlmmx.mlmm.xmly.activity.YouLikeActivity;
import com.wanquan.mlmmx.mlmm.xmly.adapter.SongFragmentAdapter;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：推荐
 * 作者：薛昌峰
 * 时间：2018.01.05
 */
public class RecommendFragment extends BaseFragment {
    private ImageView mRecommendFragmentBanner;
    private LinearLayout mRecommendFragmentL1;
    private LinearLayout mRecommendFragmentL2;
    private LinearLayout mRecommendFragmentL3;
    private LinearLayout mRecommendFragmentL4;
    private TextView mRecommendFragmentTV;
    private MyGridView mRecommendFragmentGridView;
    private SongFragmentAdapter mSongFragmentAdapter;
    private List<Album> mList = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recommend_fragment, null);

        initViews();
        initData();
        initListeners();
        mSongFragmentAdapter = new SongFragmentAdapter(getContext(), mList);
        mRecommendFragmentGridView.setAdapter(mSongFragmentAdapter);
        return view;
    }

    private void initViews() {
        mRecommendFragmentBanner = (ImageView) view.findViewById(R.id.RecommendFragment_Banner);
        mRecommendFragmentL1 = (LinearLayout) view.findViewById(R.id.RecommendFragment_l1);
        mRecommendFragmentL2 = (LinearLayout) view.findViewById(R.id.RecommendFragment_l2);
        mRecommendFragmentL3 = (LinearLayout) view.findViewById(R.id.RecommendFragment_l3);
        mRecommendFragmentL4 = (LinearLayout) view.findViewById(R.id.RecommendFragment_l4);
        mRecommendFragmentTV = (TextView) view.findViewById(R.id.RecommendFragment_TV);
        mRecommendFragmentGridView = (MyGridView) view.findViewById(R.id.RecommendFragment_GridView);
    }

    private void initData() {
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("itfaceId", "015");
//        JSONObject jsonObject = new JSONObject(hashMap);
//
//        OkGo.post(UrlContent.URL).tag(getActivity())
//                .upJson(jsonObject.toString())
//                .connTimeOut(10_000)
//                .execute(new CustomCallBackNoLoading<BannerBeans>(getContext()) {
//                    @Override
//                    public void onSuccess(BannerBeans bannerBeans, Call call, Response response) {
//                        if (bannerBeans.getResultCode() == 1) {
//                            images.clear();
//                            for (int i = 0; i < bannerBeans.getData().size(); i++) {
//                                images.add(bannerBeans.getData().get(i).getImgUrl());
//                            }
//                            setBanner(images);
//                        } else {
//                            Toast.makeText(getContext(), bannerBeans.getMsg(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        Toast.makeText(getContext(), "服务器连接异常，请稍后重试", Toast.LENGTH_SHORT).show();
//                        super.onError(call, response, e);
//                    }
//                });

        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.CATEGORY_ID, "6");
        map.put(DTransferConstants.CALC_DIMENSION, "1");

        CommonRequest.getUpToDateAlbums(map, new IDataCallBack<AlbumList>() {
            @Override
            public void onSuccess(AlbumList object) {
//                Log.e("eeeee--推荐", object.getAlbums().toString());
                mList.clear();
                mList.addAll(object.getAlbums());
                mSongFragmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code, String message) {
//                Toast.makeText(getContext(), message + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //    private void setBanner(List<String> imagesUrls) {
//        //设置banner样式
//        mRecommendFragmentBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
//        //设置图片加载器
//        mRecommendFragmentBanner.setImageLoader(new GlideImageLoader());
//        //设置图片集合
//        mRecommendFragmentBanner.setImages(imagesUrls);
//        //设置banner动画效果
////        mRecommendFragmentBanner.setBannerAnimation(Transformer.DepthPage);
//        //设置标题集合（当banner样式有显示title时）
////        mRecommendFragmentBanner.setBannerTitles(titles);
//        //设置自动轮播，默认为true
//        mRecommendFragmentBanner.isAutoPlay(true);
//        //设置轮播时间
//        mRecommendFragmentBanner.setDelayTime(3000);
//        //设置指示器位置（当banner模式中有指示器时）
//        mRecommendFragmentBanner.setIndicatorGravity(BannerConfig.CENTER);
//        //banner设置方法全部调用完毕时最后调用
//        mRecommendFragmentBanner.start();
//    }
    private void initListeners() {
        //猜你喜欢更多
        mRecommendFragmentTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), YouLikeActivity.class);
                startActivity(intent);
            }
        });
        mRecommendFragmentBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RankingListActivity.class);
                startActivity(intent);
            }
        });
        mRecommendFragmentL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mRecommendFragmentL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RankingListActivity.class);
                startActivity(intent);
            }
        });
        mRecommendFragmentL3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PlayCollectionActivity.class);
                startActivity(intent);
            }
        });
        mRecommendFragmentL4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PlayHistoryActivity.class);
                intent.putExtra("like", "likes");
                startActivity(intent);
            }
        });
        mRecommendFragmentGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), PlayListActivity.class);
                intent.putExtra("id", String.valueOf(mList.get(position).getId()));
                intent.putExtra("img", mList.get(position).getCoverUrlLarge());
                intent.putExtra("title", mList.get(position).getAlbumTitle());
                intent.putExtra("content", mList.get(position).getAlbumIntro());
                startActivity(intent);
            }
        });
    }
}
