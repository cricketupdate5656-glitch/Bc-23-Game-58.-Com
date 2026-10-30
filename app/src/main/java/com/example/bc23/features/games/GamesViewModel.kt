package com.example.bc23.features.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bc23.core.database.entity.GameCategoryEntity
import com.example.bc23.core.database.entity.GameEntity
import com.example.bc23.core.database.entity.GameProviderEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

data class GamesUiState(
    val categories: List<GameCategoryEntity> = emptyList(),
    val providers: List<GameProviderEntity> = emptyList(),
    val allGames: List<GameEntity> = emptyList(),
    val filteredGames: List<GameEntity> = emptyList(),
    val selectedCategory: String = "ALL",
    val selectedProvider: String = "ALL",
    val searchQuery: String = ""
)

class GamesViewModel(
    private val gamesRepository: GamesRepository
) : ViewModel() {

    private val _filterState = MutableStateFlow(Triple("ALL", "ALL", ""))

    val uiState: StateFlow<GamesUiState> = combine(
        gamesRepository.getCategories(),
        gamesRepository.getProviders(),
        gamesRepository.getAllGames(),
        _filterState
    ) { categories, providers, games, (selectedCat, selectedProv, query) ->
        val filtered = games.filter { game ->
            val matchCat = selectedCat == "ALL" || game.categoryCode == selectedCat
            val matchProv = selectedProv == "ALL" || game.providerCode == selectedProv
            val matchQuery = query.isBlank() || game.title.contains(query, ignoreCase = true) || game.providerName.contains(query, ignoreCase = true)
            matchCat && matchProv && matchQuery
        }

        GamesUiState(
            categories = categories,
            providers = providers,
            allGames = games,
            filteredGames = filtered,
            selectedCategory = selectedCat,
            selectedProvider = selectedProv,
            searchQuery = query
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = GamesUiState()
    )

    fun onCategorySelected(code: String) {
        _filterState.update { it.copy(first = code) }
    }

    fun onProviderSelected(code: String) {
        _filterState.update { it.copy(second = code) }
    }

    fun onSearchQueryChanged(query: String) {
        _filterState.update { it.copy(third = query) }
    }

    companion object {
        fun provideFactory(gamesRepository: GamesRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return GamesViewModel(gamesRepository) as T
                }
            }
    }
}
