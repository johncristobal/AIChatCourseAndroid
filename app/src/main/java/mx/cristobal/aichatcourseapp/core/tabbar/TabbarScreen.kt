package mx.cristobal.aichatcourseapp.core.tabbar

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    val backStack = rememberNavBackStack(Route.Explore as Route)
    val currentRoute = backStack.lastOrNull() ?: Route.Explore
    val canNavigateBack = backStack.size > 1

    // Determine selected bottom bar item (if we are on ChatDetail, Chat tab should be selected)
    val selectedTab = when (currentRoute) {
        is Route.Chats, is Route.ChatDetail -> Route.Chats
        else -> currentRoute
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            MainTopAppBar(
                currentRoute = currentRoute as Route,
                canNavigateBack = canNavigateBack,
                onNavigateBack = { backStack.removeLastOrNull() },
                scrollBehavior = scrollBehavior
            )
        },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    currentRoute: Route,
    canNavigateBack: Boolean,
    onNavigateBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {

    val title = when (currentRoute) {
        is Route.Explore -> "Explore"
        is Route.Chats -> "Chats"
        is Route.ChatDetail -> "Chat"
        is Route.Profile -> "Profile"
    }

    LargeTopAppBar(
        title = { Text(text = title) },
        scrollBehavior = scrollBehavior,
        expandedHeight = 100.dp,
        windowInsets = WindowInsets(top = 8.dp),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate back"
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { /* Action */ }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
            IconButton(onClick = { /* Action */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options"
                )
            }
        }
    )
}