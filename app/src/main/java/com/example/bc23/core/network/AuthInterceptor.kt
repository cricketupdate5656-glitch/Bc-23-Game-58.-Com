package com.example.bc23.core.network

import com.example.bc23.core.security.SessionManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val sessionManager: SessionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val token = runBlocking {
            sessionManager.authTokenFlow.firstOrNull()
        }

        val requestBuilder = originalRequest.newBuilder()
            .header(ApiConstants.HEADER_CLIENT_VERSION, "1.0.0")
            .header(ApiConstants.HEADER_DEVICE_PLATFORM, ApiConstants.PLATFORM_ANDROID)
            .header(ApiConstants.HEADER_DEMO_MODE, "true")

        if (!token.isNullOrEmpty()) {
            requestBuilder.header(ApiConstants.HEADER_AUTHORIZATION, "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}
