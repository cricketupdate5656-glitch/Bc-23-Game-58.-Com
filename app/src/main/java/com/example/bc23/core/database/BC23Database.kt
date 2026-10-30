package com.example.bc23.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.bc23.core.common.Constants
import com.example.bc23.core.database.dao.GameDao
import com.example.bc23.core.database.dao.NotificationDao
import com.example.bc23.core.database.dao.PromotionDao
import com.example.bc23.core.database.dao.ReferralDao
import com.example.bc23.core.database.dao.SupportDao
import com.example.bc23.core.database.dao.UserDao
import com.example.bc23.core.database.dao.WalletDao
import com.example.bc23.core.database.entity.GameCategoryEntity
import com.example.bc23.core.database.entity.GameEntity
import com.example.bc23.core.database.entity.GameProviderEntity
import com.example.bc23.core.database.entity.NotificationEntity
import com.example.bc23.core.database.entity.PromotionEntity
import com.example.bc23.core.database.entity.ReferralEntity
import com.example.bc23.core.database.entity.SupportTicketEntity
import com.example.bc23.core.database.entity.TransactionEntity
import com.example.bc23.core.database.entity.UserEntity
import com.example.bc23.core.database.entity.UserProfileEntity
import com.example.bc23.core.database.entity.WalletEntity
import com.example.bc23.core.security.SecurityUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserEntity::class,
        UserProfileEntity::class,
        WalletEntity::class,
        TransactionEntity::class,
        GameCategoryEntity::class,
        GameProviderEntity::class,
        PromotionEntity::class,
        ReferralEntity::class,
        NotificationEntity::class,
        SupportTicketEntity::class,
        GameEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BC23Database : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun walletDao(): WalletDao
    abstract fun gameDao(): GameDao
    abstract fun promotionDao(): PromotionDao
    abstract fun referralDao(): ReferralDao
    abstract fun notificationDao(): NotificationDao
    abstract fun supportDao(): SupportDao

    companion object {
        @Volatile
        private var INSTANCE: BC23Database? = null

        fun getDatabase(context: Context, scope: CoroutineScope): BC23Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BC23Database::class.java,
                    Constants.DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateSeedData(database)
                    }
                }
            }
        }

        suspend fun populateSeedData(db: BC23Database) {
            val defaultUserId = "usr_demo_5801"
            val defaultUid = "58921473"
            val defaultUsername = "arshad_lucky"
            val defaultPasswordHash = SecurityUtils.hashSha256("bc23demo123")

            // 1. Seed User & Profile
            val user = UserEntity(
                id = defaultUserId,
                uid = defaultUid,
                username = defaultUsername,
                passwordHash = defaultPasswordHash,
                email = "arshadlucky2478@gmail.com",
                phoneNumber = "+1 (555) 019-5823",
                isVerified = true,
                vipLevel = 3
            )
            db.userDao().insertUser(user)

            val profile = UserProfileEntity(
                userId = defaultUserId,
                displayName = "Arshad Lucky",
                vipPoints = 4500,
                vipTierName = "Gold VIP 3",
                nextTierPoints = 10000,
                kycStatus = "VERIFIED",
                referralCode = "BC58VIP"
            )
            db.userDao().insertUserProfile(profile)

            // 2. Seed Demo Wallet
            val walletId = "wal_demo_5801"
            val wallet = WalletEntity(
                id = walletId,
                userId = defaultUserId,
                demoBalance = Constants.DEMO_INITIAL_BALANCE,
                currency = "USD",
                isDemo = true,
                totalDepositedDemo = 15000.0,
                totalWithdrawnDemo = 2420.0
            )
            db.walletDao().insertWallet(wallet)

            // 3. Seed Transactions
            val transactions = listOf(
                TransactionEntity(
                    id = "tx_001",
                    walletId = walletId,
                    userId = defaultUserId,
                    type = "DEMO_DEPOSIT",
                    amount = 5000.0,
                    status = "COMPLETED",
                    timestamp = System.currentTimeMillis() - 86400000L * 2,
                    referenceNote = "Demo Sandbox Top-Up #5819",
                    paymentChannel = "Sandbox Instant Bank"
                ),
                TransactionEntity(
                    id = "tx_002",
                    walletId = walletId,
                    userId = defaultUserId,
                    type = "VIP_BONUS",
                    amount = 250.0,
                    status = "COMPLETED",
                    timestamp = System.currentTimeMillis() - 86400000L,
                    referenceNote = "VIP 3 Monthly Appreciation Bonus",
                    paymentChannel = "BC23 Rewards Engine"
                ),
                TransactionEntity(
                    id = "tx_003",
                    walletId = walletId,
                    userId = defaultUserId,
                    type = "DAILY_REBATE",
                    amount = 80.0,
                    status = "COMPLETED",
                    timestamp = System.currentTimeMillis() - 3600000L * 5,
                    referenceNote = "Daily Casino Rebate 0.8%",
                    paymentChannel = "System Auto-Credit"
                )
            )
            transactions.forEach { db.walletDao().insertTransaction(it) }

            // 4. Seed Categories
            val categories = listOf(
                GameCategoryEntity("cat_live", "Live Casino", "LIVE", "ic_live", 1),
                GameCategoryEntity("cat_slots", "Slots", "SLOTS", "ic_slots", 2),
                GameCategoryEntity("cat_sports", "Sportsbook", "SPORTS", "ic_sports", 3),
                GameCategoryEntity("cat_cards", "Card Games", "CARDS", "ic_cards", 4),
                GameCategoryEntity("cat_fishing", "Fish Hunter", "FISHING", "ic_fishing", 5),
                GameCategoryEntity("cat_lottery", "Lottery 58", "LOTTERY", "ic_lottery", 6)
            )
            db.gameDao().insertCategories(categories)

            // 5. Seed Providers
            val providers = listOf(
                GameProviderEntity("prv_pragmatic", "Pragmatic Play", "PRAGMATIC", gameCount = 180),
                GameProviderEntity("prv_evolution", "Evolution Gaming", "EVOLUTION", gameCount = 95),
                GameProviderEntity("prv_pgsoft", "PG Soft", "PGSOFT", gameCount = 120),
                GameProviderEntity("prv_jili", "JILI Games", "JILI", gameCount = 85),
                GameProviderEntity("prv_spade", "Spadegaming", "SPADE", gameCount = 64)
            )
            db.gameDao().insertProviders(providers)

            // 6. Seed Games
            val games = listOf(
                GameEntity("g_01", "Crazy Time Live", "LIVE", "EVOLUTION", "Evolution Gaming", 4.9f, 48200L, isHot = true),
                GameEntity("g_02", "Lightning Roulette", "LIVE", "EVOLUTION", "Evolution Gaming", 4.8f, 32400L, isHot = true),
                GameEntity("g_03", "Gates of Olympus", "SLOTS", "PRAGMATIC", "Pragmatic Play", 4.9f, 65000L, isHot = true),
                GameEntity("g_04", "Sweet Bonanza 1000", "SLOTS", "PRAGMATIC", "Pragmatic Play", 4.8f, 42100L, isHot = true),
                GameEntity("g_05", "Mahjong Ways 2", "SLOTS", "PGSOFT", "PG Soft", 4.7f, 38900L, isNew = true),
                GameEntity("g_06", "Fortune Tiger", "SLOTS", "PGSOFT", "PG Soft", 4.8f, 29000L, isHot = true),
                GameEntity("g_07", "Premier League Live Hub", "SPORTS", "PRAGMATIC", "BC23 Sports", 4.9f, 51000L, isHot = true),
                GameEntity("g_08", "NBA Finals Match Center", "SPORTS", "PRAGMATIC", "BC23 Sports", 4.7f, 22100L),
                GameEntity("g_09", "Texas Hold'em Poker 58", "CARDS", "EVOLUTION", "Evolution Gaming", 4.8f, 18500L),
                GameEntity("g_10", "Mega Fishing King", "FISHING", "JILI", "JILI Games", 4.6f, 14200L, isNew = true)
            )
            db.gameDao().insertGames(games)

            // 7. Seed Promotions
            val promotions = listOf(
                PromotionEntity(
                    id = "promo_01",
                    title = "VIP Royale Grand Jackpot",
                    subtitle = "Join elite VIP Club with up to 200% match bonus on demo play",
                    bonusPercentage = 200,
                    maxBonusAmount = 2500.0,
                    category = "VIP",
                    terms = "Simulated VIP membership terms for demonstration purposes only."
                ),
                PromotionEntity(
                    id = "promo_02",
                    title = "100% First Demo Top-Up",
                    subtitle = "Double your initial demo credits up to $1,000 sandbox tokens",
                    bonusPercentage = 100,
                    maxBonusAmount = 1000.0,
                    category = "WELCOME",
                    terms = "Valid for new test accounts. Demo credits have no real world cash value."
                ),
                PromotionEntity(
                    id = "promo_03",
                    title = "Daily 1.2% Loss Rebate",
                    subtitle = "Automatic daily demo credit rebate credited every midnight UTC",
                    bonusPercentage = 12,
                    maxBonusAmount = 500.0,
                    category = "DAILY_REBATE",
                    terms = "Automated credit calculation based on daily active demo sessions."
                )
            )
            db.promotionDao().insertPromotions(promotions)

            // 8. Seed Referrals
            val referrals = listOf(
                ReferralEntity("ref_01", defaultUserId, "crypto_king99", "58102394", 450.0),
                ReferralEntity("ref_02", defaultUserId, "slot_master_x", "58882012", 280.0),
                ReferralEntity("ref_03", defaultUserId, "lucky_seven7", "58334411", 120.0)
            )
            db.referralDao().insertReferrals(referrals)

            // 9. Seed Notifications
            val notifications = listOf(
                NotificationEntity(
                    id = "notif_01",
                    userId = defaultUserId,
                    title = "Welcome to BC23 Game 58.com",
                    message = "Your VIP 3 account has been initialized with $12,580.00 in demo credits.",
                    type = "SYSTEM"
                ),
                NotificationEntity(
                    id = "notif_02",
                    userId = defaultUserId,
                    title = "Weekly Rebate Credited",
                    message = "+$80.00 demo tokens added to your balance.",
                    type = "PROMOTION"
                )
            )
            db.notificationDao().insertNotifications(notifications)

            // 10. Seed Support Ticket
            val ticket = SupportTicketEntity(
                id = "tkt_01",
                userId = defaultUserId,
                ticketNumber = "BC23-5892",
                subject = "VIP Concierge Inquiry",
                category = "VIP_CONCIERGE",
                status = "OPEN",
                priority = "VIP",
                lastMessage = "Welcome Mr. Lucky. Your dedicated VIP manager is online 24/7."
            )
            db.supportDao().insertTicket(ticket)
        }
    }
}
