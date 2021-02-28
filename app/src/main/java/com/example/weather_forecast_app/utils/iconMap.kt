package com.example.weather_forecast_app.utils

import com.example.weather_forecast_app.R

object IconMap {
    val weatherIconsDetector = mapOf (
            "01d" to R.drawable.clear_sky,
            "01n" to R.drawable.clear_sky,
            "02d" to R.drawable.few_clouds,
            "02n" to R.drawable.few_clouds,
            "03d" to R.drawable.scattered_clouds,
            "03n" to R.drawable.scattered_clouds,
            "04d" to R.drawable.broken_clouds,
            "04n" to R.drawable.broken_clouds,
            "09d" to R.drawable.shower_rain,
            "09n" to R.drawable.shower_rain,
            "10d" to R.drawable.rain,
            "10n" to R.drawable.rain,
            "11d" to R.drawable.thunderstorm,
            "11n" to R.drawable.thunderstorm,
            "13d" to R.drawable.snow,
            "13n" to R.drawable.snow,
            "50d" to R.drawable.mist,
            "50n" to R.drawable.mist
    )

    val lunarPhaseDetector = mapOf (
            "0.0" to R.drawable.new_moon,
            "1.0" to R.drawable.waxing_crescent,
            "2.0" to R.drawable.first_quarter,
            "3.0" to R.drawable.waxing_gibbous,
            "4.0" to R.drawable.full_moon,
            "5.0" to R.drawable.waning_gibbous,
            "6.0" to R.drawable.last_quarter,
            "7.0" to R.drawable.waning_crescent
    )
}