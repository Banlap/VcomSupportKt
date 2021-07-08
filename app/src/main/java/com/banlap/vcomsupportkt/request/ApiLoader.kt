package com.banlap.vcomsupportkt.request

import android.util.Log
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Observer
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *@author Banlap on 2021/7/5
 */
class ApiLoader: ObjectLoader() {
    private var apiService: ApiService?=null
    companion object {
        private val api: ApiLoader = ApiLoader()
        fun getAPi(): ApiLoader {
            return api
        }
    }

    init{ apiService = RetrofitServiceManager.getInstance().create(ApiService::class.java) }

    fun userLogin(params: JSONObject, observer: Observer<ResponseBody>) {
        val type = "application/json".toMediaTypeOrNull()
        val body = params.toString().toRequestBody(type)
        //Log.e("body:", "" + body + " -- "+ type?.subtype)
        setSubscribe(apiService!!.userLogin(body), observer)
    }
    fun queryMac(page: String, rows: String, observer: Observer<ResponseBody>) {
        setSubscribe(apiService!!.queryMac(page, rows), observer)
    }
    fun selectUserId(userId: String, observer: Observer<ResponseBody>) {
        setSubscribe(apiService!!.selectUserId(userId), observer)
    }
    fun addMac(params: Map<String, String>, observer: Observer<ResponseBody>) {
        val type: MediaType = "application/json".toMediaTypeOrNull()!!
        val body: RequestBody = Gson().toJson(params).toRequestBody(type)
        setSubscribe(apiService!!.addMac(body), observer)
    }
    fun deleteMac(positionId: String, observer: Observer<ResponseBody>) {
        setSubscribe(apiService!!.deleteMac(positionId), observer)
    }

    /**
     * 后台API接口
     * */
    private interface ApiService {
        @POST("user/userLogin")   /** 用户登录 */
        fun userLogin(@Body body: RequestBody): Observable<ResponseBody>
        @POST("position/selectPosition")   /** 查询用户对应mac地址 */
        fun queryMac(@Query("page") page: String, @Query("rows") rows: String): Observable<ResponseBody>
        @GET("user/selectUser")            /** 查询用户id */
        fun selectUserId(@Query("userId") userId: String): Observable<ResponseBody>
        @POST("position/addPosition")     /** 添加预设mac */
        fun addMac(@Body body: RequestBody): Observable<ResponseBody>
        @GET("position/deletePosition")   /** 删除预设mac*/
        fun deleteMac(@Query("positionId") positionId: String): Observable<ResponseBody>
    }
}