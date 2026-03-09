package com.abudsystem.techaudit.data
import com.abudsystem.techaudit.model.AuditItem
import kotlinx.coroutines.flow.Flow;

class AuditRepository(private val auditDao: AuditDao){

    val allItems: Flow<List<AuditItem>> = auditDao.getAllItems()

    fun getItemsPorLaboratorio(laboratorioId: String): Flow<List<AuditItem>> {
        return auditDao.getItemsPorLaboratorio(laboratorioId)
    }

    suspend fun insert(item: AuditItem) {
        auditDao.insert(item)
    }

    suspend fun update(item: AuditItem) {
        auditDao.update(item)
    }

    suspend fun delete(item: AuditItem) {
        auditDao.delete(item)
    }
}