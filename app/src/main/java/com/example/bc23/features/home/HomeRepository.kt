package com.example.bc23.features.home

import com.example.bc23.core.database.dao.GameDao
import com.example.bc23.core.database.dao.PromotionDao
import com.example.bc23.core.database.dao.UserDao
import com.example.bc23.core.database.dao.WalletDao
import com.example.bc23.core.database.entity.GameCategoryEntity
import com.example.bc23.core.database.entity.GameEntity
import com.example.bc23.core.database.entity.PromotionEntity
import com.example.bc23.core.database.entity.UserEntity
import com.example.bc23.core.database.entity.UserProfileEntity
import com.example.bc23.core.database.entity.WalletEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getUser(): Flow<UserEntity?>
    fun getProfile(userId: String): Flow<UserProfileEntity?>
    fun getWallet(userId: String): Flow<WalletEntity?>
    fun getActivePromotions(): Flow<List<PromotionEntity>>
    fun getCategories(): Flow<List<GameCategoryEntity>>
    fun getHotGames(): Flow<List<GameEntity>>
}

class DefaultHomeRepository(
    private val userDao: UserDao,
    private val walletDao: WalletDao,
    private val gameDao: GameDao,
    private val promotionDao: PromotionDao
) : HomeRepository {
    override fun getUser(): Flow<UserEntity?> = userDao.getFirstUser()
    override fun getProfile(userId: String): Flow<UserProfileEntity?> = userDao.getUserProfile(userId)
    override fun getWallet(userId: String): Flow<WalletEntity?> = walletDao.getWalletByUserId(userId)
    override fun getActivePromotions(): Flow<List<PromotionEntity>> = promotionDao.getActivePromotions()
    override fun getCategories(): Flow<List<GameCategoryEntity>> = gameDao.getAllCategories()
    override fun getHotGames(): Flow<List<GameEntity>> = gameDao.getHotGames()
}
