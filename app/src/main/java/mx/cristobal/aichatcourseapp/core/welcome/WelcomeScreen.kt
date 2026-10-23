package mx.cristobal.aichatcourseapp.core.welcome

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import mx.cristobal.aichatcourseapp.R
import mx.cristobal.aichatcourseapp.components.PrimaryButton
import mx.cristobal.aichatcourseapp.ui.theme.AIChatCourseAppTheme
import mx.cristobal.aichatcourseapp.utils.Constants

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onContinue: () -> Unit = {}
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageLoaderView(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            urlString = Constants.randomImageURL
        )

        TitleSection()

        ActionButtons(
            onContinue = onContinue
        )

        Text(
            text = "Terms & Conditions",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier
                .padding(vertical = 16.dp)
                .clickable {
                    uriHandler.openUri("https://www.google.com")
                }
        )
    }
}

@Composable
private fun TitleSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "AI Chat",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier
                .padding(top = 16.dp)
        )
        Text(
            text = "Demo app chatai course",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
private fun ActionButtons(
    modifier: Modifier = Modifier,
    onContinue: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PrimaryButton(
            modifier = modifier,
            text = "Get Started",
            onClick = onContinue
        )

        Text(
            text = "Already have an account? Sign in!",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable {

                }
        )
    }
}

@Composable
fun ImageLoaderView(
    modifier: Modifier = Modifier,
    urlString: String = Constants.randomImageURL
) {
    // Check if we are currently rendering inside an Android Studio Preview
    val isPreview = LocalInspectionMode.current
    val model = if (isPreview) {
        R.drawable.ai_icon // Local fallback resource for preview
    } else {
        urlString // Remote URL for production app
    }

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(model)
            .crossfade(true)
            .build(),
        contentDescription = "demo image",
        contentScale = ContentScale.FillBounds,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun OnboargingIntroScreenPreview() {
    AIChatCourseAppTheme {
        WelcomeScreen()
    }
}
