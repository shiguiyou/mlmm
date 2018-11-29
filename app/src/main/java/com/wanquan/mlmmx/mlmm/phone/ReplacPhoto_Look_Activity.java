package com.wanquan.mlmmx.mlmm.phone;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import com.google.gson.Gson;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * 更换头像预览页
 */
public class ReplacPhoto_Look_Activity extends AppCompatActivity {
    public static final int CREAM_CODE = 110;//照相机返回结果
    //控件
    ReplacPhoto_Look_Utils mImageView;
    Bitmap bitmap;
    //图片路径
    String path = "";
    Gson mGson = new Gson();
    String userId = "0";//加密的userid
    Intent intent;
    Dialog mDialogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replacphoto_look);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(ReplacPhoto_Look_Activity.this, R.color.black);


        //设置窗口全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mImageView = (ReplacPhoto_Look_Utils) findViewById(R.id.home_replacePhotoLook_image);
        intent = getIntent();
        if (intent.getIntExtra("ReplacType", -1) == 0) {
            path = intent.getStringExtra("ReplacPhoto_Look");
            Log.e("xcf",path);
        } else {
            path = intent.getStringExtra("headerPath");
            Log.e("xcf",path);
        }
        userId = intent.getStringExtra("userId");
        bitmap = convertToBitmap(path, 600, 600);
        mImageView.setImageBitmap(bitmap);

    }

    /**
     * 根据路径加载bitmap
     *
     * @param path 路径
     * @param w    款
     * @param h    长
     * @return
     */
    public static final Bitmap convertToBitmap(String path, int w, int h) {
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            // 设置为ture只获取图片大小
            opts.inJustDecodeBounds = true;
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
            // 返回为空
            BitmapFactory.decodeFile(path, opts);
            int width = opts.outWidth;
            int height = opts.outHeight;
            float scaleWidth = 0.f, scaleHeight = 0.f;
            if (width > w || height > h) {
                // 缩放
                scaleWidth = ((float) width) / w;
                scaleHeight = ((float) height) / h;
            }
            opts.inJustDecodeBounds = false;
            float scale = Math.max(scaleWidth, scaleHeight);
            opts.inSampleSize = (int) scale;
            WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
            Bitmap bMapRotate = Bitmap.createBitmap(weak.get(), 0, 0, weak.get().getWidth(), weak.get().getHeight(), null, true);
            if (bMapRotate != null) {
                return bMapRotate;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 更换头像按钮
     * <p>
     * //     * @param view
     */
//    public void sureChangePic(View view) {
//        Bitmap bitmap_1 = null;
//        mDialogs=new HintText_Dialog(ReplacPhoto_Look_Activity.this,R.style.MyDialog);
//        mDialogs.show();
//        HintText_Dialog mTextDialog=new HintText_Dialog(ReplacPhoto_Look_Activity.this,"上传中...","");
//            if (bitmap == null) {
//                HintText_Dialog mTextDialog_=new HintText_Dialog(ReplacPhoto_Look_Activity.this,"图片加载失败","fail");
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mDialogs.dismiss();
//                    }
//                },2000);
//            } else {
//                Log.e("sss", "!kong");
//                bitmap_1 = mImageView.clip();
//                try {
//                    File file = SavePicture_toLocal_Utils.saveCacheFile(bitmap_1, Login_Static.myUserID + System.currentTimeMillis() + ".png",
//                            ReplacPhoto_Look_Activity.this);
//                    byte[] bytes = NetUtil.getByte(file);
//                    int[] pic = new int[bytes.length];
//                    //加128，去掉符号位
//                    for (int a = 0; a < pic.length; a++) {
//                        pic[a] = bytes[a] & 0xff;
//                    }
//                    MyApplication myApplication = new MyApplication();
//
//                    ImageInfo imageInfo = new ImageInfo();
//                    imageInfo.setExt(".png");
//                    imageInfo.setFileData(pic);
//                    imageInfo.setTemp(false);
//                    imageInfo.setMaxWidth(600);
//                    imageInfo.setMaxHeight(600);
//                    String data = mGson.toJson(imageInfo);
//                    Log.e("testing", "上传图片" + data);
//                    //上传头像图片
//                    OkHttpUtils.postString()
//                            .url(myApplication.getFileUrl())
//                            .mediaType(okhttp3.MediaType.parse("application/json"))
//                            .content(data)
//                            //.addParams("", request)
//                            .build()
//                            .execute(new StringCallback() {
//                                @Override
//                                public void onError(Call call, Exception e, int id) {
//                                    Log.e("testing", "上传图片失败：" + e.toString());
//                                    HintText_Dialog mTextDialog_=new HintText_Dialog(ReplacPhoto_Look_Activity.this,"更换头像失败","fail");
//                                    new Handler().postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            mDialogs.dismiss();
//                                        }
//                                    },2000);
//                                }
//
//                                @Override
//                                public void onResponse(String response, int id) {
//                                    Log.e("testing", "上传图片：" + response);
//                                    try {
//                                        if("null".equals(userId) || "".equals(userId) || userId==null) {
//                                            //宝宝头像
//                                            mDialogs.dismiss();
//                                            intent.putExtra("returnUrl",Json_Utils.getMessage(response));
//                                            Login_Static.myBabyPic=Json_Utils.getMessage(response);
//                                            setResult(2,intent);
//                                            finish();
//                                        }else {
//                                            //更换头像
//                                            changeHeader(Json_Utils.getMessage(response));
//                                            //SubString_Utils.getImageUrl(Json_Utils.getMessage(response))
//                                        }
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                }
//                            });
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//
//
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Photo_Bimp.tempSelectBitmap.clear();
    }

//    private void changeHeader(final String url){
//        UserAPI userAPI = new UserAPI();
//        userAPI.setPortrait(url);
//        userAPI.setUserId(userId);
//        String data=mGson.toJson(userAPI);
//        OKHttp.OkHttpPost("/User/UpdateUserInfo", "", data, new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                Log.e("testing","changePic失败:"+e.toString());
//                HintText_Dialog mTextDialog_=new HintText_Dialog(ReplacPhoto_Look_Activity.this,"更换头像失败","fail");
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mDialogs.dismiss();
//                    }
//                },2000);
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                Log.e("testing","changePic:"+response);
//                try {
//                    if(Json_Utils.getCode(response)==0){
//                        HintText_Dialog mTextDialog_=new HintText_Dialog(ReplacPhoto_Look_Activity.this,"更换头像成功","success");
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mDialogs.dismiss();
//                                if(userId.equals(Login_Static.myUserID))
//                                    Login_Static.mAccount.setPortrait(url);
//                                finish();
//                            }
//                        },2000);
//                    }else {
//                        HintText_Dialog mTextDialog_=new HintText_Dialog(ReplacPhoto_Look_Activity.this,
//                                Json_Utils.getMessage(response),"fail");
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mDialogs.dismiss();
//                            }
//                        },2000);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }


    public int[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("testing", "上传图片byte[]:" + out.toByteArray().toString());
        int[] pic = new int[out.toByteArray().length];
        //加128，去掉符号位
        for (int a = 0; a < pic.length; a++) {
            pic[a] = out.toByteArray()[a] & 0xff;
        }

        return pic;
    }

    public Bitmap getBitmapFromByte(byte[] temp) {
        if (temp != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        } else {
            return null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //清空选择的图片
            //Photo_Bimp.tempSelectBitmap.clear();
            finish();
        }
        return false;
    }

}
