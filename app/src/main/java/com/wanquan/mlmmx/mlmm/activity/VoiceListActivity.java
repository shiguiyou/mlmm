package com.wanquan.mlmmx.mlmm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.AntenatalTimeTableAdapter;
import com.wanquan.mlmmx.mlmm.adapter.VoiceListAdapter;
import com.wanquan.mlmmx.mlmm.beans.VoiceListActivityBeans;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：语音播放列表
 * 作者：薛昌峰
 * 时间：2017.12.21
 */
public class VoiceListActivity extends BaseActivity {
    private ListView mVoiceListListView;
    List<VoiceListActivityBeans> mList = new ArrayList<>();
    VoiceListAdapter mVoiceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(VoiceListActivity.this, R.color.tops);

        initData();
        initListeners();

        mVoiceListAdapter = new VoiceListAdapter(this,mList);
        mVoiceListListView.setAdapter(mVoiceListAdapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_voice_list;
    }

    @Override
    public void initView() throws Exception {
        mVoiceListListView = (ListView) findViewById(R.id.VoiceList_ListView);
    }

    private void initData() {
    }

    private void initListeners() {
    }

    public void VoiceListActivity_Bank(View view) {
        finish();
    }
}
