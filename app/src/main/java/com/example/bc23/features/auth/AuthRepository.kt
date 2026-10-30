package com.example.bc23.features.auth

import com.example.bc23.core.common.Resource
import com.example.bc23.core.database.dao.UserDao
import com.example.bc23.core.database.entity.UserEntity
import com.example.bc23.core.database.entity.UserProfileEntity
import com.example.bc23.core.network.BC23ApiService
import com.example.bc23.core.security.SecurityUtils
import com.example.bc23.core.security.SessionManager
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface AuthRepository {
    fun getCurrentUser(userId: String): Flow<UserEntity?>
    fun getFirstUser(): Flow<UserEntity?>
    fun getProfile(userId: String): Flow<UserProfileEntity?>
    suspend fun login(username: String, passwordRaw: String): Resource<UserEntity>
    suspend fun register(username: String, passwordRaw: String, referralCode: String?): Resource<UserEntity>
    suspend fun logout()
}

class DefaultAuthRepository(
    private val userDao: UserDao,
    private val sessionManager: SessionManager,
    private val apiService: BC23ApiService
) : AuthRepository {

    override fun getCurrentUser(userId: String): Flow<UserEntity?> = userDao.getUserById(userId)

    override fun getFirstUser(): Flow<UserEntity?> = userDao.getFirstUser()

    override fun getProfile(userId: String): Flow<UserProfileEntity?> = userDao.getUserProfile(userId)

    override suspend fun logout() {
        sessionManager.clearSession()
    }

    override suspend fun login(username: String, passwordRaw: String): Resource<UserEntity> {
        return try {
            val user = userDao.getUserByUsername(username)
            if (user == null) {
                Resource.Error("User '$username' not found. Please register an account.")
            } else {
                val inputHash = SecurityUtils.hashSha256(passwordRaw)
                if (user.passwordHash == inputHash) {
                    val token = SecurityUtils.generateSecureToken()
                    sessionManager.saveSession(user.id, user.username, user.uid, token)
                    Resource.Success(user)
                } else {
                    Resource.Error("Incorrect password. Please verify credentials.")
                }
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Login authentication failed", e)
        }
    }

    override suspend fun register(username: String, passwordRaw: String, referralCode: String?): Resource<UserEntity> {
        return try {
            val existing = userDao.getUserByUsername(username)
            if (existing != null) {
                return Resource.Error("Username '$username' is already registered.")
            }

            val newId = "usr_" + UUID.randomUUID().toString().take(8)
            val newUid = (58000000 + (100000..999999).random()).toString()
            val passHash = SecurityUtils.hashSha256(passwordRaw)

            val newUser = UserEntity(
                id = newId,
                uid = newUid,
                username = username,
                passwordHash = passHash,
                vipLevel = 1
            )
            userDao.insertUser(newUser)

            val newProfile = UserProfileEntity(
                userId = newId,
                displayName = username,
                vipPoints = 100,
                vipTierName = "Bronze VIP 1",
                nextTierPoints = 2500,
                kycStatus = "PENDING",
                referralCode = "BC58" + username.take(4).uppercase()
            )
            userDao.insertUserProfile(newProfile)

            val token = SecurityUtils.generateSecureToken()
            sessionManager.saveSession(newId, username, newUid, token)

            Resource.Success(newUser)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Registration failed", e)
        }
    }
}
