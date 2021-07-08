package com.banlap.vcomsupportkt.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 *@author Banlap on 2021/7/2
 */
/** constructor() 主构造方法 */
abstract class BaseBindingAdapter <M, VDB: ViewDataBinding> constructor(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected var mContext: Context?=null
    private var list: List<M>?=null

    init{
        this.list = ArrayList()
        this.mContext = context
    }

    fun setList(list: List<M>) { this.list = list }

    @LayoutRes
    protected abstract fun getLayoutId(layoutId: Int): Int

    override fun getItemCount(): Int { return this.list!!.size }

    class BaseBindViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        val vdb: VDB = DataBindingUtil.inflate(LayoutInflater.from(mContext), getLayoutId(viewType), parent, false)
        return BaseBindViewHolder(vdb.root)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int){
        val vdb: VDB? = DataBindingUtil.getBinding(viewHolder.itemView)
        this.onBindItem(vdb!!, this.list!![position], position)
    }

    protected abstract fun onBindItem(vdb: VDB, m: M , i : Int)
}