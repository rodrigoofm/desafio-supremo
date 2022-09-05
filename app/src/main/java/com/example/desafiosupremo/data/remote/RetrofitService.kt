package com.example.desafiosupremo.data.remote

import com.example.desafiosupremo.data.models.Balance
import com.example.desafiosupremo.data.models.Statement
import com.example.desafiosupremo.data.models.StatementResponse
import com.example.desafiosupremo.utils.Constants.BASE_URL
import com.example.desafiosupremo.utils.Constants.TOKEN
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitService {
    @GET("myStatement/{limit}/{offset}")
    @SerializedName("items")
    suspend fun listStatement(
        @Path("limit") limit: Int = 10,
        @Path("offset") offset: Int = 0
    ): Response<StatementResponse>


    @GET("myBalance")
    suspend fun getBalance(): Response<Balance>
}