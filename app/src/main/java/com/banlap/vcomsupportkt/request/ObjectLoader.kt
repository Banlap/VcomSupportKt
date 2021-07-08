package com.banlap.vcomsupportkt.request

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 *@author Banlap on 2021/7/5
 */
open class ObjectLoader {
    protected fun <T> setSubscribe(observable: Observable<T>, observer: Observer<T>) {
        //使用Schedule实现RxJava异步操作
        observable.subscribeOn(Schedulers.io())             //IO操作 读写文件、数据库、网络信息
            .subscribeOn(Schedulers.newThread())            //开启新线程
            .observeOn(AndroidSchedulers.mainThread())      //回调到主线程
            .subscribe(observer)
    }

}