package com.abudsystem.techaudit.data
/*
El dao de la segunda entidad Laboratorio
 */
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.abudsystem.techaudit.model.Laboratorio
 @Dao
interface LaboratorioDAO {
     @Insert
     suspend fun insertar(laboratorio: Laboratorio)

     @Query("SELECT * FROM laboratorios")
     suspend fun obtenerLaboratorios(): List<Laboratorio>
}