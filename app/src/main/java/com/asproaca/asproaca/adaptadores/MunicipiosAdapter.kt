package com.asproaca.asproaca.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asproaca.asproaca.databinding.ItemMunicipiosBinding
import com.asproaca.asproaca.modelos.Municipios

class MunicipiosAdapter(private val listaMunicipios: ArrayList<Municipios>, val context: Context) :
    RecyclerView.Adapter<MunicipiosAdapter.ViewHolder>(), View.OnClickListener {
    private var clickLIstener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMunicipiosBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)

        return ViewHolder(binding, parent.context)
    }

    class ViewHolder(private val binding: ItemMunicipiosBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(municipio: Municipios) {
            binding.idDepartamento.text = municipio.depto
            binding.idNombre.text = municipio.nombre
        }
    }

    override fun onBindViewHolder(holder: MunicipiosAdapter.ViewHolder, position: Int) {
        holder.bind(listaMunicipios[position])
    }

    override fun getItemCount(): Int {
        return listaMunicipios.size
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        clickLIstener = listener
    }

    override fun onClick(p0: View?) {
        clickLIstener?.onClick(p0)
    }
}