package be.cbconnectit.portfolio.app.domain.model

import be.cbconnectit.portfolio.app.domain.enums.LinkType

data class Link(
    val type: LinkType,
    val url: String
) {
    companion object
}

fun Link.Companion.previewData() = Link(
    LinkType.Github,
    ""
)