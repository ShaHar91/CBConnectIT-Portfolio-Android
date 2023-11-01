package be.cbconnectit.portfolio.app.ui.main.introduction.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import be.cbconnectit.portfolio.app.R
import be.cbconnectit.portfolio.app.databinding.ItemLinkBinding
import be.cbconnectit.portfolio.app.databinding.ItemTagBinding
import be.cbconnectit.portfolio.app.databinding.ListItemProjectVertBinding
import be.cbconnectit.portfolio.app.domain.model.Link
import be.cbconnectit.portfolio.app.domain.model.Work
import coil.load
import com.google.android.material.color.MaterialColors

class WorkVerticalAdapter(
    val onItemClicked: (link: Link) -> Unit
) : ListAdapter<Work, WorkVerticalAdapter.WorkViewHolder>(Work.WORK_DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkViewHolder {
        return WorkViewHolder(ListItemProjectVertBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: WorkViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class WorkViewHolder(private val binding: ListItemProjectVertBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Work, position: Int) {
            val ctx = binding.root.context
            binding.item = item
            binding.imageStartAligned = position % 2 == 0

            binding.ivStartImage.load(item.image)

            // <editor-fold desc="Dynamic tags">
            val flow = binding.clFlow.children.first()
            binding.clFlow.removeAllViews()
            binding.clFlow.addView(flow)

            val viewIds = item.tags.map {
                val view = ItemTagBinding.inflate(LayoutInflater.from(ctx), binding.flowTags.parent as ViewGroup, false)
                view.root.id = View.generateViewId()
                view.text = it.name
                binding.clFlow.addView(view.root)

                // return the view ID
                view.root.id
            }

            binding.flowTags.referencedIds = viewIds.toIntArray()
            // </editor-fold>

            // <editor-fold desc="Dynamic techStacks">
            val techStacks = binding.clLinks.children.first()
            binding.clLinks.removeAllViews()
            binding.clLinks.addView(techStacks)

            val linkViewIds = item.links.map { link ->
                val view = ItemLinkBinding.inflate(LayoutInflater.from(ctx), binding.flowLinks.parent as ViewGroup, false)
                view.root.id = View.generateViewId()
                view.iconDrawable = ContextCompat.getDrawable(ctx, link.type.iconRes)
                binding.clLinks.addView(view.root)

                view.root.setOnClickListener {
                    onItemClicked(link)
                }

                // return the view ID
                view.root.id
            }

            binding.flowLinks.referencedIds = linkViewIds.toIntArray()
            // </editor-fold>

            // Had to do it like this because the Custom View was not made with Material design in mind and uses a HTML text converter which removes any styling
            binding.ftvDescription.textColor = MaterialColors.getColor(binding.root, com.google.android.material.R.attr.colorOnBackground)
            binding.ftvDescription.setTextSize(ctx.resources.getDimension(R.dimen.body_text_size))
        }
    }
}