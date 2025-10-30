package com.example.desafio3.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson // Importa Gson
import com.example.desafio3.R
import com.example.desafio3.controller.CountryViewModel
import com.example.desafio3.view.adapter.CountryAdapter


class CountryListActivity : AppCompatActivity() {

    private val countryViewModel: CountryViewModel by viewModels()
    private lateinit var rvCountries: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvRegionTitle: TextView
    private lateinit var countryAdapter: CountryAdapter
    private var selectedRegion: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_list)

        // 1. Recibir la región de la Activity anterior
        selectedRegion = intent.getStringExtra("SELECTED_REGION")
        if (selectedRegion == null) {
            Toast.makeText(this, "Error: No se recibió la región", Toast.LENGTH_LONG).show()
            finish() // Cerrar la activity si no hay región
            return
        }

        // 2. Enlazar Vistas
        tvRegionTitle = findViewById(R.id.tvRegionTitle)
        rvCountries = findViewById(R.id.rvCountries)
        progressBar = findViewById(R.id.progressBar)

        tvRegionTitle.text = "Países en $selectedRegion" // Título dinámico

        // 3. Configurar RecyclerView
        setupRecyclerView()

        // 4. Observar el ViewModel
        observeViewModel()

        // 5. Pedir los datos al ViewModel
        countryViewModel.fetchCountriesByRegion(selectedRegion!!)
    }

    private fun setupRecyclerView() {
        countryAdapter = CountryAdapter(emptyList()) { country ->
            // --- MANEJO DEL CLIC ---|
            // Navegar a la pantalla de detalle
            val intent = Intent(this, CountryDetailActivity::class.java)

            // Pasamos el objeto País completo a la siguiente activity
            // Lo convertimos a JSON (String) para pasarlo fácilmente
            val countryJson = Gson().toJson(country)
            intent.putExtra("COUNTRY_JSON", countryJson)

            startActivity(intent)
        }

        rvCountries.layoutManager = LinearLayoutManager(this)
        rvCountries.adapter = countryAdapter
    }

    private fun observeViewModel() {
        countryViewModel.countries.observe(this) { countries ->
            countryAdapter.updateData(countries)
        }

        countryViewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        countryViewModel.error.observe(this) { errorMsg ->
            if (errorMsg != null) {
                Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
            }
        }
    }
}