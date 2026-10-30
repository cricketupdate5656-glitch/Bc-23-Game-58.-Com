package com.example.bc23.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bc23.core.database.entity.PromotionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PromotionDao {

    @Query("SELECT * FROM promotions WHERE isActive = 1 ORDER BY validUntil ASC")
    fun getActivePromotions(): Flow<List<PromotionEntity>>

    @Query("SELECT * FROM promotions WHERE id = :id LIMIT 1")
    suspend fun getPromotionById(id: String): PromotionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPromotions(promotions: List<PromotionEntity>)
}
