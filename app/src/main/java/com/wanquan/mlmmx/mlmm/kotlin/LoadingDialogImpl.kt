package com.example.administrator.mlmmx.kotlin


/**
 * Created by shiguiyou on 2017/7/3.
 */
interface LoadingDialogImpl {
    val loadingDialog: LoadingDialog

    fun showLoadingDialog() {
        if (!loadingDialog.isShowing) {
            loadingDialog.show()
        }
    }

    fun hideLoadingDialog() {
        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }
}