package myFirstApp.weather_forecast_app.repository

import myFirstApp.weather_forecast_app.api.RetrofitInstance
import myFirstApp.weather_forecast_app.model.ApiResponse
import myFirstApp.weather_forecast_app.model.Post
import retrofit2.Response

class Repository {
    suspend fun pushPost(post: Post): Response<List<ApiResponse>> {
        return RetrofitInstance.api.pushPost(post)
    }
}