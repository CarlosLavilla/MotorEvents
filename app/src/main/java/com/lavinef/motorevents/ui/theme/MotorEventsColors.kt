package com.lavinef.motorevents.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFF5252),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF3B0706), // A deep, muted dark red container derived from your primary
    onPrimaryContainer = Color(0xFFFFDAD6),
    secondary = Color(0xFFE0E0E0),
    background = Color(0xFF121212),
    surface = Color(0xFF121212)
)

val LightColorScheme = lightColorScheme(
    primary = Color(0xFFE10600),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFDAD6), // A soft light red container
    onPrimaryContainer = Color(0xFF410001),
    secondary = Color(0xFF15151E),
    background = Color(0xFFFFFBFF),
    surface = Color(0xFFFFFBFF)
)