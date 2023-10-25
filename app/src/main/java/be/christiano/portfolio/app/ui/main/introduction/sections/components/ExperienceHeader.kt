package be.christiano.portfolio.app.ui.main.introduction.sections.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.christiano.portfolio.app.domain.model.Experience
import be.christiano.portfolio.app.domain.model.previewData
import be.christiano.portfolio.app.ui.theme.PortfolioTheme

@Composable
fun ExperienceHeader(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    experience: Experience,
) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = if (horizontalAlignment == Alignment.CenterHorizontally) TextAlign.Center else TextAlign.Start,
            style = typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = colorScheme.primary),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = experience.jobPosition
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = if (horizontalAlignment == Alignment.CenterHorizontally) TextAlign.Center else TextAlign.Start,
            style = typography.labelLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = experience.formattedDate
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = if (horizontalAlignment == Alignment.CenterHorizontally) TextAlign.Center else TextAlign.Start,
            style = typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = experience.company
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExperienceHeaderPreview() {
    PortfolioTheme {
        Surface {
            Column {
                ExperienceHeader(experience = Experience.previewData())

                Spacer(modifier = Modifier.height(20.dp))

                ExperienceHeader(experience = Experience.previewData(), horizontalAlignment = Alignment.CenterHorizontally)
            }
        }
    }
}