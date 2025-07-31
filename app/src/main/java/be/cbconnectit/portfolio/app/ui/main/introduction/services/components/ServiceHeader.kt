package be.cbconnectit.portfolio.app.ui.main.introduction.services.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.cbconnectit.portfolio.app.R
import be.cbconnectit.portfolio.app.ui.theme.PortfolioTheme
import coil.compose.AsyncImage

@Composable
fun ServiceHeader(
    title: String,
    text: String,
    modifier: Modifier = Modifier,
    imageUrl: String? = null
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageUrl ?: R.drawable.img_services_banner,
            contentDescription = "Services banner",
            modifier = Modifier
                .matchParentSize(), // Ensures the Image matches the size of the Box
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary.copy(0.82f)),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 56.dp)
                    .padding(horizontal = 16.dp)
                    .padding(start = 40.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                text = title,
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 56.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ServiceHeaderContentPreview() {
    PortfolioTheme {
        ServiceHeader(
            "This is a title",
            "This is a text"
        )
    }
}
