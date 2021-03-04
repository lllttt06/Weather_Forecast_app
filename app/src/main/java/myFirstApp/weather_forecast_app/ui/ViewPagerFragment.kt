package myFirstApp.weather_forecast_app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import myFirstApp.weather_forecast_app.R
import myFirstApp.weather_forecast_app.myAdapter.ViewPagerAdapter
import myFirstApp.weather_forecast_app.ui.screens.Home
import myFirstApp.weather_forecast_app.ui.screens.Kamiwari
import myFirstApp.weather_forecast_app.ui.screens.Zao
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_view_pager.*
import kotlinx.android.synthetic.main.fragment_view_pager.view.*
import myFirstApp.weather_forecast_app.ui.screens.Gobansyo

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf<Fragment>(
            Home(),
            Zao(),
            Kamiwari(),
            Gobansyo()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        view.viewPager.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val locationList = listOf<String>("仙台", "蔵王山", "神割崎", "御番所公園")
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = locationList[position]
        }.attach()
    }

}