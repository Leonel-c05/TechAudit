package com.abudsystem.techaudit.data

import androidx.room.TypeConverter
import com.abudsystem.techaudit.model.AuditStatus

class Converters {

    //De Enum a String
    @TypeConverter
    fun fromStatus(status: AuditStatus): String {
        return status.name
    }

    //De String a Enum
    @TypeConverter
    fun toStatus(value:String): AuditStatus {
        return try {
            AuditStatus.valueOf(value)
        } catch (e: IllegalArgumentException) {
            AuditStatus.PENDIENTE //Valor por defecto si falla
        }
    }
}