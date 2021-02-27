package com.example.weather_forecast_app.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather_forecast_app.*
//import androidx.recyclerview.widget.RecyclerView
//import com.example.weather_forecast_app.adapter.MyAdapter
import com.example.weather_forecast_app.model.ApiResponse
import com.example.weather_forecast_app.repository.Repository
import com.example.weather_forecast_app.model.Post
import com.example.weather_forecast_app.myAdapter.MyAdapter
import com.example.weather_forecast_app.utils.IconMap.iconsDetector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.Text1_home
import kotlinx.android.synthetic.main.fragment_kamiwari.*
import kotlinx.android.synthetic.main.fragment_zao.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModelHome: MainViewModel
    //private lateinit var viewModelZao: MainViewModel
    //private lateinit var viewModelKamiwari: MainViewModel
    private val myAdapter by lazy { MyAdapter() }
    private val timeComparator: Comparator<ApiResponse> = compareBy<ApiResponse> { it.Time.toInt() }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerview()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        val myPostHome = Post("home")
        //val myPostZao = Post("zao")
        //val myPostKamiwari = Post("kamiwari")

        viewModelHome = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        //viewModelZao = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        //viewModelKamiwari = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModelHome.pushPost(myPostHome)
        //viewModelZao.pushPostZao(myPostZao)
        //viewModelKamiwari.pushPost(myPostKamiwari)

        viewModelHome.myResponse.observe(this, Observer { response ->
            if(response.isSuccessful){
                val responseSorted = response.body()!!.sortedWith(timeComparator)
                val iconLabel2 = iconsDetector[responseSorted[0].weather]

                responseSorted.let { myAdapter.setData(it) }
                if (iconLabel2 != null) {
                    imageView1_home.setImageResource(iconLabel2)
                }
                Text1_home.text = responseSorted[0].weatherDesc
                Text2_home.text = "日の出 : " + responseSorted[0].sunrise
                Text3_home.text = "日の入り : " + responseSorted[0].sunset
                Text4_home.text = "月齢 : " + responseSorted[0].lunarPhase
                Text5_home.text = "月の出 : " + responseSorted[0].moonrise
                Text6_home.text = "月の入り : " + responseSorted[0].moonset

            }else {
                Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
            }
        })
        /*
        viewModelZao.myResponseZao.observe(this, Observer { responseZao ->
            if(responseZao.isSuccessful){
                val responseZaoSorted = responseZao.body()!!.sortedWith(timeComparator)
                val iconLabelZao2 = iconsDetector[responseZaoSorted[0].weather]
                responseZaoSorted.let { myAdapter.setData(it) }
                Log.d("MainActivity", "response:${responseZaoSorted[0].weatherDesc}")
                if (iconLabelZao2 != null) {
                    imageView1_zao.setImageResource(iconLabelZao2)
                }
                Text1_zao.text = "broken clouds" //responseZaoSorted[0].weatherDesc
                Text2_zao.text = "日の出 : " + responseZaoSorted[0].sunrise
                Text3_zao.text = "日の入り : " + responseZaoSorted[0].sunset
                Text4_zao.text = "月齢 : " + responseZaoSorted[0].lunarPhase
                Text5_zao.text = "月の出 : " + responseZaoSorted[0].moonrise
                Text6_zao.text = "月の入り : " + responseZaoSorted[0].moonset

            }else {
                Toast.makeText(this, responseZao.code(), Toast.LENGTH_SHORT).show()
            }
        })

        viewModelKamiwari.myResponse.observe(this, Observer { response ->
            if(response.isSuccessful){
                val responseSorted = response.body()!!.sortedWith(timeComparator)
                val iconLabel2 = iconsDetector[responseSorted[0].weather]

                responseSorted.let { myAdapter.setData(it) }
                Log.d("MainActivity", "response:$responseSorted")
                if (iconLabel2 != null) {
                    imageView1_home.setImageResource(iconLabel2)
                }
                Text1_kamiwari.text = responseSorted[0].weatherDesc
                Text2_kamiwari.text = "日の出 : " + responseSorted[0].sunrise
                Text3_kamiwari.text = "日の入り : " + responseSorted[0].sunset
                Text4_kamiwari.text = "月齢 : " + responseSorted[0].lunarPhase
                Text5_kamiwari.text = "月の出 : " + responseSorted[0].moonrise
                Text6_kamiwari.text = "月の入り : " + responseSorted[0].moonset

            }else {
                Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
            }
        })*/
    }

    private fun setupRecyclerview() {
        recyclerView_home.adapter = myAdapter
        recyclerView_home.layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView_home.addItemDecoration(itemDecoration)
    }


}
