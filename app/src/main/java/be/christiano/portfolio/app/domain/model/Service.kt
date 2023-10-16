package be.christiano.portfolio.app.domain.model

data class Service(
    val id: String,
    val image: String,
    val title: String,
    val description: String
){
    companion object
}

fun Service.Companion.previewData() = Service(
    "1",
    "https://raw.githubusercontent.com/ShaHar91/Portfolio/master/public/android_img.png",
    "Android Development",
    "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
)