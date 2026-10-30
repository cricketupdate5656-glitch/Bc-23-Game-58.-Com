package com.example.bc23.features.notifications

import com.example.bc23.core.database.dao.NotificationDao
import com.example.bc23.core.database.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

interface NotificationsRepository {
    fun getNotifications(userId: String): Flow<List<NotificationEntity>>
    fun getUnreadCount(userId: String): Flow<Int>
    suspend fun markAsRead(notificationId: String)
    suspend fun markAllAsRead(userId: String)
}

class DefaultNotificationsRepository(
    private val notificationDao: NotificationDao
) : NotificationsRepository {
    override fun getNotifications(userId: String): Flow<List<NotificationEntity>> = notificationDao.getNotificationsByUserId(userId)
    override fun getUnreadCount(userId: String): Flow<Int> = notificationDao.getUnreadCount(userId)
    override suspend fun markAsRead(notificationId: String) = notificationDao.markAsRead(notificationId)
    override suspend fun markAllAsRead(userId: String) = notificationDao.markAllAsRead(userId)
}
