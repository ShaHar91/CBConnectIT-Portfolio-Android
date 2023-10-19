package be.christiano.portfolio.app.ui.main.introduction.sections.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.christiano.portfolio.app.domain.model.Experience
import be.christiano.portfolio.app.domain.model.previewData
import be.christiano.portfolio.app.ui.theme.PortfolioTheme

@Composable
fun ExperienceItem(
    modifier: Modifier = Modifier,
    experience: Experience,
    active: Boolean = false,
    horizontal: Boolean = true,
    maxLines: Int = Int.MAX_VALUE
) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    if (horizontal) {
        Column(modifier = modifier) {
            ExperienceHeader(
                experience = experience,
                horizontalAlignment = Alignment.CenterHorizontally
            )

            Spacer(modifier = Modifier.height(20.dp))

            TechStacksCollection(
                modifier = Modifier.fillMaxWidth(),
                techStacks = experience.techStacks,
                active = active,
                horizontal = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            ElevatedCard(
                modifier = Modifier.padding(horizontal = 10.dp),
                colors = if (active) CardDefaults.elevatedCardColors(containerColor = colorScheme.primary) else CardDefaults.elevatedCardColors()
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    style = typography.bodyLarge.copy(if (active) colorScheme.onPrimary else colorScheme.onSurface),
                    maxLines = maxLines,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    text = experience.shortDescription
                )
            }
        }
    } else {
        Row(modifier = modifier.height(IntrinsicSize.Min)) {
            TechStacksCollection(
                techStacks = experience.techStacks,
                active = active,
                horizontal = false
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f).padding(vertical = 20.dp)
            ) {
                ExperienceHeader(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    experience = experience,
                    horizontalAlignment = Alignment.Start
                )

                Spacer(modifier = Modifier.height(20.dp))

                ElevatedCard(
                    colors = if (active) CardDefaults.elevatedCardColors(containerColor = colorScheme.primary) else CardDefaults.elevatedCardColors()
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        style = typography.bodyLarge.copy(if (active) colorScheme.onPrimary else colorScheme.onSurface),
                        maxLines = maxLines,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        text = experience.description
                    )
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExperienceItemPreview() {
    PortfolioTheme {
        Surface {
            ExperienceItem(experience = Experience.previewData(), horizontal = false)

//            Column {
//            ExperienceItem(experience = Experience.previewData())
//                Spacer(modifier = Modifier.height(10.dp))
//            ExperienceItem(experience = Experience.previewData(), horizontal = false)
//            }
        }
    }
}