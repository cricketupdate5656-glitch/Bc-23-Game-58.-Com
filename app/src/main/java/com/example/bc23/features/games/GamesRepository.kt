package com.example.bc23.features.games

import com.example.bc23.core.database.dao.GameDao
import com.example.bc23.core.database.entity.GameCategoryEntity
import com.example.bc23.core.database.entity.GameEntity
import com.example.bc23.core.database.entity.GameProviderEntity
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    fun getCategories(): Flow<List<GameCategoryEntity>>
    fun getProviders(): Flow<List<GameProviderEntity>>
    fun getAllGames(): Flow<List<GameEntity>>
    fun getGamesByCategory(categoryCode: String): Flow<List<GameEntity>>
    fun getHotGames(): Flow<List<GameEntity>>
}

class DefaultGamesRepository(
    private val gameDao: GameDao
) : GamesRepository {
    override fun getCategories(): Flow<List<GameCategoryEntity>> = gameDao.getAllCategories()
    override fun getProviders(): Flow<List<GameProviderEntity>> = gameDao.getAllProviders()
    override fun getAllGames(): Flow<List<GameEntity>> = gameDao.getAllGames()
    override fun getGamesByCategory(categoryCode: String): Flow<List<GameEntity>> = gameDao.getGamesByCategory(categoryCode)
    override fun getHotGames(): Flow<List<GameEntity>> = gameDao.getHotGames()
}
