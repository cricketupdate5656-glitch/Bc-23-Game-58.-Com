package com.example.bc23.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bc23.core.ui.theme.BC23BorderGold
import com.example.bc23.core.ui.theme.BC23CrimsonRed
import com.example.bc23.core.ui.theme.BC23DarkRed
import com.example.bc23.core.ui.theme.BC23GoldDark
import com.example.bc23.core.ui.theme.BC23GoldGradient
import com.example.bc23.core.ui.theme.BC23GoldPrimary
import com.example.bc23.core.ui.theme.BC23SurfaceDark
import com.example.bc23.core.ui.theme.BC23TextPrimary
import com.example.bc23.core.ui.theme.BC23TextSecondary

@Composable
fun BC23TopBar(
    uid: String,
    vipTier: String,
    unreadNotifications: Int = 0,
    onNotificationClick: () -> Unit = {},
    onVipClick: () -> Unit = {},
    onUidCopyClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(BC23SurfaceDark)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .testTag("bc23_top_bar")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left: Brand Logo & UID
            Row(verticalAlignment = Alignment.CenterVertically) {
                // BC23 Brand Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(BC23GoldGradient)
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "BC23",
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF1B1100),
                        fontSize = 15.sp,
                        letterSpacing = 0.5.sp
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                // UID Chip
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF222432))
                        .border(1.dp, Color(0xFF3B3E52), RoundedCornerShape(14.dp))
                        .clickable(onClick = onUidCopyClick)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("uid_chip"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "UID: $uid",
                        color = BC23TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy UID",
                        tint = BC23GoldPrimary,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            // Right: VIP Badge & Notification Icon
            Row(verticalAlignment = Alignment.CenterVertically) {
                // VIP Pill
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(BC23DarkRed)
                        .border(1.dp, BC23BorderGold, RoundedCornerShape(14.dp))
                        .clickable(onClick = onVipClick)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                        .testTag("vip_pill"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "VIP Level",
                        tint = BC23GoldPrimary,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = vipTier,
                        color = BC23GoldPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Notification Bell
                IconButton(
                    onClick = onNotificationClick,
                    modifier = Modifier
                        .size(38.dp)
                        .testTag("notification_bell_button")
                ) {
                    BadgedBox(
                        badge = {
                            if (unreadNotifications > 0) {
                                Badge(containerColor = BC23CrimsonRed) {
                                    Text(
                                        text = "$unreadNotifications",
                                        color = Color.White,
                                        fontSize = 9.sp
                                    )
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = BC23TextPrimary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}
