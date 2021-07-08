package com.banlap.vcomsupportkt.ui

import android.view.View
import android.widget.Toast
import com.banlap.vcomsupportkt.R
import com.banlap.vcomsupportkt.base.BaseActivity
import com.banlap.vcomsupportkt.databinding.ActivityUpdateMacBinding
import com.banlap.vcomsupportkt.model.Presupposition
import com.banlap.vcomsupportkt.uivm.UpdateMacVM

/**
 *@author Banlap on 2021/6/28
 */
class UpdateMacActivity: BaseActivity<UpdateMacVM, ActivityUpdateMacBinding>(), UpdateMacVM.UpdateMacCallBack {

    override fun getLayoutId(): Int { return R.layout.activity_update_mac }
    override fun initView() {
        getViewDataBinding()?.vm = getViewModel()
        getViewModel()?.setCallBack(this)
    }
    override fun viewBack() { finish() }
    /** 添加mac地址 */
    override fun viewUpdateMac() {
        clickBefore()
        getViewModel()?.selectUserId(getViewDataBinding()?.etUserId?.text.toString())
    }
    override fun selectUserIdSuccess() {
        val userId = getViewDataBinding()?.etUserId?.text.toString()
        val mac = getViewDataBinding()?.etMac?.text.toString()
        if(userId != "" && mac != "") {
            getViewModel()?.updateMac(userId = userId, mac = mac)
            return
        }
        clickAfter("Mac地址为空")
    }
    override fun selectUserIdFailure() { clickAfter("用户ID不存在") }
    override fun updateMacSuccess() { clickAfter("更新成功") }
    override fun updateMacFailure() { clickAfter("更新失败") }

    /** 按钮样式点击时*/
    private fun clickBefore() {
        getViewDataBinding()?.rlUpdateMac?.background = getDrawable(R.drawable.shape_switch_bg_gray_f1)
        getViewDataBinding()?.pbLoading?.visibility = View.VISIBLE
        getViewDataBinding()?.tvUpdate?.visibility = View.INVISIBLE
    }
    /** 按钮样式点击后*/
    private fun clickAfter(toastText: String) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
        getViewDataBinding()?.rlUpdateMac?.background = getDrawable(R.drawable.shape_radius_red_db)
        getViewDataBinding()?.pbLoading?.visibility = View.INVISIBLE
        getViewDataBinding()?.tvUpdate?.visibility = View.VISIBLE
    }
}