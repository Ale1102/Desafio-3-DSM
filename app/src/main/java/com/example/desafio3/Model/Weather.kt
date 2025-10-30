package com.example.desafio3.Model

import com.google.gson.annotations.SerializedName

/**
 * Modelo principal para la respuesta de WeatherAPI.
 */
data class WeatherResponse(
    @SerializedName("location")
    val location: Location,

    @SerializedName("current")
    val current: Current
)

/**
 * Objeto anidado para la ubicación.
 */
data class Location(
    @SerializedName("name")
    val name: String,

    @SerializedName("region")
    val region: String,

    @SerializedName("country")
    val country: String
)

/**
 * Objeto anidado para los datos del clima actual.
 * Basado en los datos esperados
 */
data class Current(
    @SerializedName("temp_c")
    val temp_c: Double,

    @SerializedName("temp_f")
    val temp_f: Double,

    @SerializedName("condition")
    val condition: Condition, // Objeto anidado para la condición

    @SerializedName("wind_kph")
    val wind_kph: Double,

    @SerializedName("wind_mph")
    val wind_mph: Double,

    @SerializedName("humidity")
    val humidity: Int
)

/**
 * Objeto anidado para la condición del clima (texto e ícono).
 */
data class Condition(
    @SerializedName("text")
    val text: String,

    @SerializedName("icon")
    val icon: String // Esta es una URL al ícono
)