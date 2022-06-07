package com.asproaca.asproaca.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asproaca.asproaca.databinding.ItemFincasBinding
import com.asproaca.asproaca.modelos.Finca


class FincasAdapter(private val listFarm: ArrayList<Finca>, val context: Context) :
    RecyclerView.Adapter<FincasAdapter.ViewHolder>(), View.OnClickListener {
    private var clickLIstener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFincasBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FincasAdapter.ViewHolder, position: Int) { holder.bind(listFarm[position]) }

    class ViewHolder(private val binding: ItemFincasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Finca) {
            binding.idTxtNombreFinca.text = event.nombre_finca
            binding.idTxtVeredaFinca.text = event.vereda_finca
            binding.idTxtZona.text = event.zona
            binding.idTxtAntiguedadFinca.text = event.antiguedad_finca
            binding.idTxtAreaTotal.text = event.area_total
        }
    }

    override fun getItemCount(): Int { return listFarm.size }

    fun setOnClickListener(listener: View.OnClickListener) { clickLIstener = listener }

    override fun onClick(p0: View?) { clickLIstener?.onClick(p0) }
}