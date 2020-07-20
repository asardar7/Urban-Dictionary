package com.coding.urbandictionary.api

import com.coding.urbandictionary.util.CompanionClass
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
            .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl(CompanionClass.URL_URBAN_DICTIONARY)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build()
        }

        val api by lazy {
            retrofit.create(IDictionaryApi::class.java)
        }
    }
}