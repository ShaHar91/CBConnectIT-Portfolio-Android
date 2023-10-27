package be.christiano.portfolio.app.ui.main.introduction.sections.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.christiano.portfolio.app.domain.model.Service
import be.christiano.portfolio.app.domain.model.Work
import be.christiano.portfolio.app.domain.model.previewData
import be.christiano.portfolio.app.ui.theme.PortfolioTheme
import coil.compose.AsyncImage

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: String,
    imgDescription: String,
    text: String,
    body: String,
    bodyMaxLines: Int = 3,
    imgShape: Shape = RoundedCornerShape(6.dp),
    tags: List<String>? = null
) {
    ElevatedCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            AsyncImage(
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .clip(imgShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, imgShape),
                model = image,
                contentDescription = imgDescription
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = text
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = bodyMaxLines,
                overflow = TextOverflow.Ellipsis,
                text = body
            )

            if (tags != null) {
                Spacer(modifier = Modifier.height(10.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.Top)
                ) {
                    tags.forEach {
                        Text(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.secondaryContainer,
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 4.dp),
                            text = it,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PortfolioCardPreview() {
    PortfolioTheme {
        Surface {
            Column {
                val service = Service.previewData()
                ImageCard(
                    image = service.image,
                    text = service.title,
                    body = service.description,
                    imgDescription = service.title
                )
                Spacer(modifier = Modifier.height(20.dp))
                val portfolio = Work.previewData()
                ImageCard(
                    image = portfolio.bannerImage,
                    text = portfolio.title,
                    body = "Created this library in order to streamline and simplify the setup of new projects. Instead of copying a lot of classes and reimplementing it differently each time a single dependency was all we needed.",
                    imgDescription = portfolio.title,
                    bodyMaxLines = 5,
                    tags = portfolio.tags.map { it.name }
                )
            }
        }
    }
}