package com.wanquan.mlmmx.mlmm.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;
import com.wanquan.mlmmx.mlmm.adapter.MyAdapter;
import com.wanquan.mlmmx.mlmm.fragment.MyWeekFragment;
import com.wanquan.mlmmx.mlmm.fragment.TwentyFourFragment;
import com.wanquan.mlmmx.mlmm.utils.MyContant;
import com.wanquan.mlmmx.mlmm.utils.SPUtils;
import com.wanquan.mlmmx.mlmm.utils.SpUtil;
import com.wanquan.mlmmx.mlmm.utils.UrlContent;
import com.wanquan.mlmmx.mlmm.view.SelectPicPopupShare;
import com.wanquan.mlmmx.mlmm.view.SelectPicPopupShare2;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneSession;
import static com.wanquan.mlmmx.mlmm.App.api;

/**
 * 作者：薛昌峰
 * 描述：宝宝呼吸质量
 * 时间：2018.11.05
 */
public class BabyQualityRespirationActivity extends BaseActivity {
    private LinearLayout mBabyRespiratoryBank;
    private TextView mBabyRespiratoryClose;
    private ImageView mBabyRespiratoryShare;
    private RadioGroup mBabyRespiratoryRadioGroup;
    private RadioButton mBabyRespiratoryRadioButton1;
    private RadioButton mBabyRespiratoryRadioButton2;
    private ViewPager mBabyRespiratoryViewPager;

    private MyWeekFragment mMyWeekFragment;
    private TwentyFourFragment mTwentyFourFragment;
    private List<Fragment> mList;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private MyAdapter mAdapter;
    private String id;
    private SelectPicPopupShare2 menuWindow; // 自定义的头像编辑弹出框
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String url;
    private String title;
    private String content;
    private String ownerUserId;
    private int babyStatus;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getIntent().getStringExtra("id");
        content = getIntent().getStringExtra("content");
        ownerUserId = getIntent().getStringExtra("ownerUserId");
        name = getIntent().getStringExtra("name");
        babyStatus = getIntent().getIntExtra("babyStatus", -1);

        url = UrlContent.hx1 + id + "&come=mlmm";

        verifyStoragePermissions(this);

        initData();
        initListeners();
        if (ownerUserId == null) {
            if (SPUtils.get(BabyQualityRespirationActivity.this, "BabyState", "").equals("2")) {
                String name = String.valueOf(SPUtils.get(BabyQualityRespirationActivity.this, "babyNickname", ""));//宝宝名称
                title = name + "的呼吸成绩单";
            } else if (SPUtils.get(BabyQualityRespirationActivity.this, "BabyState", "").equals("1")) {
                title = "宝妈的呼吸成绩单";
            } else {
                title = "呼吸成绩单";
            }
        } else {
            if (babyStatus == 2) {
                title = name + "的呼吸成绩单";
            } else if (babyStatus == 1) {
                title = "宝妈的呼吸成绩单";
            } else {
                title = "呼吸成绩单";
            }
        }
    }


    @Override
    protected int setLayoutID() {
        return R.layout.activity_baby_quality_respiration;
    }

    @Override
    public void initView() throws Exception {
        mBabyRespiratoryBank = (LinearLayout) findViewById(R.id.BabyRespiratory_Bank);
        mBabyRespiratoryClose = (TextView) findViewById(R.id.BabyRespiratory_Close);
        mBabyRespiratoryShare = (ImageView) findViewById(R.id.BabyRespiratory_Share);
        mBabyRespiratoryRadioGroup = (RadioGroup) findViewById(R.id.BabyRespiratory_RadioGroup);
        mBabyRespiratoryRadioButton1 = (RadioButton) findViewById(R.id.BabyRespiratory_RadioButton1);
        mBabyRespiratoryRadioButton2 = (RadioButton) findViewById(R.id.BabyRespiratory_RadioButton2);
        mBabyRespiratoryViewPager = (ViewPager) findViewById(R.id.BabyRespiratory_ViewPager);
    }


    private void initData() {
        mList = new ArrayList<>();
        mMyWeekFragment = new MyWeekFragment();
        mTwentyFourFragment = new TwentyFourFragment();

        Bundle bundle = new Bundle();
        bundle.putString("code", id);
        mMyWeekFragment.setArguments(bundle);
        mTwentyFourFragment.setArguments(bundle);


        mList.add(mMyWeekFragment);
        mList.add(mTwentyFourFragment);

        mFragmentManager = getSupportFragmentManager();
        mAdapter = new MyAdapter(mFragmentManager, mList);
        mBabyRespiratoryViewPager.setAdapter(mAdapter);

    }

    private void initListeners() {
        mBabyRespiratoryShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuWindow = new SelectPicPopupShare2(BabyQualityRespirationActivity.this, itemsOnClick);
                View parent1 = LayoutInflater.from(BabyQualityRespirationActivity.this).inflate(R.layout.activity_baby_quality_respiration, null);
                menuWindow.showAtLocation(parent1, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        mBabyRespiratoryRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                resetViewPager(checkedId);
            }
        });
        //滑动ViewPage的时候及时修改底部导航栏对应的图标
        mBabyRespiratoryViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //根据当前位置设置默认选中单选按钮
                resetRadioButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.Message_Popup_One2:
                    // 微信注册初始化
                    api = WXAPIFactory.createWXAPI(BabyQualityRespirationActivity.this, "wxeca84b2f356693a2", true);
                    api.registerApp("wxeca84b2f356693a2");
                    share2weixin(0);
                    break;
                case R.id.Message_Popup_Two2:
                    // 微信注册初始化
                    api = WXAPIFactory.createWXAPI(BabyQualityRespirationActivity.this, "wxeca84b2f356693a2", true);
                    api.registerApp("wxeca84b2f356693a2");
                    share2weixin(1);
                    break;
                default:
                    break;
            }
        }
    };


    private void share2weixin(int flag) {
        mBabyRespiratoryShare.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(mBabyRespiratoryShare.getDrawingCache());
        mBabyRespiratoryShare.setDrawingCacheEnabled(false);
        byte[] bytes = bitmap2Bytes(bitmap, 24);

        if (!api.isWXAppInstalled()) {
            Toast.makeText(BabyQualityRespirationActivity.this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);


        msg.title = title;
        msg.description = content;//内容
        msg.thumbData = bytes;
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);//图片-
        msg.setThumbImage(thumb);
        //图片加载是使用的ImageLoader.loadImageSync() 同步方法
        // 并且还要创建图片的缩略图，因为微信限制了图片的大小
//        Bitmap thumbBmp = Bitmap.createScaledBitmap(ImageLoaderUtil.getBitmap(image), 200, 200, true);
//        msg.setThumbImage(thumbBmp);
//        thumbBmp.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag;
        api.sendReq(req);
        req.scene = WXSceneSession;
//        App.integral("sharedownload", "", "分享成功积分");
    }

    private void resetViewPager(int checkedId) {
        switch (checkedId) {
            case R.id.BabyRespiratory_RadioButton1:
                mBabyRespiratoryViewPager.setCurrentItem(0);
                mBabyRespiratoryRadioButton1.setTextColor(getResources().getColor(R.color.black));
                mBabyRespiratoryRadioButton2.setTextColor(getResources().getColor(R.color.text_hui));
                url = UrlContent.hx1 + id + "&come=mlmm";

                break;
            case R.id.BabyRespiratory_RadioButton2:
                mBabyRespiratoryViewPager.setCurrentItem(1);
                mBabyRespiratoryRadioButton1.setTextColor(getResources().getColor(R.color.text_hui));
                mBabyRespiratoryRadioButton2.setTextColor(getResources().getColor(R.color.black));
                url = UrlContent.hx2 + id + "&come=mlmm";

                break;
        }
    }

    private void resetRadioButton(int position) {
        //获取position位置处对于的单选按钮
        RadioButton radioButton = (RadioButton) mBabyRespiratoryRadioGroup.getChildAt(position);
        //设置当前单选按钮默认选中
        radioButton.setChecked(true);
    }

    /**
     * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
     *
     * @param bitmap //     * @param IMAGE_SIZE
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb && options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        return output.toByteArray();
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

    public void BabyRespiratory_Bank(View view) {
        finish();
    }

}
