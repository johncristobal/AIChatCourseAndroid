package mx.cristobal.aichatcourseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import kotlinx.serialization.Serializable
import mx.cristobal.aichatcourseapp.core.onboarding.OnboardingIntroScreen
import mx.cristobal.aichatcourseapp.ui.theme.AIChatCourseAppTheme
import mx.cristobal.aichatcourseapp.utils.Constants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIChatCourseAppTheme {
                OnboardingIntroScreen()
            }
        }
    }
}

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object Home : Route
    @Serializable
    data object Chat : Route
    @Serializable
    data class ChatDetail(val chatId: String) : Route
    @Serializable
    data object Profile : Route
}

data class BottomNavItem(
    val title: String,
    val icon: ImageVector
)

val TOP_LEVEL = mapOf(
    Route.Home to BottomNavItem(
        title = "Home",
        icon = Icons.Default.Home
    ),
    Route.Chat to BottomNavItem(
        title = "Chat",
        icon = Icons.AutoMirrored.Filled.Chat
    ),
    Route.Profile to BottomNavItem(
        title = "Profile",
        icon = Icons.Default.Person
    )
)

@Composable
fun MainView() {
    val backStack = rememberNavBackStack(Route.Home as Route)
    val currentRoute = backStack.lastOrNull() ?: Route.Home

    // Determine selected bottom bar item (if we are on ChatDetail, Chat tab should be selected)
    val selectedTab = when (currentRoute) {
        is Route.Chat, is Route.ChatDetail -> Route.Chat
        else -> currentRoute
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(
                selectedKey = selectedTab,
                onSelectedKey = { destination ->
                    if (currentRoute != destination) {
                        backStack.clear()
                        backStack.add(destination as Route)
                    }
                }
            )
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            modifier = Modifier.padding(innerPadding),
            entryProvider = entryProvider {
                entry<Route.Home> {
                    HomeScreen()
                }
                entry<Route.Chat> {
                    ChatListScreen(
                        onChatClick = { chatId ->
                            backStack.add(Route.ChatDetail(chatId))
                        }
                    )
                }
                entry<Route.ChatDetail> { key ->
                    ChatDetailScreen(chatId = key.chatId)
                }
                entry<Route.Profile> {
                    ProfileScreen()
                }
            }
        )
    }
}

@Composable
fun BottomBar(
    selectedKey: NavKey,
    onSelectedKey: (NavKey) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier
    ) {
        TOP_LEVEL.forEach { (destination, data) ->
            NavigationBarItem(
                selected = destination == selectedKey,
                onClick = {
                    onSelectedKey(destination)
                },
                icon = {
                    Icon(
                        imageVector = data.icon,
                        contentDescription = data.title
                    )
                },
                label = {
                    Text(text = data.title)
                }
            )
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to Home Screen")
    }
}

@Composable
fun ChatListScreen(onChatClick: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Chat List Screen")
        Button(
            onClick = dropUnlessResumed { onChatClick("chat_123") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Open Chat 123")
        }
    }
}

@Composable
fun ChatDetailScreen(chatId: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Chat Detail for ID: $chatId")
    }
}

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Profile Screen")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AIChatCourseAppTheme {
        MainView()
    }
}
