package be.christiano.portfolio.app.ui.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.databinding.FragmentComponentsBinding
import be.christiano.portfolio.app.ui.base.ToolbarDelegate
import be.christiano.portfolio.app.ui.base.ToolbarDelegateImpl
import be.christiano.portfolio.app.ui.base.dataBinding

// TODO: a list with all components (left item is a little snapshot or thumbnail of the actual view!)
// TODO: upon clicking, open a new fragment/activity with the component showing. If possible, also show a toggle to show the compose or xml version?
class ComponentsFragment : Fragment(), ToolbarDelegate by ToolbarDelegateImpl() {

    private val mViewModel by viewModels<ComponentsViewModel>()
    private val binding by dataBinding<FragmentComponentsBinding>(R.layout.fragment_components) {
        registerToolbar(this@ComponentsFragment, mtbMain)
        viewModel = mViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
