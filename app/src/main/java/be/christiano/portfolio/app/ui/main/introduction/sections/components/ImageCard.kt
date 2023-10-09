package be.christiano.portfolio.app.ui.main.introduction.sections.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.data.models.Portfolio
import be.christiano.portfolio.app.data.models.Service
import be.christiano.portfolio.app.ui.theme.PortfolioTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    imgDescription: String,
    text: String,
    body: String,
    imgShape: Shape = RoundedCornerShape(6.dp),
    tags: List<String>? = null
) {
    ElevatedCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Image(
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .clip(imgShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, imgShape),
                painter = painterResource(id = image),
                contentDescription = imgDescription
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = text
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 3,
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
                                .background(color = MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(6.dp))
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
                val service = Service.values().first()
                ImageCard(image = service.icon, text = service.title, body = service.description, imgDescription = service.imageDesc)
                Spacer(modifier = Modifier.height(20.dp))
                val portfolio = Portfolio.values().first()
                ImageCard(image = portfolio.image, text = portfolio.title, body = portfolio.description, imgDescription = portfolio.imageDesc, tags = portfolio.tags)
            }
        }
    }
}