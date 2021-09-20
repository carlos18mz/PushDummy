package com.ts.notification

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        val baseUrl = "https://us-central1-deploymentts.cloudfunctions.net/"

        fun getRetrofitInstance(): Retrofit {

            var logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
            client.addInterceptor(logging)

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}