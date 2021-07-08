package com.banlap.vcomsupportkt.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.banlap.vcomsupportkt.R
import com.banlap.vcomsupportkt.VcomData
import com.banlap.vcomsupportkt.base.BaseActivity
import com.banlap.vcomsupportkt.base.BaseBindingAdapter
import com.banlap.vcomsupportkt.databinding.ActivityQueryMacBinding
import com.banlap.vcomsupportkt.databinding.ItemQueryMacBinding
import com.banlap.vcomsupportkt.model.Presupposition
import com.banlap.vcomsupportkt.request.MessageEvent
import com.banlap.vcomsupportkt.uivm.QueryMacVM
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 *@author Banlap on 2021/6/28
 */
class QueryMacActivity: BaseActivity<QueryMacVM, ActivityQueryMacBinding>(), QueryMacVM.QueryMacCallBack {

    private var mAdapter: QueryMacAdapter?=null
    private var presuppositionList: MutableList<Presupposition> = ArrayList()

    override fun getLayoutId(): Int { return R.layout.activity_query_mac }

    override fun initView() {
        getViewDataBinding()?.vm = getViewModel()
        getViewModel()?.setCallBack(this)

        EventBus.getDefault().register(this)
        EventBus.getDefault().post(MessageEvent(MessageEvent.MAC_REFRESH))

        mAdapter = QueryMacAdapter(this)
        mAdapter?.setList(presuppositionList)
        getViewDataBinding()?.rvMacList?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        getViewDataBinding()?.rvMacList?.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when(event.msgCode) {
            MessageEvent.MAC_REFRESH -> queryMacData()
            MessageEvent.MAC_READY -> {
                presuppositionList.clear()
                presuppositionList.addAll(VcomData.instance.presuppositionList)
                mAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
    /** banlap: 查询账户对应mac列表 */
    private fun queryMacData() {
        getViewModel()?.queryMac()
    }

    override fun viewBack() { finish() }
    override fun queryMacSuccess() {
        Toast.makeText(this, "查询成功", Toast.LENGTH_SHORT).show()
        var row = "" + VcomData.instance.row
        getViewDataBinding()!!.tvRows.text = row
        getViewDataBinding()!!.prLoading.visibility = View.GONE
        getViewDataBinding()!!.rvMacList.visibility = View.VISIBLE
    }

    override fun queryMacFailure() {
        Toast.makeText(this, "查询失败", Toast.LENGTH_SHORT).show()
        getViewDataBinding()!!.prLoading.visibility = View.VISIBLE
        getViewDataBinding()!!.rvMacList.visibility = View.GONE
    }

    //inner class内部类
    inner class QueryMacAdapter constructor(context: Context): BaseBindingAdapter<Presupposition, ItemQueryMacBinding>(context) {
        override fun getLayoutId(layoutId: Int): Int { return R.layout.item_query_mac }
        override fun onBindItem(vdb: ItemQueryMacBinding, m: Presupposition, i: Int) {
            vdb.tvMac.text = m.productMac
            vdb.tvUserId.text = m.userId
            vdb.tvUserName.text = m.userName
            vdb.tvPositionId.text = m.positionId

            vdb.llDelete.setOnClickListener{
                val alertDialog: AlertDialog = AlertDialog.Builder(mContext)
                    .setTitle("提示")
                    .setMessage("是否删除该mac地址")
                    .setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
                    .setPositiveButton("确定") { _, _ -> deleteMacDialog(m.positionId) }
                    .create()
                alertDialog.show()
            }
        }
    }

    /** banlap: 删除对应账户的mac地址 */
    private fun deleteMacDialog(positionId: String) {
        getViewDataBinding()!!.prLoading.visibility = View.VISIBLE
        getViewModel()!!.deleteMac(positionId)
    }
    override fun deleteMacSuccess() {
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show()
        EventBus.getDefault().post(MessageEvent(MessageEvent.MAC_REFRESH))
    }
    override fun deleteMacFailure() {
        Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show()
        EventBus.getDefault().post(MessageEvent(MessageEvent.MAC_REFRESH))
    }
}