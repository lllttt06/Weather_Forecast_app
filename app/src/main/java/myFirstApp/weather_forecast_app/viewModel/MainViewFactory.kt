package myFirstApp.weather_forecast_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import myFirstApp.weather_forecast_app.repository.Repository

class MainViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}