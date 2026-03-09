package com.abudsystem.techaudit

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.abudsystem.techaudit.model.AuditItem
import com.abudsystem.techaudit.databinding.ActivityDetailBinding
import com.abudsystem.techaudit.model.AuditStatus


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //*1 Recuperamos el objeto enviado
        val item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("item", AuditItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("|Extra_item")

        }

        //2 mostrar datos del objeto esitente
        item?.let{
            mostrarDetalles(it)
        }

        enableEdgeToEdge()

        //setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun mostrarDetalles(item: AuditItem) {
        binding.tvDetalleNombre.text = item.nombre
        binding.tvDetalleId.text = "ID: ${item.id.substring(0,8)}" // Mostramos solo los primeros 8 caracteres del ID
        binding.tvDetalleFecha.text = item.fechaRegistro
        binding.tvDetalleNotas.text = item.notas.ifEmpty { "Sin notas" }

        // 3 logica visual segun el estado
        val color = when(item.estado) {
            AuditStatus.PENDIENTE -> Color.parseColor("#9e9e9e")
            AuditStatus.OPERATIVO -> Color.parseColor("#4CAF50")
            AuditStatus.DANIADO -> Color.parseColor("#f44336")
            AuditStatus.NO_ENCONTRADO -> Color.parseColor("#fff")


        }
        binding.viewHeaderStatus.setBackgroundColor(color)
        title = "Datelle de ${item.estado.name}"

    }



}