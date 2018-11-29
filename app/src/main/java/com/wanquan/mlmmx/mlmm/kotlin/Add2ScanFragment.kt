package com.example.administrator.mlmmx.fragment

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.example.administrator.mlmmx.activity.AddDeviceActivity
import com.example.administrator.mlmmx.kotlin.AppConfig
import com.example.administrator.mlmmx.kotlin.HttpHelper
import com.example.administrator.mlmmx.kotlin.HttpResult
import com.google.gson.JsonElement
import com.shiguiyou.remberpassword.getPreLong
import com.shiguiyou.remberpassword.inflate
import com.shiguiyou.remberpassword.toast
import com.wanquan.mlmmx.mlmm.R
import com.wanquan.mlmmx.mlmm.kotlin.Utils
import com.wanquan.mlmmx.mlmm.utils.MySystemBarTintManage_Utils
import kotlinx.android.synthetic.main.fragment_add2_scan.*
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*


class Add2ScanFragment : BaseFragment(), QRCodeView.Delegate {

    private var mAct: AddDeviceActivity? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is AddDeviceActivity) {
            mAct = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_add2_scan)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        zbarview.setDelegate(this)

        val manageUtils = MySystemBarTintManage_Utils()
        manageUtils.setSystemBarTintManage(activity, R.color.black)

//        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(activity,
//                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 1)
//        } else {
            tv_back.setOnClickListener { mAct?.finish() }
            tv_config.setOnClickListener { bindDevice(et_device_code.text.toString()) }
            tv_re_scan.setOnClickListener {
                mAct?.reloadScanFragment()
//            }
        }
    }

    private fun bindDevice(deviceCode: String) {
        if (deviceCode.isEmpty()) {
            context.toast("请输入正确的设备编号")
            return
        }

        val paramMap = HashMap<String, String>()
        paramMap.put("itfaceId", AppConfig.API_INTERFACE_ADD_DEVICE)
        paramMap.put("ownerUserId", context.getPreLong(AppConfig.PREFS_KEY_USER_ID).toString())
        paramMap.put("deviceCode", deviceCode)

        showLoadingDialog()
        val sub = HttpHelper.getApi()
                .requestServer(Utils.encryptParam(paramMap))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HttpResult<JsonElement>> {

                    override fun onError(e: Throwable) {
                        hideLoadingDialog()
                        context.toast("绑定失败:${e.message}")
                    }

                    override fun onNext(t: HttpResult<JsonElement>) {
                        if (t.resultCode == 1) {
                            context.toast("绑定成功")
                            mAct?.finish()
                        } else {
                            context.toast("绑定失败:${t.msg}")
                        }
                    }

                    override fun onCompleted() {
                        hideLoadingDialog()
                    }
                })
        subscriptions.add(sub)

    }

    override fun onStart() {
        super.onStart()
        zbarview.startCamera()
        zbarview.startSpot()
    }

    override fun onStop() {
        zbarview.stopCamera()
        super.onStop()
    }

    override fun onDestroyView() {
        zbarview.onDestroy()
        super.onDestroyView()
    }

    private fun vibrate() {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(200)
    }

    override fun onScanQRCodeSuccess(result: String?) {
        vibrate()
        zbarview.stopCamera()
        if (!result!!.contains("deviceCode=")) {
            context.toast("无效的设备二维码", Toast.LENGTH_LONG)
            tv_re_scan.visibility = View.VISIBLE
            return
        }
        val deviceCode = result!!.split("deviceCode=")[1]
        bindDevice(deviceCode)
    }

    override fun onScanQRCodeOpenCameraError() {
        context.toast("扫描出错")
    }

}