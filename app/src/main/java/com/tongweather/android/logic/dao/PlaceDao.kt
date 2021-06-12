package com.tongweather.android.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.tongweather.android.TongWeatherApplication
import com.tongweather.android.logic.model.Place

object PlaceDao {
//保存地点，通过GSON把Place对象转换成一个JSON字符串，然后用字符窜储存来保存数据
    fun savePlace(place: Place){
        sharedPreferences().edit {
            putString("place",Gson().toJson(place))
        }
    }
//    提取数据，提取时通过GSON将JSON字符串解析为Place对象返回
    fun getSavePlace(): Place {
        val placeJson = sharedPreferences().getString("place","")
        return Gson().fromJson(placeJson, Place::class.java)
    }
//    isPlaceSaved用于判断是否有数据被储存
    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = TongWeatherApplication.context.
            getSharedPreferences("tong_weather",Context.MODE_PRIVATE)
}