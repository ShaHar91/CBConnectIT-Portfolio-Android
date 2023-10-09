package be.christiano.portfolio.app.data.models

import androidx.annotation.DrawableRes
import be.christiano.portfolio.app.R

enum class Testimonial(@DrawableRes val img: Int, val fullName: String, val function: String, val review: String) {
    First(
        img = R.drawable.avatar1,
        fullName = "John Doe",
        function = "Android Developer",
        review = "I am incredibly fortunate to have such an inspiring mentor like Christiano. Their unwavering guidance and insightful advice have been the cornerstone of my professional growth. Their ability to navigate complex challenges with poise and their willingness to share knowledge has truly elevated my skills. Christiano leads with a perfect blend of patience and expertise, making them a true role model. Grateful for the opportunity to learn and be guided by the best!"
    ),
    Second(
        img = R.drawable.avatar2,
        fullName = "Shrek",
        function = "Android Lead",
        review = "Working alongside Christiano has been an absolute privilege. Their tireless dedication to our team's growth and success is truly commendable. Christiano consistently goes above and beyond, readily offering assistance and putting in the effort to ensure our collective progress. As a coworker, their commitment to collaboration and their unwavering support make them an invaluable asset. Grateful to be part of a team led by someone who leads not just by words, but by inspiring actions."
    ),
    Third(
        img = R.drawable.avatar3,
        fullName = "Fiona",
        function = "Developer",
        review = "It's a pleasure to express my admiration for Christiano. Their professionalism, dedication, and positive attitude create an incredible work environment. Christiano's exceptional ability to collaborate and communicate elevates team projects, making even complex tasks seem manageable. Their insightful contributions and willingness to assist showcase their remarkable work ethic. Truly grateful for the opportunity to work alongside someone who consistently sets a high standard for excellence."
    ),
    Fourth(
        img = R.drawable.avatar4,
        fullName = "Donkey",
        function = "Android Developer",
        review = "Christiano was an invaluable asset to our team. Their exceptional skills and dedication consistently yielded outstanding results. Their proactive approach to challenges and ability to work harmoniously with colleagues showcased exemplary professionalism. Christiano truly exceeded expectations and significantly contributed to our success."
    )
}