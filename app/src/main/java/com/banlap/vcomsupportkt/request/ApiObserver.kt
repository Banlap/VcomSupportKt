package com.banlap.vcomsupportkt.request

import android.util.Log
import com.banlap.vcomsupportkt.VcomData
import com.banlap.vcomsupportkt.utils.GsonUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception

/**
 *@author Banlap on 2021/7/5
 */
abstract class ApiObserver<T: ResponseBody>: Observer<T> {

    protected abstract fun onSuccess(data: String, rows: Int)
    protected abstract fun onFailure()

    /**
     * RxJava
     * Observer（观察者）
     * onNext方法可以无限调用
     * onError 和 onComplete是互斥的，Observer（观察者）只能接收到一个
     * */
    override fun onSubscribe(b: Disposable) {}
    override fun onNext(responseBody: T) {
        try {
            var total =0
            val json = responseBody.string()
            val flag = GsonUtil.getBooleanValue(json, "flag")

            if(GsonUtil.getValue(json, "total")!=null) {
                total = Integer.parseInt(GsonUtil.getValue(json, "total")!!)
            }

            Log.e("json:", "$json flag:$flag")
            // api返回true 则请求成功
            if(flag) {
                GsonUtil.getValue(json, "data")?.let { onSuccess(it, 0) }
                return
            }

            // api返回多条数据
            if(total>0) {
                VcomData.instance.row = total
                //onSuccess(GsonUtil.getValue(json, "data"),total)
                GsonUtil.getValue(json, "rows")?.let { onSuccess(it, total)}
                return
            }
            onFailure()
        } catch (e: Exception) {
            Log.e("ApiException", e.message!!)
            onFailure()
        }
    }
    override fun onError(e: Throwable) {
        Log.e("ApiError", e.message!!)
        onFailure()
    }
}