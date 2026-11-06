package mx.cristobal.aichatcourseapp.components.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import mx.cristobal.aichatcourseapp.components.ImageLoaderView
import mx.cristobal.aichatcourseapp.utils.Constants

@Composable
fun CustomListCellView(
    modifier: Modifier = Modifier,
    profileImageName: String?,
    title: String?,
    subtitle: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        ImageLoaderView(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp)),
            urlString = profileImageName ?: Constants.randomImageURL
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            if (!title.isNullOrBlank()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            if (!subtitle.isNullOrBlank()) {
                Text(
                    text = subtitle ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}