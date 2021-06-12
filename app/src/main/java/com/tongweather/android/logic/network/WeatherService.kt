/** 用于访问天气信息api的Retrofit接口 **/
package com.tongweather.android.logic.network

import com.tongweather.android.TongWeatherApplication
import com.tongweather.android.logic.model.DailyResponse
import com.tongweather.android.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path//@Path注解动态向请求接口中动态传入经纬度坐标

interface WeatherService {
/** https://api.caiyunapp.com/v2.5/{token}/{lng}/{lat}/realtime.json **/
    @GET("v2.5/${TongWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String) : Call<RealtimeResponse>

/**  https://api.caiyunapp.com/v2.5/{token}/{lng}/{lat}/daily.json **/
    @GET("v2.5/${TongWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String) : Call<DailyResponse>
}