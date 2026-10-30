package com.example.bc23.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bc23.core.database.entity.TransactionEntity
import com.example.bc23.core.database.entity.WalletEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDao {

    @Query("SELECT * FROM wallets WHERE userId = :userId LIMIT 1")
    fun getWalletByUserId(userId: String): Flow<WalletEntity?>

    @Query("SELECT * FROM wallets LIMIT 1")
    fun getFirstWallet(): Flow<WalletEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallet(wallet: WalletEntity)

    @Update
    suspend fun updateWallet(wallet: WalletEntity)

    @Query("UPDATE wallets SET demoBalance = demoBalance + :amount, totalDepositedDemo = totalDepositedDemo + :amount, updatedAt = :timestamp WHERE userId = :userId")
    suspend fun creditDemoBalance(userId: String, amount: Double, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE wallets SET demoBalance = demoBalance - :amount, totalWithdrawnDemo = totalWithdrawnDemo + :amount, updatedAt = :timestamp WHERE userId = :userId AND demoBalance >= :amount")
    suspend fun debitDemoBalance(userId: String, amount: Double, timestamp: Long = System.currentTimeMillis()): Int

    @Query("SELECT * FROM transactions WHERE userId = :userId ORDER BY timestamp DESC")
    fun getTransactionsByUserId(userId: String): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)
}
