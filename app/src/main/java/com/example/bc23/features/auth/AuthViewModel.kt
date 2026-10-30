package com.example.bc23.features.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bc23.core.common.Resource
import com.example.bc23.core.security.InputValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoginMode: Boolean = true,
    val usernameInput: String = "arshad_lucky",
    val passwordInput: String = "bc23demo123",
    val referralInput: String = "BC58VIP",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isAuthenticated: Boolean = false
)

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun toggleAuthMode() {
        _uiState.update {
            it.copy(
                isLoginMode = !it.isLoginMode,
                errorMessage = null,
                successMessage = null
            )
        }
    }

    fun onUsernameChange(username: String) {
        _uiState.update { it.copy(usernameInput = username, errorMessage = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(passwordInput = password, errorMessage = null) }
    }

    fun onReferralChange(referral: String) {
        _uiState.update { it.copy(referralInput = referral, errorMessage = null) }
    }

    fun submit() {
        val state = _uiState.value
        val usernameVal = InputValidator.validateUsername(state.usernameInput)
        if (!usernameVal.isValid) {
            _uiState.update { it.copy(errorMessage = usernameVal.errorMessage) }
            return
        }

        val passVal = InputValidator.validatePassword(state.passwordInput)
        if (!passVal.isValid) {
            _uiState.update { it.copy(errorMessage = passVal.errorMessage) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = if (state.isLoginMode) {
                authRepository.login(state.usernameInput, state.passwordInput)
            } else {
                authRepository.register(state.usernameInput, state.passwordInput, state.referralInput.ifBlank { null })
            }

            when (result) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isAuthenticated = true,
                            successMessage = if (state.isLoginMode) "Login successful!" else "Account registered successfully!"
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }
                }
                Resource.Loading -> Unit
            }
        }
    }

    companion object {
        fun provideFactory(authRepository: AuthRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AuthViewModel(authRepository) as T
                }
            }
    }
}
