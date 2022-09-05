package com.example.desafiosupremo.ui.statement

import StatementAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiosupremo.R
import com.example.desafiosupremo.data.models.Statement
import com.example.desafiosupremo.databinding.FragmentStatementBinding
import com.example.desafiosupremo.ui.base.BaseFragment
import com.example.desafiosupremo.ui.state.ResourceState
import com.example.desafiosupremo.utils.hide
import com.example.desafiosupremo.utils.show
import com.example.desafiosupremo.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StatementFragment : BaseFragment<FragmentStatementBinding, StatementViewModel>() {

    override val viewModel: StatementViewModel by viewModels()
    private val statementAdapter by lazy {
        StatementAdapter() {
            onStatementClick(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observerBalance()
        observerStatement()
        showOrHideAmount()
    }

    private fun observerBalance() = lifecycleScope.launch {
        viewModel.balance.collect { resource ->
            when (resource) {
                is ResourceState.Success -> {
                    resource.data?.let { value ->
                        binding.tvBalance.text = "R$ ${value.amount}, 00"
                    }
                }
                is ResourceState.Error -> {
                    binding.progressCircular.hide()
                    resource.message?.let { message ->
                        toast(message)
                    }
                }
                else -> {}
            }
        }
    }

    private fun observerStatement() = lifecycleScope.launch {
        viewModel.list.collect { resource ->
            when (resource) {
                is ResourceState.Success -> {
                    resource.data?.let { values ->
                        binding.progressCircular.hide()
                        statementAdapter.items = values.items.toList()
                    }
                }
                is ResourceState.Error -> {
                    binding.progressCircular.hide()
                    resource.message?.let { message ->
                        toast(message)
                    }
                }
                is ResourceState.Loading -> {
                    binding.progressCircular.show()
                }
            }
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvStatement.apply {
            adapter = statementAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun onStatementClick(statement: Statement) {
        val action =
            StatementFragmentDirections.actionStatementFragmentToDetailStatementFragment(statement)
        findNavController().navigate(action)
    }

    private fun showOrHideAmount() = with(binding) {
        ibBalanceVisibility.setOnClickListener {

            if (tvBalance.isVisible) {
                ibBalanceVisibility.setImageResource(R.drawable.ic_visibility)
                tvBalance.hide()
                dividerBalance.show()
            } else {
                tvBalance.show()
                ibBalanceVisibility.setImageResource(R.drawable.ic_visibility_off)
                dividerBalance.hide()
            }
        }
    }

    override fun getViewBingin(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStatementBinding =
        FragmentStatementBinding.inflate(inflater, container, false)
}