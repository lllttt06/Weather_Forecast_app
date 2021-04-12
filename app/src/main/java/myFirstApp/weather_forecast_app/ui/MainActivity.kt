package myFirstApp.weather_forecast_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import myFirstApp.weather_forecast_app.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Weather_Forecast_app)
        setContentView(R.layout.activity_main)
    }
}
