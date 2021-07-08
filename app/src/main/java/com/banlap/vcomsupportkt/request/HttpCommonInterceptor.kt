package com.banlap.vcomsupportkt.request

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 *@author Banlap on 2021/7/5
 */
class HttpCommonInterceptor: Interceptor {

    private var mHeaderParamsMap: HashMap<String, String> = HashMap()

    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest: Request = chain.request()
        val requestBuilder: Request.Builder = oldRequest.newBuilder()
        requestBuilder.method(oldRequest.method, oldRequest.body)

        //添加公共参数, 添加到header中
        if(mHeaderParamsMap.isNotEmpty()) {
            for((k,v) in mHeaderParamsMap) {
                requestBuilder.header(k, v)
            }
        }

        val newRequest: Request = requestBuilder.build()
        return chain.proceed(newRequest)
    }

    object Builder {
        private var mHttpCommonInterceptor: HttpCommonInterceptor?=null
        init { mHttpCommonInterceptor = HttpCommonInterceptor() }
        fun build(): HttpCommonInterceptor { return mHttpCommonInterceptor!! }
        fun addHeaderParams(key: String, value: String): Builder {
            mHttpCommonInterceptor?.mHeaderParamsMap!![key] = value
            return this
        }
    }
}