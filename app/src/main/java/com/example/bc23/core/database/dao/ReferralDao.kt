package com.example.bc23.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bc23.core.database.entity.ReferralEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReferralDao {

    @Query("SELECT * FROM referrals WHERE referrerUserId = :userId ORDER BY joinedAt DESC")
    fun getReferralsByUserId(userId: String): Flow<List<ReferralEntity>>

    @Query("SELECT COUNT(*) FROM referrals WHERE referrerUserId = :userId")
    fun getReferralCount(userId: String): Flow<Int>

    @Query("SELECT COALESCE(SUM(commissionEarnedDemo), 0.0) FROM referrals WHERE referrerUserId = :userId")
    fun getTotalCommission(userId: String): Flow<Double>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReferral(referral: ReferralEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReferrals(referrals: List<ReferralEntity>)
}
