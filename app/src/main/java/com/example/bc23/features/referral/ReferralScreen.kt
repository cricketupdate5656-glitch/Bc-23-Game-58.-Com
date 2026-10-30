package com.example.bc23.features.referral

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
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
import com.example.bc23.core.security.SecurityUtils
import com.example.bc23.core.ui.components.BC23Card
import com.example.bc23.core.ui.components.BC23PrimaryButton
import com.example.bc23.core.ui.components.DemoWalletNotice
import com.example.bc23.core.ui.theme.BC23BackgroundDark
import com.example.bc23.core.ui.theme.BC23BorderGold
import com.example.bc23.core.ui.theme.BC23BorderSubtle
import com.example.bc23.core.ui.theme.BC23CardDark
import com.example.bc23.core.ui.theme.BC23GoldDark
import com.example.bc23.core.ui.theme.BC23GoldGradient
import com.example.bc23.core.ui.theme.BC23GoldPrimary
import com.example.bc23.core.ui.theme.BC23SuccessGreen
import com.example.bc23.core.ui.theme.BC23TextMuted
import com.example.bc23.core.ui.theme.BC23TextPrimary
import com.example.bc23.core.ui.theme.BC23TextSecondary

@Composable
fun ReferralScreen(
    viewModel: ReferralViewModel,
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BC23BackgroundDark)
            .statusBarsPadding()
            .padding(16.dp)
            .testTag("referral_screen")
    ) {
        // Header
        Text(
            text = "REFERRAL & AFFILIATE HUB",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = BC23GoldPrimary,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Invite Box
        BC23Card(borderColor = BC23BorderGold) {
            Column {
                Text(
                    text = "Share Your Exclusive BC23 Invite Code",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Earn up to ${state.commissionRate} multi-tier commission on friends' gameplay turnover.",
                    color = BC23TextSecondary,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF202330))
                        .border(1.dp, Color(0xFF383B50), RoundedCornerShape(10.dp))
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = state.referralCode,
                        color = BC23GoldPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )

                    Row(
                        modifier = Modifier.clickable { /* copy code */ },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "COPY",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy Referral Code",
                            tint = BC23GoldPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BC23Card(modifier = Modifier.weight(1f)) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.People, contentDescription = "Invites", tint = BC23GoldPrimary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Total Invites", color = BC23TextSecondary, fontSize = 11.sp)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "${state.inviteCount}", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }

            BC23Card(modifier = Modifier.weight(1f)) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Savings, contentDescription = "Commissions", tint = BC23SuccessGreen, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Demo Commissions", color = BC23TextSecondary, fontSize = 11.sp)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = SecurityUtils.formatCurrency(state.totalCommissionEarned), color = BC23SuccessGreen, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        DemoWalletNotice()

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "REFERRED MEMBERS",
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.referrals, key = { it.id }) { ref ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(BC23CardDark)
                        .border(1.dp, BC23BorderSubtle, RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = ref.referredUsername, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text(text = "UID: ${SecurityUtils.maskUid(ref.referredUid)}", color = BC23TextMuted, fontSize = 11.sp)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "+" + SecurityUtils.formatCurrency(ref.commissionEarnedDemo), color = BC23SuccessGreen, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text(text = ref.status, color = BC23GoldPrimary, fontSize = 10.sp)
                        }
                    }
                }
            }
        }
    }
}
