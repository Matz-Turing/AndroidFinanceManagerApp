package com.example.orcamentopessoal.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.orcamentopessoal.R
import com.example.orcamentopessoal.data.AppDatabase
import com.example.orcamentopessoal.data.TransacaoRepository
import com.example.orcamentopessoal.databinding.ActivityMainBinding
import com.example.orcamentopessoal.databinding.DialogAddTransacaoBinding
import com.example.orcamentopessoal.model.TipoTransacao
import com.example.orcamentopessoal.model.Transacao
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: TransacaoViewModel by viewModels {
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = TransacaoRepository(database.transacaoDao())
        TransacaoViewModelFactory(repository)
    }
    private val transacaoAdapter = TransacaoAdapter { transacao ->
        showExcluirTransacaoDialog(transacao)
    }
    private val formatoMoeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        binding.transacoesRecyclerView.apply {
            adapter = transacaoAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.todasTransacoes.collectLatest { transacoes ->
                transacaoAdapter.submitList(transacoes)
            }
        }

        lifecycleScope.launch {
            viewModel.saldoTotal.collectLatest { saldo ->
                binding.saldoTotal.text = formatoMoeda.format(saldo ?: 0.0)
            }
        }
    }

    private fun setupClickListeners() {
        binding.fabAddTransaction.setOnClickListener {
            showAdicionarTransacaoDialog()
        }

        binding.btnReceita.setOnClickListener {
            showAdicionarTransacaoDialog(TipoTransacao.RECEITA)
        }

        binding.btnDespesa.setOnClickListener {
            showAdicionarTransacaoDialog(TipoTransacao.DESPESA)
        }
    }

    private fun showAdicionarTransacaoDialog(tipoPreSelecionado: TipoTransacao? = null) {
        val dialogBinding = DialogAddTransacaoBinding.inflate(LayoutInflater.from(this))
        
        if (tipoPreSelecionado != null) {
            when (tipoPreSelecionado) {
                TipoTransacao.RECEITA -> dialogBinding.rbReceita.isChecked = true
                TipoTransacao.DESPESA -> dialogBinding.rbDespesa.isChecked = true
            }
        }

        AlertDialog.Builder(this)
            .setTitle(R.string.add_transaction)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.add) { _, _ ->
                val descricao = dialogBinding.edtDescricao.text.toString()
                val valor = dialogBinding.edtValor.text.toString().toDoubleOrNull() ?: 0.0
                val tipo = when (dialogBinding.rgTipoTransacao.checkedRadioButtonId) {
                    dialogBinding.rbReceita.id -> TipoTransacao.RECEITA
                    else -> TipoTransacao.DESPESA
                }

                if (descricao.isNotBlank() && valor > 0) {
                    val transacao = Transacao(
                        descricao = descricao,
                        valor = valor,
                        tipo = tipo
                    )
                    viewModel.inserirTransacao(transacao)
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun showExcluirTransacaoDialog(transacao: Transacao) {
        AlertDialog.Builder(this)
            .setTitle("Excluir Transação")
            .setMessage("Deseja realmente excluir esta transação?")
            .setPositiveButton("Excluir") { _, _ ->
                viewModel.deletarTransacao(transacao)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
