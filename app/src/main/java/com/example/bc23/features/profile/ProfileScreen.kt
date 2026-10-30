package com.example.bc23.features.profile

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bc23.core.security.SecurityUtils
import com.example.bc23.core.ui.components.BC23Card
import com.example.bc23.core.ui.components.BC23SecondaryButton
import com.example.bc23.core.ui.components.DemoWalletNotice
import com.example.bc23.core.ui.theme.BC23BackgroundDark
import com.example.bc23.core.ui.theme.BC23BorderGold
import com.example.bc23.core.ui.theme.BC23BorderSubtle
import com.example.bc23.core.ui.theme.BC23CardDark
import com.example.bc23.core.ui.theme.BC23GoldDark
import com.example.bc23.core.ui.theme.BC23GoldGradient
import com.example.bc23.core.ui.theme.BC23GoldPrimary
import com.example.bc23.core.ui.theme.BC23TextMuted
import com.example.bc23.core.ui.theme.BC23TextPrimary
import com.example.bc23.core.ui.theme.BC23TextSecondary

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onNavigateToReferral: () -> Unit = {},
    onNavigateToSupport: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToAdmin: () -> Unit = {},
    onLogout: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    val username = state.user?.username ?: "arshad_lucky"
    val uid = state.user?.uid ?: "58921473"
    val vipTierName = state.profile?.vipTierName ?: "Gold VIP 3"
    val currentPoints = state.profile?.vipPoints ?: 4500L
    val nextPoints = state.profile?.nextTierPoints ?: 10000L
    val progress = (currentPoints.toFloat() / nextPoints.toFloat()).coerceIn(0f, 1f)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BC23BackgroundDark)
            .statusBarsPadding()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .testTag("profile_screen")
    ) {
        Text(
            text = "ACCOUNT & VIP PROFILE",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = BC23GoldPrimary,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Avatar Card
        BC23Card(borderColor = BC23BorderGold) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(BC23GoldGradient),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = username.take(2).uppercase(),
                            color = Color(0xFF1A1300),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Text(
                            text = username,
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "UID: $uid",
                            color = BC23GoldPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Email: ${state.user?.email ?: "arshadlucky2478@gmail.com"}",
                            color = BC23TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // VIP Tier Progress
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = vipTierName, color = BC23GoldPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text(text = "$currentPoints / $nextPoints pts", color = BC23TextSecondary, fontSize = 11.sp)
                }

                Spacer(modifier = Modifier.height(6.dp))

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = BC23GoldPrimary,
                    trackColor = Color(0xFF2E3144)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        DemoWalletNotice()

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "SERVICES & SETTINGS", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        BC23Card {
            Column {
                ProfileOptionRow(
                    title = "Referral & Affiliate Hub",
                    icon = Icons.Default.Share,
                    onClick = onNavigateToReferral
                )
                ProfileOptionRow(
                    title = "24/7 Customer Help Center",
                    icon = Icons.Default.HeadsetMic,
                    onClick = onNavigateToSupport
                )
                ProfileOptionRow(
                    title = "Notifications & Announcements",
                    icon = Icons.Default.Notifications,
                    onClick = onNavigateToNotifications
                )
                ProfileOptionRow(
                    title = "System Architecture & QA Audit",
                    icon = Icons.Default.Security,
                    onClick = onNavigateToAdmin
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        BC23SecondaryButton(
            text = "SWITCH / LOGOUT ACCOUNT",
            onClick = { viewModel.logout(onLogout) },
            testTag = "logout_button"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "BC23 Game 58.com • Version 1.0.0-phase1",
                color = BC23TextMuted,
                fontSize = 11.sp
            )
            Text(
                text = "Clean Architecture & Room DB Verified",
                color = BC23TextMuted,
                fontSize = 10.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun ProfileOptionRow(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = title, tint = BC23GoldPrimary, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = "Open", tint = BC23TextMuted, modifier = Modifier.size(16.dp))
    }
}
