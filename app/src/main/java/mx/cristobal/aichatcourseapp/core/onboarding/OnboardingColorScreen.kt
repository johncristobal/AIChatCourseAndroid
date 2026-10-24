package mx.cristobal.aichatcourseapp.core.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.cristobal.aichatcourseapp.components.PrimaryButton
import mx.cristobal.aichatcourseapp.ui.theme.AIChatCourseAppTheme
import mx.cristobal.aichatcourseapp.utils.ColorSaver

@Composable
fun OnboardingColorScreen(
    modifier: Modifier = Modifier,
    onContinue: (Color) -> Unit = {}
) {
    val profileColors = listOf(Color.Red, Color.Green, Color.Blue, Color.Cyan, Color.Magenta, Color.Yellow)
    var selectedColor by rememberSaveable(stateSaver = ColorSaver) {
        mutableStateOf(Color.Transparent)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.navigationBars),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Select a color"
        )

        LazyVerticalGrid(
            columns = Fixed(3),
            modifier = Modifier
                .weight(1f),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(profileColors.size) {
                val padding by animateDpAsState(
                    targetValue = if (selectedColor == profileColors[it]) 10.dp else 0.dp,
                    animationSpec = spring(),
                    label = "padding"
                )

                Box(
                    Modifier
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
//                            .padding(if (selectedColor == profileColors[it]) 10.dp else 0.dp)
                            .padding(padding)
                            .clip(CircleShape)
                            .background(profileColors[it])
                            .clickable {
                                selectedColor = profileColors[it]
                            }
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = selectedColor != Color.Transparent,
            enter = slideInVertically { -it } + fadeIn(),
        ) {
            PrimaryButton(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                text = "Continue",
                onClick = {
                    onContinue(selectedColor)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboargingColorScreenPreview() {
    AIChatCourseAppTheme {
        OnboardingColorScreen()
    }
}
