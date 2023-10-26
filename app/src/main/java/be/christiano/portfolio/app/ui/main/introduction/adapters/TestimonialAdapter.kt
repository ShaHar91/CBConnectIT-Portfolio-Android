package be.christiano.portfolio.app.ui.main.introduction.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import be.christiano.portfolio.app.databinding.ListItemTestimonialBinding
import be.christiano.portfolio.app.domain.model.Testimonial
import coil.load

class TestimonialAdapter : ListAdapter<Testimonial, TestimonialAdapter.TestimonialViewHolder>(Testimonial.TESTIMONIAL_DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestimonialViewHolder {
        return TestimonialViewHolder(ListItemTestimonialBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TestimonialViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TestimonialViewHolder(private val binding: ListItemTestimonialBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Testimonial) {
            binding.item = item

            binding.ivBanner.load(item.image)
        }
    }
}