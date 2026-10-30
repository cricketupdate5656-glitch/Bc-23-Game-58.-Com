package com.example.bc23.features.promotions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bc23.core.database.entity.PromotionEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

data class PromotionsUiState(
    val promotions: List<PromotionEntity> = emptyList(),
    val selectedCategory: String = "ALL",
    val claimedPromotionIds: Set<String> = emptySet(),
    val claimSuccessMessage: String? = null
)

class PromotionsViewModel(
    private val promotionsRepository: PromotionsRepository
) : ViewModel() {

    private val _filterCategory = MutableStateFlow("ALL")
    private val _claimedIds = MutableStateFlow<Set<String>>(emptySet())
    private val _claimMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<PromotionsUiState> = combine(
        promotionsRepository.getActivePromotions(),
        _filterCategory,
        _claimedIds,
        _claimMessage
    ) { promos, cat, claimed, msg ->
        PromotionsUiState(
            promotions = if (cat == "ALL") promos else promos.filter { it.category == cat },
            selectedCategory = cat,
            claimedPromotionIds = claimed,
            claimSuccessMessage = msg
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PromotionsUiState()
    )

    fun onCategorySelect(category: String) {
        _filterCategory.value = category
        _claimMessage.value = null
    }

    fun claimPromotion(promotion: PromotionEntity) {
        _claimedIds.update { it + promotion.id }
        _claimMessage.value = "Demo Reward '${promotion.title}' simulated successfully! +$${promotion.maxBonusAmount.toInt()} demo credits claimed."
    }

    companion object {
        fun provideFactory(promotionsRepository: PromotionsRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PromotionsViewModel(promotionsRepository) as T
                }
            }
    }
}
