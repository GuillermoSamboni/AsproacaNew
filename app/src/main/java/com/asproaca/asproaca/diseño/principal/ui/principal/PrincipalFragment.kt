package com.asproaca.asproaca.diseño.principal.ui.principal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.asproaca.asproaca.Preferencias
import com.asproaca.asproaca.R
import com.asproaca.asproaca.databinding.FragmentPrincipalBinding
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.asproaca.asproaca.AsproacaNewAplication.Companion.preferencia

class PrincipalFragment : Fragment(R.layout.fragment_principal) {
    private lateinit var binding: FragmentPrincipalBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPrincipalBinding.bind(view)

        primerIngreso()

    }

    private fun mostrarTarjetas() {
        val tagets = mutableListOf<TapTarget>(
            TapTarget.forView(binding.idNumeroDeFincas, "Número de fincas registradas").apply {
                tintTarget(false)
                dimColor(R.color.verdeAsproaca)
                textColor(R.color.white)
                transparentTarget(true)
                targetCircleColor(R.color.white)
                cancelable(false)
            },
            TapTarget.forView(binding.idTxtHectareasTotales, "Hectáreas totales").apply {
                tintTarget(false)
                dimColor(R.color.verdeAsproaca)
                textColor(R.color.white)
                transparentTarget(true)
                targetCircleColor(R.color.white)
                cancelable(false)
            },
            TapTarget.forView(binding.idPromedioAntiguedad, "Promedio de antigüedad").apply {
                tintTarget(false)
                dimColor(R.color.verdeAsproaca)
                textColor(R.color.white)
                transparentTarget(true)
                targetCircleColor(R.color.white)
                cancelable(false)
            }
        )
        TapTargetSequence(requireActivity()).targets(tagets).start()
    }

    private fun primerIngreso() { if (preferencia.esPrimeraVezEnPrincipal(requireContext())) { mostrarTarjetas() } }

    override fun onDestroy() { super.onDestroy()
        binding = FragmentPrincipalBinding.inflate(layoutInflater)
    }
}