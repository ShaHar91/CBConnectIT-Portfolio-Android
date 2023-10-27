package be.christiano.portfolio.app.ui.main.introduction.sections.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.christiano.portfolio.app.domain.model.Link
import be.christiano.portfolio.app.domain.model.Work
import be.christiano.portfolio.app.domain.model.previewData
import be.christiano.portfolio.app.extensions.thenIf
import be.christiano.portfolio.app.ui.components.textflow.TextFlow
import be.christiano.portfolio.app.ui.components.textflow.TextFlowObstacleAlignment
import be.christiano.portfolio.app.ui.theme.PortfolioTheme
import coil.compose.AsyncImage

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WorkDetail(
    imageStartAligned: Boolean = true,
    work: Work,
    onClick: (Link) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .thenIf(imageStartAligned) { Modifier.padding(start = 136.dp) },
            text = work.title,
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextFlow(
            obstacleAlignment = if (imageStartAligned) TextFlowObstacleAlignment.TopStart else TextFlowObstacleAlignment.TopEnd,
            text = work.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = 8.dp,
                        bottom = 4.dp,
                        start = if (imageStartAligned) 0.dp else 16.dp,
                        end = if (imageStartAligned) 16.dp else 0.dp
                    )
                    .widthIn(50.dp, 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier.aspectRatio(1f),
                    model = work.image,
                    contentDescription = work.title
                )

                Spacer(modifier = Modifier.height(12.dp))

                LinkBar(
                    horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.Top),
                    links = work.links,
                    itemSize = 32.dp,
                    contentPadding = 8.dp,
                    onClick = onClick
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.Top),
        ) {
            work.tags.forEach {
                Text(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                    text = it.name,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WorkDetailPreview() {
    PortfolioTheme {
        Surface {
            WorkDetail(work = Work.previewData(), onClick = {})
        }
    }
}