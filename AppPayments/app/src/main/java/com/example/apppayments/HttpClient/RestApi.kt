package com.example.apppayments.HttpClient

import com.example.apppayments.model.AnnulmentRequest
import com.example.apppayments.model.AnnulmentResponse
import com.example.apppayments.model.AuthorizationRequest
import com.example.apppayments.model.AuthorizationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface  RestApi {
    @Headers("Content-Type: application/json")
    @POST("authorization")
    fun authorization(        @Body AuthRequest: AuthorizationRequest    ): Call<AuthorizationResponse>


    @Headers("Content-Type: application/json")
    @POST("annulment")
    fun annulment(        @Body AuthRequest: AnnulmentRequest): Call<AnnulmentResponse>
}