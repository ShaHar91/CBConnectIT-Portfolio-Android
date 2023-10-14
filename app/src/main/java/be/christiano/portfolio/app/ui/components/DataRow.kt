package be.christiano.portfolio.app.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import be.christiano.portfolio.app.extensions.conditional
import be.christiano.portfolio.app.ui.theme.PortfolioTheme
import kotlin.math.max

@Composable
fun DataRow(
    modifier: Modifier = Modifier,
    labelText: String,
    labelTextStyle: TextStyle = MaterialTheme.typography.titleSmall,
    labelTextColor: Color = MaterialTheme.colorScheme.onBackground,
    startIcon: ImageVector? = null,
    endSlot: @Composable RowScope.() -> Unit,
    spaceBetween: Dp = 10.dp,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .height(48.dp)
            .conditional(onClick != null) {
                clickable { onClick?.invoke() }
            }
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (startIcon != null) {
            Icon(startIcon, contentDescription = "Localized description")

            Spacer(modifier = Modifier.width(6.dp))
        }

        HorizontalItemsWithMaximumSpaceTaken(
            modifier = Modifier
                .weight(1.0f),
            spaceBetween = spaceBetween,
            startSlot = {
                Text(
                    modifier = Modifier.wrapContentHeight(),
                    text = labelText,
                    overflow = TextOverflow.Ellipsis,
                    style = labelTextStyle,
                    color = labelTextColor.copy(if (enabled) 1f else 0.38f),
                    maxLines = 1
                )
            }, endSlot = {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    endSlot()
                }
            }
        )
    }
}

@Composable
fun ValueDataRow(
    modifier: Modifier = Modifier,
    labelText: String,
    labelTextStyle: TextStyle = MaterialTheme.typography.titleSmall,
    labelTextColor: Color = MaterialTheme.colorScheme.onBackground,
    startIcon: ImageVector? = null,
    endIcon: ImageVector? = Icons.AutoMirrored.Default.KeyboardArrowRight,
    valueText: String? = null,
    valueTextStyle: TextStyle = MaterialTheme.typography.titleSmall,
    onClick: (() -> Unit)? = null
) {
    DataRow(
        modifier = modifier,
        labelText = labelText,
        labelTextStyle = labelTextStyle,
        labelTextColor = labelTextColor,
        startIcon = startIcon,
        endSlot = {
            Text(
                modifier = Modifier.weight(1.0f),
                text = valueText ?: "",
                overflow = TextOverflow.Ellipsis,
                style = valueTextStyle,
                textAlign = TextAlign.End,
                maxLines = 1
            )

            if (endIcon != null) {
                Spacer(modifier = Modifier.width(6.dp))

                Icon(endIcon, contentDescription = "Localized description")
            }
        },
        onClick = onClick
    )
}

@Composable
fun ToggleableDataRow(
    modifier: Modifier = Modifier,
    labelText: String,
    labelTextStyle: TextStyle = MaterialTheme.typography.titleSmall,
    labelTextColor: Color = MaterialTheme.colorScheme.onBackground,
    startIcon: ImageVector? = null,
    checked: Boolean,
    enabled: Boolean = true,
    onCheckedChange: ((Boolean) -> Unit)?
) {
    DataRow(
        modifier = modifier,
        labelText = labelText,
        labelTextStyle = labelTextStyle,
        labelTextColor = labelTextColor,
        startIcon = startIcon,
        spaceBetween = 20.dp,
        enabled = enabled,
        endSlot = {
            Switch(enabled = enabled, checked = checked, onCheckedChange = onCheckedChange)
        }
    )
}


/**
 * When two items need to be laid out horizontally in a row, they can't know how much space they need to take out, which
 * more often than not results in the starting item taking up all the width it needs, squeezing the end item. This
 * layout makes sure to measure their max intrinsic width and give as much space as possible to each item, without
 * squeezing the other one. If both of them were to need more than half of the space, or less than half of the space,
 * they're simply given half of the width each.
 */
@Composable
fun HorizontalItemsWithMaximumSpaceTaken(
    startSlot: @Composable () -> Unit,
    endSlot: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    spaceBetween: Dp = 0.dp,
) {
    Layout(
        content = {
            startSlot()
            endSlot()
        },
        modifier = modifier,
    ) { measurables, constraints ->
        val first = measurables.getOrNull(0)
        val second = measurables.getOrNull(1)
        val firstWidth = first?.maxIntrinsicWidth(constraints.maxHeight) ?: 0
        val secondWidth = second?.maxIntrinsicWidth(constraints.maxHeight) ?: 0

        val totalWidth = constraints.maxWidth
        val halfWidth = totalWidth / 2

        val centerSpace = spaceBetween.roundToPx()
        val halfCenterSpace = centerSpace / 2

        val halfWidthMinusSpace = halfWidth - halfCenterSpace

        val bothTakeLessThanHalfSpace =
            firstWidth <= halfWidthMinusSpace && secondWidth <= halfWidthMinusSpace
        val bothTakeMoreThanHalfSpace =
            firstWidth > halfWidthMinusSpace && secondWidth > halfWidthMinusSpace
        val textsShouldShareEqualSpace = bothTakeLessThanHalfSpace || bothTakeMoreThanHalfSpace

        val firstConstraints: Constraints
        val secondConstraints: Constraints
        if (textsShouldShareEqualSpace) {
            val halfWidthMinusSpaceConstraints = constraints.copy(
                minWidth = halfWidthMinusSpace,
                maxWidth = halfWidthMinusSpace,
            )
            firstConstraints = halfWidthMinusSpaceConstraints
            secondConstraints = halfWidthMinusSpaceConstraints
        } else if (firstWidth > halfWidthMinusSpace) {
            firstConstraints = constraints.copy(
                minWidth = totalWidth - secondWidth - halfCenterSpace,
                maxWidth = totalWidth - secondWidth - halfCenterSpace,
            )
            secondConstraints = constraints.copy(
                minWidth = secondWidth,
                maxWidth = secondWidth,
            )
        } else {
            firstConstraints = constraints.copy(
                minWidth = firstWidth,
                maxWidth = firstWidth,
            )
            secondConstraints = constraints.copy(
                minWidth = totalWidth - firstWidth - halfCenterSpace,
                maxWidth = totalWidth - firstWidth - halfCenterSpace,
            )
        }
        val maxCommonHeight = maxOf(
            first?.maxIntrinsicHeight(firstConstraints.maxWidth) ?: 0,
            second?.maxIntrinsicHeight(secondConstraints.maxWidth) ?: 0,
        )
        val firstPlaceable = first?.measure(firstConstraints.copy(minHeight = maxCommonHeight))
        val secondPlaceable = second?.measure(secondConstraints.copy(minHeight = maxCommonHeight))
        val layoutHeight = max(firstPlaceable?.height ?: 0, secondPlaceable?.height ?: 0)
        layout(constraints.maxWidth, layoutHeight) {
            firstPlaceable?.placeRelative(0, 0)
            secondPlaceable?.placeRelative(constraints.maxWidth - secondPlaceable.width, 0)
        }
    }
}

@Preview("Light mode")
@Preview("Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DataRowPreview() {
    PortfolioTheme {
        Surface {
            Column {
                ValueDataRow(labelText = "This is a longer text than usual.")

                HorizontalDivider(Modifier.padding(start = 16.dp), thickness = 0.5.dp)

                ValueDataRow(labelText = "This is a text", valueText = "Some value")

                HorizontalDivider(Modifier.padding(start = 16.dp), thickness = 0.5.dp)

                ValueDataRow(
                    labelText = "This is a longer text to see what the data does",
                    valueText = "Some value"
                )

                HorizontalDivider(Modifier.padding(start = 16.dp), thickness = 0.5.dp)

                ValueDataRow(
                    labelText = "This is a text",
                    valueText = "Some value with a longer text"
                )

                HorizontalDivider(Modifier.padding(start = 16.dp), thickness = 0.5.dp)

                ValueDataRow(
                    labelText = "This is a longer text to see what the data does",
                    valueText = "Some value with a longer text"
                )

                HorizontalDivider(Modifier.padding(start = 16.dp), thickness = 0.5.dp)

                ValueDataRow(
                    labelText = "This is a longer text to see what the data does",
                    valueText = "Some value with a longer text",
                    endIcon = null
                )

                Spacer(modifier = Modifier.height(32.dp))

                HorizontalDivider(Modifier.padding(start = 16.dp), thickness = 0.5.dp)

                ToggleableDataRow(
                    labelText = "This is a longer text to see what the data does, but a longer text is needed",
                    checked = false,
                ) {}
            }
        }
    }
}