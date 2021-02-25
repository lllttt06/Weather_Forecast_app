package com.example.weather_forecast_app.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
import com.example.weather_forecast_app.MainViewModel
//import com.example.weather_forecast_app.adapter.MyAdapter
import com.example.weather_forecast_app.MainViewModelFactory
import com.example.weather_forecast_app.R
import com.example.weather_forecast_app.model.ApiResponse
import com.example.weather_forecast_app.repository.Repository
import com.example.weather_forecast_app.model.Post
import com.example.weather_forecast_app.myAdapter.MyAdapter
import com.example.weather_forecast_app.utils.IconMap.iconsDetector
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private val myAdapter by lazy { MyAdapter() }
    private val timeComparator: Comparator<ApiResponse> = compareBy<ApiResponse> { it.Time.toInt() }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerview()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        val myPost = Post("Home")

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.pushPost(myPost)
        viewModel.myResponse.observe(this, Observer { response ->
            if(response.isSuccessful){
                val responseSorted = response.body()!!.sortedWith(timeComparator)
                val iconLabel2 = iconsDetector[responseSorted[0].weather]

                responseSorted.let { myAdapter.setData(it) }
                Log.d("MainActivity", "response:$responseSorted")
                if (iconLabel2 != null) {
                    imageView1.setImageResource(iconLabel2)
                }
                Text1.text = "Sunrise: " + responseSorted[0].sunrise
                Text2.text = "Sunset: " + responseSorted[0].sunset
                Text3.text = "Moonrise: " + responseSorted[0].moonrise
                Text4.text = "Moonset: " + responseSorted[0].moonset

            }else {
                Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerview() {
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)
    }


}
