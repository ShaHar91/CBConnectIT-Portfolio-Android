package be.christiano.portfolio.app.ui.main.introduction.portfolio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.databinding.FragmentPorfolioBinding
import be.christiano.portfolio.app.ui.main.base.ToolbarDelegate
import be.christiano.portfolio.app.ui.main.base.ToolbarDelegateImpl
import be.christiano.portfolio.app.ui.main.base.dataBinding
import be.christiano.portfolio.app.ui.main.introduction.adapters.WorkVerticalAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PortfolioFragment : Fragment(), ToolbarDelegate by ToolbarDelegateImpl() {

    private val mViewModel by viewModel<PortfolioViewModel>()
    private val binding by dataBinding<FragmentPorfolioBinding>(R.layout.fragment_porfolio) {
        registerToolbar(this@PortfolioFragment, mtbMain)
        viewModel = mViewModel
    }

    private val workAdapter by lazy {
        WorkVerticalAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.rvItems.adapter = workAdapter
        binding.rvItems.setHasFixedSize(true)
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            mViewModel.state.collectLatest {
                workAdapter.submitList(it.projects)
            }
        }
    }
}