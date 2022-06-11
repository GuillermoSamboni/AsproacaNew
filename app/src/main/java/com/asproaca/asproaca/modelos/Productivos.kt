package com.asproaca.asproaca.modelos

data class Productivos(
    var nombre_producto: String? = null,
    var area_productiva_total: String? = null,
    var edad_producto: String? = null,
    var bodega_agroquimicos: String? = null,
    var proveedor_semilla: String? = null,
    var semilla_modificada: String? = null,
    var tiene_plagas: String? = null,
    var plagas: String? = null,
    var tiene_enfermedades: String? = null,
    var enfermedades: String? = null,

    var fertilizantes: String? = null,
    var agroquimicos: String? = null,
    var usa_proteccion: String? = null,
    var estado_proteccion: String? = null,
    var tiene_infraestructura: String? = null,
    var estado_infraestructura: String? = null,
    var tipo_secado: String? = null,
    var equipos_idustriales: String? = null,
    var cantidad_lavados: String? = null,
    var cantidadLotes: String? = null,
    var lotes: MutableList<LotesProduccion>? = null
)
