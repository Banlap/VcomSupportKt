package com.banlap.vcomsupportkt.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 *@author Banlap on 2021/6/16
 */
abstract class BaseActivity<VM: ViewModel, VDB: ViewDataBinding>: AppCompatActivity() {

    private var mViewDataBinding: VDB?=null
    private var mViewModel: VM?=null

    fun getViewDataBinding(): VDB? { return mViewDataBinding }
    fun getViewModel(): VM? { return mViewModel }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewDataBinding?.lifecycleOwner = this
        init()
        initView()
    }

    @SuppressWarnings("unchecked")
    private fun init(){
        //通过class反射获取 参数化类型即泛型(比如model文件夹里面的各种实体类)
        val mClass: Class<VM> = ((this.javaClass.genericSuperclass) as ParameterizedType).actualTypeArguments[0] as Class<VM>
        mViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(mClass)
    }
    protected abstract fun initView()
}

