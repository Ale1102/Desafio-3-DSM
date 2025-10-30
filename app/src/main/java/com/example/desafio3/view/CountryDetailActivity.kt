package com.example.desafio3.view// Reemplaza con tu paquete

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.google.gson.Gson
import com.example.desafio3.R
import com.example.desafio3.controller.CountryDetailViewModel
import com.example.desafio3.Model.Country
import com.example.desafio3.Model.WeatherResponse

class CountryDetailActivity : AppCompatActivity() {

    private val detailViewModel: CountryDetailViewModel by viewModels()
    private var country: Country? = null

    // Vistas de País
    private lateinit var ivFlagDetail: ImageView
    private lateinit var tvCountryNameDetail: TextView
    private lateinit var tvOfficialName: TextView
    private lateinit var tvCapitalDetail: TextView
    private lateinit var tvRegionDetail: TextView
    private lateinit var tvSubregionDetail: TextView
    private lateinit var tvPopulationDetail: TextView
    private lateinit var tvCurrenciesDetail: TextView
    private lateinit var tvLanguagesDetail: TextView

    // Vistas de Clima
    private lateinit var weatherContent: ConstraintLayout
    private lateinit var weatherProgressBar: ProgressBar
    private lateinit var tvWeatherError: TextView
    private lateinit var ivWeatherIcon: ImageView
    private lateinit var tvTemperature: TextView
    private lateinit var tvWeatherCondition: TextView
    private lateinit var tvHumidity: TextView
    private lateinit var tvWind: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_detail)

        // 1. Deserializar el objeto País
        val countryJson = intent.getStringExtra("COUNTRY_JSON")
        if (countryJson == null) {
            Toast.makeText(this, "Error: No se recibieron datos del país", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        country = Gson().fromJson(countryJson, Country::class.java)

        // 2. Enlazar todas las Vistas
        bindViews()

        // 3. Rellenar la UI con datos del país
        populateCountryData()

        // 4. Observar el ViewModel del Clima
        observeWeatherViewModel()

        // 5. Pedir el clima
        fetchWeatherData()
    }

    private fun bindViews() {
        // País
        ivFlagDetail = findViewById(R.id.ivFlagDetail)
        tvCountryNameDetail = findViewById(R.id.tvCountryNameDetail)
        tvOfficialName = findViewById(R.id.tvOfficialName)
        tvCapitalDetail = findViewById(R.id.tvCapitalDetail)
        tvRegionDetail = findViewById(R.id.tvRegionDetail)
        tvSubregionDetail = findViewById(R.id.tvSubregionDetail)
        tvPopulationDetail = findViewById(R.id.tvPopulationDetail)
        tvCurrenciesDetail = findViewById(R.id.tvCurrenciesDetail)
        tvLanguagesDetail = findViewById(R.id.tvLanguagesDetail)

        // Clima
        weatherContent = findViewById(R.id.weatherContent)
        weatherProgressBar = findViewById(R.id.weatherProgressBar)
        tvWeatherError = findViewById(R.id.tvWeatherError)
        ivWeatherIcon = findViewById(R.id.ivWeatherIcon)
        tvTemperature = findViewById(R.id.tvTemperature)
        tvWeatherCondition = findViewById(R.id.tvWeatherCondition)
        tvHumidity = findViewById(R.id.tvHumidity)
        tvWind = findViewById(R.id.tvWind)
    }

    private fun populateCountryData() {
        country?.let {
            // Cargar Bandera
            ivFlagDetail.load(it.flags.png)

            // Info Básica
            tvCountryNameDetail.text = it.name.common
            tvOfficialName.text = it.name.official
            tvCapitalDetail.text = it.capital?.firstOrNull() ?: "N/A"
            tvRegionDetail.text = it.region
            tvSubregionDetail.text = it.subregion ?: "N/A"
            tvPopulationDetail.text = "%,d".format(it.population) // Formato con comas

            // Formatear Monedas (Mapa)
            tvCurrenciesDetail.text = it.currencies?.map { entry ->
                "${entry.key} (${entry.value.name} - ${entry.value.symbol ?: ""})"
            }?.joinToString("\n") ?: "N/A"

            // Formatear Idiomas (Mapa)
            tvLanguagesDetail.text = it.languages?.values?.joinToString(", ") ?: "N/A"
        }
    }

    // ESTO ES CORRECTO
    private fun fetchWeatherData() {
        val capital = country?.capital?.firstOrNull()

        // ESTA COMPROBACIÓN ARREGLA EL ERROR 400
        // BIEN (comprueba 'null' Y TAMBIÉN strings vacíos)
        if (!capital.isNullOrEmpty()) {
            detailViewModel.fetchWeather(capital)
        } else {
            // Muestra un error si la capital es nula
            weatherProgressBar.visibility = View.GONE
            tvWeatherError.visibility = View.VISIBLE
            tvWeatherError.text = "No hay capital para mostrar el clima."
        }
    }

    private fun observeWeatherViewModel() {
        detailViewModel.isLoading.observe(this) { isLoading ->
            weatherProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        detailViewModel.error.observe(this) { error ->
            if (error != null) {
                tvWeatherError.visibility = View.VISIBLE
                tvWeatherError.text = error
                weatherContent.visibility = View.GONE
            } else {
                tvWeatherError.visibility = View.GONE
            }
        }

        detailViewModel.weather.observe(this) { weather ->
            // ¡Datos recibidos! Rellenar la UI del clima
            weatherContent.visibility = View.VISIBLE
            populateWeatherData(weather)
        }
    }

    private fun populateWeatherData(weather: WeatherResponse) {
        // Cargar ícono del clima
        // La API a veces da la URL sin "https:", la agregamos
        ivWeatherIcon.load("https:${weather.current.condition.icon}")

        // Rellenar datos
        tvTemperature.text = "${weather.current.temp_c}°C"
        tvWeatherCondition.text = weather.current.condition.text
        tvHumidity.text = "${weather.current.humidity}%"
        tvWind.text = "${weather.current.wind_kph} kph"
    }
}