package br.com.victor.cadastroclientes.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.victor.cadastroclientes.data.AppDatabase
import br.com.victor.cadastroclientes.data.Cliente
import br.com.victor.cadastroclientes.data.ClienteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ClienteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ClienteRepository
    val todosClientes: StateFlow<List<Cliente>>

    init {
        val clienteDao = AppDatabase.getDatabase(application).clienteDao()
        repository = ClienteRepository(clienteDao)
        todosClientes = repository.todosClientes.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

        fetchClientesFromApi()
    }

    fun insert(cliente: Cliente) = viewModelScope.launch {
        repository.insert(cliente)
    }

    fun fetchClientesFromApi() {
        viewModelScope.launch {
            repository.refreshClientes()
        }
    }
}