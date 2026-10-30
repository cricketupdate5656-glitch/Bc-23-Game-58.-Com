package com.example.bc23.features.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bc23.core.database.entity.NotificationEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class NotificationsUiState(
    val notifications: List<NotificationEntity> = emptyList(),
    val unreadCount: Int = 0
)

class NotificationsViewModel(
    private val notificationsRepository: NotificationsRepository,
    private val userId: String = "usr_demo_5801"
) : ViewModel() {

    val uiState: StateFlow<NotificationsUiState> = combine(
        notificationsRepository.getNotifications(userId),
        notificationsRepository.getUnreadCount(userId)
    ) { notifications, unread ->
        NotificationsUiState(
            notifications = notifications,
            unreadCount = unread
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NotificationsUiState()
    )

    fun markAsRead(id: String) {
        viewModelScope.launch {
            notificationsRepository.markAsRead(id)
        }
    }

    fun markAllAsRead() {
        viewModelScope.launch {
            notificationsRepository.markAllAsRead(userId)
        }
    }

    companion object {
        fun provideFactory(notificationsRepository: NotificationsRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NotificationsViewModel(notificationsRepository) as T
                }
            }
    }
}
