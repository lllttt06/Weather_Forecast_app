package com.example.weather_forecast_app.model

import java.util.*

data class ApiResponse(
    var temp: String,
    var location: String,
    var clouds: String,
    var visibility: String,
    var time: String,
    var wind: String,
    var weather: String
)
