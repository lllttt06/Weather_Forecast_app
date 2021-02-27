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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
