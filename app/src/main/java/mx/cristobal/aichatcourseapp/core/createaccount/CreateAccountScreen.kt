package mx.cristobal.aichatcourseapp.core.createaccount

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mx.cristobal.aichatcourseapp.services.user.services.UserViewModel

@Composable
fun CreateAccountSheet(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel(),
    onSignInSuccess: () -> Unit = {}
) {
    val context = LocalContext.current
    val isLoading by userViewModel.isLoading.collectAsStateWithLifecycle()
    val signInError by userViewModel.signInError.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to AI Chat",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Sign in with your Google Account to save and sync your data.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    userViewModel.signInWithGoogle(
                        context = context,
                        onSuccess = {
                            Toast.makeText(context, "Signed in successfully!", Toast.LENGTH_SHORT).show()
                            onSignInSuccess()
                        },
                        onError = { message ->
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                        }
                    )
                }
            ) {
                Text(text = "Sign in with Google")
            }
        }

        if (signInError != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = signInError ?: "",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }
    }
}
