//作为仓库层的统一封装入口

package com.tongweather.android.logic

import androidx.lifecycle.liveData
import com.tongweather.android.logic.dao.PlaceDao
import com.tongweather.android.logic.model.Place
import com.tongweather.android.logic.model.Weather
import com.tongweather.android.logic.network.TongWeatherNetwork

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext


object Repository {
    //Dispatchers.IO可以让代码块的代码都运行在子线程中
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {

            val placeResponse = TongWeatherNetwork.searchPlaces(query)
            if (placeResponse.status=="ok"){
                val places = placeResponse.places
                Result.success(places)//Result.success包装获取的城市数据列表
            }
            else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }

    }
//    refreshWeather用来刷新天气信息
    fun refreshWeather(lng: String,lat: String) = fire(Dispatchers.IO){


            /** coroutineScope创建协程作用域，配合async **/
            /** coroutineScope创建协程作用域，配合async **/
    coroutineScope {
                /** 获取实时天气 **/
                /** 获取实时天气 **/
                val deferredRealtime = async {
                    TongWeatherNetwork.getRealtimeWeather(lng, lat)
                }
                /** 获取未来几天的天气信息 **/
                /** 获取未来几天的天气信息 **/
                val deferredDaily = async {
                    TongWeatherNetwork.getDailyWeather(lng, lat)
                }
                /** 使用async的await方法可以保证只有两个网络请求成功之后，才进一步执行代码，
                 * async必须在协程作用域才可以调用 **/
                /** 使用async的await方法可以保证只有两个网络请求成功之后，才进一步执行代码，
                 * async必须在协程作用域才可以调用 **/
                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()
                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok"){
                    val weather = Weather(realtimeResponse.result.realtime,dailyResponse.result.daily)
                    Result.success(weather)
                }else{
                    Result.failure(
                        RuntimeException(
                            "realtime response status is ${realtimeResponse.status}"+
                                    "daily response status is ${dailyResponse.status}"
                        )
                    )
                }
            }
        }

    //placeDao的仓库层实现
    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavePlace() = PlaceDao.getSavePlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

    }

/** 这里的suspend表示所有传入的Lambda表达式中的代码也是拥有挂起函数的上下文 **/
    private fun <T> fire(context: CoroutineContext,block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            }catch (e: Exception){
                Result.failure<T>(e)
            }
            emit(result)        //类似于调用LiveData的setValue()方法来通知数据变化，将包装的结果发送出去

        }
