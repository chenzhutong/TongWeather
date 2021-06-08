
//创建一个ServiceCreator单例类，为了使用PlaceService接口，还需创建一个Retrofit构建器

package com.tongweather.android.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)//baseUrl()用于指定所有Retrofit请求的根路径
        .addConverterFactory(GsonConverterFactory.create())//addConverterFactory()用于指定Retrofit在解析数据时所使用的解析库
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
    //有了Retrofit对象后，就可以调用它的create方法，传入class类型，创建一个该接口的动态处理对象
    //有了动态处理对象我们就可以随意调用接口中定义的所有方法

    //提供了一个外部可见的create方法，并接受一个class类型的参数，
    // 当外部调用这个方法时，实际上四调用Retrofit.create()，从而建立出相应的service接口的动态代理对象


    inline fun <reified T> create(): T = create(T::class.java)//带有inline和reified的是泛型实化功能
    //泛型实化功能允许我们在泛型函数中获得泛型的实际类型

}