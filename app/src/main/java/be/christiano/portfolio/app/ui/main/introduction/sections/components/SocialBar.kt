package be.christiano.portfolio.app.ui.main.introduction.sections.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.christiano.portfolio.app.domain.enums.Social
import be.christiano.portfolio.app.domain.model.Link
import be.christiano.portfolio.app.ui.theme.PortfolioTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkBar(
    modifier: Modifier = Modifier,
    links: List<Link> = emptyList(),
    onClick: (Link) -> Unit
) {
    Row(
        modifier = modifier
    ) {
        links.forEachIndexed { index, link ->
            ElevatedCard(
                modifier = Modifier.padding(
                    start = if (index != 0) 4.dp else 0.dp,
                    end = if (index != links.count() - 1) 4.dp else 0.dp
                ),
                shape = RoundedCornerShape(6.dp),
                onClick = { onClick(link) }
            ) {
                Icon(
                    modifier = Modifier.padding(10.dp),
                    painter = painterResource(id = link.type.iconRes),
                    contentDescription = link.type.name
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LinkBarPreview() {
    PortfolioTheme {
        Surface {
            LinkBar(links = Social.values().map { Link(it.type, it.link) }) {}
        }
    }
}
