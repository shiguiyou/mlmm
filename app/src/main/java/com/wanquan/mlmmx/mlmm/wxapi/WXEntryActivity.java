package com.wanquan.mlmmx.mlmm.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.lzy.okgo.OkGo;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wanquan.mlmmx.mlmm.beans.WXLoginJson;
import com.wanquan.mlmmx.mlmm.beans.WxInfoReturn;
import com.wanquan.mlmmx.mlmm.beans.WxTokenReturn;
import com.wanquan.mlmmx.mlmm.beans.WxTokenrefresh;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import okhttp3.Call;
import okhttp3.Response;
import static android.content.ContentValues.TAG;


/**
 * WXEntryActivity 是微信固定的Activiy、 不要改名字、并且放到你对应的项目报名下面、
 * 例如： ....(package报名).wxapi.WXEntryActivity
 * 不然无法回调、切记...
 * Wx  回调接口 IWXAPIEventHandler
 * <p/>
 * 关于WXEntryActivity layout。 我们没给页面、而是把Activity  主题 android:theme="@android:style/Theme.Translucent" 透明、
 * <p/>
 * User: MoMo - Nen
 * Date: 2015-10-24
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI mApi;
    private Context mContext;
    private String wx = "1";

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.empty_view);
        mContext = this;
        mApi = WXAPIFactory.createWXAPI(this, "wxeca84b2f356693a2", true);
        mApi.handleIntent(this.getIntent(), (IWXAPIEventHandler) this);
    }

    //微信发送的请求将回调到onReq方法
    @Override
    public void onReq(BaseReq baseReq) {
    }


    //发送到微信请求的响应结果
    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                //发送成功
                Toast.makeText(mContext, "发送成功", Toast.LENGTH_SHORT).show();

                //通知邀请界面回调
                Intent callbackIntent = new Intent();
                callbackIntent.setAction("com.xcf.callback");
                callbackIntent.putExtra("callback", "1");
                sendBroadcast(callbackIntent);

                try {
                    if (resp instanceof SendAuth.Resp) {
                        SendAuth.Resp sendAuth = (SendAuth.Resp) resp;
                        String code = sendAuth.code;
                        getAccess_token(code);
                    }
                    if (resp instanceof SendMessageToWX.Resp) {
                        SendMessageToWX.Resp sendResp = (SendMessageToWX.Resp) resp;
                        if (sendResp != null) {
                            String code = sendResp.errStr;
//                            getAccess_token(code);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //发送取消
                Log.e("11111111微信发送取消", "");
                Toast.makeText(mContext, "取消发送", Toast.LENGTH_SHORT).show();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //发送被拒绝
                Log.e("11111111发送被拒绝", "");
                break;
            default:
                //发送返回
                Log.e("11111111发送返回", "");
                break;
        }
        finish();
    }

    /**
     * 获取openid accessToken值用于后期操作
     *
     * @param code 请求码
     */
    private void getAccess_token(final String code) {
        OkGo.post("https://api.weixin.qq.com/sns/oauth2/access_token")
                .params("appid", "wxeca84b2f356693a2")
//                .params("secret", "35c7b0dc5edd1f9db402882a7ae43982")
                .params("secret", "8ec07b16ceded9fa3a123f92838a0d2a")
                .params("code", code)
                .params("grant_type", "authorization_code")
                .execute(new CustomCallBackNoLoading<WxTokenReturn>(this) {
                    @Override
                    public void onSuccess(WxTokenReturn result, Call call, Response response) {
                        String openid = result.getOpenid().toString();
                        String access_token = result.getAccess_token().toString();

                        SPUtils.put(mContext, "wx_token", access_token);
                        SPUtils.put(mContext, "openid", openid);
                        //获取用户信息
                        getUserMesg(access_token, openid);

//                        String refresh_token=result.getRefresh_token().toString();
//                        SPUtils.put(mContext, "refresh_token", refresh_token);
//                        //刷新用户token有效期
//                        getRefresh_token(refresh_token);

                    }
                });
    }

    //refresh_token拥有较长的有效期（30天），当refresh_token失效的后，需要用户重新授权
    private void getRefresh_token(String refresh_token) {
        OkGo.post("https://api.weixin.qq.com/sns/oauth2/refresh_token")
                .params("appid", "wxeca84b2f356693a2")
                .params("grant_type", "refresh_token")
                .params("refresh_token", refresh_token)
                .execute(new CustomCallBackNoLoading<WxTokenrefresh>(this) {
                    @Override
                    public void onSuccess(WxTokenrefresh refresh, Call call, Response response) {
                        String access_token = refresh.getAccess_token();
                        SPUtils.put(mContext, "wx_token", access_token);

                        Log.e(TAG, "onSuccess: " + access_token);
                        String openid = refresh.getOpenid().toString();
                        SPUtils.put(mContext, "openid", openid);
                        //获取用户信息
                        getUserMesg(access_token, openid);
                    }
                });
    }

    private String hxusername;
    private String pwd;
    private String nickname;

    /**
     * 获取微信的个人信息
     *
     * @param access_token
     * @param openid
     */
    private void getUserMesg(final String access_token, final String openid) {
        OkGo.post("https://api.weixin.qq.com/sns/userinfo")
                .params("access_token", access_token)
                .params("openid", openid)
                .execute(new CustomCallBackNoLoading<WxInfoReturn>(this) {
                    @Override
                    public void onSuccess(WxInfoReturn result, Call call, Response response) {
                        nickname = result.getNickname();
                        int sex = result.getSex();

                        String headimgurl = result.getHeadimgurl();

                        WXLoginJson entity = new WXLoginJson();
                        entity.setOpenid(openid);
                        entity.setFaceimg(headimgurl);
                        entity.setLittleimg(headimgurl);
                        Log.e("此时返回的openid：", openid);
                        Log.e("此时返回的头像：", headimgurl);
                        Log.e("此时返回的sex：", String.valueOf(sex));
                        Log.e("此时返回的nickname：", nickname);
//                        switch (sex){
//                            case 1 :
//                                entity.setSex("男");
//                                break;
//                            case 2 :
//                                entity.setSex("女");
//                                break;
//                            default :
//                                entity.setSex("未知");
//                                break;
//                        }
                        entity.setSex(sex + "");

                        entity.setNickname(nickname);
//                        OkGo.post(UrlContant.WEIXINREGIS)
//                                .upJson(JsonUtil.toJson(entity))
//                                .execute(new CustomCallBackNoLoading<WXLoginResult>(mContext) {
//                                    @Override
//                                    public void onSuccess(WXLoginResult wxLoginResult, Call call, Response response) {
//                                        if (wxLoginResult.isSuccess()) {
//                                            SPUtils.put(mContext, "userid", wxLoginResult.getData().get(0).getUserid());
//                                            SPUtils.put(mContext, "u_sex", wxLoginResult.getData().get(0).getSex());
//                                            SPUtils.put(mContext, "headname", nickname);
//                                            SPUtils.put(mContext, "headimage", wxLoginResult.getData().get(0).getUimage());
//                                            SPUtils.put(mContext, "openid", wxLoginResult.getData().get(0).getOpenid());
////                                            SPUtils.put(mContext, "hxusername", wxLoginResult.getU_huanaccount());
////                                            SPUtils.put(mContext, "hxpsw", wxLoginResult.getU_huanapass());
//                                            SPUtils.put(mContext, "wx", wx);
//
////                                            hxusername =wxLoginResult.getU_huanaccount();
////                                            pwd =wxLoginResult.getU_huanapass();
//                                            //登录环信
////                                            loginHX();
//                                            if (wxLoginResult.getData().get(0).getTelphone() != null) {
//                                                // TODO: 2017/5/25 绑定过手机号   保存返回的账号和密码以及userID
//                                                SpUtil.setBooleanValue(WXEntryActivity.this, MyContant.ISLOGIN, true);
//                                                SPUtils.put(WXEntryActivity.this, "userid", wxLoginResult.getData().get(0).getUserid());
//                                                SPUtils.put(WXEntryActivity.this, "mdid", wxLoginResult.getData().get(0).getMdid());//保存到偏好设置
//                                                SPUtils.put(WXEntryActivity.this, "name", wxLoginResult.getData().get(0).getUname());//保存到偏好设置
////                                                SPUtils.put(WXEntryActivity.this, "storename", wxLoginResult.getData().get(0).getStorename());//保存到偏好设置
//                                                SPUtils.put(WXEntryActivity.this, "islhb", wxLoginResult.getData().get(0).getIslhb() + "");//保存到偏好设置
//                                                /**
//                                                 * 登录成功就调用加载数据
//                                                 */
//                                                EMClient.getInstance().groupManager().loadAllGroups();
//                                                EMClient.getInstance().chatManager().loadAllConversations();
//                                                // 根据返回的类型跳转
//                                                Intent intent = new Intent(WXEntryActivity.this, ReceiverMainActivity.class);
//                                                startActivity(intent);
//                                                finish();
//                                            } else {
//                                                // TODO: 2017/5/24 跳转至补充信息界面
//                                                Intent intent = new Intent(WXEntryActivity.this, WeiXinHuiDiaoActivity.class);
//                                                intent.putExtra("openid", wxLoginResult.getData().get(0).getOpenid());
//                                                startActivity(intent);
//                                            }
//                                        }
//                                    }
//                                });
                    }
                });
    }
}

