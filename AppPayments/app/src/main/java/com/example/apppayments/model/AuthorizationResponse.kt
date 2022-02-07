package com.example.apppayments.model

data class AuthorizationResponse(
        val receiptId: String?,
    val rrn         : String?,
    val statusCode: String,
    val statusDescription: String

)
