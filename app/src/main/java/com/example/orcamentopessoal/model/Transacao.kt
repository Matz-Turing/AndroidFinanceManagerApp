package com.example.orcamentopessoal.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transacoes")
data class Transacao(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val descricao: String,
    val valor: Double,
    val tipo: TipoTransacao,
    val data: Date = Date()
)

enum class TipoTransacao {
    RECEITA,
    DESPESA
}
