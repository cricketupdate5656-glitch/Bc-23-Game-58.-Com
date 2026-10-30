package com.example.bc23.core.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BC23ApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequestDto): Response<AuthResponseDto>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequestDto): Response<AuthResponseDto>

    @GET("wallet/balance")
    suspend fun getWalletBalance(@Query("userId") userId: String): Response<WalletBalanceDto>

    @GET("games")
    suspend fun getGamesCatalog(@Query("category") category: String?): Response<List<GameDto>>

    @GET("promotions")
    suspend fun getPromotions(): Response<List<PromotionDto>>
}

data class LoginRequestDto(val username: String, val passwordHash: String)
data class RegisterRequestDto(val username: String, val passwordHash: String, val referralCode: String?)
data class AuthResponseDto(val token: String, val userId: String, val uid: String, val username: String)
data class WalletBalanceDto(val balance: Double, val currency: String, val isDemo: Boolean)
data class GameDto(val id: String, val title: String, val category: String, val provider: String, val rating: Float)
data class PromotionDto(val id: String, val title: String, val subtitle: String, val bonusPercentage: Int)
