package be.cbconnectit.portfolio.app.domain.model

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import be.cbconnectit.portfolio.app.R

data class Service(
    val id: String,
    val imageUrl: String,
    val bannerImageUrl: String?= null,
    val title: String,
    val shortDescription: String? = null,
    val description: String,
    val bannerDescription: String? = null,
    val subServices: List<Service>? = null,
    val extraInfo: String? = null,
    val tag: Tag? = null,
    val createdAt: String,
    val updatedAt: String
) {
    companion object {
        val SERVICE_DIFF = object : DiffUtil.ItemCallback<Service>() {
            override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
                return oldItem == newItem
            }
        }
    }
}

@DrawableRes
fun Service.getServiceTypeIcon(): Int? {
    return when {
        title.lowercase().startsWith("mobile") -> R.drawable.ic_smartphone
        title.lowercase().startsWith("web") -> R.drawable.ic_frontend
        title.lowercase().startsWith("backend") -> R.drawable.ic_backend
        title.lowercase().startsWith("tutoring") -> R.drawable.ic_tutoring
        else -> null
    }
}

@DrawableRes
fun Service.typeImage(): Int? {
    return when {
        title.lowercase().startsWith("mobile") -> R.drawable.img_services_mobile
        title.lowercase().startsWith("web") -> R.drawable.img_services_web
        title.lowercase().startsWith("backend") -> R.drawable.img_services_backend
        title.lowercase().startsWith("tutoring") -> R.drawable.img_services_tutoring
        else -> null
    }
}

fun Service.Companion.previewData() = Service(
    "1",
    "",
    "Android Development",
    "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    "Lorum ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    createdAt = "",
    updatedAt = ""
)