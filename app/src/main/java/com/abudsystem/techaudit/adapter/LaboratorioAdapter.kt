package com.abudsystem.techaudit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abudsystem.techaudit.databinding.ItemLaboratorioBinding
import com.abudsystem.techaudit.model.Laboratorio
/*
Creacion del nuevo adaptador para la segunda entidad
 */
class LaboratorioAdapter(
    private var listaLaboratorios: List<Laboratorio>,
    private val onClick: (Laboratorio) -> Unit
) : RecyclerView.Adapter<LaboratorioAdapter.LaboratorioViewHolder>() {

    inner class LaboratorioViewHolder(val binding: ItemLaboratorioBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaboratorioViewHolder {
        val binding = ItemLaboratorioBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LaboratorioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LaboratorioViewHolder, position: Int) {
        val laboratorio = listaLaboratorios[position]

        holder.binding.tvNombreLaboratorio.text = laboratorio.nombre


        holder.itemView.setOnClickListener {
            onClick(laboratorio)
        }
    }

    override fun getItemCount(): Int = listaLaboratorios.size

    fun actualizarLista(nuevaLista: List<Laboratorio>) {
        listaLaboratorios = nuevaLista
        notifyDataSetChanged()
    }
}