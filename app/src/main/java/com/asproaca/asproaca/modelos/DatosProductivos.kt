package com.asproaca.asproaca.modelos

data class DatosProductivos(

    var lista_productivos:MutableList<Productivos>?=null,

    var fertilizantes_usados: String? = null,
    var agroquimicos_usados: String? = null,
    var usa_equipos_proteccion: String? = null,
    var estados_equipos_proteccion: String? = null,
    var tiene_infraestructura: String? = null,
    var estados_equipos_infraestructura: String? = null,
    var tipo_secado_cafe: String? = null,
    var equipos_inductriales: String? = null,
    var numero_lavados: String? = null,

    var lista_datos_animales:MutableList<Animales>?=null,

    //var produccion_poscosecha: Produccion_poscosecha? = null,

    var cantidad_trabajadores_contratados: String? = null,
    var tipo_contratacion: String? = null,
    var cant_pago_dinero: String? = null,
    var cant_pago_especie: String? = null,
    var horario_laboral: String? = null,

    var descripcion_adicional: String? = null,
    var alojamiento_trabajadores: String? = null,
    var estado_alojamiento_trabajadores: String? = null,


)
