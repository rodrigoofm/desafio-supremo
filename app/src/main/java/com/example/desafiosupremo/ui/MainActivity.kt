package com.example.desafiosupremo.ui

import StatementAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiosupremo.R
import com.example.desafiosupremo.data.RetrofitService
import com.example.desafiosupremo.databinding.ActivityMainBinding
import com.example.desafiosupremo.models.Statement
import com.example.desafiosupremo.repositories.StatementRepository
import com.example.desafiosupremo.viewmodel.main.MainViewModel
import com.example.desafiosupremo.viewmodel.main.MainViewModelFactory

class MainActivity : AppCompatActivity(), StatementAdapter.OnClick {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this, MainViewModelFactory(StatementRepository(retrofitService))).get(
                MainViewModel::class.java
            )

        showOrHideAmount()


    }

    override fun onStart() {
        super.onStart()
        viewModel.getBalance.observe(this, Observer {
            binding.tvBalance.text = "R$ ${it.amount},00"
        })

        viewModel.statementList.observe(this, Observer {
            binding.rvStatement.layoutManager = LinearLayoutManager(this)
            binding.rvStatement.adapter = StatementAdapter(it.items, this)
        })

        viewModel.errorMessage.observe(this, Observer { message ->
            Log.e("RODRIGO", "ERROR AQUI: $message")
        })
    }

    override fun onResume() {
        super.onResume()

        viewModel.getBalance()
        viewModel.getListStatement()
    }

    private fun showOrHideAmount() {
        binding.ibBalanceVisibility.setOnClickListener {

            if (binding.tvBalance.isVisible) {
                binding.ibBalanceVisibility.setImageResource(R.drawable.ic_visibility)
                binding.tvBalance.visibility = View.GONE
                binding.dividerBalance.visibility = View.VISIBLE
            } else {
                binding.tvBalance.visibility = View.VISIBLE
                binding.ibBalanceVisibility.setImageResource(R.drawable.ic_visibility_off)
                binding.dividerBalance.visibility = View.GONE
            }


        }
    }

    override fun onClickListener(statement: Statement) {
        val intent = Intent(this, DetailStatementActivity::class.java)
        intent.putExtra("STATEMENT", statement)
        startActivity(intent)
    }

}
