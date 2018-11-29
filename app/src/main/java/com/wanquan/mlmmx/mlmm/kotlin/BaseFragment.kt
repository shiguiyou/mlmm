package com.example.administrator.mlmmx.fragment

import android.support.v4.app.Fragment
import com.example.administrator.mlmmx.kotlin.LoadingDialog
import com.example.administrator.mlmmx.kotlin.LoadingDialogImpl
import com.wanquan.mlmmx.mlmm.R
import rx.subscriptions.CompositeSubscription

open class BaseFragment : Fragment(), LoadingDialogImpl {

    override val loadingDialog by lazy { LoadingDialog(context, R.style.LoadingDialog) }

    protected var subscriptions = CompositeSubscription()

    override fun onDestroyView() {
        super.onDestroyView()
        subscriptions.clear()
    }
}