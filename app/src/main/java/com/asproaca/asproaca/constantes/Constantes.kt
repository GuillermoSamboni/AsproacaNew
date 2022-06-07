package com.campo.campocolombiano.design.constantes

import com.asproaca.asproaca.modelos.DatosSociales


class Constantes {
    companion object {
        //userlogin
        var admin = false
        var internet = false

        //Datos de finca basicos
        var nombreFinca: String? = null
        var nombrePropietario: String? = null
        var identificacion: String? = null
        var telefono: String? = null
        var vereda: String? = null
        var direccionFinca: String? = null
        var tamanoFinca: String? = null
        var antiguedadFinca: String? = null

        //dats sociales
        var nombrePersona: String? = null
        var apellidoPersona: String? = null
        var fechaNacimiento: String? = null
        var identificacionPersona: String? = null
        var telefonoPersona: String? = null
        var correoPersona: String? = null
        var rolFamiliar: String? = null
        var edadPersona: String? = null
        var generoPersona: String? = null
        var nivelEstudio: String? = null

        //var listPersons: DataSocial? = null
        var listaPeronas: MutableList<DatosSociales>? = null

        //Datos Cultivos
        var areaProductiva: String? = null
        var tieneBodega: String? = null
        var utilizaFertilizantes: String? = null
        var equiposIndustriales: String? = null
        var cantidadAnimales: String? = null
        var cantidadTrabajadores: String? = null

        //Datos Ambientales
        var tieneAgua: String? = null
        var tipoAgua: String? = null
        var existenBosques: String? = null
        var existeTratamientoBasuras: String? = null
        var existenFuentesHidricas: String? = null
        var cantidadPuntosEcologicos: String? = null

        var nextAction = false

    }
}