package com.example.desafio3.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio3.Model.Country
import com.example.desafio3.network.RetrofitClient
import kotlinx.coroutines.launch


class CountryViewModel : ViewModel() {

    // LiveData para la lista de países
    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> = _countries

    // LiveData para el estado de carga
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // LiveData para errores
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    /**
     * Llama a la API para obtener los países de una región específica.
     * @param regionName El nombre de la región (ej: "Europe")
     */
    fun fetchCountriesByRegion(regionName: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            _error.postValue(null)

            try {
                // 1. Llamar a la API con la región
                val response = RetrofitClient.restCountriesInstance.getCountriesByRegion(regionName)

                if (response.isSuccessful) {
                    // 2. Publicar la lista de países
                    _countries.postValue(response.body() ?: emptyList())
                } else {
                    _error.postValue("Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error de conexión: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}