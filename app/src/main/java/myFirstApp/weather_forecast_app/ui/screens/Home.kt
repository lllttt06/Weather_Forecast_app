package myFirstApp.weather_forecast_app.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myFirstApp.weather_forecast_app.viewModel.MainViewModel
import myFirstApp.weather_forecast_app.viewModel.MainViewModelFactory
import myFirstApp.weather_forecast_app.R
import myFirstApp.weather_forecast_app.model.ApiResponse
import myFirstApp.weather_forecast_app.model.Post
import myFirstApp.weather_forecast_app.myAdapter.RecyclerViewAdapter
import myFirstApp.weather_forecast_app.repository.Repository
import myFirstApp.weather_forecast_app.utils.IconMap
import kotlinx.android.synthetic.main.fragment_home.*

class Home : Fragment() {
    private lateinit var viewModelHome: MainViewModel
    private val myAdapter by lazy { RecyclerViewAdapter() }
    private val timeComparator: Comparator<ApiResponse> = compareBy<ApiResponse> { it.Time.toInt() }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        val myPostHome = Post("home")

        viewModelHome = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModelHome.pushPost(myPostHome)
        viewModelHome.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                val responseSorted = response.body()!!.sortedWith(timeComparator)
                val weatherIcon = IconMap.weatherIconsDetector[responseSorted[0].weather]
                val lunarPhaseIcon = IconMap.lunarPhaseDetector[responseSorted[0].lunarPhaseIcon]

                responseSorted.let { myAdapter.setData(it) }

                if (weatherIcon != null) {
                    imageView1_home.setImageResource(weatherIcon)
                }
                if (lunarPhaseIcon != null) {
                    imageView2_home.setImageResource(lunarPhaseIcon)
                }
                Text1_home.text = responseSorted[0].weatherDesc
                Text2_home.text = "日の出 : " + responseSorted[0].sunrise
                Text3_home.text = "日の入り : " + responseSorted[0].sunset
                Text4_home.text = "月齢 : " + responseSorted[0].lunarPhase
                Text5_home.text = "月の出 : " + responseSorted[0].moonrise
                Text6_home.text = "月の入り : " + responseSorted[0].moonset

            } else {
                //Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewHome = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerViewHome = viewHome.findViewById<RecyclerView>(R.id.recyclerView_home)
        val linearLayoutManager = LinearLayoutManager(viewHome.context)
        val itemDecoration = DividerItemDecoration(viewHome.context, DividerItemDecoration.VERTICAL)

        recyclerViewHome.adapter = myAdapter
        recyclerViewHome.layoutManager = linearLayoutManager
        recyclerViewHome.addItemDecoration(itemDecoration)

        return viewHome
    }

}