package com.example.desafio3.Model



import com.google.gson.annotations.SerializedName

/**
 * Modelo principal que representa un País.
 * Contiene solo los campos que nos interesan[cite: 20].
 */
data class Country(
    @SerializedName("name")
    val name: Name,

    @SerializedName("capital")
    val capital: List<String>?, // La capital es una lista, puede estar vacía o ser nula

    @SerializedName("region")
    val region: String,

    @SerializedName("subregion")
    val subregion: String?, // Puede ser nulo

    @SerializedName("population")
    val population: Long,

    @SerializedName("flags")
    val flags: Flags, // Objeto anidado para las banderas

    @SerializedName("currencies")
    val currencies: Map<String, Currency>?, // Objeto complejo (mapa) para monedas

    @SerializedName("languages")
    val languages: Map<String, String>? // Objeto complejo (mapa) para idiomas
)

/**
 * Objeto anidado para el nombre del país.
 */
data class Name(
    @SerializedName("common")
    val common: String,

    @SerializedName("official")
    val official: String
)

/**
 * Objeto anidado para las URLs de las banderas.
 */
data class Flags(
    @SerializedName("png")
    val png: String,

    @SerializedName("svg")
    val svg: String? // El SVG puede no estar
)

/**
 * Objeto anidado para la información de la moneda.
 * El JSON real es un Mapa, ej: { "USD": { "name": "US Dollar", "symbol": "$" } }
 */
data class Currency(
    @SerializedName("name")
    val name: String,

    @SerializedName("symbol")
    val symbol: String? // El símbolo puede ser nulo
)