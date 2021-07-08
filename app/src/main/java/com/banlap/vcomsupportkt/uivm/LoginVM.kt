package com.banlap.vcomsupportkt.uivm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.banlap.vcomsupportkt.request.ApiLoader
import com.banlap.vcomsupportkt.request.ApiObserver
import okhttp3.ResponseBody
import org.json.JSONObject

/**
 *@author Banlap on 2021/7/7
 */
class LoginVM(application: Application) : AndroidViewModel(application) {

    private var callBack: LoginCallBack ?=null

    fun setCallBack(callBack: LoginCallBack) { this.callBack = callBack }
    fun viewLogin() { callBack?.viewLogin() }
    fun login(userId: String, password: String) {
        val params = mapOf("userName" to userId, "userPassword" to password)
        val json = JSONObject()
        json.put("userName", userId)
        json.put("userPassword", password)
        ApiLoader.getAPi().userLogin(json, object: ApiObserver<ResponseBody>() {
            override fun onSuccess(data: String, rows: Int) { callBack?.loginSuccess() }
            override fun onFailure() { callBack?.loginFailure() }
            override fun onComplete() {}
        })
    }

    interface LoginCallBack {
        fun viewLogin()
        fun loginSuccess()
        fun loginFailure()
    }
}