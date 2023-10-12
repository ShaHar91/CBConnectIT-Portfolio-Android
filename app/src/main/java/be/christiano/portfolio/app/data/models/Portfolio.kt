package be.christiano.portfolio.app.data.models

enum class Portfolio(
    val image: String,
    val title: String,
    val imageDesc: String,
    val description: String,
    val link: String,
    val tags: List<String>
) {
    One(
        image = "https://raw.githubusercontent.com/ShaHar91/Portfolio/master/public/portfolio1.png",
        title = "Android Core",
        imageDesc = "Android Library",
        description = "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        link = "https://github.com/wisemen-digital/AndroidCore",
        tags = listOf("Library", "Coroutines", "Kotlin", "XML", "MVVM")
    ),
    Two(
        image = "https://raw.githubusercontent.com/ShaHar91/Portfolio/master/public/portfolio2.png",
        title = "Measurements",
        imageDesc = "Android Library",
        description = "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        link = "https://github.com/wisemen-digital/Measurements",
        tags = listOf("Library", "Kotlin")
    ),
    Three(
        image = "https://raw.githubusercontent.com/ShaHar91/Portfolio/master/public/portfolio3.png",
        title = "Pokédex",
        imageDesc = "Android",
        description = "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        link = "https://github.com/ShaHar91/DemoPokedex",
        tags = listOf("Coroutines", "Kotlin", "Jetpack Compose", "Koin", "MVVM")
    ),
    Four(
        image = "https://raw.githubusercontent.com/ShaHar91/Portfolio/master/public/portfolio4.jpg",
        title = "FoodWatcher",
        imageDesc = "Android",
        description = "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        link = "https://github.com/ShaHar91/FoodWatcher-Android",
        tags = listOf("Coroutines", "Kotlin", "XML", "MVVM")
    ),
    Five(
        image = "https://raw.githubusercontent.com/ShaHar91/Portfolio/master/public/portfolio5.png",
        title = "PoemCollection Backend",
        imageDesc = "Kotlin",
        description = "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        link = "https://github.com/ShaHar91/PoemCollection-backend-ktor",
        tags = listOf("Ktor", "Kotlin", "Koin")
    ),
}