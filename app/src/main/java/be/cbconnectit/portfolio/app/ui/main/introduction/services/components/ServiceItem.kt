package be.cbconnectit.portfolio.app.ui.main.introduction.services.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.cbconnectit.portfolio.app.R
import be.cbconnectit.portfolio.app.domain.model.Service
import be.cbconnectit.portfolio.app.domain.model.previewData
import be.cbconnectit.portfolio.app.domain.model.typeImage
import be.cbconnectit.portfolio.app.ui.theme.PortfolioTheme
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun ServiceItem(
    service: Service,
    shouldColorBackground: Boolean = false,
    onLearnMoreClicked: () -> Unit = {}
) {
    Surface(
        color = if (shouldColorBackground) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.background,
        contentColor = if (shouldColorBackground) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onBackground
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 50.dp, horizontal = 24.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = service.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(24.dp))

            MarkdownText(
                modifier = Modifier.fillMaxWidth(),
                markdown = service.description,
                style = MaterialTheme.typography.bodyLarge,
                linkColor = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.padding(bottom = 28.dp),
                onClick = {
                    onLearnMoreClicked()
                }
            ) {
                Text(text = stringResource(R.string.learn_more))
            }

            Spacer(modifier = Modifier.height(40.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(0.65f),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(service.imageUrl)
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    alignment = Alignment.Center,
                    contentDescription = "image Url"
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ServiceSectionPreview() {
    PortfolioTheme {
        ServiceItem(service = Service.previewData())
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ServiceSectionWithBackgroundPreview() {
    PortfolioTheme {
        ServiceItem(
            service = Service.previewData().copy(
                description = """
                    hello
                    <a href="www.google.be">hello Google</a>
                """.trimIndent()
            ),
            shouldColorBackground = true
        )
    }
}