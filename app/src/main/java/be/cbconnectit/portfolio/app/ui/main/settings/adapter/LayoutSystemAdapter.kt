package be.cbconnectit.portfolio.app.ui.main.settings.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import be.cbconnectit.portfolio.app.R
import be.cbconnectit.portfolio.app.domain.enums.LayoutSystem

class LayoutSystemAdapter(context: Context, list: List<LayoutSystem>) : ArrayAdapter<LayoutSystem>(context, R.layout.list_popup_window_item, list) {

    private var selectedLayoutSystem: LayoutSystem? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val listItem: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_popup_window_item, parent, false)

        val tvName = listItem.findViewById<TextView>(R.id.tvName)
        val ivCheck = listItem.findViewById<ImageView>(R.id.ivCheck)

        val layoutSystem = getItem(position) ?: throw Exception("Invalid position")

        tvName.setText(layoutSystem.systemName)
        ivCheck.isVisible = selectedLayoutSystem == layoutSystem

        return listItem
    }

    fun updateSelectedLayoutSystem(selectedLayoutSystem: LayoutSystem?) {
        this.selectedLayoutSystem = selectedLayoutSystem
        notifyDataSetChanged()
    }
}