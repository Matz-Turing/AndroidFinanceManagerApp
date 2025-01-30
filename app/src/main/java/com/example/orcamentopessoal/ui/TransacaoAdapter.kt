package com.example.orcamentopessoal.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.orcamentopessoal.R
import com.example.orcamentopessoal.databinding.ItemTransacaoBinding
import com.example.orcamentopessoal.model.TipoTransacao
import com.example.orcamentopessoal.model.Transacao
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class TransacaoAdapter(
    private val onLongClick: (Transacao) -> Unit
) : ListAdapter<Transacao, TransacaoAdapter.ViewHolder>(TransacaoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransacaoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemTransacaoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val formatoMoeda = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        private val formatoData = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))

        fun bind(transacao: Transacao) {
            binding.apply {
                root.setOnLongClickListener {
                    onLongClick(transacao)
                    true
                }
                
                descricaoTransacao.text = transacao.descricao
                dataTransacao.text = formatoData.format(transacao.data)
                
                val valor = when (transacao.tipo) {
                    TipoTransacao.RECEITA -> transacao.valor
                    TipoTransacao.DESPESA -> -transacao.valor
                }
                
                valorTransacao.text = formatoMoeda.format(valor)
                valorTransacao.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        when (transacao.tipo) {
                            TipoTransacao.RECEITA -> R.color.receita
                            TipoTransacao.DESPESA -> R.color.despesa
                        }
                    )
                )
            }
        }
    }
}

class TransacaoDiffCallback : DiffUtil.ItemCallback<Transacao>() {
    override fun areItemsTheSame(oldItem: Transacao, newItem: Transacao): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Transacao, newItem: Transacao): Boolean {
        return oldItem == newItem
    }
}
