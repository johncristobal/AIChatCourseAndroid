package mx.cristobal.aichatcourseapp.core.chats

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mx.cristobal.aichatcourseapp.components.ImageLoaderView
import mx.cristobal.aichatcourseapp.services.avatar.services.remote.AvatarViewModel
import mx.cristobal.aichatcourseapp.services.chat.models.ChatModel
import mx.cristobal.aichatcourseapp.services.chat.services.ChatsViewModel
import mx.cristobal.aichatcourseapp.ui.theme.AIChatCourseAppTheme
import mx.cristobal.aichatcourseapp.utils.Constants

@Composable
fun ChatListScreen(
    onChatClick: (String) -> Unit,
    // Use LocalInspectionMode to instantiate a default ViewModel in Compose Previews,
    // avoiding IllegalStateException from hiltViewModel() missing an Activity Context.
    viewModel: ChatsViewModel = hiltViewModel(),
    avatarViewModel: AvatarViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val chats by viewModel.chats.collectAsStateWithLifecycle()
    val recentChats by avatarViewModel.avatars.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState)
            .padding(16.dp),
    ) {
        Text(text = "Recents")
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(recentChats.size) {
                val chat = recentChats[it]
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    ImageLoaderView(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        urlString = chat.profileImageName ?: Constants.randomImageURL
                    )

                    Text(chat.name ?: "")
                }
            }
        }

        Text(text = "Chats")
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                repeat(chats.size) {
                    val chat = chats[it]
                    ChatRowCellBuilder(
                        chat = chat
                    )
                }
            }
        }
    }
}

@Composable
fun ChatRowCellBuilder(
    modifier: Modifier = Modifier,
    chat: ChatModel,
    avatarViewModel: AvatarViewModel = hiltViewModel()
) {
    val avatar = avatarViewModel.getAvatar(chat.avatarId)

    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        ImageLoaderView(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            urlString = avatar.profileImageName ?: Constants.randomImageURL
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = avatar.name ?: "",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = avatar.description(),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun ChatRowCellView(modifier: Modifier = Modifier) {
    Text("Chat Row")
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
private fun ChatListScreenPrev() {
    AIChatCourseAppTheme {
        ChatListScreen(
            viewModel = ChatsViewModel(),
            onChatClick = { }
        )
    }
}