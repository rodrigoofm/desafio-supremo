package com.example.desafiosupremo.repositories

import com.example.desafiosupremo.data.RetrofitService

class StatementRepository constructor(private val retrofitService: RetrofitService){
    fun getBalance() = retrofitService.getBalance()
    fun getListStatement() = retrofitService.listStatement(10, 0)
}