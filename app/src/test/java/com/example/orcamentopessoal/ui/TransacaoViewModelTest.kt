package com.example.orcamentopessoal.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.orcamentopessoal.data.TransacaoRepository
import com.example.orcamentopessoal.model.TipoTransacao
import com.example.orcamentopessoal.model.Transacao
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class TransacaoViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var repository: TransacaoRepository

    private lateinit var viewModel: TransacaoViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = TransacaoViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `quando inserir transacao, deve chamar repository`() = runTest {
        // Given
        val transacao = Transacao(
            descricao = "Teste",
            valor = 100.0,
            tipo = TipoTransacao.RECEITA
        )

        // When
        viewModel.inserirTransacao(transacao)

        // Then
        verify(repository).inserirTransacao(transacao)
    }

    @Test
    fun `quando buscar transacoes, deve retornar lista do repository`() = runTest {
        // Given
        val transacoes = listOf(
            Transacao(descricao = "Teste 1", valor = 100.0, tipo = TipoTransacao.RECEITA),
            Transacao(descricao = "Teste 2", valor = 50.0, tipo = TipoTransacao.DESPESA)
        )
        `when`(repository.todasTransacoes).thenReturn(flowOf(transacoes))

        // When
        val result = viewModel.todasTransacoes.first()

        // Then
        assertThat(result).isEqualTo(transacoes)
    }
}
