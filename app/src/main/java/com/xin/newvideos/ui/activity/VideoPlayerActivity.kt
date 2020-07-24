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

import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.xin.newvideos.R
import com.xin.newvideos.app.Constant
import com.xin.newvideos.util.StatusBarUtils
import kotlinx.android.synthetic.main.activity_video_player.*

/**
 *
 *   █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 *  ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 *  ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 *  ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 *           ░     ░ ░      ░  ░
 *@author : Leo
 *@date : 2020/7/21 14:38
 *@since : xinxiniscool@gmail.com
 *@desc :
 */
class VideoPlayerActivity : GSYBaseActivityDetail<StandardGSYVideoPlayer>() {

    //    private var mUrl: String = "https://zy.aoxtv.com/m3u8.php?url=https://v4.szjal.cn/20200706/nOcmRcYg/index.m3u8"
//    private var mUrl: String = "https://zuidajiexi.net/m3u8.html?url=https://hong.tianzhen-zuida.com/20200112/18418_9750cc5e/index.m3u8"
//    private var mUrl: String = "https://hong.tianzhen-zuida.com/20200112/18418_9750cc5e/index.m3u8"
    private var mUrl: String = "https://v4.szjal.cn/20200706/nOcmRcYg/index.m3u8"

    //    private var mUrl: String = "http://livecdn1.news.cn/Live_MajorEvent01Phone/manifest.m3u8"
    private var mTitle: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
//        val videoOptionModel =
//            VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "dns_cache_clear", 1)
//        val optionList = ArrayList<VideoOptionModel>()
//        optionList.add(videoOptionModel)
//        GSYVideoManager.instance().optionModelList = optionList
        StatusBarUtils.setStatusColor(window, ContextCompat.getColor(this, R.color.blue), 1.0f)
        mTitle = intent.getStringExtra(Constant.VIDEO_PLAYER_TITLE)
        initVideoBuilderMode()
    }

    override fun clickForFullScreen() {
    }

    /**
     * 是否启动选择横屏,true启动
     */
    override fun getDetailOrientationRotateAuto(): Boolean {
        return true
    }

    override fun getGSYVideoPlayer(): StandardGSYVideoPlayer {
        return xinVideoPlayer
    }

    override fun getGSYVideoOptionBuilder(): GSYVideoOptionBuilder {
        val imageView = ImageView(this)
        loadCover(imageView, mUrl)
        return GSYVideoOptionBuilder()
            .setThumbImageView(imageView)
            .setUrl(mUrl)
            .setCacheWithPlay(true)
            .setVideoTitle(mTitle)
            .setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setShowFullAnimation(true) //打开动画
            .setNeedLockFull(true) //设置全屏内置锁
            .setSeekRatio(1f)
    }

    private fun loadCover(
        imageView: ImageView,
        url: String
    ) {
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageResource(R.mipmap.ic_launcher)
        Glide.with(this)
            .setDefaultRequestOptions(
                RequestOptions()
                    .frame(3000000)
                    .centerCrop()
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
            )
            .load(url)
            .into(imageView)
    }

    override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
        super.onQuitFullscreen(url, *objects)
    }

    override fun onQuitSmallWidget(url: String?, vararg objects: Any?) {
        super.onQuitSmallWidget(url, *objects)
    }
}