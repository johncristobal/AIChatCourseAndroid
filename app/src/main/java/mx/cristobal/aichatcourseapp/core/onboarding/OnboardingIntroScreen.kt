package mx.cristobal.aichatcourseapp.core.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import mx.cristobal.aichatcourseapp.R
import mx.cristobal.aichatcourseapp.ui.theme.AIChatCourseAppTheme
import mx.cristobal.aichatcourseapp.utils.Constants

@Composable
fun OnboardingIntroScreen(modifier: Modifier = Modifier) {
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
        Text(
            text = "AI Chat"
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
        OnboardingIntroScreen()
    }
}
