package com.example.bc23.core.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "game_categories",
    indices = [
        Index(value = ["code"], unique = true)
    ]
)
data class GameCategoryEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val code: String,
    val iconResName: String,
    val displayOrder: Int,
    val isActive: Boolean = true
)
