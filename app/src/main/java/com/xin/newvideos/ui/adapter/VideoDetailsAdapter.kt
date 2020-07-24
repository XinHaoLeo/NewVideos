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

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xin.newvideos.R
import com.xin.newvideos.http.bean.PlayerType

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
 *@date : 2020/7/24 11:34
 *@since : xinxiniscool@gmail.com
 *@desc :
 */
class VideoDetailsAdapter(var list: ArrayList<PlayerType>) :
    BaseProviderMultiAdapter<PlayerType>(list) {

    init {
        addItemProvider(TextItemProvider())
        addItemProvider(GridViewItemProvider())
    }

    override fun getItemType(data: List<PlayerType>, position: Int): Int {
        Log.d("Leo", "getItemType:$position")
        Log.d("Leo", "getItemType:size=${list.size}")
        return if (position % 2 == 0) PlayerType.TEXT else PlayerType.GRID_VIEW
    }

}

class TextItemProvider : BaseItemProvider<PlayerType>() {

    override val itemViewType: Int = PlayerType.TEXT

    override val layoutId: Int = R.layout.item_video_play_type_title

    override fun convert(helper: BaseViewHolder, item: PlayerType) {
        helper.setText(R.id.tvPlayerTypeName, item.typeName)
    }
}

class GridViewItemProvider : BaseItemProvider<PlayerType>() {

    override val itemViewType: Int = PlayerType.GRID_VIEW
    override val layoutId: Int = R.layout.item_video_play_type_list

    override fun convert(helper: BaseViewHolder, item: PlayerType) {
        val rvVideoCount = helper.getView<RecyclerView>(R.id.rvVideoCount)
        val videoCountAdapter = VideoCountAdapter(R.layout.item_video_count, item.videoCount)
        with(rvVideoCount) {
            layoutManager = StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL)
            adapter = videoCountAdapter
        }
    }
}
