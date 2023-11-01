package be.cbconnectit.portfolio.app.ui.main.introduction.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import be.cbconnectit.portfolio.app.databinding.ItemTagBinding
import be.cbconnectit.portfolio.app.databinding.ListItemProjectBinding
import be.cbconnectit.portfolio.app.domain.model.Work
import coil.load

class WorkHorizontalAdapter : ListAdapter<Work, WorkHorizontalAdapter.WorkViewHolder>(Work.WORK_DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkViewHolder {
        return WorkViewHolder(ListItemProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: WorkViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WorkViewHolder(private val binding: ListItemProjectBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Work) {
            binding.item = item

            binding.ivBanner.load(item.bannerImage)

            val viewIds = item.tags.map {
                val view = ItemTagBinding.inflate(LayoutInflater.from(binding.root.context), binding.flowTags.parent as ViewGroup, false)
                view.root.id = View.generateViewId()
                view.text = it.name
                binding.clRoot.addView(view.root)

                // return the view ID
                view.root.id
            }

            binding.flowTags.referencedIds = viewIds.toIntArray()
        }
    }
}