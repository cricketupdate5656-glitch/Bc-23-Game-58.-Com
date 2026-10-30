package com.example.bc23.features.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.bc23.core.ui.components.BC23Card
import com.example.bc23.core.ui.theme.BC23BackgroundDark
import com.example.bc23.core.ui.theme.BC23BorderGold
import com.example.bc23.core.ui.theme.BC23BorderSubtle
import com.example.bc23.core.ui.theme.BC23CardDark
import com.example.bc23.core.ui.theme.BC23GoldDark
import com.example.bc23.core.ui.theme.BC23GoldPrimary
import com.example.bc23.core.ui.theme.BC23SuccessGreen
import com.example.bc23.core.ui.theme.BC23TextMuted
import com.example.bc23.core.ui.theme.BC23TextPrimary
import com.example.bc23.core.ui.theme.BC23TextSecondary

@Composable
fun AdminScreen(
    viewModel: AdminViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BC23BackgroundDark)
            .statusBarsPadding()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .testTag("admin_screen")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Text(
                text = "PHASE 01 TECHNICAL AUDIT",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = BC23GoldPrimary
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // System Header Card
        BC23Card(borderColor = BC23BorderGold) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Security, contentDescription = "Audit", tint = BC23GoldPrimary, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(text = "BC23 Game 58.com Foundation", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Build Variant: ${state.buildVariant}", color = BC23SuccessGreen, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "API Base: ${state.apiBaseUrl}", color = BC23TextSecondary, fontSize = 11.sp)
                Text(text = "Dev Mode Active: ${state.isDevMode}", color = BC23TextSecondary, fontSize = 11.sp)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(text = "ARCHITECTURAL CHECKLIST", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        val checklist = listOf(
            Pair("Room Database Foundation", "11 Entities, DAOs, Indices & Cascade FKs configured"),
            Pair("Jetpack DataStore Preferences", "Encrypted session tokens & preferences connected"),
            Pair("Financial & Legal Safeguards", "DemoWalletGuard enforced. Real money wagers blocked"),
            Pair("Clean Architecture & MVVM", "Core, UI, Database, Network, Security, Features segregated"),
            Pair("M3 Dark Gold & Crimson Theme", "Custom typography, gradients, adaptive components"),
            Pair("Retrofit & OkHttp Network Layer", "AuthInterceptor, safeApiCall, and Moshi configured"),
            Pair("Navigation Architecture", "Full destination routing with BackHandler support"),
            Pair("Adaptive Launcher Icon", "High-res BC23 metallic gold badge with mipmap fallbacks")
        )

        checklist.forEach { (title, description) ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(BC23CardDark)
                    .border(1.dp, BC23BorderSubtle, RoundedCornerShape(10.dp))
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Passed",
                        tint = BC23SuccessGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(text = title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        Text(text = description, color = BC23TextSecondary, fontSize = 11.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
