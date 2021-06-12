package com.tongweather.android.logic.model

import com.google.gson.annotations.SerializedName
//把数据模型类定义在RealtimeResponse内可以防止出现和其他接口的数据模型类有同名冲突的情况
data class RealtimeResponse(val status: String,val result: Result){
    data class Result(val realtime: Realtime)

    data class Realtime(val skycon: String, val temperature: Float,
                @SerializedName("air_quality") val airQuality: AirQuality)
    //air_quality是JSON中的键，JSON中的一些字段的命名方式与kotlin的命名方式不一致，JSON有下划线
    // 因此使用@SerializedName注解可以让JSON与kotlin建立映射关系

    data class AirQuality(val aqi: AQI)

    data class AQI(val chn: Float)
}
/**
 * 英文单词注解：
 * status：状态
 * result：结果
 * realtime：实时
 * skycon：天气情况
 * temperature：温度
 * airQuality：空气质量指数
 * */