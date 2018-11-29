package com.wanquan.mlmmx.mlmm.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.ApplyExperienceAdapter;
import com.wanquan.mlmmx.mlmm.adapter.CircleDetailsAdapter;
import com.wanquan.mlmmx.mlmm.beans.ApplyBeans;
import com.wanquan.mlmmx.mlmm.beans.ApplyTwoBeans;
import com.wanquan.mlmmx.mlmm.beans.CircleDetailsBeans;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.SettingBabyMessageBeans;
import com.wanquan.mlmmx.mlmm.fragment.HomeFragment;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.DensityUtil;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.xmly.data.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：申请免费体验
 * 作者：薛昌峰
 * 时间：2018.08.28
 */
public class ApplyExperienceActivity extends BaseActivity {
    private LinearLayout mAppTitleBank;
    private TextView mAppTitleName;
    private TextView mAppTitleSave;
    private TextView mApplyExperienceActivityED1;
    private EditText mApplyExperienceActivityED2;
    private TextView mApplyExperienceActivityTv;
    private PopupWindow popupWindow = null;//更多弹出框
    private List<ApplyBeans.DataBean> mList = new ArrayList<>();
    private ApplyExperienceAdapter applyExperienceAdapter;
    private ListView mListView;
    private int id = -1;
    private ListView lv;
    private int selectPos = -1;
    private List<String> contacts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(ApplyExperienceActivity.this, R.color.top);

        initNetWork();
        initData();
        initListeners();


    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_apply_experience;
    }

    @Override
    public void initView() throws Exception {
        mAppTitleBank = (LinearLayout) findViewById(R.id.App_Title_Bank);
        mAppTitleName = (TextView) findViewById(R.id.App_Title_Name);
        mAppTitleSave = (TextView) findViewById(R.id.App_Title_Save);
        mApplyExperienceActivityED1 = (TextView) findViewById(R.id.ApplyExperienceActivity_ED1);
        mApplyExperienceActivityED2 = (EditText) findViewById(R.id.ApplyExperienceActivity_ED2);
        mApplyExperienceActivityTv = (TextView) findViewById(R.id.ApplyExperienceActivity_tv);
    }

    private void initData() {
        mAppTitleName.setText("申请体验");
        mAppTitleSave.setText("申请记录");
    }

    private void initNetWork() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "122");
        hashMap.put("token", SPUtils.get(ApplyExperienceActivity.this, "token", ""));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<ApplyBeans>(ApplyExperienceActivity.this) {
                    @Override
                    public void onSuccess(ApplyBeans mApplyBeans, Call call, Response response) {
                        if (mApplyBeans.getResultCode() == 1) {
                            mList.clear();
                            mList.addAll(mApplyBeans.getData());
//                            applyExperienceAdapter = new ApplyExperienceAdapter(ApplyExperienceActivity.this, mList);
//                            mListView.setAdapter(applyExperienceAdapter);
                            for (int i = 0; i < mList.size(); i++) {
                                contacts.add(mList.get(i).getLocationName() + mList.get(i).getChainStore());
                            }
//                            applyExperienceAdapter.notifyDataSetChanged();
                        } else {
                            App.ErrorToken(mApplyBeans.getResultCode(), mApplyBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
        mAppTitleBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mAppTitleSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ApplyExperienceActivity.this, ApplyRecord.class));
            }
        });

        mApplyExperienceActivityED1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ApplyExperienceActivity.this);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setContentView(R.layout.dialog_contacts);
                WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                attributes.width = display.getWidth();
                attributes.height = (int) (display.getHeight() * 0.4);
                attributes.gravity = Gravity.BOTTOM;
                dialog.getWindow().setAttributes(attributes);
                lv = (ListView) dialog.getWindow().findViewById(R.id.lv);
                TextView tvSure = (TextView) dialog.getWindow().findViewById(R.id.tv_sure);
                TextView tvCancle = (TextView) dialog.getWindow().findViewById(R.id.tv_cancel);

                final DialogAdapter dialogAdapter = new DialogAdapter();
                lv.setAdapter(dialogAdapter);
                tvCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                tvSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id = mList.get((int) dialogAdapter.getItemId(selectPos)).getId();
                        Log.e("dsadsadsa", String.valueOf(id));
                        mApplyExperienceActivityED1.setText(contacts.get((int) dialogAdapter.getItemId(selectPos)));
                        mApplyExperienceActivityED1.setTextColor(getResources().getColor(R.color.black));
                        dialog.dismiss();
                    }
                });

                lv.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {

                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        selectPos = firstVisibleItem + 1;
                        dialogAdapter.notifyDataSetChanged();
                    }
                });

                lv.setSelection(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % mList.size());
                dialog.show();
            }
        });

        mApplyExperienceActivityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("fdfsdfsdf", String.valueOf(SPUtils.get(App.getContext(), "loginName", "")));
                if (id == 0) {
                    Toast.makeText(ApplyExperienceActivity.this, "请选择月子会所", Toast.LENGTH_SHORT).show();
                } else if ("".equals(mApplyExperienceActivityED2.getText().toString().trim())) {
                    Toast.makeText(ApplyExperienceActivity.this, "请输入房间号", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("itfaceId", "123");
                    hashMap.put("token", SPUtils.get(ApplyExperienceActivity.this, "token", ""));
                    hashMap.put("username", SPUtils.get(App.getContext(), "loginName", ""));
                    hashMap.put("locationId", id);
                    hashMap.put("room", mApplyExperienceActivityED2.getText().toString().trim());
                    JSONObject jsonObject = new JSONObject(hashMap);

                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<ApplyTwoBeans>(ApplyExperienceActivity.this) {
                                @Override
                                public void onSuccess(ApplyTwoBeans mApplyTwoBeans, Call call, Response response) {
                                    if (mApplyTwoBeans.getResultCode() == 1) {
                                        id = 0;
                                        Toast.makeText(ApplyExperienceActivity.this, mApplyTwoBeans.getMsg(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        App.ErrorToken(mApplyTwoBeans.getResultCode(), mApplyTwoBeans.getMsg());
                                    }
                                }
                            });
                }
            }
        });
    }


    private class DialogAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public String getItem(int position) {
            return contacts.get((int) getItemId(position));
        }

        @Override
        public long getItemId(int position) {
            return position % mList.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(ApplyExperienceActivity.this).inflate(R.layout.dialog_contacts_item, parent, false);
                holder.tv = (TextView) convertView.findViewById(R.id.text1);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv.setText(getItem(position));
            if (selectPos != -1 && selectPos == position) {
                holder.tv.setTextSize(30);
                holder.tv.setTextColor(getResources().getColor(R.color.green));
            } else {
                holder.tv.setTextSize(20);
                holder.tv.setTextColor(getResources().getColor(R.color.black));
            }
            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv;
    }
}
