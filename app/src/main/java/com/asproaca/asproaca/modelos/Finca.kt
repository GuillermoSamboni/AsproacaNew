package com.asproaca.asproaca.modelos

data class Finca(
    val nombre_finca: String? = null,
    val coordenada_x: String? = null,
    val coordenada_y: String? = null,
    val vereda_finca: String? = null,
    val zona: String? = null,
    val antiguedad_finca: String? = null,
    val historia_finca: String? = null,
    val realiza_quema: String? = null,
    val creado: String? = null,
    val certificaciones: String? = null,
    val cantidad_viviendas: String? = null,
    val zona_riesgo: String? = null,
    val tenencia_de_la_tierra: String? = null,
    val area_total: String? = null,

    val datos_casa: Casa? = null,

    val datos_sociales: MutableList<Persona>? = null,

    val datos_productivos: DatosProductivos? = null,

    val datos_economicos: DatosEconomicos? = null,
    val datos_ambientales: DatosAmbientales? = null,
)
