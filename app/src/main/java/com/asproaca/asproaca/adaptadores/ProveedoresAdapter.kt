package com.asproaca.asproaca.adaptadores

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.asproaca.asproaca.Preferencias
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.FormularioProveedorBinding
import com.asproaca.asproaca.databinding.ItemProveedoresBinding
import com.asproaca.asproaca.modelos.Proveedor
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProveedoresAdapter(private val listaProveedores: ArrayList<Proveedor>, val context: Context) :
    RecyclerView.Adapter<ProveedoresAdapter.ViewHolder>(), View.OnClickListener {

    private var clickLIstener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProveedoresBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)

        return ViewHolder(binding, parent.context)
    }

    class ViewHolder(private val binding: ItemProveedoresBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(proveedor: Proveedor) {
            binding.idNombreProveedor.text = proveedor.nombre_proveedor
            binding.idNitProveedor.text = proveedor.nit_proveedor
            binding.idTelefonoProveedor.text = proveedor.telefono_roveedor

            binding.idEliminarProveedor.setOnClickListener {
                val alertConfirm = AlertDialog.Builder(context)
                alertConfirm.setTitle("Eliminar Proveedor")
                alertConfirm.setMessage("Esta seguro de querer eliminar al proveedor ${proveedor.nombre_proveedor}")
                alertConfirm.setCancelable(false)
                alertConfirm.setPositiveButton("Si, Eliminar") { _, _ ->
                    Firebase.firestore.collection("Proveedores")
                        .document(proveedor.nombre_proveedor.toString()).delete()
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(context, "Proveedor Eliminado", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "No se ha eliminado este provedor",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }.addOnFailureListener {
                            Toast.makeText(
                                context,
                                "error=> ${it.message.toString()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
                alertConfirm.setNegativeButton("No, Cancelar") { _, _ ->
                    alertConfirm.setCancelable(
                        true
                    )
                }
                alertConfirm.show()
            }

            binding.idBtnModificarProveedor.setOnClickListener {
                val alertProveedor =
                    FormularioProveedorBinding.inflate(LayoutInflater.from(context))
                val alertDialog = AlertDialog.Builder(context).apply {
                    setView(alertProveedor.root)
                    setCancelable(true)
                }.create()
                alertProveedor.idTitulo.setText("Modificar Proveedor")
                alertProveedor.idTxtNombreProveedor.setText(proveedor.nombre_proveedor)
                alertProveedor.idTxtNitProveedor.setText(proveedor.nit_proveedor)
                alertProveedor.idTxtTelefonoProvedor.setText(proveedor.telefono_roveedor)
                alertProveedor.idBtnRegistrarProveedor.setText("Modificar")

                alertProveedor.idBtnRegistrarProveedor.setOnClickListener {
                    try {
                        Firebase.firestore.collection("Proveedores")
                            .document(proveedor.id_proveedor.toString()).update(
                                mapOf(
                                    "nombre_proveedor" to alertProveedor.idTxtNombreProveedor.text.toString(),
                                    "nit_proveedor" to alertProveedor.idTxtNitProveedor.text.toString(),
                                    "telefono_roveedor" to alertProveedor.idTxtTelefonoProvedor.text.toString()
                                )
                            ).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Proveedor modificado Correctamente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    alertDialog.dismiss()
                                }
                            }
                    } catch (e: Exception) {
                        //
                    }
                }
                alertDialog.show()
            }
        }

        val card = binding.idCardListaProveedores


    }

    override fun onBindViewHolder(holder: ProveedoresAdapter.ViewHolder, position: Int) {
        holder.bind(listaProveedores[position])
        holder.card.animation = AnimationUtils.loadAnimation(context, R.anim.animacion_cards)
    }

    override fun getItemCount(): Int {
        return listaProveedores.size
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        clickLIstener = listener
    }

    override fun onClick(p0: View?) {
        clickLIstener?.onClick(p0)
    }
}
