package com.example.bc23.core.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "games",
    indices = [
        Index(value = ["categoryCode"]),
        Index(value = ["providerCode"]),
        Index(value = ["isHot"]),
        Index(value = ["isNew"])
    ]
)
data class GameEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val categoryCode: String,
    val providerCode: String,
    val providerName: String,
    val rating: Float = 4.8f,
    val playCount: Long = 12000L,
    val isHot: Boolean = false,
    val isNew: Boolean = false,
    val demoAvailable: Boolean = true
)
