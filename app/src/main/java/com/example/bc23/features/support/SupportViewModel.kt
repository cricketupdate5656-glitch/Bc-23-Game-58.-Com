package com.example.bc23.features.support

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bc23.core.database.entity.SupportTicketEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SupportUiState(
    val tickets: List<SupportTicketEntity> = emptyList(),
    val subjectInput: String = "",
    val messageInput: String = "",
    val selectedCategory: String = "VIP_CONCIERGE",
    val isSubmitting: Boolean = false,
    val feedbackMessage: String? = null
)

class SupportViewModel(
    private val supportRepository: SupportRepository,
    private val userId: String = "usr_demo_5801"
) : ViewModel() {

    private val _formState = MutableStateFlow(Triple("", "", "VIP_CONCIERGE"))
    private val _submitState = MutableStateFlow<String?>(null)

    val uiState: StateFlow<SupportUiState> = combine(
        supportRepository.getTickets(userId),
        _formState,
        _submitState
    ) { tickets, (subject, message, cat), feedback ->
        SupportUiState(
            tickets = tickets,
            subjectInput = subject,
            messageInput = message,
            selectedCategory = cat,
            feedbackMessage = feedback
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SupportUiState()
    )

    fun onSubjectChanged(subject: String) {
        _formState.update { it.copy(first = subject) }
    }

    fun onMessageChanged(message: String) {
        _formState.update { it.copy(second = message) }
    }

    fun onCategoryChanged(category: String) {
        _formState.update { it.copy(third = category) }
    }

    fun submitTicket() {
        val (subject, message, category) = _formState.value
        if (subject.isBlank() || message.isBlank()) {
            _submitState.value = "Please complete both subject and message fields."
            return
        }

        viewModelScope.launch {
            supportRepository.createTicket(userId, subject, category, message)
            _formState.value = Triple("", "", "VIP_CONCIERGE")
            _submitState.value = "Support ticket submitted! A VIP specialist will respond within 5 minutes."
        }
    }

    companion object {
        fun provideFactory(supportRepository: SupportRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SupportViewModel(supportRepository) as T
                }
            }
    }
}
