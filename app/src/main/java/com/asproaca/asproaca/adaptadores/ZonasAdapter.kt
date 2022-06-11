package com.asproaca.asproaca.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asproaca.asproaca.databinding.ItemZonasBinding
import com.asproaca.asproaca.modelos.ZonasModel

class ZonasAdapter(private val listaZonas: ArrayList<ZonasModel>, val context: Context) :
    RecyclerView.Adapter<ZonasAdapter.ViewHolder>(), View.OnClickListener {

    private var clickLIstener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemZonasBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)

        return ViewHolder(binding, parent.context)
    }

    class ViewHolder(private val binding: ItemZonasBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(zonas: ZonasModel) {
            binding.idTxtNombreZona.text = zonas.nombre_zona
        }
    }

    override fun onBindViewHolder(holder: ZonasAdapter.ViewHolder, position: Int) {
        holder.bind(listaZonas[position])
    }

    override fun getItemCount(): Int {
        return listaZonas.size
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        clickLIstener = listener
    }

    override fun onClick(p0: View?) {
        clickLIstener?.onClick(p0)
    }
}