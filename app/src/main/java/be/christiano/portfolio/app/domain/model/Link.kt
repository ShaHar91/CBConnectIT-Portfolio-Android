package be.christiano.portfolio.app.domain.model

import be.christiano.portfolio.app.domain.enums.LinkType

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