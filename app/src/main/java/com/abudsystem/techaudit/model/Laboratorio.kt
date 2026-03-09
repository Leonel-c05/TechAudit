package com.abudsystem.techaudit.model
/*
creacion de la nueva entidad para la nueva tabla en la base de datos
 */
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "laboratorios")
data class Laboratorio(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,

    val edificio: String
)