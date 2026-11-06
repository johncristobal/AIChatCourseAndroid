package mx.cristobal.aichatcourseapp.services.user.models

import androidx.compose.ui.graphics.Color
import mx.cristobal.aichatcourseapp.utils.toColor
import java.util.Date

data class UserModel(
    val userId: String,
    val dateCreated: Date?,
    val didCompleteOnboarding: Boolean?,
    val profileColorHex: String?,
) {
    fun profileColorCalculated(default: Color) : Color {
        if (profileColorHex.isNullOrBlank()) return default
        return profileColorHex.toColor()
    }

    companion object {

        val mocks = listOf(
            UserModel(
                userId = "user_1",
                dateCreated = Date(),
                didCompleteOnboarding = true,
                profileColorHex = "#FF0000"
            ),
            UserModel(
                userId = "user_2",
                dateCreated = Date(),
                didCompleteOnboarding = false,
                profileColorHex = "#00FF00"
            ),
            UserModel(
                userId = "user_3",
                dateCreated = Date(),
                didCompleteOnboarding = true,
                profileColorHex = "#0000FF"
            )
        )

        val mock = mocks.first()
    }
}
