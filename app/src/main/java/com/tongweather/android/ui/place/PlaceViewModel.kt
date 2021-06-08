//ViewModel层相当于逻辑层和UI层之间的桥梁，ViewModel通常和Activity或Fragment一一对应

package com.tongweather.android.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tongweather.android.logic.Repository
import com.tongweather.android.logic.model.Place

class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()//用于观察数据变化

    val placeList = ArrayList<Place>()//这个集合用于对界面上显示的数据进行缓存

    //switchMap(){}的第一个参数(searchLiveData)是LiveData对象，该方法会对他进行观察，
    // 第二个参数{花括号里}是一个转换函数，必须在这个转换函数里返回一个LiveData对象
    val placeLiveData = Transformations.switchMap(searchLiveData){ query ->
        Repository.searchPlaces(query)
        //Transformations.switchMap()的switchMap()的工作原理是将转换函数中返回的LiveData对象转换成另一个可观察的LivaData对象
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
        //该方法得到返回值并返回到searchLiveData中
    }
}