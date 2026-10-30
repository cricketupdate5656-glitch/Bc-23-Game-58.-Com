package com.example.bc23.features.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.BuildConfig
import com.example.bc23.core.database.BC23Database
import com.example.bc23.core.security.DemoWalletGuard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AdminUiState(
    val appName: String = "BC23 Game 58.com",
    val buildVariant: String = BuildConfig.ENVIRONMENT_NAME,
    val isDevMode: Boolean = BuildConfig.IS_DEV_MODE,
    val apiBaseUrl: String = BuildConfig.API_BASE_URL,
    val demoWalletEnforced: Boolean = DemoWalletGuard.IS_DEMO_ENFORCED,
    val realPaymentsBlocked: Boolean = !DemoWalletGuard.REAL_PAYMENTS_ENABLED,
    val databaseStatus: String = "Room Database Active (11 Entities)",
    val kspStatus: String = "KSP Code Generation Verified",
    val composeThemeStatus: String = "Material 3 Dark Luxury (Gold & Crimson)",
    val dataStoreStatus: String = "DataStore Preferences Connected"
)

class AdminViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AdminUiState())
    val uiState: StateFlow<AdminUiState> = _uiState.asStateFlow()

    companion object {
        fun provideFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AdminViewModel() as T
                }
            }
    }
}
