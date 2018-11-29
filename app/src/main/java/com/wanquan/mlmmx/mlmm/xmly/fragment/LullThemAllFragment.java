package com.wanquan.mlmmx.mlmm.xmly.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.fragment.BaseFragment;
import com.wanquan.mlmmx.mlmm.xmly.activity.PlayListActivity;
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
 * 描述：哄睡
 * 作者：薛昌峰
 * 时间：2018.01.05
 */

public class LullThemAllFragment extends BaseFragment {
    private SongFragmentAdapter mSongFragmentAdapter;
    private List<Album> mList = new ArrayList<>();
    private GridView mLullThemAllGridView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.slull_them_all_fragment, null);

        initViews();
        initData();
        initListeners();

        mSongFragmentAdapter = new SongFragmentAdapter(getContext(), mList);
        mLullThemAllGridView.setAdapter(mSongFragmentAdapter);
        return view;
    }

    private void initViews() {
        mLullThemAllGridView = (GridView) view.findViewById(R.id.LullThemAll_GridView);
    }

    private void initData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.CATEGORY_ID, "6");
        map.put(DTransferConstants.TAG_NAME, "哄睡");
        map.put(DTransferConstants.CALC_DIMENSION, "1");

        CommonRequest.getAlbumList(map, new IDataCallBack<AlbumList>() {
            @Override
            public void onSuccess(AlbumList object) {
//                Log.e("eeeee333", object.getAlbums().toString());
                mList.clear();
                mList.addAll(object.getAlbums());
                mSongFragmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code, String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initListeners() {
        mLullThemAllGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

