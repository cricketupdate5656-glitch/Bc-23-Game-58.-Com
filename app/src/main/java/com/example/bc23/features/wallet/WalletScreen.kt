package com.example.bc23.features.wallet

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
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.CurrencyBitcoin
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import com.example.bc23.core.database.entity.TransactionEntity
import com.example.bc23.core.security.SecurityUtils
import com.example.bc23.core.ui.components.BC23Card
import com.example.bc23.core.ui.components.BC23PrimaryButton
import com.example.bc23.core.ui.components.BC23SecondaryButton
import com.example.bc23.core.ui.components.DemoWalletNotice
import com.example.bc23.core.ui.theme.BC23BackgroundDark
import com.example.bc23.core.ui.theme.BC23BorderGold
import com.example.bc23.core.ui.theme.BC23BorderSubtle
import com.example.bc23.core.ui.theme.BC23CardDark
import com.example.bc23.core.ui.theme.BC23CardElevated
import com.example.bc23.core.ui.theme.BC23CrimsonRed
import com.example.bc23.core.ui.theme.BC23GoldDark
import com.example.bc23.core.ui.theme.BC23GoldPrimary
import com.example.bc23.core.ui.theme.BC23SuccessGreen
import com.example.bc23.core.ui.theme.BC23TextMuted
import com.example.bc23.core.ui.theme.BC23TextPrimary
import com.example.bc23.core.ui.theme.BC23TextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WalletScreen(
    viewModel: WalletViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    val balance = state.wallet?.demoBalance ?: 12580.00
    val tabs = listOf("Deposit", "Withdraw", "History")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BC23BackgroundDark)
            .statusBarsPadding()
            .testTag("wallet_screen")
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "WALLET & BANKING",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = BC23GoldPrimary,
                letterSpacing = 0.5.sp
            )
        }

        // Balance Summary Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF1B1714))
                .border(1.dp, BC23BorderGold, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "TOTAL DEMO BALANCE",
                        color = BC23TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "USD • SANDBOX",
                        color = BC23GoldPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = SecurityUtils.formatCurrency(balance),
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            DemoWalletNotice()
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Tab Selection
        TabRow(
            selectedTabIndex = state.selectedTab,
            containerColor = Color.Transparent,
            contentColor = BC23GoldPrimary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[state.selectedTab]),
                    color = BC23GoldPrimary,
                    height = 3.dp
                )
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = state.selectedTab == index,
                    onClick = { viewModel.onTabSelected(index) },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (state.selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Tab Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            when (state.selectedTab) {
                0 -> DepositTabContent(state = state, viewModel = viewModel)
                1 -> WithdrawTabContent(state = state, viewModel = viewModel)
                2 -> HistoryTabContent(transactions = state.transactions)
            }
        }
    }
}

@Composable
private fun DepositTabContent(
    state: WalletUiState,
    viewModel: WalletViewModel
) {
    val quickAmounts = listOf("50", "100", "250", "500", "1000", "5000")
    val channels = listOf(
        Pair("Sandbox Instant Bank", Icons.Default.AccountBalance),
        Pair("Sandbox USDT (TRC-20)", Icons.Default.CurrencyBitcoin),
        Pair("Sandbox Virtual Card", Icons.Default.CreditCard)
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            BC23Card {
                Column {
                    Text(
                        text = "Select Demo Payment Method",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    channels.forEach { (name, icon) ->
                        val isSelected = state.paymentChannel == name
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) Color(0xFF2E2412) else Color(0xFF1E202B))
                                .border(1.dp, if (isSelected) BC23GoldPrimary else Color(0xFF33364A), RoundedCornerShape(8.dp))
                                .clickable { viewModel.onPaymentChannelChanged(name) }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(icon, contentDescription = name, tint = BC23GoldPrimary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = name, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Demo Deposit Amount ($)",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = state.amountInput,
                        onValueChange = viewModel::onAmountChanged,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BC23GoldPrimary,
                            unfocusedBorderColor = Color(0xFF3C3E50),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("deposit_amount_input")
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Quick Chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        quickAmounts.forEach { amt ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFF282A3A))
                                    .clickable { viewModel.onAmountChanged(amt) }
                                    .padding(vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "$$amt", color = BC23GoldPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    if (state.feedbackMessage != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = state.feedbackMessage,
                            color = if (state.isError) BC23CrimsonRed else BC23SuccessGreen,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (state.isLoading) {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = BC23GoldPrimary)
                        }
                    } else {
                        BC23PrimaryButton(
                            text = "SIMULATE DEPOSIT",
                            onClick = viewModel::executeDemoDeposit,
                            testTag = "simulate_deposit_btn"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WithdrawTabContent(
    state: WalletUiState,
    viewModel: WalletViewModel
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            BC23Card {
                Column {
                    Text(
                        text = "Demo Withdrawal Sandbox Payout",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = state.amountInput,
                        onValueChange = viewModel::onAmountChanged,
                        label = { Text("Withdrawal Amount ($)") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BC23GoldPrimary,
                            unfocusedBorderColor = Color(0xFF3C3E50),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("withdraw_amount_input")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = state.withdrawalDestination,
                        onValueChange = viewModel::onDestinationChanged,
                        label = { Text("Destination Account / Address") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BC23GoldPrimary,
                            unfocusedBorderColor = Color(0xFF3C3E50),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("withdraw_dest_input")
                    )

                    if (state.feedbackMessage != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = state.feedbackMessage,
                            color = if (state.isError) BC23CrimsonRed else BC23SuccessGreen,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (state.isLoading) {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = BC23GoldPrimary)
                        }
                    } else {
                        BC23SecondaryButton(
                            text = "SIMULATE WITHDRAWAL",
                            onClick = viewModel::executeDemoWithdrawal,
                            testTag = "simulate_withdraw_btn"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryTabContent(
    transactions: List<TransactionEntity>
) {
    if (transactions.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = "Empty History",
                    tint = BC23TextMuted,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "No transaction history recorded",
                    color = BC23TextSecondary,
                    fontSize = 13.sp
                )
            }
        }
    } else {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(transactions, key = { it.id }) { tx ->
                val isCredit = tx.type in listOf("DEMO_DEPOSIT", "VIP_BONUS", "DAILY_REBATE")
                val dateStr = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(Date(tx.timestamp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(BC23CardDark)
                        .border(1.dp, BC23BorderSubtle, RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isCredit) Color(0xFF143320) else Color(0xFF381418)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isCredit) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                                    contentDescription = tx.type,
                                    tint = if (isCredit) BC23SuccessGreen else BC23CrimsonRed,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = tx.type.replace("_", " "),
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = dateStr,
                                    color = BC23TextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = (if (isCredit) "+" else "-") + SecurityUtils.formatCurrency(tx.amount),
                                color = if (isCredit) BC23SuccessGreen else Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = tx.status,
                                color = BC23GoldPrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}
