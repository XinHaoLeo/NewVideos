/*
 * Copyright 2020 Leo
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xin.newvideos.widget

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.xin.newvideos.R
import com.xin.newvideos.ui.activity.DisclaimerActivity
import com.xin.newvideos.ui.activity.PrivacyActivity
import kotlinx.android.synthetic.main.dialog_contract.*

/**
 * █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 * ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 * ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 * ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 * ░     ░ ░      ░  ░
 *
 * @author : Leo
 * @date : 2020/7/13 10:44
 * @desc : 协议对话框
 * @since : lightingxin@qq.com
 */
class ContractDialog private constructor(builder: Builder) :
    AlertDialog(builder.mContext, R.style.ContractDialog),
    View.OnClickListener {

    private val mContext: Context?
    private val title: String?
    private val message: String?
    private val dialogListener: OnDialogClickListener?

    companion object {
        private val TAG = ContractDialog::class.java.simpleName
    }

    init {
        mContext = builder.mContext
        title = builder.title
        message = builder.message
        dialogListener = builder.listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_contract)
        initView()
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }

    private fun initView() {
        dialogTitle.text = title
        dialogMessage.text = message
        val content = mContext!!.getString(R.string.contract_dialog_message)
        val spanText = SpannableString(content)
        //免责声明
        val disclaimerIndex = content.indexOf(mContext.getString(R.string.disclaimer))
        val disclaimer = mContext.getString(R.string.disclaimer)
        Log.d(TAG, "initView: disclaimerIndex=$disclaimerIndex")
        spanText.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    mContext.startActivity(Intent(mContext, DisclaimerActivity::class.java))
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = Color.RED
                    ds.isUnderlineText = true
                }
            },
            disclaimerIndex,
            disclaimerIndex + disclaimer.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        subMessage.movementMethod = LinkMovementMethod.getInstance()
        subMessage.text = spanText

        //隐私政策
        val privacyIndex = content.indexOf(mContext.getString(R.string.privacy_policy))
        val privacy = mContext.getString(R.string.privacy_policy)
        Log.d(TAG, "initView: privacyIndex=$privacyIndex")
        spanText.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                mContext.startActivity(Intent(mContext, PrivacyActivity::class.java))
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.RED
                ds.isUnderlineText = true
            }
        }, privacyIndex, privacyIndex + privacy.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        subMessage.movementMethod = LinkMovementMethod.getInstance()
        subMessage.text = spanText
        confirmButton?.setOnClickListener(this)
        cancelView?.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.confirmButton -> dialogListener?.onConfirmClick()
            R.id.cancelView -> dialogListener?.onCancelClick()
        }
        dismiss()
    }

    class Builder {

        lateinit var mContext: Context
        var title: String? = null
        var message: String? = null
        var listener: OnDialogClickListener? = null

        fun setContext(context: Context): Builder {
            mContext = context
            return this
        }

        fun setDialogTitle(s: String?): Builder {
            title = s
            return this
        }

        fun setDialogMessage(s: String?): Builder {
            message = s
            return this
        }

        fun setOnDialogClickListener(listener: OnDialogClickListener?): Builder {
            this.listener = listener
            return this
        }

        fun build(): ContractDialog {
            return ContractDialog(this)
        }
    }

    interface OnDialogClickListener {
        fun onConfirmClick()
        fun onCancelClick()
    }

}