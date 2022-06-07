package com.asproaca.asproaca.modelos

data class CocinaCasa(
    var tipo_estufa: String? = null,
    var tipo_combustible_alimentos: String? = null,
    var tipo_combustible_industriales: String? = null,
    var fuente_agua_consumo_domestico: String? = null,
    var fuente_agua_consumo_industrial: String? = null,
    var tratamiento_agua_ducha: String? = null,
    var tratamiento_agua_lavadero: String? = null,
    var tratamiento_agua_lavaplatos: String? = null,
    var tratamiento_agua_residual: String? = null)
