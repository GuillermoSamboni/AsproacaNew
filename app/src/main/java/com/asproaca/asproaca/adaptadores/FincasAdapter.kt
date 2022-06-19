package com.asproaca.asproaca.adaptadores

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.asproaca.asproaca.Preferencias
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.AlertaEliminarFincaBinding
import com.asproaca.asproaca.databinding.DetalleFincaBinding
import com.asproaca.asproaca.databinding.ItemFincasBinding
import com.asproaca.asproaca.diseño.principal.ui.gestionFincas.ModificarFincaActivity
import com.asproaca.asproaca.modelos.Finca
import com.campo.campocolombiano.design.constantes.Constantes2
import com.google.firebase.firestore.FirebaseFirestore


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
                /*
                if (Constantes2.encargadoRegistro == "Administrador") {
                    binding.idModificarUsuario.visibility = View.VISIBLE
                    binding.idEliminarUsuario.visibility = View.VISIBLE
                } else {
                    binding.idModificarUsuario.visibility = View.GONE
                    binding.idEliminarUsuario.visibility = View.GONE
                }
                */

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
                        database = FirebaseFirestore.getInstance()
                        database!!.collection("Fincas").document("Fincas")
                            .collection("ActualizacionFinca")
                            .document("${event.idFinca}")
                            .delete().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    alertDialog.dismiss()
                                    Toast.makeText(
                                        context,
                                        "Finca eliminada exitosamente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "No se ha eliminado exitosamente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }.addOnFailureListener {
                                alertDialog.dismiss()
                                Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    }
                    custom_alertDelete.idBtnCancelar.setOnClickListener { alertDialog.dismiss() }

                    alertDialog.window?.setBackgroundDrawableResource(R.color.transparente)
                    alertDialog.window?.setWindowAnimations(R.style.dialog_animationButtonTo)
                    alertDialog.show()
                }

                binding.idBtnVerFinca.setOnClickListener {
                    val detalleFinca = DetalleFincaBinding.inflate(LayoutInflater.from(context))
                    val alertDialog = AlertDialog.Builder(context).apply {
                        setView(detalleFinca.root)
                        setCancelable(false)
                    }.create()

                    detalleFinca.idNombr4Finca.setText(event.nombre_finca)
                    detalleFinca.idVereda.setText(event.vereda_finca)
                    detalleFinca.idZona.setText(event.zona)
                    detalleFinca.idCoordenadaX.setText(event.coordenada_x)
                    detalleFinca.idCoordenadaY.setText(event.coordenada_y)
                    detalleFinca.idAniosEnFinca.setText(event.antiguedad_finca)
                    detalleFinca.idCambiosEnFinca.setText(event.historia_finca)
                    detalleFinca.idCertificaciones.setText(event.certificaciones)
                    detalleFinca.idAreaFinca.setText(event.area_total)
                    detalleFinca.idZonaRiesgo.setText(event.zona_riesgo)
                    detalleFinca.idQuemas.setText(event.realiza_quema)
                    detalleFinca.idTenenciaFinca.setText(event.tenencia_de_la_tierra)

                    /**
                     * Datos vivienda
                     */
                    detalleFinca.idServicioAcueducto.setText(event.datos_casa?.servicio_acueducto)
                    detalleFinca.idServicioAlcantarillado.setText(event.datos_casa?.servicio_alcantarillado)
                    detalleFinca.idServicioEnergia.setText(event.datos_casa?.servicio_electrico)
                    detalleFinca.idServicioInternet.setText(event.datos_casa?.servicio_internet)
                    detalleFinca.idTipoTecho.setText(event.datos_casa?.tipo_techo)
                    detalleFinca.idTipoPared.setText(event.datos_casa?.tipo_pared)
                    detalleFinca.idNumeroBanios.setText(event.datos_casa?.numero_banios)
                    detalleFinca.idTipoPiso.setText(event.datos_casa?.tipo_piso)
                    /**
                     * Datos cocina vivienda
                     */
                    detalleFinca.idTipoEstufa.setText(event.datos_casa?.datos_cocina?.tipo_estufa)
                    detalleFinca.idFuenteEnergiaDomesticas.setText(event.datos_casa?.datos_cocina?.tipo_combustible_alimentos)
                    detalleFinca.idFuenteEnergiaIndustrial.setText(event.datos_casa?.datos_cocina?.tipo_combustible_industriales)
                    detalleFinca.idFuenteAguaConsumoDomestico.setText(event.datos_casa?.datos_cocina?.fuente_agua_consumo_domestico)
                    detalleFinca.idConsumoAguaIndustrial.setText(event.datos_casa?.datos_cocina?.fuente_agua_consumo_industrial)
                    detalleFinca.idTRatamientoAguaDucha.setText(event.datos_casa?.datos_cocina?.tratamiento_agua_ducha)
                    detalleFinca.idTRatamientoAguaLavadero.setText(event.datos_casa?.datos_cocina?.tratamiento_agua_lavadero)
                    detalleFinca.idTRatamientoAguaLavaplatos.setText(event.datos_casa?.datos_cocina?.tratamiento_agua_lavaplatos)
                    detalleFinca.idTRatamientoAguasResiduales.setText(event.datos_casa?.datos_cocina?.tratamiento_agua_residual)
                    /**
                     * Datos núcleco familiar
                     */
                    detalleFinca.idNombreFamiliar.setText(event.datos_sociales?.nombre)
                    detalleFinca.idNumeroIdentificacion.setText(event.datos_sociales?.identificacion)
                    detalleFinca.idFechaNacimiento.setText(event.datos_sociales?.fechaNacimiento)
                    detalleFinca.idTelefono.setText(event.datos_sociales?.telefono)
                    detalleFinca.idCorreo.setText(event.datos_sociales?.correoElectronico)
                    detalleFinca.idNivelManejoDispositivos.setText(event.datos_sociales?.nivelManejoDispositivos)
                    detalleFinca.idTipoPoblacion.setText(event.datos_sociales?.tipoPoblacion)
                    detalleFinca.idNumeroIntegrantes.setText(event.datos_sociales?.numeroIntegrantes)
                    /**
                     * Datos de produccion agricola
                     */
                    detalleFinca.idNombreProducto.setText(event.datos_productivos?.get(0)?.nombre_producto.toString())
                    detalleFinca.idAreaProductivaTotal.setText(event.datos_productivos?.get(0)?.area_productiva_total.toString())
                    detalleFinca.idEdadCultivo.setText(event.datos_productivos?.get(0)?.edad_producto.toString())
                    detalleFinca.idBodegaAgroquimicos.setText(event.datos_productivos?.get(0)?.bodega_agroquimicos.toString())
                    detalleFinca.idProveedorSemilla.setText(event.datos_productivos?.get(0)?.proveedor_semilla.toString())
                    detalleFinca.idAtenidoPlagas.setText(event.datos_productivos?.get(0)?.tiene_plagas.toString())
                    detalleFinca.idPlagas.setText(event.datos_productivos?.get(0)?.plagas.toString())
                    detalleFinca.idHaTenidoEnfermedades.setText(event.datos_productivos?.get(0)?.tiene_enfermedades.toString())
                    detalleFinca.idEnfermedades.setText(event.datos_productivos?.get(0)?.enfermedades.toString())
                    //-------------------------------------------------------------------------------
                    detalleFinca.idFertilizantesUsados.setText(event.datos_productivos?.get(0)?.fertilizantes.toString())
                    detalleFinca.idAgroquimicosUsados.setText(event.datos_productivos?.get(0)?.agroquimicos.toString())
                    detalleFinca.idUsaEquiposProteccion.setText(event.datos_productivos?.get(0)?.usa_proteccion.toString())
                    detalleFinca.idEstadoEquipoProteccion.setText(event.datos_productivos?.get(0)?.estado_proteccion.toString())
                    detalleFinca.idInfraestructura.setText(event.datos_productivos?.get(0)?.tiene_infraestructura.toString())
                    detalleFinca.idEstadoInfraestructura.setText(event.datos_productivos?.get(0)?.estado_infraestructura.toString())
                    detalleFinca.idSecadoCafe.setText(event.datos_productivos?.get(0)?.tipo_secado.toString())
                    //-------------------------------------------------------------------------------
                    detalleFinca.idEquiposIndustriales.setText(event.datos_productivos?.get(0)?.equipos_idustriales.toString())
                    detalleFinca.idNumeroLavadosCultivo.setText(event.datos_productivos?.get(0)?.cantidad_lavados.toString())
                    detalleFinca.idCantidadLotes.setText(event.datos_productivos?.get(0)?.cantidadLotes.toString())
                    /**
                     * Datos de produccion pecuaria
                     */
                    if (event.datos_animal!!.isEmpty()) {
                        detalleFinca.idVerTxtNoHayDatosAnimales.visibility = View.VISIBLE
                        detalleFinca.idOcultarDatosAnimales.visibility = View.GONE
                    } else {
                        detalleFinca.idNombreAnimal.setText(event.datos_animal.get(0).nombre_animal.toString())
                        detalleFinca.idNumeroAnimales.setText(event.datos_animal.get(0).cantidad_animal.toString())
                        detalleFinca.idAreaCrianzaAnimal.setText(event.datos_animal.get(0).area_crianza_animal.toString())
                    }

                    /**
                     * Datos de trabajadores
                     */
                    detalleFinca.idTipoContrato.setText(event.datos_trabajadores?.tipo_contratacion)
                    detalleFinca.idNumeroTrabajadores.setText(event.datos_trabajadores?.cantidad_trabajadores_contratados)
                    detalleFinca.idCantidadPagoEfectivo.setText(event.datos_trabajadores?.cant_pago_dinero)
                    detalleFinca.idCantidadPagoEspecie.setText(event.datos_trabajadores?.cant_pago_especie)
                    detalleFinca.idCantidadHorasLaborales.setText(event.datos_trabajadores?.horario_laboral)
                    detalleFinca.idDescripcionObservaciones.setText(event.datos_trabajadores?.descripcion_adicional)
                    detalleFinca.idTieneAlojamiento.setText(event.datos_trabajadores?.tiene_alojamiento)
                    detalleFinca.idEstadoAlojamiento.setText(event.datos_trabajadores?.estado_alojamiento_trabajadores)
                    /**
                     * Datos ambientales
                     */
                    detalleFinca.idTieneAguaNatural.setText(event.datos_ambientales?.ecosistemas_naturales_acuaticos)
                    detalleFinca.idNombreAguaNatural.setText(event.datos_ambientales?.ecosistemas_naturales_acuaticos)
                    detalleFinca.idTieneAcuaArtificial.setText(event.datos_ambientales?.tiene_ecosistema_acuaticos_artificiales)
                    detalleFinca.idNombreAcuaArtificial.setText(event.datos_ambientales?.ecosistemas_artificial_acuaticos)
                    detalleFinca.idAreaEcosistemaTerrestre.setText(event.datos_ambientales?.tiene_ecositemas_terrestes_naturales)
                    detalleFinca.idAreaEcosistemaTerrestre.setText(event.datos_ambientales?.area_ecosistemas_terrestre_natural)
                    detalleFinca.idConsesionAgua.setText(event.datos_ambientales?.consesion_agua)
                    detalleFinca.idTipoArboles.setText(event.datos_ambientales?.tipo_arboles_descripcion)
                    detalleFinca.idTratamientoResiduos.setText(event.datos_ambientales?.tratamiento_basura)
                    detalleFinca.idSeparacionResiduos.setText(event.datos_ambientales?.separacion_basura)
                    detalleFinca.idCoberturaSuelos.setText(event.datos_ambientales?.cobertura_suelos)
                    detalleFinca.idAreasErosion.setText(event.datos_ambientales?.areas_con_erosion)
                    detalleFinca.idEvidenciaRemocion.setText(event.datos_ambientales?.areas_con_evidencias_remocion)
                    detalleFinca.idAnimalesEnCautiverio.setText(event.datos_ambientales?.animales_silvestres_en_cautivero)
                    detalleFinca.idCaptacionAguaLluvia.setText(event.datos_ambientales?.captacion_agua_lluvia)

                    detalleFinca.idBtnCerrar.setOnClickListener {
                        alertDialog.dismiss()
                    }
                    alertDialog.window?.setBackgroundDrawableResource(R.color.transparente)
                    alertDialog.window?.setWindowAnimations(R.style.dialog_animationButtonTo)
                    alertDialog.show()
                }

                binding.idBtnModificarFincaa.setOnClickListener {
                    Constantes2.listaDatosFinca = event
                    Constantes2.idFinca = event.idFinca
                    Constantes2.crearNuevaFinca = false

                    val pasar = Intent(context, ModificarFincaActivity::class.java)
                    //pasar.putExtra("Finca", event.toString())
                    context.startActivity(pasar)
                }

                binding.idBtnActualizarDatosNuevo.setOnClickListener {
                    Constantes2.crearNuevaFinca = true
                    Constantes2.listaDatosFinca = event
                    Constantes2.idFinca = event.idFinca

                    val pasar = Intent(context, ModificarFincaActivity::class.java)
                    //pasar.putExtra("Finca", event.toString())
                    context.startActivity(pasar)

                }

            } catch (e: Exception) {
                Log.e("Errorrrr", e.toString())
            }
        }

        var preferencias1: Preferencias? = null
        var database: FirebaseFirestore? = null
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