
//全局获取context,和令牌值

package com.tongweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class TongWeatherApplication : Application(){
    companion object{
        const val TOKEN = "MuSB3EXLScpFDeYz"//全局设定令牌值
        @SuppressLint("StaticFieldLeak")//@SuppressLint用于忽略警告提示，"StaticFieldLeak"这是内存泄露的风险警告
        lateinit var context: Context//全局获取context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext//applicationContext指获取应用程序的context
    }
}