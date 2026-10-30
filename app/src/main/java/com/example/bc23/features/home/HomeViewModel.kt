package com.example.bc23.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bc23.core.common.Constants
import com.example.bc23.core.database.entity.GameCategoryEntity
import com.example.bc23.core.database.entity.GameEntity
import com.example.bc23.core.database.entity.PromotionEntity
import com.example.bc23.core.database.entity.UserEntity
import com.example.bc23.core.database.entity.UserProfileEntity
import com.example.bc23.core.database.entity.WalletEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class HomeUiState(
    val user: UserEntity? = null,
    val profile: UserProfileEntity? = null,
    val wallet: WalletEntity? = null,
    val promotions: List<PromotionEntity> = emptyList(),
    val categories: List<GameCategoryEntity> = emptyList(),
    val hotGames: List<GameEntity> = emptyList(),
    val selectedCategoryCode: String = "ALL",
    val unreadNotificationsCount: Int = 2
)

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow("ALL")

    val uiState: StateFlow<HomeUiState> = combine(
        homeRepository.getUser(),
        homeRepository.getActivePromotions(),
        homeRepository.getCategories(),
        homeRepository.getHotGames(),
        _selectedCategory
    ) { user, promos, categories, hotGames, selectedCat ->
        HomeUiState(
            user = user,
            promotions = promos,
            categories = categories,
            hotGames = if (selectedCat == "ALL") hotGames else hotGames.filter { it.categoryCode == selectedCat },
            selectedCategoryCode = selectedCat
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )

    fun onCategorySelect(code: String) {
        _selectedCategory.value = code
    }

    companion object {
        fun provideFactory(homeRepository: HomeRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeViewModel(homeRepository) as T
                }
            }
    }
}
