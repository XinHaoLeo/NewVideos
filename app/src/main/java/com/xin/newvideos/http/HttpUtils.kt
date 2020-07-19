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
import com.xin.newvideos.ext.launchUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.util.*

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
 * @date : 2020/7/12 13:26
 * @desc :
 * @since : xinxiniscool@gmail.com
 */
object HttpUtils {

    private const val TAG = "HttpUtils"

    private fun obtainAgent(): String = Constant.UA[Random().nextInt(33)]

//    private fun obtainProxy(): Proxy = Proxy(Constant.HTTPS_PROXY_POOL[Random().nextInt(4)])


    private fun createConnection(url: String): Connection {
        return Jsoup.connect(url)
            .userAgent(obtainAgent())
//            .proxy()
            .timeout(30 * 1000)
            .ignoreHttpErrors(true)
    }

    fun getHome(listener: OnHttpListener): Job {
        return launchUi {
            try {
                val statusCode = statusCode(Constant.BASE_URL)
                Log.d(TAG, "getContentData: statusCode=$statusCode")
                if (statusCode == 200) {
                    createConnectionGet(Constant.BASE_URL).let { listener.onSuccess(it) }
                } else {
                    listener.onError("获取失败")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                listener.onError(e.toString())
            }
        }
    }

    fun getVideoDetails(url: String,listener: OnHttpListener):Job{
        return launchUi {
            try {
                val statusCode = statusCode(url)
                Log.d(TAG, "getVideoDetails: statusCode=$statusCode")
                if (statusCode == 200) {
                    createConnectionGet(url).let { listener.onSuccess(it) }
                } else {
                    listener.onError("获取失败")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                listener.onError(e.toString())
            }
        }
    }

    private suspend fun createConnectionGet(url: String) = withContext(Dispatchers.IO) {
        createConnection(url).get()
    }

    private suspend fun statusCode(url: String) = withContext(Dispatchers.IO) {
        createConnection(url).execute().statusCode()
    }
}


