package br.com.victor.cadastroclientes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cliente: Cliente)

    @Query("SELECT * FROM clientes ORDER BY nome ASC")
    fun getAllClientes(): Flow<List<Cliente>>
}