package com.example.bc23.core.network

import com.example.BuildConfig
import com.example.bc23.core.security.SessionManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    fun createOkHttpClient(sessionManager: SessionManager): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.IS_DEV_MODE) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .connectTimeout(ApiConstants.TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .readTimeout(ApiConstants.TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(ApiConstants.TIMEOUT_WRITE, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor(sessionManager))
            .addInterceptor(loggingInterceptor)
            .build()
    }

    fun createMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    fun createRetrofit(okHttpClient: OkHttpClient, moshi: Moshi, baseUrl: String = BuildConfig.API_BASE_URL): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    fun createApiService(retrofit: Retrofit): BC23ApiService {
        return retrofit.create(BC23ApiService::class.java)
    }
}
