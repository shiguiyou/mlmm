package com.wanquan.mlmmx.mlmm.phone;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.wanquan.mlmmx.mlmm.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 描述：这个是进入相册显示所有图片的界面，第一次进入时界面
 * 作者：薛昌峰
 * 时间：2017.11.03
 */
public class Photo_Album_Activity extends Activity {
    public static final int ALBUM_OPEN = 11;
    public static final int RESULT_CODE = 6;//发表任务结果返回码
    //显示手机里的所有图片的列表控件
    private GridView gridView;
    //当手机里没有图片时，提示用户没有图片的控件
    private TextView tv;
    //gridView的adapter
    private Photo_AlbumGridView_Adapter gridImageAdapter;
    //上传按钮
    private LinearLayout okButton;
    // 返回按钮
    private TextView back;
    // 取消按钮
    private Button cancel;
    //选择的照片数
    private TextView photoNumber;
    private Intent intent;
    // 预览按钮
    private Button preview;
    //上传头像传来的数据
    Intent intentPhoto;
    String album_type = "";
    String userId = "";
    TextView mText;//头像上传的上传按钮
    //相册上传
    int code;
    //相册ID
    int Album_ID;
    Dialog dialog;
    private Context mContext;
    private ArrayList<Photo_ImageItem_Beans> dataList;
    private Photo_AlbumHelper helper;
    public static List<Photo_ImageBucket> contentList;
    public static Bitmap bitmap;
    //上传获取图片服务器路径标志位
    int flag_upload = 0;
    int[] urls;
    List<String> url_str;
    String Message_Flag = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_myphoto_uploading);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        //第三方开源库（解决 沉浸式与输入法遮挡问题）
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.black);//通知栏所需颜色

        if (Photo_Bimp.mPreview_activity != null) {
            Photo_Bimp.mPreview_activity.clear();
            Photo_Bimp.mPreview_activity.add(this);
        }
        //Photo_PublicWay.activityList.add(this);
        Photo_Bimp.Album_type = "";
        mContext = this;
        intentPhoto = getIntent();
        // Album_ID=intentPhoto.getIntExtra("Album_ID",0);
        album_type = intentPhoto.getStringExtra("album_type");
        userId = intentPhoto.getStringExtra("userId");
        Message_Flag = intentPhoto.getStringExtra("Message");
        Photo_Bimp.ChatType = intentPhoto.getIntExtra("IDType", 0);
        //注册一个广播，这个广播主要是用于在GalleryActivity进行预览时，防止当所有图片都删除完后，再回到该页面时被取消选中的图片仍处于选中状态
        /*IntentFilter filter = new IntentFilter("data.broadcast.action");
        registerReceiver(broadcastReceiver, filter);*/
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.plugin_camera_no_pictures);
        //相册上传
        AlbumUpload();
        init();
        //上传头像跳转传值
        initPhoto();
        initListener();
        //这个函数主要用来控制预览和完成按钮的状态
        isShowOkBt_begin();
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    //相册上传
    private void AlbumUpload() {
        code = intentPhoto.getIntExtra("album_upload", 0);
    }

    //上传头像跳转传值
    private void initPhoto() {
        if (album_type != null) {
            if (album_type.equals("replace_photo")) {
                Photo_Bimp.Album_type = "replace_photo";
                Photo_PublicWay.num = 1;
                mText.setVisibility(View.VISIBLE);
                okButton.setVisibility(View.GONE);
                preview.setVisibility(View.GONE);
                //头像上传点击事件
                mText.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Photo_Bimp.tempSelectBitmap.size() != 0) {
                            Intent intent_2 = new Intent(Photo_Album_Activity.this, ReplacPhoto_Look_Activity.class);
                            intent_2.putExtra("headerPath", Photo_Bimp.tempSelectBitmap.get(0).imagePath);
                            intent_2.putExtra("userId", userId);
                            startActivity(intent_2);
                            finish();
                        }
                    }
                });

            } else if ("putTask".equals(album_type)) {
                Photo_Bimp.Album_type = "putTask";
                Photo_PublicWay.num = 1;
                mText.setVisibility(View.VISIBLE);
                okButton.setVisibility(View.GONE);
                preview.setVisibility(View.GONE);
                //发表任务上传点击事件
                mText.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Photo_Bimp.tempSelectBitmap.size() != 0) {
                            Photo_Bimp.taskSelectBitmap.clear();
                            Photo_Bimp.taskSelectBitmap.add(Photo_Bimp.tempSelectBitmap.get(0));

                            finish();
                        }
                    }
                });

            } else if ("chatPhoto".equals(album_type)) {
                Photo_Bimp.Album_type = "chatPhoto";
                Photo_PublicWay.num = 1;
                mText.setVisibility(View.VISIBLE);
                okButton.setVisibility(View.GONE);
                preview.setVisibility(View.GONE);
                //聊天上传点击事件
                mText.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Photo_Bimp.tempSelectBitmap.size() != 0) {
                            Photo_Bimp.chatSelectBitmap.clear();
                            Photo_Bimp.chatSelectBitmap.add(Photo_Bimp.tempSelectBitmap.get(0));
                            Intent intent = new Intent(mContext, Chat_PhotoPreView_Activity.class);
                            intent.putExtra("IDType", Photo_Bimp.ChatType);
                            intent.putExtra("Message", Message_Flag);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        } else {
            Photo_PublicWay.num = 20;
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //mContext.unregisterReceiver(this);
            // TODO Auto-generated method stub  
            gridImageAdapter.notifyDataSetChanged();
        }
    };

    // 预览按钮的监听
    private class PreviewListener implements OnClickListener {
        public void onClick(View v) {
            if (code != ALBUM_OPEN) {
                if (Photo_Bimp.tempSelectBitmap.size() > 0) {
                    intent.putExtra("position", "1");
                    intent.setClass(Photo_Album_Activity.this, Photo_Preview_Activity.class);
                    startActivity(intent);
                }
            } else {
                if (Photo_Bimp.tempSelectBitmap.size() > 0) {
                    intent.putExtra("position", "1");
                    intent.putExtra("type", 1);
                    intent.setClass(Photo_Album_Activity.this, Photo_Preview_Activity.class);
                    startActivityForResult(intent, 2);
                }
            }
        }
    }

    // 上传按钮的监听
    private class AlbumSendListener implements OnClickListener {
        public void onClick(View v) {
            overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
            if (code != ALBUM_OPEN) {
                //发表说说
                Photo_Bimp.albumSelectBitmap = Photo_Bimp.tempSelectBitmap;
                Photo_Bimp.Album_open = 0;
                finish();
            } else {
                //上传相册
                ArrayList<Photo_ImageItem_Beans> beans = new ArrayList<>();
                Photo_Bimp.albumSelectBitmap = Photo_Bimp.tempSelectBitmap;
                beans.addAll(Photo_Bimp.albumSelectBitmap);
                Photo_Bimp.tempSelectBitmap.clear();
                Photo_Bimp.albumSelectBitmap = beans;
                Log.e("xcfxcfAlbum_ID", Album_ID + "");
                Photo_Bimp.Album_open = 0;

            }

        }

    }

    // 返回按钮监听
    private class BackListener implements OnClickListener {
        public void onClick(View v) {
            //返回相册列表界面
            //清空选择的图片
            // Photo_Bimp.tempSelectBitmap.clear();
            intent.setClass(Photo_Album_Activity.this, Photo_ImageFile_Activity.class);
            intent.putExtra("Album_ID", Album_ID);
            intent.putExtra("Album_code", code);
            intent.putExtra("userId", userId);
            intent.putExtra("Message", Message_Flag);
            startActivity(intent);
            finish();
        }
    }

    // 初始化，给一些对象赋值
    private void init() {
        helper = Photo_AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        contentList = helper.getImagesBucketList(false);
        dataList = new ArrayList<>();
        for (int i = 0; i < contentList.size(); i++) {
            dataList.addAll(contentList.get(i).imageList);
            Collections.reverse(dataList);
        }
        back = (TextView) findViewById(R.id.home_myPhotoUploading_return);
        photoNumber = (TextView) findViewById(R.id.home_myPhotoUploading_uploadNumber);
        mText = (TextView) findViewById(R.id.replace_photo_upload);
        back.setOnClickListener(new BackListener());

        preview = (Button) findViewById(R.id.home_myPhotoUploading_preview);
        preview.setOnClickListener(new PreviewListener());
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        //绑定适配器
        gridView = (GridView) findViewById(R.id.home_myPhotoUploading_gridView);
        gridImageAdapter = new Photo_AlbumGridView_Adapter(this, dataList,
                Photo_Bimp.tempSelectBitmap);
        gridView.setAdapter(gridImageAdapter);
        tv = (TextView) findViewById(R.id.myText);
        //空视图
        gridView.setEmptyView(tv);

        okButton = (LinearLayout) findViewById(R.id.home_myPhotoUploading_upload);
        if (code != ALBUM_OPEN) {
            photoNumber.setText(Photo_Bimp.tempSelectBitmap.size()
                    + "/" + Photo_PublicWay.num);
        } else {
            photoNumber.setText(Photo_Bimp.tempSelectBitmap.size() + "");
        }
    }

    private void initListener() {
        gridImageAdapter.setOnItemClickListener(new Photo_AlbumGridView_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(final ToggleButton toggleButton,
                                    int position, boolean isChecked, CheckBox chooseBt) {

                if (Photo_Bimp.tempSelectBitmap.size() >= Photo_PublicWay.num) {
                    toggleButton.setChecked(false);
                    chooseBt.setChecked(false);
                    if (!removeOneData(dataList.get(position))) {
                        //Toast.makeText(Photo_Album_Activity.this, Photo_Res_Utils.getString("only_choose_num"),
                        //		200).show();
                    }
                    return;
                }
                if (isChecked) {
                    chooseBt.setChecked(true);
                    Photo_Bimp.tempSelectBitmap.add(dataList.get(position));
                    //判断是否是通过相册上传打开的界面
                    if (code != ALBUM_OPEN) {
                        photoNumber.setText(Photo_Bimp.tempSelectBitmap.size() + "/" + Photo_PublicWay.num);
                    } else {
                        photoNumber.setText(Photo_Bimp.tempSelectBitmap.size() + "");
                    }
                } else {
                    Photo_Bimp.tempSelectBitmap.remove(dataList.get(position));
                    //隐藏控件
                    chooseBt.setChecked(false);
                    //判断是否是通过相册上传打开的界面
                    if (code != ALBUM_OPEN) {
                        //显示选择的照片数
                        photoNumber.setText(Photo_Bimp.tempSelectBitmap.size()
                                + "/" + Photo_PublicWay.num);
                    } else {
                        //显示选择的照片数
                        photoNumber.setText(Photo_Bimp.tempSelectBitmap.size() + "");
                    }
                }

                if (album_type != null) {
                    if (album_type.equals("replace_photo")) {
                        //  isShowOkBt_2();
                    }
                } else if (code == ALBUM_OPEN) {
                    isShowOkBt_3();
                } else {
                    isShowOkBt();
                }
            }
        });
        okButton.setOnClickListener(new AlbumSendListener());
    }

    private boolean removeOneData(Photo_ImageItem_Beans imageItem) {
        if (Photo_Bimp.tempSelectBitmap.contains(imageItem)) {
            Photo_Bimp.tempSelectBitmap.remove(imageItem);
            if (code != ALBUM_OPEN) {
                photoNumber.setText(Photo_Bimp.tempSelectBitmap.size() + "/" + Photo_PublicWay.num);
            } else {
                photoNumber.setText(Photo_Bimp.tempSelectBitmap.size() + "");
            }
            return true;
        }
        return false;
    }

    public void isShowOkBt_begin() {
        if (Photo_Bimp.tempSelectBitmap.size() > 0) {
            preview.setPressed(true);
            okButton.setPressed(true);
            preview.setClickable(true);
            okButton.setClickable(true);
            preview.setTextColor(Color.BLACK);
        } else {
            preview.setPressed(false);
            preview.setClickable(false);
            okButton.setPressed(false);
            okButton.setClickable(false);
            preview.setTextColor(Color.parseColor("#333333"));
        }
    }

    public void isShowOkBt() {
        if (Photo_Bimp.tempSelectBitmap.size() > 0) {
            photoNumber.setText(Photo_Bimp.tempSelectBitmap.size() + "/" + Photo_PublicWay.num);
            preview.setPressed(true);
            okButton.setPressed(true);
            preview.setClickable(true);
            okButton.setClickable(true);
            preview.setTextColor(Color.BLACK);
        } else {
            photoNumber.setText(Photo_Bimp.tempSelectBitmap.size() + "/" + Photo_PublicWay.num);
            preview.setPressed(false);
            preview.setClickable(false);
            okButton.setPressed(false);
            okButton.setClickable(false);
            preview.setTextColor(Color.parseColor("#333333"));
        }
    }

    public void isShowOkBt_3() {
        if (Photo_Bimp.tempSelectBitmap.size() > 0) {
            photoNumber.setText(Photo_Bimp.tempSelectBitmap.size() + "");
            preview.setPressed(true);
            okButton.setPressed(true);
            preview.setClickable(true);
            okButton.setClickable(true);
            preview.setTextColor(Color.BLACK);
        } else {
            photoNumber.setText(Photo_Bimp.tempSelectBitmap.size() + "");
            preview.setPressed(false);
            preview.setClickable(false);
            okButton.setPressed(false);
            okButton.setClickable(false);
            preview.setTextColor(Color.parseColor("#333333"));
        }
    }


    //头像上传
    public void isShowOkBt_2() {
        if (Photo_Bimp.tempSelectBitmap.size() > 0) {
            mText.setClickable(true);
        } else {
            mText.setClickable(false);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //清空选择的图片
            //Photo_Bimp.tempSelectBitmap.clear();
            intent.setClass(Photo_Album_Activity.this, Photo_ImageFile_Activity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }

    @Override
    protected void onRestart() {
        isShowOkBt_begin();
        super.onRestart();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Photo_Bimp.tempSelectBitmap.clear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                //上传相册
                ArrayList<Photo_ImageItem_Beans> beans = new ArrayList<>();
                Photo_Bimp.albumSelectBitmap = Photo_Bimp.tempSelectBitmap;
                beans.addAll(Photo_Bimp.albumSelectBitmap);
                Photo_Bimp.tempSelectBitmap.clear();
                Photo_Bimp.albumSelectBitmap = beans;

                urls = new int[Photo_Bimp.albumSelectBitmap.size()];
                url_str = new ArrayList<>();
                /*for(int i=0;i<Photo_Bimp.albumSelectBitmap.size();i++){
                    String name=LoginUsers.otherUserId +"/"+System.currentTimeMillis();
                    uploadPhotosHttp(name,Photo_Bimp.albumSelectBitmap.get(i).getBitmap());
                }*/
                Log.e("xcfxcfAlbum_ID", Album_ID + "");
                Photo_Bimp.Album_open = 0;
                break;
        }
    }
}
