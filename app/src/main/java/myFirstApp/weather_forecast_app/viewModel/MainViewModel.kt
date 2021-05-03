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
    var isResponseSuccessful: MutableLiveData<Boolean> = MutableLiveData()

    fun pushPost(post: Post) {
        viewModelScope.launch {
            try {
                val response = repository.pushPost(post)
                myResponse.value = response
                isResponseSuccessful.value = true
            } catch (e: Throwable) {
                Log.v("error", e.toString())
                isResponseSuccessful.value = false
            }
        }
    }
}