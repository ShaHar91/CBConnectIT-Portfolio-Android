package be.cbconnectit.portfolio.app.ui.main.introduction.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.annotation.AttrRes
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import be.cbconnectit.portfolio.app.databinding.ItemTechStackBinding
import be.cbconnectit.portfolio.app.databinding.ListItemExperienceVertBinding
import be.cbconnectit.portfolio.app.domain.model.Experience
import com.google.android.material.color.MaterialColors
import com.google.android.material.R as MaterialR

class ExperienceVerticalAdapter : ListAdapter<Experience, ExperienceVerticalAdapter.ExperienceViewHolder>(Experience.EXPERIENCE_DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceViewHolder {
        return ExperienceViewHolder(ListItemExperienceVertBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ExperienceViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ExperienceViewHolder(private val binding: ListItemExperienceVertBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Experience, position: Int) {
            fun getColor(@AttrRes color: Int): ColorStateList {
                return MaterialColors.getColorStateList(binding.root.context, color, ColorStateList.valueOf(Color.BLACK))
            }

            binding.item = item

            val isCurrent = position == 0
            binding.isCurrent = isCurrent

            val cardBackgroundColor = if (isCurrent) getColor(MaterialR.attr.colorPrimary) else getColor(MaterialR.attr.colorSurfaceContainerLow)
            binding.mcvExperience.setCardBackgroundColor(cardBackgroundColor)

            val textColor = if (isCurrent) getColor(MaterialR.attr.colorOnPrimary) else getColor(MaterialR.attr.colorOnSurface)
            binding.tvExperienceBody.setTextColor(textColor)

            binding.llTechStackWrapper.removeAllViews()

            item.techStacks.forEach {
                val view = ItemTechStackBinding.inflate(LayoutInflater.from(binding.root.context), binding.llTechStackWrapper, false)
                view.ivTechStack.updateLayoutParams {
                    val some = 5 * view.root.resources.displayMetrics.density
                    (this as? MarginLayoutParams)?.setMargins(0, some.toInt(), 0, some.toInt())
                }
                view.root.id = View.generateViewId()
                view.ivTechStack.setImageResource(it.iconRes)
                view.isCurrent = isCurrent
                binding.llTechStackWrapper.addView(view.root)
            }
        }
    }
}