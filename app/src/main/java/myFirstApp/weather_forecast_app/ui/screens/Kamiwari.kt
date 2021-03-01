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
import kotlinx.android.synthetic.main.fragment_kamiwari.*
import kotlin.math.roundToInt

class Kamiwari : Fragment() {
    private lateinit var viewModelKamiwari: MainViewModel
    private val myAdapter by lazy { RecyclerViewAdapter() }
    private val timeComparator: Comparator<ApiResponse> = compareBy<ApiResponse> { it.Time.toInt() }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setupRecyclerview()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        val myPostKamiwari = Post("kamiwari")

        viewModelKamiwari = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModelKamiwari.pushPostKamiwari(myPostKamiwari)
        viewModelKamiwari.myResponseKamiwari.observe(this, Observer { responseKamiwari ->
            if(responseKamiwari.isSuccessful){
                val responseKamiwariSorted = responseKamiwari.body()!!.sortedWith(timeComparator)
                val weatherIconKamiwari = IconMap.weatherIconsDetector[responseKamiwariSorted[0].weather]
                val weatherDescriptionKamiwari = IconMap.weatherDescriptionDetector[responseKamiwariSorted[0].weather]
                val lunarPhaseIconKamiwari = IconMap.lunarPhaseDetector[responseKamiwariSorted[0].lunarPhaseIcon]
                val temp = convertTemp(responseKamiwariSorted[0].temp)

                responseKamiwariSorted.let { myAdapter.setData(it) }

                if (weatherIconKamiwari != null) {
                    imageView1_kamiwari.setImageResource(weatherIconKamiwari)
                }
                if (lunarPhaseIconKamiwari != null) {
                    imageView2_kamiwari.setImageResource(lunarPhaseIconKamiwari)
                }

                textView1_kamiwari.text = weatherDescriptionKamiwari
                textView2_kamiwari.text = "$temp℃"
                Text1_kamiwari.text = responseKamiwariSorted[0].weatherDesc
                Text2_kamiwari.text = "日の出 : " + responseKamiwariSorted[0].sunrise
                Text3_kamiwari.text = "日の入り : " + responseKamiwariSorted[0].sunset
                Text4_kamiwari.text = "月齢 : " + responseKamiwariSorted[0].lunarPhase
                Text5_kamiwari.text = "月の出 : " + responseKamiwariSorted[0].moonrise
                Text6_kamiwari.text = "月の入り : " + responseKamiwariSorted[0].moonset

            }else {
                //Toast.makeText(this, responseZao.code(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewKamiwari = inflater.inflate(R.layout.fragment_kamiwari, container, false)
        val recyclerViewKamiwari = viewKamiwari.findViewById<RecyclerView>(R.id.recyclerView_kamiwari)
        val linearLayoutManager = LinearLayoutManager(viewKamiwari.context)
        val itemDecoration = DividerItemDecoration(viewKamiwari.context, DividerItemDecoration.VERTICAL)

        recyclerViewKamiwari.adapter = myAdapter
        recyclerViewKamiwari.layoutManager = linearLayoutManager
        recyclerViewKamiwari.addItemDecoration(itemDecoration)

        return viewKamiwari
    }

    private fun convertTemp(absoluteTemp: String): String {
        var relativeTemp = absoluteTemp.toFloat() - 273.15
        return relativeTemp.roundToInt().toString()
    }
}