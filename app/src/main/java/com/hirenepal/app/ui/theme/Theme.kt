package com.hirenepal.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hirenepal.app.R

val HireGreen = Color(0xFF006C4D)
val HireGreenDark = Color(0xFF004C39)
val HireMint = Color(0xFFD8F0E5)
val HireCream = Color(0xFFF6F2E9)
val HireOrange = Color(0xFFEE8B39)
val HireInk = Color(0xFF14231D)
val HireMuted = Color(0xFF697770)
val HireSurface = Color(0xFFF8FAF8)
val HireCardBorder = Color(0xFFE2E7E4)

val DmSansFontFamily = FontFamily(
    Font(R.font.dm_sans, FontWeight.Normal),
    Font(R.font.dm_sans, FontWeight.Medium),
    Font(R.font.dm_sans, FontWeight.Bold)
)

val ManropeFontFamily = FontFamily(
    Font(R.font.manrope, FontWeight.SemiBold),
    Font(R.font.manrope, FontWeight.Bold),
    Font(R.font.manrope, FontWeight.ExtraBold)
)

private val LightColorScheme = lightColorScheme(
    primary = HireGreen,
    onPrimary = Color.White,
    primaryContainer = HireMint,
    onPrimaryContainer = HireGreenDark,
    secondary = HireOrange,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFF0E2),
    onSecondaryContainer = Color(0xFF6E3500),
    background = HireSurface,
    onBackground = HireInk,
    surface = Color.White,
    onSurface = HireInk,
    surfaceVariant = Color(0xFFEEF3F0),
    onSurfaceVariant = HireMuted,
    outline = HireCardBorder
)

val HireTypography = Typography(
    displaySmall = TextStyle(
        fontFamily = ManropeFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        lineHeight = 38.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = ManropeFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 30.sp
    ),
    titleLarge = TextStyle(
        fontFamily = ManropeFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 26.sp
    ),
    titleMedium = TextStyle(
        fontFamily = DmSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = DmSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = DmSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelLarge = TextStyle(
        fontFamily = DmSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    labelSmall = TextStyle(
        fontFamily = DmSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.8.sp
    )
)

@Composable
fun HireNepalTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = HireTypography,
        content = content
    )
}
