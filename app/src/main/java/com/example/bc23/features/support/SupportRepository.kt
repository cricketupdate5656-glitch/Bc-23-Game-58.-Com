package com.example.bc23.features.support

import com.example.bc23.core.database.dao.SupportDao
import com.example.bc23.core.database.entity.SupportTicketEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface SupportRepository {
    fun getTickets(userId: String): Flow<List<SupportTicketEntity>>
    suspend fun createTicket(userId: String, subject: String, category: String, message: String): SupportTicketEntity
}

class DefaultSupportRepository(
    private val supportDao: SupportDao
) : SupportRepository {
    override fun getTickets(userId: String): Flow<List<SupportTicketEntity>> = supportDao.getTicketsByUserId(userId)

    override suspend fun createTicket(
        userId: String,
        subject: String,
        category: String,
        message: String
    ): SupportTicketEntity {
        val ticket = SupportTicketEntity(
            id = "tkt_" + UUID.randomUUID().toString().take(8),
            userId = userId,
            ticketNumber = "BC23-" + (1000..9999).random(),
            subject = subject,
            category = category,
            status = "OPEN",
            priority = "NORMAL",
            lastMessage = message
        )
        supportDao.insertTicket(ticket)
        return ticket
    }
}
