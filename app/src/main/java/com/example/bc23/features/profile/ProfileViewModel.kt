package com.example.bc23.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bc23.core.database.entity.UserEntity
import com.example.bc23.core.database.entity.UserProfileEntity
import com.example.bc23.features.auth.AuthRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ProfileUiState(
    val user: UserEntity? = null,
    val profile: UserProfileEntity? = null,
    val isNotificationsEnabled: Boolean = true,
    val appVersion: String = "1.0.0-phase1",
    val environment: String = "Development Sandbox"
)

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository,
    private val userId: String = "usr_demo_5801"
) : ViewModel() {

    val uiState: StateFlow<ProfileUiState> = combine(
        profileRepository.getUser(userId),
        profileRepository.getProfile(userId)
    ) { user, profile ->
        ProfileUiState(
            user = user,
            profile = profile
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileUiState()
    )

    fun toggleNotifications(enabled: Boolean) {
        viewModelScope.launch {
            profileRepository.setNotificationsEnabled(enabled)
        }
    }

    fun logout(onLoggedOut: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
            onLoggedOut()
        }
    }

    companion object {
        fun provideFactory(profileRepository: ProfileRepository, authRepository: AuthRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProfileViewModel(profileRepository, authRepository) as T
                }
            }
    }
}
