package be.christiano.portfolio.app.data.models

import androidx.annotation.DrawableRes
import be.christiano.portfolio.app.R

enum class Portfolio(
    @DrawableRes val image: Int,
    val title: String,
    val imageDesc: String,
    val description: String,
    val link: String,
    val tags: List<String>
) {
    One(
        image = R.drawable.portfolio1,
        title = "Android Core",
        imageDesc = "Android Library",
        description = "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        link = "https://github.com/wisemen-digital/AndroidCore",
        tags = listOf("Library", "Coroutines", "Kotlin", "XML", "MVVM")
    ),
    Two(
        image = R.drawable.portfolio2,
        title = "Measurements",
        imageDesc = "Android Library",
        description = "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        link = "https://github.com/wisemen-digital/Measurements",
        tags = listOf("Library", "Kotlin")
    ),
    Three(
        image = R.drawable.portfolio3,
        title = "Pokédex",
        imageDesc = "Android",
        description = "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        link = "https://github.com/ShaHar91/DemoPokedex",
        tags = listOf("Coroutines", "Kotlin", "Jetpack Compose", "Koin", "MVVM")
    ),
    Four(
        image = R.drawable.portfolio4,
        title = "FoodWatcher",
        imageDesc = "Android",
        description = "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        link = "https://github.com/ShaHar91/FoodWatcher-Android",
        tags = listOf("Coroutines", "Kotlin", "XML", "MVVM")
    ),
    Five(
        image = R.drawable.portfolio5,
        title = "PoemCollection Backend",
        imageDesc = "Kotlin",
        description = "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        link = "https://github.com/ShaHar91/PoemCollection-backend-ktor",
        tags = listOf("Ktor", "Kotlin", "Koin")
    ),
}