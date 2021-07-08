package com.banlap.vcomsupportkt.request

import android.annotation.SuppressLint
import com.banlap.vcomsupportkt.VcomApp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

/**
 *@author Banlap on 2021/7/5
 */
class RetrofitServiceManager {
    private var retrofit: Retrofit?=null
    /** 伴生对象 静态方法*/
    companion object {
        private const val DEFAULT_TIME_OUT: Long =5
        private const val DEFAULT_READ_TIME_OUT: Long =10

        @SuppressLint("TrulyRandom")
        fun createSSLSocketFactory(): SSLSocketFactory {
            var sslSocketFactory: SSLSocketFactory? = null
            try {
                var sslContext: SSLContext = SSLContext.getInstance("TLS")
                sslContext.init(null, arrayOf<TrustManager>(TrustAllManager), SecureRandom())
                sslSocketFactory = sslContext.socketFactory
            } catch (e: Exception) {
            }
            return sslSocketFactory!!
        }

        /** 实例化RetrofitServiceManager */
        fun getInstance(): RetrofitServiceManager { return RetrofitServiceManagerHolder.retrofitServiceManager }

    }

    private object RetrofitServiceManagerHolder {
        val retrofitServiceManager: RetrofitServiceManager = RetrofitServiceManager()
    }

    fun <T> create(service: Class<T>): T { return retrofit!!.create(service) }

    init {
        var builder: OkHttpClient.Builder = OkHttpClient.Builder()    //创建OkHttpClient,设置参数
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)    //连接超时时间, 默认10s
        builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS) //写入超时时间, 默认10s
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)  //读取超时时间, 默认10s
        //自定义拦截器 添加公共参数
        var httpCommonInterceptor: HttpCommonInterceptor = HttpCommonInterceptor.Builder
            .addHeaderParams("Content-Type", "application/json")
            .build()
        builder.addInterceptor(httpCommonInterceptor)   //添加自定义的拦截器
        builder.sslSocketFactory(createSSLSocketFactory(), TrustAllManager) //设置安全HTTPS连接的套接字工厂
        builder.hostnameVerifier(HostnameVerifier { _: String?, _: SSLSession? -> true })

        retrofit = Retrofit.Builder()
            .client(builder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(VcomApp.BASE_URL)
            .build()
    }

    object TrustAllManager: X509TrustManager {
        @SuppressLint("TrustAllX509TrustManager")
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        @SuppressLint("TrustAllX509TrustManager")
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun getAcceptedIssuers(): Array<X509Certificate?> { return arrayOfNulls(0) }
    }
    object TrustAllHostnameVerifier: HostnameVerifier {
        override fun verify(hostname: String?, session: SSLSession?): Boolean { return true }
    }

}