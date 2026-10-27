package mx.cristobal.aichatcourseapp.core.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable
import mx.cristobal.aichatcourseapp.core.onboarding.OnboardingRoot
import mx.cristobal.aichatcourseapp.core.tabbar.MainView
import mx.cristobal.aichatcourseapp.core.welcome.WelcomeScreen


@Serializable
sealed interface AppRoute: NavKey {

    @Serializable
    data object Welcome : AppRoute, NavKey

    @Serializable
    data object Onboarding : AppRoute, NavKey

    @Serializable
    data object Home : AppRoute, NavKey

}

@Composable
fun AppRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(AppRoute.Welcome)

    NavDisplay(
        modifier = Modifier
            .fillMaxSize(),
        backStack = backStack,
        entryProvider = entryProvider {
            entry<AppRoute.Welcome> {
                WelcomeScreen(
                    onContinue = { backStack.add(AppRoute.Onboarding) }
                )
            }
            entry<AppRoute.Onboarding> {
                OnboardingRoot()
            }
            entry<AppRoute.Home> {
                MainView()
            }
        }
    )
}