package be.christiano.portfolio.app.domain.model

data class Work(
    val id: String,
    val image: String,
    val title: String,
    val description: String,
    val link: String,
    val tags: List<Tag>
) {
    companion object
}

data class Tag(
    val id: String,
    val name: String
) {
    companion object
}


fun Work.Companion.previewData() = Work(
    "1",
    "https://raw.githubusercontent.com/ShaHar91/Portfolio/master/public/portfolio1.png",
    "Android Core",
    "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
    "https://github.com/wisemen-digital/AndroidCore",
    emptyList()
)

fun Tag.Companion.previewData() = Tag("1", "Kotlin")