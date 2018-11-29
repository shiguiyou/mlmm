package com.example.administrator.mlmmx.kotlin

/**
 * Created by shiguiyou on 2017/5/24.
 */
data class HttpResult<T>(
        val data: T,
        val dynamicKey: String,
        val msg: String,
        val result: String,
        val token: String,
        val resultCode: Int
)