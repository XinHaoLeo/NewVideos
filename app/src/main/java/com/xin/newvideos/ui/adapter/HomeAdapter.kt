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

package com.xin.newvideos.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xin.newvideos.R
import com.xin.newvideos.http.bean.HomeBody

/**
 *   █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 *  ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 *  ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 *  ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 *           ░     ░ ░      ░  ░
 * @author : Leo
 * @date : 2020/7/18 23:29
 * @desc :
 * @since : xinxiniscool@gmail.com
 */
class HomeAdapter(layoutId: Int, bodyList: ArrayList<HomeBody>?) :
    BaseQuickAdapter<HomeBody, HomeAdapter.HomeViewHolder>(layoutId, bodyList) {

    override fun convert(holder: HomeViewHolder, item: HomeBody) {
        with(item) {
            Glide.with(holder.ivImg.context).load(imgUrl)
                .apply(RequestOptions().error(R.drawable.ic_arrow_error_image)).into(holder.ivImg)
            holder.tvContentNum.text = "\t$updateContent"
            holder.tvTitle.text = title
            holder.tvSubTitle.text = subTitle
        }
    }

    class HomeViewHolder(view: View) : BaseViewHolder(view) {
        val ivImg: AppCompatImageView = view.findViewById(R.id.ivImg)
        val tvContentNum: TextView = view.findViewById(R.id.tvContentNum)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvSubTitle: TextView = view.findViewById(R.id.tvSubTitle)
    }

}