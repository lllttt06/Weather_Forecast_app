package com.example.weather_forecast_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
import com.example.weather_forecast_app.MainViewModel
//import com.example.weather_forecast_app.adapter.MyAdapter
import com.example.weather_forecast_app.MainViewModelFactory
import com.example.weather_forecast_app.R
import com.example.weather_forecast_app.repository.Repository
import com.example.weather_forecast_app.model.Post
import com.example.weather_forecast_app.myAdapter.MyAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    // RecyclerView 本体、および、LayoutManager と Adapter
    //private lateinit var recyclerView: RecyclerView
    private val myAdapter by lazy { MyAdapter() }
    //private lateinit var adapter: RecyclerView.Adapter<*>
    // Adapter にセットするデータ (1～100)
    //private val data = IntArray(48) { it + 1 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val myPost = Post("Scan")
        viewModel.pushPost(myPost)
        setupRecyclerview()
        viewModel.myResponse.observe(this, Observer { response ->
            if(response.isSuccessful){
                response.body()?.let { myAdapter.setData(it) }
            }else {
                Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
            }
        })


        /*
        layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(data)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).also {
            it.layoutManager = layoutManager
            it.adapter = adapter
        }*/
    }

    private fun setupRecyclerview() {

        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        /*
        Log.d("MainActivity", "setupRecyclerview is called")
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).also {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = myAdapter
        }*/
    }

}
