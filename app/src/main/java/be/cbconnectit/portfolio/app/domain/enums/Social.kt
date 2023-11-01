package be.cbconnectit.portfolio.app.domain.enums

enum class Social(val link: String, val type: LinkType) {
    Github("https://github.com/ShaHar91", LinkType.Github),
    LinkedIn("https://www.linkedin.com/in/christiano-bolla/", LinkType.LinkedIn)
}