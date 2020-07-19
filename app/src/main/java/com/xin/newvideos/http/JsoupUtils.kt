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

package com.xin.newvideos.http

import android.util.Log
import com.xin.newvideos.app.Constant
import com.xin.newvideos.http.bean.*
import org.jsoup.nodes.Document

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
 * @date : 2020/7/11 22:42
 * @desc :
 * @since : xinxiniscool@gmail.com
 */
object JsoupUtils {

    private const val TAG = "JsoupUtils"

    fun parseHomeData(document: Document): HomeData {
        val homeData = HomeData()
        try {
            val bannerList = ArrayList<HomeBanner>()
            val bodyList = ArrayList<HomeBody>()
            val bannerElements = document.getElementsByClass("pic-01")
            for (data in bannerElements) {
                val homeBanner = HomeBanner()
                var imgUrl = data.select("li[style]").attr("style")
                val linkUrl = Constant.BASE_URL + data.select("a[href]").attr("href")
                val title = data.select("a[title]").attr("title")
                imgUrl = imgUrl.substring(imgUrl.indexOf("('") + 2, imgUrl.indexOf("')"))
//                Log.d(TAG, "parseHomeData:imgUrl=$imgUrl")
//                Log.d(TAG, "parseHomeData:linkUrl=$linkUrl")
//                Log.d(TAG, "parseHomeData:title=$title")
                homeBanner.imgUrl = imgUrl
                homeBanner.linkUrl = linkUrl
                homeBanner.title = title
                bannerList.add(homeBanner)
            }
            homeData.homeBannerList = bannerList
            val select = document.getElementsByClass("star-pic-list-a")
            for (data in select) {
                val homeBody = HomeBody()
                val linkUrl = Constant.BASE_URL + data.select("a[href]").attr("href")
                val title = data.select("a[title]").attr("title")
                val imgUrl = data.select("img[data-url]").attr("data-url")
//                Log.d(TAG, "parseHomeData:imgUrl=$imgUrl")
//                Log.d(TAG, "parseHomeData:linkUrl=$linkUrl")
//                Log.d(TAG, "parseHomeData:title=$title")
                homeBody.imgUrl = imgUrl
                homeBody.linkUrl = linkUrl
                homeBody.title = title
                bodyList.add(homeBody)
            }
            val bodyElements = document.getElementsByClass("teleplay-content-list").select("li")
//            Log.d(TAG, "parseHomeData:size=${bodyElements.size}")
//            Log.d(TAG, "parseHomeData:bodyElements=${bodyElements}")
            for (data in bodyElements) {
                val homeBody = HomeBody()
                val linkUrl = Constant.BASE_URL + data.select("a[href]").attr("href")
                val title = data.select("a[title]").attr("title")
                val imgUrl = data.select("img[data-url]").attr("data-url")
                val updateContent = data.getElementsByClass("tcl-pic4").text()
                val subTitle = data.getElementsByClass("tcl-title").select("p").text()
//                Log.d(TAG, "parseHomeData:imgUrl=$imgUrl")
//                Log.d(TAG, "parseHomeData:linkUrl=$linkUrl")
//                Log.d(TAG, "parseHomeData:title=$title")
//                Log.d(TAG, "parseHomeData:updateContent=$updateContent")
//                Log.d(TAG, "parseHomeData:subTitle=$subTitle")
                homeBody.imgUrl = imgUrl
                homeBody.linkUrl = linkUrl
                homeBody.title = title
                homeBody.updateContent = updateContent
                homeBody.subTitle = subTitle
                bodyList.add(homeBody)
            }
            homeData.homeBodyList = ArrayList(LinkedHashSet(bodyList))
//            Log.d(TAG, "parseHomeData:$homeData")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return homeData
    }

    fun parseVideoDetailsData(document: Document){
        val detailsContentElements = document.getElementsByClass("synopsis")
        val pZeroElement = detailsContentElements.select("p")[0]
        val directorName = pZeroElement.select("a[target]").text()
        val score = pZeroElement.select("em").first().text()
        val pop = pZeroElement.getElementById("hits").text()
    }
}