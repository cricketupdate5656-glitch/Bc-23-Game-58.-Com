package com.example.bc23.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bc23.core.database.entity.SupportTicketEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SupportDao {

    @Query("SELECT * FROM support_tickets WHERE userId = :userId ORDER BY updatedAt DESC")
    fun getTicketsByUserId(userId: String): Flow<List<SupportTicketEntity>>

    @Query("SELECT * FROM support_tickets WHERE id = :ticketId LIMIT 1")
    suspend fun getTicketById(ticketId: String): SupportTicketEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: SupportTicketEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTickets(tickets: List<SupportTicketEntity>)
}
