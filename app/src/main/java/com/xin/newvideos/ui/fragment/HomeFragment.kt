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

package com.xin.newvideos.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kongzue.dialog.v3.TipDialog
import com.xin.newvideos.R
import com.xin.newvideos.app.Constant
import com.xin.newvideos.base.BaseMvpFragment
import com.xin.newvideos.contract.HomeContract
import com.xin.newvideos.http.bean.HomeBody
import com.xin.newvideos.http.bean.HomeData
import com.xin.newvideos.presenter.HomePresenter
import com.xin.newvideos.ui.activity.VideoDetailsActivity
import com.xin.newvideos.ui.adapter.HomeAdapter
import com.xin.newvideoss.widget.ImageLoader
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_home.*

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
 *@date : 2020/7/14 9:48
 *@since : xinxiniscool@gmail.com
 *@desc :
 */
class HomeFragment : BaseMvpFragment<HomeContract.View, HomePresenter>(), HomeContract.View {

    private lateinit var mHomeData: HomeData
    private var mBodyList: ArrayList<HomeBody>? = null
    private lateinit var mHomeAdapter: HomeAdapter
    private lateinit var banner: Banner

    override fun initPresenter(): HomePresenter = HomePresenter()

    override fun initLayoutView(): Int = R.layout.fragment_home

    @SuppressLint("InflateParams")
    override fun initData() {
        TipDialog.showWait(mActivity, "小鑫正在为您努力加载中...").setTipTime(5000)
        mPresenter.getHomeData()
        with(rvHome) {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
        }
        mHomeAdapter = HomeAdapter(R.layout.item_home, mBodyList)
        val headerGroup =
            LayoutInflater.from(mActivity).inflate(R.layout.head_banner, null) as LinearLayout
        banner = headerGroup.findViewById(R.id.banner)
        with(banner) {
            headerGroup.removeView(this)
            mHomeAdapter.addHeaderView(this)
        }
        rvHome.adapter = mHomeAdapter
        mHomeAdapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(mActivity, VideoDetailsActivity::class.java)
            intent.putExtra(Constant.VIDEO_DETAILS_URl, mHomeAdapter.getItem(position).linkUrl)
            intent.putExtra(Constant.VIDEO_DETAILS_TITLE, mHomeAdapter.getItem(position).title)
            startActivity(intent)
        }
    }

    override fun lazyLoadData() {

    }

    override fun showHomeData(homeData: HomeData) {
        TipDialog.dismiss()
        mHomeData = homeData
        mHomeAdapter.setNewInstance(mHomeData.homeBodyList)
        initBanner(mHomeData)
    }

    private fun initBanner(homeData: HomeData) {
        val bannerImages: MutableList<String> = ArrayList()
        val bannerTitles: MutableList<String> = ArrayList()
        val bannerUrl: MutableList<String> = ArrayList()
        for (homeBanner in homeData.homeBannerList) {
            with(homeBanner) {
                bannerImages.add(imgUrl)
                bannerTitles.add(title)
                bannerUrl.add(linkUrl)
            }
        }
        with(banner) {
            setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
            setImageLoader(ImageLoader())
            setImages(bannerImages)
            setBannerAnimation(Transformer.DepthPage)
            setBannerTitles(bannerTitles)
            isAutoPlay(true)
            setDelayTime(homeData.homeBannerList.size * 400)
            setIndicatorGravity(BannerConfig.CENTER)
            setOnBannerListener {

            }
            start()
        }
    }

    override fun onStart() {
        super.onStart()
        banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        banner.stopAutoPlay()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.cancel()
    }

}