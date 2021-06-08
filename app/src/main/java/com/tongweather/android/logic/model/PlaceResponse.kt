
//数据模型，按照搜索城市数据接口返回的JSON格式来定义

package com.tongweather.android.logic.model

import com.google.gson.annotations.SerializedName//该注解方式让JSON字段与kotlin字段之间建立映射关系

data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(val name: String, val location: Location,
                 @SerializedName("formatted_address") val address: String)

data class Location(val lng: String, val lat: String)