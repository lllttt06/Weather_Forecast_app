package com.example.weather_forecast_app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weather_forecast_app.R
import com.example.weather_forecast_app.myAdapter.ViewPagerAdapter
import com.example.weather_forecast_app.ui.screens.Home
import com.example.weather_forecast_app.ui.screens.Kamiwari
import com.example.weather_forecast_app.ui.screens.Zao
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_view_pager.*
import kotlinx.android.synthetic.main.fragment_view_pager.view.*

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
            Kamiwari()
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
        val locationList = listOf<String>("Home", "Zao", "Kamiwari")
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = locationList[position]
        }.attach()
    }

}