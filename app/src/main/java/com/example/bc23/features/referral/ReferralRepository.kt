package com.example.bc23.features.referral

import com.example.bc23.core.database.dao.ReferralDao
import com.example.bc23.core.database.entity.ReferralEntity
import kotlinx.coroutines.flow.Flow

interface ReferralRepository {
    fun getReferrals(userId: String): Flow<List<ReferralEntity>>
    fun getReferralCount(userId: String): Flow<Int>
    fun getTotalCommission(userId: String): Flow<Double>
}

class DefaultReferralRepository(
    private val referralDao: ReferralDao
) : ReferralRepository {
    override fun getReferrals(userId: String): Flow<List<ReferralEntity>> = referralDao.getReferralsByUserId(userId)
    override fun getReferralCount(userId: String): Flow<Int> = referralDao.getReferralCount(userId)
    override fun getTotalCommission(userId: String): Flow<Double> = referralDao.getTotalCommission(userId)
}
