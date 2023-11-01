package be.cbconnectit.portfolio.app.ui.main.introduction.sections.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.cbconnectit.portfolio.app.domain.enums.TechStack
import be.cbconnectit.portfolio.app.extensions.thenIf
import be.cbconnectit.portfolio.app.ui.theme.PortfolioTheme

@Composable
fun TechStacksCollection(
    modifier: Modifier = Modifier,
    techStacks: List<TechStack> = emptyList(),
    active: Boolean = false,
    horizontal: Boolean = true
) {
    if (horizontal) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            contentForCollection(
                modifier = Modifier.weight(1f),
                techStacks,
                active,
                true
            )
        }
    } else {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            contentForCollection(
                modifier = Modifier.weight(1f),
                techStacks,
                active,
                false
            )
        }
    }
}

@Composable
private fun contentForCollection(
    modifier: Modifier = Modifier,
    techStacks: List<TechStack> = emptyList(),
    active: Boolean = false,
    horizontal: Boolean = true
) {
    val colorScheme = MaterialTheme.colorScheme

//    Box(
//        modifier = modifier
//            .thenIf(horizontal) { Modifier.height(2.dp) }
//            .thenIf(!horizontal) { Modifier.width(2.dp) }
//            .background(colorScheme.primary)
//    )

    if (horizontal) {
        HorizontalDivider(modifier, thickness = 2.dp, color = colorScheme.primary)
    } else {
        VerticalDivider(modifier, thickness = 2.dp, color = colorScheme.primary)
    }


    TechStackCollectionWrapper(
        horizontal = horizontal,
        active = active
    ) {
        techStacks.forEachIndexed { index, techStack ->
            if (index != 0) {
                Spacer(modifier = Modifier
                    .thenIf(horizontal) { Modifier.width(10.dp) }
                    .thenIf(!horizontal) { Modifier.height(10.dp) }
                )
            }
            Icon(
                tint = if (active) colorScheme.onPrimary else colorScheme.primary,
                painter = painterResource(id = techStack.iconRes),
                contentDescription = ""
            )
        }
    }

    if (horizontal) {
        HorizontalDivider(modifier, thickness = 2.dp, color = colorScheme.primary)
    } else {
        VerticalDivider(modifier, thickness = 2.dp, color = colorScheme.primary)
    }


//    Box(
//        modifier = modifier
//            .thenIf(horizontal) { Modifier.height(2.dp) }
//            .thenIf(!horizontal) {
//                Modifier
//                    .fillMaxHeight()
//                    .width(2.dp)
//            }
//            .background(colorScheme.primary)
//    )
}


@Composable
fun TechStackCollectionWrapper(
    horizontal: Boolean = true,
    active: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val modifier = Modifier
        .thenIf(!active) { Modifier.border(2.dp, colorScheme.primary, CircleShape) }
        .thenIf(active) { Modifier.background(colorScheme.primary, CircleShape) }

    if (horizontal) {
        Row(
            modifier = modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            content = { content() }
        )
    } else {
        Column(
            modifier = modifier.padding(horizontal = 4.dp, vertical = 10.dp),
            content = { content() }
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TechStacksCollectionPreview() {
    PortfolioTheme {
        Surface {
            Column {
                TechStacksCollection(techStacks = TechStack.values().toList())
                Spacer(modifier = Modifier.height(20.dp))
                TechStacksCollection(techStacks = TechStack.values().toList(), active = true)
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.height(300.dp)) {
                    TechStacksCollection(techStacks = TechStack.values().toList(), active = true, horizontal = false)
                    Spacer(modifier = Modifier.width(20.dp))
                    TechStacksCollection(techStacks = TechStack.values().toList(), active = true, horizontal = false)
                }
            }
        }
    }
}