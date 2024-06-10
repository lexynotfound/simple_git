package com.gege.gitgege.data.api

import android.util.Log
import com.gege.gitgege.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Network {
    private val authInterceptor = Interceptor { chain ->
        val token = "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"
        if (token.isNotEmpty()) {
            Log.d("ApiClient", "Token used: $token")
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(request)
        } else {
            Log.e("ApiClient", "Error: Token is null or empty")
            println("Error: Token is null or empty")
            chain.proceed(chain.request())
        }
    }


    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}