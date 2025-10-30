package com.example.desafio3.view.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load // Importante: Importar la extensión de Coil
import com.example.desafio3.R
import com.example.desafio3.Model.Country


class CountryAdapter(
    private var countries: List<Country>,
    private val onItemClick: (Country) -> Unit
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.bind(country)
        holder.itemView.setOnClickListener {
            onItemClick(country)
        }
    }

    override fun getItemCount(): Int = countries.size

    fun updateData(newCountries: List<Country>) {
        countries = newCountries
        notifyDataSetChanged()
    }

    // --- ViewHolder ---
    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivFlag: ImageView = itemView.findViewById(R.id.ivFlag)
        private val tvCountryName: TextView = itemView.findViewById(R.id.tvCountryName)
        private val tvCapital: TextView = itemView.findViewById(R.id.tvCapital)

        fun bind(country: Country) {
            // Requerimientos: Nombre, Bandera y Capital
            tvCountryName.text = country.name.common

            // La capital es una lista, tomamos el primer elemento si existe
            tvCapital.text = country.capital?.firstOrNull() ?: "N/A"

            // --- Cargar Imagen con COIL ---
            // Usamos la URL del PNG de la bandera [cite: 20]
            ivFlag.load(country.flags.png) {
                placeholder(R.drawable.ic_launcher_background) // Imagen de carga
                error(R.drawable.ic_launcher_background) // Imagen de error
            }
        }
    }
}