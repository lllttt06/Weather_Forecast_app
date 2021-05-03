package myFirstApp.weather_forecast_app.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import myFirstApp.weather_forecast_app.R
import myFirstApp.weather_forecast_app.model.ApiResponse
import myFirstApp.weather_forecast_app.model.Post
import myFirstApp.weather_forecast_app.myAdapter.RecyclerViewAdapter
import myFirstApp.weather_forecast_app.repository.Repository
import myFirstApp.weather_forecast_app.utils.IconMap
import myFirstApp.weather_forecast_app.viewModel.MainViewModel
import myFirstApp.weather_forecast_app.viewModel.MainViewModelFactory
import kotlin.math.roundToInt


class HomeFragment : Fragment() {
    private val myAdapter by lazy { RecyclerViewAdapter() }
    private val timeComparator: Comparator<ApiResponse> = compareBy { it.Time.toInt() }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewHome = inflater.inflate(R.layout.fragment_home, container, false)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        val myPostHome = Post("home")
        val viewModelHome = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModelHome.pushPost(myPostHome)
        viewModelHome.isResponseSuccessful.observe(viewLifecycleOwner, {
            if (it == false) {
                val sampleFragment = SampleFragment()
                val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                transaction.addToBackStack(null)
                transaction.replace(R.id.fragment_container, sampleFragment)
                transaction.commit()
            }
        })
        viewModelHome.myResponse.observe(this, { response ->
            if (response.isSuccessful) {
                val responseSorted = response.body()!!.sortedWith(timeComparator)
                val weatherIcon = IconMap.weatherIconsDetector[responseSorted[0].weather]
                val weatherDescription =
                    IconMap.weatherDescriptionDetector[responseSorted[0].weather]
                val lunarPhaseIcon = IconMap.lunarPhaseDetector[responseSorted[0].lunarPhaseIcon]
                val temp = convertTemp(responseSorted[0].temp)

                responseSorted.let { myAdapter.setData(it) }

                if (weatherIcon != null) {
                    imageView1_home.setImageResource(weatherIcon)
                }
                if (lunarPhaseIcon != null) {
                    imageView2_home.setImageResource(lunarPhaseIcon)
                }

                textView1_home.text = weatherDescription
                textView2_home.text = "$temp℃"

                Text1_home.text = getString(R.string.twilight) + responseSorted[0].twilightTime
                Text2_home.text = getString(R.string.sunrise) + responseSorted[0].sunrise
                Text3_home.text = getString(R.string.sunset) + responseSorted[0].sunset
                Text4_home.text = getString(R.string.lunarPhase) + responseSorted[0].lunarPhase
                Text5_home.text = getString(R.string.moonrise) + responseSorted[0].moonrise
                Text6_home.text = getString(R.string.moonset) + responseSorted[0].moonset

            }
        })
        setRecyclerView(viewHome)


        return viewHome
    }

    private fun convertTemp(absoluteTemp: String): String {
        val relativeTemp = absoluteTemp.toFloat() - 273.15 // -273.15 is absolute zero
        return relativeTemp.roundToInt().toString()
    }

    private fun setRecyclerView(viewHome: View) {
        val recyclerViewHome = viewHome.findViewById<RecyclerView>(R.id.recyclerView_home)
        val linearLayoutManager = LinearLayoutManager(viewHome.context)
        val itemDecoration = DividerItemDecoration(viewHome.context, DividerItemDecoration.VERTICAL)

        recyclerViewHome.adapter = myAdapter
        recyclerViewHome.layoutManager = linearLayoutManager
        recyclerViewHome.addItemDecoration(itemDecoration)
    }

}