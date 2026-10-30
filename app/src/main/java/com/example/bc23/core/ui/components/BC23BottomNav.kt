package com.example.bc23.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bc23.core.ui.theme.BC23BorderSubtle
import com.example.bc23.core.ui.theme.BC23GoldDark
import com.example.bc23.core.ui.theme.BC23GoldPrimary
import com.example.bc23.core.ui.theme.BC23SurfaceDark
import com.example.bc23.core.ui.theme.BC23TextMuted
import com.example.bc23.core.ui.theme.BC23TextPrimary

enum class NavItem(val label: String, val icon: ImageVector, val tag: String) {
    HOME("Home", Icons.Default.Home, "nav_home"),
    GAMES("Games", Icons.Default.Casino, "nav_games"),
    WALLET("Wallet", Icons.Default.AccountBalanceWallet, "nav_wallet"),
    PROMOTIONS("Promos", Icons.Default.CardGiftcard, "nav_promos"),
    PROFILE("Profile", Icons.Default.Person, "nav_profile")
}

@Composable
fun BC23BottomNav(
    selectedItem: NavItem,
    onItemSelected: (NavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(BC23SurfaceDark)
            .border(width = 0.5.dp, color = BC23BorderSubtle)
            .navigationBarsPadding()
            .testTag("bc23_bottom_nav")
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp
        ) {
            NavItem.entries.forEach { item ->
                val selected = item == selectedItem
                NavigationBarItem(
                    selected = selected,
                    onClick = { onItemSelected(item) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            fontSize = 11.sp,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = BC23GoldPrimary,
                        selectedTextColor = BC23GoldPrimary,
                        unselectedIconColor = BC23TextMuted,
                        unselectedTextColor = BC23TextMuted,
                        indicatorColor = Color(0xFF382900)
                    ),
                    modifier = Modifier.testTag(item.tag)
                )
            }
        }
    }
}
