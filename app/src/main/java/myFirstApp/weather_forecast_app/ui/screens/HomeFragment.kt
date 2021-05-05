package myFirstApp.weather_forecast_app.ui.screens

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
import myFirstApp.weather_forecast_app.R
import myFirstApp.weather_forecast_app.databinding.FragmentHomeBinding
import myFirstApp.weather_forecast_app.model.ApiResponse
import myFirstApp.weather_forecast_app.model.Post
import myFirstApp.weather_forecast_app.myAdapter.RecyclerViewAdapter
import myFirstApp.weather_forecast_app.repository.Repository
import myFirstApp.weather_forecast_app.viewModel.MainViewModel
import myFirstApp.weather_forecast_app.viewModel.MainViewModelFactory


class HomeFragment : Fragment() {
    private val myAdapter by lazy { RecyclerViewAdapter() }
    private val timeComparator: Comparator<ApiResponse> = compareBy { it.Time.toInt() }
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        viewModel.pushPost(Post("home"))

        viewModel.isResponseSuccessful.observe(viewLifecycleOwner, {
            if (it == false) {
                val sampleFragment = ReloadFragment()
                val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                transaction
                    .addToBackStack(null)
                    .replace(R.id.fragment_container, sampleFragment)
                    .commit()
            }
        })

        viewModel.myResponse.observe(this, { response ->
            if (response.isSuccessful) {
                val responseSorted = response.body()!!.sortedWith(timeComparator)
                responseSorted.let { myAdapter.setData(it) }
            }
        })

        setRecyclerView(binding.root)
        return binding.root
    }

    private fun setRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_home)
        val linearLayoutManager = LinearLayoutManager(view.context)
        val itemDecoration = DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL)

        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(itemDecoration)
    }

}