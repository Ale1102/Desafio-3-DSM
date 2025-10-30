package com.example.desafio3.controller


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio3.network.RetrofitClient
import kotlinx.coroutines.launch

class RegionViewModel : ViewModel() {

    // LiveData para exponer la lista de regiones a la Vista
    private val _regions = MutableLiveData<List<String>>()
    val regions: LiveData<List<String>> = _regions

    // LiveData para el estado de carga (ProgressBar)
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // LiveData para manejar errores
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    /**
     * Llama a la API para obtener todos los países y extraer sus regiones.
     */
    fun fetchRegions() {
        // Iniciar en un hilo secundario (corutina)
        viewModelScope.launch {
            _isLoading.postValue(true) // Mostrar ProgressBar
            _error.postValue(null) // Limpiar errores previos

            try {
                // 1. Llamar a la API
                val response = RetrofitClient.restCountriesInstance.getAllCountries()

                if (response.isSuccessful) {
                    // 2. Procesar la respuesta
                    val countries = response.body() ?: emptyList()

                    // 3. Extraer regiones, filtrarlas y ordenarlas
                    val regionList = countries
                        .map { it.region } // Obtener solo la región de cada país
                        .distinct()       // Quedarse solo con valores únicos
                        .sorted()         // Ordenarlas alfabéticamente

                    // 4. Publicar el resultado en LiveData
                    _regions.postValue(regionList)

                } else {
                    // Manejar error de la API (ej: 404, 500)
                    _error.postValue("Error: ${response.code()} ${response.message()}")
                    response.errorBody()?.close()
                }
            } catch (e: Exception) {
                // Manejar error de red (ej: sin conexión)
                _error.postValue("Error de conexión: ${e.message}")
            } finally {
                _isLoading.postValue(false) // Ocultar ProgressBar
            }
        }
    }
}