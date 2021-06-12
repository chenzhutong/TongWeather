package com.tongweather.android.logic.model
/** 封装realtime（当前时间）和daily（未来几天） **/
data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)
