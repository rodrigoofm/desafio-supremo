package com.example.desafiosupremo.data

import com.example.desafiosupremo.models.Statement
import com.example.desafiosupremo.models.StatementResponse
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitService {
    @GET("myStatement/{limit}/{offset}")
    @SerializedName("items")
    fun listStatement(
        @Path("limit") limit: Int = 10,
        @Path("offset") offset: Int = 0
    ): Call<StatementResponse>


    @GET("myBalance")
    fun getBalance(): Call<Statement>

    companion object {
        private val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"


        private val retrofitService: RetrofitService by lazy {
            val http = OkHttpClient.Builder().addInterceptor { chain ->
                val newRequest: Request = chain.request().newBuilder()
                    .addHeader("token", "$token")
                    .build()
                chain.proceed(newRequest)
            }

            val retrofit = Retrofit.Builder()
                .baseUrl("https://bank-statement-bff.herokuapp.com/")
                .client(http.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(RetrofitService::class.java)
        }


        fun getInstance(): RetrofitService {
            return retrofitService
        }

    }
}