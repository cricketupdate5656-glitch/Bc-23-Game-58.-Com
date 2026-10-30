package com.example.bc23.di

import android.content.Context
import com.example.bc23.core.database.BC23Database
import com.example.bc23.core.network.BC23ApiService
import com.example.bc23.core.network.NetworkModule
import com.example.bc23.core.security.SessionManager
import com.example.bc23.features.auth.AuthRepository
import com.example.bc23.features.auth.DefaultAuthRepository
import com.example.bc23.features.games.DefaultGamesRepository
import com.example.bc23.features.games.GamesRepository
import com.example.bc23.features.home.DefaultHomeRepository
import com.example.bc23.features.home.HomeRepository
import com.example.bc23.features.notifications.DefaultNotificationsRepository
import com.example.bc23.features.notifications.NotificationsRepository
import com.example.bc23.features.profile.DefaultProfileRepository
import com.example.bc23.features.profile.ProfileRepository
import com.example.bc23.features.promotions.DefaultPromotionsRepository
import com.example.bc23.features.promotions.PromotionsRepository
import com.example.bc23.features.referral.DefaultReferralRepository
import com.example.bc23.features.referral.ReferralRepository
import com.example.bc23.features.support.DefaultSupportRepository
import com.example.bc23.features.support.SupportRepository
import com.example.bc23.features.wallet.DefaultWalletRepository
import com.example.bc23.features.wallet.WalletRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

interface AppContainer {
    val database: BC23Database
    val sessionManager: SessionManager
    val apiService: BC23ApiService

    val authRepository: AuthRepository
    val homeRepository: HomeRepository
    val walletRepository: WalletRepository
    val gamesRepository: GamesRepository
    val promotionsRepository: PromotionsRepository
    val referralRepository: ReferralRepository
    val profileRepository: ProfileRepository
    val notificationsRepository: NotificationsRepository
    val supportRepository: SupportRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override val sessionManager: SessionManager by lazy {
        SessionManager(context)
    }

    override val database: BC23Database by lazy {
        BC23Database.getDatabase(context, applicationScope)
    }

    private val okHttpClient by lazy {
        NetworkModule.createOkHttpClient(sessionManager)
    }

    private val moshi by lazy {
        NetworkModule.createMoshi()
    }

    private val retrofit by lazy {
        NetworkModule.createRetrofit(okHttpClient, moshi)
    }

    override val apiService: BC23ApiService by lazy {
        NetworkModule.createApiService(retrofit)
    }

    override val authRepository: AuthRepository by lazy {
        DefaultAuthRepository(database.userDao(), sessionManager, apiService)
    }

    override val homeRepository: HomeRepository by lazy {
        DefaultHomeRepository(database.userDao(), database.walletDao(), database.gameDao(), database.promotionDao())
    }

    override val walletRepository: WalletRepository by lazy {
        DefaultWalletRepository(database.walletDao())
    }

    override val gamesRepository: GamesRepository by lazy {
        DefaultGamesRepository(database.gameDao())
    }

    override val promotionsRepository: PromotionsRepository by lazy {
        DefaultPromotionsRepository(database.promotionDao())
    }

    override val referralRepository: ReferralRepository by lazy {
        DefaultReferralRepository(database.referralDao())
    }

    override val profileRepository: ProfileRepository by lazy {
        DefaultProfileRepository(database.userDao(), sessionManager)
    }

    override val notificationsRepository: NotificationsRepository by lazy {
        DefaultNotificationsRepository(database.notificationDao())
    }

    override val supportRepository: SupportRepository by lazy {
        DefaultSupportRepository(database.supportDao())
    }
}
