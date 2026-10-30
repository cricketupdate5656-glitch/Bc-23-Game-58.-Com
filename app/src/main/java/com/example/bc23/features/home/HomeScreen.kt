package com.example.bc23.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bc23.core.database.entity.GameEntity
import com.example.bc23.core.security.SecurityUtils
import com.example.bc23.core.ui.components.BC23BannerCarousel
import com.example.bc23.core.ui.components.BC23Card
import com.example.bc23.core.ui.components.BC23TopBar
import com.example.bc23.core.ui.components.DemoWalletNotice
import com.example.bc23.core.ui.theme.BC23BackgroundDark
import com.example.bc23.core.ui.theme.BC23BorderGold
import com.example.bc23.core.ui.theme.BC23BorderSubtle
import com.example.bc23.core.ui.theme.BC23CardDark
import com.example.bc23.core.ui.theme.BC23CardElevated
import com.example.bc23.core.ui.theme.BC23CrimsonRed
import com.example.bc23.core.ui.theme.BC23GoldDark
import com.example.bc23.core.ui.theme.BC23GoldGradient
import com.example.bc23.core.ui.theme.BC23GoldPrimary
import com.example.bc23.core.ui.theme.BC23RedGradient
import com.example.bc23.core.ui.theme.BC23SurfaceDark
import com.example.bc23.core.ui.theme.BC23TextMuted
import com.example.bc23.core.ui.theme.BC23TextPrimary
import com.example.bc23.core.ui.theme.BC23TextSecondary

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToWallet: () -> Unit,
    onNavigateToGames: () -> Unit,
    onNavigateToPromos: () -> Unit,
    onNavigateToReferral: () -> Unit,
    onNavigateToSupport: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToAdmin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()

    val uidDisplay = state.user?.uid ?: "58921473"
    val vipTierDisplay = "VIP ${state.user?.vipLevel ?: 3}"
    val demoBalance = 12580.00 // Default demo balance

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BC23BackgroundDark)
            .testTag("home_screen")
    ) {
        // Top Bar
        BC23TopBar(
            uid = uidDisplay,
            vipTier = vipTierDisplay,
            unreadNotifications = state.unreadNotificationsCount,
            onNotificationClick = onNavigateToNotifications,
            onVipClick = onNavigateToPromos
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Demo Wallet Card
            WalletDashboardCard(
                balance = demoBalance,
                onDepositClick = onNavigateToWallet,
                onWithdrawClick = onNavigateToWallet
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Quick Actions Bar
            QuickActionsBar(
                onDeposit = onNavigateToWallet,
                onWithdraw = onNavigateToWallet,
                onVip = onNavigateToPromos,
                onReferral = onNavigateToReferral,
                onSupport = onNavigateToSupport
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Promotional Banner Carousel
            if (state.promotions.isNotEmpty()) {
                BC23BannerCarousel(
                    promotions = state.promotions,
                    onPromotionClick = { onNavigateToPromos() }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Game Categories Horizontal Selector
            GameCategoriesRow(
                categories = listOf("ALL", "LIVE", "SLOTS", "SPORTS", "CARDS", "FISHING"),
                selected = state.selectedCategoryCode,
                onSelect = viewModel::onCategorySelect
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Hot Games Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(4.dp, 16.dp)
                            .background(BC23GoldPrimary, RoundedCornerShape(2.dp))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "HOT & POPULAR GAMES",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                Row(
                    modifier = Modifier.clickable(onClick = onNavigateToGames),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "View All",
                        color = BC23GoldPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "View all games",
                        tint = BC23GoldPrimary,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Hot Games Grid / Rows
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                state.hotGames.take(6).chunked(2).forEach { rowGames ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        rowGames.forEach { game ->
                            GameDashboardCard(
                                game = game,
                                modifier = Modifier.weight(1f),
                                onClick = onNavigateToGames
                            )
                        }
                        if (rowGames.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Demo Safeguard Banner
            DemoWalletNotice()

            Spacer(modifier = Modifier.height(12.dp))

            // Architecture / Admin Status Trigger
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF161720))
                    .border(1.dp, Color(0xFF2A2C3E), RoundedCornerShape(10.dp))
                    .clickable(onClick = onNavigateToAdmin)
                    .padding(12.dp)
                    .testTag("admin_audit_trigger"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "⚙️ Phase 01 System Architecture & QA Audit View",
                    color = BC23TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun WalletDashboardCard(
    balance: Double,
    onDepositClick: () -> Unit,
    onWithdrawClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFF24151B), Color(0xFF161118), Color(0xFF13141D))
                )
            )
            .border(1.dp, BC23BorderGold, RoundedCornerShape(16.dp))
            .padding(16.dp)
            .testTag("wallet_dashboard_card")
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccountBalanceWallet,
                        contentDescription = "Wallet Icon",
                        tint = BC23GoldPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "MAIN DEMO WALLET",
                        color = BC23TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF382900))
                        .border(1.dp, BC23BorderGold, RoundedCornerShape(10.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "SANDBOX",
                        color = BC23GoldPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = SecurityUtils.formatCurrency(balance),
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Deposit Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(BC23GoldGradient)
                        .clickable(onClick = onDepositClick)
                        .testTag("home_deposit_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "DEPOSIT",
                        color = Color(0xFF1A1300),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                // Withdraw Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF262838))
                        .border(1.dp, Color(0xFF43475E), RoundedCornerShape(10.dp))
                        .clickable(onClick = onWithdrawClick)
                        .testTag("home_withdraw_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "WITHDRAW",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickActionsBar(
    onDeposit: () -> Unit,
    onWithdraw: () -> Unit,
    onVip: () -> Unit,
    onReferral: () -> Unit,
    onSupport: () -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        Triple("Deposit", Icons.Default.Payments, onDeposit),
        Triple("Withdraw", Icons.Default.AccountBalanceWallet, onWithdraw),
        Triple("VIP Club", Icons.Default.Star, onVip),
        Triple("Refer & Earn", Icons.Default.Share, onReferral),
        Triple("24/7 Help", Icons.Default.HeadsetMic, onSupport)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(BC23CardDark)
            .border(0.5.dp, BC23BorderSubtle, RoundedCornerShape(12.dp))
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        items.forEach { (label, icon, action) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable(onClick = action)
                    .padding(horizontal = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF252636)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = BC23GoldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = label,
                    color = BC23TextSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun GameCategoriesRow(
    categories: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { cat ->
            val isSelected = cat == selected
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .then(
                        if (isSelected) Modifier.background(BC23GoldGradient)
                        else Modifier.background(Color(0xFF1E202B))
                    )
                    .border(
                        1.dp,
                        if (isSelected) BC23GoldPrimary else Color(0xFF333649),
                        RoundedCornerShape(10.dp)
                    )
                    .clickable { onSelect(cat) }
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    text = cat,
                    color = if (isSelected) Color(0xFF1A1300) else Color.White,
                    fontSize = 12.sp,
                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun GameDashboardCard(
    game: GameEntity,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(BC23CardElevated)
            .border(1.dp, BC23BorderSubtle, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(BC23CrimsonRed)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = if (game.isHot) "HOT" else "NEW",
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Text(
                    text = "★ ${game.rating}",
                    color = BC23GoldPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = game.title,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = game.providerName,
                color = BC23TextMuted,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF2E3142)),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Demo Play",
                        tint = BC23GoldPrimary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Demo Play",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
