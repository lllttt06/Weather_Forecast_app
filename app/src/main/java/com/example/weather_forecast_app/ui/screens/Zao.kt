package com.example.weather_forecast_app.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_forecast_app.viewModel.MainViewModel
import com.example.weather_forecast_app.viewModel.MainViewModelFactory
import com.example.weather_forecast_app.R
import com.example.weather_forecast_app.model.ApiResponse
import com.example.weather_forecast_app.model.Post
import com.example.weather_forecast_app.myAdapter.RecyclerViewAdapter
import com.example.weather_forecast_app.repository.Repository
import com.example.weather_forecast_app.utils.IconMap
import kotlinx.android.synthetic.main.fragment_zao.*

class Zao : Fragment() {
    private lateinit var viewModelZao: MainViewModel
    private val myAdapter by lazy { RecyclerViewAdapter() }
    private val timeComparator: Comparator<ApiResponse> = compareBy<ApiResponse> { it.Time.toInt() }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        val myPostZao = Post("zao")

        viewModelZao = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModelZao.pushPostZao(myPostZao)
        viewModelZao.myResponseZao.observe(this, Observer { responseZao ->
            if(responseZao.isSuccessful){
                val responseZaoSorted = responseZao.body()!!.sortedWith(timeComparator)
                val iconLabelZao2 = IconMap.weatherIconsDetector[responseZaoSorted[0].weather]
                responseZaoSorted.let { myAdapter.setData(it) }
                Log.d("MainActivity", "response:${responseZaoSorted[0].weatherDesc}")
                if (iconLabelZao2 != null) {
                    imageView1_zao.setImageResource(iconLabelZao2)
                }
                Text1_zao.text = responseZaoSorted[0].weatherDesc
                Text2_zao.text = "日の出 : " + responseZaoSorted[0].sunrise
                Text3_zao.text = "日の入り : " + responseZaoSorted[0].sunset
                Text4_zao.text = "月齢 : " + responseZaoSorted[0].lunarPhase
                Text5_zao.text = "月の出 : " + responseZaoSorted[0].moonrise
                Text6_zao.text = "月の入り : " + responseZaoSorted[0].moonset

            }else {
                //Toast.makeText(this, responseZao.code(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewZao = inflater.inflate(R.layout.fragment_zao, container, false)
        val recyclerViewZao = viewZao.findViewById<RecyclerView>(R.id.recyclerView_zao)
        val linearLayoutManager = LinearLayoutManager(viewZao.context)
        val itemDecoration = DividerItemDecoration(viewZao.context, DividerItemDecoration.VERTICAL)

        recyclerViewZao.adapter = myAdapter
        recyclerViewZao.layoutManager = linearLayoutManager
        recyclerViewZao.addItemDecoration(itemDecoration)

        return viewZao
    }
}