package com.asproaca.asproaca.modelos

data class Casa(
    var servicio_acueducto: String? = null,
    var servicio_alcantarillado: String? = null,
    var servicio_electrico: String? = null,
    var servicio_internet: String? = null,
    var tipo_techo: String? = null,
    var tipo_pared: String? = null,
    var numero_banios: String? = null,
    var tipo_piso: String? = null,
    var datos_cocina: CocinaCasa? = null
)
