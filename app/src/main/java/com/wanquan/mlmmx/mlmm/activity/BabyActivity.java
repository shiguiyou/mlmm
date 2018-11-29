package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.BabyAdapter;
import com.wanquan.mlmmx.mlmm.beans.BabyActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.fragment.HomeFragment;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.sqlite.DataBaseHelper;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.greenrobot.eventbus.EventBus;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：亲友团__宝宝
 * 作者：薛昌峰
 * 时间：2018.01.30
 */
public class BabyActivity extends BaseActivity implements BabyAdapter.ImgFinishInterface {
    private TextView mBabyActivityAddBaby;
    private ListView mBabyActivityListView;
    private BabyAdapter mBabyAdapter;
    private List<BabyActivityBeans.DataBean.BabyMessageBean> mList = new ArrayList<>();
    private String byz = "false";
    private String hyz = "false";
    private int myBaby;
    private int dayAll;
    private DataBaseHelper dataBaseHelper3;
    private SQLiteDatabase mDatabase3;
    private Cursor mCursor3;
    private String hyz_head;

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(BabyActivity.this, R.color.black);

//        initData();
        initListeners();

        mBabyAdapter = new BabyAdapter(mList, this, hyz_head);
        mBabyActivityListView.setAdapter(mBabyAdapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_baby;
    }

    @Override
    public void initView() throws Exception {
        mBabyActivityAddBaby = (TextView) findViewById(R.id.BabyActivity_AddBaby);
        mBabyActivityListView = (ListView) findViewById(R.id.BabyActivity_ListView);
    }

    private void initData() {
        Log.e("ggggggtoken", (String) SPUtils.get(BabyActivity.this, "token", ""));
        Log.e("ggggggid", String.valueOf(SPUtils.get(BabyActivity.this, "babyId", "")));

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "075");
        hashMap.put("token", SPUtils.get(BabyActivity.this, "token", ""));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<BabyActivityBeans>(BabyActivity.this) {
                    @Override
                    public void onSuccess(BabyActivityBeans mBabyActivityBeans, Call call, Response response) {
                        if (mBabyActivityBeans.getResultCode() == 1) {
                            myBaby = mBabyActivityBeans.getData().getMyBaby();
                            mList.clear();
                            mList.addAll(mBabyActivityBeans.getData().getBabyMessage());
//                            initImg();
                            mBabyAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mBabyActivityBeans.getResultCode(), mBabyActivityBeans.getMsg());
                        }
                    }
                });
    }

    private void initImg() {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getPreChildDate() != null) {
                //当前时间
                DateTime now = DateTime.now();
                DateTime dateTime = DateTime.parse(String.valueOf(mList.get(i).getPreChildDate()));//设置的时间
                DateTime nows = dateTime.minusDays(280);//向前推280
                dayAll = Days.daysBetween(nows, now).getDays();//总天
                initSQLite();
            }
        }
    }

    private void initSQLite() {
        hyz_head = "";
        //怀孕中
        int newDatTime = dayAll + 1;
        if (newDatTime >= 280) {
            newDatTime = 280;
        }

        Log.e("fdfaaafnewDatTime", String.valueOf(newDatTime));

        dataBaseHelper3 = new DataBaseHelper(this);
        try {
            dataBaseHelper3.createDataBase();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        dataBaseHelper3.openDataBase();
        mDatabase3 = dataBaseHelper3.getReadableDatabase();
        String querySQL3 = "select * from pregnancy where day = " + newDatTime;
        mCursor3 = mDatabase3.rawQuery(querySQL3, null);
        while (mCursor3.moveToNext()) {
//            int hyz_id = mCursor3.getInt(mCursor3.getColumnIndex("id"));
//            int hyz_week = mCursor3.getInt(mCursor3.getColumnIndex("day"));
//            Double hyz_height = mCursor3.getDouble(mCursor3.getColumnIndex("height"));
//            Double hyz_weight = mCursor3.getDouble(mCursor3.getColumnIndex("weight"));
            hyz_head = mCursor3.getString(mCursor3.getColumnIndex("head"));
//            String hyz_voice = mCursor3.getString(mCursor3.getColumnIndex("voice"));
        }
        Log.e("fdfaaafhyz_head", String.valueOf(hyz_head));

        mBabyAdapter = new BabyAdapter(mList, this, hyz_head);
        mBabyActivityListView.setAdapter(mBabyAdapter);
        mCursor3.close();
        mDatabase3.close();
        dataBaseHelper3.close();
    }

    private void initListeners() {
        mBabyActivityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                Log.e("gggggggggggg", (String) SPUtils.get(BabyActivity.this, "isQieHuanBBId", ""));
//                Log.e("gggggggggggg", String.valueOf((mList.get(position).getBabyId())));

                SPUtils.put(BabyActivity.this, "babyId", String.valueOf(mList.get(position).getBabyId()));
                SPUtils.put(BabyActivity.this, "BabyState", String.valueOf(mList.get(position).getSTATUS()));
                SPUtils.put(BabyActivity.this, "authority", String.valueOf(mList.get(position).getAuthority()));
                SPUtils.put(BabyActivity.this, "nickname",  String.valueOf(mList.get(position).getNickName()));
                SPUtils.put(BabyActivity.this, "babyNickname",  String.valueOf(mList.get(position).getNickName()));//只在宝宝呼吸成绩单使用

//                Log.e("ggggggggg", String.valueOf(mList.get(position).getAuthority()) + "xcf");

                SPUtils.put(BabyActivity.this, "BabySex", String.valueOf(mList.get(position).getSex()));
                SPUtils.put(BabyActivity.this, "orderId", String.valueOf(mList.get(position).getOrderId()));

                if (mList.get(position).getSTATUS() == 0) {

                } else if (mList.get(position).getSTATUS() == 1) {
                    SPUtils.put(BabyActivity.this, "timeh", mList.get(position).getPreChildDate());
                } else if (mList.get(position).getSTATUS() == 2) {
                    SPUtils.put(BabyActivity.this, "timey", mList.get(position).getChildBirthDate());
                }
//                            alert.dismiss();
                if (App.mediaPlayer2 != null) {
                    if (App.mediaPlayer2.isPlaying()) {
                        App.mediaPlayer2.stop();
                    }
                }
                if (HomeFragment.mediaPlayer != null) {
                    if (HomeFragment.mediaPlayer.isPlaying()) {
                        HomeFragment.mediaPlayer.stop();
                        HomeFragment.isPlay = true;
                        HomeFragment.isPlayStart = true;
                    }
                }
                Intent intent = new Intent(BabyActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
//                        }
//                    });
//                } else if (String.valueOf(SPUtils.get(BabyActivity.this, "isQieHuanBBId", "")).equals(String.valueOf(mList.get(position).getBabyId()))) {
//                    Toast toast = Toast.makeText(BabyActivity.this, "亲，当前宝宝已显示了哦！", Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                }
            }
        });
        mBabyActivityAddBaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myBaby < 4) {
                    List<Integer> integers = new ArrayList<Integer>();
                    for (int i = 0; i < mList.size(); i++) {
                        if (mList.get(i).getOrderId() == 1) {
                            integers.add(mList.get(i).getSTATUS());
                        }
                    }
                    for (int i = 0; i < integers.size(); i++) {
                        int status = Integer.parseInt(integers.get(i).toString());
                        if (status == 0) {
                            byz = "true";
                        } else if (status == 1) {
                            hyz = "true";
                        }
                    }
                    Intent intent = new Intent(BabyActivity.this, StateSettingActivity.class);
                    intent.putExtra("flag", "1");
                    intent.putExtra("byz", byz);
                    intent.putExtra("hyz", hyz);
                    intent.putExtra("isTianJia", "1");
                    startActivity(intent);
                } else {
                    Toast.makeText(BabyActivity.this, "亲,您添加的宝宝已达到上限哦！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void BabyActivity_Bank(View view) {
        finish();
    }

    @Override
    public void finishImg() {
//        initData();
    }
}
