package be.christiano.portfolio.app.domain.model

import be.christiano.portfolio.app.domain.enums.LinkType

data class Work(
    val id: String,
    val bannerImage: String,
    val image: String,
    val title: String,
    val shortDescription: String,
    val description: String,
    val links: List<Link>,
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
    "https://raw.githubusercontent.com/ShaHar91/Portfolio/master/public/project_image.png",
    "Android Core",
    "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
    "Nam ac blandit arcu. Aenean vel tellus nisi. Curabitur at nisi consequat, fringilla neque ut, laoreet felis. Maecenas ac ultrices tellus. Curabitur in fermentum dolor. Praesent et elementum nisi. Aliquam laoreet condimentum semper. Sed posuere neque ac vulputate aliquam. Proin ac dolor diam. Quisque quis elementum magna. Curabitur lobortis lorem a viverra dapibus. Donec feugiat non velit at varius.Nam ac blandit arcu. Aenean vel tellus nisi. Curabitur at nisi consequat, fringilla neque ut, laoreet felis. Maecenas ac ultrices tellus. Curabitur in fermentum dolor. Praesent et elementum nisi. Aliquam laoreet condimentum semper. Sed posuere neque ac vulputate aliquam. Proin ac dolor diam. Quisque quis elementum magna. Curabitur lobortis lorem a viverra dapibus. Donec feugiat non velit at varius.",
    listOf(Link(LinkType.Github,"")),
    listOf(Tag("1", "Coroutines"), Tag("2", "Kotlin"))
)

fun Tag.Companion.previewData() = Tag("1", "Kotlin")