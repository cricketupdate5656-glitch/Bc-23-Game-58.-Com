package com.example.bc23.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "support_tickets",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["userId"]),
        Index(value = ["status"])
    ]
)
data class SupportTicketEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val ticketNumber: String,
    val subject: String,
    val category: String = "GENERAL",
    val status: String = "OPEN", // "OPEN", "IN_PROGRESS", "RESOLVED"
    val priority: String = "NORMAL", // "NORMAL", "HIGH", "VIP"
    val lastMessage: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
