package be.christiano.portfolio.app.domain.model

data class Work(
    val image: String,
    val title: String,
    val description: String,
    val link: String,
    val tags: List<Tag>
)

data class Tag(
    val name: String
)