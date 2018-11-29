package com.wanquan.mlmmx.mlmm.phone;

import android.Manifest;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.activity.AddressActivity;
import com.wanquan.mlmmx.mlmm.activity.AddressTestActivity;
import com.wanquan.mlmmx.mlmm.activity.BabyPhoneLookActivity;
import com.wanquan.mlmmx.mlmm.activity.BabyPhonesActivity;
import com.wanquan.mlmmx.mlmm.activity.InformationActivity;
import com.wanquan.mlmmx.mlmm.activity.SettingBabyMessageActivity;
import com.wanquan.mlmmx.mlmm.okgo.CustomCallBackNoLoading;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.MyDialog_Views;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述：照片记录
 * 作者：薛昌峰
 * 时间：2016.10/3
 */
public class Release_Work_Activity extends AppCompatActivity {
    private MyPicpupWindow mPopWindow;//选择照片方式弹出框
    private GridAdapter adapter;
    private Uri photoUri;
    //    private static final int TAKE_PICTURE = 0x000001;
    private static final int TAKE_PICTURE = 1;
    //    private static final int TAKE_PICTURE = 0x3;
//    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;

    private RelativeLayout mReleaseR2;
    private TextView mReleaseLook;
    private LinearLayout mReleaseWorkLayout;
    private TextView mReleaseWork_Save;
    private EditText mReleaseWorkContent;
    private RelativeLayout mReleaseRl;
    private TextView mReleaseAddress;
    private MyGridView mImageWorkGridview;
    private String path;
    private String string = null;
    private Boolean recharge_flag = true;
    String name;
    private String lng;
    private String lat;
    private String authority = "1";
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;


    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_activity);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(Release_Work_Activity.this, R.color.black);

        initViews();
        initData();
        initLister();
    }

    private void initViews() {
        mReleaseR2 = (RelativeLayout) findViewById(R.id.Release_R2);
        mReleaseLook = (TextView) findViewById(R.id.Release_Look);
        mReleaseWorkLayout = (LinearLayout) findViewById(R.id.release_work_Layout);
        mReleaseWork_Save = (TextView) findViewById(R.id.releaseWork_Save);
        mReleaseWorkContent = (EditText) findViewById(R.id.Release_workContent);
        mReleaseRl = (RelativeLayout) findViewById(R.id.Release_Rl);
        mReleaseAddress = (TextView) findViewById(R.id.Release_Address);
        mImageWorkGridview = (MyGridView) findViewById(R.id.imageWorkGridview);
    }

    private void initData() {
        mImageWorkGridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this, "no");
        adapter.update();
        mImageWorkGridview.setAdapter(adapter);
    }

    private void initLister() {
        mReleaseRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Release_Work_Activity.this, AddressActivity.class), 11);
//                startActivityForResult(new Intent(Release_Work_Activity.this, AddressTestActivity.class), 11);
            }
        });
        mReleaseR2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Release_Work_Activity.this, BabyPhoneLookActivity.class).putExtra("auto", authority), 12);
            }
        });
        mImageWorkGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                if (arg2 == Photo_Bimp.albumSelectBitmap.size()) {
                    //底部弹出框
                    //实例化MyPicPopupWindow
                    HideInputManager_Utils.hideInputManager(Release_Work_Activity.this);//隐藏 键盘
                    mPopWindow = new MyPicpupWindow(Release_Work_Activity.this, itemsOnClick, "拍照", "手机相册");
                    //设置弹出动画效果
                    // mPopWindow.setAnimationStyle(R.style.PopupAnimation);
                    //显示窗口  //设置layout在PopupWindow中显示的位置
                    mPopWindow.showAtLocation(findViewById(R.id.release_work_Layout),
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                } else {
                    if (Photo_Bimp.albumSelectBitmap.get(arg2).isVideo()) {
                        VideoFragment bigPic = VideoFragment.newInstance(Photo_Bimp.albumSelectBitmap.get(arg2).getImagePath());
                        android.app.FragmentManager mFragmentManager = getFragmentManager();
                        FragmentTransaction transaction = mFragmentManager.beginTransaction();
                        transaction.replace(R.id.release_work_Layout, bigPic);
                        transaction.commit();
                    }
                }
            }
        });

        mReleaseWork_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!mReleaseWorkContent.getText().toString().trim().equals("")) {
                if (Photo_Bimp.albumSelectBitmap.size() != 0) {
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Log.e("ddddddddd", "到了");
                    mReleaseWork_Save.setClickable(false);
                    NewPhone();//上传图片
//                        Intent intent = new Intent(Release_Work_Activity.this, UpLoadingImageViewActivity.class);
//                        intent.putExtra("text", mReleaseWorkContent.getText().toString().trim());
//                        startActivity(intent);
//                            }
//                        }).start();
                } else {
                    Toast.makeText(Release_Work_Activity.this, "至少选择一张照片", Toast.LENGTH_SHORT).show();
                }
//                } else {
//                    Toast toast = Toast.makeText(Release_Work_Activity.this, "请输入成长记录", Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                }
            }
        });
    }

    private void NewPhone() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("itfaceId", "036");
        hashMap.put("token", SPUtils.get(Release_Work_Activity.this, "token", ""));
        hashMap.put("title", mReleaseWorkContent.getText().toString().trim());
        hashMap.put("place", name);
        hashMap.put("longitude", lng);
        hashMap.put("latitude", lat);
        hashMap.put("babyId", SPUtils.get(Release_Work_Activity.this, "babyId", ""));
        hashMap.put("authority", authority);

        JSONObject jsonObject = new JSONObject(hashMap);

        OkGo.post(UrlContent.URL).tag(this)
                .upJson(jsonObject.toString())
                .connTimeOut(10_000)
                .execute(new CustomCallBackNoLoading<ReleaseWorkNewBeans>(Release_Work_Activity.this) {
                    @Override
                    public void onSuccess(ReleaseWorkNewBeans mReleaseWorkNewBeans, Call call, Response response) {
                        if (mReleaseWorkNewBeans.getResultCode() == 1) {
//                            Toast.makeText(Release_Work_Activity.this, "创建相册成功", Toast.LENGTH_SHORT).show();
                            int id = mReleaseWorkNewBeans.getData();
                            UpLoadImage(id);
                        } else {
                            App.ErrorToken(mReleaseWorkNewBeans.getResultCode(), mReleaseWorkNewBeans.getMsg());
                        }
                    }
                });
    }

    private void UpLoadImage(int id) {
        for (int i = 0; i < Photo_Bimp.albumSelectBitmap.size(); i++) {
            bitmaptoString(Photo_Bimp.albumSelectBitmap.get(i).getBitmap(), 90);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("itfaceId", "037");
            hashMap.put("albumId", id);
            hashMap.put("headIco", string);
            hashMap.put("token", SPUtils.get(Release_Work_Activity.this, "token", ""));
            JSONObject jsonObject = new JSONObject(hashMap);

            OkGo.post(UrlContent.URL).tag(this)
                    .upJson(jsonObject.toString())
                    .connTimeOut(10_000)
                    .execute(new CustomCallBackNoLoading<ReleaseWorkBeans>(Release_Work_Activity.this) {
                        @Override
                        public void onSuccess(ReleaseWorkBeans mReleaseWorkBeans, Call call, Response response) {
                            if (mReleaseWorkBeans.getResultCode() == 1) {
                                final Dialog dialog = new MyDialog_Views(Release_Work_Activity.this, R.style.MyDialog);
                                dialog.setCancelable(false);
                                dialog.show();
                                MyDialog_Views myDialog_views = new MyDialog_Views(Release_Work_Activity.this, "稍等片刻就能永久记录您和宝宝的靓照啦~", "");
                                //模拟网络交互时间为5秒
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //访问服务器时间
                                        if (recharge_flag) {
                                            MyDialog_Views myDialog_views = new MyDialog_Views(Release_Work_Activity.this, "上传成功", "success");
                                        } else {
                                            MyDialog_Views myDialog_views = new MyDialog_Views(Release_Work_Activity.this, "上传失败", "fail");
                                        }
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
//                                                dialog.dismiss();
//                                                Toast.makeText(Release_Work_Activity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                                finish();
                                                mReleaseWork_Save.setClickable(true);
                                                Photo_Bimp.albumSelectBitmap.clear();
                                            }
                                        }, 2000);
                                    }
                                }, 1000);
                            } else {
                                App.ErrorToken(mReleaseWorkBeans.getResultCode(), mReleaseWorkBeans.getMsg());
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

    //为弹出窗口实现监听类
    public View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.chioce_camera:
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (ContextCompat.checkSelfPermission(Release_Work_Activity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                                || ContextCompat.checkSelfPermission(Release_Work_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(Release_Work_Activity.this, Manifest.permission.CAMERA)) {
                            }
                            ActivityCompat.requestPermissions(Release_Work_Activity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
                        } else {
                            if (Photo_Bimp.albumSelectBitmap.size() != 0) {
                                if (Photo_Bimp.albumSelectBitmap.get(0).isVideo()) {
                                } else photo();
                            } else
                                photo();
                        }
                    }
                    break;
                case R.id.chioce_photo:
                    if (ContextCompat.checkSelfPermission(Release_Work_Activity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(Release_Work_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(Release_Work_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Release_Work_Activity.this,
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                    } else {
                        if (Photo_Bimp.albumSelectBitmap.size() != 0) {
                            if (Photo_Bimp.albumSelectBitmap.get(0).isVideo()) {
                            } else {
                                Intent intent = new Intent(Release_Work_Activity.this, Photo_Album_Activity.class);
                                intent.putExtra("userId", Login_Static.myUserID);
                                startActivity(intent);
                            }
                        } else {
                            Intent intent = new Intent(Release_Work_Activity.this, Photo_Album_Activity.class);
                            intent.putExtra("userId", Login_Static.myUserID);
                            startActivity(intent);
                        }
                    }
                    break;
                default:
                    break;
            }
            mPopWindow.dismiss();
        }
    };

    public void photo() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String filename = timeStampFormat.format(new Date());
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, filename);

        photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    /**
     * 返回的数据处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Photo_Bimp.albumSelectBitmap.size() < 20 && resultCode == RESULT_OK) {
                    //这里开始的第二部分，获取图片的存储路径：
                    String[] proj = {MediaStore.Images.Media.DATA};
                    //好像是android多媒体数据库的封装接口，具体的看Android文档
                    Cursor cursor = managedQuery(photoUri, proj, null, null, null);
                    //按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径
                    path = cursor.getString(column_index);
                    Photo_ImageItem_Beans takePhoto = new Photo_ImageItem_Beans();
                    takePhoto.setImagePath(path);
                    Photo_Bimp.tempSelectBitmap.add(takePhoto);
                    Photo_Bimp.albumSelectBitmap = Photo_Bimp.tempSelectBitmap;
                }
                break;
        }
        if (requestCode == 11) {
            if (data != null) {
                name = data.getStringExtra("name");
                lat = data.getStringExtra("lat");
                lng = data.getStringExtra("lng");
                mReleaseAddress.setText(name);
            }
        }
        if (requestCode == 12 && resultCode == 2) {
            Log.e("ggggggggg", data.getStringExtra("authority") + "xcf2");
            if (data.getStringExtra("authority").equals("1")) {
                mReleaseLook.setText("公开");
                authority = "1";
            } else if (data.getStringExtra("authority").equals("0")) {
                mReleaseLook.setText("私密");
                authority = "0";
            }
        }
    }

    public void Release_Bank(View view) {
        finish();
    }

    //选择图片显示gridview显示图片
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;
        String deleteList;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context, String sDelete) {
            inflater = LayoutInflater.from(context);
            deleteList = sDelete;
        }

        public void update() {
            loading();
        }

        public int getCount() {
            if (Photo_Bimp.albumSelectBitmap.size() == 20) {
                return 20;
            }
            return (Photo_Bimp.albumSelectBitmap.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.adapter_releasegridview, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
                holder.mDelete = (ImageView) convertView.findViewById(R.id.item_grida_image_delete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position == Photo_Bimp.albumSelectBitmap.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.upimg_logo));
                holder.mDelete.setVisibility(View.INVISIBLE);
                if (position == 20) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Photo_Bimp.albumSelectBitmap.get(position).getBitmap());
//                Log.e("ttttttt---111", String.valueOf(Photo_Bimp.albumSelectBitmap.get(position).getImagePath()));
//                Log.e("ttttttt---222", String.valueOf(Photo_Bimp.albumSelectBitmap.get(position).getThumbnailPath()));
                holder.mDelete.setVisibility(View.VISIBLE);
            }
            //点击删除事件
            holder.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Photo_Bimp.albumSelectBitmap.remove(position);
                    v.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
            public ImageView mDelete;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Photo_Bimp.max == Photo_Bimp.albumSelectBitmap.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            Photo_Bimp.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }
            }).start();
        }
    }

}
