package com.example.desafiosupremo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiosupremo.databinding.ActivityDetailStatementBinding
import com.example.desafiosupremo.databinding.ShareBottomSheetBinding
import com.example.desafiosupremo.models.Statement
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat

class DetailStatementActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityDetailStatementBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val statement = intent.getSerializableExtra("STATEMENT") as Statement

        binding.tvMovimentationTypeValue.text = statement.description
        binding.tvAmountStatementDatailValue.text = "R$ ${statement.amount},00"
        binding.tvToStatementDetailValue.text = statement.to
        binding.tvDateTimeValue.text = dateFormat(statement.createdAt)
        binding.tvAuthenticationValue.text = statement.id

        closeDetailStatement()
        btnShate()
    }

    private fun dateFormat(createdAt: String): String {
        val dateReciver = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
        val date = dateReciver.parse(createdAt)
        val originalDate = SimpleDateFormat("dd/MM/yyyy - HH:mm:ss")
        return originalDate.format(date)
    }

    fun closeDetailStatement() {
        binding.ibBack.setOnClickListener{
            finish()
        }
    }

    fun btnShate() {
        binding.btShare.setOnClickListener {
            showShareBottomSheet()
        }
    }

    fun showShareBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val sheetBinding = ShareBottomSheetBinding.inflate(layoutInflater)
        dialog.setContentView(sheetBinding.root)
        dialog.show()
    }
}