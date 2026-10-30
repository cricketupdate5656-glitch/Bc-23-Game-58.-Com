package com.example.bc23.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "promotions")
data class PromotionEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val subtitle: String,
    val bannerDrawableName: String? = null,
    val bonusPercentage: Int = 100,
    val maxBonusAmount: Double = 500.0,
    val category: String = "WELCOME",
    val terms: String,
    val isActive: Boolean = true,
    val validUntil: Long = System.currentTimeMillis() + 86400000L * 30
)
