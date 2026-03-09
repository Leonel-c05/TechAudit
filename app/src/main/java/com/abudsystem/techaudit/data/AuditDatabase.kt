package com.abudsystem.techaudit.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import androidx.room.TypeConverters
import com.abudsystem.techaudit.model.AuditItem
import com.abudsystem.techaudit.model.Laboratorio

@Database(entities = [AuditItem::class, Laboratorio::class], version = 6, exportSchema = false)
@TypeConverters(Converters::class)

abstract class AuditDatabase: RoomDatabase() {
    abstract fun auditDao(): AuditDao
    abstract fun laboratorioDAO(): LaboratorioDAO

    companion object{
        @Volatile
        private var INSTANCE: AuditDatabase? = null

        fun getDatabase(context: Context): AuditDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AuditDatabase::class.java,
                    "techaudit_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}