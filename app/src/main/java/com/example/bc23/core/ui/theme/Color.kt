package com.example.bc23.core.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Primary Gold & Yellow Highlighting
val BC23GoldPrimary = Color(0xFFFFD700)
val BC23GoldLight = Color(0xFFFFE57F)
val BC23GoldDark = Color(0xFFC79100)
val BC23GoldContainer = Color(0xFF382900)
val BC23OnGoldContainer = Color(0xFFFFE082)

// Premium Deep Red Accent & Gaming Secondary
val BC23CrimsonRed = Color(0xFFE51D38)
val BC23DarkRed = Color(0xFF8B0000)
val BC23RubyRed = Color(0xFFBA1A31)
val BC23RedContainer = Color(0xFF4A000A)
val BC23OnRedContainer = Color(0xFFFFDADA)

// Background & Surface Dark Obsidian Palette
val BC23BackgroundDark = Color(0xFF0A0B0E)
val BC23SurfaceDark = Color(0xFF12131A)
val BC23CardDark = Color(0xFF1A1B24)
val BC23CardElevated = Color(0xFF232532)

// Text & Content Hierarchy
val BC23TextPrimary = Color(0xFFFFFFFF)
val BC23TextSecondary = Color(0xFFB0B3C6)
val BC23TextMuted = Color(0xFF75788D)

// Status & Indicators
val BC23SuccessGreen = Color(0xFF00E676)
val BC23WarningYellow = Color(0xFFFFB300)
val BC23ErrorRed = Color(0xFFFF5252)
val BC23BorderGold = Color(0xFF5A4515)
val BC23BorderSubtle = Color(0xFF2C2D3B)

// Luxury Gradients
val BC23GoldGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFFFDF7A), Color(0xFFFFB800), Color(0xFFD49A00))
)

val BC23RedGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFE51D38), Color(0xFFA50E24), Color(0xFF6B0012))
)

val BC23CardGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFF242634), Color(0xFF161720))
)

val BC23VipGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF6B0012), Color(0xFF2A0007), Color(0xFF120003))
)
