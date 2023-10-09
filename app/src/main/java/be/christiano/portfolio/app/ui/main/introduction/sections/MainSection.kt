package be.christiano.portfolio.app.ui.main.introduction.sections

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.ui.main.introduction.sections.components.Social
import be.christiano.portfolio.app.ui.main.introduction.sections.components.SocialBar
import be.christiano.portfolio.app.ui.theme.PortfolioTheme

@Composable
fun MainSection(
    onClick: (Social) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.titleLarge,
            text = stringResource(R.string.introduction_hello)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary),
            text = stringResource(R.string.introduction_name)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            text = stringResource(R.string.introduction_function)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyLarge,
            text = stringResource(R.string.introduction_body)
        )

        Spacer(modifier = Modifier.height(24.dp))

        SocialBar(
            modifier = Modifier.padding(horizontal = 16.dp),
            onClick = onClick
        )

        @Composable
        fun MainImage(modifier: Modifier = Modifier, visible: Boolean = true) {
            Image(
                modifier = modifier
                    .alpha(if (visible) 1f else 0f)
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.main_image),
                contentDescription = "Main image"
            )
        }

        Box(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .padding(horizontal = 16.dp)
                .offset(y = (-20).dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            ElevatedCard {
                // internal image is needed otherwise the height would not be drawn...
                MainImage(Modifier.padding(horizontal = 30.dp), visible = false)
            }

            MainImage()
        }
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainSectionPreview() {
    PortfolioTheme {
        Surface {
            MainSection {}
        }
    }
}