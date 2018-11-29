package com.wanquan.mlmmx.mlmm.utils;

import android.content.Context;

/**
 * 像素转换类
 * @author fanx
 *
 */
public class DensityUtil {
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
