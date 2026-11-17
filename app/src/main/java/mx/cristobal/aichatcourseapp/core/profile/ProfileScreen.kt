package mx.cristobal.aichatcourseapp.core.profile

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mx.cristobal.aichatcourseapp.R
import mx.cristobal.aichatcourseapp.components.views.CustomListCellView
import mx.cristobal.aichatcourseapp.services.avatar.services.remote.AvatarViewModel
import mx.cristobal.aichatcourseapp.services.user.services.AuthRepository
import mx.cristobal.aichatcourseapp.services.user.services.BillingRepository
import mx.cristobal.aichatcourseapp.services.user.services.UserViewModel
import mx.cristobal.aichatcourseapp.ui.theme.AIChatCourseAppTheme

@Composable
fun ProfileScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    avatarViewModel: AvatarViewModel = hiltViewModel(),
) {
    val currentUser by userViewModel.currentUser.collectAsStateWithLifecycle()
    val firebaseUser by userViewModel.firebaseUser.collectAsStateWithLifecycle()
    
    avatarViewModel.getAvatarsForAuthor(currentUser?.userId ?: "")
    val myAvatars = avatarViewModel.avatarsForAuthor.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val products by userViewModel.productDetails.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(currentUser?.profileColorCalculated(MaterialTheme.colorScheme.primary) ?: MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (firebaseUser != null) {
            Text(
                text = firebaseUser?.displayName ?: "Signed In User",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = firebaseUser?.email ?: firebaseUser?.uid ?: "",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = { userViewModel.signOut() }
            ) {
                Text("Sign Out")
            }
        } else {
            Text(
                text = "Not signed in",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = { userViewModel.signInWithGoogle(context) }
            ) {
                Text("Sign In with Google")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "My avatars",
                style = MaterialTheme.typography.titleSmall
            )
            Image(
                modifier = Modifier
                    .size(44.dp)
                    .clickable { },
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
            Column {
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

        Spacer(modifier = Modifier.height(16.dp))
        Text("Purchases...", style = MaterialTheme.typography.titleSmall)
        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            products.forEach { product ->
                Button(
                    onClick = {
                        val activity = context as? Activity
                        if (activity != null) {
                            userViewModel.buyProduct(activity, product)
                        }
                    }
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(vertical = 4.dp),
                        text = "Buy ${product.name} - ${product.oneTimePurchaseOfferDetails?.formattedPrice}")
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
            userViewModel = UserViewModel(
                BillingRepository(LocalContext.current),
                AuthRepository(LocalContext.current)
            ),
            avatarViewModel = AvatarViewModel()
        )
    }
}
