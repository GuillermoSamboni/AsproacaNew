package com.asproaca.asproaca.adaptadores

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asproaca.asproaca.databinding.FragmentDatosProductivosBinding
import com.asproaca.asproaca.databinding.ItemProductivosBinding
import com.asproaca.asproaca.modelos.Productivos
import kotlinx.coroutines.NonDisposableHandle.parent

class ProduccionAgicolaAdapter(
    private val listaProduccion: ArrayList<Productivos>,
    val context: Context
) :
    RecyclerView.Adapter<ProduccionAgicolaAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductivosBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaProduccion[position])
    }

    class ViewHolder(private val binding: ItemProductivosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productivos: Productivos) {
            binding.idTxtAreaProductivaTota.setText(productivos.area_productiva_total)
            binding.idTxtEdadProducto.setText(productivos.edad_producto)
            if (productivos.bodega_agroquimicos == "Si") {
                binding.idSiTieneAgroquimicos.isChecked = true
            } else {
                binding.idNoTieneAgroquimicos.isChecked = true
            }
            if (productivos.semilla_modificada == "Si") {
                binding.idSiSemillasModificadas.isChecked = true
            } else {
                binding.idNoSemillasModificadas.isChecked = true
            }
            if (productivos.tiene_plagas == "Si") {
                binding.idSiATenidoPlagas.isChecked = true
                binding.idLayoutTipoPlagas.visibility = View.VISIBLE
                binding.idTxtTipoPlagas.setText(productivos.plagas)
            } else {
                binding.idSiATenidoPlagas.isChecked = true
            }
            if (productivos.tiene_enfermedades == "Si") {
                binding.idSiEnfermedades.isChecked = true
                binding.idLayoutEnfermedades.visibility = View.VISIBLE
                binding.idTxtEnfermedades.setText(productivos.enfermedades)
            } else {
                binding.idSiEnfermedades.isChecked = true
            }
            binding.idTxtFertilizantesUsados.setText(productivos.fertilizantes)
            binding.idTxtAgroquimicosUsados.setText(productivos.agroquimicos)

            if (productivos.usa_proteccion == "Si") {
                binding.idSiEquiposProteccion.isChecked = true
            } else {
                binding.idNoEquiposProteccion.isChecked = true
            }
            if (productivos.tiene_infraestructura == "Si") {
                binding.idSiEquiposInfraestructura.isChecked = true
            } else {
                binding.idNoEquiposInfraestructura.isChecked = true
            }
            binding.idTxtEquiposIndustriales.setText(productivos.equipos_idustriales)
            binding.idTxtNumeroLavadosCafe.setText(productivos.cantidad_lavados)
            binding.idTxtCantidadLotes.setText(productivos.cantidadLotes)

        }

    }

    override fun getItemCount(): Int {
        return listaProduccion.size
    }
}