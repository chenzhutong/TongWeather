//网络层相关代码
//访问彩云天气城市搜索API的Retrofit接口

package com.tongweather.android.logic.network

//import android.telecom.Call引用报错
import retrofit2.Call
import com.tongweather.android.TongWeatherApplication
import com.tongweather.android.logic.model.PlaceResponse
import retrofit2.http.GET//当调用searchPlaces()方法的时候，Retrofit会自动发起一条GET请求，去访问@GET注释中的地址
import retrofit2.http.Query
/** api:"https://api.caiyunapp.com/v2/place?query=北京&token={token}&lang=zh_CN" **/
interface PlaceService {
    @GET("v2/place?token=${TongWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
    //搜查城市数据的api中只有query这个参数需要动态指定，所以使用@Query注解的方式实现
    //返回值声明为Call<PlaceResponse>,这样Retrofit就会将服务器返回的JSON数据自动解析成PlaceResponse单例类
}