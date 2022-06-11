package com.asproaca.asproaca.adaptadores

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.asproaca.asproaca.Preferencias
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.AlertCustomDeleteBinding
import com.asproaca.asproaca.databinding.AlertCustomModifyBinding
import com.asproaca.asproaca.databinding.CustomCargaBinding
import com.asproaca.asproaca.databinding.UsersAdapterBinding
import com.asproaca.asproaca.modelos.Usuario
import com.campo.campocolombiano.design.constantes.Constantes2
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UsuariosAdapter(private val listaUsuarios: ArrayList<Usuario>, val context: Context) :
    RecyclerView.Adapter<UsuariosAdapter.ViewHolder>(), View.OnClickListener {
    private var clickLIstener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UsersAdapterBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding, parent.context)
    }

    class ViewHolder(private val binding: UsersAdapterBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(usuario: Usuario) {
            binding.idNombreUsuario.text = usuario.nombre + " " + usuario.apellido
            binding.idIdentificacionUsario.text = usuario.identificacion
            binding.idCorreoUsuario.text = usuario.correo
            binding.idTelefonoUsuario.text = usuario.telefono

            try {
                if (Constantes2.encargadoRegistro == "Administrador") {
                    binding.idEliminarUsuario.visibility = View.GONE
                    binding.idModificarUsuario.visibility = View.GONE
                } else {
                    binding.idEliminarUsuario.visibility = View.VISIBLE
                    binding.idModificarUsuario.visibility = View.VISIBLE
                }

            } catch (e: Exception) {
                Log.e("Errorrrr", e.toString())
            }

            binding.idEliminarUsuario.setOnClickListener {
                val custom_alertDelete =
                    AlertCustomDeleteBinding.inflate(LayoutInflater.from(context))
                val alertDialog = AlertDialog.Builder(context).apply {
                    setView(custom_alertDelete.root)
                    setCancelable(false)
                }.create()

                val dialogBinding = CustomCargaBinding.inflate(LayoutInflater.from(context))
                alertCarga = AlertDialog.Builder(context).apply {
                    setView(dialogBinding.root)
                    setCancelable(true)
                }.create()

                custom_alertDelete.idnombreApellido.setText(usuario.nombre.toString() + " " + usuario.apellido.toString())
                custom_alertDelete.idIdentificacion.setText(usuario.identificacion.toString())
                custom_alertDelete.idTelefono.setText(usuario.telefono.toString())
                custom_alertDelete.idCorreo.setText(usuario.correo.toString())

                custom_alertDelete.idBtnAliminar.setOnClickListener {
                    alertCarga.show()
                    Firebase.firestore.collection("Usuarios").document(usuario.idUsuario.toString())
                        .delete().addOnCompleteListener {
                            if (it.isSuccessful){
                                alertCarga.dismiss()
                                alertDialog.dismiss()
                                Toast.makeText(context, "Usuario eliminado exitosamente", Toast.LENGTH_SHORT).show()
                            }
                        }.addOnFailureListener {
                            alertDialog.dismiss()
                            alertCarga.dismiss()
                            Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                        }
                }
                custom_alertDelete.idBtnCancelar.setOnClickListener {
                    alertDialog.dismiss()
                    alertDialog.setCancelable(true)
                }
                alertDialog.show()
            }

            binding.idModificarUsuario.setOnClickListener {
                val items = listOf("Administrador", "Super Administrador")
                val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, items)

                val custom_alert = AlertCustomModifyBinding.inflate(LayoutInflater.from(context))
                val alertDialog = AlertDialog.Builder(context).apply {
                    setView(custom_alert.root)
                    setCancelable(false)
                }.create()

                custom_alert.idTxtModifyNombre.setText(usuario.nombre.toString())
                custom_alert.idTxtModifyApellido.setText(usuario.apellido.toString())
                custom_alert.idTxtModifyIdentificacion.setText(usuario.identificacion.toString())
                custom_alert.idTxtModifyTelefono.setText(usuario.telefono.toString())
                custom_alert.idTxtModifyPerfil.setText(usuario.rol.toString())
                custom_alert.idTxtModifyPerfil.setAdapter(adapter)

                val dialogBinding = CustomCargaBinding.inflate(LayoutInflater.from(context))
                alertCarga = AlertDialog.Builder(context).apply {
                    setView(dialogBinding.root)
                    setCancelable(false)
                }.create()
                alertCarga.window?.setBackgroundDrawableResource(R.color.color_transparent)

                custom_alert.idBtnModificar.setOnClickListener {
                    if (
                        custom_alert.idTxtModifyNombre.text.toString().isNotEmpty() &&
                        custom_alert.idTxtModifyApellido.text.toString().isNotEmpty() &&
                        custom_alert.idTxtModifyIdentificacion.text.toString().isNotEmpty() &&
                        custom_alert.idTxtModifyTelefono.text.toString().isNotEmpty() &&
                        custom_alert.idTxtModifyPerfil.text.toString().isNotEmpty()
                    ) {
                        if (custom_alert.idTxtModifyPerfil.text.toString() == "Administrador" || custom_alert.idTxtModifyPerfil.text.toString() == "Super Administrador") {
                            alertCarga.show()
                            Firebase.firestore.collection("Usuarios")
                                .document(usuario.idUsuario.toString())
                                .update(
                                    mapOf(
                                        "nombre" to custom_alert.idTxtModifyNombre.text.toString(),
                                        "apellido" to custom_alert.idTxtModifyApellido.text.toString(),
                                        "identificacion" to custom_alert.idTxtModifyIdentificacion.text.toString(),
                                        "telefono" to custom_alert.idTxtModifyTelefono.text.toString(),
                                        "rol" to custom_alert.idTxtModifyPerfil.text.toString(),
                                    )
                                ).addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        alertCarga.dismiss()
                                        alertDialog.dismiss()
                                        Toast.makeText(context, "Usuario Modificado correctamente", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Usuario no Actualizado",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }.addOnFailureListener {
                                    alertCarga.dismiss()
                                    alertDialog.dismiss()

                                    Toast.makeText(
                                        context,
                                        "Error en el servidor",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                        } else {
                            custom_alert.idLayoutPerfil.setError("Perfil o rol no permitido, asegurece de que no contenga espacios")
                        }
                    } else {
                        custom_alert.idTextoError.visibility = View.VISIBLE
                        custom_alert.idTextoError.setText("Asegurece de llenar todos los campos")
                    }
                }
                custom_alert.idBtnCancelar.setOnClickListener {
                    alertDialog.setCancelable(true)
                    alertDialog.dismiss()
                }
                alertDialog.show()
            }
        }

        val card = binding.idCardUsuariosLista
        private lateinit var alertCarga: AlertDialog
        var preferencias1: Preferencias? = null


    }

    override fun onBindViewHolder(holder: UsuariosAdapter.ViewHolder, position: Int) {
        holder.bind(listaUsuarios[position])
        holder.card.animation = AnimationUtils.loadAnimation(context, R.anim.animacion_cards)
    }

    override fun getItemCount(): Int {
        return listaUsuarios.size
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        clickLIstener = listener
    }

    override fun onClick(p0: View?) {
        clickLIstener?.onClick(p0)
    }


}