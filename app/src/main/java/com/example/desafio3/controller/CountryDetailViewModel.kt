package com.example.desafio3.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio3.Model.WeatherResponse
import com.example.desafio3.network.RetrofitClient
import kotlinx.coroutines.launch

class CountryDetailViewModel : ViewModel() {

    // IMPORTANTE: Reemplaza esto con tu API Key real
    private val WEATHER_API_KEY = "515c1409c21f45cab6041746253010"

    // LiveData para la respuesta del clima
    private val _weather = MutableLiveData<WeatherResponse>()
    val weather: LiveData<WeatherResponse> = _weather

    // LiveData para el estado de carga
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // LiveData para errores
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    /**
     * Llama a la WeatherAPI para obtener el clima de la capital.
     */
    fun fetchWeather(capital: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            _error.postValue(null)

            try {
                val response = RetrofitClient.weatherApiInstance
                    .getCurrentWeather(WEATHER_API_KEY, capital)

                if (response.isSuccessful) {
                    _weather.postValue(response.body())
                } else {
                    // Manejar error de API (ej: ciudad no encontrada)
                    _error.postValue("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                // Manejar error de red
                _error.postValue("Error de conexión: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}