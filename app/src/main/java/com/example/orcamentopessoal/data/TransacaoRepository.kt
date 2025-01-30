package com.example.orcamentopessoal.data

import com.example.orcamentopessoal.model.Transacao
import kotlinx.coroutines.flow.Flow

class TransacaoRepository(private val transacaoDao: TransacaoDao) {
    val todasTransacoes: Flow<List<Transacao>> = transacaoDao.getTodasTransacoes()
    val saldoTotal: Flow<Double> = transacaoDao.getSaldoTotal()

    suspend fun inserirTransacao(transacao: Transacao) {
        transacaoDao.inserir(transacao)
    }

    suspend fun deletarTransacao(transacao: Transacao) {
        transacaoDao.deletar(transacao)
    }
}
