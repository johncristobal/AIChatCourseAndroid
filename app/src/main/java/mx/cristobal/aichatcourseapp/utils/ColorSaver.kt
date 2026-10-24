package mx.cristobal.aichatcourseapp.utils

import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.graphics.Color

val ColorSaver: Saver<Color, Long> = Saver(
    save = { it.value.toLong() },
) { Color(it.toULong()) }
