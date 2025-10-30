package com.example.desafio3.view.adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio3.R

class RegionAdapter(
    private var regions: List<String>,
    private val onItemClick: (String) -> Unit // Función lambda para manejar clics
) : RecyclerView.Adapter<RegionAdapter.RegionViewHolder>() {

    // 1. Crea la vista para cada fila (ViewHolder)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_region, parent, false)
        return RegionViewHolder(view)
    }

    // 2. Vincula los datos con la vista
    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        val region = regions[position]
        holder.bind(region)
        // Configura el clic en el item
        holder.itemView.setOnClickListener {
            onItemClick(region)
        }
    }

    // 3. Devuelve la cantidad de items
    override fun getItemCount(): Int = regions.size

    // 4. (Opcional) Función para actualizar la lista
    fun updateData(newRegions: List<String>) {
        regions = newRegions
        notifyDataSetChanged() // Recarga la lista
    }

    // Clase interna que representa la vista de una fila
    class RegionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val regionName: TextView = itemView.findViewById(R.id.tvRegionName)

        fun bind(region: String) {
            regionName.text = region
        }
    }
}