package com.company.fetchexercise.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val FetchColorSchemeLight = lightColorScheme(
    primary = OrangePrimary,
    onPrimary = OnPrimaryLight,
    secondary = PurpleSecondary,
    onSecondary = OnPrimaryLight,
    tertiary = GoldAccent,
    background = CreamBackground,
    onBackground = OnBackgroundLight,
    surface = LightSurface,
    onSurface = OnBackgroundLight
)

private val FetchColorSchemeDark = darkColorScheme(
    primary = OrangePrimary,
    onPrimary = OnPrimaryDark,
    secondary = PurpleSecondary,
    onSecondary = OnPrimaryDark,
    tertiary = GoldAccent,
    background = DarkBackground,
    onBackground = OnBackgroundDark,
    surface = DarkSurface,
    onSurface = OnBackgroundDark
)

@Composable
fun FetchExerciseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> FetchColorSchemeDark
        else -> FetchColorSchemeLight
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        shapes = Shapes(),// Optional: customize if needed
        content = content
    )
}