package com.example.desafio3.network// Reemplaza esto con tu nombre de paquete

import com.example.desafio3.Model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interfaz de Retrofit para WeatherAPI.
 * Base URL: http://api.weatherapi.com/v1 [cite: 22]
 */
interface WeatherApi {

    /**
     * Obtiene el clima actual para una ciudad (capital).
     * Corresponde al endpoint: GET /current.json
     *
     * @param apiKey Tu API Key de WeatherAPI.
     * @param capital El nombre de la ciudad (capital del país).
     */
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") capital: String
    ): Response<WeatherResponse>
}