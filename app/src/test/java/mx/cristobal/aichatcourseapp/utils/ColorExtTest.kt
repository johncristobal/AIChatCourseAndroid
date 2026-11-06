package mx.cristobal.aichatcourseapp.utils

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Test

class ColorExtTest {

    @Test
    fun testSixDigitHexToColor() {
        val hex = "#FF0000"
        val color = hex.toColor()
        assertEquals(Color.Red, color)
    }

    @Test
    fun testSixDigitHexWithoutHashToColor() {
        val hex = "00FF00"
        val color = hex.toColor()
        assertEquals(Color.Green, color)
    }

    @Test
    fun testEightDigitHexToColor() {
        val hex = "#FF0000FF"
        val color = hex.toColor()
        assertEquals(Color.Blue, color)
    }

    @Test
    fun testThreeDigitHexToColor() {
        val hex = "#F00"
        val color = hex.toColor()
        assertEquals(Color.Red, color)
    }

    @Test
    fun testInvalidHexReturnsFallback() {
        val invalidHex = "G12345"
        val color = invalidHex.toColor(fallback = Color.Gray)
        assertEquals(Color.Gray, color)
    }

    @Test
    fun testNullStringReturnsFallback() {
        val nullString: String? = null
        val color = nullString.toColor(fallback = Color.Yellow)
        assertEquals(Color.Yellow, color)
    }

    @Test
    fun testColorToHex() {
        assertEquals("#FF0000", Color.Red.toHex(includeAlpha = false))
        assertEquals("#FFFF0000", Color.Red.toHex(includeAlpha = true))
    }
}
