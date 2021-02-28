package myFirstApp.weather_forecast_app.api

import myFirstApp.weather_forecast_app.model.ApiResponse
import myFirstApp.weather_forecast_app.model.Post
import retrofit2.Response
import retrofit2.http.*
interface SimpleAPI {
    @POST("weatherAPI/dynamodbctrl")
    suspend fun pushPost(
        @Body post: Post
    ): Response<List<ApiResponse>>
}