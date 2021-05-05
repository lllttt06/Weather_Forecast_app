package myFirstApp.weather_forecast_app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import myFirstApp.weather_forecast_app.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Weather_Forecast_app)
        setContentView(R.layout.activity_main)

        val viewPagerFragment = ViewPagerFragment()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction
            .add(R.id.fragment_container, viewPagerFragment)
            .commit()
    }
}
