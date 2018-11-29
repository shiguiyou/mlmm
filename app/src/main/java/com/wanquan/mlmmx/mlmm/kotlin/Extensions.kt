package com.shiguiyou.remberpassword

import android.content.Context
import android.preference.PreferenceManager
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

/**
 * 扩展函数
 *
 * Single-Expression functions
 * https://kotlinlang.org/docs/reference/functions.html#single-expression-functions
 *
 * Created by shiguiyou on 2017/6/8.
 */
fun ViewGroup.inflate(layoutRes: Int): View = LayoutInflater.from(context).inflate(layoutRes, this, false)

fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).into(this)
}

fun Context.toast(msg: String, length: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, msg, length).show()

fun Context.toast(@StringRes msg: Int, length: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, msg, length).show()

fun Context.putPreString(key: String, value: String) {
    val sp = PreferenceManager.getDefaultSharedPreferences(this)
    sp.edit().putString(key, value).apply()
}

fun Context.getPreString(key: String): String {
    return PreferenceManager.getDefaultSharedPreferences(this).getString(key, "")
}

fun Context.putPreLong(key: String, value: Long) {
    val sp = PreferenceManager.getDefaultSharedPreferences(this)
    sp.edit().putLong(key, value).apply()
}

fun Context.getPreLong(key: String): Long {
    return PreferenceManager.getDefaultSharedPreferences(this).getLong(key, 0)
}

fun Context.putPreBoolean(key: String, value: Boolean) {
    val sp = PreferenceManager.getDefaultSharedPreferences(this)
    sp.edit().putBoolean(key, value).apply()
}

fun Context.getPreBoolean(key: String): Boolean {
    return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(key, false)
}