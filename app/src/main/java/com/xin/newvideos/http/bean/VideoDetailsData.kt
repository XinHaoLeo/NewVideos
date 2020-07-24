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

package com.xin.newvideos.http.bean

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
 * @date : 2020/7/19 23:13
 * @desc :
 * @since : xinxiniscool@gmail.com
 */
class VideoDetailsData {
    var title: String = ""
    var imgUrl: String = ""
    var imgLinkUrl: String = ""
    var directorName: String = ""
    var score: String = ""
    var pop: String = ""
    var actorName: String = ""
    var typeName: String = ""
    var area: String = ""
    var language: String = ""
    var year: String = ""
    var summary: String = ""
    lateinit var playerType: ArrayList<PlayerType>
    override fun toString(): String {
        return "VideoDetailsData(title='$title', imgUrl='$imgUrl', imgLinkUrl='$imgLinkUrl', directorName='$directorName', score='$score', pop='$pop', actorName='$actorName', typeName='$typeName', area='$area', language='$language', year='$year', summary='$summary', playerType=$playerType)"
    }
}

class PlayerType {
    companion object {
        const val TEXT = 1
        const val GRID_VIEW = 2
    }

    var typeName: String = ""
    lateinit var videoCount: ArrayList<VideoCount>
    override fun toString(): String {
        return "PlayerType(typeName='$typeName', videoCount=$videoCount)"
    }
}

class VideoCount {
    var countTitle: String = ""
    var countUrl: String = ""
    override fun toString(): String {
        return "VideoCount(countTitle='$countTitle', countUrl='$countUrl')"
    }
}