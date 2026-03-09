package com.abudsystem.techaudit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abudsystem.techaudit.adapter.AuditAdapter
import com.abudsystem.techaudit.adapter.LaboratorioAdapter
import com.abudsystem.techaudit.databinding.ActivityMainBinding
import com.abudsystem.techaudit.model.Laboratorio
import com.abudsystem.techaudit.ui.AuditViewModel
import kotlinx.coroutines.launch
import com.abudsystem.techaudit.network.RetrofitInstance

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AuditAdapter
    private lateinit var laboratorioAdapter: LaboratorioAdapter

    private val viewModel: AuditViewModel by viewModels()

    private var laboratorioSeleccionado: Laboratorio? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerEquipos()
        setupRecyclerLaboratorios()
        configurarDeslizarParaBorrar()

        cargarLaboratorios()

        // OBSERVA EQUIPOS
        viewModel.allItems.observe(this) { lista ->

            laboratorioSeleccionado?.let { lab ->

                val filtrados = lista.filter {
                    it.laboratorioId == lab.id.toString()
                }

                adapter.actualizarLista(filtrados)
            }
        }

        binding.fabAgregar.setOnClickListener {

            val laboratorio = laboratorioSeleccionado

            if (laboratorio == null) {
                Toast.makeText(this, "Selecciona un laboratorio primero", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, AddEditActivity::class.java)
            intent.putExtra("LAB_ID", laboratorio.id)
            startActivity(intent)
        }

        // --------------------------
        // PRUEBA RETROFIT (API REST)
        // --------------------------

        lifecycleScope.launch {

            try {

                val posts = RetrofitInstance.api.obtenerPosts()

                Toast.makeText(
                    this@MainActivity,
                    "Retrofit OK: ${posts.size} datos recibidos",
                    Toast.LENGTH_LONG
                ).show()

            } catch (e: Exception) {

                Toast.makeText(
                    this@MainActivity,
                    "Error conectando API",
                    Toast.LENGTH_LONG
                ).show()

            }

        }

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
    }

    private fun setupRecyclerEquipos() {

        adapter = AuditAdapter(mutableListOf()) { itemSeleccionado ->

            val intent = Intent(this, AddEditActivity::class.java)
            intent.putExtra("EXTRA_ITEM", itemSeleccionado)
            startActivity(intent)
        }

        binding.rvAuditoria.adapter = adapter
        binding.rvAuditoria.layoutManager = LinearLayoutManager(this)
    }

    private fun setupRecyclerLaboratorios() {

        laboratorioAdapter = LaboratorioAdapter(emptyList()) { laboratorio ->

            laboratorioSeleccionado = laboratorio

            Toast.makeText(
                this,
                "Laboratorio: ${laboratorio.nombre}",
                Toast.LENGTH_SHORT
            ).show()

            viewModel.allItems.value?.let { lista ->

                val filtrados = lista.filter {
                    it.laboratorioId == laboratorio.id.toString()
                }

                adapter.actualizarLista(filtrados)
            }
        }

        binding.rvLaboratorios.adapter = laboratorioAdapter

        binding.rvLaboratorios.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun cargarLaboratorios() {

        lifecycleScope.launch {

            val dao = (application as TechAuditApp).database.laboratorioDAO()
            val labs = dao.obtenerLaboratorios()

            laboratorioAdapter.actualizarLista(labs)

            if (labs.isNotEmpty()) {

                laboratorioSeleccionado = labs[0]

                viewModel.allItems.value?.let { lista ->

                    val filtrados = lista.filter {
                        it.laboratorioId == labs[0].id.toString()
                    }

                    adapter.actualizarLista(filtrados)
                }
            }
        }
    }

    private fun configurarDeslizarParaBorrar() {

        val swipeHandler = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val posicion = viewHolder.adapterPosition
                val item = adapter.listaAuditoria[posicion]

                viewModel.delete(item)

                Toast.makeText(
                    this@MainActivity,
                    "Equipo eliminado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val helper = ItemTouchHelper(swipeHandler)
        helper.attachToRecyclerView(binding.rvAuditoria)
    }
}