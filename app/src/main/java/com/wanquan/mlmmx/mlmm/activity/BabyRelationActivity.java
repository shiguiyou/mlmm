package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.BabyRelationAdapter;
import com.wanquan.mlmmx.mlmm.beans.BabyRelationBeans;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：你与宝宝的关系
 * 作者：薛昌峰
 * 时间：2017.01.30
 */
public class BabyRelationActivity extends BaseActivity {
    private ListView mBabyRelationListView;
    private List<String> mList;
    private BabyRelationAdapter mBabyRelationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(BabyRelationActivity.this, R.color.tops);

        initData();
        initListeners();

        mBabyRelationAdapter = new BabyRelationAdapter(mList, this);
        mBabyRelationListView.setAdapter(mBabyRelationAdapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_baby_relation;
    }

    @Override
    public void initView() throws Exception {
        mBabyRelationListView = (ListView) findViewById(R.id.BabyRelation_ListView);
    }

    private void initData() {
        mList = new ArrayList();
        mList.add("爷爷");
        mList.add("奶奶");
        mList.add("姑姑");
        mList.add("叔叔");
        mList.add("阿姨");
        mList.add("舅舅");
        mList.add("外婆");
        mList.add("外公");
        mList.add("其他");
    }

    private void initListeners() {
        mBabyRelationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BabyRelationActivity.this, BabyMessageActivity.class);
                intent.putExtra("name", mList.get(position).toString());
                setResult(1, intent);
                finish();
            }
        });
    }

    public void BabyRelationActivity_Bank(View view) {
        finish();
    }
}
