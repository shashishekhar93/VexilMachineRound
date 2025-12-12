package com.tech.vexilmachineround.model


import com.google.gson.annotations.SerializedName

data class ResponseObject(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("status")
    val status: Boolean
)