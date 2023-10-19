package be.christiano.portfolio.app.domain.model

import be.christiano.portfolio.app.domain.enums.TechStack

data class Experience(
    val id :String,
    val jobPosition: String,
    val shortDescription: String,
    val description: String,
    val company: String,
    val from: String,
    val to: String,
    val techStacks: List<TechStack>
) {
    companion object
}

fun Experience.Companion.previewData() = Experience(
    "1",
    "Android Developer",
    "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    "Engaged in the creation and management of more than 15 projects. Employed Material design libraries for impressive app visuals. Incorporated external libraries for handling RESTful API requests, ensuring smooth data exchange. Utilized an internal Room database for cached startup, maintaining a clear division between remote and local data. Proficient with hardware components and establishing Bluetooth connections for enhanced functionality. Managed a Core library for all projects, boosting code reusability and efficiency. Strongly inclined towards MVVM pattern and best practices, making use of official Android libraries to optimize framework potential.",
    "Wisemen (formerly Appwise)",
    "2017-05-01'T'00:00:00",
    "2023-10-16'T'00:00:00",
    listOf(TechStack.Android, TechStack.AndroidTV)
)