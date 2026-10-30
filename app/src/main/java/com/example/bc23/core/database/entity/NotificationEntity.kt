package com.example.bc23.core.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "notifications",
    indices = [
        Index(value = ["userId"]),
        Index(value = ["timestamp"]),
        Index(value = ["isRead"])
    ]
)
data class NotificationEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val title: String,
    val message: String,
    val type: String = "SYSTEM", // "SYSTEM", "PROMOTION", "VIP", "SECURITY"
    val isRead: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
