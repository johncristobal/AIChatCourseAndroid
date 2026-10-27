package mx.cristobal.aichatcourseapp.core.explore

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.cristobal.aichatcourseapp.R
import mx.cristobal.aichatcourseapp.components.ImageLoaderView
import mx.cristobal.aichatcourseapp.services.avatar.models.AvatarModel
import mx.cristobal.aichatcourseapp.ui.theme.AIChatCourseAppTheme
import mx.cristobal.aichatcourseapp.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val screenWidthDp = with(density) { windowInfo.containerSize.width.toDp() }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        val items = remember {
            AvatarModel.mocks
        }

//        HorizontalMultiBrowseCarousel(
//            state = rememberCarouselState { items.count() },
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentHeight()
//                .padding(top = 16.dp, bottom = 16.dp),
//            preferredItemWidth = screenWidthDp,
//            itemSpacing = 8.dp,
//            contentPadding = PaddingValues(horizontal = 16.dp)
//        ) { i ->
//        HorizontalUncontainedCarousel(
//            state = rememberCarouselState { items.count() },
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentHeight()
//                .padding(vertical = 16.dp),
//            itemWidth = screenWidthDp,
//            itemSpacing = 8.dp,
//            contentPadding = PaddingValues(horizontal = 16.dp)
//        ) { i ->
//            val item = items[i]
//            Card(
//                modifier = modifier
//                    .height(200.dp)
//                    .padding(horizontal = 16.dp)
//                    .background(Color.Transparent),
//                shape = RoundedCornerShape(24.dp)
//            ) {
//                ImageLoaderView(
//                    modifier = modifier
//                        .fillMaxSize(),
//                    urlString = item.profileImageName ?: Constants.randomImageURL
//                )
//            }
//        }
        val pagerState = rememberPagerState(
            pageCount = { items.count() }
        )

        HorizontalPager(state = pagerState) { page ->
            val item = items[page]
            Card(
                modifier = modifier
                    .height(200.dp)
                    .padding(horizontal = 16.dp)
                    .background(Color.Transparent),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column (
                    Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                ) {
                    ImageLoaderView(
                        modifier = modifier
                            .fillMaxWidth(),
                        urlString = item.profileImageName ?: Constants.randomImageURL
                    )
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