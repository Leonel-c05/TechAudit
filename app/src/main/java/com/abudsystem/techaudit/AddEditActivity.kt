package com.abudsystem.techaudit

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.abudsystem.techaudit.databinding.ActivityAddEditBinding
import com.abudsystem.techaudit.model.AuditItem
import com.abudsystem.techaudit.model.AuditStatus
import com.abudsystem.techaudit.model.Laboratorio
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class AddEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditBinding

    private var itemEditar: AuditItem? = null

    private var listaLaboratorios: List<Laboratorio> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("EXTRA_ITEM")) {
            itemEditar = if (android.os.Build.VERSION.SDK_INT >= 33) {
                intent.getParcelableExtra("EXTRA_ITEM", AuditItem::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra("EXTRA_ITEM")
            }
        }

        itemEditar?.let { item ->
            binding.etNombre.setText(item.nombre)

            binding.etNotas.setText(item.notas)

            val posicionSpinner = AuditStatus.values().indexOf(item.estado)
            binding.spEstado.setSelection(posicionSpinner)
        }

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        setupSpinnerEstado()
        cargarLaboratorios()

        binding.btnGuardar.setOnClickListener {
            guardarOActualizar()
        }
    }

    private fun setupSpinnerEstado() {

        val estados = AuditStatus.values()

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            estados
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spEstado.adapter = adapter
    }

    private fun cargarLaboratorios() {

        lifecycleScope.launch {

            val dao = (application as TechAuditApp).database.laboratorioDAO()
            listaLaboratorios = dao.obtenerLaboratorios()

            val nombres = listaLaboratorios.map { it.nombre }

            val adapter = ArrayAdapter(
                this@AddEditActivity,
                android.R.layout.simple_spinner_item,
                nombres
            )

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.spLaboratorio.adapter = adapter
        }
    }

    private fun guardarOActualizar() {

        val nombre = binding.etNombre.text.toString().trim()

        val notas = binding.etNotas.text.toString().trim()

        if (nombre.isBlank()) {
            Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show()
            return
        }

        val estadoSeleccionado = binding.spEstado.selectedItem as AuditStatus

        val laboratorioSeleccionado =
            listaLaboratorios[binding.spLaboratorio.selectedItemPosition]

        val database = (application as TechAuditApp).database

        lifecycleScope.launch {

            if (itemEditar == null) {

                val nuevoItem = AuditItem(
                    id = UUID.randomUUID().toString(),
                    nombre = nombre,
                    fechaRegistro = Date().toString(),
                    laboratorioId = laboratorioSeleccionado.id.toString(),
                    estado = estadoSeleccionado,
                    notas = notas
                )

                database.auditDao().insert(nuevoItem)

                Toast.makeText(
                    this@AddEditActivity,
                    "Equipo guardado",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val itemActualizado = itemEditar!!.copy(
                    nombre = nombre,
                    estado = estadoSeleccionado,
                    notas = notas
                )

                database.auditDao().update(itemActualizado)

                Toast.makeText(
                    this@AddEditActivity,
                    "Equipo actualizado",
                    Toast.LENGTH_SHORT
                ).show()
            }

            finish()
        }
    }
}