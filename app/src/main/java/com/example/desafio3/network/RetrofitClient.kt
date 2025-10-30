package com.example.desafio3.network // Reemplaza con tu paquete

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val REST_COUNTRIES_BASE_URL = "https://restcountries.com/"
    private const val WEATHER_API_BASE_URL = "http://api.weatherapi.com/v1/"

    /**
     * Instancia "perezosa" (lazy) para RestCountriesApi.
     * VAMOS A MODIFICAR ESTA SECCIÓN
     */
    val restCountriesInstance: RestCountriesApi by lazy {

        // 1. Crea el interceptor
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY) // Nivel BODY para ver todo

        // 2. Crea el cliente OkHttp y añade el interceptor
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        // 3. Construye Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(REST_COUNTRIES_BASE_URL)
            .client(client) // <-- AÑADE EL CLIENTE PERSONALIZADO AQUÍ
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(RestCountriesApi::class.java)
    }

    /**
     * Instancia "perezosa" (lazy) para WeatherApi.
     * (Esta la dejamos igual por ahora)
     */
    val weatherApiInstance: WeatherApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(WEATHER_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(WeatherApi::class.java)
    }
}