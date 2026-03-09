package com.abudsystem.techaudit

import android.app.Application
import com.abudsystem.techaudit.data.AuditDatabase
import com.abudsystem.techaudit.model.AuditItem
import com.abudsystem.techaudit.model.AuditStatus
import com.abudsystem.techaudit.model.Laboratorio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import java.util.Date
import java.util.UUID

class TechAuditApp : Application() {

    val database by lazy { AuditDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()

        val laboratorioDao = database.laboratorioDAO()
        val equipoDao = database.auditDao()

        CoroutineScope(Dispatchers.IO).launch {
        /*
        Agregar laboratorios nuevos
         */
            // ---------- LABORATORIOS ----------
            val labs = laboratorioDao.obtenerLaboratorios()

            if (labs.size < 4) {

                laboratorioDao.insertar(Laboratorio(nombre = "Lab Redes", edificio = "A"))
                laboratorioDao.insertar(Laboratorio(nombre = "Lab Software", edificio = "B"))
                laboratorioDao.insertar(Laboratorio(nombre = "Lab Hardware", edificio = "C"))
                laboratorioDao.insertar(Laboratorio(nombre = "Lab Electrónica", edificio = "D"))
            }
            // ---------- EQUIPOS ----------
            val equipos = equipoDao.getAllItems().first()

            if (equipos.size < 4) {

                equipoDao.insert(
                    AuditItem(
                        id = UUID.randomUUID().toString(),
                        nombre = "Laptop HP",
                        fechaRegistro = Date().toString(),
                        laboratorioId = "1",
                        estado = AuditStatus.OPERATIVO,
                        notas = "Equipo funcionando"
                    )
                )

                equipoDao.insert(
                    AuditItem(
                        id = UUID.randomUUID().toString(),
                        nombre = "PC Dell",
                        fechaRegistro = Date().toString(),
                        laboratorioId = "2",
                        estado = AuditStatus.PENDIENTE,
                        notas = "Pendiente revisión"
                    )
                )

                equipoDao.insert(
                    AuditItem(
                        id = UUID.randomUUID().toString(),
                        nombre = "Router Cisco",
                        fechaRegistro = Date().toString(),
                        laboratorioId = "3",
                        estado = AuditStatus.OPERATIVO,
                        notas = "Red estable"
                    )
                )

                equipoDao.insert(
                    AuditItem(
                        id = UUID.randomUUID().toString(),
                        nombre = "Osciloscopio",
                        fechaRegistro = Date().toString(),
                        laboratorioId = "4",
                        estado = AuditStatus.PENDIENTE,
                        notas = "Necesita calibración"
                    )
                )
            }
        }
    }
}