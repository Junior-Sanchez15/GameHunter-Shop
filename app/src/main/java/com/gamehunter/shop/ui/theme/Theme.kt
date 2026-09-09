package com.gamehunter.shop.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = RedPrimary,
    onPrimary = White,

    secondary = RedDark,
    onSecondary = White,

    tertiary = RedLight,
    onTertiary = Black,

    background = White,
    onBackground = Black,

    surface = White,
    onSurface = Black,

    surfaceVariant = GrayLight,
    onSurfaceVariant = Black,

    error = RedDark,
    onError = White
)

private val DarkColorScheme = darkColorScheme(
    primary = RedLight,
    onPrimary = Black,

    secondary = RedPrimary,
    onSecondary = White,

    tertiary = RedDark,
    onTertiary = White,

    background = Black,
    onBackground = White,

    surface = Color(0xFF1E1E1E),
    onSurface = White,

    surfaceVariant = Color(0xFF2C2C2C),
    onSurfaceVariant = White,

    error = RedLight,
    onError = Black
)

@Composable
fun GameHunterShopTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}