package com.example.bc23.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = WalletEntity::class,
            parentColumns = ["id"],
            childColumns = ["walletId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["walletId"]),
        Index(value = ["userId"]),
        Index(value = ["timestamp"])
    ]
)
data class TransactionEntity(
    @PrimaryKey
    val id: String,
    val walletId: String,
    val userId: String,
    val type: String, // "DEMO_DEPOSIT", "DEMO_WITHDRAW", "VIP_BONUS", "DAILY_REBATE"
    val amount: Double,
    val status: String = "COMPLETED", // "COMPLETED", "PENDING", "FAILED"
    val timestamp: Long = System.currentTimeMillis(),
    val isDemo: Boolean = true,
    val referenceNote: String,
    val paymentChannel: String = "Sandbox Wallet"
)
