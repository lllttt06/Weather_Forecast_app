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
import kotlinx.android.synthetic.main.fragment_zao.*
import kotlin.math.roundToInt

class Zao : Fragment() {
    private lateinit var viewModelZao: MainViewModel
    private val myAdapter by lazy { RecyclerViewAdapter() }
    private val timeComparator: Comparator<ApiResponse> = compareBy<ApiResponse> { it.Time.toInt() }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        val myPostZao = Post("zao")

        viewModelZao = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModelZao.pushPostZao(myPostZao)
        viewModelZao.myResponseZao.observe(this, Observer { responseZao ->
            if(responseZao.isSuccessful){
                val responseZaoSorted = responseZao.body()!!.sortedWith(timeComparator)
                val weatherIconZao = IconMap.weatherIconsDetector[responseZaoSorted[0].weather]
                val weatherDescriptionZao = IconMap.weatherDescriptionDetector[responseZaoSorted[0].weather]
                val lunarPhaseIconZao = IconMap.lunarPhaseDetector[responseZaoSorted[0].lunarPhaseIcon]
                val temp = convertTemp(responseZaoSorted[0].temp)

                responseZaoSorted.let { myAdapter.setData(it) }

                if (weatherIconZao != null) {
                    imageView1_zao.setImageResource(weatherIconZao)
                }
                if (lunarPhaseIconZao != null) {
                    imageView2_zao.setImageResource(lunarPhaseIconZao)
                }

                textView1_zao.text = weatherDescriptionZao
                textView2_zao.text = "$temp℃"
                Text1_zao.text = responseZaoSorted[0].weatherDesc
                Text2_zao.text = "日の出 : " + responseZaoSorted[0].sunrise
                Text3_zao.text = "日の入り : " + responseZaoSorted[0].sunset
                Text4_zao.text = "月齢 : " + responseZaoSorted[0].lunarPhase
                Text5_zao.text = "月の出 : " + responseZaoSorted[0].moonrise
                Text6_zao.text = "月の入り : " + responseZaoSorted[0].moonset

            }else {
                //Toast.makeText(this, responseZao.code(), Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewZao = inflater.inflate(R.layout.fragment_zao, container, false)
        val recyclerViewZao = viewZao.findViewById<RecyclerView>(R.id.recyclerView_zao)
        val linearLayoutManager = LinearLayoutManager(viewZao.context)
        val itemDecoration = DividerItemDecoration(viewZao.context, DividerItemDecoration.VERTICAL)

        recyclerViewZao.adapter = myAdapter
        recyclerViewZao.layoutManager = linearLayoutManager
        recyclerViewZao.addItemDecoration(itemDecoration)

        return viewZao
    }

    private fun convertTemp(absoluteTemp: String): String {
        var relativeTemp = absoluteTemp.toFloat() - 273.15
        return relativeTemp.roundToInt().toString()
    }
}