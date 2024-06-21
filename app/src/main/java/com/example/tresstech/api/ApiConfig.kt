package com.example.tresstech.api

// RetrofitInstance.kt
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private const val BASE_URL = "https://hair-project1234-ow6rs3c2sq-et.a.run.app/"
    fun getApiService(): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .header("Content-Type", "application/json")
                .build()
            chain.proceed(requestHeaders)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
        }
}
//        val loggingInterceptor =
//            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor { chain ->
//
//                val original = chain.request()
//                var request = original.newBuilder()
//                    .header("Content-Type", "application/json")
//                    .build()
//                var response = chain.proceed(request)
//                response
//            }
//            .addInterceptor(loggingInterceptor)
//            .callTimeout(60, TimeUnit.SECONDS)
//            .connectTimeout(60, TimeUnit.SECONDS)
//            .readTimeout(60, TimeUnit.SECONDS)
//            .writeTimeout(60, TimeUnit.SECONDS)
//            .addInterceptor(loggingInterceptor)
//            .build()
////
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build()
//        return retrofit.create(ApiService::class.java)


