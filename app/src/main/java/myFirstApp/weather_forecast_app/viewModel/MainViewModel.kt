package myFirstApp.weather_forecast_app.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import myFirstApp.weather_forecast_app.model.ApiResponse
import myFirstApp.weather_forecast_app.model.Post
import myFirstApp.weather_forecast_app.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel()  {
    var myResponse: MutableLiveData<Response<List<ApiResponse>>> = MutableLiveData()
    var myResponseZao: MutableLiveData<Response<List<ApiResponse>>> = MutableLiveData()
    var myResponseKamiwari: MutableLiveData<Response<List<ApiResponse>>> = MutableLiveData()
    var myResponseGobansyo: MutableLiveData<Response<List<ApiResponse>>> = MutableLiveData()

    fun pushPost(post: Post) {
        viewModelScope.launch {
            try {
                val response = repository.pushPost(post)
                myResponse.value = response
            } catch (e: Throwable) {
                Log.v("error", e.toString())
            }
        }
    }

    fun pushPostZao(post: Post) {
        viewModelScope.launch {
            try {
                val responseZao = repository.pushPost(post)
                myResponseZao.value = responseZao
            } catch (e: Throwable) {
                Log.v("error", e.toString())
            }
        }
    }

    fun pushPostKamiwari(post: Post) {
        viewModelScope.launch {
            try {
                val responseKamiwari = repository.pushPost(post)
                myResponseKamiwari.value = responseKamiwari
            } catch (e: Throwable) {
                Log.v("error", e.toString())
            }
        }
    }

    fun pushPostGobansyo(post: Post) {
        viewModelScope.launch {
            try {
                val responseGobansyo = repository.pushPost(post)
                myResponseGobansyo.value = responseGobansyo
            } catch (e: Throwable) {
                Log.v("error", e.toString())
            }
        }
    }


}