package myFirstApp.weather_forecast_app.utils

import myFirstApp.weather_forecast_app.R

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

    val weatherDescriptionDetector = mapOf(
            "01d" to "快晴",
            "01n" to "快晴",
            "02d" to "晴れ",
            "02n" to "晴れ",
            "03d" to "くもり",
            "03n" to "くもり",
            "04d" to "曇天",
            "04n" to "曇天",
            "09d" to "雨",
            "09n" to "雨",
            "10d" to "降雨",
            "10n" to "降雨",
            "11d" to "雷雨",
            "11n" to "雷雨",
            "13d" to "雪",
            "13n" to "雪",
            "50d" to "霧",
            "50n" to "霧"
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