package mx.cristobal.aichatcourseapp.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

/**
 * Converts a hex string representation of a color to a Compose [Color].
 * Supports formats: "#RRGGBB", "RRGGBB", "#AARRGGBB", "AARRGGBB", "#RGB", "RGB".
 *
 * @param fallback The color to return if parsing fails or string is null/invalid.
 */
fun String?.toColor(fallback: Color = Color.Unspecified): Color {
    if (this.isNullOrBlank()) return fallback
    val cleanHex = this.removePrefix("#").trim()
    return try {
        when (cleanHex.length) {
            3 -> {
                val r = cleanHex[0].toString().repeat(2)
                val g = cleanHex[1].toString().repeat(2)
                val b = cleanHex[2].toString().repeat(2)
                val colorInt = "$r$g$b".toLong(16) or 0xFF000000
                Color(colorInt)
            }
            6 -> {
                val colorInt = cleanHex.toLong(16) or 0xFF000000
                Color(colorInt)
            }
            8 -> {
                val colorInt = cleanHex.toLong(16)
                Color(colorInt)
            }
            else -> fallback
        }
    } catch (_: NumberFormatException) {
        fallback
    }
}

/**
 * Converts a Compose [Color] to a Hex string representation.
 *
 * @param includeAlpha Whether to include alpha channel in the output string (e.g. "#AARRGGBB" vs "#RRGGBB").
 */
fun Color.toHex(includeAlpha: Boolean = false): String {
    val argb = this.toArgb()
    return if (includeAlpha) {
        String.format("#%08X", argb)
    } else {
        String.format("#%06X", argb and 0xFFFFFF)
    }
}
