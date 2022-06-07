package com.asproaca.asproaca.modelos

data class DatosAmbientales(
    var tiene_ecosistema_acuaticos_naturales: String? = null,
    var ecosistemas_naturales_acuaticos: String? = null,
    var tiene_ecosistema_acuaticos_artificiales: String? = null,
    var ecosistemas_artificial_acuaticos: String? = null,
    var tiene_ecositemas_terrestes_naturales: String? = null,
    var ecosistemas_terrestre_natural: String? = null,

    var area_ecosistemas_terrestre_natural: String? = null,
    var consesion_agua: String? = null,
    var tipo_arboles_descripcion: String? = null,
    var tratamiento_basura: String? = null,
    var separacion_basura: String? = null,
    var cobertura_suelos: String? = null,
    var areas_con_erosion: String? = null,
    var areas_con_evidencias_remocion: String? = null,
    var animales_silvestres_en_cautivero: String? = null,
    var captacion_agua_lluvia: String? = null,
)
