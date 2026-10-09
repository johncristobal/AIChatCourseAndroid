package mx.cristobal.aichatcourseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import mx.cristobal.aichatcourseapp.ui.theme.AIChatCourseAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIChatCourseAppTheme {
                MainView()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun MainView() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(
                selectedKey = Route.Home,
                onSelectedKey = {

                }
            )
        }
    ) { innerPadding ->
        Greeting(
            name = "Android",
            modifier = Modifier.padding(innerPadding)
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

@Serializable
sealed interface Route: NavKey {
    @Serializable
    data object Home: Route
    @Serializable
    data object Chat: Route
    @Serializable
    data class ChatDetail(val chatId: String): Route
    @Serializable
    data object Profile: Route
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AIChatCourseAppTheme {
        MainView()
    }
}