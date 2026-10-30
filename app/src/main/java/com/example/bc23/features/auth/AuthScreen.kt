package com.example.bc23.features.auth

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Redeem
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bc23.core.ui.components.BC23Card
import com.example.bc23.core.ui.components.BC23PrimaryButton
import com.example.bc23.core.ui.components.DemoWalletNotice
import com.example.bc23.core.ui.theme.BC23BackgroundDark
import com.example.bc23.core.ui.theme.BC23BorderGold
import com.example.bc23.core.ui.theme.BC23CardDark
import com.example.bc23.core.ui.theme.BC23CrimsonRed
import com.example.bc23.core.ui.theme.BC23GoldDark
import com.example.bc23.core.ui.theme.BC23GoldPrimary
import com.example.bc23.core.ui.theme.BC23SuccessGreen
import com.example.bc23.core.ui.theme.BC23TextPrimary
import com.example.bc23.core.ui.theme.BC23TextSecondary

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    onAuthSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated) {
            onAuthSuccess()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BC23BackgroundDark)
            .statusBarsPadding()
            .padding(20.dp)
            .testTag("auth_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Brand Header
            Text(
                text = "BC23 GAME 58.COM",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = BC23GoldPrimary,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Premier Gaming Entertainment Portal",
                fontSize = 13.sp,
                color = BC23TextSecondary
            )

            Spacer(modifier = Modifier.height(20.dp))

            DemoWalletNotice()

            Spacer(modifier = Modifier.height(20.dp))

            BC23Card(
                borderColor = BC23BorderGold,
                backgroundColor = BC23CardDark
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = if (state.isLoginMode) "Member Login" else "Create Player Account",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = BC23TextPrimary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Username Input
                    OutlinedTextField(
                        value = state.usernameInput,
                        onValueChange = viewModel::onUsernameChange,
                        label = { Text("Username") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = "Username Icon", tint = BC23GoldPrimary)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BC23GoldPrimary,
                            unfocusedBorderColor = Color(0xFF3C3E50),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_username_input")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Password Input
                    OutlinedTextField(
                        value = state.passwordInput,
                        onValueChange = viewModel::onPasswordChange,
                        label = { Text("Password") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = "Password Icon", tint = BC23GoldPrimary)
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BC23GoldPrimary,
                            unfocusedBorderColor = Color(0xFF3C3E50),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("auth_password_input")
                    )

                    if (!state.isLoginMode) {
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = state.referralInput,
                            onValueChange = viewModel::onReferralChange,
                            label = { Text("Referral Code (Optional)") },
                            leadingIcon = {
                                Icon(Icons.Default.Redeem, contentDescription = "Referral Icon", tint = BC23GoldPrimary)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = BC23GoldPrimary,
                                unfocusedBorderColor = Color(0xFF3C3E50),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("auth_referral_input")
                        )
                    }

                    if (state.errorMessage != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = state.errorMessage ?: "",
                            color = BC23CrimsonRed,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    if (state.successMessage != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = state.successMessage ?: "",
                            color = BC23SuccessGreen,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    if (state.isLoading) {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = BC23GoldPrimary)
                        }
                    } else {
                        BC23PrimaryButton(
                            text = if (state.isLoginMode) "Log In" else "Register Account",
                            onClick = viewModel::submit,
                            testTag = "auth_submit_button"
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = viewModel::toggleAuthMode),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (state.isLoginMode) "Don't have an account? " else "Already have an account? ",
                            color = BC23TextSecondary,
                            fontSize = 13.sp
                        )
                        Text(
                            text = if (state.isLoginMode) "Register" else "Login",
                            color = BC23GoldPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}
