package mx.cristobal.aichatcourseapp.core.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mx.cristobal.aichatcourseapp.services.chat.viewmodel.ChatsViewModel
import mx.cristobal.aichatcourseapp.ui.theme.AIChatCourseAppTheme

@Composable
fun ChatListScreen(
    onChatClick: (String) -> Unit,
    viewModel: ChatsViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val chats by viewModel.chats.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState)
            .padding(16.dp),
    ) {
        Text(text = "Chats")
        Column(
            modifier = Modifier
        ) {
            repeat(chats.size) {
                val chat = chats[it]
                Text("hi")
                Text(chat.id)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatListScreenPrev() {
    AIChatCourseAppTheme {
        ChatListScreen(
            onChatClick = { }
        )
    }
}