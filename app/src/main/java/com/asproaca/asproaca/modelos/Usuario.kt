package com.asproaca.asproaca.modelos

data class Usuario(
    val idUsuario: String? = null,
    var nombre: String? = null,
    var apellido: String? = null,
    var identificacion: String? = null,
    var telefono: String? = null,
    val rol: String? = null,
    var contrasena: String? = null,
    var correo: String? = null,
)
