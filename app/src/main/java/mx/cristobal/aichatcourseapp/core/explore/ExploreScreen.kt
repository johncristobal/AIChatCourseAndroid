package mx.cristobal.aichatcourseapp.core.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.cristobal.aichatcourseapp.components.ImageLoaderView
import mx.cristobal.aichatcourseapp.services.avatar.models.AvatarModel
import mx.cristobal.aichatcourseapp.services.avatar.models.CharacterOption
import mx.cristobal.aichatcourseapp.ui.theme.AIChatCourseAppTheme
import mx.cristobal.aichatcourseapp.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.3f), Color.Black.copy(alpha = 0.4f))
    )
    val scrollState = rememberScrollState()
    // Brush.horizontalGradient - gradiente horizontal de izq a der
    // Brush.linearGradient - gradiente lineal de esquina a esquina

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        val featuerdItems = remember { AvatarModel.mocks }
        var selection: AvatarModel? by remember { mutableStateOf(null) }
        val pagerFeaturedState = rememberPagerState(
            pageCount = { featuerdItems.count() }
        )

        val popularItems = remember { AvatarModel.mocks }

        var categories = remember { CharacterOption.entries }
        val pagerCategoryState = rememberPagerState(
            pageCount = { categories.count() }
        )

        Text(
            text = "Featured avatars"
        )
        // HorizontalPager - Carousel with images
        HorizontalPager(
            state = pagerFeaturedState,

        ) { page ->
            val item = featuerdItems[page]
            selection = item

            Box(
                Modifier
                    .height(200.dp)
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.BottomStart
            ) {
                ImageLoaderView(
                    modifier = modifier
                        .fillMaxWidth(),
                    urlString = item.profileImageName ?: Constants.randomImageURL
                )
                CompositionLocalProvider(LocalContentColor provides Color.White) {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .background(brush = gradientBrush)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = item.name ?: "",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            text = item.description(),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerFeaturedState.pageCount) { index ->
                val selectedColor = if (pagerFeaturedState.currentPage == index) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.secondary
                }
                Box(
                    modifier = modifier
                        .padding(4.dp)
                        .height(8.dp)
                        .width(8.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            }
        }

        Text("Categories")
        HorizontalPager(
            modifier = modifier
                .fillMaxWidth(),
            state = pagerCategoryState,
            pageSize = PageSize.Fixed(180.dp)
        ) { page ->
            val item = categories[page]
            val image = popularItems.firstOrNull {
                it.characterOption == item
            }?.profileImageName

            Box(
                Modifier
                    .height(200.dp)
                    .padding(end = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Transparent),
                contentAlignment = Alignment.BottomStart
            ) {
                ImageLoaderView(
                    modifier = modifier
                        .fillMaxWidth(),
                    urlString = image ?: Constants.randomImageURL
                )
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(brush = gradientBrush)
                        .padding(8.dp)
                ) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }
        }

        Text(
            modifier = modifier
                .padding(top = 16.dp),
            text = "Popular"
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                repeat(popularItems.size) {
                val item = popularItems[it]
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
                            urlString = item.profileImageName ?: Constants.randomImageURL
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = item.name ?: "",
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(
                                text = item.description(),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExploreViewPrev() {
    AIChatCourseAppTheme {
        ExploreScreen()
    }
}