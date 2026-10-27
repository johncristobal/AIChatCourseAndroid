package mx.cristobal.aichatcourseapp.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import mx.cristobal.aichatcourseapp.R
import mx.cristobal.aichatcourseapp.utils.Constants

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