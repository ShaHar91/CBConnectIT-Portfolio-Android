package be.christiano.portfolio.app.domain.model

import androidx.recyclerview.widget.DiffUtil

data class Testimonial(
    val id: String,
    val image: String,
    val fullName: String,
    val function: String,
    val review: String
) {
    companion object {
        val TESTIMONIAL_DIFF = object : DiffUtil.ItemCallback<Testimonial>() {
            override fun areItemsTheSame(oldItem: Testimonial, newItem: Testimonial): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Testimonial, newItem: Testimonial): Boolean {
                return oldItem == newItem
            }
        }
    }
}

fun Testimonial.Companion.previewData() = Testimonial(
    "1",
    "https://raw.githubusercontent.com/ShaHar91/Portfolio/master/public/avatar4.png",
    "Shrek",
    "Project Manager",
    "I had the pleasure of working closely with Christiano Bolla in the process of making an personalised tracker application. He was very aware of the requirements I had, we discussed them thoroughly and he was able to give me some insights and good suggestions that broadend my view. During the process we could communicate in a very productive and convivial way. He kept me updated, had intermediate updates and was very clear in what feature was possible or would be a little more difficult to achieve. The end result was a very nice looking, usability friendly and functional application. I also managed to get a glimps of his coaching attitude when he was explaining certain technical steps in a friendly and understandable, yet concrete, way to me. Since a recent amount of time I have started working more in IT myself, as a test engineer. He has been a supportive help in the process as after -support. This even states his way of working in an agile method more. I would definetly advice him to others!"
)