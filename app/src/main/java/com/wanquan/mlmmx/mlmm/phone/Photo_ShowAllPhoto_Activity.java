package com.wanquan.mlmmx.mlmm.phone;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个是显示一个文件夹里面的所有图片时的界面
 */
public class Photo_ShowAllPhoto_Activity extends Activity {
    private GridView gridView;
    private Photo_AlbumGridView_Adapter gridImageAdapter;
    // 完成按钮
    private LinearLayout okButton;
    // 预览按钮
    private Button preview;
    // 返回按钮
    private TextView back;
    //选择的照片数
    private TextView photoNumber;
    //空视图
    private TextView tv;
    // 标题
    private TextView headTitle;
    //头像上传部分
    private TextView mTextUpload;
    //上传获取图片服务器路径标志位
    int flag_upload = 0;
    int[] urls;
    List<String> url_str;
    //相册id，发说说时为0
    int Album_ID;
    //操作类型，11时为上传相册图片，其他为发表说说操作
    int code;
    String title;//标题
    Dialog dialog;
    private Intent intent;
    private Context mContext;
    String userId;//用户id
    String Message_Flag = "";
    public static ArrayList<Photo_ImageItem_Beans> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_myphoto_uploading);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(Photo_ShowAllPhoto_Activity.this, R.color.black);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        //第三方开源库（解决 沉浸式与输入法遮挡问题）
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.zhong);//通知栏所需颜色

        Photo_Bimp.albumSelectBitmap.clear();
        intent = getIntent();
        code = intent.getIntExtra("Album_code", 0);
        //Album_ID=intents.getIntExtra("Album_ID",0);
        title = intent.getStringExtra("folderName");
        userId = intent.getStringExtra("userId");
        Message_Flag = intent.getStringExtra("Message");

        Photo_PublicWay.activityList.add(this);
        mContext = this;
        back = (TextView) findViewById(R.id.home_myPhotoUploading_return);
        photoNumber = (TextView) findViewById(R.id.home_myPhotoUploading_uploadNumber);
        headTitle = (TextView) findViewById(R.id.home_myPhotoUploading_albumsName);
        preview = (Button) findViewById(R.id.home_myPhotoUploading_preview);
        okButton = (LinearLayout) findViewById(R.id.home_myPhotoUploading_upload);
        mTextUpload = (TextView) findViewById(R.id.replace_photo_album_upload);

        headTitle.setText(title);
        back.setOnClickListener(new BackListener(intent));
        preview.setOnClickListener(new PreviewListener());

        init();
        initPhoto();
        initListener();
        isShowOkBt();
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

    //更换头像跳转
    private void initPhoto() {
        if (Photo_Bimp.Album_type != null) {
            if (Photo_Bimp.Album_type.equals("replace_photo")) {
                mTextUpload.setVisibility(View.VISIBLE);
                okButton.setVisibility(View.GONE);
                preview.setVisibility(View.GONE);
                mTextUpload.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Photo_Bimp.tempSelectBitmap.size() != 0) {
                            Intent intent_2 = new Intent(Photo_ShowAllPhoto_Activity.this, ReplacPhoto_Look_Activity.class);
                            intent_2.putExtra("headerPath", Photo_Bimp.tempSelectBitmap.get(0).imagePath);
                            intent_2.putExtra("userId", userId);
                            startActivity(intent_2);
                            //都销毁
                            Photo_Bimp.mList_activity.get(0).finish();
                            finish();
                        }
                    }
                });
            } else if ("putTask".equals(Photo_Bimp.Album_type)) {
                mTextUpload.setVisibility(View.VISIBLE);
                okButton.setVisibility(View.GONE);
                preview.setVisibility(View.GONE);
                //发表任务上传点击事件
                mTextUpload.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Photo_Bimp.tempSelectBitmap.size() != 0) {
                            Photo_Bimp.taskSelectBitmap.clear();
                            Photo_Bimp.taskSelectBitmap.add(Photo_Bimp.tempSelectBitmap.get(0));
                            Log.e("Photo_Bimp", Photo_Bimp.mList_activity.size() + "");
                            //都销毁
                            Photo_Bimp.mList_activity.get(0).finish();
                            finish();
                        }
                    }
                });

            } else if ("chatPhoto".equals(Photo_Bimp.Album_type)) {
                mTextUpload.setVisibility(View.VISIBLE);
                okButton.setVisibility(View.GONE);
                preview.setVisibility(View.GONE);
                //聊天上传点击事件
                mTextUpload.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Photo_Bimp.tempSelectBitmap.size() != 0) {
                            Photo_Bimp.chatSelectBitmap.clear();
                            Photo_Bimp.chatSelectBitmap.add(Photo_Bimp.tempSelectBitmap.get(0));
                            Intent intent = new Intent(mContext, Chat_PhotoPreView_Activity.class);
                            intent.putExtra("IDType", Photo_Bimp.ChatType);
                            intent.putExtra("Message", Message_Flag);
                            startActivity(intent);
                            //都销毁
                            Photo_Bimp.mList_activity.get(0).finish();
                            finish();
                        }
                    }
                });
            }
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            gridImageAdapter.notifyDataSetChanged();
        }
    };

    //预览点击事件
    private class PreviewListener implements OnClickListener {
        public void onClick(View v) {
            if (code != 11) {
                if (Photo_Bimp.tempSelectBitmap.size() > 0) {
                    intent.putExtra("position", "2");
                    intent.setClass(Photo_ShowAllPhoto_Activity.this, Photo_Preview_Activity.class);
                    startActivity(intent);
                }
            } else {
                //上传相册
                if (Photo_Bimp.tempSelectBitmap.size() > 0) {
                    intent.putExtra("position", "2");
                    intent.putExtra("type", 1);
                    intent.setClass(Photo_ShowAllPhoto_Activity.this, Photo_Preview_Activity.class);
                    startActivityForResult(intent, 2);
                }
            }
        }
    }

    private class BackListener implements OnClickListener {// 返回按钮监听
        Intent intent;

        public BackListener(Intent intent) {
            this.intent = intent;

        }

        public void onClick(View v) {
            //清空选择的图片
            Photo_Bimp.tempSelectBitmap.clear();
            finish();
        }

    }

    private void init() {
        /*IntentFilter filter = new IntentFilter("data.broadcast.action");
        registerReceiver(broadcastReceiver, filter);*/
        gridView = (GridView) findViewById(R.id.home_myPhotoUploading_gridView);
        gridImageAdapter = new Photo_AlbumGridView_Adapter(this, dataList, Photo_Bimp.tempSelectBitmap);
        Log.e("xcf", dataList.toString());
        gridView.setAdapter(gridImageAdapter);
        tv = (TextView) findViewById(R.id.myText);
        //空视图
        gridView.setEmptyView(tv);
    }

    private void initListener() {
        gridImageAdapter.setOnItemClickListener(new Photo_AlbumGridView_Adapter.OnItemClickListener() {
            public void onItemClick(final ToggleButton toggleButton,
                                    int position, boolean isChecked,
                                    CheckBox button) {
                if (Photo_Bimp.tempSelectBitmap.size() >= Photo_PublicWay.num && isChecked) {
                    button.setChecked(false);
                    toggleButton.setChecked(false);
                    return;
                }

                if (isChecked) {
                    button.setChecked(true);
                    Photo_Bimp.tempSelectBitmap.add(dataList.get(position));
                    photoNumber.setText(Photo_Bimp.tempSelectBitmap.size() + "/" + Photo_PublicWay.num);
                } else {
                    button.setChecked(false);
                    Photo_Bimp.tempSelectBitmap.remove(dataList.get(position));
                    photoNumber.setText(Photo_Bimp.tempSelectBitmap.size() + "/" + Photo_PublicWay.num);
                }
                isShowOkBt();
            }
        });
        //上传按钮点击事件
        okButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code != 11) {
                    //发表说说
                    Photo_Bimp.albumSelectBitmap = Photo_Bimp.tempSelectBitmap;
                    Log.e("testing", "图片" + Photo_Bimp.albumSelectBitmap.size());
                    Photo_Bimp.Album_open = 0;
                    Photo_Bimp.mList_activity.get(0).finish();
                    Photo_Bimp.mList_activity.clear();
                    finish();
                } else {
                    //上传相册图片
					/*dialog=new MyDialog_Views(Photo_ShowAllPhoto_Activity.this,R.style.MyDialog);
					dialog.show();
					MyDialog_Views myDialog_views = new MyDialog_Views(Photo_ShowAllPhoto_Activity.this,"正在上传...","");
					ArrayList<Photo_ImageItem_Beans> beans=new ArrayList<>();
					Photo_Bimp.albumSelectBitmap = Photo_Bimp.tempSelectBitmap;
					beans.addAll(Photo_Bimp.albumSelectBitmap);
					Photo_Bimp.tempSelectBitmap.clear();
					Photo_Bimp.albumSelectBitmap=beans;
					urls=new int[Photo_Bimp.albumSelectBitmap.size()];
					url_str=new ArrayList<String>();
					for(int i=0;i<Photo_Bimp.albumSelectBitmap.size();i++){
						String name=LoginUsers.otherUserId +"/"+System.currentTimeMillis();
						uploadPhotosHttp(name,Photo_Bimp.albumSelectBitmap.get(i).getBitmap());
					}
					Log.e("Album_ID",Album_ID+"");
					Photo_Bimp.Album_open=0;*/
                }
                //Photo_Bimp.uploadSelectBitmap=Photo_Bimp.tempSelectBitmap;
				/*//清空选择的图片
				Photo_Bimp.tempSelectBitmap.clear();*/


            }
        });

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
            preview.setTextColor(Color.parseColor("#000000"));
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //清空选择的图片
            Photo_Bimp.tempSelectBitmap.clear();
            finish();
        }
        return true;
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        isShowOkBt();
        super.onRestart();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                //上传相册图片
				/*dialog=new MyDialog_Views(Photo_ShowAllPhoto_Activity.this,R.style.MyDialog);
				dialog.show();
				MyDialog_Views myDialog_views = new MyDialog_Views(Photo_ShowAllPhoto_Activity.this,"正在上传...","");
				ArrayList<Photo_ImageItem_Beans> beans=new ArrayList<>();
				Photo_Bimp.albumSelectBitmap = Photo_Bimp.tempSelectBitmap;
				beans.addAll(Photo_Bimp.albumSelectBitmap);
				Photo_Bimp.tempSelectBitmap.clear();
				Photo_Bimp.albumSelectBitmap=beans;
				urls=new int[Photo_Bimp.albumSelectBitmap.size()];
				url_str=new ArrayList<String>();
				for(int i=0;i<Photo_Bimp.albumSelectBitmap.size();i++){
					String name=LoginUsers.otherUserId +"/"+System.currentTimeMillis();
					uploadPhotosHttp(name,Photo_Bimp.albumSelectBitmap.get(i).getBitmap());
				}
				Log.e("Album_ID",Album_ID+"");
				Photo_Bimp.Album_open=0;*/
                break;
        }
    }


}
