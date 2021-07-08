package com.banlap.vcomsupportkt.uivm

import android.app.Application
import androidx.lifecycle.AndroidViewModel

/**
 *@author Banlap on 2021/6/16
 */
class MainVM(application: Application) : AndroidViewModel(application) {

    private var callBack : MainCallBack? =null

    fun setCallBack(callBack: MainCallBack){
        this.callBack = callBack
    }

    fun goQueryMac() {
        callBack?.viewQueryMac()
    }

    fun goUpdateMac() {
        callBack?.viewUpdateMac()
    }

    interface MainCallBack {
        fun viewQueryMac()
        fun viewUpdateMac()
    }
}