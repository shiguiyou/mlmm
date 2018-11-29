package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.AntenatalTimeTableAdapter;
import com.wanquan.mlmmx.mlmm.beans.AntenatalTimeTableBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：产检时刻表
 * 作者：薛昌峰
 * 时间：2017.09.27
 */
public class AntenatalTimeTableActivity extends BaseActivity {
    private TextView mAntenatalTimeTableTextView;
    private ListView mAntenatalTimeTableLv;
    private List<AntenatalTimeTableBeans.DataBean.MapListBean> mList = new ArrayList<>();
    private AntenatalTimeTableAdapter mAntenatalTimeTableAdapter;
    private String chanjianDate;
    private int is;

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(AntenatalTimeTableActivity.this, R.color.black);

        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_antenataltimetableactivity;
    }

    @Override
    public void initView() throws Exception {
        mAntenatalTimeTableTextView = (TextView) findViewById(R.id.AntenatalTimeTable_TextView);
        mAntenatalTimeTableLv = (ListView) findViewById(R.id.AntenatalTimeTable_lv);
    }

    private void initData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "035");
        hashMap.put("token", SPUtils.get(AntenatalTimeTableActivity.this, "token", ""));
        hashMap.put("babyId", SPUtils.get(AntenatalTimeTableActivity.this, "babyId", ""));
//        Log.e("AntenatalTimetoken",String.valueOf(SPUtils.get(AntenatalTimeTableActivity.this, "userid", "")));
//        Log.e("AntenatalTimeisQieHuanBBId",String.valueOf(SPUtils.get(AntenatalTimeTableActivity.this, "isQieHuanBBId", "")));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<AntenatalTimeTableBeans>(this) {
                    @Override
                    public void onSuccess(AntenatalTimeTableBeans mAntenatalTimeTableBeans, Call call, Response response) {
                        if (mAntenatalTimeTableBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mAntenatalTimeTableBeans.getData().getMapList());
                            mAntenatalTimeTableTextView.setText("距离下次产检还有 " + String.valueOf(mAntenatalTimeTableBeans.getData().getDistanceChanjianDate()) + " 天");

                            //当前时间
                            DateTime now = DateTime.now();

                            for (int i = 0; i < mList.size(); i++) {
                                DateTime dateTime = DateTime.parse(mList.get(i).getChanjianDate());
                                int day = Days.daysBetween(now, dateTime).getDays();
                                Log.e("fsfsf", String.valueOf(day));
                                if (day >= 0) {
                                    if (mList.get(i).getStatus() != 2 && mList.get(i).getStatus() != 1) {
                                        is = i;
                                        chanjianDate = mList.get(i).getChanjianDate();
                                        break;
                                    }
                                }
                            }
                            mAntenatalTimeTableAdapter = new AntenatalTimeTableAdapter(AntenatalTimeTableActivity.this, mList, chanjianDate);
                            mAntenatalTimeTableLv.setAdapter(mAntenatalTimeTableAdapter);
                            mAntenatalTimeTableAdapter.notifyDataSetChanged();
                            mAntenatalTimeTableLv.setSelectionFromTop(is, 0);
                        } else {
                            App.ErrorToken(mAntenatalTimeTableBeans.getResultCode(), mAntenatalTimeTableBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
        mAntenatalTimeTableLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AntenatalTimeTableActivity.this, AntenatalTimeTableDetailsActivity.class);
                intent.putExtra("id", String.valueOf(mList.get(i).getId()));
                intent.putExtra("status", String.valueOf(mList.get(i).getStatus()));
                intent.putExtra("seq", String.valueOf(mList.get(i).getSeq()));
                intent.putExtra("content", String.valueOf(mList.get(i).getContent()));
                intent.putExtra("desc", String.valueOf(mList.get(i).getDesc()));
                intent.putExtra("help", String.valueOf(mList.get(i).getHelp()));
                intent.putExtra("week", String.valueOf(mList.get(i).getWeek()));
                intent.putExtra("content", String.valueOf(mList.get(i).getContent()));
                intent.putExtra("ChanjianDate", String.valueOf(mList.get(i).getChanjianDate()));
                intent.putExtra("ReminderDate", String.valueOf(mList.get(i).getReminderDate()));
                startActivity(intent);
            }
        });
    }

    public void AntenatalTimeTable_Bank(View view) {
        finish();
    }
}
