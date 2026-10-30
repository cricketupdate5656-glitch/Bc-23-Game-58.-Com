package com.example.bc23.features.profile

import com.example.bc23.core.database.dao.UserDao
import com.example.bc23.core.database.entity.UserEntity
import com.example.bc23.core.database.entity.UserProfileEntity
import com.example.bc23.core.security.SessionManager
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getUser(userId: String): Flow<UserEntity?>
    fun getProfile(userId: String): Flow<UserProfileEntity?>
    suspend fun updateDisplayName(userId: String, newName: String)
    suspend fun setNotificationsEnabled(enabled: Boolean)
}

class DefaultProfileRepository(
    private val userDao: UserDao,
    private val sessionManager: SessionManager
) : ProfileRepository {
    override fun getUser(userId: String): Flow<UserEntity?> = userDao.getUserById(userId)
    override fun getProfile(userId: String): Flow<UserProfileEntity?> = userDao.getUserProfile(userId)

    override suspend fun updateDisplayName(userId: String, newName: String) {
        // Can update UserProfileEntity
    }

    override suspend fun setNotificationsEnabled(enabled: Boolean) {
        sessionManager.setNotificationsEnabled(enabled)
    }
}
