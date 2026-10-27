package mx.cristobal.aichatcourseapp.core.tabbar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable
import mx.cristobal.aichatcourseapp.core.chats.ChatDetailScreen
import mx.cristobal.aichatcourseapp.core.chats.ChatListScreen
import mx.cristobal.aichatcourseapp.core.explore.ExploreScreen
import mx.cristobal.aichatcourseapp.core.profile.ProfileScreen

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object Explore : Route
    @Serializable
    data object Chats : Route
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
    Route.Explore to BottomNavItem(
        title = "Explore",
        icon = Icons.Default.Explore
    ),
    Route.Chats to BottomNavItem(
        title = "Chats",
        icon = Icons.AutoMirrored.Filled.Chat
    ),
    Route.Profile to BottomNavItem(
        title = "Profile",
        icon = Icons.Default.Person
    )
)

@Composable
fun MainView() {
    val backStack = rememberNavBackStack(Route.Explore as Route)
    val currentRoute = backStack.lastOrNull() ?: Route.Explore

    // Determine selected bottom bar item (if we are on ChatDetail, Chat tab should be selected)
    val selectedTab = when (currentRoute) {
        is Route.Chats, is Route.ChatDetail -> Route.Chats
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
                entry<Route.Explore> {
                    ExploreScreen()
                }
                entry<Route.Chats> {
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
