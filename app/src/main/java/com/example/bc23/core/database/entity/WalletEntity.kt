package com.example.bc23.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "wallets",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["userId"], unique = true)
    ]
)
data class WalletEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val demoBalance: Double = 12580.00,
    val currency: String = "USD",
    val isDemo: Boolean = true,
    val totalDepositedDemo: Double = 15000.00,
    val totalWithdrawnDemo: Double = 2420.00,
    val frozenBalance: Double = 0.0,
    val updatedAt: Long = System.currentTimeMillis()
)
