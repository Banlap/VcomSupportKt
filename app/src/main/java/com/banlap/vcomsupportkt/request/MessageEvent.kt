package com.banlap.vcomsupportkt.request

/**
 *@author Banlap on 2021/7/5
 */
class MessageEvent(var msgCode: Int) {

    companion object {
        const val MAC_READY: Int = 0x10E
        const val MAC_REFRESH: Int = 0x11E
    }
}