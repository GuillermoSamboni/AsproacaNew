package com.asproaca.asproaca.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.R
import com.asproaca.asproaca.databinding.ItemPecuariasBinding
import com.asproaca.asproaca.modelos.Animales
import com.campo.campocolombiano.design.constantes.Constantes2

class DatosPecuariosModificarAdapter(private var listaAnimales: MutableList<Animales>,private val context:Context):
    RecyclerView.Adapter<DatosPecuariosModificarAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DatosPecuariosModificarAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPecuariasBinding.inflate(inflater, parent, false)
       return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DatosPecuariosModificarAdapter.ViewHolder, position: Int) {
        holder.bind(listaAnimales[position],listaAnimales,position,context)
    }


    override fun getItemCount(): Int {
        return listaAnimales.size
    }
    class ViewHolder(private val binding: ItemPecuariasBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(animales: Animales, listaAnimales: MutableList<Animales>, position: Int,context: Context) {

            binding.idTxtNumeroAnimalesFinca.setText("${animales.cantidad_animal}")
            binding.idTxtAreaTotalAnimalesFinca.setText("${animales.area_crianza_animal}")

            val itemsAnimales = arrayOf(
                "vacas",
                "cerdo",
                "cuí",
                "conejos",
                "galinnas",
                "ponedoras",
                "peces",
                "pollo",
                "engorde",
                "otro"
            )

            val arrayAdapterAnimales = ArrayAdapter(
                context,
                R.layout.support_simple_spinner_dropdown_item,
                itemsAnimales
            )
            binding.idSpinerAnimalesFinca.adapter = arrayAdapterAnimales

            binding.idBtnContinuarProceso.setOnClickListener {
            animales.area_crianza_animal =  binding.idTxtAreaTotalAnimalesFinca.text.toString()
            animales.cantidad_animal =  binding.idTxtNumeroAnimalesFinca.text.toString()
            animales.nombre_animal = binding.idSpinerAnimalesFinca.selectedItem.toString()

                listaAnimales[position] = animales
                Constantes2.listaAnimales = listaAnimales

            }

        }

    }

}