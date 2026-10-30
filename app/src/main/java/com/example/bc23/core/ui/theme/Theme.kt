package com.example.bc23.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val BC23DarkColorScheme = darkColorScheme(
    primary = BC23GoldPrimary,
    onPrimary = Color(0xFF1A1300),
    primaryContainer = BC23GoldContainer,
    onPrimaryContainer = BC23OnGoldContainer,
    secondary = BC23CrimsonRed,
    onSecondary = Color.White,
    secondaryContainer = BC23RedContainer,
    onSecondaryContainer = BC23OnRedContainer,
    tertiary = BC23GoldLight,
    onTertiary = Color.Black,
    background = BC23BackgroundDark,
    onBackground = BC23TextPrimary,
    surface = BC23SurfaceDark,
    onSurface = BC23TextPrimary,
    surfaceVariant = BC23CardDark,
    onSurfaceVariant = BC23TextSecondary,
    outline = BC23BorderGold,
    outlineVariant = BC23BorderSubtle,
    error = BC23ErrorRed,
    onError = Color.White
)

@Composable
fun BC23Theme(
    darkTheme: Boolean = true, // Default to premium dark gaming theme
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = BC23DarkColorScheme,
        typography = Typography,
        content = content
    )
}
