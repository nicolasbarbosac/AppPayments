package com.example.apppayments.HttpClient

import com.example.apppayments.BuildConfig
import com.example.apppayments.Helpers.DataGlobal
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    var global = DataGlobal()

    private val client = OkHttpClient.Builder()
       .addInterceptor(loggingInterceptor)
        // Attempting to add headers to every request
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Basic ${global.keyApi}")
            chain.proceed(request.build())
        }
        .build()

//    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.3.10:8080/api/payments/") // change this IP for testing by your actual machine IP
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}