package com.example.administrator.mlmmx.kotlin

import com.google.gson.JsonElement
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import rx.Observable


interface DataApi {

    /*
    通用的请求，返回jsonElement
     */
    @FormUrlEncoded
    @POST("architecture-iot-business/itface/free/request.htm")
    fun requestServer(@Field("param") param: String): Observable<HttpResult<JsonElement>>
//
//    /*
//    通用的请求，返回string
//     */
//    @FormUrlEncoded
//    @POST("architecture-iot-business/itface/free/request.htm")
//    fun requestForStr(@Field("param") param: String): Observable<HttpResult<String>>
//
//    @FormUrlEncoded
//    @POST("architecture-iot-business/itface/free/request.htm")
//    fun login(@Field("param") param: String): Observable<HttpResult<UserBean>>
//
//    @FormUrlEncoded
//    @POST("architecture-iot-business/itface/free/request.htm")
//    fun getDevices(@Field("param") param: String): Observable<HttpResult<List<Device>>>
//
//    @FormUrlEncoded
//    @POST("architecture-iot-business/itface/free/request.htm")
//    fun getBanner(@Field("param") param: String): Observable<HttpResult<List<BannerResult>>>
//
//    @FormUrlEncoded
//    @POST("architecture-iot-business/itface/free/request.htm")
//    fun getDeviceStatus(@Field("param") param: String): Observable<HttpResult<List<DeviceStatus>>>
//
//    @FormUrlEncoded
//    @POST("architecture-iot-business/itface/free/request.htm")
//    fun getDeviceData(@Field("param") param: String): Observable<HttpResult<DeviceData>>
//
//    @FormUrlEncoded
//    @POST("architecture-iot-business/itface/free/request.htm")
//    fun controlDevice(@Field("param") param: String): Observable<HttpResult<JsonElement>>
//
//    @FormUrlEncoded
//    @POST("architecture-iot-business/itface/free/request.htm")
//    fun getMeData(@Field("param") param: String): Observable<HttpResult<MeBean>>
//
//    @FormUrlEncoded
//    @POST("architecture-iot-business/itface/free/request.htm")
//    fun getShareUsers(@Field("param") param: String): Observable<HttpResult<List<ShareUser>>>
//
//    @FormUrlEncoded
//    @POST("architecture-iot-business/itface/free/request.htm")
//    fun getFilterInfo(@Field("param") param: String): Observable<HttpResult<List<FilterBean>>>

}