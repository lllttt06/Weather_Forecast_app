package com.example.weather_forecast_app.api

import com.example.weather_forecast_app.model.ApiResponse
import com.example.weather_forecast_app.model.Post
import retrofit2.Response
import retrofit2.http.*
interface SimpleAPI {
    @POST("weatherAPI/dynamodbctrl")
    suspend fun pushPost(
        @Body post: Post
    ): Response<List<ApiResponse>>
}