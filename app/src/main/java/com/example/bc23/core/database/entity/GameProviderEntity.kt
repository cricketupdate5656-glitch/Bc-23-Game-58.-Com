package com.example.bc23.core.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "game_providers",
    indices = [
        Index(value = ["code"], unique = true)
    ]
)
data class GameProviderEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val code: String,
    val logoUrl: String? = null,
    val gameCount: Int = 0,
    val isActive: Boolean = true
)
