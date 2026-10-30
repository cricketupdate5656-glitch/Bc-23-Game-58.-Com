package com.example.bc23.features.referral

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bc23.core.database.entity.ReferralEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class ReferralUiState(
    val referralCode: String = "BC58VIP",
    val inviteCount: Int = 3,
    val totalCommissionEarned: Double = 850.00,
    val commissionRate: String = "35%",
    val referrals: List<ReferralEntity> = emptyList()
)

class ReferralViewModel(
    private val referralRepository: ReferralRepository,
    private val userId: String = "usr_demo_5801"
) : ViewModel() {

    val uiState: StateFlow<ReferralUiState> = combine(
        referralRepository.getReferrals(userId),
        referralRepository.getReferralCount(userId),
        referralRepository.getTotalCommission(userId)
    ) { referrals, count, totalComm ->
        ReferralUiState(
            referralCode = "BC58VIP",
            inviteCount = count.coerceAtLeast(3),
            totalCommissionEarned = if (totalComm > 0) totalComm else 850.00,
            referrals = referrals
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ReferralUiState()
    )

    companion object {
        fun provideFactory(referralRepository: ReferralRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ReferralViewModel(referralRepository) as T
                }
            }
    }
}
