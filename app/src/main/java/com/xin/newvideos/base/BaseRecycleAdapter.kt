package com.xin.newvideos.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * @author : Leo
 * @date : 2019/12/26 19:42
 * @desc :
 * @since : lightingxin@qq.com
 */
abstract class BaseRecycleAdapter<T> : RecyclerView.Adapter<BaseRecycleHolder> {
    protected val mContext: Context
    protected val mItemLayoutId: Int
    protected var mData: MutableList<T>?
    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnItemLongClickListener: OnItemLongClickListener? = null

//    constructor(context: Context) {
//        mContext = context
//        mItemLayoutId = 0
//        mData = ArrayList()
//    }

    /**
     * @param context      context
     * @param itemLayoutId 布局的layout的Id
     */
    constructor(context: Context, itemLayoutId: Int) {
        mContext = context
        mItemLayoutId = itemLayoutId
        mData = ArrayList()
    }

    constructor(
        context: Context,
        itemLayoutId: Int,
        data: MutableList<T>?
    ) {
        mContext = context
        mItemLayoutId = itemLayoutId
        mData = data
    }

    override fun onBindViewHolder(helper: BaseRecycleHolder, position: Int) {
        helper.itemView.setOnClickListener { v ->
            mOnItemClickListener?.onItemClick(
                v,
                helper.adapterPosition
            )
        }
        helper.itemView.setOnLongClickListener { v ->
            mOnItemLongClickListener?.onItemLongClick(v, helper.adapterPosition)
            false
        }
        convert(helper, mData?.get(position), position)
    }

    /**
     * @param helper 自定义的ViewHolder对象，可以getView获取控件
     * @param item   对应的数据
     */
    abstract fun convert(helper: BaseRecycleHolder?, item: T?, position: Int)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecycleHolder {
        return BaseRecycleHolder(
            LayoutInflater.from(mContext).inflate(mItemLayoutId, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return if (isEmpty) 0 else mData!!.size
    }

    private val isEmpty: Boolean
        get() = mData == null || mData?.size == 0

    /**
     * 设置列表中的数据
     */
    fun setData(data: MutableList<T>?) {
        if (data == null) {
            return
        }
        mData = data
        notifyDataSetChanged()
    }

    val data: List<T>? get() = mData

    fun notifyItemChange(position: Int) {
        this.notifyItemChanged(position)
    }

    fun updateView(data: MutableList<T>?) {
        mData = data
        notifyDataSetChanged()
    }

    /**
     * 将单个数据添加到列表中
     */
    fun addSingleDate(t: T?) {
        if (t == null) {
            return
        }
        mData?.add(t)
        notifyItemInserted(mData?.size!! - 1)
    }

    fun addSingleDate(t: T, position: Int) {
        mData?.add(position, t)
        notifyItemInserted(position)
        notifyItemRangeChanged(position, mData?.size!!)
    }

    fun removeData(position: Int) {
        mData?.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mData?.size!!)
    }

    fun removeData(t: T) {
        val index = mData?.indexOf(t)
        notifyItemRemoved(index!!)
        notifyItemRangeChanged(index, mData?.size!!)
    }

    /**
     * 将一个List添加到列表中
     */
    fun addDates(dates: List<T>?) {
        if (dates == null || dates.isEmpty()) {
            return
        }
        mData?.addAll(dates)
        notifyDataSetChanged()
    }

    fun clearDates() {
        if (!isEmpty) {
            mData?.clear()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(v: View?, position: Int)
    }

    /**
     * 设置点击事件
     *
     * @param onItemClickListener
     */
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    /**
     * 设置长按点击事件
     *
     * @param onItemLongClickListener
     */
    fun setonLongItemClickListener(onItemLongClickListener: OnItemLongClickListener?) {
        mOnItemLongClickListener = onItemLongClickListener
    }
}