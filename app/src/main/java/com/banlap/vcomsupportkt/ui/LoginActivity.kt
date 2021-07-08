package com.banlap.vcomsupportkt.ui

import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.banlap.vcomsupportkt.R
import com.banlap.vcomsupportkt.base.BaseActivity
import com.banlap.vcomsupportkt.databinding.ActivityLoginBinding
import com.banlap.vcomsupportkt.uivm.LoginVM

/**
 *@author Banlap on 2021/7/7
 */
class LoginActivity: BaseActivity<LoginVM, ActivityLoginBinding>(), LoginVM.LoginCallBack {

    override fun getLayoutId(): Int { return R.layout.activity_login}
    override fun initView() {
        getViewDataBinding()?.vm = getViewModel()
        getViewModel()?.setCallBack(this)
    }

    override fun viewLogin() {
        if(getViewDataBinding()!!.etAccount.text.toString() !="" && getViewDataBinding()!!.etPasswords.text.toString() !="") {
            getViewDataBinding()!!.pbLoading.visibility = View.VISIBLE
            if(getViewDataBinding()!!.etAccount.text.toString() == "root"){
                getViewModel()!!.login(getViewDataBinding()!!.etAccount.text.toString(), getViewDataBinding()!!.etPasswords.text.toString())
            } else {
                Toast.makeText(this, "请使用管理员权限登录", Toast.LENGTH_SHORT).show()
                getViewDataBinding()!!.pbLoading.visibility = View.GONE
            }
        } else {
            Toast.makeText(this, "请填写好完整信息", Toast.LENGTH_SHORT).show()
        }
    }

    override fun loginSuccess() {
        getViewDataBinding()!!.pbLoading.visibility = View.GONE
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    override fun loginFailure() {
        getViewDataBinding()!!.pbLoading.visibility = View.GONE
        Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val v = currentFocus
        if(isShouldHideInput(v, ev)) {
            v?.windowToken?.let { hideSoftInput(it) }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private fun isShouldHideInput(v: View?, event: MotionEvent?): Boolean {
        if(v != null&& (v is EditText)) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.height
            val right = left + v.width
            // 点击EditText的事件，忽略它。
            return !(event!!.x in left..right && event.y in top..bottom)
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private fun hideSoftInput(token: IBinder) {
        val im: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
    }

}