//统一的网络数据源访问入口，对所有网络的api进行封装

package com.tongweather.android.logic.network

import retrofit2.await
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object TongWeatherNetwork {

    private val placeService = ServiceCreator.create<PlaceService>()
    //使用ServiceCreator创建里一个PlaceService接口的动态处理对象

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()
    //定义了一个searchPlaces函数，并在这里调用刚刚在PlaceService接口定义的searchPlaces方法，用于发起搜索城市数据的请求

    //await()是一个挂起函数（suspend），
    // 将await定义成call<T>的扩展函数，这样所有返回值为call类型的Retrofit网络请求接口就可以直接调用await（）函数
    private suspend fun <T> Call<T>.await(): T{
        //suspendCoroutine主要作用是将当前协程立即挂起，然后在一个普通的线程中执行Lambda表达式中的代码
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T>{
                //enqueue方法让enqueue发起网络请求
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    //resume和resumeWithException方法可以让协程恢复执行
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}
