package com.example.orcamentopessoal.data

import androidx.room.*
import com.example.orcamentopessoal.model.Transacao
import kotlinx.coroutines.flow.Flow

@Dao
interface TransacaoDao {
    @Query("SELECT * FROM transacoes ORDER BY data DESC")
    fun getTodasTransacoes(): Flow<List<Transacao>>

    @Query("""
        SELECT COALESCE(
            SUM(CASE 
                WHEN tipo = 'RECEITA' THEN valor 
                ELSE -valor 
            END), 
            0.0
        ) FROM transacoes
    """)
    fun getSaldoTotal(): Flow<Double>

    @Insert
    suspend fun inserir(transacao: Transacao)

    @Delete
    suspend fun deletar(transacao: Transacao)

    @Query("SELECT SUM(valor) FROM transacoes WHERE tipo = 'RECEITA'")
    fun getTotalReceitas(): Flow<Double?>

    @Query("SELECT SUM(valor) FROM transacoes WHERE tipo = 'DESPESA'")
    fun getTotalDespesas(): Flow<Double?>
}
