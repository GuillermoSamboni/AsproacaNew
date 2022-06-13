package com.asproaca.asproaca.adaptadores

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asproaca.asproaca.databinding.ItemPecuariasBinding
import com.asproaca.asproaca.modelos.Animales

class DatosPecuariosModificarAdapter(private var listaAnimales: MutableList<Animales>):
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
        holder.bind(listaAnimales[position],listaAnimales,position)
    }


    override fun getItemCount(): Int {
        return listaAnimales.size
    }
    class ViewHolder(private val binding: ItemPecuariasBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(animales: Animales, listaAnimales: MutableList<Animales>, position: Int) {

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

            binding.idBtnContinuarProceso.setOnClickListener {
            animales.area_crianza_animal =  binding.idTxtAreaTotalAnimalesFinca.text.toString()
            animales.cantidad_animal =  binding.idTxtNumeroAnimalesFinca.text.toString()
            animales.nombre_animal = binding.idSpinerAnimalesFinca.selectedItem.toString()


            }

        }

    }




}