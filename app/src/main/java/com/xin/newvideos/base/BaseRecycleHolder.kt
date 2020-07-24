package com.xin.newvideos.base

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.util.Linkify
import android.util.SparseArray
import android.view.View
import android.view.View.OnLongClickListener
import android.view.View.OnTouchListener
import android.widget.Checkable
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @author : Leo
 * @date : 2019/12/26 19:43
 * @desc :
 * @since : lightingxin@qq.com
 */
open class BaseRecycleHolder(private val convertView: View) :
    RecyclerView.ViewHolder(convertView) {
    private val mViews: SparseArray<View> = SparseArray()


    init {
        convertView.tag = this
    }

    /**
     * 通过viewId获取控件
     */
    fun <T : View> getView(viewId: Int): T {
        var view = mViews[viewId]
        if (view == null) {
            view = convertView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    /**
     * 设置TextView的值
     */
    fun setText(viewId: Int, text: String?): BaseRecycleHolder {
        val tv = getView<TextView>(viewId)
        tv.text = text
        return this
    }

    fun setImageResource(viewId: Int, resId: Int): BaseRecycleHolder {
        val view = getView<ImageView>(viewId)
        view.setImageResource(resId)
        return this
    }

    fun setImageBitmap(viewId: Int, bitmap: Bitmap?): BaseRecycleHolder {
        val view = getView<ImageView>(viewId)
        view.setImageBitmap(bitmap)
        return this
    }

    fun setImageDrawable(
        viewId: Int,
        drawable: Drawable?
    ): BaseRecycleHolder {
        val view = getView<ImageView>(viewId)
        view.setImageDrawable(drawable)
        return this
    }

    fun setBackgroundColor(viewId: Int, color: Int): BaseRecycleHolder {
        val view = getView<View>(viewId)
        view.setBackgroundColor(color)
        return this
    }

    fun setBackgroundRes(viewId: Int, backgroundRes: Int): BaseRecycleHolder {
        val view = getView<View>(viewId)
        view.setBackgroundResource(backgroundRes)
        return this
    }

    fun setTextColor(viewId: Int, textColor: Int): BaseRecycleHolder {
        val view = getView<TextView>(viewId)
        view.setTextColor(textColor)
        return this
    }

    @SuppressLint("NewApi")
    fun setAlpha(viewId: Int, value: Float): BaseRecycleHolder {
        getView<View>(viewId).alpha = value
        return this
    }

    fun setVisible(viewId: Int, visible: Boolean): BaseRecycleHolder {
        val view = getView<View>(viewId)
        view.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    fun linkify(viewId: Int): BaseRecycleHolder {
        val view = getView<TextView>(viewId)
        Linkify.addLinks(view, Linkify.ALL)
        return this
    }

    fun setTypeface(typeface: Typeface?, vararg viewIds: Int): BaseRecycleHolder {
        for (viewId in viewIds) {
            val view = getView<TextView>(viewId)
            view.typeface = typeface
            view.paintFlags = view.paintFlags or Paint.SUBPIXEL_TEXT_FLAG
        }
        return this
    }

    fun setProgress(viewId: Int, progress: Int): BaseRecycleHolder {
        val view = getView<ProgressBar>(viewId)
        view.progress = progress
        return this
    }

    fun setProgress(viewId: Int, progress: Int, max: Int): BaseRecycleHolder {
        val view = getView<ProgressBar>(viewId)
        view.max = max
        view.progress = progress
        return this
    }

    fun setMax(viewId: Int, max: Int): BaseRecycleHolder {
        val view = getView<ProgressBar>(viewId)
        view.max = max
        return this
    }

    fun setTag(viewId: Int, tag: Any?): BaseRecycleHolder {
        val view = getView<View>(viewId)
        view.tag = tag
        return this
    }

    fun setTag(viewId: Int, key: Int, tag: Any?): BaseRecycleHolder {
        val view = getView<View>(viewId)
        view.setTag(key, tag)
        return this
    }

//    fun setChecked(viewId: Int, checked: Boolean): BaseRecycleHolder {
//        val view = getView(viewId) as Checkable
//        view.isChecked = checked
//        return this
//    }

    /**
     * 关于事件的
     */
    fun setOnClickListener(
        viewId: Int,
        listener: View.OnClickListener?
    ): BaseRecycleHolder {
        val view = getView<View>(viewId)
        view.setOnClickListener(listener)
        return this
    }

    fun setOnTouchListener(viewId: Int, listener: OnTouchListener?): BaseRecycleHolder {
        val view = getView<View>(viewId)
        view.setOnTouchListener(listener)
        return this
    }

    fun setOnLongClickListener(viewId: Int, listener: OnLongClickListener?): BaseRecycleHolder {
        val view = getView<View>(viewId)
        view.setOnLongClickListener(listener)
        return this
    }

}