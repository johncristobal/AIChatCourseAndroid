package mx.cristobal.aichatcourseapp.core.onboarding

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.cristobal.aichatcourseapp.components.PrimaryButton
import mx.cristobal.aichatcourseapp.ui.theme.AIChatCourseAppTheme

@Composable
fun OnboardingCompletedScreen(
    modifier: Modifier = Modifier,
    selectedColor: Color = Color.Transparent
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.navigationBars),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Complete",
            color = selectedColor,
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            modifier = modifier
                .padding(vertical = 16.dp),
            text = "Finish",
            onClick = {

            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboargingCompletedScreenPreview() {
    AIChatCourseAppTheme {
        OnboardingCompletedScreen()
    }
}
