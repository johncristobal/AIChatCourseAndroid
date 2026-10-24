package mx.cristobal.aichatcourseapp.core.onboarding

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable
import mx.cristobal.aichatcourseapp.utils.ColorSerializer

@Serializable
sealed interface OnboardingRoute: NavKey {

    @Serializable
    data object OnboardingIntro : OnboardingRoute, NavKey

    @Serializable
    data object OnboardingColor : OnboardingRoute, NavKey

    @Serializable
    data class OnboardingCompleted(
        @Serializable(with = ColorSerializer::class) val color: Color
    ): OnboardingRoute, NavKey

}

@Composable
fun OnboardingRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(OnboardingRoute.OnboardingIntro)

    NavDisplay(
        modifier = Modifier
            .fillMaxSize(),
        backStack = backStack,
        entryProvider = entryProvider {
            entry<OnboardingRoute.OnboardingIntro> {
                OnboardingIntroScreen(
                    onContinue = { backStack.add(OnboardingRoute.OnboardingColor) }
                )
            }
            entry<OnboardingRoute.OnboardingColor> {
                OnboardingColorScreen(
                    onContinue = {
                        backStack.add(OnboardingRoute.OnboardingCompleted(it))
                    }
                )
            }
            entry<OnboardingRoute.OnboardingCompleted> { key ->
                OnboardingCompletedScreen(
                    selectedColor = key.color
                )
            }
        }
    )
}