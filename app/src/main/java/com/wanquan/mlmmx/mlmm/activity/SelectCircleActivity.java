package com.wanquan.mlmmx.mlmm.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentThreeBeans;
import com.wanquan.mlmmx.mlmm.beans.CircleFragmentThreessBeans;
import com.wanquan.mlmmx.mlmm.beans.SelectCircleBeans;
import com.wanquan.mlmmx.mlmm.beans.SendInvitationActivityBeans;
import com.wanquan.mlmmx.mlmm.fragment.CircleFragmentThreeLiftAdapter;
import com.wanquan.mlmmx.mlmm.fragment.CircleFragmentThreeRightAdapter;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MyDialog_Views;
import com.wanquan.mlmmx.mlmm.view.MyListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：选择圈子
 * 作者：薛昌峰
 * 时间：2018.06.13
 */
public class SelectCircleActivity extends BaseActivity {
    private ImageView mSelectCircleActivityClose;
    private MyListView mSelectCircleActivityMyListViewLeft;
    private LinearLayout mSelectCircleActivitySearch;
    private ListView mSelectCircleActivityMyListViewRight;
    private CircleFragmentThreeLiftAdapter mCircleFragmentThreeLiftAdapter;
    private CircleFragmentThreeRightAdapter mCircleFragmentThreeRightAdapter;
    private List<CircleFragmentThreeBeans.DataBean> mList1 = new ArrayList<>();
    private List<CircleFragmentThreessBeans.DataBean> mList2 = new ArrayList<>();
    private String flag;
    private String url;
    private String title;
    private Boolean recharge_flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(SelectCircleActivity.this, R.color.black);

        flag = getIntent().getStringExtra("flag");
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");


        initData();
        initListeners();

        mCircleFragmentThreeLiftAdapter = new CircleFragmentThreeLiftAdapter(mList1, this, 0);
        mSelectCircleActivityMyListViewLeft.setAdapter(mCircleFragmentThreeLiftAdapter);

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_select_circle;
    }

    @Override
    public void initView() throws Exception {
        mSelectCircleActivityClose = (ImageView) findViewById(R.id.SelectCircleActivity_Close);
        mSelectCircleActivityMyListViewLeft = (MyListView) findViewById(R.id.SelectCircleActivity_MyListView_Left);
        mSelectCircleActivitySearch = (LinearLayout) findViewById(R.id.SelectCircleActivity_Search);
        mSelectCircleActivityMyListViewRight = (ListView) findViewById(R.id.SelectCircleActivity_MyListView_Right);
    }

    private void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "108");
        hashMap.put("token", SPUtils.get(this, "token", ""));
        hashMap.put("parentId", 0);
        hashMap.put("related", "1");
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<CircleFragmentThreeBeans>(this) {
                    @Override
                    public void onSuccess(CircleFragmentThreeBeans mCircleFragmentThreeBeans, Call call, Response response) {
                        if (mCircleFragmentThreeBeans.getResultCode() == 1) {
                            mList1.clear();
                            mList1.addAll(mCircleFragmentThreeBeans.getData());
                            mCircleFragmentThreeLiftAdapter.notifyDataSetChanged();
                            initNetWork(mList1.get(0).getId());
                        } else {
                            App.ErrorToken(mCircleFragmentThreeBeans.getResultCode(), mCircleFragmentThreeBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
        mSelectCircleActivityClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSelectCircleActivityMyListViewLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCircleFragmentThreeLiftAdapter = new CircleFragmentThreeLiftAdapter(mList1, SelectCircleActivity.this, position);
                mSelectCircleActivityMyListViewLeft.setAdapter(mCircleFragmentThreeLiftAdapter);
                mSelectCircleActivityMyListViewLeft.setSelectionFromTop(position, 0);
                initNetWork(mList1.get(position).getId());
            }
        });

        mSelectCircleActivityMyListViewRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (flag != null) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("itfaceId", "133");
                    hashMap.put("token", SPUtils.get(SelectCircleActivity.this, "token", ""));
                    hashMap.put("title", mList2.get(position).getName());
                    hashMap.put("type", mList2.get(position).getId());
                    hashMap.put("content", title);
                    hashMap.put("link", url);

//                    Log.e("ffsdfs",mList2.get(position).getName());
//                    Log.e("ffsdfs", String.valueOf(mList2.get(position).getId()));
//                    Log.e("ffsdfs2", title + "xcf");
//                    Log.e("ffsdfs3", url + "xcf");

                    JSONObject jsonObject = new JSONObject(hashMap);

                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<SelectCircleBeans>(SelectCircleActivity.this) {
                                @Override
                                public void onSuccess(final SelectCircleBeans mSelectCircleBeans, Call call, Response response) {
                                    if (mSelectCircleBeans.getResultCode() == 1) {
                                        final Dialog dialog = new MyDialog_Views(SelectCircleActivity.this, R.style.MyDialog);
                                        dialog.setCancelable(false);
                                        dialog.show();
                                        MyDialog_Views myDialog_views = new MyDialog_Views(SelectCircleActivity.this, "正在上传...", "");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (recharge_flag) {
                                                    MyDialog_Views myDialog_views = new MyDialog_Views(SelectCircleActivity.this, "发布成功", "success");
                                                } else {
                                                    MyDialog_Views myDialog_views = new MyDialog_Views(SelectCircleActivity.this, "发布失败", "fail");
                                                }
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        dialog.dismiss();
//                                                        startActivity(new Intent(SelectCircleActivity.this,MainActivity.class));
                                                        finish();
                                                        if (mSelectCircleBeans.getData().getGetIntegral() != 0) {
                                                            Toast.makeText(SelectCircleActivity.this, "发帖成功积分+" + mSelectCircleBeans.getData().getGetIntegral(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }, 2000);
                                            }
                                        }, 1000);
                                    } else {
                                        App.ErrorToken(mSelectCircleBeans.getResultCode(), mSelectCircleBeans.getMsg());
                                    }
                                }
                            });
                } else {
                    Intent intent = new Intent(SelectCircleActivity.this, SendInvitationActivity.class);
                    intent.putExtra("name", mList2.get(position).getName());
                    intent.putExtra("id", String.valueOf(mList2.get(position).getId()));
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void initNetWork(final int id) {
        Log.e("xxxxxid2", String.valueOf(id));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "108");
        hashMap.put("token", SPUtils.get(this, "token", ""));
        hashMap.put("parentId", id);
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<CircleFragmentThreessBeans>(this) {
                    @Override
                    public void onSuccess(CircleFragmentThreessBeans mCircleFragmentThreessBeans, Call call, Response response) {
                        if (mCircleFragmentThreessBeans.getResultCode() == 1) {
                            mList2.clear();
                            mList2.addAll(mCircleFragmentThreessBeans.getData());

                            mCircleFragmentThreeRightAdapter = new CircleFragmentThreeRightAdapter(mList2, SelectCircleActivity.this, 0);
                            mSelectCircleActivityMyListViewRight.setAdapter(mCircleFragmentThreeRightAdapter);
                        } else {
                            App.ErrorToken(mCircleFragmentThreessBeans.getResultCode(), mCircleFragmentThreessBeans.getMsg());
                        }
                    }
                });
    }
}
