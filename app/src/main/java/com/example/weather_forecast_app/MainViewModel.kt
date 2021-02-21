package com.example.weather_forecast_app

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_forecast_app.model.ApiResponse
import com.example.weather_forecast_app.model.Post
import com.example.weather_forecast_app.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel()  {
    var myResponse: MutableLiveData<Response<List<ApiResponse>>> = MutableLiveData()
    fun pushPost(post: Post) {
        viewModelScope.launch {
            val response = repository.pushPost(post)
            if (response.isSuccessful){
                val items = response.body()
                if (items != null) {
                    var num = 0
                    for (i in 0 until items.count()) {
                        var Time = items[i].Time?: "N/A"
                        Log.v("Time: ", Time)
                        num += 1
                    }
                    Log.v("num=", num.toString())
                }
            }
            myResponse.value = response
        }
    }
}