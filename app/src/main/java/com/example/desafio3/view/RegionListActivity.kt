package com.example.desafio3.view



import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio3.R
import com.example.desafio3.controller.RegionViewModel
import com.example.desafio3.view.adapter.RegionAdapter

class RegionListActivity : AppCompatActivity() {

    // 1. Inicializar el ViewModel (Controlador)
    private val regionViewModel: RegionViewModel by viewModels()

    // 2. Declarar vistas y adaptador
    private lateinit var rvRegions: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var regionAdapter: RegionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_region_list)

        // 3. Enlazar Vistas
        rvRegions = findViewById(R.id.rvRegions)
        progressBar = findViewById(R.id.progressBar)

        // 4. Configurar el RecyclerView
        setupRecyclerView()

        // 5. Observar los datos del ViewModel
        observeViewModel()

        // 6. Pedir los datos
        regionViewModel.fetchRegions()
    }

    private fun setupRecyclerView() {
        // Inicializar adaptador vacío, se llenará cuando lleguen los datos
        regionAdapter = RegionAdapter(emptyList()) { region ->
            // --- MANEJO DEL CLIC ---
            // Aquí navegamos a la siguiente pantalla
            val intent = Intent(this, CountryListActivity::class.java)
            intent.putExtra("SELECTED_REGION", region)
            startActivity(intent)
        }

        rvRegions.layoutManager = LinearLayoutManager(this)
        rvRegions.adapter = regionAdapter
    }

    private fun observeViewModel() {
        // Observador para la lista de regiones
        regionViewModel.regions.observe(this) { regions ->
            // Actualizar el adaptador con los nuevos datos
            regionAdapter.updateData(regions)
        }

        // Observador para el estado de carga
        regionViewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observador para errores
        regionViewModel.error.observe(this) { errorMsg ->
            if (errorMsg != null) {
                // Mostrar un mensaje de error amigable [cite: 31, 35]
                Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
            }
        }
    }
}