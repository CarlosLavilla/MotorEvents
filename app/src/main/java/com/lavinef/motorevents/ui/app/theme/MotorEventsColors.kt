package com.lavinef.motorevents.ui.app.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private const val PRIMARY_DARK = 0xFFFF5252
private const val PRIMARY_CONTAINER_DARK = 0xFF3B0706
private const val ON_PRIMARY_CONTAINER_DARK = 0xFFFFDAD6
private const val SECONDARY_DARK = 0xFFE0E0E0
private const val BACKGROUND_DARK = 0xFF121212
private const val SURFACE_DARK = 0xFF121212

val DarkColorScheme = darkColorScheme(
    primary = Color(PRIMARY_DARK),
    onPrimary = Color.Black,
    primaryContainer = Color(PRIMARY_CONTAINER_DARK),
    onPrimaryContainer = Color(ON_PRIMARY_CONTAINER_DARK),
    secondary = Color(SECONDARY_DARK),
    background = Color(BACKGROUND_DARK),
    surface = Color(SURFACE_DARK)
)

private const val PRIMARY_LIGHT = 0xFFE10600
private const val PRIMARY_CONTAINER_LIGHT = 0xFFFFDAD6
private const val ON_PRIMARY_CONTAINER_LIGHT = 0xFF410001
private const val SECONDARY_LIGHT = 0xFF15151E
private const val BACKGROUND_LIGHT = 0xFFFFFBFF
private const val SURFACE_LIGHT = 0xFFFFFBFF

val LightColorScheme = lightColorScheme(
    primary = Color(PRIMARY_LIGHT),
    onPrimary = Color.White,
    primaryContainer = Color(PRIMARY_CONTAINER_LIGHT), // A soft light red container
    onPrimaryContainer = Color(ON_PRIMARY_CONTAINER_LIGHT),
    secondary = Color(SECONDARY_LIGHT),
    background = Color(BACKGROUND_LIGHT),
    surface = Color(SURFACE_LIGHT)
)
