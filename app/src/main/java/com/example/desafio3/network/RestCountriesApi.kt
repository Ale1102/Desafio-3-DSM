package com.example.desafio3.network

import com.example.desafio3.Model.Country
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RestCountriesApi {

    /**
     * FUNCIÓN 1 (Esta ya la tenías bien)
     */
    @GET("v3.1/all?fields=name,capital,region,subregion,population,flags,currencies,languages")
    suspend fun getAllCountries(): Response<List<Country>>

    /**
     * FUNCIÓN 2 (Esta es la que te falta arreglar)
     * Añade la misma cadena de "?fields=..." aquí
     */
    @GET("v3.1/region/{region}?fields=name,capital,region,subregion,population,flags,currencies,languages")
    suspend fun getCountriesByRegion(
        @Path("region") region: String
    ): Response<List<Country>>
}