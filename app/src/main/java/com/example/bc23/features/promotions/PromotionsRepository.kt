package com.example.bc23.features.promotions

import com.example.bc23.core.database.dao.PromotionDao
import com.example.bc23.core.database.entity.PromotionEntity
import kotlinx.coroutines.flow.Flow

interface PromotionsRepository {
    fun getActivePromotions(): Flow<List<PromotionEntity>>
    suspend fun getPromotionById(id: String): PromotionEntity?
}

class DefaultPromotionsRepository(
    private val promotionDao: PromotionDao
) : PromotionsRepository {
    override fun getActivePromotions(): Flow<List<PromotionEntity>> = promotionDao.getActivePromotions()
    override suspend fun getPromotionById(id: String): PromotionEntity? = promotionDao.getPromotionById(id)
}
