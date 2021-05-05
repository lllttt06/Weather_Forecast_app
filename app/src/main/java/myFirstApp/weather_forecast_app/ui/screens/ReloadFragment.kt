package myFirstApp.weather_forecast_app.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_reload.view.*
import myFirstApp.weather_forecast_app.R
import myFirstApp.weather_forecast_app.ui.ViewPagerFragment

class ReloadFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.reload_button.setOnClickListener {
            val viewPagerFragment = ViewPagerFragment()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.fragment_container, viewPagerFragment)
            transaction.commit()
        }
    }

}