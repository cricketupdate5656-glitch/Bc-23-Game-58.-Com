package com.example.bc23.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "referrals",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["referrerUserId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["referrerUserId"]),
        Index(value = ["joinedAt"])
    ]
)
data class ReferralEntity(
    @PrimaryKey
    val id: String,
    val referrerUserId: String,
    val referredUsername: String,
    val referredUid: String,
    val commissionEarnedDemo: Double = 0.0,
    val status: String = "ACTIVE",
    val joinedAt: Long = System.currentTimeMillis()
)
