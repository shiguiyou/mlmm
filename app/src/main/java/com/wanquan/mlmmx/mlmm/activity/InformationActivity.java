package com.wanquan.mlmmx.mlmm.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.beans.PersonalinformationActivityBeans;
import com.wanquan.mlmmx.mlmm.beans.PersonalinformationModifitionBeans;
import com.wanquan.mlmmx.mlmm.beans.PersonalinformationModifitionImgBeans;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.SelectPicPopup;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：个人信息
 * 作者：薛昌峰
 * 时间：2017.09.27
 */
public class InformationActivity extends BaseActivity {
    private SelectPicPopup menuWindow; // 自定义的头像编辑弹出框
    /**
     * 使用照相机拍照获取图片
     */
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    /**
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
    /**
     * 获取到的图片路径
     */
    private String picPath = "";
    private Uri mPhotoUri;
    String string = null;

    private RelativeLayout mInformationOne;
    private CircleImageView mInformationImg;
    private RelativeLayout mInformationTwo;
    private TextView mInformationName;
    private TextView mInformationPhone;
    private EditText content = null;
    private AlertDialog alert;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(InformationActivity.this, R.color.black);

        initData();
        initListeners();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_information;
    }

    @Override
    public void initView() throws Exception {
        mInformationOne = (RelativeLayout) findViewById(R.id.Information_One);
        mInformationImg = (CircleImageView) findViewById(R.id.Information_Img);
        mInformationTwo = (RelativeLayout) findViewById(R.id.Information_Two);
        mInformationName = (TextView) findViewById(R.id.Information_Name);
        mInformationPhone = (TextView) findViewById(R.id.Information_Phone);
    }

    private void initData() {
        //获取个人信息
        Log.e("token", (String) SPUtils.get(this, "token", ""));
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itfaceId", "021");
        hashMap.put("token", SPUtils.get(this, "token", ""));
        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<PersonalinformationActivityBeans>(this) {
                    @Override
                    public void onSuccess(PersonalinformationActivityBeans mPersonalinformationActivityBeans, Call call, Response response) {
                        if (mPersonalinformationActivityBeans.getResultCode() == 1) {
                            Glide.with(InformationActivity.this).load(mPersonalinformationActivityBeans.getData().getHeadIco()).into(mInformationImg);
                            mInformationName.setText(mPersonalinformationActivityBeans.getData().getNickName());
                            mInformationPhone.setText(mPersonalinformationActivityBeans.getData().getMobile());
                        } else {
                            App.ErrorToken(mPersonalinformationActivityBeans.getResultCode(), mPersonalinformationActivityBeans.getMsg());
                        }
                    }
                });
    }

    private void initListeners() {
        mInformationOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuWindow = new SelectPicPopup(InformationActivity.this, itemsOnClick);
                View parent1 = LayoutInflater.from(InformationActivity.this).inflate(R.layout.activity_information, null);
                menuWindow.showAtLocation(parent1, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

        mInformationTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert = new AlertDialog.Builder(InformationActivity.this).create();
                alert.show();
                //加载自定义dialog
                alert.getWindow().setContentView(R.layout.delete_dialog);
                alert.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                //取消删除
                alert.getWindow().findViewById(R.id.cart_delete_cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                        return;
                    }
                });

                content = (EditText) alert.getWindow().findViewById(R.id.cart_delete_content);
                //确认修改
                alert.getWindow().findViewById(R.id.cart_delete_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!content.getText().toString().equals("")) {
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("itfaceId", "022");
                            hashMap.put("token", SPUtils.get(InformationActivity.this, "token", ""));
                            hashMap.put("nickName", content.getText().toString().trim());
                            JSONObject jsonObject = new JSONObject(hashMap);

                            OkGo.post(UrlContent.URL).tag(this)
                                    .upJson(jsonObject.toString())
                                    .connTimeOut(10_000)
                                    .execute(new CustomCallBackNoLoading<PersonalinformationModifitionBeans>(InformationActivity.this) {
                                        @Override
                                        public void onSuccess(PersonalinformationModifitionBeans mPersonalinformationModifitionBeans, Call call, Response response) {
                                            if (mPersonalinformationModifitionBeans.getResultCode() == 1) {
                                                Toast.makeText(InformationActivity.this, "修改用户名成功", Toast.LENGTH_SHORT).show();
                                                mInformationName.setText(content.getText().toString());
                                                alert.dismiss();
                                            } else {
                                                App.ErrorToken(mPersonalinformationModifitionBeans.getResultCode(), mPersonalinformationModifitionBeans.getMsg());
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(InformationActivity.this, "请输入修改的昵称", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                // 拍照
                case R.id.set_head_photograph:
                    if (ContextCompat.checkSelfPermission(InformationActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(InformationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(InformationActivity.this, Manifest.permission.CAMERA)) {
                        }
                        ActivityCompat.requestPermissions(InformationActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
                    } else {
                        takePhoto();
                    }
                    break;
                // 相册选择图片
                case R.id.set_head_album:
                    if (ContextCompat.checkSelfPermission(InformationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(InformationActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
                    } else {
                        pickPhoto();
                    }
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 拍照获取图片
     */
//    @NeedsPermission({Manifest.permission_group.CONTACTS})
    void takePhoto() {
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            /***
             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
             * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
             * 如果不使用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
             */
            ContentValues values = new ContentValues();
            mPhotoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        } else {
            Toast.makeText(this, "内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }


    /***
     * 从相册中取图片
     */
    void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        // 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }


    /**
     * 选择图片后，获取图片的路径
     *
     * @param requestCode
     * @param data
     */
    private void doPhoto(int requestCode, Intent data) {
        // 从相册取图片，有些手机有异常情况，请注意
        if (requestCode == SELECT_PIC_BY_PICK_PHOTO) {
            if (data == null) {
//                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
            mPhotoUri = data.getData();
            if (mPhotoUri == null) {
//                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
        }
        String[] pojo = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(mPhotoUri, pojo, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);
            // 4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
            if (Integer.parseInt(Build.VERSION.SDK) < 14) {
                cursor.close();
            }
        }
        // 如果图片符合要求将其上传到服务器
        if (picPath != null && (picPath.endsWith(".png") || picPath.endsWith(".PNG") ||
                picPath.endsWith(".jpg") || picPath.endsWith(".JPG"))) {

            BitmapFactory.Options option = new BitmapFactory.Options();
            // 压缩图片:表示缩略图大小为原始图片大小的几分之一，1为原图
            option.inSampleSize = 1;
            // 根据图片的SDCard路径读出Bitmap
            Bitmap bm = BitmapFactory.decodeFile(picPath, option);
            // 显示在图片控件上
            mInformationImg.setImageBitmap(bm);
            Log.i("mars", "doPhoto: " + picPath);
            if (bm != null) {
                bitmaptoString(bm, 24);
            }
            upLoadUImageHeader(string);
        } else {
            Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
        }
    }

    private void upLoadUImageHeader(String string) {
        if (string != null) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("itfaceId", "011");
            hashMap.put("headIco", string);
            hashMap.put("token", SPUtils.get(InformationActivity.this, "token", ""));
            JSONObject jsonObject = new JSONObject(hashMap);

            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<PersonalinformationModifitionImgBeans>(InformationActivity.this) {
                        @Override
                        public void onSuccess(PersonalinformationModifitionImgBeans mPersonalinformationModifitionImgBeans, Call call, Response response) {
                            if (mPersonalinformationModifitionImgBeans.getResultCode() == 1) {
                                Toast.makeText(InformationActivity.this, "头像上传成功", Toast.LENGTH_SHORT).show();
                            } else {
                                App.ErrorToken(mPersonalinformationModifitionImgBeans.getResultCode(), mPersonalinformationModifitionImgBeans.getMsg());
                            }
                        }
                    });
        }
    }

    /**
     * 将bitmap转换成base64字符串
     *
     * @param bitmap
     * @return base64 字符串
     */

    public String bitmaptoString(Bitmap bitmap, int bitmapQuality) {
        // 将Bitmap转换成字符串
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, bitmapQuality, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }


    /**
     * 接收从其他页面返回的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SELECT_PIC_BY_PICK_PHOTO:// 直接从相册获取
                doPhoto(requestCode, data);
                break;
            case SELECT_PIC_BY_TACK_PHOTO:// 调用相机拍照
                doPhoto(requestCode, data);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void Information_Bank(View view) {
        finish();
    }
}
