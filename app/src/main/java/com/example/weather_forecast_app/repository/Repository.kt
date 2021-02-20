package com.example.weather_forecast_app.repository

import com.example.weather_forecast_app.api.RetrofitInstance
import com.example.weather_forecast_app.model.ApiResponse
import com.example.weather_forecast_app.model.Post
import retrofit2.Response

class Repository {
    suspend fun pushPost(post: Post): Response<List<ApiResponse>> {
        return RetrofitInstance.api.pushPost(post)
    }
}