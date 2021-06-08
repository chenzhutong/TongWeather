//作为仓库层的统一封装入口

package com.tongweather.android.logic

import androidx.lifecycle.liveData
import com.tongweather.android.logic.model.Place
import com.tongweather.android.logic.network.TongWeatherNetwork

import kotlinx.coroutines.Dispatchers


object Repository {
    //Dispatchers.IO可以让代码块的代码都运行在子线程中
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = TongWeatherNetwork.searchPlaces(query)
            if (placeResponse.status=="ok"){
                val places = placeResponse.places
                Result.success(places)//Result.success包装获取的城市数据列表
            }
            else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e: Exception){
            Result.failure<List<Place>>(e)//Result.failure包装一个异常信息
        }
        emit(result)//类似于调用LiveData的setValue()方法来通知数据变化，将包装的结果发送出去
    }
}