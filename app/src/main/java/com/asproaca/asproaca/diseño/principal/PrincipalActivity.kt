package com.asproaca.asproaca.diseño.principal


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.asproaca.asproaca.AsproacaNewAplication.Companion.preferencia
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.ActivityPrincipalBinding
import com.asproaca.asproaca.diseño.utilidades.Localizacion
import com.asproaca.asproaca.diseño.utilidades.Utilidades
import com.asproaca.asproaca.modelos.Municipios
import com.asproaca.asproaca.modelos.Proveedor
import com.asproaca.asproaca.modelos.ZonasModel
import com.campo.campocolombiano.design.constantes.Constantes2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PrincipalActivity : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPrincipalBinding
    lateinit var locationService: Localizacion


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val connectionWifi = Utilidades.obtenerEstadoDeRed(this)

        locationService = Localizacion(this)
        locationService.startRequest()

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                obtenerMunicipios()
                obtenerZonas()
                obtenerProveedores()
            }
        }

        setSupportActionBar(binding.appBarPrincipal.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout

        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_principal)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_fincas,
                R.id.nav_usuarios,
                R.id.nav_proveedores,
                R.id.nav_zonas,
                R.id.idCerrarSesion,
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (preferencia.obtenerRol()
                .isNotEmpty() && preferencia.obtenerRol() == "Super Administrador"
        ) {
            val botonAgregarFinca = findViewById<FloatingActionButton>(R.id.idBtnAnadirFica)
            botonAgregarFinca.visibility = View.GONE
        } else {
            val botonAgregarFinca = findViewById<FloatingActionButton>(R.id.idBtnAnadirFica)
            botonAgregarFinca.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.principal, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_principal)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun obtenerZonas() {
        Firebase.firestore.collection("Zonas").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val listaZonas = task.result.toObjects(ZonasModel::class.java)
                Constantes2.listaZonas = mutableListOf()
                listaZonas.forEach {
                    val zonas = ZonasModel(
                        it.idZona,
                        it.nombre_zona
                    )
                    Constantes2.listaZonas?.addAll(listOf(zonas.nombre_zona.toString()))
                }
            }
        }
    }

    private fun obtenerMunicipios() {
        Firebase.firestore.collection("Municipios").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val listaMunicipios = task.result.toObjects(Municipios::class.java)
                Constantes2.listaMunicipios = mutableListOf()
                listaMunicipios.forEach {
                    val muni = Municipios(
                        it.depto,
                        it.nombre
                    )
                    Constantes2.listaMunicipios?.addAll(listOf(muni.nombre.toString()))
                }
            }
        }
    }

    private fun obtenerProveedores() {
        Firebase.firestore.collection("Proveedores").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val listaProveedores = task.result.toObjects(Proveedor::class.java)
                Constantes2.listaMunicipios = mutableListOf()
                listaProveedores.forEach {
                    val muni = Proveedor(
                        it.id_proveedor,
                        it.nit_proveedor,
                        it.nombre_proveedor,
                        it.telefono_roveedor,
                    )
                    Constantes2.listaProveedores?.addAll(listOf(muni.nombre_proveedor.toString()))
                }
            }
        }
    }

}
