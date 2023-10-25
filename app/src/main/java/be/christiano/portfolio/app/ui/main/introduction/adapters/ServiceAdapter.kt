package be.christiano.portfolio.app.ui.main.introduction.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import be.christiano.portfolio.app.databinding.ListItemServiceBinding
import be.christiano.portfolio.app.domain.model.Service
import coil.load

class ServiceAdapter : ListAdapter<Service, ServiceAdapter.ServiceViewHolder>(SERVICE_DIFF) {

    companion object {
        private val SERVICE_DIFF = object : DiffUtil.ItemCallback<Service>() {
            override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        return ServiceViewHolder(ListItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ServiceViewHolder(private val binding: ListItemServiceBinding) : ViewHolder(binding.root) {
        fun bind(item: Service) {
            binding.service = item

            binding.ivBanner.load(item.image)
        }
    }
}