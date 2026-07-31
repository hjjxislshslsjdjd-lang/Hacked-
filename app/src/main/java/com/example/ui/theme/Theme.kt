package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CyberColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = CyberDarkBackground,
    primaryContainer = TerminalHeaderBg,
    onPrimaryContainer = NeonCyan,
    secondary = NeonGreen,
    onSecondary = CyberDarkBackground,
    secondaryContainer = TerminalCardBg,
    onSecondaryContainer = NeonGreen,
    tertiary = NeonPink,
    onTertiary = CyberDarkBackground,
    background = CyberDarkBackground,
    onBackground = TextPrimary,
    surface = TerminalCardBg,
    onSurface = TextPrimary,
    surfaceVariant = TerminalHeaderBg,
    onSurfaceVariant = TextSecondary,
    outline = CyberBorder,
    error = CyberRedAlert,
    onError = TextPrimary
)

@Composable
fun CyberHackAcademyTheme(
    darkTheme: Boolean = true, // Default to cyber dark theme always
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CyberColorScheme,
        typography = Typography,
        content = content
    )
}
