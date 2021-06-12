//获取未来几天天气信息的JSON的数据模型类

package com.tongweather.android.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*


data class DailyResponse(val status: String, val result: Result){

    data class Result(val daily: Daily)

    data class Daily(val temperature: List<Temperature>, val skycon: List<Skycon>,
                @SerializedName("life_index")val lifeIndex: LifeIndex)

    data class Temperature(val max: Float, val min: Float)

    data class Skycon(val value: String, val date: Date)

    data class LifeIndex(val coldRisk: List<LifeDescription>, val carWashing: List<LifeDescription>,
                         val ultraviolet: List<LifeDescription>, val dressing: List<LifeDescription>)

    data class LifeDescription(val desc: String)
}

/**
 * daily：包含当前地区未来几天
 * lifeIndex：生活指数
 * coldRisk：感冒指数
 * carWashing：洗车指数
 * ultraviolet：紫外线指数
 * dressing：穿衣指数
 * */