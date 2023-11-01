package be.cbconnectit.portfolio.app.ui.main.introduction.sections.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.cbconnectit.portfolio.app.R
import be.cbconnectit.portfolio.app.ui.theme.PortfolioTheme

@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    actionText: String? = null,
    actionClicked: (() -> Unit)? = null,
    alignment: Alignment.Horizontal = Alignment.Start
) {
    val showAction = actionClicked != null && alignment == Alignment.Start

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = alignment
        ) {
            val textAlignment = when (alignment) {
                Alignment.Start -> TextAlign.Start
                Alignment.End -> TextAlign.End
                else -> TextAlign.Center
            }

            Text(
                textAlign = textAlignment,
                style = MaterialTheme.typography.titleMedium, text = title
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                textAlign = textAlignment,
                style = MaterialTheme.typography.headlineMedium, text = subtitle
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .height(2.dp)
                    .width(80.dp)
                    .background(MaterialTheme.colorScheme.inversePrimary)
            )
        }

        if (showAction && actionText != null) {
            TextButton(
                modifier = Modifier.height(30.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                onClick = { actionClicked?.invoke() }
            ) {
                Text(style = MaterialTheme.typography.labelLarge, text = actionText)

                Icon(modifier = Modifier.size(18.dp), painter = painterResource(id = R.drawable.ic_keyboard_arrow_right), contentDescription = "")
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SectionTitlePreview() {
    PortfolioTheme {
        Surface {
            Column {
                SectionTitle(title = "About me", subtitle = "Why Hire Me?", actionText = "See more")
                Spacer(modifier = Modifier.height(16.dp))
                SectionTitle(title = "About me", subtitle = "Why Hire Me?", actionText = "See more", actionClicked = {})
                Spacer(modifier = Modifier.height(16.dp))
                SectionTitle(title = "About me", subtitle = "Why Hire Me?", actionText = "See more", alignment = Alignment.CenterHorizontally)
                Spacer(modifier = Modifier.height(16.dp))
                SectionTitle(title = "About me", subtitle = "Why Hire Me?", actionText = "See more", actionClicked = {}, alignment = Alignment.CenterHorizontally)
                Spacer(modifier = Modifier.height(16.dp))
                SectionTitle(title = "About me", subtitle = "Why Hire Me?", actionText = "See more", alignment = Alignment.End)
                Spacer(modifier = Modifier.height(16.dp))
                SectionTitle(title = "About me", subtitle = "Why Hire Me?", actionText = "See more", actionClicked = {}, alignment = Alignment.End)
            }
        }
    }
}