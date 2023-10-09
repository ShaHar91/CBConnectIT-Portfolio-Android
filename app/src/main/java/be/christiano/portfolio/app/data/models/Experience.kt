package be.christiano.portfolio.app.data.models

enum class Experience(
    val number: String,
    val jobPosition: String,
    val description: String,
    val company: String,
    val from: String,
    val to: String
) {
    First(
        number = "01",
        jobPosition = "Android Developer",
        description = "Engaged in the creation and management of more than 15 projects. Employed Material design libraries for impressive app visuals. Incorporated external libraries for handling RESTful API requests, ensuring smooth data exchange. Utilized an internal Room database for cached startup, maintaining a clear division between remote and local data. Proficient with hardware components and establishing Bluetooth connections for enhanced functionality. Managed a Core library for all projects, boosting code reusability and efficiency. Strongly inclined towards MVVM pattern and best practices, making use of official Android libraries to optimize framework potential.",
        company = "Wisemen (formerly Appwise)",
        from = "May 2017",
        to = "October 2023"
    ),
    Second(
        number = "02",
        jobPosition = "Android (TV) Developer",
        description = "Developed and maintained white labelled Android and Android TV applications with well over 300k downloads spread over the different labels. Worked in a Scrum environment to deliver incremental updates with new features and bug fixes",
        company = "Zappware",
        from = "2018",
        to = "2020"
    ),
    Third(
        number = "03",
        jobPosition = "Freelancer",
        description = "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        company = "Netflix",
        from = "March 2020",
        to = "December 2020"
    )
}