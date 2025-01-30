package com.example.orcamentopessoal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orcamentopessoal.data.TransacaoRepository
import com.example.orcamentopessoal.model.Transacao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TransacaoViewModel(
    private val repository: TransacaoRepository
) : ViewModel() {

    val todasTransacoes: Flow<List<Transacao>> = repository.todasTransacoes
    val saldoTotal: Flow<Double> = repository.saldoTotal

    fun inserirTransacao(transacao: Transacao) {
        viewModelScope.launch {
            repository.inserirTransacao(transacao)
        }
    }

    fun deletarTransacao(transacao: Transacao) {
        viewModelScope.launch {
            repository.deletarTransacao(transacao)
        }
    }
}
