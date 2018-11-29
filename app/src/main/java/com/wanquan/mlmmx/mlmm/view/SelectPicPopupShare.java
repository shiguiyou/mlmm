package com.wanquan.mlmmx.mlmm.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.wanquan.mlmmx.mlmm.R;


/**
 * 作用：选择图片弹出框
 * Author：薛昌峰  on 2017/03/11 09:40
 * Blog: bukevin@github.io
 */
public class SelectPicPopupShare extends PopupWindow {
    private LinearLayout mMessagePopupOne;
    private LinearLayout mMessagePopupTwo;
    private LinearLayout mMessagePopupThree;
    private View mMenuView;

    @SuppressLint("InflateParams")
    public SelectPicPopupShare(Context context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.set_popupwindow_share, null);

        mMessagePopupOne = (LinearLayout) mMenuView.findViewById(R.id.Message_Popup_One);
        mMessagePopupTwo = (LinearLayout) mMenuView.findViewById(R.id.Message_Popup_Two);
        mMessagePopupThree = (LinearLayout) mMenuView.findViewById(R.id.Message_Popup_Three);

        // 设置按钮监听
        mMessagePopupOne.setOnClickListener(itemsOnClick);
        mMessagePopupTwo.setOnClickListener(itemsOnClick);
        mMessagePopupThree.setOnClickListener(itemsOnClick);

        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.PopupAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.Message_Popup_Top).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }
}
