package com.example.bc23.features.promotions

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bc23.core.database.entity.PromotionEntity
import com.example.bc23.core.ui.components.BC23Card
import com.example.bc23.core.ui.components.BC23PrimaryButton
import com.example.bc23.core.ui.components.DemoWalletNotice
import com.example.bc23.core.ui.theme.BC23BackgroundDark
import com.example.bc23.core.ui.theme.BC23BorderGold
import com.example.bc23.core.ui.theme.BC23CardDark
import com.example.bc23.core.ui.theme.BC23GoldDark
import com.example.bc23.core.ui.theme.BC23GoldGradient
import com.example.bc23.core.ui.theme.BC23GoldPrimary
import com.example.bc23.core.ui.theme.BC23SuccessGreen
import com.example.bc23.core.ui.theme.BC23TextPrimary
import com.example.bc23.core.ui.theme.BC23TextSecondary

@Composable
fun PromotionsScreen(
    viewModel: PromotionsViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    val categories = listOf("ALL", "WELCOME", "VIP", "DAILY_REBATE")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BC23BackgroundDark)
            .statusBarsPadding()
            .testTag("promotions_screen")
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "PROMOTIONS & VIP BONUSES",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = BC23GoldPrimary,
                letterSpacing = 0.5.sp
            )
        }

        // Categories Horizontal
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { cat ->
                val selected = state.selectedCategory == cat
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .then(
                            if (selected) Modifier.background(BC23GoldGradient)
                            else Modifier.background(Color(0xFF1E202B))
                        )
                        .clickable { viewModel.onCategorySelect(cat) }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = cat.replace("_", " "),
                        color = if (selected) Color(0xFF1A1300) else Color.White,
                        fontSize = 11.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            DemoWalletNotice()
        }

        if (state.claimSuccessMessage != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF122C1A))
                    .border(1.dp, BC23SuccessGreen, RoundedCornerShape(8.dp))
                    .padding(10.dp)
            ) {
                Text(
                    text = state.claimSuccessMessage ?: "",
                    color = BC23SuccessGreen,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Promotions List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(state.promotions, key = { it.id }) { promo ->
                val isClaimed = state.claimedPromotionIds.contains(promo.id)
                PromotionCard(
                    promo = promo,
                    isClaimed = isClaimed,
                    onClaim = { viewModel.claimPromotion(promo) }
                )
            }
        }
    }
}

@Composable
private fun PromotionCard(
    promo: PromotionEntity,
    isClaimed: Boolean,
    onClaim: () -> Unit
) {
    BC23Card(
        borderColor = if (isClaimed) Color(0xFF2C2D3B) else BC23BorderGold
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(BC23GoldGradient)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "${promo.bonusPercentage}% BONUS",
                        color = Color(0xFF1A1300),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Text(
                    text = "Up to $${promo.maxBonusAmount.toInt()} Demo",
                    color = BC23GoldPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = promo.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = promo.subtitle,
                color = BC23TextSecondary,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Terms: ${promo.terms}",
                color = Color(0xFF75788D),
                fontSize = 10.sp,
                lineHeight = 14.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            BC23PrimaryButton(
                text = if (isClaimed) "CLAIMED (DEMO)" else "CLAIM BONUS",
                onClick = onClaim,
                enabled = !isClaimed,
                testTag = "claim_promo_${promo.id}"
            )
        }
    }
}
