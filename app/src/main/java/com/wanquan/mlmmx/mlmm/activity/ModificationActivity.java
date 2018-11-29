package com.wanquan.mlmmx.mlmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.MessageEvent;
import com.wanquan.mlmmx.mlmm.beans.ModificationPSWBeans;
import com.wanquan.mlmmx.mlmm.beans.SettingBabyMessageBeans;
import com.wanquan.mlmmx.mlmm.fragment.HomeFragment;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MD5Util;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.utils.Verification_Utils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import okhttp3.Response;


/**
 * 描述：修改密码
 * 作者：薛昌峰
 * 时间：2017.09.27
 */
public class ModificationActivity extends BaseActivity {
    private EditText mModificationEd1;
    private EditText mModificationEd2;
    private EditText mModificationEd3;
    private RelativeLayout mModificationSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(ModificationActivity.this, R.color.black);

        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_modification;
    }

    @Override
    public void initView() throws Exception {
        mModificationEd1 = (EditText) findViewById(R.id.Modification_Ed1);
        mModificationEd2 = (EditText) findViewById(R.id.Modification_Ed2);
        mModificationEd3 = (EditText) findViewById(R.id.Modification_Ed3);
        mModificationSave = (RelativeLayout) findViewById(R.id.Modification_Save);
    }

    private void initData() {

    }

    private void initListeners() {
        mModificationSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mModificationEd1.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ModificationActivity.this, "亲，原密码不能为空哦~", Toast.LENGTH_SHORT).show();
                } else if (mModificationEd2.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ModificationActivity.this, "亲，新密码不能为空哦~", Toast.LENGTH_SHORT).show();
                } else if (!mModificationEd2.getText().toString().trim().equals(mModificationEd3.getText().toString().trim())) {
                    Toast.makeText(ModificationActivity.this, "两次密码输入不一致，请重新输入哦~", Toast.LENGTH_SHORT).show();
                } else if (mModificationEd2.getText().length() < 6) {
                    Toast.makeText(ModificationActivity.this, "亲，密码为6-16位数字、字母或数字和字母的组合哦~", Toast.LENGTH_SHORT).show();
                } else if (!Verification_Utils.CheckPassword(mModificationEd2.getText().toString().trim())) {
                    Toast.makeText(ModificationActivity.this, "亲，密码为6-16位数字、字母或数字和字母的组合哦~", Toast.LENGTH_SHORT).show();
                } else if (mModificationEd2.getText().length() > 16) {
                    Toast.makeText(ModificationActivity.this, "亲，密码为6-16位数字、字母或数字和字母的组合哦~", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("itfaceId", "023");
                    hashMap.put("token", String.valueOf(SPUtils.get(ModificationActivity.this, "token", "")));
                    hashMap.put("password", MD5Util.GetMD5Code(mModificationEd1.getText().toString().trim()));
                    hashMap.put("new1Password", MD5Util.GetMD5Code(mModificationEd2.getText().toString().trim()));
                    hashMap.put("new2Password", MD5Util.GetMD5Code(mModificationEd3.getText().toString().trim()));
                    JSONObject jsonObject = new JSONObject(hashMap);

                    OkGo.post(UrlContent.URL).tag(this)
                            .upJson(jsonObject.toString())
                            .connTimeOut(10_000)
                            .execute(new CustomCallBackNoLoading<ModificationPSWBeans>(ModificationActivity.this) {
                                @Override
                                public void onSuccess(ModificationPSWBeans mModificationPSWBeans, Call call, Response response) {
                                    if (mModificationPSWBeans.getResultCode() == 1) {
//                                        final AlertDialog alert;
//                                        alert = new AlertDialog.Builder(ModificationActivity.this).create();
//                                        alert.show();
//                                        //加载自定义dialog
//                                        alert.getWindow().setContentView(R.layout.delete_dialogs);
//                                        TextView cart_delete_title = alert.getWindow().findViewById(R.id.cart_delete_title);
//                                        TextView cart_delete_content = alert.getWindow().findViewById(R.id.cart_delete_content);
//                                        TextView cart_delete_cancle = alert.getWindow().findViewById(R.id.cart_delete_cancle);
//                                        TextView cart_delete_confirm = alert.getWindow().findViewById(R.id.cart_delete_confirm);
//
//                                        cart_delete_title.setText("提示消息");
//                                        cart_delete_content.setText("点击确定，您的账号就退出咯，您也可以点击取消再想想~");
//                                        cart_delete_cancle.setText("取消");
//                                        cart_delete_confirm.setText("确认");
//                                        //取消
//                                        alert.getWindow().findViewById(R.id.cart_delete_cancle).setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                alert.dismiss();
//                                                return;
//                                            }
//                                        });
//                                        alert.getWindow().findViewById(R.id.cart_delete_confirm).setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
                                        if (App.mediaPlayer2 != null) {
                                            if (App.mediaPlayer2.isPlaying()) {
                                                App.mediaPlayer2.release();
                                            }
                                        }
                                        if (HomeFragment.mediaPlayer != null) {
                                            if (HomeFragment.mediaPlayer.isPlaying()) {
                                                HomeFragment.mediaPlayer.release();
                                            }
                                        }
                                        //退出登录后需要把登录后的信息全都赋给登录前的
                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("itfaceId", "033");
                                        hashMap.put("token", SPUtils.get(ModificationActivity.this, "token", ""));
                                        hashMap.put("id", SPUtils.get(ModificationActivity.this, "babyId", ""));
                                        JSONObject jsonObject = new JSONObject(hashMap);
                                        OkGo.post(UrlContent.URL).tag(this)
                                                .upJson(jsonObject.toString())
                                                .connTimeOut(10_000)
                                                .execute(new CustomCallBackNoLoading<SettingBabyMessageBeans>(ModificationActivity.this) {
                                                    @Override
                                                    public void onSuccess(SettingBabyMessageBeans mSettingBabyMessageBeans, Call call, Response response) {
                                                        if (mSettingBabyMessageBeans.getResultCode() == 1) {
                                                            String babyHeadIco = mSettingBabyMessageBeans.getData().getBabyHeadIco();
                                                            String babyStatus = String.valueOf(mSettingBabyMessageBeans.getData().getBabyStatus());
                                                            String babyHeight = String.valueOf(mSettingBabyMessageBeans.getData().getBabyHeight());
                                                            String babyNickname = mSettingBabyMessageBeans.getData().getBabyNickname();
                                                            String babySex = String.valueOf(mSettingBabyMessageBeans.getData().getBabySex());
                                                            String babyWeight = String.valueOf(mSettingBabyMessageBeans.getData().getBabyWeight());
                                                            String childBirthDate = mSettingBabyMessageBeans.getData().getChildBirthDate();
                                                            String preChildDate = mSettingBabyMessageBeans.getData().getPreChildDate();
                                                            if (babyStatus.equals("1")) {
                                                                SPUtils.put(ModificationActivity.this, "w_timeh", preChildDate);
                                                                SPUtils.put(ModificationActivity.this, "w_BabyState", babyStatus);
                                                            } else if (babyStatus.equals("2")) {
                                                                //Log.e("rrrrr到了状态复制界面","1111");
                                                                //Log.e("rrrrr到了状态复制界面",babyStatus);
                                                                SPUtils.put(ModificationActivity.this, "w_timey", childBirthDate);
                                                                SPUtils.put(ModificationActivity.this, "w_BabyState", babyStatus);
                                                                SPUtils.put(ModificationActivity.this, "w_setbabyname", babyNickname);
                                                                SPUtils.put(ModificationActivity.this, "w_babytz", babyWeight);
                                                                SPUtils.put(ModificationActivity.this, "w_babysg", babyHeight);
                                                                SPUtils.put(ModificationActivity.this, "w_sex", babySex);
                                                                if (SPUtils.get(ModificationActivity.this, "icon_y", "").equals("true")) {
                                                                    SPUtils.put(ModificationActivity.this, "w_img_y", babyHeadIco);
                                                                }
                                                            } else if (babyStatus.equals("0")) {
                                                                SPUtils.put(ModificationActivity.this, "w_BabyState", babyStatus);
                                                                if (SPUtils.get(ModificationActivity.this, "icon_b", "").equals("true")) {
                                                                    SPUtils.put(ModificationActivity.this, "w_img_b", babyHeadIco);
                                                                    SPUtils.put(ModificationActivity.this, "w_icon_b", "trues");//把未登录状态设置trues（不让在显示未登录之前上传的图片）
                                                                }
                                                            }

                                                            Set<String> mSet = new HashSet<String>();
                                                            mSet.add("0");
                                                            JPushInterface.setAliasAndTags(ModificationActivity.this, "0", mSet, new TagAliasCallback() {
                                                                @Override
                                                                public void gotResult(int i, String s, Set<String> set) {
                                                                    Log.e("mars", "gotResult: code=" + i + "alias=" + s + "tags=" + set.toArray()[0]);
                                                                }
                                                            });
                                                            //通知圈子页面刷新
                                                            MessageEvent messageEvent = new MessageEvent();
                                                            messageEvent.setShuaXin(true);
                                                            EventBus.getDefault().post(messageEvent);
                                                            Toast.makeText(ModificationActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                                                            SpUtil.setBooleanValue(ModificationActivity.this, MyContant.ISLOGIN, false);
                                                            SPUtils.put(ModificationActivity.this, "userid", "");
                                                            startActivity(new Intent(ModificationActivity.this, LoginActivity.class));
                                                            finish();
                                                        } else {
                                                            Toast.makeText(ModificationActivity.this, mSettingBabyMessageBeans.getMsg(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
//                                            }
//                                        });
                                    } else {
                                        App.ErrorToken(mModificationPSWBeans.getResultCode(), mModificationPSWBeans.getMsg());

                                    }
                                }
                            });
                }
            }
        });
    }

    public void Modification_Bank(View view) {
        finish();
    }
}
