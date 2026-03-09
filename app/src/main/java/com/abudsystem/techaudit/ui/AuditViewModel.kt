package com.abudsystem.techaudit.ui
import androidx.lifecycle.AndroidViewModel
import com.abudsystem.techaudit.data.AuditRepository
import com.abudsystem.techaudit.model.AuditItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.abudsystem.techaudit.TechAuditApp
import kotlinx.coroutines.launch

import android.app.Application



class AuditViewModel (application: Application) : AndroidViewModel(application){
    private val repository: AuditRepository
    val allItems: LiveData<List<AuditItem>>
    private val laboratorioDao = (application as TechAuditApp).database.laboratorioDAO()
    init {
        val dao = (application as TechAuditApp).database.auditDao()
        repository = AuditRepository(dao)

        allItems = repository.allItems.asLiveData()
    }

    fun delete(item: AuditItem) = viewModelScope.launch {
        repository.delete(item)
    }
    fun obtenerLaboratorios() = viewModelScope.launch {
        laboratorioDao.obtenerLaboratorios()
    }
}