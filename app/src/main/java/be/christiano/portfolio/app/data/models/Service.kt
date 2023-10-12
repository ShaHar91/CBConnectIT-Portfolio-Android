package be.christiano.portfolio.app.data.models

enum class Service(
    val icon: String,
    val imageDesc: String,
    val title: String,
    val description: String
) {
    Android(
        icon = "https://raw.githubusercontent.com/ShaHar91/Portfolio/master/public/android_img.png",
        imageDesc = "Android Icon",
        title = "Android Development",
        description = "Lorum ipsum dolor sit amet."
    ),
    Tutoring(
        icon = "https://raw.githubusercontent.com/ShaHar91/Portfolio/master/public/tutoring_img.png",
        imageDesc = "Tutoring Icon",
        title = "Tutoring",
        description = "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    Teamwork(
        icon = "https://raw.githubusercontent.com/ShaHar91/Portfolio/master/public/teamwork_img.png",
        imageDesc = "Teamwork Icon",
        title = "Teamwork",
        description = "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    )
}