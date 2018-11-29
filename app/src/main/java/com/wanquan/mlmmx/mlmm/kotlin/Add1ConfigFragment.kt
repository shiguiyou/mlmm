package com.example.administrator.mlmmx.fragment

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.administrator.mlmmx.activity.AddDeviceActivity
import com.example.administrator.mlmmx.kotlin.LoadingDialog
import com.shiguiyou.remberpassword.inflate
import com.shiguiyou.remberpassword.toast
import com.wanquan.mlmmx.mlmm.R
import com.wanquan.mlmmx.mlmm.activity.IntegralStrategyActivity.flag
import com.wanquan.mlmmx.mlmm.activity.QRCodeActivity
import com.wanquan.mlmmx.mlmm.activity.SimpleCaptureActivity
import com.wanquan.mlmmx.mlmm.esptouch.EsptouchTask
import com.wanquan.mlmmx.mlmm.esptouch.IEsptouchResult
import com.wanquan.mlmmx.mlmm.esptouch.IEsptouchTask
import com.wanquan.mlmmx.mlmm.esptouch.demo_activity.EspWifiAdminSimple
import com.wanquan.mlmmx.mlmm.phone.Release_Work_Activity
import com.wanquan.mlmmx.mlmm.utils.SPUtils
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerService.getIntent
import kotlinx.android.synthetic.main.fragment_add1_config.*

/**
 * 添加设备-第一步-配置设备上网
 * Created by shiguiyou on 2017/7/6.
 */
class Add1ConfigFragment : Fragment() {

    private lateinit var mWifiAdmin: EspWifiAdminSimple
    private var mAct: AddDeviceActivity? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is AddDeviceActivity) {
            mAct = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_add1_config)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mWifiAdmin = EspWifiAdminSimple(context)
        tv_back.setOnClickListener { mAct?.finish() }
        tv_config.setOnClickListener { configDevice() }
        tv_wifi.setOnClickListener { wifi() }
    }

    private fun wifi() {
        context.startActivity(Intent(context, QRCodeActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        val wifiName = mWifiAdmin.wifiConnectedSsid
        if (wifiName != null) {
            tv_wifi_name.text = wifiName
        } else {
            tv_wifi_name.text = "请先连接到无线网"
        }


        var intent = activity.intent.extras
        var flag = intent.get("flag").toString()
        Log.e("sfsaf", flag);
        if ("1".equals(flag)) {
            imageView2.setBackground(resources.getDrawable(R.mipmap.linkdevicebg))
        } else if ("2".equals(flag)) {
//            imageView2.setBackground(resources.getDrawable(R.mipmap.chezai))
            imageView2.setImageDrawable(resources.getDrawable(R.mipmap.chezai))
        }


//        if (tv_wifi_name.text.toString().trim() == SPUtils.get(context, "wifiName", "")) {
//            et_password.text = SPUtils.get(context, "wifiPS", "") as Editable?
//        }
    }

    private fun configDevice() {
        if (tv_wifi_name.text == "请先连接到无线网") {
            context.toast("请先连接到无线网")
            return
        }

//        if (et_password.text.isEmpty()) {
//            context.toast("请输入wifi密码")
//            return
//        }

        val apSsid = mWifiAdmin.wifiConnectedSsid
        val apPassword = et_password.text.toString()
        SPUtils.put(context, "wifiName", tv_wifi_name.text.toString().trim())
        SPUtils.put(context, "wifiPS", et_password.text.toString().trim())
        val apBssid = mWifiAdmin.wifiConnectedBssid

        EsptouchAsyncTask3().execute(apSsid, apBssid, apPassword, "NO", "1")
    }

    private inner class EsptouchAsyncTask3 : AsyncTask<String, Void, List<IEsptouchResult>>() {

        private var mDialog: LoadingDialog? = null

        private var mEsptouchTask: IEsptouchTask? = null

        override fun onPreExecute() {
            mDialog = LoadingDialog(context, R.style.LoadingDialog, "连接中，路由器\n手机和设备尽量靠近")
            mDialog?.show()
        }

        override fun doInBackground(vararg params: String): List<IEsptouchResult> {
            var taskResultCount = -1
            synchronized(this@Add1ConfigFragment) {
                val apSsid = mWifiAdmin.getWifiConnectedSsidAscii(params[0])
                val apBssid = params[1]
                val apPassword = params[2]
                val isSsidHiddenStr = params[3]
                val taskResultCountStr = params[4]
                var isSsidHidden = false
                if (isSsidHiddenStr == "YES") {
                    isSsidHidden = true
                }
                taskResultCount = Integer.parseInt(taskResultCountStr)
                mEsptouchTask = EsptouchTask(apSsid, apBssid, apPassword, isSsidHidden, context)
            }
            val resultList = mEsptouchTask!!.executeForResults(taskResultCount)
            return resultList
        }

        override fun onPostExecute(result: List<IEsptouchResult>) {
            if (mDialog != null && mDialog!!.isShowing) {
                mDialog!!.dismiss()
            }

            val firstResult = result[0]
            if (!firstResult.isCancelled) {
                if (firstResult.isSuc) {
                    context.toast("设备已成功连接上网")
                    mAct?.next()
                    context.startActivity(Intent(context, QRCodeActivity::class.java))
                } else {
                    context.toast("连接失败，请重新设置连接")
                }
            }
        }
    }
}