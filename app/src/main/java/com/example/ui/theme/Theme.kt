package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PickWiseDarkColorScheme = darkColorScheme(
    primary = PickWiseBlueLight,
    onPrimary = Color.White,
    primaryContainer = PickWiseNavy,
    onPrimaryContainer = PickWiseBlueContainer,
    secondary = PickWiseTeal,
    onSecondary = Color.White,
    background = PickWiseNavyDark,
    surface = PickWiseNavy,
    onBackground = Color.White,
    onSurface = Color.White,
    surfaceVariant = Color(0xFF1E293B),
    onSurfaceVariant = PickWiseTextMuted,
    outline = Color(0xFF334155)
)

private val PickWiseLightColorScheme = lightColorScheme(
    primary = PickWiseBlue,
    onPrimary = Color.White,
    primaryContainer = PickWiseBlueContainer,
    onPrimaryContainer = PickWiseBlueDark,
    secondary = PickWiseTeal,
    onSecondary = Color.White,
    secondaryContainer = PickWiseTealLight,
    onSecondaryContainer = PickWiseTeal,
    background = PickWiseBackground,
    surface = PickWiseSurface,
    onBackground = PickWiseTextPrimary,
    onSurface = PickWiseTextPrimary,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = PickWiseTextSecondary,
    outline = PickWiseCardBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Preserve exact visual design reference
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) PickWiseDarkColorScheme else PickWiseLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

