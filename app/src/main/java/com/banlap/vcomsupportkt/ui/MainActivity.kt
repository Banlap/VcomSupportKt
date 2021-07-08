package com.banlap.vcomsupportkt.ui

import android.content.Intent
import android.widget.TextView
import com.banlap.vcomsupportkt.R
import com.banlap.vcomsupportkt.base.BaseActivity
import com.banlap.vcomsupportkt.databinding.ActivityMainBinding
import com.banlap.vcomsupportkt.uivm.MainVM

class MainActivity : BaseActivity<MainVM, ActivityMainBinding>(), MainVM.MainCallBack {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        getViewDataBinding()?.vm = getViewModel()
        getViewModel()?.setCallBack(this)

        getViewDataBinding()!!.tvAddMac.setTextStyle(20.toFloat(), R.color.red_f3)
    }

    /** 查询mac地址 */
    override fun viewQueryMac() {
        val intent = Intent(this, QueryMacActivity::class.java)
        startActivity(intent)
    }
    /** 添加mac地址*/
    override fun viewUpdateMac() {
        val intent = Intent(this, UpdateMacActivity::class.java)
        startActivity(intent)
    }
    /** TextView扩展函数 写法 */
    private fun TextView?.setTextStyle(textSize: Float, textColor: Int) {
        this!!.textSize = textSize
        this.setTextColor(resources.getColor(textColor))
    }

}