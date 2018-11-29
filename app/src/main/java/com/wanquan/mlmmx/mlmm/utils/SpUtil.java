package com.wanquan.mlmmx.mlmm.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.wanquan.mlmmx.mlmm.activity.BaseActivity;

/**
 * SharedPreferences存储的辅助类
 *
 * @author fanx
 */
public class SpUtil {
    //添加boolean值
    public static void setBooleanValue(Context context, String key, Boolean value) {
        context.getSharedPreferences(MyContant.FILENAME, context.MODE_PRIVATE).edit().putBoolean(key, value).commit();
    }

    //取boolean值
    public static boolean getBooleanValue(Context context, String key, boolean b) {
        return context.getSharedPreferences(MyContant.FILENAME, context.MODE_PRIVATE).getBoolean(key, false);
    }

    //添加String值
    public static void setStringValue(Context context, String key, String value) {
        context.getSharedPreferences(MyContant.FILENAME, context.MODE_PRIVATE).edit().putString(key, value).commit();
    }

    //取String值
    public static String getStringValue(Context context, String key) {
        return context.getSharedPreferences(MyContant.FILENAME, context.MODE_PRIVATE).getString(key, "");
    }
}
