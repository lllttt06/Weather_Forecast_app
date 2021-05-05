package myFirstApp.weather_forecast_app.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import myFirstApp.weather_forecast_app.model.ApiResponse
import myFirstApp.weather_forecast_app.model.Post
import myFirstApp.weather_forecast_app.repository.Repository
import kotlinx.coroutines.launch
import myFirstApp.weather_forecast_app.R
import myFirstApp.weather_forecast_app.utils.IconMap
import retrofit2.Response
import kotlin.math.roundToInt

class MainViewModel(private val repository: Repository) : ViewModel() {
    var myResponse: MutableLiveData<Response<List<ApiResponse>>> = MutableLiveData()
    var isResponseSuccessful: MutableLiveData<Boolean> = MutableLiveData()
    var mWeatherIcon: MutableLiveData<Int?> = MutableLiveData()
    var mWeatherDescription: MutableLiveData<String?> = MutableLiveData()
    var mLunarPhaseIcon: MutableLiveData<Int?> = MutableLiveData()
    var mTemperature: MutableLiveData<String?> = MutableLiveData()
    var mTwilightTime: MutableLiveData<String?> = MutableLiveData()
    var mSunrise: MutableLiveData<String?> = MutableLiveData()
    var mSunset: MutableLiveData<String?> = MutableLiveData()
    var mLunarPhase: MutableLiveData<String?> = MutableLiveData()
    var mMoonrise: MutableLiveData<String?> = MutableLiveData()
    var mMoonset: MutableLiveData<String?> = MutableLiveData()


    fun pushPost(post: Post) {
        viewModelScope.launch {
            try {
                val response = repository.pushPost(post)
                adjustResponse(response)
                myResponse.value = response
                isResponseSuccessful.value = true
            } catch (e: Throwable) {
                Log.v("error", e.toString())
                isResponseSuccessful.value = false
            }
        }
    }

    private fun adjustResponse(response: Response<List<ApiResponse>>) {
        val timeComparator: Comparator<ApiResponse> = compareBy { it.Time.toInt() }
        val responseSorted = response.body()!!.sortedWith(timeComparator)
        mWeatherIcon.value = IconMap.weatherIconsDetector[responseSorted[0].weather]
        mWeatherDescription.value = IconMap.weatherDescriptionDetector[responseSorted[0].weather]
        mLunarPhaseIcon.value = IconMap.lunarPhaseDetector[responseSorted[0].lunarPhaseIcon]
        mTemperature.value = convertTemp(responseSorted[0].temp) + "℃"
        mTwilightTime.value = "天文薄明 :" + responseSorted[0].twilightTime
        mSunrise.value = "日の出 :" + responseSorted[0].sunrise
        mSunset.value = "日の入り :" + responseSorted[0].sunset
        mLunarPhase.value = "月齢 :" + responseSorted[0].lunarPhase
        mMoonrise.value = "月の出 :" + responseSorted[0].moonrise
        mMoonset.value = "月の入り :" + responseSorted[0].moonset
    }

    private fun convertTemp(absoluteTemp: String): String {
        val relativeTemp = absoluteTemp.toFloat() - 273.15 // -273.15 is absolute zero
        return relativeTemp.roundToInt().toString()
    }
}