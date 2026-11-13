package mx.cristobal.aichatcourseapp.core.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mx.cristobal.aichatcourseapp.R
import mx.cristobal.aichatcourseapp.components.views.CustomListCellView
import mx.cristobal.aichatcourseapp.services.avatar.services.remote.AvatarViewModel
import mx.cristobal.aichatcourseapp.services.user.services.UserViewModel
import mx.cristobal.aichatcourseapp.ui.theme.AIChatCourseAppTheme
import kotlin.repeat

@Composable
fun ProfileScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    avatarViewModel: AvatarViewModel = hiltViewModel(),
) {
    val currentUser = userViewModel.currentUser.collectAsStateWithLifecycle()
    avatarViewModel.getAvatarsForAuthor(currentUser.value?.userId ?: "")
    val myAvatars = avatarViewModel.avatarsForAuthor.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(currentUser.value?.profileColorCalculated(MaterialTheme.colorScheme.primary)!!)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "My avatar"
            )
            Image(
                modifier = Modifier
                    .size(44.dp)
                    .clickable {

                    },
                painter = painterResource(id = R.drawable.ai_icon),
                contentDescription = "AI icon",
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                repeat(myAvatars.value.size) {
                    val item = myAvatars.value[it]
                    CustomListCellView(
                        profileImageName = item.profileImageName,
                        title = item.name,
                        subtitle = item.description()
                    )
                }
            }
        }


    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    AIChatCourseAppTheme {
        ProfileScreen(
            userViewModel = UserViewModel(),
            avatarViewModel = AvatarViewModel()
        )
    }
}