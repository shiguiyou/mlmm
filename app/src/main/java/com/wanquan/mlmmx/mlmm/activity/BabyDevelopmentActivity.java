package com.wanquan.mlmmx.mlmm.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.BabyDevelopmentAdapter;
import com.wanquan.mlmmx.mlmm.adapter.BabyDevelopmentListViewAdapter;
import com.wanquan.mlmmx.mlmm.adapter.BabyDevelopmentRecyclerViewAdapter;
import com.wanquan.mlmmx.mlmm.beans.BabyDevelopmentBeans;
import com.wanquan.mlmmx.mlmm.beans.BabyDevelopmentListViewBeans;
import com.wanquan.mlmmx.mlmm.beans.BabyDevelopmentSQLBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeFragmentSQLiteBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeFragmentSQLiteSTBeans;
import com.wanquan.mlmmx.mlmm.beans.HomeGridViewBeans;
import com.wanquan.mlmmx.mlmm.beans.Student;
import com.wanquan.mlmmx.mlmm.phone.MyGridView;
import com.wanquan.mlmmx.mlmm.sqlite.DataBaseHelper;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.view.CenterShowHorizontalScrollView;
import com.wanquan.mlmmx.mlmm.view.MyListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 描述：宝宝发育周变化
 * 作者：薛昌峰
 * 时间：2017.11.17
 */
public class BabyDevelopmentActivity extends BaseActivity implements BabyDevelopmentRecyclerViewAdapter.WeekInterface {
    private TextView mBabyDevelopmentTitle;
    private LinearLayout mBabyDevelopmentLL;
    private CenterShowHorizontalScrollView mBabyDevelopmentScrollView;
    private HorizontalScrollView mBabyDevelopmentHSVScrollView;
    private RecyclerView mBabyDevelopmentRecyclerView;
    private TextView mBabyDevelopmentTOne;
    private TextView mBabyDevelopmentTTwo;
    private CircleImageView mBabyDevelopmentImageView;
    private TextView mBabyDevelopmentCenterTitle;
    private TextView mBabyDevelopmentCenterContent;
    private MyListView mBabyDevelopmentMyListView;
    //声明数据库辅助类对象
    private List<BabyDevelopmentSQLBeans> mList = new ArrayList<>();
    //声明一个数据库操作类
    //声明一个游标接口对象，用来遍历查询结果
    private int week;
    private BabyDevelopmentAdapter mBabyDevelopmentAdapter;
    private BabyDevelopmentListViewAdapter mBabyDevelopmentListViewAdapter;
    private List<BabyDevelopmentBeans> mList1 = new ArrayList<>();
    private List<BabyDevelopmentListViewBeans> mList2 = new ArrayList<>();
    private int weekAll;
    private boolean isCheck;
    private int positions;
    private String title;
    //声明数据库辅助类对象
    private DataBaseHelper dataBaseHelper;
    private DataBaseHelper dataBaseHelper2;//声明一个数据库操作类
    private SQLiteDatabase mDatabase;
    private SQLiteDatabase mDatabase2;
    //声明一个游标接口对象，用来遍历查询结果
    private Cursor mCursor;
    private Cursor mCursor2;
    private int week2;
    private final int middlePadding = 20;
    private BabyDevelopmentRecyclerViewAdapter mBabyDevelopmentRecyclerViewAdapter;

    @Override
    protected void onResume() {
        super.onResume();
//        mBabyDevelopmentHSVScrollView.smoothScrollTo (12150, 0);

//        mBabyDevelopmentHSVScrollView.smoothScrollTo(mBabyDevelopmentHSVScrollView.getChildAt(week2).getWidth() * week2 + 3 * week2, 0);
//        mBabyDevelopmentHSVScrollView.setSelectionFromTop(syDay, 0);

//        mBabyDevelopmentGridView.smoothScrollToPosition(2);
//        mBabyDevelopmentGridView.setSelection(20);
//        mBabyDevelopmentGridView.requestFocusFromTouch();
//        mBabyDevelopmentGridView.setSelection(20);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(BabyDevelopmentActivity.this, R.color.black);

        week2 = getIntent().getIntExtra("week", 0);
        title = getIntent().getStringExtra("title");
//        Log.e("weekss", String.valueOf(week));

        initSQLite(week2);
        initData();
        initListeners();
//        //设置水平横向滑动的参数
//        int size = weekAll;
//        int length = 80;
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        float density = dm.density;
//        int gridviewWidth = (int) (size * (length + 6) * density);
//        int itemWidth = (int) (length * density);
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
//        mBabyDevelopmentGridView.setLayoutParams(params); //设置GirdView布局参数,横向布局的关键
//        mBabyDevelopmentGridView.setColumnWidth(itemWidth);
//        mBabyDevelopmentGridView.setHorizontalSpacing(15);
//        mBabyDevelopmentGridView.setStretchMode(GridView.NO_STRETCH);
//        mBabyDevelopmentGridView.setNumColumns(size);

//        mBabyDevelopmentAdapter = new BabyDevelopmentAdapter(this, mList1, week2);
//        mBabyDevelopmentAdapter.setWeek(this);
//        mBabyDevelopmentRecyclerView.setAdapter(mBabyDevelopmentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBabyDevelopmentRecyclerView.setLayoutManager(linearLayoutManager);

        mBabyDevelopmentRecyclerViewAdapter = new BabyDevelopmentRecyclerViewAdapter(mList1, this, week2);
        mBabyDevelopmentRecyclerViewAdapter.setWeek(this);
        mBabyDevelopmentRecyclerView.setAdapter(mBabyDevelopmentRecyclerViewAdapter);
        mBabyDevelopmentRecyclerView.smoothScrollToPosition(week2);

        mBabyDevelopmentListViewAdapter = new BabyDevelopmentListViewAdapter(mList2, this);
        mBabyDevelopmentMyListView.setAdapter(mBabyDevelopmentListViewAdapter);

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_babydevelopment;
    }

    @Override
    public void initView() throws Exception {
        mBabyDevelopmentLL = (LinearLayout) findViewById(R.id.BabyDevelopment_LL);
        mBabyDevelopmentTitle = (TextView) findViewById(R.id.BabyDevelopment_Title);
//        mBabyDevelopmentScrollView = (CenterShowHorizontalScrollView) findViewById(R.id.BabyDevelopment_ScrollView);
//        mBabyDevelopmentHSVScrollView = (HorizontalScrollView) findViewById(R.id.BabyDevelopment_HSVScrollView);
        mBabyDevelopmentRecyclerView = (RecyclerView) findViewById(R.id.BabyDevelopment_RecyclerView);
//        mBabyDevelopmentGridView = (MyGridView) findViewById(R.id.BabyDevelopment_GridView);
        mBabyDevelopmentTOne = (TextView) findViewById(R.id.BabyDevelopment_TOne);
        mBabyDevelopmentTTwo = (TextView) findViewById(R.id.BabyDevelopment_TTwo);
        mBabyDevelopmentImageView = (CircleImageView) findViewById(R.id.BabyDevelopment_ImageView);
        mBabyDevelopmentCenterTitle = (TextView) findViewById(R.id.BabyDevelopment_Center_Title);
        mBabyDevelopmentCenterContent = (TextView) findViewById(R.id.BabyDevelopment_Center_Content);
        mBabyDevelopmentMyListView = (MyListView) findViewById(R.id.BabyDevelopment_MyListView);
    }

    private void initSQLite(int week2) {
        dataBaseHelper = new DataBaseHelper(this);
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        dataBaseHelper.openDataBase();
        //发育信息
        mDatabase = dataBaseHelper.getReadableDatabase();
        mList.clear();
        String querySQL = "select * from growth where week = " + week2;
        mCursor = mDatabase.rawQuery(querySQL, null);
        while (mCursor.moveToNext()) {
            int id = mCursor.getInt(mCursor.getColumnIndex("id"));
            int week = mCursor.getInt(mCursor.getColumnIndex("week"));
            String key = mCursor.getString(mCursor.getColumnIndex("key"));
            String dataTitle = mCursor.getString(mCursor.getColumnIndex("dataTitle"));
            String dataIntro = mCursor.getString(mCursor.getColumnIndex("dataIntro"));
            if (!key.equals("")) {
                String str = key;
                String result = str.substring(0, str.indexOf(","));
                CharSequence charSequence = str.subSequence(str.indexOf(",") + 1, str.length());
                Log.e("fffffffff", key + "123");
                if (key != null) {
                    mBabyDevelopmentLL.setVisibility(View.VISIBLE);
                    mBabyDevelopmentTOne.setText(result);
                    mBabyDevelopmentTTwo.setText(String.valueOf(charSequence));
                } else {
                    mBabyDevelopmentLL.setVisibility(View.GONE);
                }
            }
            mBabyDevelopmentCenterTitle.setText(dataTitle);
            mBabyDevelopmentCenterContent.setText(dataIntro);
        }
        mCursor.close();
        mDatabase.close();
        //======================================
        dataBaseHelper2 = new DataBaseHelper(this);
        try {
            dataBaseHelper2.createDataBase();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        dataBaseHelper2.openDataBase();
        //发育信息
        mDatabase2 = dataBaseHelper2.getReadableDatabase();
        //listview
        mList2.clear();
        String querySQL2 = "select * from growth_content where week = " + week2;
        mCursor2 = mDatabase2.rawQuery(querySQL2, null);
        while (mCursor2.moveToNext()) {
            int id = mCursor2.getInt(mCursor2.getColumnIndex("id"));
            int week = mCursor2.getInt(mCursor2.getColumnIndex("week"));
            String title = mCursor2.getString(mCursor2.getColumnIndex("title"));
            String content = mCursor2.getString(mCursor2.getColumnIndex("content"));

            BabyDevelopmentListViewBeans babyDevelopmentListViewBeans = new BabyDevelopmentListViewBeans(id, week, title, content);
            mList2.add(babyDevelopmentListViewBeans);
            mBabyDevelopmentTitle.setText(mList2.get(0).getTitle());

            mBabyDevelopmentListViewAdapter = new BabyDevelopmentListViewAdapter(mList2, this);
            mBabyDevelopmentMyListView.setAdapter(mBabyDevelopmentListViewAdapter);
//            Log.e("gggggggg", String.valueOf(mList2.size()));
        }
        mCursor2.close();
        mDatabase2.close();
    }

    private void initData() {
        //头部
        mDatabase = dataBaseHelper.getReadableDatabase();
        mList1.clear();
        String querySQL = "select max(week) from growth";
        mCursor = mDatabase.rawQuery(querySQL, null);
        while (mCursor.moveToNext()) {
            weekAll = mCursor.getInt(mCursor.getColumnIndex("max(week)"));
            for (int i = 1; i < weekAll + 1; i++) {
                if (week == i) {
                    isCheck = true;
                } else {
                    isCheck = false;
                }
                BabyDevelopmentBeans babyDevelopmentBeans = new BabyDevelopmentBeans(i, isCheck);
                mList1.add(babyDevelopmentBeans);
//                Log.e("dddddddddd", String.valueOf(babyDevelopmentBeans));
            }
        }
        mDatabase.close();
        mCursor.close();

//        //新HorizontalScrollView
//        Log.e("ffffffff", String.valueOf(mList1.size()));
//        mBabyDevelopmentScrollView.getLinear().removeAllViews();
//        for (int i = 0; i < mList1.size(); i++) {
//            final View view = View.inflate(BabyDevelopmentActivity.this, R.layout.work_time_title_item, null);
//            mBabyDevelopmentScrollView.addItemView(view, i);
//            LinearLayout.LayoutParams titleParams = (LinearLayout.LayoutParams) view.getLayoutParams();
//            titleParams.leftMargin = middlePadding / 2;
//            titleParams.rightMargin = middlePadding / 2;
//
////            TextView mItemBabydevelopTv = (TextView) findViewById(R.id.item_babydevelop_tv);
////            ImageView mItemBabydevelopImg = (ImageView) findViewById(R.id.item_babydevelop_img);
////            mItemBabydevelopTv.setText("宝宝" + mList1.get(i).getWeek() + "周");
//
//            TextView textView = (TextView) view.findViewById(R.id.text);
//            textView.setText("宝宝" + mList1.get(i).getWeek() + "周");
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mBabyDevelopmentScrollView.onClicked(v);
//                }
//            });
//        }

        if (SPUtils.get(BabyDevelopmentActivity.this, "w_BabyState", "").equals("1") || SPUtils.get(BabyDevelopmentActivity.this, "BabyState", "").equals("1")) {
            mBabyDevelopmentImageView.setImageDrawable(getResources().getDrawable(R.mipmap.born));
        } else if (SPUtils.get(BabyDevelopmentActivity.this, "w_BabyState", "").equals("2") || SPUtils.get(BabyDevelopmentActivity.this, "BabyState", "").equals("2")) {
            mBabyDevelopmentImageView.setImageDrawable(getResources().getDrawable(R.mipmap.babyavalatar));
        } else if (SPUtils.get(BabyDevelopmentActivity.this, "w_BabyState", "4").equals("0") || SPUtils.get(BabyDevelopmentActivity.this, "BabyState", "4").equals("0")) {
            mBabyDevelopmentImageView.setImageDrawable(getResources().getDrawable(R.mipmap.born));
        }
    }

    private void initListeners() {

    }

    public void BabyDevelopment_Bank(View view) {
        finish();
    }

    @Override
    public void checkGroup(int mWeek) {
        week2 = mWeek;
        initSQLite(mWeek);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
