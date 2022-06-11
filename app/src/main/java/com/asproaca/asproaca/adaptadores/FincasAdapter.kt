package com.asproaca.asproaca.adaptadores

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.asproaca.asproaca.Preferencias
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.AlertCustomDeleteBinding
import com.asproaca.asproaca.databinding.AlertaEliminarFincaBinding
import com.asproaca.asproaca.databinding.ItemFincasBinding
import com.asproaca.asproaca.modelos.Finca
import com.campo.campocolombiano.design.constantes.Constantes2
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class FincasAdapter(private val listFarm: ArrayList<Finca>, val context: Context) :
    RecyclerView.Adapter<FincasAdapter.ViewHolder>(), View.OnClickListener {
    private var clickLIstener: View.OnClickListener? = null

    private var preferencias: Preferencias? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFincasBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: FincasAdapter.ViewHolder, position: Int) {
        holder.bind(listFarm[position])
    }

    class ViewHolder(private val binding: ItemFincasBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Finca) {
            binding.idTxtNombreFinca.text = event.nombre_finca
            binding.idTxtVeredaFinca.text = event.vereda_finca
            binding.idTxtZona.text = event.zona
            binding.idTxtAntiguedadFinca.text = event.antiguedad_finca
            binding.idTxtAreaTotal.text = event.area_total
            try {
                if (Constantes2.encargadoRegistro == "Administrador") {
                    binding.idModificarUsuario.visibility = View.VISIBLE
                    binding.idEliminarUsuario.visibility = View.VISIBLE
                } else {
                    binding.idModificarUsuario.visibility = View.GONE
                    binding.idEliminarUsuario.visibility = View.GONE
                }

                if (event.estadoActualizar == true) {
                    binding.idBtnActualizarDatosNuevo.visibility = View.VISIBLE
                } else {
                    binding.idBtnActualizarDatosNuevo.visibility = View.GONE
                }

                binding.idEliminarUsuario.setOnClickListener {
                    val custom_alertDelete =
                        AlertaEliminarFincaBinding.inflate(LayoutInflater.from(context))
                    val alertDialog = AlertDialog.Builder(context).apply {
                        setView(custom_alertDelete.root)
                        setCancelable(false)
                    }.create()
                    custom_alertDelete.idTitulo.setText(event.nombre_finca)

                    custom_alertDelete.idBtnAliminar.setOnClickListener {
                        Firebase.firestore.collection("Fincas")
                            .document()
                            .delete().addOnCompleteListener {task->
                                if (task.isSuccessful) {
                                    alertDialog.dismiss()
                                    Toast.makeText(context, "Finca eliminada exitosamente", Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(context, "No se ha eliminado exitosamente", Toast.LENGTH_SHORT).show()
                                }
                            }.addOnFailureListener {
                                alertDialog.dismiss()
                                Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    }
                    custom_alertDelete.idBtnCancelar.setOnClickListener { alertDialog.dismiss() }

                    alertDialog.show()
                }
            } catch (e: Exception) {
                Log.e("Errorrrr", e.toString())
            }
        }

        var preferencias1: Preferencias? = null
    }

    override fun getItemCount(): Int {
        return listFarm.size
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        clickLIstener = listener
    }

    override fun onClick(p0: View?) {
        clickLIstener?.onClick(p0)
    }
}