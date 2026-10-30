package com.example.bc23.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bc23.core.ui.components.BC23BottomNav
import com.example.bc23.core.ui.components.NavItem
import com.example.bc23.di.AppContainer
import com.example.bc23.features.admin.AdminScreen
import com.example.bc23.features.admin.AdminViewModel
import com.example.bc23.features.auth.AuthScreen
import com.example.bc23.features.auth.AuthViewModel
import com.example.bc23.features.games.GamesScreen
import com.example.bc23.features.games.GamesViewModel
import com.example.bc23.features.home.HomeScreen
import com.example.bc23.features.home.HomeViewModel
import com.example.bc23.features.notifications.NotificationsScreen
import com.example.bc23.features.notifications.NotificationsViewModel
import com.example.bc23.features.profile.ProfileScreen
import com.example.bc23.features.profile.ProfileViewModel
import com.example.bc23.features.promotions.PromotionsScreen
import com.example.bc23.features.promotions.PromotionsViewModel
import com.example.bc23.features.referral.ReferralScreen
import com.example.bc23.features.referral.ReferralViewModel
import com.example.bc23.features.support.SupportScreen
import com.example.bc23.features.support.SupportViewModel
import com.example.bc23.features.wallet.WalletScreen
import com.example.bc23.features.wallet.WalletViewModel

@Composable
fun BC23NavGraph(
    container: AppContainer,
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf(BC23Screen.HOME) }
    var previousScreen by remember { mutableStateOf(BC23Screen.HOME) }

    fun navigateTo(screen: BC23Screen) {
        previousScreen = currentScreen
        currentScreen = screen
    }

    fun navigateBack() {
        currentScreen = if (previousScreen != currentScreen) previousScreen else BC23Screen.HOME
    }

    // Determine selected Bottom Nav item
    val selectedNavItem = when (currentScreen) {
        BC23Screen.HOME -> NavItem.HOME
        BC23Screen.GAMES -> NavItem.GAMES
        BC23Screen.WALLET -> NavItem.WALLET
        BC23Screen.PROMOTIONS -> NavItem.PROMOTIONS
        BC23Screen.PROFILE -> NavItem.PROFILE
        else -> NavItem.HOME
    }

    val showBottomNav = currentScreen in listOf(
        BC23Screen.HOME,
        BC23Screen.GAMES,
        BC23Screen.WALLET,
        BC23Screen.PROMOTIONS,
        BC23Screen.PROFILE
    )

    // BackHandler for sub-screens
    if (currentScreen !in listOf(BC23Screen.HOME)) {
        BackHandler {
            navigateBack()
        }
    }

    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                BC23BottomNav(
                    selectedItem = selectedNavItem,
                    onItemSelected = { item ->
                        when (item) {
                            NavItem.HOME -> currentScreen = BC23Screen.HOME
                            NavItem.GAMES -> currentScreen = BC23Screen.GAMES
                            NavItem.WALLET -> currentScreen = BC23Screen.WALLET
                            NavItem.PROMOTIONS -> currentScreen = BC23Screen.PROMOTIONS
                            NavItem.PROFILE -> currentScreen = BC23Screen.PROFILE
                        }
                    }
                )
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                BC23Screen.HOME -> {
                    val homeViewModel: HomeViewModel = viewModel(
                        factory = HomeViewModel.provideFactory(container.homeRepository)
                    )
                    HomeScreen(
                        viewModel = homeViewModel,
                        onNavigateToWallet = { navigateTo(BC23Screen.WALLET) },
                        onNavigateToGames = { navigateTo(BC23Screen.GAMES) },
                        onNavigateToPromos = { navigateTo(BC23Screen.PROMOTIONS) },
                        onNavigateToReferral = { navigateTo(BC23Screen.REFERRAL) },
                        onNavigateToSupport = { navigateTo(BC23Screen.SUPPORT) },
                        onNavigateToNotifications = { navigateTo(BC23Screen.NOTIFICATIONS) },
                        onNavigateToAdmin = { navigateTo(BC23Screen.ADMIN) }
                    )
                }

                BC23Screen.GAMES -> {
                    val gamesViewModel: GamesViewModel = viewModel(
                        factory = GamesViewModel.provideFactory(container.gamesRepository)
                    )
                    GamesScreen(viewModel = gamesViewModel)
                }

                BC23Screen.WALLET -> {
                    val walletViewModel: WalletViewModel = viewModel(
                        factory = WalletViewModel.provideFactory(container.walletRepository)
                    )
                    WalletScreen(viewModel = walletViewModel)
                }

                BC23Screen.PROMOTIONS -> {
                    val promotionsViewModel: PromotionsViewModel = viewModel(
                        factory = PromotionsViewModel.provideFactory(container.promotionsRepository)
                    )
                    PromotionsScreen(viewModel = promotionsViewModel)
                }

                BC23Screen.PROFILE -> {
                    val profileViewModel: ProfileViewModel = viewModel(
                        factory = ProfileViewModel.provideFactory(container.profileRepository, container.authRepository)
                    )
                    ProfileScreen(
                        viewModel = profileViewModel,
                        onNavigateToReferral = { navigateTo(BC23Screen.REFERRAL) },
                        onNavigateToSupport = { navigateTo(BC23Screen.SUPPORT) },
                        onNavigateToNotifications = { navigateTo(BC23Screen.NOTIFICATIONS) },
                        onNavigateToAdmin = { navigateTo(BC23Screen.ADMIN) },
                        onLogout = { navigateTo(BC23Screen.AUTH) }
                    )
                }

                BC23Screen.AUTH -> {
                    val authViewModel: AuthViewModel = viewModel(
                        factory = AuthViewModel.provideFactory(container.authRepository)
                    )
                    AuthScreen(
                        viewModel = authViewModel,
                        onAuthSuccess = { navigateTo(BC23Screen.HOME) }
                    )
                }

                BC23Screen.REFERRAL -> {
                    val referralViewModel: ReferralViewModel = viewModel(
                        factory = ReferralViewModel.provideFactory(container.referralRepository)
                    )
                    ReferralScreen(
                        viewModel = referralViewModel,
                        onBack = { navigateBack() }
                    )
                }

                BC23Screen.NOTIFICATIONS -> {
                    val notificationsViewModel: NotificationsViewModel = viewModel(
                        factory = NotificationsViewModel.provideFactory(container.notificationsRepository)
                    )
                    NotificationsScreen(
                        viewModel = notificationsViewModel,
                        onBack = { navigateBack() }
                    )
                }

                BC23Screen.SUPPORT -> {
                    val supportViewModel: SupportViewModel = viewModel(
                        factory = SupportViewModel.provideFactory(container.supportRepository)
                    )
                    SupportScreen(
                        viewModel = supportViewModel,
                        onBack = { navigateBack() }
                    )
                }

                BC23Screen.ADMIN -> {
                    val adminViewModel: AdminViewModel = viewModel(
                        factory = AdminViewModel.provideFactory()
                    )
                    AdminScreen(
                        viewModel = adminViewModel,
                        onBack = { navigateBack() }
                    )
                }
            }
        }
    }
}
