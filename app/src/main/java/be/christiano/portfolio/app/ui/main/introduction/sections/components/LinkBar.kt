package be.christiano.portfolio.app.ui.main.introduction.sections.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import be.christiano.portfolio.app.domain.enums.Social
import be.christiano.portfolio.app.domain.model.Link
import be.christiano.portfolio.app.ui.theme.PortfolioTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun LinkBar(
    modifier: Modifier = Modifier,
    itemSize: Dp = 48.dp,
    contentPadding: Dp = 10.dp,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    links: List<Link> = emptyList(),
    onClick: (Link) -> Unit
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement
    ) {
        links.forEach { link ->
            ElevatedCard(
                modifier = Modifier.size(itemSize),
                shape = RoundedCornerShape(6.dp),
                onClick = { onClick(link) }
            ) {

                Icon(
                    modifier = Modifier.fillMaxSize().padding(contentPadding),
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
