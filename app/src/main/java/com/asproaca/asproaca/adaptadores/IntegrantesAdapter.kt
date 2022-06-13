package com.asproaca.asproaca.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.AlertaIntegrantesBinding
import com.asproaca.asproaca.modelos.IntegrantesFamilia

class IntegrantesAdapter(
    private val listaIntegrantes: ArrayList<IntegrantesFamilia>,
    val context: Context
) :
    RecyclerView.Adapter<IntegrantesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AlertaIntegrantesBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaIntegrantes[position])
    }

    class ViewHolder(private val binding: AlertaIntegrantesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(integrantes: IntegrantesFamilia) {
            binding.idTituloOtros.visibility = View.GONE
            binding.idConteoOtros.visibility = View.GONE
            binding.idBtnAgregarIntegranteAlerta.visibility = View.GONE



            binding.idTxtEdadOtroIntegrante.setText(integrantes.edad)

            //binding.idSpinerGeneroOtroIntegrante.setText(integrantes.genero)
            //binding.idSpineNivelAcademicoOtroIntegrante. setText(integrantes.nivelAcademico)
            if (integrantes.tieneDiscapacidad == "Si") {
                binding.idSiDiscapacidadAlerta.isChecked = true
                integrantes.tieneDiscapacidad = "Si"
                binding.idLayoutDiscapacidad.visibility = View.VISIBLE
            } else {
                binding.idNoDiscapacidadAlerta.isChecked = true
                integrantes.tieneDiscapacidad = "No"
                binding.idLayoutDiscapacidad.visibility = View.GONE
            }
            binding.idTxtDiscapacidadOtroIntegrante.setText(integrantes.discapacidad)
        }

    }

    override fun getItemCount(): Int {
        return listaIntegrantes.size
    }
}