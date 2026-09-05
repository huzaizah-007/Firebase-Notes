package com.example.firebasenotes.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val FirebaseNotesColorScheme = darkColorScheme(
    primary = PrimaryDark,
    secondary = SecondaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark,
    error = ErrorColor
)

@Composable
fun FirebaseNotesTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = FirebaseNotesColorScheme, typography = AppTypography, content = content
    )
}