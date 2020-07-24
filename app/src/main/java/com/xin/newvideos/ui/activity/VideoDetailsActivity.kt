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

package com.xin.newvideos.ui.activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xin.newvideos.R
import com.xin.newvideos.app.Constant
import com.xin.newvideos.base.BaseMvpActivity
import com.xin.newvideos.contract.VideoDetailsContract
import com.xin.newvideos.ext.setOnClickNoRepeat
import com.xin.newvideos.ext.startVideoPlayerActivity
import com.xin.newvideos.http.bean.VideoDetailsData
import com.xin.newvideos.presenter.VideoDetailsPresenter
import com.xin.newvideos.ui.adapter.VideoDetailsAdapter
import com.xin.newvideos.util.StatusBarUtils
import kotlinx.android.synthetic.main.activity_video_details.*

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
 * @date : 2020/7/19 18:14
 * @desc :
 * @since : xinxiniscool@gmail.com
 */
class VideoDetailsActivity : BaseMvpActivity<VideoDetailsContract.View, VideoDetailsPresenter>(),
    VideoDetailsContract.View {

    private var mUrl: String? = ""
    private lateinit var mAdapter: VideoDetailsAdapter

    override fun initPresenter(): VideoDetailsPresenter = VideoDetailsPresenter()

    override fun initLayoutView(): Int = R.layout.activity_video_details

    override fun initData() {
        StatusBarUtils.setStatusColor(window, ContextCompat.getColor(this, R.color.blue), 1.0f)
        mUrl = intent.getStringExtra(Constant.VIDEO_DETAILS_URl)
        mUrl?.let {
            mPresenter.getVideoDetailsData(it)
        }
        showTipDialog()
    }


    @SuppressLint("InflateParams")
    override fun showVideoDetailsData(videoDetailsData: VideoDetailsData) {
        disMissTipDialog()
        val headerGroup =
            LayoutInflater.from(this).inflate(R.layout.header_video_details, null) as LinearLayout
        mAdapter = VideoDetailsAdapter(videoDetailsData.playerType)
        mAdapter.removeHeaderView(headerGroup)
        mAdapter.addHeaderView(headerGroup)
        with(rvVideoDetails) {
            layoutManager = LinearLayoutManager(this@VideoDetailsActivity)
            adapter = mAdapter
        }
        intent.getStringExtra(Constant.VIDEO_DETAILS_TITLE)?.let {
            headerGroup.findViewById<TextView>(R.id.tvVideoDetailsTitle).text = it
            videoDetailsData.title = it
        }
        with(videoDetailsData) {
            Glide.with(this@VideoDetailsActivity).load(imgUrl)
                .apply(RequestOptions().error(R.drawable.ic_arrow_error_image)).into(ivVideoImg)
//            ivVideoImg.setOnClickNoRepeat(2000L) {
//            }
//            mAdapter.setTitle(title)
            ivVideoImg.setOnClickNoRepeat { startVideoPlayerActivity(title, imgLinkUrl) }
            mAdapter.setNewInstance(playerType)
            headerGroup.findViewById<TextView>(R.id.tvVideoDirectorName).text = directorName
            headerGroup.findViewById<TextView>(R.id.tvVideoScore).text = score
            headerGroup.findViewById<TextView>(R.id.tvVideoPop).text = pop
            headerGroup.findViewById<TextView>(R.id.tvVideoActorName).text = actorName
            headerGroup.findViewById<TextView>(R.id.tvVideoTypeName).text = typeName
            headerGroup.findViewById<TextView>(R.id.tvVideoArea).text = area
            headerGroup.findViewById<TextView>(R.id.tvVideoLanguage).text = language
            headerGroup.findViewById<TextView>(R.id.tvVideoYear).text = year
            headerGroup.findViewById<TextView>(R.id.tvVideoSummary).text = summary
        }
    }
}