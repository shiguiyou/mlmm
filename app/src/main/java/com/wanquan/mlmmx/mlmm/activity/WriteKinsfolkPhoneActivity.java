package com.wanquan.mlmmx.mlmm.activity;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.IWriteKinsfolkPhoneBeans;
import com.wanquan.mlmmx.mlmm.beans.IWriteKinsfolkPhoneSaveBeans;
import com.wanquan.mlmmx.mlmm.beans.InviteRecordBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.TextUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.utils.Verification_Utils;
import com.wanquan.mlmmx.mlmm.wxapi.WXEntryActivity;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneSession;
import static com.wanquan.mlmmx.mlmm.App.api;

/**
 * 描述：输入亲人手机号
 * 作者：薛昌峰
 * 时间：2018.01.30
 */
public class WriteKinsfolkPhoneActivity extends BaseActivity {
    private EditText mWriteKinsfolkPhoneET;
    private ImageView mWriteKinsfolkPhoneImg;
    private TextView mWriteKinsfolkPhoneTV1;
    private TextView mWriteKinsfolkPhoneTV2;
    private int babyid;
    private String name;
    private String nickname;
    private CallBankReceiver mCallBankReceiver;

    // 自定义ACTION常数作为Intent Filter识别常数
    private String SMS_SEND_ACTION = "SMS_SEND_ACTION";
    private ReceiverListener receiverlistener = new ReceiverListener();
    private IntentFilter intentfilter = new IntentFilter();
    private boolean result = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(WriteKinsfolkPhoneActivity.this, R.color.black);

        babyid = getIntent().getIntExtra("babyid", 0);
        name = getIntent().getStringExtra("name");
        nickname = getIntent().getStringExtra("nickname");

        initListeners();

        mCallBankReceiver = new CallBankReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.xcf.callback");
        registerReceiver(mCallBankReceiver, filter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_wrie_kinsfolk_phone;
    }

    @Override
    public void initView() throws Exception {
        mWriteKinsfolkPhoneET = (EditText) findViewById(R.id.WriteKinsfolkPhone_ET);
        mWriteKinsfolkPhoneImg = (ImageView) findViewById(R.id.WriteKinsfolkPhone_img);
        mWriteKinsfolkPhoneTV1 = (TextView) findViewById(R.id.WriteKinsfolkPhone_TV1);
        mWriteKinsfolkPhoneTV2 = (TextView) findViewById(R.id.WriteKinsfolkPhone_TV2);
    }

    private void initData(final int size) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "084");
        hashMap.put("mobile", mWriteKinsfolkPhoneET.getText().toString().trim());
        hashMap.put("babyId", babyid);
        hashMap.put("relationship", name);
        hashMap.put("token", SPUtils.get(this, "token", ""));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<IWriteKinsfolkPhoneBeans>(WriteKinsfolkPhoneActivity.this) {
                    @Override
                    public void onSuccess(final IWriteKinsfolkPhoneBeans mIWriteKinsfolkPhoneBeans, Call call, Response response) {
                        if (mIWriteKinsfolkPhoneBeans.getResultCode() == 1) {
                            if (size == 1) {
                                //跳转到发送界面发送
                                //Uri smsToUri = Uri.parse("smsto:" + mWriteKinsfolkPhoneET.getText().toString().trim());
                                //Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                                //intent.putExtra("sms_body", mIWriteKinsfolkPhoneBeans.getData());
                                //startActivity(intent);
                                final AlertDialog alert;
                                alert = new AlertDialog.Builder(WriteKinsfolkPhoneActivity.this, R.style.AlertDialogs).create();
                                alert.show();
                                //加载自定义dialog
                                alert.getWindow().setContentView(R.layout.delete_dialogs);
                                TextView cart_delete_title = alert.getWindow().findViewById(R.id.cart_delete_title);
                                TextView cart_delete_content = alert.getWindow().findViewById(R.id.cart_delete_content);
                                TextView cart_delete_cancle = alert.getWindow().findViewById(R.id.cart_delete_cancle);
                                TextView cart_delete_confirm = alert.getWindow().findViewById(R.id.cart_delete_confirm);
                                cart_delete_title.setText("提示消息");
                                cart_delete_content.setText("亲,您确定发送短信吗~");
                                cart_delete_cancle.setText("取消");
                                cart_delete_confirm.setText("确认");
                                //取消
                                alert.getWindow().findViewById(R.id.cart_delete_cancle).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alert.dismiss();
                                        return;
                                    }
                                });
                                alert.getWindow().findViewById(R.id.cart_delete_confirm).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (ContextCompat.checkSelfPermission(WriteKinsfolkPhoneActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(WriteKinsfolkPhoneActivity.this, new String[]{Manifest.permission.SEND_SMS,
                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                                        } else {
                                            SmsManager sms = SmsManager.getDefault();
                                            try {
                                                // 创建ACTION常数的Intent，作为PendingIntent的参数
                                                Intent SendIt = new Intent(SMS_SEND_ACTION);
                                                // 接收消息传送后的广播信息SendPendIt，作为信息发送sendTextMessage方法的sentIntent参数
                                                PendingIntent SendPendIt = PendingIntent.getBroadcast(getApplicationContext(), 0, SendIt, PendingIntent.FLAG_CANCEL_CURRENT);
                                                // 发送短信
                                                sms.sendTextMessage(mWriteKinsfolkPhoneET.getText().toString().trim(), null, "我在美丽妈妈记录了" + nickname + "的成长，你也来关注一下吧！ " + mIWriteKinsfolkPhoneBeans.getData(), SendPendIt, null);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                // 异常提醒
                                                Toast.makeText(WriteKinsfolkPhoneActivity.this, "请手动开启权限", Toast.LENGTH_LONG).show();
                                            }
                                            // 广播注册
                                            intentfilter.addAction(SMS_SEND_ACTION);
                                            registerReceiver(receiverlistener, intentfilter);
                                        }
                                        alert.dismiss();
                                    }
                                });
                            } else if (size == 2) {
                                // 微信注册初始化
                                api = WXAPIFactory.createWXAPI(WriteKinsfolkPhoneActivity.this, "wxeca84b2f356693a2", true);
                                api.registerApp("wxeca84b2f356693a2");
                                share2weixin(0, mIWriteKinsfolkPhoneBeans.getData());
                            }
                        } else {
                            App.ErrorToken(mIWriteKinsfolkPhoneBeans.getResultCode(), mIWriteKinsfolkPhoneBeans.getMsg());
                        }
                    }
                });
    }

    private void share2weixin(int flag, String data) {
        if (!api.isWXAppInstalled()) {
            Toast.makeText(WriteKinsfolkPhoneActivity.this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = data;
        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = "我在美丽妈妈记录了" + nickname + "成长，你也来关注一下吧！";//name名称
        msg.description = data;//内容
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);//图片
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag;
        api.sendReq(req);
        req.scene = WXSceneSession;
    }

    /**
     * 判断是否包含SIM卡
     *
     * @return 状态
     */
    private boolean hasSimCard(Context context) {
        TelephonyManager telMgr = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        result = true;
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                result = false; // 没有SIM卡
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                result = false;
                break;
        }
        Log.e("try", result ? "有SIM卡" : "无SIM卡");
        return result;
    }

    private void initListeners() {
        mWriteKinsfolkPhoneImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(WriteKinsfolkPhoneActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(WriteKinsfolkPhoneActivity.this, new String[]{Manifest.permission.READ_CONTACTS,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    Uri uri = ContactsContract.Contacts.CONTENT_URI;
                    Intent intent = new Intent(Intent.ACTION_PICK, uri);
                    startActivityForResult(intent, 0);
                }
            }
        });
        mWriteKinsfolkPhoneTV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasSimCard(WriteKinsfolkPhoneActivity.this);
//                int absent = TelephonyManager.SIM_STATE_ABSENT;
//                if (1 == absent) {
//                    Log.d("ffdsfsdf", "请确认sim卡是否插入或者sim卡暂时不可用！");
//                }else {
//                    Log.d("ffdsfsdf", "有卡！");
//                }
                if (mWriteKinsfolkPhoneET.getText().toString().trim().equals("")) {
                    Toast toast = Toast.makeText(WriteKinsfolkPhoneActivity.this, "亲，手机号码不能为空哦~", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (!Verification_Utils.checkMobileNumber(mWriteKinsfolkPhoneET.getText().toString().trim())) {
                    Toast toast = Toast.makeText(WriteKinsfolkPhoneActivity.this, "亲，请输入正确的电话号码哦~", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    initData(1);
                }
            }
        });
        mWriteKinsfolkPhoneTV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWriteKinsfolkPhoneET.getText().toString().trim().equals("")) {
                    Toast toast = Toast.makeText(WriteKinsfolkPhoneActivity.this, "亲，手机号码不能为空哦~", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (!Verification_Utils.checkMobileNumber(mWriteKinsfolkPhoneET.getText().toString().trim())) {
                    Toast toast = Toast.makeText(WriteKinsfolkPhoneActivity.this, "亲，请输入正确的电话号码哦~", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    initData(2);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                Uri uri = data.getData();
                String[] contacts = getPhoneContacts(uri);
                Log.e("ffffff", "姓名:" + contacts[0] + " " + "手机号:" + contacts[1]);
//                if (contacts[1].contains(" ")) {
//                    String str = contacts[1].substring(0, contacts[1].indexOf(" "));
//                    mWriteKinsfolkPhoneET.setText(str);
//                } else {
                mWriteKinsfolkPhoneET.setText(contacts[1]);
//                }
            }
        }
    }

    private String[] getPhoneContacts(Uri uri) {
        String[] contact = new String[2];
        //得到ContentResolver对象
        ContentResolver cr = getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            //取得联系人姓名
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contact[0] = cursor.getString(nameFieldColumnIndex);
            //取得电话号码
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
            if (phone != null) {
                phone.moveToFirst();
                contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            phone.close();
            cursor.close();
        } else {
            return null;
        }
        return contact;
    }

    /**
     * 功能：监听短信状态
     * 说明：自定义类ReceiverListner，覆盖BroadcastReceiver，短信发送成功提醒“发送成功”，否则提醒重新发送
     **/
    public class ReceiverListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                // 获取短信状态
                switch (getResultCode()) {
                    // 短信发送成功
                    case Activity.RESULT_OK:
                        //上传数据
                        initNetWork();
                        break;
                    // 短信发送不成功
                    default:
                        Toast toast2 = Toast.makeText(WriteKinsfolkPhoneActivity.this, "发送失败，请重新发送！", Toast.LENGTH_LONG);
                        toast2.setGravity(Gravity.CENTER, 0, 0);
                        toast2.show();
                        break;
                }
            } catch (Exception e) {
                Toast.makeText(WriteKinsfolkPhoneActivity.this, "发送出现异常，请重新发送！", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initNetWork() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "078");
        hashMap.put("mobile", mWriteKinsfolkPhoneET.getText().toString().trim());
        hashMap.put("babyId", babyid);
        hashMap.put("relationship", name);
        hashMap.put("token", SPUtils.get(this, "token", ""));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<IWriteKinsfolkPhoneSaveBeans>(WriteKinsfolkPhoneActivity.this) {
                    @Override
                    public void onSuccess(IWriteKinsfolkPhoneSaveBeans mIWriteKinsfolkPhoneSaveBeans, Call call, Response response) {
                        if (mIWriteKinsfolkPhoneSaveBeans.getResultCode() == 1) {
                            Toast toast = Toast.makeText(WriteKinsfolkPhoneActivity.this, "发送成功", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {
                            App.ErrorToken(mIWriteKinsfolkPhoneSaveBeans.getResultCode(), mIWriteKinsfolkPhoneSaveBeans.getMsg());
                        }
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mCallBankReceiver);
    }

    private class CallBankReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.xcf.callback")) {
                String logoutFlag = intent.getStringExtra("callback");
                if (logoutFlag.equals("1")) {
                    //上传数据
                    initNetWork();
                }
            }
        }
    }

    public void WriteKinsfolkPhoneActivity_Bank(View view) {
        finish();
    }
}
