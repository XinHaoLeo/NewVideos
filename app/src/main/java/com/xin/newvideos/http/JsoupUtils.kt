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
            val homeBody = HomeBody()
            val homeRecommendTitle =
                document.getElementsByClass("con3 star-bot").select("span").text()
//            Log.d(TAG, "parseHomeData:homeRecommendTitle=$homeRecommendTitle")
            homeBody.title = homeRecommendTitle
            val homeBodyTypeList = ArrayList<HomeBodyType>()
            val select = document.getElementsByClass("star-pic-list-a")
            for (data in select) {
                val homeBodyType = HomeBodyType()
                val linkUrl = Constant.BASE_URL + data.select("a[href]").attr("href")
                val title = data.select("a[title]").attr("title")
                val imgUrl = data.select("img[data-url]").attr("data-url")
//                Log.d(TAG, "parseHomeData:imgUrl=$imgUrl")
//                Log.d(TAG, "parseHomeData:linkUrl=$linkUrl")
//                Log.d(TAG, "parseHomeData:title=$title")
                homeBodyType.imgUrl = imgUrl
                homeBodyType.linkUrl = linkUrl
                homeBodyType.title = title
                homeBodyTypeList.add(homeBodyType)
            }
            val url =
                document.getElementsByClass("teleplay-left fl").select("div[class]")[1].attr("href")
            val title = document.getElementsByClass("teleplay-left fl")
                .select("div[class]")[1].attr("title")
            Log.d(TAG, "parseHomeData:url=$url")
            Log.d(TAG, "parseHomeData:title=$title")
            val es = document.getElementsByClass("splendid-tab fl")
            Log.d(TAG, "parseHomeData:es=$es")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return homeData
    }

}