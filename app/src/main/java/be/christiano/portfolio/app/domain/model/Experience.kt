package be.christiano.portfolio.app.domain.model

data class Experience(
    val jobPosition: String,
    val description: String,
    val company: String,
    val from: String,
    val to: String
)