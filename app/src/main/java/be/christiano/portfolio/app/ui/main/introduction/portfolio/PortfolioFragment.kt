package be.christiano.portfolio.app.ui.main.introduction.portfolio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import be.christiano.portfolio.app.R
import be.christiano.portfolio.app.databinding.FragmentPorfolioBinding
import be.christiano.portfolio.app.extensions.startWeb
import be.christiano.portfolio.app.ui.main.base.ToolbarDelegate
import be.christiano.portfolio.app.ui.main.base.ToolbarDelegateImpl
import be.christiano.portfolio.app.ui.main.base.dataBinding
import be.christiano.portfolio.app.ui.main.introduction.IntroductionUiEvent
import be.christiano.portfolio.app.ui.main.introduction.adapters.WorkVerticalAdapter
import com.google.android.material.color.MaterialColors
import com.google.android.material.divider.MaterialDividerItemDecoration
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
        WorkVerticalAdapter {
            mViewModel.onEvent(PortfolioEvent.OpenSocialLink(it))
        }
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
        binding.rvItems.setHasFixedSize(false)

        val decoration = MaterialDividerItemDecoration(requireContext(), LinearLayout.VERTICAL)
        decoration.isLastItemDecorated = false
        decoration.setDividerInsetEndResource(requireContext(), R.dimen.divider_margin)
        decoration.setDividerInsetStartResource(requireContext(), R.dimen.divider_margin)
        decoration.dividerColor = MaterialColors.getColor(requireView(), com.google.android.material.R.attr.colorPrimary)
        decoration.setDividerThicknessResource(requireContext(), R.dimen.divider_thickness)
        binding.rvItems.addItemDecoration(decoration)
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            mViewModel.state.collectLatest {
                workAdapter.submitList(it.projects)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            mViewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is PortfolioUiEvent.OpenSocialLink -> {
                        val color = MaterialColors.getColor(requireView(), com.google.android.material.R.attr.colorSurfaceContainer)

                        requireActivity().startWeb(
                            event.link.url,
                            toolbarColor = color
                        )
                    }
                }
            }
        }
    }
}