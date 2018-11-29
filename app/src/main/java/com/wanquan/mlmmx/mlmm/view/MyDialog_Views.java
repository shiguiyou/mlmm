package com.wanquan.mlmmx.mlmm.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanquan.mlmmx.mlmm.App;
import com.wanquan.mlmmx.mlmm.R;

public class MyDialog_Views extends Dialog {
    //上下文
    Context mContext;
    //控件
    ImageView mImageView;//加载图
    TextView mTextView;//加载提示文字
    //回传的值
    String texts = ""; //提示文字
    String state = "";//状态
    Animation animation;//补间动画

    public MyDialog_Views(Context context) {
        super(context);
        mContext = context;
    }

    /**
     * 构造方法
     *
     * @param context 上下文
     * @param texts   传过来的提示文字
     * @param state   传过来的返回状态
     */
    public MyDialog_Views(Context context, String texts, String state) {
        super(context);
        mContext = context;
        this.texts = texts;
        this.state = state;
        initListen();
    }

    public MyDialog_Views(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.my_dialog);
        //去黑角
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        mImageView = (ImageView) findViewById(R.id.my_dialog_image);
        mTextView = (TextView) findViewById(R.id.my_dialog_text);
        App.dialog_textview = mTextView;
        App.dialog_imageview = mImageView;
        //补间动画
        animation = AnimationUtils.loadAnimation(mContext, R.anim.rotate_upload);
        App.dialog_animation = animation;
        animation.start();
        mImageView.startAnimation(animation);
        initListen();
    }

    /**
     * 功能：接受传的值改变textview和imageview的值
     * 事件监听
     */
    private void initListen() {
        mTextView = (TextView) App.dialog_textview;
        mTextView.setText(texts + "");
        mTextView.setVisibility(View.VISIBLE);
        if (state.equals("success")) {
            //访问成功
            mImageView = (ImageView) App.dialog_imageview;
            mImageView.setImageResource(R.mipmap.classification_list_copy_success);
            animation = App.dialog_animation;
            animation.cancel();
            mImageView.clearAnimation();
        } else if (state.equals("fail")) {
            //访问失败
            mImageView = (ImageView) App.dialog_imageview;
            mImageView.setImageResource(R.mipmap.item_login_wrong);
            animation = App.dialog_animation;
            animation.cancel();
            mImageView.clearAnimation();
        } else if (state.equals("") && texts.equals("")) {
            mTextView.setVisibility(View.GONE);
        }
    }
}