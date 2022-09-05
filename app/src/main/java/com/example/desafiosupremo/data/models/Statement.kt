package com.example.desafiosupremo.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Statement(
    val id: String,

    @SerializedName("to")
    val name: String,

    val description: String,
    val createdAt: String,
    val amount: Int,

    @SerializedName("tType")
    val transferType: TransferType
): Serializable
