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

    fun parseVideoDetailsData(document: Document): VideoDetailsData {
        val videoDetailsData = VideoDetailsData()
        val imgElements = document.getElementsByClass("details-con1")
        val imgUrl = imgElements.select("img[data-url]").first().attr("data-url")
        val imgLinkUrl = Constant.BASE_URL + imgElements.select("a[href]").first().attr("href")
        val detailsContentElements = document.getElementsByClass("synopsis")
        val pOneElement = detailsContentElements.select("p")[0]
        val directorName = pOneElement.select("a[target]").text()
        val score = pOneElement.select("em").first().text()
        val pop = pOneElement.getElementById("hits").text()
        val pTwoElement = detailsContentElements.select("p")[1]
        val actorName = pTwoElement.select("a[target]").text()
        val pThreeElement = detailsContentElements.select("p")[2]
        val typeName = pThreeElement.select("a[href]").first().text()
        for (pThreeData in pThreeElement.select("a[target]")) {
            when {
                pThreeData.select("a[href]").attr("href").contains("area") -> {
                    val area = pThreeData.text()
                    videoDetailsData.area = area
//                    Log.d(TAG, "parseVideoDetailsData:area=$area")
                }
                pThreeData.select("a[href]").attr("href").contains("lang") -> {
                    val language = pThreeData.text()
                    videoDetailsData.language = language
//                    Log.d(TAG, "parseVideoDetailsData:language=$language")
                }
                pThreeData.select("a[href]").attr("href").contains("year") -> {
                    val year = pThreeData.text()
                    videoDetailsData.year = year
//                    Log.d(TAG, "parseVideoDetailsData:year=$year")
                }
            }
        }
        var summary = detailsContentElements.select("p[class]").select("span").text()
        summary += detailsContentElements.select("p[class]").text()
//        Log.d(TAG, "parseVideoDetailsData:imgUrl=$imgUrl")
//        Log.d(TAG, "parseVideoDetailsData:imgLinkUrl=$imgLinkUrl")
//        Log.d(TAG, "parseVideoDetailsData:directorName=$directorName")
//        Log.d(TAG, "parseVideoDetailsData:score=$score")
//        Log.d(TAG, "parseVideoDetailsData:pop=$pop")
//        Log.d(TAG, "parseVideoDetailsData:actorName=$actorName")
//        Log.d(TAG, "parseVideoDetailsData:typeName=$typeName")
//        Log.d(TAG, "parseVideoDetailsData:summary=$summary")
        videoDetailsData.imgUrl = imgUrl
        videoDetailsData.imgLinkUrl = imgLinkUrl
        videoDetailsData.directorName = directorName
        videoDetailsData.score = score
        videoDetailsData.pop = pop
        videoDetailsData.actorName = actorName
        videoDetailsData.typeName = typeName
        videoDetailsData.summary = summary
        val playerTypeList = ArrayList<PlayerType>()
        val playerTypeElements = document.getElementsByClass("details-con2-body")
        for (data in playerTypeElements) {
            val playerType = PlayerType()
            val playerTypeName = data.getElementsByClass("active-style8").text()
//            Log.d(TAG, "parseVideoDetailsData:playerTypeName=$playerTypeName")
            playerType.typeName = playerTypeName
            val videoCountList = ArrayList<VideoCount>()
            val videoCountElements = data.getElementsByClass("details-con2-list").select("li")
            for (videoData in videoCountElements) {
                val videoCount = VideoCount()
                val countTitle = videoData.select("a[data-num]").text()
                val countUrl = Constant.BASE_URL + videoData.select("a[href]").attr("href")
//                Log.d(TAG, "parseVideoDetailsData:countTitle=$countTitle")
//                Log.d(TAG, "parseVideoDetailsData:countUrl=$countUrl")
                videoCount.countTitle = countTitle
                videoCount.countUrl = countUrl
                videoCountList.add(videoCount)
            }
            playerType.videoCount = videoCountList
            playerTypeList.add(playerType)
        }
        videoDetailsData.playerType = playerTypeList
        return videoDetailsData
    }
}