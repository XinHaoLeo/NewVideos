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

import androidx.viewpager.widget.ViewPager
import com.kongzue.dialog.v3.TipDialog
import com.xin.newvideos.R
import com.xin.newvideos.base.BaseMvpFragment
import com.xin.newvideos.contract.HomeContract
import com.xin.newvideos.http.bean.HomeData
import com.xin.newvideos.presenter.HomePresenter
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
class HomeFragment : BaseMvpFragment<HomeContract.View, HomePresenter>(), HomeContract.View,
    ViewPager.OnPageChangeListener {

    private lateinit var mTitleList: ArrayList<String>
    private lateinit var mHomeData: HomeData
    private var mHomePage = 0

    override fun initPresenter(): HomePresenter = HomePresenter()

    override fun initLayoutView(): Int = R.layout.fragment_home

    override fun initData() {
//        TipDialog.showWait(mActivity, "小鑫正在为您努力加载中...").setTipTime(5000)
        mTitleList = ArrayList()
        mPresenter.getHomeData()
        vpHome.currentItem = 0
    }

    override fun lazyLoadData() {

    }

    override fun showHomeData(homeData: HomeData) {
        TipDialog.dismiss()
        mHomeData = homeData
//        initHomeTabData()
    }

//    /**
//     * 将viewPager+TabLayout绑定
//     */
//    private fun initHomeTabData() {
//        mTitleList.add("首页推荐")
//        for (i in mHomeData.bodyList.indices) {
//            mTitleList.add(mHomeData.bodyList[i].title)
//        }
//        for (i in mTitleList.indices) {
//            tabLayoutHome.addTab(tabLayoutHome.newTab().setText(mTitleList[i]))
//        }
//        for (i in mTitleList.indices) {
//            val homeListFragment = HomeListFragment()
//            mFragmentList.add(homeListFragment)
//        }
//        mFragmentList[0].setOnClickDataListener(this)
//        vpHome.adapter = HomeAdapter(
//            mActivity.supportFragmentManager,
//            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
//        )
//        tabLayoutHome.setupWithViewPager(vpHome)
//        vpHome.currentItem = mHomePage
//        vpHome.addOnPageChangeListener(this)
//    }
//
//    override fun onClickData(title: String) {
//        var position = 0
//        for (i in mTitleList.indices) {
//            if (title == mTitleList[i]) {
//                position = i
//            }
//        }
//        vpHome.currentItem = position
//    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        mHomePage = position
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.cancel()
    }


//    inner class HomeAdapter(fm: FragmentManager, behavior: Int) :
//        FragmentPagerAdapter(fm, behavior) {
//        override fun getItem(position: Int): Fragment {
//            Log.d("Leo", "getItem:$position")
//            val bundle = Bundle()
//            if (position != 0) {
//                bundle.putString(Constant.HOME_URL, mHomeData.bodyList[position - 1].detailUrl)
//            } else {
//                mFragmentList[position].setHomeData(mHomeData)
//            }
//            bundle.putInt(Constant.HOME_PAGE, position)
//            mFragmentList[position].arguments = bundle
//            return mFragmentList[position]
//        }
//
//        override fun getCount(): Int = mTitleList.size
//
//        @Nullable
//        override fun getPageTitle(position: Int): CharSequence? {
//            return mTitleList[position]
//        }
//
//    }

}