package com.abudsystem.techaudit.adapter
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.abudsystem.techaudit.model.AuditStatus
import com.abudsystem.techaudit.model.AuditItem
import com.abudsystem.techaudit.databinding.ItemAuditBinding



// ESte modelo nos permite comunicar nuestro modelo con la vista
class AuditAdapter (
    val listaAuditoria: MutableList<AuditItem>,
    private val onItemSelected: (AuditItem) -> Unit
): RecyclerView.Adapter<AuditAdapter.AuditViewHolder>() {

    inner class AuditViewHolder(val binding: ItemAuditBinding) : RecyclerView.ViewHolder(binding.root)

    //1, Crear el molde
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuditViewHolder {
        var binding = ItemAuditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AuditViewHolder(binding)

    }

    //2, Cuantos elementos tengo
    override fun getItemCount(): Int {
        return listaAuditoria.size
    }

    fun actualizarLista(nuevaLista: List<AuditItem>) {
        listaAuditoria.clear()
        listaAuditoria.addAll(nuevaLista)
        notifyDataSetChanged() // Refresca la pantall
    }


    //3. Pintar los datos
    override fun onBindViewHolder(holder: AuditViewHolder, position: Int) {

        val item = listaAuditoria[position]

        // asignar texto
        holder.binding.tvNombreEquipo.text = item.nombre

        holder.binding.tvEstadoLabel.text = item.estado.name
        // asignar color

        val colorEstado = when (item.estado) {
            AuditStatus.PENDIENTE -> Color.parseColor("#9e9e9e")
            AuditStatus.OPERATIVO -> Color.parseColor("#4CAF50")
            AuditStatus.DANIADO -> Color.parseColor("#f44336")
            AuditStatus.NO_ENCONTRADO -> Color.parseColor("#000000")


        }

        // pintar la barra lateral y el texto
        holder.binding.viewStatusColor.setBackgroundColor(colorEstado)
        holder.binding.tvEstadoLabel.setTextColor(colorEstado)

        // Cpmonfigurar el click de cada tarjeta
        holder.itemView.setOnClickListener {
            onItemSelected(item)
        }
    }
}