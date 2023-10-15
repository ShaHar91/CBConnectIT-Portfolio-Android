package be.christiano.portfolio.app.ui.main.introduction.sections.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import be.christiano.portfolio.app.extensions.thenIf
import be.christiano.portfolio.app.ui.theme.PortfolioTheme

@Composable
fun ExperienceCard(
    modifier: Modifier = Modifier,
    experience: Experience,
    active: Boolean = false
) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(modifier = modifier.padding(vertical = 20.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = colorScheme.primary),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = experience.jobPosition
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = typography.labelLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = "${experience.from} - ${experience.to}"
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = experience.company
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(2.dp)
                    .background(colorScheme.primary)
            )

            Text(
                modifier = Modifier
                    .thenIf(!active) { Modifier.border(2.dp, colorScheme.primary, CircleShape) }
                    .thenIf(active) { Modifier.background(colorScheme.primary, CircleShape) }
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                style = typography.labelMedium.copy(if (active) colorScheme.onPrimary else colorScheme.primary),
                text = "01"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(2.dp)
                    .background(colorScheme.primary)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        ElevatedCard(
            modifier = Modifier.padding(horizontal = 10.dp),
            colors = if (active) CardDefaults.elevatedCardColors(containerColor = colorScheme.primary) else CardDefaults.elevatedCardColors()
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                style = typography.bodyLarge.copy(if (active) colorScheme.onPrimary else colorScheme.onSurface),
                maxLines = 10,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                text = experience.description
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExperienceCardPreview() {
    PortfolioTheme {
        Surface {
            ExperienceCard(
                experience = Experience("", "", "", "", "")
            )
        }
    }
}