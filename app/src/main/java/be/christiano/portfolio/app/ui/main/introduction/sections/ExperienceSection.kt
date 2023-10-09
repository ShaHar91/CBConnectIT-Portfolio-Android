package be.christiano.portfolio.app.ui.main.introduction.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.data.models.Experience
import be.christiano.portfolio.app.ui.main.introduction.sections.components.ExperienceCard
import be.christiano.portfolio.app.ui.main.introduction.sections.components.SectionTitle
import be.christiano.portfolio.app.ui.theme.PortfolioTheme

@Composable
fun ExperienceSection(
    experiences: List<Experience>,
    actionClicked: () -> Unit
) {
    Column {
        SectionTitle(
            modifier = Modifier.padding(start = 24.dp, end = 12.dp),
            title = stringResource(R.string.service),
            subtitle = stringResource(R.string.service_subtitle),
            actionText = stringResource(R.string.see_more),
            actionClicked = actionClicked
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp)
        ) {
            itemsIndexed(experiences) { index, item ->
                ExperienceCard(
                    modifier = Modifier.fillParentMaxWidth(),
                    experience = item,
                    index == 0
                )
            }
        }
    }
}

@Preview
@Composable
fun ExperienceSectionPreview() {
    PortfolioTheme {
        Surface {
            ExperienceSection(Experience.values().toList()) {}
        }
    }
}