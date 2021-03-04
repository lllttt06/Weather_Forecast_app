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
import kotlinx.android.synthetic.main.fragment_gobansyo.*
import myFirstApp.weather_forecast_app.viewModel.MainViewModel
import myFirstApp.weather_forecast_app.viewModel.MainViewModelFactory
import myFirstApp.weather_forecast_app.R
import myFirstApp.weather_forecast_app.model.ApiResponse
import myFirstApp.weather_forecast_app.model.Post
import myFirstApp.weather_forecast_app.myAdapter.RecyclerViewAdapter
import myFirstApp.weather_forecast_app.repository.Repository
import myFirstApp.weather_forecast_app.utils.IconMap
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.imageView1_home
import kotlin.math.roundToInt

class Gobansyo : Fragment() {
    private lateinit var viewModelGobansyo: MainViewModel
    private val myAdapter by lazy { RecyclerViewAdapter() }
    private val timeComparator: Comparator<ApiResponse> = compareBy<ApiResponse> { it.Time.toInt() }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        val myPostHome = Post("home")

        viewModelGobansyo = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModelGobansyo.pushPost(myPostHome)
        viewModelGobansyo.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                val responseGobansyoSorted = response.body()!!.sortedWith(timeComparator)
                val weatherIcon = IconMap.weatherIconsDetector[responseGobansyoSorted[0].weather]
                val weatherDescription = IconMap.weatherDescriptionDetector[responseGobansyoSorted[0].weather]
                val lunarPhaseIcon = IconMap.lunarPhaseDetector[responseGobansyoSorted[0].lunarPhaseIcon]
                val temp = convertTemp(responseGobansyoSorted[0].temp)

                responseGobansyoSorted.let { myAdapter.setData(it) }

                if (weatherIcon != null) {
                    imageView1_gobansyo.setImageResource(weatherIcon)
                }
                if (lunarPhaseIcon != null) {
                    imageView2_gobansyo.setImageResource(lunarPhaseIcon)
                }

                textView1_gobansyo.text = weatherDescription
                textView2_gobansyo.text = "$temp℃"

                Text1_gobansyo.text = "天文薄明 : " + responseGobansyoSorted[0].twilightTime
                Text2_gobansyo.text = "日の出 : " + responseGobansyoSorted[0].sunrise
                Text3_gobansyo.text = "日の入り : " + responseGobansyoSorted[0].sunset
                Text4_gobansyo.text = "月齢 : " + responseGobansyoSorted[0].lunarPhase
                Text5_gobansyo.text = "月の出 : " + responseGobansyoSorted[0].moonrise
                Text6_gobansyo.text = "月の入り : " + responseGobansyoSorted[0].moonset

            } else {
                //Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val viewGobansyo = inflater.inflate(R.layout.fragment_gobansyo, container, false)
        val recyclerViewGobansyo = viewGobansyo.findViewById<RecyclerView>(R.id.recyclerView_gobansyo)
        val linearLayoutManager = LinearLayoutManager(viewGobansyo.context)
        val itemDecoration = DividerItemDecoration(viewGobansyo.context, DividerItemDecoration.VERTICAL)

        recyclerViewGobansyo.adapter = myAdapter
        recyclerViewGobansyo.layoutManager = linearLayoutManager
        recyclerViewGobansyo.addItemDecoration(itemDecoration)

        return viewGobansyo
    }

    private fun convertTemp(absoluteTemp: String): String {
        var relativeTemp = absoluteTemp.toFloat() - 273.15
        return relativeTemp.roundToInt().toString()
    }

}