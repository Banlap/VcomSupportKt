package com.banlap.vcomsupportkt

import com.banlap.vcomsupportkt.model.Presupposition
import java.util.concurrent.CopyOnWriteArrayList

/**
 *@author Banlap on 2021/7/5
 */
class VcomData {
    companion object { val instance = VcomData() }
    var row: Int =0
    var presuppositionList: MutableList<Presupposition> = CopyOnWriteArrayList()
}
