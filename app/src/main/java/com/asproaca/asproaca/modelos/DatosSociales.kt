package com.asproaca.asproaca.modelos

data class DatosSociales(
    var nombre: String? = null,
    var identificacion: String? = null,
    var fechaNacimiento: String? = null,
    var telefono: String? = null,
    var correoElectronico: String? = null,
    var tipoPoblacion: String? = null,
    var genero: String? = null,
    var nivelAcademico: String? = null,
    var numeroIntegrantes: String? = null,
    var nivelManejoDispositivos: String? = null,
    var otrosIntegrantes: MutableList<IntegrantesFamilia>? = null

)
