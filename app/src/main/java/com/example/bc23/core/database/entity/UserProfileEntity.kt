package com.example.bc23.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_profiles",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["userId"], unique = true),
        Index(value = ["referralCode"], unique = true)
    ]
)
data class UserProfileEntity(
    @PrimaryKey
    val userId: String,
    val displayName: String,
    val avatarUrl: String? = null,
    val vipPoints: Long = 0,
    val vipTierName: String = "VIP 1 Bronze",
    val nextTierPoints: Long = 5000,
    val kycStatus: String = "PENDING",
    val referralCode: String,
    val lastLoginAt: Long = System.currentTimeMillis()
)
