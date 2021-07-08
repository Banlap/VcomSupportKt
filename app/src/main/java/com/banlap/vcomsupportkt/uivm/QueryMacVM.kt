package com.banlap.vcomsupportkt.uivm

import android.app.Application
import android.app.Presentation
import androidx.lifecycle.AndroidViewModel
import com.banlap.vcomsupportkt.VcomData
import com.banlap.vcomsupportkt.model.Presupposition
import com.banlap.vcomsupportkt.request.ApiLoader
import com.banlap.vcomsupportkt.request.ApiObserver
import com.banlap.vcomsupportkt.request.MessageEvent
import com.banlap.vcomsupportkt.utils.GsonUtil
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import org.greenrobot.eventbus.EventBus

/**
 *@author Banlap on 2021/6/28
 */
class QueryMacVM(application: Application) : AndroidViewModel(application) {

    private var callBack : QueryMacCallBack? = null

    fun setCallBack(callBack: QueryMacCallBack) { this.callBack = callBack }
    fun viewBack() { callBack?.viewBack() }
    fun queryMac() {
        ApiLoader.getAPi().queryMac("1", "9999", object: ApiObserver<ResponseBody>(){
            override fun onSuccess(data: String, rows: Int) {
                VcomData.instance.presuppositionList = GsonUtil.jsonToList(data, object: TypeToken<MutableList<Presupposition?>?>(){}.type)
                VcomData.instance.row = rows
                EventBus.getDefault().post(MessageEvent(MessageEvent.MAC_READY))
                callBack?.queryMacSuccess()
            }
            override fun onFailure() { callBack?.queryMacFailure() }
            override fun onComplete() {}
        })

    }
    fun deleteMac(positionId: String) {
        ApiLoader.getAPi().deleteMac(positionId, object: ApiObserver<ResponseBody>() {
            override fun onSuccess(data: String, rows: Int) { callBack?.deleteMacSuccess() }
            override fun onFailure() { callBack?.deleteMacFailure() }
            override fun onComplete() {}
        })
    }

    interface QueryMacCallBack {
        fun viewBack()
        fun queryMacSuccess()
        fun queryMacFailure()
        fun deleteMacSuccess()
        fun deleteMacFailure()
    }
}