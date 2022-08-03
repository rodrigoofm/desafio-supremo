package com.example.desafiosupremo.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafiosupremo.models.Statement
import com.example.desafiosupremo.models.StatementResponse
import com.example.desafiosupremo.repositories.StatementRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: StatementRepository): ViewModel() {
    val statementList = MutableLiveData<StatementResponse>()
    val getBalance = MutableLiveData<Statement>()
    val errorMessage = MutableLiveData<String>()

    fun getListStatement() {
        val request = repository.getListStatement()
        request.enqueue(object: Callback<StatementResponse> {

            override fun onResponse(
                call: Call<StatementResponse>,
                response: Response<StatementResponse>
            ) {
                statementList.postValue(response.body())
            }

            override fun onFailure(call: Call<StatementResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getBalance() {
        val request = repository.getBalance()
        request.enqueue(object: Callback<Statement> {
            override fun onResponse(call: Call<Statement>, response: Response<Statement>) {
                getBalance.postValue(response.body())
            }

            override fun onFailure(call: Call<Statement>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }
}