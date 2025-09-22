package br.com.victor.cadastroclientes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")
data class Cliente(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val email: String
)