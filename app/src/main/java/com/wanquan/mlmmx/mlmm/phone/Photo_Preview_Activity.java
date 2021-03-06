package com.wanquan.mlmmx.mlmm.phone;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils;

import java.util.ArrayList;

/**
 * 预览界面
 */
public class Photo_Preview_Activity extends AppCompatActivity {
    ArrayList<View> listViews = null;
    ViewPagerFixed pager;
    MyPageAdapter adapter;
    //当前的位置
    private int location = 0;
    //头部信息
    TextView mTitle;
    //上传按钮
    LinearLayout mUpload;
    //上传数量
    TextView mNumber;
    //返回
    ImageView mReturn;
    Intent intent;
    int type;//如果为1则表示是上传相册

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);

        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(Photo_Preview_Activity.this, R.color.black);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        //第三方开源库（解决 沉浸式与输入法遮挡问题）
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.black);//通知栏所需颜色

        mReturn = (ImageView) findViewById(R.id.home_localPreview_return);
        pager = (ViewPagerFixed) findViewById(R.id.home_localPreview_viewpage);
        mTitle = (TextView) findViewById(R.id.home_localPreview_albumsName);
        mUpload = (LinearLayout) findViewById(R.id.home_localPreview_upload);
        mNumber = (TextView) findViewById(R.id.home_localPreview_uploadNumber);
        initData();
        //返回按钮点击事件
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //上传事件监听
        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                // Photo_Bimp.uploadSelectBitmap = Photo_Bimp.tempSelectBitmap;
                Photo_Bimp.albumSelectBitmap = Photo_Bimp.tempSelectBitmap;
                if (type == 0) {
                    //查看所有图片的预览
                    if (Photo_Bimp.mPreview_activity != null) {
                        if (Photo_Bimp.mPreview_activity.size() != 0)
                            Photo_Bimp.mPreview_activity.get(0).finish();
                        //Photo_Bimp.mPreview_activity.clear();
                        Photo_Bimp.mPreview_activity = null;
                    }
                    //查看单个相册图片的预览
                    if (Photo_PublicWay.activityList != null) {
                        if (Photo_PublicWay.activityList.size() != 0) {
                            for (int i = 0; i < Photo_PublicWay.activityList.size(); i++) {
                                Photo_PublicWay.activityList.get(i).finish();
                            }
                            //Photo_Bimp.mList_activity.clear();
                            Photo_PublicWay.activityList = null;
                        }
                    }
                } else {
                    //查看所有图片的预览
                    if (Photo_Bimp.mPreview_activity != null) {
                        //Photo_Bimp.mPreview_activity.get(0).finish();
                        Photo_Bimp.mPreview_activity.clear();
                    }
                    //查看单个相册图片的预览
                    if (Photo_Bimp.mList_activity.size() != 0) {
                        //for (int i = 0; i < Photo_Bimp.mList_activity.size(); i++) {
                        Photo_Bimp.mList_activity.get(0).finish();
                        //}
                        Photo_Bimp.mList_activity.clear();
                    }
                }
                finish();
            }
        });

        pager.setOnPageChangeListener(pageChangeListener);
        for (int i = 0; i < Photo_Bimp.tempSelectBitmap.size(); i++) {
            initListViews(Photo_Bimp.tempSelectBitmap.get(i).getBitmap());
        }
        adapter = new MyPageAdapter(listViews);
        pager.setAdapter(adapter);
        int id = intent.getIntExtra("ID", 0);
        pager.setCurrentItem(id);
        //赋值数据
        mTitle.setText((location + 1) + "/" + Photo_Bimp.tempSelectBitmap.size());
        //更新数据
        mNumber.setText(Photo_Bimp.tempSelectBitmap.size() + "");
        adapter.notifyDataSetChanged();
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

    /**
     * 数据处理
     */
    private void initData() {
        //获取传过来的数据
        intent = getIntent();
        type = intent.getIntExtra("type", 0);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        public void onPageSelected(int arg0) {
            location = arg0;
            mTitle.setText((location + 1) + "/" + Photo_Bimp.tempSelectBitmap.size());
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        public void onPageScrollStateChanged(int arg0) {

        }
    };

    //显示图片
    private void initListViews(Bitmap bm) {
        if (listViews == null)
            listViews = new ArrayList<View>();
        PhotoView img = new PhotoView(this);
        img.setBackgroundColor(0xff000000);
        img.setImageBitmap(bm);
        img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listViews.add(img);
    }

    class MyPageAdapter extends PagerAdapter {

        private ArrayList<View> listViews;

        private int size;

        public MyPageAdapter(ArrayList<View> listViews) {
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public void setListViews(ArrayList<View> listViews) {
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public int getCount() {
            return size;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPagerFixed) arg0).removeView(listViews.get(arg1 % size));
        }

        public void finishUpdate(View arg0) {
        }

        public Object instantiateItem(View arg0, int arg1) {
            try {
                ((ViewPagerFixed) arg0).addView(listViews.get(arg1 % size), 0);

            } catch (Exception e) {
            }
            return listViews.get(arg1 % size);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按键返回
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (Photo_Bimp.mPreview_activity != null) {
                Photo_Bimp.mPreview_activity.clear();
            }
            finish();
        }
        return true;
    }

}
