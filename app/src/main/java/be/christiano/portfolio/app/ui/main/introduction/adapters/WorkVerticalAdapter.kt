package be.christiano.portfolio.app.ui.main.introduction.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import be.christiano.portfolio.app.databinding.ItemTagBinding
import be.christiano.portfolio.app.databinding.ListItemProjectVertBinding
import be.christiano.portfolio.app.domain.model.Work
import coil.load

class WorkVerticalAdapter : ListAdapter<Work, WorkVerticalAdapter.WorkViewHolder>(Work.WORK_DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkViewHolder {
        return WorkViewHolder(ListItemProjectVertBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: WorkViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WorkViewHolder(private val binding: ListItemProjectVertBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Work) {
            binding.item = item

            val flow = binding.clFlow.children.first()
            binding.clFlow.removeAllViews()
            binding.clFlow.addView(flow)

            val viewIds = item.tags.map {
                val view = ItemTagBinding.inflate(LayoutInflater.from(binding.root.context), binding.flowTags.parent as ViewGroup, false)
                view.root.id = View.generateViewId()
                view.text = it.name
                binding.clFlow.addView(view.root)

                // return the view ID
                view.root.id
            }

            binding.flowTags.referencedIds = viewIds.toIntArray()
        }
    }
}