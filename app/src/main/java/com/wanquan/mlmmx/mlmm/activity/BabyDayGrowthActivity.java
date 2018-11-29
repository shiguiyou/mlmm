package com.wanquan.mlmmx.mlmm.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.BabyDayGrowthAdapter;
import com.wanquan.mlmmx.mlmm.beans.BabyDayGrowthBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.sqlite.DataBaseHelper;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.view.MyListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：胎宝宝每日发育变化
 * 作者：薛昌峰
 * 时间：2017.11.28
 */
public class BabyDayGrowthActivity extends BaseActivity {
    private TextView mBabyDayGrowthActivityTv;
    private List<BabyDayGrowthBeans> mList = new ArrayList<>();
    private ListView mBabyDayGrowthActivityListView;
    private BabyDayGrowthAdapter mBabyDayGrowthAdapter;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase mDatabase;
    private Cursor mCursor;
    private int hyz_id;
    private int hyz_week;
    private String hyz_description;
    private Double hyz_height;
    private Double hyz_weight;
    private int days;
    private int week;
    private int dayAll;
    private int syDay;

    @Override
    protected void onResume() {
        super.onResume();
        mBabyDayGrowthActivityListView.setSelectionFromTop(syDay, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(BabyDayGrowthActivity.this, R.color.black);

        week = getIntent().getIntExtra("week", 0);
        dayAll = getIntent().getIntExtra("dayAll", 0);
        days = getIntent().getIntExtra("days", 0);
        syDay = dayAll - days - 1;
//        Log.e("dddddddddd", String.valueOf(syDay));


        Log.e("wewewewdayAll", String.valueOf(dayAll)+"xcf");
        Log.e("wewewewdays", String.valueOf(days)+"xcf");
        Log.e("wewewewweek", String.valueOf(week)+"xcf");

        initDate();
        initListeners();

        mBabyDayGrowthAdapter = new BabyDayGrowthAdapter(this, mList, syDay);
        mBabyDayGrowthActivityListView.setAdapter(mBabyDayGrowthAdapter);

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_baby_day_growth;
    }

    @Override
    public void initView() throws Exception {
        mBabyDayGrowthActivityListView = (ListView) findViewById(R.id.BabyDayGrowthActivity_ListView);
        mBabyDayGrowthActivityTv = (TextView) findViewById(R.id.BabyDayGrowthActivity_tv);
    }

    private void initListeners() {
        mBabyDayGrowthActivityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBabyDayGrowthActivityListView.setSelectionFromTop(syDay, 0);
            }
        });
    }

    private void initDate() {
        dataBaseHelper = new DataBaseHelper(this);
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        dataBaseHelper.openDataBase();
        mDatabase = dataBaseHelper.getReadableDatabase();
        mList.clear();
        String querySQL = "select * from pregnancy order by day";
        mCursor = mDatabase.rawQuery(querySQL, null);
        while (mCursor.moveToNext()) {
            hyz_id = mCursor.getInt(mCursor.getColumnIndex("id"));
            hyz_week = mCursor.getInt(mCursor.getColumnIndex("day"));
            hyz_description = mCursor.getString(mCursor.getColumnIndex("description"));
            hyz_height = mCursor.getDouble(mCursor.getColumnIndex("height"));
            hyz_weight = mCursor.getDouble(mCursor.getColumnIndex("weight"));

            BabyDayGrowthBeans babyDayGrowthBeans = new BabyDayGrowthBeans(hyz_id, hyz_week, hyz_description, hyz_height, hyz_weight);
            mList.add(babyDayGrowthBeans);
        }
        mCursor.close();
        mDatabase.close();
    }


    public void BabyDayGrowthActivity_Bank(View view) {
        finish();
    }
}
