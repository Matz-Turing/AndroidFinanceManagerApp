package com.example.orcamentopessoal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.orcamentopessoal.data.TransacaoRepository

class TransacaoViewModelFactory(
    private val repository: TransacaoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransacaoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransacaoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
