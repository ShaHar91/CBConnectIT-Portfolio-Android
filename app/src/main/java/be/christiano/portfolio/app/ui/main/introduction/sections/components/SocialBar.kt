package be.christiano.portfolio.app.ui.main.introduction.sections.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.ui.theme.PortfolioTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialBar(
    modifier: Modifier = Modifier,
    onClick: (Social) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        val socials = Social.values()
        socials.forEachIndexed { index, social ->
            ElevatedCard(
                modifier = Modifier.padding(
                    start = if (index != 0) 4.dp else 0.dp,
                    end = if (index != socials.count() - 1) 4.dp else 0.dp
                ),
                shape = RoundedCornerShape(6.dp),
                onClick = { onClick(social) }
            ) {
                Icon(
                    modifier = Modifier.padding(10.dp),
                    painter = painterResource(id = social.icon),
                    contentDescription = social.name
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SocialBarPreview() {
    PortfolioTheme {
        Surface {
            SocialBar {}
        }
    }
}

enum class Social(val link: String, @DrawableRes val icon: Int) {
    Github("https://github.com/ShaHar91", R.drawable.ic_github),
    LinkedIn("https://www.linkedin.com/in/christiano-bolla/", R.drawable.ic_linkedin)
}