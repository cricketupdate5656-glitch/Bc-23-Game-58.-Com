package com.example.bc23.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bc23.core.database.entity.GameCategoryEntity
import com.example.bc23.core.database.entity.GameEntity
import com.example.bc23.core.database.entity.GameProviderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Query("SELECT * FROM game_categories WHERE isActive = 1 ORDER BY displayOrder ASC")
    fun getAllCategories(): Flow<List<GameCategoryEntity>>

    @Query("SELECT * FROM game_providers WHERE isActive = 1 ORDER BY name ASC")
    fun getAllProviders(): Flow<List<GameProviderEntity>>

    @Query("SELECT * FROM games ORDER BY playCount DESC")
    fun getAllGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM games WHERE categoryCode = :categoryCode ORDER BY playCount DESC")
    fun getGamesByCategory(categoryCode: String): Flow<List<GameEntity>>

    @Query("SELECT * FROM games WHERE isHot = 1 ORDER BY playCount DESC LIMIT 10")
    fun getHotGames(): Flow<List<GameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<GameCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProviders(providers: List<GameProviderEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GameEntity>)
}
