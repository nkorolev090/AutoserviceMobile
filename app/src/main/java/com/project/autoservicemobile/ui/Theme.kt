package com.project.autoservicemobile.ui

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun AppTheme(
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

private val DarkColorScheme = darkColorScheme(
    primary = blue_300,
    inversePrimary = white,
    onPrimary = gray_800,

    secondary = gray_700,
    tertiary = gray_200,
    tertiaryContainer = gray_400,
    onSecondary = gray_300,
    onSecondaryContainer = gray_500,

    error = red_300,
    onError = light_gray_300,

    surface = green_300
)

private val LightColorScheme = lightColorScheme(
    primary = blue_500,
    inversePrimary = white,
    onPrimary = white,

    secondary = gray_200,
    tertiary = black,
    tertiaryContainer = gray_500,
    onSecondary = gray_400,
    onSecondaryContainer = gray_200,

    error = red_400,
    onError = light_gray_400,

    surface = green_300
)