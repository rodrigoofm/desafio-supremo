package com.example.desafiosupremo.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.desafiosupremo.R
import com.example.desafiosupremo.data.models.Statement
import com.example.desafiosupremo.databinding.FragmentDetailStatementBinding
import com.example.desafiosupremo.databinding.ShareBottomSheetBinding
import com.example.desafiosupremo.ui.base.BaseFragment
import com.example.desafiosupremo.ui.statement.StatementViewModel
import com.example.desafiosupremo.utils.dateFormater
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailStatementFragment :
    BaseFragment<FragmentDetailStatementBinding, StatementViewModel>() {

    override val viewModel: StatementViewModel by viewModels()
    private val args: DetailStatementFragmentArgs by navArgs()
    private lateinit var statement: Statement

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgs()
        setListeners()
    }

    private fun setListeners() = with(binding) {
        ibBack.setOnClickListener {
            findNavController().navigateUp()
        }
        btShare.setOnClickListener {
            showShareBottomSheet()
        }
    }

    private fun showShareBottomSheet() {
        val dialog = BottomSheetDialog(parentFragment?.context!!)
        val sheetBinding = ShareBottomSheetBinding.inflate(layoutInflater)
        dialog.setContentView(sheetBinding.root)
        dialog.show()
    }

    private fun getArgs() = with(binding) {
        tvMovimentationTypeValue.text = args.statement.description
        tvAmountStatementDatailValue.text = "R$ ${args.statement.amount},00"
        tvToStatementDetailValue.text = args.statement.name
        tvDateTimeValue.text =
            dateFormater(args.statement.createdAt, "dd/MM/yyyy - HH:mm:ss")
        tvAuthenticationValue.text = args.statement.id
    }

    override fun getViewBingin(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailStatementBinding =
        FragmentDetailStatementBinding.inflate(inflater, container, false)
}