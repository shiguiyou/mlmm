package com.example.administrator.mlmmx.kotlin

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import com.wanquan.mlmmx.mlmm.R

class LoadingDialog(context: Context, theme: Int, msg: String = "配置中") : Dialog(context, theme) {

    val showMsg = msg

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCancelable(false)
        setCanceledOnTouchOutside(false)

        setContentView(R.layout.view_progress_dialog)
        (findViewById<TextView>(R.id.tv_show_msg) as TextView).text = showMsg


        window.attributes.gravity = Gravity.CENTER
        val lp = window.attributes
        lp.dimAmount = 0.2f
        window.attributes = lp
    }

}