package com.example.orcamentopessoal.data

import com.example.orcamentopessoal.model.TipoTransacao
import com.example.orcamentopessoal.model.Transacao
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class TransacaoRepositoryTest {

    @Mock
    private lateinit var transacaoDao: TransacaoDao
    private lateinit var repository: TransacaoRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = TransacaoRepository(transacaoDao)
    }

    @Test
    fun `getTodasTransacoes deve retornar lista de transacoes do DAO`() = runTest {
        // Given
        val transacoes = listOf(
            Transacao(descricao = "Teste 1", valor = 100.0, tipo = TipoTransacao.RECEITA),
            Transacao(descricao = "Teste 2", valor = 50.0, tipo = TipoTransacao.DESPESA)
        )
        `when`(transacaoDao.getTodasTransacoes()).thenReturn(flowOf(transacoes))

        // When
        val result = repository.todasTransacoes.first()

        // Then
        assertThat(result).isEqualTo(transacoes)
    }

    @Test
    fun `getSaldoTotal deve retornar saldo correto do DAO`() = runTest {
        // Given
        val saldo = 150.0
        `when`(transacaoDao.getSaldoTotal()).thenReturn(flowOf(saldo))

        // When
        val result = repository.saldoTotal.first()

        // Then
        assertThat(result).isEqualTo(saldo)
    }
}
