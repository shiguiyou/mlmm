package com.wanquan.mlmmx.mlmm.phone;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

/**
 * 描述：隐藏软键盘
 * 作者：xcf
 * 时间：2016.8.3
 */
public class HideInputManager_Utils {
    // 隐藏软键盘
    public static void hideInputManager(Context ct) {
        try {
            ((InputMethodManager) ct.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) ct)
                    .getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            Log.e("error","异常");
        }
    }
}
