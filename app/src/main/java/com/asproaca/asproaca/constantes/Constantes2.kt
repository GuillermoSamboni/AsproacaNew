package com.campo.campocolombiano.design.constantes

import com.asproaca.asproaca.modelos.*


class Constantes2 {
    companion object {
        var listaMunicipios: MutableList<String>? = null
        var listaZonas: MutableList<String>? = null
        var listaProveedores: MutableList<String>? = null
        var idUsuario: String? = null
        var idFincaPadre: String? = null
        var modificacionesFincas: MutableList<String> = mutableListOf()
        var encargadoRegistro: String? = null
        var EstadoActualizar: Boolean? = null
        var estadoModificar = false
        var listaDatosFinca: Finca? = null
        var idFinca: String? = null
        var crearNuevaFinca: Boolean? = false

        /**
         * Datos Básicos
         * */
        var nombre_finca: String? = null
        var coordenada_x: String? = null
        var coordenada_y: String? = null
        var vereda_finca: String? = null
        var zona: String? = null
        var municipio: String? = null
        var antiguedad_finca: String? = null
        var historia_finca: String? = null
        var realiza_quema: String? = null
        var creado: String? = null
        var certificaciones: String? = null
        var zona_riesgo: String? = null
        var tenencia_de_la_tierra: String? = null
        var area_total: String? = null

        /**
         * Datos Casa
         * */
        var servicio_acueducto: String? = null
        var servicio_alcantarillado: String? = null
        var servicio_electrico: String? = null
        var servicio_internet: String? = null
        var casa_costruida: String? = null
        var tipo_techo: String? = null
        var tipo_pared: String? = null
        var numero_banios: String? = null
        var tipo_piso: String? = null

        /**
         * Datos Cosina
         * */
        var tipo_estufa: String? = null
        var tipo_combustible_alimentos: String? = null
        var tipo_combustible_industriales: String? = null
        var fuente_comsumo_domestico: String? = null
        var fuente_comsumo_industrial: String? = null
        var tratamiento_agua_ducha: String? = null
        var tratamiento_lavadero: String? = null
        var tratamiento_lavaplatos: String? = null
        var tratamiento_agua_residual: String? = null

        /**
         * Datos SOCIALES
         * */
        var nombre: String? = null
        var identificacion: String? = null
        var fechaNacimiento: String? = null
        var telefono: String? = null
        var correoElectronico: String? = null
        var edad: String? = null
        var tipoPoblacion: String? = null
        var genero: String? = null
        var nivelAcademico: String? = null
        var numeroIntegrantes: String? = null
        var nivelManejoDispositivos: String? = null
        var listaIntegrantes: MutableList<IntegrantesFamilia>? = null

        /**
         * Datos Productivos
         * */
        var listaProductivos: MutableList<Productivos>? = null
        var listaLotes: MutableList<LotesProduccion>? = null

        var listaAnimales: MutableList<Animales>? = null

        // Datos Productivos Produccion
        var fertilizantes_usados: String? = null
        var agroquimicos_usados: String? = null
        var equipo_proteccion: String? = null
        var equipo_proteccion_estado: String? = null
        var cuenta_con_infraestructura: String? = null
        var estado_infraestrcutura: String? = null
        var tipo_de_secado_de_cafe: String? = null
        var equipos_industriales: String? = null
        var numero_lavados: String? = null

        /**
         * Datos Trabajadores
         * */
        var cantidad_trabajadores_contratados: String? = null
        var tipo_contratacion: String? = null
        var cant_pago_dinero: String? = null
        var cant_pago_especie: String? = null
        var horario_laboral: String? = null
        var descripcion_adicional: String? = null
        var tiene_alojamiento: String? = null
        var alojamiento_trabajadores: String? = null
        var estado_alojamiento_trabajadores: String? = null

        /**
         * Datos Ambientales
         */
        var tiene_ecosistema_acuaticos_naturales: String? = null
        var ecosistemas_naturales_acuaticos: String? = null
        var tiene_ecosistema_acuaticos_artificiales: String? = null
        var ecosistemas_artificial_acuaticos: String? = null
        var tiene_ecositemas_terrestes_naturales: String? = null
        var ecosistemas_terrestre_natural: String? = null
        var area_ecosistemas_terrestre_natural: String? = null
        var consesion_agua: String? = null
        var tipo_arboles_descripcion: String? = null
        var tratamiento_basura: String? = null
        var separacion_basura: String? = null
        var cobertura_suelos: String? = null
        var areas_con_erosion: String? = null
        var areas_con_evidencias_remocion: String? = null
        var animales_silvestres_en_cautivero: String? = null
        var captacion_agua_lluvia: String? = null
    }
}