package com.example.administrator.mlmmx.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.example.administrator.mlmmx.fragment.Add1ConfigFragment
import com.tbruyelle.rxpermissions.RxPermissions
import com.wanquan.mlmmx.mlmm.R
//import com.wanquan.mlmmx.mlmm.activity.QRCodeViewActivity
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils

/**
 * 描述：添加设备页面
 * 作者：薛昌峰
 * 时间：2017/10/09
 */
class AddDeviceActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AddDeviceActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_framelayout)

        val manageUtils = MySystemBarTintManage_Utils()
        manageUtils.setSystemBarTintManage(this, R.color.black)

        supportFragmentManager.beginTransaction()
                .replace(R.id.container, Add1ConfigFragment(), "ScanFragment")
                .commit()

        requestPermission()
    }

    /*
    进入配置第二步，扫描设备
     */
    fun next(){
//        supportFragmentManager.beginTransaction()
//                .replace(R.id.container, Add2ScanFragment(), "ScanFragment")
//                .commit()
//        startActivity(Intent(this, QRCodeViewActivity::class.java))
    }

    fun reloadScanFragment() {
        var frg: Fragment? = null
        frg = supportFragmentManager.findFragmentByTag("ScanFragment")
        val ft = supportFragmentManager.beginTransaction()
        ft.detach(frg)
        ft.attach(frg)
        ft.commit()
    }

    private fun requestPermission() {
        val rxPermission = RxPermissions(this)
        rxPermission.request(Manifest.permission.CAMERA)
                .subscribe({ granted ->
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                    } else {
                        AlertDialog.Builder(this)
                                .setMessage("您拒绝了拍照权限，将无法启动摄像头扫描二维码，如需开启，请到设置中权限管理中打开")
                                .setPositiveButton("确定", null)
                                .show()
                    }
                })
    }

}