package br.com.victor.cadastroclientes.data

import android.util.Log
import br.com.victor.cadastroclientes.data.network.RetrofitClient
import kotlinx.coroutines.flow.Flow

class ClienteRepository(private val clienteDao: ClienteDao) {

    private val apiService = RetrofitClient.instance

    val todosClientes: Flow<List<Cliente>> = clienteDao.getAllClientes()

    suspend fun insert(cliente: Cliente) {
        clienteDao.insert(cliente)
    }

    suspend fun refreshClientes() {
        try {
            val usersFromApi = apiService.getUsers()
            val clientesParaSalvar = usersFromApi.map { userResponse ->
                Cliente(
                    id = userResponse.id,
                    nome = userResponse.name,
                    email = userResponse.email
                )
            }
            clientesParaSalvar.forEach { cliente ->
                clienteDao.insert(cliente)
            }
        } catch (e: Exception) {
            Log.e("ClienteRepository", "Falha ao buscar dados da API", e)
        }
    }
}