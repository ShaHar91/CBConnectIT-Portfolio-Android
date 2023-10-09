package be.christiano.portfolio.app.ui.main.introduction.sections

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.ui.main.introduction.sections.components.AboutMeCard
import be.christiano.portfolio.app.ui.main.introduction.sections.components.SectionTitle
import be.christiano.portfolio.app.ui.theme.PortfolioTheme

@Composable
fun AboutMeSection(
    yearsOfExperience: Int
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(vertical = 32.dp, horizontal = 16.dp)
    ) {
        SectionTitle(title = stringResource(R.string.about_me), subtitle = stringResource(R.string.why_hire_me), alignment = Alignment.CenterHorizontally)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            textAlign = TextAlign.Center,
            text = stringResource(R.string.about_me_body)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            AboutMeCard(
                modifier = Modifier.width(100.dp),
                title = stringResource(R.string.experience),
                subtitle = stringResource(R.string.experience_in_years, yearsOfExperience)
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_experience), contentDescription = "")
            }

            Spacer(modifier = Modifier.width(16.dp))

            AboutMeCard(
                modifier = Modifier.width(100.dp),
                title = stringResource(R.string.completed),
                subtitle = stringResource(R.string.completed_projects)
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_fact_check), contentDescription = "")
            }

            Spacer(modifier = Modifier.width(16.dp))

            AboutMeCard(
                modifier = Modifier.width(100.dp),
                title = "Something",
                subtitle = "Dunno"
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_assignment_turned_in), contentDescription = "")
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AboutMeSectionPreview() {
    PortfolioTheme {
        Surface {
            AboutMeSection(2)
        }
    }
}