package com.wanquan.mlmmx.mlmm.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

//import com.umeng.socialize.ShareAction;
//import com.umeng.socialize.UMShareAPI;
//import com.umeng.socialize.UMShareListener;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.media.UMImage;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.MyAdapter;
import com.wanquan.mlmmx.mlmm.fragment.FragmentFour;
import com.wanquan.mlmmx.mlmm.fragment.FragmentOne;
import com.wanquan.mlmmx.mlmm.fragment.FragmentThree;
import com.wanquan.mlmmx.mlmm.fragment.FragmentTwo;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneSession;
import static com.wanquan.mlmmx.mlmm.App.api;
import static com.wanquan.mlmmx.mlmm.activity.BabyRoomActivity.bitmap2Bytes;
import static com.wanquan.mlmmx.mlmm.wxapi.Util.bmpToByteArray;

/**
 * 描述：空气小秘分享界面
 * 作者：薛昌峰
 * 时间：2018.01.03
 */
public class AirSmallShareActivity extends AppCompatActivity implements View.OnClickListener, IUiListener {
    private Tencent mTencent;
    private String APP_ID = "1106283467";
    private static final int THUMB_SIZE = 150;

    /**
     * 使用照相机拍照获取图片
     */
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    /**
     * 获取到的图片路径
     */
    private String picPath = "";
    private Uri mPhotoUri;
    private LinearLayout mShareBitmapBto;
    private LinearLayout mShareCamera;
    private LinearLayout mShareLin;
    private ViewPager mIdViewpager;
    private EditText mShareEditText;

    private List<Fragment> mList;
    private LinearLayout mShareQQ;
    private LinearLayout mSharePYQ;
    private LinearLayout mShareWeixin;
    private LinearLayout mShareWeibo;

    private FragmentOne mFragmentOne;
    private FragmentTwo mFragmentTwo;
    private FragmentThree mFragmentThree;
    private FragmentManager mFragmentManager;

    private MyAdapter mAdapter;

    private boolean isAgree = false;
    private View dView;
    private Bitmap bitmap = null;
    private static Bitmap screenshot1, screenshot2;
    private static Bitmap bitmaps;
    private static String str;
    private File f;
    private String path;
    private Bitmap bmp;
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;


    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airsmall_share);

        mTencent = Tencent.createInstance(APP_ID, getApplicationContext());

        initViews();
        initData();
        initListeners();

        mIdViewpager.setOffscreenPageLimit(0);
    }

    /**
     * 验证读取sd卡的权限
     *
     * @param activity
     */
    public boolean verifyStoragePermissions(Activity activity) {
        /*******below android 6.0*******/
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "授权失败,请去设置打开权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initViews() {
        mShareBitmapBto = (LinearLayout) findViewById(R.id.Share_BitmapBto);//输入框
        mShareCamera = (LinearLayout) findViewById(R.id.Share_Camera);
        mShareLin = (LinearLayout) findViewById(R.id.Share_Lin);
        mIdViewpager = (ViewPager) findViewById(R.id.id_viewpager);
        mShareEditText = (EditText) findViewById(R.id.Share_EditText);
        mShareQQ = (LinearLayout) findViewById(R.id.Share_QQ);
        mSharePYQ = (LinearLayout) findViewById(R.id.Share_PYQ);
        mShareWeixin = (LinearLayout) findViewById(R.id.Share_Weixin);
        mShareWeibo = (LinearLayout) findViewById(R.id.Share_Weibo);
    }

    private void initData() {
        mList = new ArrayList<>();
        mFragmentOne = new FragmentOne();
        mFragmentTwo = new FragmentTwo();
        mFragmentThree = new FragmentThree();

        mList.add(mFragmentOne);
        mList.add(mFragmentTwo);
        mList.add(mFragmentThree);

        //初始化适配器
        mFragmentManager = getSupportFragmentManager();
        mAdapter = new MyAdapter(mFragmentManager, mList);
        mIdViewpager.setAdapter(mAdapter);

        mIdViewpager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin));
    }

    private void initListeners() {
        mShareCamera.setOnClickListener(this);
        mShareQQ.setOnClickListener(this);
        mSharePYQ.setOnClickListener(this);
        mShareWeixin.setOnClickListener(this);
        mShareWeibo.setOnClickListener(this);
        mShareEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareEditText.setCursorVisible(true);
            }
        });

//    mMainActivityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//      @Override
//      public void onCheckedChanged(RadioGroup group, int checkedId) {
//        resetViewPager(checkedId);
//      }
//    });

        //调用系统相机
        mShareCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(AirSmallShareActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(AirSmallShareActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(AirSmallShareActivity.this, Manifest.permission.CAMERA)) {
                    }
                    ActivityCompat.requestPermissions(AirSmallShareActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
                } else {
                    takePhoto();
                }
            }
        });

        // 将父类的touch事件分发至viewPgaer，否则只能滑动中间的一个view对象
        mShareLin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mIdViewpager.dispatchTouchEvent(motionEvent);
            }
        });

        //滑动ViewPage的时候及时修改底部导航栏对应的图标
        mIdViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //根据当前位置设置默认选中单选按钮
//        resetRadioButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

//    public void resetViewPager(int checkedId) {
//        switch (checkedId) {
//            case R.id.ShareActivity_RadioButton1:
//                mIdViewpager.setCurrentItem(0);
//                break;
//            case R.id.ShareActivity_RadioButton2:
//                mIdViewpager.setCurrentItem(1);
//                break;
//            case R.id.ShareActivity_RadioButton3:
//                mIdViewpager.setCurrentItem(2);
//                break;
//            case R.id.ShareActivity_RadioButton4:
//                mIdViewpager.setCurrentItem(3);
//                break;
//        }
//    }

//    private void resetRadioButton(int position) {
//        //获取position位置处对于的单选按钮
//        RadioButton radioButton = (RadioButton) mMainActivityRadioGroup.getChildAt(position);
//        //设置当前单选按钮默认选中
//        radioButton.setChecked(true);
//    }

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
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mPhotoUri);
            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        } else {
            Toast.makeText(this, "内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 选择图片后，获取图片的路径
     *
     * @param requestCode
     * @param data
     */
    private void doPhoto(int requestCode, Intent data) {
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
//            PersonalInformation_Head.setImageBitmap(bm);
//            Log.e("mars", "doPhoto: " + picPath);
//            Toast.makeText(this, picPath, Toast.LENGTH_SHORT).show();
            SPUtils.put(AirSmallShareActivity.this, "path", picPath);
//            initData(1);
            //初始化适配器
//            mFragmentManager = getSupportFragmentManager();
//            mAdapter = new MyAdapter(mFragmentManager, mList);
//            mIdViewpager.setAdapter(mAdapter);
//            upLoadUImageHeader(picPath);
        } else {
            Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
        }
    }

    public void Share_close(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (mTencent != null) {
            Tencent.onActivityResultData(requestCode, resultCode, data, this);
        }
        // 点击取消按钮
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case SELECT_PIC_BY_TACK_PHOTO:// 调用相机拍照
                doPhoto(requestCode, data);
                break;
        }
//        switch (requestCode) {
//            case 1:
//                if (resultCode == 11) {
//                    path = data.getStringExtra("picPath");
//                    Log.e("000000000000", path);
//                    if (path != null) {
//                        if (mFragmentOne != null) {
//                            Bundle bundle = new Bundle();
//                            bundle.putString("path", path);
//                            mFragmentOne.setArguments(bundle);
//                        }
//                    }
//                }
//                break;
//        }
    }

    //获得Bitmap的封装的静态方法(截取view)
    public static Bitmap loadBitmapFromView(View v) {
        if (v == null) {
            return null;
        }
        screenshot1 = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(screenshot1);
        canvas.translate(-v.getScrollX(), -v.getScrollY());//我们在用滑动View获得它的Bitmap时候，获得的是整个View的区域（包括隐藏的），如果想得到当前区域，需要重新定位到当前可显示的区域
        v.draw(canvas);// 将 view 画到画布上
        return screenshot1;
    }

    public static Bitmap loadBitmapFromView2(View v) {
        if (v == null) {
            return null;
        }
        screenshot2 = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(screenshot2);
        canvas.translate(-v.getScrollX(), -v.getScrollY());//我们在用滑动View获得它的Bitmap时候，获得的是整个View的区域（包括隐藏的），如果想得到当前区域，需要重新定位到当前可显示的区域
        v.draw(canvas);// 将 view 画到画布上
        return screenshot2;
    }

    /**
     * 把两个位图覆盖合成为一个位图，上下拼接
     *
     * @param leftBitmap
     * @param rightBitmap
     * @param isBaseMax   是否以高度大的位图为准，true则小图等比拉伸，false则大图等比压缩
     * @return
     */
    public static Bitmap mergeBitmap_TB(Bitmap topBitmap, Bitmap bottomBitmap, boolean isBaseMax) {
        if (topBitmap == null || topBitmap.isRecycled()
                || bottomBitmap == null || bottomBitmap.isRecycled()) {
//            JDLog.logError(TAG, "topBitmap=" + topBitmap + ";bottomBitmap=" + bottomBitmap);
            return null;
        }
        int width = 0;
        if (isBaseMax) {
            width = topBitmap.getWidth() > bottomBitmap.getWidth() ? topBitmap.getWidth() : bottomBitmap.getWidth();
        } else {
            width = topBitmap.getWidth() < bottomBitmap.getWidth() ? topBitmap.getWidth() : bottomBitmap.getWidth();
        }
        Bitmap tempBitmapT = topBitmap;
        Bitmap tempBitmapB = bottomBitmap;

        if (topBitmap.getWidth() != width) {
            tempBitmapT = Bitmap.createScaledBitmap(topBitmap, width, (int) (topBitmap.getHeight() * 1f / topBitmap.getWidth() * width), false);
        } else if (bottomBitmap.getWidth() != width) {
            tempBitmapB = Bitmap.createScaledBitmap(bottomBitmap, width, (int) (bottomBitmap.getHeight() * 1f / bottomBitmap.getWidth() * width), false);
        }

        int height = tempBitmapT.getHeight() + tempBitmapB.getHeight();

        bitmaps = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmaps);

        Rect topRect = new Rect(0, 0, tempBitmapT.getWidth(), tempBitmapT.getHeight());
        Rect bottomRect = new Rect(0, 0, tempBitmapB.getWidth(), tempBitmapB.getHeight());

        Rect bottomRectT = new Rect(0, tempBitmapT.getHeight(), width, height);

        canvas.drawBitmap(tempBitmapT, topRect, topRect, null);
        canvas.drawBitmap(tempBitmapB, bottomRect, bottomRectT, null);
        return bitmaps;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SPUtils.put(AirSmallShareActivity.this, "path", "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Share_Camera:
//                startActivityForResult(new Intent(ShareActivity.this, CameraActivity.class), 1);
//                startActivity(new Intent(AirSmallShareActivity.this, CameraActivity.class));
                break;
            case R.id.Share_QQ:
                mShareEditText.setCursorVisible(false);
                loadBitmapFromView(mIdViewpager);//截取ViewPager
                loadBitmapFromView2(mShareBitmapBto);//截取输入框
                mergeBitmap_TB(screenshot1, screenshot2, false);
                bitMap2File(bitmaps);

                Bundle shareParams = new Bundle();
                shareParams.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                shareParams.putString(QQShare.SHARE_TO_QQ_TITLE, "美丽妈妈好空气");
//                shareParams.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, f.getPath());
                shareParams.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, String.valueOf(f));
//                shareParams.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, String.valueOf(f));

                shareParams.putString(QQShare.SHARE_TO_QQ_APP_NAME, "美丽妈妈好空气");
                shareParams.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
                mTencent.shareToQQ(AirSmallShareActivity.this, shareParams, AirSmallShareActivity.this);

//                doShareToQQ(shareParams);
//                Bundle params = new Bundle();
//                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//                params.putString(QQShare.SHARE_TO_QQ_TITLE, "美丽妈妈好空气");
//                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "美丽妈妈好空气");
//                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, String.valueOf(bitmaps));
//                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, String.valueOf(bitmaps));
//                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "美丽妈妈");
//                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
//                mTencent.shareToQQ(AirSmallShareActivity.this, params, AirSmallShareActivity.this);
                break;
            case R.id.Share_PYQ:
                verifyStoragePermissions(this);

                mShareEditText.setCursorVisible(false);
                if (bitmap != null) {
                    bitmap.recycle();
                }
                loadBitmapFromView(mIdViewpager);//截取ViewPager
                loadBitmapFromView2(mShareBitmapBto);//截取输入框
                mergeBitmap_TB(screenshot1, screenshot2, false);
                // 微信注册初始化
                api = WXAPIFactory.createWXAPI(AirSmallShareActivity.this, "wxeca84b2f356693a2", true);
                api.registerApp("wxeca84b2f356693a2");
                bitMap2File(bitmaps);
                imageShare(String.valueOf(f), 1);
                break;
            case R.id.Share_Weixin:
                verifyStoragePermissions(this);

                if (bitmap != null) {
                    bitmap.recycle();
                }
                loadBitmapFromView(mIdViewpager);//截取ViewPager
                loadBitmapFromView2(mShareBitmapBto);//截取输入框
                mergeBitmap_TB(screenshot1, screenshot2, false);
                // 微信注册初始化
                api = WXAPIFactory.createWXAPI(AirSmallShareActivity.this, "wxeca84b2f356693a2", true);
                api.registerApp("wxeca84b2f356693a2");
                bitMap2File(bitmaps);
                imageShare(String.valueOf(f), 0);
                break;
            case R.id.Share_Weibo:
                if (bitmap != null) {
                    bitmap.recycle();
                }
                loadBitmapFromView(mIdViewpager);//截取ViewPager
                loadBitmapFromView2(mShareBitmapBto);//截取输入框
                mergeBitmap_TB(screenshot1, screenshot2, false);
                break;
            default:
                break;
        }
    }

    public void imageShare(String imgurl, int sendtype) {
        File file = new File(imgurl);
        if (!file.exists()) {
            Toast.makeText(AirSmallShareActivity.this, "图片不存在", Toast.LENGTH_LONG).show();
        }
        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(imgurl);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        try {
            decodeFile(imgurl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        msg.setThumbImage(thumbBmp);
        bmp.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = sendtype == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    /**
     * 根据 路径 得到 file 得到 bitmap
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public Bitmap decodeFile(String filePath) throws IOException {
        Bitmap b = null;
        int IMAGE_MAX_SIZE = 600;

        File f = new File(filePath);
        if (f == null) {
            return null;
        }
        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        FileInputStream fis = new FileInputStream(f);
        BitmapFactory.decodeStream(fis, null, o);
        fis.close();

        int scale = 1;
        if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
            scale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
        }

        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        fis = new FileInputStream(f);
        bmp = BitmapFactory.decodeStream(fis, null, o2);
        fis.close();
        return b;
    }

    /**
     * 把bitmap转化为file
     **/
    public File bitMap2File(Bitmap bitmap) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            //保存到sd根目录下
            path = Environment.getExternalStorageDirectory() + File.separator;
        }
        f = new File(path, "share" + ".jpg");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            bitmap.recycle();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return f;
        }
    }

    @Override
    public void onComplete(Object o) {
//        Toast.makeText(this, o.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(UiError uiError) {
//        Toast.makeText(this, uiError.errorMessage + "--" + uiError.errorCode + "---" + uiError.errorDetail, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
    }


}
