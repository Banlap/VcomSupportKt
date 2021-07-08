package com.banlap.vcomsupportkt.uivm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.banlap.vcomsupportkt.request.ApiLoader
import com.banlap.vcomsupportkt.request.ApiObserver
import okhttp3.ResponseBody
import org.json.JSONObject

/**
 *@author Banlap on 2021/6/28
 */
class UpdateMacVM(application: Application): AndroidViewModel(application) {

    private var callBack: UpdateMacCallBack?=null

    fun setCallBack(callBack: UpdateMacCallBack) { this.callBack = callBack }
    fun viewBack() { callBack?.viewBack() }
    fun viewUpdateMac() { callBack?.viewUpdateMac() }
    /**
     * 查询输入的账号是否存在
     * */
    fun selectUserId(userId: String) {
        ApiLoader.getAPi().selectUserId(userId, object: ApiObserver<ResponseBody>() {
            override fun onSuccess(data: String, rows: Int) { callBack?.selectUserIdSuccess() }
            override fun onFailure() { callBack?.selectUserIdFailure() }
            override fun onComplete() {}
        })
    }
    /**
     * 更新账号的搜索设备列表
     *  */
    fun updateMac(userId: String, mac: String) {
        var params: MutableMap<String, String> = HashMap()
        params["userId"] = userId
        params["productMac"] = mac

        ApiLoader.getAPi().addMac(params, object: ApiObserver<ResponseBody>() {
            override fun onSuccess(data: String, rows: Int) { callBack?.updateMacSuccess() }
            override fun onFailure() { callBack?.updateMacFailure() }
            override fun onComplete() {}
        })
    }

    interface UpdateMacCallBack {
        fun viewBack()
        fun viewUpdateMac()

        fun selectUserIdSuccess()
        fun selectUserIdFailure()

        fun updateMacSuccess()
        fun updateMacFailure()
    }
    
}