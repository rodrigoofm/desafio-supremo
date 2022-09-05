package com.example.desafiosupremo.repository

import com.example.desafiosupremo.data.remote.RetrofitService
import javax.inject.Inject

class StatementRepository @Inject constructor(private val retrofitService: RetrofitService){
    suspend fun getBalance() = retrofitService.getBalance()
    suspend fun getListStatement() = retrofitService.listStatement(10, 0)
}