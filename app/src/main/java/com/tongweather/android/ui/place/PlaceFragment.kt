package com.tongweather.android.ui.place

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tongweather.android.R
import com.tongweather.android.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceFragment : Fragment() {
    //lazy懒加载技术，懒加载不用担心什么时候初始化和是否为空值
    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        加载fragment_place布局
        return inflater.inflate(R.layout.fragment_place,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        对储存状态进行判断和读取
        if (viewModel.isSavedPlace()) {
            val place = viewModel.getSavePlace()
            val intent = Intent(context,WeatherActivity::class.java).apply {
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat",place.location.lat)
                putExtra("place_name",place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }

//设置layoutmanager，设置recyclerview的排列方式
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
//设置适配器
        adapter = PlaceAdapter(this,viewModel.placeList)//使用placeList作为数据源
        recyclerView.adapter = adapter

//监听输入框内容发生变化searchPlaceEdit.addTextChangedListener
        searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()//获取输入框的文本

            if (content.isNotEmpty()){
                viewModel.searchPlaces(content)
            }else{
                recyclerView.visibility = View.GONE//设置为隐藏（GONE）
                bgImageView.visibility = View.VISIBLE//设置为显示（VISIBLE）
                viewModel.placeList.clear()//清除缓存
                adapter.notifyDataSetChanged()//刷新适配器
            }

//            observe第一个参数传入this报错Fragment与Fragment中的View的周期不一样
            viewModel.placeLiveData.observe(viewLifecycleOwner, Observer{ result ->
                val places = result.getOrNull()//获取索引所在的元素
                if (places != null){
                    recyclerView.visibility = View.VISIBLE
                    bgImageView.visibility = View.GONE
                    viewModel.placeList.clear()
                    viewModel.placeList.addAll(places)//把获取的全部数据添加到缓存去
                    adapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(activity,"未能查询到任何地点",Toast.LENGTH_SHORT).show()
                    result.exceptionOrNull()?.printStackTrace()//把具体的异常打印出来
                }

            })
        }
    }
}