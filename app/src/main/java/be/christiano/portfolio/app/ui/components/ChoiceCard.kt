package be.christiano.portfolio.app.ui.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.ui.theme.PortfolioTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoiceCard(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @DrawableRes drawable: Int,
    checked: Boolean,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(MaterialTheme.colorScheme.surfaceColorAtElevation(if (checked) 30.dp else 0.dp))
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
        ) innerColumn@{
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {

                this@innerColumn.AnimatedVisibility(
                    visible = checked,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    RadioButton(selected = checked, onClick = null)
                }

                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(id = drawable),
                    contentDescription = ""
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ChoiceCardPreview() {
    PortfolioTheme {
        Row {
            ChoiceCard(modifier = Modifier.weight(1f), title = R.string.jetpack_compose, drawable = R.drawable.compose, checked = false) {}

            Spacer(Modifier.width(16.dp))

            ChoiceCard(modifier = Modifier.weight(1f), title = R.string.xml_layout, drawable = R.drawable.xml, checked = true) {}
        }
    }
}