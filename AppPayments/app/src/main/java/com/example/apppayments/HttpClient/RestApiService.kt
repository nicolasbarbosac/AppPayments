package com.example.apppayments.HttpClient

import com.example.apppayments.model.AnnulmentRequest
import com.example.apppayments.model.AnnulmentResponse
import com.example.apppayments.model.AuthorizationRequest
import com.example.apppayments.model.AuthorizationResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RestApiService {
    fun authorization(userData: AuthorizationRequest, onResult: (AuthorizationResponse?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.authorization(userData).enqueue(
            object : Callback<AuthorizationResponse> {
                override fun onFailure(call: Call<AuthorizationResponse>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(
                    call: Call<AuthorizationResponse>,
                    response: Response<AuthorizationResponse>
                ) {
                    val addedUser = response.body()
                    if (addedUser!=null) {
                        onResult(addedUser)
                    }
                        else
                    {
                        val gson = Gson()
                        val type = object : TypeToken<AuthorizationResponse>() {}.type
                        var errorResponse: AuthorizationResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                        onResult(errorResponse)
                    }
                }
            }
        )
    }


    fun annulment(userData: AnnulmentRequest, onResult: (AnnulmentResponse?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.annulment(userData).enqueue(
            object : Callback<AnnulmentResponse> {
                override fun onFailure(call: Call<AnnulmentResponse>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(
                    call: Call<AnnulmentResponse>,
                    response: Response<AnnulmentResponse>
                ) {
                    val addedUser = response.body()
                    if (addedUser!=null) {
                        onResult(addedUser)
                    }
                    else
                    {
                        val gson = Gson()
                        val type = object : TypeToken<AnnulmentResponse>() {}.type
                        var errorResponse: AnnulmentResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                        onResult(errorResponse)
                    }
                }
            }
        )
    }
}