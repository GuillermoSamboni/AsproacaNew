package com.asproaca.asproaca.modelos

data class Productivos(
    var nombre_producto: String? = null,
    var bodega_agroquimicos: String? = null,
    var area_productiva_total: String? = null,
    /**var producto_certificado: String? = null,
    var certificado: String? = null,**/
    var proveedor_semilla: String? = null,
    var semilla_modificada: String? = null,
    var edad_producto: String? = null,
    var tipo_plagas: String? = null,
)
