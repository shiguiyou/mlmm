package com.example.administrator.mlmmx.kotlin

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 网络请求 单例模式
 * Created by shiguiyou on 2017/5/27.
 */
object HttpHelper {

    val apiService: DataApi?=null
    val builder=OkHttpClient.Builder()

    fun getApi():DataApi{
        builder.connectTimeout(AppConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)

        if (apiService==null){
            val retrofit= retrofit2.Retrofit.Builder()
                    .client(builder.build())
                    .baseUrl(AppConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
            return retrofit.create(DataApi::class.java)
        }
        Log.i("square",AppConfig.BASE_URL)
        return apiService!!
    }
}