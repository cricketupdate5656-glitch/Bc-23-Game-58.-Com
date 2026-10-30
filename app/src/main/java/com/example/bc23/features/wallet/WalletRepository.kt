package com.example.bc23.features.wallet

import com.example.bc23.core.common.Resource
import com.example.bc23.core.database.dao.WalletDao
import com.example.bc23.core.database.entity.TransactionEntity
import com.example.bc23.core.database.entity.WalletEntity
import com.example.bc23.core.security.DemoWalletGuard
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface WalletRepository {
    fun getWallet(userId: String): Flow<WalletEntity?>
    fun getTransactions(userId: String): Flow<List<TransactionEntity>>
    suspend fun performDemoDeposit(userId: String, amount: Double, paymentMethod: String): Resource<WalletEntity>
    suspend fun performDemoWithdrawal(userId: String, amount: Double, destination: String): Resource<WalletEntity>
}

class DefaultWalletRepository(
    private val walletDao: WalletDao
) : WalletRepository {

    override fun getWallet(userId: String): Flow<WalletEntity?> = walletDao.getWalletByUserId(userId)

    override fun getTransactions(userId: String): Flow<List<TransactionEntity>> = walletDao.getTransactionsByUserId(userId)

    override suspend fun performDemoDeposit(
        userId: String,
        amount: Double,
        paymentMethod: String
    ): Resource<WalletEntity> {
        return try {
            DemoWalletGuard.assertDemoModeAllowed()
            val sanitizedAmount = DemoWalletGuard.sanitizeDemoTransactionAmount(amount)

            walletDao.creditDemoBalance(userId, sanitizedAmount)

            // Log Demo Transaction
            val tx = TransactionEntity(
                id = "tx_demo_" + UUID.randomUUID().toString().take(8),
                walletId = "wal_demo_$userId",
                userId = userId,
                type = "DEMO_DEPOSIT",
                amount = sanitizedAmount,
                status = "COMPLETED",
                isDemo = true,
                referenceNote = "Demo Sandbox Balance Credit - Simulated",
                paymentChannel = paymentMethod
            )
            walletDao.insertTransaction(tx)

            Resource.Success(
                WalletEntity(
                    id = "wal_demo_$userId",
                    userId = userId,
                    demoBalance = sanitizedAmount,
                    isDemo = true
                )
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Demo deposit simulation failed", e)
        }
    }

    override suspend fun performDemoWithdrawal(
        userId: String,
        amount: Double,
        destination: String
    ): Resource<WalletEntity> {
        return try {
            DemoWalletGuard.assertDemoModeAllowed()
            val sanitizedAmount = DemoWalletGuard.sanitizeDemoTransactionAmount(amount)

            val updatedRows = walletDao.debitDemoBalance(userId, sanitizedAmount)
            if (updatedRows == 0) {
                return Resource.Error("Insufficient demo balance to process withdrawal request.")
            }

            // Log Demo Transaction
            val tx = TransactionEntity(
                id = "tx_demo_" + UUID.randomUUID().toString().take(8),
                walletId = "wal_demo_$userId",
                userId = userId,
                type = "DEMO_WITHDRAW",
                amount = sanitizedAmount,
                status = "COMPLETED",
                isDemo = true,
                referenceNote = "Demo Sandbox Payout - Simulated to $destination",
                paymentChannel = "Sandbox Vault"
            )
            walletDao.insertTransaction(tx)

            Resource.Success(
                WalletEntity(
                    id = "wal_demo_$userId",
                    userId = userId,
                    demoBalance = sanitizedAmount,
                    isDemo = true
                )
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Demo withdrawal simulation failed", e)
        }
    }
}
