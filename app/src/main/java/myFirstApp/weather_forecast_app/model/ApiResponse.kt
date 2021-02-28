package myFirstApp.weather_forecast_app.model


data class ApiResponse(
    var temp: String,
    var clouds: String,
    var humidity: String,
    var Time: String,
    var wind: String,
    var weather: String,
    var sunrise: String,
    var sunset: String,
    var moonrise: String,
    var moonset: String,
    var lunarPhase:String,
    var lunarPhaseIcon: String,
    var weatherDesc: String
)
