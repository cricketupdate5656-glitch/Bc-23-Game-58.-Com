package com.example.bc23.features.support

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.bc23.core.ui.components.BC23PrimaryButton
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
fun SupportScreen(
    viewModel: SupportViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    val categories = listOf("VIP_CONCIERGE", "ACCOUNT", "GAME_QUERY", "TECHNICAL")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BC23BackgroundDark)
            .statusBarsPadding()
            .padding(16.dp)
            .testTag("support_screen")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Text(
                text = "24/7 CUSTOMER SUPPORT",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = BC23GoldPrimary
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Live Channels Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BC23Card(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Chat, contentDescription = "Live Chat", tint = BC23GoldPrimary, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = "Live Chat", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Online 24/7", color = BC23SuccessGreen, fontSize = 10.sp)
                    }
                }
            }

            BC23Card(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Phone, contentDescription = "Hotline", tint = BC23GoldPrimary, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = "VIP Hotline", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(text = "+1 (800) BC23-58", color = BC23TextSecondary, fontSize = 10.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Create Ticket Form
        BC23Card(borderColor = BC23BorderGold) {
            Column {
                Text(text = "Create Support Ticket", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    categories.forEach { cat ->
                        val isSelected = state.selectedCategory == cat
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .then(
                                    if (isSelected) Modifier.background(BC23GoldGradient)
                                    else Modifier.background(Color(0xFF222432))
                                )
                                .clickable { viewModel.onCategoryChanged(cat) }
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = cat.replace("_", " "),
                                color = if (isSelected) Color(0xFF1A1300) else Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = state.subjectInput,
                    onValueChange = viewModel::onSubjectChanged,
                    label = { Text("Subject") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BC23GoldPrimary,
                        unfocusedBorderColor = Color(0xFF33364A),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = state.messageInput,
                    onValueChange = viewModel::onMessageChanged,
                    label = { Text("Describe your query or issue") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BC23GoldPrimary,
                        unfocusedBorderColor = Color(0xFF33364A),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )

                if (state.feedbackMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = state.feedbackMessage ?: "", color = BC23SuccessGreen, fontSize = 11.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                BC23PrimaryButton(
                    text = "SUBMIT TICKET",
                    onClick = viewModel::submitTicket,
                    testTag = "submit_ticket_btn"
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(text = "TICKET HISTORY", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.tickets, key = { it.id }) { tkt ->
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
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = tkt.ticketNumber, color = BC23GoldPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "• ${tkt.category}", color = BC23TextMuted, fontSize = 10.sp)
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = tkt.subject, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text(text = tkt.lastMessage, color = BC23TextSecondary, fontSize = 11.sp, maxLines = 1)
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF14301B))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(text = tkt.status, color = BC23SuccessGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
