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

import android.content.Intent
import android.os.CountDownTimer
import androidx.core.content.ContextCompat
import com.xin.newvideos.R
import com.xin.newvideos.app.MyApp
import com.xin.newvideos.base.BaseSimpleActivity
import com.xin.newvideos.ext.setOnClickNoRepeat
import com.xin.newvideos.util.StatusBarUtils
import com.xin.newvideos.widget.ModelSVG
import kotlinx.android.synthetic.main.activity_splash.*

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
 *@date : 2020/7/4 9:47
 *@since : lightingxin@qq.com
 *@desc :
 */
class SplashActivity : BaseSimpleActivity() {

    private lateinit var countDownTimer: CountDownTimer


    override fun initLayoutView(): Int = R.layout.activity_splash

    override fun initEvent() {
        StatusBarUtils.setStatusColor(window, ContextCompat.getColor(this, R.color.white), 0.0f)
    }

    override fun initData() {
        if (MyApp.getInstance().isFirstRun) {
            setSvg(ModelSVG.values()[4])
        } else {
            jumpToMainActivity()
        }
        countDownTimer = object : CountDownTimer(5000, 1000) {
            override fun onFinish() {
                jumpToMainActivity()
            }

            override fun onTick(millisUntilFinished: Long) {
                val text = "点击跳过\r\r" + millisUntilFinished / 1000
                tvJump.text = text
            }
        }
        //启动倒计时
        countDownTimer.start()

        tvJump.setOnClickNoRepeat {
            jumpToMainActivity()
        }
    }


    private fun setSvg(modelSvg: ModelSVG) {
        animatedSvgView.setGlyphStrings(*modelSvg.glyphs)
        animatedSvgView.setFillColors(modelSvg.colors)
        animatedSvgView.setViewportSize(modelSvg.width, modelSvg.height)
        animatedSvgView.setTraceResidueColor(0x32000000)
        animatedSvgView.setTraceColors(modelSvg.colors)
        animatedSvgView.rebuildGlyphData()
        animatedSvgView.start()
    }

    private fun jumpToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        MyApp.getInstance().isFirstRun = false
        finish()
    }


    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

}