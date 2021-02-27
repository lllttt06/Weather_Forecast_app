package com.example.weather_forecast_app.screens

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
import com.example.weather_forecast_app.MainViewModel
import com.example.weather_forecast_app.MainViewModelFactory
import com.example.weather_forecast_app.R
import com.example.weather_forecast_app.model.ApiResponse
import com.example.weather_forecast_app.model.Post
import com.example.weather_forecast_app.myAdapter.MyAdapter
import com.example.weather_forecast_app.repository.Repository
import com.example.weather_forecast_app.utils.IconMap
import kotlinx.android.synthetic.main.fragment_kamiwari.*

class Kamiwari : Fragment() {
    private lateinit var viewModelKamiwari: MainViewModel
    private val myAdapter by lazy { MyAdapter() }
    private val timeComparator: Comparator<ApiResponse> = compareBy<ApiResponse> { it.Time.toInt() }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setupRecyclerview()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        val myPostKamiwari = Post("kamiwari")

        viewModelKamiwari = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModelKamiwari.pushPostKamiwari(myPostKamiwari)
        viewModelKamiwari.myResponseKamiwari.observe(this, Observer { responseKamiwari ->
            if(responseKamiwari.isSuccessful){
                val responseKamiwariSorted = responseKamiwari.body()!!.sortedWith(timeComparator)
                val iconLabelKamiwari2 = IconMap.iconsDetector[responseKamiwariSorted[0].weather]
                responseKamiwariSorted.let { myAdapter.setData(it) }
                Log.d("MainActivity", "response:${responseKamiwariSorted[0].weatherDesc}")
                if (iconLabelKamiwari2 != null) {
                    imageView1_kamiwari.setImageResource(iconLabelKamiwari2)
                }
                Text1_kamiwari.text = responseKamiwariSorted[0].weatherDesc
                Text2_kamiwari.text = "日の出 : " + responseKamiwariSorted[0].sunrise
                Text3_kamiwari.text = "日の入り : " + responseKamiwariSorted[0].sunset
                Text4_kamiwari.text = "月齢 : " + responseKamiwariSorted[0].lunarPhase
                Text5_kamiwari.text = "月の出 : " + responseKamiwariSorted[0].moonrise
                Text6_kamiwari.text = "月の入り : " + responseKamiwariSorted[0].moonset

            }else {
                //Toast.makeText(this, responseZao.code(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewKamiwari = inflater.inflate(R.layout.fragment_kamiwari, container, false)
        val recyclerViewKamiwari = viewKamiwari.findViewById<RecyclerView>(R.id.recyclerView_kamiwari)
        val linearLayoutManager = LinearLayoutManager(viewKamiwari.context)
        val itemDecoration = DividerItemDecoration(viewKamiwari.context, DividerItemDecoration.VERTICAL)

        recyclerViewKamiwari.adapter = myAdapter
        recyclerViewKamiwari.layoutManager = linearLayoutManager
        recyclerViewKamiwari.addItemDecoration(itemDecoration)

        return viewKamiwari
    }
}