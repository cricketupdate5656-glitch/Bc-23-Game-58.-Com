package com.example.bc23.features.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bc23.core.common.Resource
import com.example.bc23.core.database.entity.TransactionEntity
import com.example.bc23.core.database.entity.WalletEntity
import com.example.bc23.core.security.InputValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class WalletUiState(
    val wallet: WalletEntity? = null,
    val transactions: List<TransactionEntity> = emptyList(),
    val selectedTab: Int = 0, // 0 = Deposit, 1 = Withdraw, 2 = Transactions
    val amountInput: String = "100.00",
    val paymentChannel: String = "Sandbox Instant Bank",
    val withdrawalDestination: String = "Demo Account #5829-US",
    val isLoading: Boolean = false,
    val feedbackMessage: String? = null,
    val isError: Boolean = false
)

class WalletViewModel(
    private val walletRepository: WalletRepository,
    private val userId: String = "usr_demo_5801"
) : ViewModel() {

    private val _viewState = MutableStateFlow(WalletUiState())

    val uiState: StateFlow<WalletUiState> = combine(
        walletRepository.getWallet(userId),
        walletRepository.getTransactions(userId),
        _viewState
    ) { wallet, txList, state ->
        state.copy(
            wallet = wallet,
            transactions = txList
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WalletUiState()
    )

    fun onTabSelected(tabIndex: Int) {
        _viewState.update { it.copy(selectedTab = tabIndex, feedbackMessage = null) }
    }

    fun onAmountChanged(amount: String) {
        _viewState.update { it.copy(amountInput = amount, feedbackMessage = null) }
    }

    fun onPaymentChannelChanged(channel: String) {
        _viewState.update { it.copy(paymentChannel = channel) }
    }

    fun onDestinationChanged(destination: String) {
        _viewState.update { it.copy(withdrawalDestination = destination) }
    }

    fun executeDemoDeposit() {
        val amountStr = _viewState.value.amountInput
        val validation = InputValidator.validateDemoAmount(amountStr, minAmount = 10.0, maxAmount = 25000.0)
        if (!validation.isValid) {
            _viewState.update { it.copy(feedbackMessage = validation.errorMessage, isError = true) }
            return
        }

        val amount = amountStr.toDouble()
        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true, feedbackMessage = null) }
            val result = walletRepository.performDemoDeposit(userId, amount, _viewState.value.paymentChannel)
            when (result) {
                is Resource.Success -> {
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            feedbackMessage = "Simulated deposit of $$amount successfully credited to your Demo Balance!",
                            isError = false
                        )
                    }
                }
                is Resource.Error -> {
                    _viewState.update { it.copy(isLoading = false, feedbackMessage = result.message, isError = true) }
                }
                Resource.Loading -> Unit
            }
        }
    }

    fun executeDemoWithdrawal() {
        val amountStr = _viewState.value.amountInput
        val validation = InputValidator.validateDemoAmount(amountStr, minAmount = 20.0, maxAmount = 10000.0)
        if (!validation.isValid) {
            _viewState.update { it.copy(feedbackMessage = validation.errorMessage, isError = true) }
            return
        }

        val amount = amountStr.toDouble()
        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true, feedbackMessage = null) }
            val result = walletRepository.performDemoWithdrawal(userId, amount, _viewState.value.withdrawalDestination)
            when (result) {
                is Resource.Success -> {
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            feedbackMessage = "Simulated withdrawal of $$amount approved in Sandbox environment.",
                            isError = false
                        )
                    }
                }
                is Resource.Error -> {
                    _viewState.update { it.copy(isLoading = false, feedbackMessage = result.message, isError = true) }
                }
                Resource.Loading -> Unit
            }
        }
    }

    companion object {
        fun provideFactory(walletRepository: WalletRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return WalletViewModel(walletRepository) as T
                }
            }
    }
}
